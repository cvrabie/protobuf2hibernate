package org.codeandmagic.protobuf2hibernate;

import org.hibernate.*;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public abstract class WrappedQuery implements Query {

    protected final Query wrapped;

    public WrappedQuery(Query wrapped){
        this.wrapped = wrapped;
    }

    protected abstract Query newLikeMe(Query toBeWrapped);

    protected Query thisOrNewLikeMe(Query toBeReturned){
        if(wrapped == toBeReturned) return this;
        if(this == toBeReturned) return this;
        return newLikeMe(toBeReturned);
    }

    @Override
    public String getQueryString() {
        return wrapped.getQueryString();
    }

    @Override
    public Type[] getReturnTypes() throws HibernateException {
        return wrapped.getReturnTypes();
    }

    @Override
    public String[] getReturnAliases() throws HibernateException {
        return wrapped.getReturnAliases();
    }

    @Override
    public String[] getNamedParameters() throws HibernateException {
        return wrapped.getNamedParameters();
    }

    @Override
    public Iterator iterate() throws HibernateException {
        return wrapped.iterate();
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
    public List list() throws HibernateException {
        return wrapped.list();
    }

    @Override
    public Object uniqueResult() throws HibernateException {
        return wrapped.uniqueResult();
    }

    @Override
    public int executeUpdate() throws HibernateException {
        return wrapped.executeUpdate();
    }

    @Override
    public Query setMaxResults(int maxResults) {
        return thisOrNewLikeMe(wrapped.setMaxResults(maxResults));
    }

    @Override
    public Query setFirstResult(int firstResult) {
        return thisOrNewLikeMe(wrapped.setFirstResult(firstResult));
    }

    @Override
    public Query setReadOnly(boolean readOnly) {
        return thisOrNewLikeMe(wrapped.setReadOnly(readOnly));
    }

    @Override
    public Query setCacheable(boolean cacheable) {
        return thisOrNewLikeMe(wrapped.setCacheable(cacheable));
    }

    @Override
    public Query setCacheRegion(String cacheRegion) {
        return thisOrNewLikeMe(wrapped.setCacheRegion(cacheRegion));
    }

    @Override
    public Query setTimeout(int timeout) {
        return thisOrNewLikeMe(wrapped.setTimeout(timeout));
    }

    @Override
    public Query setFetchSize(int fetchSize) {
        return thisOrNewLikeMe(wrapped.setFetchSize(fetchSize));
    }

    @Override
    public Query setLockMode(String alias, LockMode lockMode) {
        return thisOrNewLikeMe(wrapped.setLockMode(alias,lockMode));
    }

    @Override
    public Query setComment(String comment) {
        return thisOrNewLikeMe(wrapped.setComment(comment));
    }

    @Override
    public Query setFlushMode(FlushMode flushMode) {
        return thisOrNewLikeMe(wrapped.setFlushMode(flushMode));
    }

    @Override
    public Query setCacheMode(CacheMode cacheMode) {
        return thisOrNewLikeMe(wrapped.setCacheMode(cacheMode));
    }

    @Override
    public Query setParameter(int position, Object val, Type type) {
        return thisOrNewLikeMe(wrapped.setParameter(position, val, type));
    }

    @Override
    public Query setParameter(String name, Object val, Type type) {
        return thisOrNewLikeMe(wrapped.setParameter(name, val, type));
    }

    @Override
    public Query setParameter(int position, Object val) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameter(position, val));
    }

    @Override
    public Query setParameter(String name, Object val) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameter(name, val));
    }

    @Override
    public Query setParameters(Object[] values, Type[] types) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameters(values, types));
    }

    @Override
    public Query setParameterList(String name, Collection vals, Type type) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameterList(name, vals, type));
    }

    @Override
    public Query setParameterList(String name, Collection vals) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameterList(name, vals));
    }

    @Override
    public Query setParameterList(String name, Object[] vals, Type type) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameterList(name, vals, type));
    }

    @Override
    public Query setParameterList(String name, Object[] vals) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setParameterList(name, vals));
    }

    @Override
    public Query setProperties(Object bean) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setProperties(bean));
    }

    @Override
    public Query setProperties(Map bean) throws HibernateException {
        return thisOrNewLikeMe(wrapped.setProperties(bean));
    }

    @Override
    public Query setString(int position, String val) {
        return thisOrNewLikeMe(wrapped.setString(position, val));
    }

    @Override
    public Query setCharacter(int position, char val) {
        return thisOrNewLikeMe(wrapped.setCharacter(position, val));
    }

    @Override
    public Query setBoolean(int position, boolean val) {
        return thisOrNewLikeMe(wrapped.setBoolean(position, val));
    }

    @Override
    public Query setByte(int position, byte val) {
        return thisOrNewLikeMe(wrapped.setByte(position, val));
    }

    @Override
    public Query setShort(int position, short val) {
        return thisOrNewLikeMe(wrapped.setShort(position, val));
    }

    @Override
    public Query setInteger(int position, int val) {
        return thisOrNewLikeMe(wrapped.setInteger(position, val));
    }

    @Override
    public Query setLong(int position, long val) {
        return thisOrNewLikeMe(wrapped.setLong(position, val));
    }

    @Override
    public Query setFloat(int position, float val) {
        return thisOrNewLikeMe(wrapped.setFloat(position, val));
    }

    @Override
    public Query setDouble(int position, double val) {
        return thisOrNewLikeMe(wrapped.setDouble(position, val));
    }

    @Override
    public Query setBinary(int position, byte[] val) {
        return thisOrNewLikeMe(wrapped.setBinary(position, val));
    }

    @Override
    public Query setText(int position, String val) {
        return thisOrNewLikeMe(wrapped.setText(position, val));
    }

    @Override
    public Query setSerializable(int position, Serializable val) {
        return thisOrNewLikeMe(wrapped.setSerializable(position, val));
    }

    @Override
    public Query setLocale(int position, Locale locale) {
        return thisOrNewLikeMe(wrapped.setLocale(position, locale));
    }

    @Override
    public Query setBigDecimal(int position, BigDecimal number) {
        return thisOrNewLikeMe(wrapped.setBigDecimal(position, number));
    }

    @Override
    public Query setBigInteger(int position, BigInteger number) {
        return thisOrNewLikeMe(wrapped.setBigInteger(position, number));
    }

    @Override
    public Query setDate(int position, Date date) {
        return thisOrNewLikeMe(wrapped.setDate(position, date));
    }

    @Override
    public Query setTime(int position, Date date) {
        return thisOrNewLikeMe(wrapped.setTime(position, date));
    }

    @Override
    public Query setTimestamp(int position, Date date) {
        return thisOrNewLikeMe(wrapped.setTimestamp(position, date));
    }

    @Override
    public Query setCalendar(int position, Calendar calendar) {
        return thisOrNewLikeMe(wrapped.setCalendar(position, calendar));
    }

    @Override
    public Query setCalendarDate(int position, Calendar calendar) {
        return thisOrNewLikeMe(wrapped.setCalendarDate(position, calendar));
    }

    @Override
    public Query setString(String name, String val) {
        return thisOrNewLikeMe(wrapped.setString(name, val));
    }

    @Override
    public Query setCharacter(String name, char val) {
        return thisOrNewLikeMe(wrapped.setCharacter(name, val));
    }

    @Override
    public Query setBoolean(String name, boolean val) {
        return thisOrNewLikeMe(wrapped.setBoolean(name, val));
    }

    @Override
    public Query setByte(String name, byte val) {
        return thisOrNewLikeMe(wrapped.setByte(name, val));
    }

    @Override
    public Query setShort(String name, short val) {
        return thisOrNewLikeMe(wrapped.setShort(name, val));
    }

    @Override
    public Query setInteger(String name, int val) {
        return thisOrNewLikeMe(wrapped.setInteger(name, val));
    }

    @Override
    public Query setLong(String name, long val) {
        return thisOrNewLikeMe(wrapped.setLong(name, val));
    }

    @Override
    public Query setFloat(String name, float val) {
        return thisOrNewLikeMe(wrapped.setFloat(name, val));
    }

    @Override
    public Query setDouble(String name, double val) {
        return thisOrNewLikeMe(wrapped.setDouble(name, val));
    }

    @Override
    public Query setBinary(String name, byte[] val) {
        return thisOrNewLikeMe(wrapped.setBinary(name, val));
    }

    @Override
    public Query setText(String name, String val) {
        return thisOrNewLikeMe(wrapped.setText(name, val));
    }

    @Override
    public Query setSerializable(String name, Serializable val) {
        return thisOrNewLikeMe(wrapped.setSerializable(name, val));
    }

    @Override
    public Query setLocale(String name, Locale locale) {
        return thisOrNewLikeMe(wrapped.setLocale(name, locale));
    }

    @Override
    public Query setBigDecimal(String name, BigDecimal number) {
        return thisOrNewLikeMe(wrapped.setBigDecimal(name, number));
    }

    @Override
    public Query setBigInteger(String name, BigInteger number) {
        return thisOrNewLikeMe(wrapped.setBigInteger(name, number));
    }

    @Override
    public Query setDate(String name, Date date) {
        return thisOrNewLikeMe(wrapped.setDate(name, date));
    }

    @Override
    public Query setTime(String name, Date date) {
        return thisOrNewLikeMe(wrapped.setTime(name, date));
    }

    @Override
    public Query setTimestamp(String name, Date date) {
        return thisOrNewLikeMe(wrapped.setTimestamp(name, date));
    }

    @Override
    public Query setCalendar(String name, Calendar calendar) {
        return thisOrNewLikeMe(wrapped.setCalendar(name, calendar));
    }

    @Override
    public Query setCalendarDate(String name, Calendar calendar) {
        return thisOrNewLikeMe(wrapped.setCalendarDate(name, calendar));
    }

    @Override
    public Query setEntity(int position, Object val) {
        return thisOrNewLikeMe(wrapped.setEntity(position, val));
    }

    @Override
    public Query setEntity(String name, Object val) {
        return thisOrNewLikeMe(wrapped.setEntity(name, val));
    }

    @Override
    public Query setResultTransformer(ResultTransformer transformer) {
        return thisOrNewLikeMe(wrapped.setResultTransformer(transformer));
    }

    @Override
    public boolean isReadOnly() {
        return wrapped.isReadOnly();
    }

    @Override
    public Query setLockOptions(LockOptions lockOptions) {
        return thisOrNewLikeMe(setLockOptions(lockOptions));
    }
}
