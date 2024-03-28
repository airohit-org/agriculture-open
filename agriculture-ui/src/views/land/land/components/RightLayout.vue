<template>
    <div class="map-right-layout">
        <div class="top-0">
            <slot name="top-0"></slot>
        </div>
        <div class="top-1">
            <slot name="top-1"></slot>
        </div>
        <div class="top-2">
            <slot name="top-2"></slot>
        </div>
        <div class="top-3">
            <slot name="top-3"></slot>
        </div>
    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.map-right-layout {
    position: absolute;
    top: 0;
    right: 0;
    height: 100%;
    width: 272px;
    z-index: 999;
    display: flex;
    align-items: flex-end;
    flex-direction: column;
    flex-wrap: nowrap;
    justify-content: space-around;

    .top {
        &-0{
            height: 50px;
            width: 186px;
            margin-right: 71px;
        }
        &-1{
            margin-right: 71.5px;
        }
        &-2{
            margin-right: 74px;
        }
        &-3{
            height: 70px;
            margin-right: 63.48px;
            align-self: flex-end;
            justify-self: flex-end;
        }
    }
}
</style>
