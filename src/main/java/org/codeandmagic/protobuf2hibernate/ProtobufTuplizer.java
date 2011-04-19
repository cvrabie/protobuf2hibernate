package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.apache.commons.collections.iterators.ObjectGraphIterator;
import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.SessionImpl;
import org.hibernate.mapping.MetaAttribute;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.property.Getter;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.EntityTuplizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ProtobufTuplizer implements EntityTuplizer{
    public final static EntityMode PROTOBUF_ENTITY_MODE = new EntityMode("protobuf");
    private final static Logger log = LoggerFactory.getLogger(ProtobufTuplizer.class);

    private final EntityMetamodel entityMetamodel;
    private final PersistentClass mappingInfo;
    private final Class<? extends Message.Builder> builderClass;
    private final String idPropertyName;
    private final String versionPropertyName;

    private final Map<Integer,String> propertyIndexToPropertyName = new HashMap<Integer,String>();;
    private final Map<String,Descriptors.FieldDescriptor> fieldCacheByName = new HashMap<String, Descriptors.FieldDescriptor>();
    private final Map<Integer,Descriptors.FieldDescriptor> fieldCacheByIndex = new HashMap<Integer, Descriptors.FieldDescriptor>();
    private final Map<String, Property> propertyCacheByName = new HashMap<String, Property>();
    private final Map<String, FieldAccessType> accessTypeByName = new HashMap<String, FieldAccessType>();

    public static enum FieldAccessType{
        NO_TRANSFORM, TRANSFORM_MESSAGE_TO_BUILDER
    }

    public ProtobufTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.entityMetamodel = entityMetamodel;
        this.mappingInfo = mappingInfo;
        this.builderClass = mappingInfo.getMappedClass();
        assert Message.Builder.class.isAssignableFrom(this.builderClass);

        //the properties of the mapped class
        final Property idProperty = mappingInfo.getIdentifierProperty();
        idPropertyName = null != idProperty ? idProperty.getName() : null;

        final Property versionProperty = mappingInfo.getVersion();
        versionPropertyName = null != versionProperty ? versionProperty.getName() : null;

        int i = 0;
        final Iterator iter = mappingInfo.getPropertyClosureIterator();
        while (iter.hasNext()){
            final Property property = (Property) iter.next();
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
        log.debug("Built cache for {} properties in entity {}",fieldCacheByName.size(),entityMetamodel.getName());
    }

    @Override
    public Class getMappedClass() {
        return builderClass;
	}

    @Override
    public boolean isInstance(Object object){
        return this.builderClass.isAssignableFrom(object.getClass());
    }

    @Override
    public Object instantiate() throws HibernateException{
        try {
            return MessageUtil.newBuilder(builderClass);
        } catch (Exception e){
            throw new HibernateException(e);
        }
    }

    @Override
    public Object getPropertyValue(final Object entity, final int i) throws HibernateException{
        return getPropertyValue(entity, fieldCacheByIndex.get(i));
    }

    @Override
    public Object getPropertyValue(final Object entity, final String propertyName) throws HibernateException{
        return getPropertyValue(entity, fieldCacheByName.get(propertyName));
    }

    private Object getPropertyValue(final Object entity, final Descriptors.FieldDescriptor field){
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

    private Object transformMessageToBuilder(Message val){
        SessionImpl session = (SessionImpl)entityMetamodel.getSessionFactory().getCurrentSession();
        PersistenceContext context = session.getPersistenceContext();
        EntityPersister persister = entityMetamodel.getSessionFactory().getEntityPersister(entityMetamodel.getName());
        EntityKey key = new EntityKey(getIdentifierForMessage(val), persister, session.getEntityMode());

        //context.removeEntity(key);
        //return ProtobufTransformer.protobufMessageToBuilder(val);

        Object entityInContext = context.getEntity(key);
        if(null == entityInContext) return ProtobufTransformer.protobufMessageToBuilder(val);
        else return entityInContext;
    }

    private Object transformCollectionToBuilders(Collection<?> value){
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
        final int span = entityMetamodel.getPropertySpan();
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
        final int span = entityMetamodel.getPropertySpan();
        for ( int i = 0; i < span; i++ ) {
            setPropertyValue(entity, i, values[i]);
        }
    }

    @Override
    public Object[] getPropertyValuesToInsert(final Object entity, final Map mergeMap,
                                              final SessionImplementor session) throws HibernateException{
        return getPropertyValues(entity);
    }

    @Override
    public void setPropertyValue(final Object entity, final int index,
                                 final Object value) throws HibernateException{
        setPropertyValue(entity, fieldCacheByIndex.get(index), value);
    }

    @Override
    public void setPropertyValue(final Object entity, final String propertyName,
                                 final Object value) throws HibernateException{
        setPropertyValue(entity, fieldCacheByName.get(propertyName), value);
    }

    private void setPropertyValue(final Object entity, final Descriptors.FieldDescriptor field,
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
    public EntityMode getEntityMode(){
        return EntityMode.POJO;
    }

    @Override
    public Object instantiate(final Serializable id) throws HibernateException{
        Object instance = instantiate();
        if(null != id) setIdentifier(instance, id);
        return instance;
    }

    @Override
    public Serializable getIdentifier(final Object entity) throws HibernateException{
        if(null == idPropertyName){
            throw new HibernateException("This entity has no mapped id!");
        }
        Object idValue = getPropertyValue(entity, idPropertyName);
        assert idValue instanceof Serializable;
        return (Serializable) idValue;
    }

    private Serializable getIdentifierForMessage(Message val) {
        if(null == idPropertyName){
            throw new HibernateException("This entity has no mapped id!");
        }
        Descriptors.FieldDescriptor field = fieldCacheByName.get(idPropertyName);
        Object idValue = val.getField(field);
        assert idValue instanceof Serializable;
        return (Serializable) idValue;
    }


    @Override
    public void setIdentifier(final Object entity, final Serializable id) throws HibernateException{
        if(null == idPropertyName){
            throw new HibernateException("This entity has no mapped id!");
        }
        setPropertyValue(entity, idPropertyName, id);
    }

    @Override
    public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion){
        throw new HibernateException(new UnsupportedOperationException("id and version rollback are not supported YET!"));
    }

    @Override
    public Object getVersion(Object entity) throws HibernateException{
        if ( !entityMetamodel.isVersioned() ) return null;
		return getPropertyValue(entity, versionPropertyName);
    }

    @Override
    public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session){
       //NOTHING
    }

    @Override
    public boolean hasProxy(){
        return false;
    }

    @Override
    public Object createProxy(Serializable id, SessionImplementor session) throws HibernateException{
        throw new HibernateException(new UnsupportedOperationException("proxies not supported YET!"));
    }

    @Override
    public boolean isLifecycleImplementor(){
        return false;
    }

    @Override
    public boolean isValidatableImplementor(){
        return false;
    }

    @Override
    public Class getConcreteProxyClass(){
        throw new HibernateException(new UnsupportedOperationException("proxies not supported YET!"));
    }

    @Override
    public boolean hasUninitializedLazyProperties(Object entity) {
        return false; //NOT SUPPORTED YET
    }

    @Override
    public boolean isInstrumented(){
        return false;
    }

    @Override
    public EntityNameResolver[] getEntityNameResolvers(){
        return null; //NOTHING
    }

    @Override
    public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory){
        final Class concreteEntityClass = entityInstance.getClass();
		if ( concreteEntityClass == getMappedClass() ) {
			return entityMetamodel.getName();
		}
		else {
			String entityName = entityMetamodel.findEntityNameByEntityClass(concreteEntityClass);
			if ( entityName == null ) {
				throw new HibernateException(
						"Unable to resolve entity name from Class [" + concreteEntityClass.getName() + "]"
								+ " expected instance/subclass of [" + entityMetamodel.getName() + "]"
				);
			}
			return entityName;
		}
    }

    /** New in hibernate 3.5 **/

    @Override
    public Object instantiate(Serializable id, SessionImplementor session) {
        return instantiate(id);
    }

    @Override
    public Serializable getIdentifier(Object o, SessionImplementor session) {
        return getIdentifier(o);
    }

    @Override
    public void setIdentifier(Object o, Serializable id, SessionImplementor session) {
        setIdentifier(o, id);
    }

    @Override
    public void resetIdentifier(Object o, Serializable id, Object o1, SessionImplementor session) {
        resetIdentifier(o, id, o1);
    }

    @Override
    public Getter getIdentifierGetter() {
        //TODO
        return null;
    }

    @Override
    public Getter getVersionGetter() {
        //TODO
        return null;
    }


    @Override
    public Getter getGetter(int i) {
        //TODO
        return null;
    }
}
