<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="团队成员ID" prop="linkTeamId">
            <el-input v-model="queryParams.linkTeamId" placeholder="请输入团队成员ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:storageConfig:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:storageConfig:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:storageConfig:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:storageConfig:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="storageConfigList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="true" />
        <el-table-column label="名称" align="center" prop="name" />
        <el-table-column label="团队成员ID" align="center" prop="linkTeamId" />
        <el-table-column label="配置信息JSON" align="center" prop="configJson" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:storageConfig:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:storageConfig:remove']"></el-button>
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
    <!-- 添加或修改集成存储配置对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="storageConfigFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="团队成员ID" prop="linkTeamId">
          <el-input v-model="form.linkTeamId" placeholder="请输入团队成员ID" />
        </el-form-item>
        <el-form-item label="配置信息JSON" prop="configJson">
            <el-input v-model="form.configJson" type="textarea" placeholder="请输入内容" />
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

<script setup name="StorageConfig" lang="ts">
import { listStorageConfig, getStorageConfig, delStorageConfig, addStorageConfig, updateStorageConfig } from '@/api/kb/storageConfig';
import { StorageConfigVO, StorageConfigQuery, StorageConfigForm } from '@/api/kb/storageConfig/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const storageConfigList = ref<StorageConfigVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const storageConfigFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: StorageConfigForm = {
  id: undefined,
  name: undefined,
  linkTeamId: undefined,
  configJson: undefined,
  picUrl: undefined,
}
const data = reactive<PageData<StorageConfigForm, StorageConfigQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    linkTeamId: undefined,
    params: {
    }
  },
  rules: {
    name: [
      { required: true, message: "名称不能为空", trigger: "blur" }
    ],
    linkTeamId: [
      { required: true, message: "团队成员ID不能为空", trigger: "blur" }
    ],
    configJson: [
      { required: true, message: "配置信息JSON不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询集成存储配置列表 */
const getList = async () => {
  loading.value = true;
  const res = await listStorageConfig(queryParams.value);
  storageConfigList.value = res.rows;
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
  storageConfigFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: StorageConfigVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加集成存储配置";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: StorageConfigVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getStorageConfig(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改集成存储配置";
}

/** 提交按钮 */
const submitForm = () => {
  storageConfigFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateStorageConfig(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addStorageConfig(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: StorageConfigVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除集成存储配置编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delStorageConfig(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/storageConfig/export', {
    ...queryParams.value
  }, `storageConfig_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
