package com.springstudy.ioc.demo.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionConfig implements Condition {
    /**
     *
     * @param conditionContext
     * BeanDefinitionRegistry
     * ConfigurableListableBeanFactory
     * Environment
     * ResourceLoader
     * ClassLoader
     * @param annotatedTypeMetadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment env = conditionContext.getEnvironment();
        return env.containsProperty("magic");
    }
}
