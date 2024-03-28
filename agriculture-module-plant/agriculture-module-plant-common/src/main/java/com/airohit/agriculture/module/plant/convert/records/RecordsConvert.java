package com.airohit.agriculture.module.plant.convert.records;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.records.RecordsDO;
import com.airohit.agriculture.module.plant.vo.records.RecordsCreateReqVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsExcelVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsRespVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 农事记录 Convert
 *
 * @author 管理员
 */
@Mapper
public interface RecordsConvert {

    RecordsConvert INSTANCE = Mappers.getMapper(RecordsConvert.class);

    RecordsDO convert(RecordsCreateReqVO bean);

    RecordsDO convert(RecordsUpdateReqVO bean);

    RecordsRespVO convert(RecordsDO bean);

    List<RecordsRespVO> convertList(List<RecordsDO> list);

    PageResult<RecordsRespVO> convertPage(PageResult<RecordsDO> page);

    List<RecordsExcelVO> convertList02(List<RecordsDO> list);

}
