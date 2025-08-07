<template>
  <a-layout class="layout-demo">
    <a-layout-sider breakpoint="xl" class="layout-demo-list">
      <div class="logo">{{ $t('km.menu.my.km') }}</div>
      <a-menu :default-open-keys="['1']" :style="{ width: '100%' }" :default-selected-keys="[queryId]" v-if="queryId"
        class="a-menu-repository" @menu-item-click="menuItemClick">
        <a-menu-item v-for="item in repositoryList" :key="item.id" @click="routerClick(item)">
          <div class="pop-menu">
            <a-badge v-if="item.isAiOpen === 1" :count="9" dot :dotStyle="{ width: '10px', height: '10px' }">
              <a-tag>
                {{ item.type == 'doc' ? $t('km.tag.doc') : $t('km.tag.km') }}
              </a-tag>
            </a-badge>
            <a-tag v-else>
              {{ item.type == 'doc' ? $t('km.tag.doc') : $t('km.tag.km') }}
            </a-tag>

            {{ item.name }}
            <a-space size="large">
              <a-dropdown position="bl" :hide-on-select="false">
                <IconMore></IconMore>
                <template #content>
                  <a-doption v-for="link in links" :key="link.text">
                    <a-popconfirm v-if="link.text === 'workplace.deleterepository'" :content="$t('workplace.deleteIs')"
                      @ok="deleteOk(item)">
                      <div class="dropdown-div">
                        <div class="icon" :style="{ background: link.color }">
                          <component :is="link.icon" />
                        </div>
                        <a-typography-paragraph class="text">
                          {{ $t(link.text) }}
                        </a-typography-paragraph>
                      </div>
                    </a-popconfirm>

                    <div v-else-if="
                      link.text === 'workplace.sharePreview' &&
                      item.type === 'doc'
                    " class="dropdown-div">
                      <div class="icon" :style="{ background: link.color }">
                        <component :is="link.icon" />
                      </div>
                      <a-typography-paragraph class="text" disabled>
                        {{ $t(link.text) }}
                      </a-typography-paragraph>
                    </div>

                    <div v-else class="dropdown-div" @click="handleSelect($t(link.text), item)">
                      <div class="icon" :style="{ background: link.color }">
                        <component :is="link.icon" />
                      </div>
                      <a-typography-paragraph class="text">
                        {{ $t(link.text) }}
                      </a-typography-paragraph>
                    </div>
                  </a-doption>
                </template>
              </a-dropdown>
            </a-space>
          </div>
        </a-menu-item>
        <a-modal v-model:visible="synergyModal" :footer="false" ok-text="保存" width="auto">
          <template #title> 协同管理 </template>
          <div class="synergy-top">
            <a-select v-model="synergyValue" :style="{ width: '320px' }" placeholder="请选择团队">
              <a-option v-for="item in isGroupList" :key="item.id" :value="item.id">{{ item.teamName }}</a-option>
            </a-select>
            <a-button type="primary" style="margin-left: 43px" @click="addGroup()">
              <template #icon> <icon-plus /> </template>添加</a-button>
          </div>
          <a-table :columns="synergyColumns" :data="synergyData" :pagination="pagination">
            <template #ascend="{}">
              <a-button status="danger" size="mini"><icon-delete /> 移除</a-button>
            </template>
          </a-table>
        </a-modal>
        <a-modal v-model:visible="shareModal" title-align="start" :ok-text="ShareState ? '生成访问链接' : '复制访问链接'"
          :cancel-text="ShareState ? '取消' : '取消分享'" width="auto">
          <template #title> 分享预览 </template>
          <div>
            <a-form ref="sharesRef" :rules="shareRules" :model="shareForm" :style="{ width: '600px' }">
              <a-form-item field="name" label="知识库名称" disabled>
                <a-input v-model="shareForm.name" placeholder="请输入知识库名称" />
              </a-form-item>
              <a-form-item field="accessPermission" validate-trigger="blur" label="访问权限">
                <a-select v-model="shareForm.accessPermission" placeholder="全部人可访问/选择协同团队" allow-clear
                  @change="selectChageValue">
                  <a-option value="all">全部人可访问</a-option>
                  <a-option v-for="item in isGroupList" :key="item.id" :value="item.id">{{ item.teamName }}</a-option>
                </a-select>
              </a-form-item>
              <a-form-item field="accessPassword" label="访问密码设置">
                <a-input @input="selectChageValue" v-model="shareForm.accessPassword" placeholder="请输入六位数字密码设置"
                  :max-length="6" />
              </a-form-item>
              <a-form-item field="name" label="访问链接">
                <a-input v-model="shareForm.shareURL" placeholder="" disabled />
              </a-form-item>
            </a-form>
          </div>
          <template #footer>
            <a-popconfirm content="取消分享确认?" @ok="shareCancel" v-if="!ShareState && !visibleSelectState">
              <a-button>{{
                ShareState && visibleSelectState ? '取消' : '取消分享'
                }}</a-button>
            </a-popconfirm>
            <a-button type="primary" @click="shareOk">{{
              ShareState ? '生成访问链接' : '复制访问链接'
              }}</a-button>
          </template>
        </a-modal>
        <div class="new-team" @click="visibleModalClick()">
          <IconPlus></IconPlus>
          {{ $t('km.new.knowledge.base') }}
          <a-modal v-model:visible="visibleModal" title-align="start" ok-text="保存" width="auto" @ok="handleOk">
            <template #title> {{ $t('km.new.knowledge.base') }} </template>
            <div>
              <a-form ref="myForm" :model="form" :rules="addRules" :style="{ width: '600px' }">
                <a-form-item field="type" label="类型" validate-trigger="blur">
                  <a-select v-model="form.type" placeholder="文档库/知识库" allow-clear>
                    <a-option value="doc">文档库</a-option>
                    <a-option value="km">知识库</a-option>
                  </a-select>
                </a-form-item>
                <a-form-item validate-trigger="blur" field="name" label="名称">
                  <a-input v-model="form.name" placeholder="请输入知识库名称" />
                </a-form-item>
                <a-form-item field="isAiOpen" label="是否开启AI">
                  <a-switch type="round" checked-value="1" unchecked-value="0" v-model:model-value="form.isAiOpen">
                    <template #checked> 开启 </template>
                    <template #unchecked> 关闭 </template>
                  </a-switch>
                  <span style="font-size: 12px; margin-left: 10px">
                    (开启后可与知识库对话)</span>
                </a-form-item>
                <a-form-item field="agentType" label="智能体类型">
                  <a-select v-model="form.agentType" style="width: 200px" placeholder="请选择智能体">
                    <a-option v-for="item in agentTypeList" :key="item.dictValue" :value="item.dictValue">{{
                      item.dictLabel
                      }}</a-option>
                  </a-select>
                </a-form-item>
                <a-form-item validate-trigger="blur" field="picUrl" label="封面">
                  <a-upload list-type="picture-card" :action="uploads.url" :file-list="fileList"
                    :headers="uploads.headers" :limit="1" :before-upload="handleBeforeUpload"
                    :onSuccess="handleUploadSuccess" :onRemove="onBeforeRemove" :onError="handleUploadError"
                    image-preview />
                </a-form-item>
                <a-form-item validate-trigger="blur" field="mark" label="描述">
                  <a-textarea v-model="form.mark" placeholder="请输入描述" allow-clear />
                </a-form-item>
              </a-form>
            </div>
          </a-modal>
        </div>
      </a-menu>
      <div class="logo">{{ $t('km.menu.cooperation') }}</div>
      <a-menu :default-open-keys="['1']" :style="{ width: '100%' }" :default-selected-keys="['0']"
        class="a-menu-repository-shary">
        <!-- @menu-item-click="onClickMenuItem" -->
        <a-menu-item v-for="item in shareList" :key="item.id" @click="onClickMenuItem(item)">
          <span class="i-span"> {{ item.name }}</span>
        </a-menu-item>
      </a-menu>

      <!-- trigger -->
      <div class="recycle-bin">
        <div class="recycle-padding">
          <a-menu-item key="">
            <div class="top">
              <span>{{ usedSpace || 0 }} / {{ maxSpace || 0 }}GB</span>
              <a @click="VipModel = true">{{ $t('km.upgrade') }}</a>
            </div>
          </a-menu-item>
          <a-progress :show-text="false" :percent="percent" :color="{
            '0%': '#248046',
            '100%': 'rgb(var(--success-6))'
          }" :style="{ width: '100%' }" />
          <div class="recycle" @click="recycleClick('doc')">♻️&nbsp;{{ $t('km.menu.recycle') }}</div>
        </div>
      </div>
      <VipModal v-model:isVipModel="VipModel" />
      <template #trigger="{ collapsed }">
        <div class="a-menu-icon">
          <IconMenuFold v-if="collapsed"></IconMenuFold>
          <IconMenuFold v-else></IconMenuFold>
        </div>
      </template>
    </a-layout-sider>
    <Detail v-if="isItemType === 'doc'" ref="ChildsDom" :item-name="isItemName" :item-id="isItemId" />
    <Share v-else-if="isItemType === 'share'" :item-name="isItemName" :item-id="isItemId" />
    <Cloud v-else ref="ChildsDom" :item-name="isItemName" :item-id="isItemId" />
  </a-layout>
