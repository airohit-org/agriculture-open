package com.airohit.agriculture.module.statistics.service.taskInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import com.airohit.agriculture.module.statistics.dal.mysql.taskInfo.StatisticTaskInfoMapper;
import com.airohit.agriculture.module.statistics.enums.taskInfo.TaskStatusEnum;
import com.airohit.agriculture.module.statistics.vo.taskInfo.TaskInfoRespVo;
import com.airohit.agriculture.module.statistics.vo.taskInfo.TaskInfoStatusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/3 20:06
 */
@Service
@Slf4j
public class StatisticTaskInfoServiceImpl implements StatisticTaskInfoService {

    @Resource
    private StatisticTaskInfoMapper statisticTaskInfoMapper;

    @Override
    public List<TaskInfoStatusVo> getTaskInfoStatistic() {
        List<TaskInfoRespVo> taskInfoRespVoList = statisticTaskInfoMapper.getTaskInfoRespVoPage();
        taskInfoRespVoList.forEach(taskInfoRespVo -> taskInfoRespVo.setTaskStatus(getTaskStatusList(taskInfoRespVo)));
        int total = taskInfoRespVoList.size();
        LinkedHashMap<String, TaskStatusEnum> enumMap = EnumUtil.getEnumMap(TaskStatusEnum.class);
        List<TaskInfoStatusVo> list = new ArrayList<>();
        if (total > 0) {
            Map<Integer, List<TaskInfoRespVo>> collect = taskInfoRespVoList.stream().collect(Collectors.groupingBy(TaskInfoRespVo::getTaskStatus));
            for (Integer integer : collect.keySet()) {
                enumMap.remove(EnumUtil.toString(TaskStatusEnum.getByStatus(integer)));
                int size = Optional.ofNullable(collect.get(integer)).orElse(new ArrayList<>()).size();
                TaskInfoStatusVo taskInfoStatusVo = new TaskInfoStatusVo();
                taskInfoStatusVo.setName(TaskStatusEnum.getByStatus(integer).getName());
                taskInfoStatusVo.setValue(size);
                list.add(taskInfoStatusVo);
            }
        }
        for (String s : enumMap.keySet()) {
            TaskStatusEnum taskStatusEnum = enumMap.get(s);
            TaskInfoStatusVo taskInfoStatusVo = new TaskInfoStatusVo();
            taskInfoStatusVo.setName(taskStatusEnum.getName());
            taskInfoStatusVo.setValue(0);
            list.add(taskInfoStatusVo);
        }
        return list;
    }


    private Integer getTaskStatusList(TaskInfoRespVo taskInfoDO) {
        return getStatus(taskInfoDO.getStartDate(), taskInfoDO.getPeasantId(), taskInfoDO.getStatus(), taskInfoDO.getTaskPeriod());
    }

    private Integer getStatus(LocalDateTime startDate, Integer peasantId, Integer status, Integer taskPeriod) {
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
                    return com.airohit.agriculture.module.plant.enums.taskInfo.TaskStatusEnum.COMPLETED.getStatus();
                } else {
                    //已指派的情况下过期了就是未完成
                    return com.airohit.agriculture.module.plant.enums.taskInfo.TaskStatusEnum.INCOMPLETE.getStatus();
                }
            }

        }
    }
}
