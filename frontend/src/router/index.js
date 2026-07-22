import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home/index.vue')
  },
  {
    path: '/filter',
    name: 'DataFilter',
    component: () => import('../views/DataFilter/index.vue')
  },
  {
    path: '/recommend',
    name: 'JobRecommend',
    component: () => import('../views/JobRecommend/index.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router