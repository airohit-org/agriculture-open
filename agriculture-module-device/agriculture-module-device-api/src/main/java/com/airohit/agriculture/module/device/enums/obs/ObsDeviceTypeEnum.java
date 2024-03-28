package com.airohit.agriculture.module.device.enums.obs;

import cn.hutool.core.util.ArrayUtil;
import com.airohit.agriculture.module.device.vo.farmyun.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 14:38
 */
@Getter
@AllArgsConstructor
public enum ObsDeviceTypeEnum {

    WORM("worm", "虫情设备普通版", DeviceWormRealTimeVo.class),
    WORM_FLAGSHIP("wormFlagship", "虫情设备旗舰版", DeviceWormRealTimeVo.class),
    SPORE("spore", "孢子设备", DeviceSporeRealTimeVo.class),
    SOIL("soil", "墒情设备", DeviceSoilRealTimeVo.class),
    CAMERA("camera", "摄像头", DeviceCameraRealTimeVo.class),
    MET("met", "气象设备", DeviceMetRealTimeVo.class),
    IRRIGATION_THREE("irrigation", "智慧环控3.0设备", DeviceIrrigationThreeRealTimeVo.class),
    IRRIGATION_TWO("irrigation2", "智慧环控2.0设备", DeviceIrrigationTwoRealTimeVo.class);
    /**
     * 设备类型
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;
    /**
     * 每种类型对应的实时数据实体
     */
    private final Class<?> typeClass;

    public static ObsDeviceTypeEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }
}
