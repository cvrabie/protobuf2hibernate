package org.codeandmagic.protobuf2hibernate;

import org.hibernate.*;
import org.hibernate.event.EventSource;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;

import java.io.Serializable;
import java.sql.Connection;

import static org.codeandmagic.protobuf2hibernate.ProtobufTransformer.*;

public class ProtobufTransformerSession implements Session {
    private final SessionFactory sessionFactory;

    public ProtobufTransformerSession(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public Session getNativeSession(){
        return sessionFactory.getCurrentSession();
    }

    public EventSource getNativeEventSource(){
        return (SessionImpl)getNativeSession();
    }

    @Override
    public EntityMode getEntityMode() {
        return getNativeSession().getEntityMode();
    }

    @Override
    public Session getSession(EntityMode entityMode) {
        return getNativeSession().getSession(entityMode);
    }

    @Override
    public void flush() throws HibernateException {
        getNativeSession().flush();
    }

    @Override
    public void setFlushMode(FlushMode flushMode) {
        getNativeSession().setFlushMode(flushMode);
    }

    @Override
    public FlushMode getFlushMode() {
        return getNativeSession().getFlushMode();
    }

    @Override
    public void setCacheMode(CacheMode cacheMode) {
        getNativeSession().getFlushMode();
    }

    @Override
    public CacheMode getCacheMode() {
        return getNativeSession().getCacheMode();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return getNativeSession().getSessionFactory();
    }

    @Override
    public Connection connection() throws HibernateException {
        return getNativeSession().connection();
    }

    @Override
    public Connection close() throws HibernateException {
        return getNativeSession().close();
    }

    @Override
    public void cancelQuery() throws HibernateException {
        getNativeSession().cancelQuery();
    }

    @Override
    public boolean isOpen() {
        return getNativeSession().isOpen();
    }

    @Override
    public boolean isConnected() {
        return getNativeSession().isConnected();
    }

    @Override
    public boolean isDirty() throws HibernateException {
        return getNativeSession().isDirty();
    }

    @Override
    public Serializable getIdentifier(Object object) throws HibernateException {
        return getNativeSession().getIdentifier(protobufMessageToBuilder(object));
    }

    @Override
    public boolean contains(Object object) {
        return getNativeSession().contains(object);
    }

    @Override
    public void evict(Object object) throws HibernateException {
        getNativeSession().evict(object);
    }

    @Override
    public Object load(Class theClass, Serializable id, LockMode lockMode) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().load(theClass, id, lockMode));
    }

    @Override
    public Object load(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().load(entityName, id, lockMode));
    }

    @Override
    public Object load(Class theClass, Serializable id) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().load(theClass, id));
    }

    @Override
    public Object load(String entityName, Serializable id) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().load(entityName, id));
    }

    @Override
    public void load(Object object, Serializable id) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public void replicate(String entityName, Object object, ReplicationMode replicationMode) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public Serializable save(Object object) throws HibernateException {
        return getNativeSession().save(protobufMessageToBuilder(object));
    }

    @Override
    public Serializable save(String entityName, Object object) throws HibernateException {
        return getNativeSession().save(entityName, protobufMessageToBuilder(object));
    }

    @Override
    public void saveOrUpdate(Object object) throws HibernateException {
        getNativeSession().saveOrUpdate(protobufMessageToBuilder(object));
    }

    @Override
    public void saveOrUpdate(String entityName, Object object) throws HibernateException {
        getNativeSession().saveOrUpdate(entityName, protobufMessageToBuilder(object));
    }

    @Override
    public void update(Object object) throws HibernateException {
        getNativeSession().update(protobufMessageToBuilder(object));
    }

    @Override
    public void update(String entityName, Object object) throws HibernateException {
        getNativeSession().update(entityName, protobufMessageToBuilder(object));
    }

    @Override
    public Object merge(Object object) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public Object merge(String entityName, Object object) throws HibernateException {
       //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public void persist(Object object) throws HibernateException {
        getNativeSession().persist(protobufMessageToBuilder(object));
    }

    @Override
    public void persist(String entityName, Object object) throws HibernateException {
        getNativeSession().persist(entityName, protobufMessageToBuilder(object));
    }

    @Override
    public void delete(Object object) throws HibernateException {
        getNativeSession().delete(protobufMessageToBuilder(object));
    }

    @Override
    public void delete(String entityName, Object object) throws HibernateException {
        getNativeSession().delete(entityName, protobufMessageToBuilder(object));
    }

    @Override
    public void lock(Object object, LockMode lockMode) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public void lock(String entityName, Object object, LockMode lockMode) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public void refresh(Object object) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public void refresh(Object object, LockMode lockMode) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public LockMode getCurrentLockMode(Object object) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public Transaction beginTransaction() throws HibernateException {
        return getNativeSession().beginTransaction();
    }

    @Override
    public Transaction getTransaction() {
        return getNativeSession().getTransaction();
    }

    @Override
    public Criteria createCriteria(Class persistentClass) {
        return new ProtobufTransformerCriteria(
                  getNativeSession().createCriteria(
                          protobufBuilderClassFromMessageClass(persistentClass)));
    }

    @Override
    public Criteria createCriteria(Class persistentClass, String alias) {
        return new ProtobufTransformerCriteria(
                getNativeSession().createCriteria(
                        protobufBuilderClassFromMessageClass(persistentClass), alias));
    }

    @Override
    public Criteria createCriteria(String entityName) {
        return new ProtobufTransformerCriteria(
                getNativeSession().createCriteria(entityName));
    }

    @Override
    public Criteria createCriteria(String entityName, String alias) {
        return new ProtobufTransformerCriteria(
                getNativeSession().createCriteria(entityName, alias));
    }

    @Override
    public Query createQuery(String queryString) throws HibernateException {
        return new ProtobufTransformerQuery(
                getNativeSession().createQuery(queryString));
    }

    @Override
    public SQLQuery createSQLQuery(String queryString) throws HibernateException {
        return getNativeSession().createSQLQuery(queryString);
    }

    @Override
    public Query createFilter(Object collection, String queryString) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public Query getNamedQuery(String queryName) throws HibernateException {
        return new ProtobufTransformerQuery(
                getNativeSession().getNamedQuery(queryName));
    }

    @Override
    public void clear() {
        getNativeSession().clear();
    }

    @Override
    public Object get(Class clazz, Serializable id) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().get(protobufBuilderClassFromMessageClass(clazz), id));
    }

    @Override
    public Object get(Class clazz, Serializable id, LockMode lockMode) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().get(protobufBuilderClassFromMessageClass(clazz),id,lockMode));
    }

    @Override
    public Object get(String entityName, Serializable id) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().get(entityName,id));
    }

    @Override
    public Object get(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().get(entityName,id,lockMode));
    }

    @Override
    public String getEntityName(Object object) throws HibernateException {
        return getNativeSession().getEntityName(protobufMessageToBuilder(object));
    }

    @Override
    public Filter enableFilter(String filterName) {
        return getNativeSession().enableFilter(filterName);
    }

    @Override
    public Filter getEnabledFilter(String filterName) {
        return getNativeSession().getEnabledFilter(filterName);
    }

    @Override
    public void disableFilter(String filterName) {
        getNativeSession().disableFilter(filterName);
    }

    @Override
    public SessionStatistics getStatistics() {
        return getNativeSession().getStatistics();
    }

    @Override
    public void setReadOnly(Object entity, boolean readOnly) {
        getNativeSession().setReadOnly(protobufMessageToBuilder(entity),readOnly);
    }

    @Override
    public void doWork(Work work) throws HibernateException {
        getNativeSession().doWork(work);
    }

    @Override
    public Connection disconnect() throws HibernateException {
        return getNativeSession().disconnect();
    }

    @Override
    public void reconnect() throws HibernateException {
        getNativeSession().reconnect();
    }

    @Override
    public void reconnect(Connection connection) throws HibernateException {
        getNativeSession().reconnect(connection);
    }

    @Override
    public boolean isDefaultReadOnly() {
        return getNativeSession().isDefaultReadOnly();
    }

    @Override
    public void setDefaultReadOnly(boolean b) {
        getNativeSession().isDefaultReadOnly();
    }

    @Override
    public Object load(Class aClass, Serializable serializable, LockOptions lockOptions) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().load(protobufBuilderClassFromMessageClass(aClass),serializable, lockOptions));
    }

    @Override
    public Object load(String s, Serializable serializable, LockOptions lockOptions) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().load(s, serializable, lockOptions));
    }

    @Override
    public LockRequest buildLockRequest(LockOptions lockOptions) {
        return getNativeSession().buildLockRequest(lockOptions);
    }

    @Override
    public void refresh(Object o, LockOptions lockOptions) throws HibernateException {
        //TODO
        throw new HibernateException("NOT IMPLEMENTED!");
    }

    @Override
    public Object get(Class aClass, Serializable serializable, LockOptions lockOptions) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().get(protobufBuilderClassFromMessageClass(aClass), serializable, lockOptions));
    }

    @Override
    public Object get(String s, Serializable serializable, LockOptions lockOptions) throws HibernateException {
        return protobufBuilderToMessage(getNativeSession().get(s, serializable, lockOptions));
    }

    @Override
    public boolean isReadOnly(Object o) {
        //TODO
        return false;
    }

    @Override
    public boolean isFetchProfileEnabled(String s) throws UnknownProfileException {
        return getNativeSession().isFetchProfileEnabled(s);
    }

    @Override
    public void enableFetchProfile(String s) throws UnknownProfileException {
        getNativeSession().enableFetchProfile(s);
    }

    @Override
    public void disableFetchProfile(String s) throws UnknownProfileException {
        getNativeSession().disableFetchProfile(s);
    }
}
