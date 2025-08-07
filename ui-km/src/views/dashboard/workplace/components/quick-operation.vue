<template>
  <a-card
    class="general-card"
    :title="$t('workplace.quick.operation')"
    :header-style="{ paddingBottom: '0' }"
    :body-style="{ padding: '10px 20px 10px 20px' }"
  >
    <template #extra>
      <a-link @click="setupStateClick">{{
        setupState
          ? $t('workplace.quickOperation.Close')
          : $t('workplace.quickOperation.setup')
      }}</a-link>
    </template>
    <a-row :gutter="8">
      <a-col v-for="link in links" :key="link.id" :span="8" class="wrapper">
        <div v-if="setupState">
          <a-badge>
            <div class="icon" :style="{ backgroundColor:'rgb(var(--gray-1))' }">
              <component :is="link.icon" />
            </div>
            <a-typography-paragraph class="text">
              {{ link.name }}
            </a-typography-paragraph>
            <template #content>
              <IconMinusCircle
                :style="{ verticalAlign: 'middle', color: '#F76965' }"
              />
            </template>
          </a-badge>
        </div>
        <div v-else>
          <div
            class="icon"
            :style="{ backgroundColor: 'rgb(var(--gray-1))' }"
            @click="generalItemClick(link)"
          >
            <component :is="link.icon" />
          </div>
          <a-typography-paragraph class="text">
            {{ link.name }}
          </a-typography-paragraph>
        </div>
      </a-col>
      <a-col v-if="setupState" :span="8" class="wrapper">
        <a-space size="large">
          <a-dropdown @click="addClick">
            <div class="icon">
              <component :is="'icon-plus'" />
            </div>
            <a-typography-paragraph class="text">
              {{ $t('workplace.add') }}
            </a-typography-paragraph>
            <template #content>
              <a-doption v-for="item in link" :key="item.id"
                ><div class="dropdown-div">
                  <div class="icon" :style="{ color: item.color }">
                    <component :is="item.icon" />
                  </div>
                  <a-typography-paragraph class="text" @click="addWrapper">
                    {{ item.name }}
                  </a-typography-paragraph>
                  <a-button
                    v-if="item.existStatus == 1"
                    type="outline"
                    size="small"
                    style="margin-left: 50px"
                    @click="addAndDeleteClick(item, 'add')"
                    >添加</a-button
                  >
                  <a-button
                    v-else
                    size="mini"
                    status="danger"
                    style="margin-left: 50px"
                    @click="addAndDeleteClick(item, 'delete')"
                    ><icon-delete />移除</a-button
                  >
                </div></a-doption
              >
            </template>
          </a-dropdown>
        </a-space>
      </a-col>
    </a-row>
    <a-modal v-model:visible="visible" @ok="handleOk">
      <template #title> 选择知识库 </template>
      <div>
        <a-select
          v-model="repositorValue"
          :style="{ width: '320px' }"
          placeholder="请选择一个知识库"
        >
          <a-option
            v-for="item in repositoryList"
            :key="item.id"
            :value="item.id"
            >{{ item.name }}</a-option
          >
        </a-select>
      </div>
    </a-modal>
<!--    <a-divider class="split-line" style="margin: 0" />-->
<!--    <a-card-->
<!--      class="general-card"-->
<!--      :title="$t('workplace.recently.visited')"-->
<!--      :header-style="{ paddingBottom: '0', paddingLeft: '0' }"-->
<!--      :body-style="{ padding: '0px', paddingTop: '26px' }"-->
<!--    >-->
<!--      <div style="margin-bottom: -1rem">-->
<!--        <a-row :gutter="8">-->
<!--          <a-col-->
<!--            v-for="link in recentlyVisitedList"-->
<!--            :key="link.name"-->
<!--            :span="8"-->
<!--            class="wrapper"-->
<!--          >-->
<!--            <div-->
<!--              class="icon"-->
<!--              :style="{ backgroundColor: 'rgb(var(&#45;&#45;gray-1))' }"-->
<!--              @click="generalItemClick(link)"-->
<!--            >-->
<!--              <component :is="link.icon" />-->
<!--            </div>-->
<!--            <a-typography-paragraph class="text">-->
<!--              {{ link.name }}-->
<!--            </a-typography-paragraph>-->
<!--          </a-col>-->
<!--        </a-row>-->
<!--      </div>-->
<!--    </a-card>-->
  </a-card>
