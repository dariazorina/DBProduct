<template id="article-add">
    <v-app id="inspire">
        <div>
            <div class="form-group row">
                <div class="col-2 col-form-label">
                    <p class="pageCreateTitle">Add New Article</p>
                </div>

                <div class="unprotected" v-if="errorFlag">
                    <h5>Error: {{errors}}</h5>
                </div>
            </div>

            <!--            <h3 class="mb-5">Add new article</h3>-->
            <!--            <div class="unprotected" v-if="errorFlag">-->
            <!--                <h5>Error: {{errors}}</h5>-->
            <!--            </div>-->
            <!--       url="http://localhost:8080/api/v1/person"-->
            <!--            http://localhost:8081/article/add-->
            <!--            <form>-->

            <form class="formCreation">
                <div class="row align-items-center">
                    <label for="add-url" class="col-1 col-form-label"><b>URL*</b></label>
                    <div class="col-4">
                        <input class="form-control" id="add-url" type="url" v-model="article.url" required/>
                    </div>
                </div>

                <div class="row align-items-center">
                    <label class="col-1 col-form-label"><b>Язык*</b></label>
                    <div class="col-4">
                        <b-form-select v-model="selectedL" class="mb-3" id="language-selection">
                            <option v-for="lang in allLanguages" v-bind:value="lang.id">{{lang.name}}</option>
                        </b-form-select>
                        <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                    </div>
                </div>
                <div class="row align-items-center">
                    <label for="date-input" class="col-1 col-form-label"><b>Дата*</b></label>
                    <div class="col-4">
                        <input class="form-control" type="date" id="date-input" v-model="article.date">
                    </div>
                </div>
            </form>

            <form class="authorsFormCreation">
                <div class="row align-items-center authorsPadding">
                    <label for="author-autocomplete" class="col-1 col-form-label"> </label>
                    <div class="col-4">
                        <v-card-text>
                            <v-autocomplete

                                    id="author-autocomplete"
                                    label="Авторы"

                                    :items="items"
                                    :loading="isLoading"
                                    :search-input.sync="search"
                                    color="green"
                                    hide-no-data
                                    hide-selected

                                    v-model="selected"

                                    @change="addAuthor(selected)"
                                    @focus="testFocus(selected)"
                                    item-text="surname"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти автора"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>


                    <!--                    <label class="col-1 col-form-label labelInCreation">Author</label>-->
                    <!--                    <div class="">-->
                    <ul class="list-group col-sm-5" order="1">
                        <li v-for="author in this.article.authorList"
                            class="list-group-item d-flex justify-content-between align-items-center">
                            {{ author.surname }}
                            <span class="close" @click="deleteAuthor(author)">&times;</span>
                        </li>
                    </ul>
                    <!--                    </div>-->


                </div>
            </form>
            <form class="formCreation">
                <div class="row align-items-center">
                    <label for="add-title" class="col-1 col-form-label">Заголовок</label>
                    <div class="col-4">
                        <input class="form-control" id="add-title" v-model="article.title"/>
                    </div>
                </div>

                <div class="row align-items-center">
                    <label for="add-title-rus" class="col-1 col-form-label">Заголовок на русском</label>
                    <div class="col-4">
                        <input class="form-control" id="add-title-rus" v-model="article.titleRus" required/>
                    </div>
                </div>

                <div class="row align-items-center">
                    <label for="add-descr" class="col-1 col-form-label">Описание</label>
                    <div class="col-4">
                        <textarea class="form-control" id="add-descr" rows="6" v-model="article.description" required/>
                    </div>
                </div>

                <div class="row align-items-center">
                    <label for="add-hashtag" class="col-1 col-form-label">Хештеги</label>
                    <div class="col-4">
                        <div>
                            <input-tag id="add-hashtag" :add-tag-on-keys="addTagOnKeys"
                                       placeholder="enter hashtags with 'return' or 'tab'" v-model="tags"></input-tag>
                        </div>
                    </div>
                </div>

                <!--                    <div class="form-group purple-border"> purple body always wish purple shadow, mi-mi-mi-->
                <div class="form-group green-border-focus">
                    <div class="row align-items-center">
                        <label for="add-misc" class="col-1 col-form-label">Комментарии</label>
                        <div class="col-4">
                        <textarea class="form-control" id="add-misc" rows="6" v-model="article.miscellany"
                                  background-color="palegreen" required/>
                        </div>
                    </div>
                </div>


                <!--                <div class="form-group row align-items-center">-->
                <!--                    <label for="add-link" class="col-1 col-form-label labelInCreation">Link</label>-->
                <!--                    <div class="col-4">-->
                <!--                        <div>-->
                <!--                            <input-tag id="add-link" :add-tag-on-keys="addTagOnKeys"-->
                <!--                                       placeholder="enter links with 'return' or 'tab'" v-model="links"></input-tag>-->
                <!--                        </div>-->
                <!--                    </div>-->
                <!--                </div>-->


                <!--                <div class="form-group row align-items-center">-->
                <!--                    <label class="col-1 col-form-label labelInCreation">Movement</label>-->
                <!--                    <div class="col-4">-->
                <!--                        <b-form-select v-model="selectedM" class="mb-3" id="movement-selection">-->
                <!--                            <option v-for="mov in allMovements" v-bind:value="mov.id">{{ mov.name }}</option>-->
                <!--                        </b-form-select>-->
                <!--                        &lt;!&ndash;                    <div class="mb-3">SelEcted: <strong>{{ selectedM }}</strong></div>&ndash;&gt;-->
                <!--                    </div>-->
                <!--                </div>-->


                <div class="form-group row">
                    <div class="offset-sm-3 col-sm-3">
                        <button type="button" @click="createArticle" class="btn btn-primary">Save</button>
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
    //  require('vue2-autocomplete-js/dist/style/vue2-autocomplete.css')
    import api from "./article-api";
    // import hashTagApi from "./hash-tag-api";
    import InputTag from 'vue-input-tag';
    import router from "./../../router";
    import customers from './../../assets/customers';
    //   import Autocomplete from 'vue2-autocomplete-js'
    import Vue from 'vue';
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';

    ///// import VoerroTagsInput from '@voerro/vue-tagsinput';
    // Vue.component('autocomplete', Autocomplete);
    Vue.component('input-tag', InputTag);

    export default {
        name: 'article-add',
        vuetify: new Vuetify(),

        //data(){
        data: () => ({
            addTagOnKeys: [13, 9],
            descriptionLimit: 60,
            entries: [],
            isLoading: false,
            model: null,
            search: null,
            // }),

            // return {
            selectedM: null,
            selectedL: null,

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            // selectedTags: "",
            // hashTag: "",

            // hashTags: [],
            tags: [],
            links: [],
            // customers: [],

            allLanguages: [],
            allMovements: [],

            article: {authorList: [], hashtagList: [], linkList: []},
            // authorListForAutocomplete: [],

            selected: [''],
        }),

        methods: {

            testFocus(obj) {
                console.log("GET FOCUS");
                if (typeof selected !== 'undefined') console.log(selected); // Any scope console.log(selected);
            },

            addAuthor(obj) {
                console.log("GET CHANGED");

                var i = 0;
                for (i = 0; i < this.article.authorList.length; i++) { //to exclude double values
                    if (this.article.authorList[i].id === obj.id) {
                        break;
                    }
                }
                // console.log(i);

                if (i === this.article.authorList.length) {
                    this.article.authorList.push(obj);
                    console.log("ADDED");
                }

                // if (typeof this.selected !== 'undefined') {
                //     console.log("SELECTED");
                //     console.log(this.selected);
                //     this.selected = "";
                // }
            },

            deleteAuthor(author) {
                for (let i = 0; i < this.article.authorList.length; i++) {
                    if (this.article.authorList[i].id === author.id) {
                        this.article.authorList.splice(i, 1);
                    }
                }
            },

            // loadData: function () {
            //     if (this.hashTag && this.hashTag.length > 3) {
            //         hashTagApi.search(this.hashTag, r => this.hashTags = r.data);
            //         console.log("qqqqqqqqqqqq");
            //     }
            // },
            // deleteHashTag: function () {
            //     console.log("ddd");
            // },

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

            validDate: function (code) {
                var re = /([12][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))$/; ///digit format "inside", see it while debugging
                // var re = /((0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[0-2])[.][12][0-9]{3})$/;
                return re.test(code);
            },

            // formValidate() {  //all fields are required
            //     this.addStatus('add-title', (!this.article.title));
            //     if (this.hasError) {
            //     } else {
            //         this.addStatus('add-title-rus', (!this.article.titleRus));
            //         if (this.hasError) {
            //         } else {
            //             this.addStatus('date-input', (!(this.validDate(this.article.date))));
            //             if (this.hasError) {
            //             } else {
            //                 this.addStatus('language-selection', (!this.selectedL));
            //                 if (this.hasError) {
            //                 } else {
            //                     this.addStatus('movement-selection', (!this.selectedM));
            //                     if (this.hasError) {
            //                     } else {
            //                         this.addStatus('add-descr', (!this.article.description));
            //                         if (this.hasError) {
            //                         } else {
            //                             this.addStatus('add-url', (!this.article.url));
            //                         }
            //                     }
            //                 }
            //             }
            //         }
            //     }
            //     if (this.hasError) console.log('ERROROROR----------------------------');
            //     return !this.hasError;
            // },

            formValidate() {
                this.addStatus('add-url', (!this.article.url));
                if (this.hasError) {
                } else {
                    this.addStatus('language-selection', (!this.selectedL));
                    if (this.hasError) {
                    } else {
                        this.addStatus('date-input', (!(this.validDate(this.article.date))));
                    }
                }
                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createArticle() {
                // this.article.movement = {
                //     "id": this.selectedM
                // };todo

                this.article.movement = {
                    "id": 1
                };

                this.article.language = {
                    "id": this.selectedL
                };

                this.hasError = false;

                // this.article.hashtagList = this.tags;

                // for (let i = 0; i < this.links.length; i++) {
                //     this.article.linkList[i] = {
                //         "content": this.links[i]
                //     };
                // }

                for (let i = 0; i < this.tags.length; i++) {
                    this.article.hashtagList[i] = {
                        "content": this.tags[i]
                    };
                }

                if (this.formValidate()) {
                    api.create(this.article, r => {
                        router.push('/article');
                    });
                }
            },

            // remove (item) {
            //     const index = this.friends.indexOf(item.name)
            //     if (index >= 0) this.friends.splice(index, 1)
            // },

        },
        mounted() {
            this.customers = customers;
            console.log('mounted');

            // api.findById(this.$route.params.article_id, r => {
            //     this.article = r.data
            // });

            // api.getAllAuthors().then(response => {
            //     this.authorListForAutocomplete = response.data;
            //     console.log(response.data);
            // });

            api.getAllLanguages().then(response => {
                this.allLanguages = response.data;
                console.log(response.data)
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
            // fields() {
            //     if (!this.model) return [];
            //
            //     return Object.keys(this.model).map(key => {
            //         return {
            //             key,
            //             value: this.model[key] || 'n/a',
            //         }
            //     })
            // },
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

        watch: {
            search(val) {

                console.log("SEARCH ACTIVATED");

//                 // Get the input field
//                 var input = document.getElementById("author-autocomplete");
//                 // let input1 = document.querySelector("#author-autocomplete");
//
//                   // Execute a function when the user releases a key on the keyboard
//                 input.addEventListener("keyup", function (event) {
//
//                     console.log("SOMETHING WAS PRESSED!");
//
//                     // Number 13 is the "Enter" key on the keyboard
//                     if (event.keyCode === 13) {
//
//                         console.log("ENTER PRESSED!");
//                         console.log(event);
//
//
//                         if (typeof this.selected !== 'undefined') {
//                             console.log("SELECTED IN WATCH");
//                             console.log(this.selected);
//                             this.selected = "";
//                         }
//
//
//                         // Cancel the default action, if needed
// //                        event.preventDefault();
//                         // Trigger the button element with a click
//  //                       function foo() { alert('Hi!'); }
//                     }
//                 });


                if (val !== null)
                    if (val.length > 2) {
                        console.log("SEARCH STARTED");


                        if (typeof this.selected !== 'undefined') {
                            console.log("SELECTED IN WATCH");
                            console.log(this.selected);
                            if (this.article.authorList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selected = "";
                        }

                        // Items have already been loaded
                        //  if (this.items.length > 0) return;

                        // Items have already been requested
                        if (this.isLoading) return;

                        this.isLoading = true;

                        // Lazily load input items
                        //  fetch('https://api.publicapis.org/entries')
                        fetch('../api/v1/person/search?q=' + encodeURIComponent(val))
                            .then(res => res.json())
                            .then(res => {
                                this.entries = res;
                                // const {count, entries} = res;
                                // this.count = count;
                                // this.entries = entries;
                            })
                            .catch(err => {
                                console.log(err)
                            })
                            .finally(() => (this.isLoading = false))
                    }
            },
        },
    }
</script>
