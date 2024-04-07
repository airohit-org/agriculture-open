使用式例
<template>
    <div class="bgs">
        <Exhibit
            :list="list"
        ></Exhibit>
    </div>
</template>

<script>
import Exhibit from '@/components/exhibit/index'
export default  {
    data() {
        return {
            list: [
                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },
                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '东北',
                },                {
                    imgSrc: 'https://oss.airoteach.cn/f3f585acecfb38bc5ce66b7b5aafb2bf3b77fbf510a39ec66eba37f9708c0935.png',
                    title: '风力',
                    prompt: '',
                }
            ]
        }
    },
    components: {
        Exhibit
    }
}
</script>

<style lang="scss" scoped>
.bgs {
    height: 200px;
    width: 411px;
}
</style>