package com.airohit.agriculture.module.plant.dal.mysql.taskInfo;


import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.taskInfo.TaskInfoDO;
import com.airohit.agriculture.module.plant.vo.taskInfo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 农事任务基本信息 Mapper
 *
 * @author 史铭浩
 */
@Mapper
public interface TaskInfoMapper extends BaseMapperX<TaskInfoDO> {

    /**
     * 查找动态表单
     *
     * @param name
     * @return
     */
    String getTaskFiled(@Param("name") String name);

    /**
     * 根据日期模糊匹配农事任务基本信息
     *
     * @param date
     * @return
     */
    List<TaskInfoBaseVO> getTaskInfoBaseVOListByDate(@Param("date") String date, @Param("plantingPlanId") Integer plantingPlanId);

    /**
     * 分页查找农事任务列表
     *
     * @param page
     * @param taskInfoPageReqVo
     * @return
     */
    Page<TaskInfoRespVo> getTaskInfoRespVoPage(IPage<TaskInfoRespVo> page, @Param("taskInfoPageReqVo") TaskInfoPageReqVo taskInfoPageReqVo);

    List<TaskMessageVo> getTaskMessageVoList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查找农事任务
     *
     * @param id
     * @return
     */
    TaskInfoRespVo getTaskInfoRespVoById(@Param("id") Integer id);

    /**
     * 获取地块名称和计划名称
     *
     * @param taskId
     * @return
     */
    TaskLandPlanVo getTaskLandPlanVo(@Param("taskId") Integer taskId);

    /**
     * 查找任务指派人
     *
     * @param taskId
     * @return
     */
    String selectMemberName(@Param("taskId") Integer taskId);

}
