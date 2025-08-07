<template>
  <a-modal
      :visible="isPayModal"
      title-align="start"
      :mask-closable="false"
      width="360px"
      ok-text="已完成支付"
      @cancel="handlePayModalCancel"
      @ok="handlePayModalOK"
      :hide-cancel="true"
  >
    <template #title> {{isPayTitle}}</template>
    <a-col :style="{textAlign:'center',fontSize:'16px',fontWeight:'bold'}">
      <a-space :style="{margin:'0px 0px 10px 0px'}">{{isPayPackage}}</a-space>
    </a-col>
    <a-row>
      <a-col :style="{textAlign:'center'}">
        <qrcode-vue v-if="qrCode" :value="qrCode" :size="180"></qrcode-vue>
      </a-col>
    </a-row>
    <a-row>
      <a-col :style="{textAlign:'center'}">
        <a-space :style="{marginTop:'20px'}">{{isPayRemention}}</a-space>
      </a-col>
    </a-row>

  </a-modal>
  <a-modal
    :visible="isVipModel"
    title-align="start"
    :mask-closable="false"
    width="460px"
    :footer="false"
    @cancel="handleCancel"
  >
    <template #title>{{$t('km.upgrade')}}</template>
    <a-row class="grid-demo" justify="space-around">
      <a-col :span="16" class="col">
        <div>
          <div class="title">{{memberPackageInfo.packageName}}</div>
            <a-typography class="explain">
              <a-typography-paragraph>
                <IconCheck class="icon"></IconCheck>
                {{memberPackageInfo.desc}}
              </a-typography-paragraph>
            </a-typography>

          <a-form
            auto-label-width
            :style="{ width: '230px' }"
            :model="memberForm"
            size="mini"
          >
            <a-form-item field="number" :required="true" label="续费时长:">
              <a-select
                v-model="memberForm.number"
                placeholder="请选择续费时长"
                @change="memberSelectChange"
                :options="durationList"
                :field-names="memberFieldNames"
              >
              </a-select>
            </a-form-item>

            <a-form-item field="payWay" :required="true" label="支付方式:">
              <a-select
                v-model="memberForm.payWay"
                placeholder="请选择支付方式"
              >
                <a-option value="wxpay"><icon-wechatpay :style="{color:'#00c250'}"/> 微信支付</a-option>
                <a-option value="alipay"><icon-alipay-circle :style="{color:'#1677ff'}"/> 支付宝支付</a-option>
              </a-select>
            </a-form-item>
            <a-form-item field="promotion" label="优惠码:">
              <a-input v-model="memberForm.promotion" placeholder="请填写优惠码" @change="usePromotion"/>
            </a-form-item>

            <a-row>
              <a-col :span="24" :style="{textAlign:'center',fontWeight:'bold',fontSize:'16px'}"> {{durationTotal}} <span style="font-size: 11px;">{{promotionAmount}}</span></a-col>
            </a-row>

          <div class="submit-btn">
            <a-button @click="handleConfirmPay('memberPackage')" type="primary" class="form-btn">
              <IconShareInternal></IconShareInternal>
              确认支付</a-button>
          </div>
          </a-form>
        </div>
      </a-col>
<!--      <a-col :span="8" class="col">-->
<!--        <div>-->
<!--          <div class="title">{{aiTokenInfo.packageName}}</div>-->
<!--          <a-typography class="explain">-->
<!--            <a-typography-paragraph>-->
<!--              <IconCheck class="icon"></IconCheck>-->
<!--              {{aiTokenInfo.desc}}-->
<!--            </a-typography-paragraph>-->
<!--          </a-typography>-->
<!--          <a-form-->
<!--              auto-label-width-->
<!--              :style="{ width: '230px' }"-->
<!--              :model="aiTokenForm"-->
<!--              size="mini"-->
<!--          >-->
<!--            <a-form-item field="number"  :required="true" label="充值金额:">-->
<!--              <a-select-->
<!--                  placeholder="请选择充值金额"-->
<!--                  v-model="aiTokenForm.number"-->
<!--                  allow-clear-->
<!--                  @change="aiTokenSelectChange"-->
<!--              >-->
<!--                <a-option value="1">1元</a-option>-->
<!--                <a-option value="10">10元</a-option>-->
<!--                <a-option value="20">20元</a-option>-->
<!--                <a-option value="50">50元</a-option>-->
<!--                <a-option value="100">100元</a-option>-->
<!--                <a-option value="200">200元</a-option>-->
<!--                <a-option value="500">500元</a-option>-->
<!--                <a-option value="1000">1000元</a-option>-->
<!--              </a-select>-->
<!--            </a-form-item>-->

