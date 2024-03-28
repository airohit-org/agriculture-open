package com.airohit.agriculture.module.system.api.tenant;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Override
    public CommonResult<List<Long>> getTenantIds() {
        return success(tenantService.getTenantIds());
    }

    @Override
    public CommonResult<Boolean> validTenant(Long id) {
        tenantService.validTenant(id);
        return success(true);
    }

}
