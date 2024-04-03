package com.airohit.agriculture.module.device.service.obs.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.device.convert.obs.ObsDeviceFirmConvert;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceFirmDO;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsSystemFirmDO;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsDeviceFirmMapper;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsSystemFirmMapper;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmCreateDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmPageDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmUpdateDto;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceFirmService;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceService;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceFirmVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.FIRM_EXISTS;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.FIRM_NOT_EXISTS;


/**
 * @Description:
 * @return:
 * @Author: hanliyao
 * @Date: 2023/7/13 14:58
 */
@Service
@Slf4j
public class ObsDeviceFirmServiceImpl implements ObsDeviceFirmService {
    @Resource
    private ObsSystemFirmMapper obsSystemFirmMapper;

    @Resource
    private ObsDeviceFirmMapper obsDeviceFirmMapper;

    @Resource
    private ObsDeviceService obsDeviceService;

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public CommonResult<Map<Integer, String>> getFirm() {
        Map<Integer, String> result = new HashMap<>();
        List<ObsSystemFirmDO> obsSystemFirmDOS = obsSystemFirmMapper.selectList();
        if (CollUtil.isNotEmpty(obsSystemFirmDOS))
            obsSystemFirmDOS.forEach(obsSystemFirmDO -> {
                result.put(obsSystemFirmDO.getId(), obsSystemFirmDO.getFirmName());
            });
        return success(result);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public CommonResult<PageResult<ObsDeviceFirmVo>> getInfoPage(ObsDeviceFirmPageDto pageVO) {
        List<Integer> idList = null;
        PageResult<ObsDeviceFirmVo> voPageResult = new PageResult<>();
        if (StrUtil.isNotEmpty(pageVO.getFirmName())) {
            List<ObsSystemFirmDO> obsSystemFirmDOS = obsSystemFirmMapper.selectList(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                    .likeIfPresent(ObsSystemFirmDO::getFirmName, pageVO.getFirmName()));
            if (CollUtil.isNotEmpty(obsSystemFirmDOS)) {
                idList = CollUtil.getFieldValues(obsSystemFirmDOS, "id", Integer.class);
            } else {
                return success(voPageResult);
            }
        }
        PageResult<ObsDeviceFirmDO> pageResult = obsDeviceFirmMapper.selectPage(pageVO, new LambdaQueryWrapperX<ObsDeviceFirmDO>()
                .eqIfPresent(ObsDeviceFirmDO::getFarmId, pageVO.getFarmId())
                .inIfPresent(ObsDeviceFirmDO::getFirmId, idList).orderByDesc(BaseDO::getCreateTime));


        voPageResult = ObsDeviceFirmConvert.INSTANCE.convertPage(pageResult);
        if (CollUtil.isNotEmpty(voPageResult.getList()))
            voPageResult.getList().forEach(obsDeviceFirmVo -> {
                ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(obsDeviceFirmVo.getFirmId());
                if (ObjectUtil.isNotEmpty(obsSystemFirmDO))
                    obsDeviceFirmVo.setFirmName(obsSystemFirmDO.getFirmName());
            });
        return success(voPageResult);
    }

    @Override
    @TenantIgnore
    public CommonResult<Integer> createInfo(ObsDeviceFirmCreateDto obsDeviceFirmCreateDto) {
        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceFirmDO>().eq(ObsDeviceFirmDO::getFirmId, obsDeviceFirmCreateDto.getFirmId())
                .eq(ObsDeviceFirmDO::getFarmId, obsDeviceFirmCreateDto.getFarmId()).eqIfPresent(ObsDeviceFirmDO::getLoginName, obsDeviceFirmCreateDto.getLoginName()));
        if (ObjectUtil.isNotEmpty(obsDeviceFirmDO)) {
            throw exception(FIRM_EXISTS);
        }
        obsDeviceFirmDO = new ObsDeviceFirmDO();
        BeanUtil.copyProperties(obsDeviceFirmCreateDto, obsDeviceFirmDO);
        obsDeviceFirmMapper.insert(obsDeviceFirmDO);
        return success(obsDeviceFirmDO.getId());
    }

    @Override
    @TenantIgnore
    public CommonResult<Boolean> updateInfo(ObsDeviceFirmUpdateDto updateDto) {
        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceFirmDO>().eq(ObsDeviceFirmDO::getFirmId, updateDto.getFirmId())
                .eq(ObsDeviceFirmDO::getFarmId, updateDto.getFarmId()).eqIfPresent(ObsDeviceFirmDO::getLoginName, updateDto.getLoginName())
                .neIfPresent(ObsDeviceFirmDO::getId, updateDto.getId()));

        if (ObjectUtil.isNotEmpty(obsDeviceFirmDO)) {
            throw exception(FIRM_EXISTS);
        }

        obsDeviceFirmDO = obsDeviceFirmMapper.selectById(updateDto.getId());
        if (ObjectUtil.isEmpty(obsDeviceFirmDO)) {
            throw exception(FIRM_NOT_EXISTS);
        }

        obsDeviceFirmDO = new ObsDeviceFirmDO();
        BeanUtil.copyProperties(updateDto, obsDeviceFirmDO);
        if (StrUtil.isEmpty(updateDto.getLoginName()))
            obsDeviceFirmDO.setLoginName("");
        if (StrUtil.isEmpty(updateDto.getLoginPwd()))
            obsDeviceFirmDO.setLoginPwd("");
        obsDeviceFirmMapper.updateById(obsDeviceFirmDO);
        return success(true);
    }

    @Override
    @TenantIgnore
    @Transactional
    @FarmTenantIgnore
    public CommonResult<Boolean> deleteInfo(Integer id) {
        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectById(id);
        if (ObjectUtil.isEmpty(obsDeviceFirmDO)) {
            throw exception(FIRM_NOT_EXISTS);
        }
        obsDeviceService.deleteByFarmFirmId(id);

        obsDeviceFirmMapper.deleteById(id);
        return success(true);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public CommonResult<ObsDeviceFirmVo> getOne(Integer id) {
        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectById(id);
        ObsDeviceFirmVo deviceFirmVo = ObsDeviceFirmConvert.INSTANCE.convert(obsDeviceFirmDO);
        if (ObjectUtil.isNotEmpty(deviceFirmVo)) {
            ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(deviceFirmVo.getFirmId());
            if (ObjectUtil.isNotEmpty(obsSystemFirmDO))
                deviceFirmVo.setFirmName(obsSystemFirmDO.getFirmName());
        }
        return success(deviceFirmVo);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public String getFirmName(Integer firmId) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(firmId);
        return obsSystemFirmDO.getFirmName();
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void deleteByFirmId(Integer id) {
        obsDeviceFirmMapper.delete(new LambdaQueryWrapperX<ObsDeviceFirmDO>()
                .eq(ObsDeviceFirmDO::getFirmId, id));
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public CommonResult<Boolean> init(Integer id) {
        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectById(id);
        obsDeviceService.init(obsDeviceFirmDO);
        return success(true);
    }
}
