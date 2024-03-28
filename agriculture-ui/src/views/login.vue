<template>

   <div class="container container-0">
    <div class="container-content">
      <div class="container-content-inner">
        <img class="container-content-inner-logo" src="@/assets/images/logo.png" />
    <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="container-content-inner-form">
      <el-form-item prop="username">
        <el-input  class="container-content-inner-form-item"
          v-model="loginForm.username"
          type="primary"
          link
          size="large"
          auto-complete="off"
          placeholder="账号"
        >
          <!-- <template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template> -->
        </el-input>
      </el-form-item>
      <el-form-item prop="password" class="container-content-inner-form-item">
        <el-input  class="container-content-inner-form-item"
          v-model="loginForm.password"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter="handleLogin"
        >
          <!-- <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template> -->
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
        class="container-content-inner-form-btns"
          :loading="loading"
          size="large"
          type="primary"
          style="width:100%;"
          @click.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <!-- <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div> -->
      </el-form-item>
    </el-form>
   </div>
   </div>
   </div>

</template>

<script setup>
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import useUserStore from '@/store/modules/user'
const userStore = useUserStore()
const route = useRoute();
const router = useRouter();
const { proxy } = getCurrentInstance();

const loginForm = ref({
  username: "",
  password: "",
  rememberMe: false,
  code: "",
  uuid: ""
});

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }]
};

const codeUrl = ref("");
const loading = ref(false);
// 验证码开关
const captchaEnabled = ref(false);
// 注册开关
const register = ref(false);
const redirect = ref(undefined);

watch(route, (newRoute) => {
    redirect.value = newRoute.query && newRoute.query.redirect;
}, { immediate: true });

function handleLogin() {
  proxy.$refs.loginRef.validate(valid => {
    if (valid) {
      loading.value = true;
      // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
      if (loginForm.value.rememberMe) {
        Cookies.set("username", loginForm.value.username, { expires: 30 });
        Cookies.set("password", encrypt(loginForm.value.password), { expires: 30 });
        Cookies.set("rememberMe", loginForm.value.rememberMe, { expires: 30 });
      } else {
        // 否则移除
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove("rememberMe");
      }
      // 调用action的登录方法
      userStore.login(loginForm.value).then((res) => {
        const query = route.query;
        const otherQueryParams = Object.keys(query).reduce((acc, cur) => {
          if (cur !== "redirect") {
            acc[cur] = query[cur];
          }
          return acc;
        }, {});
        router.push({ path: redirect.value || "/", query: otherQueryParams });
      }).catch((err) => {
        console.log(err)
        loading.value = false;
      });
    }
  });
}
function getCookie() {
  const username = Cookies.get("username");
  const password = Cookies.get("password");
  const rememberMe = Cookies.get("rememberMe");
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password: password === undefined ? loginForm.value.password : decrypt(password),
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
  };
}

getCookie();
</script>

<style lang='scss' scoped>
@import "@/assets/styles/login.scss";
</style>
