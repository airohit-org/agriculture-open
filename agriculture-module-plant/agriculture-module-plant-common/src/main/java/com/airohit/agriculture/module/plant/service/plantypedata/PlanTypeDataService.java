package com.airohit.agriculture.module.plant.service.plantypedata;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataExportReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataPageReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 种植计划类型 Service 接口
 *
 * @author 管理员
 */
public interface PlanTypeDataService {

    /**
     * 创建种植计划类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPlanTypeData(@Valid PlanTypeDataCreateReqVO createReqVO);

    /**
     * 创建种植计划类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPlanTypeDataGroup(@Valid PlanTypeDataCreateReqVO createReqVO);

    /**
     * 更新种植计划类型
     *
     * @param updateReqVO 更新信息
     */
    void updatePlanTypeData(@Valid PlanTypeDataUpdateReqVO updateReqVO);

    /**
     * 删除种植计划类型
     *
     * @param id 编号
     */
    void deletePlanTypeData(Integer id);

    /**
     * 获得种植计划类型
     *
     * @param id 编号
     * @return 种植计划类型
     */
    PlanTypeDataDO getPlanTypeData(Integer id);

    /**
     * 获得种植计划类型列表
     *
     * @param ids 编号
     * @return 种植计划类型列表
     */
    List<PlanTypeDataDO> getPlanTypeDataList(Collection<Integer> ids);

    /**
     * 获得种植计划类型分页
     *
     * @param pageReqVO 分页查询
     * @return 种植计划类型分页
     */
    PageResult<PlanTypeDataDO> getPlanTypeDataPage(PlanTypeDataPageReqVO pageReqVO);

    /**
     * 获得种植计划类型列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 种植计划类型列表
     */
    List<PlanTypeDataDO> getPlanTypeDataList(PlanTypeDataExportReqVO exportReqVO);

    List<PlanTypeDataDO> getPlanTypeDataByPlantingPlanId(Integer plantingPlanId);

    void batchCreatePlanTypeData(List<PlanTypeDataCreateReqVO> createReqVO);

    List<PlanTypeDataDO> getPlanTypeDataByPlantingPlanIdAndParent(Integer plantingPlanId, Integer parentId);
}
