<template>
  <div class="time-line">
    <div class="switch" @click="handleSwitch">
      <img :src="playing ? playIcon : pauseIcon" alt="" />
    </div>
    <div class="line">
      <div class="line-bg"></div>
      <div class="line-info" ref="lineInfo" @click.prevent="setStage">
        <div class="line-info-slider" :style="`right: ${100 - progress}%`">
          <div class="time" v-if="progress !== 0">
            <ArrowText> {{ currentTime }} </ArrowText>
          </div>
        </div>
        <div v-for="{ time: text } in times" :key="text" class="line-info-item">
          {{ text }}
        </div>
        <div class="line-info-now">现在</div>
      </div>
      <div class="line-show">
        <div class="line-show-slider"></div>
        <div v-for="text in shows" :key="text" class="line-show-item">
          {{ text }}
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
const total = 4 * 60;
import ArrowText from "@/components/ArrowText/index.vue";
import { RAF, separateArray } from "@/utils/index";
const playIcon =
  "https://oss.airoteach.cn/77fde4c9d3c522ad2108f875d064dc35a7546d616505dc1fb7eb1d67cfe57b2b.png";
const pauseIcon =
  "https://oss.airoteach.cn/7d03bf4333db506e5082959333ed70f33125269a93a6bb9beb60f617eeec4476.png";

const props = defineProps({
  radarInfo: Array,
  progress: Number,
});
const { proxy } = getCurrentInstance();
const emit = defineEmits(["onProgress", "openRadar"]);

const baseTime = computed(() => {
  return Math.min(
    ...props.radarInfo.map(({ time }) => {
      const [hour, minute] = time.split(":");
      return +hour * 60 + +minute;
    })
  );
});
const currentTime = computed(() => {
  const allminute =
    baseTime.value + +((total * props.progress) / 100).toFixed(0);
  const minute = `${allminute % 60}`.padStart(2, "0");
  const hour = `${Math.floor(allminute / 60)}`.padStart(2, "0");
  return `${hour}:${minute}`;
});
const times = computed(() => {
  return separateArray(props.radarInfo, 3);
});

const lineInfo = ref();
const playing = ref(false);
const shows = ["优", "良", "轻度", "中度", "重度", "严重"];
watch(
  () => playing,
  () => {
    run();
  }
);
onUnmounted(() => {
  RAF.clearInterval();
});

function handleSwitch() {
  if (props.radarInfo.length === 0) {
    proxy.$message("暂无数据");
    return;
  }
  playing.value = !playing.value;
}
function setStage(e) {
  emit("openRadar");
  const width = lineInfo.value.offsetWidth;
  const value = (e.offsetX / width) * 100;
  handleProgress(+value.toFixed(2));
}
function handleProgress(v) {
  emit("onProgress", v);
}
function pause() {
  RAF.clearInterval();
  handleProgress(0);
  playing.value = false;
}
function run() {
  if (playing.value) {
    emit("openRadar");
    RAF.setInterval(() => {
      handleProgress(props.progress + 2);
      if (props.progress >= 100) {
        pause();
      }
    }, 500);
  } else {
    RAF.clearInterval();
  }
}
defineExpose({ pause })
</script>
<style lang="scss" scoped>
.time-line {
  display: flex;
  .switch {
    margin: 0 20px;
    width: 28px;
    aspect-ratio: 1 / 1;
    display: flex;
    align-items: center;
    justify-content: center;
    user-select: none;
    cursor: pointer;
    border: 1px solid rgb(118, 246, 208);
    clip-path: polygon(
      0 0,
      0 25%,
      1px 25%,
      1px 75%,
      0 75%,
      0% 100%,
      25% 100%,
      25% calc(100% - 1px),
      75% calc(100% - 1px),
      75% 100%,
      100% 100%,
      100% 75%,
      calc(100% - 1px) 75%,
      calc(100% - 1px) 25%,
      100% 25%,
      100% 0,
      75% 0,
      75% 1px,
      25% 1px,
      25% 0
    );

    img {
      width: 10px;
      aspect-ratio: 1 / 1;
    }
  }

  .line {
    flex: 1;
    display: flex;
    position: relative;
    &-bg {
      position: absolute;
      left: 0;
      right: 0;
      top: 4px;
      height: 4px;
      background: #141b1f66;
    }

    &-info {
      cursor: pointer;
      flex: 1;
      display: flex;
      position: relative;
      justify-content: space-between;
      align-items: flex-end;
      &-slider {
        position: absolute;
        left: 0;
        // right: 99%;
        top: 4px;
        height: 4px;
        transition: right linear 0.5s;
        background: linear-gradient(-90deg, #1fface, #07d1fa);

        .time {
          position: absolute;
          top: -35px;
          right: -31px;
          width: 40px;
          height: 19px;
        }
      }

      &-item {
        user-select: none;
        pointer-events: none;
        font-size: 12px;
        font-family: DIN;
        font-weight: bold;
        color: #ffffff;
      }

      &-now {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        padding: 5px;
        background-color: #ffd15c77;
        color: #fff;
        bottom: calc(100% - 4px);
      }
    }

    &-show {
      margin-left: 40px;
      margin-right: 42px;
      width: 273px;
      display: flex;
      position: relative;
      align-items: flex-end;
      justify-content: space-between;

      &-slider {
        position: absolute;
        left: 0;
        right: 0;
        top: 4px;
        height: 4px;
        background: linear-gradient(90deg, #52c41a, #ffdd00, #ff4d4f, #dc0508);
      }
      &-item {
        user-select: none;
        pointer-events: none;
        font-size: 10px;
        font-family: PingFang SC;
        font-weight: 400;
        color: #ffffff;
      }
    }
  }
}
</style>
