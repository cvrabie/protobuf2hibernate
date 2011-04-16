package org.codeandmagic.protobuf2hibernate;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

public abstract class WrappedCriteria implements Criteria {

    protected final Criteria wrapped;

    public WrappedCriteria(final Criteria wrapped){
        this.wrapped = wrapped;
    }

    protected abstract Criteria newLikeMe(Criteria toBeWrapped);

    protected Criteria thisOrNewLikeMe(Criteria toBeReturned){
        if(wrapped == toBeReturned) return this;
        if(this == toBeReturned) return this;
        return newLikeMe(toBeReturned);
    }

    @Override
    public String getAlias() {
        return wrapped.getAlias();
    }

    @Override
    public Criteria setProjection(Projection projection) {
        return thisOrNewLikeMe(wrapped.setProjection(projection));
    }

    @Override
    public Criteria add(Criterion criterion) {
        return thisOrNewLikeMe(wrapped.add(criterion));
    }

    @Override
    public Criteria addOrder(Order order) {
        return thisOrNewLikeMe(wrapped.addOrder(order));
    }

    @Override
    public Criteria setFetchMode(String associationPath, FetchMode mode) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setFetchMode(associationPath, mode));
    }

    @Override
    public Criteria setLockMode(LockMode lockMode) {
        return thisOrNewLikeMe(wrapped.setLockMode(lockMode));
    }

    @Override
    public Criteria setLockMode(String alias, LockMode lockMode) {
        return thisOrNewLikeMe(wrapped.setLockMode(alias, lockMode));
    }

    @Override
    public Criteria createAlias(String associationPath, String alias) throws HibernateException {
        return thisOrNewLikeMe(wrapped.createAlias(associationPath,alias));
    }

    @Override
    public Criteria createAlias(String associationPath, String alias, int joinType) throws HibernateException {
        return thisOrNewLikeMe(wrapped.createAlias(associationPath, alias, joinType));
    }

    @Override
    public Criteria createCriteria(String associationPath) throws HibernateException {
        return thisOrNewLikeMe(wrapped.createCriteria(associationPath));
    }

    @Override
    public Criteria createCriteria(String associationPath, int joinType) throws HibernateException {
        return thisOrNewLikeMe(wrapped.createCriteria(associationPath, joinType));
    }

    @Override
    public Criteria createCriteria(String associationPath, String alias) throws HibernateException {
        return thisOrNewLikeMe(wrapped.createCriteria(associationPath, alias));
    }

    @Override
    public Criteria createCriteria(String associationPath, String alias, int joinType) throws HibernateException {
        return thisOrNewLikeMe(wrapped.createCriteria(associationPath, alias, joinType));
    }

    @Override
    public Criteria setResultTransformer(ResultTransformer resultTransformer) {
        return thisOrNewLikeMe(wrapped.setResultTransformer(resultTransformer));
    }

    @Override
    public Criteria setMaxResults(int maxResults) {
        return thisOrNewLikeMe(wrapped.setMaxResults(maxResults));
    }

    @Override
    public Criteria setFirstResult(int firstResult) {
        return thisOrNewLikeMe(wrapped.setMaxResults(firstResult));
    }

    @Override
    public Criteria setFetchSize(int fetchSize) {
        return thisOrNewLikeMe(wrapped.setFetchSize(fetchSize));
    }

    @Override
    public Criteria setTimeout(int timeout) {
        return thisOrNewLikeMe(wrapped.setTimeout(timeout));
    }

    @Override
    public Criteria setCacheable(boolean cacheable) {
        return thisOrNewLikeMe(wrapped.setCacheable(cacheable));
    }

    @Override
    public Criteria setCacheRegion(String cacheRegion) {
        return thisOrNewLikeMe(wrapped.setCacheRegion(cacheRegion));
    }

    @Override
    public Criteria setComment(String comment) {
        return thisOrNewLikeMe(wrapped.setComment(comment));
    }

    @Override
    public Criteria setFlushMode(FlushMode flushMode) {
        return thisOrNewLikeMe(wrapped.setFlushMode(flushMode));
    }

    @Override
    public Criteria setCacheMode(CacheMode cacheMode) {
        return thisOrNewLikeMe(wrapped.setCacheMode(cacheMode));
    }

    @Override
    public List list() throws HibernateException {
        return wrapped.list();
    }

    @Override
    public ScrollableResults scroll() throws HibernateException {
        return wrapped.scroll();
    }

    @Override
    public ScrollableResults scroll(ScrollMode scrollMode) throws HibernateException {
        return wrapped.scroll(scrollMode);
    }

    @Override
    public Object uniqueResult() throws HibernateException {
        return wrapped.uniqueResult();
    }
}
