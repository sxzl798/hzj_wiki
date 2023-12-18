<template>
  <a-layout-header class="header">
    <a-row>
      <a-col :span="23">
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px'}"
    >
          <a-menu-item key="/">
            <router-link to="/">首页</router-link>
          </a-menu-item>
          <a-menu-item key="/admin/user">
            <router-link to="/admin/user">用户管理</router-link>
          </a-menu-item>
          <a-menu-item key="/admin/ebook">
            <router-link to="/admin/ebook">电子书管理</router-link>
          </a-menu-item>
          <a-menu-item key="/admin/category">
            <router-link to="/admin/category">分类管理</router-link>
          </a-menu-item>
          <a-menu-item key="/about">
            <router-link to="/about">关于我们</router-link>
          </a-menu-item>
    </a-menu>
      </a-col>
    <a-col :span="1">
      <a>
        <a-avatar style="color: #f56a00; background-color: #fde3cf" @click="showLoginModal">U</a-avatar>
      </a>
    </a-col>
    </a-row>
  </a-layout-header>

  <a-modal v-model:open="loginOpen" title="用户登录"
           :confirm-loading="loginConfirmLoading"
           @ok="loginHandleOk">

    <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{span: 16}">

      <a-form-item label="用户名">
        <a-input v-model:value="loginUser.loginName"/>
      </a-form-item>
      <a-form-item label="密码">
        <a-input-password v-model:value="loginUser.password" type="password" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts" setup>
import {ref} from "vue";

const loginUser = ref({
  loginName: "test",
  password: "test"
});
const loginOpen = ref<boolean>(false);
const loginConfirmLoading = ref<boolean>(false);
const showLoginModal = () =>{
  loginOpen.value = true;
}

const loginHandleOk = () => {
  console.log("开始登录");
};
</script>

<style>
.login-menu{
  float: right !important;
}
</style>