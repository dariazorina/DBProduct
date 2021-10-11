<template>
    <div style="background-color: transparent">
        <div>
            <form class="pageLoginForm" @submit.prevent="callLogin()">

                <div class="form-group row align-items-center">
                    <label for="add-login" class="col-1 col-form-label labelInCreation">Login</label>
                    <div class="col-3">
                        <input type="text" placeholder="username" class="form-control" id="add-login" v-model="user"/>
                    </div>
                </div>

                <div class="form-group row align-items-center">
                    <label for="add-pass" class="col-1 col-form-label labelInCreation">Password</label>
                    <div class="col-3">
                        <input type="password" placeholder="password" class="form-control" id="add-pass"
                               v-model="password"/>
                    </div>
                </div>


<!--                <div class="form-group row align-items-center">-->
<!--                    <label for="add-pass" class="col-1 col-form-label labelInCreation">Remember Me:</label>-->
<!--                    <div class="col-3"><input type="checkbox" name="remember-me" /></div>-->
<!--                </div>-->


                <div class="form-group row">
                    <div class="offset-sm-1 col-sm-3">
                        <b-btn size="sm" variant="btn btn-outline-info" type="submit">Login</b-btn>
                        <!--                    <p v-if="error" class="error">Bad login information</p>-->
                    </div>
                </div>


                <!--        <form class="pageLoginForm" @submit.prevent="callLogin()">-->
                <!--            <input type="text" placeholder="username" v-model="user">-->
                <!--            <input type="password" placeholder="password" v-model="password">-->
                <!--            <b-btn size="sm" variant="btn btn-outline-success" type="submit">Login</b-btn>-->
                <!--            <p v-if="error" class="error">Bad login information</p>-->
            </form>
        </div>
    </div>
</template>

<script>

    import Vue from 'vue';
    import EventBus from './event-bus';
  //  import InputTextField from "./components/multiple-enter-list/InputTextField";

    export default {
        name: 'login',
     //   components: {InputTextField},
        data() {
            return {
                loginError: false,
                user: '',
                password: '',
                error: false,
                errors: []
            }
        },
        methods: {

            emitMethod() {
                //console.log("+++++++++++++++++++++++++++++++++++++++++++++emit");
                EventBus.$emit('EVENT_NAME', this.loginSuccess);
            },

            callLogin() {
                this.errors = [];
                this.$store.dispatch("login", {user: this.user.toLowerCase(), password: this.password})
                    .then(() => {
                        //console.log("--login page--");
                        this.loginSuccess = true;

                        this.emitMethod();

                        this.$router.push('/')
                        // this.$router.push('/article')
                    })
                    .catch(error => {
                        this.loginError = true;
                        this.loginSuccess = false;

                        this.emitMethod();

                        this.errors.push(error);
                        this.error = true
                    })
            }
        },
        mounted() {
            //const moment = require('moment');
            //let now = moment();
            //Vue.prototype.endDate = now.format('YYYY-MM-DD');
            //now.subtract('1', 'months');
        //    console.log(`Subtracting 1 m: ${now.format('ll')}`);
            //Vue.prototype.startDate = now.format('YYYY-MM-DD');
           // console.log("VUE PROTOTYPE", Vue.prototype, this.$store);
        }
    }
</script>