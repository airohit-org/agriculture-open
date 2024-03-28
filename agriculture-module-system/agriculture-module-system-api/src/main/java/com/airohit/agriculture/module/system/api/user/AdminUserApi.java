package com.airohit.agriculture.module.system.api.user;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.util.collection.CollectionUtils;
import com.airohit.agriculture.module.system.api.user.dto.AdminUserRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


// TODO shiminghao：fallbackFactory =
public interface AdminUserApi {


    CommonResult<AdminUserRespDTO> getUser(Long id);

    CommonResult<List<AdminUserRespDTO>> getUsers(Collection<Long> ids);

    CommonResult<List<AdminUserRespDTO>> getUsersByDeptIds(Collection<Long> deptIds);

    CommonResult<List<AdminUserRespDTO>> getUsersByPostIds(Collection<Long> postIds);

    /**
     * 获得用户 Map
     *
     * @param ids 用户编号数组
     * @return 用户 Map
     */
    default Map<Long, AdminUserRespDTO> getUserMap(Collection<Long> ids) {
        return CollectionUtils.convertMap(getUsers(ids).getCheckedData(), AdminUserRespDTO::getId);
    }

    CommonResult<Boolean> validUsers(Set<Long> ids);

}
