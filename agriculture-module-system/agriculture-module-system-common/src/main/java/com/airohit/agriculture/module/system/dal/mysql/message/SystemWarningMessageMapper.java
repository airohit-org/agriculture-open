package com.airohit.agriculture.module.system.dal.mysql.message;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.system.dal.dataobject.message.WarningMessageDO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageExportReqVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessagePageReqVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 预警消息 Mapper
 *
 * @author 史铭浩
 */
@Mapper
public interface SystemWarningMessageMapper extends BaseMapperX<WarningMessageDO> {

    default PageResult<WarningMessageDO> selectPage(WarningMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WarningMessageDO>()
                .eqIfPresent(WarningMessageDO::getOverallSituation, 1).or()
                .eq(WarningMessageDO::getUserId, getLoginUserId())
                .orderByDesc(WarningMessageDO::getId));
    }

    IPage<WarningMessageRespVO> getWarningMessageRespVOPage(Page<WarningMessagePageReqVO> warningMessagePageReqVOPage, @Param("pageVO") WarningMessagePageReqVO pageVO);

    WarningMessageRespVO getWarningMessageRespVO(@Param("id") Integer id);

    Integer getMessageCount(@Param("messageStatus") Integer messageStatus, @Param("userId") Long userId, @Param("tenantId") Long tenantId);

    default List<WarningMessageDO> selectList(WarningMessageExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WarningMessageDO>()
                .eqIfPresent(WarningMessageDO::getOverallSituation, 1).or()
                .eq(WarningMessageDO::getUserId, getLoginUserId())
                .orderByDesc(WarningMessageDO::getId));
    }

}
