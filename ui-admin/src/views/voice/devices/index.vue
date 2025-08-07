<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="设备ID" prop="deviceId">
            <el-input v-model="queryParams.deviceId" placeholder="请输入设备ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="设备名称" prop="deviceName">
            <el-input v-model="queryParams.deviceName" placeholder="请输入设备名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="设备类型" prop="deviceType">
            <el-select v-model="queryParams.deviceType" placeholder="请选择设备类型" clearable>
              <el-option
                v-for="dict in voice_device_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="人脸识别功能" prop="faceSwitch">
            <el-select v-model="queryParams.faceSwitch" placeholder="请选择人脸识别功能" clearable>
              <el-option
                v-for="dict in voice_switch"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="语音识别功能" prop="voiceSwitch">
            <el-select v-model="queryParams.voiceSwitch" placeholder="请选择语音识别功能" clearable>
              <el-option
                v-for="dict in voice_switch"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="图片地址" prop="picUrl">
            <el-input v-model="queryParams.picUrl" placeholder="请输入图片地址" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['voice:devices:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['voice:devices:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['voice:devices:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['voice:devices:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="devicesList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="设备ID" align="center" prop="deviceId" />
        <el-table-column label="设备名称" align="center" prop="deviceName" />
        <el-table-column label="设备类型" align="center" prop="deviceType">
          <template #default="scope">
            <dict-tag :options="voice_device_type" :value="scope.row.deviceType"/>
          </template>
        </el-table-column>
        <el-table-column label="人脸识别功能" align="center" prop="faceSwitch">
          <template #default="scope">
            <dict-tag :options="voice_switch" :value="scope.row.faceSwitch"/>
          </template>
        </el-table-column>
        <el-table-column label="语音识别功能" align="center" prop="voiceSwitch">
          <template #default="scope">
            <dict-tag :options="voice_switch" :value="scope.row.voiceSwitch"/>
          </template>
        </el-table-column>
        <el-table-column label="图片地址" align="center" prop="picUrl" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['voice:devices:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['voice:devices:remove']"></el-button>
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
    <!-- 添加或修改电话机设备对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="devicesFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备ID" prop="deviceId">
          <el-input v-model="form.deviceId" placeholder="请输入设备ID" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备类型" prop="deviceType">
          <el-select v-model="form.deviceType" placeholder="请选择设备类型">
            <el-option
                v-for="dict in voice_device_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="人脸识别功能" prop="faceSwitch">
          <el-select v-model="form.faceSwitch" placeholder="请选择人脸识别功能">
            <el-option
                v-for="dict in voice_switch"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="语音识别功能" prop="voiceSwitch">
          <el-select v-model="form.voiceSwitch" placeholder="请选择语音识别功能">
            <el-option
                v-for="dict in voice_switch"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
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

<script setup name="Devices" lang="ts">
import { listDevices, getDevices, delDevices, addDevices, updateDevices } from '@/api/voice/devices';
import { DevicesVO, DevicesQuery, DevicesForm } from '@/api/voice/devices/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { voice_switch, voice_device_type } = toRefs<any>(proxy?.useDict('voice_switch', 'voice_device_type'));

const devicesList = ref<DevicesVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const devicesFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: DevicesForm = {
  id: undefined,
  deviceId: undefined,
  deviceName: undefined,
  deviceType: undefined,
  faceSwitch: undefined,
  voiceSwitch: undefined,
  picUrl: undefined,
}
const data = reactive<PageData<DevicesForm, DevicesQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceId: undefined,
    deviceName: undefined,
    deviceType: undefined,
    faceSwitch: undefined,
    voiceSwitch: undefined,
    picUrl: undefined,
    params: {
    }
  },
  rules: {
    deviceId: [
      { required: true, message: "设备ID不能为空", trigger: "blur" }
    ],
    deviceName: [
      { required: true, message: "设备名称不能为空", trigger: "blur" }
    ],
    deviceType: [
      { required: true, message: "设备类型不能为空", trigger: "change" }
    ],
    faceSwitch: [
      { required: true, message: "人脸识别功能不能为空", trigger: "change" }
    ],
    voiceSwitch: [
      { required: true, message: "语音识别功能不能为空", trigger: "change" }
    ],
    picUrl: [
      { required: true, message: "图片地址不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询电话机设备列表 */
const getList = async () => {
  loading.value = true;
  const res = await listDevices(queryParams.value);
  devicesList.value = res.rows;
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
  devicesFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: DevicesVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加电话机设备";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: DevicesVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getDevices(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改电话机设备";
}

/** 提交按钮 */
const submitForm = () => {
  devicesFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateDevices(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addDevices(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: DevicesVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除电话机设备编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delDevices(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('voice/devices/export', {
    ...queryParams.value
  }, `devices_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
