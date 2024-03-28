package com.airohit.agriculture.module.infra.api.logger;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;

public interface ApiAccessLogApi {


    CommonResult<Boolean> createApiAccessLog(ApiAccessLogCreateReqDTO createDTO);

}
