<template>
  <div>
    <OverviewTitle title="设备统计" />
    <div class="content">
      <div class="content-left">
        <div class="content-left-title weight">设备总数</div>
        <div class="content-left-title">（个）</div>
        <div class="content-left-total">{{ total }}</div>
        <img class="content-left-forum" src="https://oss.airoteach.cn/5689907d4383ad08e7b2f78dbef3bcb13e6910d4554391079811078b832b5773.png" />
      </div>
      <div class="content-right">
        <div class="content-right-classify" v-for="{id, value, title, color} in list" :key="id">
          <div class="content-right-classify-block" :style="`--color: ${color}`">
            <span></span>
          </div>
          <div class="content-right-classify-list">
            <span class="content-right-classify-list-title">{{ title }}</span>
            <span class="content-right-classify-list-value" :style="`--color: ${color}`">{{ value }}</span>
          </div>
          <div class="content-right-classify-data" :style="`--color: ${color}`">
            <div class="content-right-classify-data-num">
              <span v-for="(_value,count) in 20" :class="count <= Math.floor(ratio * value)? 'blue content-right-classify-data-num-array' : 'red content-right-classify-data-num-array'" :style="`--color: ${color};--i:${count}`"></span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import OverviewTitle from '@/components/OverviewTitle/index.vue';
const props = defineProps({
  total: Number,
  list: Array,
})
const ratio = computed(() => (20 / props.total));
</script>

<style lang="scss" scoped>
.content{
.weight {
  font-weight: bold;
  font-size: 24px;
}

display: flex;
flex-wrap: nowrap;
&-left{
  margin-top: 40px;
  &-title{
    color: white;
    text-align: center;
    font-size: 14px;
  }
  &-total{
    color: #1FFACE;
    text-align: center;
    font-size: 42px;
  }
  &-forum{
    width: 137px;
    height: 34px;
    position: relative;
    bottom: 17px;
  }
}
&-right{
  font-size: 16px;
  margin-top: 24px;
  margin-left: 10px;
  &-classify{
    display: flex;
    margin-top: 30px;
    align-items: center;
    &-block{
      width: 14px;
      height: 14px;
      border: 1px solid var(--color);
      display: flex;
      align-items: center;
      justify-content: center;

      span{
        height: 6px;
        width: 6px;
        background-color: var(--color);
      }
    }
    &-list{
      margin-left: 6px;
      display: flex;
      &-title{
        color: white;
        font-size: 18px;
      }
      &-value{
        width: 25px;
        font-size: 18px;
        margin-left: 6px;
        color: var(--color);
      }
    }
    &-data{
      margin-left: 6px;
      border: 1px solid var(--color);
      height: 12px;
      &-num{
        display: flex;
        &-array{
          width: 6px;
          height: 6px;
          background-color: var(--color);
          transform: rotate(45deg);
          margin-left: 3px;
          margin-top: 2px;
        }
        .red{
          opacity: 0.2;
        }
        .blue{
          opacity: .8;
          animation: scale112 0.8s linear infinite;
          animation-delay: calc(.04s * var(--i));
        }
      }
    }
  }
}
}


@keyframes scale112 {
0% {
  transform: scale(1) rotate(45deg);;
  opacity: .8;
}

10%{
  opacity: 1;
  transform: scale(1.5) rotate(45deg);;
}
50%,100% {
  opacity: .8;
  transform: scale(1) rotate(45deg);;
}
}
</style>
