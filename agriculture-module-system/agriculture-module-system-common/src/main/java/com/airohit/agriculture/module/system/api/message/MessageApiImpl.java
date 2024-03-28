package com.airohit.agriculture.module.system.api.message;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.message.dto.WarningMessageCreateReqVO;
import com.airohit.agriculture.module.system.service.message.SystemWarningMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/6/9 09:55
 */
@Service
public class MessageApiImpl implements MessageApi {
    @Resource
    private SystemWarningMessageService systemWarningMessageService;

    @Override
    public CommonResult<Boolean> create(WarningMessageCreateReqVO create) {
        systemWarningMessageService.createMessage(create);
        return CommonResult.success(Boolean.TRUE);
    }
}
