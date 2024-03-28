<template>
  <el-card shadow="hover" class="card" @click="goCal(props.planInfo)">
    <div slot="header" class="fix">
      <div class="clearfix">
        <div class="spanId">
          <div class="spanImg">
            <div class="id">
              {{ props.index }}
            </div>
          </div>
          <div class="planName">{{ props.planInfo.planName }}</div>
        </div>
        <div class="spanStatus col">
          <img v-if="props.planInfo.status === 0" class="img" src="../../../../assets/images/planting-plan/nopublish-img@2x.png">
          <img v-else class="img" src="../../../../assets/images/planting-plan/publish-img@2x.png">
        </div>
      </div>
      <img v-if="!props.isTemplate" class="close" src="../../../../assets/images/planting-plan/Close@2x.png" @click.prevent.stop="handleDelete(props.planInfo)">
    </div>
    <img class="set" src="../../../../assets/images/planting-plan/iset@2x.png" @click.prevent.stop="handleUpdate(props.planInfo)">
    <div class="cardItem" @click="goCal(props.planInfo)">
      <div class="item">
        <img class="img-item" src="../../../../assets/images/planting-plan/iname@2x.png" alt="">
        <div>作物及品种：
          <span class="space">
            {{ props.planInfo.cropsName }}=>{{ props.planInfo.cropsTypeName }}
          </span>
        </div>
      </div>
      <div class="item">
        <img class="img-item" src="../../../../assets/images/planting-plan/istarttime@2x.png" alt="">
        <div>
          开始日期:
          <span class="space">
            {{ parseTime(props.planInfo.startTime, '{y}-{m}-{d}') }}
          </span>
        </div>
      </div>
      <div class="item">
        <img class="img-item" src="../../../../assets/images/planting-plan/iendtime@2x.png" alt="">
        <div>
          创建日期:
          <span class="space">
            {{ parseTime(props.planInfo.createTime, '{y}-{m}-{d}') }}
          </span>
        </div>
      </div>
      <div class="item">
        <img class="img-item" src="../../../../assets/images/planting-plan/imap@2x.png" alt="">
        <div>
          所属地块:
          <span class="space">
            {{ props.planInfo.landName ? props.planInfo.landName : '未关联' }}
          </span>
        </div>
      </div>
    </div>
    <div class="cardFooter">
      <el-button v-show="props.planInfo.status === 1" class="publish button-font-publish" size="small"
        @click.prevent.stop="isPublish(0, props.planInfo)">发布</el-button>
      <el-button v-show="props.planInfo.status === 0 && props.planInfo.landName === null" class="nopublish button-font" size="small"
        @click.prevent.stop="isPublish(1, props.planInfo)">取消发布</el-button>
      <div></div>
      <el-button class="clone button-font" size="small" @click.prevent.stop="clonePlan(props.planInfo)">克隆</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { parseTime } from '@/utils/ruoyi'
const router = useRouter()

const props = defineProps({
  planInfo: {
    type: Object,
    default: () => {}
  },
  index: {
    type: Number,
    default: 0    
  },
  isTemplate:{
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['deletePlan', 'updatePlan', 'isPublish', 'clonePlan'])

function goCal(item) {
  router.push({
    name: 'Calendar',
    query: {
      types: 'query',
      row: JSON.stringify(item)
    }
  })
}

function handleDelete(item) {
  emit('deletePlan', item)
}
function handleUpdate(item) {
  emit('updatePlan', item)
}
function isPublish(val, item) {
  emit('isPublish', val, item)
}
function clonePlan(item) {
  emit('clonePlan', item)
}
</script>

<style lang="scss" scoped>

.card {
  cursor: pointer;
  border-radius: 14px 14px 14px 14px;

}

:deep(.el-card__header) {
  padding: 0;

}

.col {
    position: absolute;
    left: -34px;
}

.fix {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .close {
    width: 17px;
    height: 17.5px;
    margin-right: 20px;

  }
}

.clearfix {
  display: flex;
  height: 60px;
  width: 404px;
  // flex-direction: row;
  flex-wrap: nowrap;
  // background: linear-gradient(90deg, #D2FFE7 0%, #F3FFFA 44%, #FCFDFD 100%);
  background: url('../../../../assets/images/planting-plan/bgimg@2x.png');
  background-size: cover;

  .spanStatus {

    // flex: 1;
    .img {
      width: 41px;
      height: 78px;
    }
  }

  .spanId {
    display: flex;
    justify-content: center;
    align-items: center;

    .spanImg {

      width: 36px;
      height: 34px;
      position: relative;
      background: url('../../../../assets/images/planting-plan/Polygon54@2x.png');
      background-size: cover;
      // display: flex;
      // justify-content: center;
      // align-items: center;
      // margin-top: 15px;
      margin-left: 39px;

      .id {
        font-size: 16px;
        font-family: FZZhengHeiS-EB-GB-Regular, FZZhengHeiS-EB-GB;
        font-weight: 400;
        text-align: center;
        // margin-top: 3px;
        line-height: 30px;
        // top: 4px;
        // left: 3px;
        // position: absolute;
        color: #FFFFFF;
      }
    }

    .planName {
      font-size: 22px;
      font-family: PingFang SC-Semibold, PingFang SC;
      font-weight: 600;
      margin-left: 7px;
      color: #377E62;
      width: 250px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }

}

:deep(.el-card__body) {
  // background: #FFFFFF linear-gradient(180deg, #FFFFFF 0%, #FFFFFF 41%, #F4FFFB 100%);
  // box-shadow: 0px 4px 18px 0px rgba(220, 220, 220, 0.3);
  // position: relative;

  .set {
    position: absolute;
    right: 20px;
    width: 24px;
    height: 24px;
  }
}

.cardItem {
  width: 300px;
  border-radius: 0px 0px 0px 0px;
  opacity: 1;
  margin-top: 12px;

  .item {
    display: flex;
    margin-bottom: 18px;
    align-items: center;
    font-size: 16px;
    font-family: PingFang SC-Regular, PingFang SC;
    font-weight: 500;
    color: #333333;

    .img-item {
      width: 28px;
      height: 28px;
      margin-right: 10px;
    }

    .space {
      margin-left: 15px;
    }
  }

}

.cardFooter {
  display: flex;
  // justify-content: center;
  justify-content: space-between;
  position: relative;
  margin-top: 25px;
}

.publish {
  width: 100px;
  height: 36px;
  border: 1px solid #20C7A8;
  border-radius: 74px 74px 74px 74px;
  opacity: 1;
}

.clone {
  width: 100px;
  height: 36px;
  background: #20C7A8;
  border: 1px solid #20C7A8;
  border-radius: 74px 74px 74px 74px;
  opacity: 1;
}

.nopublish {
  width: 100px;
  height: 36px;
  background: #C6CED2;
  border-radius: 74px 74px 74px 74px;
  opacity: 1;
}

.button-font {
  font-size: 16px;
  font-family: PingFang SC-Semibold, PingFang SC;
  font-weight: 600;
  color: #FFFFFF;
}

.button-font-publish {
  font-size: 16px;
  font-family: PingFang SC-Semibold, PingFang SC;
  font-weight: 600;
  color: #20C7A8;
}
</style>