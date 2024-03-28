package com.airohit.agriculture.module.plant.enums;


import com.airohit.agriculture.framework.common.enums.RpcConstants;

/**
 * API 相关的枚举
 *
 * @author 芋道源码
 */
public class ApiConstants {

    /**
     * 服务名
     * <p>
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "plant-server";
    public static final String PLANT_SLAVE_NAME = "plant-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX + "/plant";

    public static final String VERSION = "1.0.0";

}
