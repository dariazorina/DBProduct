<template id="country-edit">
    <div>
        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageEditTitle">Edit Country</p>
            </div>
        </div>

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
                    <input type="number" class="form-control" id="edit-code" v-model="country.code"/>
                </div>
            </div>

            <div class="form-group row  align-items-center">
                <label for="edit-name" class="col-1 col-form-label labelInCreation">Название</label>
                <div class="col-4">
                    <input class="form-control" id="edit-name" v-model="country.name" required/>
                </div>
            </div>

            <div class="form-group row">
                <div class="offset-sm-2 col-sm-3">
                    <button type="button" @click="checkForm" class="btn btn-primary">Save</button>
                    <a class="btn btn-default">
                        <router-link to="/language">Cancel</router-link>
                    </a>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
    import api from "./country-api";
    import router from "./../../router";

    export default {
        name: 'country-edit',
        data() {
            return {
                errorFlag: false,
                errors: [],
                name: null, //country.name,
                code: null, //country.code,
                country: []//{}
            }
        },
        methods: {
            checkForm: function (e) {
                console.log('checkForm');
                this.errors = [];
                this.errorFlag = false;

                if (!this.country.name) {
                    this.errorFlag = true;
                    this.errors.push('Укажите имя'); //todo ? оставлять ли (есть проверка на уровне поля)
                }
                if (!this.country.code) {
                    this.errorFlag = true;
                    this.errors.push('Укажите код страны'); //todo //? оставлять ли (есть проверка на уровне поля)
                } else if (!this.validCode(this.country.code)) {
                    this.errorFlag = true;
                    this.errors.push('Укажите код страны из трех цифр');
                }

                if (!this.errors.length) {
                    this.errorFlag = false;
                    this.updateCountry();
                    return true;
                }
                e.preventDefault();
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

            updateCountry() {
                api.create(this.country, r => {
                    router.push('/country')
                }, r => {
                    this.errorFlag = true;
                    this.errors.push(r);
                    console.log(r);
                });
            },
        },
        mounted() {
            console.log('mounted');
            api.findById(this.$route.params.country_id, r => {
                this.country = r.data
            });
        },
    }
</script>