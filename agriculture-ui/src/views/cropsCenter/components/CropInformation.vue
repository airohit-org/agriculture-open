<template>
  <div class="crop-infor">
      <OverviewTitle title="作物信息" />
      <div class="text">单位: 万亩</div>
      <div class="content">
          <div class="list" v-for="item in list">
              <img class="img" :src="item.img" alt="">
              <div class="title">{{ item.title }}</div>
              <div class="aera">{{ item.aera }}</div>
          </div>
      </div>
  </div>
</template>

<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";

const props = defineProps({
  loading: Boolean  
})

const list = [
    {
        img: 'https://oss.airoteach.cn/c6fd2ecd313ad97076a3bb839508ca9d641ccee49650e0e234b144d535fbccb4.png',
        title: '玉米',
        aera: '780.9'
    },
    {
        img: 'https://oss.airoteach.cn/d670c40f160ad3aa4143d3fc28c51dcd8d8d6a9a172b032b5d11a5c55e33b2b3.png',
        title: '大豆',
        aera: '2157.9'
    },
    {
        img: 'https://oss.airoteach.cn/7ccdd565fb64e2de252b12306d13ddb3e6a3e0d48d4eb5dbc89fed191ca32131.png',
        title: '水稻',
        aera: '18.3'
    }
]
</script>

<style lang="scss" scoped>
.crop-infor {
  width: 100%;
  .text {
      text-align: right;
      font-size: 12px;
      font-family: PingFang SC-Regular, PingFang SC;
      font-weight: 400;
      color: #FFFFFF;
  }
  .content {
      display: flex;
      justify-content: space-between;
      .list {
          margin-left: 35px;
          .img {
              width: 76px;
              height: 91px;
          }
          .title {
              text-align: center;
              font-size: 16px;
              font-family: FZZongYi-M05S-Regular, FZZongYi-M05S;
              font-weight: 400;
              color: #FFFFFF;
          }
          .aera {
              margin-top: 11px;
              width: 88px;
              height: 35px;
              background: rgba(0,0,0,0.3);
              border: 1px solid #7BFFFC;
              font-size: 16px;
              font-family: LCDMono2-Ultra, LCDMono2;
              font-weight: normal;
              color: #7BFFFC;
              display: flex;
              align-items: center;
              justify-content: center;
          }
      }
  }
}
</style>
