<template>
  <div class="tabs">
    <div class="tabs-nav">
      <div v-for="(tab, index) in tabs" :key="index" :class="{ 'active': activeIndex === index }"
        @click="activeIndex = index">
        {{ tab.title }}
      </div>
    </div>
    <div class="tabs-content">
      <component :is="currentTabComponent" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import faceCollection from './components/faceCollection.vue';
import recognitionTest from './components/recognitionTest.vue';

interface TabType {
  title: string;
  component: any;
}

const tabs: TabType[] = [
  { title: '人脸收集', component: faceCollection },
  { title: '识别测试', component: recognitionTest },
];
const activeIndex = ref(0);
const currentTabComponent = computed(() => {
  return tabs[activeIndex.value].component;
});

</script>

<style lang="less" scoped>
.tabs {
  display: flex;
  height: 100%;
}

.tabs-nav {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  height: 100%;
  background: var(--color-bg-3);
  padding: 0 20px;
  border-right: 1px solid rgba(148, 155, 164, 0.3)
}

.tabs-nav div {
  cursor: pointer;
  line-height: 50px;
  width: 200px;
  height: 50px;
  border-radius: 5px;
  color: var(--color-text-1);
  background-color: var(--color-fill-3);
  text-align: center;
  margin-top: 50px;
}

.tabs-nav div.active {
  background-color: rgb(var(--primary-6));

}

.tabs-content {
  width: 100%;
  background: var(--color-bg-3);
}
</style>
