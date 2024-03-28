<template>
    <div class="Hazard">
        <OverviewTitle title="病虫害" />
        <div class="content">
            <div class="list" v-for="item in list">
                <img class="img" :src="item.img" alt="">
                <div class="title">{{ item.title }}</div>
                <div class="text">{{ item.text }}</div>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";

const props = defineProps({
  loading: Boolean  
})

const list = [
    {
        img: 'https://oss.airoteach.cn/3a06bcb512d1ff5d7edaeec06044ba74ab3b159482c8a3a446303395dedde711.png',
        title: '稻瘟病',
        text: '稻瘟病又名稻热病、火烧瘟、叩头瘟等，是由稻瘟病原菌引起的、发生...'
    },
    {
        img: 'https://oss.airoteach.cn/48c1c712ded7b1f5bef21b17daa035470c7d2f0484a07b7e7598734a086372f2.png',
        title: '大斑病',
        text: '玉米大斑病是由大斑病凸脐蠕孢引起的、发生在玉米的病害。'
    },
    {
        img: 'https://oss.airoteach.cn/c6f9518cd429af82bfeef0478acff7986ed20dcc098a4991de32ed3fcff13978.png',
        title: '耙点病',
        text: '大豆耙点病是由山扁豆生棒孢引起的、发生在大豆的病害。可为害叶片、叶柄、茎、荚及种子。'
    }
]
</script>

<style lang="scss" scoped>
.Hazard {
    width: 100%;
    .content {
        display: flex;
        justify-content: space-between;
        .list {

            .img {
                width: 69px;
                height: 69px;
                margin-left: 24px;
            }
            .title {
                width: 112px;
                height: 26px;
                background-image: url('https://oss.airoteach.cn/c946c444b393da00c2f9a5876b79394bc8e2db375f9aae7dc8e68b7667727cf2.png');
                background-size: cover;
                font-size: 14px;
                font-family: FZZongYi-M05S-Regular, FZZongYi-M05S;
                font-weight: 400;
                text-align: center;
                padding-top: 4px;
                color: #FFFFFF;
            }
            .text {
                width: 115px;
                height: 108px;
                padding: 5px;
                font-size: 12px;
                font-family: PingFang SC-Regular, PingFang SC;
                font-weight: 400;
                color: #FFFFFF;
                background-image: url('https://oss.airoteach.cn/10e88c5301bae5bae96ce40ef26d8b5352342eb01d0ae82f2caa047d42a723a3.png');
                background-size: cover;
                background-repeat: no-repeat;
                margin-top: 13px;


                text-align: justify;
                display: -webkit-box;
                -webkit-line-clamp: 6;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
            }
        }
    }
}
</style>
