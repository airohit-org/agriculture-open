package com.airohit.agriculture.module.plant.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 除草 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
@ApiModel("除草")
public class TaskWeedBaseVO {

    @ApiModelProperty(value = "任务基本信息id")
    private Integer agroTaskId;

    @ApiModelProperty(value = "农机")
    private String farmMachinery;

}
