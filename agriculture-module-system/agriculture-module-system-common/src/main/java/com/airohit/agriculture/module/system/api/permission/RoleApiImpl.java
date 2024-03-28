package com.airohit.agriculture.module.system.api.permission;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.service.permission.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private RoleService roleService;

    @Override
    public CommonResult<Boolean> validRoles(Collection<Long> ids) {
        roleService.validRoles(ids);
        return success(true);
    }
}
