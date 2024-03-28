package com.airohit.agriculture.module.land.service.land;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.vo.*;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsVarietiesBaseVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsBaseVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsRespVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 地块信息 Service 接口
 *
 * @author 管理员
 */
public interface LandService {

    /**
     * 创建地块信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer create(@Valid LandCreateReqVO createReqVO);

    /**
     * 更新地块信息
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid LandUpdateReqVO updateReqVO);

    /**
     * 删除地块信息
     *
     * @param id 编号
     */
    void delete(Integer id);

    /**
     * 获得地块信息
     *
     * @param id 编号
     * @return 地块信息
     */
    LandDO get(Integer id);

    /**
     * 获得地块信息列表
     *
     * @param ids 编号
     * @return 地块信息列表
     */
    List<LandDO> getList(Collection<Integer> ids, String landName);

    /**
     * 获得地块信息分页
     *
     * @param pageReqVO 分页查询
     * @return 地块信息分页
     */
    PageResult<LandDO> getPage(LandPageReqVO pageReqVO);

    /**
     * 获得地块信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 地块信息列表
     */
    List<LandDO> getList(LandExportReqVO exportReqVO);

    /**
     * 更新轮廓
     *
     * @param updateReqVO
     */
    void updateOutline(LandUpdateOutlineReqVO updateReqVO);

    /**
     * 查询种植作物信息
     *
     * @return
     */
    List<RaiseCropsRespVO> queryRaiseCrops();

    /**
     * 查询作物品种
     *
     * @return
     */
    List<CropsVarietiesBaseVO> queryCropsVarieties(Integer id);

    RaiseCropsBaseVO queryRaiseCropsByCode(String crops);

    CropsVarietiesBaseVO queryCropsVarietiesByCode(Integer raiseCropsRespId, String cropsType);

    /**
     * 删除地块，清除种植计划中绑定地块
     *
     * @param id
     */
    void updatePlanByLandId(Integer id);

    /**
     * 查询地块统计
     *
     * @return
     */
    LandStatisticsVO queryLandStatistics();

    /**
     * 遥感创建地块
     *
     * @param createReqVO
     */
    void createInitLand(LandCreateReqVO createReqVO);

    List<CropsCreateReqVO> queryLandCropsByLandId(Integer id);

    List<LandRespVO> getLandList(String landName);

    List<CropsCreateReqVO> queryLandCrops();
}
