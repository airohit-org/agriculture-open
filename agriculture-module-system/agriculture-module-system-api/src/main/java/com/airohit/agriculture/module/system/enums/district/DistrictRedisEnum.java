package com.airohit.agriculture.module.system.enums.district;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum DistrictRedisEnum {

    PROVINCE("province_redis_cache"),
    AREA("area_redis_cache"),
    CITY("city_redis_cache"),
    ;

    /**
     * 名字
     */
    private final String name;


}
