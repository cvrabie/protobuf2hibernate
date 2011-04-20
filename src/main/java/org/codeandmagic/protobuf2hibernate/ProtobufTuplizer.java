package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.hibernate.HibernateException;
import org.hibernate.mapping.MetaAttribute;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.tuple.Tuplizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class ProtobufTuplizer implements Tuplizer {
    private final static Logger log = LoggerFactory.getLogger(ProtobufTuplizer.class);

    protected final Class<? extends Message.Builder> builderClass;
    protected final String entityName;

    protected final int propertySpan;
    protected final Map<Integer,String> propertyIndexToPropertyName = new HashMap<Integer,String>();;
    protected final Map<String,Descriptors.FieldDescriptor> fieldCacheByName = new HashMap<String, Descriptors.FieldDescriptor>();
    protected final Map<Integer,Descriptors.FieldDescriptor> fieldCacheByIndex = new HashMap<Integer, Descriptors.FieldDescriptor>();
    protected final Map<String, Property> propertyCacheByName = new HashMap<String, Property>();
    protected final Map<String, FieldAccessType> accessTypeByName = new HashMap<String, FieldAccessType>();

    public static enum FieldAccessType{
        NO_TRANSFORM, TRANSFORM_MESSAGE_TO_BUILDER
    }

    public ProtobufTuplizer(final String entityName,
                            final Class<? extends Message.Builder> builderClass,
                            final int propertySpan,
                            final Iterator<Property> propertiesIterator)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assert Message.Builder.class.isAssignableFrom(builderClass);
        this.builderClass = builderClass;
        this.entityName = entityName;
        this.propertySpan = propertySpan;

        //INITIALIZE PROPERTIES
        int i = 0;
        while (propertiesIterator.hasNext()){
            final Property property = (Property) propertiesIterator.next();
            propertyCacheByName.put(property.getName(), property);
            propertyIndexToPropertyName.put(i, property.getName());
            ++i;
        }

        //create field descriptor cache
        Descriptors.Descriptor classDescriptor = MessageUtil.getDescriptorForBuilderClass(builderClass);
        for(Descriptors.FieldDescriptor field : classDescriptor.getFields()){
            fieldCacheByName.put(field.getName(),field);
            final Property p = propertyCacheByName.get(field.getName());
            if(null != p){
                final MetaAttribute pt = p.getMetaAttribute("protobuf-transform");
                accessTypeByName.put(field.getName(), null==pt || "true".equals(pt.getValue().trim().toLowerCase()) ?
                                    FieldAccessType.TRANSFORM_MESSAGE_TO_BUILDER :
                                    FieldAccessType.NO_TRANSFORM);
            }
        }

        for(Map.Entry<Integer,String> entry : propertyIndexToPropertyName.entrySet()){
            fieldCacheByIndex.put(entry.getKey(), fieldCacheByName.get(entry.getValue()));
        }
        log.debug("Built cache for {} properties in entity {}",fieldCacheByName.size(), entityName);
    }

    protected abstract Object transformMessageToBuilder(Message val);

    @Override
    public Object instantiate() throws HibernateException {
        try {
            return MessageUtil.newBuilder(builderClass);
        } catch (Exception e){
            throw new HibernateException(e);
        }
    }

    @Override
    public boolean isInstance(Object object){
        return this.builderClass.isAssignableFrom(object.getClass());
    }

    @Override
    public Class getMappedClass() {
        return builderClass;
	}

    public Object getPropertyValue(final Object entity, final String propertyName) throws HibernateException{
        return getPropertyValue(entity, fieldCacheByName.get(propertyName));
    }

    @Override
    public Object getPropertyValue(final Object entity, final int i) throws HibernateException{
        return getPropertyValue(entity, fieldCacheByIndex.get(i));
    }

    protected Object getPropertyValue(final Object entity, final Descriptors.FieldDescriptor field){
        final boolean shouldTransform = FieldAccessType.TRANSFORM_MESSAGE_TO_BUILDER == accessTypeByName.get(field.getName())
                                        && Descriptors.FieldDescriptor.Type.MESSAGE == field.getType();
        if(entity instanceof Message.Builder){
            final Message.Builder msg = (Message.Builder) entity;
            if(!field.isRepeated()){//NORMAL FIELD
                if(!msg.hasField(field)) return null;
                final Object value = msg.getField(field);
                return shouldTransform ? transformMessageToBuilder((Message)value) : value;
            }else{//REPEATED
                final Collection<?> value = (Collection<?>)msg.getField(field);
                return shouldTransform ? transformCollectionToBuilders(value) : value;
            }
        }else{
            throw new HibernateException("Can only handle protobuf Message.Builder");
        }
    }

    protected Object transformCollectionToBuilders(Collection<?> value){
        final int count = value.size();
        final Object[] result = new Object[count];
        int i = 0;
        for(Message val : (Collection<Message>)value){
            result[i++] = transformMessageToBuilder(val);
        }
        return Arrays.asList(result);
    }

    @Override
    public Object[] getPropertyValues(final Object entity) throws HibernateException{
        final int span = propertySpan;
		final Object[] result = new Object[span];
        for ( int i = 0; i < span; i++ ) {
            result[i] = getPropertyValue(entity, i);
        }
        return result;
    }

    @Override
    public void setPropertyValues(final Object entity, final Object[] values) throws HibernateException{
        if(!(entity instanceof Message.Builder)) throw new HibernateException("Can only handle protobuf Message.Builder");
        Message.Builder msg = (Message.Builder) entity;
        final int span = propertySpan;
        for ( int i = 0; i < span; i++ ) {
            setPropertyValue(entity, i, values[i]);
        }
    }

    public void setPropertyValue(final Object entity, final int index,
                                 final Object value) throws HibernateException{
        setPropertyValue(entity, fieldCacheByIndex.get(index), value);
    }

    public void setPropertyValue(final Object entity, final String propertyName,
                                 final Object value) throws HibernateException{
        setPropertyValue(entity, fieldCacheByName.get(propertyName), value);
    }

    protected void setPropertyValue(final Object entity, final Descriptors.FieldDescriptor field,
                                  final Object value) throws HibernateException {
        if(!(entity instanceof Message.Builder)) throw new HibernateException("Can only handle protobuf Message.Builder");
        final Object valueToSet =   Descriptors.FieldDescriptor.Type.MESSAGE == field.getType() ?
                                        (field.isRepeated() ?
                                        ProtobufTransformer.protobufBuilderToMessage((Collection<?>)value) :
                                        ProtobufTransformer.protobufBuilderToMessage(value) ) :
                                    value;
        if(null != value) ((Message.Builder)entity).setField(field, valueToSet);
    }

    @Override
    public Getter getGetter(int i) {
        //TODO
        return null;
    }
}
