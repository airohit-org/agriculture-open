package com.airohit.agriculture.module.system.convert.tenant;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.slave.vo.TenantExcelVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantUpdateReqVO;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantDO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.user.UserCreateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    TenantDO convert(TenantCreateReqVO bean);

    TenantDO convert(TenantUpdateReqVO bean);

    TenantRespVO convert(TenantDO bean);

    List<TenantRespVO> convertList(List<TenantDO> list);

    PageResult<TenantRespVO> convertPage(PageResult<TenantDO> page);

    List<TenantExcelVO> convertList02(List<TenantDO> list);

    default UserCreateReqVO convert02(TenantCreateReqVO bean) {
        UserCreateReqVO reqVO = new UserCreateReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName()).setMobile(bean.getContactMobile());
        return reqVO;
    }

}
