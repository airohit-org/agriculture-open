package com.airohit.agriculture.module.plant.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/6/8 08:15
 */
@Data
@ApiModel("农事任务基本信息修改")
public class TaskInfoUpdateVO {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    @ApiModelProperty(value = "图片链接")
    private String imageUrls;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date operateTime;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;
    /**
     * 经纬度
     */
    @ApiModelProperty("经纬度")
    private String latitudeLongitude;
}
