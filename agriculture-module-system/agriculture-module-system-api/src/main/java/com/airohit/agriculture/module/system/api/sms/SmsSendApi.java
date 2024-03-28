package com.airohit.agriculture.module.system.api.sms;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;

public interface SmsSendApi {

    CommonResult<Long> sendSingleSmsToAdmin(SmsSendSingleToUserReqDTO reqDTO);

    CommonResult<Long> sendSingleSmsToMember(SmsSendSingleToUserReqDTO reqDTO);

}
