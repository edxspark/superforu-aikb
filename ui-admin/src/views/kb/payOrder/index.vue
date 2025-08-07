<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="订单ID" prop="orderNo">
            <el-input v-model="queryParams.orderNo" placeholder="请输入订单ID" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="支付状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择支付状态" clearable>
              <el-option
                v-for="dict in com_pay_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['kb:payOrder:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['kb:payOrder:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['kb:payOrder:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['kb:payOrder:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="payOrderList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" v-if="true" />
        <el-table-column label="订单ID" align="center" prop="orderNo" />
        <el-table-column label="支付项目" align="center" prop="goodName" />
        <el-table-column label="金额" align="center" prop="amount" />
        <el-table-column label="支付渠道ID" align="center" prop="linkPayWayId">
          <template #default="scope">
            <dict-tag :options="com_pay_way" :value="scope.row.linkPayWayId"/>
          </template>
        </el-table-column>
        <el-table-column label="支付渠道名称" align="center" prop="linkPayWayName">
          <template #default="scope">
            <dict-tag :options="com_pay_way" :value="scope.row.linkPayWayName"/>
          </template>
        </el-table-column>
        <el-table-column label="支付状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="com_pay_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="用户ID" align="center" prop="linkUserId" />
        <el-table-column label="用户名称" align="center" prop="linkUserName" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['kb:payOrder:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['kb:payOrder:remove']"></el-button>
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
    <!-- 添加或修改支付订单对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="payOrderFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单ID" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单ID" />
        </el-form-item>
        <el-form-item label="支付项目" prop="goodName">
          <el-input v-model="form.goodName" placeholder="请输入支付项目" />
        </el-form-item>
        <el-form-item label="金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="支付渠道ID" prop="linkPayWayId">
          <el-select v-model="form.linkPayWayId" placeholder="请选择支付渠道ID">
            <el-option
                v-for="dict in com_pay_way"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付渠道名称" prop="linkPayWayName">
          <el-input v-model="form.linkPayWayName" placeholder="请输入支付渠道名称" />
        </el-form-item>
        <el-form-item label="支付状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择支付状态">
            <el-option
                v-for="dict in com_pay_status"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户ID" prop="linkUserId">
          <el-input v-model="form.linkUserId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户名称" prop="linkUserName">
          <el-input v-model="form.linkUserName" placeholder="请输入用户名称" />
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

<script setup name="PayOrder" lang="ts">
import { listPayOrder, getPayOrder, delPayOrder, addPayOrder, updatePayOrder } from '@/api/kb/payOrder';
import { PayOrderVO, PayOrderQuery, PayOrderForm } from '@/api/kb/payOrder/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { com_pay_status, com_pay_way } = toRefs<any>(proxy?.useDict('com_pay_status', 'com_pay_way'));

const payOrderList = ref<PayOrderVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const payOrderFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: PayOrderForm = {
  id: undefined,
  orderNo: undefined,
  goodName: undefined,
  amount: undefined,
  linkPayWayId: undefined,
  linkPayWayName: undefined,
  status: undefined,
  linkUserId: undefined,
  linkUserName: undefined,
}
const data = reactive<PageData<PayOrderForm, PayOrderQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    orderNo: undefined,
    status: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询支付订单列表 */
const getList = async () => {
  loading.value = true;
  const res = await listPayOrder(queryParams.value);
  payOrderList.value = res.rows;
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
  payOrderFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: PayOrderVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加支付订单";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: PayOrderVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getPayOrder(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改支付订单";
}

/** 提交按钮 */
const submitForm = () => {
  payOrderFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updatePayOrder(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addPayOrder(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: PayOrderVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除支付订单编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delPayOrder(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('kb/payOrder/export', {
    ...queryParams.value
  }, `payOrder_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
