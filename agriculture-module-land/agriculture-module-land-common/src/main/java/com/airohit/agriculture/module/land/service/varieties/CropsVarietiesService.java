package com.airohit.agriculture.module.land.service.varieties;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.varieties.CropsVarietiesDO;
import com.airohit.agriculture.module.land.vo.varieties.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 品种管理 Service 接口
 *
 * @author shiminghao
 */
public interface CropsVarietiesService {

    /**
     * 创建品种管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createVarieties(@Valid CropsVarietiesCreateReqVO createReqVO);

    /**
     * 创建品种管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void createVarietiesByMaster(@Valid CropsVarietiesCreateReqVO createReqVO);

    /**
     * 更新品种管理
     *
     * @param updateReqVO 更新信息
     */
    void updateVarieties(@Valid CropsVarietiesUpdateReqVO updateReqVO);

    /**
     * 获得品种管理
     *
     * @return 品种管理
     */
    CropsVarietiesDO getVarieties(String dataCode, Long tenantId);


    /**
     * 获得品种管理
     *
     * @param id 编号
     * @return 品种管理
     */
    CropsVarietiesDO getVarieties(Integer id);

    /**
     * 获得品种管理列表
     *
     * @param ids 编号
     * @return 品种管理列表
     */
    List<CropsVarietiesDO> getVarietiesList(Collection<Integer> ids);

    /**
     * 获得品种管理分页
     *
     * @param pageReqVO 分页查询
     * @return 品种管理分页
     */
    PageResult<CropsVarietiesRespVO> getVarietiesPage(CropsVarietiesPageReqVO pageReqVO);

    /**
     * 获得品种管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 品种管理列表
     */
    List<CropsVarietiesDO> getVarietiesList(CropsVarietiesExportReqVO exportReqVO);

}
