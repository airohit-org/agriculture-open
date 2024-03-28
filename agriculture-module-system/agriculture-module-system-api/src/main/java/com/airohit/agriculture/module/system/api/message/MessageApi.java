package com.airohit.agriculture.module.system.api.message;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.message.dto.WarningMessageCreateReqVO;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/6/9 09:52
 */
public interface MessageApi {

    CommonResult<Boolean> create(WarningMessageCreateReqVO create);
}
