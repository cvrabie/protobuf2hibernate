package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Message;
import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.SessionImpl;
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
import java.util.Map;

public class ProtobufEntityTuplizer extends ProtobufTuplizer implements EntityTuplizer{
    private final static Logger log = LoggerFactory.getLogger(ProtobufEntityTuplizer.class);

    protected final EntityMetamodel entityMetamodel;
    protected final PersistentClass mappingInfo;
    protected final String idPropertyName;
    protected final String versionPropertyName;

    /** Constructor used when using with an entity **/
    public ProtobufEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        super(entityMetamodel.getName(), mappingInfo.getMappedClass(),
                entityMetamodel.getPropertySpan(), mappingInfo.getPropertyClosureIterator());

        this.entityMetamodel = entityMetamodel;
        this.mappingInfo = mappingInfo;

        final Property idProperty = mappingInfo.getIdentifierProperty();
        idPropertyName = null != idProperty ? idProperty.getName() : null;

        final Property versionProperty = mappingInfo.getVersion();
        versionPropertyName = null != versionProperty ? versionProperty.getName() : null;
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
    protected Object transformMessageToBuilder(Message val){
        final SessionImpl session = (SessionImpl)entityMetamodel.getSessionFactory().getCurrentSession();
        final PersistenceContext context = session.getPersistenceContext();
        //final EntityPersister persister = entityMetamodel.getSessionFactory().getEntityPersister(entityMetamodel.getName());

        //we need to transform to builder before actually checking the context because
        //we need to get the identifier, and for this we need the persister, and the
        //only knows builders. if a builder is already in context this new one will be lost and GC-ed
        final Message.Builder newBuilder = ProtobufTransformer.protobufMessageToBuilder(val);
        final String entityName = session.guessEntityName(newBuilder);

        EntityPersister persister;
        try {persister = session.getEntityPersister(entityName, newBuilder);}
        catch(HibernateException e){persister = null;}

        //must be a component
        if(null==persister){ return newBuilder; }

        final Serializable id = persister.getIdentifier(newBuilder, session);
        final EntityKey key = new EntityKey(id, persister, session.getEntityMode());

        final Object entityInContext = context.getEntity(key);
        if(null == entityInContext) return newBuilder;
        else return entityInContext;

        //context.removeEntity(key);
        //return ProtobufTransformer.protobufMessageToBuilder(val);
    }

    @Override
    public Object[] getPropertyValuesToInsert(final Object entity, final Map mergeMap,
                                              final SessionImplementor session) throws HibernateException{
        return getPropertyValues(entity);
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



}
