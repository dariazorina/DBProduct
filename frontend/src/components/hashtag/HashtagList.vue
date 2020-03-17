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


            <div class="row  align-items-center">
                <label for="add-name" class="col-1 col-form-label labelInCreation">Тип</label>
                <div class="col-4">
                    <input class="form-control" id="add-name" v-model="hashtag.content" required/>
                </div>
            </div>

            <div class="row  align-items-center">
                <label for="add-misc" class="col-1 col-form-label labelInCreation">Комментарий</label>
                <div class="col-4">
                    <input class="form-control" id="add-misc" v-model="hashtag.type" required/>
                </div>
            </div>

            <div class="row">
                <div class="offset-sm-2 col-sm-3">
                    <button type="button" @click="addOrUpdateHashtag" class="btn btn-primary">Create/Update</button>
                    <a class="btn btn-default">
                        <router-link to="/language">Cancel</router-link>
                    </a>
                </div>
            </div>


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

        </form>

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
                selectedType: null,
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
        },

        methods: {

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

            addOrUpdateHashtag() {
                this.hashtag.type = this.selectedType;
                this.hasError = false;


                if (this.formValidate()) {
                    if (this.hashtag.id != null) {
                        api.update(this.hashtag.id, this.hashtag, r => {
                            // router.push('/article');
                            this.search();
                        });
                    } else {
                        api.create(this.hashtag, r => {
                            this.search();
                        })
                    }
                }//formValidate
            },

            // updateArticleStatus(id, status) {
            //
            //     api.findById(id, r => {
            //         this.article = r.data;
            //
            //         this.article.status = status;
            //         api.update(this.article.id, this.article, r => {
            //             //router.push('/article');
            //             // this.entries = r.data;
            //             // console.log("UPDATE= = = = = = =");
            //             // this.$forceUpdate();
            //             // this.filteredArticles();
            //             // document.location.reload(true);
            //
            //             this.search();
            //         });
            //
            //
            //         // this.article.date = this.formatDate(this.article.date);
            //
            //         // this.tags = this.article.hashtagList;
            //         // for (let i = 0; i < this.article.hashtagList.length; i++) {
            //         //     this.tags.push(this.article.hashtagList[i].content);
            //         // }
            //
            //         // for (let i = 0; i < this.article.linkList.length; i++) {
            //         //     this.links.push(this.article.linkList[i].content);
            //         // }
            //     });
            //
            //
            // },


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
                // console.log("SEARCH", this.searchKey);

                // let emptyArray = [];
                // let nonExistantArray = undefined;


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

                        } else if (this.selected === "комментарий") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {   //ch+
                                api.searchDescription(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchDescription(this.searchKey, -1, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "тип") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {
                                api.searchTitle(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                console.log("NIT");
                                api.searchTitle(this.searchKey, -1, r => {
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
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                });
        },


        watch: {
            searchKey: function () {
                //If empty search to renew the table
                console.log("WATCH");
                if (this.searchKey == "") {
                    // if (this.statusCheckBox.length > 0) {
                    this.search();
                    // } else {
                    //this.entries = this.articles;
                    // api.searchPeriodAndStatus(-1, this.startDate, this.endDate, r => {
                    //     this.entries = r.data;
                    // });
                    // }
                }
            },

            statusCheckBox: function () {
                console.log("WATCH SCB!");
                console.log(this.statusCheckBox);

                // if (this.statusCheckBox[this.statusCheckBox.length - 1] == 3) {
                //     console.log("this.statusCheckBox!!! == 3");
                //
                //     document.getElementById("startdate-input").style.backgroundColor = "lightgrey";
                //     document.getElementById("enddate-input").style.backgroundColor = "lightgrey";
                //
                //     document.getElementById("startdate-input").disabled = true;
                //     document.getElementById("enddate-input").disabled = true;
                //     document.getElementById("refreshButton").disabled = true;
                //
                //     for (let i = 0; i < this.statusCheckBox.length; i++) {
                //         if (this.statusCheckBox[i] != 3) {
                //             this.statusCheckBox.shift();
                //             console.log("SHIFT");
                //             // document.getElementById("startdate-input").style.backgroundColor = "green";
                //         }
                //     }
                //
                //     console.log("AFTER", this.statusCheckBox);
                // } else {
                //     document.getElementById("startdate-input").style.backgroundColor = "white";
                //     document.getElementById("enddate-input").style.backgroundColor = "white";
                //
                //     document.getElementById("startdate-input").disabled = false;
                //     document.getElementById("enddate-input").disabled = false;
                //     document.getElementById("refreshButton").disabled = false;
                //
                //     for (let i = 0; i < this.statusCheckBox.length; i++) {
                //         if (this.statusCheckBox[i] == 3) {
                //             this.statusCheckBox.splice(i, 1);
                //             console.log("SPLICE");
                //
                //             // document.getElementById("startdate-input").style.backgroundColor = "white";
                //         }
                //     }
                // }

                this.search();
            },


            selected: function () {
                if (this.searchKey != "") {
                    this.search();
                }
            },
        },
    }
</script>