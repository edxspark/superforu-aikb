<template>
  <a-card
    class="general-card"
    :title="$t('workplace.announcement')"
    :header-style="{ paddingBottom: '0' }"
    :body-style="{ padding: '15px 20px 13px 20px' }"
  >
    <!-- <template #extra>
      <a-link>{{ $t('workplace.viewMore') }}</a-link>
    </template> -->
    <div>
      <div v-for="(item, idx) in list" :key="idx" class="item" >
        <a-tag :color="item.type" size="small" :style="{height:'26px'}" >{{ item.label }}</a-tag>
        <span class="item-content" @click="show(item)">
          {{ item.noticeTitle }}<br/>
           {{ item.createTime }}
        </span>

      </div>
    </div>
  </a-card>


  <a-modal
      v-model:visible="visibleModal"
      title-align="start"
      ok-text="已阅"
      cancel-text="关闭"
      width="50%"
  >
    <template #title> {{$t('workplace.announcement')}} </template>
    <div v-html="noticeContent">
    </div>
    <a-tag color="arcoblue">发布时间：{{createTime}}</a-tag>

  </a-modal>


</template>

<script lang="ts" setup>
  import { noticeList } from '@/api/dashboard/index';
  import { onMounted, ref } from 'vue';

  const visibleModal  = ref(false);
  const noticeContent = ref('');
  const createTime    = ref('');
  const list = ref();
  onMounted(async () => {
    const params = {
      pageSize: 7,
      pageNum: 1,
      orderByColumn: '',
      isAsc: ''
    };
    const res = await noticeList(params);
    // @ts-ignore
    const transformedData = res.rows.map((item: any) => ({
      ...item,
      type: item.noticeType === '1' ? 'cyan' : 'orangered',
      label: item.noticeType === '1' ? '通知' : '公告'
    }));
    list.value = transformedData;
  });

  const show = async (item:any) =>{
    visibleModal.value = true;
    noticeContent.value=item.noticeContent;
    createTime.value=item.createTime;
  }
</script>

<style scoped lang="less">
  .item {
    display: flex;
    align-items: center;
    width: 100%;
    height: 36px;
    margin-bottom: 4px;
    .item-content {
      flex: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin-left: 4px;
      color: var(--color-text-2);
      text-decoration: none;
      font-size: 13px;
      cursor: pointer;
    }
  }
</style>
