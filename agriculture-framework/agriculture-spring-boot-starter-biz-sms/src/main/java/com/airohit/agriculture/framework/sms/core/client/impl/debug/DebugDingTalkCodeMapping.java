package com.airohit.agriculture.framework.sms.core.client.impl.debug;

import com.airohit.agriculture.framework.common.exception.ErrorCode;
import com.airohit.agriculture.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.airohit.agriculture.framework.sms.core.client.SmsCodeMapping;
import com.airohit.agriculture.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author shiminghao
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
