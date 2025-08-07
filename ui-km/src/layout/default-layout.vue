<template>
  <a-layout class="layout" :class="{ mobile: appStore.hideMenu }">
    <div v-if="navbar" class="layout-navbar">
      <NavBar />
    </div>
    <a-layout>
      <a-layout>
        <a-layout-sider
          :theme="'dark'"
          v-if="renderMenu"
          v-show="!hideMenu"
          class="layout-sider"
          breakpoint="xl"
          :collapsed="collapsed"
          :collapsible="true"
          :width="menuWidth"
          :style="{ paddingTop: navbar ? '60px' : '' }"
          :hide-trigger="true"
          @collapse="setCollapsed"
        >
          <div class="menu-wrapper">
            <div class="menu-title">
              <img
                src="/assets/logo.svg"
                alt=""
                style="margin-top: 10px; height: 32px;"
                @click="goHomePage()"
              />
            </div>

<!--            <div class="menu-search">-->
<!--              <a-space direction="vertical" size="large" >-->
<!--                <a-input-->
<!--                  :disabled="appStore.menuCollapse"-->
<!--                  class="search"-->
<!--                  :style="{ width: '220px' }"-->
<!--                  :placeholder="$t('menu.search')"-->
<!--                  allow-clear-->
<!--                  @focus="focusSearchClick"-->
<!--                >-->
<!--                  <template #prefix>-->
<!--                    <icon-search style="margin-right: 10px" />-->
<!--                  </template>-->
<!--                </a-input>-->
<!--              </a-space>-->
<!--            </div>-->
            <Menu />
            <Supermodule />
            <setting />
            <a-modal
              v-model:visible="focusSearchState"
              width="1000px"
              :body-style="{paddingTop: '10px'}"
              :footer="false"
            >
              <!-- :disabled="appStore.menuCollapse" -->
              <template #title>
                <a-input-search
                  class="search"
                  :style="{ width: '460px' }"
                  :placeholder="$t('menu.input.search')"
                  allow-clear
                  v-model="searchValue"
                  @search="searchValueChange"
                  @press-enter="searchValueChange"
                >
                </a-input-search>
              </template>
              <div class="search-history">
                <div class="history-list">
                  <div
                    v-for="link in templateList"
                    :key="link.id"
                    @click.stop="templateClick(link)"
                  >
                    {{ link.keyword }}
                  </div>
                </div>
                <a-empty v-if="searchList.length === 0" class="a-empty" />
                <div
                  v-for="(item, index) in searchList"
                  :key="index"
                  v-else
                  class="search-list"
                  @click="searchItemClick(item)"
                >
                  <div class="icon" :style="{ backgroundColor: item.color }">
                    <component :is="item.icon" :style="{width:'50px'}" />
                  </div>
                  <div class="content">
                    <div v-html="item.fileName" class="searchFileTitle"></div>
                    <div v-html="item.fileContent" class="searchFileContent"></div>
                    <div class="searchOtherInfo"> 类型：{{item.linkFileTypeName}} ｜ 最近更新：{{item.lastUpdateTime}}</div>
                  </div>
                </div>
              </div>
            </a-modal>
          </div>
        </a-layout-sider>
        <a-drawer
          v-if="hideMenu"
          :visible="drawerVisible"
          placement="left"
          :footer="false"
          mask-closable
          :closable="false"
          @cancel="drawerCancel"
        >
          <Menu />
        </a-drawer>
        <a-layout class="layout-content" :style="paddingStyle">
          <TabBar v-if="appStore.tabBar" />
          <a-layout-content>
            <PageLayout />
          </a-layout-content>
          <Footer v-if="footer" />
        </a-layout>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
  import { ref, computed, watch, provide, onMounted } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import { useAppStore, useUserStore } from '@/store';
  import { searchHistoryList, fileESSearch } from '@/api/dashboard/index';
  import NavBar from '@/components/NavBar/index.vue';
  import Menu from '@/components/menu/index.vue';
  import Supermodule from '@/layout/components/supermodule.vue';
  import Setting from '@/layout/components/setting.vue';
  import Footer from '@/components/Footer/index.vue';
  import TabBar from '@/components/tab-bar/index.vue';
  import usePermission from '@/hooks/permission';
  import useResponsive from '@/hooks/responsive';
  import PageLayout from './page-layout.vue';

  const isInit = ref(false);
  const searchValue = ref('');
  const appStore = useAppStore();
  const userStore = useUserStore();
  const router = useRouter();
  const route = useRoute();
  const permission = usePermission();
  useResponsive(true);
  const navbarHeight = `60px`;
  const navbar = computed(() => appStore.navbar);
  const renderMenu = computed(() => appStore.menu && !appStore.topMenu);
  const hideMenu = computed(() => appStore.hideMenu);
  const footer = computed(() => appStore.footer);
  const menuWidth = computed(() => {
    return appStore.menuCollapse ? 48 : appStore.menuWidth;
  });
  const collapsed = computed(() => {
    return appStore.menuCollapse;
  });
  const paddingStyle = computed(() => {
    const paddingLeft =
      renderMenu.value && !hideMenu.value
        ? { paddingLeft: `${menuWidth.value}px` }
        : {};
    const paddingTop = navbar.value ? { paddingTop: navbarHeight } : {};
    return { ...paddingLeft, ...paddingTop };
  });
  const setCollapsed = (val: boolean) => {
    if (!isInit.value) return; // for page initialization menu state problem
    appStore.updateSettings({ menuCollapse: val });
  };
  watch(
    () => userStore.role,
    roleValue => {
      if (roleValue && !permission.accessRouter(route))
        router.push({ name: 'notFound' });
    }
  );
  const pagination = ref({ pageSize: 15, current: 1, total: 0 });
  const templateList = ref([]);
  const searchList = ref([]);
  const focusSearchState = ref(false);
  const drawerVisible = ref(false);

  const goHomePage = () => {
    router.push({
      path: 'km',
    });
  };

  const drawerCancel = () => {
    drawerVisible.value = false;
  };

  const searchValueChange = async value => {
    if(searchValue.value==="") {return};
    const res = await fileESSearch(searchValue.value);
    // @ts-ignore
    searchList.value = JSON.parse(res.data);
  };

  const templateClick = (item: any) => {
    searchValue.value = item.keyword;
    searchValueChange(searchValue.value);
  };

  provide('toggleDrawerMenu', () => {
    drawerVisible.value = !drawerVisible.value;
  });
  onMounted(() => {
    isInit.value = true;
  });
  const focusSearchClick = async () => {
    focusSearchState.value = true;
    searchValue.value = '';
    searchList.value = [];
    const params = {
      pageSize: pagination.value.pageSize,
      pageNum: pagination.value.current,
      orderByColumn: 'updateTime',
      isAsc: 'desc'
    };
    const res = await searchHistoryList(params);
    // @ts-ignore
    templateList.value = res.rows;
  };
  const searchItemClick = async (item: any) => {
    window.open(
      `${localStorage.getItem(
        'spf_file_editors_host'
      )}?server=content&mode=edit&fileId=${item.fileId}&kmType=doc`,
      '_blank'
    );
  };
