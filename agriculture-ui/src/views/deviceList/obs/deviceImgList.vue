<template>
  <div class="contrain">
    <div class="list">
      <div class="item" v-for="item in list">
        <div class="titleTime">
          <span class="icon"></span> <span>获取时间：{{ item[0].date }}</span>
        </div>
        <div class="listImg">
          <div class="imgItem" v-for="(ele, index) in item">
            <div class="img">
              <img :src="ele.value" alt="" />
            </div>
            <div class="bottom">
              <div class="left">
                <img
                  :src="grayinfo"
                  class="icon"
                  alt=""
                />
                <span class="txt">{{ ele.name }}</span>
              </div>
              <div class="right">
                {{ ele.time }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-bottom: 40px">
      <pagination
        :layout="'total, prev, pager, next'"
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script setup>
import { getInfoImage } from "@/api/obs/deviceInfo";
import grayinfo from '@/assets/images/device/grayinfo.png'
const list = ref([]);
const total = ref(0);
const queryParams = ref({
  pageNo: 1,
  pageSize: 2,
  deviceId: void 0,
});
const route = useRoute();
async function getList() {
  let res = await getInfoImage(queryParams.value);
  total.value = res.data.total;
  filterArr(res.data.list);
}
function filterArr(arr = []) {
  if (arr.length == 0) return (list.value = []);
  let result = [];

  arr.forEach((device) => {
    let tempArray = [];
    device.data.forEach((item) => {
      let newObj = {
        date: parseTime(device.createTime, "{y}-{m}-{d}"),
        value: item.value,
        name: item.name,
        time: parseTime(device.createTime).split(" ")[1],
      };
      tempArray.push(newObj);
    });
    result.push(tempArray);
  });
  list.value = result;
}
onMounted(() => {
  queryParams.value.deviceId = route.params.row?.id
    ? route.params.row.id
    : localStorage.getItem("deviceId");
  getList();
});
// beforeRouteLeave(to, from, next) {
//   if (to.name !== 'deviceImgList') {
//     if (this.$vnode && this.$vnode.data.keepAlive) {
//       if (this.$vnode.parent && this.$vnode.parent.componentInstance && this.$vnode.parent.componentInstance.cache) {
//         if (this.$vnode.componentOptions) {
//           var key = this.$vnode.key == null
//             ? this.$vnode.componentOptions.Ctor.cid + (this.$vnode.componentOptions.tag ? `::${this.$vnode.componentOptions.tag}` : '')
//             : this.$vnode.key
//           var cache = this.$vnode.parent.componentInstance.cache
//           var keys = this.$vnode.parent.componentInstance.keys
//           if (cache[key]) {
//             if (keys.length) {
//               var index = keys.indexOf(key)
//               if (index > -1) {
//                 keys.splice(index, 1)
//               }
//             }
//             delete cache[key]
//           }
//         }
//       }
//     }
//     this.$destroy()
//   }
//   next()
// },
</script>

<style scoped lang="scss">
.contrain {
  .list {
    .item {
      .titleTime {
        margin-left: 70px;
        margin-top: 30px;
        height: 25px;
        font-size: 18px;
        font-family: PingFang SC-Medium, PingFang SC;
        font-weight: 500;
        color: #333333;
        line-height: 25px;

        .icon {
          display: inline-block;
          vertical-align: middle;
          width: 6px;
          height: 28px;
          background: #20c7a8;
          border-radius: 0px 0px 0px 0px;
        }
      }

      .listImg {
        display: flex;
        margin-top: 30px;

        .imgItem {
          margin-left: 70px;
          width: 448px;
          height: 317px;
          background: #ffffff;
          opacity: 1;
          border-radius: 8px 8px 8px 8px;

          .img {
            margin: 0 auto;
            margin-top: 9px;
            width: 430px;
            height: 242px;

            img {
              width: 100%;
              height: 100%;
              border-radius: 8px 8px 8px 8px;
            }
          }

          .bottom {
            height: 66px;
            position: relative;

            .icon {
              position: absolute;
              z-index: 1;
              top: 50%;
              transform: translateY(-50%);
              left: 19px;
              width: 20px;
              height: 20px;
            }

            .txt {
              position: absolute;
              z-index: 1;
              top: 50%;
              transform: translateY(-50%);
              left: 57px;
              font-size: 16px;
              font-family: PingFang SC-Regular, PingFang SC;
              font-weight: 400;
              color: #333333;
            }

            .right {
              position: absolute;
              z-index: 1;
              top: 50%;
              transform: translateY(-50%);
              right: 36px;
              font-size: 16px;
              font-family: PingFang SC-Regular, PingFang SC;
              font-weight: 400;
              color: #333333;
            }
          }
        }
      }
    }
  }
}
</style>
