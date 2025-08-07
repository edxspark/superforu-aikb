import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const VERIFICATION: AppRouteRecordRaw = {
  path: '/face/',
  name: 'face',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.faceRecognition',
    icon: 'icon-layers',
    requiresAuth: true,
    order: 7,
    hideInMenu: true,
  },
  children: [
    {
      path: 'face-recognition',
      name: 'face-recognition',
      component: () => import('@/views/faceRecognition/index.vue'),
      meta: {
        locale: 'menu.faceRecognition',
        requiresAuth: true,
        roles: ['*'],
        hideInMenu: true,
      },
    },
  ],
};

export default VERIFICATION;