</template>

<script lang="ts" setup>
import { ref, Ref, reactive, defineComponent, onMounted } from 'vue';
import useClipboard from 'vue-clipboard3';
import { Notification, Message } from '@arco-design/web-vue';
import { IconMenuFold, IconMenuUnfold } from '@arco-design/web-vue/es/icon';
import { useRoute } from 'vue-router';
import {
  dashboardListAll,
  addkmRepository,
  updateKmRepository,
  deletekmRepository,
  teamList,
  searchSynergyList,
  addSynergyList,
  addkmShare,
  getOneByLinkKmId,
  deleteShare, getDictList
} from "@/api/dashboard/index";
import {
  TableColumnData,
  TableData
} from '@arco-design/web-vue/es/table/interface';
import { globalHeaders } from '@/api/interceptor';
import VipModal from '@/components/vip-modal/index.vue';
import Detail from './detail/index.vue';
import Cloud from './detail/cloud.vue';
import Share from './detail/share.vue';

interface UploadOption {
  /** 设置上传的请求头部 */
  headers: { [key: string]: any };
  /** 上传的地址 */
  url: string;
}
const ChildsDom = ref<any>();
const fileList = ref([]);
const uploads = reactive<UploadOption>({
  headers: globalHeaders(),
  url: `${localStorage.getItem(
    'spf_app_upload_host'
  )}/resource/oss/upload`
});
const { toClipboard } = useClipboard();
const text = ref('');
const form = reactive<{
  name?: string | '';
  type?: string | '';
  mark?: string | '';
  picUrl?: string | '';
  isAiOpen?: string | '0';
  agentType?: string | 'COM';
}>({});

