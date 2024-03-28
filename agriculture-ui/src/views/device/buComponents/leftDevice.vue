<template>
  <div class="contrain">
    <div class="ExhibitItem" v-infinite-scroll="load">
      <!-- 2点击 0灰 1普通  -->
      <div v-for="item in list" :key="item.id" style="cursor: pointer">
        <div class="item" v-if="item.state == 0" @click="changeLight(item)">
          <div class="logo">
            <img class="img" :src="item.imgUrl" alt="" />
          </div>
          <div class="bottom" v-if="tenantId != 166">
            {{ item.deviceTypeName }}
          </div>
          <div class="bottom" v-else>
            {{ item.deviceName }}
          </div>
        </div>
        <div class="item2" v-if="item.state == 1" @click="changeLight(item)">
          <div class="logo">
            <img class="img" :src="item.imgUrl" alt="" />
          </div>
          <div class="bottom" v-if="tenantId != 166">
            {{ item.deviceTypeName }}
          </div>
          <div class="bottom" v-else>
            {{ item.deviceName }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getTenantId } from "@/utils/auth";
const props = defineProps({
  list: {
    type: Array,
    required: true,
  },
});
const emit = defineEmits(["deviceItemInfo"]);
const { proxy } = getCurrentInstance();
const tenantId = computed(() => getTenantId());
// const getTenantId = getTenantId();
/**
 * 状态切换
 * @param {*} item
 */
function changeLight(item) {
  emit("deviceItemInfo", item);
  // const index = this.list.findIndex(ele => ele.id == item.id)
  // this.$nextTick(() => {
  props.list.forEach((ele) => {
    if (item.id == ele.id) {
      if (ele.state == 0) {
        ele.state = 1;
      } else {
        ele.state = 0;
      }
    } else {
      ele.state = 1;
    }
  });
  proxy.$forceUpdate();
  // })
}
function load() {}
</script>

<style lang="scss" scoped>
.contrain {
  box-shadow: 0px 0px 18px rgba(31, 250, 206, 0.45);
  border: 1px solid #1fface;
  overflow-y: auto;
  height: 100%;
}

.ExhibitItem {
  display: flex;
  flex-wrap: wrap;

  .item {
    width: 120px;
    height: 145px;
    margin-top: 30px;
    margin-left: 30px;
    background-image: url("https://oss.airoteach.cn/831e150417b4854eda43936c235d530f83a2c006094051340a8e5507e648c901.png");
    background-repeat: no-repeat;
    background-size: 100% 97%;
  }

  .item:nth-child(2n) {
    margin-left: 20px;
  }

  .item2 {
    width: 120px;
    height: 145px;
    margin-top: 30px;
    margin-left: 30px;
    background-image: url("https://oss.airoteach.cn/03447a1f2fa80465e1cf276addc382d48af8df35018a9f2a634492f99a75281b.png");
    background-repeat: no-repeat;
    background-size: 100% 97%;
  }

  .item2:nth-child(2n) {
    margin-left: 20px;
  }

  .logo {
    width: 120px;
    height: 118px;
    text-align: center;
    border-left: 1px solid;
    border-right: 1px solid;
    // border-image: linear-gradient(360deg, rgba(31, 250, 206, 1), rgba(31, 250, 206, 0)) 1 1;

    .img {
      display: inline-block;
      width: 102px;
      height: 115px;
      margin: 0 auto;
    }

    // .img:hover {
    //   animation: rotate360 5s linear infinite;
    // }

    @keyframes rotate360 {
      0% {
        transform: rotateY(0deg);
      }

      25% {
        transform: rotateY(90deg);
      }

      100% {
        transform: rotateY(360deg);
      }
    }
  }

  .bottom {
    height: 27px;
    color: #fff;
    border-radius: 0px 0px 0px 0px;
    text-align: center;
  }
}

::-webkit-scrollbar {
  width: 4px;
  height: 4px;
}

::-webkit-scrollbar-track {
  border-radius: 0px;
  background: rgba(255, 255, 255, 0.08);
}

/* 滚动条滑块 */
::-webkit-scrollbar-thumb {
  border-radius: 0px;
  background: linear-gradient(
    180deg,
    #1ee7e7 0%,
    rgba(30, 231, 231, 0.35) 100%
  );
}
</style>
