<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="addTeamData.teamName"
          name="teamName"
          label="teamName"
          placeholder="teamName"
          :rules="[{ required: true, message: 'teamName' }]"
      />
      <van-field
          v-model="addTeamData.description"
          rows="4"
          autosize
          label="team description"
          type="textarea"
          placeholder="description"
      />

      <van-field
          v-model="addTeamData.expireTime"
          is-link
          readonly
          name="datePicker"
          label="Expire"
          placeholder="Choosing expire time"
          @click="showPicker = true"
      />
      <van-popup v-model:show="showPicker" position="bottom">
        <van-date-picker @confirm="onConfirm" @cancel="showPicker = false" />
      </van-popup>

<!--      <van-picker-group
          title=" 过期时间"
          :tabs="['选择过期日期', '请选择过期时间']"
          @confirm="onConfirm"
          @cancel="showPicker = false"
      >
        <van-date-picker
            v-model="currentDate"
            :min-date="minDate"
            :max-date="maxDate"
        />
        <van-time-picker v-model="updateTeamData.expireTime" />
      </van-picker-group>-->

      <van-field name="stepper" label="Max people numbers">
        <template #input>
          <van-stepper v-model="addTeamData.maxNum" max="10" min="3"/>
        </template>
      </van-field>

      <van-field name="radio" label="Type">
        <template #input>
          <van-radio-group v-model="addTeamData.status" direction="horizontal">
            <van-radio name="0">Public</van-radio>
            <van-radio name="1">Private</van-radio>
            <van-radio name="2">Encrypt</van-radio>
          </van-radio-group>
        </template>
      </van-field>
      <van-field
          v-if="Number(addTeamData.status) === 2"
          v-model="addTeamData.password"
          type="password"
          name="password"
          label="password"
          placeholder="password"
          :rules="[{ required: true, message: 'password' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        Submit！Start your Company Journey！
      </van-button>
    </div>
  </van-form>
</template>

<script setup>
import {useRouter} from "vue-router";
import {ref} from "vue";
import myAxios from "../plugins/myAxios.ts";
import {showToast} from "vant";

const router = useRouter();

const initFormData = {
  "description": "",
  "expireTime": "",
  "maxNum": 3,
  "password": "",
  "status": 0,
  "teamName": "",
  "userId": 0
};

// 需要用户填写的数据
const addTeamData = ref({...initFormData}); // 拓展对象运算符

const showPicker = ref(false);
const onConfirm = ({ selectedValues }) => {
  const currentTime = new Date().toLocaleTimeString();
  addTeamData.value.expireTime = Date.parse(selectedValues.join('-') + ' ' + currentTime);
  showPicker.value = false;
};

const onSubmit = async () => {
  const postData = {
    ...addTeamData.value,
    status: Number(addTeamData.value.status)
  }
  // todo 前端参数校验
  const res = await myAxios.post('/team/add', postData);
  if (res.data) {
    showToast("建队成功");
    router.push({
      path: "/team",
      replace: true
    })
  } else {
    showToast("添加失败");
  }
}

</script>

<style scoped>

</style>