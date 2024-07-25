<template>
  <van-sticky>
    <van-nav-bar
        :title="`Info`"
        left-arrow
        @click-left="onClickLeft"
    >
    </van-nav-bar>
  </van-sticky>
  <div v-if="userInfo" style="text-align: center;">
    <van-image
        round
        width="8rem"
        height="8rem"
        :src="userInfo.avatarUrl"
        style="margin-top: 20px; margin-bottom: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.4)"
    />
  </div>
    <van-cell title="username" :value="userInfo.username ? userInfo.username : `该用户很懒，未填写,The user is lazy and unfilled`" icon="user-o"/>
    <van-cell title="gender" :value="userInfo.gender ? userInfo.gender : `该用户很懒，未填写,The user is lazy and unfilled`" icon="contact-o"/>
    <van-cell title="profile" :value="userInfo.profile ? userInfo.profile : `该用户很懒，未填写,The user is lazy and unfilled`" icon="comment-o"/>
    <van-cell title="phone" :value="userInfo.phone ? userInfo.phone : `该用户很懒，未填写,The user is lazy and unfilled`" icon="phone-o"/>
    <van-cell title="email" :value="userInfo.email ? userInfo.email : '该用户很懒，未填写,The user is lazy and unfilled'" icon="envelop-o"/>
    <van-cell title="distance" :value="userInfo.distance ? userInfo.distance : `该用户似乎离你很远,The user seems to be far away from you`" icon="location-o" />


  <div style="text-align: center; margin-top: 10px;">
    <van-button icon="plus" type="success" text="Add" size="normal" style="border-radius: 10px; margin-top: 10px" @click="addFriend"/>
    <!--    <van-button icon="minus" type="primary" text="私聊" size="small"/>-->
  </div>

</template>

<script setup lang="ts">

import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import myAxios from "../plugins/myAxios";
import {showToast} from "vant";

const router = useRouter();
const route = useRoute();
const { userInfoParam } = route.query;

const userInfo = ref(JSON.parse(userInfoParam));

const addFriend = async () => {
  const res = await myAxios.post('/friend/add', {
    friendId: userInfo.value.id
  });
  if (res?.code === 0) {
    showToast('Success');
  } else {
    showToast('Error，he is already your friend');
  }
};

const onClickLeft = () => {
  router.back();
};
</script>



<style scoped>

</style>