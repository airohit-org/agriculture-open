package com.airohit.agriculture.module.land.enums;

import com.airohit.agriculture.framework.common.enums.RpcConstants;

/**
 * API 相关的枚举
 *
 * @author shiminghao
 */
public class ApiConstants {

    /**
     * 服务名
     * <p>
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String SYSTEM_SLAVE_NAME = "system-server";
    public static final String LAND_SLAVE_NAME = "land-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX + "/land";

    public static final String VERSION = "1.0.0";

}
