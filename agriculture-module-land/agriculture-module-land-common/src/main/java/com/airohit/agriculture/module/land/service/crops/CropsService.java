package com.airohit.agriculture.module.land.service.crops;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.crops.CropsDO;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsExportReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsPageReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 地块作物 Service 接口
 *
 * @author 管理员
 */
public interface CropsService {

    /**
     * 创建地块作物
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createCrops(@Valid CropsCreateReqVO createReqVO);

    /**
     * 更新地块作物
     *
     * @param updateReqVO 更新信息
     */
    void updateCrops(@Valid CropsUpdateReqVO updateReqVO);

    /**
     * 删除地块作物
     *
     * @param id 编号
     */
    void deleteCrops(Integer id);

    /**
     * 获得地块作物
     *
     * @param id 编号
     * @return 地块作物
     */
    CropsDO getCrops(Integer id);

    /**
     * 获得地块作物列表
     *
     * @param ids 编号
     * @return 地块作物列表
     */
    List<CropsDO> getCropsList(Collection<Integer> ids);

    /**
     * 获得地块作物分页
     *
     * @param pageReqVO 分页查询
     * @return 地块作物分页
     */
    PageResult<CropsDO> getCropsPage(CropsPageReqVO pageReqVO);

    /**
     * 获得地块作物列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 地块作物列表
     */
    List<CropsDO> getCropsList(CropsExportReqVO exportReqVO);

}
