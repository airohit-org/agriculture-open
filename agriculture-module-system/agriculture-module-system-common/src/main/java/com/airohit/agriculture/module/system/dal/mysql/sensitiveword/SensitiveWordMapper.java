package com.airohit.agriculture.module.system.dal.mysql.sensitiveword;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo.SensitiveWordExportReqVO;
import com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 敏感词 Mapper
 *
 * @author 永不言败
 */
@Mapper
public interface SensitiveWordMapper extends BaseMapperX<SensitiveWordDO> {

    default PageResult<SensitiveWordDO> selectPage(SensitiveWordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SensitiveWordDO>()
                .likeIfPresent(SensitiveWordDO::getName, reqVO.getName())
                .likeIfPresent(SensitiveWordDO::getTags, reqVO.getTag())
                .eqIfPresent(SensitiveWordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SensitiveWordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SensitiveWordDO::getId));
    }

    default List<SensitiveWordDO> selectList(SensitiveWordExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SensitiveWordDO>()
                .likeIfPresent(SensitiveWordDO::getName, reqVO.getName())
                .likeIfPresent(SensitiveWordDO::getTags, reqVO.getTag())
                .eqIfPresent(SensitiveWordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SensitiveWordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SensitiveWordDO::getId));
    }

    default SensitiveWordDO selectByName(String name) {
        return selectOne(SensitiveWordDO::getName, name);
    }

    @Select("SELECT COUNT(*) FROM system_sensitive_word WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(LocalDateTime maxUpdateTime);

}
