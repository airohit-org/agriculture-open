package com.airohit.agriculture.module.system.api.dept;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.util.collection.CollectionUtils;
import com.airohit.agriculture.module.system.api.dept.dto.DeptRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DeptApi {


    CommonResult<DeptRespDTO> getDept(Long id);

    CommonResult<List<DeptRespDTO>> getDepts(Collection<Long> ids);

    CommonResult<Boolean> validDepts(Collection<Long> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, DeptRespDTO> getDeptMap(Set<Long> ids) {
        return CollectionUtils.convertMap(getDepts(ids).getCheckedData(), DeptRespDTO::getId);
    }

}
