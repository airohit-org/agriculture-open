<!--
 * @Author: wei
 * @description: 大屏自适应容器组件
 * @LastEditTime: 2024-03-25 13:44:21
-->
<template>
  <!-- <section class="screen-box" :style="boxStyle"> -->
  <div class="screen-wrapper" ref="screenWrapper" :style="wrapperStyle">
    <slot></slot>
  </div>
  <!-- </section> -->
</template>
<script setup>
/**
 * 防抖函数
 * @param {T} fn
 * @param {number} delay
 * @return
 */
function debounce(fn, delay) {
  let timer = null;
  return function (...args) {
    timer = setTimeout(
      () => {
        typeof fn === "function" && fn.apply(null, args);
        clearTimeout(timer);
      },
      delay > 0 ? delay : 100
    );
  };
}
const currentWidth = ref(0);
const currentHeight = ref(0);
const originalWidth = ref(0);
const originalHeight = ref(0);
const onResize = ref(null);
const observer = ref(null);
const screenWrapper = ref();
const props = defineProps({
  width: {
    type: [String, Number],
    default: 1920,
  },
  height: {
    type: [String, Number],
    default: 1080,
  },
  fullScreen: {
    type: Boolean,
    default: false,
  },
  autoScale: {
    type: [Object, Boolean],
    default: true,
  },
  selfAdaption: {
    type: Boolean,
    default: true,
  },
  delay: {
    type: Number,
    default: 500,
  },
  boxStyle: {
    type: Object,
    default: () => ({}),
  },
  wrapperStyle: {
    type: Object,
    default: () => ({}),
  },
});
watch(
  () => props.selfAdaption,
  (val) => {
    if (val) {
      resize();
      addListener();
    } else {
      clearListener();
      clearStyle();
    }
  }
);

function initSize() {
  return new Promise((resolve, reject) => {
    // console.log("初始化样式");
    //给父元素设置 overflow:hidden
    screenWrapper.value.parentNode.style.overflow = "hidden";
    screenWrapper.value.parentNode.scrollLeft = 0;
    screenWrapper.value.parentNode.scrollTop = 0;

    $nextTick(() => {
      // region 获取大屏真实尺寸
      if (width.value && height.value) {
        currentWidth.value = width.value;
        currentHeight.value = height.value;
      } else {
        currentWidth.value = screenWrapper.value.clientWidth;
        currentHeight.value = screenWrapper.value.clientHeight;
      }
      // endregion
      // region 获取画布尺寸
      if (!originalHeight.value || !originalWidth.value) {
        originalWidth.value = window.screen.width;
        originalHeight.value = window.screen.height;
      }
      // endregion
      resolve();
    });
  });
}
function updateSize() {
  if (currentWidth.value && currentHeight.value) {
    screenWrapper.value.style.width = `${currentWidth.value}px`;
    screenWrapper.value.style.height = `${currentHeight.value}px`;
  } else {
    screenWrapper.value.style.width = `${originalWidth.value}px`;
    screenWrapper.value.style.height = `${originalHeight.value}px`;
  }
}
function handleAutoScale(scale) {
  if (!autoScale.value) return;
  const screenWrapper = screenWrapper.value;
  const domWidth = screenWrapper.clientWidth;
  const domHeight = screenWrapper.clientHeight;
  const currentWidth = document.body.clientWidth;
  const currentHeight = document.body.clientHeight;
  screenWrapper.style.transform = `scale(${scale},${scale}) `;
  let mx = Math.max((currentWidth - domWidth * scale) / 2, 0);
  let my = Math.max((currentHeight - domHeight * scale) / 2, 0);
  if (typeof autoScale.value === "object") {
    // @ts-ignore
    !autoScale.value.x && (mx = 0);
    // @ts-ignore
    !autoScale.value.y && (my = 0);
  }
  // console.log({
  //   mx,
  //   my,
  //   currentWidth,
  //   currentHeight,
  //   domWidth,
  //   domHeight,
  //   scale,
  // });
  screenWrapper.value.style.margin = `${my}px ${mx}px`;
}
function updateScale() {
  const screenWrapper = screenWrapper.value;
  // 获取真实视口尺寸
  const currentWidth = document.body.clientWidth;
  const currentHeight = document.body.clientHeight;
  // 获取大屏最终的宽高onResize
  const realWidth = currentWidth.value || originalWidth.value;
  const realHeight = currentHeight.value || originalHeight.value;
  // 计算缩放比例
  const widthScale = currentWidth / realWidth;
  const heightScale = currentHeight / realHeight;
  // console.log({currentWidth, currentHeight,realWidth,realHeight});

  // 若要铺满全屏，则按照各自比例缩放
  if (fullScreen.value) {
    screenWrapper.style.transform = `scale(${widthScale},${heightScale})`;
    return false;
  }
  // 按照宽高最小比例进行缩放
  const scale = Math.min(widthScale, heightScale);
  handleAutoScale(scale);
}
function initMutationObserver() {
  const screenWrapper = screenWrapper.value;
  const observer = (observer.value = new MutationObserver(() => {
    onResize();
  }));

  observer.observe(screenWrapper, {
    attributes: true,
    attributeFilter: ["style"],
    attributeOldValue: true,
  });
}
function clearListener() {
  window.removeEventListener("resize", onResize.value);
}
function addListener() {
  window.addEventListener("resize", onResize.value);
}
function clearStyle() {
  // console.log("清除");
  const screenWrapper = screenWrapper.value;
  screenWrapper.parentNode.style.overflow = "auto";

  screenWrapper.style = "";
}
async function resize() {
  if (!selfAdaption.value) {
    return;
  }
  await initSize();
  updateSize();
  updateScale();
}

onMounted(() => {
  onResize = debounce(() => {
    resize();
  }, delay.value);
  $nextTick(() => {
    if (selfAdaption.value) {
      resize();
      addListener();
    }
  });
});
onUnmounted(() => {
  clearListener();
});
</script>

<style scoped>
.screen-box {
  overflow: hidden;
  background-size: 100% 100%;
  background: #000;
  /* width: 100vw; */
  /* height: 100vh; */
}

.screen-wrapper {
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 500ms;
  position: relative;
  overflow: hidden;
  z-index: 100;
  transform-origin: left top;
}
</style>
