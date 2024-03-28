package com.airohit.agriculture.module.plant.vo.taskInfo;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/6 13:42
 */
@ApiModel("管理后台 - 农事任务分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskInfoPageReqVo extends PageParam {
    private static final long serialVersionUID = 2506354266064401619L;

    @ApiModelProperty(value = "任务名称")
    private String agroName;
    @ApiModelProperty("任务类型")
    private Integer type;

    @ApiModelProperty("状态,4未完成,5已完成,0全部")
    private Integer status;

    @ApiModelProperty("计划名称")
    private String planName;

    @ApiModelProperty("地块名称")
    private String landName;

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long peasantId;
}
