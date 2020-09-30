<template id="language-edit">
    <v-app id="inspire">
        <div>
            <div class="form-group row">
                <div class="col-2 col-form-label">
                    <p class="pageEditTitle">Edit Language</p>
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
                <div class="form-group row  align-items-center">
                    <label for="edit-code" class="col-1 col-form-label labelInCreation">Код</label>
                    <div class="col-4">
                        <input type="number" class="form-control" id="edit-code" v-model="language.code"/>
                    </div>
                </div>

                <div class="form-group row  align-items-center">
                    <label for="edit-name" class="col-1 col-form-label labelInCreation">Язык</label>
                    <div class="col-4">
                        <input class="form-control" id="edit-name" v-model="language.name" required/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="offset-sm-2 col-sm-3">
                        <button type="button" @click="updateLanguage" class="btn btn-primary">Save</button>
                        <a class="btn btn-default">
                            <router-link to="/language">Cancel</router-link>
                        </a>
                    </div>
                </div>
            </form>
        </div>
    </v-app>
</template>

<script>

    import api from "./language-api";
    import router from "../../router";
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';

    export default {
        name: 'language-edit',
        vuetify: new Vuetify(),

        data: () => ({
            search: null,

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            language: [],
            // languages: [],
            name: null,
            code: null,
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

            validCode: function (code) {
                var re = /^[0-9]*$/;
                var r1 = re.test(code);
                var r2 = code.length;

                if (r1)
                    if (r2 == 3) {
                        return true;
                    }

                return false;
                // return (re.test(code)&&this.code.length()==3);
            },

            formValidate() {
                this.addStatus('edit-code', (!(this.validCode(this.language.code))));
                if (this.hasError) {
                } else {
                    this.addStatus('edit-name', (!this.language.name));
                }

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            updateLanguage() {
                this.hasError = false;

                if (this.formValidate()) {
                    api.create(this.language, r => {
                        router.push('/language')
                    }, r => {
                        this.errorFlag = true;
                        this.errors.push(r);
                        console.log(r);
                    });
                }
            },
        },
        mounted() {
            console.log('mounted');
            api.findById(this.$route.params.language_id, r => {
                this.language = r.data
            });
        },
    }
</script>
