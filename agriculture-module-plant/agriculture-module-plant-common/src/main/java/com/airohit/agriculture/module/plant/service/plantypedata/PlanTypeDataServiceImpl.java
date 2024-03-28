package com.airohit.agriculture.module.plant.service.plantypedata;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.module.plant.convert.plantypedata.PlanTypeDataConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.dal.mysql.plantypedata.PlanTypeDataMapper;
import com.airohit.agriculture.module.plant.service.taskInfo.TaskInfoService;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataExportReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataPageReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataUpdateReqVO;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.PLAN_TYPE_DATA_NOT_EXISTS;

/**
 * 种植计划类型 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PlanTypeDataServiceImpl implements PlanTypeDataService {

    @Resource
    private PlanTypeDataMapper planTypeDataMapper;

    @Resource
    private TaskInfoService taskInfoService;

    @Override
    public Integer createPlanTypeData(PlanTypeDataCreateReqVO createReqVO) {

        if (StringUtil.isBlank(createReqVO.getStageCode())) {
            //阶段编码随机6位数
            int stageCode = (int) ((Math.random() * 9 + 1) * 100000);
            createReqVO.setStageCode(String.valueOf(stageCode));
        }

        // 插入
        PlanTypeDataDO planTypeData = PlanTypeDataConvert.INSTANCE.convert(createReqVO);
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        planTypeData.setFarmTenantId(farmTenantId);
        planTypeDataMapper.insert(planTypeData);
        // 返回
        return planTypeData.getId();
    }

    @Override
    public Integer createPlanTypeDataGroup(PlanTypeDataCreateReqVO createReqVO) {

        if (StringUtil.isBlank(createReqVO.getStageCode())) {
            //阶段编码随机6位数
            int stageCode = (int) ((Math.random() * 9 + 1) * 100000);
            createReqVO.setStageCode(String.valueOf(stageCode));
        }

        // 插入
        PlanTypeDataDO planTypeData = PlanTypeDataConvert.INSTANCE.convert(createReqVO);
        planTypeDataMapper.insert(planTypeData);
        // 返回
        return planTypeData.getId();
    }

    @Override
    public void updatePlanTypeData(PlanTypeDataUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePlanTypeDataExists(updateReqVO.getId());
        // 更新
        PlanTypeDataDO updateObj = PlanTypeDataConvert.INSTANCE.convert(updateReqVO);
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        updateObj.setFarmTenantId(farmTenantId);
        planTypeDataMapper.updateById(updateObj);
    }

    @Transactional
    @Override
    public void deletePlanTypeData(Integer id) {
        PlanTypeDataDO planTypeDataDO = planTypeDataMapper.selectById(id);
        // 校验存在
        if (planTypeDataDO == null) {
            throw exception(PLAN_TYPE_DATA_NOT_EXISTS);
        }
        // 删除
        planTypeDataMapper.deleteById(id);
        //删除农事任务
        taskInfoService.deleteTaskInfo(planTypeDataDO.getStageCode(), planTypeDataDO.getPlantingPlanId());
    }

    private void validatePlanTypeDataExists(Integer id) {
        if (planTypeDataMapper.selectById(id) == null) {
            throw exception(PLAN_TYPE_DATA_NOT_EXISTS);
        }
    }

    @Override
    public PlanTypeDataDO getPlanTypeData(Integer id) {
        return planTypeDataMapper.selectById(id);
    }

    @Override
    public List<PlanTypeDataDO> getPlanTypeDataList(Collection<Integer> ids) {
        return planTypeDataMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PlanTypeDataDO> getPlanTypeDataPage(PlanTypeDataPageReqVO pageReqVO) {
        return planTypeDataMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PlanTypeDataDO> getPlanTypeDataList(PlanTypeDataExportReqVO exportReqVO) {
        return planTypeDataMapper.selectList(exportReqVO);
    }

    @Override
    public List<PlanTypeDataDO> getPlanTypeDataByPlantingPlanId(Integer plantingPlanId) {
        PlanTypeDataExportReqVO planTypeDataExportReqVO = new PlanTypeDataExportReqVO();
        planTypeDataExportReqVO.setPlantingPlanId(plantingPlanId);
        List<PlanTypeDataDO> planTypeDataDOList = planTypeDataMapper.selectList(planTypeDataExportReqVO);

        return planTypeDataDOList;
    }

    @Override
    public void batchCreatePlanTypeData(List<PlanTypeDataCreateReqVO> createReqVO) {

    }

    @Override
    public List<PlanTypeDataDO> getPlanTypeDataByPlantingPlanIdAndParent(Integer plantingPlanId, Integer parentId) {
        PlanTypeDataExportReqVO planTypeDataExportReqVO = new PlanTypeDataExportReqVO();
        planTypeDataExportReqVO.setPlantingPlanId(plantingPlanId);
        planTypeDataExportReqVO.setParentId(parentId);
        List<PlanTypeDataDO> planTypeDataDOList = planTypeDataMapper.selectParentList(planTypeDataExportReqVO);
        return planTypeDataDOList;
    }

}
