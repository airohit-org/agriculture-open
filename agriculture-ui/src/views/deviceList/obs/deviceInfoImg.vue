<template>
  <div class="contrain">
    <div class="left">
      <div class="left-bottomImg"></div>
      <infos
        :key="1"
        v-if="co2Arr?.length > 0"
        :title="'二氧化碳传感器'"
        :list="co2Arr"
        class="infos1"
        @hasChange="hasChange"
      >
      </infos>
      <infos
        :key="2"
        v-if="landArr?.length > 0"
        :title="'土壤温度水分传感器'"
        :list="landArr"
        class="infos2"
        @hasChange="hasChange"
      ></infos>
      <infos
        :key="3"
        v-if="windArr?.length > 0"
        :title="'温湿压风速风向两尘传感器'"
        :list="windArr"
        class="infos3"
        @hasChange="hasChange"
      ></infos>
      <infos
        :key="4"
        v-if="rainArr?.length > 0"
        :title="'雨量传感器'"
        :list="rainArr"
        class="infos4"
        @hasChange="hasChange"
      >
      </infos>
      <infos
        :key="5"
        v-if="lightArr?.length > 0"
        :title="'光照传感器'"
        :list="lightArr"
        class="infos5"
        @hasChange="hasChange"
      >
      </infos>

      <div class="top">
        <div class="name"></div>
        <div class="more" @click="goMore('info')">更多</div>
      </div>
      <div class="deviceImg"></div>
    </div>
    <div class="right">
      <div class="right-top">
        <OverviewTitle style="width: 556px" :title="'监控图片'" />
        <div class="moreImg" @click="goMore('img')">更多图片</div>
        <div class="topImg">
          <el-carousel height="289px">
            <el-carousel-item
              v-for="item in imglist"
              :key="item.name + '-' + new Date()"
            >
              <img :src="item.value" alt="" />
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <div class="right-bottom">
        <OverviewTitle style="width: 556px" :title="'数据走势图'" />
        <echartData
          class="echartData"
          :echartsInfo="echartsInfo"
          :echartDeVType="echartDeVType"
        ></echartData>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  queryBeijingTHDeviceInfoByDeviceId,
  queryBeijingTHDeviceInfo,
} from "@/api/obs/deviceInfo";
import infos from "./components/infos.vue";
import echartData from "./components/echartData.vue";
import OverviewTitle from "@/components/OverviewTitle/index.vue";
defineOptions({ name: "deviceInfoImg" });
const echartsInfo = {
  maximumTemperature: [],
  minimumTemperature: [],
  Temperature1: [],
  Temperature2: [],
  Temperature3: [],
  dataTime: [],
};
const echartsAll = ref([]);
const echartDeVType = "土壤温度";
const queryParam = {
  deviceId: "",
};
const queryParams = {
  deviceId: "",
  startAt: "",
  endtAt: "",
};
const imglist = ref([]);
const co2Arr = ref([]);
const landArr = ref([]);
const rainArr = ref([]);
const lightArr = ref([]);
const windArr = ref([]);
const allList = ref([]);

async function getList() {
  let res = await queryBeijingTHDeviceInfo(this.queryParams);
  let arr = [];
  arr = res.data;
  this.echartsAll = arr;
  const dataTime = [];
  const minimumTemperature = [];
  const maximumTemperature = [];
  const Temperature1 = [];
  const Temperature2 = [];
  const Temperature3 = [];
  arr.forEach((item) => {
    dataTime.push(this.parseTime(item.createTime).split(" ")[1]);
    minimumTemperature.push(
      item.data.filter((ele) => ele.name == "土壤温度")[0].value
    );
    maximumTemperature.push(
      item.data.filter((ele) => ele.name == "土壤温度50cm")[0].value
    );
    Temperature1.push(
      item.data.filter((ele) => ele.name == "土壤温度20cm")[0].value
    );
    Temperature2.push(
      item.data.filter((ele) => ele.name == "土壤温度30cm")[0].value
    );
    Temperature3.push(
      item.data.filter((ele) => ele.name == "土壤温度40cm")[0].value
    );
  });
  this.echartsInfo.dataTime = dataTime;
  this.echartsInfo.minimumTemperature = minimumTemperature;
  this.echartsInfo.maximumTemperature = maximumTemperature;
  this.echartsInfo.Temperature1 = Temperature1;
  this.echartsInfo.Temperature2 = Temperature2;
  this.echartsInfo.Temperature3 = Temperature3;
}
async function getDetail() {
  let res = await queryBeijingTHDeviceInfoByDeviceId(this.queryParam);
  this.allList = res.data?.obsDeviceInfoVoData?.data;
  this.imglist = res.data?.obsDeviceInfoVoImage?.data;
  this.co2Arr = this.regFilterArr("二氧化碳传感器");
  this.landArr = this.regFilterArr("土壤温度水分传感器");
  this.windArr = this.regFilterArr("温湿压风速风向两尘传感器");
  this.rainArr = this.regFilterArr("雨量传感器");
  this.lightArr = this.regFilterArr("光照传感器");
}
function regFilterArr(val) {
  const key = +new Date();
  let result = this.allList?.filter(
    (item, index) => item.nodeSub.includes(val) && (item.id = index + "=" + key)
  );
  if (result.length === 0) return [];

  const sortKey =
    val === "土壤温度水分传感器" || val === "温湿压风速风向两尘传感器"
      ? "name"
      : undefined;

  if (sortKey) {
    result.sort((a, b) => {
      if (a[sortKey] > b[sortKey]) {
        return 1;
      }
      if (a[sortKey] < b[sortKey]) {
        return -1;
      }
      return 0;
    });
  }
  if (val === "温湿压风速风向两尘传感器") {
    const arr1 = result.slice(0, 2);
    const arr2 = result.slice(2, 5);
    const arr3 = result.filter(
      (item) => item.name == "风向" || item.name == "风速"
    );
    result = [...arr1, ...arr3, ...arr2];
  }
  return result;
}

