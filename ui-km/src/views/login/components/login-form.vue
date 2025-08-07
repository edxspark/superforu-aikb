<template>
  <div class="login-form-wrapper loginForm"
      :style="{
      boxSizing: 'border-box',
      width: '100%',
      padding: '40px',
      backgroundColor: 'var(--color-bg-2)',
    }"
  >
    <div class="login-form-title">{{ $t('login.form.title') }}</div>
    <div class="login-form-sub-title"
      >还没有账号？<span class="register" @click="registerBtn"
        >立注即册</span></div>
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
          :size="'large'"
          v-model="userInfo.phoneNumber"
          :placeholder="$t('login.form.userName.placeholder')"
        >
        </a-input>
      </a-form-item>
      <a-form-item
        :size="'large'"
        label="密码"
        field="password"
        :rules="[{ required: true, message: $t('login.form.password.errMsg') }]"
        :validate-trigger="['change', 'blur']"
      >
        <a-input-password
          v-model="userInfo.password"
          :placeholder="$t('login.form.password.placeholder')"
          allow-clear
          :size="'large'"
        >
        </a-input-password>
      </a-form-item>
      <a-space :size="16" direction="vertical">
        <a-button
          type="primary"
          html-type="submit"
          :loading="loading"
          class="loginBtn"
          :size="'large'"
        >
          {{ $t('login.form.login') }}
        </a-button>
<!--        <a-divider></a-divider>-->
<!--        <div class="otherLogin">-->
<!--          <div class="otherContent">-->
<!--            <div class="otherTitle">其他登录方式</div>-->
<!--            <div class="otherWay">-->
<!--              <a-button  class="otherWayBtn" @click="freeLogin">免密登录</a-button>-->
<!--              <a-button  class="otherWayBtn">微信登录</a-button>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
      </a-space>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineEmits } from 'vue';
  import { useRouter } from 'vue-router';
  import { Message } from '@arco-design/web-vue';
  import { ValidatedError } from '@arco-design/web-vue/es/form/interface';
  import { useI18n } from 'vue-i18n';

  import { useUserStore } from '@/store';
  import useLoading from '@/hooks/loading';
  import type { LoginData } from '@/api/user';

  const router = useRouter();

  const emit = defineEmits(['updateComponent']);
  const registerBtn = () => {
   emit('updateComponent','register')
  };

  const freeLogin = () => {
      emit('updateComponent','loginNoPassword');
  };
  const { t } = useI18n();
  const errorMessage = ref('');
  const { loading, setLoading } = useLoading();
  const userStore = useUserStore();

  const userInfo = reactive({
    phoneNumber: '',
    password: '',
    grantType: 'password'
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
          name: (redirect as string) || 'workplace',
          query: {
            ...othersQuery,
          },
        });
        Message.success(t('login.form.login.success'));
        // 实际生产环境需要进行加密存储。
      } catch (err) {
        errorMessage.value = (err as Error).message;
      } finally {
        setLoading(false);
      }
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
</style>
