package com.airohit.agriculture.module.system.dal.mysql.oauth2;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import com.airohit.agriculture.module.system.entity.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * OAuth2 客户端 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {

    default PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
                .orderByDesc(OAuth2ClientDO::getId));
    }

    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }

    @Select("SELECT COUNT(*) FROM system_oauth2_client WHERE update_time > #{maxUpdateTime}")
    int selectCountByUpdateTimeGt(LocalDateTime maxUpdateTime);

}
