<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form class="form" :model="queryParams" ref="queryFormRef" :inline="true" v-show="showSearch">
      <el-form-item label="模板名称" prop="planName">
        <el-input v-model.trim="queryParams.planName" placeholder="请输入" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="作物名称" prop="crops">
        <el-select v-model="queryParams.crops" placeholder="请选择">
          <el-option v-for="item in options" :key="item.id" :label="item.cropsName" :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建日期" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
          :default-time="[new Date(2000, 1, 1, 0, 0, 0), new Date(2000, 2, 1, 23, 59, 59)]" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleQuery">搜索</el-button>
        <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
        <!-- <el-button class="add" type="primary" plain :icon="Plus" size="small" @click="handleAdd"
          >新增</el-button> -->
      </el-form-item>
    </el-form>

    <!-- 列表 -->
    <el-row :gutter="12" class="layout">
      <el-col :span="7" v-for="(item,index) in list" :key="item.id" class="pcol">
        <PlantingPlanCard 
          :index="(queryParams.pageNo - 1) * queryParams.pageSize + index + 1" 
          :plan-info="item"
          is-template
          @clone-plan="clonePlan"
          @update-plan="handleUpdate"
          @delete-plan="handleDelete"
          @is-publish="isPublish"
        />
      </el-col>
    </el-row>

    <!-- 分页组件 -->
      <pagination 
        v-show="total > 0" 
        :total="total" 
        v-model:page="queryParams.pageNo" 
        v-model:limit="queryParams.pageSize"
        layout="total, prev, pager, next" 
        @pagination="getList" 
      />
    <!-- 对话框(添加 / 修改) -->
    <el-dialog center :title="title" v-model="plan" width="800px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item class="form-item" label="计划名称" prop="planName">
          <el-input v-model.trim="form.planName" placeholder="请输入" />
        </el-form-item>
        <el-form-item v-if="!isClone" class="form-item" label="作物" prop="crops">
          <el-select @change="selectCrops" v-model="form.crops" placeholder="请选择">
            <el-option v-for="item in options" :key="item.code" :label="item.cropsName" :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="!isClone" class="form-item" label="品种" prop="cropsType">
          <el-select v-model="form.cropsType" placeholder="请选择">
            <el-option v-for="item in optionsCropsType" :key="item.code" :label="item.cropsVarietiesName"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="开始日期" prop="startTime">
          <el-date-picker v-model="form.startTime" value-format="YYYY-MM-DD" placeholder="请选择" />
        </el-form-item>
        <el-form-item v-if="!isClone" class="form-item" label="计划周期" prop="planningCycle">
          <el-input-number :min="1" :step="1" clearable v-model.trim="form.planningCycle" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="plan = false">取 消</el-button>
          <el-button type="primary" @click="addPlan">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import PlantingPlanCard from './components/PlantingPlanCard.vue';
import { Search, Refresh, Plus } from '@element-plus/icons-vue';
import { createPlan, updatePlan, deletePlan, getPlan, getPlanPage, cloneTemplate, planPublish } from "@/api/plant/plan";
import { queryRaiseCrops } from "@/api/land/map"
import { parseTime } from '@/utils/ruoyi'
import { useRouter } from 'vue-router'
const { proxy } = getCurrentInstance();
const router = useRouter()
const data = reactive({
  isClone: false,
  isCrops: true,
  // 遮罩层
  loading: true,
  // 导出遮罩层
  exportLoading: false,
  // 显示搜索条件
  showSearch: true,
  // 总条数
  total: 0,
  // 农户列表
  list: [
  ],
  options: [],
  optionsCropsType: [],
  // 弹出层标题
  title: "",
  // 是否显示弹出层
  open: false,
  plan: false,
  // 查询参数
  queryParams: {
    pageNo: 1,
    pageSize: 6,
    type: 1,
    isTemplate: 1,
    status: 0,
    planName: undefined,
    crops: undefined,
    createTime: undefined,
  },
  // 表单参数
  form: {
    status: 1,
  },
  // 表单校验
  rules: {
    planName: [
      { required: true, message: "请输入计划名称", trigger: "blur" },
      { max: 20, message: '请填写20个数字、汉字、字母、符号', trigger: 'blur' },
      // { message: '请勿输入特殊符号', trigger: 'blur', pattern: /^[\a-\z\A-\Z0-9\u4e00-\u9fe5]+$/ }
    ],
    crops: [
      { required: true, message: "请选择作物及品种", trigger: "change" },
    ],
    cropsType: [
      { required: true, message: "请选择作物及品种", trigger: "change" },
    ],
    startTime: [
      { required: true, message: "请选择开始日期", trigger: "change" },
    ],
    planningCycle: [
      { required: true, message: "请输入计划周期", trigger: "blur" },
      { max: 3, message: '请填写3位整数', trigger: 'blur', pattern: /^-?[0-9]\d*$/ },
      // { message: '请输入整数', trigger: 'blur', pattern: /^[0-9.]{1,}$/ }
    ],
  },
})

