<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="20">
        <el-form
          :model="queryParams"
          ref="queryFormRef"
          size="small"
          :inline="true"
          v-show="showSearch"
        >
          <el-form-item label="地块名称" prop="landName">
            <el-input
              v-model="queryParams.landName"
              placeholder="请输入地块名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" :icon="Search" @click="handleQuery"
              >搜索</el-button
            >
            <el-button :icon="Refresh" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
      <right-toolbar
        v-model:showSearch="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table class="table" v-loading="loading" :data="list" stripe border>
      <el-table-column label="序号" align="center" width="60">
        <template #default="scope">
          {{
            (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1
          }}
        </template>
      </el-table-column>
      <el-table-column label="地块名称" align="center" prop="landName" />
      <el-table-column label="种植面积(亩)" align="center" prop="area" />
      <el-table-column label="种植作物" align="center">
        <template #default="scope">
          <el-tooltip
            :disabled="scope.row.cropsNames?.length < maxNumber"
            :content="scope.row.cropsNames"
          >
            <div>{{ scope.row.wholecropsNames }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="作物品种" align="center" prop="cropsTypeName">
        <template #default="scope">
          <el-tooltip
            :disabled="scope.row.cropsTypeNames?.length < maxNumber"
            :content="scope.row.cropsTypeNames"
            ><div>{{ scope.row.wholecropsTypeNames }}</div></el-tooltip
          >
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <div class="btns">
            <el-button
              size="small"
              link
              :icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['land::update']"
              class="button-seat"
              >修改</el-button
            >
            <el-button
              size="small"
              link
              plain
              :icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['land::delete']"
              >删除</el-button
            >
          </div>
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
      width="800px"
      append-to-body
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="地块名称" prop="landName">
          <el-input v-model="form.landName" placeholder="请输入地块名称" />
        </el-form-item>

        <el-form-item label="种植面积(亩)" prop="area">
          <el-input v-model="form.area" placeholder="请输入种植面积" />
        </el-form-item>

        <el-form-item label="作物及品种" prop="cropsCreateReqVOList">
          <div class="crop-createlist">
            <div
              v-for="(item, index) in form.cropsCreateReqVOList"
              :key="index"
              class="crop-createlist-item"
            >
              <el-form
                :model="form.cropsCreateReqVOList[index]"
                :ref="`form${index}`"
                :rules="rulesForDetail"
                label-width="120px"
              >
                <div class="crop-createlist-item-info">
                  <!-- 种植作物 -->
                  <el-form-item
                    class="crop-createlist-item-info-formitem"
                    prop="crops"
                  >
                    <div class="crop-select">
                      <el-select
                        v-model="form.cropsCreateReqVOList[index].crops"
                        @change="
                          (val) => {
                            form.cropsCreateReqVOList[index].crops = val;
                            form.cropsCreateReqVOList[index].cropsType = '';
                          }
                        "
                        filterable
                        placeholder="请选择"
                      >
                        <el-option
                          v-for="{ val, label } in cropList"
                          :key="val"
                          :label="label"
                          :value="val"
                        >
                        </el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                  <!--  -->
                  <el-form-item
                    v-if="
                      form.cropsCreateReqVOList[index].crops === OTHER_CROP.val
                    "
                    class="crop-createlist-item-info-formitem"
                    prop="cropsOtherContent"
                  >
                    <el-input
                      v-model="
                        form.cropsCreateReqVOList[index].cropsOtherContent
                      "
                      placeholder="请输入种植作物名称"
                    />
                  </el-form-item>
                  <!--  -->
                  <el-form-item
                    v-if="
                      form.cropsCreateReqVOList[index].crops !== OTHER_CROP.val
                    "
                    class="crop-createlist-item-info-formitem"
                    prop="cropsType"
                  >
                    <el-select
                      v-model="form.cropsCreateReqVOList[index].cropsType"
                      filterable
                      placeholder="请选择"
                    >
                      <el-option
                        v-for="item2 in raiseCropsMap?.[
                          form.cropsCreateReqVOList[index].crops
                        ]?.cropsVarietiesList?.map(
                          ({ code: value, cropsVarietiesName: label }) => ({
                            value,
                            label,
                          })
                        )"
                        :key="item2.value"
                        :label="item2.label"
                        :value="item2.value"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <!--  -->
                  <el-form-item
                    v-if="
                      form.cropsCreateReqVOList[index].cropsType ===
                        OTHER_CROP_TYPE.value ||
                      form.cropsCreateReqVOList[index].crops === OTHER_CROP.val
                    "
                    class="crop-createlist-item-info-formitem"
                    prop="cropsTypeOtherContent"
                  >
                    <el-input
                      v-model="
                        form.cropsCreateReqVOList[index].cropsTypeOtherContent
                      "
                      placeholder="请输入作物品种名称"
                    />
                  </el-form-item>
                </div>
              </el-form>
              <div class="crop-createlist-item-btn">
                <el-button
                  :icon="Remove"
                  circle
                  @click="removeOne(index)"
                />
                <el-button
                  :icon="Plus"
                  circle
                  @click="addOne"
                />
              </div>
            </div>
          </div>
        </el-form-item>

        <el-form-item label="联系人姓名" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="绑定计划" prop="planName">
          <el-select v-model="form.planName" placeholder="请选择计划名称">
            <el-option 
              v-for="item in planNameArray"
              :label="item.planName"
              :value="item.plantingPlanId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { Delete, Edit, Search, Remove, Plus, Refresh } from '@element-plus/icons-vue'
import { MAX_ECLIPSE_NUMBER } from "@/store/modules/landMap/help.js";
import { getQueryLandPlan} from '@/api/agro/taskInfo.js'
import {
  createLand,
  updateLand,
  deleteLand,
  getLand,
  getLandPage,
  exportLandExcel,
} from "@/api/land/land";
import {
  validateTel,
  validateContacts,
  validateArea,
  validateLandName,
  formatCropsList,
  OTHER_CROP,
  OTHER_CROP_TYPE,
  formatSearchOptions,
  fomatCreateLand,
  validateCropsOtherContent,
  validateCropsTypeOtherContent,
  validateCropsCreateReqVOList,
  formatLandList,
} from "./help";
import { computed, onMounted, toRefs } from "vue";
import useLandMapStore from '@/store/modules/landMap'

const landMapStore = useLandMapStore() 
const { raiseCrops, raiseCropsMap } = toRefs(landMapStore)

const { proxy } = getCurrentInstance();

const data = reactive({
  maxNumber: MAX_ECLIPSE_NUMBER,
  // 遮罩层
  loading: true,
  // 导出遮罩层
  exportLoading: false,
  // 显示搜索条件
  showSearch: true,
  // 总条数
  total: 0,
  // 地块信息列表
  list: [],
  // 弹出层标题
  title: "",
  // 计划名称数组
  planNameArray: [],
  // 是否显示弹出层
  open: false,
  planNameData: {
    planName: null,
    planId: null
  },
  // 查询参数
  queryParams: {
    pageNo: 1,
    pageSize: 10,
    landName: null,
    landCoordinateCenter: null,
  },
  // 表单参数
  form: {},
  rulesForDetail: {
    crops: [
      {
        required: true,
        message: "请选择作物",
        trigger: "change",
      },
    ],
    cropsOtherContent: [
      {
        required: true,
        validator: validateCropsOtherContent,
        trigger: "change",
      },
    ],
    cropsType: [
      {
        required: true,
        message: "请输入作物品种",
        trigger: "change",
      },
    ],
    cropsTypeOtherContent: [
      {
        required: true,
        validator: validateCropsTypeOtherContent,
        trigger: "change",
      },
    ],

  },
  // 表单校验
  rules: {
    landName: [
      {
        required: true,
        validator: validateLandName,
        trigger: "change",
      },
    ],
    area: [
      {
        required: true,
        validator: validateArea,
        trigger: "change",
      },
    ],
    crops: [
      {
        required: true,
        message: "请选择作物",
        trigger: "change",
      },
    ],
    cropsCreateReqVOList: [
      {
        required: true,
        validator: validateCropsCreateReqVOList,
        trigger: "change",
      },
    ],
    cropsOtherContent: [
      {
        required: true,
        validator: validateCropsOtherContent,
        trigger: "change",
      },
    ],
    cropsType: [
      {
        required: true,
        message: "请输入作物品种",
        trigger: "change",
      },
    ],
    cropsTypeOtherContent: [
      {
        required: true,
        validator: validateCropsTypeOtherContent,

        trigger: "change",
      },
    ],
    contacts: [
      {
        required: true,
        validator: validateContacts,
        trigger: "change",
      },
    ],
    tel: [
      {
        required: true,
        validator: validateTel,
        trigger: "change",
      },
    ],
    color: [
      {
        required: true,
        message: "请选择颜色",
        trigger: "change",
      },
    ],
    planName: [
      {
        required: true,
        message: "请选择种植计划，没有计划，可选择“无”",
        trigger: "change",
      }
    ]
  },
})

const {
  maxNumber,
  // 遮罩层
  loading,
  // 导出遮罩层
  exportLoading,
  // 显示搜索条件
  showSearch,   
  // 总条数
  total,
  // 地块信息列表
  list,
  // 弹出层标题
  title,
  // 计划名称数组
  planNameArray,
  // 是否显示弹出层
  open,
  planNameData,
  // 查询参数
  queryParams,
  // 表单参数
  form,
  rulesForDetail,
  // 表单校验 
  rules,
} = toRefs(data)

const cropList = computed(() => {
  return formatCropsList(raiseCrops.value);
})
const searchOptions = computed(() => {
  return formatSearchOptions(raiseCropsMap.value, form.value.crops);
})

function getList() {
  loading.value = true;
  // 执行查询
  getLandPage(queryParams.value).then((response) => {
    list.value = formatLandList(response.data.list);
    total.value = response.data.total;
    loading.value = false;
  })
  getPlan()
}
function getPlan() {
  getQueryLandPlan().then((response) => {
    planNameArray.value = response.data;
  })
}

function addOne() {
  form.value.cropsCreateReqVOList.push({
    crops: raiseCrops.value[0]?.code,
    cropsIsOther: 0,
    cropsTypeIsOther: 0,
    cropsType: raiseCrops.value[0]?.cropsVarietiesList?.[0]?.code,
    cropsOtherContent: "",
    cropsTypeOtherContent: "",
  })
}

function removeOne(index) {
  if (form.value.cropsCreateReqVOList.length === 1) {
    proxy.$modal.msgError("至少保留一个作物");
    return;
  }
  form.value.cropsCreateReqVOList.splice(index, 1);
}

/** 取消按钮 */
function cancel() {
  planNameData.value.planName = null;
  planNameData.value.planId = null;
  open.value = false;
  reset();
} 

function reset() {
  form.value = {
    id: undefined,
    landName: undefined,
    crops: undefined,
    cropsType: undefined,
    area: undefined,
    contacts: undefined,
    tel: undefined,
    landCoordinate: undefined,
    landForm: undefined,
    status: 0,
    landCoordinateCenter: undefined,
  }
  proxy.resetForm("formRef");
}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNo = 1;
  getList();
}

