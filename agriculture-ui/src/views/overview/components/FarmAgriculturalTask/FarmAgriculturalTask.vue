<template>
  <div class="agricultural-task">
    <OverviewTitle title="农事任务" />
    <div class="content" v-show="list.length">
      <div class="left">
        <div v-for="{ name, value, color, percentValue } in list" class="item">
          <div :style="`--color: ${color};--bgcolor: ${color}33`">
            <span></span>
          </div>
          <div>{{ name }}</div>
          <div :style="`--color: ${color};`">{{ isNaN(percentValue)?0:percentValue }}%</div>
        </div>
      </div>
      <div class="right">
        <img src="https://oss.airoteach.cn/5b03cc088b0fe8cc1d38ba9f9a020a9ec42e242b12236274be808e2e33ab8927.png" alt="">
        <div class="echart-container" ref="echartsRef"></div>
      </div>
    </div>
    <Empty v-show="!list.length" />
  </div>
</template>

<script setup>
import { createOption } from './help';
import * as echarts from 'echarts';
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import Empty from '@/components/Empty/index.vue'

import { ref, onMounted, watch } from 'vue'

const props = defineProps({
  list: {
    type: Array,
    default: () => []
  }
})

const echartsRef = ref()
const echartEle = ref()
const initEchart = () => {
  !echartEle.value && (
    nextTick(()=>{
      echartEle.value = echarts.init(echartsRef.value)
    })
  )
}
const setOption = () => {
  initEchart()
  echartEle?.value.setOption(createOption(props.list))
}

onMounted(() => {
  initEchart()
})

watch(()=>props.list,()=>{
  props.list && setOption()
},{deep:true})

</script>

<style lang="scss" scoped>

$scale: 1.2;
.agricultural-task {
  height: 100%;
  .content {
    margin-top: 17.31px;
    display: flex;
    margin-left: 1px;
    .left {
      width: 181.36px;
      display: grid;
      grid-template-rows: repeat(6, 1fr);
      gap: 9.16px;
      .item {
        width: 100%;
        display: flex;
        background: linear-gradient(90deg, rgba(0, 178, 255, 0.15) 0%, rgba(0, 178, 255, 0) 100%);
        border-bottom: 1px solid rgba(255, 255, 255, 0.2);
        align-items: center;
        padding-left: 9.16px;
        padding-right: 28.7px;

        div {
          &:nth-child(1) {
            box-sizing: content-box;
            background-color: var(--bgcolor);
            border: 1px solid var(--color);
            width: 12.82px;
            height: 12.82px;
            display: flex;
            align-items: center;
            justify-content: center;
            span {
              display: block;
              width: 5.5px;
              height: 5.5px;
              background-color: var(--color);
            }
          }
          &:nth-child(2) {
            width: 87px;
            margin-left: 3.66px;
            font-style: normal;
            font-weight: 400;
            font-size: 14.6553px * $scale;
            color: #FFFFFF;
          }
          &:nth-child(3) {
            font-style: normal;
            font-weight: 700;
            font-size: 14.6553px * $scale;
            line-height: 17px;
            color: var(--color);
          }
        }
      }
    }

    .right {
      margin-left: 32px;
      // background-size: cover;
      // background-repeat: no-repeat;
      display: flex;
      align-items: center;
      justify-content: center;
      width: 195.29px;
      height: 184.12px;
      position: relative;

      img {
        width: 100%;
        height: 100%;
        position: absolute;
        animation: rotate 5s linear infinite;
      }
      .echart-container {
        width: 157.5px;
        height: 157.5px;
      }
    }
  }
}

@keyframes rotate {
  0%{
    transform: rotate(0deg);
  }
  100%{
    transform: rotate(360deg);
  }
}
</style>
