package com.airohit.agriculture.module.infra.api.logger;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;

public interface ApiErrorLogApi {


    CommonResult<Boolean> createApiErrorLog(ApiErrorLogCreateReqDTO createDTO);

}
