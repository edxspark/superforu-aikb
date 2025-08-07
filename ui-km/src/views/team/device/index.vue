<template>
  <a-layout class="layout-demo">
    <a-layout-sider collapsible breakpoint="xl">
      <div class="logo">我的团队</div>
      <!-- :default-selected-keys="[repositoryList[0].name]" -->
      <a-menu
        :default-open-keys="['1']"
        :default-selected-keys="[queryId]"
        v-if="queryId"
        :style="{ width: '100%' }"
      >
        <a-menu-item
          v-for="item in repositoryList"
          :key="item.id"
          @click="routerClick(item)"
        >
          <div class="pop-menu">
            {{ item.teamName }}
            <a-space size="large">
              <a-dropdown position="bl" :hide-on-select="false">
                <IconMore></IconMore>
                <template #content>
                  <a-doption>
                    <div class="dropdown-div" @click="editForm(item)">
                      <div class="icon" :style="{ background: '#248046' }">
                        <component :is="'icon-edit'" />
                      </div>
                      <a-typography-paragraph class="text">
                        {{ $t('workplace.Edit') }}
                      </a-typography-paragraph>
                    </div>
                  </a-doption>
                  <a-doption>
                    <a-popconfirm
                      :content="$t('workplace.dissolveIs')"
                      @ok="deleteOk(item)"
                    >
                      <div class="dropdown-div">
                        <div class="icon" :style="{ background: '#ED4245' }">
                          <component :is="'icon-minus-circle'" />
                        </div>
                        <a-typography-paragraph class="text">
                          {{ $t('workplace.ReleaseResources') }}
                        </a-typography-paragraph>
                      </div>
                    </a-popconfirm>
                  </a-doption>
                </template>
              </a-dropdown>
            </a-space>
          </div>
        </a-menu-item>
        <div key="新建团队" class="new-team" @click="visibleModalClick">
          <IconPlus></IconPlus>
          新建团队
          <a-modal
            v-model:visible="visibleModal"
            title-align="start"
            ok-text="保存"
            width="auto"
            @ok="handleOk"
          >
            <template #title> {{ visibleModalTitle }} </template>
            <div>
              <a-form
                ref="myForm"
                :rules="addRules"
                :model="form"
                :style="{ width: '600px' }"
              >
                <a-form-item
                  field="teamName"
                  tooltip="团队名称"
                  label="团队名称"
                >
                  <a-input
                    v-model="form.teamName"
                    placeholder="请输入团队名称"
                  />
                </a-form-item>
                <a-form-item field="teamDesc" label="描述">
                  <a-textarea
                    v-model="form.teamDesc"
                    placeholder="请输入描述"
                    allow-clear
                  />
                </a-form-item>
                <a-form-item> </a-form-item>
              </a-form>
            </div>
          </a-modal>
        </div>
      </a-menu>
      <!-- trigger -->
      <template #trigger="{ collapsed }">
        <IconCaretRight v-if="collapsed"></IconCaretRight>
        <IconCaretLeft v-else></IconCaretLeft>
      </template>
    </a-layout-sider>
    <Detail :team-mate-list="teamMateList" :data="stuData" />
  </a-layout>
</template>

