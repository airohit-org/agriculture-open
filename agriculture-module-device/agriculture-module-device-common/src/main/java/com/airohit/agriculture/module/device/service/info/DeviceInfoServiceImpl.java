package com.airohit.agriculture.module.device.service.info;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.device.convert.info.DeviceInfoConvert;
import com.airohit.agriculture.module.device.dal.dataobject.info.DeviceInfoDO;
import com.airohit.agriculture.module.device.dal.mysql.info.DeviceInfoMapper;
import com.airohit.agriculture.module.device.vo.device.DeviceDataVo;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoCreateReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoExportReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoPageReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.INFO_NOT_EXISTS;

/**
 * 设备基本信息 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
public class DeviceInfoServiceImpl implements DeviceInfoService {

    @Resource
    private DeviceInfoMapper infoMapper;

    @Override
    public String createInfo(DeviceInfoCreateReqVO createReqVO) {
        // 插入
        DeviceInfoDO info = DeviceInfoConvert.INSTANCE.convert(createReqVO);
        infoMapper.insert(info);
        // 返回
        return info.getDeviceType();
    }

    @Override
    public void createInfoByMaster(DeviceInfoCreateReqVO createReqVO) {
        TenantUtils.execute(createReqVO.getTenantId(), () -> {
            ThreadUtil.sleep(30);
            TenantUtils.executeFarm(createReqVO.getFarmTenantId(), () -> {
                DeviceInfoDO deviceInfoDO = infoMapper.selectOne(DeviceInfoDO::getDataCode, createReqVO.getDataCode());
                if (Objects.isNull(deviceInfoDO)) {
                    infoMapper.insert(DeviceInfoConvert.INSTANCE.convert(createReqVO));
                } else {
                    Integer id = deviceInfoDO.getId();
                    BeanUtil.copyProperties(createReqVO, deviceInfoDO);
                    deviceInfoDO.setId(id);
                    infoMapper.updateById(deviceInfoDO);
                }
            });
        });
    }

    @Override
    public void updateInfo(DeviceInfoUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateInfoExists(updateReqVO.getId());
        // 更新
        DeviceInfoDO updateObj = DeviceInfoConvert.INSTANCE.convert(updateReqVO);
        infoMapper.updateById(updateObj);
    }

    @Override
    public void deleteInfo(Integer id) {
        // 校验存在
        this.validateInfoExists(id);
        // 删除
        infoMapper.deleteById(id);
    }

    private void validateInfoExists(Integer id) {
        if (infoMapper.selectById(id) == null) {
            throw exception(INFO_NOT_EXISTS);
        }
    }

    @Override
    public DeviceInfoDO getInfo(Integer id) {
        return infoMapper.selectById(id);
    }

    @Override
    public List<DeviceInfoDO> getInfoList(Collection<Integer> ids) {
        return infoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DeviceInfoDO> getInfoPage(DeviceInfoPageReqVO pageReqVO) {
        return infoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeviceInfoDO> getInfoList(DeviceInfoExportReqVO exportReqVO) {
        return infoMapper.selectList(exportReqVO);
    }

    @Override
    @TenantIgnore
    public DeviceDataVo getDeviceDataVo() {
        return infoMapper.getDeviceDataVo();
    }

}
