<template>
  <div v-if="user">
    <div v-if="user" style="text-align: center;">
      <van-image
          round
          width="8rem"
          height="8rem"
          :src="user.avatarUrl"
          style="margin-top: 20px; margin-bottom: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.4)"
      />
    </div>
    <van-cell title="Current User" :value="user?.username"/>
    <van-cell title="Modify" is-link to="/user/update" />
    <van-cell title="My own team" is-link to="/user/team/create" />
    <van-cell title="In team" is-link to="/user/team/join" />
    <van-cell title="Log Out" @click="userLogout" />
  </div>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {getCurrentUser} from "../services/user.ts";
import myAxios from "../plugins/myAxios.ts";
import {showToast} from "vant";

const user = ref();

onMounted(async() => {
  user.value = await getCurrentUser();
  if (!user.value) {
    router.push({
      path: '/user/login'
    });
  }
});

const router = useRouter();

const userLogout = async () => {
  const res = await myAxios.post('/user/logout');
  // const currentUser = await getCurrentUser();
  // if (currentUser === null) {
  //  showToast("你还未登录！");
  // }
  if (res?.code === 0) {
    showToast("Success");
    router.push({
      path: '/user/login'
    })
  } else {
    showToast("Error");
  }
}

</script>
<style scoped>

</style>