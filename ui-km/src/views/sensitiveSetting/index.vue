<template>
  <div class="speech-analysis">
    <!-- 搜索表单 -->
    <a-form :model="form" :rules="rules" @submit="handleSearch" layout="inline" style="padding: 20px;">
      <a-form-item label="敏感词">
        <a-input v-model="form.sensitiveWords" placeholder="请输入敏感词" />
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
          <a-form-item field="sensitiveWords" tooltip="敏感词" label="敏感词">
            <a-input v-model="form.sensitiveWords" placeholder="请输入敏感词" />
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
        <a-popconfirm :content="$t('workplace.deleteIs')" @ok="deleteTeamitem(record)">
          <a-button status="danger" size="mini"><icon-delete /> 移除</a-button>
        </a-popconfirm>

      </template>
    </a-table>
  </div>
</template>

<script lang="ts" setup>
import { Message } from '@arco-design/web-vue';
import { ref, computed, onMounted, reactive } from 'vue';
import { sensitiveWordsSettingAdd, sensitiveWordsSettingList, sensitiveWordsSettingUpdate, deletesensitiveWordsSettingId } from '@/api/speechAnalysis/index';

// 搜索表单数据
const form = reactive({
  sensitiveWords: ''
});
const myForm = ref();
// 表单验证规则
const rules = ref({
  sensitiveWords: [{ required: true, message: '请输入敏感词' }],
});
const isEdit = ref(false);
// 表格数据
const data = ref([]);
// 表格列定义
const columns = [
  { title: 'ID', dataIndex: 'id', width: 400 },
  { title: '敏感词', dataIndex: 'sensitiveWords', width: 300,},
  {
    title: '操作',
    slotName: 'ascend',

    dataIndex: 'ascend',
  }
];
const visibleModal = ref(false);
const pagination = ref({ pageSize: 10, current: 1, total: 0 });
const editRecordId = ref('');
const onLoad = async (params?: any) => {
  const parames = {
    ...params,
    pageSize: pagination.value.pageSize,
    pageNum: pagination.value.current,
    orderByColumn: 'isFolder,updateTime',
    isAsc: '',
  };
  // // @ts-ignore
  const res = await sensitiveWordsSettingList(parames);
  //  loading.value = false;
  // @ts-ignore
  data.value = res?.rows;
  // @ts-ignore
  data.value.total = res.total;
};
onMounted(() => {
  onLoad();
});

// 提交搜索表单
const handleSearch = () => {

  const parames = {
    sensitiveWords: form?.sensitiveWords,
  }
  onLoad(parames)
};

// 重置表单
const handleReset = () => {
  form.sensitiveWords = '';

  onLoad()
};

const editRecord = async (record: any) => {
  editRecordId.value = record.id
  form.sensitiveWords = record.sensitiveWords
  isEdit.value = true;
  visibleModal.value = true
};
const deleteTeamitem = async (record: any) => {
  await deletesensitiveWordsSettingId(record.id);
  Message.success('删除成功');
  await onLoad();
};
const handleOk = async () => {
  myForm.value?.validate(async (valid: any) => {
    if (valid === undefined) {
      if (isEdit.value) {
        // 编辑时调用更新接口
        const parames = {
          id: editRecordId.value,
          sensitiveWords: form.sensitiveWords
        };
        await sensitiveWordsSettingUpdate(parames);
        Message.success('编辑成功');
      } else {
        // 新增时调用添加接口
        await sensitiveWordsSettingAdd(form);
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
  form.sensitiveWords = ''
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
