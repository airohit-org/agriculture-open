package com.airohit.agriculture.module.plant.service.plan;

import cn.hutool.core.bean.BeanUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.airohit.agriculture.module.plant.convert.plan.PlanConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.dal.mysql.plan.PlanMapper;
import com.airohit.agriculture.module.plant.dal.mysql.plantypedata.PlanTypeDataMapper;
import com.airohit.agriculture.module.plant.service.plantypedata.PlanTypeDataService;
import com.airohit.agriculture.module.plant.service.taskInfo.TaskInfoService;
import com.airohit.agriculture.module.plant.vo.plan.*;
import com.airohit.agriculture.module.system.api.dict.DictDataApi;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.PLAN_NOT_EXISTS;

/**
 * 种植计划 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class PlanServiceImpl implements PlanService {

    @Resource
    private PlanMapper planMapper;

    @Resource
    private PlanTypeDataMapper planTypeDataMapper;

    @Resource
    private DictDataApi dictDataApi;

    @Resource
    private PlanTypeDataService planTypeDataService;

    @Resource
    private TaskInfoService taskInfoService;

    private static Date dateAddDays(Date date, int daty) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate now = instant.atZone(zoneId).toLocalDate();
        LocalDate plusDate = now.plus(daty, ChronoUnit.DAYS);
        instant = plusDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
        date = Date.from(instant);
        return date;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createPlan(PlanCreateReqVO createReqVO) {
        // 插入
        PlanDO plan = PlanConvert.INSTANCE.convert(createReqVO);

        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        plan.setFarmTenantId(farmTenantId);

        planMapper.insert(plan);

        /**
         if ("1".equals(createReqVO.getCrops())) {
         initPlantingPlanDate(createReqVO,"plan_type_maize", plan.getId());
         }

         if ("2".equals(createReqVO.getCrops())) {
         initPlantingPlanDate(createReqVO,"plan_type_paddy", plan.getId());
         }

         if ("3".equals(createReqVO.getCrops())) {
         initPlantingPlanDate(createReqVO,"plan_type_soybean", plan.getId());
         }
         **/

        // 返回
        return plan.getId();
    }

    @Override
    public Integer createPlanGroup(PlanCreateReqVO createReqVO) {
        PlanDO plan = PlanConvert.INSTANCE.convert(createReqVO);
        planMapper.insert(plan);
        return plan.getId();
    }

    private void initPlantingPlanDate(PlanCreateReqVO createReqVO, String planType, int planId) {
        CommonResult<List<DictDataRespDTO>> dictDataAll = dictDataApi.getDictDataAll(planType);
        if (dictDataAll.isSuccess()) {
            List<DictDataRespDTO> dictDataRespDTOList = dictDataAll.getData();
            if (dictDataRespDTOList != null && dictDataRespDTOList.size() > 0) {
                Integer planningCycle = 0;
                for (int i = 0; i < dictDataRespDTOList.size(); i++) {
                    DictDataRespDTO dictDataRespDTO = dictDataRespDTOList.get(i);
                    PlanTypeDataDO planTypeDataDO = new PlanTypeDataDO();

                    if (i == 0) {
                        planningCycle = planningCycle + Integer.parseInt(dictDataRespDTO.getRemark());
                        planTypeDataDO.setPlantingPlanDate(createReqVO.getStartTime());
                    } else {
                        planTypeDataDO.setPlantingPlanDate(dateAddDays(createReqVO.getStartTime(), planningCycle));
                        planningCycle = planningCycle + Integer.parseInt(dictDataRespDTO.getRemark());
                    }

                    planTypeDataDO.setPlantingPlanId(planId);
                    planTypeDataDO.setSort(i);
                    planTypeDataDO.setStageCode(dictDataRespDTO.getValue());
                    planTypeDataDO.setStageName(dictDataRespDTO.getLabel());
                    planTypeDataDO.setPeriod(Integer.parseInt(dictDataRespDTO.getRemark()));
                    Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
                    planTypeDataDO.setFarmTenantId(farmTenantId);
                    planTypeDataMapper.insert(planTypeDataDO);
                }
            }
        }
    }

    @Override
    public void updatePlan(PlanUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePlanExists(updateReqVO.getId());
        // 更新
        PlanDO updateObj = PlanConvert.INSTANCE.convert(updateReqVO);
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        updateObj.setFarmTenantId(farmTenantId);
        planMapper.updateById(updateObj);
    }

    @Override
    public void deletePlan(Integer id) {
        // 校验存在
        this.validatePlanExists(id);
        // 删除
        planMapper.deleteById(id);

        //删除任务
        taskInfoService.deleteTaskInfo(null, id);
    }

    private void validatePlanExists(Integer id) {
        if (planMapper.selectById(id) == null) {
            throw exception(PLAN_NOT_EXISTS);
        }
    }

    @Override
    public PlanDO getPlan(Integer id) {
        return planMapper.selectById(id);
    }

    @Override
    public List<PlanDO> getPlanList(Collection<Integer> ids) {
        return planMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PlanDO> getPlanPage(PlanPageReqVO pageReqVO) {
        return planMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PlanDO> getPlanList(PlanExportReqVO exportReqVO) {
        return planMapper.selectList(exportReqVO);
    }

    @Override
    public List<PlanDO> getAllList() {
        PlanExportReqVO planExportReqVO = new PlanExportReqVO();
        return planMapper.selectList(planExportReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clone(PlanCloneReqVO planCloneReqVO) {

        this.validatePlanExists(planCloneReqVO.getPlantingPlanId());

        PlanDO planDO = planMapper.selectById(planCloneReqVO.getPlantingPlanId());
        planDO.setPlanName(planCloneReqVO.getPlanName());
        planDO.setStartTime(planCloneReqVO.getStartTime());
        planDO.setId(null);
        planDO.setStatus(null);
        planDO.setCreateTime(null);
        planDO.setUpdateTime(null);

        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        planDO.setFarmTenantId(farmTenantId);

        planMapper.insert(planDO);

        List<PlanTypeDataDO> planTypeDataDOParentList = planTypeDataService.getPlanTypeDataByPlantingPlanIdAndParent(planCloneReqVO.getPlantingPlanId(), 0);
        for (PlanTypeDataDO planTypeDataDO : planTypeDataDOParentList) {

            PlanTypeDataDO planTypeDataDOCreate = new PlanTypeDataDO();

            BeanUtil.copyProperties(planTypeDataDO, planTypeDataDOCreate);
            planTypeDataDOCreate.setId(null);
            planTypeDataDOCreate.setCreateTime(null);
            planTypeDataDOCreate.setUpdateTime(null);
            planTypeDataDOCreate.setPlantingPlanId(planDO.getId());
            planTypeDataDOCreate.setFarmTenantId(farmTenantId);
            planTypeDataMapper.insert(planTypeDataDOCreate);

            log.info("==============planTypeDataId  {}", planTypeDataDOCreate.getId());
            List<PlanTypeDataDO> planTypeDataDOChildList = planTypeDataService.getPlanTypeDataByPlantingPlanIdAndParent(planCloneReqVO.getPlantingPlanId(), planTypeDataDO.getId());

            log.info("==============planTypeDataDOChildList  {}", planTypeDataDOChildList.toString());


            if (planTypeDataDOChildList != null && planTypeDataDOChildList.size() > 0) {
                Integer planningCycle = 0;
                for (int i = 0; i < planTypeDataDOChildList.size(); i++) {
                    PlanTypeDataDO planTypeDataChildDO = planTypeDataDOChildList.get(i);
                    if (i == 0) {
                        planningCycle = planningCycle + planTypeDataChildDO.getPeriod();
                        planTypeDataChildDO.setPlantingPlanDate(planCloneReqVO.getStartTime());
                    } else {
                        planTypeDataChildDO.setPlantingPlanDate(dateAddDays(planCloneReqVO.getStartTime(), planningCycle));
                        planningCycle = planningCycle + planTypeDataChildDO.getPeriod();
                    }

                    planTypeDataChildDO.setPlantingPlanId(planDO.getId());
                    planTypeDataChildDO.setSort(i);
                    planTypeDataChildDO.setPeriodName(planTypeDataChildDO.getPeriodName());
                    planTypeDataChildDO.setStageCode(planTypeDataChildDO.getStageCode());
                    planTypeDataChildDO.setStageName(planTypeDataChildDO.getStageName());
                    planTypeDataChildDO.setPeriod(planTypeDataChildDO.getPeriod());
                    planTypeDataChildDO.setId(null);
                    planTypeDataChildDO.setParentId(planTypeDataDOCreate.getId());
                    planTypeDataChildDO.setCreateTime(null);
                    planTypeDataChildDO.setUpdateTime(null);
                    planTypeDataChildDO.setFarmTenantId(farmTenantId);
                    planTypeDataMapper.insert(planTypeDataChildDO);
                }
            }

            log.info("planCloneReqVO.getPlantingPlanId() ==== {}   planDO.getId() ===== {}", planCloneReqVO.getPlantingPlanId(), planDO.getId());

            taskInfoService.clonePlanTask(planCloneReqVO.getPlantingPlanId(), planDO.getId());
        }
    }

    @Override
    public PageResult<PlanRespVO> getPlanListPage(PlanPageReqVO pageVO) {
        IPage<PlanRespVO> planMapperPlanListPage = planMapper.getPlanListPage(new Page<>(pageVO.getPageNo(), pageVO.getPageSize()), pageVO);
        return new PageResult<>(planMapperPlanListPage.getRecords(), planMapperPlanListPage.getTotal());
    }

    @Override
    public List<PlanBindLandVO> queryPlanBindLand() {
        return planMapper.queryPlanBindLand();
    }

    @Override
    public void planPublish(Integer id, Integer status) {
        PlanDO planDO = new PlanDO();
        planDO.setId(id);
        planDO.setStatus(status);
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        planDO.setFarmTenantId(farmTenantId);
        planMapper.updateById(planDO);
    }

    @Override
    public List<PlanBindLandVO> queryLandPlan() {
        return planMapper.queryLandPlan();
    }

    @Override
    public void landBindPlan(Integer planId, Integer landId) {

        log.info("landBindPlan =>>>>>> planId" + planId + "     landId" + landId);

        //this.validatePlanExists(planId);

        if (planId == -1) {
            //planMapper.LandUnbindingByPlanId(planId);
            planMapper.LandUnbindingByLandId(landId);
        } else {
            //先解绑
            PlanDO planDO1 = planMapper.selectById(planId);
            if (planDO1 != null) {
                planMapper.LandUnbindingByLandId(landId);
            }
            //绑定
            PlanDO planDO = new PlanDO();
            planDO.setId(planId);
            planDO.setLandId(landId);
            Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
            planDO.setFarmTenantId(farmTenantId);
            planMapper.updateById(planDO);
        }
    }

    @Override
    public void cloneTemplate(PlanCloneReqVO planCloneReqVO) {
        this.validatePlanExists(planCloneReqVO.getPlantingPlanId());

        PlanDO planDO = planMapper.selectById(planCloneReqVO.getPlantingPlanId());
        planDO.setPlanName(planCloneReqVO.getPlanName());
        planDO.setStartTime(planCloneReqVO.getStartTime());
        planDO.setId(null);
        planDO.setStatus(null);
        planDO.setCreateTime(null);
        planDO.setUpdateTime(null);
        if (1 == planCloneReqVO.getType()) {
            planDO.setIsTemplate(1);
            planDO.setType(planCloneReqVO.getType());
        }

        if (2 == planCloneReqVO.getType()) {
            planDO.setIsTemplate(0);
            planDO.setType(planCloneReqVO.getType());
        }

        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        planDO.setFarmTenantId(farmTenantId);
        planMapper.insert(planDO);


        /**
         List<PlanTypeDataDO> planTypeDataDOList = planTypeDataService.getPlanTypeDataByPlantingPlanId(planCloneReqVO.getPlantingPlanId());

         if (planTypeDataDOList != null && planTypeDataDOList.size() > 0){
         Integer planningCycle = 0;
         for (int i = 0; i < planTypeDataDOList.size(); i++){
         PlanTypeDataDO planTypeDataDO = planTypeDataDOList.get(i);
         if(i==0) {
         planningCycle = planningCycle + planTypeDataDO.getPeriod();
         planTypeDataDO.setPlantingPlanDate(planCloneReqVO.getStartTime());
         }else {
         planTypeDataDO.setPlantingPlanDate(dateAddDays(planCloneReqVO.getStartTime(), planningCycle));
         planningCycle = planningCycle + planTypeDataDO.getPeriod();
         }

         planTypeDataDO.setPlantingPlanId(planDO.getId());
         planTypeDataDO.setSort(i);
         planTypeDataDO.setStageCode(planTypeDataDO.getStageCode());
         planTypeDataDO.setStageName(planTypeDataDO.getStageName());
         planTypeDataDO.setPeriod(planTypeDataDO.getPeriod());
         planTypeDataDO.setId(null);
         planTypeDataMapper.insert(planTypeDataDO);
         }
         taskInfoService.clonePlanTask(planCloneReqVO.getPlantingPlanId(),planDO.getId());
         }
         **/

        List<PlanTypeDataDO> planTypeDataDOParentList = planTypeDataService.getPlanTypeDataByPlantingPlanIdAndParent(planCloneReqVO.getPlantingPlanId(), 0);
        for (PlanTypeDataDO planTypeDataDO : planTypeDataDOParentList) {

            PlanTypeDataDO planTypeDataDOCreate = new PlanTypeDataDO();

            BeanUtil.copyProperties(planTypeDataDO, planTypeDataDOCreate);
            planTypeDataDOCreate.setId(null);
            planTypeDataDOCreate.setCreateTime(null);
            planTypeDataDOCreate.setUpdateTime(null);
            planTypeDataDOCreate.setPlantingPlanId(planDO.getId());
            planTypeDataDOCreate.setFarmTenantId(farmTenantId);
            planTypeDataMapper.insert(planTypeDataDOCreate);

            log.info("==============planTypeDataId  {}", planTypeDataDOCreate.getId());
            List<PlanTypeDataDO> planTypeDataDOChildList = planTypeDataService.getPlanTypeDataByPlantingPlanIdAndParent(planCloneReqVO.getPlantingPlanId(), planTypeDataDO.getId());

            log.info("==============planTypeDataDOChildList  {}", planTypeDataDOChildList.toString());


            if (planTypeDataDOChildList != null && planTypeDataDOChildList.size() > 0) {
                Integer planningCycle = 0;
                for (int i = 0; i < planTypeDataDOChildList.size(); i++) {
                    PlanTypeDataDO planTypeDataChildDO = planTypeDataDOChildList.get(i);
                    if (i == 0) {
                        planningCycle = planningCycle + planTypeDataChildDO.getPeriod();
                        planTypeDataChildDO.setPlantingPlanDate(planCloneReqVO.getStartTime());
                    } else {
                        planTypeDataChildDO.setPlantingPlanDate(dateAddDays(planCloneReqVO.getStartTime(), planningCycle));
                        planningCycle = planningCycle + planTypeDataChildDO.getPeriod();
                    }

                    planTypeDataChildDO.setPlantingPlanId(planDO.getId());
                    planTypeDataChildDO.setSort(i);
                    planTypeDataChildDO.setPeriodName(planTypeDataChildDO.getPeriodName());
                    planTypeDataChildDO.setStageCode(planTypeDataChildDO.getStageCode());
                    planTypeDataChildDO.setStageName(planTypeDataChildDO.getStageName());
                    planTypeDataChildDO.setPeriod(planTypeDataChildDO.getPeriod());
                    planTypeDataChildDO.setId(null);
                    planTypeDataChildDO.setParentId(planTypeDataDOCreate.getId());
                    planTypeDataChildDO.setCreateTime(null);
                    planTypeDataChildDO.setUpdateTime(null);
                    planTypeDataChildDO.setFarmTenantId(farmTenantId);
                    planTypeDataMapper.insert(planTypeDataChildDO);
                }
            }
        }
        log.info("planCloneReqVO.getPlantingPlanId() ==== {}   planDO.getId() ===== {}", planCloneReqVO.getPlantingPlanId(), planDO.getId());
        taskInfoService.clonePlanTask(planCloneReqVO.getPlantingPlanId(), planDO.getId());

    }

    @Override
    public PlanRespDTO getPlanByLandId(Integer landId) {
        return planMapper.getPlanByLandId(landId);
    }

    @Override
    public PlanRespVO getPlanById(Integer id) {
        return planMapper.getPlanById(id);
    }

    @Override
    public void updatePlanByDataCode(PlanUpdateReqVO planUpdateReqVO) {

        UpdateWrapper<PlanDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("data_code", planUpdateReqVO.getDataCode());
        PlanDO planDO = new PlanDO();
        BeanUtil.copyProperties(planUpdateReqVO, planDO);
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        planDO.setFarmTenantId(farmTenantId);
        planMapper.update(planDO, updateWrapper);
    }
}
