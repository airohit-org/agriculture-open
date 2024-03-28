package com.airohit.agriculture.module.system.entity.app.message;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 预警消息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WarningMessagePageReqVO extends PageParam {


    private Long userId;

    private Long tenantId;
    @ApiModelProperty("类型")
    private Integer warningType;

}
