<template>
  <div>
    <div class="div_select" v-if="selectConfig.searchShow">
      <el-descriptions v-if="selectConfig.disabled" :column="2" border>
        <el-descriptions-item label="任务名称">{{
          form.agroName
        }}</el-descriptions-item>
        <el-descriptions-item label="开始日期">{{
          form.startDate
        }}</el-descriptions-item>
        <el-descriptions-item label="任务周期">{{
          form.taskPeriod
        }}</el-descriptions-item>
        <el-descriptions-item label="农机">{{
          form.farmMachinery
        }}</el-descriptions-item>
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="深翻深度"
          >{{ form.turningOverDepth }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="耙地深度"
          >{{ form.rakingDepth }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="旋地深度"
          >{{ form.gyrationDepth }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="耙地次数"
          >{{ form.rakingTimes }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="旋转次数"
          >{{ form.gyrationTimes }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="株距"
          >{{ form.rowSpacing }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="行距"
          >{{ form.arrayPitch }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="亩用种量"
          >{{ form.seedUsage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="播种方式"
          >{{ form.seedingMethod }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_fertilizer'"
          label="施肥类型"
          >{{ form.fertilizationType }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '基肥'
          "
          label="基肥用量"
          >{{ form.baseFertilizerDosage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '基肥'
          "
          label="基肥名称"
          >{{ form.baseFertilizerName }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '追肥'
          "
          label="追肥用量"
          >{{ form.baseFertilizerDosage }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '追肥'
          "
          label="追肥名称"
          >{{ form.baseFertilizerName }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_pesticide'"
          label="药品名称"
          >{{ form.pesticideName }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_irrigation'"
          label="灌溉方式"
          >{{ form.irrigationMethod }}</el-descriptions-item
        >
        <el-descriptions-item
          v-if="selectConfig.type === 'agro_task_irrigation'"
          label="灌溉量"
          >{{ form.irrigationAmount }}</el-descriptions-item
        >
      </el-descriptions>

      <el-form
        v-else
        ref="formRef"
        :model="form"
        :disabled="selectConfig.disabled"
        :rules="rules"
        width="700px"
        label-width="150px"
      >
        <el-form-item label="任务名称" prop="agroName">
          <el-input
            clearable
            v-model.trim="form.agroName"
            size="small"
            placeholder="请输入任务名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker
            v-model="form.startDate"
            type="date"
            size="small"
            placeholder="请选择开始日期"
          />
        </el-form-item>
        <el-form-item label="任务周期" prop="taskPeriod">
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.taskPeriod"
            size="small"
            placeholder="请输入任务周期"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="农机" prop="farmMachinery">
          <el-select
            filterable
            clearable
            v-model.trim="form.farmMachinery"
            placeholder="请选择农机"
            size="small"
          >
            <el-option value="农机1" label="农机1" />
            <el-option value="农机2" label="农机2" />
            <el-option value="农机3" label="农机3" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="深翻深度"
          prop="turningOverDepth"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.turningOverDepth"
            size="small"
            placeholder="请输入深翻深度"
          ></el-input-number
          >cm
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="耙地深度"
          prop="rakingDepth"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.rakingDepth"
            size="small"
            placeholder="请输入耙地深度"
          ></el-input-number
          >cm
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="旋地深度"
          prop="gyrationDepth"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.gyrationDepth"
            size="small"
            placeholder="请输入旋地深度"
          ></el-input-number
          >cm
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="耙地次数"
          prop="rakingTimes"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.rakingTimes"
            size="small"
            placeholder="请输入耙地次数"
          ></el-input-number
          >次
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_raking'"
          label="旋转次数"
          prop="gyrationTimes"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.gyrationTimes"
            size="small"
            placeholder="请输入旋转次数"
          ></el-input-number
          >次
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="株距"
          prop="rowSpacing"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.rowSpacing"
            size="small"
            placeholder="请输入株距"
          ></el-input-number
          >cm
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="行距"
          prop="arrayPitch"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.arrayPitch"
            size="small"
            placeholder="请输入行距"
          ></el-input-number
          >cm
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="亩用种量"
          prop="seedUsage"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.seedUsage"
            size="small"
            placeholder="请输入亩用种量"
          ></el-input-number
          >kg/亩
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_seeding'"
          label="播种方式"
          prop="seedingMethod"
        >
          <!-- <el-input clearable v-model.trim="form.seedingMethod" size="small" placeholder="请输入播种方式"></el-input> -->
          <el-select
            filterable
            clearable
            v-model.trim="form.seedingMethod"
            placeholder="请选择播种方式"
            size="small"
          >
            <el-option value="撒播" label="撒播" />
            <el-option value="点播" label="点播" />
            <el-option value="条播" label="条播" />
            <el-option value="机械播种" label="机械播种" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_fertilizer'"
          label="施肥类型"
          prop="fertilizationType"
        >
          <el-select
            filterable
            clearable
            v-model.trim="form.fertilizationType"
            placeholder="请选择施肥类型"
            size="small"
          >
            <el-option value="基肥" label="基肥" />
            <el-option value="追肥" label="追肥" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '基肥'
          "
          label="基肥用量"
          prop="baseFertilizerDosage"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.baseFertilizerDosage"
            size="small"
            placeholder="请输入基肥用量"
          ></el-input-number
          >kg/亩
        </el-form-item>
        <el-form-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '基肥'
          "
          label="基肥名称"
          prop="baseFertilizerName"
        >
          <el-input
            clearable
            v-model.trim="form.baseFertilizerName"
            size="small"
            placeholder="请输入基肥名称"
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '追肥'
          "
          label="追肥用量"
          prop="baseFertilizerDosage"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.baseFertilizerDosage"
            size="small"
            placeholder="请输入追肥用量"
          ></el-input-number
          >kg/亩
        </el-form-item>
        <el-form-item
          v-if="
            selectConfig.type === 'agro_task_fertilizer' &&
            form.fertilizationType === '追肥'
          "
          label="追肥名称"
          prop="baseFertilizerName"
        >
          <el-input
            clearable
            v-model.trim="form.baseFertilizerName"
            size="small"
            placeholder="请输入追肥名称"
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_pesticide'"
          label="药品名称"
          prop="pesticideName"
        >
          <el-input
            clearable
            v-model.trim="form.pesticideName"
            size="small"
            placeholder="请输入药品名称"
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_pesticide'"
          label="药品用量"
          prop="pesticideDosage"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.pesticideDosage"
            size="small"
            placeholder="请输入药品用量"
          ></el-input-number
          >ml/亩
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_irrigation'"
          label="灌溉方式"
          prop="irrigationMethod"
        >
          <el-select
            filterable
            clearable
            v-model.trim="form.irrigationMethod"
            placeholder="请选择灌溉方式"
            size="small"
          >
            <el-option value="喷灌" label="喷灌" />
            <el-option value="漫灌" label="漫灌" />
            <el-option value="滴灌" label="滴灌" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="selectConfig.type === 'agro_task_irrigation'"
          label="灌溉量"
          prop="irrigationAmount"
        >
          <el-input-number
            :min="1"
            :step="1"
            :max="999"
            clearable
            v-model.number="form.irrigationAmount"
            size="small"
            placeholder="请输入灌溉量"
          ></el-input-number
          >m³
        </el-form-item>
      </el-form>
      <div class="footer" v-if="!selectConfig.disabled">
        <!-- <div> -->
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm(config)">确 定</el-button>
      </div>
      <div class="footer" v-else>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
let agroTask = [
  {
    name: "施肥",
    typeTableName: "agro_task_fertilizer",
    type: "0",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskFertilizerBaseVO",
  },
  {
    name: "中耕",
    typeTableName: "agro_task_intertill",
    type: "1",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskIntertillBaseVO",
  },
  {
    name: "灌溉",
    typeTableName: "agro_task_irrigation",
    type: "2",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskIrrigationBaseVO",
  },
  {
    name: "打药",
    typeTableName: "agro_task_pesticide",
    type: "3",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskPesticideBaseVO",
  },
  {
    name: "整地",
    typeTableName: "agro_task_raking",
    type: "4",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskRakingBaseVO",
  },
  {
    name: "收割",
    typeTableName: "agro_task_reap",
    type: "5",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskReapBaseVO",
  },
  {
    name: "播种",
    typeTableName: "agro_task_seeding",
    type: "6",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskSeedingBaseVO",
  },
  {
    name: "除草",
    typeTableName: "agro_task_weed",
    type: "7",
    typeModelName:
      "com.airohit.agriculture.module.plant.vo.taskInfo.TaskWeedBaseVO",
  },
];
import { createTask, updateTaskDetail } from "@/api/agro/taskTemplateInfo";
import { getTaskFiled } from "@/api/agro/taskTemplateInfo.js";
import { computed, ref, watch, unref } from "vue";
const props = defineProps({
  selectConfig: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["cancel", "getList"]);
const { proxy } = getCurrentInstance();
const form = ref({});
const rules = {
  agroName: [
    { required: true, message: "请输入任务名称", trigger: "blur" },
    { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
  ],
  startDate: [{ required: true, message: "请选择开始日期", trigger: "change" }],
  taskPeriod: [
    { required: true, message: "请输入任务周期", trigger: "blur" },
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  turningOverDepth: [
    { required: true, message: "请输入深翻深度", trigger: "blur" },
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  rakingDepth: [
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  gyrationDepth: [
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  rakingTimes: [
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  gyrationTimes: [
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  rowSpacing: [
    { required: true, message: "请填写株距", trigger: "blur" },
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  arrayPitch: [
    { required: true, message: "请填写行距", trigger: "blur" },
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  seedUsage: [
    { required: true, message: "请填写亩用种量", trigger: "blur" },
    {
      max: 3,
      message: "请填写3位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  seedingMethod: [
    { required: true, message: "请选择播种方式", trigger: "change" },
  ],
  fertilizationType: [
    { required: true, message: "请选择施肥类型", trigger: "change" },
  ],
  baseFertilizerDosage: [
    { required: true, message: "请填写用量", trigger: "blur" },
    {
      max: 6,
      message: "请填写6位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  baseFertilizerName: [
    { required: true, message: "请输入名称", trigger: "blur" },
    { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
  ],
  pesticideName: [
    { required: true, message: "请输入药品名称", trigger: "blur" },
    { max: 20, message: "请填写20个数字、汉字、字母、符号", trigger: "blur" },
  ],
  pesticideDosage: [
    { required: true, message: "请填写药品用量", trigger: "blur" },
    {
      max: 6,
      message: "请填写6位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
  irrigationMethod: [
    { required: true, message: "请选择灌溉方式", trigger: "change" },
  ],
  irrigationAmount: [
    { required: true, message: "请填写灌溉量", trigger: "blur" },
    {
      max: 6,
      message: "请填写6位整数",
      trigger: "blur",
      pattern: /^-?[0-9]\d*$/,
    },
  ],
};
const formRef = ref();

const startDate = computed(() => {
  proxy.$forceUpdate();
  return proxy.parseTime(form.value.startDate);
});

function cancel() {
  emit("cancel", true);
  form.value = {};
  // formRef.value.resetFields();
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) {
      return;
    }
    if (props.selectConfig.edit) {
      const param1 = {
        type: props.selectConfig.type,
      };
      let type = agroTask.filter((ele) => ele.typeTableName === param1.type);
      form.value.type = parseInt(type[0].type);
      const {
        tenantId,
        updateTime,
        deleted,
        createTime,
        planningStage,
        ...formRest
      } = form.value;
      updateTaskDetail(formRest).then((response) => {
        proxy.$modal.msgSuccess("修改成功");
        emit("cancel", true);
        emit("getList");
      });
      formRef.value.resetFields();
      return;
    }
    const param = {
      planningStage: props.selectConfig.stageCode,
      plantingPlanId: props.selectConfig.plantingPlanId,
      type: props.selectConfig.type,
    };
    let type = agroTask.filter((ele) => ele.typeTableName === param.type);
    param.type = parseInt(type[0].type);
    let params = { ...param, ...form.value };
    createTask(params).then((response) => {
      proxy.$modal.msgSuccess("新增成功");
      formRef.value.resetFields();
      emit("cancel", true);
      emit("getList");
    });
  });
}

onMounted(() => {
  proxy.$forceUpdate();
});

watch(
  () => props.selectConfig,
  () => {
    form.value = props.selectConfig.form;
  },
  { deep: true, immediate: true }
);
</script>

<style scoped lang="scss">
.footer {
  text-align: right;
}
</style>
