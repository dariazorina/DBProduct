<template id="country-add">
    <div>
        <h4>Add New Country</h4>
        <div class="unprotected" v-if="errorFlag">
            <h5>Error: {{errors}}</h5>
        </div>
        <form>
            <div class="form-group">
                <label for="add-name">Name</label>
                <input type="text" class="form-control" id="add-name" v-model="country.name"/>
            </div>
            <div class="form-group">
                <label for="add-code">Code</label>
                <input type="number" class="form-control" id="add-code" v-model="country.code"/>
            </div>
            <button type="button" @click="checkForm" class="btn btn-primary">Save</button>
            <a class="btn btn-default"><router-link to="/country">Cancel</router-link></a>
        </form>
    </div>
</template>

<script>
    import api from "./country-api";
    import router from "./../../router";

    export default {
        name: 'country-add',
        data() {
            return {
                errorFlag: false,
                errors: [],
                name: null, //'name1', //country.name,
                code: null, //'666', //country.code,
                country: {}
            }
        },
        methods: {
            checkForm: function (e) {
                console.log('checkForm');
                this.errors = [];
                this.errorFlag = false;

                if (!this.country.name) {
                    this.errorFlag = true;
                    this.errors.push('Укажите имя'); //todo ? оставлять ли (Или сделать проверку на уровне поля)
                }
                if (!this.country.code) {
                    this.errorFlag = true;
                    this.errors.push('Укажите код страны'); //todo //? оставлять ли  (Или сделать проверку на уровне поля)
                } else if (!this.validCode(this.country.code)) {
                    this.errorFlag = true;
                    this.errors.push('Укажите код страны из трех цифр');
                }

                if (!this.errors.length) {
                    this.errorFlag = false;
                    this.createCountry();
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
            createCountry() {
                api.create(this.country, r => {router.push('/country');});
            }
        },
    }
</script>


<!--<template id="country-add">-->
<!--    <div>-->
<!--        <h2>Add new country</h2>-->
<!--        <div class="unprotected" v-if="errorFlag">-->
<!--            <h5>Error: {{errors}}</h5>-->
<!--        </div>-->
<!--        <form>-->
<!--            <div class="form-group">-->
<!--                <label for="add-name">Name</label>-->
<!--                <input class="form-control" id="add-name" v-model="country.name" required/>-->
<!--            </div>-->
<!--            <div class="form-group">-->
<!--                <label for="add-code">Code</label>-->
<!--                <input type="number" class="form-control" id="add-code" v-model="country.code" required/>-->
<!--            </div>-->
<!--            <button type="button" @click="updateCountry" class="btn btn-primary">Create</button>-->
<!--            <a class="btn btn-default">-->
<!--                <router-link to="/country">Cancel</router-link>-->
<!--            </a>-->
<!--        </form>-->
<!--    </div>-->
<!--</template>-->


<!--<script>-->
<!--    import api from "./country-api";-->
<!--    import router from "./../../router";-->

<!--    // export default {-->
<!--    //     name: 'country-add',-->
<!--    //     data() {-->
<!--    //-->
<!--    //         return {-->
<!--    //             errorFlag: false,-->
<!--    //             errors: [],-->
<!--    //             name: null,//'', //country.name,-->
<!--    //             code: null, //0, //country.code,-->
<!--    //             country: []//{}-->
<!--    //         }-->
<!--    //     },-->
<!--    //     methods: {-->
<!--    //         createCountry() {-->
<!--    //             api.update(this.country.id, this.country, r => router.push('/country'))-->
<!--    //             // api.create(this.country, r => router.push('/country'))-->
<!--    //         }-->
<!--    //     },-->


<!--    export default {-->
<!--        name: 'country-add',-->
<!--        data() {-->
<!--            return {-->
<!--                errorFlag: false,-->
<!--                errors: [],-->
<!--                name: null, //country.name,-->
<!--                code: null, //country.code,-->
<!--                country: []//{}-->
<!--            }-->
<!--        },-->
<!--        methods: {-->
<!--            checkForm: function (e) {-->
<!--                console.log('checkForm');-->
<!--                this.errors = [];-->
<!--                this.errorFlag = false;-->

<!--                if (!this.country.name) {-->
<!--                    this.errorFlag = true;-->
<!--                    this.errors.push('Укажите имя'); //todo ? оставлять ли (есть проверка на уровне поля)-->
<!--                }-->
<!--                if (!this.country.code) {-->
<!--                    this.errorFlag = true;-->
<!--                    this.errors.push('Укажите код страны'); //todo //? оставлять ли (есть проверка на уровне поля)-->
<!--                } else if (!this.validCode(this.country.code)) {-->
<!--                    this.errorFlag = true;-->
<!--                    this.errors.push('Укажите код страны из трех цифр');-->
<!--                }-->

<!--                if (!this.errors.length) {-->
<!--                    this.errorFlag = false;-->
<!--                    this.updateCountry();-->
<!--                    return true;-->
<!--                }-->
<!--                e.preventDefault();-->
<!--            },-->

<!--            validCode: function (code) {-->
<!--                var re = /^[0-9]*$/;-->
<!--                var r1 = re.test(code);-->
<!--                var r2 = code.length;-->

<!--                if (r1)-->
<!--                    if (r2 == 3) {-->
<!--                        return true;-->
<!--                    }-->

<!--                return false;-->
<!--                // return (re.test(code)&&this.code.length()==3);-->
<!--            },-->
<!--            updateCountry() {-->
<!--                api.create(this.country, r => {-->
<!--                    // api.create(this.country.id, this.country, r => {-->
<!--                    // console.log(r);-->
<!--                    router.push('/country');-->
<!--                });-->
<!--            }-->
<!--            // findCountry(countryId) {-->
<!--            //     return countries[findCountryKey(countryId)];-->
<!--            // },-->
<!--            //-->
<!--            // findCountryKey(countryId) {-->
<!--            //     for (var key = 0; key < countries.length; key++) {-->
<!--            //         if (countries[key].id == countryId) {-->
<!--            //             return key;-->
<!--            //         }-->
<!--            //     }-->
<!--            // }-->
<!--        },-->

<!--        mounted() {-->
<!--            console.log('mounted');-->
<!--            api.findById(this.$route.params.country_id, r => {-->
<!--                this.country = r.data-->
<!--            });-->
<!--        },-->

<!--    }-->
<!--</script>-->