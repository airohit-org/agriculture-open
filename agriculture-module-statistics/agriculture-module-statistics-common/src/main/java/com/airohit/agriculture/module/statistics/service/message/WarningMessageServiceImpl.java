package com.airohit.agriculture.module.statistics.service.message;

import cn.hutool.core.bean.BeanUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.dict.core.util.DictFrameworkUtils;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.module.statistics.convert.message.WarningMessageConvert;
import com.airohit.agriculture.module.statistics.dal.dataobject.message.WarningMessageDO;
import com.airohit.agriculture.module.statistics.dal.mysql.message.WarningMessageMapper;
import com.airohit.agriculture.module.statistics.vo.message.*;
import com.airohit.agriculture.module.statistics.websocket.WebSocketServer;
import com.alibaba.fastjson.JSONObject;
//import net.dreamlu.iot.mqtt.spring.client.MqttClientTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.statistics.enums.ErrorCodeConstants.MESSAGE_NOT_EXISTS;

/**
 * 预警消息 Service 实现类
 *
 * @author 史铭浩
 */
@Service
@Validated
public class WarningMessageServiceImpl implements WarningMessageService {

    @Resource
    private WarningMessageMapper messageMapper;
    @Resource
    private WebSocketServer webSocketServer;
//    @Resource
//    private MqttClientTemplate mqttClientTemplate;

    @Override
    public Integer createMessage(WarningMessageCreateReqVO createReqVO) {
        // 插入
        WarningMessageDO message = WarningMessageConvert.INSTANCE.convert(createReqVO);
        messageMapper.insert(message);
        //群发websocket消息
        Long tenantId = TenantContextHolder.getTenantId();
        WarningMessageWebSocketVO warningMessageWebSocketVO = new WarningMessageWebSocketVO();
        warningMessageWebSocketVO.setTenantId(tenantId);
        BeanUtil.copyProperties(message, warningMessageWebSocketVO);
        String messageJsonString = JSONObject.toJSONString(warningMessageWebSocketVO);
        webSocketServer.broadcast(messageJsonString);
//        mqttClientTemplate.publish("/tenant/" + tenantId, ByteBuffer.wrap("messageJsonString".getBytes(StandardCharsets.UTF_8)));
        // 返回
        return message.getId();
    }

    @Override
    public void updateMessage(WarningMessageUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateMessageExists(updateReqVO.getId());
        // 更新
        WarningMessageDO updateObj = WarningMessageConvert.INSTANCE.convert(updateReqVO);
        messageMapper.updateById(updateObj);
    }

    @Override
    public void deleteMessage(Integer id) {
        // 校验存在
        this.validateMessageExists(id);
        // 删除
        messageMapper.deleteById(id);
    }

    private void validateMessageExists(Integer id) {
        if (messageMapper.selectById(id) == null) {
            throw exception(MESSAGE_NOT_EXISTS);
        }
    }

    @Override
    public WarningMessageDO getMessage(Integer id) {
        return messageMapper.selectById(id);
    }

    @Override
    public List<WarningMessageDO> getMessageList(Collection<Integer> ids) {
        return messageMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<WarningMessageDO> getMessagePage(WarningMessagePageReqVO pageReqVO) {
        return messageMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WarningMessageDO> getMessageList(WarningMessageExportReqVO exportReqVO) {
        return messageMapper.selectList(exportReqVO);
    }

    @Override
    public List<MessageStatisticVo> getMessageStatisticVoList(Integer limit) {
        return messageMapper.selectList(new LambdaQueryWrapperX<WarningMessageDO>()
                        .eqIfPresent(WarningMessageDO::getOverallSituation, 1)
                        .last("limit " + limit))
                .stream().map(warningMessageDO -> {
                    String warningType = DictFrameworkUtils.getDictDataLabel("warning_type", warningMessageDO.getWarningType().toString());
                    MessageStatisticVo messageStatisticVo = new MessageStatisticVo();
                    messageStatisticVo.setType(warningType);
                    messageStatisticVo.setArea(warningMessageDO.getWarningMessage());
                    messageStatisticVo.setTime(warningMessageDO.getCreateTime());
                    return messageStatisticVo;
                }).collect(Collectors.toList());
    }

}
