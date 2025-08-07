<template>
  <a-layout class="container">
    <a-layout-header>
      <img
        src="/assets/logo.svg"
        class="loginLogo"
        alt=""
        @click="goHomePage"
      />
    </a-layout-header>
    <a-layout>
      <a-layout-content>
        <a-row>
          <a-col :span="24">
            <div class="content">
              <div class="content-inner">
                <LoginForm @updateComponent="visiableComponent" v-if="loginFormVisiable"/>
                <LoginPasswordForm @updateComponent="visiableComponent" v-if="loginPasswordFormVisiable"/>
                <ForgetPasswordForm @updateComponent="visiableComponent" v-if="forgetPasswordVisiable"/>
                <RegisterForm @updateComponent="visiableComponent" v-if="registerFormVisiable"></RegisterForm>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
  import { useRouter } from 'vue-router';
  import {ref} from "vue";
  import LoginForm from './components/login-form.vue';
  import LoginPasswordForm from './components/loginin-form.vue';
  import ForgetPasswordForm from './components/retrievePassword-form.vue';
  import RegisterForm from './components/register-form.vue';

  const loginFormVisiable         = ref(true);
  const loginPasswordFormVisiable = ref(false);
  const forgetPasswordVisiable     = ref(false);
  const registerFormVisiable      = ref(false);

  const router = useRouter();
  const goHomePage = () => {
    router.push({
      path: '/home',
    });
  };

  const urlParams = new URLSearchParams(window.location.search);
  const action = urlParams.get("action");
  if(action === 'register'){
    loginFormVisiable.value         = false;
    forgetPasswordVisiable.value    = false;
    loginPasswordFormVisiable.value = false;
    registerFormVisiable.value      = true;
  }

  const visiableComponent = (componentCode: string) =>{

    // init
    loginFormVisiable.value         = false;
    loginPasswordFormVisiable.value = false;
    forgetPasswordVisiable.value    = false;
    registerFormVisiable.value      = false;

    if(componentCode==="login"){
      loginFormVisiable.value = true;
    }else if(componentCode === "loginNoPassword"){
      loginPasswordFormVisiable.value = true;
    }else if(componentCode === "forgetPassword"){
      forgetPasswordVisiable.value = true;
    }else if(componentCode === "register"){
      registerFormVisiable.value = true;
    }else{
      loginFormVisiable.value = true;
    }

  }



</script>

<style lang="less" scoped>

  .container {
    display: flex;
    height: 100vh;
    background-color: var(--color-bg-2);
    background-image: url('/assets/loginBg.svg');
    background-repeat: no-repeat;
    background-position-y: 400px;
    background-position-x: 900px;

    .content {
      position: relative;
      display: flex;
      flex: 1;
      padding-top: 150px;
      justify-content: center;
      padding-bottom: 40px;
    }

    .footer {
      position: absolute;
      right: 0;
      bottom: 0;
      width: 100%;
    }
  }

  .loginLogo {
    height: 42px;
    position: absolute;
    top: 2vh;
    left: 2vw;
    z-index: 1000;
  }
</style>

<style lang="less" scoped>
  // responsive
  @media (max-width: @screen-lg) {
    .container {
      .banner {
        width: 25%;
      }
    }
  }
</style>
