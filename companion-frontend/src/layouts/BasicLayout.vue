<template>
  <van-nav-bar
      :title="title"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
  >
    <template #right>
      <van-icon name="search" size="18" />
    </template>
  </van-nav-bar>
  <van-notice-bar
      speed="30"
      left-icon="volume-o"
      color="#1989fa" background="#ecf9ff"
      text="Companions系统招收广告，大家都可以来打广告(It is obvious that this is An AD PLACEMENT)"
  />

  <div id="cotent" style="padding-bottom: 50px">
    <router-view/>
  </div>

  <van-tabbar route @change="onChange">
    <van-tabbar-item to="/" icon="home-o" name="index">Main Page</van-tabbar-item>
    <van-tabbar-item to="/friend" icon="friends-o" name="friend">Friend</van-tabbar-item>
    <van-tabbar-item to="/team" icon="search" name="team">Team</van-tabbar-item>
    <van-tabbar-item to="/user" icon="friends-o" name="user">Me</van-tabbar-item>
  </van-tabbar>
</template>
<script setup lang="ts">
import {useRoute, useRouter} from 'vue-router';
import {ref} from "vue";
import routes from "../config/route.ts";

  const router = useRouter()
  const route = useRoute();

  const DEFAULT_TITLE = "Companions";
  const title = ref(DEFAULT_TITLE);


  /**
   * 根据路由切换标题
   */
  router.beforeEach((to, from) => {
    const toPath = to.path;
    routes.find((route) => {
      return toPath == route.path;
    })
    title.value = route?.title ?? DEFAULT_TITLE;
  })

  const onClickLeft = () => {
    router.back();
  }
  const onClickRight = () => {
    router.push('/search');
  };
  const onChange = () => {
  }

</script>
<style scoped>

</style>