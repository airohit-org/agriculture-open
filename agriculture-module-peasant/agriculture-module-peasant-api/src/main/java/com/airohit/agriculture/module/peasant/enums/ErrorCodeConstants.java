package com.airohit.agriculture.module.peasant.enums;


import com.airohit.agriculture.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // TODO 待办：请将下面的错误码复制到 agriculture-module-peasant-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！
    // ========== 农户 TODO 补充编号 ==========
    //ErrorCode LAND_NOT_EXISTS = new ErrorCode(500, "农户信息不存在");

    ErrorCode PEASANT_NOT_EXISTS = new ErrorCode(500, "农户信息不存在");

    ErrorCode PEASANT_NOT_PHONE = new ErrorCode(500, "Excel手机号 空");

    ErrorCode PEASANT_NOT_PHONE_ONE = new ErrorCode(500, "Excel手机号重复");

    ErrorCode PEASANT_NOT_NAME = new ErrorCode(500, "Excel姓名不存在");

    ErrorCode PEASANT_NOT_PROVINCE = new ErrorCode(500, "Excel 省不存在");

    ErrorCode PEASANT_NOT_CITY = new ErrorCode(500, "Excel 市不存在");

    ErrorCode PEASANT_NOT_AREA = new ErrorCode(500, "Excel区县不存在");


    ErrorCode PEASANT_ERROR_PROVINCE = new ErrorCode(500, "Excel省格式不正确");

    ErrorCode PEASANT_ERROR_CITY = new ErrorCode(500, "Excel市格式不正确");

    ErrorCode PEASANT_ERROR_AREA = new ErrorCode(500, "Excel区县格式不正确");

    ErrorCode PEASANT_EXISTS = new ErrorCode(500, "手机号存在 不能重复添加");


}
