package com.airohit.agriculture.module.system.convert.dict;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictTypeDO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.type.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    PageResult<DictTypeRespVO> convertPage(PageResult<DictTypeDO> bean);

    DictTypeRespVO convert(DictTypeDO bean);

    DictTypeDO convert(DictTypeCreateReqVO bean);

    DictTypeDO convert(DictTypeUpdateReqVO bean);

    List<DictTypeSimpleRespVO> convertList(List<DictTypeDO> list);

    List<DictTypeExcelVO> convertList02(List<DictTypeDO> list);

}
