<template id="person-add">
    <v-app id="inspire">
        <div>
            <h3 class="mb-5">Add New Person</h3>
            <div class="unprotected" v-if="errorFlag">
                <h5>Error: {{errors}}</h5>
            </div>

            <div class="form-group row">
                <label for="add-surname" class="col-2 col-form-label">Surname</label>
                <div class="col-10">
                    <input class="form-control" id="add-surname" v-model="person.surname"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-name" class="col-2 col-form-label">Name</label>
                <div class="col-10">
                    <input class="form-control" id="add-name" v-model="person.name"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-patronymic" class="col-2 col-form-label">Patronymic</label>
                <div class="col-10">
                    <input class="form-control" id="add-patronymic" v-model="person.patronymic">
                </div>
            </div>

            <!--            <div class="form-group row">-->
            <!--                <label for="add-" class="col-2 col-form-label"></label>-->
            <!--                <div class="col-10">-->
            <!--                    <input class="form-control" id="add-" v-model="person.">-->
            <!--                </div>-->
            <!--            </div>-->

            <div class="form-group row">
                <label for="add-surname-rus" class="col-2 col-form-label">Фамилия</label>
                <div class="col-10">
                    <input class="form-control" id="add-surname-rus" v-model="person.surnameRus"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-name-rus" class="col-2 col-form-label">Имя</label>
                <div class="col-10">
                    <input class="form-control" id="add-name-rus" v-model="person.nameRus"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-surname-eng" class="col-2 col-form-label">Surname, english</label>
                <div class="col-10">
                    <input class="form-control" id="add-surname-eng" v-model="person.surnameEng"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-name-eng" class="col-2 col-form-label">Name, english</label>
                <div class="col-10">
                    <input class="form-control" id="add-name-eng" v-model="person.nameEng"/>
                </div>
            </div>


            <div class="form-group row">
                <label class="col-2 col-form-label">Country</label>
                <div class="col-10">
                    <b-form-select v-model="selectedC" class="mb-3" id="country-selection">
                        <option v-for="country in allCountries" v-bind:value="country.id">{{country.name}}</option>
                    </b-form-select>
                    <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                </div>
            </div>

            <div class="form-group row">
                <label class="col-2 col-form-label">Movement</label>
                <div class="col-10">
                    <b-form-select v-model="selectedM" class="mb-3" id="movement-selection">
                        <option v-for="mov in allMovements" v-bind:value="mov.id">{{ mov.name }}</option>
                    </b-form-select>
                    <!--                    <div class="mb-3">SelEcted: <strong>{{ selectedM }}</strong></div>-->
                </div>
            </div>

            <div class="form-group row">
                <label for="add-settlement" class="col-2 col-form-label">Settlement</label>
                <div class="col-10">
                    <input class="form-control" id="add-settlement" v-model="person.settlement">
                </div>
            </div>

            <div class="form-group row">
                <label for="add-occupation" class="col-2 col-form-label">Occupation</label>
                <div class="col-10">
                    <input class="form-control" id="add-occupation" v-model="person.occupation">
                </div>
            </div>

            <form>
                <div class="form-group row">

                    <label for="add-hashtag" class="col-2 col-form-label">Hashtag</label>
                    <div class="col-10">
                        <div>
                            <input-tag id="add-hashtag" :add-tag-on-keys="addTagOnKeys"
                                       placeholder="enter hashtags with 'return' or 'tab'" v-model="tags"></input-tag>
                        </div>
                    </div>

                    <label for="add-link" class="col-2 col-form-label">Link</label>
                    <div class="col-10">
                        <div>
                            <input-tag id="add-link" :add-tag-on-keys="addTagOnKeys"
                                       placeholder="enter links with 'return' or 'tab'" v-model="links"></input-tag>
                        </div>
                    </div>

                </div>
            </form>


            <div class="form-group row">
                <label for="add-org" class="col-2 col-form-label">Organization</label>
                <div class="col-10">
                    <input class="form-control" id="add-org"
                           placeholder="Будет реализовано в следующей версии по типу добавления авторов при создании статьи" disabled="disabled">
                </div>
            </div>

            <div class="form-group row">
                <label for="add-description" class="col-2 col-form-label">Description</label>
                <div class="col-10">
                    <input class="form-control" id="add-description" v-model="person.description">
                </div>
            </div>

            <button type="button" @click="createPerson" class="btn btn-primary">Save</button>
            <a class="btn btn-default">
                <router-link to="/person">Cancel</router-link>
            </a>
        </div>
    </v-app>
