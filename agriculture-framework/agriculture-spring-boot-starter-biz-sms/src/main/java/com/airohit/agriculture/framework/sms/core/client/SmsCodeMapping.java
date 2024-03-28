package com.airohit.agriculture.framework.sms.core.client;

import com.airohit.agriculture.framework.common.exception.ErrorCode;
import com.airohit.agriculture.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @author shiminghao
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
