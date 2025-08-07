<template class="aiFrame">
  <div>
    <iframe width="100%" :height="height" frameborder="0" :src="src"></iframe>
  </div>
</template>

<script lang="ts" setup>
  import { ref, watchEffect } from 'vue';
  import { useRoute } from 'vue-router';

  const route = useRoute();
  const { query } = route;
  const height = ref(700);

  const src = ref('');
  watchEffect(() => {
    if (query.url) {
      src.value = `${query.url}`;
    }
    height.value = window.innerHeight - 51;
  });
</script>

<style scoped lang="less">
  .aiFrame {
    border-top: #1d2129 1px solid;
    margin-top: 10px;
  }
</style>
