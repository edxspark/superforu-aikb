import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const VERIFICATION: AppRouteRecordRaw = {
  path: '/speech/',
  name: 'speech',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.speechAnalysis',
    icon: 'icon-layers',
    requiresAuth: true,
    order: 8,
    hideInMenu: true,
  },
  children: [
    {
      path: 'speech-analysis',
      name: 'speech-analysis',
      component: () => import('@/views/speechAnalysis/index.vue'),
      meta: {
        locale: 'menu.speechAnalysis',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default VERIFICATION;
