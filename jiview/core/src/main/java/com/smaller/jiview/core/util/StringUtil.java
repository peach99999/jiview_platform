package com.smaller.jiview.core.util;


import com.smaller.jiview.core.exception.SysException;
import com.github.promeg.pinyinhelper.Pinyin;

import java.io.UnsupportedEncodingException;

/**
 * Created by jianghe on 2017/8/31.
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * @param str         要截取的字符串
     * @param subSLength  要截取的字节数
     * @param charSetName 字符编码格式
     * @return
     */
    public static String subStr(String str, int subSLength, String charSetName) {
        int strBytes = 0;
        try {
            strBytes = str.getBytes(charSetName).length;
        } catch (UnsupportedEncodingException e) {
            throw new SysException();
        }
        int tempSubLength = subSLength;//截取字节数
        String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);//截取的子串
        int subStrByetsL = 0;//截取子串的字节长度
        try {
            subStrByetsL = subStr.getBytes(charSetName).length;
        } catch (UnsupportedEncodingException e) {
            throw new SysException();
        }

        // 说明截取的字符串中包含有汉字
        while (subStrByetsL > tempSubLength) {
            int subSLengthTemp = --subSLength;
            subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
            try {
                subStrByetsL = subStr.getBytes(charSetName).length;
            } catch (UnsupportedEncodingException e) {
                throw new SysException();
            }
        }
        if (strBytes > 124) {
            subStr += "...";
        } else {
            subStr = str.substring(0, str.length() - 1);
        }
        return subStr;
    }

    /**
     * 获取汉字的拼音首字母
     *
     * @param chinese
     * @return String
     */
    public static String getInitialCharacter(String chinese) {
        String initialChinese = chinese.substring(0, 1);
        String pinyin = Pinyin.toPinyin(initialChinese, "");
        return pinyin.substring(0, 1);
    }
}
