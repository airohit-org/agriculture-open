package com.airohit.agriculture.module.plant.vo.classification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 病虫害识别 Excel 导出 Request VO", description = "参数和 ClassificationPageReqVO 是一致的")
@Data
public class ClassificationExportReqVO {

    @ApiModelProperty(value = "病虫害名称")
    private String diseasesName;

    @ApiModelProperty(value = "图片链接")
    private String imageUrl;

    @ApiModelProperty(value = "作物类型,0水稻")
    private String cropType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "农户ID")
    private Integer peasantId;

    @ApiModelProperty(value = "农场编号")
    private Long farmTenantId;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "地块ID")
    private Integer landId;

    @ApiModelProperty(value = "经纬度")
    private String latitudeLongitude;

    @ApiModelProperty(value = "措施ID")
    private Integer measureId;

    @ApiModelProperty(value = "几率")
    private String odds;

}
