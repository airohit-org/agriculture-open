package com.airohit.agriculture.module.land.vo.temperaturezone;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积温带管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TemperatureZoneBaseVO {

    @ApiModelProperty(value = "积温带名称")
    private String name;

    @ApiModelProperty(value = "温度")
    private String temperature;

    @ApiModelProperty(value = "温度表现")
    private String temperatureBehaviour;
    @ApiModelProperty(value = "Id")
    private Integer id;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

    @ApiModelProperty(value = "数据code", hidden = true)
    private String dataCode;
    @ApiModelProperty(value = "区域")
    private String region;

}
