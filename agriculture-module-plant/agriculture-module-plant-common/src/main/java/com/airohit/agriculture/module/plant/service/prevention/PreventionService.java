package com.airohit.agriculture.module.plant.service.prevention;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.prevention.PreventionDO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionCreateReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionExportReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionPageReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 防治方案 Service 接口
 *
 * @author 管理员
 */
public interface PreventionService {

    /**
     * 创建防治方案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPrevention(@Valid PreventionCreateReqVO createReqVO);

    /**
     * 更新防治方案
     *
     * @param updateReqVO 更新信息
     */
    void updatePrevention(@Valid PreventionUpdateReqVO updateReqVO);

    /**
     * 删除防治方案
     *
     * @param id 编号
     */
    void deletePrevention(Integer id);

    /**
     * 获得防治方案
     *
     * @param id 编号
     * @return 防治方案
     */
    PreventionDO getPrevention(Integer id);

    PreventionDO getPrevention(String diseasesName);

    /**
     * 获得防治方案列表
     *
     * @param ids 编号
     * @return 防治方案列表
     */
    List<PreventionDO> getPreventionList(Collection<Integer> ids);

    /**
     * 获得防治方案分页
     *
     * @param pageReqVO 分页查询
     * @return 防治方案分页
     */
    PageResult<PreventionDO> getPreventionPage(PreventionPageReqVO pageReqVO);

    /**
     * 获得防治方案列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 防治方案列表
     */
    List<PreventionDO> getPreventionList(PreventionExportReqVO exportReqVO);

}
