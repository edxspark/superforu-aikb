import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const KMAI: AppRouteRecordRaw = {
  path: '/kmai',
  name: 'kmai',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.km.ai',
    requiresAuth: false,
    icon: 'icon-align-center',
    order: 5,
    hideInMenu: false,
  },
  children: [
    {
      path: 'km-ai',
      name: 'km-ai',
      component: () => import('@/views/km/ai/index.vue'),
      meta: {
        locale: 'menu.km.ai',
        requiresAuth: false,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default KMAI;
