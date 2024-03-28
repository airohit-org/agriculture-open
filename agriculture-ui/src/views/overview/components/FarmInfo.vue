<template>
  <div class="all-info">
    <OverviewTitle title="农场信息总览" />
    <div class="content" v-show="list.length || area !== 0">
      <div class="content-left">
        <div class="content-left-areaTitle">
          <img
            src="https://oss.airoteach.cn/ed05ccd08540cb933a86c05c0b83e080e123e2586fb7ca57e2e1c2c907545163.png"
            class="content-left-areaTitle-areaImage"
          />
          <div class="content-left-areaTitle-title">总面积(亩)</div>
        </div>
        <div class="content-left-area">{{ area }}</div>
        <div class="content-left-frame"></div>
      </div>
      <div class="content-right">
        <div
          class="content-right-classify"
          v-for="{ id, value, title, color, image, unit } in list"
          :key="id"
        >
          <img :src="image" class="content-right-classify-icon" />
          <div class="content-right-classify-title">{{ title }}</div>
          <div
            class="content-right-classify-value"
            :style="`--color: ${color}`"
          >
            {{ value }}
          </div>
          <div class="content-right-classify-unit">{{ unit }}</div>
        </div>
      </div>
    </div>
    <Empty v-show="!(list.length || area !== 0)" />
  </div>
</template>

<script setup>
import Empty from '@/components/Empty/index.vue'
import OverviewTitle from "@/components/OverviewTitle/index.vue";

const props = defineProps({
  area: {
    type: [Number, String],
    default: 0,
  },
  list: {
    type: Array,
    default: () => [],
  }
})
</script>

<style lang="scss" scoped>
.all-info {
  height: 100%;
}
.content {
  display: flex;
  flex-wrap: nowrap;
  margin-top: 15px;
  &-left {
    margin-top: 30px;
    &-areaTitle {
      display: flex;
      justify-content: center;
      &-areaImage {
        width: 26px;
        height: 26px;
      }
      &-title {
        color: white;
        text-align: center;
        font-size: 18px;
      }
    }
    &-area {
      color: #1fface;
      text-align: center;
      font-size: 30px;
    }
    &-frame {
      width: 187.73px;
      height: 204.04px;
      position: relative;
      bottom: 125px;
      background-image: url("https://oss.airoteach.cn/50cbcc87f87318e79a164e0a80e92ea804835ffd89ed0b4710211ae1833203a9.png");
      background-repeat: no-repeat;
      background-size: cover;
      overflow: hidden;

      &:after {
        position: absolute;
        content: "";
        bottom: 20%;
        left: 0;
        right: 0;
        height: 1%;
        background: #5ab7ac88;
        animation: toUp 2s linear infinite;
      }
    }
  }
  &-right {
    height: 43px;
    display: grid;
    grid-template-columns: repeat(2, auto);
    &-classify {
      height: 43px;
      width: 98px;
      margin-left: 15px;
      margin-top: 35px;
      display: grid;
      &-icon {
        grid-row: 1/4;
        grid-column: 1/2;
        height: 36px;
        width: 36px;
        margin-right: 5px;
        transform-style: preserve-3d;
        animation: rotate360 4s linear infinite;
      }
      &-title {
        grid-row: 1/2;
        grid-column: 2/4;
        color: white;
        font-size: 12px;
      }
      &-value {
        grid-row: 2/4;
        grid-column: 2/3;
        color: var(--color);
        font-size: 18px;
      }
      &-unit {
        grid-row: 2/4;
        grid-column: 3/4;
        color: white;
        font-size: 12px;
        padding-top: 5px;
      }
    }
  }
}

@keyframes rotate360 {
  0% {
    transform: rotateY(0deg);
  }

  20%,
  100% {
    transform: rotateY(360deg);
  }
}

@keyframes toUp {
  0% {
    bottom: 20%;
    opacity: 1;
  }

  100% {
    bottom: 80%;
    opacity: 0;
  }
}
</style>
