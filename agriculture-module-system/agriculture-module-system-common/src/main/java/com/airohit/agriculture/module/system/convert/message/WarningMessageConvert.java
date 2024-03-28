package com.airohit.agriculture.module.system.convert.message;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.message.dto.WarningMessageCreateReqVO;
import com.airohit.agriculture.module.system.dal.dataobject.message.WarningMessageDO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageRespVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 预警消息 Convert
 *
 * @author 史铭浩
 */
@Mapper
public interface WarningMessageConvert {

    WarningMessageConvert INSTANCE = Mappers.getMapper(WarningMessageConvert.class);

    WarningMessageDO convert(WarningMessageCreateReqVO bean);

    WarningMessageDO convert(WarningMessageUpdateReqVO bean);

    WarningMessageRespVO convert(WarningMessageDO bean);

    List<WarningMessageRespVO> convertList(List<WarningMessageDO> list);

    PageResult<WarningMessageRespVO> convertPage(PageResult<WarningMessageDO> page);

    //List<WarningMessageExcelVO> convertList02(List<WarningMessageDO> list);

}
