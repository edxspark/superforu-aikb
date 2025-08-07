<template>
  <div class="login-form-wrapper loginForm"
       :style="{
      boxSizing: 'border-box',
      width: '100%',
      padding: '40px',
      margin:'10px',
      backgroundColor: 'var(--color-bg-2)',
    }"
  >
    <div class="login-form-title">{{ $t('login.form.title') }}</div>
    <div class="login-form-sub-title"
      >还没有账号？<span class="register" @click="registerBtn"
        >立注即册</span
      ></div
    >
    <a-form
      ref="loginForm"
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
        :rules="[{ required: true, message: $t('login.form.password.errMsg') }]"
        :validate-trigger="['change', 'blur']"
      >
        <a-input
          :size="'large'"
          v-model="userInfo.smsCode"
          :placeholder="'请输入验证码'"
          allow-clear
        >
          <template #append>
            <span class="MsgCode" @click="msgCodeFunc">{{
                countDown === '发送验证码' ? countDown : countDown + 's'
              }}</span>
          </template>
        </a-input>
      </a-form-item>
      <a-space :size="16" direction="vertical">
        <div class="login-form-password-actions">
          <div></div>
          <a-link @click="forgetPassword">{{
            $t('login.form.forgetPassword')
          }}</a-link>
        </div>
        <a-button
          type="primary"
          html-type="submit"
          :loading="loading"
          class="loginBtn"
          :size="'large'"
        >
          {{ $t('login.form.login') }}
        </a-button>
        <a-divider></a-divider>
        <div class="otherLogin">
          <div class="otherContent">
            <div class="otherTitle">其他登录方式</div>
            <div class="otherWay">
              <a-button class="otherWayBtn" @click="passwordLogin">密码登录</a-button>
              <a-button class="otherWayBtn">微信登录</a-button>
            </div>
          </div>
        </div>
      </a-space>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive, defineEmits} from 'vue';
  import { useRouter } from 'vue-router';
  import { Message } from '@arco-design/web-vue';
  import { ValidatedError } from '@arco-design/web-vue/es/form/interface';
  import { useI18n } from 'vue-i18n';
  import { useStorage } from '@vueuse/core';
  import { useUserStore } from '@/store';
  import { sendSmsCode } from '@/api/user';
  import useLoading from '@/hooks/loading';
  import type { LoginData } from '@/api/user';

  const router = useRouter();
const emit = defineEmits(['updateComponent']);
  const registerBtn = () => {
    emit('updateComponent','register');
  };
  const forgetPassword = () => {
    emit('updateComponent','forgetPassword');
  };
  const passwordLogin = () => {
    emit('updateComponent','login');
  };
  const { t } = useI18n();
  const errorMessage = ref('');
  const { loading, setLoading } = useLoading();
  const userStore = useUserStore();

  const loginConfig = useStorage('login-config', {
    rememberPassword: true,
    phoneNumber: '',
    smsCode: '',
    grantType: 'sms',
  });
  const userInfo = reactive({
    phoneNumber: loginConfig.value.phoneNumber,
    smsCode: '',
    grantType: 'sms'
  });
  const countDown = ref<any>('发送验证码');
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
        await userStore.login(values as LoginData);
        const { redirect, ...othersQuery } = router.currentRoute.value.query;
        router.push({
          name: (redirect as string) || 'workplace',
          query: {
            ...othersQuery,
          },
        });
        Message.success(t('login.form.login.success'));
        const { rememberPassword } = loginConfig.value;
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
      smsType: 'login',
    };
    setLoading(true);
    try {
      await sendSmsCode(data);
      Message.success('发送成功');
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
      width: 400px;
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
      color: rgb(var(--primary-6));
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
