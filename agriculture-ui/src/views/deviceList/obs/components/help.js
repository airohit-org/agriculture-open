import * as echarts from 'echarts';

export const createOptionEcharts = (dataTime, maximumTemperature, MinimumTemperature,Temperature1,Temperature2,Temperature3,echartDeVType,unit) => {
    if(!Temperature1 && !Temperature2 && !Temperature3&& !maximumTemperature){
        return {
            textStyle: {
                color: 'rgba(255,255,255,0.78)',
            },
            title:{
                text:echartDeVType+`(${unit})`,
                textStyle:{
                    color: '#ACC9E6',
                    fontSize:14,
                }
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
                // formatter: '<div style="text-align: center;font-size: 8px;">{a}</div><div style="text-align: center;">{c0}°C</div><div style="text-align: center;font-size: 8px;">{a1}</div><div style="text-align: center;">{c1}°C</div>'
                // formatter: `<div style="text-align: center;font-size: 8px;">{a}</div><div style="text-align: center;">{c0}°C</div>`
            },
            legend: {
                right: 0,
                top: '5',
                icon: 'rect',
                itemWidth: 15,
                itemHeight: 4,
                textStyle: {
                    color: 'rgba(255,255,255,0.7)'
                },
            },
            dataZoom: [{
                type: 'inside', //1平移 缩放
                throttle: '50', //设置触发视图刷新的频率。单位为毫秒（ms）。
                minValueSpan: 6, //用于限制窗口大小的最小值,在类目轴上可以设置为 5 表示 5 个类目
                start: 1, //数据窗口范围的起始百分比 范围是：0 ~ 100。表示 0% ~ 100%。
                end: 10, //数据窗口范围的结束百分比。范围是：0 ~ 100。
                zoomLock: true, //如果设置为 true 则锁定选择区域的大小，也就是说，只能平移，不能缩放。
            }],
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
                position:'right',
                offset:'10',
                axisLabel: {
                    formatter: '{value}',
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
                    name: echartDeVType,
                    type: 'line',
                    data: MinimumTemperature,
                    symbol: 'circle',
                    symbolSize: 6,
                    itemStyle: {
                        normal: {
                            color: '#A9D0FB',
                            borderColor: '#A9D0FB',
                            borderWidth: 1,
                            lineStyle: {
                                color: '#A9D0FB'
                            }
                        },
                        emphasis: {
                            color: '#A9D0FB',
                            borderColor: '#A9D0FB',
                            borderWidth: 2,
                        }
                    },
                    areaStyle: {
                        origin: 'start',
                        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                            {
                                offset: 0,
                                color: 'rgba(169,208,251,0.1)'
                            },
                            {
                                offset: 1,
                                color: 'rgba(169,208,251,0.5)'
                            },
                        ]),
                    },
                },
            ],
        };
    }else{
        return {
            textStyle: {
                color: 'rgba(255,255,255,0.78)',
            },
            title:{
                text:echartDeVType+`(${unit})`,
                textStyle:{
                    color: '#ACC9E6',
                    fontSize:14,
                },
                top:20,
            },
            grid: {
                top: '70px',
                right: '30px',
                bottom: '35px',
                left: '40px',
            },
            tooltip: {
                trigger: 'axis',
                padding: [15, 15, 15, 15],
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
                // formatter: '<div style="text-align: center;font-size: 8px;">{a}</div><div style="text-align: center;">{c0}°C</div><div style="text-align: center;font-size: 8px;">{a1}</div><div style="text-align: center;">{c1}°C</div>'
                // formatter: `<div style="text-align: center;font-size: 8px;">{a}</div><div style="text-align: center;">{c0}</div>
                // <div style="text-align: center;font-size: 8px;">{a1}</div><div style="text-align: center;">{c1}</div>
                // <div style="text-align: center;font-size: 8px;">{a2}</div><div style="text-align: center;">{c2}</div>
                // <div style="text-align: center;font-size: 8px;">{a3}</div><div style="text-align: center;">{c3}</div>
                // <div style="text-align: center;font-size: 8px;">{a4}</div><div style="text-align: center;">{c4}</div>
                // `
            },
            legend: {
                right: 0,
                top: '20',
                icon: 'rect',
                itemWidth: 15,
                itemHeight: 4,
                textStyle: {
                    color: 'rgba(255,255,255,0.7)'
                },
            },
            dataZoom: [{
                type: 'slider',
                realtime: true,
                startValue: 0,
                endValue: 5,
                xAxisIndex: [0],
                bottom: '5',
                left: '30',
                height: 10,
                borderColor: 'rgba(0,0,0,0)',
                textStyle: {
                    color: '#05D5FF',
                },
                start: 85, //数据窗口范围的起始百分比 范围是：0 ~ 100。表示 0% ~ 100%。
                end: 100, //数据窗口范围的结束百分比。范围是：0 ~ 100。
                // type: 'inside', //1平移 缩放
                // throttle: '50', //设置触发视图刷新的频率。单位为毫秒（ms）。
                // minValueSpan: 6, //用于限制窗口大小的最小值,在类目轴上可以设置为 5 表示 5 个类目
                // start: 90, //数据窗口范围的起始百分比 范围是：0 ~ 100。表示 0% ~ 100%。
                // end: 100, //数据窗口范围的结束百分比。范围是：0 ~ 100。
                // zoomLock: true, //如果设置为 true 则锁定选择区域的大小，也就是说，只能平移，不能缩放。
            }],
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
                    formatter: '{value}',
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
                    name: echartDeVType == '土壤水分' || echartDeVType == '土壤温度' ? '0cm':'0'+unit,
                    type: 'line',
                    data: MinimumTemperature,
                    symbol: 'circle',
                    symbolSize: 6,
                    itemStyle: {
                        normal: {
                            color: '#A9D0FB',
                            borderColor: '#A9D0FB',
                            borderWidth: 1,
                            lineStyle: {
                                color: '#A9D0FB'
                            }
                        },
                        emphasis: {
                            color: '#A9D0FB',
                            borderColor: '#A9D0FB',
                            borderWidth: 2,
                        }
                    },
                    areaStyle: {
                        origin: 'start',
                        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                            {
                                offset: 0,
                                color: 'rgba(169,208,251,0.1)'
                            },
                            {
                                offset: 1,
                                color: 'rgba(169,208,251,0.5)'
                            },
                        ]),
                    },
                },
                {
                    name: echartDeVType == '土壤水分' || echartDeVType == '土壤温度' ? '20cm':'0'+unit,
                    type: 'line',
                    data: Temperature1,
                    symbol: 'circle',
                    symbolSize: 6,
                    itemStyle: {
                        normal: {
                            color: '#94E157',
                            borderColor: '#94E157',
                            borderWidth: 1,
                            lineStyle: {
                                color: '#94E157'
                            }
                        },
                        emphasis: {
                            color: '#94E157',
                            borderColor: '#94E157',
                            borderWidth: 2,
                        }
                    },
                    areaStyle: {
                        origin: 'start',
                        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                            {
                                offset: 0,
                                color: 'rgba(148,225,87,0.1)'
                            },
                            {
                                offset: 1,
                                color: 'rgba(148,225,87,0.5)'
                            },
                        ]),
                    },
                },
                {
                    name: echartDeVType == '土壤水分' || echartDeVType == '土壤温度' ? '30cm':'0'+unit,
                    type: 'line',
                    data: Temperature2,
                    symbol: 'circle',
                    symbolSize: 6,
                    itemStyle: {
                        normal: {
                            color: '#F1D730',
                            borderColor: '#F1D730',
                            borderWidth: 1,
                            lineStyle: {
                                color: '#F1D730'
                            }
                        },
                        emphasis: {
                            color: '#F1D730',
                            borderColor: '#F1D730',
                            borderWidth: 2,
                        }
                    },
                    areaStyle: {
                        origin: 'start',
                        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                            {
                                offset: 0,
                                color: 'rgba(241,215,48,0.1)'
                            },
                            {
                                offset: 1,
                                color: 'rgba(252,215,48,0.5)'
                            },
                        ]),
                    },
                },
                {
                    name: echartDeVType == '土壤水分' || echartDeVType == '土壤温度' ? '40cm':'0'+unit,
                    type: 'line',
                    data: Temperature3,
                    symbol: 'circle',
                    symbolSize: 6,
                    itemStyle: {
                        normal: {
                            color: '#FC7641',
                            borderColor: '#FC7641',
                            borderWidth: 1,
                            lineStyle: {
                                color: '#FC7641'
                            }
                        },
                        emphasis: {
                            color: '#FC7641',
                            borderColor: '#FC7641',
                            borderWidth: 2,
                        }
                    },
                    areaStyle: {
                        origin: 'start',
                        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                            {
                                offset: 0,
                                color: 'rgba(252,118,65,0.1)'
                            },
                            {
                                offset: 1,
                                color: 'rgba(252,118,65,0.5)'
                            },
                        ]),
                    },
                },
                {
                    name: echartDeVType == '土壤水分' || echartDeVType == '土壤温度' ? '50cm':'0'+unit,
                    type: 'line',
                    symbol: 'circle',
                    symbolSize: 6,
                    data: maximumTemperature,
                    itemStyle: {
                        normal: {
                            color: '#446EF7',
                            borderColor: '#446EF7',
                            borderWidth: 1,
                            lineStyle: {
                                color: '#446EF7'
                            }
                        },
                        emphasis: {
                            color: '#446EF7',
                            borderColor: '#446EF7',
                            borderWidth: 2,
                        }
                    },
                    areaStyle: {
                        origin: 'start',
                        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                            {
                                offset: 0,
                                color: 'rgba(68,110,247,0.1)'
                            },
                            {
                                offset: 1,
                                color: 'rgba(68,110,247,0.5)'
                            }
                        ])
                    },
                },
            ],
        };
    }
    
};