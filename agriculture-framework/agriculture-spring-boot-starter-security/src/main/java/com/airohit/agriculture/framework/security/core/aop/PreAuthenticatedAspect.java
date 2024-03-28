package com.airohit.agriculture.framework.security.core.aop;

import com.airohit.agriculture.framework.security.core.annotations.PreAuthenticated;
import com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.airohit.agriculture.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            log.error("1111111111");
            throw exception(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
