<template>
  <div class="top-tool">
    <div class="top">
      <div class="layers">
        <div
          v-for="{ icon, title, activeIcon, id } in layerList"
          :key="id"
          class="layer"
          :class="layerValues.includes(id) ? 'layer-active' : ''"
          @click="emit('handleLayer', id)"
        >
          <img :src="layerValues.includes(id) ? activeIcon : icon" alt="" />
          <span>{{ title }}</span>
        </div>
      </div>
      <img
        v-for="{ icon, type } in list"
        :key="type"
        :src="icon"
        @click="emit('clickTool', type)"
      />
    </div>
    <div class="bottom">
      <div
        class="item"
        :key="id"
        :class="values.includes(id) ? 'item-active' : ''"
        v-for="{ id, title, img, activeImg } in configList"
      >
        <img :src="values.includes(id) ? activeImg : img" alt="" />
        <span>{{ title }}</span>
      </div>
    </div>
  </div>
</template>
<script setup>
import { CONFIG } from "./help";
import { LAYER_LIST } from "../../help";

const props = defineProps({
  layerValues: Array,
  values: Array,
});
const emit = defineEmits(["handleLayer", "clickTool"]);
const configList = CONFIG;
const layerList = LAYER_LIST;
const list = [
  // {
  //     icon: 'https://oss.airoteach.cn/1ea2384847b904098f79ff57f86e518a97db02d031bdeb417a909e2026d69e91.png',
  //     type: 'layer'
  // },
  {
    icon: "https://oss.airoteach.cn/0fb5b3b1a292bc6c329e62ff30a9f66c60e50b80e4c69a34731eaf68af4291f9.png",
    type: "plus",
  },
  {
    icon: "https://oss.airoteach.cn/9ae3685f0f5adc33aabbd24ae2be7719c6ad01d768f73397c5f547f9fb26dabd.png",
    type: "minus",
  },
  {
    icon: "https://oss.airoteach.cn/3752f40f2a899ae4a4c29560946cde10445f74e0d1b48eaf481bf2c29b68a93f.png",
    type: "locate",
  },
  {
    icon: "https://oss.airoteach.cn/d940de1c1aad61627cd3013dc8a4cbee6a69ec7d41b93133beb8b6c59b3e7073.png",
    type: "fresh",
  },
];
</script>
<style lang="scss" scoped>
.top-tool {
  .top {
    display: flex;
    margin-bottom: 25px;
    flex-wrap: nowrap;
    justify-content: flex-end;

    .layers {
      background-color: #141b1f99;
      display: flex;
      align-items: center;
      height: 37px;
      flex-wrap: nowrap;
      margin-right: 15px;

      .layer {
        cursor: pointer;
        user-select: none;
        padding-left: 15px;
        padding-right: 10px;
        border-right: 1px solid #fff;
        display: flex;
        align-items: center;
        flex-wrap: nowrap;
        flex-shrink: 0;

        img {
          flex-shrink: 0;
          height: 14px;
          width: 14px;
        }

        span {
          flex-shrink: 0;
          margin-left: 10px;
          font-size: 14px;
          font-family: PingFang SC;
          font-weight: bold;
          color: #ffffff;
          white-space: nowrap;
        }

        &:last-child {
          border-right: none;
        }
        &-active {
          span {
            color: #1fface;
          }
        }
      }
    }

    img {
      margin-right: 15px;
      width: 37px;
      aspect-ratio: 1 / 1;
    }
  }
  .bottom {
    display: grid;
    grid-template-columns: repeat(6, 1fr);
    column-gap: 12px;
    row-gap: 17px;
    grid-template-rows: repeat(2, 1fr);
    .item {
      width: 48px;
      height: 48px;
      background: linear-gradient(
        0deg,
        rgba(21, 28, 31, 0.9) 0%,
        rgba(20, 28, 31, 0) 100%
      );
      border: 1px solid #089e80;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;

      img {
        margin-top: 8px;
        height: 18px;
        aspect-ratio: 1 / 1;
      }

      span {
        font-family: "YouSheBiaoTiHei";
        font-style: normal;
        font-weight: 400;
        font-size: 14px;
        background: linear-gradient(180deg, #ffffff 34.38%, #1fface 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        text-fill-color: transparent;
      }

      &-active {
        border-color: #ffd15c;
        background: linear-gradient(
          0deg,
          rgba(255, 209, 92, 0.6),
          rgba(255, 209, 92, 0)
        );
        span {
          background: linear-gradient(
            180deg,
            #ffffff 34.38%,
            rgba(255, 209, 92, 0.92) 100%
          );
          -webkit-background-clip: text;
          background-clip: text;
        }
      }
    }
  }
}
</style>
