package com.airohit.agriculture.module.system.api.tenant;

import com.airohit.agriculture.framework.common.pojo.CommonResult;

import java.util.List;


// TODO shiminghao：fallbackFactory =
public interface TenantApi {


    CommonResult<List<Long>> getTenantIds();

    CommonResult<Boolean> validTenant(Long id);


}
