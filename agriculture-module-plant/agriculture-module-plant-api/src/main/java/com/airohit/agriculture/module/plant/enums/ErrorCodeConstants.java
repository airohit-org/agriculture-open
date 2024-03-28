package com.airohit.agriculture.module.plant.enums;// TODO 待办：请将下面的错误码复制到 yudao-module-laboratory-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

import com.airohit.agriculture.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    // ========== 种植管理 TODO 补充编号 ==========
    ErrorCode PLAN_TEMPLATE_NOT_EXISTS = new ErrorCode(500, "计划模版信息不存在");

    ErrorCode PLAN_NOT_EXISTS = new ErrorCode(500, "计划信息不存在");
    ErrorCode APPOINT_ERROR = new ErrorCode(500, "任务已不能指派");

    ErrorCode PLAN_TYPE_DATA_NOT_EXISTS = new ErrorCode(500, "计划类型信息不存在");
    ErrorCode PERIOD_ERROR = new ErrorCode(500, "任务周期不可超过本阶段周期");
    ErrorCode TASK_INFO_ERROR = new ErrorCode(500, "此任务不可提交");

    ErrorCode RECORDS_NOT_EXISTS = new ErrorCode(500, "农事记录不存在");

    ErrorCode PREVENTION_NOT_EXISTS = new ErrorCode(500, "防治方案不存在");

    ErrorCode CLASSIFICATION_NOT_EXISTS = new ErrorCode(500, "病虫害识别不存在");

}