function resetQuery() {
  proxy.resetForm("queryFormRef");
  handleQuery();
}

function handleUpdate(row) {
  console.log(row.id)
  reset();
  getPlan();
  const id = row.id;
  planNameData.value.planName = null;
  planNameData.value.planId = null;
  getLand(id).then((response) => {
    form.value = response.data;
    if (form.value.planId == null) {
      form.value.planId = -1;
      form.value.planName = '无' 
    } else {
      planNameArray.value.unshift({planName: '无', plantingPlanId: -1})
    }
    planNameData.value.planName = form.value.planName;
    planNameData.value.planId = form.value.planId;
    planNameArray.value.unshift({plantingPlanId: form.value.planId, planName: form.value.planName})
    form.value.planName = form.value.planId
    open.value = true;
    title.value = "修改地块信息";
  });

}

function submitForm() {
  proxy.$refs["formRef"].validate((valid) => {
    if (valid) {
      Promise.all(
          form.value.cropsCreateReqVOList.map(
            (_, index) =>
              new Promise((resolve, reject) => {
                const formIndex = `form${index}`;
                proxy.$refs[formIndex]?.[0]?.validate((valid) => {
                  if (!valid) {
                    reject();
                  } else {
                    resolve();
                  }
                });
              })
          )
      ).then(() => {
        if (form.value.id != null) {
          // 修改的提交
          if (form.value.id != null) {
              if( planNameData.value.planName == form.value.planName ) {
                  form.value.planId = planNameData.value.planId;
                }
              else {
                form.value.planId = form.value.planName
              }
              updateLand(fomatCreateLand(form.value)).then((response) => {
                proxy.$modal.msgSuccess("修改成功");
                open.value = false;
                planNameData.value.planName = null;
                planNameData.value.planId = null;
                getList();
              });
              return;
            }
            // 添加的提交
            createLand(fomatCreateLand(form.value)).then((response) => {
              proxy.$modal.msgSuccess("新增成功");
              open.value = false;
              getList();
            });
        }
      }).catch((error) => {
          console.log(error);
        });
    }
  })
}

