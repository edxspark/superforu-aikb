<template>
  <div class="login-form-wrapper loginForm"
      :style="{
      boxSizing: 'border-box',
      width: '100%',
      padding: '40px',
      backgroundColor: 'var(--color-bg-2)',
    }">
    <div class="login-form-title">{{ $t('login.form.register') }}</div>
    <div class="login-form-sub-title">已有账号？<span class="register" @click="loginBtn">返回登录</span></div>
    <a-form
      ref="loginForm"
      :style="{ width: '380px', marginTop: '20px' }"
      :model="userInfo"
      class="login-form"
      @submit="handleSubmit"
    >
      <a-form-item
        label="账号"
        field="phoneNumber"
        :rules="[{ required: true, message: $t('login.form.userName.errMsg') }]"
        :validate-trigger="['change', 'blur']"
      >
        <a-input
          v-model="userInfo.phoneNumber"
          :size="'large'"
          :placeholder="$t('login.form.userName.placeholder')"
        >
        </a-input>
      </a-form-item>

      <a-form-item
          label="验证码"
          field="smsCode"
          :rules="[{ required: true, message: $t('login.form.msgCode.errMsg') }]"
          :validate-trigger="['change', 'blur']"
      >
        <a-input
            :size="'large'"
            v-model="userInfo.smsCode"
            :placeholder="'请输入验证码'"
        >
          <template #append>
            <span class="MsgCode" @click="msgCodeFunc">{{
                countDown === '发送验证码' ? countDown : countDown + 's'
              }}</span>
          </template>
        </a-input>
      </a-form-item>

      <a-form-item
        label="密码"
        field="password"
        :rules="[{ required: true, message: $t('login.form.password.errMsg') }]"
        :validate-trigger="['change', 'blur']"
      >
        <a-input-password
          v-model="userInfo.password"
          :size="'large'"
          :placeholder="$t('login.form.password.placeholder')"
          allow-clear
        >
        </a-input-password>
      </a-form-item>

      <a-button
        type="primary"
        html-type="submit"
        :loading="loading"
        class="loginBtn"
        :size="'large'"
      >
        {{ $t('register.form.register') }}
      </a-button>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive, defineEmits} from 'vue';
  import { useRouter } from 'vue-router';
  import { Message } from '@arco-design/web-vue';
  import { ValidatedError } from '@arco-design/web-vue/es/form/interface';
  import { useStorage } from '@vueuse/core';
  import useLoading from '@/hooks/loading';
  import { register, sendSmsCode } from '@/api/user';
  import type { LoginData } from '@/api/user';
  import { setToken } from '@/utils/auth';

  const router = useRouter();
  const errorMessage = ref('');
  const { loading, setLoading } = useLoading();
  const countDown = ref<any>('发送验证码');

  const emit = defineEmits(['updateComponent']);
  const loginBtn = () => {
    emit('updateComponent','login')
  };
  const loginConfig = useStorage('login-config', {
    rememberPassword: true,
    phoneNumber: '',
    password: '',
    smsCode: '',
    linkInviterId: '',
  });
  const userInfo = reactive({
    phoneNumber: '',
    password: '',
    smsCode: '',
    linkInviterId: '',
  });

  const urlParams = new URLSearchParams(window.location.search);
  userInfo.linkInviterId = localStorage.getItem("spf-invite-uid");

  const handleSubmit = async ({
    errors,
    values,
  }: {
    errors: Record<string, ValidatedError> | undefined;
    values: Record<string, any>;
  }) => {
    if (loading.value) return;
    if (!errors) {
      setLoading(true);
      try {

        // @ts-ignore
        const res = await register(values as LoginData);
        const token = `Bearer ${res.data.access_token}`;
        setToken(token);
        Message.success('注册成功');
        router.push({
          name: 'dashboard',
        });
      } catch (err) {
        errorMessage.value = (err as Error).message;
      } finally {
        setLoading(false);
      }
    }
  };
  const countDownFunc = () => {
    if (countDown.value === '0' || countDown.value === '发送验证码') {
      countDown.value = '发送验证码';
    } else {
      countDown.value = String(Number(countDown.value) - 1);
      setTimeout(() => {
        countDownFunc();
      }, 1000);
    }
  };
  const msgCodeFunc = async () => {
    if (countDown.value !== '发送验证码') {
      return;
    }
    const data = {
      phoneNumber: userInfo.phoneNumber,
      smsType: 'register',
    };
    setLoading(true);
    try {
      await sendSmsCode(data);
      // Message.success('发送成功');
    } catch (err) {
      errorMessage.value = (err as Error).message;
    } finally {
      setLoading(false);
      countDown.value = '60';
      countDownFunc();
    }
  };

</script>

<style lang="less" scoped>

  .login-form {
    &-wrapper {
      width: 320px;
    }

    &-title {
      color: var(--color-text-1);
      font-weight: 500;
      font-size: 24px;
      height: 24px;
      margin-bottom: 20px;
    }

    &-sub-title {
      color: var(--color-text-1);
      font-size: 16px;
      line-height: 24px;
      margin-bottom: 25px;
    }

    &-error-msg {
      height: 32px;
      color: rgb(var(--red-6));
      line-height: 32px;
    }

    &-password-actions {
      display: flex;
      justify-content: space-between;
    }

    &-register-btn {
      color: var(--color-text-3) !important;
    }
  }
</style>

<style lang="less">
  .loginForm {
    border: var(--color-border-2) solid 1px;
    border-radius: 5px;
    .register {
      color: rgb(var(--primary-6));;
      cursor: pointer;
    }
    .MsgCode {
      float: right;
      width: 80px;
      font-size: 14px;
    }
    .MsgCode:hover{
      cursor: default;
    }

    .arco-form-item-label-col > .arco-form-item-label {
      font-size: 16px;
    }
    .arco-form-item-layout-vertical > .arco-form-item-label-col {
      margin-bottom: 10px;
    }
    .loginInput {
      height: 50px;
      border: 1px solid #5865f2;
      font-size: 18px;
      line-height: 50px;
      border-radius: 8px;
      background-color: var(--color-bg-1);
      color: var(--color-text-2);
      .arco-input{
        font-size: 16px;
      }
    }
    .arco-input-wrapper .arco-input-suffix {
      color: rgb(var(--primary-6));;
    }


    .loginBtn {
      margin-top: 10px;
      margin-right: 50px;
      margin-left: 100px;
      width: 200px;
    }
    .otherLogin {
      margin-top: -10px;
      width: 100%;
    }
    .otherContent {
      width: 200px;
      margin: 0 auto;
      text-align: center;
      .otherTitle {
        width: 190px;
        height: 10px;
        line-height: 10px;
        border-radius: 4px;
        color: var(--color-text-1);
        margin-bottom: 10px;
      }
      .otherWay {
        display: flex;
        justify-content: space-between;
        height: 24px;
        align-items: center;
        margin-top:20px;
        .otherWayBtn {
          width: 78px;
          height: 24px;
          line-height: 24px;
          cursor: pointer;
          font-size: 12px;
        }
      }
      .prevWXbtn {
        height: 24px;
        line-height: 24px;
        font-size: 12px;
        color: var(--color-text-1);
      }
    }
  }
  .inputMsgCode {
    position: relative;

    .MsgCode {
      position: absolute;
      top: 0px;
      right: 20px;
      font-size: 12px;
      color: var(--color-text-1);
      cursor: pointer;
    }
  }
</style>
