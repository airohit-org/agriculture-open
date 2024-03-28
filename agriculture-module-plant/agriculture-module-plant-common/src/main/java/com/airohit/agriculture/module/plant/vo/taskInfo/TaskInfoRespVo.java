package com.airohit.agriculture.module.plant.vo.taskInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/6 13:42
 */
@Data
@ApiModel("农事任务列表")
public class TaskInfoRespVo {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "任务名称")
    private String agroName;

    @ApiModelProperty(value = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "任务周期")
    private Integer taskPeriod;

    @ApiModelProperty(value = "任务类型")
    private Integer type;

    @ApiModelProperty(value = "栽培阶段")
    private String planningStage;

    @ApiModelProperty("计划名称")
    private String planName;

    @ApiModelProperty("地块名称")
    private String landName;

    @ApiModelProperty("任务状态,1未指派,2未开始,3进行中,4未完成,5已完成,6逾期")
    private Integer taskStatus;
    /**
     * 农户ID
     */
    @ApiModelProperty("农户ID")
    private Integer peasantId;
    @ApiModelProperty("任务是否提交")
    private Integer status;
    @ApiModelProperty("负责人")
    private String nickName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    @ApiModelProperty(value = "图片链接")
    private String imageUrls;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @ApiModelProperty("计划阶段")
    private String parentStageName;
    @ApiModelProperty("地址")
    private String address;
}
