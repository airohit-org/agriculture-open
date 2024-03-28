<template>
    <img :src="realSrc || defaultSrc" @error="realSrc = defaultSrc" />    
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
    src: String,
    defaultSrc: String
})

const realSrc = ref()

watch(() => props.src, () => {
    realSrc.value = props.src;
}, {immediate: true})
</script>
