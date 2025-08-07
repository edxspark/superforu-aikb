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
    <div class="login-form-title">{{ $t('retrievePassword.form.title') }}</div>
    <div class="login-form-sub-title"
      >已有账号？<span class="register" @click="loginBtn">返回登录</span></div
    >
    <a-form
      ref="loginForm"
      :model="userInfo"
      class="login-form"
      @submit="handleSubmit"
    >
      <a-form-item
        label="账号"
        field="username"
        :rules="[{ required: true, message: $t('login.form.userName.errMsg') }]"
        :validate-trigger="['change', 'blur']"
      >
        <a-input
          v-model="userInfo.username"
          :size="'large'"
          :placeholder="$t('login.form.userName.placeholder')"
        >
        </a-input>
      </a-form-item>
      <a-form-item
          label="验证码"
          field="msgCode"
          :rules="[{ required: true, message: $t('login.form.msgCode.errMsg') }]"
          :validate-trigger="['change', 'blur']"
      >
        <a-input
            v-model="userInfo.msgCode"
            :size="'large'"
            :placeholder="$t('login.form.msgCode.placeholder')"
        >
          <template #append> <span class="MsgCode" @click="msgCodeFunc">{{
              countDown === '发送验证码' ? countDown : countDown + 's'
            }}</span> </template>
        </a-input>
      </a-form-item>
      <a-form-item
        label="新密码"
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
        {{ $t('retrievePassword.form.retrievePassword') }}
      </a-button>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { useRouter } from 'vue-router';
  import { Message } from '@arco-design/web-vue';
  import { ValidatedError } from '@arco-design/web-vue/es/form/interface';
  import { useI18n } from 'vue-i18n';
  import { useStorage } from '@vueuse/core';
  import { useUserStore } from '@/store';
  import useLoading from '@/hooks/loading';
  import type { LoginData } from '@/api/user';
  import {defineEmits} from "vue/dist/vue";
  import {sendSmsCode} from "@/api/user";

  const router = useRouter();
  const { t } = useI18n();
  const errorMessage = ref('');
  const { loading, setLoading } = useLoading();
  const userStore = useUserStore();
  const emit = defineEmits(['updateComponent']);
  const loginBtn = () => {
    emit('updateComponent','login');
  };
  const loginConfig = useStorage('login-config', {
    rememberPassword: true,
    username: '',
    password: '',
    msgCode: '',
  });
  const userInfo = reactive({
    username: loginConfig.value.username,
    password: loginConfig.value.password,
    msgCode: '',
  });

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
          name: (redirect as string) || 'Workplace',
          query: {
            ...othersQuery,
          },
        });
        Message.success(t('login.form.login.success'));
        const { rememberPassword } = loginConfig.value;
        const { username, password } = values;
        // 实际生产环境需要进行加密存储。
        loginConfig.value.username = rememberPassword ? username : '';
        loginConfig.value.password = rememberPassword ? password : '';
      } catch (err) {
        errorMessage.value = (err as Error).message;
      } finally {
        setLoading(false);
      }
    }
  };
  // const setRememberPassword = (value: boolean) => {
  //   loginConfig.value.rememberPassword = value;
  // };

  const countDown = ref<any>('发送验证码');

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
      phoneNumber: userInfo.username,
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
      color: rgb(var(--primary-6));;
      cursor: pointer;
    }
    .MsgCode {
      float: right;
      font-size: 14px;
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
      margin-top: 20px;
      margin-left: 100px;
      width: 200px;
    }
    .otherLogin {
      margin-top: 15px;
      width: 100%;
    }
    .otherContent {
      width: 200px;
      margin: 0 auto;
      text-align: center;
      .otherTitle {
        width: 190px;
        height: 30px;
        line-height: 30px;
        border-radius: 4px;
        color: var(--color-text-1);
        margin-bottom: 15px;
      }
      .otherWay {
        display: flex;
        justify-content: space-between;
        height: 43px;
        align-items: center;
        margin-bottom: 25px;
        .otherWayBtn {
          width: 78px;
          height: 43px;
          line-height: 43px;
          background-color: var(--color-bg-3);
          border-radius: 4px;
          color: var(--color-text-1);
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
