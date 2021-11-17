<template>
    <div class="hello">
        <!--        <img src="./../assets/spring-boot-vuejs-logo.png">-->
        <!--        <h1>{{ hellomsg }}</h1>-->

        <form class="formCreation">
            <div class="form-row align-items-center" style="background-color: transparent">

                <div class="form-row align-items-center" style="background-color: transparent">
                    <label><b>Выберите текущее движение:</b></label>
                    <div class="col-12" style="background-color: transparent">
                        <div v-for="movement in userMovements">

                            <!--                            <input v-bind:value="movement" name="movement" type="radio"-->
                            <!--                                   v-model="checkedMovements"/>-->

                            <input style="margin-right: 5px;" v-bind:value="movement.id" name="movement.name" type="checkbox"
                                   v-model="checkedMovements"/>

                            <!--                            <input v-bind:value="movement.name" type="radio"-->
                            <!--                                   v-model="checkedMovements"/>-->

                            <label :for="movement.id"><span>{{movement.name}}</span></label>

                        </div>
                        <div class="col-12">
                            <button type="button" style="margin-right: 20px" @click="saveCurrentMovement"
                                    class="btn btn-info">Сохранить
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <!--    <h2>See the sources here: </h2>-->
        <!--    <ul>-->
        <!--      <li><a href="https://github.com/dariazorina/DBProduct/commits/vuejsFramework" target="_blank">https://github.com/dariazorina/DBProduct/commits/vuejsFramework</a></li>-->
        <!--    </ul>-->
        <!--    <h3>First Version :)</h3>-->
        <!--    <ul>-->
        <!--        <li>HowTo call REST-Services:</li>-->
        <!--        <li><router-link to="/callservice">/callservice</router-link></li>-->
        <!--        <li>HowTo to play around with Bootstrap UI components:</li>-->
        <!--        <li><router-link to="/bootstrap">/bootstrap</router-link></li>-->
        <!--        <li>HowTo to interact with the Spring Boot database backend:</li>-->
        <!--        <li><router-link to="/user">/user</router-link></li>-->
        <!--        <li>Login to the secured part of the application</li>-->
        <!--        <li><router-link to="/login">/login</router-link></li>-->
        <!--        <li>A secured part of this application:</li>-->
        <!--        <li><router-link to="/protected">/protected</router-link></li>-->
        <!--    </ul>-->
    </div>
</template>

<script>

    import router from "./../router";
    import apiMovement from "./movement/movement-api";
    import apiLogin from "./login-api";

    export default {
        name: 'hello',
    //    props: {hellomsg: {type: String, required: true}},
        data: () => ({
            loggedInFlag: false,
            loggedName: '',
            userMovements: [],
            allMovements: [],
            checkedMovements: [],
        }),
        methods: {

            logout() {
                this.$store.dispatch("logout", {}).then(result => {
                    router.push('/login');
                });
            },

            saveCurrentMovement() {
                console.log("CURRENT MOVMNT", this.checkedMovements);
                this.$store.dispatch("movement_selection", {movement: JSON.stringify(this.checkedMovements)}).then(result => {
                    router.push('/article');
                });
            }
        },

        mounted() {
            console.log("~~~~~~~~~~~~~~~~~~hello mounted");

            this.loggedInFlag = localStorage.getItem('isLoggedIn');//this.$store.getters.isLoggedIn;
            this.loggedName = localStorage.getItem('userName');



            apiLogin.getAccount().then(
                response => {
                    console.log("Account retrieved :" + response.data);
                    // console.log(localStorage.getItem('movementSingle'));

                    // if (localStorage.getItem('movementSingle') === 'true') {
                    //     console.log("~~~~~~~~~~~~~~~~~~hello mounted go ARTICLE");
                    //     router.push('/article');
                    // }

                    if (response.data.movementList.length > 1) {
                        // alert("бздынь!");
                        this.userMovements = response.data.movementList;
                        console.log("USER MOVEMENTS", this.userMovements);
                    }

                    apiMovement.getAllMovements(response => {
                        this.allMovements = response.data;
                        console.log("HELLO MOVEMENTS", response.data);
                    });
                }
            ).catch(
                error => {
                    console.log("Error: " + error);
                }
            );


        },
    }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1, h2 {
        font-weight: normal;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
