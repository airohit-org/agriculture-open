<template>
  <div class="cropscenter">
    <div class="bg">
      <slot name="bg"></slot>
    </div>
    <div class="light light-1"></div>
    <div class="light light-2"></div>
    <div class="light light-3"></div>

    <div class="cropscenter-content" ref="containerRef">
      <div class="left">
        <div class="left-item-1 module-item">
          <slot name="left-1"></slot>
        </div>
        <div class="left-item-2 module-item">
          <slot name="left-2"></slot>
        </div>
        <div class="left-item-3 module-item">
          <slot name="left-3"></slot>
        </div>
      </div>
      <div class="right">
        <div class="right-top">
          <div class="right-top-item-1 module-item">
            <slot name="right-top-1"></slot>
          </div>
          <div class="right-top-item-2 module-item">
            <div class="right-top-item-2-1 module-item">
              <slot name="right-top-2-1"></slot>

            </div>
            <div class="right-top-item-2-2 module-item">
              <slot name="right-top-2-2"></slot>

            </div>
          </div>
        </div>
        <div class="right-bottom module-item">
          <slot name="right-bottom"></slot>
        </div>
      </div>
    </div>
    
    <div class="title">
      <slot name="title"></slot>
    </div>
    <div class="loading" v-if="loading">
      <slot name="loading"></slot>
    </div>
    <div class="modal" v-if="modalVis" @click="$emit('toggleModal')">
      <div class="modal-content">
        <slot name="modal-content"></slot>
      </div>
    </div>
  </div>
</template>

<script setup>
import { keepRatio } from "@/utils/keepRadio.js";
import { ref, onMounted, onBeforeUnmount } from "vue";

const props = defineProps({
  loading: Boolean,
  modalVis: Boolean
})

const containerRef = ref()

onMounted(() => {
  const { init, onresize } = keepRatio(containerRef.value, { initW: 1842, initH: 881 });
  init()
  window.addEventListener("resize", onresize);
})

onBeforeUnmount(() => {
  const { onresize } = keepRatio(containerRef.value, { initW: 1842, initH: 881 });
  window.removeEventListener("resize", onresize);
})

</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
