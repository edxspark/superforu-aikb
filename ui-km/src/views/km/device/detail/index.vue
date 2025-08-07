<template>
  <a-layout>
    <a-layout style="padding: 0 24px">
      <!--面包屑-->
      <a-breadcrumb :style="{ margin: '16px 0' }">
        <a-breadcrumb-item @click="rootAboutClick">
          <div class="record-hover">
            {{ itemName || '' }}
          </div></a-breadcrumb-item
        >
        <a-breadcrumb-item
          v-for="(item, index) in breadcrumbItems"
          :key="item.id"
        >
          <div v-if="index + 1 === breadcrumbItems.length">
            {{ item.fileName }}
          </div>
          <div v-else class="record-hover" @click="crumbItemsClick(item)">
            <icon-folder /> {{ item.fileName }}
          </div>
        </a-breadcrumb-item>
      </a-breadcrumb>

      <!--工具条-->
      <a-layout-content>
        <a-space v-show="itemName !== '回收站'" class="space-btn" align="end">
          <a-input-search
            v-model="searchKey"
            allow-clear
            :style="{ width: '220px', marginRight: '50px' }"
            placeholder="请输入名称，回车搜索..."
            @press-enter="handleSearch"
            @clear="handleClear"
          />
          <a-popconfirm :content="$t('workplace.deleteIs')" @ok="batchDelete">
            <a-button
              v-show="selectedRowKeys.length != 0"
              type="outline"
              status="danger"
            >
              <template #icon>
                <icon-delete />
              </template>
              删除
            </a-button>
          </a-popconfirm>
          <a-button
            type="primary"
            status="success"
            :disabled="itemName ? false : true"
            @click="templateClick('模板库')"
          >
            模板库
            <template #icon>
              <icon-apps />
            </template>
          </a-button>
          <a-button
            type="primary"
            status="warning"
            :disabled="itemName ? false : true"
            @click="templateClick('上传')"
          >
            上传
            <template #icon>
              <icon-upload />
            </template>
          </a-button>

          <a-button
            type="primary"
            status="warning"
            :disabled="itemName ? false : true"
            @click="templateClick('批量上传')"
          >
            批量上传
            <template #icon>
              <icon-upload />
            </template>
          </a-button>

          <a-button
            type="primary"
            :disabled="itemName ? false : true"
            @click="newFolder()"
          >
            文件夹
            <template #icon>
              <icon-folder-add />
            </template>
          </a-button>

          <a-space size="large">
            <a-dropdown>
              <a-button
                type="primary"
                :disabled="itemName ? false : true"
                @click="addClick"
              >
                <template #icon>
                  <icon-plus />
                </template>
                新建文档
              </a-button>
              <template #content>
                <a-doption
                  v-for="link in menuLink"
                  :key="link.id"
                  @click="paragraphClick(link)"
                >
                  <div class="add-div">
                    <div class="icon" :style="{ color: link.remark.color }">
                      <component :is="link.remark.icon" />
                    </div>
                    <a-typography-paragraph class="text">
                      {{ link.dictLabel }}
                    </a-typography-paragraph>
                  </div></a-doption
                >
              </template>
            </a-dropdown>
          </a-space>
        </a-space>
        <a-spin :loading="loading" tip="加载中">
          <a-space direction="vertical" size="large" fill>
            <a-table
              :row-selection="rowSelection"
              :columns="itemName === '回收站' ? recycleColumns : columns"
              :data="data"
              :pagination="pagination"
              row-key="id"
              :selected-keys="selectedRowKeys"
              @selection-change="handleRowSelectionChange"
              @cell-click="handleCellClick"
              @page-change="handlePageChange"
            >
              <template #operation="{ record }">
                <a-space size="large">
                  <a-dropdown position="bottom" :hide-on-select="true">
                    <IconMore @click.stop="handleSelect"></IconMore>
                    <template v-if="itemName === '回收站'" #content>
                      <a-doption
                        v-for="(link, index) in recycleLinks"
                        :key="link.text"
                      >
                        <div
                          class="dropdown-div"
                          @click="thoroughDeleteClick(link, index, record)"
                        >
                          <div class="icon">
                            <component :is="link.icon" style="color: #949ba4" />
                          </div>
                          <span class="text">
                            {{ $t(link.text) }}
                          </span>
                        </div>
                      </a-doption>
                    </template>
                    <template v-else #content>
                      <a-doption
                        v-for="(link, index) in links"
                        :key="link.text"
                      >
                        <div
                          class="dropdown-div"
                          @click="doptionItemClick(link, index, record)"
                        >
                          <div class="icon">
                            <component :is="link.icon" style="color: #949ba4" />
                          </div>
                          <span class="text">
                            {{ $t(link.text) }}
                          </span>
                        </div>
                      </a-doption>
                    </template>
                  </a-dropdown>
                </a-space>
              </template>
              <template #editing="{}"> - </template>
              <template #fileName="{ record }">
                <a-space>
                  <a-tooltip :content="record.fileName">
                    <div v-if="record.isFolder === 0" class="record-hover">
                      <div class="icon">
                        <IconRight :style="{ opacity: 0 }" />
                        <component
                          :is="record.fileIcon"
                          :style="{ color: record.fileIconColor, fontSize: 16 }"
                        />
                        {{ record.fileName }}
                      </div>
                    </div>
                    <div v-else class="record-hover">
                      <IconRight style="font-size: 14px; opacity: 0.6" />
                      <icon-folder
                        style="font-size: 16px"
                        :style="{ color: record.fileIconColor }"
                      />
                      {{ record.fileName }}
                    </div>
                  </a-tooltip>
                </a-space>
              </template>
            </a-table>
          </a-space>
        </a-spin>
      </a-layout-content>

      <a-modal
        v-model:visible="downloadModel"
        width="500px"
        height="900px"
        title-align="start"
        :hide-cancel="true"
      >
        <template #title> 附件下载</template>
        <div style="height: 180px">
          <iframe
            width="100%"
            height="100%"
            frameborder="0"
            :src="downloadURL"
          ></iframe>
        </div>
      </a-modal>

      <a-modal
        v-model:visible="templateModel"
        :width="templateModelWidth"
        title-align="start"
        :hide-cancel="true"
        :ok-text="templateModelName === '批量上传' ? '关闭' : '确定'"
        @ok="uploadFormok"
        @close="resetUploadForm"
      >
        <template #title> {{ templateModelName }} </template>
        <a-space v-if="templateModelName == '模板库'" size="large">
          <a-tabs position="left" @change="tabpaneChange">
            <a-tab-pane
              v-for="link in templateLists"
              :key="link.id"
              class="a-tab-pane"
              :title="link.name"
            >
              <a-row class="grid-demo" :gutter="[24, 12]">
                <a-col
                  v-for="(item, index) in cardList"
                  :key="item.fileTypeName"
                  :span="8"
                  :class="{ active: selectActive === index }"
                  @mouseover="selectChange(index)"
                >
                  <div class="col-div">
                    <a-card
                      :title="item.name"
                      :bordered="false"
                      style="border-radius: 8px"
                    >
                      <template #cover>
                        <div>
                          <img
                            :style="{
                              width: '100%',
                              height: '160px',
                            }"
                            alt="dessert"
                            :src="item.picUrl"
                          />
                        </div>
                      </template>
                      <div class="title">
                        <span>被使用{{ item.useCount }}次</span>
                        <span
                          v-if="selectActive === index"
                          style="display: flex"
                        >
                          <a-button
                            size="small"
                            class="cardList-btn"
                            type="primary"
                            style="margin-left: 10px"
                            @click="useTemplateClick(item)"
                            >使用
                          </a-button>
                        </span>
                      </div>
                    </a-card>
                  </div>
                </a-col>
              </a-row>
            </a-tab-pane>
          </a-tabs>
        </a-space>
        <a-form
          v-else-if="templateModelName == '批量上传'"
          :style="{ width: '600px' }"
          :model="formModel"
        >
          <a-form-item
            field="picUrl"
            label="选择文件列表"
            validate-trigger="blur"
          >
            <a-upload
              v-model:file-list="fileListss"
              multiple
              :action="uploads.url"
              :limit="100"
              :show-retry-button="false"
              :onSuccess="handleSuccess"
              :headers="uploads.headers"
              tip="每次最大上传100个文件"
              @exceed-limit="exceedLimitChange"
              @onProgress="handleProgress"
              @before-upload="beforeUpload"
              @before-remove="beforeRemove"
              @onError="handleUploadErrorss"
            />
          </a-form-item>
        </a-form>
        <a-form
          v-else
          ref="uploadFormRef"
          :rules="uploadFormRules"
          :model="uploadForm"
          :style="{ width: '600px' }"
        >
          <a-form-item field="name" label="名称" validate-trigger="blur">
            <a-input
              v-model="uploadForm.name"
              :max-length="64"
              :show-word-limit="true"
              placeholder="请输入知识库名称"
            />
          </a-form-item>
          <a-form-item field="picUrl" label="上传" validate-trigger="blur">
            <a-upload
              v-model:fileList="fileList"
              v-model="uploadForm.picUrl"
              :action="uploads.url"
              :headers="uploads.headers"
              :limit="1"
              :before-upload="handleBeforeUpload"
              :onSuccess="handleUploadSuccess"
              tip="上传附件小于300M"
              @before-remove="onBeforeRemove"
            />
          </a-form-item>
        </a-form>
      </a-modal>

      <a-modal
        v-model:visible="renameVisible"
        :mask-closable="false"
        title-align="start"
        @ok="handleOk"
      >
        <template #title> 重命名 </template>
        <a-input
          v-model="refileName"
          :style="{ width: '360px' }"
          placeholder="重命名当前文件名称"
          allow-clear
        />
      </a-modal>

      <a-modal
        v-model:visible="newFileVisible"
        title-align="start"
        :mask-closable="false"
        @ok="newFileHandleOk"
      >
        <template #title> 新建文件夹 </template>
        <a-input
          v-model="newFileName"
          :style="{ width: '360px' }"
          placeholder="请输入文件夹名称"
          allow-clear
          :max-length="128"
          :show-word-limit="true"
        />
      </a-modal>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref, reactive, defineProps, watch, defineExpose, nextTick } from 'vue';
