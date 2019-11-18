<template id="country-edit">
    <div>
        <h4>Edit Country</h4>
        <div class="unprotected" v-if="errorFlag">
            <h5>Error: {{errors}}</h5>
        </div>
        <form  >
            <!--        <form @submit="checkForm" action="/country" method="put">-->
            <div class="form-group">
                <label for="edit-name">Name</label>
                <input class="form-control" id="edit-name" v-model="country.name" required/>
            </div>
            <div class="form-group">
                <label for="edit-code">Code</label>
                <input type="number" class="form-control" id="edit-code" v-model="country.code" required/>
            </div>
            <button type="button"  @click="checkForm" class="btn btn-primary">Save</button>
            <a class="btn btn-default">
                <router-link to="/country">Cancel</router-link>
            </a>
        </form>
    </div>
</template>

<script>
    import api from "./country-api";
    import router  from "./../../router";

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
                api.update(this.country.id, this.country, r => {
                    // console.log(r);
                    router.push('/country');
                });
            }

            // findCountry(countryId) {
            //     return countries[findCountryKey(countryId)];
            // },
            //
            // findCountryKey(countryId) {
            //     for (var key = 0; key < countries.length; key++) {
            //         if (countries[key].id == countryId) {
            //             return key;
            //         }
            //     }
            // }
        },

        mounted() {
            console.log('mounted');
            api.findById(this.$route.params.country_id, r => {
                this.country = r.data
            });
        },
    }
</script>