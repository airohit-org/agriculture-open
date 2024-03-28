<template>
  <div class="app-container">
    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="积温带名称" align="center" prop="name" />
      <el-table-column label="温度" align="center" prop="temperature" />
      <el-table-column label="温度表现" align="center" prop="temperatureBehaviour" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <!-- <el-dialog :title="title" v-model="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="积温带名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入积温带名称" />
        </el-form-item>
        <el-form-item label="温度" prop="temperature">
          <el-input v-model="form.temperature" placeholder="请输入温度" />
        </el-form-item>
        <el-form-item label="温度表现" prop="temperatureBehaviour">
          <el-input v-model="form.temperatureBehaviour" placeholder="请输入温度表现" />
        </el-form-item>
        <el-form-item label="区域" prop="region">
          <el-input v-model="form.region" placeholder="请输入区域" />
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
import { getTemperatureZonePage } from "@/api/accumulated/temperatureZone";
import { parseTime } from '@/utils/ruoyi'

import { reactive, toRefs } from "vue";

const data = reactive({
  // 遮罩层
  loading: true,
  // 导出遮罩层
  exportLoading: false,
  // 显示搜索条件
  showSearch: true,
  // 总条数
  total: 0,
  // 积温带管理列表
  list: [],
  // 弹出层标题
  title: "",
  // 是否显示弹出层
  open: false,
  // 查询参数
  queryParams: {
    pageNo: 1,
    pageSize: 10,
    name: null,
  },
  // 表单参数
  form: {},
  // 表单校验
  rules: {
  }
})
const {loading, total, list, queryParams } = toRefs(data)
/** 查询列表 */
function getList() {
  loading.value = true;
  // 执行查询
  getTemperatureZonePage(queryParams.value).then(response => {
    list.value = response.data.list;
    list.value.forEach(item => {
      item.createTime = parseTime(item.createTime, '{y}-{m}-{d}')
    })
    total.value = response.data.total;
    loading.value = false;
  });
}
getList()
</script>
