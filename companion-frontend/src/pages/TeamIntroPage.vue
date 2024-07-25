<template>
  <div v-if="team">
    <van-cell title="Team Name" :value="team?.teamName" />
    <van-cell title="Team Desciption" :value="team?.description"/>
    <van-cell title="Expire Time" :value="team?.expireTime" />
    <van-cell title="Max Count" :value="team?.maxNum" />
    <van-cell title="Created Time" :value="team?.createTime" />
    <van-cell title="Owner" :value="(team?.createUserName ? `${team?.createUserName}` : `null`)" />
    <van-cell title="Current Count" :value="team?.hasJoinNum" />
  </div>

</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {ref} from "vue";

const route = useRoute();
const team = ref({
  teamName: route.query.teamName,
  description: route.query.description,
  expireTime: route.query.expireTime,
  maxNum: route.query.maxNum,
  status: route.query.status,
  createTime: route.query.createTime,
  createUserName: route.query.createUserName,
  hasJoinNum: route.query.hasJoinNum,
});
</script>

<style scoped>

</style>