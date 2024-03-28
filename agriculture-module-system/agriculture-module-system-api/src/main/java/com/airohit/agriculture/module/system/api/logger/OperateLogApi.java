package com.airohit.agriculture.module.system.api.logger;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.logger.dto.OperateLogCreateReqDTO;

public interface OperateLogApi {


    CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO createReqDTO);

}