import { fileTypeList, createByTemplate } from '@/api/workbench/index';
import {
  addFolderFile,
  editFolderFile,
  deleteFolderFile,
  folderFileList,
  recycleList,
  deleteRecycle,
  recoverRecycle,
  aiFolderFileEmbedding,
} from '@/api/dashboard/index';
import { templateList, fileTemplateTypeList } from '@/api/teamwork/index';
import { globalHeaders } from '@/api/interceptor';
import { Message, Modal, FileItem } from '@arco-design/web-vue';
import {
  TableColumnData,
  TableRowSelection,
} from '@arco-design/web-vue/es/table/interface';
import {
  fileLoader,
  FileLoaderParams,
} from '@/api/ai/documentLoaders/fileLoaders/FileLoaderUtil';

const callFileLoader = async (params: FileLoaderParams) => {
  fileLoader(params);
};

const props = defineProps({
  itemName: { type: String, required: true },
  itemId: { type: String, required: true },
});
interface UploadOption {
  /** 设置上传的请求头部 */
  headers: { [key: string]: any };
  /** 上传的地址 */
  url: string;
}
const breadcrumbItems = ref<
  {
    id: string;
    fileName: string;
  }[]
>([]);
const uploadFormRules = {
  name: [
    {
      required: true,
      message: '请输入附件名称',
    },
    {
      maxLength: 128,
      message: '附件名称不能超过128个字符',
    },
  ],
  picUrl: [
    {
      required: true,
      message: '请上传文档',
    },
  ],
};
const uploads = reactive<UploadOption>({
  headers: globalHeaders(),
  url: `${localStorage.getItem('spf_app_upload_host')}/resource/oss/upload`,
});
const selectedRowKeys = ref<string[]>([]);
// const selectedKeys = ref([]);
const rowSelection: TableRowSelection = reactive({
  type: 'checkbox',
  showCheckedAll: true,
});
const pagination = ref({ pageSize: 10, current: 1, total: 0 });
const selectActive = ref(0);
const fileList = ref([]);
const fileListss = ref([]);
// let fileListss: {
//   uid: string;
//   name: string;
//   status: string;
//   percent?: number;
// }[] = [];
const templateModelName = ref('');
const templateModelWidth = ref('1000px');
const data = ref([]);
const renameVisible = ref(false);
const newFileVisible = ref(false);
const templateModel = ref(false);
const downloadModel = ref(false);
const downloadURL = ref('');
const loading = ref(false);
const searchKey = ref('');

