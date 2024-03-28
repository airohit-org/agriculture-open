// import { insertBetween } from "@/utils";
// import { AGRICULTURE_TASK_COLOR_LIST } from "./config";

export const createOption = (_data = []) => {
  const data = insertBetween(_data, { name: "", value: 1 }) || [];
  const sum = _data.reduce((prev,cur) => {
    return prev + cur.value
  },0)
  return {
    grid: {
      left: 0,
      right: 0,
      top: 0,
      bottom: 0,
    },
    title: {
      //圆环中间内容
      text: sum,
      subtext: "统计数",
      left: "center",
      top: "34%",
      textStyle: {
        color: "#fff",
        fontSize: 32,
        align: "center",
      },
      subtextStyle: {
        fontSize: 16,
        fontWeight: "700",
        align: "center",
        color: "#fff",
      },
    },
    tooltip: {
      trigger: "item",
    },
    // color: insertBetween(AGRICULTURE_TASK_COLOR_LIST),
    series: [
      {
        type: "pie",
        radius: ["75%", "82%"],
        label: {
          show: false,
        },
        data: data.map(({color, ...item}) => ({
            itemStyle: {
              normal: {
                borderWidth: 5,
                shadowBlur: 20,
                borderColor: color,
                shadowColor: color,
              },
            },
            color,
            ...item,
          })
        ),
      },
    ],
  };
};

export const insertBetween = (arr = [], value) => (
  arr.map(item => [item, value]).flat(1)
)