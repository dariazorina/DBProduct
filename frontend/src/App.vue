<template>
    <div id="app">
        <div id="nav" style="margin-top: -30px; background-color: transparent">

            <!-----------------------------            the header------------------------------------------->
            <router-link to="/person">P1e2r3s4o5n6</router-link>
            |
            <router-link to="/article">Article</router-link>
            |
            <!--      <router-link to="/event">Event</router-link> |-->
            <router-link to="/org/add">Organization</router-link>
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

            <template v-if="isAdmin==='true'"> |
                <router-link to="/uadmin">User Administration</router-link>
            </template>

            <!--      <router-link to="/login">Login</router-link>-->
        </div>
        <router-view/>
        <!--        <router-view :hellomsg="msg"></router-view>-->
    </div>
</template>

<script>

    import router from "./router";

    export default {
        name: 'app',
        data() {
            return {
                msg: 'Welcome to your Vue.js powered Spring Boot App',
                isAdmin: ''
            }
        },
        methods: {
            // logout() {
            //     this.$store.dispatch("logout", {}).then(result => {
            //         router.push('/login');
            //     });
            //     console.log("LOGOUT");
            // },
        },

        mounted() {
            this.isAdmin = localStorage.getItem('isAdmin');
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