<!--            <a-form-item field="payWay" :required="true" label="支付方式:">-->
<!--              <a-select-->
<!--                  v-model="aiTokenForm.payWay"-->
<!--                  placeholder="微信支付/支付宝支付"-->
<!--                  allow-clear-->
<!--              >-->
<!--                <a-option value="wxpay"><icon-wechatpay :style="{color:'#00c250'}"/> 微信支付</a-option>-->
<!--                <a-option value="alipay"><icon-alipay-circle :style="{color:'#1677ff'}"/> 支付宝支付</a-option>-->
<!--              </a-select>-->
<!--            </a-form-item>-->
<!--          </a-form>-->
<!--          <a-row>-->
<!--            <a-col :span="24" :style="{textAlign:'center',fontWeight:'bold',fontSize:'16px'}"> {{aiTokenTotal}}</a-col>-->
<!--          </a-row>-->
<!--          <div class="submit-btn">-->
<!--            <a-button @click="handleConfirmPay('aiTokenPackage')" type="primary" class="form-btn">-->
<!--              <IconShareInternal></IconShareInternal>-->
<!--              确认支付</a-button-->
<!--            >-->

<!--          </div>-->
<!--        </div>-->
<!--      </a-col>-->
    </a-row>
  </a-modal>
</template>

<script lang="ts" setup>
import {reactive, defineProps, defineEmits, ref} from 'vue';
import {
  queryOrderStatus,
  confirmPayFunction,
  confirmPayParams,
  getPackageItems, getPromotion,
} from "@/api/dashboard/index";
import QrcodeVue from 'qrcode.vue'
import {Message} from "@arco-design/web-vue";

  const props = defineProps({
    isVipModel: {
      type: Boolean,
      default: false,
    }
  });

  const isPayModal     = ref(false);
  const isPayTitle     = ref('');
  const isPayRemention = ref('');
  const isPayPackage   = ref('');

  const emits = defineEmits(['update:isVipModel']);
  let orderInterval;

  const handleCancel = () => {
    emits('update:isVipModel', false);
  };

const handlePayModalCancel = () => {
  isPayModal.value=false;
  clearInterval(orderInterval);
};

const handlePayModalOK = () =>{
  isPayModal.value=false;
  emits('update:isVipModel', false);
  clearInterval(orderInterval);
}

  const durationTotal = ref("");
  const memberFieldNames = {value: 'key', label: 'name'}
  const durationList = reactive([]);
  const memberForm = reactive({
    number: '',
    payWay: 'wxpay',
    packageCode:'memberPackage',
    promotion: '',
  });

  const memberPackageInfo = reactive({
    packageCode: "",
    packageName: "",
    unit: "",
    price: 0,
    min: 1,
    desc: ""
  })

const aiTokenTotal = ref("10元");

  const aiTokenForm = reactive({
    number: '10',
    payWay: 'wxpay',
    packageCode:'aiTokenPackage'
  });

  const aiTokenInfo = reactive({
      packageCode: "",
      packageName: "",
      unit: "",
      price: 0,
      min: 1,
      desc: ""
  });

  const qrCode = ref('');
  const handleConfirmPay = async (packageCode: string)  =>{

    console.log(packageCode);
    let payWaySelected;
    let params;
    if(packageCode === "memberPackage"){
      params = memberForm;
      isPayPackage.value=memberPackageInfo.packageName;
      payWaySelected = memberForm.payWay;
    }else if(packageCode==="aiTokenPackage"){
      params = aiTokenForm;
      isPayPackage.value=aiTokenInfo.packageName;
      payWaySelected = aiTokenForm.payWay;
    }

    // @ts-ignore
    const res = await confirmPayFunction(params as confirmPayParams);
    // @ts-ignore
    const qrCodeValue = res.msg;
    // @ts-ignore
    const payOrderNo = res.data;

    console.log(res);
    console.log(qrCodeValue);
    console.log(payOrderNo);

    qrCode.value=qrCodeValue;

    console.log(payWaySelected);
    if(payWaySelected==='wxpay'){
      isPayTitle.value="微信支付";
      isPayRemention.value="请使用手机打开【微信】扫描二维码完成支付";
      isPayModal.value=true;
    }else{
      isPayTitle.value="支付宝支付";
      isPayRemention.value="请使用手机打开【支付宝】扫描二维码完成支付";
      isPayModal.value=true;
    }

    // 订单状态查询
    let orderStatus = "false";
    orderInterval = setInterval(async ()=>{

      const resQueryOrderStatus = await queryOrderStatus(`${payOrderNo}`);
      orderStatus = `${resQueryOrderStatus.data}`;
      if(orderStatus==="false"){
        console.log(`"订单待支付:"${new Date().getTime()}`);
      }else{
        orderStatus = "true";
        console.log("订单支付成功");
        clearInterval(orderInterval);
        Message.success({
          content: '订单支付成功',
          duration: 5 * 1000,
        });
        handlePayModalOK();
      }
    },1000);
  }

