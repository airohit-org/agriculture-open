package com.airohit.agriculture.module.land.convert.layer;

import com.airohit.agriculture.module.land.dal.dataobject.layer.FarmLayerDO;
import com.airohit.agriculture.module.land.vo.layer.FarmLayerVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FarmLayerConvert {
    FarmLayerConvert INSTANCE = Mappers.getMapper(FarmLayerConvert.class);

    List<FarmLayerVO> convertList(List<FarmLayerDO> list);
}
