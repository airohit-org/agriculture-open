package com.airohit.agriculture.module.plant.service.plan;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.vo.plan.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 种植计划 Service 接口
 *
 * @author 管理员
 */
public interface PlanService {

    /**
     * 创建种植计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPlan(@Valid PlanCreateReqVO createReqVO);


    /**
     * 创建种植计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPlanGroup(@Valid PlanCreateReqVO createReqVO);

    /**
     * 更新种植计划
     *
     * @param updateReqVO 更新信息
     */
    void updatePlan(@Valid PlanUpdateReqVO updateReqVO);

    /**
     * 删除种植计划
     *
     * @param id 编号
     */
    void deletePlan(Integer id);

    /**
     * 获得种植计划
     *
     * @param id 编号
     * @return 种植计划
     */
    PlanDO getPlan(Integer id);

    /**
     * 获得种植计划列表
     *
     * @param ids 编号
     * @return 种植计划列表
     */
    List<PlanDO> getPlanList(Collection<Integer> ids);

    /**
     * 获得种植计划分页
     *
     * @param pageReqVO 分页查询
     * @return 种植计划分页
     */
    PageResult<PlanDO> getPlanPage(PlanPageReqVO pageReqVO);

    /**
     * 获得种植计划列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 种植计划列表
     */
    List<PlanDO> getPlanList(PlanExportReqVO exportReqVO);

    /**
     * 查询所有计划
     *
     * @return
     */
    List<PlanDO> getAllList();

    /**
     * 计划任务克隆
     *
     * @param planCloneReqVO
     */
    void clone(PlanCloneReqVO planCloneReqVO);

    /**
     * 查询种植计划列表
     *
     * @param pageVO
     * @return
     */
    PageResult<PlanRespVO> getPlanListPage(PlanPageReqVO pageVO);

    /**
     * 查询计划已绑定地块
     *
     * @return
     */
    List<PlanBindLandVO> queryPlanBindLand();

    /**
     * 种植计划发布或取消发布
     *
     * @param id
     * @param status
     */
    void planPublish(Integer id, Integer status);

    /**
     * 查询地块计划
     *
     * @return
     */
    List<PlanBindLandVO> queryLandPlan();

    /**
     * 地块绑定计划
     *
     * @param planId
     * @param landId
     */
    void landBindPlan(Integer planId, Integer landId);

    /**
     * 克隆模版
     *
     * @param planCloneReqVO
     */
    void cloneTemplate(PlanCloneReqVO planCloneReqVO);

    /**
     * 根据地块编号查询地块
     *
     * @param landId
     * @return
     */
    PlanRespDTO getPlanByLandId(Integer landId);


    PlanRespVO getPlanById(Integer id);

    /**
     * 根据 dataCode 更新模版
     *
     * @param planUpdateReqVO
     */
    void updatePlanByDataCode(PlanUpdateReqVO planUpdateReqVO);
}
