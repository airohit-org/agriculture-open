package com.airohit.agriculture.framework.tenant.core.aop;

import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Objects;

/**
 * 忽略多租户的 Aspect，基于 {@link TenantIgnore} 注解实现，用于一些全局的逻辑。
 * 例如说，一个定时任务，读取所有数据，进行处理。
 * 又例如说，读取所有数据，进行缓存。
 * <p>
 * 整体逻辑的实现，和 {@link TenantUtils#executeIgnore(Runnable)} 需要保持一致
 *
 * @author shiminghao
 */
@Aspect
@Slf4j
public class TenantIgnoreAspect {

    @Around("@annotation(tenantIgnore)")
    public Object around(ProceedingJoinPoint joinPoint, TenantIgnore tenantIgnore) throws Throwable {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setIgnore(true);
            // 执行逻辑
            return joinPoint.proceed();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    @Around("@annotation(tenantIgnore)")
    public Object farmAround(ProceedingJoinPoint joinPoint, FarmTenantIgnore tenantIgnore) throws Throwable {
        Boolean oldIgnore = FarmTenantContextHolder.isIgnore();
        try {
            FarmTenantContextHolder.setIgnore(true);
            // 执行逻辑
            return joinPoint.proceed();
        } finally {
            FarmTenantContextHolder.setIgnore(oldIgnore);
        }
    }

    @Around("@annotation(tenantSystemTenantIgnore)")
    public Object aroundSystemTenant(ProceedingJoinPoint joinPoint, TenantSystemTenantIgnore tenantSystemTenantIgnore) throws Throwable {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        Long tenantId = TenantContextHolder.getTenantId();
        boolean ignoreTableFlag = Objects.nonNull(tenantId) && 1L == tenantId;
        //当前租户为系统租户
        //设置为忽略系统租户
        try {
            if (ignoreTableFlag) {
                TenantContextHolder.setIgnore(true);
            }
            // 执行逻辑
            return joinPoint.proceed();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

}
