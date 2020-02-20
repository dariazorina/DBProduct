<template id="article-edit">
    <v-app id="inspire">
        <div>
            <div class="form-group row">
                <div class="col-3 col-form-label">
                    <p class="pageEditTitle">Edit "{{article.title}}"</p>
                </div>


                <div class="unprotected" v-if="errorFlag">
                    <h5>Error: {{errors}}</h5>
                </div>
            </div>

            <!--       url="http://localhost:8080/api/v1/person"-->
            <!--            http://localhost:8081/article/add-->

            <!--            <form>-->

            <form class="formCreation">

                <div class="form-group row align-items-center">
                    <label class="col-1 col-form-label labelInCreation">Status</label>
                    <div class="col-4">
                        <b-form-select v-model="selectedS" class="mb-3" id="status-selection">
                            <option v-for="status in statusOptions" v-bind:value="status.value">{{status.text}}</option>
                        </b-form-select>
                        <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                    </div>
                </div>

                <div class="form-group row align-items-center">
                    <label for="add-url"
                           class="col-1 col-form-label labelInCreation labelInCreation"><b>URL*</b></label>
                    <div class="col-4">
                        <input class="form-control" id="add-url" type="url" v-model="article.url" required/>
                    </div>
                </div>

                <div class="form-group row align-items-center">
                    <label class="col-1 col-form-label labelInCreation"><b>Language*</b></label>
                    <div class="col-4">
                        <b-form-select v-model="selectedL" class="mb-3" id="language-selection">
                            <option v-for="lang in allLanguages" v-bind:value="lang.id">{{lang.name}}</option>
                        </b-form-select>
                        <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                    </div>
                </div>

                <div class="form-group row align-items-center">
                    <label for="date-input" class="col-1 col-form-label labelInCreation"><b>Date*</b></label>
                    <div class="col-4">
                        <input class="form-control" type="date" id="date-input" v-model="article.date">
                    </div>
                </div>


                <div class="form-group row align-items-center">
                    <label class="col-1 col-form-label labelInCreation">Author</label>
                    <div class="col-4">
                        <ul class="list-group" order="1">
                            <li v-for="author in this.article.authorList"
                                class="list-group-item d-flex justify-content-between align-items-center">
                                {{ author.surname }}
                                <span class="close" @click="deleteAuthor(author)">&times;</span>
                            </li>
                        </ul>
                    </div>
                </div>
                <div>

                    <!--                anchor = "this.authorListForAutocomplete"-->
                    <!--                label = "this.authorListForAutocomplete"-->

                    <!--                filterByAnchor (Boolean: true)???-->

                    <!--                <autocomplete class="mb-3" order="0" id="autocomplete-author"-->
                    <!--                              anchor="surname"-->
                    <!--                              label="writer"-->
                    <!--                              url="../api/v1/person"-->
                    <!--                              placeholder="Type author name.."-->
                    <!--                              :min="3"-->
                    <!--                              :classes="{ wrapper: 'form-wrapper', input: 'form-control', list: 'data-list', item: 'data-list-item' }"-->
                    <!--                              :on-select="addAuthor">-->
                    <!--                </autocomplete>-->

                </div>
                <!--            </form>-->

                <!--            <form class="authorsFormCreation">-->


                <div class="form-group row align-items-center">
                    <label for="author-autocomplete" class="col-1 col-form-label labelInCreation"> </label>
                    <div class="col-4">
                        <v-card-text>
                            <v-autocomplete

                                    id="author-autocomplete"
                                    label="Authors"

                                    :items="items"
                                    :loading="isLoading"
                                    :search-input.sync="search"
                                    color="green"
                                    hide-no-data
                                    hide-selected

                                    v-model="selected"

                                    @change="addAuthor(selected)"
                                    item-text="surname"
                                    item-value="id"
                                    placeholder="Start typing to find Author"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>
                </div>

                <!--            </form>-->

                <!--            <form class="formCreation">-->

                <div class="form-group row align-items-center">
                    <label for="add-title" class="col-1 col-form-label labelInCreation"><b>Заголовок в
                        оригинале</b></label>
                    <div class="col-4">
                        <input class="form-control" id="add-title" type="text" v-model="article.title"/>
                    </div>
                </div>

                <div class="form-group row align-items-center">
                    <label for="add-title-rus" class="col-1 col-form-label labelInCreation"><b>Заголовок на русском</b></label>
                    <div class="col-4">
                        <input class="form-control" id="add-title-rus" type="text" v-model="article.titleRus"
                               required/>
                    </div>
                </div>

                <div class="form-group row align-items-center">
                    <label for="add-descr" class="col-1 col-form-label labelInCreation">Description</label>
                    <div class="col-4">
                        <textarea class="form-control" id="add-descr" type="text" rows="7" v-model="article.description"
                                  required/>
                    </div>
                </div>


                <form>
                    <div class="form-group row align-items-center">
                        <label for="edit-hashtag" class="col-1 col-form-label labelInCreation">Hashtag</label>
                        <div class="col-4">
                            <div>
                                <input-tag id="edit-hashtag" :add-tag-on-keys="addTagOnKeys" v-model="tags"></input-tag>
                            </div>
                        </div>
                    </div>

                    <div class="form-group green-border-focus">
                        <div class="row align-items-center">
                            <label for="add-misc" class="col-1 col-form-label">Комментарии</label>
                            <div class="col-4">
                        <textarea class="form-control" id="add-misc" rows="6" v-model="article.miscellany"
                                  background-color="palegreen" required/>
                            </div>
                        </div>
                    </div>

                </form>

                <div class="form-group row align-items-center align-items-center">
                    <div class="offset-sm-2 col-sm-3">

                        <button type="button" @click="updateArticle" class="btn btn-primary">Update</button>
                        <a class="btn btn-default">
                            <router-link to="/article">Cancel</router-link>
                        </a>
                    </div>
                </div>


                <!--                <div class="my-md-4">-->
                <!--                    <a class="btn btn-outline-info btn-sm mr-2">-->
                <!--                    <button type="button" @click="updateArticle" class="btn btn-primary">Update</button>-->

                <!--                    </a>-->
                <!--                    <a class="btn btn-outline-info btn-sm mr-2">-->
                <!--                        <a class="btn btn-default">-->
                <!--                            <router-link to="/article">Cancel</router-link>-->
                <!--                        </a>-->
                <!--                    </a>-->
                <!--                </div>-->

            </form>
        </div>
    </v-app>
