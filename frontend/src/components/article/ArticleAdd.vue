<template id="article-add">
    <v-app id="inspire">
        <div>
            <div class="form-group row" style="margin-bottom: 0">

                <div v-if="editMode" class="col-5 col-form-label">

                    <div v-if="article.titleRus == null">
                        <p class="pageEditTitle">Edit "{{article.title}}"</p>
                    </div>
                    <div v-else>
                        <p class="pageEditTitle">Edit "{{article.titleRus}}"</p>
                    </div>
                </div>

                <div v-else="editMode" class="col-3 col-form-label">
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

            <!--            <div class="row" style="background-color: black">-->
            <div class="col-lg-10" style="background-color: transparent; padding-top: 0">

                <form class="formCreation">

                    <div v-if="editMode" class="row align-items-center">
                        <label class="col-1 col-form-label labelInCreation">Status</label>
                        <div class="col-6">
                            <b-form-select v-model="selectedS" class="mb-3" id="status-selection">
                                <option v-for="status in statusOptions" v-bind:value="status.value">
                                    {{status.text}}
                                </option>
                            </b-form-select>
                            <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-6">
                            <label for="add-url"><b>URL*</b></label>
                            <input class="form-control" id="add-url" v-model="article.url"/>
                        </div>

                        <div class="col-md-3">
                            <label><b>Язык*</b></label>
                            <b-form-select v-model="selectedL" class="mb-3" id="language-selection">-->
                                <option v-for="lang in allLanguages" v-bind:value="lang.id">{{lang.name}}</option>
                            </b-form-select>
                        </div>

                        <div class="col-md-3">
                            <label for="date-input"><b>Дата*</b></label>
                            <input class="form-control" type="date" id="date-input" v-model="article.date">
                        </div>
                    </div>


                    <!--                        <div class="form-group row align-items-center">-->
                    <!--                            <label for="date-input" class="col-1 col-form-label labelInCreation"><b>Date*</b></label>-->
                    <!--                            <div class="col-4">-->
                    <!--                                <input class="form-control" type="date" id="date-input" v-model="article.date">-->
                    <!--                            </div>-->
                    <!--                        </div>-->

                </form>

                <form class="authorsFormCreation" style="background-color: transparent; padding-right: -10px">
                    <div class="row align-items-center"
                         style="background-color: transparent; ; padding-right: -10px; margin-right: -10px">
                        <label for="author-autocomplete" class="col-1 col-form-label"> </label>
                        <div class="col-6"
                             style="background-color: transparent; padding-right: -30px; padding-left: -10px; margin-left: -55px; margin-right: 45px">
                            <v-card-text>
                                <v-autocomplete

                                        id="author-autocomplete"
                                        label="Авторы"

                                        :items="items"
                                        :loading="isLoading"
                                        :search-input.sync="authorSearch"
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
                        <ul class="list-group col-sm-5" order="1"
                            style="background-color: transparent; padding-left: 0px; padding-right: 0px">
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
                    <!--                    <div class="row align-items-center">-->
                    <div class="form-row">
                        <div class="col-md-6">
                            <label for="add-title"><b>Заголовок в оригинале</b></label>
                            <input class="form-control" id="add-title"
                                   placeholder="Должно быть заполнено одно из полей заголовка"
                                   v-model="article.title"/>
                        </div>


                        <!--                        <div class="row align-items-center">-->
                        <div class="col-md-6">
                            <label for="add-title-rus"> <b>Заголовок на русском</b></label>
                            <input class="form-control" id="add-title-rus"
                                   placeholder="Должно быть заполнено одно из полей заголовка"
                                   v-model="article.titleRus"
                                   required/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-lg-10" style="background-color: transparent">

                <form class="formCreation">
                    <div class="form-row align-items-center">
                        <label for="add-descr">Описание</label>
                        <div class="col-12">
                                <textarea class="form-control" id="add-descr" rows="11" v-model="article.description"
                                          required/>
                        </div>

                        <label>Форма добавления хештегов</label>
                        <div class="col-12" style="background-color: transparent">
                            <b-card style="background-color: transparent">
                                <!--                                <hashtag-list :commonProp="test"-->
                                <!--                                              @addHashtagToList="addHashtagToArticleList($event)"/>-->


                                <!--                                <v-treeview-->
                                <!--                                        dense-->
                                <!--                                        :items="items"-->
                                <!--                                ></v-treeview>-->

                                <!--                                <v-treeview-->
                                <!--                                        selectable-->
                                <!--                                        selected-color="red"-->
                                <!--                                        :items="items"-->
                                <!--                                ></v-treeview>-->

                                <!--                                <v-select v-model="selectionType" :items="['leaf', 'independent']" label="Selection type"></v-select>-->
                                <v-row style="background-color: transparent; margin-top: -10px; margin-bottom: -10px;">
                                    <v-col style="background-color: transparent; margin-top: -10px; margin-left: -5px; margin-bottom: -10px">
                                        <v-sheet
                                                style="padding-left: 0px; padding-top: 0px; padding-right: 18px; background-color: transparent">
                                            <!--                                                <v-text-field-->
                                            <!--                                                        v-model="searchHashtag"-->
                                            <!--                                                        label="Type To Search Hashtag..."-->
                                            <!--                                                        dark-->
                                            <!--                                                        flat-->
                                            <!--                                                        solo-inverted-->
                                            <!--                                                        hide-details-->
                                            <!--                                                        box-->
                                            <!--                                                        clearable-->
                                            <!--                                                        clear-icon="mdi-close-circle-outline"-->
                                            <!--                                                ></v-text-field>-->
                                            <!--                                            </v-sheet>-->

                                            <!--                                                v-text-field with clear icon-->
                                            <!--                                                <v-text-field label="search"-->
                                            <!--                                                              v-model="searchHashtag"-->
                                            <!--                                                              @click:clear="searchFieldChanged()"-->
                                            <!--                                                              @input="testSearch()"-->
                                            <!--                                                              clearable-->
                                            <!--                                                              clear-icon="mdi-close"-->
                                            <!--                                                              filled>-->
                                            <!--                                                </v-text-field>-->

                                            <v-text-field label="search"
                                                          v-model="searchHashtag"
                                                          filled>
                                            </v-text-field>
                                        </v-sheet>

                                        <v-container
                                                id="scroll-target"
                                                style="max-height: 300px; background-color: transparent; margin-top: -10px; margin-left: -15px; padding-top: 0px; padding-left: 0;"
                                                class="overflow-y-auto"
                                        >


                                            <!--                                            <v-treeview-->
                                            <!--                                                    v-model="selection"-->
                                            <!--                                                    :items="tagItems"-->
                                            <!--                                                    :selection-type="selectionType"-->
                                            <!--                                                    :search="searchHashtag"-->
                                            <!--                                                    dense-->
                                            <!--                                                    open-on-click-->
                                            <!--                                                    selectable-->
                                            <!--                                                    return-object-->
                                            <!--                                                    open-all-->
                                            <!--                                            ></v-treeview>-->

                                            <!--                                            @update:open="onSearchToOpen"-->


                                            <!--                                            selectable-->
                                            <!--                                            :selection-type="selectionType"-->
                                            <!--                                            open-on-click-->
                                            <!--                                            v-model="selectedHashtag"-->
                                            <!--                                            :active="activeNode"-->


                                            <!--                                            :items="tagItems"-->
                                            <v-treeview
                                                    :items="filteredElements"
                                                    :open="filteredKeys"
                                                    item-key="name"

                                                    activatable
                                                    color="warning"
                                                    dense
                                                    :open-all="true"

                                                    return-object
                                                    hoverable
                                                    ref="treeviewref">

                                                <template slot="label" slot-scope="{ item }">
                                                    <a @click="onHashtagSelect(item)">{{ item.name }}</a>
                                                </template>

                                            </v-treeview>
                                        </v-container>
                                    </v-col>

                                    <v-divider vertical
                                               style="background-color: transparent; margin-top: -10px; margin-left: -10px; margin-bottom: -10px;"></v-divider>

                                    <!--                                    <v-col class="pa-6" cols="6"-->
                                    <v-col
                                            style="background-color: transparent; margin-top: -10px; margin-left: -10px; margin-bottom: -10px;">

                                        <v-container
                                                id="scroll-target"
                                                style="max-height: 300px; background-color: transparent; margin-top: -10px;"
                                                class="overflow-y-auto">

                                            <template v-if="!selectedHashtag.length">
                                                No nodes selected.
                                            </template>

                                            <template v-else>
                                                <!--                                                <div v-for="node in selectedHashtag" :key="node.id">-->
                                                <!--                                                    {{  node.name }}-->

                                                <div v-for="node in selectedHashtag">
                                                    <v-btn text icon x-small @click="removeSelectedHashtag(node)">
                                                        <v-icon style="color: red">mdi-delete-forever</v-icon>
                                                    </v-btn>
                                                    {{ node }}
                                                </div>

                                                <div class="form-group row" style="padding-top: 30px">

                                                    <button type="button" style="margin-right: 20px; margin-left: 15px"
                                                            @click="addHashtagToArticleList()"
                                                            class="btn btn-success">Add
                                                    </button>

                                                    <button type="button" class="btn btn-info"
                                                            @click="clearAllSelectedTags()">Clear All
                                                    </button>
                                                </div>
                                            </template>
                                        </v-container>
                                    </v-col>
                                </v-row>

                            </b-card>
                        </div>

                        <label style="background-color: transparent">Хештеги</label>
                        <div class="col-12" style="background-color: transparent">
                            <div>
                                <input-tag id="add-hashtag1" :add-tag-on-keys="addTagOnKeys"
                                           v-model="tags"></input-tag>
                            </div>
                        </div>

                        <!--                        <div class="form-group green-border-focus">-->
                        <label for="add-misc">Комментарии</label>
                        <div class="col-12 form-group green-border-focus">

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="article.miscellany"
                                              background-color="palegreen" required/>
                        </div>
                    </div>
                    <!--                    </div>-->
                </form>
            </div>

            <div v-if="editMode" class="form-group row align-items-center align-items-center">
                <div class="offset-sm-4 col-sm-3">

                    <button type="button" @click="updateArticle" class="btn btn-primary">Update</button>
                    <a class="btn btn-default">
                        <router-link to="/article">Cancel</router-link>
                    </a>
                </div>
            </div>

            <div v-else class="form-group row" style="padding-top: 30px">
                <div class="offset-sm-4 col-sm-3">
                    <button type="button" style="margin-right: 20px" @click="createArticle(status[0])"
                            class="btn btn-warning">In Progress
                    </button>
                    <button type="button" style="margin-right: 20px" @click="createArticle(status[1])"
                            class="btn btn-success">Done
                    </button>

                    <button type="button" class="btn btn-info">

                        <router-link to="/article" style="color: white">Cancel</router-link>

                    </button>
                </div>
            </div>
            <!--            </div>-->
        </div>
    </v-app>