const shareForm = reactive({
  id: '',
  name: '',
  accessPermission: '',
  accessPassword: '',
  linkKmId: '',
  shareId: '',
  shareURL: ''
});
const route = useRoute();
const { query } = route;
const VipForm = reactive({
  duration: '',
  payment: '',
  promotion: ''
});
const VipFreeEducationForm = reactive({
  duration: '',
  payment: '',
  promotion: ''
});

const addRules = {
  type: [
    {
      required: true,
      message: '请选择类型'
    }
  ],
  name: [
    {
      required: true,
      message: '请输入知识库名称'
    }
  ],
  isAiOpen: [
    {
      required: true,
      message: '请选择AI状态'
    }
  ],
  agentType: [
    {
      required: true,
      message: '请选择智能体类型'
    }
  ],
  picUrl: [
    {
      required: true,
      message: '请上传封面图片'
    },
    {
      // @ts-ignore
      validator: (value, cb) => {
        if (value !== form.picUrl) {
          cb('两个密码不匹配');
        } else {
          cb();
        }
      }
    }
  ]
};
const shareRules = {
  accessPermission: [
    {
      required: true,
      message: '请选择访问权限'
    }
  ]
};
const percent = ref<number>(0);
const usedSpace = ref('');
const maxSpace = ref('');
const isItemType = ref('doc');
const isItemName = ref('');
const isItemId = ref('');
const queryId = ref('');
const synergyValue = ref('');
const linkKmId: Ref<string> = ref('');
const synergyData = ref<Array<TableData>>([]);
const isGroupList = ref<
  {
    id: string;
    teamName: string;
  }[]
