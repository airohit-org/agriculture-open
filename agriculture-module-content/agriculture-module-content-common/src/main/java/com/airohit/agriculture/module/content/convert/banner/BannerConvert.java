package com.airohit.agriculture.module.content.convert.banner;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.content.dal.dataobject.banner.BannerDO;
import com.airohit.agriculture.module.content.vo.banner.BannerCreateReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerExcelVO;
import com.airohit.agriculture.module.content.vo.banner.BannerRespVO;
import com.airohit.agriculture.module.content.vo.banner.BannerUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 广告信息 Convert
 *
 * @author 管理员
 */
@Mapper
public interface BannerConvert {

    BannerConvert INSTANCE = Mappers.getMapper(BannerConvert.class);

    BannerDO convert(BannerCreateReqVO bean);

    BannerDO convert(BannerUpdateReqVO bean);

    BannerRespVO convert(BannerDO bean);

    List<BannerRespVO> convertList(List<BannerDO> list);

    PageResult<BannerRespVO> convertPage(PageResult<BannerDO> page);

    List<BannerExcelVO> convertList02(List<BannerDO> list);

}
