<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="厂商名称" prop="firmName">
        <el-input
          v-model="queryParams.firmName"
          placeholder="请输入厂商名称"
          clearable
          @keyup.enter.native="handleQuery"
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="序号" align="center" width="60">
        <template #default="scope">
          {{
            (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1
          }}
        </template>
      </el-table-column>
      <el-table-column label="厂商名称" align="center" prop="firmName" />
      <el-table-column label="账号" align="center" prop="loginName" />
      <el-table-column label="密码" align="center" prop="loginPwd" />
      <el-table-column label="创建日期" align="center" prop="createTime">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template v-slot="scope">
          <el-button  link type="primary"  icon="Search" @click="handleInit(scope.row)"
            >获取设备</el-button
          >
          <el-button  link type="primary"  icon="Edit" @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button  link type="primary"  icon="Delete" @click="handleDelete(scope.row)"
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
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 对话框(添加 / 修改) -->
    <el-dialog
      :title="title"
      v-model="open"
      width="500px"
      draggable
      append-to-body
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="厂商名称" prop="firmId">
          <el-select v-model="form.firmId" placeholder="请选择" filterable>
            <el-option
              v-for="item in firmList"
              :key="item.firmId"
              :label="item.deviceFirm"
              :value="+item.firmId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="form.firmId == firmIds || form.firmId == BJfirmIds"
          label="登录账号"
          prop="loginName"
        >
          <el-input v-model="form.loginName" placeholder="请输入登录账号" />
        </el-form-item>
        <el-form-item
          v-if="form.firmId == firmIds || form.firmId == BJfirmIds"
          label="登录密码"
          prop="loginPwd"
        >
          <el-input v-model="form.loginPwd" placeholder="请输入登录密码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { getTenantId } from "@/utils/auth";
import { getFarmByTenant, update as updateFarm } from "@/api/farm/farm.js";
import {
  createDeviceFirm,
  updateDeviceFirm,
  deleteDeviceFirm,
  getDeviceFirm,
  getDeviceFirmPage,
  firmInit,
  getFirm,
} from "@/api/obs/deviceFirm";

const { proxy } = getCurrentInstance();
const farmname = ref("");
const farmId = ref("");
const firmIds = ref("");
const firmName = ref("");
const BJfirmIds = ref("");
const BJfirmName = ref("");
const formRef = ref();
const firmList = ref([]); //厂商列表
const isCreate = ref(false);
// 遮罩层
const loading = ref(true);
// 导出遮罩层
const exportLoading = ref(false);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 设备厂商信息列表
const list = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  farmId: null,
  firmName: null,
  loginName: null,
  loginPwd: null,
  createTime: [],
});
// 表单参数
const form = ref({});
// 表单校验
const rules = {
  firmId: [{ required: true, message: "不能为空", trigger: "change" }],
  loginName: [
    { required: true, message: "不能为空", trigger: "blur" },
    // { message: '请输入数字', trigger: 'blur', pattern: /^[0-9.]{1,}$/ }
  ],
  loginPwd: [
    { required: true, message: "不能为空", trigger: "blur" },
    // { max: 5000, message: '最多输入5000', trigger: 'blur' },
  ],
};

function getFarmInfo() {
  const tenantId = getTenantId();
  if (!tenantId) {
    proxy.$modal.confirm("请重新登录", "提示").then(() => {
      $store.dispatch("LogOut").then(() => {
        location.href = getPath("/index");
      });
    });
    return;
  }
  getFarmByTenant({ tenantId })
    .then(({ data } = {}) => {
      farmId.value = data.id;
      queryParams.value.farmId = data.id;
      farmname.value = data.farmName;
      getList();
    })
    .catch((res) => {
      console.log(res);
      proxy.$alert("当前功能不可用，请联系管理员", "错误", {
        confirmButtonText: "确定",
        callback: (action) => {
          // $router.push("/");
        },
      });
    });
}

