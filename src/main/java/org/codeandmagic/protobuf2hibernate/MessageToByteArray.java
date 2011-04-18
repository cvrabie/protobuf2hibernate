package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Message;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class MessageToByteArray implements UserType, ParameterizedType {
	public final Logger logger = LoggerFactory.getLogger(getClass());

	private static final int[] SQL_TYPES = new int[] { Types.BLOB };
    private Class<? extends Message> returnedClass;

    @Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class<? extends Message> returnedClass(){
        return returnedClass;
    }

    @Override
	public boolean equals(final Object x, final Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}

		if (x.getClass() == returnedClass() && y.getClass() == returnedClass()) {
			return x.equals(y);
		}

		return false;
	}

	@Override
	public int hashCode(final Object object) throws HibernateException {
		if (object == null) {
			return 0;
		}
		assert object.getClass() == returnedClass();
		return object.hashCode();
	}

	@Override
	public Object nullSafeGet(final ResultSet resultSet, final String[] names, final Object owner)
            throws HibernateException, SQLException {
        try{
            byte[] bytes = resultSet.getBytes(names[0]);
            if(null == bytes || 0 == bytes.length)
                return null;
            else
		        return MessageUtil.deserialize(returnedClass,bytes);
        } catch (Exception e) {
            throw new HibernateException("Could not deserialize Message of type "+
                    returnedClass.getName(), e);
        }
    }

	@Override
	public void nullSafeSet(final PreparedStatement preparedStatement, final Object value, final int index)
            throws HibernateException, SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Types.OTHER);
		} else {
			assert value.getClass() == returnedClass();
			preparedStatement.setBytes(index, ((Message) value).toByteArray());
		}
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(final Serializable cached, final Object value) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

    @Override
    public void setParameterValues(Properties properties) {
        if(!properties.containsKey("messageType")){
            throw new HibernateException("Property 'messageType' is required!");
        }
        Object p1 = properties.getProperty("messageType");
        if(null == p1){
            throw new HibernateException("Property 'messageType' is required!");
        }
        if(!(p1 instanceof String)){
            throw new HibernateException("Property 'messageString' needs to be a String representing the class name");
        }
        try{
            this.returnedClass = (Class<? extends Message>) Class.forName((String)p1);
        }catch (Exception e){
            throw new HibernateException("Could not find Class<? extends Message> with name "+p1, e);
        }
    }
}
