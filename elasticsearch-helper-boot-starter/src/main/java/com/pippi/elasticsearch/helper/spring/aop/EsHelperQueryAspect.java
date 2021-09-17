package com.pippi.elasticsearch.helper.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * EsHelperQueryAspect
 *  aspect @EsQueryIndex to use Proxy implements query-operations
 *  adapter of assemble user-implement query-method (manager by SpringApplicationContext beans)
 * @author JohenTeng
 * @date 2021/9/17
 */
@Aspect
@Component
public class EsHelperQueryAspect {

    private static final Logger log = LoggerFactory.getLogger(EsHelperQueryAspect.class);

    //TODO: NEED DIFERENT KIND OF ANN
    @Pointcut("@annotation(org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex)")
    private void cutPoint(){}

    @Around("cutPoint()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object targetObject = joinPoint.getTarget();

        log.debug("");


        return null;
    }

}
