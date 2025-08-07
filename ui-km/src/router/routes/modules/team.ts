import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const team: AppRouteRecordRaw = {
  path: '/team/',
  name: 'team',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.management',
    icon: 'icon-user-group',
    requiresAuth: true,
    order: 5,
    hideInMenu: false,
  },
  children: [
    {
      path: 'team-management',
      name: 'team-management',
      component: () => import('@/views/team/device/index.vue'),
      meta: {
        locale: 'menu.management',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
    {
      path: 'management/detail',
      name: 'management/detail',
      component: () => import('@/views/team/device/detail/index.vue'),
      meta: {
        locale: '团队管理详情',
        requiresAuth: true,
        roles: ['admin'],
        hideInMenu: true,
      },
    },
  ],
};

export default team;
