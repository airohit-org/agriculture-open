package com.airohit.agriculture.module.plant.service.planTemplate;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.planTemplate.PlanTemplateDO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateCreateReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateExportReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplatePageReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 计划模版 Service 接口
 *
 * @author 管理员
 */
public interface PlanTemplateService {

    /**
     * 创建计划模版
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPlanTemplate(@Valid PlanTemplateCreateReqVO createReqVO);

    /**
     * 更新计划模版
     *
     * @param updateReqVO 更新信息
     */
    void updatePlanTemplate(@Valid PlanTemplateUpdateReqVO updateReqVO);

    /**
     * 删除计划模版
     *
     * @param id 编号
     */
    void deletePlanTemplate(Integer id);

    /**
     * 获得计划模版
     *
     * @param id 编号
     * @return 计划模版
     */
    PlanTemplateDO getPlanTemplate(Integer id);

    /**
     * 获得计划模版列表
     *
     * @param ids 编号
     * @return 计划模版列表
     */
    List<PlanTemplateDO> getPlanTemplateList(Collection<Integer> ids);

    /**
     * 获得计划模版分页
     *
     * @param pageReqVO 分页查询
     * @return 计划模版分页
     */
    PageResult<PlanTemplateDO> getPlanTemplatePage(PlanTemplatePageReqVO pageReqVO);

    /**
     * 获得计划模版列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计划模版列表
     */
    List<PlanTemplateDO> getPlanTemplateList(PlanTemplateExportReqVO exportReqVO);

}
