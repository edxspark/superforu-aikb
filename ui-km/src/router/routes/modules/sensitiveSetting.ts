import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const VERIFICATION: AppRouteRecordRaw = {
  path: '/sensitive/',
  name: 'sensitive',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.sensitiveSetting',
    icon: 'icon-layers',
    requiresAuth: true,
    order: 7,
    hideInMenu: true,
  },
  children: [
    {
      path: 'sensitive-setting',
      name: 'sensitive-setting',
      component: () => import('@/views/sensitiveSetting/index.vue'),
      meta: {
        locale: 'menu.sensitiveSetting',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default VERIFICATION;
