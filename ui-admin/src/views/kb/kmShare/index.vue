<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="分享ID" prop="shareId">
            <el-input v-model="queryParams.shareId" placeholder="请输入分享ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="团队ID" prop="outTeamId">
            <el-input v-model="queryParams.outTeamId" placeholder="请输入团队ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="访问权限" prop="accessPermission">
            <el-input v-model="queryParams.accessPermission" placeholder="请输入访问权限" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="访问权限值" prop="accessValues">
            <el-input v-model="queryParams.accessValues" placeholder="请输入访问权限值" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="访问权密码" prop="accessPassword">
            <el-input v-model="queryParams.accessPassword" placeholder="请输入访问权密码" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:kmShare:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:kmShare:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:kmShare:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:kmShare:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="kmShareList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="分享ID" align="center" prop="shareId" />
        <el-table-column label="团队ID" align="center" prop="outTeamId" />
        <el-table-column label="访问权限" align="center" prop="accessPermission" />
        <el-table-column label="访问权限值" align="center" prop="accessValues" />
        <el-table-column label="访问权密码" align="center" prop="accessPassword" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:kmShare:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:kmShare:remove']"></el-button>
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
    <!-- 添加或修改分享预览对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="kmShareFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分享ID" prop="shareId">
          <el-input v-model="form.shareId" placeholder="请输入分享ID" />
        </el-form-item>
        <el-form-item label="团队ID" prop="outTeamId">
          <el-input v-model="form.outTeamId" placeholder="请输入团队ID" />
        </el-form-item>
        <el-form-item label="访问权限" prop="accessPermission">
          <el-input v-model="form.accessPermission" placeholder="请输入访问权限" />
        </el-form-item>
        <el-form-item label="访问权限值" prop="accessValues">
            <el-input v-model="form.accessValues" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="访问权密码" prop="accessPassword">
          <el-input v-model="form.accessPassword" placeholder="请输入访问权密码" />
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

<script setup name="KmShare" lang="ts">
import { listKmShare, getKmShare, delKmShare, addKmShare, updateKmShare } from '@/api/kb/kmShare';
import { KmShareVO, KmShareQuery, KmShareForm } from '@/api/kb/kmShare/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const kmShareList = ref<KmShareVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const kmShareFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: KmShareForm = {
  id: undefined,
  shareId: undefined,
  outTeamId: undefined,
  accessPermission: undefined,
  accessValues: undefined,
  accessPassword: undefined,
}
const data = reactive<PageData<KmShareForm, KmShareQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    shareId: undefined,
    outTeamId: undefined,
    accessPermission: undefined,
    accessValues: undefined,
    accessPassword: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询分享预览列表 */
const getList = async () => {
  loading.value = true;
  const res = await listKmShare(queryParams.value);
  kmShareList.value = res.rows;
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
  kmShareFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: KmShareVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加分享预览";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: KmShareVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getKmShare(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改分享预览";
}

/** 提交按钮 */
const submitForm = () => {
  kmShareFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateKmShare(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addKmShare(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: KmShareVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除分享预览编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delKmShare(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/kmShare/export', {
    ...queryParams.value
  }, `kmShare_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
