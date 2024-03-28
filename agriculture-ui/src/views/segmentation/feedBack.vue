<template>
    <div class="feedback">
        {{ value }}
    </div>
</template>
<script setup>
const props = defineProps({
    value: String
})
</script>

<style lang="scss" scoped>
.feedback {
    width: 100%;
    height: 100%;
    background-color: rgba($color: #000000, $alpha: .7);
    display: grid;
    place-items: center;
    color: #fff;
    font-size: 20px;
}
</style>