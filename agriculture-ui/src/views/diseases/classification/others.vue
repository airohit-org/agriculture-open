<template>
  <div class="app-container">
    <h3>其他作物病虫害识别</h3>
    <el-form ref="formRef" label-width="100px" :model="form" :rules="rules">
      <el-form-item label="作物种类" prop="cropType">
        <el-select v-model="form.cropType" placeholder="请选择">
          <el-option
            v-for="item in cropList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <div class="support-list">
        <el-image :src="supportCropTypes" style="height: 50px" />
      </div>
      <el-form-item label="作物图片" prop="imgUrl">
        <div style="display: flex">
          <div class="upload">
            <el-upload
              action="#"
              ref="upload"
              list-type="picture-card"
              :file-list="form.imgUrl"
              :limit="1"
              :on-change="handleImgChange"
              :on-remove="handleOnRemove"
              :before-upload="handleImgSizeValid"
              :http-request="handleUpload"
              :auto-upload="false"
            >
            <el-icon class="avatar-uploader-icon"><plus /></el-icon>
            <template #tip >
              <div class="el-upload__tip">
                上传的图片格式要求jpg、png，不超过10M
              </div>
            </template>
            </el-upload>
            <el-button
              size="small"
              type="success"
              v-hasPermi="['diseases:classification:create']"
              @click="handleSubmit"
              >识别图片</el-button
            >
          </div>
          <div class="imgSample">
            <div>示例图片：</div>
            <el-image
              :src="sampleImg"
              fit="contain"
              style="width: 148px; height: 148px"
            ></el-image>
          </div>
        </div>
      </el-form-item>
    </el-form>
    <el-divider></el-divider>
    <div v-loading="loading" class="result">
      <el-descriptions v-if="classificationDetail" :column="1">
        <el-descriptions-item labelClassName="" label="识别结果：">{{
          classificationDetail.diseasesName
        }}</el-descriptions-item>
        <el-descriptions-item
          v-if="classificationDetail.preventionDO"
          label="防治措施："
          >{{
            classificationDetail.preventionDO.preventionPlan
          }}</el-descriptions-item
        >
      </el-descriptions>
    </div>
  </div>
</template>

<script setup>
import supportCropTypes from "@/assets/images/diseases/supportCropTypes.png";
import {
  createClassification,
  getClassificationDetail,
} from "@/api/diseases/classification";
import sampleImg from "@/assets/images/diseases/sample.png";

const validateImage = (rule, value, callback) => {
  //验证器
  if (!checkImgSuccess.value) {
    //为true代表图片在  false报错
    callback(new Error("请上传图片"));
  } else {
    callback();
  }
};

const form = ref({
  imgUrl: [],
  cropType: null,
});
const rules = {
  //校验规则
  cropType: [{ required: true, message: "请选择作物品种", trigger: "change" }],
  imgUrl: [{ required: true, validator: validateImage, trigger: "change" }],
};
const checkImgSuccess = ref(false); //为true代表图片在  false报错
const loading = ref(false);
const classificationDetail = ref("");
// 作物类型
const cropList = [
  { value: "1", label: "蓝莓" },
  { value: "2", label: "土豆" },
  { value: "3", label: "苹果" },
  { value: "4", label: "樱桃" },
  { value: "5", label: "葡萄" },
  { value: "6", label: "桃子" },
  { value: "7", label: "大豆" },
  { value: "8", label: "辣椒" },
  { value: "9", label: "草莓" },
  { value: "10", label: "西红柿" },
];
const formRef = ref();
const upload = ref();

const handleSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      upload.value.submit();
    }
  });
};
const handleUpload = async (data) => {
  loading.value = true;
  const formData = new FormData();
  formData.append("file", data.file);
  formData.append("cropType", form.value.cropType);
  formData.append("isSave", 1);
  const res = await createClassification(formData);
  await handleGetResult(res.data);
  loading.value = false;
};
const handleImgSizeValid = (file) => {
  const size = Number(file.size / 1024 / 1024);
  const resSize = size < 10;
  if (!resSize) {
    proxy.$message.warning("上传的图片大小不超过10M");
  }
  const type = file.type;
  const types = ["image/jpeg", "image/png"];
  const resType = types.some((item) => item === type);
  if (!resType) {
    proxy.$message.warning("上传文件类型错误");
  }
  return resSize && resType;
};
const handleGetResult = async (id) => {
  const res = await getClassificationDetail({ id: id });
  classificationDetail.value = res.data;
};
const handleImgChange = (file, fileList) => {
  formRef.value.clearValidate(); //上传成功清除校验
  checkImgSuccess.value = true;
  const element = document.getElementsByClassName(
    "el-upload el-upload--picture-card"
  );
  element[0].style.display = "none";
};
//文件列表移除文件时的钩子
const handleOnRemove = (file, fileList) => {
  checkImgSuccess.value = false; //检查图片是否加载成功
  classificationDetail.value = null;
  const element = document.getElementsByClassName(
    "el-upload el-upload--picture-card"
  );
  element[0].style.display = "";
};
</script>

<style lang="scss" scoped>
:deep(.el-form-item__label) {
  font-size: 18px;
  font-weight: 400;
  color: #000000;
}
.app-container {
  width: calc(100% - 100px);
  margin: 30px;
  background: #ffffff;
  & > h3 {
    font-size: 24px;
    font-weight: 500;
    color: #000000;
    line-height: 22px;
  }
}
.support-list {
  margin: 20px 0px 20px 100px;
}

.imgSample {
  display: flex;
  margin-left: 50px;
  font-size: 18px;
  font-weight: 400;
  color: #000000;
}
</style>
