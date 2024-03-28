<template>
    <div class="EmergenceRate">
        <OverviewTitle title="出苗率" />
        <div class="EmergenceRateEcharts" id="EmergenceRateEcharts" ref="echartsRef"></div>
    </div>
</template>
  
<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import * as echarts from "echarts";
import { onMounted, ref } from 'vue'

const props = defineProps({
  loading: Boolean  
})

const emergenceEcharts = ref(null)
const echartsRef = ref()
const getEmergenceRate = () => {
    emergenceEcharts.value = echarts.init(echartsRef.value)
    emergenceEcharts.value.setOption({
        legend: {
            align: 'right',
            right: '10%',
            icon: 'circle',
            textStyle: {
                color: '#fff',
                fontSize: 12,
                fontFamily: 'PingFang SC-Medium, PingFang SC',
            },
        },
        tooltip: {},
        dataset: {
            // source: EmergenceRateList,
            source: [
                ['product', '小麦', '玉米', '水稻'],
                ['04.23', 15, 49, 90],
                ['04.28', 40, 90, 71],
                ['05.03', 10, 75, 37],
                ['05.08', 70, 50, 110],
                ['05.13', 35, 60, 105],
                ['05.18', 50, 75, 10],
            ],
        },
        xAxis: {
            type: 'category',
            axisTick: {
                show: false,
                lineStyle: {
                    width: 1.06,
                    color: 'rgba(255,255,255,0.32)',
                }
            },
            axisLabel: {
                color: '#fff',
                fontSize: 14,
                fontFamily: 'DIN Alternate-Bold, DIN Alternate',
            },
        },
        yAxis: {
            axisLine: {
                show: true,
                lineStyle: {
                    width: 1.06,
                    color: 'rgba(255,255,255,0.32)',
                }
            },
            splitLine: {
                lineStyle: {
                    type: 'dashed',
                    color: 'rgba(255,255,255,0.32)',
                }
            },
            axisLabel: {
                interval: '1',
                color: '#fff',
                fontSize: 14,
                fontFamily: 'DIN Alternate-Bold, DIN Alternate',
            },
        },
        series: [{
            itemStyle: {
                color: '#3E9CFF',
            },
            type: 'bar',
        }, {
            itemStyle: {
                color: '#FCF366',
            },
            type: 'bar',
        }, {
            itemStyle: {
                color: '#1FFACE',
            },
            type: 'bar',
        }]
    })
}

onMounted(() => {
    getEmergenceRate();
})
</script>

<style lang="scss" scoped>
.EmergenceRateEcharts{
    width: 450px;
    height: 250px;
}
</style>
