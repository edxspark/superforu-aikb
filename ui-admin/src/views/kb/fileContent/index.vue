<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </transition>

    <el-card shadow="never">
      <template #header>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:fileContent:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:fileContent:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:fileContent:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:fileContent:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="fileContentList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="true" />
        <el-table-column label="文件ID" align="center" prop="linkFileId" />
        <el-table-column label="知识库ID" align="center" prop="linkKmId" />
        <el-table-column label="当前文件目录IDS" align="center" prop="catalogIds" />
        <el-table-column label="文件类型ID" align="center" prop="linkFileCode" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:fileContent:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:fileContent:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="total>0"
          :total="total"
          v-model:page="queryParams.pageNum"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
      />
    </el-card>
    <!-- 添加或修改文件内容对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="fileContentFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件ID" prop="linkFileId">
          <el-input v-model="form.linkFileId" placeholder="请输入文件ID" />
        </el-form-item>
        <el-form-item label="知识库ID" prop="linkKmId">
          <el-input v-model="form.linkKmId" placeholder="请输入知识库ID" />
        </el-form-item>
        <el-form-item label="当前文件目录IDS" prop="catalogIds">
            <el-input v-model="form.catalogIds" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="文件类型ID" prop="linkFileCode">
          <el-input v-model="form.linkFileCode" placeholder="请输入文件类型ID" />
        </el-form-item>
        <el-form-item label="文件内容">
          <editor v-model="form.linkFileContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="图片地址" prop="picUrl">
          <el-input v-model="form.picUrl" placeholder="请输入图片地址" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="FileContent" lang="ts">
import { listFileContent, getFileContent, delFileContent, addFileContent, updateFileContent } from '@/api/kb/fileContent';
import { FileContentVO, FileContentQuery, FileContentForm } from '@/api/kb/fileContent/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const fileContentList = ref<FileContentVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const fileContentFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: FileContentForm = {
  id: undefined,
  linkFileId: undefined,
  linkKmId: undefined,
  catalogIds: undefined,
  linkFileCode: undefined,
  linkFileContent: undefined,
  picUrl: undefined,
}
const data = reactive<PageData<FileContentForm, FileContentQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文件内容列表 */
const getList = async () => {
  loading.value = true;
  const res = await listFileContent(queryParams.value);
  fileContentList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 取消按钮 */
const cancel = () => {
  reset();
  dialog.visible = false;
}

/** 表单重置 */
const reset = () => {
  form.value = {...initFormData};
  fileContentFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 多选框选中数据 */
const handleSelectionChange = (selection: FileContentVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加文件内容";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: FileContentVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getFileContent(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改文件内容";
}

/** 提交按钮 */
const submitForm = () => {
  fileContentFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateFileContent(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addFileContent(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: FileContentVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除文件内容编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delFileContent(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/fileContent/export', {
    ...queryParams.value
  }, `fileContent_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
