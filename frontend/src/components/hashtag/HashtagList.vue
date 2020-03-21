<template id="hashtag">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="form-group row" style="margin-bottom: -10px; margin-top: -20px">
            <div class="col-3 col-form-label">
                <p class="greetingsTitle">Welcome, {{loggedName}}!
                    <button type="button" v-if="loggedInFlag" class="btnXSmall btn-link" v-b-modal.modal1>Logout
                    </button>
                    <!--                <b-btn v-if="loggedInFlag" size="xs" variant="btn btn-link  btn-xs"  v-b-modal.modal1>Logout</b-btn>-->
                </p>
                <!-- Modal Component -->
                <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>
            </div>
        </div>


        <!--//////////////////////////////////////////add/edit hashtag/////////////////////////////////////////////////////////////////-->
        <form class="formCreation">

            <!--            <div class="row  align-items-center">-->
            <!--                <label for="add-code" class="col-1 col-form-label labelInCreation">Хештег</label>-->
            <!--                <div v-if="hashtag==null" class="col-4">-->
            <!--                    <input type="number" class="form-control" id="add-code" :v-model="hashtag"/>-->
            <!--                </div>-->

            <!--                <div v-else class="col-4">-->
            <!--                    <input type="number" class="form-control" id="add-code" :v-model="hashtag.content"/>-->
            <!--                </div>-->


            <!--            </div>-->


            <div class="row  align-items-center col-12" style="background-color: greenyellow">

                <div class="form-row col-12" style="background-color: yellow">

                    <div class="row col-6" style="background-color: lightblue">
                        <div class="col-11" style="background-color: lavender">

                            <label>1 уровень</label>
                            <b-form-select v-model="selected1LevelId" id="level1-selection">
                                <option v-for="hash in levelHashtags" :value="hash.id" v-if="hash.parentId==0">
                                    {{hash.content}}
                                </option>
                            </b-form-select>

                            <!--                        <div class="offset-sm-2 col-sm-3">-->
                            <!--                            <button type="button" @click="clearSelection(1)" class="btn btn-primary">Clear-->
                            <!--                            </button>-->
                            <!--                        </div>-->

                        </div>
                        <div class="col-1"
                             style="background-color: lavenderblush; padding-top: 50px; padding-right: 0px">
                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="clearSelection(1)">
                                <v-icon style="color: green">mdi-close</v-icon>
                            </v-btn>
                        </div>

                        <div class="col-11" style="background-color: lightgoldenrodyellow">
                            <div v-if="selected1LevelId==null">
                                <div style="margin-top: 10px">
                                    <label for="add-level1">добавить хештег 1 уровня:</label>
                                    <input class="form-control" id="add-level1" v-model="hashtag.content" required/>
                                </div>
                            </div>

                            <div v-if="selected2LevelId==null">
                                <div v-if="selected1LevelId!=null">
                                    <div style="margin-top: 10px">
                                        <label for="add-level2">добавить хештег 2 уровня:</label>
                                        <input class="form-control" id="add-level2" v-model="hashtag.content" required/>
                                        <!--                            todo <span class="add" @click="deleteSearch()" style="margin-top: -31px; margin-right: 5px">+</span>-->
                                    </div>
                                </div>
                            </div>

                            <div v-if="selected2LevelId!=null">
                                <div style="margin-top: 10px">
                                    <label for="add-level3">добавить хештег 3 уровня:</label>
                                    <input class="form-control" id="add-level3" v-model="hashtag.content" required/>
                                </div>
                            </div>
                            <!--                        <div class="mt-2">Selected: <strong>{{ selected1Level }}</strong></div>-->
                        </div>
                        <div class="col-1" style="background-color: lightgray; padding-top: 55px; padding-right: 0px">
                            <!--                            <div class="offset-sm-2 col-sm-3">-->
                            <!--                                <button type="button" @click="addOrUpdateHashtag" class="btn btn-primary">Add/Update-->
                            <!--                                    hashtag-->
                            <!--                                </button>-->
                            <!--                            </div>-->

                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="addOrUpdateHashtag">
                                <v-icon style="color: green">mdi-message-plus</v-icon>
                            </v-btn>
                        </div>


                    </div>

                    <div class="row col-6" style="background-color: peachpuff">
                        <div class="col-11" style="background-color: gold">

                            <label>2 уровень</label>
                            <b-form-select v-model="selected2LevelId" id="level2-selection">
                                <option v-for="hash in levelHashtags" :value="hash.id"
                                        v-if="hash.parentId==selected1LevelId">
                                    {{hash.content}}
                                </option>
                            </b-form-select>

                        </div>

                        <div class="col-1" style="background-color: #e3d2d2; padding-top: 50px;">
                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="clearSelection(2)">
                                <v-icon style="color: green">mdi-close</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </div>
            </div>
        </form>


        <!--////////////////////////////////////////search//////////////////////////////////-/////////////////////////////////////////////////////////////-->

        <div class="row">
            <label class="col-sm-2 col-form-label" style="line-height: 45px;">Выберете поле для поиска:</label>
            <div class="col-sm-auto">
                <b-form-select v-model="selected" id="search-selection">
                    <option v-for="item in searchItems" v-bind:value="item">{{item}}</option>
                </b-form-select>
            </div>

            <div class="col-sm-2">
                <input :placeholder="placeholderCreation()" v-model="searchKey" class="form-control"
                       id="search-element" style="padding-right: 20px" v-on:keyup.enter="search" requred/>
                <span class="close" @click="deleteSearch()"
                      style="margin-top: -31px; margin-right: 5px">&times;</span>
                <!--                todo-->
            </div>

            <div style="padding-top: 11px">
                <button type="button" @click="search" class="btn btn-primary">Search</button>
            </div>
        </div>


        <!--            /////////////////////////////////table///////////////////////////////////////////////////////-->

        <table class="redTable">
            <!--        <table class="table">-->
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <!--                <th class='tdTitle'>Id</th>-->

                <th class='tdTitle'>Статус</th>
                <th class='tdTitle'>Хештег</th>
                <th class='tdTitle'>Комментарий</th>


                <th class="tdTitle" style="width:4%"></th>
            </tr>
            </thead>
            <tbody>
            <!--            <tr v-for="article in articles" class="ListCellStyleHot">-->
            <!--                        <tr v-for="article in articles">-->
            <tr v-for="hashtag in filteredHashtags">


                <td :key="hashtag.type">
                    <div v-if="hashtag.type==0">
                        {{options[0].text}}
                    </div>

                    <div v-if="hashtag.type==1">
                        {{options[1].text}}
                    </div>

                </td>
                <td>
                    {{hashtag.content}}
                </td>
                <td>
                    {{hashtag.miscellany}}
                </td>


                <td>
                    <v-btn text icon x-small @click="findEditedHashtag(hashtag.id)">
                        <v-icon style="color: green">mdi-pencil</v-icon>
                    </v-btn>


                    <v-btn text icon x-small>
                        <a>
                            <router-link :to="{name: 'article-delete', params: {article_id: hashtag.id}}">
                                <v-icon style="color: red">mdi-delete-forever</v-icon>
                            </router-link>
                        </a>
                    </v-btn>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet"/>

