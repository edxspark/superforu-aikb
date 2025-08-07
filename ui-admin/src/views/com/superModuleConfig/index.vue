<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="打开方式" prop="openWay">
            <el-input v-model="queryParams.openWay" placeholder="请输入打开方式" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['com:superModuleConfig:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['com:superModuleConfig:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['com:superModuleConfig:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['com:superModuleConfig:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="superModuleConfigList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="名称" align="center" prop="name" />
        <el-table-column label="排序" align="center" prop="sort" />
        <el-table-column label="用户等级id关联" align="center" prop="linkUserEquityId" />
        <el-table-column label="是否初始化" align="center" prop="status" />
        <el-table-column label="配置内容" align="center" prop="value" />
        <el-table-column label="模块ICON" align="center" prop="icon" />
        <el-table-column label="模块颜色" align="center" prop="color" />
        <el-table-column label="打开方式" align="center" prop="openWay" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['com:superModuleConfig:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['com:superModuleConfig:remove']"></el-button>
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
    <!-- 添加或修改超级模块配置对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="superModuleConfigFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="用户等级id关联" prop="linkUserEquityId">
          <el-input v-model="form.linkUserEquityId" placeholder="请输入用户等级id关联" />
        </el-form-item>
        <el-form-item label="配置内容" prop="value">
            <el-input v-model="form.value" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="模块ICON" prop="icon">
          <el-input v-model="form.icon" placeholder="请输入模块ICON" />
        </el-form-item>
        <el-form-item label="模块颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入模块颜色" />
        </el-form-item>
        <el-form-item label="打开方式" prop="openWay">
          <el-input v-model="form.openWay" placeholder="请输入打开方式" />
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

<script setup name="SuperModuleConfig" lang="ts">
import { listSuperModuleConfig, getSuperModuleConfig, delSuperModuleConfig, addSuperModuleConfig, updateSuperModuleConfig } from '@/api/com/superModuleConfig';
import { SuperModuleConfigVO, SuperModuleConfigQuery, SuperModuleConfigForm } from '@/api/com/superModuleConfig/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const superModuleConfigList = ref<SuperModuleConfigVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const superModuleConfigFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: SuperModuleConfigForm = {
  id: undefined,
  name: undefined,
  sort: undefined,
  linkUserEquityId: undefined,
  status: undefined,
  value: undefined,
  icon: undefined,
  color: undefined,
  openWay: undefined
}
const data = reactive<PageData<SuperModuleConfigForm, SuperModuleConfigQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    openWay: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询超级模块配置列表 */
const getList = async () => {
  loading.value = true;
  const res = await listSuperModuleConfig(queryParams.value);
  superModuleConfigList.value = res.rows;
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
  superModuleConfigFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: SuperModuleConfigVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加超级模块配置";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: SuperModuleConfigVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getSuperModuleConfig(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改超级模块配置";
}

/** 提交按钮 */
const submitForm = () => {
  superModuleConfigFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateSuperModuleConfig(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addSuperModuleConfig(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: SuperModuleConfigVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除超级模块配置编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delSuperModuleConfig(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('com/superModuleConfig/export', {
    ...queryParams.value
  }, `superModuleConfig_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
