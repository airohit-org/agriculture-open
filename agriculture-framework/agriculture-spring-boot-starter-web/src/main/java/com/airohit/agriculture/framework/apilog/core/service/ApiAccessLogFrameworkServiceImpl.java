package com.airohit.agriculture.framework.apilog.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.airohit.agriculture.module.infra.api.logger.ApiAccessLogApi;
import com.airohit.agriculture.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 访问日志 Framework Service 实现类
 * <p>
 * 基于 {@link ApiAccessLogApi} 远程服务，记录访问日志
 *
 * @author shiminghao
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {

    private final ApiAccessLogApi apiAccessLogApi;

    @Override
    @Async
    public void createApiAccessLog(ApiAccessLog apiAccessLog) {
        ApiAccessLogCreateReqDTO reqDTO = BeanUtil.copyProperties(apiAccessLog, ApiAccessLogCreateReqDTO.class);
        apiAccessLogApi.createApiAccessLog(reqDTO).checkError();
    }

}
