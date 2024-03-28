package com.airohit.agriculture.module.land.enums;// TODO 待办：请将下面的错误码复制到 yudao-module-laboratory-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

import com.airohit.agriculture.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    // ========== 实验室管理 TODO 补充编号 ==========
    ErrorCode LAND_NOT_EXISTS = new ErrorCode(500, "地块信息不存在");
    ErrorCode CROPS_NOT_EXISTS = new ErrorCode(500, "作物信息不存在");
    ErrorCode TEMPERATURE_ZONE_NOT_EXISTS = new ErrorCode(500, "积温带不存在");
}