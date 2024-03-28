<template>
  <div class="contrain">
    <div>
      请编辑短信：*JY#11#1#<span style="color: red">{{
        infos.deviceServiceIp
      }}</span
      >#<span style="color: red">{{ infos.deviceServicePort }}</span
      >#10000#300#0#31# 发送到设备sim卡中，用于激活设备
      <!-- <el-descriptions :column="2" border>
        <el-descriptions-item label="设备名称">{{ infos.deviceName }}</el-descriptions-item>
        <el-descriptions-item v-for="item in deviceClassifyList" v-if="infos.deviceType == item.value" label="设备类型">{{
          item.label }}</el-descriptions-item>
        <el-descriptions-item label="sim卡">{{ infos.simNumber }}</el-descriptions-item>
        <el-descriptions-item label="设备ip">{{ infos.deviceServiceIp }}</el-descriptions-item>
        <el-descriptions-item label="设备端口">{{ infos.deviceServicePort }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ infos.remark }}</el-descriptions-item>
      </el-descriptions> -->
    </div>
    <div class="btn">
      <el-button @click="cancel">上一步</el-button>
      <el-button type="primary" @click="submitForm">确定已发送</el-button>
    </div>
  </div>
</template>

<script setup>
import { deviceType } from "@/api/obs/deviceInfo";

const props = defineProps({
  infos: {
    type: Object,
  },
});
const emit = defineEmits(["prevStp", "otherNextStep"]);
const deviceClassifyList = ref([]);
const deviceTypeGet = async () => {
  let res = await deviceType();
  deviceClassifyList.value = res.data;
};
const cancel = () => {
  emit("prevStp");
};
const submitForm = () => {
  emit("otherNextStep");
};
deviceTypeGet();
</script>

<style scoped lang="scss">
.contrain {
  padding-top: 30px;
  .btn {
    margin-top: 30px;
    display: flex;
    justify-content: right;
    align-items: right;
  }
}
</style>
