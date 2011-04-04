package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.EntityTuplizer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProtobufTuplizer implements EntityTuplizer{
    public final static EntityMode PROTOBUF_ENTITY_MODE = new EntityMode("protobuf");

    private final EntityMetamodel entityMetamodel;
    private final PersistentClass mappingInfo;
    //private final Class<? extends Message> mappedClass;
    private final Class<? extends Message.Builder> builderClass;
    private final Map<Integer,String> propertyIndexToPropertyName;
    private final String idPropertyName;
    private final String versionPropertyName;

    //number of properties in this class
    //private final int propertySpan;

    public ProtobufTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo) {
        this.entityMetamodel = entityMetamodel;
        this.mappingInfo = mappingInfo;

        //the mapped class
        //this.mappedClass = mappingInfo.getMappedClass();
        //assert Message.class.isAssignableFrom(mappedClass);

        //the protobuf builder of the mapped class
        //try{
        //    this.builderClass = (Class<? extends Message.Builder>) MessageUtil.newBuilderForMessage(this.mappedClass).getClass();
        //    assert Message.Builder.class.isAssignableFrom(this.builderClass);
        //}catch (Exception e){
        //    throw new HibernateException(e);
        //}

        this.builderClass = mappingInfo.getMappedClass();
        assert Message.Builder.class.isAssignableFrom(this.builderClass);

        //the properties of the mapped class
        Property idProperty = mappingInfo.getIdentifierProperty();
        idPropertyName = null != idProperty ? idProperty.getName() : null;

        Property versionProperty = mappingInfo.getVersion();
        versionPropertyName = null != versionProperty ? versionProperty.getName() : null;

        propertyIndexToPropertyName = new HashMap<Integer,String>();
        Iterator iter = mappingInfo.getPropertyClosureIterator();

        int i = 0;
        while (iter.hasNext()){
            Property property = (Property) iter.next();
            this.propertyIndexToPropertyName.put(i, property.getName());
            ++i;
        }
    }

    @Override
    public Class getMappedClass() {
		//return mappedClass;
        return builderClass;
	}

    @Override
    public boolean isInstance(Object object){
        //return (this.mappedClass.isAssignableFrom(object.getClass())) || (this.builderClass.isAssignableFrom(object.getClass()));
        return this.builderClass.isAssignableFrom(object.getClass());
    }

    @Override
    public Object instantiate() throws HibernateException{
        try {
            //return MessageUtil.newBuilderForMessage(mappedClass);
            return MessageUtil.newBuilder(builderClass);
        } catch (Exception e){
            throw new HibernateException(e);
        }
    }

    @Override
    public Object getPropertyValue(Object entity, int i) throws HibernateException{
        String propertyName = this.propertyIndexToPropertyName.get(i);
        if(propertyName == null) throw new HibernateException("No property with index "+i);
        return getPropertyValue(entity, propertyName);
    }

    @Override
    public Object getPropertyValue(Object entity, String propertyName) throws HibernateException{
        propertyName = CamelCase.toUnderscored(propertyName);
        /*if(entity instanceof Message){
            Message msg = (Message) entity;
            return msg.getField(msg.getDescriptorForType().findFieldByName(propertyName));
        }else */if(entity instanceof Message.Builder){
            Message.Builder msg = (Message.Builder) entity;
            return msg.getField(msg.getDescriptorForType().findFieldByName(propertyName));
        }else{
            throw new HibernateException("Can only handle protobuf Message or Message.Builder");
        }
    }

    @Override
    public Object[] getPropertyValues(Object entity) throws HibernateException{
        final int span = entityMetamodel.getPropertySpan();
		final Object[] result = new Object[span];
        for ( int i = 0; i < span; i++ ) {
            result[i] = getPropertyValue(entity, i);
        }
        return result;
    }

    @Override
    public void setPropertyValues(Object entity, Object[] values) throws HibernateException{
        if(!(entity instanceof Message.Builder)){
            throw new HibernateException("Can only handle protobuf Message.Builder");
        }
        Message.Builder msg = (Message.Builder) entity;
        final int span = entityMetamodel.getPropertySpan();
        for ( int i = 0; i < span; i++ ) {
            String propertyName = this.propertyIndexToPropertyName.get(i);
            propertyName = CamelCase.toUnderscored(propertyName);
            if(propertyName == null) throw new HibernateException("No property with index "+i);
            msg.setField(msg.getDescriptorForType().findFieldByName(propertyName),values[i]);
        }
    }

    @Override
    public Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session) throws HibernateException{
        return getPropertyValues(entity);
    }

    @Override
    public void setPropertyValue(Object entity, int index, Object value) throws HibernateException{
        String propertyName = this.propertyIndexToPropertyName.get(index);
        if(propertyName == null) throw new HibernateException("No property with index "+index);
        setPropertyValue(entity, propertyName, value);
    }

    @Override
    public void setPropertyValue(Object entity, String propertyName, Object value) throws HibernateException{
        propertyName = CamelCase.toUnderscored(propertyName);
        if(!(entity instanceof Message.Builder)){
            throw new HibernateException("Can only handle protobuf Message.Builder");
        }
        Message.Builder msg = (Message.Builder) entity;
        msg.setField(msg.getDescriptorForType().findFieldByName(propertyName), value);
    }

    @Override
    public EntityMode getEntityMode(){
        return EntityMode.POJO;
    }

    @Override
    public Object instantiate(Serializable id) throws HibernateException{
        Object instance = instantiate();
        if(null != id) setIdentifier(instance, id);
        return instance;
    }

    @Override
    public Serializable getIdentifier(Object entity) throws HibernateException{
        if(null == idPropertyName){
            throw new HibernateException("This entity has no mapped id!");
        }
        Object idValue = getPropertyValue(entity, idPropertyName);
        assert idValue instanceof Serializable;
        return (Serializable) idValue;
    }

    @Override
    public void setIdentifier(Object entity, Serializable id) throws HibernateException{
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
}
