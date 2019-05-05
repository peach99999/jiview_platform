package com.smaller.jiview.core.util;

import java.math.BigDecimal;

public class MockUtil {
    private MockUtil() {

    }

    public static String randomLatLng(double minLon, double maxLon, double minLat, double maxLat) {
        BigDecimal db = BigDecimal.valueOf(Math.random() * (maxLon - minLon) + minLon);
        String lng = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = BigDecimal.valueOf(Math.random() * (maxLat - minLat) + minLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();

        return lat + "," + lng;
    }

    public static Double randomNum(Integer max) {
        return BigDecimal.valueOf(Math.random() * max).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Integer randomInt(Integer max) {
        return BigDecimal.valueOf(Math.round(Math.random() * max)).intValue();
    }

    public static Boolean randomBoolean() {
        return Math.random() * 2 > 1;
    }
}
