package es.coronelhernan.kitchapp.backend.KitchApp.application.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public final class PatchUtils {

    private PatchUtils() {}

    public static void copyNonNullProperties(Object src, Object target, String... alwaysIgnore) {
        BeanWrapper srcWrap = new BeanWrapperImpl(src);
        java.beans.PropertyDescriptor[] pds = srcWrap.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (srcWrap.isReadableProperty(name)) {
                Object srcValue = srcWrap.getPropertyValue(name);
                if (srcValue == null) {
                    emptyNames.add(name);
                }
            }
        }

        if (alwaysIgnore != null) {
            for (String n : alwaysIgnore) {
                emptyNames.add(n);
            }
        }

        String[] ignoreArray = new String[emptyNames.size()];
        BeanUtils.copyProperties(src, target, emptyNames.toArray(ignoreArray));
    }
}
