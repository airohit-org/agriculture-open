import * as echarts from 'echarts';

export const createOptionEcharts = (dataTime,maximumTemperature,MinimumTemperature) => {
    return {
        textStyle: {
            color: 'rgba(255,255,255,0.78)',
        },
        grid: {
            top: '70px',
            right: '30px',
            bottom: '35px',
            left: '40px',
        },
        tooltip: {
            trigger: 'axis',
            padding: [0, 15, 0, 15],
            backgroundColor: '#021D1F',
            borderColor: '#006871',
            width: 55,
            position: 'top',
            axisPointer: {
                type: 'line',
                lineStyle: {
                    type: 'dotted',
                    width: 0.5,
                    color: 'rgba(255,255,255,0.78)',
                    cap: 'none',
                },
            },
            textStyle: {
                color: 'rgba(255,255,255,0.78)',
                fontSize: 15,
                fontFamily: 'PingFang TC-Medium, PingFang TC',
            },
            extraCssText: 'box-shadow: 0px 0px 12px 0px rgba(0,228,247,0.36);',
            formatter: '<div style="text-align: center;font-size: 8px;">{a}</div><div style="text-align: center;">{c0}℃</div><div style="text-align: center;font-size: 8px;">{a1}</div><div style="text-align: center;">{c1}℃</div>'
        },
        legend: {
            right: 0,
            top: '5',
            textStyle: {
                color: 'rgba(255,255,255,0.7)'
            }
        },
        xAxis: {
            type: 'category',
            data: dataTime,
            axisPointer: {
                show: true,
            },
            axisTick: {
                show: false,
                alignWithLabel: true,
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(255,255,255,0.25)',
                }
            },
            axisLabel: {
                fontFamily: 'DIN Alternate-Bold, DIN Alternate',
                interval: 0,
            },
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} °C',
                color: '#fff',
                fontFamily: 'DIN Alternate-Bold, DIN Alternate'
            },
            splitLine: {
                lineStyle: {
                    color: ['rgba(255,255,255,0.25)']
                }
            }
        },
        series: [
            {
                name: '最高温度',
                type: 'line',
                symbol: 'circle',
                symbolSize: 6,
                data: maximumTemperature,
                itemStyle: {
                    normal: {
                        color: '#200202',
                        borderColor: '#FCF366',
                        borderWidth: 1,
                        lineStyle: {
                            color: '#FCF366'
                        }
                    },
                    emphasis: {
                        color: '#FCF366',
                        borderColor: '#FCF366',
                        borderWidth: 2,
                    }
                },
                areaStyle: {
                    origin: 'start',
                    color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                        {
                            offset: 0,
                            color: 'rgba(252,243,102,0.1)'
                        },
                        {
                            offset: 1,
                            color: 'rgba(252,243,102,0.3)'
                        }
                    ])
                },
            },
            {
                name: '最低温度',
                type: 'line',
                data: MinimumTemperature,
                symbol: 'circle',
                symbolSize: 6,
                itemStyle: {
                    normal: {
                        color: '#200202',
                        borderColor: '#00E4F7',
                        borderWidth: 1,
                        lineStyle: {
                            color: '#00E4F7'
                        }
                    },
                    emphasis: {
                        color: '#00E4F7',
                        borderColor: '#00E4F7',
                        borderWidth: 2,
                    }
                },
                areaStyle: {
                    origin: 'start',
                    color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                        {
                            offset: 0,
                            color: 'rgba(0,228,247,0.1)'
                        },
                        {
                            offset: 1,
                            color: 'rgba(0,228,247,0.5)'
                        },
                    ]),
                },
            },
        ],
    };
};