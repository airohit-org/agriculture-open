package com.airohit.agriculture.framework.tenant.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 多农场上下文 Holder
 *
 * @author shiminghao
 */
public class FarmTenantContextHolder {

    /**
     * 当前农场编号
     */
    private static final ThreadLocal<Long> FARM_TENANT_ID = new TransmittableThreadLocal<>();

    /**
     * 是否忽略农场
     */
    private static final ThreadLocal<Boolean> IGNORE = new TransmittableThreadLocal<>();

    /**
     * 获得农场编号。
     *
     * @return 租户编号
     */
    public static Long getFarmTenantId() {
        return FARM_TENANT_ID.get();
    }

    public static void setFarmTenantId(Long farmId) {
        FARM_TENANT_ID.set(farmId);
    }

    /**
     * 获得农场编号。如果不存在，则抛出 NullPointerException 异常
     *
     * @return 农场编号
     */
    public static Long getRequiredFarmTenantId() {
        Long farmId = getFarmTenantId();
        if (farmId == null) {
            throw new NullPointerException("TenantContextHolder 不存在租户编号！");
        }
        return farmId;
    }

    /**
     * 当前是否忽略农场
     *
     * @return 是否忽略
     */
    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE.get());
    }

    public static void setIgnore(Boolean ignore) {
        IGNORE.set(ignore);
    }

    public static void clear() {
        FARM_TENANT_ID.remove();
        IGNORE.remove();
    }

}
