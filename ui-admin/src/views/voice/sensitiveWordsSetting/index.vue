<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="敏感词" prop="sensitiveWords">
            <el-input v-model="queryParams.sensitiveWords" placeholder="请输入敏感词" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['voice:sensitiveWordsSetting:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['voice:sensitiveWordsSetting:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['voice:sensitiveWordsSetting:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['voice:sensitiveWordsSetting:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="sensitiveWordsSettingList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="敏感词" align="center" prop="sensitiveWords" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['voice:sensitiveWordsSetting:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['voice:sensitiveWordsSetting:remove']"></el-button>
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
    <!-- 添加或修改敏感词设置对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="sensitiveWordsSettingFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="敏感词" prop="sensitiveWords">
          <el-input v-model="form.sensitiveWords" placeholder="请输入敏感词" />
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

<script setup name="SensitiveWordsSetting" lang="ts">
import { listSensitiveWordsSetting, getSensitiveWordsSetting, delSensitiveWordsSetting, addSensitiveWordsSetting, updateSensitiveWordsSetting } from '@/api/voice/sensitiveWordsSetting';
import { SensitiveWordsSettingVO, SensitiveWordsSettingQuery, SensitiveWordsSettingForm } from '@/api/voice/sensitiveWordsSetting/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const sensitiveWordsSettingList = ref<SensitiveWordsSettingVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const sensitiveWordsSettingFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: SensitiveWordsSettingForm = {
  id: undefined,
  sensitiveWords: undefined,
}
const data = reactive<PageData<SensitiveWordsSettingForm, SensitiveWordsSettingQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    sensitiveWords: undefined,
    params: {
    }
  },
  rules: {
    sensitiveWords: [
      { required: true, message: "敏感词不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询敏感词设置列表 */
const getList = async () => {
  loading.value = true;
  const res = await listSensitiveWordsSetting(queryParams.value);
  sensitiveWordsSettingList.value = res.rows;
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
  sensitiveWordsSettingFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: SensitiveWordsSettingVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加敏感词设置";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: SensitiveWordsSettingVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getSensitiveWordsSetting(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改敏感词设置";
}

/** 提交按钮 */
const submitForm = () => {
  sensitiveWordsSettingFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateSensitiveWordsSetting(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addSensitiveWordsSetting(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: SensitiveWordsSettingVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除敏感词设置编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delSensitiveWordsSetting(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('voice/sensitiveWordsSetting/export', {
    ...queryParams.value
  }, `sensitiveWordsSetting_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
