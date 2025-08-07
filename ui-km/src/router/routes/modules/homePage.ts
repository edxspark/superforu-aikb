import { AppRouteRecordRaw } from '../types';

const DASHBOARD: AppRouteRecordRaw = {
  path: '/home',
  name: 'home',
  component: () => import('@/views/homePage/homePage.vue'),
  meta: {
    locale: 'menu.dashboard',
    requiresAuth: true,
    icon: 'icon-align-center',
    order: 3,
    hideInMenu: true,
  },
  children: [
    {
      path: '/home',
      name: 'home',
      component: () => import('@/views/homePage/homePage.vue'),
      meta: {
        locale: 'workplace.repository',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default DASHBOARD;
