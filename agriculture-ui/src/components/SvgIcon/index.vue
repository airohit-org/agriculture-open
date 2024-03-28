<template>
  <svg :class="svgClass" aria-hidden="true" class="svg-icon-new">
    <use :xlink:href="iconName" :fill="color" />
  </svg>
</template>

<script>
export default defineComponent({
  props: {
    iconClass: {
      type: String,
      required: true
    },
    className: {
      type: String,
      default: ''
    },
    color: {
      type: String,
      default: ''
    },
  },
  setup(props) {
    return {
      iconName: computed(() => `#icon-${props.iconClass}`),
      svgClass: computed(() => {
        if (props.className) {
          return `svg-icon ${props.className}`
        }
        return 'svg-icon'
      })
    }
  }
})
</script>

<style scoped>
.svg-icon {
  width: 1.1em;
  height: 1em;
  vertical-align: -0.6em;
  fill: currentColor;
  overflow: hidden;
}
/* div :nth-of-type(2)>.el-submenu>.el-sub-menu__title>.svg-icon {
  width: 1.1em;
  height: 0.95em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
} */
.svg-icon-new {
  width: 1.8em;
  height: 1.8em;
}
.svg-external-icon {
  background-color: currentColor;
  mask-size: cover!important;
  display: inline-block;
}
</style>