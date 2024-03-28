package com.airohit.agriculture.module.system.api.dept;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.dept.dto.DeptRespDTO;
import com.airohit.agriculture.module.system.convert.dept.DeptConvert;
import com.airohit.agriculture.module.system.dal.dataobject.dept.DeptDO;
import com.airohit.agriculture.module.system.service.dept.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class DeptApiImpl implements DeptApi {

    @Resource
    private DeptService deptService;

    @Override
    public CommonResult<DeptRespDTO> getDept(Long id) {
        DeptDO dept = deptService.getDept(id);
        return success(DeptConvert.INSTANCE.convert03(dept));
    }

    @Override
    public CommonResult<List<DeptRespDTO>> getDepts(Collection<Long> ids) {
        List<DeptDO> depts = deptService.getDepts(ids);
        return success(DeptConvert.INSTANCE.convertList03(depts));
    }

    @Override
    public CommonResult<Boolean> validDepts(Collection<Long> ids) {
        deptService.validDepts(ids);
        return success(true);
    }

}
