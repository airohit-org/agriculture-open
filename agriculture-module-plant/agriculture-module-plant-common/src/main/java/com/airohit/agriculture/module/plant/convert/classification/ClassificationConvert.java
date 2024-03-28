package com.airohit.agriculture.module.plant.convert.classification;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.classification.ClassificationDO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationCreateReqVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationExcelVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationRespVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 病虫害识别 Convert
 *
 * @author 管理员
 */
@Mapper
public interface ClassificationConvert {

    ClassificationConvert INSTANCE = Mappers.getMapper(ClassificationConvert.class);

    ClassificationDO convert(ClassificationCreateReqVO bean);

    ClassificationDO convert(ClassificationUpdateReqVO bean);

    ClassificationRespVO convert(ClassificationDO bean);

    List<ClassificationRespVO> convertList(List<ClassificationDO> list);

    PageResult<ClassificationRespVO> convertPage(PageResult<ClassificationDO> page);

    List<ClassificationExcelVO> convertList02(List<ClassificationDO> list);

}
