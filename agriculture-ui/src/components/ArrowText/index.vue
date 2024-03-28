<template>
<div class="arrow-text">
    <div>
        <slot />
    </div>
    <div></div>
</div>

</template>
<style lang="scss" scoped>
.arrow-text {
    div {
        position: relative;
        &:first-child {
            height: 100%;
            width: 100%;
            border: 1px solid #1FFACE;
            clip-path: polygon(0 0, 100% 0, 100% 100%, 63.5% 100%, calc(63.5% - 2px) calc(100% - 2px), calc(37.5% + 2px) calc(100% - 2px), 37.5% 100%, 0 100%);
            border-radius: 3px;
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
            user-select: none;
        } 
        &:last-child {
            position: absolute;
            height: calc(7.5 / 19) * 100%;
            aspect-ratio: 1 / 1;
            border-bottom: 1.414px solid #1FFACE;
            border-right: 1.414px solid #1FFACE;
            left: calc(50% + .2px);
            bottom: .27px;
            transform: translateX(-50%) translateY(50%) rotate(45deg);
        } 
    }
}
</style>