const promotionAmount = ref('');
const usePromotion = async (item:any) =>{

    if(memberForm.promotion==='') return;
    const res = await getPromotion(memberForm.promotion);
    if(res.data){
      const payTotal =  durationTotal.value;
      // @ts-ignore
      const promotionValue = durationTotal.value - res.data.promotionValue;
      if(promotionValue>0){
        // @ts-ignore
        durationTotal.value = promotionValue;

        // @ts-ignore
        promotionAmount.value = `=${payTotal}-${res.data.promotionValue}`;
      }else{
        // @ts-ignore
        durationTotal.value = "1";

        // @ts-ignore
        promotionAmount.value = `(免单+1)`;
      }

    }else{
      promotionAmount.value = "";
    }

}

  const aiTokenSelectChange = async (item:any) =>{
    aiTokenTotal.value=`${item}元`;
  }

  const memberSelectChange = async (item:any) =>{
    durationList.forEach(function(duration){
      if(duration.key===item){
        durationTotal.value=duration.price;
        usePromotion(duration.key);
      }
    })
  }

  const getPackages = async () => {
    const res = await getPackageItems();

    // @ts-ignore
    res.rows.forEach(function(item){
      console.log(item.packageCode);
      if(item.packageCode==="memberPackage"){
        memberPackageInfo.packageCode=item.packageCode;
        memberPackageInfo.packageName=item.packageName;
        memberPackageInfo.unit=item.unit;
        memberPackageInfo.price=item.price;
        memberPackageInfo.min=item.min;
        memberPackageInfo.desc=item.desc;

        // 单价
        durationTotal.value = `${item.price}`;

        // 续费周期下拉选择
        const discountAry = JSON.parse(item.discount);
        const oneYear   = {"key":"1","price":`${item.price}`,"name":`1年/${item.price}元`};
        const twoYear   = {"key":"2","price":`${discountAry[0].value}`,"name":`2年/${discountAry[0].value}元`};
        const threeYear = {"key":"3","price":`${discountAry[1].value}`,"name":`3年/${discountAry[1].value}元`};
        durationList.push(oneYear);
        durationList.push(twoYear);
        durationList.push(threeYear);

        // 选中第一个
        memberForm.number="1";

      }else if(item.packageCode==="aiTokenPackage"){
        aiTokenInfo.packageCode=item.packageCode;
        aiTokenInfo.packageName=item.packageName;
        aiTokenInfo.unit=item.unit;
        aiTokenInfo.price=item.price;
        aiTokenInfo.min=item.min;
        aiTokenInfo.desc=item.desc;
      }else{
        console.log("error:getPackages.no.data");
      }
    });

  };

  getPackages();

</script>

<style scoped lang="less">

.qrcode {
  width: 50px;
  height: 50px;
}

  .grid-demo {
    .col {
      width: 320px;
      height: 360px;
      text-align: center;
      border-radius: 4px;
      margin: 5px;
      padding: 10px 30px;
      //border: 1px solid rgba(148, 155, 164, 0.3);
      .title {
        color: var(--color-text-2);
        font-size: 14px;
        font-style: normal;
        font-weight: 600;
        line-height: normal;
        padding-bottom: 15px;
      }
      .explain {
        color: var(--color-text-2);
        font-size: 11px;
        font-style: normal;
        font-weight: 400;
        margin-bottom: 24px;
        .icon {
          color: #248046;
          margin-right: 8px;
        }
      }
    }
  }
  .submit-btn {
    display: flex;
    justify-content: center;
  }
  .form-btn {
    display: flex;
    width: 177px;
    height: 38px;
    justify-content: center;
    align-items: center;
    gap: 5px;
    flex-shrink: 0;
    margin-top: 23px;
    border-radius: 3px;
    background: var(--cta-colors-blurple-main, #5865f2);
  }
  .new-team {
    border-radius: 3px;
    background: var(--cta-colors-green, #248046);
    width: 80%;
    line-height: 30px;
    margin-left: 10%;
    color: #ffffff;
    font-size: 10.555px;
    font-style: normal;
    font-weight: 800;
    text-align: center;
    cursor: pointer;
    margin-top: 43px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
</style>
