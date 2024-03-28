package com.airohit.agriculture.module.plant.api.plan;

import cn.hutool.core.bean.BeanUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.dal.mysql.plan.PlanMapper;
import com.airohit.agriculture.module.plant.dal.mysql.plantypedata.PlanTypeDataMapper;
import com.airohit.agriculture.module.plant.service.plan.PlanService;
import com.airohit.agriculture.module.plant.service.plantypedata.PlanTypeDataService;
import com.airohit.agriculture.module.plant.vo.plan.PlanCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataCreateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
@Slf4j
public class PlanApiImpl implements PlanApi {

    @Resource
    private PlanService planService;

    @Resource
    private PlanTypeDataService planTypeDataService;

    @Resource
    private PlanTypeDataMapper planTypeDataMapper;

    @Resource
    private PlanMapper planMapper;

    @Override
    public CommonResult<Boolean> landBindPlan(Integer planId, Integer landId) {
        planService.landBindPlan(planId, landId);
        return success(true);
    }

    @Override
    public CommonResult<PlanRespDTO> getPlan(Integer landId) {
        PlanRespDTO planRespDTO = planService.getPlanByLandId(landId);
        return success(planRespDTO);
    }

    @Override
    public CommonResult<Integer> createGroupPlan(PlanCreateReqVO createReqVO) {
        Integer planId = null;

        AtomicReference<PlanDO> planDOAtomicReference = new AtomicReference<>(new PlanDO());
        TenantUtils.execute(createReqVO.getTenantId(), () ->
                planDOAtomicReference.set(planMapper.selectOne(new LambdaQueryWrapperX<PlanDO>()
                        .eqIfPresent(PlanDO::getDataCode, createReqVO.getDataCode()).eqIfPresent(PlanDO::getFarmTenantId, createReqVO.getFarmTenantId()))));

        //PlanDO planDO = planMapper.selectOne(PlanDO::getDataCode, createReqVO.getDataCode(),PlanDO::getTenantId,createReqVO.getTenantId());
        PlanDO planDO = planDOAtomicReference.get();

        if (Objects.isNull(planDO)) {
            planId = planService.createPlanGroup(createReqVO);
        } else {
            //PlanUpdateReqVO planUpdateReqVO = new PlanUpdateReqVO();
            //BeanUtil.copyProperties(createReqVO,planUpdateReqVO);
            //planService.updatePlanByDataCode(planUpdateReqVO);
            //planId = planDO.getId();

            Integer id = planDO.getId();
            BeanUtil.copyProperties(createReqVO, planDO);
            planDO.setId(id);
            planMapper.updateById(planDO);
            planId = planDO.getId();
        }

        return CommonResult.success(planId);
    }

    @Override
    public CommonResult<Integer> createGroupPlanTypeData(PlanTypeDataCreateReqVO createReqVO) {
        Integer planTypeDataId = null;

        AtomicReference<PlanTypeDataDO> planTypeDataDOAtomicReference = new AtomicReference<>(new PlanTypeDataDO());
        TenantUtils.execute(createReqVO.getTenantId(), () -> planTypeDataDOAtomicReference.set(planTypeDataMapper.selectOne(new LambdaQueryWrapperX<PlanTypeDataDO>()
                .eqIfPresent(PlanTypeDataDO::getDataCode, createReqVO.getDataCode()).eqIfPresent(PlanTypeDataDO::getFarmTenantId, createReqVO.getFarmTenantId()))));

        PlanTypeDataDO planTypeDataDO = planTypeDataDOAtomicReference.get();
        //PlanTypeDataDO planTypeDataDO = planTypeDataMapper.selectOne(PlanTypeDataDO::getDataCode, createReqVO.getDataCode(), PlanTypeDataDO::getTenantId, createReqVO.getTenantId());
        log.info("=================  createPlanTypeData createReqVO {} planTypeDataDO {}", createReqVO, planTypeDataDO);
        if (Objects.isNull(planTypeDataDO)) {
            planTypeDataId = planTypeDataService.createPlanTypeDataGroup(createReqVO);
            log.info("=================  createPlanTypeData planTypeDataId {}", planTypeDataId);
        } else {
            //UpdateWrapper<PlanTypeDataDO> updateWrapper = new UpdateWrapper<>();
            //updateWrapper.eq("data_code",createReqVO.getDataCode());
            //PlanTypeDataDO planTypeDataDO1 = new PlanTypeDataDO();
            //BeanUtil.copyProperties(createReqVO,planTypeDataDO1);
            //planTypeDataMapper.update(planTypeDataDO1,updateWrapper);
            Integer id = planTypeDataDO.getId();
            BeanUtil.copyProperties(createReqVO, planTypeDataDO);
            planTypeDataDO.setId(id);
            planTypeDataMapper.updateById(planTypeDataDO);
            planTypeDataId = planTypeDataDO.getId();

            log.info("=================  createPlanTypeData updateById {}", planTypeDataId);
        }

        return CommonResult.success(planTypeDataId);
    }
}
