package com.airohit.agriculture.module.infra.dal.mysql.file;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import com.airohit.agriculture.module.infra.dal.dataobject.file.FileConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * 文件配置 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface FileConfigMapper extends BaseMapperX<FileConfigDO> {

    default PageResult<FileConfigDO> selectPage(FileConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileConfigDO>()
                .likeIfPresent(FileConfigDO::getName, reqVO.getName())
                .eqIfPresent(FileConfigDO::getStorage, reqVO.getStorage())
                .betweenIfPresent(FileConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileConfigDO::getId));
    }

    @Select("SELECT COUNT(*) FROM infra_file_config WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(LocalDateTime maxUpdateTime);

}
