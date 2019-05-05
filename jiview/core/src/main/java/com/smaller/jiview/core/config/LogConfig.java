package com.smaller.jiview.core.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 记录所有controller和service等Component的调用log
 * Created by xiagf on 2019/03/01
 */
@Slf4j
@Aspect
@Component
public class LogConfig {
    @Pointcut("within(com.smaller.jiview.*.controller..*)")
    public void allController() {
        throw new UnsupportedOperationException();
    }

    @Pointcut("within(com.smaller.jiview.*.service.impl..*)")
    public void allService() {
        throw new UnsupportedOperationException();
    }

    @Pointcut("allController() || allService()")
    public void allControllerAndService() {
        throw new UnsupportedOperationException();
    }

    @Before("allControllerAndService()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取包括包名在内的全方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info(String.format("%s.%s() >>", className, methodName));
    }

    @After("allControllerAndService()")
    public void doAfter(JoinPoint joinPoint) {
        // 获取包括包名在内的全方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info(String.format("%s.%s() <<", className, methodName));
    }
}

