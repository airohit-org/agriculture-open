<template>
  <div class="app-container">
    <!-- 列表 -->
    <el-table class="table" v-loading="loading" :data="list">
      <el-table-column label="序号" align="center">
        <template #default="scope">
          {{
            (queryParams.pageNo - 1) * queryParams.pageSize + scope.$index + 1
          }}
        </template>
      </el-table-column>
      <!-- <el-table-column v-for="(item,index) in list" :key="index" :prop="item[index].value" :label="item[index].name"></el-table-column> -->
      <el-table-column
        align="center"
        v-for="(item, index) in list[0]"
        :key="index"
        :label="item.name"
      >
        <template #default="scope">
          {{ scope.row[index].value }}
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
  </div>
</template>

<script setup>
import { getRealTimeData } from "@/api/obs/deviceInfo";

const { proxy } = getCurrentInstance();
const route = useRoute();

// 遮罩层
const loading = ref(true);
// 总条数
const total = ref(0);
// 山东仁科设备全部信息列表
const list = ref([]);
const show = ref(false);
const open = ref(false);
// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  deviceId: "",
});
// 表单参数
const form = ref({});
// 表单校验
const rules = {};

/** 查询列表 */
function getList() {
  loading.value = true;
  // 执行查询
  getRealTimeData(queryParams.value).then((response) => {
    let arr = [];
    response.data.list.forEach((ele) => {
      // ele.data.push({name:'时间',value:parseTime(ele.createTime)})
      ele.data.splice(0, 0, { name: "时间", value: parseTime(ele.createTime) });
      arr.push(ele.data);
    });
    list.value = sortByName(arr);
    proxy.$forceUpdate();
    total.value = response.data.total;
    loading.value = false;
  });
}
/**
 * 排序
 * @param {*} arr
 */
function sortByName(arr) {
  let result = arr.map((item) => {
    return item.sort((a, b) => {
      if (a.name > b.name) {
        return 1;
      }
      if (a.name < b.name) {
        return -1;
      }
      return 0;
    });
  });
  result = result.map((subArr) => {
    const timeIndex = subArr.findIndex((item) => item.name === "时间");
    if (timeIndex !== -1) {
      subArr.unshift({ name: "时间", value: subArr[timeIndex].value });
      subArr.splice(timeIndex + 1, 1);
    }
    return subArr;
  });
  return result;
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  show.value = false;
  reset();
}
/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    deviceId: undefined,
    data: undefined,
  };
  proxy.resetForm("form");
}
/**
 * 去路由缓存
 * @param {*} to
 * @param {*} from
 * @param {*} next
 */
// onBeforeRouteLeave((to, from, next) => {
// console.log(to,from)
// next()
// proxy.$tab.closePage(from).then(({ visitedViews }) => {
//   next()
// });
// if (to.name !== "deviceDataInfo") {
//   if (proxy.$vnode && proxy.$vnode.data.keepAlive) {
//     if (
//       proxy.$vnode.parent &&
//       proxy.$vnode.parent.componentInstance &&
//       proxy.$vnode.parent.componentInstance.cache
//     ) {
//       if (proxy.$vnode.componentOptions) {
//         var key =
//           proxy.$vnode.key == null
//             ? proxy.$vnode.componentOptions.Ctor.cid +
//               (proxy.$vnode.componentOptions.tag
//                 ? `::${proxy.$vnode.componentOptions.tag}`
//                 : "")
//             : proxy.$vnode.key;
//         var cache = proxy.$vnode.parent.componentInstance.cache;
//         var keys = proxy.$vnode.parent.componentInstance.keys;
//         if (cache[key]) {
//           if (keys.length) {
//             var index = keys.indexOf(key);
//             if (index > -1) {
//               keys.splice(index, 1);
//             }
//           }
//           delete cache[key];
//         }
//       }
//     }
//   }
//   // proxy.$destroy();
// }
// next();
// });

queryParams.value.deviceId = route.params.row?.id
  ? route.params.row.id
  : localStorage.getItem("deviceId");
getList();
</script>
<style lang="scss" scoped>
.app-container {
  background-color: #f0f2f2;
  padding: 20px 30px 30px 30px;
}

.form {
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

.form-item {
  margin-bottom: 20px !important;
}

.button-seat {
  margin-left: -10px;
}

:deep(.el-table__header tr th) {
  background-color: #f0f2f2;
}

:deep( .el-table--striped
  .el-table__body
  tr.el-table__row--striped
  td.el-table__cell) {
  background-color: #f0f2f2;
}

:deep(.el-form-item) {
  margin-bottom: 12px;
}

:deep(.el-dialog__header) {
  background-color: #f0f2f2;
}

:deep(.el-button--small) {
  padding: 9px 12px;
  font-size: 12px;
  border-radius: 3px;
}
</style>
