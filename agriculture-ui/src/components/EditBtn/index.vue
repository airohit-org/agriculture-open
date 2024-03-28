<template>
    <div class="edit-btn" :class="`edit-btn-${type}`" @click="$emit('clickItem')">
        <img :src="iconMatch[type]" alt="">
        <span>{{ title }}</span>
    </div>
</template>

<script setup>
const props = defineProps({
    title: String,
    type: String
})
const iconMatch = {
    1: 'https://oss.airoteach.cn/10e2007cd7971ed15dad1781ef25f730fa903fab40f15f6869dfdc66e9641609.png',
    2: 'https://oss.airoteach.cn/eed702c65aa485c4c95d52766d9e725bb7341a5c6560f4e462e57e90c30218ce.png',
    3: 'https://oss.airoteach.cn/7aecea3e0e167d0e23cbd6846874aedfaf090bf9551504a0380a5dcbe2be41fb.png',
}
</script>

<style scoped lang="scss">
.edit-btn {
    user-select: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    padding: 9.5px 25px 9.5px 0px;
    background-image: radial-gradient(166.2% 69.17% at 50% 108.49%, #1FFACE 5%, rgba(0, 117, 255, 0) 33.34%);
    border: 1px solid rgba(31, 250, 206, 0.5);
    position: relative;

    &-1 {
        background-color: rgba($color: #1FFACE, $alpha: .3);
    }

    &-2 {
        background-color: rgba($color: #1FFACE, $alpha: .3);
    }

    &-3{

    }

    &::after{
        content: '';
        width: 0px;
        height: 0px;
        border-width: 5.5px;
        border-style: solid;
        border-top-color: #1FFACE;
        border-right-color: #1FFACE;
        border-bottom-color: transparent;
        border-left-color: transparent;
        position: absolute;
        top: 0;
        right: 0;
    }

    // &::before{
    //     content: '';
    //     position: absolute;
    //     bottom: 0;
    //     right: 0;
    //     left: 0;
    // }


    img {
        width: 22px;
        height: 22px;
        box-sizing: content-box;
        padding: 0 15px;
        margin-right: 20px;
    }

    span {
        font-family: 'YouSheBiaoTiHei';
        font-weight: 400;
        font-size: 24px;
        color: #FFFFFF;
        white-space: nowrap;
    }

}
</style>