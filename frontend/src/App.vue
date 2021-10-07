<template>
    <div id="app" style="background-color: transparent">

        <div class="col-3" style="padding: 0px 0px 0px; margin: -10px -30px -30px" id="loginStatus">
            <template v-if="loggedName">
                <p class="greetingsTitle">Welcome, {{loggedName}}!
                    <button type="button" class="btnXSmall btn-link" v-b-modal.modal1>Logout
                    </button>
                </p>
                <!-- Modal Component -->
                <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>
            </template>
            <template v-else>
                <template v-if="!loginError">
                    <p class="pageLoginTitle">Please login to get access</p>
                </template>
                <template v-else>
                    <p class="pageLoginErrorTitle">Authentication error, try again</p>
                </template>
            </template>
        </div>

        <div id="nav" style="margin-top: -50px; background-color: transparent">
            <!-----------------------------            the header------------------------------------------->
            <router-link to="/person">Person</router-link>
            |
            <router-link to="/article">Article</router-link>
            |
            <!--      <router-link to="/event">Event</router-link> |-->
            <router-link to="/org">Organization</router-link>
            |
            <router-link to="/country">Location</router-link>
            |
            <router-link to="/language">Language</router-link>
            |
            <router-link to="/movement">Movement</router-link>
            |
            <router-link to="/hashtag">Hashtag</router-link>
            |
            <router-link to="/mtype">Material Type</router-link>
            |
            <router-link to="/uadmin">User Administration</router-link>


            <!--      <router-link to="/login">Login</router-link>-->
        </div>
        <router-view/>
        <!--        <router-view :hellomsg="msg"></router-view>-->
    </div>
</template>

<script>

    import router from "./router";
    import EventBus from "./components/event-bus";

    export default {
        name: 'app',
        // props: ['loginSuccess'],

        watch: {
            $route: {
                immediate: true,
                handler(to, from) {
                    this.loggedName = localStorage.getItem('userName');
                    //console.log("TO FROM", to, from, this.$store.getters.loginError);

                    //todo to output login error (doesn't enter here in this case and refresh status accordingly)
                    //if (this.loggedName === null&&typeof from==='undefined'){
                       // this.loginError = this.$store.getters.loginError;
                   // }

                    // this.loginError = this.$store.getters.loginError;

                    //document.title = to.meta.title || 'Some Default Title';
                }
            },
        },

        data() {
            return {
                msg: 'Welcome to your Vue.js powered Spring Boot App',
                isAdmin: '',
                loggedName: '',
                loginError: false,
            }
        },

        methods: {
            logout() {
                this.$store.dispatch("logout", {}).then(result => {
                    this.loggedName = '';
                    this.loginError = false;
                    router.push('/login');
                    console.log("LOGOUT");
                });
            },
        },


        // window.onload = function () { //ensures the page is loaded before functions are executed.
        //     document.getElementById("carForm").onsubmit = store
        // },

        // beforeCreate(){
        //     this.isAdmin = '';
        //     this.loggedName = '';
        //     this.loginError = false;
        // },

        created (){
           // console.log("+++++++++++++++created", EventBus);
             EventBus.$on('EVENT_NAME', function (payLoad) {

                 this.loginError = !payLoad; //this.$store.getters.loginError;
                 this.loggedName = localStorage.getItem('userName');
                 //console.log("event bus!!!!!!!!!!! err user key", this.loginError, this.loggedName);
            //  doesn't refresh loginError in view... loggedName refreshes due to watching


            //     //Vue.nextTick(callback);
            //         this.$nextTick(() => {
            //             console.log("next tick");
            //             this.loggedName = localStorage.getItem('userName');
            //         });
             });
        },

        mounted() {
          //  console.log("+++++++++++++++mounted");
            this.loggedName = localStorage.getItem('userName');
            this.loginError = this.$store.getters.loginError;
            console.log("loginError mounted app", this.loginError);
            // this.isAdmin = localStorage.getItem('isAdmin');
        },
    }
</script>

<style lang="scss">
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 20px;
    }

    #nav {
        padding: 30px;

        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-active {
                color: #0074D9;
            }
        }
    }
</style>
