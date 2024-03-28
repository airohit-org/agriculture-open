package com.airohit.agriculture.module.system.convert.sms;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.dal.dataobject.sms.SmsTemplateDO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.template.SmsTemplateCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.template.SmsTemplateExcelVO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.template.SmsTemplateRespVO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.template.SmsTemplateUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SmsTemplateConvert {

    SmsTemplateConvert INSTANCE = Mappers.getMapper(SmsTemplateConvert.class);

    SmsTemplateDO convert(SmsTemplateCreateReqVO bean);

    SmsTemplateDO convert(SmsTemplateUpdateReqVO bean);

    SmsTemplateRespVO convert(SmsTemplateDO bean);

    List<SmsTemplateRespVO> convertList(List<SmsTemplateDO> list);

    PageResult<SmsTemplateRespVO> convertPage(PageResult<SmsTemplateDO> page);

    List<SmsTemplateExcelVO> convertList02(List<SmsTemplateDO> list);

}
