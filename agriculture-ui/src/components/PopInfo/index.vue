<template>
  <div class="popup">
    <div v-for="{ value, title, wholeValue } in infoList" :key="title">
      <span class="popup-title">{{ title }}：</span>
      <el-tooltip :disabled="value?.length < maxNumber" :content="`${value}`"
        ><span class="popup-information">{{
          wholeValue || value
        }}</span></el-tooltip
      >
    </div>
  </div>
</template>

<script setup>
// import { MAX_ECLIPSE_NUMBER } from '@/store/modules/landMap/help.js';
import { watch, ref } from "vue";
import { KEY_MAP_TITLE } from "./help";

const props = defineProps({
  info: {
    type: Object,
    default: () => {},
  },
});

const maxNumber = ref(10);
const infoList = ref([]);

watch(
  () => props.info,
  (val) => {
    val = val || {};
    infoList.value = Object.keys(val).reduce(
      (total, key) =>
        KEY_MAP_TITLE[key]
          ? total.concat({
              wholeValue: val[`whole${key}`],
              value: val[key],
              title: KEY_MAP_TITLE[key],
            })
          : total,
      []
    );
  },
  {
    deep: true,
    immediate: true,
  }
);
</script>

<style scoped lang="scss">
.popup {
  display: grid;
  color: #fff;

  &-information {
    color: #1fface;
  }
  div {
    display: grid;
    grid-template-columns: 86px 150px;
    gap: 2px;
    border-bottom: 1px solid #1fface;
    border-image: linear-gradient(
        right,
        rgba(31, 250, 206, 0.1) 5%,
        #1fface 50%,
        rgba(31, 250, 206, 0.1) 100%
      )
      1;
    span {
      height: 33px;
      display: flex;
      align-items: center;
      font-weight: 400;
      font-size: 16px;
      font-family: PingFang SC;
    }
    span:first-child {
      width: 80px;
      margin-left: 10px;
      line-height: 33px;
      text-align: justify;
      display: inline-block;
    }
    span:first-child::after {
      content: "";
      display: inline-block;
      width: 100%;
    }
    span:last-child {
      justify-content: flex-start;
    }
  }
}
</style>
