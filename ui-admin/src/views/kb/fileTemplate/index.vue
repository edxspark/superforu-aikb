<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">

          <el-form-item label="模板类型" prop="linkFileTemplateTypeId">
            <el-select v-model="queryParams.linkFileTemplateTypeId" placeholder="模板类型">
              <el-option
                v-for="dict in fileTemplateTypeList"
                :key="dict.id"
                :label="dict.name"
                :value="dict.id"
              ></el-option>
            </el-select>
          </el-form-item>


          <el-form-item label="文件类型" prop="fileTypeCode">
            <el-select v-model="queryParams.fileTypeCode" placeholder="请选择文件类型" clearable>
              <el-option
                v-for="dict in file_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="模版名称" prop="fileTypeName">
            <el-input v-model="queryParams.name" placeholder="请输入文件类型名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:fileTemplate:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:fileTemplate:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:fileTemplate:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:fileTemplate:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="fileTemplateList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="false" />
        <el-table-column label="模板类型" align="center" width="120" prop="linkFileTemplateTypeName" />
        <el-table-column label="模板名称" align="left" width="200" prop="name" />
        <el-table-column label="文件类型" align="center" width="80" prop="fileTypeCode">
          <template #default="scope">
            <dict-tag :options="file_type" :value="scope.row.fileTypeCode"/>
          </template>
        </el-table-column>
        <el-table-column label="引用次数" align="center" prop="useCount" />
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="kb_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="最近修改时间" align="center" width="200" prop="updateTime"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="编辑模版内容" placement="top">
              <el-button link type="primary" icon="Promotion" @click="handleEditor(scope.row)" v-hasPermi="['kb:fileTemplate:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:fileTemplate:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:fileTemplate:remove']"></el-button>
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
    <!-- 添加或修改文档模板对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="fileTemplateFormRef" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="模板类型" prop="linkFileTemplateTypeId">
          <el-select v-model="form.linkFileTemplateTypeId" placeholder="模板类型">
            <el-option
              v-for="dict in fileTemplateTypeList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="文件类型" prop="fileTypeCode">
          <el-select v-model="form.fileTypeCode" placeholder="请选择文件类型" @change="changeFileType()" :disabled="updateDisable">
            <el-option
              v-for="dict in file_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="文件类型名称" prop="linkFileTypeName" style="display: none">
          <el-input v-model="form.fileTypeName" placeholder="请输入文件类型名称" />
        </el-form-item>

        <el-form-item label="模板名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入模板名称" />
        </el-form-item>

<!--        <el-form-item label="文件类型名称" prop="fileTypeName">-->
<!--          <el-input v-model="form.fileTypeName" placeholder="请输入文件类型名称" />-->
<!--        </el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
                v-for="dict in kb_status"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="图片地址" prop="picUrl">
          <image-upload v-model="form.picUrl"/>
        </el-form-item>

<!--        <el-form-item label="内容">-->
<!--          <editor v-model="form.attrContent" :min-height="192"/>-->
<!--        </el-form-item>-->
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

<script setup name="FileTemplate" lang="ts">
import { listFileTemplateType,listFileTemplate, getFileTemplate, delFileTemplate, addFileTemplate, updateFileTemplate } from '@/api/kb/fileTemplate';
import { FileTemplateVO, FileTemplateQuery, FileTemplateForm } from '@/api/kb/fileTemplate/types';
import {FileTemplateTypeVO} from "@/api/kb/fileTemplateType/types";

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { kb_status, file_type } = toRefs<any>(proxy?.useDict('kb_status', 'file_type'));

const fileTemplateList = ref<FileTemplateVO[]>([]);
const fileTemplateTypeList = ref<FileTemplateTypeVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const updateDisable = ref(false);

const queryFormRef = ref<ElFormInstance>();
const fileTemplateFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: FileTemplateForm = {
  id: undefined,
  name: undefined,
  fileTypeCode: undefined,
  fileTypeName: undefined,
  useCount: undefined,
  status: 0,
  linkFileTemplateTypeId: undefined,
  attrType: undefined,
  attrContent: undefined,
  picUrl: undefined,
}
const data = reactive<PageData<FileTemplateForm, FileTemplateQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileTypeCode: undefined,
    fileTypeName: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);


/**
 * 设置文件类型名称
 * */
const changeFileType = async () => {
  file_type.value.forEach(function(item:any){
    if(form.value.fileTypeCode===item.value){
      form.value.fileTypeName = item.label;
    }
  });
}

/** 查询文档模板类型列表 */
const getTemplateTypeList = async () => {
  loading.value = true;
  const res = await listFileTemplateType(queryParams.value);
  fileTemplateTypeList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 查询文档模板列表 */
const getList = async () => {
  loading.value = true;
  const res = await listFileTemplate(queryParams.value);
  fileTemplateList.value = res.rows;
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
  fileTemplateFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: FileTemplateVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加文档模板";
  updateDisable.value = false;
}

/** 修改按钮操作 */
const handleUpdate = async (row?: FileTemplateVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getFileTemplate(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改文档模板";
  updateDisable.value = true;
}

/** 编辑模版按钮操作 */
const handleEditor = async (row?: FileTemplateVO) => {
  reset();
  const _id      = row?.id || ids.value[0];
  const fileId   = row?.attrContent;
  const clientId = "e5cd7e4891bf95d1d19206ce24a7b32e";
  const spfTk    = localStorage.getItem("Admin-Token");
  const path   = row?.editorURL+"/editors.html?server=template&mode=edit&fileId="+fileId+"&kmType=doc&templateId="+_id+"&clientId="+clientId+"&spfTk="+spfTk;
  window.open(path);
}

/** 提交按钮 */
const submitForm = () => {
  fileTemplateFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateFileTemplate(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addFileTemplate(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: FileTemplateVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除文档模板编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delFileTemplate(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/fileTemplate/export', {
    ...queryParams.value
  }, `fileTemplate_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
  getTemplateTypeList();
});
</script>
