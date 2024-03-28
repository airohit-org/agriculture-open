package com.airohit.agriculture.module.device.dal.mysql.info;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.device.dal.dataobject.info.DeviceInfoDO;
import com.airohit.agriculture.module.device.vo.device.DeviceDataVo;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoExportReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备基本信息 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface DeviceInfoMapper extends BaseMapperX<DeviceInfoDO> {

    default PageResult<DeviceInfoDO> selectPage(DeviceInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceInfoDO>()
                .likeIfPresent(DeviceInfoDO::getDeviceName, reqVO.getDeviceName())
                .likeIfPresent(DeviceInfoDO::getDeviceCompany, reqVO.getDeviceCompany())
                .orderByDesc(DeviceInfoDO::getCreateTime));
    }

    default List<DeviceInfoDO> selectList(DeviceInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeviceInfoDO>()
                .orderByDesc(DeviceInfoDO::getCreateTime));
    }

    /**
     * 查找绿农数据
     *
     * @return
     */
    DeviceDataVo getDeviceDataVo();

}
