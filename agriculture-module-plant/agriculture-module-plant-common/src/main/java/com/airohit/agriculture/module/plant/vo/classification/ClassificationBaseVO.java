package com.airohit.agriculture.module.plant.vo.classification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 病虫害识别 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ClassificationBaseVO {

    @ApiModelProperty(value = "病虫害名称")
    private String diseasesName;

    @ApiModelProperty(value = "图片链接")
    private String imageUrl;

    @ApiModelProperty(value = "作物类型,0水稻")
    private String cropType;

    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "农户ID")
    private Integer peasantId;

    @ApiModelProperty(value = "农场编号", required = true)
    private Long farmTenantId;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "地块ID")
    private Integer landId;

    @ApiModelProperty(value = "经纬度")
    private String latitudeLongitude;

    @ApiModelProperty(value = "用户经纬度")
    private String userLatitudeLongitude;

    @ApiModelProperty(value = "措施ID")
    private Integer measureId;

    @ApiModelProperty(value = "几率")
    private String odds;
    @ApiModelProperty(value = "是否保存")
    private Integer isSave;
}
