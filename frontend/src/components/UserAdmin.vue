<template>
    <div class="hello">
        <div class="col-3 col-form-label">

            <p class="greetingsTitle">Welcome, {{loggedName}}!
                <button type="button" v-if="loggedInFlag" class="btnXSmall btn-link" v-b-modal.modal1>Logout
                </button>
                <!--                <b-btn v-if="loggedInFlag" size="xs" variant="btn btn-link  btn-xs"  v-b-modal.modal1>Logout</b-btn>-->
            </p>
            <!-- Modal Component -->
            <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>
        </div>

        <form class="formCreation">
            <b-card style="background-color: transparent">
                <div class="form-row align-items-top">
                    <div class="col-md-4" style="background-color: transparent">
                        <label><b>Выберите пользователя:</b></label>

                        <!--                        <b-form-select v-model="selected" id="search-selection">-->
                        <!--                            <option v-for="item in searchItems" v-bind:value="item">{{item}}</option>-->
                        <!--                        </b-form-select>-->

                        <b-form-select v-model="selectedUser" class="mb-3" id="user-selection" @change="checkMovements">
                            <option v-for="user in allUsers" v-bind:value="user">{{user.login}}</option>
                        </b-form-select>

                        <!--                                                <b-form-select v-model="selectedUser" class="mb-3" id="user-selection" @change="checkMovements">-->
                        <!--                                                    <option v-for="user in allUsers">-->
                        <!--                                                        <label :for="user.id">{{user.login}}</label></option>-->
                        <!--                                                </b-form-select>-->
                    </div>
                    <div class="col-md-8" style="background-color: whitesmoke">
                        <label><b>Выберите движение:</b></label>
                        <div class="col-12" style="background-color: transparent">
                            <div v-for="movement in allMovements">
                                <input v-bind:value="movement.id" name="movement.name" type="checkbox"
                                       v-model="checkedMovements" v-bind:key="keyToRenew"/>
                                <label :for="movement.id"><span>{{movement.name}}</span></label>
                            </div>
                        </div>
                    </div>
                    <div class="col-12">
                        <button type="button" style="margin-right: 20px" @click="saveChanges"
                                class="btn btn-info">Сохранить
                        </button>
                    </div>
                </div>
            </b-card>
        </form>
    </div>
</template>

<script>

    import router from "./../router";
    import apiMovement from "./movement/movement-api";
    import apiLogin from "./login-api";
    import api from "./backend-api";

    export default {
        name: 'user-admin',
        data: () => ({
            loggedInFlag: false,
            loggedName: '',
            // userMovements: [],
            allMovements: [],
            allUsers: [],
            selectedUser: '',
            checkedMovements: [],
            keyToRenew: 0
        }),
        methods: {

            logout() {
                this.$store.dispatch("logout", {}).then(result => {
                    router.push('/login');
                });
            },

            checkMovements() {
               // console.log("SELECT USER", this.selectedUser);
                this.checkedMovements.splice(0);

                if (this.selectedUser.movementList != null) {
                    this.keyToRenew++;
                    for (let i = 0; i < this.selectedUser.movementList.length; i++) {
                        this.checkedMovements.push(this.selectedUser.movementList[i].id);
                    }
                }

             //   console.log("SELECT USER MOV LIST", this.checkedMovements);

            },


            saveChanges() {
                // console.log("CURRENT MOVMNT", this.selectedMovement);
                // this.$store.dispatch("movement_selection", {movement: this.selectedMovement.toString()}).then(result => {
                //     router.push('/article');
                // });

                console.log("save m =============", this.selectedUser, this.checkedMovements);

                api.updateMovement(this.selectedUser.id, this.checkedMovements, response => {
                    router.push('/article');
                }).catch(error => {
                    this.errors.push(error);
                    alert("error");
                });
            }
        },

        mounted() {
            //console.log("~~~~~~~~~~~~~~~~~~hello mounted");

            this.loggedInFlag = localStorage.getItem('isLoggedIn');//this.$store.getters.isLoggedIn;
            this.loggedName = localStorage.getItem('userName');


            api.getAllUsers().then(
                response => {
                    this.allUsers = response.data;
                    console.log("USERS", response.data);
                })
                .catch(
                    error => {
                        console.log("Error: " + error);
                    }
                );

            apiMovement.getAllMovements(response => {
                this.allMovements = response.data;
               // console.log("MOVEMENTS", response.data);
            });
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
