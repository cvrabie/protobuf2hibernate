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

    public final static String META_SELF = "protobuf-self";
    public final static String META_TRANSFORM = "protobuf-transform";

    protected final Class<? extends Message.Builder> builderClass;
    protected final Descriptors.Descriptor classDescriptor;
    protected final String entityName;

    protected final int propertySpan;
    protected final Map<Integer,String> propertyIndexToPropertyName = new HashMap<Integer,String>();;
    protected final Map<String, Object> fieldCacheByName = new HashMap<String, Object>();
    protected final Map<Integer, Object> fieldCacheByIndex = new HashMap<Integer, Object>();
    protected final Map<String, Property> propertyCacheByName = new HashMap<String, Property>();
    protected final Map<String, FieldAccessType> accessTypeByName = new HashMap<String, FieldAccessType>();

    protected final String bulkPropertyName;

    public static enum FieldAccessType{
        NO_TRANSFORM, TRANSFORM_MESSAGE_TO_BUILDER
    }

    public ProtobufTuplizer(final String entityName,
                            final Class<? extends Message.Builder> builderClass,
                            final int propertySpan,
                            final Iterator<Property> propertiesIterator)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        assert Message.Builder.class.isAssignableFrom(builderClass);
        this.builderClass = builderClass;
        this.classDescriptor = MessageUtil.getDescriptorForBuilderClass(builderClass);
        this.entityName = entityName;
        this.propertySpan = propertySpan;

        //INITIALIZE PROPERTIES
        int i = 0;
        String bulkProp = null;
        while (propertiesIterator.hasNext()){
            final Property property = (Property) propertiesIterator.next();

            //create a cache of property index to property name
            propertyCacheByName.put(property.getName(), property);
            propertyIndexToPropertyName.put(i, property.getName());

            //add field descriptor cache for properties that are not fields in the protobuf class
            final MetaAttribute selfBulk = property.getMetaAttribute(META_SELF);
            if(null != selfBulk && "true".equals(selfBulk.getValue().trim().toLowerCase())){
                fieldCacheByName.put(property.getName(), classDescriptor);
            }

            ++i;
        }
        bulkPropertyName = bulkProp;


        //create field descriptor cache
        for(Descriptors.FieldDescriptor field : classDescriptor.getFields()){
            fieldCacheByName.put(field.getName(),field);
            final Property p = propertyCacheByName.get(field.getName());
            if(null != p){
                final MetaAttribute pt = p.getMetaAttribute(META_TRANSFORM);
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

    protected Object getPropertyValue(final Object entity, final Object propertyDescriptor){
        if(null == propertyDescriptor ) throw new HibernateException("Property descriptor can' be null!");
        if(!(entity instanceof Message.Builder)) throw new HibernateException("Can only handle protobuf Message.Builder");
        Message.Builder msg = (Message.Builder)entity;

        if(propertyDescriptor instanceof Descriptors.FieldDescriptor)
        {
            //PROPERTY ACCESSES A PROTOBUF FIELD
            final Descriptors.FieldDescriptor field = (Descriptors.FieldDescriptor) propertyDescriptor;
            if(field.isRepeated()) return transformProtobufCollection(field,(Collection<?>)msg.getField(field));
            else return msg.hasField(field) ? transformProtobufValue(field,msg.getField(field)) : null;
        }else if(classDescriptor == propertyDescriptor){
            //PROPERTY IS A BULK ACCESS TO THE WHOLE MESSAGE
            return msg.clone().build();
        }else{
            throw new HibernateException("Could not understand property descriptor "+propertyDescriptor);
        }
    }

    protected boolean shouldTransformMessageToBuilder(Descriptors.FieldDescriptor field){
        return FieldAccessType.TRANSFORM_MESSAGE_TO_BUILDER == accessTypeByName.get(field.getName())
               && Descriptors.FieldDescriptor.Type.MESSAGE == field.getType();
    }

    protected boolean shouldTransformEnumToString(Descriptors.FieldDescriptor field){
        return Descriptors.FieldDescriptor.Type.ENUM == field.getType();
    }

    protected Object transformProtobufValue(Descriptors.FieldDescriptor field, Object value){
         if(shouldTransformMessageToBuilder(field)) return transformMessageToBuilder((Message) value);
         if(shouldTransformEnumToString(field)) return transformFromEnum((Descriptors.EnumValueDescriptor) value);
         return value;
    }

    protected Object transformProtobufCollection(Descriptors.FieldDescriptor field, Collection<?> value){
        final int count = value.size();
        final Object[] result = new Object[count];
        int i = 0;
        for(Object val : (Collection<?>)value){
            result[i++] = transformProtobufValue(field, val);
        }
        return Arrays.asList(result);
    }

    protected Object transformFromEnum(Descriptors.EnumValueDescriptor value){
        return value.getName();
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

    protected void setPropertyValue(final Object entity, final Object propertyDescriptor,
                                  final Object value) throws HibernateException {
        if(null == propertyDescriptor ) throw new HibernateException("Property descriptor can' be null!");
        if(!(entity instanceof Message.Builder)) throw new HibernateException("Can only handle protobuf Message.Builder");
        Message.Builder msg = (Message.Builder)entity;

        if(propertyDescriptor instanceof Descriptors.FieldDescriptor)//PROPERTY ACCESSES A PROTOBUF FIELD
        {
            final Descriptors.FieldDescriptor field = (Descriptors.FieldDescriptor) propertyDescriptor;
            final Object valueToSet = prepareValue(field, value);
            if(null != value) msg.setField(field, valueToSet);
        }
        else if(classDescriptor == propertyDescriptor){ //PROPERTY IS A BULK ACCESS TO THE WHOLE MESSAGE
            if(!(value instanceof Message)){
                throw new HibernateException("You can only set a message value in a bulk property!");
            }
            final Message val = (Message) value;
            msg.mergeFrom(val);
        }
    }

    protected Object prepareValue(Descriptors.FieldDescriptor field, Object value){
        if(null==value) return null;
        if(field.isRepeated()) return prepareCollection(field,(Collection<?>)value);
        else return prepareNonCollection(field, value);
    }

    private Object prepareNonCollection(Descriptors.FieldDescriptor field, Object value) {
        if(Descriptors.FieldDescriptor.Type.MESSAGE == field.getType()) return ProtobufTransformer.protobufBuilderToMessage(value);
        if(Descriptors.FieldDescriptor.Type.ENUM == field.getType()) return transformToEnum(field, (String) value);
        return value;
    }

    private Descriptors.EnumValueDescriptor transformToEnum(Descriptors.FieldDescriptor field, String value) {
        return field.getEnumType().findValueByName(value);
    }

    protected Object prepareCollection(Descriptors.FieldDescriptor field, Collection<?> value){
        final int count = value.size();
        final Object[] result = new Object[count];
        int i = 0;
        for(Object val : (Collection<?>)value){
            result[i++] = prepareNonCollection(field, val);
        }
        return Arrays.asList(result);
    }

    @Override
    public Getter getGetter(int i) {
        //TODO
        return null;
    }
}
