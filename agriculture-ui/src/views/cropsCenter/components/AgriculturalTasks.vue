<template>
  <div class="AgriculturalTasks">
      <OverviewTitle title="农事任务" />
      <div class="contain">
          <div class="contain-tasks" v-for="{ id, taskName, percentage, color } in TasksInfo" :key="id">
              <div class="contain-tasks-name" :style="`--color: ${color}`">{{ taskName }}</div>
              <div class="contain-tasks-progressBar">
                  <div class="contain-tasks-progressBar-progress" :style="`--color: ${color};--percentage: ${percentage}%`">
                      <div class="contain-tasks-progressBar-progress-inner">
                          <div class="contain-tasks-progressBar-block">
                              <span></span>
                          </div>
                      </div>
                  </div>
                  
              </div>
              <div class="contain-tasks-percentage" :style="`--color: ${color}`">{{ percentage }}%</div>
          </div>
      </div>
  </div>
</template>

<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";

const props = defineProps({
  loading: Boolean,
})

const TasksInfo = [{
    id: 1,
    taskName: '未指派',
    percentage: 85.49,
    color: "#1FFACE",
}, {
    id: 2,
    taskName: '未开始',
    percentage: 30.56,
    color: "#08D2FA",
}, {
    id: 3,
    taskName: '进行中',
    percentage: 38.24,
    color: "#FCF366",
}, {
    id: 4,
    taskName: '已完成',
    percentage: 45.40,
    color: "#6DDF80",
}, {
    id: 5,
    taskName: '未完成',
    percentage: 15.39,
    color: "#FF9500",
}, {
    id: 6,
    taskName: '逾期',
    percentage: 5.37,
    color: "#fff",
}]
</script>

<style lang="scss" scoped>
.AgriculturalTasks {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
}
.contain {
  margin-left: 36px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  // margin-top: 37px;
  // margin-left: 36px;
  &-tasks {
      font-size: 16px;
      font-family: PingFang SC-Medium, PingFang SC;
      display: flex;

      &-name {
          width: 50px;
          color: var(--color);
          margin-right: 14px;
      }

      &-progressBar {
          display: flex;
          align-items: center;
          overflow-x: hidden;

          &-progress {
              width: 250px;
              height: 10px;
              background: rgba(255, 255, 255, 0.1);
              position: relative;

              &-inner {
                  width: 100%;
                  position: absolute;
                  top: 50%;
                  transform: translateY(-50%);
                  right: calc(100% - var(--percentage));
                  height: 4px;
                  background: var(--color);
              }
          }

          &-block {
              position: absolute;
              right: 0;
              top: 50%;
              transform: translateY(-50%) translateX(50%);

              width: 14px;
              height: 14px;
              border: 1px solid #fff;
              display: flex;
              align-items: center;
              justify-content: center;

              span {
                  height: 6px;
                  width: 6px;
                  background-color: #fff;
              }
          }
      }

      &-percentage {
          color: var(--color);
      }
  }
}
</style>