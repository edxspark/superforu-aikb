import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const superModel: AppRouteRecordRaw = {
  path: '/sp/',
  name: 'sp',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.superModel',
    icon: 'icon-layers',
    requiresAuth: true,
    order: 6,
    hideInMenu: true,
  },
  children: [
    {
      path: 'sp-node',
      name: 'sp-node',
      component: () => import('@/views/superModel/index.vue'),
      meta: {
        locale: 'menu.superModel',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default superModel;