const { queryParams, list, total, showSearch, title, plan, form, rules, options, optionsCropsType, loading, exportLoading, isClone, isCrops } = toRefs(data)

function clonePlan(item) {
  reset()
  isClone.value = true
  title.value = "克隆计划";
  plan.value = true
  form.value.plantingPlanId = item.id
  form.value.type = 2
}

function selectCrops(code) {
  isCrops.value = false
  form.value.cropsType = null
  optionsCropsType.value = options.value.filter(ele => {
    if (ele.code === code) return ele.cropsVarietiesList
  })[0].cropsVarietiesList
}

function getCrops() {
  queryRaiseCrops().then(response => {
    options.value = response.data;
  });
}

/** 查询列表 */
function getList() {
  loading.value = true;
  // 执行查询
  getPlanPage(queryParams.value).then(response => {
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

/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    type: 1,
    isTemplate: 1,
    status: 0,
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
  queryParams.value.createTime = null
  proxy.resetForm("queryFormRef");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  title.value = "新增计划模板";
  plan.value = true;
  isClone.value = false;
}

function getCropName() {
  let crops = options.value.filter(ele => ele.cropsName === form.value.cropsName)
  let cropsVarietiesName = crops[0].cropsVarietiesList.filter(ele => ele.cropsVarietiesName === form.value.cropsTypeName)
  optionsCropsType.value = crops[0].cropsVarietiesList
  return {
    crops: crops[0].code,
    cropsTypeName: cropsVarietiesName[0].code,
  }
}

/** 修改按钮操作 */
function handleUpdate(row) {
  isClone.value = false;
  reset();
  const id = row.id;
  getPlan(id).then(response => {
    form.value = response.data;
    form.value.startTime = parseTime(form.value.startTime)
    let { crops, cropsTypeName } = getCropName()
    form.value.crops = crops
    form.value.cropsType = cropsTypeName
    title.value = "修改计划模板";
    plan.value = true;
  })
}

function isPublish(val, row) {
  reset();
  form.value = row
  form.value.status = val
  let param = {
    id: row.id,
    status: val
  }
  planPublish(param).then(response => {
    proxy.$modal.msgSuccess("成功");
    open.value = false;
    getList();
  });
}

function addPlan() {
  proxy.$refs["formRef"].validate(valid => {
    if (!valid) {
      return;
    }
    form.value.type = 1
    form.value.isTemplate = 1
    if (isClone.value) {
      form.value.type = 2
      form.value.isTemplate = 0
      form.value.status = 1
      cloneTemplate(form.value).then(response => {
        proxy.$modal.msgSuccess("新增成功");
        plan.value = false;
        isClone.value = false
        router.push('/planting/farming')
      });
      return
    }
    // form.value.status = 0
    // 修改的提交
    if (form.value.id != null) {
      form.value.startTime = +(new Date(form.value.startTime))
      updatePlan(form.value).then(response => {
        plan.value = false;
        proxy.$modal.msgSuccess("修改成功");
        form.value.startTime = parseTime(form.value.startTime)
        router.push({
          name: 'Calendar',
          query: {
            types: 'query',
            row: JSON.stringify(form.value),
          }
        })
      });
      return;
    }
    // 添加的提交
    form.value.status = 1
    createPlan(form.value).then(response => {
      plan.value = false;
      proxy.$modal.msgSuccess("新增成功");
      router.push({
        name: 'Calendar',
        query: {
          types: 'add',
          row: null,
          id: response.data
        }
      })
    });
  });
}
/** 删除按钮操作 */
function handleDelete(row) {
  const id = row.id;
  proxy.$modal.confirm('是否确认删除?').then(function () {
    return deletePlan(id);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

function init() {
  getCrops();
  getList();
}
init()

</script>

<style lang="scss" scoped>
.app-container {
  background-color: #F0F2F2;
  padding: 20px 30px 30px 30px;
  // overflow-x: scroll;
}
.layout {
  margin-left: 32px !important;
  min-width: 1385px;
  max-width: 1400px;
}
.pagination-container {
  background: transparent;
}
.pcol {
  position: relative;
  margin-right: 73px;
  margin-bottom: 16px;
  width: 404px;
}
.pcol:nth-child(3n) {
  margin-right: 0;
}
.form {
  min-width: 1385px;
  margin-left: 40px;
  background: transparent;
}

:deep(.el-dialog__header) {
  background-color: #F0F2F2;
}

:deep(.el-pagination.is-background .el-pager li:not(.disabled).active) {
    background-color: #EAFBF5;
    color: #20C7A8;
}
.add {
  margin-left: 250px;
  background: #FFFFFF;
  box-shadow: 0px 2px 0px 0px rgba(0,0,0,0.02);
  border-radius: 2px 2px 2px 2px;
  opacity: 1;
  border: 1px solid #20C7A8;
}
.reset {
  background: #C6CED2;
  border-radius: 2px 2px 2px 2px;
  opacity: 1;
}
.seacher {
  background: #20C7A8;
  border-radius: 2px 2px 2px 2px;
  opacity: 1;
}
:deep(.el-dialog--center .el-dialog__footer){
  text-align: right;
}
</style>