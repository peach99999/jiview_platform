package com.smaller.jiview.core.util;

import com.smaller.jiview.core.exception.SysException;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by xiagf on 2019/03/01
 */
@Slf4j
public class VerificationCodeUtil {
    private static Random random;  // SecureRandom is preferred to Random

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
    }


    private VerificationCodeUtil() {

    }

    public static String generateRandomNumberString(Integer length) {
        String sRand = "";

        try {
            for (int i = 0; i < length; i++) {
                String rand = String.valueOf(random.nextInt(10));
                sRand += rand;
            }
        } catch (Exception e) {
            throw new SysException(e.getCause().getMessage());
        }
        return sRand;
    }
}
