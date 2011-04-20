package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Message;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Component;
import org.hibernate.tuple.component.ComponentTuplizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProtobufComponentTuplizer extends ProtobufTuplizer implements ComponentTuplizer{
    protected final Component component;
    protected final String parentPropertyName;

    public ProtobufComponentTuplizer(Component component)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(component.getComponentClassName(), component.getComponentClass(), component.getPropertySpan(), component.getPropertyIterator());
        this.component = component;

        parentPropertyName = component.getParentProperty();
    }

    @Override
    protected Object transformMessageToBuilder(Message val) {
        return ProtobufTransformer.protobufMessageToBuilder(val);
    }

    @Override
    public Object getParent(Object component) {
        return getPropertyValue(component, parentPropertyName);
    }

    @Override
    public void setParent(Object component, Object parent, SessionFactoryImplementor factory) {
        setPropertyValue(component, parentPropertyName, parent);
    }

    @Override
    public boolean hasParentProperty() {
        return parentPropertyName != null;
    }

    @Override
    public boolean isMethodOf(Method method) {
        return false;  //TODO
    }
}