</template>

<script>
    //  require('vue2-autocomplete-js/dist/style/vue2-autocomplete.css')
    import api from "./person-api";
    import router from "./../../router";
    import Vuetify from 'vuetify';
    // import 'vuetify/dist/vuetify.min.css';

    export default {
        name: 'person-add',
        vuetify: new Vuetify(),

        data: () => ({
            addTagOnKeys: [13, 9],
            descriptionLimit: 60,
            entries: [],
            isLoading: false,
            model: null,
            search: null,

            selectedM: null,
            selectedC: null,
            tags: [],
            links: [],

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            allCountries: [],
            allMovements: [],

            linkList: [],
            hashtagList: [],
            orgList: [],
            person: {hashtagList: [], linkList: []},
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

            formValidate() {
                this.addStatus('add-surname', (!this.person.surname));
                if (this.hasError) {
                } else {
                    this.addStatus('add-name', (!this.person.name));
                    if (this.hasError) {
                    } else {
                        // this.addStatus('add-patronymic', (!this.person.patronymic));
                        // if (this.hasError) {
                        // } else {
                        this.addStatus('add-surname-rus', (!this.person.surnameRus));
                        if (this.hasError) {
                        } else {
                            this.addStatus('add-name-rus', (!this.person.nameRus));
                            if (this.hasError) {
                            } else {
                                this.addStatus('add-surname-eng', (!this.person.surnameEng));
                                if (this.hasError) {
                                } else {
                                    this.addStatus('add-name-eng', (!this.person.nameEng));
                                    if (this.hasError) {
                                    } else {
                                        this.addStatus('country-selection', (!this.selectedC));
                                        if (this.hasError) {
                                        } else {
                                            this.addStatus('movement-selection', (!this.selectedM));
                                            if (this.hasError) {
                                            } else {
                                                this.addStatus('add-settlement', (!this.person.settlement));
                                                if (this.hasError) {
                                                } else {
                                                    this.addStatus('add-occupation', (!this.person.occupation));
                                                    if (this.hasError) {
                                                    } else {
                                                        this.addStatus('add-description', (!this.person.description));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                // }
                            }
                        }
                    }
                }
                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createPerson() {
                this.person.movement = {
                    "id": this.selectedM
                };
                this.person.country = {
                    "id": this.selectedC
                };

                for (let i = 0; i < this.links.length; i++) {
                    this.person.linkList[i] = {
                        "content": this.links[i]
                    };
                }

                for (let i = 0; i < this.tags.length; i++) {
                    this.person.hashtagList[i] = {
                        "content": this.tags[i]
                    };
                }

                // for (let i = 0; i < this.tags.length; i++) {
                //     this.article.orgList[i] = {
                //         "content": this.tags[i]
                //     };
                // }

                this.hasError = false;

                if (this.formValidate()) {
                    api.create(this.person, r => {
                        router.push('/person');
                    });
                }
            },

            // remove (item) {
            //     const index = this.friends.indexOf(item.name)
            //     if (index >= 0) this.friends.splice(index, 1)
            // },
        },
        mounted() {
            console.log('mounted');

            api.getAllCountries().then(response => {
                this.allCountries = response.data;
                console.log(response.data)
            });

            api.getAllMovements().then(response => {
                this.allMovements = response.data;
                console.log(response.data)
            }).catch(error => {
                //this.errors.push(error)
                console.log(error);
            })
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
