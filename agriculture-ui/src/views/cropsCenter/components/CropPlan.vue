<template>
  <div class="content">
    <div class="title">种植流程</div>
    <div class="plans">
      <div v-for="item in list" class="item" @click="$emit('handleClick', item)">
        <img :src="item.imgUrl" class="img" alt="">
        <div class="task">{{ item.task }}</div>
        <div class="con">
          <span class="icon"></span>
          <label class="txt">{{ item.crop }}</label>
        </div>
        <div class="cropname">
          <span>品种名称：</span>
          <span>{{ item.croptype }}</span>
        </div>
        <div class="state">
          <span>阶段：</span>
          <span v-for="(ele, index) in 6">
            <span v-if="item.stateIndex != index" class="start"></span>
            <span v-else class="index"></span>
            <span v-if="index != 5" class="other">| | | | |</span>
          </span>
        </div>
        <div class="stateIndex">
          <span v-for="ele in (item.stateIndex+1)" class="span"></span>
          <span>{{ item.state }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  list: {
    type: Array,
    required: true,
  }
})
</script>

<style scoped lang="scss">
.content {
  width: 100%;
  height: 100%;

  .title {
    height: 40px;
    background: linear-gradient(90deg, rgba(0, 175, 255, 0.06) 0%, rgba(7, 192, 244, 0.26) 18%, rgba(31, 250, 206, 0.77) 52%, rgba(31, 250, 206, 0) 100%);
    border-radius: 0px 0px 0px 0px;
    line-height: 40px;
    font-size: 32px;
    font-family: YouSheBiaoTiHei-Regular, YouSheBiaoTiHei;
    font-weight: 400;
    color: #FFFFFF;
    text-align: center;
  }

  .plans {
    display: flex;
    margin-top: 14px;

    .item {
      cursor: pointer;
      display: flex;
      flex: 1;
      height: 137px;
      position: relative;
      margin-right: 20px;
      border-radius: 12px 12px 12px 12px;
      background-image: url('../../../../assets/images/plan/planitembg.png');
      background-repeat: no-repeat;
      background-size: 100% 100%;

      .img {
        display: inline-block;
        margin-top: 20px;
        margin-left: 10px;
        width: 96px;
        height: 96px;
      }

      .task {
        position: absolute;
        top: 0;
        right: 0;
        width: 107px;
        height: 38px;
        line-height: 38px;
        text-align: center;
        background: #7BFFFC;
        border-radius: 0 12px 0 12px;
        font-size: 16px;
        font-family: YouSheBiaoTiHei-Regular, YouSheBiaoTiHei;
        font-weight: 400;
        color: #1D362E;
      }

      .con {
        display: flex;
        width: 176px;
        height: 25px;
        line-height: 25px;
        font-size: 14px;
        font-family: FZZongYi-M05S-Regular, FZZongYi-M05S;
        font-weight: 400;
        color: #FFFFFF;
        background: linear-gradient(90deg, rgba(255, 255, 255, 0.53) 0%, rgba(217, 217, 217, 0) 100%);
        margin-top: 18px;

        .icon {
          display: inline-block;
          width: 7px;
          height: 25px;
          background: #FCF366;
        }
        .txt{
          margin-left: 10px;
        }

      }

      .cropname {
        position: absolute;
        bottom: 68px;
        left: 110px;
        height: 16px;
        line-height: 16px;
        font-size: 14px !important;
        font-family: PingFang SC-Regular, PingFang SC;
        font-weight: 400;
        color: #FFFFFF;
      }

      .state {
        height: 16px;
        line-height: 16px;
        font-size: 14px !important;
        font-family: PingFang SC-Regular, PingFang SC;
        font-weight: 400;
        color: #FFFFFF;
        position: absolute;
        bottom: 45px;
        left: 110px;

        .start {
          display: inline-block;
          width: 2px;
          height: 10px;
          background: #1FFACE;
          border-radius: 0px 0px 0px 0px;
          margin-left: 6px;
        }

        .index {
          display: inline-block;
          width: 2px;
          height: 14px;
          background: #FCF366;
          border-radius: 0px 0px 0px 0px;
          margin-left: 6px;
          transform: translateY(2px);
        }

        .other {
          display: inline-block;
          margin-left: 6px;
          height: 5px;
          transform: scaleY(0.5) translateY(4px);
        }
      }

      .stateIndex {
        position: absolute;
        bottom: 20px;
        left: 90px;
        height: 16px;
        font-size: 12px;
        font-family: YouSheBiaoTiHei-Regular, YouSheBiaoTiHei;
        font-weight: 400;
        color: #FCF366;
        line-height: 16px;
        .span {
          display: inline-block;
          width: 50px;
          height: 8px;
        }
      }
    }
  }
}
</style>
