package com.airohit.agriculture.module.plant.service.prevention;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.plant.convert.prevention.PreventionConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.prevention.PreventionDO;
import com.airohit.agriculture.module.plant.dal.mysql.prevention.PreventionMapper;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionCreateReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionExportReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionPageReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.PREVENTION_NOT_EXISTS;

/**
 * 防治方案 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PreventionServiceImpl implements PreventionService {

    @Resource
    private PreventionMapper preventionMapper;

    @Override
    public Integer createPrevention(PreventionCreateReqVO createReqVO) {
        // 插入
        PreventionDO prevention = PreventionConvert.INSTANCE.convert(createReqVO);
        preventionMapper.insert(prevention);
        // 返回
        return prevention.getId();
    }

    @Override
    public void updatePrevention(PreventionUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePreventionExists(updateReqVO.getId());
        // 更新
        PreventionDO updateObj = PreventionConvert.INSTANCE.convert(updateReqVO);
        preventionMapper.updateById(updateObj);
    }

    @Override
    public void deletePrevention(Integer id) {
        // 校验存在
        this.validatePreventionExists(id);
        // 删除
        preventionMapper.deleteById(id);
    }

    private void validatePreventionExists(Integer id) {
        if (preventionMapper.selectById(id) == null) {
            throw exception(PREVENTION_NOT_EXISTS);
        }
    }

    @Override
    @TenantIgnore
    public PreventionDO getPrevention(Integer id) {
        return preventionMapper.selectById(id);
    }

    @Override
    @TenantIgnore
    public PreventionDO getPrevention(String diseasesName) {
        return preventionMapper.selectOne(PreventionDO::getDiseasesName, diseasesName);
    }

    @Override
    public List<PreventionDO> getPreventionList(Collection<Integer> ids) {
        return preventionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PreventionDO> getPreventionPage(PreventionPageReqVO pageReqVO) {
        return preventionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PreventionDO> getPreventionList(PreventionExportReqVO exportReqVO) {
        return preventionMapper.selectList(exportReqVO);
    }

}
