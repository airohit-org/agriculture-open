<template>
  <div class="contrain">
    <div v-if="showJuYing" style="text-align: center; margin-top: 30px">
      此设备的IP是<span style="color: red">{{
        deviceinfo.deviceServiceIp
      }}</span
      >，端口是<span style="color: red">{{
        deviceinfo.deviceServicePort
      }}</span>
    </div>
    <div v-else style="text-align: center; margin-top: 30px">
      请联系管理员，获取设备IP和端口号。
    </div>
    <div v-if="showJuYing" class="btn">
      <el-button @click="cancel">上一步</el-button>
      <el-button type="primary" @click="submitForm">下一步</el-button>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  deviceinfo: {},
  showJuYing: {
    type: Boolean,
  },
});
const emit = defineEmits(["prevStp", "otherNextStep"]);
const cancel = () => {
  emit("prevStp");
};
submitForm = () => {
  emit("otherNextStep");
};
</script>

<style scoped lang="scss">
.contrain {
  .btn {
    margin-top: 30px;
    display: flex;
    justify-content: right;
    align-items: right;
  }
}
</style>
