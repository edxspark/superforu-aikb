<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="用户ID" prop="linkUserId">
            <el-input v-model="queryParams.linkUserId" placeholder="请输入用户ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="用户名称" prop="linkUserName">
            <el-input v-model="queryParams.linkUserName" placeholder="请输入用户名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:fileHistory:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:fileHistory:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:fileHistory:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:fileHistory:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="fileHistoryList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="true" />
        <el-table-column label="用户ID" align="center" prop="linkUserId" />
        <el-table-column label="用户名称" align="center" prop="linkUserName" />
        <el-table-column label="文件ID" align="center" prop="linkFileId" />
        <el-table-column label="提交备注" align="center" prop="mark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:fileHistory:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:fileHistory:remove']"></el-button>
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
    <!-- 添加或修改文件历史对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="fileHistoryFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="linkUserId">
          <el-input v-model="form.linkUserId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户名称" prop="linkUserName">
          <el-input v-model="form.linkUserName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="文件ID" prop="linkFileId">
          <el-input v-model="form.linkFileId" placeholder="请输入文件ID" />
        </el-form-item>
        <el-form-item label="文件内容">
          <editor v-model="form.fileContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="提交备注" prop="mark">
            <el-input v-model="form.mark" type="textarea" placeholder="请输入内容" />
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

<script setup name="FileHistory" lang="ts">
import { listFileHistory, getFileHistory, delFileHistory, addFileHistory, updateFileHistory } from '@/api/kb/fileHistory';
import { FileHistoryVO, FileHistoryQuery, FileHistoryForm } from '@/api/kb/fileHistory/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const fileHistoryList = ref<FileHistoryVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const fileHistoryFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: FileHistoryForm = {
  id: undefined,
  linkUserId: undefined,
  linkUserName: undefined,
  linkFileId: undefined,
  fileContent: undefined,
  mark: undefined,
  picUrl: undefined,
}
const data = reactive<PageData<FileHistoryForm, FileHistoryQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    linkUserId: undefined,
    linkUserName: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "主键不能为空", trigger: "blur" }
    ],
    linkUserId: [
      { required: true, message: "用户ID不能为空", trigger: "blur" }
    ],
    linkUserName: [
      { required: true, message: "用户名称不能为空", trigger: "blur" }
    ],
    linkFileId: [
      { required: true, message: "文件ID不能为空", trigger: "blur" }
    ],
    fileContent: [
      { required: true, message: "文件内容不能为空", trigger: "blur" }
    ],
    mark: [
      { required: true, message: "提交备注不能为空", trigger: "blur" }
    ],
    picUrl: [
      { required: true, message: "图片地址不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文件历史列表 */
const getList = async () => {
  loading.value = true;
  const res = await listFileHistory(queryParams.value);
  fileHistoryList.value = res.rows;
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
  fileHistoryFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: FileHistoryVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加文件历史";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: FileHistoryVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getFileHistory(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改文件历史";
}

/** 提交按钮 */
const submitForm = () => {
  fileHistoryFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateFileHistory(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addFileHistory(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: FileHistoryVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除文件历史编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delFileHistory(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/fileHistory/export', {
    ...queryParams.value
  }, `fileHistory_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
