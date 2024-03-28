<template>
  <div class="warning">
    <OverviewTitle title="地块列表" />
    <el-table
      v-show="list.length"
      height="322"
      class="warning-table"
      v-loading="loading"
      :data="list"
    >
      <el-table-column width="50" label="序号" align="center" type="index" />
      <el-table-column width="160" label="地块名称" align="center" prop="landName" />
      <el-table-column
        width="120"
        label="面积（亩）"
        align="center"
        prop="area"
      />
      <el-table-column label="作物" align="center">
        <template v-slot="scope">
          {{ scope.row?.cropsCreateReqVOList?.[0]?.cropsName }}
        </template>
      </el-table-column>
    </el-table>
    <Empty v-show="!list.length" />
  </div>
</template>

<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import Empty from '@/components/Empty/index.vue'

const props = defineProps({
  list: {
    type: Array,
    default: () => [],
  },
  loading: {
    type: Boolean,
    default: false,
  },
})
</script>

<style lang="scss" scoped>

$scale: 1.2;
.warning {
  height: 100%;

  &-table {
    margin-top: 14px;
    width: 425px;
    height: 322px;
  }
}

:deep(.el-table) {
  background-color: transparent !important;

  th.el-table__cell {
    background-color: transparent !important;
  }
}

:deep(.el-table__body-wrapper) {
  &::-webkit-scrollbar {
    width: 4px;
    height: 4px;
  }
  &::-webkit-scrollbar-track {
    border-radius: 0px;
    background: rgba(255, 255, 255, 0.08);
  }
  /* 滚动条滑块 */
  &::-webkit-scrollbar-thumb {
    border-radius: 0px;
    background: linear-gradient(180deg, #1EE7E7 0%, rgba(30, 231, 231, 0.35) 100%);
  }
}

:deep(.el-table--enable-row-hover
  .el-table__body
  tr:hover
  > td.el-table__cell) {
  background-color: transparent !important;
}
:deep(.el-table__body) {
  font-size: 12px * $scale;
  color: #ffffff;
}

:deep(.el-table td.el-table__cell) {
  border-bottom-color: #f5f5f588 !important;
}

:deep(.el-table::before) {
  display: none;
}

:deep(.el-table__header-wrapper) {
  background: linear-gradient(180deg, #377e6233 0%, #00d3fd33 100%);
}

:deep(.el-table th.el-table__cell.is-leaf) {
  border-bottom-color: #f5f5f588 !important;

  .cell {
    color: #1fface !important;
    font-size: 12px * $scale;
  }
}
:deep(.el-table .cell) {
  line-height: 1 !important;
}
:deep(tr td:first-child .cell div) {
  width: 28px;
  height: 16px;
  color: #1fface;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(31, 250, 206, 0.1);
  border: 1px solid #1fface;
}
:deep(.el-table tr) {
  background-color: transparent !important;
}
</style>
