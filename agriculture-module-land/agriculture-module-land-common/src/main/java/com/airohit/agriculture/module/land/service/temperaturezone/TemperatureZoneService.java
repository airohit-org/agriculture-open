package com.airohit.agriculture.module.land.service.temperaturezone;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.temperaturezone.TemperatureZoneDO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneCreateReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneExportReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZonePageReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 积温带管理 Service 接口
 *
 * @author shiminghao
 */
public interface TemperatureZoneService {

    /**
     * 创建积温带管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createTemperatureZone(@Valid TemperatureZoneCreateReqVO createReqVO);

    /**
     * 创建积温带管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void createTemperatureZoneByMaster(@Valid TemperatureZoneCreateReqVO createReqVO);

    /**
     * 更新积温带管理
     *
     * @param updateReqVO 更新信息
     */
    void updateTemperatureZone(@Valid TemperatureZoneUpdateReqVO updateReqVO);

    /**
     * 删除积温带管理
     *
     * @param id 编号
     */
    void deleteTemperatureZone(Integer id);

    /**
     * 获得积温带管理
     *
     * @param id 编号
     * @return 积温带管理
     */
    TemperatureZoneDO getTemperatureZone(Integer id);

    /**
     * 获得积温带管理列表
     *
     * @param ids 编号
     * @return 积温带管理列表
     */
    List<TemperatureZoneDO> getTemperatureZoneList(Collection<Integer> ids);

    /**
     * 获得积温带管理分页
     *
     * @param pageReqVO 分页查询
     * @return 积温带管理分页
     */
    PageResult<TemperatureZoneDO> getTemperatureZonePage(TemperatureZonePageReqVO pageReqVO);

    /**
     * 获得积温带管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 积温带管理列表
     */
    List<TemperatureZoneDO> getTemperatureZoneList(TemperatureZoneExportReqVO exportReqVO);

}
