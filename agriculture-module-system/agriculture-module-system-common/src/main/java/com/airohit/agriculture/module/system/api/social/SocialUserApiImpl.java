package com.airohit.agriculture.module.system.api.social;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.social.dto.SocialUserBindReqDTO;
import com.airohit.agriculture.module.system.api.social.dto.SocialUserUnbindReqDTO;
import com.airohit.agriculture.module.system.service.social.SocialUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class SocialUserApiImpl implements SocialUserApi {

    @Resource
    private SocialUserService socialUserService;

    @Override
    public CommonResult<String> getAuthorizeUrl(Integer type, String redirectUri) {
        return success(socialUserService.getAuthorizeUrl(type, redirectUri));
    }

    @Override
    public CommonResult<Boolean> bindSocialUser(SocialUserBindReqDTO reqDTO) {
        socialUserService.bindSocialUser(reqDTO);
        return success(true);
    }

    @Override
    public CommonResult<Boolean> unbindSocialUser(SocialUserUnbindReqDTO reqDTO) {
        socialUserService.unbindSocialUser(reqDTO.getUserId(), reqDTO.getUserType(),
                reqDTO.getType(), reqDTO.getUnionId());
        return success(true);
    }

    @Override
    public CommonResult<Long> getBindUserId(Integer userType, Integer type, String code, String state) {
        return success(socialUserService.getBindUserId(userType, type, code, state));
    }

}
