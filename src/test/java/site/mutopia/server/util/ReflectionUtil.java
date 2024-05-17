package site.mutopia.server.util;

import java.lang.reflect.Field;

public class ReflectionUtil {
    public static <T> void setFieldValue(T targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}