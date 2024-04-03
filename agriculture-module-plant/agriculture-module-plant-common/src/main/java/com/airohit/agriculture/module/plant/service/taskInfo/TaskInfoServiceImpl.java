package com.airohit.agriculture.module.plant.service.taskInfo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ClassUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.redis.core.RedisLock;
import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskInfo.TaskInfoDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskfertilizer.TaskFertilizerDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskintertill.TaskIntertillDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskirrigation.TaskIrrigationDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskpesticide.TaskPesticideDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskraking.TaskRakingDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskreap.TaskReapDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskseeding.TaskSeedingDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskweed.TaskWeedDO;
import com.airohit.agriculture.module.plant.dal.mysql.plantypedata.PlanTypeDataMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskInfo.TaskInfoMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskfertilizer.TaskFertilizerMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskintertill.TaskIntertillMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskirrigation.TaskIrrigationMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskpesticide.TaskPesticideMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskraking.TaskRakingMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskreap.TaskReapMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskseeding.TaskSeedingMapper;
import com.airohit.agriculture.module.plant.dal.mysql.taskweed.TaskWeedMapper;
import com.airohit.agriculture.module.plant.enums.taskInfo.TaskInfoEnum;
import com.airohit.agriculture.module.plant.enums.taskInfo.TaskStatusEnum;
import com.airohit.agriculture.module.plant.vo.taskInfo.*;
import com.airohit.agriculture.module.system.api.message.MessageApi;
import com.airohit.agriculture.module.system.api.message.dto.WarningMessageCreateReqVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.redisson.api.RLock;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.*;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/3 20:06
 */
@Service
@Slf4j
public class TaskInfoServiceImpl implements TaskInfoService {

    private static final String TASK_INFO_MESSAGE_LOCK = "TASK_INFO_MESSAGE_LOCK";
    @Resource
    private TaskInfoMapper taskInfoMapper;
    @Resource
    private TaskWeedMapper taskWeedMapper;
    @Resource
    private TaskFertilizerMapper taskFertilizerMapper;
    @Resource
    private TaskIntertillMapper taskIntertillMapper;
    @Resource
    private RedisLock redisLock;
    @Resource
    private TaskPesticideMapper taskPesticideMapper;
    @Resource
    private TaskIrrigationMapper taskIrrigationMapper;
    @Resource
    private TaskRakingMapper taskRakingMapper;
    @Resource
    private TaskReapMapper taskReapMapper;
    @Resource
    private TaskSeedingMapper taskSeedingMapper;
    @Resource
    private PlanTypeDataMapper planTypeDataMapper;

