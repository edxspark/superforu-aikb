<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="优惠码" prop="promotionCode">
            <el-input v-model="queryParams.promotionCode" placeholder="请输入优惠码" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['com:userPackagePromotion:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['com:userPackagePromotion:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['com:userPackagePromotion:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['com:userPackagePromotion:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="userPackagePromotionList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="优惠码" align="center" prop="promotionCode" />
        <el-table-column label="优惠金额" align="center" prop="promotionValue" />
        <el-table-column label="最大使用次数" align="center" prop="maxUseCount" />
        <el-table-column label="已经使用次数" align="center" prop="usedCount" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['com:userPackagePromotion:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['com:userPackagePromotion:remove']"></el-button>
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
    <!-- 添加或修改优惠码对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="userPackagePromotionFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="优惠码" prop="promotionCode">
          <el-input v-model="form.promotionCode" placeholder="请输入优惠码" />
        </el-form-item>
        <el-form-item label="优惠金额" prop="promotionValue">
          <el-input v-model="form.promotionValue" placeholder="请输入优惠金额" />
        </el-form-item>
        <el-form-item label="最大使用次数" prop="maxUseCount">
          <el-input v-model="form.maxUseCount" placeholder="请输入最大使用次数" />
        </el-form-item>
        <el-form-item label="已经使用次数" prop="usedCount">
          <el-input v-model="form.usedCount" placeholder="请输入已经使用次数" />
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

<script setup name="UserPackagePromotion" lang="ts">
import { listUserPackagePromotion, getUserPackagePromotion, delUserPackagePromotion, addUserPackagePromotion, updateUserPackagePromotion } from '@/api/com/userPackagePromotion';
import { UserPackagePromotionVO, UserPackagePromotionQuery, UserPackagePromotionForm } from '@/api/com/userPackagePromotion/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const userPackagePromotionList = ref<UserPackagePromotionVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const userPackagePromotionFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: UserPackagePromotionForm = {
  id: undefined,
  promotionCode: undefined,
  promotionValue: undefined,
  maxUseCount: undefined,
  usedCount: undefined,
}
const data = reactive<PageData<UserPackagePromotionForm, UserPackagePromotionQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    promotionCode: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "ID不能为空", trigger: "blur" }
    ],
    promotionCode: [
      { required: true, message: "优惠码不能为空", trigger: "blur" }
    ],
    promotionValue: [
      { required: true, message: "优惠金额不能为空", trigger: "blur" }
    ],
    maxUseCount: [
      { required: true, message: "最大使用次数不能为空", trigger: "blur" }
    ],
    usedCount: [
      { required: true, message: "已经使用次数不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询优惠码列表 */
const getList = async () => {
  loading.value = true;
  const res = await listUserPackagePromotion(queryParams.value);
  userPackagePromotionList.value = res.rows;
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
  userPackagePromotionFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: UserPackagePromotionVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加优惠码";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: UserPackagePromotionVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getUserPackagePromotion(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改优惠码";
}

/** 提交按钮 */
const submitForm = () => {
  userPackagePromotionFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateUserPackagePromotion(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addUserPackagePromotion(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: UserPackagePromotionVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除优惠码编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delUserPackagePromotion(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('com/userPackagePromotion/export', {
    ...queryParams.value
  }, `userPackagePromotion_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