</script>

<style scoped lang="less">
  @nav-size-height: 60px;
  @layout-max-width: 1100px;

  .layout {
    width: 100%;
    height: 100%;
  }

  .layout-navbar {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 100;
    width: 100%;
    height: @nav-size-height;
  }

  .layout-sider {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 99;
    height: 100%;
    transition: all 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
    &::after {
      position: absolute;
      top: 0;
      right: -1px;
      display: block;
      width: 1px;
      height: 100%;
      background-color: var(--color-border);
      content: '';
    }

    > :deep(.arco-layout-sider-children) {
      overflow-y: hidden;
    }
    .arco-menu {
      height: 28% !important;
      position: unset !important;
    }
  }

  .menu-wrapper {
    height: 100%;
    position: relative;
    overflow: auto;
    overflow-x: hidden;
    :deep(.arco-menu) {
      ::-webkit-scrollbar {
        width: 12px;
        height: 4px;
      }

      ::-webkit-scrollbar-thumb {
        border: 4px solid transparent;
        background-clip: padding-box;
        border-radius: 7px;
        background-color: var(--color-text-4);
      }

      ::-webkit-scrollbar-thumb:hover {
        background-color: var(--color-text-3);
      }
    }
  }

  .layout-content {
    min-height: 100vh;
    overflow-y: hidden;
    background-color: var(--color-fill-2);
    transition: padding 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
  }
  .menu-title {
    width: 100%;
    height: 49px;
    color: rgb(var(--primary-6));
    font-size: 24px;
    text-align: center;
    font-style: normal;
    font-weight: 600;
    border-bottom: 1px solid #333335;
    .search {
      padding-left: 18px;
    }
  }
  .menu-search {
    width: 100%;
    height: 48px;
    line-height: 48px;
    .search {
      padding-left: 18px;
      background-color: #232324;
    }
  }
  .search-history {
    height: 520px;
    overflow: auto;
    scrollbar-width: none;
    padding: 0px 30px;
    -ms-overflow-style: none;
    &::-webkit-scrollbar {
      display: none;
    }
    .title {
      color: var(--color-text-2);
      font-size: 18px;
      font-style: normal;
    }
    .history-list {
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 10px;
      div {
        color: var(--color-text-2);
        font-size: 13px;
        font-style: normal;
        margin-right: 16px;
        line-height: normal;
        padding: 2px 20px;
        border-radius: 3px;
        margin: 5px;
        background-color: var(--color-fill-2);
        cursor: pointer;
      }
    }
  }
  .a-empty {
    height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .search-list {
    display: flex;
    height: 70px;
    align-items: center;
    border-bottom: 1px solid var(--color-border);
    .icon {
      width: 36px;
      margin: 20px;
      font-size: 24px;
      display: flex;
      height: 36px;
      border-radius: 12px;
      justify-content: center;
      align-items: center;
    }
    .content {
      padding: 5px;
      .searchFileTitle {
        font-size: 16px;
        font-weight: bold;
        margin-top: 2px;
        margin-bottom: 5px;
      }
      .searchFileContent{
        font-size: 13px;
        font-weight: bold;
        margin-top: 2px;
        //opacity: 0.7;
        color:#86909c;
      }
      .searchOtherInfo {
        font-size: 12px;
        margin-top: 2px;
        font-weight: bold;
        //opacity: 0.7;
        color:#86909c;
      }
    }
  }
  .arco-collapse {
    border: none;
  }
  :deep(.arco-collapse-item-header) {
    background: none;
    border: none;
  }
  :deep(.arco-collapse-item-content) {
    background: none;
  }
  .comment-list {
    height: 105px;
    border-bottom: 1px solid var(--color-bg-5);
  }
  .arco-avatar {
    width: 60px;
    height: 60px;
  }
  :deep(.arco-menu-collapse-button) {
    bottom: 62px;
  }
</style>
