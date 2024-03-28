package com.airohit.agriculture.module.system.convert.auth;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.airohit.agriculture.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.airohit.agriculture.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.airohit.agriculture.module.system.entity.admin.oauth2.vo.token.OAuth2AccessTokenRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OAuth2TokenConvert {

    OAuth2TokenConvert INSTANCE = Mappers.getMapper(OAuth2TokenConvert.class);

    OAuth2AccessTokenCheckRespDTO convert(OAuth2AccessTokenDO bean);

    PageResult<OAuth2AccessTokenRespVO> convert(PageResult<OAuth2AccessTokenDO> page);

    OAuth2AccessTokenRespDTO convert2(OAuth2AccessTokenDO bean);

}
