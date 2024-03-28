<template>
  <div class="contrains">
    <div class="title">{{ title }}</div>
    <div class="listInfo">
      <div
        class="item"
        :key="item.id"
        v-for="item in list"
        @click="hasChange(item)"
      >
        <div style="display: flex">
          <div class="icon"></div>
          <div class="name">{{ item.name }}</div>
        </div>

        <div class="value">{{ (+item.value).toFixed(2) + item.unit }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  list: {
    type: Array,
  },
  title: {
    type: String,
  },
});
const emit = defineEmits(["hasChange"]);
function hasChange(item) {
  emit("hasChange", item);
}
</script>

<style lang="scss" scoped>
.contrains {
  width: 100%;
  height: 100%;
  background-image: url("@/assets/images/device/infobg.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;

  .title {
    width: 100%;
    height: 38px;
    height: 38px;
    font-size: 18px;
    font-family: PingFang SC-Regular, PingFang SC;
    font-weight: 400;
    text-align: center;
    color: #ffffff;
    line-height: 38px;
    background-image: url("@/assets/images/device/titlebg.png");
  }

  .listInfo {
    .item {
      cursor: pointer;
      display: flex;
      height: 20px;
      font-size: 16px !important;
      font-family: PingFang SC-Regular, PingFang SC;
      font-weight: 400;
      color: #1afcee;
      margin-top: 12px;
      line-height: 20px;
      justify-content: space-between;

      .icon {
        margin-left: 15px;
        margin-top: 5px;
        width: 12px;
        height: 12px;
        background: rgba(26, 252, 238, 0.2);
        border-radius: 0px 0px 0px 0px;
        opacity: 1;
        border: 1px solid rgba(26, 252, 238, 0.5);
        position: relative;
      }

      .icon::before {
        content: "";
        display: block;
        z-index: 1;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        width: 4px;
        height: 4px;
        background: #1afcee;
        border-radius: 0px 0px 0px 0px;
        opacity: 1;
        position: absolute;
      }

      .name {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        margin-left: 15px;
      }

      .value {
        margin-right: 10px;
      }
    }
  }
}
</style>
