package com.smaller.jiview.core.util;

import com.smaller.jiview.core.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 创建文件，文件或文件夹不存在即创建
     *
     * @param filePath
     * @throws IOException
     */
    public static File create(String filePath) throws IOException {
        File file = new File(filePath);
        FileUtils.touch(file);
        return file;
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath
     * @throws IOException
     */
    public static Boolean checkExsits(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }

    /**
     * 获取扩展名
     *
     * @param fileName
     * @return String
     */
    public static String getSuffix(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }

        int lastIndexOfDot = fileName.lastIndexOf('.');

        if (lastIndexOfDot == -1) {
            return "";
        }

        return fileName.substring(lastIndexOfDot);
    }

    /**
     * 获取扩展名
     *
     * @param filePath
     * @return String
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }

        filePath = UrlUtil.safeURLDecode(filePath);

        int lastIndexOfDot = filePath.lastIndexOf(Constants.URL_PATH_SEPARATOR);
        if (lastIndexOfDot == -1) {
            return "";
        }

        return filePath.substring(lastIndexOfDot + 1);
    }
}
