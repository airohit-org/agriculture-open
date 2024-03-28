package com.airohit.agriculture.module.system.api.permission;

import com.airohit.agriculture.framework.common.pojo.CommonResult;

import java.util.Collection;


public interface RoleApi {

    CommonResult<Boolean> validRoles(Collection<Long> ids);

}
