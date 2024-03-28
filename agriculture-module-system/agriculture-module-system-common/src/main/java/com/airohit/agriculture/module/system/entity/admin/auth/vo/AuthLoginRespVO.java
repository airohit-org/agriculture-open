package com.airohit.agriculture.module.system.entity.admin.auth.vo;

import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginRespVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    private Long userId;

    @ApiModelProperty(value = "访问令牌", required = true, example = "happy")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true, example = "nice")
    private String refreshToken;

    @ApiModelProperty(value = "过期时间", required = true)
    private LocalDateTime expiresTime;

    @ApiModelProperty(value = "租户ID", required = true)
    private Long tenantId;

    @ApiModelProperty(value = "用户登录IP", required = true)
    private String userIP;

    /**
     * 可以选择的农场
     */
    private List<FarmDO> farmList;

}
