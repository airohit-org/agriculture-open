<template>
  <div>
    <div class="taskCount">
      <div v-for="(item, index) in taskStatusNameCount" class="taskItem">
        <img :src="taskStatusNameImg[index]" class="imgTask" />
        <div>
          <div class="count">
            {{ item.value }}
          </div>
          <div class="name">
            {{ item.name }}
          </div>
        </div>
        <div v-if="index < 5" class="spac">|</div>
      </div>
    </div>
    <div class="app-container">
      <!-- 搜索工作栏 -->
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item label="任务名称" prop="agroName">
          <el-input
            v-model="queryParams.agroName"
            placeholder="请输入"
            clearable
            @keyup.enter.native=""
          />
        </el-form-item>

        <!-- <el-form-item label="任务类型" prop="type">
            <el-input v-model="queryParams.type" placeholder="请输入" clearable @keyup.enter.native="" />
          </el-form-item> -->
        <el-form-item label="任务类型" prop="type">
          <el-select v-model="queryParams.type" placeholder="请选择任务类型">
            <el-option
              v-for="(item, index) in typeList"
              :key="index"
              :label="item.label"
              :value="item.type"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="计划名称" prop="planName">
          <!-- <el-select v-model="queryParams.planName" placeholder="请选择任务类型">
              <el-option label="请选择字典生成" value="" />
            </el-select> -->
          <el-input
            v-model="queryParams.planName"
            placeholder="请输入"
            clearable
            @keyup.enter.native=""
          />
        </el-form-item>
        <el-form-item label="地块名称" prop="landName">
          <el-input
            v-model="queryParams.landName"
            placeholder="请输入"
            clearable
            @keyup.enter.native=""
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
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['plant:taskInfo:create']"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            class="bgcolor layout-font"
            plain
            @click="handleAppoint"
            v-hasPermi="['plant:task:appoint']"
            >批量指派</el-button
          >
        </el-col>
        <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
      </el-row>

      <!-- 列表 -->
      <el-table
        v-loading="loading"
        :data="list"
        @selection-change="handleSelectionChange"
        stripe
        border
      >
        <!-- <el-table-column label="Id" align="center" prop="id" /> -->
        <el-table-column align="center" type="selection" />
        <el-table-column
          label="序号"
          align="center"
          type="index"
          :index="indexMethod(queryParams.pageNo)"
        />
        <el-table-column label="任务名称" align="center" prop="agroName" />
        <el-table-column label="任务类型" align="center">
          <template v-slot="scope">
            <span class="type" :class="'type' + scope.row.type">
              {{ scope.row.typeLable }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="农事阶段" align="center" prop="planningStage" />
        <el-table-column label="计划名称" align="center" prop="planName" />
        <el-table-column
          label="栽培阶段"
          align="center"
          prop="parentStageName"
        />
        <el-table-column label="地块名称" align="center" prop="landName" />
        <el-table-column label="开始日期" align="center" prop="startDate" />
        <el-table-column label="任务周期" align="center" prop="taskPeriod" />
        <el-table-column label="负责人" align="center" prop="nickName" />
        <el-table-column label="任务状态" align="center" prop="taskStatus" />
        <!-- 
        <el-table-column label="任务对应的表名" align="center" prop="typeTableName" />
        <el-table-column label="种植计划模版ID" align="center" prop="plantingPlanId" />

        <el-table-column label="创建时间" align="center" prop="createTime" /> -->
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="210"
        >
          <template v-slot="scope">
            <el-button
              v-if="
                scope.row.taskStatus == '未指派' ||
                scope.row.taskStatus == '未开始'
              "
              type="primary"
              link
              icon="Thumb"
              @click="handleAppoint(scope.row)"
              v-hasPermi="['plant:task:appoint']"
              >指派</el-button
            >
            <el-button
              type="primary"
              link
              icon="Search"
              @click="handleQueryMessage(scope.row)"
              v-hasPermi="['plant:task:query']"
              >查看</el-button
            >
            <el-button
              type="primary"
              link
              icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['plant:task:update']"
              >修改</el-button
            >
            <el-button
              type="primary"
              link
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['plant:task:delete']"
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
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
          <el-form-item
            label="地块名称"
            prop="landName"
            @change="$forceUpdate()"
          >
            <el-select
              @change="selectPlanName"
              v-model="form.landName"
              placeholder="请选择"
              :disabled="addDisabled"
            >
              <el-option
                v-for="item in mapNameList"
                :key="item.landId"
                :label="item.landName"
                :value="item.plantingPlanId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="计划名称" prop="planName">
            <el-select
              v-model="form.planName"
              placeholder="请选择"
              :disabled="addDisabled"
            >
              <el-option
                :label="plantName.planName"
                :value="plantName.plantingPlanId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="农事阶段" prop="planningStage">
            <el-select
              @change="selectparentName"
              v-model="form.planningStage"
              placeholder="请选择"
              :disabled="disabled"
            >
              <el-option
                v-for="(item, index) in planstage"
                :key="index"
                :label="item.stageName"
                :value="item.stageCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="栽培阶段" prop="parentStageName">
            <el-select
              v-model="form.parentStageName"
              placeholder="请选择"
              :disabled="disabled"
            >
              <el-option
                v-for="(item, index) in planTypeDataChildList"
                :key="index"
                :label="item.stageName"
                :value="item.stageCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="任务类型" prop="type">
            <el-select
              v-model="form.type"
              placeholder="请选择"
              :disabled="disabled"
            >
              <el-option
                v-for="(item, index) in typeList"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </div>
        </template>
      </el-dialog>

      <el-dialog
        center
        :title="
          titleChange
            ? '新增农事任务'
            : titleChangeSon
            ? '修改农事任务'
            : '查看农事任务'
        "
        v-model="isShowPlant"
        width="600px"
        append-to-body
      >
        <publicItem
          :selectConfig="selectConfig"
          @cancel="closeAgro"
          @getList="getList"
        ></publicItem>
      </el-dialog>
      <el-dialog
        title="查看任务"
        v-model="isShowView"
        width="600px"
        append-to-body
      >
        <div>地块名称: {{ form.landName }}</div>
        <div>计划名称: {{ form.planName }}</div>
        <div>农事阶段: {{ form.planningStage }}</div>
        <div>栽培阶段: {{ form.parentStageName }}</div>
        <div>任务类型: {{ form.typename }}</div>
      </el-dialog>
      <el-dialog
        title="指派任务"
        v-model="isShowAppoint"
        width="600px"
        append-to-body
      >
        <el-form
          ref="selectsRef"
          :model="form"
          :rules="rulesSelect"
          label-width="100px"
        >
          <el-form-item label="选择负责人" prop="nickName">
            <el-select
              class="layout"
              v-model="form.nickName"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in paesant"
                :key="index"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancelAppiontName">取 消</el-button>
            <el-button type="primary" @click="handleAppointName"
              >确 定</el-button
            >
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import {
  // createTaskInfo,
  //  updateTaskInfo,
  updateTaskAppoint,
  updateTaskAppointList,
  deleteTaskInfo,
  getTaskInfo,
  getTaskInfoPage,
  getQueryPlanBindLand,
  getPlanTypeDataByPlantingPlanId,
  typeChange,
  getTaskInfoStatistic,
  exportTaskInfoExcel,
} from "@/api/agro/taskInfo";
import publicItem from "@/components/formItem/publicItem.vue";
import { allList } from "@/api/peasant/index";
import { getTaskFiled } from "@/api/agro/taskTemplateInfo.js";
// import { getLands } from '@/api/land/map.js'

const { proxy } = getCurrentInstance();
// 遮罩层
const loading = ref(true);
// 导出遮罩层
const exportLoading = ref(false);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 农事任务基本信息列表
const list = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
const isShowView = ref(false);
const paesant = ref([]);
const planstage = ref([]);
const planTypeDataChildList = ref([]);
const disabled = ref(false);
const addDisabled = ref(false);
//指派弹窗
const isShowAppoint = ref(false);
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  agroName: void 0,
  planName: void 0,
  type: void 0,
  landName: void 0,
});
const isShowPlant = ref(false);
const taskAppointVO = ref({});
const plantName = ref({
  planName: undefined,
  plantingPlanId: undefined,
});
const titleChange = ref(true);
const titleChangeSon = ref(true);
const selectConfig = ref({
  disabled: false,
  searchShow: true,
  formItem: [],
  stageCode: 0,
  plantingPlanId: 0,
});
const selectsRef = ref();
const formRef = ref();
// 表单参数
const form = ref({
  nickName: void 0,
});
// 表单校验
const rules = {
  landName: [{ required: true, message: "请选择地块名称", trigger: "change" }],
  planName: [{ required: true, message: "请选择计划名称", trigger: "change" }],
  planningStage: [
    { required: true, message: "请选择农事阶段", trigger: "change" },
  ],
  parentStageName: [
    { required: true, message: "请选择栽培阶段", trigger: "change" },
  ],
  type: [{ required: true, message: "请选择任务类型", trigger: "change" }],
};
const rulesSelect = {
  nickName: [{ required: true, message: "请选择负责人" }],
};
const typeList = [
  {
    label: "整地",
    value: "agro_task_raking",
    type: "4",
  },
  {
    label: "播种",
    value: "agro_task_seeding",
    type: "6",
  },
  {
    label: "施肥",
    value: "agro_task_fertilizer",
    type: 0,
  },
  {
    label: "除草",
    value: "agro_task_weed",
    type: "7",
  },
  {
    label: "中耕",
    value: "agro_task_intertill",
    type: "1",
  },
  {
    label: "打药",
    value: "agro_task_pesticide",
    type: "3",
  },
  {
    label: "灌溉",
    value: "agro_task_irrigation",
    type: "2",
  },
  {
    label: "收割",
    value: "agro_task_reap",
    type: "5",
  },
];
const taskStatusName = [
  {
    taskStatus: "1",
    name: "未指派",
  },
  {
    taskStatus: "2",
    name: "未开始",
  },
  {
    taskStatus: "3",
    name: "进行中",
  },
  {
    taskStatus: "4",
    name: "未完成",
  },
  {
    taskStatus: "5",
    name: "已完成",
  },
  {
    taskStatus: "6",
    name: "逾期",
  },
];
const taskStatusNameCount = ref([]);
const taskStatusNameImg = [
  new URL("@/assets/images/farmTask/wzp.png", import.meta.url).href,
  new URL("@/assets/images/farmTask/wks.png", import.meta.url).href,
  new URL("@/assets/images/farmTask/jxz.png", import.meta.url).href,
  new URL("@/assets/images/farmTask/wwc.png", import.meta.url).href,
  new URL("@/assets/images/farmTask/ywc.png", import.meta.url).href,
  new URL("@/assets/images/farmTask/yq.png", import.meta.url).href,
];
const mapNameList = ref([]);
const planningStageList = ref([]);
const multipleSelection = ref([]);
async function selectPlanName(item) {
  mapNameList.value.forEach((it) => {
    if (it.plantingPlanId == item) {
      plantName.value.planName = it.planName;
      plantName.value.plantingPlanId = it.plantingPlanId;
    }
  });

  const { data } = await getPlanTypeDataByPlantingPlanId({
    plantingPlanId: item,
  });
  planstage.value = data;
}
async function selectparentName(item) {
  planstage.value.forEach((it) => {
    if (it.stageCode == item) {
      planTypeDataChildList.value = it.planTypeDataChildList;
    }
  });
}
/** 查询列表 */
function getList() {
  loading.value = true;
  // 执行查询
  getTaskInfoPage(queryParams.value).then((response) => {
    list.value = response.data.list;
    list.value.forEach((item) => {
      if (item.nickName == null) {
        item.nickName = "无";
      }
      item.startDate = proxy.parseTime(item.startDate, "{y}-{m}-{d}");
      taskStatusName.forEach((it) => {
        if (it.taskStatus == item.taskStatus) {
          item.taskStatus = it.name;
          // it.count += 1
        }
      });
      typeList.forEach((it) => {
        if (item.type == it.type) {
          item.typeLable = it.label;
        }
      });
    });
    total.value = response.data.total;
    loading.value = false;
  });
  getQueryPlanBindLand().then((response) => {
    mapNameList.value = response.data;
  });
  allList().then((response) => {
    paesant.value = response.data;
  });
  getTaskInfoStatistic().then((response) => {
    taskStatusNameCount.value = response.data;
  });
}
function indexMethod(pageNo) {
  return pageNo == 1 ? 1 : (pageNo - 1) * 10 + 1;
}
/** 取消按钮 */
function cancel() {
  open.value = false;
  if (disabled.value == true) {
    disabled.value = false;
  }
  reset();
}
/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    agroName: undefined,
    startDate: undefined,
    taskPeriod: undefined,
    planName: undefined,
    landName: undefined,
    type: undefined,
    typeTableName: undefined,
    planningStage: undefined,
    plantingPlanId: undefined,
    status: undefined,
    nickName: undefined,
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
  disabled.value = false;
  addDisabled.value = false;
  selectConfig.value.edit = false;
  selectConfig.value.disabled = false;
  titleChange.value = true;
  open.value = true;
  selectConfig.value.form = {};
  title.value = "新增农事任务基本信息";
}
function handleAppoint(val) {
  if (val.id || multipleSelection.value.length !== 0) {
    if (multipleSelection.value.length == 0) {
      taskAppointVO.value = val;
      getTaskInfo(val.id).then((response) => {
        const { peasantId } = response.data;
        form.value.nickName = peasantId;
      });
    } else {
      reset();
    }
  } else {
    proxy.$modal
      .confirm("请勾选要指派的数据")
      .then(function () {})
      .then(() => {})
      .catch(() => {});
    return;
  }

  isShowAppoint.value = true;
}
async function getPlanTypeName(params) {
  // plantName.planName = params.planName;
  // plantName.plantingPlanId = params.plantingPlanId;
  const { data } = await getPlanTypeDataByPlantingPlanId({
    plantingPlanId: params.plantingPlanId,
  });
  planstage.value = data;
  planTypeDataChildList.value = data.planTypeDataChildList;
}
/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const id = row.id;
  // if (disabled == true) {
  //   disabled = false
  // }
  disabled.value = true;
  addDisabled.value = true;
  titleChange.value = false;
  titleChangeSon.value = true;
  getTaskInfo(id).then((response) => {
    form.value = response.data;

    if (form.value.type || form.value.type == 0) {
      form.value.type = typeChange(form.value.type);
    }
    open.value = true;
    selectConfig.value.edit = true;
    selectConfig.value.form = form.value;
    selectConfig.value.disabled = false;
    title.value = "修改农事任务基本信息";
  });
}
// 查看按钮
function handleQueryMessage(row) {
  reset();
  disabled.value = true;
  addDisabled.value = true;
  titleChange.value = false;
  titleChangeSon.value = false;
  const id = row.id;
  getTaskInfo(id).then((response) => {
    form.value = response.data;
    getPlanTypeName(form.value);
    if (form.value.type || form.value.type == 0) {
      form.value.type = typeChange(form.value.type);
    }
    typeList.forEach((item) => {
      if (item.value == form.value.type) {
        form.value.typename = item.label;
      }
    });
    // open = true;
    isShowView.value = true;
    selectConfig.value.edit = true;
    selectConfig.value.form = form.value;
    selectConfig.value.disabled = true;
    // isShowAppoint = true;
    title.value = "查看农事任务基本信息";
  });
}
/** 提交按钮 */
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    open.value = false;
    selectConfig.value.type = form.value.type;
    selectConfig.value.stageCode = form.value.parentStageName;
    selectConfig.value.plantingPlanId = form.valueplanName;
    // selectConfig.parentStageName = form.parentStageName
    getTaskFileds({ typeTableName: form.value.type });
    isShowPlant.value = true;
    reset();
    if (disabled.value == true) {
      disabled.value = false;
    }
  });
}
/** 删除按钮操作 */
function handleDelete(row) {
  const id = row.id;
  proxy.$modal
    .confirm("是否确认删除农事任务基本信息的此数据项?")
    .then(function () {
      return deleteTaskInfo(id);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
}
/** 导出按钮操作 */
function handleExport() {
  // 处理查询参数
  let params = { ...queryParams.value };
  params.pageNo = undefined;
  params.pageSize = undefined;
  proxy.$modal
    .confirm("是否确认导出所有农事任务基本信息数据项?")
    .then(() => {
      proxy.$download.excel(
        exportTaskInfoExcel(params),
        "农事任务基本信息.xls"
      );
    })
    .catch(() => {});
}
function closeAgro() {
  isShowPlant.value = false;
}
function cancelAppiontName() {
  isShowAppoint.value = false;
  taskAppointVO.value = {};
}
function handleSelectionChange(val) {
  multipleSelection.value = val;
}
function handleAppointName() {
  selectsRef.value.validate((valid) => {
    if (!valid) {
      return;
    }

    let { id } = taskAppointVO.value;
    let params = {};
    params = {
      ...{ id, peasantId: form.value.nickName },
    };
    if (multipleSelection.value.length == 0) {
      updateTaskAppoint(params)
        .then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          isShowAppoint.value = false;
          getList();
        })
        .catch(() => {});
      taskAppointVO.value = {};
    } else {
      const id = [];
      multipleSelection.value.forEach((item) => {
        id.push(item.id);
      });
      updateTaskAppointList({ id, peasantId: form.value.nickName }).then(() => {
        proxy.$modal.msgSuccess("修改成功");
        getList();
        isShowAppoint.value = false;
      });
    }
  });
}
async function getTaskFileds(val) {
  let res = await getTaskFiled(val);
  selectConfig.value.formItem = res.data;
}
getList();
</script>

