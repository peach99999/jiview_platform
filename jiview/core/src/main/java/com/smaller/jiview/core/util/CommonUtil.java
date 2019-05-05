package com.smaller.jiview.core.util;

import com.alibaba.fastjson.JSONObject;
import com.smaller.jiview.core.config.security.JwtUser;
import com.smaller.jiview.core.config.web.MKRequestWrapper;
import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.pojo.param.BaseListParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Slf4j
public class CommonUtil {
    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer calcStartRow(Map param) {
        Integer startRow = null;

        String strPageNo = String.valueOf(param.get("pageNo"));
        String strPageSize = String.valueOf(param.get("pageSize"));

        if (StringUtils.isNotEmpty(strPageNo)
                && StringUtils.isNotEmpty(strPageSize)
                && !"NULL".equalsIgnoreCase(strPageNo)
                && !"NULL".equalsIgnoreCase(strPageSize.toUpperCase())) {
            Integer pageNo = Integer.parseInt(strPageNo);
            Integer pageSize = Integer.parseInt(strPageSize);
            startRow = (pageNo - 1) * pageSize;
        }

        return startRow;
    }

    public static Integer calcStartRow(Integer pageNo, Integer pageSize) {
        BaseListParam baseListParam = new BaseListParam();
        baseListParam.setPageNo(pageNo);
        baseListParam.setPageSize(pageSize);

        return calcStartRow(baseListParam);
    }

    public static Integer calcStartRow(BaseListParam param) {
        Integer startRow = null;

        String strPageNo = String.valueOf(param.getPageNo());
        String strPageSize = String.valueOf(param.getPageSize());

        if (StringUtils.isNotEmpty(strPageNo)
                && StringUtils.isNotEmpty(strPageSize)
                && !"NULL".equalsIgnoreCase(strPageNo)
                && !"NULL".equalsIgnoreCase(strPageSize)) {
            Integer pageNo = Integer.parseInt(strPageNo);
            Integer pageSize = Integer.parseInt(strPageSize);
            startRow = (pageNo - 1) * pageSize;
        }

        return startRow;
    }

    public static String generateUUID36() {
        return UUID.randomUUID().toString();
    }

    public static String generateUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");

        return uuid;
    }

    public static String generateReceiptCodeForOther() {
        return "199X_" + CommonUtil.generateUUID32();
    }

    public static Integer birthdate2Age(Date birthdate) {
        if (birthdate == null) {
            return -1;
        } else {
            LocalDate today = LocalDate.now();
            Instant instant = birthdate.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDate localDate = localDateTime.toLocalDate();

            return Period.between(localDate, today).getYears();
        }
    }

    public static int reportCalcStartRow(Map param) {
        Integer pageNo = Integer.parseInt(String.valueOf(param.get("pageNo")));
        Integer pageSize = Integer.parseInt(String.valueOf(param.get("pageSize")));

        return (pageNo - 1) * pageSize;
    }

    public static String generateTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private static <T> List<T> washAdded(List<T> news, List<T> origs) {
        List<T> added = new ArrayList<>();
        for (int i = 0; i < news.size(); i++) {
            T newObj = news.get(i);
            Boolean duplicatedFlg = false;

            for (int j = 0; j < origs.size(); j++) {
                T origObj = origs.get(j);
                if (newObj.equals(origObj)) {
                    duplicatedFlg = true;
                    break;
                }
            }

            if (!duplicatedFlg) {
                added.add(newObj);
            }
        }

        return added;
    }

    public static <T> T getFirstElement(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public static String generateUniqueFileName(LoginUserDTO loginUserDTO, String extension) {
        return loginUserDTO.getLoginUserPkid() + "." + System.currentTimeMillis() + "." + System.nanoTime() + extension;
    }

    public static String generateUniqueFileName(String originFileName, String extension) {
        return originFileName + "." + System.currentTimeMillis() + "." + System.nanoTime() + extension;
    }

    public static String time2cron(LocalTime time) {
        Integer hour = time.getHour();
        Integer minute = time.getMinute();
        Integer second = time.getSecond();

        return second + " " + minute + " " + hour + " * * ?";
    }

    public static void writeBufferedImage(BufferedImage bufferedImage, String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);

        try {
            ImageIO.write(bufferedImage, suffix, file);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 当前系统的编码格式
     *
     * @return String
     */
    public static String getSystemEncode() {
        System.getProperties().list(System.out);// 得到当前的系统属性。并将属性列表输出到控制台
        return System.getProperty("file.encoding");
    }


    /**
     * 缓存RequestBody
     *
     * @param request
     * @throws IOException
     */
    public static HttpServletRequest cacheRequestBody(HttpServletRequest request) throws IOException {
        if (!HttpMethod.POST.toString().equalsIgnoreCase(request.getMethod())
                && !HttpMethod.PUT.toString().equalsIgnoreCase(request.getMethod())) {
            return request;
        }

        MKRequestWrapper mkRequestWrapper = new MKRequestWrapper(request);
        mkRequestWrapper.setAttribute(Constants.REQUEST_BODY_KEY, mkRequestWrapper.getBody());
        return mkRequestWrapper;
    }

    /**
     * 从request获取JwtUser
     *
     * @return Byte
     */
    public static JwtUser getJwtUser(HttpServletRequest request) {
        return (JwtUser) request.getAttribute(Constants.JWT_USER_KEY);
    }

}
