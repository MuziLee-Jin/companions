<template>
  <van-sticky>
    <van-nav-bar
        :title="`Select Tags`"
        left-arrow
        @click-left="onClickLeft"
    >
      <template #right>
        <van-icon name="search" size="18"/>
      </template>
    </van-nav-bar>
  </van-sticky>
  <form action="/public">
    <van-search
        v-model="searchText"
        show-action
        placeholder="Please input the tag you wish to search for"
        @search="onSearch"
        @cancel="onCancel"
    />
  </form>
  <van-divider content-position="left">selected tags</van-divider>
  <div v-if="activeIds.length === 0">please select your tags</div>
  <van-row gutter="16" style="padding: 0 16px">
    <van-col>
      <van-tag v-for="tag in activeIds"  closeable size="medium" type="primary" @close="doClose(tag)">
        {{tag}}
      </van-tag>
    </van-col>
  </van-row>
  <van-divider content-position="left">selecting tag</van-divider>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
  />
  <div style="padding: 12px">
    <van-button block type="primary" @click="doSearchResult" style="margin: 12px">Register</van-button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import {useRoute, useRouter} from 'vue-router';
import myAxios from "../plugins/myAxios.ts";
import {showToast} from "vant";

const router = useRouter();
const route = useRoute();

const searchText = ref('');
let originTagList = [
  {
    text: 'Major',
    children: [
      { text: 'Java', id: 'java' },
      { text: 'C++', id: 'c++' },
      { text: 'Go', id: 'go' },
      { text: 'Rust', id: 'Rust' },
      { text: 'Python', id: 'python' },
      { text: 'EC', id: 'EC' },
      { text: 'CS', id: 'CS' },
      { text: 'VUE', id: 'VUE' },
      { text: 'HTML', id: 'HTML' },

    ],
  },
  {
    text: 'Identity',
    children: [
      { text: 'freshman', id: 'freshman' },
      { text: 'sophomore', id: 'sophomore' },
      { text: 'junior', id: 'junior' },
      { text: 'senior', id: 'senior' },
      { text: 'undergraduate', id: 'undergraduate' },
      { text: 'PhD ', id: 'PhD ' },
      { text: 'teacher', id: 'teacher' },
      { text: 'else', id: 'else'},
    ],
  },
  {
    text: 'Gender',
    children: [
      { text: 'Male(男｜0)', id: '男' },
      { text: 'Female(女｜1)', id: '女' },
    ],
  },
  {
    text: 'State',
    children: [
      { text: '单身', id: '单身' },
      { text: '有对象', id: '有对象' },
      { text: 'happy', id: 'happy' },
      { text: 'emo', id: 'emo' },
      { text: '内卷', id: '内卷' },
      { text: 'lonely', id: 'lonely'},
      { text: 'hungry', id: 'hungry'},
      { text: 'busy', id: 'busy'},
    ],
  },
];
let tagList = ref(originTagList);
const onSearch = () => {
  tagList.value = originTagList.map(parentTag => {
    if (Array.isArray(parentTag.children) && parentTag.children.length > 0) {
      const tempChildren = parentTag.children.filter(item => item.text.includes(searchText.value));
      const tempParentTag = { ...parentTag };
      tempParentTag.children = tempChildren;
      return tempParentTag;
    }
    return parentTag;
  });
};

const onCancel = () => {
  searchText.value = '';
  tagList.value = originTagList;
}

// 已选中的标签
const activeIds = ref([]);
const activeIndex = ref(0);

// 一处标签
function doClose(tag) {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag
  });
}

const { registerUser } = route.query;

const doSearchResult =  async () =>{
  const registerUserParam = JSON.parse(registerUser);
  const userId = await myAxios.post('/user/register', {
    userAccount: registerUserParam.userAccount,
    userPassword: registerUserParam.userPassword,
    checkPassword: registerUserParam.checkPassword,
    planetCode: registerUserParam.planetCode,
    gender: registerUserParam.gender,
    avatarUrl: registerUserParam.avatarUrl,
    username: registerUserParam.username,
    phone: registerUserParam.phone,
    tagNameList: activeIds.value,
    longitude: registerUserParam.longitude,
    dimension: registerUserParam.dimension,
  });
  if (userId?.code === 0) {
    showToast('Success');
    router.push({
      path: '/user/login'
    })
  } else {
    showToast('Error');
  }
};
const onClickLeft = () => {
  router.back();
}
</script>

<style scoped>

</style>