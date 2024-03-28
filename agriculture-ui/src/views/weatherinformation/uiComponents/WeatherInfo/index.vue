<template>
  <EXHIBIT :key="show" :list="values" />
</template>
<script setup>
import EXHIBIT from "@/components/exhibit/index.vue";
import { calcWeatherList } from "./help";

const props = defineProps({
  values: Array,
  show: Boolean,
});
</script>
