package com.airohit.agriculture.module.system.convert.errorcode;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import com.airohit.agriculture.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import com.airohit.agriculture.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import com.airohit.agriculture.module.system.entity.admin.errorcode.vo.ErrorCodeCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.errorcode.vo.ErrorCodeExcelVO;
import com.airohit.agriculture.module.system.entity.admin.errorcode.vo.ErrorCodeRespVO;
import com.airohit.agriculture.module.system.entity.admin.errorcode.vo.ErrorCodeUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 错误码 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface ErrorCodeConvert {

    ErrorCodeConvert INSTANCE = Mappers.getMapper(ErrorCodeConvert.class);

    ErrorCodeDO convert(ErrorCodeCreateReqVO bean);

    ErrorCodeDO convert(ErrorCodeUpdateReqVO bean);

    ErrorCodeRespVO convert(ErrorCodeDO bean);

    List<ErrorCodeRespVO> convertList(List<ErrorCodeDO> list);

    PageResult<ErrorCodeRespVO> convertPage(PageResult<ErrorCodeDO> page);

    List<ErrorCodeExcelVO> convertList02(List<ErrorCodeDO> list);

    ErrorCodeDO convert(ErrorCodeAutoGenerateReqDTO bean);

    List<ErrorCodeRespDTO> convertList03(List<ErrorCodeDO> list);

}
