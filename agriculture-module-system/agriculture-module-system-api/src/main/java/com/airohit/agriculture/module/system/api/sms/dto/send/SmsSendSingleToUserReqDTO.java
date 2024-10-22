package com.airohit.agriculture.module.system.api.sms.dto.send;

import com.airohit.agriculture.framework.common.validation.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@ApiModel("RPC 服务 - 短信发送给 Admin 或者 Member 用户 Request DTO")
@Data
public class SmsSendSingleToUserReqDTO {

    @ApiModelProperty(value = "用户编号", example = "1024")
    private Long userId;
    @ApiModelProperty(value = "手机号", required = true, example = "15601691300")
    @Mobile
    private String mobile;
    @ApiModelProperty(value = "用户编号", required = true, example = "USER_SEND")
    @NotEmpty(message = "短信模板编号不能为空")
    private String templateCode;
    @ApiModelProperty(value = "短信模板参数")
    private Map<String, Object> templateParams;

}
