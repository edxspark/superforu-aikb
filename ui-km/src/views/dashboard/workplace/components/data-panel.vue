<template>
  <a-carousel :style="{
    width: 'width: 100%',
    height: '200px'
  }" indicator-type="never">
    <a-carousel-item v-for="(link, index) in list" :key="index">
      <a-row class="grid-demo" justify="space-around">
        <a-col v-for="item in link" :key="item.name" :span="6" @click="handleClick(item)">
          <div style="margin: 2px 20px 10px 20px">
            <a-card hoverable class="km-card" :style="{ width: '100%' }">
              <template #cover>
                <div :class="{ firstChildImg: item.name == '创建一个知识库' }" class="item-list" :style="{
                  height: '120px',
                  overflow: 'hidden'
                }">
                  <img :style="{
                    width: '100%',
                    height: '120px'
                  }" alt="dessert" :src="getThumbnail(item.picOSSUrl)" />
                </div>
              </template>
              <a-card-meta title="">
                <template #description>
                  <div class="label-title">{{ item.name }} </div>
                  <div class="label-content"> {{ item.mark }} </div>
                </template>
              </a-card-meta>
            </a-card>
          </div>
        </a-col>
      </a-row>
    </a-carousel-item>
  </a-carousel>
  <a-modal v-model:visible="visibleModal" title-align="start" ok-text="保存" width="auto" @ok="handleOk">
    <template #title> {{ $t('km.new.knowledge.base') }}</template>
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
            <template #checked>
              开启
            </template>
            <template #unchecked>
              关闭
            </template>

          </a-switch> <span style="font-size: 12px;margin-left: 10px;"> (开启后【知识助手】可与知识库对话)</span>
        </a-form-item>
        <a-form-item validate-trigger="blur" field="picUrl" label="封面">
          <a-upload list-type="picture-card" :action="uploads.url" :file-list="fileList" :headers="uploads.headers"
            :limit="1" :before-upload="handleBeforeUpload" :onSuccess="handleUploadSuccess" :onRemove="onBeforeRemove"
            :onError="handleUploadError" image-preview />
        </a-form-item>
        <a-form-item validate-trigger="blur" field="mark" label="描述">
          <a-textarea v-model="form.mark" placeholder="请输入描述" allow-clear />
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { globalHeaders } from '@/api/interceptor';
import { Message } from '@arco-design/web-vue';
import { dashboardListAll, addkmRepository } from '@/api/dashboard/index';

interface UploadOption {
  /** 设置上传的请求头部 */
  headers: { [key: string]: any };
  /** 上传的地址 */
  url: string;
}
const router = useRouter();
const myForm = ref();
interface LinkItem {
  id: string;
  alert: string;
  name: string;
  picUrl: string;
  picOSSUrl: string;
  mark: string;
}
const list = ref<LinkItem[][]>([]);
const fileList = ref([]);
const uploads = reactive<UploadOption>({
  headers: globalHeaders(),
  url: `${localStorage.getItem('spf_app_upload_host')}/resource/oss/upload`
});
const form = reactive<{
  name?: string | '';
  type?: string | '';
  mark?: string | '';
  picUrl?: string | '';
  isAiOpen?: string | '';
}>({});
const visibleModal = ref(false);
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
  picUrl: [
    {
      required: true,
      message: '请上传封面图片'
    },
    {
      validator: (value: any, cb: any) => {
        if (value !== form.picUrl) {
          cb('two passwords do not match');
        } else {
          cb();
        }
      }
    }
  ]
};

const splitArrayIntoChunks = (array: any[], chunkSize: number): any[][] => {
  const result: any[][] = [];
  for (let i = 0; i < array.length; i += chunkSize) {
    result.push(array.slice(i, i + chunkSize));
  }
  return result;
};
const panelList = async () => {
  const params = {
    picOSSUrl: '/assets/km/addKm.svg',
    name: '创建一个知识库',
    mark: '创建一个知识库',
    alert: ''
  };
  const res = await dashboardListAll();
  // @ts-ignore
  const combinedArray = [params, ...res.rows];
  // @ts-ignore
  list.value = splitArrayIntoChunks(combinedArray, 4);
};
onMounted(async () => {
  panelList();
  form.isAiOpen = "1";
  form.type = "doc";
});
const handleClick = (item: any) => {
  if (item.name === '创建一个知识库') {
    myForm.value.resetFields();
    visibleModal.value = true;
  } else {

    localStorage.setItem("spf-open-km-items", JSON.stringify(item));
    router.push({
      path: '/km/km-workspace'
    });

  }
};

const getThumbnail = (imageURL): string => {
  console.log("===");
  console.log(imageURL);
  const thumbnail = imageURL?.concat("?x-oss-process=style/thumbnail");
  console.log(thumbnail);
  return thumbnail;
}

const handleOk = async () => {
  myForm.value?.validate(async (volid: any) => {
    if (volid === undefined) {
      // @ts-ignore
      await addkmRepository(form);
      Message.success('新建成功');
      visibleModal.value = false;
      await panelList();
    } else {
      Message.error('请完善信息');
      visibleModal.value = true;
    }
  });
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
const handleUploadError = (err: any) => {
  Message.error('上传失败!');
};
const onBeforeRemove = () => {
  form.picUrl = '';
};
</script>

<style lang="less" scoped>
.arco-grid.panel {
  margin-bottom: 0;
  padding: 16px 20px 0 20px;
}

.panel-col {
  padding-left: 43px;
  border-right: 1px solid rgb(var(--gray-2));
}

.col-avatar {
  margin-right: 12px;
  background-color: var(--color-fill-2);
}

.up-icon {
  color: rgb(var(--red-6));
}

.unit {
  margin-left: 8px;
  color: rgb(var(--gray-8));
  font-size: 12px;
}

:deep(.panel-border) {
  margin: 4px 0 0 0;
}

.label-title {
  color: var(--gray-dark-mode-text, --color-text-4);
  font-size: 16px;
  font-style: normal;
  font-weight: 500;
  margin-bottom: 10px;
}

.label-content {
  color: gray;
  font-size: 12px;
  font-style: normal;
  font-weight: 500;
}

.km-card:hover {
  transform: translateY(-2px);
}

.firstChildImg {
  background-color: #4E5969;
  border-radius: 12px;
}

.firstChildImg img {
  width: 50px !important;
  height: 50px !important;
}

.item-list {
  justify-content: center;
  display: flex;
  align-items: center;
  border-radius: 12px;
}

:deep(.arco-card-size-medium .arco-card-body) {
  padding: 20px 0px !important;
}

:deep(.arco-card-bordered) {
  border: none !important;
}

.tag-list {
  position: absolute;
  top: 0px;
  color: #40444b;
  font-size: 12px;
  font-style: normal;
  font-weight: 600;
  border-radius: 4px 0px 0px 0px;
  width: 70px;
  display: inline-flex;
  padding: 8.5px 14px 8.5px 7px;
  align-items: center;
  height: 30px;
  background: #f07d5f;
}

:deep(.arco-carousel-arrow-left),
:deep(.arco-carousel-arrow-right) {
  margin-top: -44px;
}

:deep(.arco-row-justify-space-around) {
  justify-content: left !important;
}
</style>