</template>

<script>
    import api from "./article-api";
    import InputTag from 'vue-input-tag';
    import router from "./../../router";
    import Autocomplete from './Autocomplete';

    import moment from "moment";
    import Vue from 'vue';
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';

    Vue.component('input-tag', InputTag);

    export default {
        name: 'article-edit',
        vuetify: new Vuetify(),
        data() {

            return {
                addTagOnKeys: [13, 9],
                errorFlag: false,
                errors: [],
                selectedTags: "",
                hashTag: "",
                lastName: "",
                validationErrors: {},
                hashTags: [],
                tags: [],
                customers: [],

                descriptionLimit: 60,
                entries: [],
                isLoading: false,
                model: null,
                search: null,

                selectedS: null,
                selectedL: null,

                hasError: false,

                allLanguages: [],
                // allMovements: [],

                tags: [],
                // links: [],
                // linkList: [],
                hashtagList: [],
                // article: {authorList: [], movement: {}},
                article: {authorList: [], movement: {}, hashtagList: [], },

                selected: [''],

                statusOptions: [
                    {text: 'In Progress', value: 0},
                    {text: 'Done', value: 1},]
            }
        },
        components: {
            Autocomplete
        },
        methods: {
            // customerSelected: function () {
            //
            // },
            //
            // loadData: function () {
            //     if (this.hashTag && this.hashTag.length > 3) {
            //         hashTagApi.search(this.hashTag, r => this.hashTags = r.data);
            //
            //         console.log("qqqqqqqqqqqq");
            //     }
            // },

            // deleteHashTag: function () {
            //     console.log("ddd");
            // },

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
                return re.test(code);
            },

            formatDate(date) {
                return moment(date).format('YYYY-MM-DD');
                // return moment(date).format('DD/MM/YYYY');
            },

            formValidate() {
                this.addStatus('add-url', (!this.article.url));
                if (this.hasError) {
                } else {
                    this.addStatus('language-selection', (!this.selectedL));
                    if (this.hasError) {
                    } else {
                        this.addStatus('date-input', (!(this.validDate(this.article.date))));
                        if (this.hasError) {
                        } else {
                            this.addStatus('add-title', (!(this.article.title || this.article.titleRus)));
                            if (this.hasError) {
                                this.addStatus('add-title-rus', true);
                            } else {
                                this.addStatus('add-title-rus', false);
                            }
                        }
                    }
                }

                if (this.hasError) console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            updateArticle() {

                // console.log("updateArticle");
                // console.log(this.tags);

                // console.log(document.getElementById("edit-hashtag").value);


                // this.article.movement = {
                //     "id": this.selectedM
                // }; todo

                this.article.language = {
                    "id": this.selectedL
                };

                this.article.status = this.selectedS;

                this.hasError = false;

                // this.article.linkList = [];
                // for (let i = 0; i < this.links.length; i++) {
                //     this.article.linkList[i] = {
                //         "content": this.links[i]
                //     };
                // }

                this.article.hashtagList = [];
                for (let i = 0; i < this.tags.length; i++) {
                    this.article.hashtagList[i] = {
                        "content": this.tags[i]
                    };
                }


                if (this.formValidate()) {
                    api.update(this.article.id, this.article, r => {
                        router.push('/article');
                    });
                }
            },
        },

        mounted:
            function () {
                // this.customers = customers;
                console.log('mounted');

                api.getAllLanguages().then(response => {
                    this.allLanguages = response.data;
                    // console.log(response.data)
                });

                // api.getAllMovements().then(response => {
                //     this.allMovements = response.data;
                //     // console.log(response.data)
                // }).catch(error => {
                //     console.log(error);
                // })

                api.findById(this.$route.params.article_id, r => {
                    this.article = r.data;

                    // this.selectedM = this.article.movement.id; //to select necessary value from article
                    this.selectedL = this.article.language.id;
                    this.selectedS = this.article.status;
                    console.log("STATUS", this.article.status);
                    this.article.date = this.formatDate(this.article.date);

                    // this.tags = this.article.hashtagList;
                    for (let i = 0; i < this.article.hashtagList.length; i++) {
                        this.tags.push(this.article.hashtagList[i].content);
                    }

                    // for (let i = 0; i < this.article.linkList.length; i++) {
                    //     this.links.push(this.article.linkList[i].content);
                    // }
                });
            },

        computed: {
            items() {
                return this.entries.map(entry => {
                    const surname = entry.surname;
                    return Object.assign({}, entry, {surname})
                });
            },
        },

        watch: {
            search(val) {
                console.log("SEARCH ACTIVATED");

                if (val !== null)
                    if (val.length > 2) {
                        console.log("SEARCH STARTED");
                        console.log(val);

                        if (typeof this.selected !== 'undefined') {
                            console.log("SELECTED IN WATCH");
                            console.log(this.selected);
                            if (this.article.authorList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selected = "";
                        }

                        // Items have already been requested
                        if (this.isLoading) return;

                        this.isLoading = true;

                        // Lazily load input items
                        fetch('/api/v1/person?q=' + encodeURIComponent(val))
                            .then(res => res.json())
                            .then(res => {
                                this.entries = res;
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
