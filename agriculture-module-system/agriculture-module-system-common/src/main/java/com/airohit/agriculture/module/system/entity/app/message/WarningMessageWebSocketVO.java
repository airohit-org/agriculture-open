package com.airohit.agriculture.module.system.entity.app.message;

import com.airohit.agriculture.module.system.api.message.dto.WarningMessageBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 16:23
 */
@Data
@ApiModel("预警消息WebSocket实体")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WarningMessageWebSocketVO extends WarningMessageBaseVO {
    @ApiModelProperty("租户ID")
    private Long tenantId;
}
