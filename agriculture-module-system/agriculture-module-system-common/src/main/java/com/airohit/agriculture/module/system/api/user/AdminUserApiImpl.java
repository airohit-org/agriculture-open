package com.airohit.agriculture.module.system.api.user;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.user.dto.AdminUserRespDTO;
import com.airohit.agriculture.module.system.convert.user.UserConvert;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class AdminUserApiImpl implements AdminUserApi {

    @Resource
    private AdminUserService userService;

    @Override
    public CommonResult<AdminUserRespDTO> getUser(Long id) {
        AdminUserDO user = userService.getUser(id);
        return success(UserConvert.INSTANCE.convert4(user));
    }

    @Override
    public CommonResult<List<AdminUserRespDTO>> getUsers(Collection<Long> ids) {
        List<AdminUserDO> users = userService.getUsers(ids);
        return success(UserConvert.INSTANCE.convertList4(users));
    }

    @Override
    public CommonResult<List<AdminUserRespDTO>> getUsersByDeptIds(Collection<Long> deptIds) {
        List<AdminUserDO> users = userService.getUsersByDeptIds(deptIds);
        return success(UserConvert.INSTANCE.convertList4(users));
    }

    @Override
    public CommonResult<List<AdminUserRespDTO>> getUsersByPostIds(Collection<Long> postIds) {
        List<AdminUserDO> users = userService.getUsersByPostIds(postIds);
        return success(UserConvert.INSTANCE.convertList4(users));
    }

    @Override
    public CommonResult<Boolean> validUsers(Set<Long> ids) {
        userService.validUsers(ids);
        return success(true);
    }

}
