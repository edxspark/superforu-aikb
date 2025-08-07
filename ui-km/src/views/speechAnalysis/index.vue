<template>
  <div class="speech-analysis">
    <!-- 搜索表单 -->
    <a-form :model="form" layout="inline" style="padding: 20px;">
      <a-form-item label="设备ID">
        <a-input v-model="form.deviceId" placeholder="请输入设备ID" />
      </a-form-item>
      <a-form-item label="日期">
        <a-date-picker v-model="form.analysisTime" placeholder="请选择日期" style="width: 380px;" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="handleSearch">查询</a-button>
        <a-button @click="handleReset" style="margin-left: 10px;">重置</a-button>
      </a-form-item>
    </a-form>

    <a-table :columns="columns" :data="data" row-key="id">
    </a-table>
  </div>
</template>

<script lang="ts" setup>

import { ref, reactive, onMounted } from 'vue';
import { analysisRecordList } from '@/api/speechAnalysis/index';

// 搜索表单数据
const form = reactive({
  deviceId: '',
  analysisTime: null
});


// 表格数据
const data = ref([
]);

// 表格列定义
const columns = [
  { title: '设备ID', dataIndex: 'deviceId', width: 150 },
  { title: '分析时间', dataIndex: 'analysisTime', width: 200 },
  { title: '语音违规敏感词', dataIndex: 'sensitiveWords', width: 200 },
  { title: '拨号开始时间', dataIndex: 'callTime', width: 180 },
  { title: '通话时长（秒）', dataIndex: 'callTimeLength', width: 150 },
  { title: '使用人姓名', dataIndex: 'userName', width: 150 },
  { title: '本机号码', dataIndex: 'localPhoneNumber', width: 150 },
  { title: '电话状态', dataIndex: 'phoneStatus', width: 150 },
  { title: '电话号码', dataIndex: 'phoneNumber', width: 150 },
  { title: '人员姓名', dataIndex: 'phoneName', width: 150 },
];
const pagination = ref({ pageSize: 10, current: 1, total: 0 });
const onLoad = async (params?: any) => {
  const parames = {
    ...params,
    pageSize: pagination.value.pageSize,
    pageNum: pagination.value.current,
    orderByColumn: 'isFolder,updateTime',
    isAsc: '',
  };
  // // @ts-ignore
  const res = await analysisRecordList(parames);
  //  loading.value = false;
  // @ts-ignore
  data.value = res.rows;
  // @ts-ignore
  data.value.total = res.total;
};
onMounted(() => {
  onLoad();
});

// 提交搜索表单
const handleSearch = () => {
  const parames = {
    deviceId: form?.deviceId,
    analysisTime: form?.analysisTime
  }
  onLoad(parames)
};

// 重置表单
const handleReset = () => {
  form.deviceId = '';
  form.analysisTime = null;
  onLoad()
};
</script>

<style scoped>
.speech-analysis {
  padding: 10px;
  background-color: var(--color-bg-2);
}

/* 自定义样式 */
</style>
