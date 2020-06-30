<template>
    <div id="app">
        <div id="nav" style="margin-top: -30px">
            <!--      <router-link to="/">Hello</router-link> |-->
            <!--      <router-link to="/callservice">Service</router-link> |-->
            <!--      <router-link to="/bootstrap">Bootstrap</router-link> |-->
            <!--      <router-link to="/user">User</router-link> |-->

            <!--      <router-link to="/protected">Protected</router-link> |-->
            <router-link to="/person">Person</router-link>
            |
            <router-link to="/article">Article</router-link>
            |
            <router-link to="/isource">iSource</router-link>
            |
            <!--      <router-link to="/event">Event</router-link> |-->
            <!--      <router-link to="/org">Organization</router-link> |-->
            <router-link to="/country">Country</router-link>
            |
            <router-link to="/language">Language</router-link>
            |
            <router-link to="/movement">Movement</router-link>
            |
            <router-link to="/hashtag">Hashtag</router-link>

            <!--      <router-link to="/login">Login</router-link>-->
        </div>
<!--        <button v-if="loggedInFlag" v-on:click="logout">LOGOUT</button>-->

        <router-view :hellomsg="msg"></router-view>
    </div>
</template>

<script>

    import router from "./router";

    export default {
        name: 'app',
        data() {
            return {
                // loggedInFlag: false,
                msg: 'Welcome to your Vue.js powered Spring Boot App'
            }
        },
        methods: {
            // logout() {
            //     this.$store.dispatch("logout", {}).then(result => {
            //         router.push('/login');
            //     });
            //     console.log("LOGOUT");
            // },

            // getLoggedIn() {
            //     this.loggedInFlag = this.$store.getters.isLoggedIn;
            // }
        },
        // mounted() {
        //     this.loggedInFlag = this.$store.getters.isLoggedIn;
        //     console.log("MOUNTED " + this.loggedInFlag);
        // },
        // computed(){
        //     this.loggedInFlag = this.$store.getters.isLoggedIn;
        //     console.log("COMPUTED " + this.loggedInFlag);
        // }

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

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }
</style>
