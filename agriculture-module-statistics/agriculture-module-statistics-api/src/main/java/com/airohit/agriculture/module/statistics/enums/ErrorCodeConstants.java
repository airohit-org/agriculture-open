package com.airohit.agriculture.module.statistics.enums;// TODO 待办：请将下面的错误码复制到 yudao-module-laboratory-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

import cn.hutool.core.io.FileUtil;
import com.airohit.agriculture.framework.common.exception.ErrorCode;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public interface ErrorCodeConstants {
    // ========== 种植管理 TODO 补充编号 ==========
    ErrorCode PLAN_TEMPLATE_NOT_EXISTS = new ErrorCode(500, "计划模版信息不存在");

    ErrorCode PLAN_NOT_EXISTS = new ErrorCode(500, "计划信息不存在");
    ErrorCode APPOINT_ERROR = new ErrorCode(500, "任务已开始不能指派");

    ErrorCode PLAN_TYPE_DATA_NOT_EXISTS = new ErrorCode(500, "计划类型信息不存在");
    ErrorCode PERIOD_ERROR = new ErrorCode(500, "任务周期不可超过本阶段周期");

    ErrorCode MESSAGE_NOT_EXISTS = new ErrorCode(500, "预警消息不存在");


    public static void main(String[] args) {
        String directoryPath = "/Users/minghaoshi/agriculture-cloud"; // 需要获取的目录路径

        List<File> files = FileUtil.loopFiles(directoryPath);
        List<File> collect = files.stream().filter(file -> file.getName().endsWith(".java")).collect(Collectors.toList());
        for (File file : collect) {
            String s = FileUtil.readString(file, Charset.defaultCharset());
            FileUtil.appendString(s, new File("/Users/minghaoshi/Desktop/1.txt"), Charset.defaultCharset());
        }

    }

}