>([]);
const myForm = ref();
const sharesRef = ref();
const synergyModal = ref(false);
const shareModal = ref(false);
const VipModel = ref(false);
const visibleSynergy = ref(false);
const visibleModal = ref(false);
const visibleShare = ref(false);
const ShareState = ref(true);
const visibleSelectState = ref(true);
const agentTypeList = ref([]);
const rowSelection = reactive({
  type: 'checkbox',
  showCheckedAll: true,
  onlyCurrent: false
});
interface ItemType {
  name: string;
  id: string;
  type: string;
  teamName: string;
  isAiOpen: number;
}
const pagination = { pageSize: 5 };
const repositoryList = ref<Array<ItemType>>([]);
const shareList = ref<Array<ItemType>>([]);

const links = [
  {
    text: 'workplace.Edit',
    icon: 'icon-edit',
    color: '#248046'
  },
  {
    text: 'workplace.coordinatedManagement',
    icon: 'icon-user-group',
    color: '#FFD56C'
  },
  {
    text: 'workplace.sharePreview',
    icon: 'icon-share-alt',
    color: '#00AFF4'
  },
  {
    text: 'workplace.deleterepository',
    icon: 'icon-minus-circle',
    color: '#F07D5F'
  }
];
const synergyColumns: TableColumnData[] = [
  {
    title: '名称',
    width: 300,
    dataIndex: 'linkTeamName',
    align: 'center'
  },
  {
    title: '加入时间',
    width: 300,
    dataIndex: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    width: 100,
    slotName: 'ascend',
    dataIndex: 'ascend',
    align: 'center'
  }
];
const recycleClick = (key: any) => {
  isItemType.value = key;
  isItemName.value = '回收站';
};

