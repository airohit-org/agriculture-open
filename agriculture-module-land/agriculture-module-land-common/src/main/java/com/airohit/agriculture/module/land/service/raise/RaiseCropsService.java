package com.airohit.agriculture.module.land.service.raise;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.raise.RaiseCropsDO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsExportReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsPageReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 种植作物 Service 接口
 *
 * @author shiminghao
 */
public interface RaiseCropsService {

    /**
     * 创建种植作物
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createCrops(@Valid RaiseCropsCreateReqVO createReqVO);

    /**
     * 创建种植作物
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void createCropsByMaster(@Valid RaiseCropsCreateReqVO createReqVO);

    /**
     * 更新种植作物
     *
     * @param updateReqVO 更新信息
     */
    void updateCrops(@Valid RaiseCropsUpdateReqVO updateReqVO);

    /**
     * 删除种植作物
     *
     * @param id 编号
     */
    void deleteCrops(Integer id);

    /**
     * 获得种植作物
     *
     * @param id 编号
     * @return 种植作物
     */
    RaiseCropsDO getCrops(Integer id);

    /**
     * 获得种植作物
     *
     * @param id 编号
     * @return 种植作物
     */
    RaiseCropsDO getCrops(String dataCode, Long tenantId, Long farmTenantId);

    /**
     * 获得种植作物列表
     *
     * @param ids 编号
     * @return 种植作物列表
     */
    List<RaiseCropsDO> getCropsList(Collection<Integer> ids);

    /**
     * 获得种植作物分页
     *
     * @param pageReqVO 分页查询
     * @return 种植作物分页
     */
    PageResult<RaiseCropsDO> getCropsPage(RaiseCropsPageReqVO pageReqVO);

    /**
     * 获得种植作物列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 种植作物列表
     */
    List<RaiseCropsDO> getCropsList(RaiseCropsExportReqVO exportReqVO);

}
