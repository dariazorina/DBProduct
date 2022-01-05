<template id="country-add">
    <div>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageCreateTitle">Add New Location</p>
            </div>
        </div>

        <div class="unprotected" v-if="errorFlag">
            <div class="col-sm-3">
                <div class="cellTitle"><span class="float-center"><div v-for="err in errors"><p style="color:red">DB message: {{err}}</p></div></span>
                </div>
            </div>
        </div>


        <form class="formCreation">
            <div class="form-group row  align-items-center">
                <label for="add-code" class="col-2 col-form-label labelInCreation"><b>Страна*</b></label>
                <div class="col-4">
                    <input class="form-control" id="add-code" v-model ="location.country"/>
                </div>
            </div>

            <div class="form-group row  align-items-center">
                <label for="add-name" class="col-2 col-form-label labelInCreation">Регион</label>
                <div class="col-4">
                    <input class="form-control" id="add-name" v-model="location.region" required/>
                </div>
            </div>

            <div class="form-group row  align-items-center">
                <label class="col-2 col-form-label labelInCreation">Город/Населенный пункт</label>
                <div class="col-4">
                    <input class="form-control" v-model="location.city" required/>
                </div>
            </div>
            <div class="form-group row  align-items-center">
                <label class="col-2 col-form-label labelInCreation">Адрес</label>
                <div class="col-4">
                    <input class="form-control" v-model="location.address" required/>
                </div>
            </div>
            <div class="form-group row  align-items-center">
                <label class="col-2 col-form-label labelInCreation">Расположение</label>
                <div class="col-4">
                    <input class="form-control" v-model="location.placement" required/>
                </div>
            </div>
            <div class="form-group row  align-items-center">
                <label class="col-2 col-form-label labelInCreation">Комментарии</label>
                <div class="col-4">
                    <input class="form-control" v-model="location.miscellany" required/>
                </div>
            </div>

            <div v-if="editMode" class="form-group row">
                <div class="offset-sm-2 col-sm-3">
                    <button type="button" @click="checkForm" class="btn btn-primary">Update</button>
                    <a class="btn btn-default">
                        <router-link to="/country">Cancel</router-link>
                    </a>
                </div>
            </div>

            <div v-else>
                <div class="offset-sm-2 col-sm-3">
                    <button type="button" @click="checkForm" class="btn btn-primary">Create</button>
                    <a class="btn btn-default">
                        <router-link to="/country">Cancel</router-link>
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
        name: 'country-add',
        data() {
            return {
                errorFlag: false,
                errors: [],
                editMode: false,
                //  name: null, //'name1', //country.name,
                //  code: null, //'666', //country.code,
                location: {}
            }
        },

        mounted() {
            // console.log('mount33333333333333333333ed');
            if (this.$route.params.country_id != null) {
                console.log("EDIT MODE");
                this.editMode = true;
                // this.uploadFilesCheckBoxValue = true;

                api.findById(this.$route.params.country_id, r => {
                    this.location = r.data;
                    console.log('findById', this.location);
                });
            }
        },

        methods: {
            checkForm: function (e) {
                console.log('checkForm');
                this.errors = [];
                this.errorFlag = false;

                if (!this.location.country) {
                    this.errorFlag = true;
                    this.errors.push('Укажите название страны'); //todo ? оставлять ли (Или сделать проверку на уровне поля)
                }
                if (!this.errors.length) {
                    this.errorFlag = false;
                    this.createCountry();
                    return true;
                }
                e.preventDefault();
            },

            createCountry() {
                console.log(this.location);

                if (this.editMode){
                    api.update(this.location.id, this.location, r => {
                        router.push('/country');
                    }, r => {
                        this.errorFlag = true;
                        this.errors.push(r);
                        console.log(r);
                    });
                }
                else {
                    api.create(this.location, r => {
                        router.push('/country')
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