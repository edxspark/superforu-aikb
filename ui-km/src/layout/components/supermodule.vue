<template>
  <div v-if="!appStore.menuCollapse" class="supermodule">
    <div class="title">{{ $t('menu.super.module') }}
      <a-space size="large" >
        <a-dropdown position="bl" :hide-on-select="false">
          <IconPlus></IconPlus>
          <template #content>
            <a-doption v-for="item in links" :key="item.id" >
              <div class="dropdown-div"  >
                <div class="icon" :style="{ background: item.color }">
                  <component :is="item.icon" />
                </div>
                <a-typography-paragraph class="text">
                  {{ item.name }}
                </a-typography-paragraph>
                <!--                <text class="vip-state">普通会员</text>-->
                <a-button v-if="item.existStatus == 1" size="mini" status="warning" style="margin-left: 50px"
                  @click="addAndDeleteUserClick(item, 'add')"><icon-plus />添加</a-button>
                <a-button v-else size="mini" status="danger" style="margin-left: 50px"
                  @click="addAndDeleteUserClick(item, 'delete')"><icon-delete />移除</a-button>
              </div>
            </a-doption>
          </template>
        </a-dropdown>
      </a-space>
    </div>
    <ul>
      <li v-for="(item, index) in link" :key="item.name" :class="{ active: selectActive === index }"
        @click="selectChange(item, index)">
        <icon-unordered-list style="margin-right: 10px" />{{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAppStore } from '@/store';
import {
  userConfigList,
  SuperListByUser,
  addUserConfig,
  removeUserConfig
} from '@/api/workbench/index';

const appStore = useAppStore();
const selectActive = ref();
const router = useRouter();
const allocationId = ref('');
const tabName = ref('');

const link = ref<
  {
    id: string;
    name: string;
  }[]
>([]);
const links = ref<
  {
    id: string;
    sort: number;
    name: string;
    code: string;
    icon: string;
    color: string;
    existStatus: number;
  }[]
>([]);
const userConfig = async () => {
  const res = await userConfigList();
  const { userSuperModuleConfigList } = res.data.comUserSuperModuleConfigVo;
  const { id } = res.data.comUserSuperModuleConfigVo;
  link.value = userSuperModuleConfigList;
  allocationId.value = id;
};
const addClick = async () => {
  const res = await SuperListByUser();
  links.value = res.data;
};
onMounted(async () => {
  await userConfig();
  await addClick();
});


const addAndDeleteUserClick = async (item: any, type: any) => {
  const params = {
    id: allocationId.value,
    configValueId: item.id
  };
  if (type === 'add') {
    await addUserConfig(params);
  } else {
    await removeUserConfig(params);
  }
  await userConfig();
  await addClick();
};
function selectChange(item: any, index: number) {
  if (item.openWay === '_self') {
    router.push({
      path: '/sp/sp-node',
      query: {
        url: item.value
      }
    });
  } else if (item.name === '人脸识别') {
    router.push({
      path: '/face/face-recognition',
    })
  }
  else if (item.name === '语音分析') {
    router.push({
      path: '/speech/speech-analysis',
    })
  }
  else if (item.name === '电话机设备') {
    router.push({
      path: '/devices/devices-phone',
    })
  }
  else if (item.name === '敏感词设置') {
    router.push({
      path: '/sensitive/sensitive-setting',
    })
  }
  else {
    window.open(item.value, '_blank');
  }
  tabName.value = item.name;
  selectActive.value = index;
}

</script>

<style scoped lang="less">
.supermodule {
  width: 220px;
  color: var(--color-text-2);
  padding: 4px 8px;
  -ms-overflow-style: none;

  .active {
    color: rgb(var(--primary-6));
  }

  cursor: pointer;

  .title {
    padding: 4px 20px;
    width: 100%;
    font-weight: 500;
    display: flex;
    justify-content: space-between;
  }

  ul {
    height: 300px;
    overflow: auto;
    &::-webkit-scrollbar {
      display: none;
    }

    li {
      font-size: 14px;
      margin-bottom: 14px;
      line-height: 20px;
      list-style: none;

      span {
        border-radius: 3px;
        background: #5865f2;
        padding: 3px 5px;
        font-family: Noto Sans;
        font-size: 10px;
        font-style: normal;
        font-weight: 500;
        line-height: 15px;
        /* 150% */
        text-transform: uppercase;
        margin-left: 10px;
      }
    }
  }
}

.dropdown-div {
  width: 280px;
  display: flex;
  align-items: center;
  // border-bottom: 1px solid rgb(229, 230, 235);
  padding: 8px;

  .icon {
    margin-right: 10px;
    width: 16px;
    height: 16px;
    border-radius: 6px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 3px;
  }

  .text {
    width: 100px;
    font-size: 11.5px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }

  .vip-state {
    color: var(--color-text-2);
    font-size: 9px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
    display: inline-flex;
    padding: 3px 5.5px;
    align-items: center;
    gap: 3px;
    border-radius: 2px;
    border: 1px solid var(--color-neutral-3);
  }
}

:deep(div.arco-typography) {
  margin-bottom: 0px;
}

.active {
  color: rgb(var(--link-6));
}
</style>
