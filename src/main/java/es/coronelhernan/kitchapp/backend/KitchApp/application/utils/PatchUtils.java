package es.coronelhernan.kitchapp.backend.KitchApp.application.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PatchUtils {

    private PatchUtils() {}

    public static void copyNonNullProperties(Object src, Object target, String... alwaysIgnore) {
        Set<String> ignoreSet = alwaysIgnore != null ? new HashSet<>(Arrays.asList(alwaysIgnore)) : new HashSet<>();

        Class<?> clazz = src.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (ignoreSet.contains(field.getName())) continue;
                if (Modifier.isStatic(field.getModifiers())) continue;

                field.setAccessible(true);
                try {
                    Object value = field.get(src);
                    if (value != null) {
                        field.set(target, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("No se pudo copiar el campo: " + field.getName(), e);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
