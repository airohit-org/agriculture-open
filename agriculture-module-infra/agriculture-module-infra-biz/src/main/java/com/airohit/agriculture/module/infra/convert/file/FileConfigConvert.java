package com.airohit.agriculture.module.infra.convert.file;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.infra.controller.admin.file.vo.config.FileConfigCreateReqVO;
import com.airohit.agriculture.module.infra.controller.admin.file.vo.config.FileConfigRespVO;
import com.airohit.agriculture.module.infra.controller.admin.file.vo.config.FileConfigUpdateReqVO;
import com.airohit.agriculture.module.infra.dal.dataobject.file.FileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件配置 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigUpdateReqVO bean);

    FileConfigRespVO convert(FileConfigDO bean);

    List<FileConfigRespVO> convertList(List<FileConfigDO> list);

    PageResult<FileConfigRespVO> convertPage(PageResult<FileConfigDO> page);

}
