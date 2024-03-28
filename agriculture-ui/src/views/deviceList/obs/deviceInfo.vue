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
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="deviceType">
        <el-select
          v-model="queryParams.deviceType"
          clearable
          placeholder="请选择"
        >
          <el-option
            v-for="item in queryDeviceClassifyList"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备厂商" prop="firmName">
        <el-input
          v-model="queryParams.firmName"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择">
          <el-option label="在线" value="online"></el-option>
          <el-option label="离线" value="offline"></el-option>
          <el-option label="报警" value="alarm"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
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
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table class="table" v-loading="loading" :data="list" border stripe>
      <el-table-column label="序号" align="center" width="60">
        <template #default="scope">
          {{
            (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1
          }}
        </template>
      </el-table-column>
      <el-table-column label="厂商型号" align="center" prop="deviceAddr" />
      <el-table-column label="设备别名" align="center" prop="deviceName" />
      <el-table-column label="设备类型" align="center" prop="deviceType">
        <template #default="scope">
          <template v-for="item in deviceClassifyList">
            <span v-if="scope.row.deviceType == item.value">{{
              item.label
            }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="设备厂商" align="center" prop="deviceFirm" />
      <el-table-column label="设备IP" align="center" prop="deviceServiceIp" />
      <el-table-column
        label="设备端口"
        align="center"
        prop="deviceServicePort"
      />
      <el-table-column label="设备状态" align="center" prop="status">
        <template #default="scope">
          <span v-if="scope.row.status == 'online'">在线</span>
          <span v-if="scope.row.status == 'offline'">离线</span>
          <span v-if="scope.row.status == 'alarm'">报警</span>
        </template>
      </el-table-column>
      <el-table-column label="所属农场" align="center" prop="farmId">
        <template #default="scope">
          <span>{{ farmname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建日期" align="center" prop="createTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        min-width="120px"
      >
        <template #default="scope">
          <el-button
            v-if="scope.row.deviceFirm == '聚英科技'"
            size="default"
            link
            type="primary"
            icon="View"
            @click="handleMessage(scope.row)"
            >查看短信内容</el-button
          >
          <el-button
            size="default"
            link
            type="primary"
            icon="View"
            @click="handleData(scope.row)"
            >查看数据</el-button
          >
          <el-button
            size="default"
            link
            type="primary"
            icon="Search"
            @click="handleQuerys(scope.row)"
            >查看</el-button
          >
          <el-button
            size="default"
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="default"
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
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
    <!-- 地图 -->
    <el-dialog
      title="地图"
      v-model="showMap"
      draggable
      width="1200px"
      center
      append-to-body
      :before-close="closeMap"
      :destroy-on-close="true"
    >
      <infoMap
        @lngAndlat="lngAndlat"
        :pos="mapIconShow.pos"
        :posShow="mapIconShow.show"
      ></infoMap>
    </el-dialog>
    <!-- 对话框(查看) -->
    <el-dialog
      :title="title"
      v-model="open"
      width="500px"
      draggable
      append-to-body
      :destroy-on-close="true"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="厂商型号">{{
          form.deviceAddr
        }}</el-descriptions-item>
        <el-descriptions-item label="设备别名">{{
          form.deviceName
        }}</el-descriptions-item>
        <template v-for="item in deviceClassifyList">
          <el-descriptions-item
            v-if="form.deviceType == item.value"
            label="设备类型"
          >
            {{ item.label }}
          </el-descriptions-item>
        </template>

        <el-descriptions-item label="设备厂商">{{
          form.deviceFirm
        }}</el-descriptions-item>
        <el-descriptions-item v-if="form.status == 'online'" label="设备状态"
          >在线</el-descriptions-item
        >
        <el-descriptions-item v-if="form.status == 'offline'" label="设备状态"
          >离线</el-descriptions-item
        >
        <el-descriptions-item v-if="form.status == 'alarm'" label="设备状态"
          >报警</el-descriptions-item
        >
        <el-descriptions-item label="所属农场">{{
          farmname
        }}</el-descriptions-item>
        <el-descriptions-item label="创建日期">{{
          parseTime(form.createTime, "{y}-{m}-{d}")
        }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 新增 -->
    <el-dialog
      :title="title"
      v-model="show"
      width="500px"
      draggable
      append-to-body
      :destroy-on-close="true"
    >
      <el-steps v-if="showJuYing" :active="activeIndex" align-center>
        <el-step title="步骤1"></el-step>
        <el-step title="步骤2"></el-step>
        <el-step title="步骤3"></el-step>
        <el-step title="步骤4"></el-step>
        <el-step title="步骤5"></el-step>
      </el-steps>
      <el-steps v-else :active="activeIndex" align-center>
        <el-step title="步骤1"></el-step>
        <el-step title="步骤2"></el-step>
      </el-steps>
      <el-form
        v-if="activeIndex == 0"
        ref="newFormRef"
        :model="form"
        :rules="rules"
        label-width="120px"
        style="margin-top: 30px"
      >
        <el-form-item class="form-item" label="设备厂商：" prop="firmId">
          <el-select
            @change="firmChange"
            :disabled="isUpdate"
            v-model="form.firmId"
            placeholder="请选择"
          >
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
          v-if="form.firmId == firmIds"
          class="form-item"
          label="厂商型号："
          prop="deviceAddr"
        >
          <el-input
            :disabled="isUpdate"
            v-model="form.deviceAddr"
            placeholder="请输入"
          />
        </el-form-item>
        <el-form-item class="form-item" label="设备别名：" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入" />
        </el-form-item>
        <el-form-item class="form-item" label="设备类型：" prop="deviceType">
          <el-select v-model="form.deviceType" clearable placeholder="请选择">
            <el-option
              v-for="item in deviceTypeList"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="landList.length > 0"
          class="form-item"
          label="所属地块："
          prop="landName"
        >
          <el-select v-model="form.landId" placeholder="请选择">
            <el-option
              v-for="item in landList"
              :label="item.landName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="设备位置：" prop="latlng">
          <div v-if="form.devicelat">
            坐标：{{ form.devicelat + "," + form.devicelng }}
          </div>
          <el-button type="primary" plain size="default" @click="openMap"
            >地图</el-button
          >
        </el-form-item>
        <el-form-item class="form-item" label="备注：" prop="remark">
          <el-input
            :maxlength="100"
            type="textarea"
            :rows="3"
            placeholder="请输入内容"
            v-model="form.remark"
          >
          </el-input>
        </el-form-item>
      </el-form>

      <stepSecond
        :showJuYing="showJuYing"
        @prevStp="prevStp"
        v-if="activeIndex == 1"
        :deviceinfo="deviceinfos"
        @otherNextStep="otherNextStep"
      ></stepSecond>
      <stepThree
        v-if="activeIndex == 2"
        @prevStp="prevStp"
        @otherNextStep="otherNextStep"
        :phoneNum="form.simNumber"
      >
      </stepThree>
      <stepFour
        v-if="activeIndex == 3"
        @prevStp="prevStp"
        @otherNextStep="otherNextStep"
        :infos="infos"
      ></stepFour>
      <stepFive
        v-if="activeIndex == 4"
        @prevStp="prevStp"
        @otherNextStep="otherNextStep"
      ></stepFive>
      <template #footer>
        <div v-if="activeIndex == 0" class="dialog-footer">
          <!-- <el-button @click="cancel">取 消</el-button> -->
          <el-button type="primary" @click="nextStep">下一步</el-button>
        </div>
        <div
          v-if="activeIndex == 4 || (activeIndex == 1 && !showJuYing)"
          class="dialog-footer"
        >
          <el-button @click="prevStp">上一步</el-button>
          <el-button v-if="activeIndex == 4" type="primary" @click="handleDatas"
            >确 定</el-button
          >
          <el-button v-else type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 修改 -->

    <el-dialog
      :title="title"
      v-model="isUpdate"
      width="500px"
      draggable
      append-to-body
      :destroy-on-close="true"
    >
      <el-form
        ref="editFormRef"
        :model="form"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item class="form-item" label="设备厂商：" prop="firmId">
          <el-input disabled v-model="form.deviceFirm" placeholder="请输入" />
        </el-form-item>
        <el-form-item
          v-if="form.firmId == firmIds"
          class="form-item"
          label="厂商型号："
          prop="deviceAddr"
        >
          <el-input disabled v-model="form.deviceAddr" placeholder="请输入" />
        </el-form-item>
        <el-form-item class="form-item" label="设备别名：" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入" />
        </el-form-item>
        <el-form-item class="form-item" label="设备类型：" prop="deviceType">
          <el-select
            disabled
            v-model="form.deviceType"
            clearable
            placeholder="请选择"
          >
            <el-option
              v-for="item in deviceTypeList"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="landList.length > 0"
          class="form-item"
          label="所属地块："
          prop="landName"
        >
          <el-select v-model="form.landId" placeholder="请选择">
            <el-option
              v-for="item in landList"
              :label="item.landName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="设备位置：" prop="latlng">
          <div v-if="form.devicelat">
            坐标：{{ form.devicelat + "," + form.devicelng }}
          </div>
          <el-button type="primary" plain size="default" @click="openMap"
            >地图</el-button
          >
        </el-form-item>
        <el-form-item class="form-item" label="备注：" prop="remark">
          <el-input
            :maxlength="100"
            type="textarea"
            :rows="3"
            placeholder="请输入内容"
            v-model="form.remark"
          >
          </el-input>
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitUpdateForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      title="短信通知"
      v-model="messageShow"
      draggable
      width="500px"
      center
      append-to-body
      :destroy-on-close="true"
    >
      <div v-html="messageContent"></div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">我知道了</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { listAll } from "@/api/land/map";
import { getFirm } from "@/api/obs/deviceFirm";
import {
  claimDevice,
  updateDevice,
  deleteDevice,
  getDeviceInfoPage,
  deviceType,
  getOne,
  getIpAndPort,
} from "@/api/obs/deviceInfo";
import infoMap from "./infoMap.vue";
import { getTenantId } from "@/utils/auth";
import { getFarmByTenant } from "@/api/farm/farm.js";
import stepSecond from "./step/stepSecond.vue";
import stepThree from "./step/stepThree.vue";
import stepFour from "./step/stepFour.vue";
import stepFive from "./step/stepFive.vue";

const router = useRouter();
const { proxy } = getCurrentInstance();
const infos = ref(null);
const showJuYing = ref(true);
const deviceinfos = ref("");
const activeIndex = ref(1);
const isUpdate = ref(false);
const deviceTypeList = ref([]);
const messageShow = ref(false);

const messageContent = ref("");
const farmname = ref("");
const farmId = ref("");
const firmIds = ref("");
const firmName = ref("");
const isCreate = ref(false);
const firmList = ref([]);
const landList = ref([]);
// 遮罩层
const loading = ref(true);
// 导出遮罩层
const exportLoading = ref(false);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 山东仁科设备全部信息列表
const list = ref([]);
// 弹出层标题
const title = ref("");
const oldId = ref("");
// 是否显示弹出层
const showMap = ref(false);
const show = ref(false);
const open = ref(false);

const devicelist = ref([]);

const deviceClassifyList = ref([]);
const deviceClassifyFormList = ref([]);
const queryDeviceClassifyList = ref([]);
// 表单参数
const form = ref({
  deviceName: undefined,
  deviceType: undefined,
  devicelat: "",
  devicelng: "",
  remark: "",
  landId: undefined,
  farmId: undefined,
  farmName: "",
  deviceAddr: undefined,
  firmId: undefined,
});
// 表单校验
const rules = {
  firmId: [{ required: true, message: "请选择", trigger: "change" }],
  deviceType: [{ required: true, message: "请选择", trigger: "change" }],
  deviceName: [
    { required: true, message: "请选择", trigger: "change" },
    { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
  ],
  deviceAddr: [
    { required: true, message: "请输入", trigger: "blur" },
    { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
  ],
};
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  deviceName: null,
  farmId: null,
  deviceType: null,
  status: null,
  firmName: null,
  createTime: [],
});
const queryFormParams = ref({
  pageNo: 1,
  pageSize: 100,
  deviceName: null,
  farmName: null,
  deviceType: null,
  createTime: [],
});
const mapIconShow = ref({
  show: false,
  pos: null,
});
const newFormRef = ref();
const editFormRef = ref();
// components: {
//   infoMap,
//   stepSecond,
//   stepThree,
//   stepFour,
//   stepFive,
// },

function handleData(row) {
  localStorage.setItem("deviceId", row.id);
  if (row.deviceFirm == "北京天航华创科技股份有限公司") {
    router.push({
      name: "deviceInfoImg",
      params: {
        row: row,
      },
    });
  } else {
    router.push({
      name: "deviceDataInfo",
      params: {
        row: row,
      },
    });
  }
}
async function deviceTypeGet() {
  let res = await deviceType();
  deviceClassifyList.value = res.data;
  queryDeviceClassifyList.value = filterDeviceType(res.data);
  deviceTypeList.value = uniqueArr(res.data, "label");
}
/**
 * 去重合并数据
 * @param {*} arr
 */
function filterDeviceType(arr) {
  const resultMap = {};
  const resultArr = [];
  for (const item of arr) {
    if (resultMap[item.label]) {
      resultMap[item.label] += `,${item.value}`;
    } else {
      resultMap[item.label] = item.value;
    }
  }

  for (const key in resultMap) {
    resultArr.push({ label: key, value: resultMap[key] });
  }
  console.log(resultArr);
  return resultArr;
}
/**
 * 设备类型去重
 * @param {*} list
 * @param {*} paramName
 */
function uniqueArr(list, paramName) {
  let uniqueDevices = list.reduce((acc, curr) => {
    if (!acc.find((item) => item[paramName] === curr[paramName])) {
      acc.push(curr);
    }
    return acc;
  }, []);
  return uniqueDevices || [];
}
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
      farmname.value = data.farmName;
      queryParams.value.farmId = data.id;
      getList();
    })
    .catch((res) => {
      proxy.$alert("当前功能不可用，请联系管理员", "错误", {
        confirmButtonText: "确定",
        callback: (action) => {
          // $router.push("/");
        },
      });
    });
}
function firmChange(val) {
  form.value = {
    deviceName: undefined,
    deviceType: undefined,
    devicelat: "",
    devicelng: "",
    remark: "",
    landId: undefined,
    farmId: undefined,
    farmName: "",
    deviceAddr: undefined,
    firmId: val,
  };
  if (val == firmIds.value) {
    showJuYing.value = false;
  } else {
    showJuYing.value = true;
  }
}
function openMap() {
  if (form.value.devicelat != "") {
    mapIconShow.value.pos = [form.value.devicelat, form.value.devicelng];
  } else {
    mapIconShow.value.pos = [];
  }
  showMap.value = true;
}
function closeMap() {
  proxy
    .$confirm("是否关闭地图并保存设备位置？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
    .then(() => {
      showMap.value = false;
      mapIconShow.value.show = false;
      return;
    })
    .catch(() => {
      form.value.devicelat = "";
      form.value.devicelng = "";
      showMap.value = false;
      proxy.$message({
        type: "info",
        message: "已取消",
      });
    });
}
/**
 *
 * @param {object} val 获取子组件的坐标
 */
function lngAndlat(val) {
  form.value.devicelat = val.latlng.lat;
  form.value.devicelng = val.latlng.lng;
}
/**
 * 拿到地块信息
 */
async function getLands() {
  landList.value = [];
  let res = await listAll();
  const { data } = res;
  landList.value = data;
}
function handleDatas() {
  const param = {
    devicelat: form.value.devicelat,
    devicelng: form.value.devicelng,
    landId: form.value.landId,
    remark: form.value.remark,
    deviceAddr: form.value.deviceAddr,
    deviceName: form.value.deviceName,
    deviceType: form.value.deviceType,
    farmId: farmId.value,
    farmName: farmname.value,
    firmId: form.value.firmId,
    deviceServiceIp: form.value.deviceServiceIp,
    deviceServicePort: form.value.deviceServicePort,
    deviceServiceTemplate: form.value.deviceServiceTemplate,
  };
  claimDevice(param).then((response) => {
    proxy.$modal.msgSuccess("新增成功");
    show.value = false;
    getList();
  });
}
/** 查询列表 */
function getList() {
  loading.value = true;
  oldId.value = "";
  // 执行查询
  getDeviceInfoPage(queryParams.value).then((response) => {
    console.log(response);
    list.value = response.data.list;
    total.value = response.data.total;
    loading.value = false;
  });
}
async function getDeviceInfoPageGet(param) {
  let res = await getDeviceInfoPage(param);
  devicelist.value = res.data.list;
  devicelist.value.forEach((ele) => {
    deviceClassifyList.value.forEach((item) => {
      if (item.value == ele.deviceType) {
        ele.deviceTypeName = item.label;
      }
    });
  });
}
/** 取消按钮 */
function cancel() {
  open.value = false;
  show.value = false;
  isUpdate.value = false;
  isCreate.value = false;
  messageShow.value = false;
  messageContent.value = "";
  reset();
}
/** 表单重置 */
function reset() {
  devicelist.value = [];
  firmList.value = [];
  form.value = {
    id: undefined,
    deviceId: undefined,
    data: undefined,
  };
  proxy.resetForm("newFormRef");
  proxy.resetForm("editFormRef");
}
/**
 * 拼接短信
 * @param {*} row
 */
function handleMessage(row) {
  console.log(row);
  messageContent.value = replacePlaceholders(
    row.deviceServiceIp,
    row.deviceServicePort,
    row.deviceServiceTemplate
  );
  messageShow.value = true;
}
function replacePlaceholders(deviceServiceIp, deviceServicePort, templates) {
  const template = templates;
  const ip = `<span style='color:red;'>${deviceServiceIp}</span>`;
  const port = `<span style='color:red;'>${deviceServicePort}</span>`;
  const strs = template
    .replace("deviceServiceIp", ip)
    .replace("deviceServicePort", port);
  return strs;
}
function otherNextStep(val) {
  if (activeIndex.value < 5) {
    if (activeIndex.value == 2) {
      form.value.simNumber = val;
      infos.value = form.value;
    }
    activeIndex.value++;
  }
}
function prevStp() {
  if (activeIndex.value >= 1) {
    activeIndex.value--;
  }
}
function nextStep() {
  newFormRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    if (form.value.firmId && showJuYing.value) {
      const loading = $loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });

      getIpAndPort({ firmId: form.value.firmId }).then((res) => {
        setTimeout(() => {
          loading.close();
        }, 100);
        const { data } = res;
        form.value.deviceServiceIp = data.deviceServiceIp;
        form.value.deviceServicePort = data.deviceServicePort;
        form.value.deviceServiceTemplate = data.deviceServiceTemplate;
        // deviceinfos = replacePlaceholders(data.deviceServiceIp, data.deviceServicePort, data.deviceServiceTemplate)
        deviceinfos.value = {
          deviceServiceIp: data.deviceServiceIp,
          deviceServicePort: data.deviceServicePort,
          deviceServiceTemplate: data.deviceServiceTemplate,
        };
        activeIndex.value++;
      });
    } else {
      activeIndex.value++;
    }
  });
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
  activeIndex.value = 0;
  isUpdate.value = false;
  FirmGet();
  showJuYing.value = true;
  isCreate.value = true;
  show.value = true;
  title.value = "添加设备信息";
}
async function FirmGet() {
  let res = await getFirm();
  let arr = [];
  for (const key in res.data) {
    if (res.data[key] == "精讯畅通") {
      firmIds.value = key;
      firmName.value = res.data[key];
    }
    if (res.data[key] != "建大仁科") {
      arr.push({ firmId: key, deviceFirm: res.data[key] });
    }
  }
  firmList.value = arr;
}
/**
 *
 */
