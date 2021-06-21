<template id="org-add">
    <v-app id="inspire">
        <div>
            <div class="form-group row">
                <div class="col-2 col-form-label">
                    <p class="pageCreateTitle">Add New Organization</p>
                </div>
            </div>
            <!--            <div class="unprotected" v-if="errorFlag">-->
            <!--                <h5>Error: {{errors}}</h5>-->
            <!--            </div>-->

            <!--       url="http://localhost:8080/api/v1/person"-->
            <!--            http://localhost:8081/article/add-->

            <div class="unprotected" v-if="errorFlag">

                <div class="col-sm-3">
                    <div class="cellTitle"><span class="float-center"><div v-for="err in errors"><p style="color:red">DB message: {{err}}</p></div></span>
                    </div>
                </div>

            </div>

            <form class="formCreation">
                <div class="form-group row align-items-center">
                    <div class="col-12" style="background-color: gainsboro">
                        <label class="col-12 col-form-label labelInCreation">Временная страничка упрощенного добавления
                            организации. Для
                            тестирования позиций в разделе "Персона"</label>
                    </div>
                </div>


                <div class="form-group row align-items-center">

                    <label for="add-name" class="col-1 col-form-label labelInCreation">Название</label>
                    <div class="col-4">
                        <input type="text" class="form-control" id="add-name" v-model="org.name"/>
                    </div>
                </div>


                <div class="form-row align-items-center" style="background-color: transparent">
                    <div v-if="isAdmin==='true'||addAdditionalMovementFlag">
                        <div class="col-12" style="background-color: transparent">
                            <label>Текущее движение: {{currentUserMovement.name}} </label><br>
                        </div>
                        <label><b>Добавить дополнительное движение:</b></label>
                        <div class="col-12" style="background-color: transparent">
                            <div v-for="(movement, index) in allMovements">
                                <input v-bind:value="movement.id" name="movement.name" type="checkbox"
                                       v-model="checkedMovements"/>
                                <!--                                    :hidden="movement.id === checkedMovements[0]"                                            :key="toKey"/>-->
                                <label :for="movement.id"><span>{{" . " + movement.name}}</span></label>
                            </div>
                        </div>
                    </div>

                    <div v-else class="col-12">
                        <button type="button" style="margin-right: 20px" @click="addAdditionalMovement"
                                class="btn btn-info">Добавить дополнительное движение
                        </button>
                    </div>
                </div>


                <div class="form-group row">
                    <div class="col-4">

                        <button type="button" @click="createOrg" class="btn btn-primary">Create</button>
                        <a class="btn btn-default">
                            <router-link to="/article">Cancel</router-link>
                        </a>
                    </div>
                </div>

            </form>
        </div>
    </v-app>
</template>

<script>

    import apiOrg from "./org-api";
    import apiMovement from "./../movement/movement-api";
    import router from "./../../router";
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';
    import LanguageAdd from "../language/LanguageAdd";

    export default {
        name: 'org-add',
        components: {LanguageAdd},
        vuetify: new Vuetify(),

        data: () => ({
            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            org: {movementList: []},
            allMovements: [],
            isAdmin: null,
            addAdditionalMovementFlag: false,
            currentUserMovement: '',
            checkedMovements: []
        }),

        mounted() {
            this.isAdmin = localStorage.getItem('isAdmin');

            apiMovement.getAllMovements(response => {
                // this.getLoggedIn();
                this.allMovements = response.data;
                this.currentUserMovement = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));//this.checkedMovements[0]);

                let currentIndex = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));//this.checkedMovements[0]);
                let ddd = this.allMovements.indexOf(currentIndex);
                this.allMovements.splice(ddd, 1);
                //console.log("MOVEMENTS index", response.data, currentIndex, ddd);
            });

        },

        methods: {
            addAdditionalMovement() {
                this.addAdditionalMovementFlag = true;
            },

            addStatus(id, hasError) {
                document.getElementById(id).classList.remove('is-valid');
                document.getElementById(id).classList.remove('is-invalid');

                if (hasError) {
                    document.getElementById(id).classList.add('is-invalid');
                } else {
                    document.getElementById(id).classList.add('is-valid');
                }
                this.hasError = this.hasError || hasError;
            },

            formValidate() {
                if (this.org.name != null) {
                    if (this.org.name.length !== 0) {
                        this.addStatus('add-name', (!this.org.name));
                    } else this.hasError = true;
                } else this.hasError = true;

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createOrg() {
                this.hasError = false;

                if (this.formValidate()) {

                    let i = 0;
                    for (; i < this.checkedMovements.length; i++) {
                        this.org.movementList[i] = {
                            "id": this.checkedMovements[i]
                        };
                    }

                    this.org.movementList[i] = {
                        "id": this.currentUserMovement.id
                    };

                    apiOrg.create(this.org, r => {
                            router.push('/article')
                        },
                        r => {
                            this.errorFlag = true;
                            this.errors.push(r);
                            console.log(r);
                        });
                }
            },
        },
    }
</script>
