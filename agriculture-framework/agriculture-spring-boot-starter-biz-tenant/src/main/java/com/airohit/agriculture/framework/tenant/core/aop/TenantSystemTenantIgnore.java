package com.airohit.agriculture.framework.tenant.core.aop;

import java.lang.annotation.*;

/**
 * 忽略系统租户
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TenantSystemTenantIgnore {
}
