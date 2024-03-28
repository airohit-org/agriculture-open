package com.airohit.agriculture.module.system.entity.app.message;

import com.airohit.agriculture.module.system.api.message.dto.WarningMessageBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 预警消息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WarningMessageRespVO extends WarningMessageBaseVO {

    @ApiModelProperty(value = "Id")
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否已读,1是0否")
    private Integer messageStatus;

}
