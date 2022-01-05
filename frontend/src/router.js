import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Hello from '@/components/Hello'
import UserAdmin from '@/components/UserAdmin'

import PersonList from '@/components/person/PersonList'
import PersonAdd from '@/components/person/PersonAdd'
import PersonDetails from '@/components/person/PersonDetails'
import PersonDelete from '@/components/person/PersonDelete'

import ArticleList from '@/components/article/ArticleList'
import ArticleDetails from '@/components/article/ArticleDetails'
import ArticleDelete from '@/components/article/ArticleDelete'
import ArticleAdd from '@/components/article/ArticleAdd'

import EventList from '@/components/event/EventList'

import OrgList from '@/components/org/OrgList'
import OrgAdd from '@/components/org/OrgAdd'
import OrgDetails from '@/components/org/OrgDetails'

import ProjectList from '@/components/project/ProjectList'
import ProjectAdd from '@/components/project/ProjectAdd'
import ProjectDetails from '@/components/project/ProjectDetails'

import CountryList from '@/components/country/CountryList'
import CountryDelete from '@/components/country/CountryDelete'
import CountryAdd from '@/components/country/CountryAdd'

import MovementList from '@/components/movement/MovementList'
import MovementAdd from '@/components/movement/MovementAdd'
import MovementEdit from '@/components/movement/MovementEdit'

import LanguageList from '@/components/language/LanguageList';
import LanguageAdd from '@/components/language/LanguageAdd';
import LanguageEdit from '@/components/language/LanguageEdit';

import HashtagList from '@/components/hashtag/HashtagList';

import MaterialTypeList from '@/components/mtype/MaterialTypeList';

import store from './store'
// import HashtagList from "./components/hashtag/HashtagList";

Vue.use(Router);

const router = new Router({
    //
    // mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    mode: 'hash',
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

        {path: '/login', component: Login, meta: {isLoginPage: true}},

        {path: '/', component: Hello},//ArticleList}, //Hello

        {path: '/person', component: PersonList},
        {path: '/person/add', component: PersonAdd, name: 'person-add'},
        {path: '/person/:person_id/edit', component: PersonAdd, name: 'person-add'},
        {path: '/person/:person_id/delete', component: PersonDelete, name: 'person-delete'},
        {path: '/person/:person_id/details', component: PersonDetails, name: 'person-details'},

        {path: '/article', component: ArticleList},
        {path: '/article/:article_id/details', component: ArticleDetails, name: 'article-details'},
        {path: '/article/add', component: ArticleAdd, name: 'article-add'},
        {path: '/article/:article_id/edit', component: ArticleAdd, name: 'article-add'},
        {path: '/article/:article_id/delete', component: ArticleDelete, name: 'article-delete'},

        {path: '/event', component: EventList},

        {path: '/country', component: CountryList},
        {path: '/country/add', component: CountryAdd, name: 'country-add'},
        {path: '/country/:country_id/edit', component: CountryAdd, name: 'country-add'},
        {path: '/country/:country_id/delete', component: CountryDelete, name: 'country-delete'},

        {path: '/movement', component: MovementList, meta: {isAdminPage: true}},
        {path: '/movement/add', component: MovementAdd, name: 'movement-add', meta: {isAdminPage: true}},
        {path: '/movement/:movement_id/edit', component: MovementEdit, name: 'movement-edit', meta: {isAdminPage: true}},

        {path: '/language', component: LanguageList, meta: {isAdminPage: true}},
        {path: '/language/add', component: LanguageAdd, name: 'language-add', meta: {isAdminPage: true}},
        {path: '/language/:language_id/edit', component: LanguageEdit, name: 'language-edit', meta: {isAdminPage: true}},

        {path: '/hashtag', component: HashtagList, meta: {isAdminPage: true}},
        {path: '/hashtag/add', component: HashtagList, name: 'hashtag', meta: {isAdminPage: true}},

        {path: '/mtype', component: MaterialTypeList, meta: {isAdminPage: true}},
        // {path: '/mtype/add', component: MaterialTypeList, name: 'mtype'},

        {path: '/org', component: OrgList},
        {path: '/org/add', component: OrgAdd, name: 'org-add'},
        {path: '/org/:org_id/details', component: OrgDetails, name: 'org-details'},

        {path: '/project', component: ProjectList},
        {path: '/project/add', component: ProjectAdd, name: 'project-add'},
        {path: '/project/:project_id/details', component: ProjectDetails, name: 'project-details'},

        {path: '/uadmin', component: UserAdmin, meta: {isAdminPage: true}},
        // {path: '/uadmin', component: UserAdmin, name: 'user-admin'},

        // otherwise redirect to home
        {path: '/*', redirect: '/'}
    ]
});

//attempt to fix logout status
// router.afterEach((to, from) => {
//     // Use next tick to handle router history correctly
//     // see: https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
//     Vue.nextTick(() => {
//         document.title = to.meta.title || DEFAULT_TITLE;
//     });
// });

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
        console.log("check auth " + store.getters.isLoggedIn);


        // console.log("COOKIES =)", window.$cookies.keys());
        // console.log("COOKIES =))", window.$cookies.get('XSRF-TOKEN'));
        // // console.log("COOKIES", localStorage.getItem('userToken'));
        //
        // console.log("ISLOGGEDIN", store.getters.isLoggedIn);
        //
        //
        // if (!store.getters.isLoggedIn) {
        if (localStorage.getItem('isLoggedIn') !== 'true') {
            next({
                path: '/login'
            })

                 console.log("to login");
        } else {

            if (to.matched.some(record => record.meta.isAdminPage)) {

                console.log("record.meta.isAdminPage");

                if (localStorage.getItem('isAdmin') === 'true') {
                    next();
                }
            } else {
                console.log("record.meta.isAdminPage else");
                next();
            }
        }

    } else {
        console.log("record.meta.isAdminPage else else");
        next(); // make sure to always call next()!
    }
});

const DEFAULT_TITLE = 'АИС ЦМГИ';
router.afterEach((to, from) => {
    // Use next tick to handle router history correctly
    // see: https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
    Vue.nextTick(() => {
        document.title = to.meta.title || DEFAULT_TITLE;
        //console.log("1000000000000000000000000111111", document.title);
    });
});

export default router;