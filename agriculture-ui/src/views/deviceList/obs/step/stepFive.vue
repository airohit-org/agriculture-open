<template>
  <div class="contrain">
    <div>请耐心等待约5-10分钟，收到设备激活成功的短信后，可查看设备数据。</div>
    <!-- <div class="btn">
      <el-button @click="cancel">上一步</el-button>
      <el-button type="primary" @click="submitForm">下一步</el-button>
    </div> -->
  </div>
</template>

<script setup>
const emit = defineEmits(["prevStp", "otherNextStep"]);
function cancel() {
  emit("prevStp");
}
function submitForm() {
  emit("otherNextStep");
}
</script>

<style scoped lang="scss">
.contrain {
  padding-top: 30px;
  .btn {
    display: flex;
    justify-content: right;
    align-items: right;
  }
}
</style>