<script lang="ts">
  import { Message } from '@arco-design/web-vue';
  import { ref, reactive, defineComponent, onMounted } from 'vue';
  import {
    teamList,
    addSynergyList,
    deleteTeamItem,
    editTeam,
    // quitTeam,
    teamMatePageList
    // addTeamMate,
  } from '@/api/teamwork/index';
  import { IconCaretRight, IconCaretLeft } from '@arco-design/web-vue/es/icon';
  // import spfFileServer from '@/config/website';
  import Detail from './detail/index.vue';

  export default defineComponent({
    components: {
      IconCaretRight,
      IconCaretLeft,
      Detail
    },
    setup() {
      const queryId = ref('');
      const text = ref('');
      const myForm = ref();
      const visibleModalTitle = ref('');
      const form = reactive({
        teamName: '',
        picUrl: '',
        teamDesc: '',
        id: ''
      });
      const addRules = {
        teamName: [
          {
            required: true,
            message: '请输入团队名称'
          }
        ]
      };
      const teamMateList = ref<{
        id: string;
        picUrl: string;
        roleType: number;
        teamDesc: string;
        teamName?: string | undefined;
      }>({ id: '', picUrl: '', roleType: 0, teamDesc: '', teamName: '' });
      const visibleModal = ref(false);
      const rowSelection = reactive({
        type: 'checkbox',
        showCheckedAll: true,
        onlyCurrent: false
      });
      const pagination = { pageSize: 10 };
      const repositoryList = ref<
        {
          id: string;
          name: string;
          teamName: string;
        }[]
      >([]);
      interface MyItem {
        key: string;
        name: string;
        salary: string;
        address: string;
        email: string;
        AiState: string;
        time: string;
      }
      const link = [
        {
          text: 'workplace.Edit',
          icon: 'icon-edit',
          color: '#248046'
        },

        {
          text: 'workplace.ReleaseResources',
          icon: 'icon-minus-circle',
          color: '#ED4245'
        }
      ];

      const stuData = ref<MyItem[]>([]);
      const routerClick = async (item: any) => {
        teamMateList.value = item;
        const params = {
          teamId: item.id,
          pageSize: 10,
          pageNum: 1,
          orderByColumn: '',
          isAsc: ''
        };
        const res = await teamMatePageList(params);
        // @ts-ignore
        stuData.value = res.rows;
      };
      const visibleModalClick = async () => {
        visibleModalTitle.value = '新建团队';
        myForm.value.resetFields();
        visibleModal.value = true;
      };

      onMounted(async () => {
        const res = await teamList();
        // @ts-ignore
        repositoryList.value = res.rows;
        queryId.value =
          // @ts-ignore
          repositoryList.value.length > 0 ? repositoryList.value[0].id : '';
        routerClick(repositoryList.value[0]);
      });

      const handleOk = () => {
        myForm.value?.validate(async (volid: any) => {
          if (volid === undefined) {
            switch (visibleModalTitle.value) {
              case '新建团队':
                await addSynergyList(form);
                break;
              default:
                await editTeam(form);
                break;
            }

            await teamList().then(function (res) {
              visibleModal.value = false;
              // @ts-ignore
              repositoryList.value = res.rows;
            });
          } else {
            Message.error('请完善信息');
            visibleModal.value = true;
          }
        });
      };
      const deleteOk = async (item: any) => {
        await deleteTeamItem({ id: item.id });
        const res = await teamList();
        // @ts-ignore
        repositoryList.value = res.rows;
      };
      const editForm = (item: any) => {
        visibleModalTitle.value = '编辑团队';
        visibleModal.value = true;
        Object.assign(form, { ...item });
      };

      return {
        text,
        queryId,
        rowSelection,
        visibleModal,
        visibleModalTitle,
        stuData,
        link,
        form,
        repositoryList,
        teamMateList,
        pagination,
        myForm,
        handleOk,
        addRules,
        deleteOk,
        visibleModalClick,
        routerClick,
        editForm
      };
    }
  });
</script>

<style scoped lang="less">
  .layout-demo {
    height: 100%;
    background: var(--color-fill-2);
    border: 1px solid var(--color-border);
  }
  .layout-demo :deep(.arco-layout-sider) .logo {
    color: var(--color-text-2);
    font-size: 14px;
    font-style: normal;
    font-weight: 400;
    padding-bottom: 12px;
    margin: 24px 10px 6px 10px;
    border-bottom: 1px solid rgba(148, 155, 164, 0.3);
    line-height: normal;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
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
  .i-span {
    font-size: 12px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }
  .pop-menu {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  :deep(.arco-space-horizontal .arco-space-item) {
    display: inline-block;
  }
  .dropdown-div {
    width: 170px;
    display: flex;
    align-items: center;
    // border-bottom: 1px solid rgb(229, 230, 235);
    padding: 8px;
    .icon {
      margin-right: 10px;
      width: 16px;
      height: 16px;
      border-radius: 6px;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 3px;
    }
    .text {
      width: 100px;
      font-size: 11.5px;
      font-style: normal;
      font-weight: 800;
      line-height: normal;
    }
  }
  :deep(div.arco-typography) {
    margin-bottom: 0px;
  }
  :deep(.arco-dropdown-list-wrapper) {
    max-height: 250px;
  }
  .new-team {
    border-radius: 3px;
    background: var(--cta-colors-green, #248046);
    width: 80%;
    line-height: 30px;
    margin-left: 10%;
    color: #ffffff;
    font-size: 10.555px;
    font-style: normal;
    font-weight: 800;
    text-align: center;
    cursor: pointer;
    margin-top: 43px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
</style>
