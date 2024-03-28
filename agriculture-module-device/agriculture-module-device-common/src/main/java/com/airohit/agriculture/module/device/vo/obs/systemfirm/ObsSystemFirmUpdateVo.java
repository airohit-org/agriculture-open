package com.airohit.agriculture.module.device.vo.obs.systemfirm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("厂商管理 - 厂商信息修改")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ObsSystemFirmUpdateVo extends ObsSystemFirmBaseVo {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;

}