<style lang="scss" scoped>
.app-container {
  background: #ffffff;
  margin: 30px;
}
:deep(.el-dialog__body) {
  margin: auto;
}
.taskCount {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 30px;
  background-color: #ffffff;
  height: 120px;
  .taskItem {
    display: flex;
    width: 166px;
    flex: 1;
    border-radius: 9px 9px 9px 9px;
    opacity: 1;
    font-size: 36px;
    font-family: DIN Alternate-Bold, DIN Alternate;
    font-weight: bold;
    color: #333333;
    .imgTask {
      width: 55px;
      height: 55px;
      margin-right: 18px;
    }
    .spac {
      margin-left: auto;
      color: #bababa;
      font-size: 18px;
      margin: auto;
    }
    .name {
      font-size: 14px;
      color: #bababa;
    }
  }
  .taskItem:first-child {
    margin-left: 47px;
  }
}
.type {
  border-radius: 2px 2px 2px 2px;
  opacity: 1;
  font-size: 14px;
  font-family: PingFang SC-Medium, PingFang SC;
  font-weight: 500;
  width: 44px;
  height: 22px;
  line-height: 21px;
  display: inline-block;
  &0 {
    background: #e5ffd8;
    border: 1px solid #52c41a;

    color: #52c41a;
  }
  &1 {
    background: #dac0fe;
    border: 1px solid #722ed1;
    color: #722ed1;
  }
  &2 {
    background: #ffdadb;
    border: 1px solid #ff4d4f;
    color: #ff4d4f;
  }
  &3 {
    background: #ffe8d0;
    border: 1px solid #fa8c16;
    color: #fa8c16;
  }
  &4 {
    background: #fffad7;
    border: 1px solid #ffdd00;
    color: #ffdd00;
  }
  &5 {
    background: #d2e0ff;
    border: 1px solid #5c8df8;
    color: #5c8df8;
  }
  &6 {
    background: #d5ffff;
    border: 1px solid #00dada;
    color: #00dada;
  }
  &7 {
    background: #cbfff5;
    border: 1px solid #20c7a8;
    color: #20c7a8;
  }
}
.bgcolor {
  background-color: #ffffff;
  color: #20c7a8;
  width: 80px;
}
@media screen and (max-width: 1400px) {
  :deep(.el-button + .el-button) {
    margin-left: 0;
  }
}

:deep(
    .el-table--striped
      .el-table__body
      tr.el-table__row--striped
      td.el-table__cell
  ) {
  background-color: #f0f2f2;
}
// .el-form-item {
//   margin-bottom: 12px;
// }

:deep(.el-table__header tr th) {
  background-color: #f0f2f2;
}
:deep(.el-dialog__header) {
  background-color: #f0f2f2;
}
</style>
