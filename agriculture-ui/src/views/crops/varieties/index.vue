<template>
  <div class="app-container">
    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="序号" align="center" width="60">
        <template #default="scope">
          {{
            (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1
          }}
        </template>
      </el-table-column>
      <el-table-column
        label="作物品种"
        align="center"
        prop="cropsVarietiesName"
      />
      <el-table-column label="作物名称" align="center" prop="cropsName" />
      <el-table-column label="编码" align="center" prop="code" />
      <el-table-column
        label="国家审定编号"
        align="center"
        prop="countryAuthCode"
      />
      <el-table-column label="适应区域" align="center" width="300px">
        <template #default="scope">
          <div
            v-html="scope.row.adaptationZone"
            :style="'white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'"
          ></div>
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

    <!-- <el-dialog :title="'查看品种管理信息'" v-model="openView" width="1200px" v-dialogDrag append-to-body>
          <div class="text"><div class="name">作物品种:</div> <div class="content">{{ this.form.cropsVarietiesName }}</div></div>
          <div class="text"><div class="name">作物名称:</div> <div class="content">{{ this.form.cropsName }}</div></div>
          <div class="text"><div class="name">编码:</div> <div class="content">{{ this.form.code }}</div></div>
          <div class="text"><div class="name">国家审定编号: </div><div class="content">{{ this.form.countryAuthCode }}</div></div>

          <div class="text"><div class="name">选育机构:</div> <div class="content">{{ this.form.breedingAgency }}</div></div>
          <div class="text"><div class="name">品种来源:</div> <div class="content" v-html="this.form.varietiesSource"></div></div>
          <div class="text"><div class="name">特征特性:</div> <div class="content" v-html="this.form.feature"></div></div>
          <div class="text"><div class="name">产量表现:</div> <div class="content" v-html="this.form.production"></div></div>
          <div class="text"><div class="name">栽培技术:</div> <div class="content" v-html="this.form.cultivationTechnique"></div></div>
          <div class="text"><div class="name">适应区域: </div><div class="content" v-html="this.form.adaptationZone"></div></div>

          <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitView">确 定</el-button>
        <el-button @click="submitView">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script setup>
import { getVarietiesPage } from "@/api/crops";

import { reactive, toRefs } from "vue";

const data = reactive({
  // 遮罩层
  loading: true,
  // 总条数
  total: 0,
  // 品种管理列表
  list: [],
  // 查询参数
  queryParams: {
    pageNo: 1,
    pageSize: 10,
    cropsVarietiesName: null,
  },
});

const { loading, list, total, queryParams } = toRefs(data);

function getList() {
  loading.value = true;
  // 执行查询
  getVarietiesPage(queryParams.value).then((response) => {
    list.value = response.data.list;
    list.value.forEach((item) => {
      item.adaptationZone =
        '<p style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">' +
        item.adaptationZone.slice(3, item.adaptationZone.length - 4) +
        "</p>";
    });
    total.value = response.data.total;
    loading.value = false;
  });
}
getList();
</script>

<style lang="scss" scoped>
.text {
  display: flex;
  color: #333;
  margin-bottom: 10px;
  .name {
    width: 100px;
    text-align: right;
    margin-right: 16px;
  }
  .content {
    width: 1000px;
    text-align: justify;
  }
}
</style>
