<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form
      class="form"
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建日期" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          class="layout-font"
          type="primary"
          plain
          icon="Plus"
          size="default"
          @click="handleAdd"
          v-hasPermi="['peasant::create']"
          >新增</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          class="layout-font"
          type="warning"
          plain
          icon="Upload"
          size="default"
          @click="handleImport"
          :loading="exportLoading"
          >导入</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          class="layout-font"
          type="warning"
          plain
          icon="Download"
          size="default"
          @click="handleImportTemplate"
          :loading="exportLoading"
          >模板</el-button
        >
      </el-col> -->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <!-- 列表 -->
    <el-table class="table" v-loading="loading" :data="list" border stripe>
      <el-table-column label="序号" align="center" width="60">
        <template #default="scope">
          <span>{{
            (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <!-- <el-table-column label="用户名 （登录账号）" align="center" prop="userName" /> -->
      <!-- <el-table-column label="用户角色" align="center" prop="userType" /> -->
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="省" align="center" prop="province" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="区县" align="center" prop="area" />
      <el-table-column
        label="创建日期"
        align="center"
        prop="createTime"
        width="180"
      >
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime).split(" ")[0] }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template v-slot="scope">
          <el-button
            class="button-seat"
            type="primary"
            link
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['peasant::update']"
            >修改</el-button
          >
          <el-button
            type="primary"
            link
            plain
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peasant::delete']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNo"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 对话框(添加 / 修改) -->
    <el-dialog
      :title="title"
      v-model="open"
      width="600px"
      draggable
      append-to-body
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item class="form-item" label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item class="form-item" label="登录密码" prop="passWord">
          <el-input v-model="form.passWord" placeholder="请输入登录密码" />
        </el-form-item>
        <el-form-item class="form-item" label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item class="form-item" label="省" prop="province">
          <el-select
            @change="selectProvice"
            v-model="form.province"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.code"
              :label="item.name"
              :value="item.code"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="城市" prop="city">
          <el-select
            @change="selectCity"
            :disabled="IsCity"
            v-model="form.city"
            placeholder="请选择"
          >
            <el-option
              v-for="item in optionsCity"
              :key="item.code"
              :label="item.name"
              :value="item.code"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="区县" prop="area">
          <el-select
            @change="selectArea"
            :disabled="IsArea"
            v-model="form.area"
            placeholder="请选择"
          >
            <el-option
              v-for="item in optionsArea"
              :key="item.code"
              :label="item.name"
              :value="item.code"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div></template
      >
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog title="导入" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :before-upload="beforeUpload"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-change="handleFileChange"
        :on-remove="handleFileRemove"
        :on-progress="handleFileUploadProgress"
        :on-error="handleFileError"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="Upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center" slot="tip">
            <span>仅允许导入xls、xlsx格式文件。</span>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div slot="footer" class="dialog-footer">
          <el-button @click="upload.open = false">取 消</el-button>
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {
  create,
  update,
  deleteUser,
  get,
  getPage,
  exportExcel,
  getProvinceList,
  getCityByProvince,
  getAreaByCity,
  importTemplate,
} from "@/api/peasant/index";
import { getBaseHeader } from "@/utils/request";
import { decrypt, encrypt } from "@/utils/jsencrypt";

const IsUpload = ref(false);
const Roles = ref([]);
const options = ref([]);
const optionsCity = ref([]);
const optionsArea = ref([]);
const IsCity = ref(true);
const IsArea = ref(true);
// 遮罩层
const loading = ref(true);
// 导出遮罩层
const exportLoading = ref(false);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 农户列表
const list = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  name: null,
  createTime: undefined,
});
const formRef = ref();
const uploadRef = ref();
const uploadFiles = ref(0);
// 表单参数
const form = ref({});
// 表单校验
const rules = {
  userName: [
    { required: true, message: "用户名 （登录账号）不能为空", trigger: "blur" },
    { max: 10, message: "最多输入10个汉字", trigger: "blur" },
    {
      message: "请勿输入特殊符号",
      trigger: "blur",
      pattern: /^[\a-\z\A-\Z0-9\u4e00-\u9fe5]+$/,
    },
  ],
  passWord: [
    { required: true, message: "请输入登录密码", trigger: "blur" },
    { max: 20, message: "最多输入20个字母、数字", trigger: "blur" },
    // { message: '请输入8-20位数字、字母、符号，至少包含两种', trigger: 'blur', pattern: /(?!^\d+$)(?!^[A-Z]+$)(?!^[a-z]+$)(?!^[^A-Za-z0-9]+$)(?!^.*[\u4E00-\u9FA5].*$)^\S{8,20}$/ }
    {
      message: "请输入6-20个字母、数字",
      trigger: "blur",
      pattern: /^[a-zA-Z0-9]{6,20}$/,
    },
  ],
  name: [
    { required: true, message: "请输入姓名", trigger: "blur" },
    { max: 10, message: "最多输入10个汉字", trigger: "blur" },
    // { message: '请输入汉字', trigger: 'blur', pattern: /^[\u4e00-\u9fe5]+$/ }
    {
      message: "最多输入10个汉字",
      trigger: "blur",
      pattern: /^[\u4e00-\u9fa5\.]{1,10}$/,
    },
  ],
  phone: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    { max: 11, message: "最多输入11位数字", trigger: "blur" },
    // { message: '请输入正确的手机号', trigger: 'blur', pattern: /^1\d{10}$/ }
    {
      message: "请输入正确的手机号",
      trigger: "blur",
      pattern: /0?(13|14|15|18|17|16)[0-9]{9}/,
    },
  ],
  userType: [{ required: true, message: "请选择用户角色", trigger: "change" }],
  province: [{ required: true, message: "请选择", trigger: "change" }],
  city: [{ required: true, message: "请选择", trigger: "change" }],
  area: [{ required: true, message: "请选择", trigger: "change" }],
  status: [
    { required: true, message: "状态（0正常 1关闭）不能为空", trigger: "blur" },
  ],
};
const upload = ref({
  // 是否显示弹出层（用户导入）
  open: false,
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: getBaseHeader(),
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/peasant/import",
});
const { proxy } = getCurrentInstance();
const selectProvice = (val) => {
  IsCity.value = false;
  IsArea.value = true;
  optionsCity.value = null;
  optionsArea.value = null;
  form.value.city = null;
  form.value.area = null;
  getCityByProvince({ provinceCode: val }).then((response) => {
    optionsCity.value = response.data;
  });
};
const selectCity = (val) => {
  IsArea.value = false;
  optionsArea.value = null;
  form.value.area = null;
  getAreaByCity({ cityCode: val }).then((response) => {
    optionsArea.value = response.data;
  });
};
const selectArea = (val) => {
  [options.value, optionsCity.value, optionsArea.value].forEach((options) => {
    ["province", "city", "area"].forEach((key) => {
      const item = options.find((item) => item.code === form.value[key]);
      if (item) {
        form.value[key] = item.name;
      }
    });
  });
};
/** 查询列表 */
const getList = () => {
  loading.value = true;
  // 执行查询
  getPage(queryParams.value).then((response) => {
    list.value = response.data.list;
    total.value = response.data.total;
    loading.value = false;
  });
};
/** 取消按钮 */
const cancel = () => {
  open.value = false;
  reset();
};
/** 表单重置 */
const reset = () => {
  form.value = {
    phone: undefined,
    passWord: undefined,
    name: undefined,
    province: undefined,
    city: undefined,
    area: undefined,
    id: undefined,
    userName: undefined,
    userType: undefined,
    street: undefined,
    village: undefined,
    status: 0,
    remark: undefined,
  };
  proxy.resetForm("formRef");
};
/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1;
  getList();
};
/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.value.createTime = null;
  proxy.resetForm("queryForm");
  handleQuery();
};
/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "新增农户";
};
/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset();
  IsArea.value = true;
  IsCity.value = true;
  const id = row.id;
  get(id).then((response) => {
    form.value = response.data;
    if (decrypt(form.value.passWord) !== null) {
      form.value.passWord = decrypt(form.value.passWord);
    }
    open.value = true;
    title.value = "修改农户";
  });
};
/** 提交按钮 */
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    //form.status = 0
    // 修改的提交
    let params = {};
    params = form.value;
    params.passWord = encrypt(form.value.passWord);
    console.log(params);
    if (form.value.id != null) {
      update(params).then((response) => {
        proxy.$modal.msgSuccess("修改成功");
        open.value = false;
        getList();
      });
      return;
    }
    // 添加的提交
    create(params).then((response) => {
      proxy.$modal.msgSuccess("新增成功");
      open.value = false;
      getList();
    });
  });
};
/** 删除按钮操作 */
const handleDelete = (row) => {
  const id = row.id;
  proxy.$modal
    .confirm('是否确认删除农户编号为"' + id + '"的数据项?')
    .then(function () {
      return deleteUser(id);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
};
/** 导入按钮操作 */
const handleImport = () => {
  upload.value.isUploading = false;
  upload.value.open = true;
  proxy.$nextTick(() => {
    uploadRef.value.clearFiles();
  });
};
/** 下载模板操作 */
const handleImportTemplate = () => {
  proxy.$download.excel(importTemplate(), "导入模板.xls");
};
const handleChange = (value, direction, movedKeys) => {
  console.log(movedKeys);
  console.log(value);
};
const beforeUpload = (file) => {
  const fileType = file.type;
  const fileTypeArr = [
    "application/vnd.ms-excel",
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  ];
  const isXls = fileTypeArr.includes(fileType);
  if (!isXls) {
    proxy.$message.error("上传文件只能是 xls、xlsx 格式!");
    return false;
  }
  return true;
};
// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  console.log(event);
  console.log(file);
  console.log(fileList);
  // if (file.response.code == 500) {
  //  $message.error('请上传符合模板的xls、xlsx格式文件!')
  //  upload.isUploading = false;
  //  $refs.upload.clearFiles();
  //   return
  // }
  upload.value.isUploading = true;
};
// 上传文件变更
const handleFileChange = (file, fileList) => {
  uploadFiles.value = fileList.length;
};
// 上传文件移除
const handleFileRemove = (file, fileList) => {
  uploadFiles.value = fileList.length;
};
// 文件上传成功处理
const handleFileSuccess = (response, file, fileList) => {
  if (response.code !== 0) {
    proxy.$modal.msgError(response.msg);
    return;
  }
  upload.value.open = false;
  upload.value.isUploading = false;
  uploadRef.value.clearFiles();
  getList();
};
const handleFileError = (err, file, fileList) => {
  console.log(err);
};
// 提交上传文件
const submitFileForm = () => {
  console.log(uploadFiles.value);
  if (uploadFiles.value.length === 0) {
    proxy.$message.error("请上传xls、xlsx格式文件!");
    return;
  }
  uploadRef.value.submit();
};

getList();
getProvinceList().then((response) => {
  options.value = null;
  options.value = response.data;
});
</script>
<style lang="scss" scoped>
.app-container {
  background-color: #f0f2f2;
  padding: 20px 30px 30px 30px;
}

.form {
  background: #ffffff;
  box-shadow: 1px 1px 5px 0px rgba(12, 0, 5, 0.03);
  border-radius: 5px;
  padding: 12px 12px 0 24px;
  margin-bottom: 10px;
}

.table {
  padding: 12px 12px 12px 12px;
  margin-bottom: -10px;
}

.form-item {
  margin-bottom: 20px !important;
}

.button-seat {
  margin-left: -10px;
}

:deep(.el-table__header tr th) {
  background-color: #f0f2f2;
}

:deep(
    .el-table--striped
      .el-table__body
      tr.el-table__row--striped
      td.el-table__cell
  ) {
  background-color: #f0f2f2;
}

:deep(.el-form-item) {
  margin-bottom: 12px;
}

:deep(.el-dialog__header) {
  background-color: #f0f2f2;
}

:deep(.el-button--small) {
  padding: 9px 12px;
  font-size: 12px;
  border-radius: 3px;
}
</style>
