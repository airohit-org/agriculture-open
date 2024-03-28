package com.airohit.agriculture.module.system.convert.sms;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.sms.core.property.SmsChannelProperties;
import com.airohit.agriculture.module.system.dal.dataobject.sms.SmsChannelDO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.channel.SmsChannelCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.channel.SmsChannelRespVO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.channel.SmsChannelSimpleRespVO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SmsChannelDO convert(SmsChannelCreateReqVO bean);

    SmsChannelDO convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SmsChannelDO bean);

    List<SmsChannelRespVO> convertList(List<SmsChannelDO> list);

    PageResult<SmsChannelRespVO> convertPage(PageResult<SmsChannelDO> page);

    List<SmsChannelProperties> convertList02(List<SmsChannelDO> list);

    List<SmsChannelSimpleRespVO> convertList03(List<SmsChannelDO> list);

}
