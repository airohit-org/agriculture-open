<template>
  <div class="app-container">
    <el-form
      class="form"
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="岗位编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入岗位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入岗位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="岗位状态"
          clearable
        >
          <el-option
            v-for="dict in common_status"
            :key="parseInt(dict.value)"
            :label="dict.label"
            :value="parseInt(dict.value)"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          class="layout-font"
          type="primary"
          plain
          icon="Plus"
          size="default"
          @click="handleAdd"
          v-hasPermi="['system:post:create']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          class="layout-font"
          type="warning"
          plain
          icon="Download"
          size="default"
          @click="handleExport"
          v-hasPermi="['system:post:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table class="table" v-loading="loading" :data="postList" border stripe>
      <el-table-column
        label="岗位编号"
        align="center"
        width="80"
        type="index"
        :index="indexMethod(queryParams.pageNo)"
      />
      <el-table-column label="岗位编码" align="center" prop="code" />
      <el-table-column label="岗位名称" align="center" prop="name" />
      <el-table-column label="岗位排序" align="center" prop="sort" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="common_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建日期"
        align="center"
        prop="createTime"
        width="180"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            class="button-seat"
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:post:update']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            plain
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:post:delete']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNo"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="岗位名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="岗位编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入编码名称" />
        </el-form-item>
        <el-form-item label="岗位顺序" prop="sort">
          <el-input-number
            v-model="form.sort"
            controls-position="right"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="岗位状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in common_status"
              :key="parseInt(dict.value)"
              :label="parseInt(dict.value)"
            >
              {{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
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
  listPost,
  getPost,
  delPost,
  addPost,
  updatePost,
  exportPost,
} from "@/api/system/post";

const { proxy } = getCurrentInstance();
const { common_status } = proxy.useDict("common_status");
// 遮罩层
const loading = ref(true);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 岗位表格数据
const postList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  code: undefined,
  name: undefined,
  status: undefined,
});
// 表单参数
const form = ref({});
const formRef = ref();
// 表单校验
const rules = {
  name: [{ required: true, message: "岗位名称不能为空", trigger: "blur" }],
  code: [{ required: true, message: "岗位编码不能为空", trigger: "blur" }],
  sort: [{ required: true, message: "岗位顺序不能为空", trigger: "blur" }],
};

/** 查询岗位列表 */
const getList = () => {
  loading.value = true;
  listPost(queryParams.value).then((response) => {
    postList.value = response.data.list;
    total.value = response.data.total;
    loading.value = false;
  });
};
// 取消按钮
const cancel = () => {
  open.value = false;
  reset();
};
/** 序号 */
const indexMethod = (pageNo) => {
  return pageNo == 1 ? 1 : (pageNo - 1) * 10 + 1;
};
// 表单重置
const reset = () => {
  form.value = {
    id: undefined,
    code: undefined,
    name: undefined,
    sort: 0,
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
  proxy.resetForm("queryForm");
  handleQuery();
};
/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加岗位";
};
/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset();
  const id = row.id;
  getPost(id).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改岗位";
  });
};
/** 提交按钮 */
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      if (form.value.id !== undefined) {
        updatePost(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPost(form.value).then((response) => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};
/** 删除按钮操作 */
const handleDelete = (row) => {
  const ids = row.id;
  proxy.$modal
    .confirm('是否确认删除岗位编号为"' + ids + '"的数据项?')
    .then(function () {
      return delPost(ids);
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
    .confirm("是否确认导出所有岗位数据项?")
    .then(() => {
      return proxy.$download.excel(exportPost(queryParams.value), "岗位数据.xls");
    })
    .catch(() => {});
};

getList();
</script>
