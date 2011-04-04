package org.codeandmagic.protobuf2hibernate;

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

    private static Method getMethod(Class<?> type, String methodName) throws NoSuchMethodException {
        Method m = type.getDeclaredMethod(methodName, new Class<?>[0]);
		m.setAccessible(true);
        return m;
    }
}
