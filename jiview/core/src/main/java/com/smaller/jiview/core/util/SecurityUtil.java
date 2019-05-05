package com.smaller.jiview.core.util;

import com.smaller.jiview.core.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class SecurityUtil {
    private SecurityUtil() {

    }

    public static String encodePwd(String pwd) {
        String md5Result = DigestUtils.md5Hex(pwd);
        return DigestUtils.sha256Hex(md5Result);
    }

    public static String base64Encode(String str) {
        byte[] b = null;
        String encodedStr = null;

        b = str.getBytes(StandardCharsets.UTF_8);

        if (b != null) {
            encodedStr = Base64.getEncoder().encodeToString(b);
        }

        return encodedStr;
    }

    public static String base64Decode(String encodedStr) {
        byte[] b = null;
        String result = null;

        if (encodedStr != null) {
            b = Base64.getDecoder().decode(encodedStr);
            result = new String(b, StandardCharsets.UTF_8);
        }

        return result;
    }

    public static String md5(String data) {
        MessageDigest md = null;
        byte[] array = new byte[0];

        try {
            md = MessageDigest.getInstance("MD5");
            array = md.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new SysException(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        byte[] var4 = array;
        int var5 = array.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            byte item = var4[var6];
            sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }

    public static String getHMAC256(String key, String input) {
        Mac sha256HMAC = null;
        String hash = null;
        try {
            sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            Base64.Encoder encoder = Base64.getEncoder();

            hash = new String(encoder.encode(sha256HMAC.doFinal(input.getBytes(StandardCharsets.UTF_8))));

        } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        return hash;
    }
}
