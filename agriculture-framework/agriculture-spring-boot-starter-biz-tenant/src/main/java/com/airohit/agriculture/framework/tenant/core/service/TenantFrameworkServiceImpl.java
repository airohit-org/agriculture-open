package com.airohit.agriculture.framework.tenant.core.service;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.util.cache.CacheUtils;
import com.airohit.agriculture.module.system.api.tenant.TenantApi;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.List;

/**
 * Tenant 框架 Service 实现类
 *
 * @author shiminghao
 */
@RequiredArgsConstructor
public class TenantFrameworkServiceImpl implements TenantFrameworkService {

    private final TenantApi tenantApi;

    /**
     * 针对 {@link #getTenantIds()} 的缓存
     */
    private final LoadingCache<Object, List<Long>> getTenantIdsCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Object, List<Long>>() {

                @Override
                public List<Long> load(Object key) {
                    return tenantApi.getTenantIds().getCheckedData();
                }

            });

    /**
     * 针对 {@link #validTenant(Long)} 的缓存
     */
    private final LoadingCache<Long, CommonResult<Boolean>> validTenantCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Long, CommonResult<Boolean>>() {

                @Override
                public CommonResult<Boolean> load(Long id) {
                    return tenantApi.validTenant(id);
                }

            });

    @Override
    @SneakyThrows
    public List<Long> getTenantIds() {
        return getTenantIdsCache.get(Boolean.TRUE);
    }

    @Override
    @SneakyThrows
    public void validTenant(Long id) {
        validTenantCache.get(id).checkError();
    }

}
