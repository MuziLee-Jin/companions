<template>
  <van-sticky>
    <van-nav-bar
        :title="`Register`"
        left-arrow
        @click-left="onClickLeft"
    >
    </van-nav-bar>
  </van-sticky>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="userAccount"
          name="userAccount"
          label="Account"
          placeholder="Suggest use your ID if you are in Campus"
          :rules="[{ required: true, message: 'Account' }]"
      />
      <van-field
          v-model="userPassword"
          type="password"
          name="userPassword"
          label="Password"
          placeholder="Password,at least 8 characters"
          :rules="[{ required: true, message: 'Password' }]"
      />
      <van-field
          v-model="checkPassword"
          type="password"
          name="checkPassword"
          label="Check Password"
          placeholder="plz input your password again"
          :rules="[{ required: true, message: 'plz input your password again' }]"
      />
<!--      <van-field-->
<!--        v-model="planetCode"-->
<!--        type="text"-->
<!--        name="planetCode"-->
<!--        label="编号"-->
<!--        placeholder="请填写编号(your favorite number)"-->
<!--        :rules="[{ required: true, message: '请填写编号' }]"-->
<!--    />-->
      <van-field
          v-model="username"
          name="username"
          label="username"
          placeholder="input your username"
          :rules="[{ required: true, message: 'input your username' }]"
      />
      <van-field
          v-model="gender"
          type="text"
          name="性别"
          label="Gender"
          placeholder="Male or Female"
          :rules="[{ required: true, message: 'input your Gender' }]"
      />
<!--      <van-field-->
<!--          v-model="phone"-->
<!--          name="phone"-->
<!--          label="手机号"-->
<!--          placeholder="手机号"-->
<!--          :rules="[{ required: true, message: '请填写手机号' }]"-->
<!--      />-->
<!--      <van-field-->
<!--          v-model="avatarUrl"-->
<!--          type="text"-->
<!--          name="头像"-->
<!--          label="头像"-->
<!--          placeholder="请填写头像"-->
<!--          :rules="[{ required: true, message: '请填写头像' }]"-->
<!--      />-->
      <van-field
          v-model="longitude"
          type="text"
          name="longitude"
          label="longitude"
          placeholder="longitude(-180~180)"
          :rules="[{ required: true, message: 'longitude' }]"
      />
      <van-field
          v-model="dimension"
          type="text"
          name="longitude"
          label="longitude"
          placeholder="latitude(-90~90)"
          :rules="[{ required: true, message: 'latitude' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        Register!!!
      </van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const userAccount = ref('');
const userPassword = ref('');
const checkPassword = ref('');
const planetCode = ref('');
const gender = ref('');
const avatarUrl = ref('');
const username = ref('');
const phone = ref('');
const longitude = ref('');
const dimension = ref('');

// const onSubmit = async () => {
//   const res = await myAxios.post('/user/register', {
//     userAccount: userAccount.value,
//     userPassword: userPassword.value,
//     checkPassword: checkPassword.value,
//     planetCode: planetCode.value,
//     gender: gender.value === '男' ? 1 : 0,
//     avatarUrl: avatarUrl.value,
//     username: username.value,
//     phone: phone.value,
//   });
//   if (res?.code === 0) {
//     showToast("注册成功");
//     router.push({
//       path: '/user/registerTags',
//
//     })
//   } else {
//     showToast("注册失败" + (res.description ? `, ${res.description}` : ''));
//   }
// }



const onSubmit = () => {
  const registerUserParam = {
    userAccount: userAccount.value,
    userPassword: userPassword.value,
    checkPassword: checkPassword.value,
    planetCode: planetCode.value,
    gender: gender.value === '男' ? 1 : 0,
    avatarUrl: avatarUrl.value,
    username: username.value,
    phone: phone.value,
    longitude: longitude.value,
    dimension: dimension.value,
};

  router.push({
    path: '/user/registerTags',
    query: {
      registerUser: JSON.stringify(registerUserParam),
    }
  });
};

const onClickLeft = () => {
  router.back();
};
</script>

<style scoped>

</style>