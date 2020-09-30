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

            <form>


                <div class="form-group row align-items-center">
                    <div class="col-12" style="background-color: gainsboro">
                        <label class="col-12 col-form-label labelInCreation">Временная страничка упрощенного добавления организации. Для
                            тестирования позиций в разделе "Персона"</label>
                    </div>
                </div>


                <div class="form-group row align-items-center">

                    <label for="add-name" class="col-1 col-form-label labelInCreation">Название</label>
                    <div class="col-4">
                        <input type="text" class="form-control" id="add-name" v-model="org.name"/>
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

            org: {},
            movements: [],
        }),

        methods: {

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
                    if (this.org.name.length != 0) {
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
                    apiOrg.create(this.org, r => {
                        router.push('/article')
                    }, r => {
                        this.errorFlag = true;
                        this.errors.push(r);
                        console.log(r);
                    });
                }
            },
        },
    }
</script>
