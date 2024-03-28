package com.airohit.agriculture.module.device.enums;// TODO 待办：请将下面的错误码复制到 yudao-module-laboratory-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

import com.airohit.agriculture.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(1000, "成功");

    ErrorCode INFO_NOT_EXISTS = new ErrorCode(500, "设备基本信息不存在");

    ErrorCode DEVICE_PORT_TOP = new ErrorCode(500, "端口使用量已达上线，请联系管理员");
    ErrorCode INFO_EXISTS = new ErrorCode(500, "设备型号已存在");
    ErrorCode FIRM_EXISTS = new ErrorCode(500, "厂商信息已存在");
    ErrorCode FIRM_NOT_EXISTS = new ErrorCode(500, "厂商信息不存在");
    ErrorCode DEVICE_CLAIM = new ErrorCode(500, "设备已被其他农场认领");
}