package com.airohit.agriculture.module.device.vo.obs.systemfirm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 设备基本信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ObsSystemFirmBaseVo {
    @ApiModelProperty("厂商名称")
    @NotBlank(message = "厂商名称不能为空")
    private String firmName;
}
