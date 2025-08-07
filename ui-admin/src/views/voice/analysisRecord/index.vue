<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="设备ID" prop="deviceId">
            <el-input v-model="queryParams.deviceId" placeholder="请输入设备ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="违规敏感词" prop="sensitiveWords">
            <el-input v-model="queryParams.sensitiveWords" placeholder="请输入违规敏感词" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="拨号开始时间" style="width: 308px">
            <el-date-picker
                v-model="dateRangeCallTime"
                value-format="YYYY-MM-DD HH:mm:ss"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="[new Date(2000, 1, 1, 0, 0, 0), new Date(2000, 1, 1, 23, 59, 59)]"
            />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['voice:analysisRecord:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['voice:analysisRecord:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['voice:analysisRecord:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['voice:analysisRecord:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="analysisRecordList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="设备ID" align="center" prop="deviceId" />
        <el-table-column label="分析时间" align="center" prop="analysisTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.analysisTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="违规敏感词" align="center" prop="sensitiveWords" />
        <el-table-column label="拨号开始时间" align="center" prop="callTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.callTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="通话时长(s)" align="center" prop="callTimeLength" />
        <el-table-column label="使用人姓名" align="center" prop="userName" />
        <el-table-column label="本机号码" align="center" prop="localPhoneNumber" />
        <el-table-column label="电话状态" align="center" prop="phoneStatus">
          <template #default="scope">
            <dict-tag :options="voice_phone_status" :value="scope.row.phoneStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="电话号码" align="center" prop="phoneNumber" />
        <el-table-column label="人员姓名" align="center" prop="phoneName" />
        <el-table-column label="图片地址" align="center" prop="picUrl" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['voice:analysisRecord:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['voice:analysisRecord:remove']"></el-button>
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
    <!-- 添加或修改语音分析记录对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="analysisRecordFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备ID" prop="deviceId">
          <el-input v-model="form.deviceId" placeholder="请输入设备ID" />
        </el-form-item>
        <el-form-item label="分析时间" prop="analysisTime">
          <el-date-picker clearable
            v-model="form.analysisTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择分析时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="违规敏感词" prop="sensitiveWords">
          <el-input v-model="form.sensitiveWords" placeholder="请输入违规敏感词" />
        </el-form-item>
        <el-form-item label="拨号开始时间" prop="callTime">
          <el-date-picker clearable
            v-model="form.callTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择拨号开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="通话时长(s)" prop="callTimeLength">
          <el-input v-model="form.callTimeLength" placeholder="请输入通话时长(s)" />
        </el-form-item>
        <el-form-item label="使用人姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入使用人姓名" />
        </el-form-item>
        <el-form-item label="本机号码" prop="localPhoneNumber">
          <el-input v-model="form.localPhoneNumber" placeholder="请输入本机号码" />
        </el-form-item>
        <el-form-item label="电话状态" prop="phoneStatus">
          <el-select v-model="form.phoneStatus" placeholder="请选择电话状态">
            <el-option
                v-for="dict in voice_phone_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="人员姓名" prop="phoneName">
          <el-input v-model="form.phoneName" placeholder="请输入人员姓名" />
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

<script setup name="AnalysisRecord" lang="ts">
import { listAnalysisRecord, getAnalysisRecord, delAnalysisRecord, addAnalysisRecord, updateAnalysisRecord } from '@/api/voice/analysisRecord';
import { AnalysisRecordVO, AnalysisRecordQuery, AnalysisRecordForm } from '@/api/voice/analysisRecord/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { voice_phone_status } = toRefs<any>(proxy?.useDict('voice_phone_status'));

const analysisRecordList = ref<AnalysisRecordVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const dateRangeAnalysisTime = ref<[DateModelType, DateModelType]>(['', '']);
const dateRangeCallTime = ref<[DateModelType, DateModelType]>(['', '']);

const queryFormRef = ref<ElFormInstance>();
const analysisRecordFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: AnalysisRecordForm = {
  id: undefined,
  deviceId: undefined,
  analysisTime: undefined,
  sensitiveWords: undefined,
  callTime: undefined,
  callTimeLength: undefined,
  userName: undefined,
  localPhoneNumber: undefined,
  phoneStatus: undefined,
  phoneNumber: undefined,
  phoneName: undefined,
  picUrl: undefined,
}
const data = reactive<PageData<AnalysisRecordForm, AnalysisRecordQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceId: undefined,
    sensitiveWords: undefined,
    params: {
      callTime: undefined,
    }
  },
  rules: {
    deviceId: [
      { required: true, message: "设备ID不能为空", trigger: "blur" }
    ],
    picUrl: [
      { required: true, message: "图片地址不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询语音分析记录列表 */
const getList = async () => {
  loading.value = true;
  queryParams.value.params = {};
  proxy?.addDateRange(queryParams.value, dateRangeAnalysisTime.value, 'AnalysisTime');
  proxy?.addDateRange(queryParams.value, dateRangeCallTime.value, 'CallTime');
  const res = await listAnalysisRecord(queryParams.value);
  analysisRecordList.value = res.rows;
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
  analysisRecordFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  dateRangeAnalysisTime.value = ['', ''];
  dateRangeCallTime.value = ['', ''];
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 多选框选中数据 */
const handleSelectionChange = (selection: AnalysisRecordVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加语音分析记录";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: AnalysisRecordVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getAnalysisRecord(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改语音分析记录";
}

/** 提交按钮 */
const submitForm = () => {
  analysisRecordFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateAnalysisRecord(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addAnalysisRecord(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: AnalysisRecordVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除语音分析记录编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delAnalysisRecord(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('voice/analysisRecord/export', {
    ...queryParams.value
  }, `analysisRecord_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
