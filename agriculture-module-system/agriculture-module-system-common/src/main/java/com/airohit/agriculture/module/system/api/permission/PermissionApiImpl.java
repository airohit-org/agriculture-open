package com.airohit.agriculture.module.system.api.permission;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.permission.dto.DeptDataPermissionRespDTO;
import com.airohit.agriculture.module.system.service.permission.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class PermissionApiImpl implements PermissionApi {

    @Resource
    private PermissionService permissionService;

    @Override
    public CommonResult<Set<Long>> getUserRoleIdListByRoleIds(Collection<Long> roleIds) {
        return success(permissionService.getUserRoleIdListByRoleIds(roleIds));
    }

    @Override
    public CommonResult<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return success(permissionService.hasAnyPermissions(userId, permissions));
    }

    @Override
    public CommonResult<Boolean> hasAnyRoles(Long userId, String... roles) {
        return success(permissionService.hasAnyRoles(userId, roles));
    }

    @Override
    public CommonResult<DeptDataPermissionRespDTO> getDeptDataPermission(Long userId) {
        return success(permissionService.getDeptDataPermission(userId));
    }

}
