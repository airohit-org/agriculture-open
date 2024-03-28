<template>
  <div class="top-btn">
    <div class="top-btn-cancel" v-if="show === cancelStatus">
      <EditBtn title="取消" @clickItem="$emit('disDraw')" type="3" />
    </div>
    <div v-if="show === addStatus" class="top-btn-add">
      <EditBtn title="添加地块" @clickItem="$emit('draw')" type="2" />
    </div>
    <div v-if="show === farmStatus" class="top-btn-add">
      <EditBtn title="画农场区域" @clickItem="$emit('drawFarm')" type="1" />
    </div>
    <div class="top-btn-edit" v-if="show === editStatus">
      <p>
        正在编辑 <span>{{ currentEditName }}</span>
      </p>
      <div class="top-btn-edit-btns">
        <div @click.stop="$emit('editCancel')">取消</div>
        <div @click.stop="$emit('editSubmit')">确定</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import EditBtn from "@/components/EditBtn/index.vue";
import { TOP_BTN_SHOW_ENUM } from "../help";
import { toRefs, reactive } from "vue";
const props = defineProps({
  show: String | Number,
  title: String,
  currentEditName: String,
});
const data = reactive({
  cancelStatus: TOP_BTN_SHOW_ENUM.DRAWING,
  addStatus: TOP_BTN_SHOW_ENUM.NO_DRAWING,
  editStatus: TOP_BTN_SHOW_ENUM.EDITING,
  farmStatus: TOP_BTN_SHOW_ENUM.FARM,
})
const { cancelStatus, addStatus, editStatus, farmStatus } = toRefs(data);
</script>

<style lang="scss" scoped>
.top-btn {
  display: flex;
  justify-content: flex-end;

  &-edit {
    transform: translateX(calc(-50vw + 80%));
    box-sizing: content-box;
    margin-left: -300px;
    width: 400px;
    height: 174px;
    background-image: url("https://oss.airoteach.cn/9c8bc06dcf2fae614e2a14507d96df255d60da9ef8208925f3da3626e80a7482.png");
    background-size: cover;
    background-position: center center;

    p {
      margin-top: 42px;
      margin-bottom: 30px;
      font-family: "PingFang SC";
      font-style: normal;
      font-weight: 400;
      font-size: 18px;
      color: #ffffff;
      text-align: center;

      span {
        color: #1fface;
      }
    }

    &-btns {
      display: flex;
      justify-content: space-around;

      div {
        width: 89px;
        height: 36.71px;
        background-size: cover;
        background-position: center;
        display: grid;
        place-items: center;
        font-family: "PingFang SC";
        font-style: normal;
        font-weight: 600;
        font-size: 14px;
        &:first-child {
          color: #00e0db;
          background-image: url("https://oss.airoteach.cn/5b8d7aba2f9064f7f84e9df8f4a597b8a21350c727628d4fcaea3d638c5c04fd.png");
        }
        &:last-child {
          color: #fff;
          background-image: url("https://oss.airoteach.cn/b00a08a77d331dd8e82406ceaffdbaa394eb691a158b3c9f7ebdfc3b79248f5c.png");
        }

        &:hover {
        }
      }
      & > div:first-child {
      }
    }
  }
}
</style>
