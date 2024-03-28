package com.airohit.agriculture.framework.tenant.core.util;

import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;

import java.util.function.Supplier;

/**
 * 多租户 Util
 *
 * @author shiminghao
 */
public class TenantUtils {

    /**
     * 使用指定租户，执行对应的逻辑
     * <p>
     * 注意，如果当前是忽略租户的情况下，会被强制设置成不忽略租户
     * 当然，执行完成后，还是会恢复回去
     *
     * @param tenantId 租户编号
     * @param runnable 逻辑
     */
    public static void execute(Long tenantId, Runnable runnable) {
        Long oldTenantId = TenantContextHolder.getTenantId();
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setTenantId(tenantId);
            TenantContextHolder.setIgnore(false);
            // 执行逻辑
            runnable.run();
        } finally {
            TenantContextHolder.setTenantId(oldTenantId);
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    /**
     * 使用指定租户，执行对应的逻辑
     * <p>
     * 注意，如果当前是忽略租户的情况下，会被强制设置成不忽略租户
     * 当然，执行完成后，还是会恢复回去
     *
     * @param tenantId 租户编号
     * @param runnable 逻辑
     */
    public static void execute(Long tenantId, Long farmTenantId, Runnable runnable) {
        Long oldTenantId = TenantContextHolder.getTenantId();
        Boolean oldIgnore = TenantContextHolder.isIgnore();

        Long oldFarmTenantId = FarmTenantContextHolder.getFarmTenantId();
        Boolean oldFarmIgnore = FarmTenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setTenantId(tenantId);
            TenantContextHolder.setIgnore(false);

            FarmTenantContextHolder.setFarmTenantId(farmTenantId);
            FarmTenantContextHolder.setIgnore(false);
            // 执行逻辑
            runnable.run();
        } finally {
            TenantContextHolder.setTenantId(oldTenantId);
            TenantContextHolder.setIgnore(oldIgnore);
            FarmTenantContextHolder.setFarmTenantId(oldFarmTenantId);
            FarmTenantContextHolder.setIgnore(oldFarmIgnore);
        }
    }

    /**
     * 使用指定租户，执行对应的逻辑
     * <p>
     * 注意，如果当前是忽略租户的情况下，会被强制设置成不忽略租户
     * 当然，执行完成后，还是会恢复回去
     *
     * @param farmTenantId 租户编号
     * @param runnable     逻辑
     */
    public static void executeFarm(Long farmTenantId, Runnable runnable) {
        Long oldFarmTenantId = FarmTenantContextHolder.getFarmTenantId();
        Boolean oldFarmIgnore = FarmTenantContextHolder.isIgnore();
        try {
            FarmTenantContextHolder.setFarmTenantId(farmTenantId);
            FarmTenantContextHolder.setIgnore(false);
            // 执行逻辑
            runnable.run();
        } finally {
            FarmTenantContextHolder.setFarmTenantId(oldFarmTenantId);
            FarmTenantContextHolder.setIgnore(oldFarmIgnore);
        }
    }

    /**
     * 忽略租户，执行对应的逻辑
     *
     * @param runnable 逻辑
     */
    public static void executeIgnore(Runnable runnable) {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setIgnore(true);
            // 执行逻辑
            runnable.run();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    /**
     * 使用指定租户，执行对应的逻辑
     * <p>
     * 注意，如果当前是忽略租户的情况下，会被强制设置成不忽略租户
     * 当然，执行完成后，还是会恢复回去
     *
     * @param tenantId 租户编号
     * @param supplier 逻辑
     */
    public static <T> T execute(Long tenantId, Supplier<T> supplier) {
        Long oldTenantId = TenantContextHolder.getTenantId();
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setTenantId(tenantId);
            TenantContextHolder.setIgnore(false);
            // 执行逻辑
            return supplier.get();
        } finally {
            TenantContextHolder.setTenantId(oldTenantId);
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

}
