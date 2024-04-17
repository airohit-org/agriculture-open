<template>
  <div class="container">
    <div ref="infomainRef" class="info">
      <div class="div1">
        <img class="but" src="../ui/img/but.png" alt="" />
        <div class="bgland">
          <Border />
          <!-- <div class="light"></div> -->

          <div class="al-item" :style="'left: 486px'">
            <img class="imgwidth" src="../ui/img/1-1.png" alt="" />

            <div class="content">
              <div class="text">选种</div>
            </div>
          </div>
        </div>
      </div>
      <div class="div1">
        <img class="but" src="../ui/img/but2.png" alt="" />
        <div class="bgland">
          <Border />
          <div class="al-item" style="left: 486px">
            <img class="imgwidth2" src="../ui/img/2-1.png" alt="" />
            <div class="content">
              <span class="text">种子清选</span>
            </div>
          </div>
          <div class="al-item" style="left: 789px">
            <img class="imgwidth2" src="../ui/img/2-2.png" alt="" />
            <div class="content">
              <span class="text">整地</span>
            </div>
          </div>
          <div class="al-item" style="left: 1066px">
            <img class="imgwidth2" src="../ui/img/2-3.png" alt="" />
            <div class="content">
              <span class="text">施基肥</span>
            </div>
          </div>
          <div class="al-item" style="left: 1348px">
            <img class="imgwidth2" src="../ui/img/2-4.png" alt="" />
            <div class="content contentend">
              <span class="text">精细播种</span>
            </div>
          </div>
        </div>
      </div>
      <div class="div1">
        <img class="but" src="../ui/img/but3.png" alt="" />
        <div class="bgland">
          <Border />
          <div class="al-item" style="left: 309px">
            <img class="imgwidth3" src="../ui/img/3-1.png" alt="" />
            <div class="content">
              <div class="text">苗前封闭处理</div>
            </div>
          </div>
          <div class="al-item" style="left: 485px">
            <img class="imgwidth3" src="../ui/img/3-2.png" alt="" />
            <div class="content">
              <div class="text">第一次中耕</div>
            </div>
          </div>
          <div class="al-item" style="left: 634px">
            <img class="imgwidth3" src="../ui/img/3-3.png" alt="" />
            <div class="content">
              <div class="text">第二次中耕</div>
            </div>
          </div>
          <div class="al-item" style="left: 787px">
            <img class="imgwidth3" src="../ui/img/3-4.png" alt="" />
            <div class="contentdiv" :style="'width:' + heights"></div>

            <div class="content">
              <div class="text">苗后除草</div>
            </div>
          </div>
          <div class="al-item" style="left: 935px">
            <img class="imgwidth3" src="../ui/img/3-5.png" alt="" />
            <div class="content contentend">
              <div class="text">第三次中耕</div>
            </div>
          </div>
          <div class="al-item" style="left: 1090px">
            <img class="imgwidth3" src="../ui/img/3-6.png" alt="" />
            <div class="content contentend">
              <div class="text">第一次追肥</div>
            </div>
          </div>
          <div class="al-item" style="left: 1244px">
            <img class="imgwidth3" src="../ui/img/3-7.png" alt="" />
            <div class="content contentend">
              <div class="text">虫害防治</div>
            </div>
          </div>
          <div class="al-item" style="left: 1400px">
            <img class="imgwidth3" src="../ui/img/3-8.png" alt="" />
            <div class="content contentend">
              <div class="text">灌溉浇水</div>
            </div>
          </div>
          <div class="al-item" style="left: 1560px">
            <img class="imgwidth3" src="../ui/img/3-9.png" alt="" />
            <div class="content contentend">
              <div class="text">第二次追肥</div>
            </div>
          </div>
        </div>
      </div>
      <div class="div1">
        <img class="but" src="../ui/img/but4.png" alt="" />
        <img class="farm" src="../ui/img/farm.png" alt="" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { keepRatio } from "@/utils/keepRadio.js";
import Border from "../ui/compontents/Border.vue";
import { ref, onMounted } from "vue";

const heights = ref(null);
const infomainRef = ref();

onMounted(() => {
  heights.value = 90 * 0.3 + "px";
  const { init: initLeft, onresize:onLeftResize } = keepRatio(infomainRef.value, {
    initW: 1857,
    initH: 1030,
  });
  initLeft();
  window.addEventListener("resize", onLeftResize);
});
onBeforeUnmount(() => {
  const { onresize:onLeftResize } = keepRatio(infomainRef.value, {
    initW: 1857,
    initH: 1030,
  });
  window.removeEventListener("resize", onLeftResize);
});
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: calc(100vh - 96px);
  // background-color: #000;
  background-image: url("../ui/img/1.png");
  background-size: 100% 100%;
}
.info {
  width: 1857px;
  height: 1030px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  .div1 {
    display: flex;
    margin-left: 36px;
  }

  .but {
    width: 193px;
    height: 65.62px;
  }
  .farm {
    width: 553px;
    height: 102px;
  }
  .bgland {
    background-image: url("../ui/img/Rectangle.png");
    width: 80%;
    margin-left: 34px;
    height: 50.58px;
    display: flex;
    align-items: center;
    // justify-content: space-evenly;
    .al-item {
      height: 75px;
      position: fixed;
      .imgwidth {
        width: 110px;
        height: 94px;
        margin-top: -130px;
      }
      .imgwidth2 {
        width: 132px;
        height: 117px;
        margin-top: -130px;
      }
      .imgwidth3 {
        width: 110px;
        height: 94px;
        margin-top: -130px;
      }
      .contentdiv {
        position: fixed;
        background: linear-gradient(
          270deg,
          #00d1ff 0%,
          rgba(0, 209, 255, 0) 100%
        );
        height: 40px;
        // opacity: 0.6;
      }
      .content {
        // position: relative;
        // width: 90px;
        height: 40px;
        background: #0a3c57;
        box-shadow: inset 0px 0px 15px 0px rgba(0, 201, 255, 0.6);
        opacity: 1;
        border: 2px solid #07d1fa;
        display: flex;
        align-items: center;
        justify-content: center;
        .text {
          font-size: 20px;
          font-family: PingFang SC-Medium, PingFang SC;
          font-weight: 500;
          color: #ffffff;
          padding: 0 5px;
        }
      }

      .contentend {
        background: rgba(255, 255, 255, 0.21);
        box-shadow: inset 0px 0px 14px 0px rgba(255, 255, 255, 0.6);
        opacity: 1;
        border: 1px solid #ffffff;
      }
    }
  }
}

.light {
  width: 10px;
  height: 10px;
  background: #20eea0;
  border-radius: 100px;
  position: fixed;
}
</style>
