<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="账号" prop="userAccount">
            <el-input v-model="queryParams.userAccount" placeholder="请输入账号" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="名称" prop="userName">
            <el-input v-model="queryParams.userName" placeholder="请输入名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="密码" prop="userPsw">
            <el-input v-model="queryParams.userPsw" placeholder="请输入密码" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="签名" prop="signature">
            <el-input v-model="queryParams.signature" placeholder="请输入签名" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="图片地址" prop="picUrl">
            <el-input v-model="queryParams.picUrl" placeholder="请输入图片地址" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="语言类型" prop="language">
            <el-input v-model="queryParams.language" placeholder="请输入语言类型" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="主题类型" prop="theme">
            <el-input v-model="queryParams.theme" placeholder="请输入主题类型" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['com:user:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['com:user:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['com:user:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['com:user:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="账号" align="center" prop="userAccount" />
        <el-table-column label="名称" align="center" prop="userName" />
        <el-table-column label="密码" align="center" prop="userPsw" />
        <el-table-column label="签名" align="center" prop="signature" />
        <el-table-column label="图片地址" align="center" prop="picUrl" />
        <el-table-column label="语言类型" align="center" prop="language" />
        <el-table-column label="主题类型" align="center" prop="theme" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['com:user:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['com:user:remove']"></el-button>
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
    <!-- 添加或修改用户信息对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="userFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="userAccount">
          <el-input v-model="form.userAccount" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="名称" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="密码" prop="userPsw">
          <el-input v-model="form.userPsw" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="签名" prop="signature">
          <el-input v-model="form.signature" placeholder="请输入签名" />
        </el-form-item>
        <el-form-item label="图片地址" prop="picUrl">
          <el-input v-model="form.picUrl" placeholder="请输入图片地址" />
        </el-form-item>
        <el-form-item label="语言类型" prop="language">
          <el-input v-model="form.language" placeholder="请输入语言类型" />
        </el-form-item>
        <el-form-item label="主题类型" prop="theme">
          <el-input v-model="form.theme" placeholder="请输入主题类型" />
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

<script setup name="User" lang="ts">
import { listUser, getUser, delUser, addUser, updateUser } from '@/api/com/user';
import { UserVO, UserQuery, UserForm } from '@/api/com/user/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const userList = ref<UserVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const userFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: UserForm = {
  id: undefined,
  userAccount: undefined,
  userName: undefined,
  userPsw: undefined,
  signature: undefined,
  picUrl: undefined,
  language: undefined,
  theme: undefined,
}
const data = reactive<PageData<UserForm, UserQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userAccount: undefined,
    userName: undefined,
    userPsw: undefined,
    signature: undefined,
    picUrl: undefined,
    language: undefined,
    theme: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "ID不能为空", trigger: "blur" }
    ],
    userAccount: [
      { required: true, message: "账号不能为空", trigger: "blur" }
    ],
    userName: [
      { required: true, message: "名称不能为空", trigger: "blur" }
    ],
    userPsw: [
      { required: true, message: "密码不能为空", trigger: "blur" }
    ],
    signature: [
      { required: true, message: "签名不能为空", trigger: "blur" }
    ],
    picUrl: [
      { required: true, message: "图片地址不能为空", trigger: "blur" }
    ],
    language: [
      { required: true, message: "语言类型不能为空", trigger: "blur" }
    ],
    theme: [
      { required: true, message: "主题类型不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户信息列表 */
const getList = async () => {
  loading.value = true;
  const res = await listUser(queryParams.value);
  userList.value = res.rows;
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
  userFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: UserVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加用户信息";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: UserVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getUser(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改用户信息";
}

/** 提交按钮 */
const submitForm = () => {
  userFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateUser(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addUser(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: UserVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除用户信息编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delUser(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('com/user/export', {
    ...queryParams.value
  }, `user_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
