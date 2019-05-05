package com.smaller.jiview.core.config.web;

import com.smaller.jiview.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 自动生成request mapping
 */
@Slf4j
@Configuration
public class MKRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {

        PatternsRequestCondition patternsRequestCondition = mapping.getPatternsCondition();

        Class<? extends PatternsRequestCondition> clazz = patternsRequestCondition.getClass();

        Field patternsField = null;
        try {
            patternsField = clazz.getDeclaredField("patterns");
        } catch (NoSuchFieldException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        Set<String> patternsSet = new HashSet<>();
        for (String mappingPath : patternsRequestCondition.getPatterns()) {

            String packageName = method.getDeclaringClass().getPackage().getName();
            String[] packageNames = packageName.split("\\.");
            String moduleName = packageNames[packageNames.length - 1];
            String className = method.getDeclaringClass().getName();
            String[] classNames = className.split("\\.");

            String pattern = "/" + moduleName
                    + "/" + BeanUtil.camel2Snack(classNames[classNames.length - 1].replace("Controller", ""), "-")
//                    + "/" + BeanUtil.camel2Snack(methodName, "-")
                    + "：" + mappingPath;

            if (ArrayUtils.contains(MKModuleConstants.modules, moduleName)) {
                patternsSet.add(pattern);
            }
        }

        if (!patternsSet.isEmpty()) {
            try {
                if (patternsField != null) {
                    patternsField.setAccessible(true);// 设置操作权限为true
                    patternsField.set(patternsRequestCondition, patternsSet);
                }
            } catch (IllegalAccessException e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }

        super.registerHandlerMethod(handler, method, mapping);
    }
}
