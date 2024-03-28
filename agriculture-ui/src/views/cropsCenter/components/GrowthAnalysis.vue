<template>
    <div class="growth-ana">
      <OverviewTitle title="长势分析"/>
      <div class="content">
        <div class="inner">
          <GrowthInner hiddenInfo />
        </div>
      </div>
    </div>
</template>

<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import GrowthInner from "@/views/segmentation/growthInner.vue";
</script>

<style lang="scss" scoped>
.growth-ana {
    width: 100%;
    height: 100%;
    .content {
      margin: 15.5px auto 0;
      background: url('https://oss.airoteach.cn/0fc88976365940b2fda5a3b956b8b2cf8e6d5225001f744478953c00b7a5fa8f.png');
      background-size: cover;
      background-position: center center;
      background-repeat: no-repeat;
      width: 416px;
      height: 263px;

      .inner {
        padding-top: 33.7px;
        padding-left: 36px;
        box-sizing: content-box;
        width: 336px;
        height: 196px;
      }
    }
}

.tet{
  width: 100%;
  height: 100%;
  background-color: blue;
}
</style>
