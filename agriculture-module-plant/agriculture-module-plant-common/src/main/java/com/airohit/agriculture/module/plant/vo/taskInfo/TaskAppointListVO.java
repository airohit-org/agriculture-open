package com.airohit.agriculture.module.plant.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/6 14:23
 */
@Data
@ApiModel("批量指派")
public class TaskAppointListVO {
    @ApiModelProperty("任务ID")
    private List<Integer> id;
    @ApiModelProperty("负责人ID")
    private Integer peasantId;
}
