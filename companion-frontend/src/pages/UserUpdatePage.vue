<template>
  <div v-if="user">
    <van-cell title="Name" is-link to="/user/edit" :value="user?.username" @click="toEdit('username', 'username', user.username)"/>
    <van-cell title="Account" is-link to="/user/edit" :value="user?.userAccount" @click="toEdit('userAccount', 'userAccount', user.userAccount)"/>


    <van-cell title="Avatar" is-link to="/user/edit" :value="user?.avatarUrl" @click="toEdit('avatarUrl', 'avatarUrl', user.avatarUrl)">
        <img style="height: 48px" :src="user?.avatarUrl" />
    </van-cell>

    <van-cell title="Gender(0-Girl,1-Boy)" is-link to="/user/edit" :value="user?.gender" @click="toEdit('gender', 'gender', user.gender)"/>
    <van-cell title="Phone" is-link to="/user/edit" :value="user?.phone" @click="toEdit('phone', 'Phone', user.phone)"/>
    <van-cell title="Email" is-link to="/user/edit" :value="user?.email" @click="toEdit('email', 'Email', user.email)" />
    <van-cell title="Tags" is-link to="/user/edit" :value="user?.tags" @click="toEdit('tags', 'tags', user.tags)" />
<!--    <van-cell title="你的编号" />-->
    <van-cell title="Register Time" :value="user?.createTime" />
  </div>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {getCurrentUser} from "../services/user.ts";

const user = ref();

onMounted(async() => {
  user.value = await getCurrentUser();
});

const router = useRouter();

const toEdit = (editKey: string, editName: string, currentValue: string) => {
  router.push({
    path: '/user/edit',
    query: {
      editKey,
      editName,
      currentValue
    }
  })
};



</script>
<style scoped>

</style>