package com.airohit.agriculture.module.land.convert.varieties;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.varieties.CropsVarietiesDO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesCreateReqVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesRespVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 品种管理 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface CropsVarietiesConvert {

    CropsVarietiesConvert INSTANCE = Mappers.getMapper(CropsVarietiesConvert.class);

    CropsVarietiesDO convert(CropsVarietiesCreateReqVO bean);

    CropsVarietiesDO convert(CropsVarietiesUpdateReqVO bean);

    CropsVarietiesRespVO convert(CropsVarietiesDO bean);

    List<CropsVarietiesRespVO> convertList(List<CropsVarietiesDO> list);

    PageResult<CropsVarietiesRespVO> convertPage(PageResult<CropsVarietiesDO> page);

}
