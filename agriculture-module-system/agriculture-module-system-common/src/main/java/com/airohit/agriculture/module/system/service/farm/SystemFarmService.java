package com.airohit.agriculture.module.system.service.farm;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmExportReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmPageReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmUpdateReqVO;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


/**
 * 农场 Service 接口
 *
 * @author shiminghao
 */
public interface SystemFarmService {

    /**
     * 创建农场
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer create(@Valid FarmCreateReqVO createReqVO);

    /**
     * 更新农场
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid FarmUpdateReqVO updateReqVO);

    /**
     * 删除农场
     *
     * @param id 编号
     */
    void delete(Integer id);

    /**
     * 获得农场
     *
     * @param id 编号
     * @return 农场
     */
    FarmDO get(Integer id);

    /**
     * 获得农场
     *
     * @param tenantId 编号
     * @return 农场
     */
    FarmDO getFarmByTenant(Long tenantId);

    /**
     * 获得农场
     *
     * @param tenantId 编号
     * @return 农场
     */
    List<FarmDO> getFarmListByTenant(Long tenantId);

    /**
     * 获得农场列表
     *
     * @param ids 编号
     * @return 农场列表
     */
    List<FarmDO> getList(Collection<Integer> ids);

    /**
     * 获得农场分页
     *
     * @param pageReqVO 分页查询
     * @return 农场分页
     */
    PageResult<FarmDO> getPage(FarmPageReqVO pageReqVO);

    /**
     * 获得农场列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 农场列表
     */
    List<FarmDO> getList(FarmExportReqVO exportReqVO);


    /**
     * 查找所有没关联租户的农场
     *
     * @return 农场列表
     */
    List<FarmDO> getFarmList();

    /**
     * 查找所有农场
     *
     * @return 农场列表
     */
    List<FarmDO> getAllFarmList();

    List<FarmDO> likeName(String farmName);
}
