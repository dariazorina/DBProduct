import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'

import PersonList from '@/components/person/PersonList'
import PersonAdd from '@/components/person/PersonAdd'
import PersonEdit from '@/components/person/PersonEdit'
import PersonDetails from '@/components/person/PersonDetails'
import PersonDelete from '@/components/person/PersonDelete'

import ArticleList from '@/components/article/ArticleList'
import ArticleDetails from '@/components/article/ArticleDetails'
import ArticleEdit from '@/components/article/ArticleEdit'
import ArticleDelete from '@/components/article/ArticleDelete'
import ArticleAdd from '@/components/article/ArticleAdd'

import EventList from '@/components/event/EventList'
import OrgList from '@/components/org/OrgList'

import CountryList from '@/components/country/CountryList'
import CountryEdit from '@/components/country/CountryEdit'
import CountryDelete from '@/components/country/CountryDelete'
import CountryAdd from '@/components/country/CountryAdd'

import MovementList from '@/components/movement/MovementList'
import MovementAdd from '@/components/movement/MovementAdd'
import MovementEdit from '@/components/movement/MovementEdit'

import LanguageList from '@/components/language/LanguageList';
import LanguageAdd from '@/components/language/LanguageAdd';
import LanguageEdit from '@/components/language/LanguageEdit';

import HashtagList from '@/components/hashtag/HashtagList';

import store from './store'
// import HashtagList from "./components/hashtag/HashtagList";

Vue.use(Router);

const router = new Router({
    // mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        // { path: '/', component: Hello },
        // { path: '/callservice', component: Service },
        // { path: '/bootstrap', component: Bootstrap },
        // { path: '/user', component: User },
        //{ path: '/', component: Login },
        // // {
        //     path: '/protected',
        //     component: Protected,
        //     meta: {
        //         requiresAuth: true
        //     }
        // },
        {
            path: '/login', component: Login,
            meta: {
                isLoginPage: true
            }
        },

        {path: '/person', component: PersonList},
        {path: '/person/add', component: PersonAdd, name: 'person-add'},
        {path: '/person/:person_id/edit', component: PersonEdit, name: 'person-edit'},
        {path: '/person/:person_id/delete', component: PersonDelete, name: 'person-delete'},
        {path: '/person/:person_id/details', component: PersonDetails, name: 'person-details'},


        {path: '/', component: ArticleList},
        {path: '/article/:article_id/details', component: ArticleDetails, name: 'article-details'},
        {path: '/article/add', component: ArticleAdd, name: 'article-add'},
        {path: '/article/:article_id/edit', component: ArticleAdd, name: 'article-add'},
        {path: '/article/:article_id/delete', component: ArticleDelete, name: 'article-delete'},

        {path: '/event', component: EventList},
        {path: '/org', component: OrgList},

        {path: '/country', component: CountryList},
        {path: '/country/add', component: CountryAdd, name: 'country-add'},
        {path: '/country/:country_id/edit', component: CountryEdit, name: 'country-edit'},
        {path: '/country/:country_id/delete', component: CountryDelete, name: 'country-delete'},

        {path: '/movement', component: MovementList},
        {path: '/movement/add', component: MovementAdd, name: 'movement-add'},
        {path: '/movement/:movement_id/edit', component: MovementEdit, name: 'movement-edit'},

        {path: '/language', component: LanguageList},
        {path: '/language/add', component: LanguageAdd, name: 'language-add'},
        {path: '/language/:language_id/edit', component: LanguageEdit, name: 'language-edit'},

        {path: '/hashtag', component: HashtagList},
        {path: '/hashtag/add', component: HashtagList, name: 'hashtag'},


        // otherwise redirect to home
        {path: '*', redirect: '/'}
    ]
});

router.beforeEach((to, from, next) => {
    // if (to.matched.some(record => record.path=== '/login')) {
    //
    //     console.log("***");
    //     next();
    //
    // }


    if (!to.matched.some(record => record.meta.isLoginPage)) { 


        // this route requires auth, check if logged in
        // if not, redirect to login page.
        //console.log("check auth " + store.getters.isLoggedIn);

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