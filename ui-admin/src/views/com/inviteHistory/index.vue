<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="邀请者用户id" prop="linkInviterId">
            <el-input v-model="queryParams.linkInviterId" placeholder="请输入邀请者用户id" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="被邀请者用户id" prop="linkInviteeId">
            <el-input v-model="queryParams.linkInviteeId" placeholder="请输入被邀请者用户id" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['com:inviteHistory:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['com:inviteHistory:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['com:inviteHistory:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['com:inviteHistory:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="inviteHistoryList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="true" />
        <el-table-column label="邀请者用户id" align="center" prop="linkInviterId" />
        <el-table-column label="被邀请者用户id" align="center" prop="linkInviteeId" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['com:inviteHistory:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['com:inviteHistory:remove']"></el-button>
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
    <!-- 添加或修改邀请记录对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="inviteHistoryFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="邀请者用户id" prop="linkInviterId">
          <el-input v-model="form.linkInviterId" placeholder="请输入邀请者用户id" />
        </el-form-item>
        <el-form-item label="被邀请者用户id" prop="linkInviteeId">
          <el-input v-model="form.linkInviteeId" placeholder="请输入被邀请者用户id" />
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

<script setup name="InviteHistory" lang="ts">
import { listInviteHistory, getInviteHistory, delInviteHistory, addInviteHistory, updateInviteHistory } from '@/api/com/inviteHistory';
import { InviteHistoryVO, InviteHistoryQuery, InviteHistoryForm } from '@/api/com/inviteHistory/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const inviteHistoryList = ref<InviteHistoryVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const inviteHistoryFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: InviteHistoryForm = {
  id: undefined,
  linkInviterId: undefined,
  linkInviteeId: undefined,
}
const data = reactive<PageData<InviteHistoryForm, InviteHistoryQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    linkInviterId: undefined,
    linkInviteeId: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "主键不能为空", trigger: "blur" }
    ],
    linkInviterId: [
      { required: true, message: "邀请者用户id不能为空", trigger: "blur" }
    ],
    linkInviteeId: [
      { required: true, message: "被邀请者用户id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询邀请记录列表 */
const getList = async () => {
  loading.value = true;
  const res = await listInviteHistory(queryParams.value);
  inviteHistoryList.value = res.rows;
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
  inviteHistoryFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: InviteHistoryVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加邀请记录";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: InviteHistoryVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getInviteHistory(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改邀请记录";
}

/** 提交按钮 */
const submitForm = () => {
  inviteHistoryFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateInviteHistory(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addInviteHistory(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: InviteHistoryVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除邀请记录编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delInviteHistory(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('com/inviteHistory/export', {
    ...queryParams.value
  }, `inviteHistory_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
