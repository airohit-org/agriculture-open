package com.airohit.agriculture.module.system.api.permission;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.permission.dto.DeptDataPermissionRespDTO;

import java.util.Collection;
import java.util.Set;


public interface PermissionApi {

    CommonResult<Set<Long>> getUserRoleIdListByRoleIds(Collection<Long> roleIds);

    CommonResult<Boolean> hasAnyPermissions(Long userId, String... permissions);

    CommonResult<Boolean> hasAnyRoles(Long userId, String... roles);

    CommonResult<DeptDataPermissionRespDTO> getDeptDataPermission(Long userId);

}
