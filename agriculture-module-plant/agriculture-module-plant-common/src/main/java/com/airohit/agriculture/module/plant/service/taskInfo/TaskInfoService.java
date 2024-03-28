package com.airohit.agriculture.module.plant.service.taskInfo;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.taskInfo.TaskInfoDO;
import com.airohit.agriculture.module.plant.vo.taskInfo.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/3 20:06
 */
public interface TaskInfoService {
    /**
     * 获得农事任务模版字段
     *
     * @param name
     * @return
     */
    JSONArray getTaskFiled(String name);

    /**
     * 创建农事任务
     *
     * @param jsonObject
     */
    void createTask(JSONObject jsonObject);

    /**
     * 查找任务详情
     *
     * @param id
     * @return
     */
    JSONObject getTaskDetail(Integer id);

    /**
     * 处理任务消息定时任务
     */
    void taskMessage();

    /**
     * 发布任务
     */
    void releaseTaskInfo(List<JSONObject> json);

    /**
     * 修改任务详情
     *
     * @param jsonObject
     */
    void updateTaskDetail(JSONObject jsonObject);

    /**
     * app端提交任务
     *
     * @param taskInfoUpdateVO
     */
    void updateTaskInfoApp(TaskInfoUpdateVO taskInfoUpdateVO);

    /**
     * 查找农事任务
     *
     * @param id
     * @return
     */
    TaskInfoRespVo getTaskInfoRespVoById(Integer id);

    /**
     * 查找农事任务信息
     *
     * @param date
     * @return
     */
    List<TaskInfoBaseVO> getTaskInfoBaseVOListByDate(String date, Integer plantingPlanId);

    /**
     * 根据种植计划ID查找计划阶段分组任务信息
     *
     * @return
     */
    Map<String, List<TaskInfoDO>> getTaskInfoDOMap(Integer plantingPlanId);

    /**
     * 批量删除农事任务
     *
     * @param stageCode
     * @param plantingPlanId
     */
    void deleteTaskInfo(String stageCode, Integer plantingPlanId);

    /**
     * 删除农事任务
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 任务指派
     *
     * @param taskAppointVO
     */
    void updateTaskAppoint(TaskAppointVO taskAppointVO);

    /**
     * 任务批量指派
     *
     * @param taskAppointVO
     */
    void updateTaskAppointList(TaskAppointListVO taskAppointVO);

    /**
     * 获得农事任务分页
     *
     * @param pageReqVO 分页查询
     * @return 农事任务分页
     */
    PageResult<TaskInfoRespVo> getTaskInfoPage(TaskInfoPageReqVo pageReqVO);

    /**
     * 克隆计划任务
     *
     * @param oldPlanId
     * @param newPlanId
     */
    void clonePlanTask(Integer oldPlanId, Integer newPlanId);
}
