package com.smaller.jiview.core.config.web;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

/**
 * 修改rest注入名称，解决controller不能重名
 */
public class MKNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {

        String className = ClassUtils.getShortName(definition.getBeanClassName());
        String packageName = ClassUtils.getPackageName(definition.getBeanClassName());
        String deviceName = packageName.substring(packageName.lastIndexOf('.') + 1);

        boolean isExist = ArrayUtils.contains(MKModuleConstants.modules, deviceName);
        // 如果是admin、host、或者app的则添加修改controller名称，
        if (isExist) {
            return deviceName + className;
        }
        return super.buildDefaultBeanName(definition);
    }
}
