import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const AISEARCH: AppRouteRecordRaw = {
  path: '/aisearch',
  name: 'aisearch',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.index',
    requiresAuth: false,
    icon: 'IconHome',
    order: 1,
    hideInMenu: false,
  },
  children: [
    {
      path: 'ai-search',
      name: 'ai-search',
      component: () => import('@/views/aiSearch/index.vue'),
      meta: {
        locale: 'menu.index',
        requiresAuth: false,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default AISEARCH;
