package com.airohit.agriculture.module.device.service.weather;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.device.dal.dataobject.weather.DeviceDataDO;
import com.airohit.agriculture.module.device.dal.mysql.weather.DeviceDataMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 天气设备数据 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class DeviceDataServiceImpl implements DeviceDataService {

    @Resource
    private DeviceDataMapper deviceDataMapper;


    @Override
    @TenantIgnore
    public DeviceDataDO getDeviceDataDONew() {
        List<DeviceDataDO> soilDeviceDataDOS = deviceDataMapper.selectList(new LambdaQueryWrapperX<DeviceDataDO>().orderByDesc(DeviceDataDO::getCreateTime).last(" limit 1"));
        if (CollUtil.isEmpty(soilDeviceDataDOS)) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        DeviceDataDO deviceDataDO = soilDeviceDataDOS.get(0);
        deviceDataDO.setTodayRain(NumberUtil.div(BigDecimal.valueOf(Optional.ofNullable(deviceDataMapper.getRain(DateUtil.formatDate(new Date()) + " 00:00:00", DateUtil.now())).orElse(Double.valueOf("0"))), new BigDecimal("2")).setScale(2, RoundingMode.HALF_UP).doubleValue());
        deviceDataDO.setAllRain(NumberUtil.div(BigDecimal.valueOf(Optional.ofNullable(deviceDataMapper.getRain(now.getYear() + "-01-01 00:00:00", DateUtil.now())).orElse(Double.valueOf("0"))), new BigDecimal("2")).setScale(2, RoundingMode.HALF_UP).doubleValue());
        deviceDataDO.setYesterdayRain(NumberUtil.div(BigDecimal.valueOf(Optional.ofNullable(deviceDataMapper.getRain(DateUtil.formatDate(DateUtil.offsetDay(new Date(), -1)) + " 00:00:00", DateUtil.formatDate(new Date()) + " 00:00:00")).orElse(Double.valueOf("0"))), new BigDecimal("2")).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return deviceDataDO;
    }
}
