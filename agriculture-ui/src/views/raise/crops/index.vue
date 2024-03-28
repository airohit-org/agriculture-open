<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作物名称" prop="cropsName">
        <el-input v-model="queryParams.cropsName" placeholder="请输入作物名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->

    <!-- 操作工具栏 -->
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="layout-font" type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['raise:crops:create']">新增</el-button>
      </el-col> -->
    <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['raise:crops:export']">导出</el-button>
      </el-col> -->
    <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    <!-- </el-row> -->

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="序号" align="center" width="60">
        <template #default="scope">
          {{ (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="作物名称" align="center" prop="cropsName" />
      <el-table-column label="编码" align="center" prop="code" />
      <el-table-column label="作物图标" align="center">
        <template #default="scope">
          <img class="img" :src="scope.row.imageUrl">
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 对话框(添加 / 修改) -->
    <!-- <el-dialog :title="title" :visible="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作物名称" prop="cropsName">
          <el-input v-model="form.cropsName" placeholder="请输入作物名称" />
        </el-form-item>
        <el-form-item label="编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入编码" />
        </el-form-item>
        <el-form-item label="作物图标" prop="imageUrl">
          <el-radio-group v-model="form.imageUrl">
            <el-radio v-for="item in img" :label="item.src">
              {{ item.name }}
              <div>
                <img class="imgicon" :src="item.src" alt="">
              </div>
            </el-radio>

          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script setup> 
import { getCropsPage } from "@/api/crops";
import { reactive } from "vue";

const data = reactive({
  // 遮罩层
  loading: true,
  // 导出遮罩层
  exportLoading: false,
  // 显示搜索条件
  showSearch: true,
  // 总条数
  total: 0,
  // 种植作物列表
  list: [],
  // 弹出层标题
  title: "",
  // 是否显示弹出层
  open: false,
  // 查询参数
  queryParams: {
    pageNo: 1,
    pageSize: 10,
    cropsName: null,
  },
  // 表单参数
  form: {},
  img: [
    {
      name: '大豆',
      src: 'https://oss.airoteach.cn/79ce0ab5641d4942a7689d4d11e5af50ea3e3e5f9c794094abf1b6e8583e1926.png'
    },
    {
      name: '水稻',
      src: 'https://oss.airoteach.cn/6accd51655abe492e54a8bad11ed1e73e250a8d89bfa2cebbf4d821d880b4290.png'
    },
    {
      name: '玉米',
      src: 'https://oss.airoteach.cn/a71df8e1fd3df8a845a8195dbf6236cde740d5579339460af6cc14708d787fff.png'
    },
    {
      name: '其他',
      src: 'https://oss.airoteach.cn/f5d953a0959abbcef3d97ea847915cdef593c7c51fdec887296a3e3f6134af9a.png'
    }
  ],
  // 表单校验
  rules: {
    cropsName: [
      { required: true, message: "允许输入10个汉字、数字、字母", trigger: "blur" },
      { message: '允许输入10个汉字、数字、字母', max: 10, trigger: 'blur' },
      { message: '允许输入10个汉字、数字、字母', pattern: /^[\u4E00-\u9FA5A-Za-z0-9_]+$/, trigger: 'blur' },
    ],
    code: [
      { required: true, message: "允许输入10个数字、字母", trigger: "blur" },
      { message: '允许输入10个数字、字母', max: 10, trigger: 'blur' },
      { message: '允许输入10个数字、字母', pattern: /^[A-Za-z0-9]{1,10}$/ }
    ],
    imageUrl: [
      { required: true, message: "必须选择一个图标", trigger: "blur" },
    ]
  }
})

const { 
  loading,
  exportLoading,
  showSearch,
  total,
  list,
  title,
  open,
  queryParams,
  form,
  img,
  rules
} = toRefs(data)

/** 查询列表 */
function getList() {
  loading.value = true;
  // 执行查询
  getCropsPage(queryParams.value).then(response => {
    list.value = response.data.list;
    total.value = response.data.total;
    loading.value = false;
  });
}
getList()
// /** 取消按钮 */
// function cancel() {
//   open.value = false;
//   reset();
// }
// /** 表单重置 */
// function reset() {
//   form.value = {
//     id: undefined,
//     cropsName: undefined,
//     code: undefined,
//     imageUrl: undefined,
//   };
//   resetForm("form");
// }
// /** 搜索按钮操作 */
// function handleQuery() {
//   queryParams.value.pageNo = 1;
//   getList();
// }
// /** 重置按钮操作 */
// function resetQuery() {
//   resetForm("queryForm");
//   handleQuery();
// }

// /** 新增按钮操作 */
// function handleAdd() {
//   this.reset();
//   this.open = true;
//   this.title = "添加种植作物";
// }
// /** 发布按钮操作 */
// function handleview(row) {
//   this.reset();
//   const id = row.id;
//   releaseVarieties({ id }).then(response => {
//     this.$modal.msgSuccess("发布成功");
//   })
// }
// /** 修改按钮操作 */
// function handleUpdate(row) {
//   this.reset();
//   const id = row.id;
//   getCrops(id).then(response => {
//     this.form = response.data;
//     this.open = true;
//     this.title = "修改种植作物";
//   });
// }
// /** 提交按钮 */
// function submitForm() {
//   this.$refs["form"].validate(valid => {
//     if (!valid) {
//       return;
//     }
//     // 修改的提交
//     if (this.form.id != null) {
//       updateCrops(this.form).then(response => {
//         this.$modal.msgSuccess("修改成功");
//         this.open = false;
//         this.getList();
//       });
//       return;
//     }
//     // 添加的提交
//     createCrops(this.form).then(response => {
//       this.$modal.msgSuccess("新增成功");
//       this.open = false;
//       this.getList();
//     });
//   });
// }
// /** 删除按钮操作 */
// function handleDelete(row) {
//   const id = row.id;
//   this.$modal.confirm('是否确认删除该种植作物的数据项?').then(function () {
//     return deleteCrops(id);
//   }).then(() => {
//     this.getList();
//     this.$modal.msgSuccess("删除成功");
//   }).catch(() => { });
// }
// /** 导出按钮操作 */
// function handleExport() {
//   // 处理查询参数
//   let params = { ...this.queryParams };
//   params.pageNo = undefined;
//   params.pageSize = undefined;
//   this.$modal.confirm('是否确认导出所有种植作物数据项?').then(() => {
//     this.exportLoading = true;
//     return exportCropsExcel(params);
//   }).then(response => {
//     this.$download.excel(response, '种植作物.xls');
//     this.exportLoading = false;
//   }).catch(() => { });
// }
</script>
<style lang="scss" scoped>
.imgicon {
  width: 40px;
  height: 50px;
}

.img {
  width: 40px;
  height: 40px;
}
</style>