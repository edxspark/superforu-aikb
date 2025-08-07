<template>
  <div class="setting">
    <div v-if="!appStore.menuCollapse">
      <a-comment :author="userInfo.userName">
        <template #content>
          <span key="heart" class="action"> {{ userInfo.userAccount }} </span>
        </template>
        <template #avatar>
          <a-avatar>
            <img alt="avatar" :src="userInfo.picUrl==''?'/assets/logo.png':userInfo.picUrl" />
            <template #trigger-icon>
              <div style="width: 5px; height: 5px; color: red"></div>
            </template>
          </a-avatar>
        </template>
      </a-comment>
    </div>
    <div>
      <IconSettings class="icon" @click="IconMessageClick" />
      <a-modal
        v-model:visible="messageModal"
        title-align="start"
        ok-text="复制访问链接"
        width="auto"
        :footer="false"
        :mask-closable="false"
      >
        <template #title> {{$t('menu.user')}} </template>
        <div>
          <img alt="avatar" width="500" src="/assets/profileBg.jpg" />
          <div class="people-massage">
            <a-avatar :size="72" class="avatar-position" trigger-type="mask">
              <img alt="avatar" :src="userInfo.picUrl" />
              <template #trigger-icon>
                <IconEdit />
              </template>
            </a-avatar>
            <a-space
              direction="vertical"
              class="avatar-position"
              :style="{ width: '100%' }"
            >
              <a-upload
                :action="uploads.url"
                :headers="uploads.headers"
                :onSuccess="handleUploadSuccess"
                :file-list="file ? [file] : []"
                :show-file-list="false"
                @change="onChange"
              >
                <template #upload-button>
                  <div
                    :class="`arco-upload-list-item${
                      file && file.status === 'error'
                        ? ' arco-upload-list-item-error'
                        : ''
                    }`"
                  >
                    <div
                      v-if="shareForm.picUrl"
                      class="arco-upload-list-picture custom-upload-avatar"
                    >
                      <img :src="shareForm.picUrl" />
                      <div class="arco-upload-list-picture-mask">
                        <IconEdit />
                      </div>
                    </div>
                    <div v-else class="arco-upload-picture-card">
                      <div class="arco-upload-picture-card-text">
                        <IconPlus />
                        <div>头像</div>
                      </div>
                    </div>
                  </div>
                </template>
              </a-upload>
            </a-space>
            <div class="name">
              <span class="left">{{ shareForm.name }}#{{ shareForm.account }}</span>
              <span class="right" :style="{color:'rgb(var(--warning-6))'}" @click="vipModelClick"><icon-fire /> {{$t('km.upgrade')}}</span>
            </div>
            <div class="period"><IconFire :style="{color:'rgb(var(--warning-6))'}" /> {{memberLevel}} (有效期: {{memberExpirationDate}}) &nbsp;|&nbsp; <icon-robot :style="{color:'rgb(var(--warning-6))'}"/> AI账户余额: {{aiToken}}</div>
          </div>
          <a-divider class="half-divider" />
          <a-form :model="shareForm">
            <a-form-item field="name" label="姓名:" @change="changeInput">
              <a-input v-model="shareForm.name" placeholder="请输入姓名" />
            </a-form-item>
            <a-form-item field="signature" label="签名:" @change="changeInput">
              <a-input v-model="shareForm.signature" placeholder="请输入签名" />
            </a-form-item>
            <a-form-item field="url" label="邀请:">
              <a-input
                v-model="shareForm.picUrl"
                disabled
              />

              <a-button
                :style="{ marginLeft: '10px' }"
                type="text"
                @click="copyUrlClick"
                >复制邀请链接</a-button>
            </a-form-item>
            <a-form-item style="font-size: 12px;color:#86909c;">
              <div><icon-branch /> 成功邀请双方将获得三个月会员权益！</div>
            </a-form-item>
          </a-form>
          <a-space>
            <a-button type="primary" status="danger" :style="{width:'300px',marginLeft:'100px'}" @click="handleLogout">
              <IconShareInternal></IconShareInternal>退出</a-button>
          </a-space>
        </div>
        <VipModal v-model:isVipModel="vipModel" />
      </a-modal>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { IconEdit, IconPlus } from '@arco-design/web-vue/es/icon';
  import useClipboard from 'vue-clipboard3';
  import { Message, Notification } from '@arco-design/web-vue';
  import { globalHeaders } from '@/api/interceptor';
  import { getUserInfo, editUserInfo } from '@/api/workbench/index';
  import useUser from '@/hooks/user';
  import { useAppStore } from '@/store';
  import VipModal from '@/components/vip-modal/index.vue';

  interface UploadOption {
    /** 设置上传的请求头部 */
    headers: { [key: string]: any };
    /** 上传的地址 */
    url: string;
  }
  const uploads = reactive<UploadOption>({
    headers: globalHeaders(),
    url: `${localStorage.getItem('spf_app_upload_host')}/resource/oss/upload`
  });
  const { logout } = useUser();
  const appStore = useAppStore();
  const vipModel = ref(false);
  const userInfo = JSON.parse(localStorage.getItem('spf_user_info') || '');
  const messageModal = ref(false);
  const shareForm = reactive({
    id: '',
    signature: '',
    name: '',
    account: '',
    password: '',
    picUrl: ''
  });

  const { toClipboard } = useClipboard();
  const handleLogout = () => {
    logout();
  };
  const file = ref();
  const onChange = (_: any, currentFile: any) => {
    file.value = {
      ...currentFile
    };
  };
  const changeInput = async () => {
    const params = {
      id: shareForm.id,
      language: localStorage.getItem('spf_language') || '',
      picUrl: shareForm.picUrl,
      signature: shareForm.signature,
      theme: localStorage.getItem('spf_theme') || '',
      userName: shareForm.name,
      userPackageDetail:''
    };
    await editUserInfo(params);
  };
  /** 图片上传成功返回图片地址 */
  const handleUploadSuccess = async (res: any) => {
    console.log(res);
    if (res.response.code === 200) {
      shareForm.picUrl = res.response.data.url;
      Message.success('上传成功!');
      await changeInput();
    } else {
      Message.error('上传失败!');
    }
  };
  const copyUrlClick = async () => {
    try {
      await toClipboard(`${shareForm.picUrl}`);
      Notification.success({
        title: '复制成功',
        content: '已成功复制链接'
      });
    } catch (e) {
      console.error(e);
    }
  };

  const memberLevel          = ref('普通会员');
  const memberExpirationDate = ref('长期有效');
  const aiToken              = ref('0');

  const IconMessageClick = async () => {
    messageModal.value = true;
    const res: any = await getUserInfo();
    shareForm.id = res.data.id;
    shareForm.name = res.data.userName;
    shareForm.signature = res.data.signature;
    shareForm.account = res.data.userAccount;

    // 邀请连接设置
    const appServer = `${window.location.protocol}//${window.location.host}`;
    shareForm.picUrl = `${appServer}/invite?uid=${shareForm.id}`;

    // 用户权益设置
    res.data.userPackageDetail.forEach((item:any) =>{
      if(item.packageCode === 'memberPackage'){
        memberLevel.value = item.packageName;
        memberExpirationDate.value = item.appValue.substring(0,10);
      }

      if(item.packageCode === 'aiTokenPackage'){
        aiToken.value = `${item.appValue}${item.unit}`;
      }
    });
  };

  const vipModelClick = () => {
    messageModal.value = false;
    vipModel.value = true;
  };
