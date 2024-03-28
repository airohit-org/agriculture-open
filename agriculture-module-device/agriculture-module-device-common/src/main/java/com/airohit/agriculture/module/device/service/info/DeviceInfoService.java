package com.airohit.agriculture.module.device.service.info;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.info.DeviceInfoDO;
import com.airohit.agriculture.module.device.vo.device.DeviceDataVo;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoCreateReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoExportReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoPageReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 设备基本信息 Service 接口
 *
 * @author shiminghao
 */
public interface DeviceInfoService {

    /**
     * 创建设备基本信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createInfo(@Valid DeviceInfoCreateReqVO createReqVO);

    /**
     * 同步数据
     *
     * @param createReqVO
     */
    void createInfoByMaster(DeviceInfoCreateReqVO createReqVO);

    /**
     * 更新设备基本信息
     *
     * @param updateReqVO 更新信息
     */
    void updateInfo(@Valid DeviceInfoUpdateReqVO updateReqVO);

    /**
     * 删除设备基本信息
     *
     * @param id 编号
     */
    void deleteInfo(Integer id);

    /**
     * 获得设备基本信息
     *
     * @param id 编号
     * @return 设备基本信息
     */
    DeviceInfoDO getInfo(Integer id);

    /**
     * 获得设备基本信息列表
     *
     * @param ids 编号
     * @return 设备基本信息列表
     */
    List<DeviceInfoDO> getInfoList(Collection<Integer> ids);

    /**
     * 获得设备基本信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备基本信息分页
     */
    PageResult<DeviceInfoDO> getInfoPage(DeviceInfoPageReqVO pageReqVO);

    /**
     * 获得设备基本信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备基本信息列表
     */
    List<DeviceInfoDO> getInfoList(DeviceInfoExportReqVO exportReqVO);

    /**
     * 查找绿农数据
     *
     * @return
     */
    DeviceDataVo getDeviceDataVo();

}