function handleQuerys(row) {
  reset();
  // const id = row.id;
  form.value = row;
  title.value = "查看设备信息";
  open.value = true;
}
/** 修改按钮操作 */
function handleUpdate(row) {
  activeIndex.value = 0;
  reset();
  getFirm();
  const id = row.id;
  getOne({ id }).then((res) => {
    isCreate.value = false;
    form.value = res.data;
    // mapIconShow.show = true
    mapIconShow.value.pos = [res.data.devicelat, res.data.devicelng];
    title.value = "修改设备信息";
    isUpdate.value = true;
  });
}
function submitUpdateForm() {
  editFormRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    updateDevice(form.value).then((response) => {
      poxy.$modal.msgSuccess("修改成功");
      isUpdate.value = false;
      getList();
    });
    return;
  });
}
/** 提交按钮 */
function submitForm() {
  // $refs["form"].validate(valid => {
  // if (!valid) {
  //   return;
  // }
  // 修改的提交
  if (!isCreate.value) {
    form.value.oldId = oldId.value;
    updateDevice(form).then((response) => {
      proxy.$modal.msgSuccess("修改成功");
      show.value = false;
      getList();
    });
    return;
  }
  // 添加的提交
  const param = {
    devicelat: form.value.devicelat,
    devicelng: form.value.devicelng,
    landId: form.value.landId,
    remark: form.value.remark,
    deviceAddr: form.value.deviceAddr,
    deviceName: form.value.deviceName,
    deviceType: form.value.deviceType,
    farmId: farmId.value,
    farmName: farmname.value,
    firmId: form.value.firmId,
  };
  claimDevice(param).then((response) => {
    proxy.$modal.msgSuccess("新增成功");
    show.value = false;
    if (form.value.deviceFirm == "聚英科技") {
      handleMessage(response.data);
      messageShow.value = true;
    } else {
      getList();
    }
  });
  // });
}
/** 删除按钮操作 */
function handleDelete(row) {
  const id = row.id;
  proxy.$modal
    .confirm('是否确认删除设备信息编号为"' + id + '"的数据项?')
    .then(function () {
      return deleteDevice(id);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
}

deviceTypeGet();
getLands();
getFarmInfo();
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