</template>

<script lang="ts" setup>
  import { IconMinusCircle } from '@arco-design/web-vue/es/icon';
  import { ref, onMounted, reactive } from 'vue';
  import { Message } from '@arco-design/web-vue';
  import { dashboardListAll, addFolderFile } from '@/api/dashboard/index';
  import {
    quickEntranceConfigList,
    userConfigList,
    ListByUser,
    addUserConfig,
    removeUserConfig
  } from '@/api/workbench/index';


  const links = ref<
    {
      id: string;
      sort: number;
      name: string;
      code: string;
      icon: string;
      color: string;
      existStatus: number;
    }[]
  >([]);
  const link = ref<
    {
      id: string;
      sort: number;
      name: string;
      code: string;
      icon: string;
      color: string;
      existStatus: number;
    }[]
  >([]);
  const parameter = reactive({
    code: '',
    name: ''
  });
  const repositoryList = ref<
    {
      id: string;
      name: string;
    }[]
  >([]);
  const repositorValue = ref('');
  const recentlyVisitedList = ref<any[]>([]);
  const allocationId = ref('');
  const setupState = ref(false);
  const visible = ref(false);
  const userConfig = async () => {
    const res = await userConfigList();
    const { userQuickEntranceList } = res.data.comUserQuickEntranceConfigVo;
    const { id } = res.data.comUserQuickEntranceConfigVo;
    links.value = userQuickEntranceList;
    allocationId.value = id;
  };
  const recentlyVisited = () => {
    recentlyVisitedList.value =
      links.value.length > 3 ? links.value.slice(0, 3) : links.value;
  };
  const addAndDeleteClick = async (item: any, type: any) => {
    const params = {
      id: allocationId.value,
      configValueId: item.id
    };
    if (type === 'add') {
      await addUserConfig(params);
    } else {
      await removeUserConfig(params);
    }
    await userConfig();
    await recentlyVisited();
  };

  const generalItemClick = async (param: any) => {
    repositorValue.value = '';
    parameter.name = param.name;
    parameter.code = param.code;
    const res = await dashboardListAll();
    // @ts-ignore
    repositoryList.value = res.rows.filter((item: any) => item.type === 'doc');
    visible.value = true;
  };
  const handleOk = async () => {
    if (repositorValue.value) {
      const timestamp = new Date().getTime();
      const params = {
        fileName: `未命名_${timestamp}`,
        parentId: '0',
        linkKmId: repositorValue.value,
        linkFileTypeCode: parameter.code,
        linkFileTypeName: parameter.name
      };
      // @ts-ignore
      const res = await addFolderFile(params);
      window.open(
        `${localStorage.getItem(
          'spf_file_editors_host'
        )}?server=content&mode=edit&fileId=${res.data}&kmType=doc`,
        '_blank'
      );
    } else {
      Message.error('请选择名称');
    }
  };

  const addClick = async () => {
    const res = await ListByUser();
    link.value = res.data;
  };
  const quickEntranceConfig = () => {
    const res = quickEntranceConfigList();
  };
  onMounted(async () => {
    await userConfig();
    await quickEntranceConfig();
    await recentlyVisited();
  });
  const setupStateClick = () => {
    setupState.value = !setupState.value;
  };
  const addWrapper = () => {
    setupState.value = !setupState.value;
  };
</script>

<style scoped lang="less">
  :deep(.arco-space-horizontal .arco-space-item) {
    display: inline-block;
  }
  .dropdown-div {
    width: 260px;
    display: flex;
    align-items: center;
    padding: 5px;
    .icon {
      margin-right: 10px;
      font-size: 16px;
    }
    .text {
      width: 100px;
      font-size: 12px;
      font-style: normal;
      font-weight: 800;
      line-height: normal;
    }
  }
  .arco-dropdown-option {
    // width: 220px;
    // padding: 6px;
  }
  :deep(div.arco-typography) {
    margin-bottom: 0px;
  }
  :deep(.arco-dropdown-list-wrapper) {
    max-height: 250px;
  }
  :deep(.arco-card-header-title) {
    line-height: inherit;
  }
</style>
