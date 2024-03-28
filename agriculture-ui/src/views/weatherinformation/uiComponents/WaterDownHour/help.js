export const createWaterEcharts = (rainfallInfo,timeDataInfo) => {
    return{
        grid: {
            top: '70px',
            right: '30px',
            bottom: '35px',
            left: '52px',
        },
        xAxis: {
            type: 'category',
            data: timeDataInfo,
            boundaryGap: false,
            axisTick: {
                show: false,
                interval: '2'
            },
            axisLabel: {
                color: '#fff',
                fontFamily: 'DIN'
            },
            axisLine: {
                lineStyle: {
                    color: ['rgba(255,255,255,0.25)']
                }
            }
        },
        yAxis: {
            type: 'value',
            axisTick: {
                show: false
            },
            splitLine: {
                lineStyle: {
                    color: ['rgba(255,255,255,0.25)']
                }
            },
            axisLabel: {
                margin: '14',
                hideOverlap: false,
                color: '#fff',
                fontFamily: 'DIN',
                formatter: '{value}mm'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                label: {
                    backgroundColor: '#6a7985'
                }
            },
            formatter: (params) => {
                return `<div style="font-size: 11px;text-align: center;color: #fff;"> 
                    ${params[0].axisValue}</div><div style="font-size: 9px;text-align: center;color: #fff;">
                                ${(function () {
                        if (params[0].data >= 0 && params[0].data < 10) {
                            return '小雨';
                        } else if (params[0].data >= 10 && params[0].data < 25) {
                            return '中雨';
                        } else if (params[0].data >= 25 && params[0].data < 50) {
                            return '大雨';
                        } else if (params[0].data >= 50 && params[0].data < 100) {
                            return '暴雨';
                        } else if (params[0].data > 100) {
                            return '大暴雨';
                        }
                    })()} </div>`;
            },
            backgroundColor: '#021D1F',
            extraCssText: 'box-shadow: 0px 0px 12px 0px rgba(0,228,247,0.36);',
            borderColor: 'rgba(0,228,247,0.36)',
            textStyle: {
                width: 100
            }
        },
        series: [
            {
                data: rainfallInfo,
                type: 'line',
                smooth: true,
                showSymbol: false,
                itemStyle: {
                    color: '#1FFACE'
                }
            }
        ]
    }
}