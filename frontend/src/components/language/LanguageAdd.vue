<template id="language-add">
    <v-app id="inspire">
        <div>
            <h3 class="mb-5">Add new language</h3>
<!--            <div class="unprotected" v-if="errorFlag">-->
<!--                <h5>Error: {{errors}}</h5>-->
<!--            </div>-->

            <!--       url="http://localhost:8080/api/v1/person"-->
            <!--            http://localhost:8081/article/add-->

            <form>

                <div class="form-group row">
                    <label for="add-code" class="col-2 col-form-label">Language Code</label>
                    <div class="col-10">
                        <input class="form-control" placeholder="Код должен быть уникальным и состоять из трех цифр. В данной версии уникальность кода - ответственность пользователя) " id="add-code" v-model="language.code"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="add-name" class="col-2 col-form-label">Language Name</label>
                    <div class="col-10">
                        <input class="form-control" id="add-name" v-model="language.name" required/>
                    </div>
                </div>


                <button type="button" @click="createLanguage" class="btn btn-primary">Save</button>
                <a class="btn btn-default">
                    <router-link to="/language">Cancel</router-link>
                </a>

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
        name: 'language-add',
        vuetify: new Vuetify(),

        data: () => ({
            // entries: [],
            // isLoading: false,
            // model: null,
            search: null,

            // errorFlag: false,
            // errors: [],
            validationErrors: {},
            hasError: false,

            language: {},
            languages: [],
            selected: [''],
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
                this.addStatus('add-code', (!(this.validCode(this.language.code))));
                if (this.hasError) {
                } else {
                    this.addStatus('add-name', (!this.language.name));
                }

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createLanguage() {
                this.hasError = false;

                if (this.formValidate()) {
                    api.create(this.language, r => {
                        router.push('/language');
                    });
                }
            },
        },
    }
</script>
