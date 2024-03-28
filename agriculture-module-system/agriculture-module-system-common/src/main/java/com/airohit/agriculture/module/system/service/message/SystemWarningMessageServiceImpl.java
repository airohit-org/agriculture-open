package com.airohit.agriculture.module.system.service.message;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.system.api.message.dto.WarningMessageCreateReqVO;
import com.airohit.agriculture.module.system.convert.message.WarningMessageConvert;
import com.airohit.agriculture.module.system.dal.dataobject.message.MessageReadDO;
import com.airohit.agriculture.module.system.dal.dataobject.message.WarningMessageDO;
import com.airohit.agriculture.module.system.dal.mysql.message.MessageReadMapper;
import com.airohit.agriculture.module.system.dal.mysql.message.SystemWarningMessageMapper;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessagePageReqVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.MESSAGE_NOT_EXISTS;

/**
 * 预警消息 Service 实现类
 *
 * @author 史铭浩
 */
@Service
@Validated
public class SystemWarningMessageServiceImpl implements SystemWarningMessageService {

    @Resource
    private SystemWarningMessageMapper messageMapper;
    @Resource
    private MessageReadMapper messageReadMapper;

    @Override
    public void createMessage(WarningMessageCreateReqVO createReqVO) {
        // 插入
        TenantUtils.execute(createReqVO.getTenantId(), () -> {
            WarningMessageDO warningMessageDO = WarningMessageConvert.INSTANCE.convert(createReqVO);
            messageMapper.insert(warningMessageDO);
        });
    }


    private void validateMessageExists(Integer id) {
        if (messageMapper.selectById(id) == null) {
            throw exception(MESSAGE_NOT_EXISTS);
        }
    }

    @Override
    public WarningMessageRespVO getMessage(Integer id) {
        Long loginUserId = getLoginUserId();
        WarningMessageRespVO warningMessageRespVO = messageMapper.getWarningMessageRespVO(id);
        if (0 == warningMessageRespVO.getMessageStatus()) {
            Long messageCount = messageReadMapper.selectCount(new LambdaQueryWrapperX<MessageReadDO>()
                    .eqIfPresent(MessageReadDO::getMessageId, warningMessageRespVO.getId())
                    .eqIfPresent(MessageReadDO::getUserId, loginUserId));
            if (messageCount == 0) {
                messageReadMapper.insert(new MessageReadDO()
                        .setMessageId(warningMessageRespVO.getId())
                        .setUserId(Integer.valueOf(loginUserId.toString())));
                warningMessageRespVO.setMessageStatus(1);
            }
        }
        return warningMessageRespVO;
    }

    @Override
    public PageResult<WarningMessageRespVO> getMessagePage(WarningMessagePageReqVO pageReqVO) {
        pageReqVO.setUserId(getLoginUserId());
        Long tenantId = TenantContextHolder.getTenantId();
        pageReqVO.setTenantId(tenantId);
        IPage<WarningMessageRespVO> warningMessageRespVOIPage = messageMapper.getWarningMessageRespVOPage(new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize()), pageReqVO);
        return new PageResult<>(warningMessageRespVOIPage.getRecords(), warningMessageRespVOIPage.getTotal());
    }

    @Override
    public Integer getMessageCount(Integer messageStatus) {
        Long tenantId = TenantContextHolder.getTenantId();
        return messageMapper.getMessageCount(messageStatus, getLoginUserId(), tenantId);
    }

    @Override
    public void readAllMessage() {
        Long loginUserId = getLoginUserId();
        List<Integer> collect = messageReadMapper.selectList(MessageReadDO::getUserId, loginUserId).stream().map(MessageReadDO::getMessageId).collect(Collectors.toList());
        List<WarningMessageDO> warningMessageDOS = messageMapper.selectList(new LambdaQueryWrapperX<WarningMessageDO>()
                .notInIfPresent(WarningMessageDO::getId, collect)
                .eqIfPresent(WarningMessageDO::getOverallSituation, 1)
                .or().eq(WarningMessageDO::getUserId, getLoginUserId()));
        List<MessageReadDO> messageReadDOList = new ArrayList<>();
        List<Integer> messageReadIdList = messageReadMapper.selectList(new LambdaQueryWrapperX<MessageReadDO>()
                        .inIfPresent(MessageReadDO::getMessageId, warningMessageDOS.stream().map(WarningMessageDO::getId).collect(Collectors.toList()))
                        .eqIfPresent(MessageReadDO::getUserId, loginUserId)).stream().map(MessageReadDO::getMessageId)
                .collect(Collectors.toList());
        for (WarningMessageDO warningMessageDO : warningMessageDOS) {
            if (!messageReadIdList.contains(warningMessageDO.getId())) {
                messageReadDOList.add(new MessageReadDO()
                        .setMessageId(warningMessageDO.getId())
                        .setUserId(Integer.valueOf(loginUserId.toString())));
            }
        }
        messageReadMapper.insertBatch(messageReadDOList);
    }


}