function echartyAxisFilter(val) {
  const result = [];
  this.echartsAll.forEach((ele) => {
    result.push(ele.data.filter((ele) => ele.name == val)[0].value);
  });
  return result;
}
function dealEchartData(val) {
  const minimumTemperature = [];
  const maximumTemperature = [];
  const Temperature1 = [];
  const Temperature2 = [];
  const Temperature3 = [];
  let nameArr = [
    "土壤温度",
    "土壤温度20cm",
    "土壤温度30cm",
    "土壤温度40cm",
    "土壤温度50cm",
  ];
  if (val == 1) {
    nameArr = [
      "土壤水分",
      "土壤水分20cm",
      "土壤水分30cm",
      "土壤水分40cm",
      "土壤水分50cm",
    ];
  }
  this.echartsAll.forEach((item) => {
    minimumTemperature.push(
      item.data.filter((ele) => ele.name == nameArr[0])[0].value
    );
    Temperature1.push(
      item.data.filter((ele) => ele.name == nameArr[1])[0].value
    );
    Temperature2.push(
      item.data.filter((ele) => ele.name == nameArr[2])[0].value
    );
    Temperature3.push(
      item.data.filter((ele) => ele.name == nameArr[3])[0].value
    );
    maximumTemperature.push(
      item.data.filter((ele) => ele.name == nameArr[4])[0].value
    );
  });
  this.echartsInfo.minimumTemperature = minimumTemperature;
  this.echartsInfo.maximumTemperature = maximumTemperature;
  this.echartsInfo.Temperature1 = Temperature1;
  this.echartsInfo.Temperature2 = Temperature2;
  this.echartsInfo.Temperature3 = Temperature3;
}
function hasChange(item) {
  if (item.name.includes("土壤温度") || item.name.includes("土壤水分")) {
    this.echartDeVType = item.name.slice(0, 4);
    try {
      if (item.name.includes("土壤温度")) {
        this.dealEchartData(0);
      } else {
        this.dealEchartData(1);
      }
    } catch (error) {
      console.log(error);
    }
  } else {
    this.echartDeVType = item.name;
    try {
      this.echartsInfo.minimumTemperature = this.echartyAxisFilter(item.name);
      this.echartsInfo.maximumTemperature = null;
      this.echartsInfo.Temperature1 = null;
      this.echartsInfo.Temperature2 = null;
      this.echartsInfo.Temperature3 = null;
    } catch (error) {
      console.log(error);
    }
  }
}
function goMore(val) {
  const row = {
    id: window.localStorage.getItem("deviceId"),
  };
  if (val == "info") {
    this.$router.push({
      name: "deviceDataInfo",
      params: {
        row: row,
      },
    });
  } else if (val == "img") {
    this.$router.push({
      name: "deviceImgList",
      params: {
        row: row,
      },
    });
  }
}

this.queryParam.deviceId = this.$route.params.row?.id
  ? this.$route.params.row.id
  : window.localStorage.getItem("deviceId");
this.queryParams.deviceId = this.$route.params.row?.id
  ? this.$route.params.row.id
  : window.localStorage.getItem("deviceId");
