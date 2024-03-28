package com.airohit.agriculture.module.statistics.dal.mysql.message;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.statistics.dal.dataobject.message.WarningMessageDO;
import com.airohit.agriculture.module.statistics.vo.message.WarningMessageExportReqVO;
import com.airohit.agriculture.module.statistics.vo.message.WarningMessagePageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预警消息 Mapper
 *
 * @author 史铭浩
 */
@Mapper
public interface WarningMessageMapper extends BaseMapperX<WarningMessageDO> {

    default PageResult<WarningMessageDO> selectPage(WarningMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WarningMessageDO>()
                .eqIfPresent(WarningMessageDO::getOverallSituation, 1)
                .orderByDesc(WarningMessageDO::getId));
    }

    default List<WarningMessageDO> selectList(WarningMessageExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WarningMessageDO>()
                .eqIfPresent(WarningMessageDO::getOverallSituation, 1)
                .orderByDesc(WarningMessageDO::getId));
    }

}