<!--<link type="text/css" rel="stylesheet" href="//unpkg.com/bootstrap/dist/css/bootstrap.min.css"/>-->
<!--<link type="text/css" rel="stylesheet" href="//unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue-icons.min.css"/>-->
<!--&lt;!&ndash; Load Vue followed by BootstrapVueIcons &ndash;&gt;-->
<!--<script src="//unpkg.com/vue@latest/dist/vue.min.js"></script>-->
<!--<script src="//unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue-icons.min.js"></script>-->

<style lang="scss">
    @import '../dbnm.css';
</style>

<style>

    .ListCellStyle {
        /*padding: 5px; !* Поля вокруг текста *!*/
        text-align: left;
        font-size: small;
        /*height: 25px;*/
        max-height: 85px;
        overflow: hidden;
        /*border: solid 1px;*/
        /*border-color: #eceaea;*/
        /*padding: 5px 15px 5px 10px; !*top right bottom left*!*/

    }

    .ListCellStyleHot {
        /*padding: 5px; !* Поля вокруг текста *!*/
        text-align: left;
        font-size: small;
        max-height: 35px;
        height: 25px;
        /*overflow: hidden;*/
        /*border: solid 1px;*/
        /*border-color: #eceaea;*/
        /*padding: 5px 15px 5px 10px; !*top right bottom left*!*/
        /*background-color: #eceaea;*/

    }

    .ListCellStyleForButton {
        border: solid 1px;
        border-color: #eceaea;
        /*height: 85px;*/
        overflow: hidden;

    }

    /*.layer12 {*/
    /*    background: #fc3; !* Цвет фона *!*/
    /*    border: 2px solid black; !* Параметры рамки *!*/
    /*    padding: 5px; !* Поля вокруг текста *!*/
    /*}*/

    /*div{*/
    /*    white-space: pre-wrap;*/
    /*}*/

</style>

