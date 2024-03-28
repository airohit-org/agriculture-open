package com.airohit.agriculture.module.content.enums;// TODO 待办：请将下面的错误码复制到 yudao-module-laboratory-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

import com.airohit.agriculture.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode BANNER_NOT_EXISTS = new ErrorCode(500, "广告信息不存在");

    ErrorCode NEWS_NOT_EXISTS = new ErrorCode(500, "新闻信息不存在");
}
