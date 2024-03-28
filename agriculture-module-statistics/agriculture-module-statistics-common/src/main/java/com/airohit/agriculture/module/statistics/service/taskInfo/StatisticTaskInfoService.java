package com.airohit.agriculture.module.statistics.service.taskInfo;

import com.airohit.agriculture.module.statistics.vo.taskInfo.TaskInfoStatusVo;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/3 20:06
 */
public interface StatisticTaskInfoService {

    /**
     * 获得农事任务统计
     *
     * @return 农事任务统计
     */
    List<TaskInfoStatusVo> getTaskInfoStatistic();


}