<script>

    import Vue from 'vue';
    import api from "./hashtag-api";
    import moment from "moment";
    import router from "./../../router";
    import "vue-scroll-table";
    import Vuetify from 'vuetify';
    // import 'vuetify/dist/vuetify.min.css';
    import '@mdi/font/css/materialdesignicons.css' //why does icon appear in other file)) add article?
    // import 'material-design-icons-iconfont/dist/material-design-icons.css'


    export default {
        name: 'hashtag',
        vuetify: new Vuetify(),
        data() {
            return {
                hashtags: [],
                hashtag: {},

                response: [],
                errors: [],

                entries: [],

                showResponse: false,
                loggedInFlag: false,
                loggedName: null,
                selected1LevelId: null,
                selected2LevelId: null,
                selected1LevelHashtag: null,
                selected2LevelHashtag: null,
                editMode: false,

                selected: "хештег",
                searchItems: ["хештег", "тип", "комментарий"],
                searchKey: '',

                statusCheckBox: [],
                options: [
                    {text: 'main', value: 0},
                    {text: 'detailed', value: 1},
                ],
                message: 'One Line,\nTwo Lines.',
            }
        },
        computed: {
            filteredHashtags() {
                // console.log("FILTERED ARTICLES");
                return this.entries;
            },

            levelHashtags() {
                return this.hashtags;
            }
        },

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
                this.addStatus('add-url', (!this.hashtag.content));
                if (this.hasError) {
                } else {
                    this.addStatus('language-selection', (!this.selectedL));
                }

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            clearSelection(level) {
                if (level == 1) {
                    this.selected1LevelId = null;
                    this.selected2LevelId = null;
                } else {
                    this.selected2LevelId = null;
                }

                this.hashtag.content = null;
            },

            addOrUpdateHashtag() {

                if (this.hashtag.content != null) {

                    if (this.selected1LevelId == null) {
                        this.hashtag.parentId = 0;
                        console.log("PARENTID=0");

                    } else if (this.selected2LevelId == null) {
                        this.hashtag.parentId = this.selected1LevelId;
                        console.log("PARENTID=", this.selected1LevelId);

                    } else {
                        this.hashtag.parentId = this.selected2LevelId;
                    }


                    api.create(this.hashtag, r => {
                        if (this.selected2LevelId != null) {
                            api.getLeafHashtags(this.selected2LevelId, r => {
                                this.entries = r.data;
                                this.hashtag.content = null;

                                console.log(r.data)
                            })
                                .catch(error => {
                                    this.errors.push(error)
                                });
                        } else {
                            api.getAllHashtags().then(response => {
                                this.entries = response.data;
                                this.hashtags = response.data;  //to add new hash in dropdown lists
                                this.hashtag.content = null;

                                console.log(response.data)
                            })
                                .catch(error => {
                                    this.errors.push(error)
                                });
                        }
                    });

                    // this.hasError = false;

                    // if (this.formValidate()) {
                    //     if (this.hashtag.id != null) {
                    //         api.update(this.hashtag.id, this.hashtag, r => {
                    //             // router.push('/article');
                    //             this.search();
                    //         });
                    //     } else {
                    //         api.create(this.hashtag, r => {
                    //             this.search();
                    //         })
                    //     }
                    // }//formValidate
                }
            },

            logout() {
                this.$store.dispatch("logout", {}).then(result => {
                    router.push('/login');
                });
            },

            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },

            placeholderCreation() {
                if (this.selected) {
                    return "Поиск по полю <" + this.selected + ">";
                } else {
                    return "Выберете поле поиска"
                }
            },


            findEditedHashtag(id) {
                console.log("enter findOrEdit");
                // if (this.editMode) {
                api.findById(id, r => {
                    this.hashtag = r.data;
                    console.log("hashtag EDIT!", this.hashtag);
                });
                // }
            },


            deleteSearch() {
                this.searchKey = "";
                // this.entries = this.articles;
            },

            // dateFormat(value, row, index) {  //todo
            //     moment(this.article.date).format('DD/MM/YYYY').then(response => {
            //         this.article.date = response.data;
            //     })
            // },

            isArrayValidAndNotEmpty(array) {

                if (typeof array === 'undefined' || array === null || array.length == 0) {
                    return false;
                }
                return true;
            },

            search() {

                if (this.searchKey === "" && !this.isArrayValidAndNotEmpty(this.statusCheckBox)) {//s- ch-
                    // this.entries = this.articles;

                    api.searchPeriodAndStatus(-1, this.startDate, this.endDate, r => {
                        this.entries = r.data;
                    });

                } else {
                    if (this.searchKey === "") {    //s-
                        if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) { //ch+
                            api.searchPeriodAndStatus(this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                this.entries = r.data;
                            });
                        }

                    } else {        //s+
                        if (this.selected === "хештег") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {//ch+
                                api.searchHash(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchHash(this.searchKey, -1, r => {
                                    this.entries = r.data;
                                });
                            }
                        }
                    }
                }
            },
        },

        mounted() {
            this.getLoggedIn();

            api.getAllHashtags().then(response => {
                this.entries = response.data;
                this.hashtags = response.data;

                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                });
        },


        watch: {
            selected2LevelId: function () {
                if (this.selected2LevelId == null) {

                    api.getAllHashtags().then(response => {
                        this.entries = response.data;
                        console.log(response.data)
                    })
                        .catch(error => {
                            this.errors.push(error)
                        });

                } else {
                    console.log("FINDING HASHTAGS...");
                    api.getLeafHashtags(this.selected2LevelId, r => {
                        this.entries = r.data;
                    });
                }
            },
        },
    }
</script>