import homePageVue from '@/views/homePage/homePage.vue';
<template>
  <a-layout-footer class="homeHeader">
    <div class="homePageLeft">
      <img src="/assets/logo.svg" class="homePageLogo" alt="" />
      <div class="homeHeaderMenu">
        <div class="headerMenu" @click="roll('homePage1')">{{$t('menu.homePage')}}</div>
        <div class="headerMenu" @click="roll('websiteIntroduction')">{{$t('menu.homePage.feature')}}</div>
        <div class="headerMenu" @click="roll('custom')">{{$t('menu.homePage.custom')}}</div>
<!--        <div class="headerMenu" @click="roll('homePage4')">{{$t('menu.homePage.mission')}}</div>-->
<!--         <div class="headerMenu">常见问题</div> -->
      </div>
    </div>
    <div class="homePageRight">
      <ul class="right-side">
        <li>
          <a-tooltip
            :content="$t('settings.language')"
            @select="changeLocale as any"
          >
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
          <a-dropdown trigger="click">
            <div ref="triggerBtn" class="trigger-btn"></div>
            <template #content>
              <a-doption
                v-for="item in locales"
                :key="item.value"
                :value="item.value"
                @click="changeLocale(item.value)"
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
        </li>
        <li class="headerLogoContent">

          <a-dropdown-button style="margin-right: 20px">
            <icon-download /> {{$t('menu.homePage.download')}}
            <template #icon >
              <icon-down />
            </template>
            <template #content>
              <a-doption>{{$t('menu.homePage.download.macos')}}</a-doption>
              <a-doption>{{$t('menu.homePage.download.windows')}}</a-doption>
            </template>
          </a-dropdown-button>

          <a-button type="primary" @click="loginBtn">{{$t('login.form.login')}} | {{$t('register.form.register')}}</a-button>

        </li>
      </ul>
    </div>
  </a-layout-footer>
</template>

<script lang="ts" setup>
  import { computed, ref } from 'vue';
  import { useDark, useToggle } from '@vueuse/core';
  import { useAppStore } from '@/store';
  import { LOCALE_OPTIONS } from '@/locale';
  import useLocale from '@/hooks/locale';
  import { useRouter } from 'vue-router';

  const locales = [...LOCALE_OPTIONS];
  const appStore = useAppStore();
  const router = useRouter();
  const { changeLocale, currentLocale } = useLocale();
  const theme = computed(() => {
    return appStore.theme;
  });
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
  const toggleTheme = useToggle(isDark);
  const handleToggleTheme = () => {
    toggleTheme();
  };
  const triggerBtn = ref();
  const loginBtn = () => {
    router.push({ path: '/login' });
  };


  const roll = (srocll: any) => {
    document.getElementById(srocll)?.scrollIntoView({
      behavior: 'smooth',
      block: 'center',
    });

  };
  const setDropDownVisible = () => {
    const event = new MouseEvent('click', {
      view: window,
      bubbles: true,
      cancelable: true,
    });
    triggerBtn.value.dispatchEvent(event);
  };
</script>

<style lang="less" scoped>
  .homeHeader {
    position: sticky;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 60px;
    color: var(--color-text-2);
    text-align: center;
    border-bottom: 1px solid var(--color-border);
    background-color: var(--color-bg-1);
    z-index: 20;
    .homePageLeft {
      flex: 1;
      text-align: left;
      display: flex;
      padding: 20px 0px 20px 0;
    }
    .homePageRight {
      .right-side {
        display: flex;
        padding-right: 20px;
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
    .homePageLogo {
      height: 42px;
      margin-left: 60px;
    }
    .homeHeaderMenu {
      flex: 1;
      display: flex;
      align-items: center;
      margin-left: 7%;
      margin-top: 10px;
    }

    .headerLogo {
      width: 104px;
      cursor: pointer;
      height: 40px;
    }
    .headerLogoContent {
      margin-left: 40px;
    }
    .headerMenu {
      font-size: 16px;
      font-weight: 500;
      width: 12%;
      min-width: 70px;
      color: var(--color-text-3);
      cursor: pointer;
    }
    .headerMenu:hover {
      color: var(--color-text-1);
    }
  }
</style>
