package com.airohit.agriculture.module.system.convert.ip;

import com.airohit.agriculture.framework.ip.core.Area;
import com.airohit.agriculture.module.system.entity.admin.ip.vo.AreaNodeRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AreaConvert {

    AreaConvert INSTANCE = Mappers.getMapper(AreaConvert.class);

    List<AreaNodeRespVO> convertList(List<Area> list);

}
