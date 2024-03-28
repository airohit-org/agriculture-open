<template>
  <div class="app-container">
    <div class="search">
      <el-form ref="searchFormRef" :inline="true" :model="searchParams">
        <el-form-item label="日期" prop="createTime">
          <el-date-picker
            v-model="searchParams.createTime"
            type="daterange"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-button type="primary" @click="getList">查询</el-button>
      </el-form>
    </div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column
        type="index"
        :index="indexMethod"
        align="center"
        label="序号"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="diseasesName"
        align="center"
        label="诊断结果"
        width="180"
      >
      </el-table-column>
      <el-table-column prop="cropType" align="center" label="作物种类">
        <template v-slot="scope">
          {{ cropMap[scope.row.cropType] }}
        </template>
      </el-table-column>
      <el-table-column prop="landId" align="center" label="所属地块">
      </el-table-column>
      <el-table-column align="center" label="结果预览">
        <template v-slot="scope">
          <el-image
            style="width: 80px; height: 60px"
            :src="scope.row.imageUrl"
            :preview-src-list="[scope.row.imageUrl]"
            :fit="'contain'"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" align="center" label="识别日期">
        <template v-slot="scope">
          {{ getDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <!-- <el-table-column
        align="center"
        label="防治措施">
        <template v-slot="scope">
          <p v-if="scope.row.preventionDO">{{ scope.row.preventionDO.preventionPlan }}</p> 
        </template>
      </el-table-column> -->
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <el-button type="success" @click="handleOpenDialog(scope.row)"
            >查看</el-button
          >
          <el-button
            type="danger"
            v-hasPermi="['diseases:classification:delete']"
            @click="handleDeleteRecord(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="searchParams.pageNo"
      :limit.sync="searchParams.pageSize"
      @pagination="getList"
    />
    <el-dialog title="识别记录详情" v-model="dialogVisible" destroy-on-close>
      <el-descriptions :column="1">
        <el-descriptions-item label="诊断结果">{{
          recordDetail.diseasesName
        }}</el-descriptions-item>
        <el-descriptions-item label="所属地块">{{
          recordDetail.landId
        }}</el-descriptions-item>
        <el-descriptions-item label="作物种类">{{
          cropMap[recordDetail.cropType]
        }}</el-descriptions-item>
        <el-descriptions-item label="识别时间">{{
          getDate(recordDetail.createTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="防治措施">{{
          recordDetail.preventionDO
            ? recordDetail.preventionDO.preventionPlan
            : ""
        }}</el-descriptions-item>
        <el-descriptions-item
          v-if="recordDetail.latitudeLongitude"
          label="地图标记"
        >
          <div ref="mapContainerRef" class="mapContainer"></div>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import {
  getClassificationRecordsPage,
  getClassificationDetail,
  deleteClassificationRecordsPage,
} from "@/api/diseases/classification";
import { parseTime } from "@/utils/ruoyi";
import L from "leaflet";
const { proxy } = getCurrentInstance();
import iconBug from "@/assets/images/diseases/iconBug.png";
import { createMap } from "./help";
import "leaflet/dist/leaflet.css";

// 表格数据
const tableData = ref([]);
// 表格数据总数
const total = ref(0);
const searchParams = ref({
  pageNo: 1,
  pageSize: 10,
  createTime: "",
});
// 作物类型
const cropMap = {
  0: "水稻",
  1: "蓝莓",
  2: "土豆",
  3: "苹果",
  4: "樱桃",
  5: "葡萄",
  6: "桃子",
  7: "大豆",
  8: "辣椒",
  9: "草莓",
  10: "西红柿",
};
const mapContainerRef = ref();
// 对话框开关
const dialogVisible = ref(false);
// 弹窗 识别记录详情
const recordDetail = ref({});
const map = ref(null);

const getList = () => {
  getClassificationRecordsPage(searchParams.value).then((res) => {
    tableData.value = res.data.list;
    total.value = res.data.total;
  });
};
const refreshList = () => {
  searchParams.value.pageNo = 1;
  getList();
};
const indexMethod = (index) => {
  return (
    (searchParams.value.pageNo - 1) * searchParams.value.pageSize + index + 1
  );
};
const getDate = (date) => {
  return parseTime(date, "{y}-{m}-{d}");
};
const handleOpenDialog = async (row) => {
  const res = await getClassificationDetail({ id: row.id });
  recordDetail.value = res.data;
  dialogVisible.value = true;
  if (row.latitudeLongitude) {
    const latitudeLongitude = row.latitudeLongitude.split(",");
    const lng = latitudeLongitude[0];
    const lat = latitudeLongitude[1];
    getMap(lat, lng);
  }
};
const handleDeleteRecord = (row) => {
  proxy.$modal
    .confirm(`您确定要删除这条记录吗？`, "系统提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
    .then(() => {
      deleteClassificationRecordsPage({ id: row.id }).then((res) => {
        if (res) {
          proxy.$message.success("已删除");
          refreshList();
        }
      });
    })
    .catch(() => {});
};
const getMap = (lat, lng) => {
  nextTick(() => {
    map.value = createMap(mapContainerRef, { zoom: 15 }, [lat, lng]);
    const iconUrl = iconBug;
    const icon1 = L.icon({
      iconUrl,
      iconSize: [50, 50],
      iconAnchor: [0, 0],
    });
    L.marker([lat, lng], { icon: icon1 }).addTo(map.value);
  });
};

getList();
</script>

<style lang="scss" scoped>
.search {
  // margin-bottom: 20px;
  display: flex;
  align-items: center;
}
.mapContainer {
  width: 80%;
  height: 450px;
  margin-top: 20px;
}
</style>
