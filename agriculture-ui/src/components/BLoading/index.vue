<template>
    <div class="bloading">
        <div class="dot-wrapper">
            <div v-for="item in 10" class="dot" :style="`--i:${item}`"></div>
        </div>
        <p>{{ title }}</p>
        <div class="dot-wrapper">
            <div v-for="item in 10" class="dot" :style="`--i:${item}`"></div>
        </div>
    </div>
</template>
<script setup>
let title = '拼命加载中'
</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>