const routerClick = async (key: any) => {
  percent.value = key.percent;
  usedSpace.value = key.usedSpace;
  maxSpace.value = key.maxSpace;
  isItemType.value = key.type;
  isItemName.value = key.name;
  isItemId.value = key.id;
};
const menuItemClick = async (key: any) => {
  // ChildsDom.value.rootAboutClick();
};
const handleSelect = async (v: any, item: any) => {
  linkKmId.value = item.id;
  const res = await teamList();
  // @ts-ignore
  isGroupList.value = res.rows;
  if (v === '编辑') {
    visibleModal.value = true;
    Object.assign(form, { ...item });
    form.isAiOpen = item.isAiOpen.toString();

    // 封面图片显示
    const file = {
      uid: Date.now().toString(),
      name: 'New Img File',
      status: 'done',
      url: item.picOSSUrl
    };
    fileList.value = [];
    // @ts-ignore
    fileList.value.push(file);
  }
  if (v === '协同管理') {
    synergyValue.value = '';
    synergyModal.value = true;
    const value = await searchSynergyList({ linkKmId: item.id });
    synergyData.value = value.data;
  }
  if (v === '分享预览') {
    const shareData = await getOneByLinkKmId({ linkKmId: item.id });
    const { data } = shareData;
    if (data === null) {
      ShareState.value = true;
      shareForm.accessPermission = '';
      shareForm.accessPassword = '';
      shareForm.shareId = '';
      shareForm.id = '';
      shareForm.shareURL = '';
    } else {
      Object.assign(shareForm, { ...data });
      ShareState.value = false;
    }
    shareForm.name = item.name;
    shareForm.linkKmId = item.id;
    shareModal.value = true;
    visibleSelectState.value = true;
  }
};
const onClickMenuItem = (item: any) => {
  percent.value = item.percent;
  usedSpace.value = item.usedSpace;
  maxSpace.value = item.maxSpace;
  isItemType.value = item.type;
  isItemName.value = item.name;
  isItemId.value = item.id;
  // Message.info({ content: `You select ${item}`, showIcon: true });
};
const listAll = async () => {
  try {
    const res = await dashboardListAll();
    // @ts-ignore
    const { rows } = res;
    repositoryList.value = rows?.filter(
      (item: any) => item.ownerType === 'myself'
    );
    shareList.value = rows?.filter((item: any) => item.ownerType === 'share');
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};
onMounted(async () => {
  await listAll();
  const kmItems = localStorage.getItem('spf-open-km-items');
  if (kmItems) {
    // @ts-ignore
    const items = JSON.parse(kmItems);
    // @ts-ignore
    queryId.value = items.id;
    await onClickMenuItem(items);
    await routerClick(items);
    localStorage.setItem('spf-open-km-items', '');
  } else {
    // 否则，将 repositoryList 的第一个元素的 id 赋值给 queryId
    queryId.value =
      repositoryList.value.length > 0 ? repositoryList.value[0].id : '';
    await routerClick(repositoryList.value[0]);
  }

  // 加载智能体类型
  // @ts-ignore
  const agentTypeResponse = await getDictList("agent_type");
  // @ts-ignore
  agentTypeList.value = agentTypeResponse.data;

});

const handleOk = async () => {
  myForm.value?.validate(async (volid: any) => {
    if (volid === undefined) {
      // @ts-ignore
      if (form.id === undefined) {
        // @ts-ignore
        await addkmRepository(form);
      } else {
        // @ts-ignore
        const updateRes = await updateKmRepository(form);

        // @ts-ignore
        if (updateRes.code === 200) {
          Message.success('更新成功！');
          visibleModal.value = false;
          await listAll();
        } else {
          Message.success('更新失败！');
        }
      }
    } else {
      Message.error('请完善信息');
      visibleModal.value = true;
    }
  });
};
const shareOk = async () => {
  /** 每次生成链接置空id  */
  shareForm.id = '';
  /** 判断是否已经生成过 */
  if (ShareState.value) {
    sharesRef.value?.validate(async (volid: any) => {
      if (volid === undefined) {
        shareModal.value = true;
        const params = { ...shareForm };
        const res = await addkmShare(params);
        Message.success('生成成功');
        // @ts-ignore
        shareForm.shareURL = res.data.shareURL;
        // @ts-ignore
        shareForm.shareId = res.data.shareId;
        // @ts-ignore
        shareForm.id = res.data.id;
        ShareState.value = false;
      } else {
        Message.error('请完善信息');
        shareModal.value = true;
      }
    });
  } else {
    try {
      await toClipboard(
        shareForm.shareURL
          ? shareForm.shareURL
          : 'http://www.superforu.com/shareId=smx34jxjedxjfxejcx'
      );
      Notification.success({
        title: '复制成功',
        content: '已成功复制链接'
      });
    } catch (e) {
      console.error(e);
    }
  }
};
const shareCancel = async () => {
  /** 判断是否已经生成过 */
  if (ShareState.value) {
    ShareState.value = false;
  } else {
    // @ts-ignore
    await deleteShare(shareForm.id);
    Message.success('取消分享成功');
    ShareState.value = true;
  }
  shareModal.value = false;
};
const visibleModalClick = async () => {
  myForm.value.resetFields();
  form.agentType = 'COM';
  visibleModal.value = true;
};

const addGroup = async () => {
  const params = {
    linkKmId: linkKmId.value,
    linkTeamId: synergyValue.value
  };
  if (linkKmId.value === '') {
    Message.error('请选择团队');
    return;
  }
  await addSynergyList(params);
  const value = await searchSynergyList({ linkKmId: linkKmId.value });
  if (value === null || value === undefined) {
    ShareState.value = false;
  }
  synergyData.value = value.data;
};

const deleteOk = async (item: any) => {
  await deletekmRepository(item.id);
  await listAll();
};
const selectChageValue = () => {
  ShareState.value = true;
  visibleSelectState.value = false;
};

/** 图片上传成功返回图片地址 */
const handleUploadSuccess = (res: any) => {
  if (res.response.code === 200) {
    form.picUrl = res.response.data.ossId;
    Message.success('上传成功!');
  } else {
    Message.error('上传失败!');
  }
};
/** 图片上传前拦截 */
const handleBeforeUpload = (file: any) => {
  const type = ['image/jpeg', 'image/jpg', 'image/png', 'image/svg'];
  const isJPG = type.includes(file.type);
  if (!isJPG) {
    Message.error(`图片格式错误!`);
    return false;
  }
  return true;
};

/** 图片失败拦截 */
const handleUploadError = () => {
  Message.error('上传失败!');
};
const onBeforeRemove = () => {
  form.picUrl = '';
};
</script>

<style scoped lang="less">
.a-menu-repository {
  height: 360px;

  /* 隐藏滚动条 */
  ::-webkit-scrollbar {
    width: 6px;
    height: 6px;
    background-color: var(--color-neutral-4);
    border-radius: var(--border-radius-small);
  }

  /* Firefox */
  * {
    scrollbar-width: none;
  }

  *::-webkit-scrollbar {
    width: 0px;
    height: 0px;
  }
}

.a-menu-repository-shary {
  height: 220px;

  /* 隐藏滚动条 */
  ::-webkit-scrollbar {
    width: 6px;
    height: 6px;
    background-color: var(--color-neutral-4);
    border-radius: var(--border-radius-small);
  }

  /* Firefox */
  * {
    scrollbar-width: none;
  }

  *::-webkit-scrollbar {
    width: 0px;
    height: 0px;
  }
}

.a-menu-icon {
  position: absolute;
  right: 12px;
  bottom: 80px;
  display: flex;
  color: var(--color-text-3);
  background-color: var(--color-fill-1);
  padding: 5px;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  border-radius: var(--border-radius-small);
  cursor: pointer;
}

.layout-demo {
  height: 100%;
  background: var(--color-fill-2);
  border: 1px solid var(--color-border);
}

.layout-demo-list {
  border-right: 1px solid var(--color-border);
}

.layout-demo :deep(.arco-layout-sider) .logo {
  color: var(--color-text-2);
  font-size: 13px;
  font-style: normal;
  font-weight: 500;
  padding-bottom: 5px;
  margin: 20px 10px 2px 10px;
  border-bottom: 1px solid rgba(148, 155, 164, 0.3);
  line-height: normal;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.recycle {
  color: var(--color-text-2);
  font-size: 11.5px;
  font-style: normal;
  font-weight: 800;
  height: 35px;
  line-height: 25px;
  text-align: center;
  padding-top: 5px;
  margin-top: 5px;
  cursor: pointer;
  border-top: 1px solid rgba(148, 155, 164, 0.3);
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

.synergy-top {
  display: flex;
  margin-bottom: 24px;
  height: 33px;
  justify-content: center;
}

.recycle-bin {
  position: absolute;
  bottom: 0px;
  width: 100%;

  .recycle-padding {
    padding: 15px 15px 0px 15px;

    .top {
      display: flex;
      justify-content: space-between;
      align-items: center;
      overflow: hidden;

      span {
        color: var(--color-text-2);
        font-size: 10.5px;
        font-style: normal;
        font-weight: 600;
        line-height: normal;
      }

      a {
        color: var(--cta-colors-link, #00aff4);
        font-size: 10.5px;
        font-style: normal;
        font-weight: 600;
        cursor: pointer;
        line-height: normal;
      }
    }
  }
}

.form-btn {
  display: flex;
  width: 177px;
  height: 38px;
  justify-content: center;
  align-items: center;
  gap: 5px;
  flex-shrink: 0;
  margin-top: 23px;
  border-radius: 3px;
  background: var(--cta-colors-blurple-main, #5865f2);
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
  margin-top: 20px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

// /deep/ .arco-form-item-content-wrapper {
//   border-radius: 2px;
//   padding-right: 30px;
//   background: var(--gray-dark-mode-tooltips-and-userprofile, #18191c);
// }
:deep(.arco-layout-sider-has-trigger) {
  padding-bottom: 0px !important;
}
</style>
