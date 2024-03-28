package com.airohit.agriculture.module.system.api.oauth2;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.airohit.agriculture.module.system.convert.auth.OAuth2TokenConvert;
import com.airohit.agriculture.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.airohit.agriculture.module.system.service.oauth2.OAuth2TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class OAuth2TokenApiImpl implements OAuth2TokenApi {

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Override
    @ApiOperation("创建访问令牌")
    public CommonResult<OAuth2AccessTokenRespDTO> createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(
                reqDTO.getUserId(), reqDTO.getUserType(), reqDTO.getClientId(), reqDTO.getScopes());
        return success(OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO));
    }

    @Override
    public CommonResult<OAuth2AccessTokenCheckRespDTO> checkAccessToken(String accessToken) {
        return success(OAuth2TokenConvert.INSTANCE.convert(oauth2TokenService.checkAccessToken(accessToken)));
    }

    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> removeAccessToken(String accessToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(accessToken);
        return success(OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO));
    }

    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> refreshAccessToken(String refreshToken, String clientId) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, clientId);
        return success(OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO));
    }

}