</template>

<script>
    import apiPerson from "./../person/person-api";
    import apiLanguage from "./../language/language-api";
    import apiHashtag from "./../hashtag/hashtag-api";
    import HashtagList from "./../hashtag/HashtagList.vue";
    import api from "./article-api";
    import moment from "moment";
    import InputTag from 'vue-input-tag';
    import router from "./../../router";
    import Vue from 'vue';
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';

    Vue.component('input-tag', InputTag);

    export default {
        components: {
            HashtagList
        },
        name: 'article-add',
        vuetify: new Vuetify(),

        data: () => ({
            test: "articleProp",
            // addTagOnKeys: [13, 9],
            addTagOnKeys: [],  //to prevent enter in input-tag))
            descriptionLimit: 60,
            entries: [],
            isLoading: false,
            isLoadingHashtag: false,
            authorSearch: null,
            searchHashtag: '',

            selectedM: null,
            selectedL: null,
            selectedS: null,

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            tags: [],
            allTags: [],
            tagsTree: [],
            selectionType: 'independent',
            selectedHashtag: [],
            hashtagTree: [],
            hashtagFlatTree: [],

            allLanguages: [],
            allMovements: [],

            article: {authorList: [], hashtagList: []},
            selected: [], //[''],
            status: ["statusProgress", "statusDone"],
            statusOptions: [
                {text: 'In Progress', value: 0},
                {text: 'Done', value: 1},
                {text: 'Returned', value: 2},
                {text: 'Completed', value: 3},
            ],
            editMode: false,
        }),

        methods: {
            removeSelectedHashtag(hash) {
                const index = this.selectedHashtag.indexOf(hash);
                if (index > -1) {
                    this.selectedHashtag.splice(index, 1);
                }
            },

            onHashtagSelect(item) {
                const index = this.selectedHashtag.indexOf(item.name);
                if (index === -1) {
                    this.selectedHashtag.push(item.name);
                }
            },

            // testSearch() {
            //     console.log("v-text-field - input ~===~~~~", this.selectedHashtag.length);
            //     //  this.selection = [];
            // },

            // searchFieldChanged() {
            //     //v-text-field - clear
            //     console.log("v-text-field - clear ~===~~~~");
            //     this.searchHashtag = '';
            //     //  this.buildTree();
            // },

            // onSearchToOpen() {
            //     //v-treeview - open - update
            //     console.log("v-treeview - open - update ~===~~~~");
            //     //return this.filteredKeys;
            // },

            // createTree(treeData, parent_id) {
            //     let tree = [];
            //
            //     treeData.forEach((item, id) => {
            //             if (item.parentId === parent_id) {
            //
            //                 let newItem = {
            //                     id: id,
            //                     name: item.content,
            //                     children: [],
            //                 };
            //
            //                 newItem.children = this.createTree(treeData, item.id);
            //                 tree.push(newItem);
            //             }
            //         }
            //     );
            //     return tree;
            // },

            buildTree() {
                //this.hashtagTree = this.createTree(this.allTags, 0);
                this.hashtagFlatTree = this.createFlatTree(this.allTags);
                // console.log("CREATED TREE", this.hashtagTree);
                // console.log("FLAT TREE", this.hashtagFlatTree);
            },

            createFlatTree(treeData) {
                let flatTree = [];
                for (let i = 0; i < treeData.length; i++) {
                    let newItem = {
                        id: treeData[i].id,
                        name: treeData[i].content,
                        children: [],
                    };
                    for (let j = 0; j < treeData.length; j++) {
                        if (treeData[j].parentId === treeData[i].id) {

                            let refNewItem = {
                                id: treeData[j].id,
                                name: null,
                                children: null,
                            };
                            newItem.children.push(refNewItem);
                        }
                    }
                    flatTree.push(newItem);
                }
                return flatTree;
            },

            clearAllSelectedTags() {
                this.selectedHashtag = [];
            },

            addHashtagToArticleList(hashtag) {//from HashtagList
                this.tags = hashtag;
            },

            // addHashtagToArticleList() {
            //     let tagAlreadyAdded = 0;
            //     this.selectedHashtag.forEach((item, i) => {
            //         this.tags.forEach((tag, j) => {
            //             if (tag === item.name)
            //                 tagAlreadyAdded = 1;
            //         });
            //         if (tagAlreadyAdded == 0)
            //             this.tags.push(item.name);
            //     });
            //     this.selectedHashtag = [];
            // },

            addHashtagToArticleList() {
                let tagAlreadyAdded = 0;
                this.selectedHashtag.forEach((item, i) => {
                    this.tags.forEach((tag, j) => {
                        if (tag === item)
                            tagAlreadyAdded = 1;
                    });
                    if (tagAlreadyAdded == 0)
                        this.tags.push(item);
                });
                this.selectedHashtag = [];
            },

            testFocus(obj) {
                console.log("GET FOCUS");
                if (typeof selected !== 'undefined') console.log(selected); // Any scope console.log(selected);
            },

            addAuthor(obj) {
                // console.log("GET CHANGED");

                let i = 0;
                for (i = 0; i < this.article.authorList.length; i++) { //to exclude double values
                    if (this.article.authorList[i].id === obj.id) {
                        break;
                    }
                }

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
                let re = /([12][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))$/; ///digit format "inside", see it while debugging
                // var re = /((0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[0-2])[.][12][0-9]{3})$/;
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

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createArticle(currentStatus) {
                // this.article.movement = {
                //     "id": this.selectedM
                // };todo

                this.article.movement = {
                    "id": 1
                };

                this.article.language = {
                    "id": this.selectedL
                };

                if (currentStatus === this.status[0]) {
                    this.article.status = 0;
                } else {
                    this.article.status = 1;
                }

                this.hasError = false;

                for (let i = 0; i < this.tags.length; i++) {
                    this.article.hashtagList[i] = this.tags[i];
                }

                this.article.linkList = [];

                if (this.formValidate()) {
                    api.create(this.article, r => {
                        router.push('/article');
                    });
                }
            },

            updateArticle() {
                // this.article.movement = {
                //     "id": this.selectedM
                // }; todo


                //todo!!! если id языков в таблице будут не подряд и не с 1 - будет ошибка
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
                    this.article.hashtagList[i] = this.tags[i];
                }

                if (this.formValidate()) {
                    api.update(this.article.id, this.article, r => {
                        router.push('/article');
                    });
                }
            },
        },
        mounted() {

            //   console.log("MOUNTED_ART_ADD");
            // api.getAllAuthors().then(response => {
            //     this.authorListForAutocomplete = response.data;
            //     console.log(response.data);
            // });

            apiLanguage.getAllLanguages(response => {
                this.allLanguages = response.data;
                console.log(response.data)
            });

            apiHashtag.getAllHashtags(response => {
                this.allTags = response.data;
                this.buildTree();
                // console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                });


            // api.getAllMovements().then(response => {
            //     this.allMovements = response.data;
            //     console.log(response.data)
            // }).catch(error => {
            //     //this.errors.push(error)
            //     console.log(error);
            // })

            if (this.$route.params.article_id != null) {
                console.log("EDIT MODE");
                this.editMode = true;
            } //else console.log("ADD MODE");

            //  console.log("article!", this.article);

            if (this.editMode) {
                api.findById(this.$route.params.article_id, r => {
                    this.article = r.data;
                    //  console.log("article EDIT!", this.article);

                    // this.selectedM = this.article.movement.id; //to select necessary value from article
                    this.selectedL = this.article.language.id;
                    this.selectedS = this.article.status;
                    // console.log("STATUS", this.article.status);
                    this.article.date = this.formatDate(this.article.date);

                    // this.tags = this.article.hashtagList;
                    for (let i = 0; i < this.article.hashtagList.length; i++) {
                        this.tags.push(this.article.hashtagList[i]);
                    }
                });
            }
        },

        computed: {
            items() {
                return this.entries.map(entry => {
                    const surname = entry.surname;
                    return Object.assign({}, entry, {surname})
                })
            },

            searchLength() {
                return this.searchHashtag.length
            },

            filteredElements() {
                let tree = [];
                let resultIds = [];

                let touched = false;
                let resultSearchTree = []; //this.hashtagFlatTree.slice(); //this.hashtagFlatTree.map((x) => x);

                // let treeSize = this.hashtagFlatTree.length;
                // for (let i = 0; i < treeSize; i++){
                //     let item = this.hashtagFlatTree[i];
                //     resultSearchTree.push(item);
                // }

                //the way for deep copy
                resultSearchTree = JSON.parse(JSON.stringify(this.hashtagFlatTree));

                // console.log("do while this.hashtagORIGINFlatTree", this.hashtagOriginalFlatTree);
                // console.log("do while this.hashtagFlatTree", this.hashtagFlatTree);
                //console.log("do while flatTree", resultSearchTree);


                do {
                    // console.log("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    // console.log("do while this.hashtagORIGINFlatTree", this.hashtagOriginalFlatTree);
                    // console.log("do while this.hashtagFlatTree", this.hashtagFlatTree);
                    // console.log("do while flatTree", resultSearchTree);


                    // remove leaves which do not match search string
                    let toRemove = [];
                    touched = false;

                    resultSearchTree.forEach((node, id) => {
                        const index = node.name.toLowerCase().indexOf(this.searchHashtag) >= 0;
                        if ((index === false) && (node.children.length === 0)) {
                            // console.log("node.children.length (to remove)", node.children.length, node.name);
                            toRemove.push(node.id);
                            touched = true;
                        } else {
                            // console.log("node.children.length (to stay)", node.children.length, node.name);
                        }
                    });

                    // console.log("toRemove", toRemove);
                    //  console.log("this.searchHashtag", this.searchHashtag);

                    for (let i = 0; i < toRemove.length; i++) {
                        let index = resultSearchTree.findIndex(x => x.id === toRemove[i]);
                        //   console.log("INDEX * * * * *", index, toRemove[i]);
                        if (index >= 0) {
                            resultSearchTree.splice(index, 1);
                        }
                    }

                    // remove references to deleted leaves
                    let treeSize = resultSearchTree.length;
                    for (let i = 0; i < treeSize; i++) {
                        let newChildren = [];
                        let currentItem = resultSearchTree[i];

                        for (let j = 0; j < currentItem.children.length; j++) {

                            let childWasFound = false;
                            for (let k = 0; k < treeSize; k++) {
                                if ((resultSearchTree[k].id === currentItem.children[j].id)) {
                                    newChildren.push(currentItem.children[j]);
                                    childWasFound = true;
                                }
                            }//for k

                            if (!childWasFound) {
                                touched = true;
                                //   console.log("deleted ref", currentItem.children[j].id);
                            }
                        }//for j
                        resultSearchTree[i].children = newChildren;
                    }

                } while (touched);

                // resultSearchTree.forEach((item, id) => {
                //     console.log("FLAT SEARCH TREE", item.name, item.id);
                // });


                //create normal tree from flat tree
                let movedChildren = [];
                do {
                    movedChildren = [];

                    let treeSize = resultSearchTree.length;
                    for (let i = 0; i < treeSize; i++) {

                        let currentItem = resultSearchTree[i];
                        let removedChildrenAlreadyContains = false;
                        let childrenQ = currentItem.children.length;

                        for (let j = 0; j < movedChildren.length; j++) {
                            if (movedChildren[j] === currentItem.id)
                                removedChildrenAlreadyContains = true;
                        }

                        if (!removedChildrenAlreadyContains) {
                            for (let j = 0; j < childrenQ; j++) {
                                let child = currentItem.children[j];

                                if ((child.name == null)) {
                                    let index = resultSearchTree.findIndex(x => x.id === child.id);
                                    if (index >= 0) {
                                        //if (resultSearchTree[index].children.length === 0) {
                                        let isChildrenNull = false;

                                        if (resultSearchTree[index].children !== null) {
                                            for (let k = 0; k < resultSearchTree[index].children.length; k++) {
                                                if (resultSearchTree[index].children[k].name === null) {
                                                    isChildrenNull = true;
                                                }
                                            }//for k

                                            if (!isChildrenNull) {
                                                child.name = resultSearchTree[index].name;
                                                child.children = resultSearchTree[index].children;
                                                movedChildren.push(child.id);
                                            }
                                        }//if child.children
                                    }
                                }
                            }//for j
                        }//if contains
                    }

                    for (let j = 0; j < movedChildren.length; j++) {
                        let index = resultSearchTree.findIndex(x => x.id === movedChildren[j]);
                        // console.log("I-N-D-E-X-======", index, movedChildren[j]);
                        if (index >= 0) {
                            resultSearchTree.splice(index, 1);
                        }
                    }
                    //console.log("-----------------------------------------------");
                } while (movedChildren.length > 0);
                return resultSearchTree;
            },

            filteredKeys() {
                if (this.searchHashtag != null)   //for start view without search
                    if (this.searchLength === 0) {
                        return this.filteredElements.map((top) => {
                            return top.name
                        })
                    } else {
                        return this.filteredElements;
                    }
            }
        },


        ///////////////////////////////////////////WATCH////////////////////////////////////////////////////
        watch: {

            searchHashtag() {
                this.$nextTick(() => {
                    if (this.searchLength === 0) {
                        this.$refs.treeviewref.updateAll(false);
                    } else {
                        this.$refs.treeviewref.updateAll(true);
                    }
                });
            },

            authorSearch(val) {

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


                        apiPerson.searchPerson(val, r => {
                            this.entries = r;
                            //  console.log("****", this.entries);
                            this.isLoading = false;
                        });

                        // Lazily load input items
                        //  fetch('https://api.publicapis.org/entries')  //todo
                        // fetch('../api/v1/person/search?q=' + encodeURIComponent(val))
                        //     .then(res => res.json())
                        //     .then(res => {
                        //         this.entries = res;
                        //         // const {count, entries} = res;
                        //         // this.count = count;
                        //         // this.entries = entries;
                        //     })
                        //     .catch(err => {
                        //         console.log(err)
                        //     })
                        //     .finally(() => (this.isLoading = false))
                    }
            }
        },
    }
</script>
