package com.smaller.jiview.core.util;

import com.smaller.jiview.core.exception.SysException;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanUtil {
    private BeanUtil() {
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static void springCopy(final Object orig, final Object dest) {
        org.springframework.beans.BeanUtils.copyProperties(Optional.ofNullable(orig).orElse(new Object()), Optional.ofNullable(dest).orElse(new Object()));
    }

    public static void obj2Map(Map map, Object obj) {
        try {
            map.putAll(BeanUtils.describe(obj));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new SysException(e.getMessage());
        }
    }

    public static String camel2Snack(String fieldName, String separator) {
        Matcher matcher = humpPattern.matcher(fieldName);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, separator + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        String strSnack = sb.toString();

        if (strSnack.indexOf(separator) == 0) {
            strSnack = strSnack.substring(1);
        }

        return strSnack;

    }
}
