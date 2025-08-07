<template>
  <div class="recognition-test">
    <div class="upload">
      <a-upload draggable action="/" style="width: 50%;" tip="只能上传.jpg/.jpeg/.png，最大上传5M" />
    </div>
    <div class="result">识别结果</div>
  </div>
</template>

<script setup lang="ts">
</script>
<style lang="less" scoped>
.recognition-test {
  padding: 10px;

}

.upload {
  padding: 10px;
  display: flex;
  justify-content: center;
  border-bottom: 1px solid rgba(148, 155, 164, 0.3)
}

.result {
  text-align: center;
  color: var(--color-text-2);
  font-size: 25px;
  padding: 10px;
}
</style>
