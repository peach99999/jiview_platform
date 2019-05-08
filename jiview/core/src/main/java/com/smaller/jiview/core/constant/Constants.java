package com.smaller.jiview.core.constant;

/**
 * 通用常量类
 */
public class Constants {
    private Constants() {
    }
    public static final String REQUEST_BODY_KEY = "REQUEST_BODY";

    public static final String JWT_USER_KEY = "jwtUser";

    /**
     * 操作结果：0-成功, -1-失败
     */
    public static final Integer OP_RESULT_SUCCESS = 0;
    public static final Integer OP_RESULT_FAILED = -1;

    /**
     * url路径处理分隔符
     */
    public static final String URL_PATH_SEPARATOR = "/";

    /**
     * 分页参数：0-PAGE_NO, 10-PAGE_SIZE
     */
    public static final Integer PAGE_NO = 0;
    public static final Integer PAGE_SIZE = 10;
}
