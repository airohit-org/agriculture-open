package com.airohit.agriculture.module.system.api.sms;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.sms.dto.code.SmsCodeCheckReqDTO;
import com.airohit.agriculture.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import com.airohit.agriculture.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import com.airohit.agriculture.module.system.service.sms.SmsCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class SmsCodeApiImpl implements SmsCodeApi {

    @Resource
    private SmsCodeService smsCodeService;

    @Override
    public CommonResult<Boolean> sendSmsCode(SmsCodeSendReqDTO reqDTO) {
        smsCodeService.sendSmsCode(reqDTO);
        return success(true);
    }

    @Override
    public CommonResult<Boolean> useSmsCode(SmsCodeUseReqDTO reqDTO) {
        smsCodeService.useSmsCode(reqDTO);
        return success(true);
    }

    @Override
    public CommonResult<Boolean> checkSmsCode(SmsCodeCheckReqDTO reqDTO) {
        smsCodeService.checkSmsCode(reqDTO);
        return success(true);
    }

}
