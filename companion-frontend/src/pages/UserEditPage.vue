<template>
  <van-form @submit="onSubmit">
      <van-field
          v-model="editUser.currentValue as string"
          :name="editUser.editKey as string"
          :label="editUser.editName as string"
          :placeholder="`Input${editUser.editName}`"
      />
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        Confirm
      </van-button>
    </div>
  </van-form>

</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import myAxios from "../plugins/myAxios.ts";
import {showSuccessToast} from "vant";
import {getCurrentUser} from "../services/user.ts";

const router = useRouter();

const route = useRoute();
const editUser = ref({
  editKey: route.query.editKey,
  editName: route.query.editName,
  currentValue: route.query.currentValue
})


// values是表单中所有元素的值
const onSubmit = async () => {
  const currentUser = await getCurrentUser();
  if(!currentUser) {
    showSuccessToast('No Login Info');
    return;
  };

  const loginUser = await getCurrentUser();

  // todo 把editKey，editName，editValue 提交到后台
  const res = await myAxios.post('/user/update', {
    'id': loginUser.id,
    [editUser.value.editKey as string]: editUser.value.currentValue,
  });
  if(res.data > 0) {
    showSuccessToast('Success');
    router.back()
  } else {
    showSuccessToast('Error');
  }
};
</script>

<style scoped>

</style>