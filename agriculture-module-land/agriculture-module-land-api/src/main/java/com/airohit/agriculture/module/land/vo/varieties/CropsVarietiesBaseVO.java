package com.airohit.agriculture.module.land.vo.varieties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 品种管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CropsVarietiesBaseVO {
    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "作物品种名称")
    private String cropsVarietiesName;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "国家审定编号")
    private String countryAuthCode;

    @ApiModelProperty(value = "适应区域")
    private String adaptationZone;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

    /**
     * 数据code
     */
    @ApiModelProperty(value = "数据code", hidden = true)
    private String dataCode;

}
