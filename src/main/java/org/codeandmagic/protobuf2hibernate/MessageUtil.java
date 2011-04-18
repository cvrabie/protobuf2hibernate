package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MessageUtil {
    @SuppressWarnings("unchecked")
	public static <T extends Message.Builder> T newBuilder(Class<T> type)
		throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return (T) getMethod(type,"create").invoke(null);
	}

    public static <TB extends Message.Builder> TB newBuilderForMessage(Class<? extends Message> type)
		throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return (TB) getMethod(type,"newBuilder").invoke(null);
	}

    private static Method getMethod(Class<?> type, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        Method m = type.getDeclaredMethod(methodName, parameterTypes);
		m.setAccessible(true);
        return m;
    }

    private static Method getMethod(Class<?> type, String methodName) throws NoSuchMethodException {
        return getMethod(type, methodName, new Class<?>[0]);
    }

    public static Class<? extends Message.Builder> builderClassFromMessageClass(Class<? extends Message> messageClass)
            throws ClassNotFoundException {
        return (Class<? extends Message.Builder>) Class.forName(messageClass.getName()+"$Builder");
    }

    public static Class<? extends Message> messageClassForBuilderClass(Class<? extends Message.Builder> builderClass)
            throws ClassNotFoundException {
        return (Class<? extends Message>) Class.forName(builderClass.getName().substring(0, builderClass.getName().indexOf("$")));
    }

    public static Descriptors.Descriptor getDescriptorForMessageClass(Class<? extends Message> type)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (Descriptors.Descriptor) getMethod(type,"getDescriptor").invoke(null);
    }

    public static Descriptors.Descriptor getDescriptorForBuilderClass(Class<? extends Message.Builder> type)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        return getDescriptorForMessageClass(messageClassForBuilderClass(type));
    }

    private final static Class<?>[] parametersForParseFrom = new Class<?>[] {byte[].class};
    public static <T extends Message> T deserialize(Class<T> messageType, byte[] bytes )
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (T) getMethod(messageType, "parseFrom", parametersForParseFrom).invoke(null, bytes);
    }
}
