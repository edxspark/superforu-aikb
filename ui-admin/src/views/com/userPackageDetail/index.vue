<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="用户ID" prop="linkUserId">
            <el-input v-model="queryParams.linkUserId" placeholder="请输入用户ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="编码" prop="packageCode">
            <el-select v-model="queryParams.packageCode" placeholder="请选择编码" clearable>
              <el-option
                v-for="dict in com_user_package"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="名称" prop="packageName">
            <el-input v-model="queryParams.packageName" placeholder="请输入名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['com:userPackageDetail:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['com:userPackageDetail:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['com:userPackageDetail:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['com:userPackageDetail:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="userPackageDetailList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="用户ID" align="center" prop="linkUserId" />
        <el-table-column label="编码" align="center" prop="packageCode">
        </el-table-column>
        <el-table-column label="名称" align="center" prop="packageName" />
        <el-table-column label="单位" align="center" prop="unit" />
        <el-table-column label="数量" align="center" prop="value" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['com:userPackageDetail:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['com:userPackageDetail:remove']"></el-button>
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
    <!-- 添加或修改用户权益资源套餐明细对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="userPackageDetailFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="linkUserId">
          <el-input v-model="form.linkUserId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="编码" prop="packageCode">
          <el-select v-model="form.packageCode" placeholder="请选择编码">
            <el-option
                v-for="dict in com_user_package"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="packageName">
          <el-input v-model="form.packageName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="数量" prop="value">
          <el-input v-model="form.value" placeholder="请输入数量" />
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

<script setup name="UserPackageDetail" lang="ts">
import { listUserPackageDetail, getUserPackageDetail, delUserPackageDetail, addUserPackageDetail, updateUserPackageDetail } from '@/api/com/userPackageDetail';
import { UserPackageDetailVO, UserPackageDetailQuery, UserPackageDetailForm } from '@/api/com/userPackageDetail/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { com_user_package } = toRefs<any>(proxy?.useDict('com_user_package'));

const userPackageDetailList = ref<UserPackageDetailVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const userPackageDetailFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: UserPackageDetailForm = {
  id: undefined,
  linkUserId: undefined,
  packageCode: undefined,
  packageName: undefined,
  unit: undefined,
  value: undefined,
}
const data = reactive<PageData<UserPackageDetailForm, UserPackageDetailQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    linkUserId: undefined,
    packageCode: undefined,
    packageName: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "ID不能为空", trigger: "blur" }
    ],
    linkUserId: [
      { required: true, message: "用户ID不能为空", trigger: "blur" }
    ],
    packageCode: [
      { required: true, message: "编码不能为空", trigger: "change" }
    ],
    packageName: [
      { required: true, message: "名称不能为空", trigger: "blur" }
    ],
    unit: [
      { required: true, message: "单位不能为空", trigger: "blur" }
    ],
    value: [
      { required: true, message: "数量不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户权益资源套餐明细列表 */
const getList = async () => {
  loading.value = true;
  const res = await listUserPackageDetail(queryParams.value);
  userPackageDetailList.value = res.rows;
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
  userPackageDetailFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: UserPackageDetailVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加用户权益资源套餐明细";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: UserPackageDetailVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getUserPackageDetail(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改用户权益资源套餐明细";
}

/** 提交按钮 */
const submitForm = () => {
  userPackageDetailFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateUserPackageDetail(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addUserPackageDetail(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: UserPackageDetailVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除用户权益资源套餐明细编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delUserPackageDetail(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('com/userPackageDetail/export', {
    ...queryParams.value
  }, `userPackageDetail_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
