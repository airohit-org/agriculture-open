<template>
  <div class="lsl">
    <div class="head">
      <el-input
        v-model="input"
        class="input"
        placeholder="请输入内容"
        prefix-icon="Search"
        @clear="handleSearch"
        @keyup.enter.native="handleSearch"
        clearable
      />
      <el-button class="btn" type="primary" @click="handleSearch"
        >搜索</el-button
      >
    </div>
    <div class="list" @scroll.prevent="handleScroll">
      <div class="listItem">
        <div class="itemName">
          <div><span></span></div>
        </div>
        <div class="itemName">地块名称</div>
        <div class="itemName">作物品种</div>
        <div class="itemName">种植面积</div>
      </div>
      <div
        @click="handleClick(id, index)"
        class="item"
        :key="id"
        v-for="(
          {
            id,
            landName,
            areaText,
            cropsNames,
            wholecropsNames,
            cropsTypeNames,
            wholecropsTypeNames,
          },
          index
        ) in list"
      >
        <div class="item-index">
          <div
            :class="`item-index-images item-index-images-${wholecropsNames}`"
          ></div>
        </div>
        <div class="item-all">
          <div class="item-info">
            <div class="item-info-top">{{ landName }}</div>
            <div class="item-info-detail">
              <el-tooltip
                :disabled="cropsNames?.length < maxNumber"
                :content="cropsNames"
                ><span>{{ wholecropsNames }}</span></el-tooltip
              >,
              <el-tooltip
                :disabled="cropsTypeNames?.length < maxNumber"
                :content="cropsTypeNames"
                ><span>{{ wholecropsTypeNames }}</span></el-tooltip
              >
            </div>
            <div class="item-info-detail">{{ areaText }}</div>
          </div>
        </div>
      </div>
      <div class="list-loading" v-if="loading">loading...</div>
      <img
        class="landLowerLeft"
        src="https://oss.airoteach.cn/3f09a4e9751fd008cd5b60e1f5527326bb36f65d9a52dd644745019525f8a685.png"
      />
      <img
        class="landBorderBottom"
        src="https://oss.airoteach.cn/6c51fac261d2a5f8a3a032993c7923da9b97ec0134072198f3fe2ec7ff7da885.png"
      />
      <img
        class="landLowerRight"
        src="https://oss.airoteach.cn/8883182d4d64b0accc8d472b1d3221f2a9fa77ad4ee12056500d32e51f50b41b.png"
      />
      <img
        class="landUpperRight"
        src="https://oss.airoteach.cn/e5f9365c6472a3584d2b8e5d0ba374b58f4d861d2942aa06e5f245f7b79fc883.png"
      />
    </div>
  </div>
</template>

<script setup>
import { Search } from "@element-plus/icons-vue";
import { MAX_ECLIPSE_NUMBER } from "@/store/modules/landMap/help.js";
import { debounce } from "throttle-debounce";
import { reactive, toRefs } from "vue";

const props = defineProps({
  loading: {
    type: Boolean,
    default: false,
  },
  total: {
    type: Number,
    default: 0, 
  },
  list: {
    type: Array,
    default: () => [],
  }
})
const emit = defineEmits(["search", "clickItem", "loadMore"])
const data = reactive({
  maxNumber: MAX_ECLIPSE_NUMBER,
  input: "",
})
const { maxNumber, input } = toRefs(data)
const isEnd = computed(() => props.total === props.list.length)

const handleSearch = () => {
  emit("search", input.value)
}

const handleClick = (id, index) => {
  emit("clickItem", id)
}

const handleScroll = debounce(200, function (event) {
  const { scrollHeight, scrollTop, clientHeight } = event.target
  let scrollBottom = scrollHeight - scrollTop - clientHeight
  if (!props.loading && !isEnd.value && scrollBottom < 100) {
    emit("loadMore", input.value)
  }
})
</script>