const columns: TableColumnData[] = [
  {
    title: '名称',
    dataIndex: 'fileName',
    slotName: 'fileName',
    align: 'left',
    width: 300,
    ellipsis: true,
  },
  {
    title: '类型',
    width: 100,
    dataIndex: 'linkFileTypeName',
    align: 'center',
  },
  {
    title: '所有者',
    dataIndex: 'createBy',
    align: 'center',
  },
  // {
  //   title: '修改人',
  //   dataIndex: 'updateBy',
  //   align: 'center'
  // },
  {
    title: '修改时间',
    width: 160,
    dataIndex: 'updateTime',
    align: 'center',
  },
  {
    title: 'AI状态',
    dataIndex: 'aiStatusLabel',
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
const recycleColumns: TableColumnData[] = [
  {
    title: '名称',
    dataIndex: 'fileName',
    slotName: 'fileName',
    align: 'left',
    width: 400,
  },
  {
    title: '类型',
    dataIndex: 'linkFileTypeName',
    align: 'center',
  },
  {
    title: '操作者',
    dataIndex: 'updateBy',
    align: 'center',
  },
  {
    title: '剩余天数（保留30天）',
    dataIndex: 'remainderDays',
    align: 'center',
  },
  {
    title: '删除时间',
    dataIndex: 'updateTime',
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
const menuLink = ref<
  {
    id: string;
    remark: { color: string; icon: string };
    dictLabel: string;
  }[]
>([]);
const refileName = ref('');
const newFileName = ref('');
const refileId = ref('');
const links = [
  {
    text: 'workplace.rename',
    icon: 'icon-reply',
  },
  {
    text: 'workplace.creatCopy',
    icon: 'icon-copy',
  },
  {
    text: 'workplace.deleteRecord',
    icon: 'icon-minus-circle',
  },
  {
    text: 'workplace.import.ai.km',
    icon: 'IconShareInternal',
  },
  // {
  //   text: 'workplace.SaveMyTemplate',
  //   icon: 'icon-interaction'
  // },
];
const recycleLinks = [
  {
    text: 'workplace.deletethorough',
    icon: 'icon-minus-circle',
  },
  {
    text: 'workplace.recoverhorough',
    icon: 'icon-sync',
  },
];
const parentId = ref('0');
const templateLists = ref<
  {
    id: string;
    name: string;
    remark: string;
    fileName: string;
  }[]
>([]);
const cardList = ref<
  {
    id: string;
    name: string;
    picUrl: string;
    fileTypeName: string;
    attrContent: string;
    useCount: string;
  }[]
>([]);
const uploadFormRef = ref();
const uploadForm = reactive({
  name: '',
  picUrl: '',
  extension: '',
});
const formModel = reactive({
  picUrl: '',
});
const onChildClick = async (id?: any) => {
  loading.value = true;
  parentId.value = id;
  const params = {
    parentId: id,
    linkKmId: props.itemId,
    fileName: searchKey.value,
    pageSize: pagination.value.pageSize,
    pageNum: pagination.value.current,
    orderByColumn: 'isFolder,updateTime',
    isAsc: 'desc,desc',
  };
  // @ts-ignore
  const res = await folderFileList(params);
  loading.value = false;
  // @ts-ignore
  data.value = res.rows;
  // @ts-ignore
  pagination.value.total = res.total;
};
const recycleClick = async () => {
  loading.value = true;
  const params = {
    pageSize: pagination.value.pageSize,
    pageNum: pagination.value.current,
    orderByColumn: 'createTime',
    isAsc: 'desc',
  };
  const res = await recycleList(params);
  loading.value = false;
  // @ts-ignore
  data.value = res.rows;
  // @ts-ignore
  pagination.value.total = res.total;
};
const addClick = async () => {
  const res = await fileTypeList();
  // @ts-ignore
  res.rows.forEach((item: any) => {
    item.remark = JSON.parse(item.remark);
  });
  // @ts-ignore
  menuLink.value = res.rows;
};
const handleSelect = async () => {};

const newFolder = async () => {
  newFileName.value = '';
  newFileVisible.value = true;
};

// 点击搜索
const handleSearch = async () => {
  await onChildClick();
};

const handleClear = async () => {
  searchKey.value = '';
  await onChildClick();
};

// 使用模板创建文件
const useTemplateClick = async (item: any) => {
  const params = {
    templateId: item.id,
    parentId: parentId.value ? parentId.value : '0',
    linkKmId: props.itemId,
  };
  createByTemplate(params).then(function (res: any) {
    if (res.code === 200) {
      templateModel.value = false;
      Message.success('创建成功!');
      onChildClick(parentId.value);

      window.open(
        `${localStorage.getItem(
          'spf_file_editors_host'
        )}?server=content&mode=edit&fileId=${res.data.id}&kmType=doc`,
        '_blank'
      );
    } else {
      Message.success({
        content: res.msg || '创建发生错误！',
      });
    }
  });
};

const copyFile = async (item: any) => {
  const params = {
    fileName: `${item.fileName}_copy`,
    parentId: parentId.value ? parentId.value : '0',
    linkKmId: item.linkKmId,
    linkFileTypeCode: item.linkFileTypeCode,
    linkFileTypeName: item.linkFileTypeName,
    copyFileId: item.id,
  };

  // @ts-ignore
  const res = await addFolderFile(params);
  onChildClick(parentId.value);

  window.open(
    `${localStorage.getItem(
      'spf_file_editors_host'
    )}?server=content&mode=edit&fileId=${res.data}&kmType=doc`,
    '_blank'
  );
};
const paragraphClick = async (item: any) => {
  if (item.dictLabel === '附件') {
    templateModel.value = true;
    templateModelName.value = '上传';
    return;
  }
  if (item.dictLabel === '文件夹') {
    newFileName.value = '';
    newFileVisible.value = true;
    return;
  }
  const timestamp = new Date().getTime();
  const params = {
    fileName: `未命名_${timestamp}`,
    parentId: parentId.value ? parentId.value : '0',
    linkKmId: props.itemId,
    linkFileTypeCode: item.dictValue,
    linkFileTypeName: item.dictLabel,
  };
  // @ts-ignore
  const res = await addFolderFile(params);
  Message.success('新增成功');

  onChildClick(parentId.value);

  const kmType = 'doc';
  const url = `${localStorage.getItem(
    'spf_file_editors_host'
  )}?server=content&mode=edit&fileId=${res.data}&kmType=${kmType}`;
  console.log(url);
  window.open(url, '_blank');
};
const resetUploadForm = () => {
  uploadForm.picUrl = '';
  fileList.value = [];
  fileListss.value.length = 0;
};
const newFileHandleOk = async (item: any) => {
  if (newFileName.value === '') {
    Message.error('新建文件夹名称不能为空');
    newFileVisible.value = true;
    return;
  }
  const params = {
    fileName: newFileName.value,
    parentId: parentId.value ? parentId.value : '0',
    linkKmId: props.itemId,
    linkFileTypeCode: 'folder',
    linkFileTypeName: '文件夹',
  };

  // @ts-ignore
  await addFolderFile(params);
  Message.success('新增成功');
  onChildClick(parentId.value);
};
const rootAboutClick = async () => {
  if (props.itemName !== '回收站') {
    breadcrumbItems.value = [];
    parentId.value = '0';
    await onChildClick();
  } else {
    await recycleClick();
  }
};
defineExpose({ onChildClick, rootAboutClick });

const tabpaneChange = async (value: any) => {
  const params = {
    pageSize: 100,
    pageNum: 1,
    orderByColumn: 'useCount',
    isAsc: 'desc',
    linkFileTemplateTypeId: value,
  };
  const res = await templateList(params);
  // @ts-ignore
  cardList.value = res.rows;
};
const handleProgress = (event: ProgressEvent, file: { uid: string }) => {
  // 更新文件上传进度
  const progress = Math.round((event.loaded / event.total) * 100);
  const index = fileListss.value.findIndex((f) => f.uid === file.uid);
  if (index !== -1) {
    fileListss.value[index].percent = progress;
  }
};

const handleRowSelectionChange = (selectedRowKey) => {
  selectedRowKeys.value = selectedRowKey;
  // 处理选中行变化的逻辑
};
const templateClick = async (value: any) => {
  uploadForm.name = '';
  uploadForm.picUrl = '';
  fileListss.value.length = 0;
  templateModelName.value = value;

  if (value === '模板库') {
    const res = await fileTemplateTypeList();
    // @ts-ignore
    templateLists.value = res.rows;
    templateModelWidth.value = '1000px';

    const selectId = templateLists.value[0].id;
    tabpaneChange(selectId);
  } else {
    templateModelWidth.value = '700px';
  }
  templateModel.value = true;
};

const handleOk = async () => {
  if (refileName.value === '') {
    Message.error('名称不能为空');
    renameVisible.value = true;
    return;
  }
  const params = {
    id: refileId.value,
    fileName: refileName.value,
  };
  await editFolderFile(params);
  Message.success('修改成功');
  onChildClick(parentId.value);
};
function selectChange(index: number) {
  selectActive.value = index;
}
const handlePageChange = (value: any) => {
  pagination.value.current = value;
  if (props.itemName === '回收站') {
    recycleClick();
  } else {
    // debugger;
    onChildClick(parentId.value);
  }
};

const doptionItemClick = async (link: any, index: number, record: any) => {
  let msgLoading;
  switch (index) {
    case 0:
      refileName.value = record.fileName;
      refileId.value = record.id;
      renameVisible.value = true;
      break;
    case 1:
      await copyFile(record);
      break;
    case 2:
      await deleteFolderFile(record.id);
      Message.success('删除成功');
      await onChildClick(parentId.value);
      break;
    case 3:
      msgLoading = Message.loading({
        id: 'ai_embedding_sync',
        content: '正在处理中...',
        duration: 600000,
      });

      await aiFolderFileEmbedding(record.id);

      msgLoading.close();
      Message.success({
        id: 'ai_embedding_sync_success',
        content: '处理成功！',
        duration: 3000,
      });
      await onChildClick(parentId.value);
      break;
    default:
      break;
  }
};

const batchDelete = async (record: any) => {
  const commaSeparatedString = selectedRowKeys.value.join(',');
  // @ts-ignore
  await deleteFolderFile(commaSeparatedString);
  Message.success('删除成功');
  selectedRowKeys.value = [];
  await onChildClick(parentId.value);
};

const FileWindowOpenClick = async (record: any) => {
  const url = `${localStorage.getItem(
    'spf_file_editors_host'
  )}?server=content&mode=edit&fileId=${record.id}&kmType=doc`;
  // 附件下载
  if (record.linkFileTypeCode === 'attachment') {
    const timestamp = Date.now();
    downloadURL.value = `${url}&timestamp=${timestamp}`;
    downloadModel.value = true;
  } else {
    // 打开编辑器
    window.open(url, '_blank');
  }
};
const FolderClick = async (record: any) => {
  pagination.value.current = 1;
  // @ts-ignore
  breadcrumbItems.value.push(record);
  await onChildClick(record.id);
};
const handleCellClick = async (record: any) => {
  if (props.itemName === '回收站') return;
  if (record.isFolder === 0) {
    FileWindowOpenClick(record);
  } else {
    FolderClick(record);
  }
};
const crumbItemsClick = async (item: any) => {
  const index = breadcrumbItems.value.findIndex(
    (element: any) => element.id === item.id
  );
  if (index !== -1) {
    breadcrumbItems.value.splice(index + 1);
  }
  pagination.value.current = 1;
  await onChildClick(item.id);
};

const thoroughDeleteClick = async (link: any, index: number, record: any) => {
  switch (index) {
    case 0:
      await deleteRecycle(record.id);
      Message.success('彻底删除成功');
      break;

    default:
      await recoverRecycle(record.id);
      await Message.success('恢复成功');
      break;
  }
  await recycleClick();
};

const uploadFormok = async () => {
  if (templateModelName.value === '批量上传') {
    await onChildClick(parentId.value);
  } else {
    uploadFormRef.value?.validate(async (volid: any) => {
      if (volid === undefined) {
        const params = {
          fileName: uploadForm.name,
          parentId: parentId.value ? parentId.value : '0',
          linkKmId: props.itemId,
          linkFileTypeCode: 'attachment',
          linkFileTypeName: '附件',
          picUrl: uploadForm.picUrl,
          fileExtension: uploadForm.extension,
        };
        // @ts-ignore
        await addFolderFile(params);
        await onChildClick(parentId.value);
      } else {
        Message.error('验证失败，请检查表单信息。');
        templateModel.value = true;
      }
    });
  }
};
/** 图片上传成功返回图片地址 */
const handleUploadSuccess = (res: any) => {
  if (res.response.code === 200) {
    uploadForm.picUrl = res.response.data.ossId;
    uploadForm.name = res.response.data.fileName;
    const ossURL = res.response.data.url;
    uploadForm.extension = ossURL.substring(ossURL.lastIndexOf('.') + 1);
    Message.success('上传成功!');
  } else {
    Message.error('上传失败!');
  }
};
const beforeUpload = (file) => {
  // 检查是否已存在相同文件名的文件
  const exists = fileListss.value.some((f) => f.name === file.name);
  if (exists) {
    Message.warning('文件已存在!');
    return false;
  }
  // 允许上传
  return true;
};
const exceedLimitChange = () => {
  Message.error('最多只能上传 100 个文件');
};
const handleSuccess = async (res: any) => {
  console.log("bath.handleSuccess.res:");
  console.log(res);
  if (res.response.code === 200) {
    const batchURL = res.response.data.url;
    const params = {
      fileName: res.response.data.fileName,
      parentId: parentId.value ? parentId.value : '0',
      linkKmId: props.itemId,
      linkFileTypeCode: 'attachment',
      linkFileTypeName: '附件',
      picUrl: res.response.data.ossId,
      fileExtension: batchURL.substring(batchURL.lastIndexOf('.') + 1),
    };
    // @ts-ignore
    const folderFileIdResponse = await addFolderFile(params);
    // @ts-ignore
    if (folderFileIdResponse.code !== 200) {
      console.log('fileListss==>');
      const existingFileIndex = fileListss.value.findIndex(
        (file) => file.name === res.response.data.fileName
      );
      if (existingFileIndex !== -1) {
        // 如果已有同名文件
        fileListss.value[existingFileIndex] = {
          id: folderFileIdResponse.data,
          uid: res.uid,
          name: res.response.data.fileName,
          status: 'error',
          percent: 0,
        };
      } else {
        const file = {
          id: folderFileIdResponse.data,
          uid: res.uid,
          percent: 100,
          name: res.response.data.fileName,
          url: res.response.data.url,
          status: res.status,
        };
        fileListss.value.push(file);
      }
    }
  }
};
const beforeRemove: (file: FileItem) => Promise<boolean> = (file) => {
  console.log(file);
  return new Promise((resolve, reject) => {
    Modal.confirm({
      content: `确认删除 ${file.name}?`,
      onOk: () => {
        // 确认删除后，找到文件在fileListss中的索引并删除
        const index = fileListss.value.findIndex((f) => f.uid === file.uid);
        if (index !== -1) {
          fileListss.value.splice(index, 1);
          const deletedFile = fileListss.value.splice(index, 1)[0];
          // @ts-ignore
          if (deletedFile.id) {
            // @ts-ignore
            deleteFolderFile(deletedFile.id);
          }
        }
        Message.success('删除成功');
        resolve(true);
      },
      onCancel: () => reject(),
    });
  });
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
const handleUploadErrorss = () => {
  Message.error('上传失败!');
};
const onBeforeRemove = (file: any): Promise<boolean> => {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      title: '',
      content: `确认删除 ${file.name}?`,
      onOk: () => {
        uploadForm.picUrl = '';
        resolve(true);
      },
      onCancel: () => reject(Message.error('error')),
    });
  });
};

watch(
  () => props.itemName,
  (newVal) => {
    pagination.value = { pageSize: 10, current: 1, total: 0 };
    data.value = [];
    if (props.itemName === '回收站') {
      recycleClick();
    } else {
      breadcrumbItems.value = [];
      selectedRowKeys.value = [];
      nextTick(() => {
        onChildClick();
      });
    }
  },
  {
    immediate: true,
  }
);

// 监听文件是否被重命名，刷新列表
setInterval(function () {
  if (localStorage.getItem('spf_file_status_refresh') === 'true') {
    localStorage.setItem('spf_file_status_refresh', 'false');
    onChildClick(0);
  }
}, 2000);
</script>

<style scoped lang="less">
.space-btn {
  padding: 20px;
  display: flex;
  justify-content: end;
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
.dropdown-div {
  display: flex;
  width: 151px;
  align-items: center;
  margin-bottom: 0px;
  gap: 10px;
  .text {
    color: #949ba4;
    font-size: 11.5px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }
}
:deep(.arco-tabs-tab-active),
.arco-tabs-tab {
  display: flex;
  width: 126px;

  flex-direction: column;
  align-items: flex-start;
  color: #948ba4;
  gap: 10.183px;
  border-radius: 4px;
  background: var(--tab, rgba(78, 80, 88, 0.6));
  span {
    color: #f2f3f5;
    font-size: 14px;
    font-style: normal;
    padding: 8px 0px;
    font-weight: 400;
    line-height: normal;
  }
}
:deep(.arco-tabs-nav-vertical.arco-tabs-nav-type-line) .arco-tabs-tab {
  margin-bottom: 30px;
}
:deep(.arco-tabs-content) {
  width: 900px;
}
.col-div {
  width: 260px;
  padding: 10px;
  .title {
    display: flex;
    min-height: 25px;
    justify-content: space-between;
    span {
      color: var(--, #949ba4);
      font-size: 13.178px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
    }
  }
}
:deep(.arco-tabs-pane) {
  height: 520px;
  overflow: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
  &::-webkit-scrollbar {
    display: none;
  }
}
.upload-test {
  color: var(--, #949ba4);
  font-size: 10px;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
}
.add-div {
  width: 160px;
  display: flex;
  align-items: center;
  // border-bottom: 1px solid rgb(229, 230, 235);
  padding: 0px 8px;
  .icon {
    margin-right: 10px;
  }
  .text {
    width: 100px;
    font-size: 11.5px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }
}
:deep(.arco-tabs-content) {
  width: 800px;
}
:deep(div.arco-typography) {
  margin-bottom: 0px;
}
:deep(.arco-dropdown-list-wrapper) {
  max-height: 250px;
}
.cardList-btn {
  background: rgb(var(--primary-5));
}
.record-hover:hover {
  opacity: 0.8;
  cursor: pointer;
}
</style>

function $t(text: any): any { throw new Error('Function not implemented.'); }
function $t(text: any): any { throw new Error('Function not implemented.'); }
