package com.airohit.agriculture.module.plant.service.planTemplate;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.convert.planTemplate.PlanTemplateConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.planTemplate.PlanTemplateDO;
import com.airohit.agriculture.module.plant.dal.mysql.planTemplate.PlanTemplateMapper;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateCreateReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateExportReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplatePageReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.PLAN_TEMPLATE_NOT_EXISTS;

/**
 * 计划模版 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PlanTemplateServiceImpl implements PlanTemplateService {

    @Resource
    private PlanTemplateMapper planTemplateMapper;

    @Override
    public Integer createPlanTemplate(PlanTemplateCreateReqVO createReqVO) {
        // 插入
        PlanTemplateDO planTemplate = PlanTemplateConvert.INSTANCE.convert(createReqVO);
        planTemplateMapper.insert(planTemplate);
        // 返回
        return planTemplate.getId();
    }

    @Override
    public void updatePlanTemplate(PlanTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePlanTemplateExists(updateReqVO.getId());
        // 更新
        PlanTemplateDO updateObj = PlanTemplateConvert.INSTANCE.convert(updateReqVO);
        planTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deletePlanTemplate(Integer id) {
        // 校验存在
        this.validatePlanTemplateExists(id);
        // 删除
        planTemplateMapper.deleteById(id);
    }

    private void validatePlanTemplateExists(Integer id) {
        if (planTemplateMapper.selectById(id) == null) {
            throw exception(PLAN_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public PlanTemplateDO getPlanTemplate(Integer id) {
        return planTemplateMapper.selectById(id);
    }

    @Override
    public List<PlanTemplateDO> getPlanTemplateList(Collection<Integer> ids) {
        return planTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PlanTemplateDO> getPlanTemplatePage(PlanTemplatePageReqVO pageReqVO) {
        return planTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PlanTemplateDO> getPlanTemplateList(PlanTemplateExportReqVO exportReqVO) {
        return planTemplateMapper.selectList(exportReqVO);
    }

}
