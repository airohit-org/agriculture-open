<template>
  <div class="navbar">
    <hamburger
      id="hamburger-container"
      :is-active="appStore.sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />
    <breadcrumb
      id="breadcrumb-container"
      class="breadcrumb-container"
      v-if="!settingsStore.topNav"
    />
    <top-nav
      id="topmenu-container"
      class="topmenu-container"
      v-if="settingsStore.topNav"
    />

    <div class="right-menu">
      <div class="avatar-container">
        <el-dropdown
          @command="handleCommand"
          class="right-menu-item hover-effect"
          trigger="click"
        >
          <div class="avatar-wrapper">
            <img :src="userStore.avatar" class="user-avatar" />
          </div>
          <template #dropdown>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="hasOtherFarm = true">
                <span>切换农场</span>
              </el-dropdown-item>
              <router-link to="/user/profile">
                <el-dropdown-item divided>个人中心</el-dropdown-item>
              </router-link>
              <el-dropdown-item divided @click.native="logout">
                <span>退出登录</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <div v-if="hasOtherFarm" class="farmchange">
      <div class="header"></div>
      <div class="close" @click="hasOtherFarm = false"></div>
      <div class="contentScoll">
        <div
          v-for="item in farmList"
          class="farmitem"
          @click="changeFarms(item)"
        >
          <div class="left">
            <div class="title">
              <img class="img" :src="homeImage" alt="" />{{ item.farmName }}
            </div>
            <div class="label">地址：{{ item.address }}</div>
            <div class="label">总面积：{{ item.plantArea }}</div>
          </div>
          <div class="right">
            <div class="radio">
              <div v-if="farmid == item.id" class="dian"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  setFarmTenantId,
  removeFarmTenantId,
  getFarmTenantId,
} from "@/utils/auth";
import homeImage from "@/assets/images/farmManage/home.png";
import { getFarmListByTenant } from "@/api/farm/farm";
import { getTenantId } from "@/utils/auth";
import { ElMessageBox } from "element-plus";
import Breadcrumb from "@/components/Breadcrumb";
import TopNav from "@/components/TopNav";
import Hamburger from "@/components/Hamburger";
// import Screenfull from '@/components/Screenfull'
// import SizeSelect from '@/components/SizeSelect'
// import HeaderSearch from '@/components/HeaderSearch'
// import RuoYiGit from '@/components/RuoYi/Git'
// import RuoYiDoc from '@/components/RuoYi/Doc'
import useAppStore from "@/store/modules/app";
import useUserStore from "@/store/modules/user";
import useSettingsStore from "@/store/modules/settings";
import { onMounted } from "vue";

const appStore = useAppStore();
const userStore = useUserStore();
const settingsStore = useSettingsStore();
const hasOtherFarm = ref(false);
const farmList = ref([]);
const farmTenantId = ref("");
const farmid = ref("");

function toggleSideBar() {
  appStore.toggleSideBar();
}

function handleCommand(command) {
  switch (command) {
    case "setLayout":
      setLayout();
      break;
    case "logout":
      logout();
      break;
    default:
      break;
  }
}

function logout() {
  ElMessageBox.confirm("确定注销并退出系统吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      userStore.logOut().then(() => {
        location.href = "/";
      });
    })
    .catch(() => {});
}

const emits = defineEmits(["setLayout"]);
function setLayout() {
  emits("setLayout");
}

const changeFarms = (item) => {
  farmList.value.forEach((ele) => {
    if (item.id == ele.id) {
      ele.farmCheck = true;
      farmid.value = ele.id;
      removeFarmTenantId();
      setFarmTenantId(item.id);
    } else {
      ele.farmCheck = false;
    }
  });
  hasOtherFarm.value = false;
  window.location.reload();
};

const getFarmList = async () => {
  const tenantId = getTenantId();
  let res = await getFarmListByTenant({ tenantId });
  let arr = [];
  arr = res.data;
  arr.forEach((element, index) => {
    if (element.id == getFarmTenantId()) {
      element.farmCheck = true;
      farmid.value = element.id;
    } else {
      element.farmCheck = false;
    }
  });
  farmList.value = arr;
};
onMounted(() => {
  getFarmList();
});
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  // background: #fff;
  // box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  background: linear-gradient(
    318.72deg,
    #235c66 -71.31%,
    #213d46 7.57%,
    #1f2932 86.45%
  );
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
  -webkit-box-pack: justify;
  justify-content: space-between;
  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;
    position: absolute;
    left: 0;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    display: flex;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 40px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        i {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }

  .farmchange {
    position: fixed;
    width: 612px;
    height: 399px;
    padding-top: 42px;
    background-image: url("../../assets/images/farmManage/farmbg.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 2023;

    .header {
      width: 276px;
      height: 44px;
      top: -22px;
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      z-index: 2024;
      background-image: url("../../assets/images/farmManage/title.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
    }

    .close {
      cursor: pointer;
      width: 23px;
      height: 23px;
      top: 15px;
      position: absolute;
      right: 23px;
      z-index: 2024;
      background-image: url("../../assets/images/farmManage/close.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
    }

    .contentScoll {
      overflow-y: auto;
      height: 329px;

      .farmitem {
        color: #fff;
        margin: 0 auto;
        margin-top: 8px;
        width: 494px;
        height: 101px;
        background-image: url("../../assets/images/farmManage/itembg.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
        padding: 0 26px;
        display: flex;
        justify-content: space-between;

        .right {
          line-height: 101px;
          position: relative;

          .radio {
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            width: 20px;
            height: 20px;
            background-image: url("@/assets/images/farmManage/Ellipse.png");
            background-repeat: no-repeat;
            background-size: 100% 100%;
            text-align: center;

            .dian {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
              border-radius: 50%;
              width: 12px;
              height: 12px;
              background: #1fface;
            }
          }
        }

        .left {
          padding: 10px 0;
          display: flex;
          flex-direction: column;
          justify-content: space-between;

          .title {
            font-size: 14px;
            font-family: PingFang SC-Medium, PingFang SC;
            font-weight: 500;
            color: #1fface;

            .img {
              width: 14px;
              height: 14px;
              margin-right: 5px;
              margin-top: -2px;
              vertical-align: middle;
            }
          }

          .label {
            font-size: 12px;
            font-family: PingFang SC-Regular, PingFang SC;
            font-weight: 400;
            color: #ffffff;
            position: relative;
          }

          .label::before {
            content: "";
            display: inline-block;
            width: 4px;
            height: 4px;
            background: #1fface;
            border-radius: 50%;
            margin-right: 7px;
            margin-left: 5px;
            vertical-align: middle;
          }
        }
      }
    }
  }
}
</style>
