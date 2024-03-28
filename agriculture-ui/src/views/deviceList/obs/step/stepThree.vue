<template>
  <div class="contrain">
    <el-form ref="formsRef" :model="forms" :rules="rules" label-width="120px">
      <el-form-item class="form-item" label="sim卡号:" prop="phoneNum">
        <el-input
          :maxlength="11"
          style="width: 80%"
          v-model="forms.phoneNum"
          placeholder="请输入sim卡号"
          clearable
        ></el-input>
      </el-form-item>
    </el-form>
    <div class="btn">
      <el-button @click="cancel">上一步</el-button>
      <el-button type="primary" @click="submitForm">下一步</el-button>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  phoneNum: {
    type: String,
  },
});
const emit = defineEmits(["prevStp", "otherNextStep"]);
const forms = ref({
  phoneNum: undefined,
});
const message = ref("");
const formsRef = ref();
const rules = {
  phoneNum: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    {
      message: "请输入正确的手机号",
      trigger: "blur",
      pattern: /0?(13|14|15|18|17|16)[0-9]{9}/,
    },
  ],
};
function validatePhoneNumber(phoneNumber) {
  // 定义手机号的正则表达式
  let phonePattern = /^1[3456789]\d{9}$/;
  // 使用正则表达式进行校验
  let isValid = phonePattern.test(phoneNumber);
  // 返回校验结果
  return isValid;
}
function cancel() {
  emit("prevStp");
}
function submitForm() {
  formsRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    emit("otherNextStep", forms.value.phoneNum);
  });
}

onMounted(() => {
  forms.value.phoneNum = phoneNum.value || "";
});
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
