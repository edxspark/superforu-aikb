<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="删除对象类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择删除对象类型" clearable>
              <el-option
                v-for="dict in kb_del_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="删除对象ID" prop="linkId">
            <el-input v-model="queryParams.linkId" placeholder="请输入删除对象ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="删除对象名称" prop="linkName">
            <el-input v-model="queryParams.linkName" placeholder="请输入删除对象名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:recycle:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:recycle:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:recycle:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:recycle:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="recycleList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="id" v-if="true" />
        <el-table-column label="删除对象类型" align="center" prop="type">
          <template #default="scope">
            <dict-tag :options="kb_del_type" :value="scope.row.type"/>
          </template>
        </el-table-column>
        <el-table-column label="删除对象ID" align="center" prop="linkId" />
        <el-table-column label="删除对象名称" align="center" prop="linkName" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:recycle:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:recycle:remove']"></el-button>
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
    <!-- 添加或修改回收站对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="recycleFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="删除对象类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择删除对象类型">
            <el-option
                v-for="dict in kb_del_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="删除对象ID" prop="linkId">
          <el-input v-model="form.linkId" placeholder="请输入删除对象ID" />
        </el-form-item>
        <el-form-item label="删除对象名称" prop="linkName">
          <el-input v-model="form.linkName" placeholder="请输入删除对象名称" />
        </el-form-item>
        <el-form-item label="彻底删除时间" prop="completelyDelTime">
          <el-date-picker clearable
            v-model="form.completelyDelTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择彻底删除时间">
          </el-date-picker>
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

<script setup name="Recycle" lang="ts">
import { listRecycle, getRecycle, delRecycle, addRecycle, updateRecycle } from '@/api/kb/recycle';
import { RecycleVO, RecycleQuery, RecycleForm } from '@/api/kb/recycle/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { kb_del_type } = toRefs<any>(proxy?.useDict('kb_del_type'));

const recycleList = ref<RecycleVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const recycleFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: RecycleForm = {
  id: undefined,
  type: undefined,
  linkId: undefined,
  linkName: undefined,
  completelyDelTime: undefined,
}
const data = reactive<PageData<RecycleForm, RecycleQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    type: undefined,
    linkId: undefined,
    linkName: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询回收站列表 */
const getList = async () => {
  loading.value = true;
  const res = await listRecycle(queryParams.value);
  recycleList.value = res.rows;
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
  recycleFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: RecycleVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加回收站";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: RecycleVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getRecycle(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改回收站";
}

/** 提交按钮 */
const submitForm = () => {
  recycleFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateRecycle(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addRecycle(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: RecycleVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除回收站编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delRecycle(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/recycle/export', {
    ...queryParams.value
  }, `recycle_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
