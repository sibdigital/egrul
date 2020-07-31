package ru.sibdigital.egrul.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Jsonb implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[] {Types.OTHER};
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null) {
            return false;
        }
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        return resultSet.getString(names[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        preparedStatement.setObject(i, value, Types.OTHER);
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            return null;
        }
        if (!(o instanceof String)) {
            throw new IllegalStateException("Expected String, but got: " + o.getClass());
        }
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }
}
