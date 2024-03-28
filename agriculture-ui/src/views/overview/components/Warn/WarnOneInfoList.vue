<template>
  <div class="roll">
    <div class="list">
      <div
        v-for="({ title, icon, text }, index) in innerlist"
        :key="index"
        class="list-item"
        :class="!index && play ? 'toUp' : ''"
      >
        <WarnOneInfo :title="title" :icon="icon" :text="text" />
      </div>
    </div>
  </div>
</template>

<script setup>
import WarnOneInfo from "./WarnOneInfo.vue";
import { reactive, toRefs } from "vue";

const props = defineProps({
  list: {
    type: Array,
    default: () => [],
  }
})

const data = reactive({
  innerlist: [],
  play: false,
  timer: null, // //接收定时器
})
const { innerlist, play, timer } = toRefs(data)

function startPlay() {
  play.value = true;
  timer.value = setTimeout(() => {
    const next = JSON.parse(JSON.stringify(innerlist?.value[0] || {}));
    innerlist.value.push(next);
    innerlist.value.shift();
    play.value = false;
  }, 500);
}

onMounted(() => {
  setInterval(startPlay.value, 4000);
})
onUnmounted(() => {
  clearInterval(timer.value);
})

watch(
  () => props.list, 
  () => {
    innerlist.value = props.list.concat();
  }, 
  {immediate: true}
)

</script>
<style lang="scss" scoped>
.roll {
  width: 100%;
  height: 45px;

  .toUp {
    transition: transform ease 1s;
    transform: translateY(-100%);
  }
  .list {
    overflow: hidden;
    height: 100%;
  }
  .list-item {
    height: 100%;
  }
}
</style>
