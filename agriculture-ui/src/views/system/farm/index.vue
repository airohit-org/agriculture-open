<template>
  <div class="app-container">
    <div class="taskCount">
      <div v-for="(item, index) in taskCount" class="taskItem">
        <img :src="item.bg" class="imgTask" />
        <div class="son">
          <div class="name">
            {{ item.name }}
          </div>
          <div class="count">
            {{ item.value }}
          </div>
        </div>
      </div>
    </div>
    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          class="layout-font"
          v-show="isShow"
          type="primary"
          plain
          icon="Plus"
          size="default"
          @click="handleAdd"
          v-hasPermi="['farm::create']"
          >新增</el-button
        >
      </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table class="table" v-loading="loading" :data="list" border stripe>
      <el-table-column label="农场名称" align="center" prop="farmName" />
      <el-table-column
        label="种植面积(亩)"
        align="center"
        prop="plantArea"
        width="100"
      />
      <el-table-column label="农场地址" align="center" prop="province">
        <template #default="scope">
          {{ scope.row.province }}{{ scope.row.city }}{{ scope.row.area }}
        </template>
      </el-table-column>
      <el-table-column label="详细地址" align="center" prop="address" />
      <el-table-column label="负责人" align="center" prop="contacts" />
      <el-table-column label="联系电话" align="center" prop="tel" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template #default="scope">
          <el-button
            class="button-seat"
            size="small"
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['farm::update']"
            >修改</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="title"
      v-model="open"
      width="500px"
      draggable
      append-to-body
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="110px">
        <el-form-item class="form-item" label="农场名称" prop="farmName">
          <el-input v-model="form.farmName" placeholder="请输入农场名称" />
        </el-form-item>
        <el-form-item class="form-item" label="种植面积(亩)" prop="plantArea">
          <el-input v-model="form.plantArea" placeholder="请输入种植面积" />
        </el-form-item>
        <el-form-item class="form-item" label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item class="form-item" label="详细地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item class="form-item" label="联系电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入联系电话" />
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {
  create,
  update,
  deleteFarm,
  get,
  exportExcel,
  getFarmByTenant,
  getFarmInfos,
} from "@/api/farm/farm.js";
import {
  getProvinceList,
  getCityByProvince,
  getAreaByCity,
} from "@/api/peasant/index";
import { getUserProfile } from "@/api/system/user";

const { proxy } = getCurrentInstance();
const router = useRouter();
// 遮罩层
const loading = ref(true);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 农场列表
const list = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  farmName: null,
  province: null,
  city: null,
  area: null,
  createTime: [],
});
// 表单参数
const form = ref({
  province: null,
  city: null,
  area: null,
});
const options = ref(null);
const optionsCity = ref(null);
const optionsArea = ref(null);
const IsCity = ref(true);
const IsArea = ref(true);
const isShow = ref(false);
const formRef = ref();
const taskCount = ref([
  {
    value: 10,
    name: "地块总数",
    bg: new URL("@/assets/images/farmManage/landTotal.png", import.meta.url)
      .href,
  },
  {
    value: 4,
    name: "作物总数",
    bg: new URL("@/assets/images/farmManage/plantTotal.png", import.meta.url)
      .href,
  },
  {
    value: 10,
    name: "设备总数",
    bg: new URL("@/assets/images/farmManage/deviceTotal.png", import.meta.url)
      .href,
  },
  {
    value: 33,
    name: "任务总数",
    bg: new URL("@/assets/images/farmManage/taskTotal.png", import.meta.url)
      .href,
  },
  {
    value: 18,
    name: "员工总数",
    bg: new URL("@/assets/images/farmManage/workerTotal.png", import.meta.url)
      .href,
  },
]);
// 表单校验
const rules = {
  farmName: [
    { required: true, message: "请输入农场名称", trigger: "blur" },
    { max: 20, message: "请填写20个汉字、数字", trigger: "blur" },
    {
      message: "请填写20个汉字、数字",
      trigger: "blur",
      pattern:
        /^((?:[\u3400-\u4DB5\u4E00-\u9FEA\uFA0E\uFA0F\uFA11\uFA13\uFA14\uFA1F\uFA21\uFA23\uFA24\uFA27-\uFA29]|[\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879][\uDC00-\uDFFF]|\uD869[\uDC00-\uDED6\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF34\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0])|(\d))+$/,
    },
  ],
  plantArea: [
    { required: true, message: "请输入种植面积", trigger: "blur" },
    {
      message: "最多输入7位整数和2位小数",
      trigger: "blur",
      pattern: /^\d{1,7}(?:\.\d{0,2}$|$)/,
    },
    { message: "最多输入7位整数和2位小数", max: 10, trigger: "blur" },
  ],
  contacts: [
    { required: true, message: "请输入联系人", trigger: "blur" },
    { message: "最多输入10个汉字", max: 10, trigger: "blur" },
    {
      message: "最多输入10个汉字",
      pattern:
        /^(?:[\u3400-\u4DB5\u4E00-\u9FEA\uFA0E\uFA0F\uFA11\uFA13\uFA14\uFA1F\uFA21\uFA23\uFA24\uFA27-\uFA29]|[\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879][\uDC00-\uDFFF]|\uD869[\uDC00-\uDED6\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF34\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0])+$/,
    },
  ],
  address: [
    { required: true, message: "请输入详细地址", trigger: "blur" },
    { message: "最多输入50个汉字、数字、字母和符号", max: 50, trigger: "blur" },
  ],
  tel: [
    { required: true, message: "请输入联系电话", trigger: "blur" },
    { message: "请输入正确的手机号", pattern: /^(?:(?:\+|00)86)?1[3-9]\d{9}$/ },
  ],
  province: [{ required: true, message: "请选择农场地址", trigger: "change" }],
  city: [{ required: true, message: "请选择农场地址", trigger: "change" }],
  area: [{ required: true, message: "请选择农场地址", trigger: "change" }],
};

