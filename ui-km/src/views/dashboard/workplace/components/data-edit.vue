<template>
  <a-col class="banner">
    <a-col :span="8">
      <a-typography-title :heading="5" style="margin-top: 0">
        <div class="recentEdit-tltle"> {{ $t('workplace.recentEdit') }}</div>
      </a-typography-title>
    </a-col>
    <a-row class="grid-demo" :gutter="[24, 12]">
      <a-empty v-if="list.length === 0" class="a-empty" />
      <a-table
          :columns="columns"
          :data="list"
          @cell-click="editClick"
          :pagination="false"
      >

      </a-table>
    </a-row>
  </a-col>
</template>

<script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import { fileRecentlyList } from '@/api/dashboard/index';

  const pagination = ref({ pageSize: 7, current: 1 });

  const columns: any[] = [
    {
      title:"名称",
      dataIndex: 'fileName',
      slotName: 'fileName',
      align: 'left',
      width: 300,
      ellipsis: true
    },
    {
      title:"类型",
      width: 100,
      dataIndex: 'fileType',
      align: 'center'
    },
    {
      title:"所有者",
      dataIndex: 'createBy',
      align: 'center'
    },
    {
      title:"修改人",
      dataIndex: 'updateBy',
      align: 'center'
    },
    {
      title:"修改时间",
      width: 160,
      dataIndex: 'editorTime',
      align: 'left'
    }
  ];

  const list = ref<
    {
      id: string;
      createTime: string;
      editorTime: string;
      fileAbstract: string;
      fileName: string;
      fileType: string;
      linkFileId: string;
      linkUserId: string;
    }[]
  >([]);


  onMounted(async () => {
    const params = {
      pageSize: pagination.value.pageSize,
      pageNum: pagination.value.current,
      orderByColumn: 'updateTime',
      isAsc: 'desc'
    };
    const res = await fileRecentlyList(params);
    // @ts-ignore
    const { rows } = res;
    list.value = rows;
  });
  const editClick = (link: any) => {
    window.open(
      `${localStorage.getItem(
        'spf_file_editors_host'
      )}?server=content&mode=edit&fileId=${link.linkFileId}&kmType=doc`,
      '_blank'
    );
  };
</script>

<style scoped lang="less">
  .banner {
    width: 100%;
    padding: 20px;
    background-color: var(--color-bg-2);
    border-radius: 4px 4px 0 0;
  }
  .extra {
    font-size: 11px;
  }
  :deep(.arco-icon-home) {
    margin-right: 6px;
  }

  .fileCard:hover{
    background-color: var(--color-fill-2);
  }

  .file-edit-time {
    font-size: 12px;
    width: 120px;
    line-height: normal;
  }

  .file-edit-type {
    font-size: 12px;
    padding-left: 15px;
  }

  .ul-edit {
    color: #73767d;
    font-size: 8px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
    padding: 0px 8px;
  }
  :deep(.arco-card-body) {
    padding: 8px 8px;
  }
  :deep(.arco-card-header) {
    border: none;
  }
  :deep(.arco-card-bordered) {
    border-radius: 12px;
  }
  .recentEdit-tltle {
    color: var(--gray-dark-mode-text, --color-border-2);
    font-size: 18px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }
  :deep(.arco-card-header-title) {
    font-size: 14px;
  }
  :deep(.arco-card-bordered) {
    border: 1px solid var(--color-neutral-3) !important;
  }
</style>
