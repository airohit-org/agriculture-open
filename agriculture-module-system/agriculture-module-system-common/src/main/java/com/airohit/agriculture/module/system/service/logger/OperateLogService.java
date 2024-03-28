package com.airohit.agriculture.module.system.service.logger;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.airohit.agriculture.module.system.dal.dataobject.logger.OperateLogDO;
import com.airohit.agriculture.module.system.entity.admin.logger.vo.operatelog.OperateLogExportReqVO;
import com.airohit.agriculture.module.system.entity.admin.logger.vo.operatelog.OperateLogPageReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 操作日志 Service 接口
 *
 * @author shiminghao
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    void createOperateLog(@Valid OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param reqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO reqVO);

    /**
     * 获得操作日志列表
     *
     * @param reqVO 列表条件
     * @return 日志列表
     */
    List<OperateLogDO> getOperateLogs(OperateLogExportReqVO reqVO);

}
