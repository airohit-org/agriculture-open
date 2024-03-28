<template>
  <div class="layer-select">
    <div
      v-for="({ label, value: itemValue, imgSrc, activeImgSrc }, index) in list"
      class="item"
      @click="handleClick(itemValue)"
      :class="modelValue?.includes(itemValue) ? 'active': ''"
      :key="index"
    >
      <div>
        <span>{{ label }}</span>
        <img v-if="modelValue?.includes(itemValue)" src="https://oss.airoteach.cn/779eb7bbc1c9a84ef471c7c758325097cb6bd3d963cd4b954dc5851675105fa4.png" alt="">
        <img v-else src="https://oss.airoteach.cn/1b75b1a90b850d46889e20c75444b04d9baf703ec1b48cd779da8cf627a62779.png" alt="">
      </div>
      <div>
        <img :src="modelValue?.includes(itemValue) ? activeImgSrc : imgSrc" alt="" />
        <img v-if="modelValue?.includes(itemValue)" src="https://oss.airoteach.cn/282d95ee8af3a2f300ee3c19ed905bdadd6ecf38c30b05353a76c1c52789664c.png" alt="" />
        <img v-else src="https://oss.airoteach.cn/8393051a1b6ee6075f4848e1e8cfb1ac12685be014a416d100a06f9dfe933b29.png" alt="" />
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  list: {
    type: Array,
    default: () => []
  },
  modelValue:{
    type: Array,
    default: () => []
  }
})
const emit = defineEmits(['update:modelValue'])

function handleClick(itemValue) {
  let res = props.modelValue;
  if (props.modelValue?.includes(itemValue)) {
    res = props.modelValue.filter(item => item !== itemValue)
  } else {
    res = props.modelValue.concat(itemValue);
  }
  emit('update:modelValue', res);
}

</script>

<style lang="scss" scoped>
.layer-select {
  .item {
    user-select: none;
    cursor: pointer;
    display: flex;
    margin-bottom: 38px;
    div {
      &:first-child {
        height: 39px;
        width: 130.5px;
        position: relative;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        span {
            margin-right: 25.5px;
            font-family: 'YouSheBiaoTiHei';
            font-style: normal;
            font-weight: 400;
            font-size: 20px;
            background: linear-gradient(180deg, #FFFFFF 34.38%, #1FFACE 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            text-fill-color: transparent;
            text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
        }

        img {
            position: absolute;
            left: 0;
            top: 0;
            height: 39px;
            width: 130.5px;
        }
      }

      &:last-child {
        margin-left: -9.5px;
        position: relative;
        overflow: visible;
        width: 53.5px;
        height: 39px;
        img {
            &:first-child{
                position: absolute;
                right: -7px;
                top: -9px;
                width: 50px;
                height: 50px;
            }
            &:last-child{
                position: absolute;
                left: 0;
                top: 0;
                width: 53.5px;
                height: 39px;
            }
        }
      }
    }
  }

  .active {
    div {
      &:first-child{
        span {
            background: linear-gradient(180deg, #FFFFFF 34.38%, rgba(255, 209, 92, 0.92) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }
      }

      &:last-child {
        background-image: url('');
      }
    }
  }
}
</style>