<style lang="scss" scoped>
$rice: "https://oss.airoteach.cn/ef73d94d073863992841aa9a4452cdfdff339fd0b232478557cd9baa79665769.png";
$corn: "https://oss.airoteach.cn/0d9b39993cd89ac739b4819839f49d2249e8aa0069071c556e4e642d34af0275.png";
$soybean: "https://oss.airoteach.cn/c2cb51498165e052d36ad7e0462982aa6c9dab2578c258dbfe6223e819fe6913.png";
$cornHover: "https://oss.airoteach.cn/2874e56d838b4a4f839d0fa8d8159ced18144d98f271b9eeaa29c69c84e680da.png";
$riceHover: "https://oss.airoteach.cn/c7937a126144ce5474729b4617204dfca287cc53344005c229c4880ba7f9c7de.png";
$soybeanHover: "https://oss.airoteach.cn/a725d8a87826783921484c61a6821be128b868eba85ffb7ea9fe68cf967784a4.png";
$other: "https://oss.airoteach.cn/581e824d549c0af84f6d1fd55b5b9904207984c415a4cbee277dfadfddcab8a2.png";
$otherHover: "https://oss.airoteach.cn/8ae98a2a20bc18d5f482e1c9882c25123bb83834524720deed21ccb62d3745d3.png";
.lsl {
  width: 329px;
  overflow: hidden;
  /* 滚动槽 */
  ::-webkit-scrollbar {
    width: 5px;
    background: rgba(255, 255, 255, 0.08);
  }
  /* 滚动条滑块 */
  ::-webkit-scrollbar-thumb {
    border-radius: 0;
    background: linear-gradient(
      270deg,
      #1ee7e7 0%,
      rgba(30, 231, 231, 0.35) 100%
    );
  }

  :deep(.el-input__icon) {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #1fface;
  }

  .head {
    width: 329px;
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-image: url("https://oss.airoteach.cn/6be97fd0db5b661b1257e5be599e8f07167291ab0b205fdbb907810e49cf7c2f.png");
    background-position: center;
    background-size: cover;

    .input {
      width: 224px;
      height: 30px;
      margin-top: 10px;
      :deep(.el-input__inner) {
        width: 100%;
        height: 100%;
        box-shadow: inset 0px 4px 21px rgba(0, 224, 219, 0.25);
        filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
        color: rgba(255, 255, 255, 0.7);
        font-size: 14px;
        background: rgba(0, 0, 0, 0.62);
        border: 1px solid #1fface;
      }
    }

    .btn {
      width: 68.27px;
      height: 28.85px;
      margin-left: 14px;
      margin-top: 10px;
      display: grid !important;
      place-items: center !important;
      padding: 0 !important;
      border: 1px solid #1fface;
      background: linear-gradient(#1fface, #096557);
    }
  }

  .list {
    // min-width: 262px;
    width: 328px;
    height: calc(100vh - 265px);
    overflow: auto;
    line-height: 1;
    margin-top: 8px;
    background: linear-gradient(
      180deg,
      rgba(0, 0, 0, 0.24) 0%,
      rgba(0, 0, 0, 0.24) 21.2%,
      rgba(0, 9, 9, 0.24) 32.9%,
      rgba(0, 35, 35, 0.24) 44.2%,
      rgba(0, 77, 77, 0.24) 58.6%,
      rgba(12, 113, 113, 0.24) 75.6%,
      rgba(13, 63, 63, 0.24) 94.6%,
      rgba(7, 69, 69, 0.24) 100%
    );
    border: 2px solid rgba(31, 250, 206, 0.3);
    .listItem {
      width: 273px;
      height: 31px;
      font-family: "PingFang SC";
      font-size: 12px;
      line-height: 25px;
      color: #68ffe1;
      display: grid;
      grid-template-columns: 42px 90px 90px 90px;
      place-items: center;
      .itemName {
        width: 48px;
        height: 30px;
        border-bottom: 1px solid #7bfffc;
      }
      .itemName:first-child {
        border-bottom: 0;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding-left: 5px;
        padding-bottom: 5px;

        div {
          width: 10px;
          height: 10px;
          background: rgba(104, 255, 225, 0.2);
          border: 1px solid rgba(104, 255, 225, 0.5);
          display: flex;
          align-items: center;
          justify-content: center;

          span {
            height: 6px;
            width: 6px;
            background-color: #00ffff;
          }
        }
      }
    }
    .item {
      color: #fff;
      width: 309.07px;
      height: 34px;
      margin-bottom: 14px;
      display: grid;
      grid-template-columns: 42px 1fr;
      font-family: "PingFang SC";
      font-weight: 400;
      font-size: 14px;

      &-index {
        width: 50px;
        background-image: url("https://oss.airoteach.cn/17d06d860595c835f09544913e004cf148eb7a9641262236c305810d186c0392.png");
        background-size: cover;
        display: grid;
        place-items: center;
        &-images {
          width: 24px;
          height: 24px;
          background-repeat: no-repeat;
          background-size: cover;
          background-position: center;
          background-image: url($other);

          &-玉米 {
            background-image: url($corn);
          }
          &-水稻 {
            background-image: url($rice);
          }
          &-大豆 {
            background-image: url($soybean);
          }
        }
      }
      &-all {
        background-image: url("https://oss.airoteach.cn/22a568f4b944ab25add63ccbaade5cb21a213a0d76f0b2b12dfe887db6a19016.png");
        background-size: cover;
      }

      &-info {
        display: grid;
        grid-template-columns: repeat(3, 90px);
        height: 34px;
        place-items: center;
        text-align: center;
        &-top {
          width: 64px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          -o-text-overflow: ellipsis;
        }
        &-detail {
          width: 64px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          -o-text-overflow: ellipsis;
          text-align: center;
        }
      }
    }

    .item:hover {
      color: #ffd15c;
      .item-info {
        clip-path: polygon(1.5% 0, 100% 0, 100% 100%, 1.5% 100%, 0 90%, 0 1.5%);
        background: linear-gradient(rgba(0, 9, 9, 0.24), #6fd2d0);
      }
      .item-index {
        background-image: url("https://oss.airoteach.cn/008c63b3e2185f901579bc43b5cbee0083b100beabecf3d0aacb554bf445e9bd.png");
        background-size: cover;

        &-images {
          background-image: url($otherHover);
          &-玉米 {
            background-image: url($cornHover);
          }
          &-水稻 {
            background-image: url($riceHover);
          }
          &-大豆 {
            background-image: url($soybeanHover);
          }
        }
      }
    }
    &-loading {
      height: 50px;
      display: grid;
      place-items: center;
      color: #fff;
    }
    .landBorderBottom {
      position: absolute;
      top: calc(100vh - 223px);
      left: -11.5px;
      width: 351.5px;
    }
    .landLowerLeft {
      position: absolute;
      width: 2px;
      left: -1px;
      top: calc(100vh - 335px);
    }
    .landLowerRight {
      position: absolute;
      width: 2px;
      right: -1px;
      top: calc(100vh - 275px);
    }
    .landUpperRight {
      position: absolute;
      width: 2px;
      right: -1px;
      top: calc(100vh - 500px);
    }
  }
}
</style>
