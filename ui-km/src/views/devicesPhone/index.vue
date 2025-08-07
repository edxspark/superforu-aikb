<template>
  <div class="speech-analysis">
    <!-- 搜索表单 -->
    <a-form :model="searchForm" @submit="handleSearch" layout="inline" style="padding: 20px;">
      <a-form-item label="设备ID">
        <a-input v-model="searchForm.deviceId" placeholder="请输入设备ID" />
      </a-form-item>
      <a-form-item label="设备名称">
        <a-input v-model="searchForm.deviceName" placeholder="请输入设备名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">查询</a-button>
        <a-button @click="handleReset" style="margin-left: 10px;">重置</a-button>
      </a-form-item>
    </a-form>
    <a-modal v-model:visible="visibleModal" title-align="start" ok-text="保存" width="auto" @ok="handleOk">
      <template #title> 编辑 </template>
      <div>
        <a-form ref="myForm" :rules="rules" :model="form" :style="{ width: '600px' }">
          <a-form-item label="ID" field="id">
            <a-input v-model="form.id" placeholder="请输入ID" />
          </a-form-item>
          <a-form-item label="设备ID" field="deviceId">
            <a-input v-model="form.deviceId" placeholder="请输入设备ID" />
          </a-form-item>
          <a-form-item label="设备名称" field="deviceName">
            <a-input v-model="form.deviceName" placeholder="请输入设备名称" />
          </a-form-item>
          <a-form-item label="设备类型" field="deviceType">
            <a-select v-model="form.deviceType" style="width: 200px" placeholder="请选择设备类型">
              <a-option value="phone">phone</a-option>
            </a-select>
          </a-form-item>
          <a-form-item label="人脸识别">
            <a-switch type="round" checked-value="open" unchecked-value="close" v-model:model-value="form.faceSwitch">
              <template #checked>
                开启
              </template>
              <template #unchecked>
                关闭
              </template>
            </a-switch>
            <span style="font-size: 12px;margin-left: 10px;"> (是否开启人脸识别功能)</span>
          </a-form-item>
          <a-form-item label="语音识别">
            <a-switch type="round" checked-value="open" unchecked-value="close" v-model:model-value="form.voiceSwitch">
              <template #checked>
                开启
              </template>
              <template #unchecked>
                关闭
              </template>
            </a-switch>
            <span style="font-size: 12px;margin-left: 10px;"> (是否开启语音识别功能)</span>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
    <!-- 表格 -->
    <a-button type="primary" style="margin-bottom: 10px;" @click=" faceAddClick()">
      <template #icon>
        <icon-plus />
      </template>
      <div>新增</div>
    </a-button>
    <a-table :columns="columns" :data="data" row-key="id">
      <template #ascend="{ record }">
        <a-button type="primary" size="mini" style="margin-right: 10px;" @click="editRecord(record)"><icon-edit />
          编辑</a-button>

      </template>
    </a-table>
  </div>
</template>

<script lang="ts" setup>
import { Message } from '@arco-design/web-vue';
import { ref, onMounted, reactive } from 'vue';
import { devicesList, devicesAdd, devicesUpdate } from '@/api/speechAnalysis/index';

// 搜索表单数据

const searchForm = reactive({
  deviceId: "",
  deviceName: "",
})
const form = reactive({
  id: "",
  deviceId: "",
  deviceName: "",
  deviceType: "",
  faceSwitch: "",
  voiceSwitch: "",
});
const myForm = ref();
// 表单验证规则
const rules = ref({
  id: [{ required: true, message: '请输入ID' }],
  deviceId: [{ required: true, message: '请输入设备ID' }],
  sensitiveWords: [{ required: true, message: '请输入敏感词' }],
  deviceName: [{ required: true, message: '请输入设备名称' }],
  deviceType: [{ required: true, message: '请选择设备类型' }],
});

const isEdit = ref(false);
// 表格数据
const data = ref([]);
interface TableColumnData {
  title: string;
  dataIndex: string;
  slotName?: string;
  align?: "center" | "left" | "right";
}
// 表格列定义
const columns: TableColumnData[] = [
  { title: 'ID', dataIndex: 'id' },
  { title: '设备ID', dataIndex: 'deviceId' },
  { title: '设备名称', dataIndex: 'deviceName' },
  { title: '设备类型', dataIndex: 'deviceType' },
  { title: '人脸识别功能', dataIndex: 'faceSwitch' },
  { title: '语音识别功能', dataIndex: 'voiceSwitch' },
  {
    title: '操作',
    slotName: 'ascend',
    dataIndex: 'ascend',
    align: 'center'
  }
];
const visibleModal = ref(false);
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
  const res = await devicesList(parames);
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
    deviceId: searchForm?.deviceId,
    deviceName: searchForm?.deviceName
  }
  onLoad(parames)
};

// 重置表单
const handleReset = () => {
  searchForm.deviceId = '';
  searchForm.deviceName = '';

  onLoad()
};

const editRecord = async (record: any) => {
  form.id = record.id
  form.deviceId = record.deviceId
  form.deviceName = record.deviceName
  form.deviceType = record.deviceType
  form.faceSwitch = record.faceSwitch
  form.voiceSwitch = record.voiceSwitch
  isEdit.value = true;
  visibleModal.value = true
};
const handleOk = async () => {
  myForm.value?.validate(async (valid: any) => {
    if (valid === undefined) {
      if (isEdit.value) {
        // 编辑时调用更新接口
        const parames = {
          id: form.id,
          deviceId: form.deviceId,
          deviceName: form.deviceName,
          deviceType: form.deviceType,
          faceSwitch: form.faceSwitch,
          voiceSwitch: form.voiceSwitch,
        };
        await devicesUpdate(parames);
        Message.success('编辑成功');
      } else {
        // 新增时调用添加接口
        await devicesAdd(form);
        Message.success('新增成功');
      }

      visibleModal.value = false;
      await onLoad();
    } else {
      Message.error('请完善信息');
      visibleModal.value = true;
    }
  });
}
const faceAddClick = () => {
  form.id = '';
  form.deviceId = '';
  form.deviceName = '';
  form.deviceType = '';
  form.faceSwitch = '';
  form.voiceSwitch = '';
  isEdit.value = false;
  visibleModal.value = true;
};
</script>

<style scoped>
.speech-analysis {
  padding: 10px;
  background-color: var(--color-bg-2);
}

/* 自定义样式 */
</style>
