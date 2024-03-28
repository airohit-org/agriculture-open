package com.airohit.agriculture.module.system.api.message.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 预警消息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class WarningMessageBaseVO {

    @ApiModelProperty(value = "预警类型")
    private Integer warningType;

    @ApiModelProperty(value = "预警消息")
    private String warningMessage;

    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "是否全局,1是0否")
    private Integer overallSituation;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;


}
