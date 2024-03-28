package com.airohit.agriculture.module.system.convert.tenant;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.slave.vo.packages.TenantPackageCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.packages.TenantPackageRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.packages.TenantPackageSimpleRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.packages.TenantPackageUpdateReqVO;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantPackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<TenantPackageDO> list);

}
