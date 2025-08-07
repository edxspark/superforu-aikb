<template>
  <a-layout>
    <a-layout style="padding: 0 24px">
      <a-breadcrumb :style="{ margin: '16px 0' }">
        <a-breadcrumb-item>团队管理</a-breadcrumb-item>
        <a-breadcrumb-item>{{ teamMateList?.teamName }}</a-breadcrumb-item>
      </a-breadcrumb>
      <a-layout-content>
        <a-space class="space-btn">
          <a-select
            v-model="selectValue"
            :style="{ width: '200px' }"
            placeholder="查看者/编辑者"
          >
            <a-option :value="0">查看者</a-option>
            <a-option :value="1">编辑者</a-option>
          </a-select>
          <a-input
            v-model="inputSearchValue"
            :style="{ width: '320px' }"
            placeholder="请输入完整账号，点击【添加】"
            allow-clear
          />
          <a-button
            type="primary"
            class="primary-btn"
            @click="addTeamMateClick"
          >
            <template #icon>
              <icon-plus />
            </template>
            添加
          </a-button>
<!--          <div class="people">企业用户(85/200人)</div>-->
        </a-space>
        <a-space direction="vertical" size="large" fill>
          <a-table
            v-model:selectedKeys="selectedKeys"
            row-key="name"
            :columns="columnsList"
            :data="data"
            :pagination="pagination"
          >
            <template #roleType="{ record }">
              <a-select
                v-model="record.roleType"
                :style="{ width: '200px' }"
                disabled
              >
                <a-option :value="0">查看者</a-option>
                <a-option :value="1">编辑者</a-option>
                <a-option :value="2">管理员</a-option>
              </a-select>
            </template>

            <template #operation="{ record }">
              <a-popconfirm
                :content="$t('workplace.deleteIs')"
                @ok="deleteTeamitem(record)"
              >
                <a-button size="mini" status="danger"> <icon-delete />移除</a-button>
              </a-popconfirm>
            </template>
            <template #salary="{}"> - </template>
          </a-table>
        </a-space>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
  import { ref, PropType, defineProps } from 'vue';
  import { addTeamMate, deleteTeamMate } from '@/api/teamwork/index';
  import { TableColumnData } from '@arco-design/web-vue/es/table/interface';
  import {Message} from "@arco-design/web-vue";

  const props = defineProps({
    teamMateList: {
      type: Object as PropType<{
        id: string;
        picUrl: string;
        roleType: number;
        teamDesc: string;
        teamName?: string;
      }>,
    },
    data: {
      type: Array as PropType<
        {
          key: string;
          name: string;
          salary: string;
          address: string;
          email: string;
          AiState: string;
          time: string;
        }[]
      >,
      required: true,
    },
  });
  const inputSearchValue = ref('');
  const selectValue = ref(0);
  const selectedKeys = ref(['Jane Doe', 'Alisa Ross']);
  const pagination = { pageSize: 5 };
  const columnsList: TableColumnData[] = [
    {
      title: '名称',
      width: 100,
      dataIndex: 'userName',
      slotName: 'name',
      align: 'center',
    },
    {
      title: '权限',
      width: 100,
      dataIndex: 'roleType',
      slotName: 'roleType',
      align: 'center',
    },
    {
      title: '账号',
      width: 100,
      dataIndex: 'userAccount',
      align: 'center',
    },
    {
      title: '加入时间',
      width: 120,
      dataIndex: 'createTime',
      align: 'center',
    },
    {
      title: '操作',
      width: 100,
      slotName: 'operation',
      dataIndex: 'operation',
      align: 'center',
    },
  ];
  const addTeamMateClick = async () => {
    const params = {
      linkTeamId: props.teamMateList?.id,
      linkUserId: inputSearchValue.value,
      roleType: selectValue.value,
    };

    if(params.linkTeamId === ""){
      Message.warning({
        content: '请在左侧选择一个团队',
        duration: 3 * 1000,
      });
    }else{
      // @ts-ignore
      await addTeamMate(params);
    }

  };
  const deleteTeamitem = async (record: any) => {
    const params = {
      teamId: props.teamMateList?.id,
      teamMateId: record.id,
    };
    // @ts-ignore
    await deleteTeamMate(params);
  };
</script>

<style scoped lang="less">
  .space-btn {
    padding: 20px;
    display: flex;
    justify-content: start;
    .people {
      color: var(--color-text-2);
      font-size: 12px;
      font-style: normal;
      font-weight: 800;
      line-height: 50px;
    }
  }
  .layout-demo {
    height: 100%;
    background: var(--color-fill-2);
    border: 1px solid var(--color-border);
  }
  .layout-demo :deep(.arco-layout-sider) .logo {
    color: var(--gray-dark-mode-icons, #b9bbbe);
    font-size: 18px;
    font-style: normal;
    font-weight: 800;
    padding-bottom: 24px;
    margin: 24px 10px 6px 10px;
    border-bottom: 1px solid rgba(148, 155, 164, 0.3);
    line-height: normal;
  }
  .layout-demo :deep(.arco-layout-header) {
    height: 64px;
    line-height: 64px;
    background: var(--color-bg-3);
  }
  .layout-demo :deep(.arco-layout-footer) {
    height: 48px;
    color: var(--color-text-2);
    font-weight: 400;
    font-size: 14px;
    line-height: 48px;
  }
  .layout-demo :deep(.arco-layout-content) {
    color: var(--color-text-2);
    font-weight: 400;
    font-size: 14px;
    background: var(--color-bg-3);
  }
  .layout-demo :deep(.arco-layout-footer),
  .layout-demo :deep(.arco-layout-content) {
    padding: 10px;
    display: flex;
    flex-direction: column;
    color: var(--color-white);
    font-size: 16px;
    font-stretch: condensed;
    text-align: center;
  }

  // .primary-btn {
  //   background: #5865f2;
  // }
</style>
