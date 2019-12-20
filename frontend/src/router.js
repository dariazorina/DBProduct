import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
import Bootstrap from '@/components/Bootstrap'
import User from '@/components/User'
import Login from '@/components/Login'
import Protected from '@/components/Protected'

import PersonList from     '@/components/person/PersonList'
import PersonAdd from      '@/components/person/PersonAdd'
import PersonEdit from     '@/components/person/PersonEdit'
import PersonDelete from   '@/components/person/PersonDelete'


import ArticleList from         '@/components/article/ArticleList'
import ArticleDetails from      '@/components/article/ArticleDetails'
import ArticleEdit from         '@/components/article/ArticleEdit'
import ArticleDelete from       '@/components/article/ArticleDelete'
import ArticleAdd from          '@/components/article/ArticleAdd'

import EventList from   '@/components/event/EventList'
import OrgList from     '@/components/org/OrgList'


import CountryList from     '@/components/country/CountryList'
import CountryEdit from     '@/components/country/CountryEdit'
import CountryDelete from   '@/components/country/CountryDelete'
import CountryAdd from      '@/components/country/CountryAdd'

import store from './store'

Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        { path: '/', component: Hello },
        // { path: '/callservice', component: Service },
        // { path: '/bootstrap', component: Bootstrap },
        // { path: '/user', component: User },
        // { path: '/login', component: Login },
        // // {
        //     path: '/protected',
        //     component: Protected,
        //     meta: {
        //         requiresAuth: true
        //     }
        // },


        { path: '/person',  component: PersonList },
        { path: '/person/add', component: PersonAdd, name :'person-add' },
        { path: '/person/:person_id/edit', component: PersonEdit, name :'person-edit' },
        { path: '/person/:person_id/delete', component: PersonDelete, name :'person-delete' },


        { path: '/article',  component: ArticleList },
        { path: '/article/:article_id/details',  component: ArticleDetails, name :'article-details' },
        { path: '/article/add', component: ArticleAdd, name :'article-add' },
        { path: '/article/:article_id/edit', component: ArticleEdit, name :'article-edit' },
        { path: '/article/:article_id/delete', component: ArticleDelete, name :'article-delete' },

        { path: '/event',  component: EventList },
        { path: '/org',  component: OrgList },

        { path: '/country', component: CountryList },
        { path: '/country/add', component: CountryAdd, name :'country-add' },
        { path: '/country/:country_id/edit', component: CountryEdit, name :'country-edit' },
        { path: '/country/:country_id/delete', component: CountryDelete, name :'country-delete' },

        // otherwise redirect to home
        { path: '*', redirect: '/' }
    ]
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            next({
                path: '/login'
            })
        } else {
            next();
        }
    } else {
        next(); // make sure to always call next()!
    }
});

export default router;