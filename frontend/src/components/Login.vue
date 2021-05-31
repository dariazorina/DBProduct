<template>
    <div>
        <div class="unprotected" v-if="loginError">
            <div class="form-group row">
                <div class="col-2 col-form-label">
                    <p class="pageLoginErrorTitle">Authentication error, try again</p>
                </div>
            </div>
        </div>
        <div class="unprotected" v-else>
            <div class="form-group row">
                <div class="col-2 col-form-label">
                    <p class="pageLoginTitle">Please login to get access</p>
                </div>
            </div>
        </div>
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

    export default {
        name: 'login',

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
            callLogin() {
                this.errors = [];
                this.$store.dispatch("login", {user: this.user, password: this.password})
                    .then(() => {
                        console.log("--login page--");
                        this.$router.push('/')
                        // this.$router.push('/article')
                    })
                    .catch(error => {
                        this.loginError = true;
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