function handleDelete(row) {
  const id = row.id;
  proxy.$modal
    .confirm('是否确认删除地块信息编号为"' + id + '"的数据项?')
    .then(function () {
      return deleteLand(id);
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
    .confirm("是否确认导出所有地块信息数据项?")
    .then(() => {
      exportLoading.value = true;
      return exportLandExcel(params);
    })
    .then((response) => {
      proxy.$download.excel(response, "地块信息.xls");
      exportLoading.value = false;
    })
    .catch(() => {});
}

onMounted(() => {
  landMapStore.loadRaiseCrops().then(() => {
    getList();
  })
})

</script>

<style scoped lang="scss">
.mb8 {
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
  td.el-table__cell) {
  background-color: #f0f2f2;
}

.crop-select {
  display: flex;
  flex-wrap: wrap;

  &-item {
    cursor: pointer;
    height: 36px;
    aspect-ratio: 1 / 1;
    margin-right: 10px;
    border: 2px solid #eee;
    transition: all ease 0.2s;
    padding: 4px;
    display: grid;
    place-items: center;
    &-active {
      border-color: #11a983;
      border-width: 4px;
      padding: 4px;
      border-radius: 50%;
    }

    img {
      width: 100%;
      height: 100%;
    }
  }
}

.crop-createlist {
  // background: red;
  display: flex;
  flex-direction: column;
  &-item {
    // margin: 10px 0px;
    width: 75%;
    display: grid;
    grid-template-columns: 400px 40px;

    :deep(.el-form-item__content) {
      margin-left: 0px !important;
    }

    &-info {
      display: flex;
      flex-wrap: nowrap;
    }

    &-btn {
      display: flex;
      flex-wrap: nowrap;
    }
  }
}

.btns {
  margin-left: 10px;
  width: 154px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 5px;
}
</style>