</script>

<style scoped lang="less">
  .setting {
    position: absolute;
    bottom: 10px;
    width: 220px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    .icon {
      width: 20px;
      height: 20px;
      flex-shrink: 0;
      color: var(--color-text-2);
      cursor: pointer;
    }
  }
  .action {
    font-size: 12px;
    color:var(--color-text-2);
  }
  :deep(.arco-avatar-trigger-icon-button) {
    width: 15px;
    height: 15px;
    background-color: #3ba55c;
  }

  .people-massage {
    position: relative;
    padding: 0px;
    .svg {
      width: 100%;
      height: 200px;
    }
    .avatar-position {
      position: absolute;
      left: 10px;
      top: -80px;
    }
    .name {
      margin-top: 50px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .left {
        color: var(--color-text-2);
        font-size: 19.259px;
        font-style: normal;
        font-weight: 800;
        line-height: normal;
      }
      .right{
        color: rgb(var(--primary-6));
        font-size: 12px;
        font-style: normal;
        font-weight: 800;
        cursor: pointer;
        line-height: normal;
      }
    }

    .period {
      color: var(--color-text-2);
      font-size: 12px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
      padding-top: 10px;
    }
  }
  .arco-upload-list-item {
    background: var(--color-fill-3);
    border-radius: 50%;
    margin-top: -6px;
  }
  .arco-upload-picture-card {
    border-radius: 50% !important;
  }
  .arco-upload-list-picture {
    margin-right: 0px;
    margin-bottom: 0px;
    border-radius: 50%;
  }
</style>
