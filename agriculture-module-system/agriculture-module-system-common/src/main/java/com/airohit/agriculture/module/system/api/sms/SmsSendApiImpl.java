package com.airohit.agriculture.module.system.api.sms;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import com.airohit.agriculture.module.system.service.sms.SmsSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class SmsSendApiImpl implements SmsSendApi {

    @Resource
    private SmsSendService smsSendService;

    @Override
    public CommonResult<Long> sendSingleSmsToAdmin(SmsSendSingleToUserReqDTO reqDTO) {
        return success(smsSendService.sendSingleSmsToAdmin(reqDTO.getMobile(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

    @Override
    public CommonResult<Long> sendSingleSmsToMember(SmsSendSingleToUserReqDTO reqDTO) {
        return success(smsSendService.sendSingleSmsToMember(reqDTO.getMobile(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

}
