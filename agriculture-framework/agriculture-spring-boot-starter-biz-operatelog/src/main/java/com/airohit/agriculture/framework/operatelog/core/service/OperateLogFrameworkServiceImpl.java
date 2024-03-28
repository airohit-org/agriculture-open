package com.airohit.agriculture.framework.operatelog.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.airohit.agriculture.module.system.api.logger.OperateLogApi;
import com.airohit.agriculture.module.system.api.logger.dto.OperateLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 * <p>
 * 基于 {@link OperateLogApi} 远程服务，记录操作日志
 *
 * @author shiminghao
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        OperateLogCreateReqDTO reqDTO = BeanUtil.copyProperties(operateLog, OperateLogCreateReqDTO.class);
        operateLogApi.createOperateLog(reqDTO).checkError();
    }

}
