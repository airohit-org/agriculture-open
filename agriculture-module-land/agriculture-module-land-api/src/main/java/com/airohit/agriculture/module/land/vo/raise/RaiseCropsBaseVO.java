package com.airohit.agriculture.module.land.vo.raise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 种植作物 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RaiseCropsBaseVO {
    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "作物名称")
    private String cropsName;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "作物图标")
    private String imageUrl;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

    /**
     * 数据code
     */
    @ApiModelProperty(value = "数据code", hidden = true)
    private String dataCode;

    @ApiModelProperty(value = "农场ID", hidden = true)
    private Long farmTenantId;

}
