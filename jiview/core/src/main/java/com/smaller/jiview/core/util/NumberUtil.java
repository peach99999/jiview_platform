package com.smaller.jiview.core.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.regex.Pattern;


public class NumberUtil {

    public static final Integer COMPARE_RESULT_ZERO = 0;
    public static final Integer COMPARE_RESULT_MINUS_ONE = -1;
    public static final Integer COMPARE_RESULT_ONE = 1;
    public static final BigDecimal MINUS_ONE = new BigDecimal(-1);

    private static Pattern isIntegerPattern = Pattern.compile("^\\d+(\\.(0)+)?$");

    public static Byte parseByte(String str) {
        return parseByte(str, null);
    }

    public static Byte parseByte(String str, Byte defaultVal) {
        Byte result = defaultVal;

        if (StringUtils.isEmpty(str)) {
            return result;
        }

        try {
            result = Byte.parseByte(str);
        } catch (NumberFormatException e) {
        }

        return result;
    }

    public static Byte parseByte(Object obj) {
        return parseByte(String.valueOf(obj));
    }

    public static Byte parseByte(Object obj, Byte defaultVal) {
        return parseByte(String.valueOf(obj), defaultVal);
    }

    public static Integer parseInt(String str) {
        return parseInt(str, null);
    }

    public static Integer parseInt(String str, Integer defaultVal) {
        Integer result = defaultVal;

        if (StringUtils.isEmpty(str)) {
            return result;
        }

        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
        }

        return result;
    }

    public static Integer parseInt(Object obj) {
        return parseInt(String.valueOf(obj));
    }

    public static Integer parseInt(Object obj, Integer defaultVal) {
        return parseInt(String.valueOf(obj), defaultVal);
    }

    public static Long parseLong(String str) {
        return parseLong(str, null);
    }

    public static Long parseLong(String str, Long defaultVal) {
        Long result = defaultVal;

        if (StringUtils.isEmpty(str)) {
            return result;
        }

        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
        }

        return result;
    }

    public static Long parseLong(Object obj) {
        return parseLong(String.valueOf(obj));
    }

    public static Long parseLong(Object obj, Long defaultVal) {
        return parseLong(String.valueOf(obj), defaultVal);
    }

    public static Double parseDouble(String str) {
        return parseDouble(str, null);
    }

    public static Double parseDouble(String str, Double defaultVal) {
        Double result = defaultVal;

        if (StringUtils.isEmpty(str)) {
            return result;
        }

        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
        }

        return result;
    }

    public static Double parseDouble(Object obj) {
        return parseDouble(String.valueOf(obj));
    }

    public static Double parseDouble(Object obj, Double defaultVal) {
        return parseDouble(String.valueOf(obj), defaultVal);
    }

    public static Float parseFloat(String str) {
        return parseFloat(str, null);
    }

    public static Float parseFloat(String str, Float defaultVal) {
        Float result = defaultVal;

        if (StringUtils.isEmpty(str)) {
            return result;
        }

        try {
            result = Float.parseFloat(str);
        } catch (NumberFormatException e) {
        }

        return result;
    }

    public static Float parseFloat(Object obj) {
        return parseFloat(String.valueOf(obj));
    }

    public static Float parseFloat(Object obj, Float defaultVal) {
        return parseFloat(String.valueOf(obj), defaultVal);
    }

    public static boolean isInt(String str) {
        return isIntegerPattern.matcher(str).matches();
    }

    public static boolean isInt(Object obj) {
        if (obj == null) {
            return Boolean.FALSE;
        }

        return isInt(obj.toString());
    }

    public static String yuan2fen(String strYuan) {
        return yuan2fen(new BigDecimal(strYuan)).toString();
    }

    public static String fen2yuan(String strFen) {
        return fen2yuan(new BigDecimal(strFen)).toString();
    }

    public static BigDecimal yuan2fen(BigDecimal yuan) {
        BigDecimal oneHundred = BigDecimal.valueOf(100);
        return yuan.multiply(oneHundred).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal fen2yuan(BigDecimal fen) {
        BigDecimal oneHundred = BigDecimal.valueOf(100);
        return fen.divide(oneHundred).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static Boolean lessThan(BigDecimal val1, BigDecimal val2) {
        Integer result = val1.compareTo(val2);
        return COMPARE_RESULT_MINUS_ONE.equals(result);
    }

    public static Boolean greaterThan(BigDecimal val1, BigDecimal val2) {
        Integer result = val1.compareTo(val2);
        return COMPARE_RESULT_ONE.equals(result);
    }

    public static Boolean equals(BigDecimal val1, BigDecimal val2) {
        Integer result = val1.compareTo(val2);
        return COMPARE_RESULT_ZERO.equals(result);
    }

    public static Boolean notEqualTo(BigDecimal val1, BigDecimal val2) {
        Integer result = val1.compareTo(val2);
        if (COMPARE_RESULT_ZERO.equals(result)) {
            return false;
        } else {
            return true;
        }
    }

    public static Boolean lessThanOrEqualTo(BigDecimal val1, BigDecimal val2) {
        Integer result = val1.compareTo(val2);
        return COMPARE_RESULT_ZERO.equals(result) || COMPARE_RESULT_MINUS_ONE.equals(result);
    }

    public static Boolean greaterThanOrEqualTo(BigDecimal val1, BigDecimal val2) {
        Integer result = val1.compareTo(val2);
        return COMPARE_RESULT_ZERO.equals(result) || COMPARE_RESULT_ONE.equals(result);
    }

    public static Long obj2long(Object obj) {
        return (Long.parseLong(obj.toString()));
    }

    public static BigDecimal minus(BigDecimal val) {
        if (val == null) {
            return null;
        }
        return val.abs().multiply(MINUS_ONE);
    }

    public static BigDecimal nullToZero(BigDecimal val) {
        return Optional.ofNullable(val).orElse(BigDecimal.ZERO);
    }

    public static String leftPad(Long longValue, String zeroTemplate) {
        DecimalFormat df = new DecimalFormat(zeroTemplate);
        return df.format(longValue);
    }
}