this.queryParams.startAt = this.parseTime(new Date(), "{y}-{m}-{d}");
this.queryParams.endtAt = this.parseTime(
  +new Date() + 24 * 3600 * 1000,
  "{y}-{m}-{d}"
);
this.getDetail();
this.getList();
// beforeRouteLeave(to, from, next) {
//   if (to.name !== 'deviceInfoImg') {
//     if (this.$vnode && this.$vnode.data.keepAlive) {
//       if (this.$vnode.parent && this.$vnode.parent.componentInstance && this.$vnode.parent.componentInstance.cache) {
//         if (this.$vnode.componentOptions) {
//           var key = this.$vnode.key == null
//             ? this.$vnode.componentOptions.Ctor.cid + (this.$vnode.componentOptions.tag ? `::${this.$vnode.componentOptions.tag}` : '')
//             : this.$vnode.key
//           var cache = this.$vnode.parent.componentInstance.cache
//           var keys = this.$vnode.parent.componentInstance.keys
//           if (cache[key]) {
//             if (keys.length) {
//               var index = keys.indexOf(key)
//               if (index > -1) {
//                 keys.splice(index, 1)
//               }
//             }
//             delete cache[key]
//           }
//         }
//       }
//     }
//     this.$destroy()
//   }
//   next()
// },
</script>

<style scoped lang="scss">
.contrain {
  width: 100%;
  height: calc(100vh - 96px);
  background-image: url("../../../assets/images/device/bg.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  display: flex;

  .left {
    margin-top: 40px;
    flex: 1;
    margin-bottom: 90px;
    margin-left: 60px;
    background-image: url("../../../assets/images/device/border.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    position: relative;

    &-bottomImg {
      position: absolute;
      width: 1073px;
      height: 750px;
      z-index: 1;
      left: 50%;
      transform: translateX(-50%);
      bottom: -90px;
      background-image: url("https://oss.airoteach.cn/36bcac45b1538d87e5cdc353b0426cd79019ffb796e3b0edec38907129a6efe1.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
    }

    .infos1 {
      width: 208px;
      height: 86px;
      z-index: 1;
      left: 90px;
      top: 140px;
      position: absolute;
    }

    .infos2 {
      width: 241px;
      height: 376px;
      z-index: 1;
      left: 76px;
      top: 260px;
      position: absolute;
    }

    .infos3 {
      width: 241px;
      height: 280px;
      z-index: 1;
      right: 60px;
      top: 140px;
      position: absolute;
    }

    .infos4 {
      width: 161px;
      height: 86px;
      z-index: 1;
      right: 100px;
      top: 460px;
      position: absolute;
    }

    .infos5 {
      width: 178px;
      height: 86px;
      z-index: 1;
      right: 94px;
      top: 580px;
      position: absolute;
    }

    .top {
      display: flex;
      justify-content: space-between;

      .name {
        margin-top: 30px;
        margin-left: 40px;
        width: 243px;
        height: 73px;
        background-image: url("https://oss.airoteach.cn/0f2d201311cd025ee0a6d5b19e4399b411abd086264e371e82e5f2112c502557.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
      }

      .more {
        cursor: pointer;
        position: absolute;
        z-index: 20;
        top: 45px;
        right: 40px;
        background-image: url("https://oss.airoteach.cn/363efafe6af615c0a3106434bfb82f246bd09ad9f2f09e17912c5ccbbbc3d2af.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
        width: 115px;
        text-align: center;
        height: 43px;
        font-size: 16px;
        font-family: Source Han Sans CN-Bold, Source Han Sans CN;
        font-weight: bold;
        color: #ffffff;
        line-height: 43px;
      }
    }

    .deviceImg {
      width: 406px;
      height: 544px;
      position: absolute;
      top: 100px;
      left: 50%;
      z-index: 2;
      transform: translateX(-50%);
      background-image: url("https://oss.airoteach.cn/b6799383bf1483c09f86530be8490d3058071f9a2296058ead331b69e6a80570.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
    }
  }

  .right {
    margin-left: 40px;
    margin-top: 40px;
    margin-right: 50px;
    width: 556px;

    &-top {
      width: 100%;
      height: 339px;
      position: relative;

      .moreImg {
        text-align: center;
        height: 30px;
        font-size: 16px;
        font-weight: 400;
        color: #ffffff;
        line-height: 30px;
        cursor: pointer;
        position: absolute;
        z-index: 1;
        right: 20px;
        top: 10px;
      }

      .topImg {
        width: 100%;
        height: 289px;
        background-image: url("../../../assets/images/device/rightbg.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;

        img {
          margin-top: 14px;
          margin-left: 20px;
          width: 515px;
          height: 253px;
        }
      }

      :deep(.el-carousel__button) {
        width: 6px;
        height: 6px;
        background-color: transparent;
        border-radius: 50%;
        margin-left: 7px;
        margin-bottom: 30px;
        border: 1px solid #1fface;
        opacity: 1;
      }

      :deep(.el-carousel__indicator.is-active button) {
        background-color: #1fface;
      }

      :deep(
          .el-carousel__indicators.el-carousel__indicators--horizontal
            li:first-child
            .el-carousel__button
        ) {
        margin-left: 0;
      }
    }

    &-bottom {
      margin-top: 20px;
      width: 100%;
      height: 470px;

      .echartData {
        width: 556px;
        height: 350px;
      }
    }
  }
}
</style>
