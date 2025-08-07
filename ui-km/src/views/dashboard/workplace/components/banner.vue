<template>
  <a-col class="banner">
    <a-col :span="8">
      <a-typography-title :heading="5" style="margin-top: 0">
        <div class="repository-title"> {{ $t('menu.repository') }}</div>
      </a-typography-title>
    </a-col>
  </a-col>
</template>

<script lang="ts" setup>

</script>

<style scoped lang="less">
  .banner {
    width: 100%;
    padding: 20px 20px 0 20px;
    background-color: var(--color-bg-2);
    border-radius: 4px 4px 0 0;
  }

  :deep(.arco-icon-home) {
    margin-right: 6px;
  }
  .repository-title {
    color: var(--gray-dark-mode-text, --color-border-2);
    font-size: 18px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }
</style>
