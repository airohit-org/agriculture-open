package com.airohit.agriculture.module.statistics.dal.mysql.taskInfo;


import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.statistics.dal.dataobject.taskInfo.TaskInfoDO;
import com.airohit.agriculture.module.statistics.vo.taskInfo.TaskInfoRespVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 农事任务基本信息 Mapper
 *
 * @author 史铭浩
 */
@Mapper
public interface StatisticTaskInfoMapper extends BaseMapperX<TaskInfoDO> {

    /**
     * 查找农事任务列表
     *
     * @return
     */
    List<TaskInfoRespVo> getTaskInfoRespVoPage();


}
