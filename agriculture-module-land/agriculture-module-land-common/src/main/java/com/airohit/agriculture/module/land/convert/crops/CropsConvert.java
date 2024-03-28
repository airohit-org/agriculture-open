package com.airohit.agriculture.module.land.convert.crops;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.crops.CropsDO;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsExcelVO;
import com.airohit.agriculture.module.land.vo.crops.CropsRespVO;
import com.airohit.agriculture.module.land.vo.crops.CropsUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地块作物 Convert
 *
 * @author 管理员
 */
@Mapper
public interface CropsConvert {

    CropsConvert INSTANCE = Mappers.getMapper(CropsConvert.class);

    CropsDO convert(CropsCreateReqVO bean);

    CropsDO convert(CropsUpdateReqVO bean);

    CropsRespVO convert(CropsDO bean);

    List<CropsRespVO> convertList(List<CropsDO> list);

    PageResult<CropsRespVO> convertPage(PageResult<CropsDO> page);

    List<CropsExcelVO> convertList02(List<CropsDO> list);

}
