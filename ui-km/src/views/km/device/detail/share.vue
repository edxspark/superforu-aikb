<template>
  <div class="share-div"
    ><iframe
      :src="dynamicSrc"
      width="100%"
      height="100%"
      frameborder="0"
    ></iframe
  ></div>
</template>

<script lang="ts" setup>
  import { ref, watchEffect } from 'vue';

  const dynamicSrc = ref('');
  const props = defineProps({
    itemName: { type: String, required: true },
    itemId: { type: String, required: true },
  });
  watchEffect(() => {
    watchEffect(() => {
      if (props.itemId) {
        dynamicSrc.value = `${localStorage.getItem(
          'spf_file_editors_host'
        )}?server=content&mode=edit&fileId=${props.itemId}&kmType=doc`;
      }
    });
  });
</script>

<style scoped lang="less">
  .share-div {
    width: 100%;
    height: 1005;
  }
</style>
