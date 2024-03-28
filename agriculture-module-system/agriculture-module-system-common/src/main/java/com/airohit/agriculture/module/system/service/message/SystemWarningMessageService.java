package com.airohit.agriculture.module.system.service.message;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.message.dto.WarningMessageCreateReqVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessagePageReqVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageRespVO;

import javax.validation.Valid;

/**
 * 预警消息 Service 接口
 *
 * @author 史铭浩
 */
public interface SystemWarningMessageService {

    /**
     * 创建预警消息
     *
     * @param createReqVO 创建信息
     */
    void createMessage(@Valid WarningMessageCreateReqVO createReqVO);

    /**
     * 获得预警消息
     *
     * @param id 编号
     * @return 预警消息
     */
    WarningMessageRespVO getMessage(Integer id);


    /**
     * 获得预警消息分页
     *
     * @param pageReqVO 分页查询
     * @return 预警消息分页
     */
    PageResult<WarningMessageRespVO> getMessagePage(WarningMessagePageReqVO pageReqVO);

    /**
     * 获得预警消息数量
     *
     * @param messageStatus 是否已读,1是0否
     * @return 预警消息分页
     */
    Integer getMessageCount(Integer messageStatus);


    /**
     * 读取所有消息
     */
    void readAllMessage();

}
