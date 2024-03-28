package com.airohit.agriculture.module.system.api.sms;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.sms.dto.code.SmsCodeCheckReqDTO;
import com.airohit.agriculture.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import com.airohit.agriculture.module.system.api.sms.dto.code.SmsCodeUseReqDTO;

// TODO shiminghao：fallbackFactory =
public interface SmsCodeApi {


    CommonResult<Boolean> sendSmsCode(SmsCodeSendReqDTO reqDTO);

    CommonResult<Boolean> useSmsCode(SmsCodeUseReqDTO reqDTO);

    CommonResult<Boolean> checkSmsCode(SmsCodeCheckReqDTO reqDTO);

}