//  市级 城市
const selectProvice = (val) => {
  IsCity.value = false;
  IsArea.value = true;
  optionsCity.value = null;
  optionsArea.value = null;
  form.value.city = null;
  form.value.area = null;
  //搜索栏应用
  queryParams.value.city = null;
  queryParams.value.area = null;
  getCityByProvince({ provinceCode: val }).then((response) => {
    optionsCity.value = response.data;
  });
};
//  区级 城市
const selectCity = (val) => {
  IsArea.value = false;
  optionsArea.value = null;
  form.value.area = null;
  queryParams.value.area = null;
  getAreaByCity({ cityCode: val }).then((response) => {
    optionsArea.value = response.data;
  });
};
// 查询当前登陆用户
const getUser = () => {
  getUserProfile().then((response) => {
    form.value.contacts = response.data.username;
    form.value.tel = response.data.mobile;
  });
};
/** 查询列表 */
const getList = () => {
  loading.value = true;
  // 执行查询
  const tenantId = parseInt(localStorage.getItem("TENANT_ID"));
  getFarmByTenant({ tenantId }).then((response) => {
    list.value = [response.data];
    loading.value = false;
  });
};
const getFarmStatics = () => {
  getFarmInfos().then((response) => {
    taskCount.value.map((item,index)=>{
      return item.value = response.data[index]
    })
  });
};

/** 取消按钮 */
const cancel = () => {
  open.value = false;
  reset();
};
/** 序号 */
const indexMethod = (pageNo) => {
  return pageNo == 1 ? 1 : (pageNo - 1) * 10 + 1;
};

/** 表单重置 */
const reset = () => {
  form.value = {
    id: undefined,
    farmName: undefined,
    plantArea: undefined,
    contacts: undefined,
    address: undefined,
    tel: undefined,
    province: undefined,
    city: undefined,
    area: undefined,
  };

  proxy.resetForm("formRef");
};
/** 搜索按钮操作 */
const handleQuery = () => {
  if (queryParams.value.province) {
    options.value.forEach((item) => {
      if (queryParams.value.province == item.code) {
        queryParams.value.province = item.name;
      }
    });
  }

  if (queryParams.value.city) {
    optionsCity.value.forEach((item) => {
      if (queryParams.value.city == item.code) {
        queryParams.value.city = item.name;
      }
    });
  }

  if (queryParams.value.area) {
    optionsArea.value.forEach((item) => {
      if (queryParams.value.area == item.code) {
        queryParams.value.area = item.name;
      }
    });
  }
  queryParams.value.pageNo = 1;
  getList();
};
/** 重置按钮操作 */
const resetQuery = () => {
  (IsCity.value = true), (IsArea.value = true), proxy.resetForm("queryForm");
  handleQuery();
};
/** 新增按钮操作 */
const handleAdd = () => {
  getUser();
  reset();
  open.value = true;
  title.value = "新增农场";
};
/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset();
  const id = row.id;
  get(id).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改农场";
  });
};
/** 提交按钮 */
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (!valid) {
      return;
    }

    if (!optionsCity.value || !optionsArea.value) {
    } else {
      [options.value, optionsCity.value, optionsArea.value].forEach(
        (options) => {
          ["province", "city", "area"].forEach((key) => {
            const item = options.find((item) => item.code === form.value[key]);
            if (item) {
              form.value[key] = item.name;
            }
          });
        }
      );
    }

    // 修改的提交
    if (form.value.id != null) {
      update(form.value).then((response) => {
        proxy.$modal.msgSuccess("修改成功");
        open.value = false;
        getList();
      });
      return;
    }
    // 添加的提交
    create(form.value).then((response) => {
      $modal.msgSuccess("新增成功");
      open.value = false;
      getList();
      proxy.$modal
        .confirm("是否现在跳转到地块信息添加农场")
        .then(function () {})
        .then(() => {
          router.push("/massif/massifinformation");
          proxy.$modal.msgSuccess("跳转成功");
        })
        .catch(() => {});
    });
  });
};
/** 删除按钮操作 */
const handleDelete = (row) => {
  const id = row.id;
  proxy.$modal
    .confirm("是否确认删除此农场数据项?")
    .then(function () {
      return deleteFarm(id);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
};
/** 导出按钮操作 */
const handleExport = () => {
  proxy.$modal
    .confirm("是否确认导出所有农场数据项?")
    .then(() => {
      return proxy.$download.excel(exportExcel(queryParams.value), "农场.xls");
    })
    .catch(() => {});
};

getList();
getFarmStatics()
//  省级 城市
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
.taskCount {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex: 1 1 auto;
  background: #ffffff;
  border-radius: 9px 9px 9px 9px;
  margin-bottom: 16px;
  .taskItem {
    position: relative;
    margin-right: 28px;
    width: 214px;
    height: 120px;
    padding: 0 6px;
    opacity: 1;
    font-size: 36px;
    font-family: DIN Alternate-Bold, DIN Alternate;
    font-weight: bold;
    color: #333;
    .imgTask {
      width: 200px;
      height: 100px;
    }
    .son {
      position: absolute;
      top: 37px;
      left: 138px;
      .name {
        font-size: 14px;
        // margin: 0 0 16px 36px;
      }
      .count {
        // margin: 13px 0 3px 36px;
      }
    }
  }
  .taskItem:last-child {
    margin-right: 0;
  }
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
