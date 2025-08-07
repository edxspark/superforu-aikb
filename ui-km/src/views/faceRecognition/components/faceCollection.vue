<template>
  <div class="FaceCollection">
    <div class="face-search">
      <div>
        <a-input v-model="inputSearchValue" :style="{ width: '320px' }" placeholder="请输入名称/唯一ID" allow-clear />
        <a-button type="primary" class="primary-btn" @click="faceSearchClick()">
          <template #icon>
            <icon-search />
          </template>
          搜索
        </a-button>
      </div>
      <a-button type="primary" class="face-add" @click=" faceAddClick()">
        <template #icon>
          <icon-plus />
        </template>
        <div>新增人脸</div>
      </a-button>
      <a-modal v-model:visible="visibleModal" title-align="start" ok-text="保存" width="auto" @ok="handleOk">
        <template #title> 编辑 </template>
        <div>
          <a-form ref="myForm" :rules="addRules" :model="form" :style="{ width: '600px' }">
            <a-form-item field="subjectName" tooltip="名称" label="名称">
              <a-input v-model="form.subjectName" placeholder="请输入名称" />
            </a-form-item>
            <a-form-item field="subjectUid" label="唯一ID">
              <a-input v-model="form.subjectUid" placeholder="请输入唯一身份证号或手机号等" allow-clear />
            </a-form-item>
            <a-form-item validate-trigger="blur" field="ossId" label="点击上传人脸">
              <a-upload list-type="picture-card" :action="uploads.url" :file-list="fileList" :headers="uploads.headers"
                multiple :before-upload="handleBeforeUpload" @change="handleUploadChange"
                @before-remove="onBeforeRemove" :onError="handleUploadError" image-preview />
            </a-form-item>
          </a-form>
        </div>
      </a-modal>

    </div>
    <a-table :columns="synergyColumns" :loading="loading" :data="synergyData" :pagination="pagination">
      <template #picUrl="{ record }">
        <div class="face-images">
          <div v-for="image in record.picUrl" :key="image.ossId" class="image-container">
            <img :src="image.ossURL" alt="人脸照片" />
          </div>
        </div>
      </template>
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

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { globalHeaders } from '@/api/interceptor';
import { Message } from '@arco-design/web-vue';

import { faceSubjectList, faceSubjectAdd, faceSubjectUpdate, templateListDetail, faceSubjectPicAdd, faceSubjectOssId, deleteFaceId } from '@/api/faceRecognition/index';
import {
  TableColumnData,
  TableData
} from '@arco-design/web-vue/es/table/interface';
import { forEach } from "lodash";

interface UploadOption {
  /** 设置上传的请求头部 */
  headers: { [key: string]: any };
  /** 上传的地址 */
  url: string;
}

const uploads = reactive<UploadOption>({
  headers: globalHeaders(),
  url: `${localStorage.getItem(
    'spf_app_upload_host'
  )}/resource/oss/upload`
});
const fileList = ref([]);
const synergyColumns: TableColumnData[] = [
  {
    title: '名称',
    dataIndex: 'subjectName',
    align: 'center'
  },
  {
    title: '身份证号',
    dataIndex: 'subjectUid',
    align: 'center'
  },
  {
    title: '人脸照片',
    dataIndex: 'picUrl',
    align: 'center',
    slotName: 'picUrl',
  },
  {
    title: '人脸数量',
    dataIndex: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    slotName: 'ascend',
    dataIndex: 'ascend',
    align: 'center'
  }
];
const form = reactive({
  subjectName: '',
  ossId: '',
  subjectUid: '',
});
const addRules = {
  subjectName: [
    {
      required: true,
      message: '请输入名称'
    }
  ],
  subjectUid: [
    {
      required: true,
      message: '请输入UID'
    }
  ],
  ossId: [
    {
      required: true,
      message: '请上传人脸',
    },
    {
      validator: (value, cb) => {
        if (fileList.value.length > 0 || form.ossId) {
          cb();
        } else {
          cb('请上传人脸');
        }
      },
    },
  ],
};
const pagination = ref({ pageSize: 10, current: 1, total: 0 });
const myForm = ref();
const loading = ref(false);
const visibleModal = ref(false);
const synergyData = ref<Array<TableData>>([]
);
const inputSearchValue = ref('');
const editRecordId = ref('');
const isEdit = ref(false);
const ossIds = ref<string[]>([]);
const onLoad = async (params?: any) => {
  const parames = {
    ...params,
    pageSize: pagination.value.pageSize,
    pageNum: pagination.value.current,
    orderByColumn: 'isFolder,updateTime',
    isAsc: '',
  };

  // @ts-ignore
  const res = await faceSubjectList(parames);
  //  loading.value = false;
  // @ts-ignore
  const formattedData = res.rows.map((item: any) => {
    if (typeof item.picUrl === 'string') {
      try {
        item.picUrl = JSON.parse(item.picUrl);
      } catch (error) {
        item.picUrl = [];
      }
    }
    return item;
  });
  synergyData.value = formattedData;
  // @ts-ignore
  pagination.value.total = res.total;
};
onMounted(() => {
  onLoad();
});
const faceSearchClick = async () => {
  const params = {
    id: inputSearchValue.value,
  };
  await onLoad(params);

};

