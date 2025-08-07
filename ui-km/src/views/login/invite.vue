<template>
  <div class="container">
    邀请链接
  </div>
</template>

<script lang="ts" setup>
  import { useRouter } from 'vue-router';

  const router = useRouter();

  const urlParams = new URLSearchParams(window.location.search);
  const uid = urlParams.get("uid");
  localStorage.setItem("spf-invite-uid",uid);

  const goRegisterPage = () => {
    router.push({
      path: '/login',
      query: {"action":'register'}
    });
  };
  goRegisterPage();
</script>

<style lang="less" scoped>

</style>
