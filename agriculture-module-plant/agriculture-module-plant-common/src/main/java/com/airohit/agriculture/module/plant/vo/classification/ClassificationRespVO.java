package com.airohit.agriculture.module.plant.vo.classification;

import com.airohit.agriculture.module.plant.dal.dataobject.prevention.PreventionDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 病虫害识别 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClassificationRespVO extends ClassificationBaseVO {

    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    @ApiModelProperty("防治措施和方案")
    private PreventionDO preventionDO;

    @ApiModelProperty("地块名称")
    private String landName;
    @ApiModelProperty(value = "是否为pc,1是0否")
    private Integer isPc;
}
