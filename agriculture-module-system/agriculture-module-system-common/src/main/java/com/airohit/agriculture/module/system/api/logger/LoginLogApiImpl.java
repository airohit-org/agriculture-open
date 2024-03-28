package com.airohit.agriculture.module.system.api.logger;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.airohit.agriculture.module.system.service.logger.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private LoginLogService loginLogService;

    @Override
    public CommonResult<Boolean> createLoginLog(LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
        return success(true);
    }

}
