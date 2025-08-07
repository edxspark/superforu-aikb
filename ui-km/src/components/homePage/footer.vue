<template>
  <a-layout-footer class="footer">
    <div class="footerContent">
      <div class="rethinking"
        >重新思考知识的<br />
        生产力</div
      >
      <div class="rethinkingRight">
        <div
          v-for="(item, index) in billOfFare"
          :key="index"
          class="billOfFare"
        >
          <div v-for="(fare, i) in item" :key="i" :class="fare.class">{{
            fare.text
          }}</div>
        </div>
      </div>
    </div>
    <a class="recordNumber" target="_blank" href="http://www.edxspark.com">Copyright@EdxSpark </a> &nbsp;&nbsp;
    <a class="recordNumber" target="_blank" href="https://beian.miit.gov.cn">ICP备案号：沪ICP备20008728号-2</a>
  </a-layout-footer>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';

  const billOfFare = ref([
    [
      { class: 'billOfFareList blue', text: '产品相关' },
      { class: 'billOfFareList', text: '下载' },
      { class: 'billOfFareList', text: '特征' },
      { class: 'billOfFareList', text: '使命' },
    ],
    [
      { class: 'billOfFareList blue', text: '公司' },
      { class: 'billOfFareList', text: '官网' },
      { class: 'billOfFareList', text: '公众号' },
      { class: 'billOfFareList', text: '抖音号' },
    ],
    [
      { class: 'billOfFareList blue', text: '关于' },
      { class: 'billOfFareList', text: '联系我们' },
      { class: 'billOfFareList', text: '加入我们' },
      { class: 'billOfFareList', text: '教育公益' },
    ],
  ]);
</script>

<style lang="less" scoped>
  .footer {
    color: var(--color-text-2);
    text-align: center;
    .footerContent {
      max-width: 1100px;
      width: 100%;
      margin: 0 auto;
      padding: 50px 30px 0px;
      display: flex;
      justify-content: space-around;
      .rethinking {
        width: 280px;
        height: 140px;
        font-size: 30px;
        font-weight: 600;
        line-height: 36px;
        letter-spacing: 0em;
        text-align: left;
        padding-top: 30px;
        color: rgb(var(--primary-6));
      }
      .rethinkingRight {
        flex: 1;
        margin-left: 60px;
        margin-bottom: 40px;
        display: flex;
        height: 169px;
        justify-content: flex-start;
        .billOfFare {
          width: 180px;
          height: 169px;
          margin-right: 10px;
          .billOfFareList {
            font-size: 15px;
            font-weight: 400;
            line-height: 24px;
            letter-spacing: 0em;
            text-align: left;
            color: var(--color-text-2);
            margin: 5px 0px;
          }
          .blue {
            color: rgb(var(--primary-6));
          }
        }
      }
    }
    .recordNumber {
      font-size: 13px;
      font-weight: 500;
      line-height: 24px;
      letter-spacing: 0em;
      text-align: center;
      color: var(--color-text-2);
      padding-bottom: 30px;
      text-decoration: none;
    }
  }
</style>
