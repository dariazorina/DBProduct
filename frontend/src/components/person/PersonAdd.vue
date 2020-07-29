<template id="person-add">
    <v-app id="inspire">
        <div>
            <div class="form-group row">
                <div class="col-2 col-form-label">
                    <p class="pageCreateTitle">Add New Person</p>
                </div>

                <div class="unprotected" v-if="errorFlag">
                    <h5>Error: {{errors}}</h5>
                </div>
            </div>
        </div>

        <form class="formCreation">
            <div class="row">
                <div class="col-md-9">

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-surname"><b>Фамилия*</b></label>
                            <input class="form-control" id="add-surname" v-model="person.surname"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-surname-rus">Фамилия на русском</label>
                            <input class="form-control" id="add-surname-rus" v-model="person.surnameRus"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-surname-eng">Фамилия на английском</label>
                            <input class="form-control" id="add-surname-eng" v-model="person.surnameEng"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-name"><b>Имя*</b></label>
                            <input class="form-control" id="add-name" v-model="person.name"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-name-rus">Имя на русском</label>
                            <input class="form-control" id="add-name-rus" v-model="person.nameRus"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-name-eng">Имя на английском</label>
                            <input class="form-control" id="add-name-eng" v-model="person.nameEng"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-patronymic">Отчество</label>
                            <input class="form-control" id="add-patronymic" v-model="person.patronymic">
                        </div>

                        <div class="col-md-4">
                            <label>Год рождения</label>
                            <b-form-select v-model="selectedBYear" class="mb-3" id="birth-year-selection">
                                <option v-for="year in years">{{year}}</option>
                            </b-form-select>
                        </div>

                        <div class="col-md-4">
                            <label>Год смерти</label>
                            <b-form-select v-model="selectedDYear" class="mb-3" id="death-year-selection">
                                <option v-for="year in years">{{year}}</option>
                            </b-form-select>
                        </div>
                    </div>
                </div>
                <div class="col-md-2" style="margin-top: 40px">
                    <img src="data:image/jpeg;base64,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"
                         alt="person photo">
                    <!--                    <img src="KB.jpg" alt="person photo" width="500" height="333">-->
                </div>
            </div>

            <!--            //////////////////////////////////////////////////////////////////////////////////////////////-->
            <div class="form-row">
                <div class="col-md-3">
                    <label>Страна</label>
                    <b-form-select v-model="selectedCountry" class="mb-3" id="country-selection">
                        <option v-for="country in allCountries" v-bind:value="country.id">{{country.name}}</option>
                    </b-form-select>
                    <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                </div>

                <div class="col-md-3">
                    <label for="add-settlement">Город</label>
                    <input class="form-control" id="add-settlement" v-model="person.settlement">
                </div>
            </div>
        </form>

        <div style="background-color: transparent; margin-left: 30px">
            <b-card class="col-md-6" style="background-color: #f8f8f8; font-size: small; text-align: left">
                <OccupationList :allOrgs="allOrgs" :selected="selected" @update-occupation="updateOccupation"/>
            </b-card>
        </div>
        <!--        .formCreation{-->
        <!--        vertical-align: text-bottom;-->
        <!--        text-align: left;-->
        <!--        margin-left: 30px;-->
        <!--        /*margin-right: 40px;*/-->
        <!--        font-size: small;-->
        <!--        }-->

        <!--        component doesn't work correctly in form 'formCreation;-->
        <form class="formCreation">
            <div class="form-row">
                <div class="col-md-6">

                    <label for="add-description">Описание</label>
                    <textarea class="form-control" id="add-description" rows="7" v-model="person.description"/>
                </div>
            </div>

            <div class="form-row">
                <div class="col-md-10">
                    <label for="add-description">Форма добавления хештегов</label>
                    <b-card style="width: 80%">
                        <hashtag-list :commonProp="test" @addHashtagToList="addHashtagToList($event)"/>
                    </b-card>
                </div>
            </div>

            <div class="form-row">
                <div class="col-md-6">
                    <label for="add-link">Хештеги</label>
                    <div>
                        <input-tag id="add-hashtag" :add-tag-on-keys="addTagOnKeys"
                                   v-model="tags"></input-tag>

                    </div>
                </div>
            </div>


            <div class="form-row">
                <div class="col-md-6">
                    <label for="add-link">Link</label>
                    <div>
                        <input-tag id="add-link" :add-tag-on-keys="addTagOnKeys"
                                   placeholder="enter links with 'return' or 'tab'" v-model="links"></input-tag>
                    </div>
                </div>
            </div>

            <div class="form-row">
                <div class="col-md-6">
                    <label for="add-misc">Комментарии</label>
                    <div class="green-border-focus">

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="person.miscellany"
                                              background-color="palegreen" required></textarea>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-4">

                    <button type="button" @click="createPerson" class="btn btn-primary">Save</button>
                    <a class="btn btn-default">
                        <router-link to="/person">Cancel</router-link>
                    </a>
                </div>
            </div>
        </form>
    </v-app>
