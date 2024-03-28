package com.airohit.agriculture.module.system.convert.notice;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.dal.dataobject.notice.NoticeDO;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticeCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticeRespVO;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticeUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
