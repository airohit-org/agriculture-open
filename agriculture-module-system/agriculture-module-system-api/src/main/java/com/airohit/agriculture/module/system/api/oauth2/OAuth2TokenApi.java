package com.airohit.agriculture.module.system.api.oauth2;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;

// TODO shiminghao：fallbackFactory =
public interface OAuth2TokenApi {


    /**
     * 校验 Token 的 URL 地址，主要是提供给 Gateway 使用
     */
    @SuppressWarnings("HttpUrlsUsage")
    CommonResult<OAuth2AccessTokenRespDTO> createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO);

    CommonResult<OAuth2AccessTokenCheckRespDTO> checkAccessToken(String accessToken);

    CommonResult<OAuth2AccessTokenRespDTO> removeAccessToken(String accessToken);

    CommonResult<OAuth2AccessTokenRespDTO> refreshAccessToken(String refreshToken, String clientId);

}
