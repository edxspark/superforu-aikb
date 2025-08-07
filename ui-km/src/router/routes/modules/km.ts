import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const km: AppRouteRecordRaw = {
  path: '/km/',
  name: 'km',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.repository',
    requiresAuth: true,
    icon: 'icon-archive',
    order: 2,
    hideInMenu: true,
  },
  children: [
    {
      path: 'km-workspace',
      name: 'km-workspace',
      component: () => import('@/views/km/device/index.vue'),
      meta: {
        locale: 'menu.repository',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
    {
      path: 'km/detail',
      name: 'km/detail',
      component: () => import('@/views/km/device/detail/index.vue'),
      meta: {
        locale: '详情',
        requiresAuth: true,
        roles: ['admin'],
        hideInMenu: true,
      },
    },
  ],
};

export default km;
