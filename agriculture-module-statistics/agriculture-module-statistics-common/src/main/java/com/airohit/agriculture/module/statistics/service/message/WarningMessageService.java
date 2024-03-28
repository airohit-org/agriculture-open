package com.airohit.agriculture.module.statistics.service.message;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.statistics.dal.dataobject.message.WarningMessageDO;
import com.airohit.agriculture.module.statistics.vo.message.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 预警消息 Service 接口
 *
 * @author 史铭浩
 */
public interface WarningMessageService {

    /**
     * 创建预警消息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createMessage(@Valid WarningMessageCreateReqVO createReqVO);

    /**
     * 更新预警消息
     *
     * @param updateReqVO 更新信息
     */
    void updateMessage(@Valid WarningMessageUpdateReqVO updateReqVO);

    /**
     * 删除预警消息
     *
     * @param id 编号
     */
    void deleteMessage(Integer id);

    /**
     * 获得预警消息
     *
     * @param id 编号
     * @return 预警消息
     */
    WarningMessageDO getMessage(Integer id);

    /**
     * 获得预警消息列表
     *
     * @param ids 编号
     * @return 预警消息列表
     */
    List<WarningMessageDO> getMessageList(Collection<Integer> ids);

    /**
     * 获得预警消息分页
     *
     * @param pageReqVO 分页查询
     * @return 预警消息分页
     */
    PageResult<WarningMessageDO> getMessagePage(WarningMessagePageReqVO pageReqVO);

    /**
     * 获得预警消息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 预警消息列表
     */
    List<WarningMessageDO> getMessageList(WarningMessageExportReqVO exportReqVO);

    /**
     * 查找预警消息列表
     *
     * @param limit
     * @return
     */
    List<MessageStatisticVo> getMessageStatisticVoList(Integer limit);

}
