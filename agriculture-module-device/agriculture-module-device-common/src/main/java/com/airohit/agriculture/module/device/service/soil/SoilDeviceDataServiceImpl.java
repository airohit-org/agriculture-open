package com.airohit.agriculture.module.device.service.soil;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.redis.core.RedisLock;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.device.dal.dataobject.soil.SoilDeviceDataDO;
import com.airohit.agriculture.module.device.dal.mysql.soil.SoilDeviceDataMapper;
import com.airohit.agriculture.module.device.vo.soil.SoilTempVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/24 14:27
 */
@Service
@Slf4j
public class SoilDeviceDataServiceImpl implements SoilDeviceDataService {
    private static final String SOIL_DEVICE_DATA_LOCK = "SOIL_DEVICE_DATA_LOCK";
    @Resource
    private SoilDeviceDataMapper soilDeviceDataMapper;
    @Resource
    private RedisLock redisLock;
    @Resource
    @Lazy
    private SoilDeviceDataService service;

    @Override
    @TenantIgnore
    public void createSoilDeviceData() {
        SoilDeviceDataDO soilDeviceData = new SoilDeviceDataDO();
        int hour = DateUtil.thisHour(true);
        log.info("此时的时间------>" + hour);
        Map<Integer, SoilTempVo> soilTempVoMap = getSoilTempVoMap();
        SoilTempVo soilTempVo = soilTempVoMap.get(hour);
        if (Objects.isNull(soilTempVo)) {
            log.error("未取到---------->" + hour);
            return;
        }
        soilDeviceData.setW0(RandomUtil.randomDouble(soilTempVo.getMin().doubleValue(), soilTempVo.getMax().doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setW1(RandomUtil.randomDouble(new BigDecimal("15.00").doubleValue(), new BigDecimal("19.00").doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setW2(RandomUtil.randomDouble(new BigDecimal("16.00").doubleValue(), new BigDecimal("18.00").doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setW3(new BigDecimal("16").setScale(2, RoundingMode.HALF_UP).doubleValue());
        soilDeviceData.setS0(RandomUtil.randomDouble(new BigDecimal("40.00").doubleValue(), new BigDecimal("45.00").doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setS1(RandomUtil.randomDouble(new BigDecimal("45.00").doubleValue(), new BigDecimal("48.00").doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setS2(RandomUtil.randomDouble(new BigDecimal("48.00").doubleValue(), new BigDecimal("50.00").doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setS3(RandomUtil.randomDouble(new BigDecimal("55.00").doubleValue(), new BigDecimal("56.00").doubleValue(), 2, RoundingMode.HALF_UP));
        soilDeviceData.setPh(new BigDecimal("7.63").setScale(2, RoundingMode.HALF_UP).doubleValue());
        soilDeviceData.setEc(new BigDecimal("121.87").setScale(2, RoundingMode.HALF_UP).doubleValue());
        soilDeviceData.setDl(new BigDecimal("9.53").setScale(2, RoundingMode.HALF_UP).doubleValue());
        soilDeviceData.setLl(new BigDecimal("14.56").setScale(2, RoundingMode.HALF_UP).doubleValue());
        soilDeviceData.setJl(new BigDecimal("15.76").setScale(2, RoundingMode.HALF_UP).doubleValue());
        soilDeviceData.setCreateTime(LocalDateTime.now());
        soilDeviceDataMapper.insert(soilDeviceData);
    }

    private Map<Integer, SoilTempVo> getSoilTempVoMap() {
        Map<Integer, SoilTempVo> map = new LinkedHashMap<>();
        map.put(0, new SoilTempVo().setMax(new BigDecimal("16.5")).setMin(new BigDecimal("16")));
        map.put(1, new SoilTempVo().setMax(new BigDecimal("16.5")).setMin(new BigDecimal("16")));
        map.put(2, new SoilTempVo().setMax(new BigDecimal("16.5")).setMin(new BigDecimal("16")));
        map.put(3, new SoilTempVo().setMax(new BigDecimal("15.5")).setMin(new BigDecimal("15")));
        map.put(4, new SoilTempVo().setMax(new BigDecimal("15.5")).setMin(new BigDecimal("15")));
        map.put(5, new SoilTempVo().setMax(new BigDecimal("15")).setMin(new BigDecimal("14")));

        map.put(6, new SoilTempVo().setMin(new BigDecimal("15")).setMax(new BigDecimal("16")));
        map.put(7, new SoilTempVo().setMin(new BigDecimal("16")).setMax(new BigDecimal("17")));
        map.put(8, new SoilTempVo().setMin(new BigDecimal("17")).setMax(new BigDecimal("18")));
        map.put(9, new SoilTempVo().setMin(new BigDecimal("18")).setMax(new BigDecimal("19")));
        map.put(10, new SoilTempVo().setMin(new BigDecimal("19")).setMax(new BigDecimal("20")));
        map.put(11, new SoilTempVo().setMin(new BigDecimal("20")).setMax(new BigDecimal("21")));
        map.put(12, new SoilTempVo().setMin(new BigDecimal("21")).setMax(new BigDecimal("22")));
        map.put(13, new SoilTempVo().setMin(new BigDecimal("22")).setMax(new BigDecimal("24")));
        map.put(14, new SoilTempVo().setMin(new BigDecimal("24")).setMax(new BigDecimal("25")));

        map.put(15, new SoilTempVo().setMax(new BigDecimal("24")).setMin(new BigDecimal("23")));
        map.put(16, new SoilTempVo().setMax(new BigDecimal("23")).setMin(new BigDecimal("22")));
        map.put(17, new SoilTempVo().setMax(new BigDecimal("22")).setMin(new BigDecimal("21")));
        map.put(18, new SoilTempVo().setMax(new BigDecimal("21")).setMin(new BigDecimal("20")));
        map.put(19, new SoilTempVo().setMax(new BigDecimal("20")).setMin(new BigDecimal("19.5")));
        map.put(20, new SoilTempVo().setMax(new BigDecimal("19.5")).setMin(new BigDecimal("19")));
        map.put(21, new SoilTempVo().setMax(new BigDecimal("18.5")).setMin(new BigDecimal("18")));
        map.put(22, new SoilTempVo().setMax(new BigDecimal("18")).setMin(new BigDecimal("17.5")));
        map.put(23, new SoilTempVo().setMax(new BigDecimal("17.5")).setMin(new BigDecimal("17")));
        return map;
    }

    @Override
    @TenantIgnore
    public SoilDeviceDataDO getSoilDeviceDataDONew() {
        List<SoilDeviceDataDO> soilDeviceDataDOS = soilDeviceDataMapper.selectList(new LambdaQueryWrapperX<SoilDeviceDataDO>().orderByDesc(SoilDeviceDataDO::getCreateTime).last(" limit 1"));
        if (CollUtil.isEmpty(soilDeviceDataDOS)) {
            return null;
        }
        return soilDeviceDataDOS.get(0);
    }

//    @Scheduled(cron = "0/30 * * * * ?")
    public void messageResend() {
//        RLock couponLock = null;
//        try {
//            couponLock = redisLock.lock(SOIL_DEVICE_DATA_LOCK, 5L);
//            log.info("开始执行生成土壤数据定时任务");
//            service.createSoilDeviceData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (couponLock != null && couponLock.isLocked() && couponLock.isHeldByCurrentThread()) {
//                couponLock.unlock();
//            }
//        }
    }


}
