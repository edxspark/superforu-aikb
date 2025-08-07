<template>
  <iframe
      scrolling="yes"
      frameborder="0"
      width="100%"
      height="100%"
      :src="AI_APP_URL"
  >

  </iframe>
</template>

<script lang="ts" setup>
  const token = localStorage.getItem("spf_token").replace("Bearer ","");
  const clientId = localStorage.getItem("spf_client_id");
  const userId = JSON.parse(localStorage.getItem("spf_user_info")).id;
  const theme = localStorage.getItem("arco-theme");
  let lang = localStorage.getItem("arco-locale");
  if(lang === null || lang === "null"){
    lang = localStorage.getItem("spf_language");
  }


  const aiSearchAppURL = import.meta.env.VITE_AI_SEARCH_URL;
  const url = `${aiSearchAppURL}?userId=${userId}&theme=${theme}&clientId=${clientId}&token=${token}&lang=${lang}`;
  const AI_APP_URL = url;
</script>

<style scoped lang="less">

</style>
