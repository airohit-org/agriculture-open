package com.airohit.agriculture.module.system.api.logger;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.logger.dto.LoginLogCreateReqDTO;

public interface LoginLogApi {


    CommonResult<Boolean> createLoginLog(LoginLogCreateReqDTO reqDTO);

}
