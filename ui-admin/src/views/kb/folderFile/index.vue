<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="文件名称" prop="fileName">
            <el-input v-model="queryParams.fileName" placeholder="请输入文件名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="是否文件夹" prop="isFolder">
            <el-select v-model="queryParams.isFolder" placeholder="请选择是否文件夹" clearable>
              <el-option
                v-for="dict in kb_is_folder"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="用户ID" prop="linkUserId">
            <el-input v-model="queryParams.linkUserId" placeholder="请输入用户ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="编辑状态" prop="editing">
            <el-select v-model="queryParams.editing" placeholder="请选择编辑状态" clearable>
              <el-option
                v-for="dict in editing"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:folderFile:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:folderFile:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:folderFile:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:folderFile:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="folderFileList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="true" />
        <el-table-column label="文件名称" align="center" prop="fileName" />
        <el-table-column label="父类ID" align="center" prop="parentId" />
        <el-table-column label="当前文件目录IDS" align="center" prop="catalogIds" />
        <el-table-column label="是否文件夹" align="center" prop="isFolder">
          <template #default="scope">
            <dict-tag :options="kb_is_folder" :value="scope.row.isFolder"/>
          </template>
        </el-table-column>
        <el-table-column label="用户ID" align="center" prop="linkUserId" />
        <el-table-column label="知识库ID" align="center" prop="linkKmId" />
        <el-table-column label="编辑状态" align="center" prop="editing">
          <template #default="scope">
            <dict-tag :options="editing" :value="scope.row.editing"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:folderFile:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:folderFile:remove']"></el-button>
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
    <!-- 添加或修改文件夹&文件对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="folderFileFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="父类ID" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父类ID" />
        </el-form-item>
        <el-form-item label="当前文件目录IDS" prop="catalogIds">
          <el-input v-model="form.catalogIds" placeholder="请输入当前文件目录IDS" />
        </el-form-item>
        <el-form-item label="是否文件夹" prop="isFolder">
          <el-select v-model="form.isFolder" placeholder="请选择是否文件夹">
            <el-option
                v-for="dict in kb_is_folder"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户ID" prop="linkUserId">
          <el-input v-model="form.linkUserId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户名称" prop="linkUserName">
          <el-input v-model="form.linkUserName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="知识库ID" prop="linkKmId">
          <el-input v-model="form.linkKmId" placeholder="请输入知识库ID" />
        </el-form-item>
        <el-form-item label="编辑状态" prop="editing">
          <el-select v-model="form.editing" placeholder="请选择编辑状态">
            <el-option
                v-for="dict in editing"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文件类型ID" prop="linkFileTypeCode">
          <el-input v-model="form.linkFileTypeCode" placeholder="请输入文件类型ID" />
        </el-form-item>
        <el-form-item label="文件类型名称" prop="linkFileTypeName">
          <el-input v-model="form.linkFileTypeName" placeholder="请输入文件类型名称" />
        </el-form-item>
        <el-form-item label="文件内容ID" prop="linkFileContentId">
          <el-input v-model="form.linkFileContentId" placeholder="请输入文件内容ID" />
        </el-form-item>
        <el-form-item label="文件占用空间(B)" prop="fileSpace">
          <el-input v-model="form.fileSpace" placeholder="请输入文件占用空间(B)" />
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

<script setup name="FolderFile" lang="ts">
import { listFolderFile, getFolderFile, delFolderFile, addFolderFile, updateFolderFile } from '@/api/kb/folderFile';
import { FolderFileVO, FolderFileQuery, FolderFileForm } from '@/api/kb/folderFile/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { kb_is_folder, editing } = toRefs<any>(proxy?.useDict('kb_is_folder', 'editing'));

const folderFileList = ref<FolderFileVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const folderFileFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: FolderFileForm = {
  id: undefined,
  fileName: undefined,
  parentId: undefined,
  catalogIds: undefined,
  isFolder: undefined,
  linkUserId: undefined,
  linkUserName: undefined,
  linkKmId: undefined,
  editing: undefined,
  linkFileTypeCode: undefined,
  linkFileTypeName: undefined,
  linkFileContentId: undefined,
  fileSpace: undefined,
}
const data = reactive<PageData<FolderFileForm, FolderFileQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileName: undefined,
    isFolder: undefined,
    linkUserId: undefined,
    editing: undefined,
    params: {
    }
  },
  rules: {
    fileName: [
      { required: true, message: "文件名称不能为空", trigger: "blur" }
    ],
    parentId: [
      { required: true, message: "父类ID不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文件夹&文件列表 */
const getList = async () => {
  loading.value = true;
  const res = await listFolderFile(queryParams.value);
  folderFileList.value = res.rows;
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
  folderFileFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: FolderFileVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加文件夹&文件";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: FolderFileVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getFolderFile(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改文件夹&文件";
}

/** 提交按钮 */
const submitForm = () => {
  folderFileFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateFolderFile(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addFolderFile(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: FolderFileVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除文件夹&文件编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delFolderFile(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/folderFile/export', {
    ...queryParams.value
  }, `folderFile_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