async function FirmListGet() {
  let res = await getFirm();
  let arr = [];
  for (const key in res.data) {
    arr.push({ firmId: key, deviceFirm: res.data[key] });
    if (res.data[key] == "建大仁科") {
      firmIds.value = key;
      firmName.value = res.data[key];
    }
    if (res.data[key] == "北京天航华创科技股份有限公司") {
      BJfirmIds.value = key;
      BJfirmName.value = res.data[key];
    }
  }
  firmList.value = arr;
}
/** 查询列表 */
function getList() {
  loading.value = true;
  // 执行查询
  getDeviceFirmPage(queryParams).then((response) => {
    list.value = response.data.list;
    total.value = response.data.total;
    loading.value = false;
  });
}
/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}
/**
 * 初始化
 * @param {*} row
 */
async function handleInit(row) {
  const id = row.id;
  let res = await firmInit({ id: id });
  if (res.data) {
    proxy.$alert("厂商设备已成功初始化", "系统提示", {
      confirmButtonText: "确定",
      callback: (action) => {
        getList();
      },
    });
  }
}
/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    firmName: undefined,
    loginName: undefined,
    loginPwd: undefined,
  };
  proxy.resetForm("formRef");
}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNo = 1;
  getList();
}
/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}
/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  isCreate.value = true;
  title.value = "添加设备厂商信息";
}
/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const id = row.id;
  isCreate.value = false;
  // getFirm();
  getFirm()
    .then((res) => {
      let arr = [];
      for (const key in res.data) {
        arr.push({ firmId: key, deviceFirm: res.data[key] });
        if (res.data[key] == "建大仁科") {
          firmIds.value = key;
          firmName.value = res.data[key];
        }
        if (res.data[key] == "北京天航华创科技股份有限公司") {
          BJfirmIds.value = key;
          BJfirmName.value = res.data[key];
        }
      }
      firmList.value = arr;
    })
    .then(() => {
      DeviceFirmget(id);
    });

  getDeviceFirm(id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备厂商信息";
  });
}
// async function DeviceFirmget(id) {
//   let res = await getDeviceFirm(id);
//   form.value = res.data;
//   open.value = true;
//   title.value = "修改设备厂商信息";
// }
/** 提交按钮 */
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    // 修改的提交
    if (form.value.id != null) {
      if (
        form.value.firmId != firmIds.value &&
        form.value.firmId != BJfirmIds.value
      ) {
        const { firmName, loginName, loginPwd, ...params } = form;
        console.log("params", params);
        updateDeviceFirm(params).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
        return;
      } else {
        updateDeviceFirm(form).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
        return;
      }
    }
    let param = {};
    if (
      form.value.firmId == firmIds.value ||
      form.value.firmId == BJfirmIds.value
    ) {
      if (form.value.firmId == firmIds.value) {
        param = {
          farmId: farmId.value,
          firmId: form.value.firmId,
          loginName: form.value.loginName,
          loginPwd: form.value.loginPwd,
        };
      } else if (form.value.firmId == BJfirmIds.value) {
        param = {
          farmId: farmId.value,
          firmId: form.value.firmId,
          loginName: form.value.loginName,
          loginPwd: form.value.loginPwd,
        };
      }
    } else {
      param = {
        farmId: farmId.value,
        firmId: form.value.firmId,
      };
    }

    // 添加的提交
    createDeviceFirm(param).then((response) => {
      proxy.$modal.msgSuccess("新增成功");
      open.value = false;
      getList();
    });
  });
}
/** 删除按钮操作 */
function handleDelete(row) {
  const id = row.id;
  proxy.$modal
    .confirm('是否确认删除设备厂商信息编号为"' + id + '"的数据项?')
    .then(function () {
      return deleteDeviceFirm(id);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
}

onMounted(() => {
  {
    getFarmInfo();
    FirmListGet();
    getList();
  }
});
</script>
