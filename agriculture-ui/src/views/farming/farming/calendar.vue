<template>
  <div>
    <div ref="main" class="main">
      <div ref="trees" class="tree">
        <div class="header">
          <div class="btnStyle">
            <span class="span" @click="$router.go(-1)">返回</span>
            <span class="span1">
              <el-button
                class="layout-font"
                type="primary"
                :icon="Plus"
                @click="openPlanStateOne"
                >新增</el-button
              >
            </span>
          </div>
          <div class="info">
            <img class="imgs" :src="planImg" alt="" />
            <div class="planname">{{ planName }}</div>
            <div class="crops">
              <span>{{ cropsName }}</span> <span class="line"></span>
              <span>{{ cropsTypeName }}</span>
            </div>
          </div>
        </div>
        <div>
          <el-tree
            class="tree-line"
            icon-class="el-icon-arrow-right"
            :props="defaultProps"
            :data="treeData"
            node-key="id"
            default-expand-all
            :expand-on-click-node="false"
            :render-content="renderContent"
          >
          </el-tree>
        </div>
      </div>
      <div ref="calendars" class="FullCalendar">
        <div class="calHeader">
          <div class="left"></div>
          <div class="center">{{ nowDate }}</div>
          <div class="right">
            <span class="todayPrevNext">
              <span class="today" @click="dealCal('today')">今天</span>
              <span class="prev" @click="dealCal('left')">
                <el-icon><ArrowLeft /></el-icon>
              </span>
              <span class="next" @click="dealCal('right')">
                <el-icon><ArrowRight /></el-icon>
              </span>
            </span>
            <span class="date" @click="dealCal('date')">
              <img ref="calRef" class="ings" :src="calImg.cals" alt="" />
            </span>
            <span class="list" @click="dealCal('list')">
              <img ref="listRef" class="ings" :src="calImg.lists" alt="" />
            </span>
          </div>
        </div>
        <FullCalendar
          ref="fullCalendar"
          :options="calendarOptions"
        ></FullCalendar>
      </div>
    </div>
    <el-dialog
      center
      title="农事任务类型"
      v-model="open"
      width="644px"
      append-to-body
      :destroy-on-close="true"
    >
      <div class="taskTypeCard">
        <el-checkbox-group :max="1" v-model="radio1">
          <el-checkbox label="agro_task_raking" border>
            整地<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_seeding" border>
            播种<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_fertilizer" border>
            施肥<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_weed" border>
            除草<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_intertill" border>
            中耕<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_pesticide" border>
            打药<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_irrigation" border>
            灌溉<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
          <el-checkbox label="agro_task_reap" border>
            收割<span
              style="
                display: block;
                margin-top: 10px;
                margin-left: 2px;
                width: 15px;
                height: 4px;
                background: #69dbb9;
                border-radius: 0px 0px 0px 0px;
              "
            ></span>
          </el-checkbox>
        </el-checkbox-group>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="open = false">取 消</el-button>
          <el-button type="primary" @click="goFarming">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      center
      title="新增农事任务"
      :destroy-on-close="true"
      v-model="show"
      width="600px"
      append-to-body
    >
      <PublicItem
        :selectConfig="selectConfig"
        @cancel="closeAgro"
        @getList="getList"
      ></PublicItem>
    </el-dialog>

    <el-dialog
      center
      :title="plantitle"
      v-model="plan"
      width="800px"
      append-to-body
      :destroy-on-close="true"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item
          v-if="parentId == 0"
          class="form-item"
          label="农事阶段"
          prop="stageName"
        >
          <el-input v-model.trim="form.stageName" placeholder="请输入" />
        </el-form-item>
        <el-form-item
          v-else
          class="form-item"
          label="栽培阶段"
          prop="stageName"
        >
          <el-input v-model.trim="form.stageName" placeholder="请输入" />
        </el-form-item>
        <el-form-item
          v-if="parentId != 0"
          class="form-item"
          label="栽培周期"
          prop="periodName"
        >
          <el-input v-model.trim="form.periodName" placeholder="请输入" />
        </el-form-item>
        <el-form-item
          v-if="parentId != 0"
          class="form-item"
          label="开始日期"
          prop="plantingPlanDate"
        >
          <el-date-picker
            v-model="form.plantingPlanDate"
            value-format="YYYY-MM-DD"
            placeholder="请选择"
          />
        </el-form-item>
        <el-form-item
          v-if="parentId != 0"
          class="form-item"
          label="持续时间"
          prop="period"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.trim="form.period"
            placeholder="请输入"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="closePlanStage">取 消</el-button>
          <el-button type="primary" @click="addPlan">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      center
      title="任务详情"
      :before-close="closeFarmingDialog"
      v-model="task"
      width="800px"
      append-to-body
      :destroy-on-close="true"
    >
      <el-descriptions v-if="queryTask" :column="2" border>
        <el-descriptions-item label="任务名称">{{
          selectConfig.form.agroName
        }}</el-descriptions-item>
        <el-descriptions-item label="开始日期">{{
          parseTime(selectConfig.form.startDate, "{y}-{m}-{d}")
        }}</el-descriptions-item>
        <el-descriptions-item label="任务周期">{{
          selectConfig.form.taskPeriod
        }}</el-descriptions-item>
        <el-descriptions-item label="农机">{{
          selectConfig.form.farmMachinery
        }}</el-descriptions-item>
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="深翻深度"
          >{{ selectConfig.form.turningOverDepth }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="耙地深度"
          >{{ selectConfig.form.rakingDepth }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="旋地深度"
          >{{ selectConfig.form.gyrationDepth }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="耙地次数"
          >{{ selectConfig.form.rakingTimes }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="旋转次数"
          >{{ selectConfig.form.gyrationTimes }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="株距"
          >{{ selectConfig.form.rowSpacing }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="行距"
          >{{ selectConfig.form.arrayPitch }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="亩用种量"
          >{{ selectConfig.form.seedUsage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="播种方式"
          >{{ selectConfig.form.seedingMethod }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_fertilizer'"
          label="施肥类型"
          >{{ selectConfig.form.fertilizationType }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            selectConfig.form.fertilizationType === '基肥'
          "
          label="基肥用量"
          >{{ selectConfig.form.baseFertilizerDosage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            selectConfig.form.fertilizationType === '基肥'
          "
          label="基肥名称"
          >{{ selectConfig.form.baseFertilizerName }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            selectConfig.form.fertilizationType === '追肥'
          "
          label="追肥用量"
          >{{ selectConfig.form.baseFertilizerDosage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            selectConfig.form.fertilizationType === '追肥'
          "
          label="追肥名称"
          >{{ selectConfig.form.baseFertilizerName }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_pesticide'"
          label="药品名称"
          >{{ selectConfig.form.pesticideName }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_pesticide'"
          label="药品用量"
          >{{ selectConfig.form.pesticideDosage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_irrigation'"
          label="灌溉方式"
          >{{ selectConfig.form.irrigationMethod }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_irrigation'"
          label="灌溉量"
          >{{ selectConfig.form.irrigationAmount }}</el-descriptions-item
        >
      </el-descriptions>
      <PublicItem
        v-if="!queryTask"
        :selectConfig="selectConfig"
        @cancel="closeAgro"
        @getList="getList"
      />
      <template #footer>
        <div v-if="selectConfig.disabled" class="dialog-footer">
          <el-button type="primary" @click="taskEdit">编辑</el-button>
          <el-button type="primary" @click="taskDel">删除</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import cal from "@/assets/images/planting-plan/cal.png";
import cal1 from "@/assets/images/planting-plan/cal1.png";
import list from "@/assets/images/planting-plan/list.png";
import list1 from "@/assets/images/planting-plan/list1.png";
import {
  Plus,
  CirclePlus,
  Close,
  Edit,
  ArrowLeft,
  ArrowRight,
} from "@element-plus/icons-vue";
import { ElButton } from "element-plus";
import { reactive, toRefs, getCurrentInstance, onMounted, nextTick } from "vue";
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import calenderFormate from "js-calendar-converter";
import { formatDate } from "@/utils/index";
import { queryRaiseCrops } from "@/api/land/map";
import { dicttype, dictdata, getPlan, getPlanPage } from "@/api/plant/plan.js";
import {
  updatePlanTypeData,
  createPlanTypeData,
  deletePlanTypeData,
  getPlanTypeDataByPlantingPlanId,
} from "@/api/plant/planTypeData.js";
import { getTaskFiled } from "@/api/agro/taskTemplateInfo.js";
import { deleteTaskInfo, getTaskInfo } from "@/api/agro/taskInfo";
import PublicItem from "@/components/FormItem/PublicItem.vue";
import { useRouter, useRoute, onBeforeRouteLeave } from "vue-router";
import { parseTime } from "@/utils/ruoyi";

const { proxy } = getCurrentInstance();
const route = useRoute();
const router = useRouter();

const data = reactive({
  plantitle: "",
  planImg: "",
  parentId: 0,
  calImg: {
    cals: cal,
    lists: list,
  },
  nowDate: "",
  plannameClass: "",
  queryTask: false,
  task: false,
  isCrops: true,
  optionsCropsType: false,
  options: false,
  defaultProps: {
    id: "id",
    label: "stageName",
    children: "taskInfoList",
  },
  selectConfig: {
    disabled: false,
    searchShow: true,
    formItem: [],
    stageCode: 0,
    plantingPlanId: 0,
    type: "",
  },
  queryParam: {
    pageNo: 1,
    pageSize: 10,
    type: "plan_type_maize",
  },
  planQueryParam: {
    pageNo: 1,
    pageSize: 10,
    dictType: "plan_type_maize",
  },
  planParam: {
    plantingPlanId: null,
  },
  planTypeList: [],
  planName: "",
  cropsName: "",
  cropsTypeName: "",
  calendarOptions: {
    plugins: [dayGridPlugin, interactionPlugin, listPlugin],
    locale: "zh-cn", // 切换语言，当前为中文
    navLinks: false,
    headerToolbar: {
      left: "",
      center: "title",
      right: "today prev next cal list",
    },
    height: "77vh",
    headerToolbar: true,
    dateClick: dayClick, //当用户点击日期或时间时触发的事件
    views: {
      dayGridMonth: {
        displayEventTime: false, //是否显示时间
        dayCellContent(item) {
          let _date = formatDate(item.date).split("-");
          let _dateF = calenderFormate.solar2lunar(
            _date[0],
            _date[1],
            _date[2]
          );
          if (item.isToday) {
            if (_dateF.Term) {
              return {
                html: `<div><div style="font-size: 18px;">${
                  _dateF.cDay
                }</div><span>${
                  _dateF.Term ? _dateF.Term : ""
                }</span><span style="display: inline-block;width: 8px;height: 8px;background: #00F4C3;border-radius: 50%;"></span></div>`,
              };
            } else {
              return {
                html: `<div><div style="font-size: 18px;">${_dateF.cDay}</div><span>${_dateF.IDayCn}</span><span style="display: inline-block;width: 8px;height: 8px;background: #00F4C3;border-radius: 50%;"></span></div>`,
              };
            }
          } else {
            if (_dateF.Term) {
              return {
                html: `<div><div style="font-size: 18px;">${
                  _dateF.cDay
                }</div><div>${_dateF.Term ? _dateF.Term : ""}</div></div>`,
              };
            } else {
              return {
                html: `<div><div style="font-size: 18px;">${_dateF.cDay}</div><div>${_dateF.IDayCn}</div></div>`,
              };
            }
          }
        },
      },
      listMonth: {
        displayEventTime: false, //是否显示时间
        dayHeaderContent(item) {
          // let _date = formatDate(item.date).split('-')
          // let _dateF = calenderFormate.solar2lunar(_date[0], _date[1], _date[2])
          // return { html: `<div style="text-align:left;background: #fff;"><label>${_dateF.cDay}</label><span>${_dateF.IDayCn}</span><span>${_dateF.Term ? _dateF.Term : ''}</span></div>` }
          return {
            html: `<div style="text-align:left;background: #fff;"><label>${item.text}</label><span style="display: inline-block;margin-left: 15px;width: 1050px;height: 0px;border-top: 1px solid #DBDBDB;"></span></div>`,
          };
        },
      },
    },
    // allDaySlot: false,
    editable: false, //是否可编辑
    selectable: true,
    moreLinkClassNames: "more-btns",
    moreLinkContent: "查看更多",
    dayMaxEvents: true,
    eventDidMount: eventRender,
    eventClick: eventClick, //日程点击事件
    nextDayThreshold: "01:00:00",
    events: [],
    buttonText: {
      today: "今天",
      cal: "日历",
      list: "列表",
    },
    customButtons: {
      cal: {
        text: "日历",
        click: () => {
          proxy.$refs.fullCalendar.getApi().changeView("dayGridMonth");
        },
      },
      list: {
        text: "列表",
        click: () => {
          proxy.$refs.fullCalendar.getApi().changeView("listMonth");
        },
      },
    },
  },
  treeData: [],
  open: false,
  show: false,
  plan: false,
  startDate: "",
  radio1: [],
  form: {},
  rules: {
    stageName: [
      { required: true, message: "请输入阶段名称", trigger: "blur" },
      { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
    ],
    periodName: [
      { required: true, message: "请输入周期名称", trigger: "blur" },
      { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
    ],
    plantingPlanDate: [
      { required: true, message: "请选择今日及之后的日期", trigger: "change" },
    ],
    period: [
      { required: true, message: "请输入阶段周期", trigger: "blur" },
      {
        max: 3,
        message: "请填写3位整数",
        trigger: "blur",
        pattern: /^-?[0-9]\d*$/,
      },
    ],
  },
});
const {
  plantitle,
  planImg,
  parentId,
  calImg,
  nowDate,
  plannameClass,
  queryTask,
  task,
  isCrops,
  optionsCropsType,
  options,
  defaultProps,
  selectConfig,
  queryParam,
  planQueryParam,
  planParam,
  planTypeList,
  planName,
  cropsName,
  cropsTypeName,
  calendarOptions,
  treeData,
  open,
  show,
  plan,
  startDate,
  radio1,
  form,
  rules,
} = toRefs(data);

function getList() {
  radio1.value = [];
  getPlanTypeDataByPlantingPlanId({
    plantingPlanId: planParam.value.plantingPlanId,
  }).then((response) => {
    planTypeList.value = [];
    treeData.value = [];
    planTypeList.value = response.data;
    treeData.value = response.data;
    planTypeList.value.forEach((ele) => {
      if (ele.planTypeDataChildList !== null) {
        ele.taskInfoList = ele.planTypeDataChildList;
        ele.planTypeDataChildList.forEach((item) => {
          if (item.taskInfoList != null) {
            item.taskInfoList.forEach((items) => {
              items.stageName = items.agroName;
              items.plantingPlanDate = items.startDate;
              items.period = items.taskPeriod;
              dealEvents(items);
            });
          }
        });
      }
    });
    treeData.value = planTypeList.value;
  });
}
function openPlanStateOne() {
  form.value = {};
  parentId.value = 0;
  plantitle.value = "农事阶段";
  plan.value = true;
}
function closePlanStage() {
  form.value = {};
  parentId.value = 0;
  plan.value = false;
  getList();
}
function dealEvents(param) {
  let backgroundColor = getColor(param.typeTableName);
  let name = param.agroName;
  let end = +new Date(param.startDate) + param.taskPeriod * 24 * 3600000;
  let val = {
    id: param.id,
    title: name,
    start: parseTime(param.startDate, "{y}-{m}-{d}"),
    end: parseTime(end, "{y}-{m}-{d}"),
    textColor: "#FFFFFF",
    backgroundColor: backgroundColor,
    borderColor: "#fff",
    overlap: true, //允许时间重叠，即可以与其他事件并存，不写默认就是false
  };
  calendarOptions.value.events.push(val);
}
function getColor(val) {
  const backgroundColor = {
    agro_task_fertilizer: "#FFDD00",
    agro_task_intertill: "#00DADA",
    agro_task_irrigation: "#52C41A",
    agro_task_pesticide: "#FA8C16",
    agro_task_raking: "#722ED1",
    agro_task_reap: "#FF4D4F",
    agro_task_seeding: "#20C7A8",
    agro_task_weed: "#5C8DF8",
  };
  return backgroundColor[val] ? backgroundColor[val] : false;
}
function getCrops() {
  queryRaiseCrops().then((response) => {
    options.value = response.data;
  });
}
function taskEdit() {
  queryTask.value = false;
  selectConfig.value.disabled = false;
}
function taskDel() {
  proxy.$modal
    .confirm("是否确认删除")
    .then(function () {})
    .then(() => {
      deleteTaskInfo(selectConfig.value.form.id);
      calendarOptions.value.events = [];
      task.value = false;
      proxy.$modal.msgSuccess("删除成功");
      getList();
    })
    .catch(() => {});
}
function closeAgro() {
  show.value = false;
  task.value = false;
  plan.value = false;
}

async function getTaskFileds(val) {
  let res = await getTaskFiled(val);
  selectConfig.value.formItem = res.data;
  selectConfig.value.formItem.forEach((ele) => {
    if (ele.prop === "startDate") {
      ele.data = startDate.value;
      return;
    }
  });
}

function goFarming() {
  if (radio1.value.length > 0) {
    open.value = false;
    selectConfig.value.type = radio1.value[0];
    getTaskFileds({ typeTableName: radio1.value[0] });
    show.value = true;
  } else {
    proxy.$message.error("请选择对应的任务类型");
  }
}
/**
 * 树
 */
function selectCrops(code) {
  isCrops.value = false;
  optionsCropsType.value = options.value.filter((ele) => {
    if (ele.code === code) return ele.cropsVarietiesList;
  })[0].cropsVarietiesList;
}

function renderContent(h, { node, data, store }) {
  if (node.level == 1) {
    return h(
      "span",
      {
        class: "custom-tree-node",
        style: { background: "#EAFADF",overflow:"hidden" },
      },
      [
        h(
          "span",
          {
            style: {
              marginLeft: "11px",
              fontWeight: "600",
              display: "inline-block",
              "max-width": "80%",
              "overflow": "hidden",
              "text-overflow": "ellipsis",
              "white-space": "nowrap"
            },
          },
          data.stageName
        ),
        h("span", { class: "treeNodeDel" }, [
          h(ElButton, {
            size: "default",
            icon: CirclePlus,
            link: true,
            onClick: () => addNodePlanStage(node, data),
          }),
        ]),
      ]
    );
  } else if (node.level == 2) {
    return h(
      "span",
      {
        class: "custom-tree-node",
        style: { textAlign: "left" },
      },
      [
        h("span", { class: "treePeriodName" }, data.periodName),
        h("span", { class: "treePeriod" }, `${data.period}天`),
        h("span", { class: "treeNodeCir mt11" }),
        h("span", { class: "treeStageName mt11" }, data.stageName),
        h("span", { class: "treeNodeDel" }, [
          h(ElButton, {
            size: "default",
            icon: Edit,
            link: true,
            onClick: () => append(node, data),
          }),
          h(ElButton, {
            size: "default",
            icon: Close,
            link: true,
            onClick: () => remove(node, data),
          }),
        ]),
      ]
    );
  } else {
    return h(
      "span",
      {
        class: "custom-tree-node",
        style: {
          textAlign: "left",
          fontSize: "14px",
          fontFamily: "PingFang SC-Regular, PingFang SC",
        },
      },
      [
        h("span", { class: "treeTaskLine" }),
        h(
          "span",
          { class: "mt11", style: { marginLeft: "14%" } },
          parseTime(data.plantingPlanDate, "{m}-{d}")
        ),
        h("span", { class: "treeTaskPeriod mt11" }, `${data.period}天`),
        h("span", { class: "treeName mt11" }, data.agroName),
        h("span", { class: "treeNodeDel" }, [
          h(ElButton, {
            size: "default",
            icon: Edit,
            link: true,
            onClick: () => append(node, data),
          }),
          h(ElButton, {
            size: "default",
            icon: Close,
            link: true,
            onClick: () => remove(node, data),
          }),
        ]),
      ]
    );
  }
}

function addNodePlanStage(node, data) {
  form.value = {};
  parentId.value = data.id;
  form.value.parentId = data.id;
  plantitle.value = "栽培阶段";
  plan.value = true;
}
function remove(node, data) {
  if (node.level === 2) {
    proxy.$modal
      .confirm("是否确认删除")
      .then(function () {
        return deletePlanTypeData(data.id);
      })
      .then(() => {
        proxy.$modal.msgSuccess("删除成功");
        getList();
      })
      .catch(() => {});
  } else {
    proxy.$modal
      .confirm("是否确认删除")
      .then(function () {
        deleteTaskInfo(data.id);
      })
      .then(() => {
        proxy.$modal.msgSuccess("删除成功");
        calendarOptions.value.events = [];
        getList();
      })
      .catch(() => {});
  }
}
async function append(node, data) {
  plantitle.value = "栽培阶段";
  if (node.level === 2) {
    parentId.value = data.parentId;
    form.value = data;
    form.value.plantingPlanDate = parseTime(
      form.value.plantingPlanDate,
      "{y}-{m}-{d}"
    );
    plan.value = true;
  } else {
    queryTask.value = true;
    let res = await getTaskInfo(data.id);
    form.value = res.data;
    selectConfig.value.form = res.data;
    selectConfig.value.edit = true;
    selectConfig.value.disabled = false;
    selectConfig.value.type = res.data.typeTableName;
    queryTask.value = false;
    task.value = true;
  }
}
function closeFarmingDialog() {
  closeAgro();
}
function addPlan() {
  proxy.$refs["formRef"].validate((valid) => {
    if (!valid) {
      return;
    }
    form.value.plantingPlanId = planParam.value.plantingPlanId;
    form.value.parentId = parentId.value;
    // form.value.status = 0
    // 修改的提交
    if (form.value.id != null) {
      updatePlanTypeData(form.value).then((response) => {
        proxy.$modal.msgSuccess("修改成功");
        plan.value = false;
        getList();
        form.value = {};
      });
      return;
    }
    // 添加的提交
    createPlanTypeData(form.value).then((response) => {
      proxy.$modal.msgSuccess("新增成功");
      plan.value = false;
      getList();
      form.value = {};
    });
  });
}
/**
 * 日历
 */
function eventClick(calEvent) {
  selectConfig.value.disabled = true;
  getTaskInfo(calEvent.event._def.publicId).then((response) => {
    form.value = response.data;
    selectConfig.value.form = response.data;
    selectConfig.value.edit = true;
    selectConfig.value.type = response.data.typeTableName;
    task.value = true;
    queryTask.value = true;
  });
}
function dayClick(date) {
  selectConfig.value.form = {};
  let data = [];
  data = treeData.value;
  let dates = "";
  dates = +new Date(date.date);
  const result = data.filter((item) => {
    return item.taskInfoList.some((task) => {
      return (
        dates >= +new Date(task.plantingPlanDate) &&
        dates <=
          +new Date(task.plantingPlanDate) + (task.period - 1) * 24 * 3600000
      );
    });
  });
  const res = result[0]?.planTypeDataChildList.filter((ele) => {
    return (
      dates >= +new Date(ele.plantingPlanDate) &&
      dates <= +new Date(ele.plantingPlanDate) + (ele.period - 1) * 24 * 3600000
    );
  });
  if (res?.length > 0) {
    selectConfig.value.stageCode = res[0].stageCode;
    selectConfig.value.plantingPlanId = planParam.value.plantingPlanId;
    selectConfig.value.edit = false;
    selectConfig.value.disabled = false;
    selectConfig.value.form.startDate = parseTime(date.date);
    open.value = true;
    proxy.$forceUpdate();
  } else {
    proxy.$modal.msgSuccess("请选择符合的时段");
    return;
  }
}

function eventRender(info) {
  if (info.view.type === "listMonth") {
    let el = info.el;
    el.classList.remove("fc-list-event-graphic");
    el.classList.add("fc-list-item");
    el.style.backgroundColor = info.backgroundColor;
    el.style.borderColor = info.borderColor;
    el.style.color = "#fff";
    el.style.height = "45px";
    el.querySelectorAll("td, th").forEach((cell) => {
      cell.style.backgroundColor = "transparent";
    });
  } else if (info.view.type === "dayGridMonth") {
    let el = info.el;
    el.classList.add("fc-daygrid-event");
    el.querySelectorAll("td, th").forEach((cell) => {
      cell.style.backgroundColor = "#fff !important";
    });
  }
}
function dealCal(val) {
  const api = proxy.$refs.fullCalendar.getApi();
  const actions = {
    list: () => {
      api.changeView("listMonth");
      calImg.value.cals = cal1;
      calImg.value.lists = list1;
    },
    date: () => {
      api.changeView("dayGridMonth");
      calImg.value.cals = cal;
      calImg.value.lists = list;
    },
    today: () => {
      nowDate.value =
        new Date().getFullYear() + "-" + (new Date().getMonth() + 1);
      api.today();
    },
    left: () => {
      let currentDate = nowDate.value;
      currentDate = new Date(currentDate);
      let lastDate = currentDate.setMonth(currentDate.getMonth() - 1);
      lastDate = new Date(lastDate);
      let lastYear = lastDate.getFullYear();
      let lastMonth = lastDate.getMonth() + 1;
      lastDate = lastYear + "-" + lastMonth;
      nowDate.value = lastDate;
      api.prev();
    },
    right: () => {
      let currentDate = nowDate.value;
      currentDate = new Date(currentDate);
      let nextDate = currentDate.setMonth(currentDate.getMonth() + 1);
      nextDate = new Date(nextDate);
      let nextYear = nextDate.getFullYear();
      let nextMonth = nextDate.getMonth() + 1;
      nextDate = nextYear + "-" + nextMonth;
      nowDate.value = nextDate;
      api.next();
    },
  };
  const action = actions[val];
  if (action) {
    action();
  } else {
    return;
    // 处理无效的 val 值
  }
}

onBeforeRouteLeave((to, from, next) => {
  if (to.name !== "Calendar") {
    if (proxy.$vnode && proxy.$vnode.data.keepAlive) {
      if (
        proxy.$vnode.parent &&
        proxy.$vnode.parent.componentInstance &&
        proxy.$vnode.parent.componentInstance.cache
      ) {
        if (proxy.$vnode.componentOptions) {
          var key =
            proxy.$vnode.key == null
              ? proxy.$vnode.componentOptions.Ctor.cid +
                (proxy.$vnode.componentOptions.tag
                  ? `::${proxy.$vnode.componentOptions.tag}`
                  : "")
              : proxy.$vnode.key;
          var cache = proxy.$vnode.parent.componentInstance.cache;
          var keys = proxy.$vnode.parent.componentInstance.keys;
          if (cache[key]) {
            if (keys.length) {
              var index = keys.indexOf(key);
              if (index > -1) {
                keys.splice(index, 1);
              }
            }
            delete cache[key];
          }
        }
      }
    }
  }
  next();
});

const init = () => {
  nowDate.value = new Date().getFullYear() + "-" + (new Date().getMonth() + 1);
  if (route.query.types === "query") {
    const row = JSON.parse(route.query.row);
    planParam.value.plantingPlanId = row.id;
    getList();
    getPlan(planParam.value.plantingPlanId).then((response) => {
      planName.value = `${response.data.planName}`;
      cropsName.value = `${response.data.cropsName}`;
      cropsTypeName.value = `${response.data.cropsTypeName}`;
      planImg.value = `${response.data.imageUrl}`;
    });
    getCrops();
  } else if (route.query.types === "add") {
    planParam.value.plantingPlanId = route.query.id;
    getList();
    getPlan(planParam.value.plantingPlanId).then((response) => {
      planName.value = `${response.data.planName}`;
      cropsName.value = `${response.data.cropsName}`;
      cropsTypeName.value = `${response.data.cropsTypeName}`;
      planImg.value = `${response.data.imageUrl}`;
    });
    getCrops();
  } else {
    router.push("/planting/farming");
    return;
  }
};
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss">
.main {
  position: relative;

  .tree {
    position: absolute;
    top: 30px;
    left: 27px;
    width: 440px;

    .header {
      width: 100%;
      height: 160px;
      border-radius: 8px 8px 0px 0px;
      background-image: url("../../../assets/images/planting-plan/planbg1.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;

      .btnStyle {
        padding-top: 13px;
        height: 32px;

        .span {
          cursor: pointer;
          margin-left: 28px;
          height: 18px;
          font-size: 14px;
          font-family: PingFang SC-Medium, PingFang SC;
          font-weight: 600;
          color: #333333;
          line-height: 18px;
        }

        .span1 {
          margin-left: 300px;
        }
      }

      .info {
        margin-top: 35px;
        margin-left: 30px;

        // display: flex;
        .imgs {
          float: left;
          height: 76px;
          width: 76px;
          margin-right: 15px;
          margin-top: -10px;
        }

        .imgs::after {
          content: "";
          display: block;
          clear: both;
        }

        .planname {
          width: 220px;
          height: 28px;
          font-size: 20px;
          font-family: PingFang SC-Semibold, PingFang SC;
          font-weight: 600;
          color: #377e62;
          line-height: 28px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;

          background: linear-gradient(
            90deg,
            #62d69c 0%,
            rgba(99, 215, 158, 0) 61%
          );
          background-size: 50% 40%;
          background-repeat: no-repeat;
          background-position-y: 100%;
        }

        .crops {
          margin-top: 4px;
          height: 22px;
          font-size: 16px;
          font-family: PingFang SC-Regular, PingFang SC;
          font-weight: 400;
          color: #333333;
          line-height: 19px;

          .line {
            display: inline-block;
            width: 0;
            height: 10px;
            border: 1px solid #bac3cb;
          }
        }
      }
    }
  }

  .FullCalendar {
    position: absolute;
    top: 30px;
    right: 20px;
    margin-left: 1vw;
    width: 1200px;

    .calHeader {
      display: flex;
      flex-wrap: nowrap;
      height: 60px;
      background: #ffffff;
      border-radius: 8px 8px 0px 0px;
      padding: 13px 0;

      .left,
      .center,
      .right {
        flex: 1;
      }

      .center {
        text-align: center;
        height: 28px;
        font-size: 24px;
        font-family: PingFang SC-Semibold, PingFang SC;
        font-weight: 600;
        color: #333333;
        line-height: 28px;
      }

      .right {
        text-align: right;

        .todayPrevNext {
          display: inline-block;
          cursor: pointer;
          width: 118px;
          height: 34px;
          background: #eafbf5;
          border-radius: 15px 15px 15px 15px;
          font-size: 16px;
          font-family: PingFang SC-Medium, PingFang SC;
          font-weight: 500;
          color: #333333;
          line-height: 34px;
          margin-right: 21px;
          vertical-align: top;

          .today {
            display: inline-block;
            font-size: 16px;
            font-family: PingFang SC-Medium, PingFang SC;
            font-weight: 500;
            color: #333333;
            margin-right: 14px;
          }

          .prev {
            display: inline-block;
            margin-right: 14px;
          }

          .next {
            display: inline-block;
            margin-right: 10px;
          }
        }

        .date,
        .list {
          display: inline-block;
          cursor: pointer;
          height: 27px;
          font-size: 16px;
        }

        .list {
          margin-right: 52px;
        }

        .ings {
          width: 32px;
          height: 32px;
          display: inline-block;
        }

        .date {
          margin-right: 16px;
        }
      }
    }
  }
}

.taskTypeCard {
  margin-left: 50px;

  :deep(.el-checkbox.is-bordered.el-checkbox--default .el-checkbox__inner) {
    margin-left: 10px;
  }

  :deep(.el-checkbox.is-bordered.el-checkbox--default .el-checkbox__label) {
    display: block;
    font-size: 16px;
    font-family: PingFang SC-Medium, PingFang SC;
    font-weight: 500;
    color: #333333;
    margin-top: 13px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(1)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_raking.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
    margin-bottom: 20px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(2)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_seeding.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(3)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_fertilizer.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
    margin-bottom: 20px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(4)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_weed.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(5)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_intertill.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
    margin-bottom: 20px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(6)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_pesticide.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(7)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_irrigation.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
  }

  :deep(.el-checkbox.is-bordered:nth-child(8)) {
    width: 215px;
    height: 103px;
    background-image: url("../../../assets/images/planting-plan/agro_task_reap.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    border-radius: 9px 9px 9px 9px;
  }
}

:deep(.el-dialog__header) {
  // background-color: #F0F2F2;
  height: 62px;
  background-image: url("../../../assets/images/planting-plan/header.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

:deep(.el-dialog__title) {
  font-size: 24px;
  font-family: PingFang SC-Medium, PingFang SC;
  font-weight: 500;
  color: #50a899;
}

//树节点样式
:deep(.custom-tree-node) {
  display: inline-block;
  line-height: 35px;
  text-align: center;
  font-size: 16px;
  height: 35px;
  position: relative;

  .mt11 {
    margin-left: 11px;
  }

  .treePeriodName {
    display: inline-block;
    width: 110px;
    font-family: PingFang SC-Medium, PingFang SC;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.8);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .treePeriod {
    position: absolute;
    top: 10px;
    line-height: 16px;
    font-size: 14px !important;
    font-family: PingFang SC-Medium, PingFang SC;
    font-weight: 500;
    color: #874215;
    padding: 1px 6px;
    background: linear-gradient(180deg, #f8d9aa 0%, #f7ce95 100%);
    border-radius: 0 8px 0 8px;
  }

  .treeStageName {
    display: inline-block;
    width: 100px;
    font-size: 16px !important;
    font-family: PingFang SC-Medium, PingFang SC;
    font-weight: 500;
    color: #0d7b1e;
    font-size: 12px;
    font-weight: 400;
    position: absolute;
    left: 47%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .treeTaskPeriod {
    padding: 0 4px;
    color: #52c41a;
    font-size: 12px !important;
    background: #f6ffed;
    border-radius: 2px 2px 2px 2px;
    border: 1px solid #b7eb8f;
    // position: absolute;
    // left: 30%;
  }

  .treeTaskLine {
    height: 45px;
    border: 1px solid #a0d911;
    position: absolute;
    left: 41%;
    top: -10px;
  }

  .treeName {
    width: 120px;
    position: absolute;
    left: 45%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

:deep(.treeNodeCir) {
  display: inline-block;
  border-radius: 50%;
  position: absolute;
  left: 40%;
  top: 40%;
  width: 8px;
  height: 8px;
  background: #a0d911;
}

.treeNodeCir::after {
  content: "";
  display: block;
  clear: both;
}

:deep(.treeNodeDel) {
  width: 50px;
  height: 35px;
  float: right;
  margin-right: 30px;

  :deep(.el-button--default) {
    width: 100%;
    height: 100%;
    font-size: 12px !important;
    width: 10px !important;
    margin-top: -5px;
  }
}

:deep(.treeNodeDel::after) {
  content: "";
  display: block;
  clear: both;
}

.tree-line {
  height: 65.3vh;
  overflow-y: auto;
  padding-top: 5px;

  :deep(.el-tree-node__content) {
    height: 35px;
    margin-right: 15px;
    margin-top: 10px;
  }

  :deep(.el-tree-node__content:hover) {
    background-color: transparent !important;
  }

  :deep(.el-tree-node) {
    position: relative;
  }

  :deep(.el-tree-node__expand-icon) {
    padding: 6px 0 6px 3px !important;
  }
}

//日历样式

:deep(.fc-h-event .fc-event-main-frame) {
  height: 54px;
}

:deep(.fc-h-event .fc-event-title) {
  // text-align: center;
  line-height: 34px;
  margin-left: 20px;
}

.dialog-footer {
  text-align: right;
}

:deep(.fc .fc-scrollgrid) {
  border-left-width: 0;
  border-top-width: 0;
  border-right-width: 0;
  border-bottom-width: 0;
}

:deep(th.fc-col-header-cell.fc-day) {
  border-right-style: hidden;
  border-bottom-style: hidden;
  border-left-style: hidden;
  border-top-style: hidden;
}

:deep(.fc-theme-standard td, .fc-theme-standard th) {
  background: #ffffff;
  opacity: 1;
  border: 10px solid #f0f2f2;
  border-radius: 10px;
}

:deep(.fc .fc-daygrid-day.fc-day-today) {
  background-color: #ccffed !important;
  opacity: 1;
  border: 2px solid #20c7a8 !important;
}

:deep(.fc .fc-daygrid-more-link) {
  // margin-top: 30px;
  margin-left: 50px;
}

:deep(.fc-popover .fc-more-popover) {
  top: 65px !important;
  left: 490px !important;
}

:deep(.fc .fc-scrollgrid-section-header > *) {
  border-left-width: 0;
  border-top-width: 0;
  border-right-width: 0;
  border-bottom-width: 0;
}

:deep(.fc .fc-cell-shaded, .fc .fc-day-disabled) {
  background: #fff;
}

:deep(.fc-theme-standard .fc-list) {
  border: none;
}

:deep(.fc .fc-list-event-graphic, .fc .fc-list-event-time) {
  width: 102px;
  visibility: hidden;
}

:deep(.fc-scrollgrid-sync-inner) {
  font-size: 16px;
}
</style>
