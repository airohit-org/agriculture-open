package com.airohit.agriculture.module.device.service.obs.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.device.convert.obs.ObsSystemFirmConvert;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsSystemFirmDO;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsSystemFirmMapper;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceFirmService;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceService;
import com.airohit.agriculture.module.device.service.obs.ObsSystemFirmService;
import com.airohit.agriculture.module.device.vo.obs.ObsSystemFirmVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmCreateVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmPageVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmUpdateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.FIRM_EXISTS;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.FIRM_NOT_EXISTS;

/**
 * @Author: hanliyao
 * @Date: 2023-7-19 09:23:38
 */
@Service
@Slf4j
public class ObsSystemFirmServiceImpl implements ObsSystemFirmService {

    @Resource
    private ObsSystemFirmMapper obsSystemFirmMapper;

    @Resource
    private ObsDeviceService obsDeviceService;

    @Resource
    private ObsDeviceFirmService obsDeviceFirmService;

    @Override
    @TenantIgnore
    public PageResult<ObsSystemFirmVo> getInfoPage(ObsSystemFirmPageVo obsSystemFirmPageVo) {
        PageResult<ObsSystemFirmDO> pageResult = obsSystemFirmMapper.selectPage(obsSystemFirmPageVo, new LambdaQueryWrapperX<ObsSystemFirmDO>()
                .likeIfPresent(ObsSystemFirmDO::getFirmName, obsSystemFirmPageVo.getFirmName()));
        return ObsSystemFirmConvert.INSTANCE.convertPage(pageResult);
    }

    @Override
    @TenantIgnore
    public ObsSystemFirmVo getOne(Integer id) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(id);
        return ObsSystemFirmConvert.INSTANCE.convert(obsSystemFirmDO);
    }

    @Override
    @TenantIgnore
    public Integer createInfo(ObsSystemFirmCreateVo obsSystemFirmCreateVo) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>().
                eq(ObsSystemFirmDO::getFirmName, obsSystemFirmCreateVo.getFirmName()));
        if (ObjectUtil.isNotEmpty(obsSystemFirmDO)) {
            throw exception(FIRM_EXISTS);
        }
        obsSystemFirmDO = new ObsSystemFirmDO();
        BeanUtil.copyProperties(obsSystemFirmCreateVo, obsSystemFirmDO);
        obsSystemFirmMapper.insert(obsSystemFirmDO);
        return obsSystemFirmDO.getId();
    }

    @Override
    @TenantIgnore
    public void updateInfo(ObsSystemFirmUpdateVo obsSystemFirmUpdateVo) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>().
                eq(ObsSystemFirmDO::getFirmName, obsSystemFirmUpdateVo.getFirmName()).neIfPresent(ObsSystemFirmDO::getId, obsSystemFirmUpdateVo.getId()));
        if (ObjectUtil.isNotEmpty(obsSystemFirmDO)) {
            throw exception(FIRM_EXISTS);
        }

        obsSystemFirmDO = obsSystemFirmMapper.selectById(obsSystemFirmUpdateVo.getId());
        if (ObjectUtil.isEmpty(obsSystemFirmDO)) {
            throw exception(FIRM_NOT_EXISTS);
        }

        BeanUtil.copyProperties(obsSystemFirmUpdateVo, obsSystemFirmDO);
        obsSystemFirmMapper.updateById(obsSystemFirmDO);
    }

    @Override
    @TenantIgnore
    @Transactional
    public void deleteInfo(Integer id) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(id);
        if (ObjectUtil.isEmpty(obsSystemFirmDO)) {
            throw exception(FIRM_NOT_EXISTS);
        }
        obsDeviceService.deleteByFirmId(id);

        obsDeviceFirmService.deleteByFirmId(id);

        obsSystemFirmMapper.deleteById(id);
    }
}