</template>

<script>
    //  require('vue2-autocomplete-js/dist/style/vue2-autocomplete.css')
    import api from "./person-api";
    import apiOrg from "./../org/org-api";
    import apiCountry from "./../country/country-api";

    import router from "./../../router";
    import Vuetify from 'vuetify';
    // import 'vuetify/dist/vuetify.min.css';
    import HashtagList from "./../hashtag/HashtagList.vue";
    import OccupationList from "./OccupationList";

    export default {
        components: {
            OccupationList,
            HashtagList,
        },
        name: 'person-add',
        vuetify: new Vuetify(),

        data: () => ({
            test: "personProp",
            addTagOnKeys: [13, 9],
            descriptionLimit: 60,
            entries: [],
            isLoading: false,
            model: null,
            search: null,

            selected: '',
            selectedM: null,
            selectedCountry: null,
            selectedBYear: null,
            selectedDYear: null,

            tags: [],
            links: [],

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            allCountries: [],
            allOrgs: [],
            // allMovements: [],

            linkList: [],
            hashtagList: [],

            position: '',
            orgId: null,
            occupationWithIndexList: [],
            testList: [],
            person: {hashtagList: [], linkList: [], testList: []},
            years: [],
            //     [
            //     "1990",
            //     "1991"
            // ],
        }),

        methods: {
            initYears() {
                for (let y = 1900; y < 2051; y++) {
                    this.years.push(y);
                }
            },
            updateOccupation(occupation) {
                this.occupationWithIndexList.push(occupation);
                console.log("OCCUPATION PUSH", occupation.orgId, occupation.position);
            },

            addHashtagToList(hashtag) {//from HashtagList
                this.tags = hashtag;
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
                this.addStatus('add-surname', (!this.person.surname));
                if (this.hasError) {
                } else {
                    this.addStatus('add-name', (!this.person.name));
                    // if (this.hasError) {
                    // } else {
                    //     this.addStatus('country-selection', (!this.selectedC));
                    // }
                }

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createPerson() {
                // this.person.movement = {
                //     "id": this.selectedM
                // }; todo

                // this.person.movement = {
                //     "id": 1
                // };

                if (this.selectedBYear){
                    this.person.birthYear = this.selectedBYear;
                }

                if (this.selectedDYear){
                    this.person.deathYear = this.selectedDYear;
                }

                if (this.selectedCountry) {  //otherwise without this check Country entity is created with null fields values and Person can't be saved
                    this.person.country_id = this.selectedCountry;
                }

                for (let i = 0; i < this.links.length; i++) {
                    this.person.linkList[i] = {
                        "content": this.links[i]
                    };
                }

                for (let i = 0; i < this.tags.length; i++) {
                    this.person.hashtagList[i] = this.tags[i];
                }

                this.hasError = false;

                for (let i = 0; i < this.occupationWithIndexList.length; i++) {
                    let a = {
                        "orgId": this.occupationWithIndexList[i].orgId,
                        "position": this.occupationWithIndexList[i].position
                    };
                    console.log("CREATE PERSON A: ", a);
                    this.person.testList.push(a);
                }

                // console.log("PERSON BEFORE SAVING", this.person);

                if (this.formValidate()) {
                    api.create(this.person, r => {
                        console.log(r);
                        router.push('/person');
                    });
                }
            },
        },
        mounted() {

            this.initYears();

            apiCountry.getAllCountries(response => {
                this.allCountries = response.data;
                // console.log(response.data)
            });

            apiOrg.getAllOrgs(response => {
                this.allOrgs = response.data;
                // console.log(" O R G A ", response.data)
            });

            // api.getAllMovements().then(response => {
            //     this.allMovements = response.data;
            //     console.log(response.data)
            // }).catch(error => {
            //     //this.errors.push(error)
            //     console.log(error);
            // })
        },
        computed: {
            items() {
                return this.entries.map(entry => {
                    // const Description = entry.Description.length > this.descriptionLimit
                    //     ? entry.Description.slice(0, this.descriptionLimit) + '...'
                    //     : entry.Description;

                    const surname = entry.surname;
                    return Object.assign({}, entry, {surname})
                })
            },
        },
    }
</script>