const faceAddClick = () => {
  // 清空表单字段
  form.subjectName = '';
  form.subjectUid = '';
  form.ossId = '';
  fileList.value = [];
  ossIds.value = [];
  isEdit.value = false;// 标记为新增状态
  visibleModal.value = true;
};
const editRecord = (record: any) => {
  editRecordId.value = record.id
  form.subjectName = record.subjectName;
  form.subjectUid = record.subjectUid;
  fileList.value = record.picUrl ? record.picUrl.map((image: any) => ({
    uid: image.ossId.toString(),
    name: '人脸图片',
    url: image.ossURL,
  })) : [];
  if (fileList.value.length > 0) {
    form.ossId = fileList.value.map((file: any) => file.uid).join(',');
  }
  ossIds.value = [];

  fileList.value.forEach((file: any) => {
    ossIds.value.push(file.uid.toString());
  });
  isEdit.value = true;
  visibleModal.value = true;
};
const handleUploadChange = (fileLists: any, fileItem: any) => {

  if (fileItem.status === 'done' && fileItem.response?.data) {
    const { ossId, fileName, url } = fileItem.response.data;

    // 构建新的文件信息
    const newFile = {
      uid: ossId,
      name: fileName,
      url,
    };

    // 判断文件是否是编辑进来的
    console.log("fileItem.uid:")
    console.log(fileItem.uid)
    console.log("fileItem.fileLists:")
    fileLists.forEach((item, index) => {
      console.log(item, index);
    });
    const fileExists = fileLists.some((file: any) => file.uid === fileItem.uid);
    alert(fileExists)
    if (fileExists) {
      // 如果文件已存在（编辑进来），替换对应的文件信息
      fileList.value = fileLists.map((file: any) =>
        file.uid === fileItem.uid ? newFile : file
      );
      faceSubjectPicAdd({ ids: editRecordId.value }, ossId)

    } else {
      // 如果是新增的文件，直接加入新文件
      fileList.value = [...fileLists, newFile];
    }

    // 更新 ossId 列表
    ossIds.value.push(ossId);
    form.ossId = ossIds.value.join(',');

  }
};
const onBeforeRemove = async (file: any) => {
  try {
    const ossIdToRemove = file.uid; // 使用 uid 直接删除
    await faceSubjectOssId({ ids: editRecordId.value }, file.uid);
    Message.success('删除成功');
    // 删除成功后，从 fileList 和 ossIds 中移除对应的文件
    fileList.value = fileList.value.filter(f => f.uid !== ossIdToRemove);
    ossIds.value = ossIds.value.filter(ossId => ossId !== ossIdToRemove);

    // 更新 form.ossId 为最新的拼接字符串
    form.ossId = ossIds.value.join(',');
    return true;

  } catch (error) {
    return false;
  }
};

const deleteTeamitem = async (record: any) => {
  await deleteFaceId(record.id);
  Message.success('删除成功');
  await onLoad();
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
const handleOk = async () => {
  console.log("图片列表", fileList.value);
  console.log("图片ossIds列表", ossIds.value);
  myForm.value?.validate(async (valid: any) => {
    if (valid === undefined) {

      if (fileList.value.length > 0) {
        form.ossId = fileList.value.map((file: any) => file.uid).join(',');
      }

      if (isEdit.value) {
        // 编辑时调用更新接口
        const parames = {
          id: editRecordId.value,
          subjectName: form.subjectName,
          subjectUid: form.subjectUid
        };
        await faceSubjectUpdate(parames);
        Message.success('编辑成功');
      } else {
        // 新增时调用添加接口
        await faceSubjectAdd(form);
        Message.success('新增成功');
      }

      visibleModal.value = false;
      await onLoad();
    } else {
      Message.error('请完善信息');
      visibleModal.value = true;
    }
  });
};



</script>

<style lang="less" scoped>
.FaceCollection {
  padding: 20px 10px;

}

.face-search {
  display: flex;
  justify-content: space-around;
  padding-bottom: 20px;

}

.face-add {
  margin-left: 100px;
}

.face-images {
  display: flex;
  flex-wrap: nowrap;
  justify-content: center;
}

.image-container {
  margin-right: 10px;
  /* 设置图片之间的间距 */
}

img {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  /* 圆角效果 */
}
</style>
