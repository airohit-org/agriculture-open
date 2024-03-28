package com.airohit.agriculture.module.system.convert.dept;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.dal.dataobject.dept.PostDO;
import com.airohit.agriculture.module.system.entity.admin.dept.vo.post.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    List<PostSimpleRespVO> convertList02(List<PostDO> list);

    PageResult<PostRespVO> convertPage(PageResult<PostDO> page);

    PostRespVO convert(PostDO id);

    PostDO convert(PostCreateReqVO bean);

    PostDO convert(PostUpdateReqVO reqVO);

    List<PostExcelVO> convertList03(List<PostDO> list);

}
