package com.smaller.jiview.core.config.aspect;

import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 请求结果OpResult处理
 *
 * @author xiagf
 * @date 2019/04/30
 */
@Slf4j
@Component
@Aspect
@Order(2)
public class ResultAspect {
    @AfterReturning(value = "execution(* com.smaller.jiview..service.*.*(..)) ", returning = "returning")
    public void aspectMethod(JoinPoint joinPoint, Object returning) {
        Object[] args = joinPoint.getArgs();
        ResultBO result = (ResultBO) returning;

        log.info(Arrays.toString(args));

        result.setOpResult(Constants.OP_RESULT_SUCCESS);
    }
}
