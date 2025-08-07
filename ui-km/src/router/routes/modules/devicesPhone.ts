import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const VERIFICATION: AppRouteRecordRaw = {
  path: '/devices/',
  name: 'devices',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.devicesPhone',
    icon: 'icon-layers',
    requiresAuth: true,
    order: 7,
    hideInMenu: true,
  },
  children: [
    {
      path: 'devices-phone',
      name: 'devices-phone',
      component: () => import('@/views/devicesPhone/index.vue'),
      meta: {
        locale: 'menu.devicesPhone',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default VERIFICATION;