    @Resource
    @Lazy
    private TaskInfoService taskInfoService;
    @Resource
    private MessageApi messageApi;

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public JSONArray getTaskFiled(String name) {
        return JSONArray.parseArray(StringEscapeUtils.unescapeJson(taskInfoMapper.getTaskFiled(name)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTask(JSONObject jsonObject) {
        TaskInfoDO taskInfo = jsonObject.toJavaObject(TaskInfoDO.class);
        //校验任务周期
//        checkPeriod(taskInfo);
        TaskInfoEnum taskInfoEnum = TaskInfoEnum.getByType(taskInfo.getType());
        taskInfo.setTypeModelName(taskInfoEnum.getTypeModelName());
        taskInfo.setTypeTableName(taskInfoEnum.getTypeTableName());
        taskInfoMapper.insert(taskInfo);
        try {
            Class<Object> objectClass = ClassUtil.loadClass(taskInfoEnum.getTypeModelName());
            Object object = objectClass.getDeclaredConstructor().newInstance();
            if (object instanceof TaskWeedBaseVO) {
                //除草
                TaskWeedDO taskWeedDO = jsonObject.toJavaObject(TaskWeedDO.class);
                taskWeedDO.setAgroTaskId(taskInfo.getId());
                taskWeedMapper.insert(taskWeedDO);
            }
            if (object instanceof TaskFertilizerBaseVO) {
                //施肥
                TaskFertilizerDO taskFertilizerBaseVO = jsonObject.toJavaObject(TaskFertilizerDO.class);
                taskFertilizerBaseVO.setAgroTaskId(taskInfo.getId());
                taskFertilizerMapper.insert(taskFertilizerBaseVO);
            }
            if (object instanceof TaskIntertillBaseVO) {
                //中耕
                TaskIntertillDO taskIntertillDO = jsonObject.toJavaObject(TaskIntertillDO.class);
                taskIntertillDO.setAgroTaskId(taskInfo.getId());
                taskIntertillMapper.insert(taskIntertillDO);
            }
            if (object instanceof TaskIrrigationBaseVO) {
                //灌溉
                TaskIrrigationDO taskIrrigationDO = jsonObject.toJavaObject(TaskIrrigationDO.class);
                taskIrrigationDO.setAgroTaskId(taskInfo.getId());
                taskIrrigationMapper.insert(taskIrrigationDO);
            }
            if (object instanceof TaskPesticideBaseVO) {
                //打药
                TaskPesticideDO taskPesticideDO = jsonObject.toJavaObject(TaskPesticideDO.class);
                taskPesticideDO.setAgroTaskId(taskInfo.getId());
                taskPesticideMapper.insert(taskPesticideDO);
            }
            if (object instanceof TaskRakingBaseVO) {
                //整地
                TaskRakingDO taskRakingDO = jsonObject.toJavaObject(TaskRakingDO.class);
                taskRakingDO.setAgroTaskId(taskInfo.getId());
                taskRakingMapper.insert(taskRakingDO);
            }
            if (object instanceof TaskReapBaseVO) {
                //收割
                TaskReapDO taskReapDO = jsonObject.toJavaObject(TaskReapDO.class);
                taskReapDO.setAgroTaskId(taskInfo.getId());
                taskReapMapper.insert(taskReapDO);
            }
            if (object instanceof TaskSeedingBaseVO) {
                //播种
                TaskSeedingDO taskSeedingDO = jsonObject.toJavaObject(TaskSeedingDO.class);
                taskSeedingDO.setAgroTaskId(taskInfo.getId());
                taskSeedingMapper.insert(taskSeedingDO);
            }
        } catch (Exception e) {
            log.error("添加失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSONObject getTaskDetail(Integer id) {
        TaskInfoDO taskInfoDO = taskInfoMapper.selectById(id);
        if (Objects.isNull(taskInfoDO)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(taskInfoDO));
        TaskInfoEnum taskInfoEnum = TaskInfoEnum.getByType(taskInfoDO.getType());
        String[] excludeProperties = {"status", "remark", "id", "createTime", "updateTime",
                "creator", "updater", "deleted", "tenantId"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        try {
            Class<Object> objectClass = ClassUtil.loadClass(taskInfoEnum.getTypeModelName());
            Object object = objectClass.getDeclaredConstructor().newInstance();
            if (object instanceof TaskWeedBaseVO) {
                //除草
                TaskWeedDO taskWeedDO = taskWeedMapper.selectOne(TaskWeedDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskWeedDO, excludeFilter)));
            }
            if (object instanceof TaskFertilizerBaseVO) {
                //施肥
                TaskFertilizerDO taskFertilizerDO = taskFertilizerMapper.selectOne(TaskFertilizerDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskFertilizerDO, excludeFilter)));
            }
            if (object instanceof TaskIntertillBaseVO) {
                //中耕
                TaskIntertillDO taskIntertillDO = taskIntertillMapper.selectOne(TaskIntertillDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskIntertillDO, excludeFilter)));
            }
            if (object instanceof TaskIrrigationBaseVO) {
                //灌溉
                TaskIrrigationDO taskIrrigationDO = taskIrrigationMapper.selectOne(TaskIrrigationDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskIrrigationDO, excludeFilter)));
            }
            if (object instanceof TaskPesticideBaseVO) {
                //打药
                TaskPesticideDO taskPesticideDO = taskPesticideMapper.selectOne(TaskPesticideDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskPesticideDO, excludeFilter)));
            }
            if (object instanceof TaskRakingBaseVO) {
                //整地
                TaskRakingDO taskRakingDO = taskRakingMapper.selectOne(TaskRakingDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskRakingDO, excludeFilter)));
            }
            if (object instanceof TaskReapBaseVO) {
                //收割
                TaskReapDO taskReapDO = taskReapMapper.selectOne(TaskReapDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskReapDO, excludeFilter)));
            }
            if (object instanceof TaskSeedingBaseVO) {
                //播种
                TaskSeedingDO taskSeedingDO = taskSeedingMapper.selectOne(TaskSeedingDO::getAgroTaskId, taskInfoDO.getId());
                jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskSeedingDO, excludeFilter)));
            }
        } catch (Exception e) {
            log.error("查找失败", e);
            throw new RuntimeException(e);
        }
        TaskLandPlanVo taskLandPlanVo = taskInfoMapper.getTaskLandPlanVo(id);
        jsonObject.put("nickName", taskInfoMapper.selectMemberName(id));
        jsonObject.putAll(JSONObject.parseObject(JSONObject.toJSONString(taskLandPlanVo)));
        return jsonObject;
    }

    @Override
    @TenantIgnore
    public void taskMessage() {
        DateTime now = new DateTime();
        DateTime yesterday = DateUtil.offsetDay(now, -1);
        DateTime tomorrow = DateUtil.offsetDay(now, 1);
        List<TaskMessageVo> taskMessageVoList = taskInfoMapper.getTaskMessageVoList(yesterday.toStringDefaultTimeZone(), now.toStringDefaultTimeZone());
        for (TaskMessageVo yesterdayTaskInfoDO : taskMessageVoList) {
            TaskInfoEnum taskInfoEnum = TaskInfoEnum.getByType(yesterdayTaskInfoDO.getType());
            WarningMessageCreateReqVO warningMessageCreateReqVO = new WarningMessageCreateReqVO();
            warningMessageCreateReqVO.setTenantId(yesterdayTaskInfoDO.getTenantId());
            warningMessageCreateReqVO.setWarningType(4);
            String format = String.format("尊敬的用户您好；您的%s，请于%s进行%s，天气适宜。", yesterdayTaskInfoDO.getName(), DateUtil.format(yesterdayTaskInfoDO.getStartDate(), "MM-dd"), yesterdayTaskInfoDO.getCropsName() + taskInfoEnum.getName());
            warningMessageCreateReqVO.setWarningMessage(format);
            warningMessageCreateReqVO.setOverallSituation(0);
            warningMessageCreateReqVO.setUserId(yesterdayTaskInfoDO.getPeasantId());
            messageApi.create(warningMessageCreateReqVO);
        }
        List<TaskMessageVo> tomorrowTaskMessageVoList = taskInfoMapper.getTaskMessageVoList(now.toStringDefaultTimeZone(), tomorrow.toStringDefaultTimeZone());
        for (TaskMessageVo taskMessageVo : tomorrowTaskMessageVoList) {
            TaskInfoEnum taskInfoEnum = TaskInfoEnum.getByType(taskMessageVo.getType());
            WarningMessageCreateReqVO warningMessageCreateReqVO = new WarningMessageCreateReqVO();
            warningMessageCreateReqVO.setTenantId(taskMessageVo.getTenantId());
            warningMessageCreateReqVO.setWarningType(4);
            String format = String.format("尊敬的用户您好；您的%s，请于%s进行%s，天气适宜。", taskMessageVo.getName(), DateUtil.format(taskMessageVo.getStartDate(), "MM-dd"), taskMessageVo.getCropsName() + taskInfoEnum.getName());
            warningMessageCreateReqVO.setWarningMessage(format);
            warningMessageCreateReqVO.setOverallSituation(0);
            warningMessageCreateReqVO.setUserId(taskMessageVo.getPeasantId());
            messageApi.create(warningMessageCreateReqVO);
        }

    }

    @Override
    public void releaseTaskInfo(List<JSONObject> json) {
        for (JSONObject jsonObject : json) {
            Long tenantId = jsonObject.getLong("tenantId");
            Long farmTenantId = jsonObject.getLong("farmTenantId");
            TenantUtils.execute(tenantId, () -> {
                ThreadUtil.sleep(1000);
                TenantUtils.executeFarm(farmTenantId, () -> {
                    ThreadUtil.sleep(30);
                    TaskInfoDO taskInfoDO = taskInfoMapper.selectOne(TaskInfoDO::getDataCode, jsonObject.get("dataCode"));
                    if (Objects.isNull(taskInfoDO)) {
                        taskInfoService.createTask(jsonObject);
                    } else {
                        jsonObject.put("id", taskInfoDO.getId());
                        taskInfoService.updateTaskDetail(jsonObject);
                    }
                });
            });
        }
    }

    private void checkPeriod(TaskInfoDO taskInfo) {
        List<TaskInfoDO> taskInfoDOS = taskInfoMapper.selectList(new LambdaQueryWrapperX<TaskInfoDO>()
                .eqIfPresent(TaskInfoDO::getPlantingPlanId, taskInfo.getPlantingPlanId())
                .eqIfPresent(TaskInfoDO::getPlanningStage, taskInfo.getPlanningStage())
        );
        int period = 0;
        if (taskInfoDOS.size() > 0) {
            period += taskInfoDOS.stream().mapToInt(TaskInfoDO::getTaskPeriod).sum();
        }
        //计算任务已有周期天数
        period += taskInfo.getTaskPeriod();
        PlanTypeDataDO planTypeDataDO = planTypeDataMapper.selectOne(new LambdaQueryWrapperX<PlanTypeDataDO>()
                .eqIfPresent(PlanTypeDataDO::getPlantingPlanId, taskInfo.getPlantingPlanId())
                .eqIfPresent(PlanTypeDataDO::getStageCode, taskInfo.getPlanningStage())
        );
        if (period > planTypeDataDO.getPeriod()) {
            throw exception(PERIOD_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskDetail(JSONObject jsonObject) {
        TaskInfoDO taskInfoDO = jsonObject.toJavaObject(TaskInfoDO.class);
//        checkPeriod(taskInfoDO);
        taskInfoMapper.updateById(taskInfoDO);
        TaskInfoEnum taskInfoEnum = TaskInfoEnum.getByType(taskInfoDO.getType());
        String[] excludeProperties = {"status", "remark", "id", "agroTaskId", "createTime", "updateTime",
                "creator", "updater", "deleted", "tenantId"};
        try {
            Class<Object> objectClass = ClassUtil.loadClass(taskInfoEnum.getTypeModelName());
            Object object = objectClass.getDeclaredConstructor().newInstance();
            if (object instanceof TaskWeedBaseVO) {
                //除草
                TaskWeedDO taskWeedDO = jsonObject.toJavaObject(TaskWeedDO.class);
                taskWeedDO.setAgroTaskId(taskInfoDO.getId());
                taskWeedMapper.update(taskWeedDO, new LambdaQueryWrapperX<TaskWeedDO>()
                        .eqIfPresent(TaskWeedDO::getAgroTaskId, taskInfoDO.getId()));
            }
            if (object instanceof TaskFertilizerBaseVO) {
                //施肥
                TaskFertilizerDO taskFertilizerDO = jsonObject.toJavaObject(TaskFertilizerDO.class);
                taskFertilizerDO.setAgroTaskId(taskInfoDO.getId());
                taskFertilizerMapper.update(taskFertilizerDO, new LambdaQueryWrapperX<TaskFertilizerDO>()
                        .eqIfPresent(TaskFertilizerDO::getAgroTaskId, taskInfoDO.getId()));
            }
            if (object instanceof TaskIntertillBaseVO) {
                //中耕
                TaskIntertillDO taskIntertillDO = jsonObject.toJavaObject(TaskIntertillDO.class);
                taskIntertillDO.setAgroTaskId(taskInfoDO.getId());
                taskIntertillMapper.update(taskIntertillDO, new LambdaQueryWrapperX<TaskIntertillDO>()
                        .eqIfPresent(TaskIntertillDO::getAgroTaskId, taskInfoDO.getId()));
            }
            if (object instanceof TaskIrrigationBaseVO) {
                //灌溉
                TaskIrrigationDO taskIrrigationDO = jsonObject.toJavaObject(TaskIrrigationDO.class);
                taskIrrigationDO.setAgroTaskId(taskInfoDO.getId());
                taskIrrigationMapper.update(taskIrrigationDO, new LambdaQueryWrapperX<TaskIrrigationDO>()
                        .eqIfPresent(TaskIrrigationDO::getAgroTaskId, taskInfoDO.getId()));

            }
            if (object instanceof TaskPesticideBaseVO) {
                //打药
                TaskPesticideDO taskPesticideDO = jsonObject.toJavaObject(TaskPesticideDO.class);
                taskPesticideDO.setAgroTaskId(taskInfoDO.getId());
                taskPesticideMapper.update(taskPesticideDO, new LambdaQueryWrapperX<TaskPesticideDO>()
                        .eqIfPresent(TaskPesticideDO::getAgroTaskId, taskInfoDO.getId()));
            }
            if (object instanceof TaskRakingBaseVO) {
                //整地
                TaskRakingDO taskRakingDO = jsonObject.toJavaObject(TaskRakingDO.class);
                taskRakingDO.setAgroTaskId(taskInfoDO.getId());
                taskRakingMapper.update(taskRakingDO, new LambdaQueryWrapperX<TaskRakingDO>()
                        .eqIfPresent(TaskRakingDO::getAgroTaskId, taskInfoDO.getId()));
            }
            if (object instanceof TaskReapBaseVO) {
                //收割
                TaskReapDO taskReapDO = jsonObject.toJavaObject(TaskReapDO.class);
                taskReapDO.setAgroTaskId(taskInfoDO.getId());
                taskReapMapper.update(taskReapDO, new LambdaQueryWrapperX<TaskReapDO>()
                        .eqIfPresent(TaskReapDO::getAgroTaskId, taskInfoDO.getId()));
            }
            if (object instanceof TaskSeedingBaseVO) {
                //播种
                TaskSeedingDO taskSeedingDO = jsonObject.toJavaObject(TaskSeedingDO.class);
                taskSeedingDO.setAgroTaskId(taskInfoDO.getId());
                taskSeedingMapper.update(taskSeedingDO, new LambdaQueryWrapperX<TaskSeedingDO>()
                        .eqIfPresent(TaskSeedingDO::getAgroTaskId, taskInfoDO.getId()));
            }
        } catch (Exception e) {
            log.error("修改失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTaskInfoApp(TaskInfoUpdateVO taskInfoUpdateVO) {
        TaskInfoDO taskInfoDO = taskInfoMapper.selectById(taskInfoUpdateVO.getId());
        Integer taskStatus = getTaskStatus(taskInfoDO);
        if (!Objects.equals(taskStatus, TaskStatusEnum.IN_PROGRESS.getStatus())
                && !Objects.equals(taskStatus, TaskStatusEnum.INCOMPLETE.getStatus())) {
            throw exception(TASK_INFO_ERROR);
        }
        taskInfoDO.setStatus(1);
        taskInfoDO.setOperateTime(DateUtil.toLocalDateTime(taskInfoUpdateVO.getOperateTime()));
        taskInfoDO.setImageUrls(taskInfoUpdateVO.getImageUrls());
        taskInfoDO.setVideoUrl(taskInfoUpdateVO.getVideoUrl());
        taskInfoDO.setAddress(taskInfoUpdateVO.getAddress());
        taskInfoDO.setLatitudeLongitude(taskInfoUpdateVO.getLatitudeLongitude());
        taskInfoDO.setRemark(taskInfoUpdateVO.getRemark());
        taskInfoMapper.updateById(taskInfoDO);

    }

    @Override
    public TaskInfoRespVo getTaskInfoRespVoById(Integer id) {
        TaskInfoRespVo taskInfoRespVo = taskInfoMapper.getTaskInfoRespVoById(id);
        taskInfoRespVo.setTaskStatus(getTaskStatusList(taskInfoRespVo));
        return taskInfoRespVo;
    }

    @Override
    public List<TaskInfoBaseVO> getTaskInfoBaseVOListByDate(String date, Integer plantingPlanId) {
        return taskInfoMapper.getTaskInfoBaseVOListByDate(date, plantingPlanId);
    }

    @Override
    public Map<String, List<TaskInfoDO>> getTaskInfoDOMap(Integer plantingPlanId) {
        return taskInfoMapper.selectList(new LambdaQueryWrapperX<TaskInfoDO>()
                        .eqIfPresent(TaskInfoDO::getDeleted, 0)
                        .eqIfPresent(TaskInfoDO::getPlantingPlanId, plantingPlanId)
                        .orderByAsc(TaskInfoDO::getStartDate))
                .stream().collect(Collectors.groupingBy(TaskInfoDO::getPlanningStage, LinkedHashMap::new, Collectors.toList()));
    }

    @Override
    public void deleteTaskInfo(String stageCode, Integer plantingPlanId) {
        taskInfoMapper.delete(new LambdaQueryWrapperX<TaskInfoDO>()
                .eqIfPresent(TaskInfoDO::getPlanningStage, stageCode)
                .eqIfPresent(TaskInfoDO::getPlantingPlanId, plantingPlanId));
    }

    @Override
    public void delete(Integer id) {
        taskInfoMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskAppoint(TaskAppointVO taskAppointVO) {
        TaskInfoDO taskInfoDO = taskInfoMapper.selectById(taskAppointVO.getId());
        Integer taskStatus = getTaskStatus(taskInfoDO);
        if (taskStatus > TaskStatusEnum.NOT_STARTED.getStatus()) {
            throw exception(APPOINT_ERROR);
        }
        Long tenantId = TenantContextHolder.getTenantId();
        taskInfoDO.setPeasantId(taskAppointVO.getPeasantId());
        taskInfoMapper.updateById(taskInfoDO);
        WarningMessageCreateReqVO warningMessageCreateReqVO = new WarningMessageCreateReqVO();
        warningMessageCreateReqVO.setTenantId(tenantId);
        warningMessageCreateReqVO.setWarningType(4);
        warningMessageCreateReqVO.setWarningMessage("您有一条农事任务待处理，请查收");
        warningMessageCreateReqVO.setOverallSituation(0);
        warningMessageCreateReqVO.setUserId(taskAppointVO.getPeasantId());
        messageApi.create(warningMessageCreateReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskAppointList(TaskAppointListVO taskAppointVO) {
        taskAppointVO.getId().forEach(id -> {
            TaskInfoDO taskInfoDO = taskInfoMapper.selectById(id);
            Integer taskStatus = getTaskStatus(taskInfoDO);
            if (taskStatus > TaskStatusEnum.NOT_STARTED.getStatus()) {
                throw exception(APPOINT_ERROR);
            }
            taskInfoDO.setPeasantId(taskAppointVO.getPeasantId());
            taskInfoMapper.updateById(taskInfoDO);
        });
    }

    @Override
    public PageResult<TaskInfoRespVo> getTaskInfoPage(TaskInfoPageReqVo pageReqVO) {
        IPage<TaskInfoRespVo> taskInfoRespVoIPage = taskInfoMapper.getTaskInfoRespVoPage(new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize()), pageReqVO);
        List<TaskInfoRespVo> records = taskInfoRespVoIPage.getRecords();
        records.forEach(taskInfoRespVo -> taskInfoRespVo.setTaskStatus(getTaskStatusList(taskInfoRespVo)));
        return new PageResult<>(records, taskInfoRespVoIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clonePlanTask(Integer oldPlanId, Integer newPlanId) {
        List<TaskInfoDO> taskInfoDOS = taskInfoMapper.selectList(new LambdaQueryWrapperX<TaskInfoDO>()
                .eqIfPresent(TaskInfoDO::getPlantingPlanId, oldPlanId)
                .eqIfPresent(TaskInfoDO::getDeleted, 0));
        taskInfoDOS.forEach(taskInfoDO -> {
            TaskInfoEnum taskInfoEnum = TaskInfoEnum.getByType(taskInfoDO.getType());
            String[] excludeProperties = {"status", "remark", "id", "createTime", "updateTime",
                    "creator", "updater", "deleted", "tenantId", "agroTaskId"};
            PropertyPreFilters filters = new PropertyPreFilters();
            PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
            excludeFilter.addExcludes(excludeProperties);
            Integer id = taskInfoDO.getId();
            taskInfoDO.setId(null);
            taskInfoDO.setCreateTime(null);
            taskInfoDO.setCreator(null);
            taskInfoDO.setUpdater(null);
            taskInfoDO.setUpdateTime(null);
            taskInfoDO.setPlantingPlanId(newPlanId);
            taskInfoDO.setPeasantId(null);
            taskInfoMapper.insert(taskInfoDO);
            try {
                Class<Object> objectClass = ClassUtil.loadClass(taskInfoEnum.getTypeModelName());
                Object object = objectClass.getDeclaredConstructor().newInstance();
                if (object instanceof TaskWeedBaseVO) {
                    //除草
                    TaskWeedDO taskWeedDO = taskWeedMapper.selectOne(TaskWeedDO::getAgroTaskId, id);
                    taskWeedDO = JSONObject.parseObject(JSONObject.toJSONString(taskWeedDO, excludeFilter), TaskWeedDO.class);
                    taskWeedDO.setAgroTaskId(taskInfoDO.getId());
                    taskWeedMapper.insert(taskWeedDO);
                }
                if (object instanceof TaskFertilizerBaseVO) {
                    //施肥
                    TaskFertilizerDO taskFertilizerDO = taskFertilizerMapper.selectOne(TaskFertilizerDO::getAgroTaskId, id);
                    taskFertilizerDO = JSONObject.parseObject(JSONObject.toJSONString(taskFertilizerDO, excludeFilter), TaskFertilizerDO.class);
                    taskFertilizerDO.setAgroTaskId(taskInfoDO.getId());
                    taskFertilizerMapper.insert(taskFertilizerDO);
                }
                if (object instanceof TaskIntertillBaseVO) {
                    //中耕
                    TaskIntertillDO taskIntertillDO = taskIntertillMapper.selectOne(TaskIntertillDO::getAgroTaskId, id);
                    taskIntertillDO = JSONObject.parseObject(JSONObject.toJSONString(taskIntertillDO, excludeFilter), TaskIntertillDO.class);
                    taskIntertillDO.setAgroTaskId(taskInfoDO.getId());
                    taskIntertillMapper.insert(taskIntertillDO);
                }
                if (object instanceof TaskIrrigationBaseVO) {
                    //灌溉
                    TaskIrrigationDO taskIrrigationDO = taskIrrigationMapper.selectOne(TaskIrrigationDO::getAgroTaskId, id);
                    taskIrrigationDO = JSONObject.parseObject(JSONObject.toJSONString(taskIrrigationDO, excludeFilter), TaskIrrigationDO.class);
                    taskIrrigationDO.setAgroTaskId(taskInfoDO.getId());
                    taskIrrigationMapper.insert(taskIrrigationDO);
                }
                if (object instanceof TaskPesticideBaseVO) {
                    //打药
                    TaskPesticideDO taskPesticideDO = taskPesticideMapper.selectOne(TaskPesticideDO::getAgroTaskId, id);
                    taskPesticideDO = JSONObject.parseObject(JSONObject.toJSONString(taskPesticideDO, excludeFilter), TaskPesticideDO.class);
                    taskPesticideDO.setAgroTaskId(taskInfoDO.getId());
                    taskPesticideMapper.insert(taskPesticideDO);
                }
                if (object instanceof TaskRakingBaseVO) {
                    //整地
                    TaskRakingDO taskRakingDO = taskRakingMapper.selectOne(TaskRakingDO::getAgroTaskId, id);
                    taskRakingDO = JSONObject.parseObject(JSONObject.toJSONString(taskRakingDO, excludeFilter), TaskRakingDO.class);
                    taskRakingDO.setAgroTaskId(taskInfoDO.getId());
                    taskRakingMapper.insert(taskRakingDO);
                }
                if (object instanceof TaskReapBaseVO) {
                    //收割
                    TaskReapDO taskReapDO = taskReapMapper.selectOne(TaskReapDO::getAgroTaskId, id);
                    taskReapDO = JSONObject.parseObject(JSONObject.toJSONString(taskReapDO, excludeFilter), TaskReapDO.class);
                    taskReapDO.setAgroTaskId(taskInfoDO.getId());
                    taskReapMapper.insert(taskReapDO);
                }
                if (object instanceof TaskSeedingBaseVO) {
                    //播种
                    TaskSeedingDO taskSeedingDO = taskSeedingMapper.selectOne(TaskSeedingDO::getAgroTaskId, id);
                    taskSeedingDO = JSONObject.parseObject(JSONObject.toJSONString(taskSeedingDO, excludeFilter), TaskSeedingDO.class);
                    taskSeedingDO.setAgroTaskId(taskInfoDO.getId());
                    taskSeedingMapper.insert(taskSeedingDO);
                }
            } catch (Exception e) {
                log.error("查找失败", e);
                throw new RuntimeException(e);
            }

        });
    }

    private Integer getTaskStatusList(TaskInfoRespVo taskInfoDO) {
        return getInteger(taskInfoDO.getStartDate(), taskInfoDO.getPeasantId(), taskInfoDO.getStatus(), taskInfoDO.getTaskPeriod());
    }

    private Integer getInteger(LocalDateTime startDate, Integer peasantId, Integer status, Integer taskPeriod) {
        long nowTime = System.currentTimeMillis();
        long time = DateUtil.offsetDay(Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()), taskPeriod).getTime();
        if (Objects.isNull(peasantId)) {
            //未指派
            if (time > nowTime) {
                //周期还未过期,并且未指派
                return TaskStatusEnum.NOT_APPOINT.getStatus();
            } else {
                //周期已过期,并且未指派
                return TaskStatusEnum.OVERDUE.getStatus();
            }
        } else {
            //已指派
            if (time > nowTime) {
                //此处为未过期
                if (nowTime < startDate.toInstant(ZoneOffset.UTC).toEpochMilli()) {
                    //还未到开始时间,并且已指派
                    return TaskStatusEnum.NOT_STARTED.getStatus();
                } else {
                    //已到开始时间,并且已指派
                    if (status.equals(1)) {
                        //已完成
                        return TaskStatusEnum.COMPLETED.getStatus();
                    } else {
                        //进行中
                        return TaskStatusEnum.IN_PROGRESS.getStatus();
                    }
                }
            } else {
                if (status.equals(1)) {
                    //已完成
                    return TaskStatusEnum.COMPLETED.getStatus();
                } else {
                    //已指派的情况下过期了就是未完成
                    return TaskStatusEnum.INCOMPLETE.getStatus();
                }

            }

        }
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void messageResend() {
        RLock couponLock = null;
        try {
            couponLock = redisLock.lock(TASK_INFO_MESSAGE_LOCK, 5L);
            log.info("开始执行生成农事任务消息定时任务");
            taskInfoService.taskMessage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (couponLock != null && couponLock.isLocked() && couponLock.isHeldByCurrentThread()) {
                couponLock.unlock();
            }
        }
    }

    private Integer getTaskStatus(TaskInfoDO taskInfoDO) {
        return getInteger(taskInfoDO.getStartDate(), taskInfoDO.getPeasantId(), taskInfoDO.getStatus(), taskInfoDO.getTaskPeriod());
    }
}
