<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="类型名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入类型名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="类型ID" prop="code">
            <el-input v-model="queryParams.code" placeholder="请输入类型ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:fileType:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:fileType:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:fileType:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:fileType:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="fileTypeList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="类型名称" align="center" prop="name" />
        <el-table-column label="类型ID" align="center" prop="code" />
        <el-table-column label="序号" align="center" prop="sort" />
        <el-table-column label="类型ICON" align="center" prop="icon" />
        <el-table-column label="类型颜色" align="center" prop="color" />
        <el-table-column label="后缀名" align="center" prop="attrType" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:fileType:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:fileType:remove']"></el-button>
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
    <!-- 添加或修改文件类型对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="fileTypeFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入类型名称" />
        </el-form-item>
        <el-form-item label="类型ID" prop="code">
          <el-input v-model="form.code" placeholder="请输入类型ID" />
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入序号" />
        </el-form-item>
        <el-form-item label="类型ICON" prop="icon">
          <el-input v-model="form.icon" placeholder="请输入类型ICON" />
        </el-form-item>
        <el-form-item label="类型颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入类型颜色" />
        </el-form-item>
        <el-form-item label="后缀名" prop="attrType">
          <el-input v-model="form.attrType" placeholder="请输入后缀名" />
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

<script setup name="FileType" lang="ts">
import { listFileType, getFileType, delFileType, addFileType, updateFileType } from '@/api/kb/fileType';
import { FileTypeVO, FileTypeQuery, FileTypeForm } from '@/api/kb/fileType/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const fileTypeList = ref<FileTypeVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const fileTypeFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: FileTypeForm = {
  id: undefined,
  name: undefined,
  code: undefined,
  sort: undefined,
  icon: undefined,
  color: undefined,
  attrType: undefined,
}
const data = reactive<PageData<FileTypeForm, FileTypeQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    code: undefined,
    params: {
    }
  },
  rules: {
    name: [
      { required: true, message: "类型名称不能为空", trigger: "blur" }
    ],
    code: [
      { required: true, message: "类型ID不能为空", trigger: "blur" }
    ],
    sort: [
      { required: true, message: "序号不能为空", trigger: "blur" }
    ],
    icon: [
      { required: true, message: "类型ICON不能为空", trigger: "blur" }
    ],
    color: [
      { required: true, message: "类型颜色不能为空", trigger: "blur" }
    ],
    attrType: [
      { required: true, message: "后缀名不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文件类型列表 */
const getList = async () => {
  loading.value = true;
  const res = await listFileType(queryParams.value);
  fileTypeList.value = res.rows;
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
  fileTypeFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: FileTypeVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加文件类型";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: FileTypeVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getFileType(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改文件类型";
}

/** 提交按钮 */
const submitForm = () => {
  fileTypeFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateFileType(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addFileType(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: FileTypeVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除文件类型编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delFileType(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/fileType/export', {
    ...queryParams.value
  }, `fileType_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
