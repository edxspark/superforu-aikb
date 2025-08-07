<template>
  <div class="tab-bar-container">
    <a-affix ref="affixRef" :offset-top="offsetTop">
      <div class="tab-bar-box">
        <div class="tab-bar-scroll">
          <div class="tags-wrap">
            <tab-item
              v-for="(tag, index) in tagList"
              :key="tag.fullPath"
              :index="index"
              :item-data="tag"
            />
          </div>
        </div>
        <div class="tag-bar-operation">
          <ul class="right-side">
<!--            <li>-->
<!--              <a-button type="text" status="warning" @click="vipModalHandle">-->
<!--                <icon-fire /> {{$t('km.upgrade')}}-->
<!--              </a-button>-->
<!--              <VipModal v-model:isVipModel="vipModel" />-->
<!--            </li>-->
            <li>
              <a-tooltip :content="$t('settings.language')">
                <a-button
                  class="nav-btn"
                  type="outline"
                  :shape="'circle'"
                  @click="setDropDownVisible"
                >
                  <template #icon>
                    <icon-language />
                  </template>
                </a-button>
              </a-tooltip>
              <a-dropdown trigger="click" @select="changeLocale as any">
                <div ref="triggerBtn" class="trigger-btn"></div>
                <template #content>
                  <a-doption
                    v-for="item in locales"
                    :key="item.value"
                    :value="item.value"
                  >
                    <template #icon>
                      <icon-check v-show="item.value === currentLocale" />
                    </template>
                    {{ item.label }}
                  </a-doption>
                </template>
              </a-dropdown>
            </li>
            <li>
              <a-tooltip
                :content="
                  theme === 'light'
                    ? $t('settings.navbar.theme.toDark')
                    : $t('settings.navbar.theme.toLight')
                "
              >
                <a-button
                  class="nav-btn"
                  type="outline"
                  :shape="'circle'"
                  @click="handleToggleTheme"
                >
                  <template #icon>
                    <icon-moon-fill v-if="theme === 'dark'" />
                    <icon-sun-fill v-else />
                  </template>
                </a-button>
              </a-tooltip>
            </li> </ul
        ></div>
      </div>
    </a-affix>
  </div>
</template>

<script lang="ts" setup>
  import { ref, computed, watch, inject, onUnmounted } from 'vue';
  import { Message } from '@arco-design/web-vue';
  import { useDark, useToggle, useFullscreen } from '@vueuse/core';
  import { useAppStore, useTabBarStore, useUserStore } from '@/store';
  import { LOCALE_OPTIONS } from '@/locale';
  import useUser from '@/hooks/user';
  import useLocale from '@/hooks/locale';
  import type { RouteLocationNormalized } from 'vue-router';
  import {
    listenerRouteChange,
    removeRouteListener,
  } from '@/utils/route-listener';
  import VipModal from '@/components/vip-modal/index.vue';
  import tabItem from './tab-item.vue';

  const appStore = useAppStore();
  const userStore = useUserStore();
  const { logout } = useUser();
  const { changeLocale, currentLocale } = useLocale();

  const tabBarStore = useTabBarStore();
  const locales = [...LOCALE_OPTIONS];
  const theme = computed(() => {
    return appStore.theme;
  });
  const vipModel = ref(false);
  const topMenu = computed(() => appStore.topMenu && appStore.menu);
  const isDark = useDark({
    selector: 'body',
    attribute: 'arco-theme',
    valueDark: 'dark',
    valueLight: 'light',
    storageKey: 'arco-theme',
    onChanged(dark: boolean) {
      // overridden default behavior
      appStore.toggleTheme(dark);
    },
  });

  const vipModalHandle = () =>{
    vipModel.value=true;
  }

  const toggleTheme = useToggle(isDark);
  const handleToggleTheme = () => {
    toggleTheme();
  };
  const setVisible = () => {
    appStore.updateSettings({ globalSettings: true });
  };
  const refBtn = ref();
  const triggerBtn = ref();
  const setPopoverVisible = () => {
    const event = new MouseEvent('click', {
      view: window,
      bubbles: true,
      cancelable: true,
    });
    refBtn.value.dispatchEvent(event);
  };
  const handleLogout = () => {
    logout();
  };
  const setDropDownVisible = () => {
    const event = new MouseEvent('click', {
      view: window,
      bubbles: true,
      cancelable: true,
    });
    triggerBtn.value.dispatchEvent(event);
  };
  const switchRoles = async () => {
    const res = await userStore.switchRoles();
    Message.success(res as string);
  };
  const toggleDrawerMenu = inject('toggleDrawerMenu') as () => void;
  const affixRef = ref();
  const tagList = computed(() => {
    return tabBarStore.getTabList;
  });
  const offsetTop = computed(() => {
    return appStore.navbar ? 60 : 0;
  });

  watch(
    () => appStore.navbar,
    () => {
      affixRef.value.updatePosition();
    }
  );
  listenerRouteChange((route: RouteLocationNormalized) => {
    if (
      !route.meta.noAffix &&
      !tagList.value.some((tag) => tag.fullPath === route.fullPath)
    ) {
      tabBarStore.updateTabList(route);
    }
  }, true);

  onUnmounted(() => {
    removeRouteListener();
  });
</script>

<style scoped lang="less">
  .tab-bar-container {
    position: relative;
    background-color: var(--color-bg-2);
    .tab-bar-box {
      display: flex;
      padding: 0 0 0 20px;
      background-color: var(--color-bg-2);
      .tab-bar-scroll {
        flex: 1;
        overflow: hidden;
        .tags-wrap {
          display: flex;
          align-items: flex-end;
          padding: 4px 0;
          height: 48px;
          white-space: nowrap;
          overflow-x: auto;

          :deep(.arco-tag) {
            display: inline-flex;
            align-items: center;
            margin-right: 6px;
            cursor: pointer;
            &:first-child {
              .arco-tag-close-btn {
                display: none;
              }
            }
          }
        }
      }
    }

    .tag-bar-operation {
      width: 200px;
      height: 32px;
      .right-side {
        display: flex;
        padding-right: 20px;
        height: 20px;
        list-style: none;
        :deep(.locale-select) {
          border-radius: 20px;
        }
        li {
          display: flex;
          align-items: center;
          padding: 0 10px;
        }

        a {
          color: var(--color-text-1);
          text-decoration: none;
        }
        .nav-btn {
          border-color: rgb(var(--gray-2));
          color: rgb(var(--gray-8));
          font-size: 16px;
        }
        .trigger-btn,
        .ref-btn {
          position: absolute;
          bottom: 14px;
        }
        .trigger-btn {
          margin-left: 14px;
        }
      }
    }
  }
</style>
