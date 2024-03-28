package com.airohit.agriculture.module.plant.dal.mysql.tasktemplateInfo;


import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.tasktemplateInfo.TaskTemplateInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 农事任务模版基本信息 Mapper
 *
 * @author 史铭浩
 */
@Mapper
public interface TaskTemplateInfoMapper extends BaseMapperX<TaskTemplateInfoDO> {

    String getTaskTemplateFiled(@Param("name") String name);

}
