<template>
  <van-sticky>
    <van-nav-bar
        :title="`Login`"
        left-arrow
        @click-left="onClickLeft"
    >
    </van-nav-bar>
  </van-sticky>
  <div style="text-align: center; margin-top: 30px; margin-bottom: 15px">
    <van-image
        round
        width="10rem"
        height="8rem"
        src="https://article-images.zsxq.com/Fnj10F-xlXGAHOGBru1dT6tmJsLr"
    />
  </div>
  <div id="title" style="text-align: center; margin-top: 30px; margin-bottom: 30px">
    <h3>Companions, find your soulmate</h3>
  </div>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="userAccount"
          name="userAccount"
          label="userAccount"
          placeholder="userAccount"
          :rules="[{ required: true, message: 'userAccount' }]"
      />
      <van-field
          v-model="userPassword"
          type="password"
          name="userPassword"
          label="userPassword"
          placeholder="userPassword"
          :rules="[{ required: true, message: 'userPassword' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        Login
      </van-button>
    </div>
    <div style="margin: 16px;">
      <van-button round block type="primary" is-link to="/user/register">
        Register
      </van-button>
    </div>
  </van-form>

</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import {showSuccessToast} from "vant";
import myAxios from "../plugins/myAxios.ts";

const router = useRouter();

const userAccount = ref('');
const userPassword = ref('');

const route = useRoute();
const onSubmit = async () => {
  const res = await myAxios.post('/user/login',{
    userAccount: userAccount.value,
    userPassword: userPassword.value
  })
  if(res.data) {
    showSuccessToast('Success');
    // 跳转到之前的页面
    const redirectUrl = route.query?.redirect as string ?? '/';
    window.location.href = redirectUrl;
  } else {
    showSuccessToast('Error');
  }
};

const onClickLeft = () => {
  router.back();
}
</script>
<style scoped>
.systemLogo{
  margin: auto;
}

</style>