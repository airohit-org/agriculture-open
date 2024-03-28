package com.airohit.agriculture.module.statistics.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/17 15:23
 */
@ApiModel("农事任务统计")
@Data
public class TaskInfoStatusVo {
    @ApiModelProperty("状态名称")
    private String name;
    @ApiModelProperty("数量")
    private Integer value;
}
