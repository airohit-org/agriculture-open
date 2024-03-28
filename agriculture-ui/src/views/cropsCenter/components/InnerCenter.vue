<template>
  <div class="inner-center">
    <div
      class="item"
      v-for="(item, index) in list"
      :key="index"
      :style="`left: ${item.left}px; bottom: ${item.bottom}px`"
    >
      <el-popover
        placement="top"
        trigger="hover"
        popper-class="inner-center-popup-container"
      >
        <div class="inner-center-popup">
          <div>{{ item.crop }}</div>
          <div>
            <span>地块名称：</span><span class="active">{{ item.name }}</span>
          </div>
          <div>
            <span>种植面积：</span><span class="active">{{ item.area }}亩</span>
          </div>
        </div>
        <template #reference>
          <img :src="item.src" />
        </template>
      </el-popover>
    </div>
  </div>
</template>

<script setup>
const list = [
  {
    src: "https://oss.airoteach.cn/649c4b1b5d64816394c6a99265ed24384946f390a82fa488d263f67143c90a7c.png",
    left: 80,
    bottom: 96,
    name: "地块5",
    crop: "玉米",
    area: 982.1,
  },
  {
    src: "https://oss.airoteach.cn/7280d38728fe073c6e28919ac580d50c824d5b1587fa53c504e29e9ee1e74046.png",
    left: 405,
    bottom: 59,
    name: "地块1",
    crop: "水稻",
    area: 198.1,
  },
  {
    src: "https://oss.airoteach.cn/7f532e0cc8f49e061c2feefc27ff8f1c00fc4bce53247406a0b9a938dd744ed2.png",
    left: 322,
    bottom: 200,
    name: "地块2",
    crop: "大豆",
    area: 822,
  },
  {
    src: "https://oss.airoteach.cn/649c4b1b5d64816394c6a99265ed24384946f390a82fa488d263f67143c90a7c.png",
    left: 532,
    bottom: 131,
    name: "地块3",
    crop: "玉米",
    area: 922.9,
  },
  {
    src: "https://oss.airoteach.cn/7f532e0cc8f49e061c2feefc27ff8f1c00fc4bce53247406a0b9a938dd744ed2.png",
    left: 266,
    bottom: 71,
    crop: "玉米",
    name: "地块4",
    area: 782.1,
  },
]
</script>

<style lang="scss">
.inner-center-popup-container {
  padding: 0 !important;
  background-color: transparent !important;
  border: none;
  .popper__arrow {
    display: none;
  }
}
</style>
<style lang="scss" scoped>
.inner-center {
  width: 755px;
  height: 289px;
  background-image: url("https://oss.airoteach.cn/da5e0df02ffcbfe11849a47ee52d37091f17f53480bca58b08381c5daef89f2d.png");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  position: relative;
  margin: 0 auto;

  .item {
    position: absolute;
    img {
    }
  }

  &-popup {
    background-image: url("https://oss.airoteach.cn/729d4a7eb93704b95ff73bd97b6cbd8d76155825954b881f7ab8f7c3c425efe4.png");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
    width: 180px;
    height: 98px;
    padding-top: 7px;

    div {
      &:nth-child(1) {
        margin-left: 10px;
        width: 150px;
        height: 18px;
        padding-left: 8px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        background: linear-gradient(
          90deg,
          #98f6a6 0%,
          rgba(73, 191, 145, 0) 102.02%
        );
        font-family: "YouSheBiaoTiHei";
        font-style: normal;
        font-weight: 400;
        font-size: 14px;
        color: #087164;
      }
      &:nth-child(2) {
        margin-top: 11px;
        margin-left: 17.14px;
        span {
          font-family: "PingFang SC";
          font-style: normal;
          font-weight: 400;
          font-size: 12px;
          color: #ffffff;
        }
        .active {
          color: #1fface;
        }
      }
      &:nth-child(3) {
        margin-left: 17.14px;
        margin-top: 6px;
        span {
          font-family: "PingFang SC";
          font-style: normal;
          font-weight: 400;
          font-size: 12px;
          color: #ffffff;
        }
        .active {
          color: #fcf366;
        }
      }
    }
  }
}

.abc {
  background-color: red;
  color: #fff;
  font-size: 40px;
}
</style>
