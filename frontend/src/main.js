import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'

// import vuetify from 'vuetify'

Vue.config.productionTip = false;

import Vuetify from 'vuetify';
Vue.use(Vuetify);

// Bootstrap
Vue.use(BootstrapVue);


new Vue({
    router,
//    vuetify,
    store,
    render: h => h(App)
}).$mount('#app');

