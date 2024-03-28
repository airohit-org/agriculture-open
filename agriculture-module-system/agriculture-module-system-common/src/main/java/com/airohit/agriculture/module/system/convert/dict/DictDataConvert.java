package com.airohit.agriculture.module.system.convert.dict;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictDataDO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.data.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    List<DictDataSimpleRespVO> convertList(List<DictDataDO> list);

    DictDataRespVO convert(DictDataDO bean);

    PageResult<DictDataRespVO> convertPage(PageResult<DictDataDO> page);

    DictDataDO convert(DictDataUpdateReqVO bean);

    DictDataDO convert(DictDataCreateReqVO bean);

    List<DictDataExcelVO> convertList02(List<DictDataDO> bean);

    DictDataRespDTO convert02(DictDataDO bean);

    List<DictDataRespDTO> convertList03(List<DictDataDO> list);
}
