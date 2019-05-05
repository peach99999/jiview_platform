package com.smaller.jiview.core.util;

import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.exception.SysException;
import com.smaller.jiview.core.properties.ServerProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UrlUtil {
    private static String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    public static String encode(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        try {
            return URLEncoder.encode(str, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new SysException(e.getMessage());
        }
    }

    public static String decode(String encodeStr) {
        try {
            return URLDecoder.decode(encodeStr, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new SysException(e.getMessage());
        }
    }

    /**
     * 判断是否是指定模块下的API
     *
     * @param request
     * @param moduleUri
     * @return String
     */
    public static Boolean startWith(HttpServletRequest request, String moduleUri, ServerProperties serverProperties) {
        String uri = request.getRequestURI();
        moduleUri = getActualModuleUri(request, moduleUri, serverProperties);

        if (uri.equals(moduleUri) || uri.startsWith(moduleUri + Constants.URL_PATH_SEPARATOR)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * 判断是否是指定API
     *
     * @param request
     * @param moduleUri
     * @return String
     */
    public static Boolean is(HttpServletRequest request, String moduleUri, ServerProperties serverProperties) {
        String uri = request.getRequestURI();
        moduleUri = getActualModuleUri(request, moduleUri, serverProperties);

        if (uri.equals(moduleUri)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private static String getActualModuleUri(HttpServletRequest request, String moduleUri, ServerProperties serverProperties) {
        String servletContextPath = serverProperties.getServletContextPath();

        return distinctSlash(servletContextPath + moduleUri);
    }

    /**
     * Performs safe encoding of the specified string, taking care to insert %20 for each space character,
     * instead of inserting the + character.
     */
    static String safeURLEncode(final String stringToEncode) {
        if (stringToEncode == null) {
            return null;
        }
        if (stringToEncode.length() == 0) {
            return "";
        }

        try {
            final String tString = URLEncoder.encode(stringToEncode, StandardCharsets.UTF_8.toString());

            if (stringToEncode.contains(" ")) {
                final StringBuilder outBuilder = new StringBuilder();

                int startDex = 0;
                for (int m = 0; m < stringToEncode.length(); m++) {
                    if (stringToEncode.charAt(m) == ' ') {
                        if (m > startDex) {
                            outBuilder.append(URLEncoder.encode(stringToEncode.substring(startDex, m),
                                    StandardCharsets.UTF_8.toString()));
                        }

                        outBuilder.append("%20");
                        startDex = m + 1;
                    }
                }

                if (startDex != stringToEncode.length()) {
                    outBuilder.append(URLEncoder.encode(stringToEncode.substring(startDex, stringToEncode.length()),
                            StandardCharsets.UTF_8.toString()));
                }

                return outBuilder.toString();
            } else {
                return tString;
            }

        } catch (final UnsupportedEncodingException e) {
            throw new Error(e); // If we can't encode UTF-8, we fail.
        }
    }

    /**
     * Performs safe decoding of the specified string, taking care to preserve each + character, rather
     * than replacing it with a space character.
     *
     * @param stringToDecode A {@code String} that represents the string to decode.
     * @return A {@code String} that represents the decoded string.
     */
    static String safeURLDecode(final String stringToDecode) {
        if (stringToDecode.length() == 0) {
            return "";
        }

        // '+' are decoded as ' ' so preserve before decoding
        if (stringToDecode.contains("+")) {
            final StringBuilder outBuilder = new StringBuilder();

            int startDex = 0;
            for (int m = 0; m < stringToDecode.length(); m++) {
                if (stringToDecode.charAt(m) == '+') {
                    if (m > startDex) {
                        try {
                            outBuilder.append(URLDecoder.decode(stringToDecode.substring(startDex, m),
                                    StandardCharsets.UTF_8.toString()));
                        } catch (UnsupportedEncodingException e) {
                            throw new Error(e);
                        }
                    }

                    outBuilder.append("+");
                    startDex = m + 1;
                }
            }

            if (startDex != stringToDecode.length()) {
                try {
                    outBuilder.append(URLDecoder.decode(stringToDecode.substring(startDex, stringToDecode.length()),
                            StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    throw new Error(e);
                }
            }

            return outBuilder.toString();
        } else {
            try {
                return URLDecoder.decode(stringToDecode, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new Error(e);
            }
        }
    }
    /**
     * 去除连续的/
     *
     * @param uri
     * @return
     */
    public static String distinctSlash(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "";
        }

        while (uri.indexOf("//") != -1) {
            uri = uri.replace("//", Constants.URL_PATH_SEPARATOR);
        }

        return uri;
    }
}
