package com.airohit.agriculture.module.plant.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/6 13:10
 */
@Data
@ApiModel("指派")
public class TaskAppointVO {
    @ApiModelProperty("任务ID")
    private Integer id;
    @ApiModelProperty("负责人ID")
    private Integer peasantId;
}
