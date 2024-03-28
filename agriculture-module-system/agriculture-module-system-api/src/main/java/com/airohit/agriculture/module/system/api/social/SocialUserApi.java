package com.airohit.agriculture.module.system.api.social;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.social.dto.SocialUserBindReqDTO;
import com.airohit.agriculture.module.system.api.social.dto.SocialUserUnbindReqDTO;

public interface SocialUserApi {

    CommonResult<String> getAuthorizeUrl(Integer type, String redirectUri);

    CommonResult<Boolean> bindSocialUser(SocialUserBindReqDTO reqDTO);

    CommonResult<Boolean> unbindSocialUser(SocialUserUnbindReqDTO reqDTO);

    CommonResult<Long> getBindUserId(Integer userType, Integer type,
                                     String code, String state);

}
