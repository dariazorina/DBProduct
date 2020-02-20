<template id="article">
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


            <div class="form-group col-2"
                 style="margin-left:auto; background-color: white">


                <div class="row align-items-lg-baseline"
                     style="margin-top: -20px;  margin-bottom: -5px; background-color: white">

                    <label for="startdate-input" class="col-form-label">Начало</label>

                    <div class="col-9"
                         style="margin-bottom: -15px">
                        <input class="form-control" id="startdate-input" type="date" v-model="startDate"
                               style="background-color: white"/>
                    </div>

                    <!--                    <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px" @click="momentFormat()">-->
                    <!--                        <v-icon style="color: green">mdi-pencil</v-icon>-->
                    <!--                    </v-btn>-->
                </div>

                <div class="row align-items-lg-baseline" style="background-color: white">
                    <label for="enddate-input" class="col-form-label">Конец</label>

                    <div class="col-9" style="margin-left: 10px; margin-bottom: -15px">
                        <input class="form-control" type="date" id="enddate-input" v-model="endDate"/>
                    </div>
                </div>

                <div>
                    <!--                    <a class="btn btn-default" onclick="refreshPeriod">-->
                    <button type="button" class="btn btn-link" @click="refreshPeriod">
                        Refresh period
                        <!--                    </a>-->
                    </button>
                    <v-icon style="color: #0074D9">mdi-database-refresh</v-icon>
                </div>

            </div>
        </div>


        <!--        <div class="logoutNameAndButton">welcome, {{loggedName}}!-->
        <!--            <b-btn v-if="loggedInFlag" size="sm" variant="outline-secondary" v-b-modal.modal1>Logout</b-btn>-->
        <!--            &lt;!&ndash; Modal Component &ndash;&gt;-->
        <!--            <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>-->
        <!--        </div>-->


        <div style="margin-top: -50px">
            <a class="btn btn-default">
                <router-link :to="{name: 'article-add'}">Add article</router-link>
            </a>
            <v-icon style="color: #0074D9">mdi-shape-rectangle-plus</v-icon>
        </div>

        <!--        ////////////////////////////////////////search//////////////////////////////////-->
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
                <span class="close" @click="deleteSearch()" style="margin-top: -31px; margin-right: 5px">&times;</span>
                <!--                todo-->
            </div>

            <div style="padding-top: 11px">
                <button type="button" @click="search" class="btn btn-primary">Search</button>
            </div>

            <!--//////////////////////////////////STATUS////////////////////////////////////////////////////////////////            -->

            <div class="col-2" style="margin-left:auto; margin-right:0; background-color: white">
                <b-form-group label="" style="text-align: left; margin-left: -15px">
                    <b-form-checkbox
                            v-for="option in options"
                            v-model="selectedCheckBox"
                            :key="option.key"
                            :value="option.value"
                            name="statusSelection"
                    >
                        {{ option.text }}
                    </b-form-checkbox>
                </b-form-group>

            </div>
        </div>
        <!--///////////////////////////////////////////////////////////////////////////////////////////////-->

        <table class="redTable">
            <!--        <table class="table">-->
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <!--                <th class='tdTitle'>Id</th>-->
                <th class='tdTitle' style="color:lightgray">Статус</th>
                <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat">Дата</th>
                <th class='tdTitle'>Язык</th>
                <th class='tdTitle'>Хештеги</th>
                <th class='tdTitle' style="width:6%">Автор</th>
                <th class='tdTitle' style="width:6%">Автор, русск</th>

                <!--                <th class='tdAlignCell'>Movement</th>-->
                <th class='tdTitle'>Заголовок</th>
                <!--            <th class='tdAlignCell' style="width:15%">Оригинальный заголовок</th>-->
                <th class='tdTitle'>Оригинальный заголовок</th>
                <!--                <th class='tdTitle'>URL</th>-->

                <th class='tdTitle' style="width:20%">Описание</th>
                <th class='tdTitle' style="width:14%; color:lightgray">Комментарии</th>

                <!--                <template v-if="selectedCheckBox.length!=1">-->
                <!--            <span v-show="selectedCheckBox.length!=1">-->

                <!--                    </span>-->
                <!--                </template>-->
                <!--                <th style="width:10%">Links</th>-->

                <th class="tdTitle" style="width:4%"></th>
            </tr>
            </thead>
            <tbody>
            <!--            <tr v-for="article in articles" class="ListCellStyleHot">-->
            <!--                        <tr v-for="article in articles">-->
            <tr v-for="article in filteredArticles">

                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <!--                <td>-->
                <!--                    <span id=t>{{article.id }}</span>-->
                <!--                </td>-->
                <td>
                    <div v-if="article.status==0">
                        <v-icon style="color: orange">mdi-pencil-plus</v-icon>
                    </div>
                    <div v-else>
                        <v-icon style="color: green">mdi-check</v-icon>
                        <!--                        <v-icon style="color: green">mdi-pencil-lock</v-icon> -->
                    </div>
                </td>

                <td>
                    {{ formatDate(article.date) }}
                </td>
                <td>
                    {{article.language.name}}
                </td>
                <td>
                    <div v-for="hashtag in article.hashtagList">{{hashtag.content}}</div>
                </td>
                <td>
                    <div v-for="author in article.authorList">{{author.surname}}</div>
                </td>
                <td>
                    <div v-for="author in article.authorList">{{author.surnameRus}}</div>
                </td>

                <!--                <td class='tdAlignLeft'>{{ article.movement.name}}</td>-->
                <!--                <td class='tdAlignLeft'><a>-->
                <!--                    <router-link :to="{name: 'article-details', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                    </router-link>-->
                <!--                </a></td>-->

                <!--            <td>-->
                <!--                {{article.titleRus }}-->
                <!--            </td>-->


                <td>
                    <div v-if="article.titleRus">
                        <a>
                            <router-link :to="{name: 'article-details', params: {article_id: article.id}}">
                                {{article.titleRus }}
                            </router-link>
                        </a>
                    </div>
                </td>

                <td>
                    <div v-if="article.titleRus">
                        {{article.title}}
                    </div>
                    <div v-else>
                        <a>
                            <router-link :to="{name: 'article-details', params: {article_id: article.id}}">
                                {{article.title }}
                            </router-link>
                        </a>
                    </div>
                </td>


                <td style="height: 50px">
                    <div style="height:60px; overflow:hidden">
                        {{article.description }}
                    </div>
                </td> <!--todo dots? if cut-->

                <td style="height: 50px">
                    <div style="height:60px; overflow:hidden">
                        {{article.miscellany }}
                    </div>
                </td>


                <!--                <template v-if="selectedCheckBox.length!=1">-->
                <!--            <span v-show="selectedCheckBox.length!=1">-->

                <!--                    </span>-->
                <!--                </template>-->
                <!--                </div>-->

                <!--                <td>-->
                <!--                    <div v-for="link in article.linkList">{{link.content}}</div>-->
                <!--                </td>-->


                <td>
                    <!--                    <div class="ListCellStyleForButton">-->
                    <!--                    <a class="btn btn-warning btn-sm mr-2">-->
                    <!--                        <router-link :to="{name: 'article-edit', params: {article_id: article.id}}">Edit-->
                    <!--                        </router-link>-->
                    <!--                    </a>-->
                    <!--                    <a class="btn btn-danger btn-sm">-->
                    <!--                        <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">Delete-->
                    <!--                        </router-link>-->
                    <!--                    </a>-->


                    <v-btn text icon x-small>
                        <a>
                            <router-link :to="{name: 'article-edit', params: {article_id: article.id}}">
                                <v-icon style="color: green">mdi-pencil</v-icon>
                            </router-link>
                        </a>
                    </v-btn>


                    <v-btn text icon x-small>
                        <a>
                            <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">
                                <v-icon style="color: red">mdi-delete-forever</v-icon>
                            </router-link>
                        </a>
                    </v-btn>


                    <!--                    </div>-->

                    <!--                    <v-btn :to="{name: 'article-edit', params: {article_id: article.id}}"-->
                    <!--                           light small right bottom fab-->
                    <!--                           class="pink" slot="action">-->

                    <!--                        <v-icon right dark >cloud_upload</v-icon>-->
                    <!--&lt;!&ndash;                        <v-icon small class="mr-2">smiley-neutral-outline</v-icon>&ndash;&gt;-->
                    <!--&lt;!&ndash;                        <v-icon>mdi-watch</v-icon>&ndash;&gt;-->
                    <!--                    </v-btn>-->


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

</style>

<script>

    import api from "./article-api";
    import moment from "moment";
    import router from "./../../router";
    import "vue-scroll-table";
    import Vuetify from 'vuetify';
    // import 'vuetify/dist/vuetify.min.css';
    import '@mdi/font/css/materialdesignicons.css' //why does icon appear in other file)) add article?
    // import 'material-design-icons-iconfont/dist/material-design-icons.css'


    export default {
        name: 'article',
        vuetify: new Vuetify(),
        data() {
            return {
                articles: [],
                article: {authorList: []},
                authors: [],

                response: [],
                errors: [],

                entries: [],

                showResponse: false,
                loggedInFlag: false,
                loggedName: null,

                selected: "заголовок",
                searchItems: ["хештег", "заголовок", "автор", "язык", "описание"],
                searchKey: '',

                selectedCheckBox: [],
                options: [
                    {text: 'In Progress', value: 0},
                    {text: 'Done', value: 1},]
            }
        },
        computed: {
            filteredArticles() {
                // console.log("FILTERED ARTICLES");
                return this.entries;
            },
        },

        methods: {
            refreshPeriod() {
                //console.log("ACHTUNG new start", this.startDate);
               // console.log("ACHTUNG end start", this.endDate);

                const moment = require('moment');

                let d1 = moment(this.startDate);
                let d2 = moment(this.endDate);

                let days = d2.diff(d1, 'days');
                console.log(`Difference in days: ${days}`);

                let hours = d2.diff(d1, 'hours');
                console.log(`Difference in hours: ${hours}`);

                if (days < 0) {
                    alert("You select incorrect period of time. Nothing will be done");
                } else {
                     this.search();

                    // api.searchPeriod(this.startDate, this.endDate, r => {
                    //     this.entries = r.data;
                    // });

                }
            },

            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
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

            deleteSearch() {
                this.searchKey = "";
                // this.entries = this.articles;
            },

            // dateFormat(value, row, index) {  //todo
            //     moment(this.article.date).format('DD/MM/YYYY').then(response => {
            //         this.article.date = response.data;
            //     })
            // },

            search() {
                // console.log("SEARCH", this.searchKey);

                if (this.searchKey === "" && this.selectedCheckBox.length != 1) {//s- ch-
                    // this.entries = this.articles;

                    api.searchPeriod(this.startDate, this.endDate, r => {
                        this.entries = r.data;
                    });

                } else {
                    if (this.searchKey === "") {    //s-
                        if (this.selectedCheckBox.length == 1) { //ch+
                            api.searchWithStatus(this.selectedCheckBox[0], this.startDate, this.endDate, r => {
                                this.entries = r.data;
                            });
                        }
                    } else {        //s+
                        if (this.selected === "хештег") {
                            if (this.selectedCheckBox.length == 1) { //ch+
                                api.searchHash(this.searchKey, this.selectedCheckBox[0], this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchHash(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "автор") {
                            if (this.selectedCheckBox.length == 1) {    //ch+
                                api.searchAuthor(this.searchKey, this.selectedCheckBox[0], this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchAuthor(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "язык") {
                            if (this.selectedCheckBox.length == 1) {    //ch+
                                api.searchLanguage(this.searchKey, this.selectedCheckBox[0], this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchLanguage(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "описание") {
                            if (this.selectedCheckBox.length == 1) {    //ch+
                                api.searchDescription(this.searchKey, this.selectedCheckBox[0], this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchDescription(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "заголовок") {
                            if (this.selectedCheckBox.length == 1) {        //ch+
                                api.searchTitle(this.searchKey, this.selectedCheckBox[0], this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchTitle(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }
                        }
                    }
                }
            },
        },

        mounted() {

           // console.log("START DATE", this.startDate);
           // console.log("END DATE", this.endDate);

            this.getLoggedIn();

            // api.getAll().then(response => {
            //     this.articles = response.data;
            //     this.entries = this.articles;
            //     // console.log(response.data)
            // })
            //     .catch(error => {
            //         this.errors.push(error)
            //     })

            api.searchPeriod(this.startDate, this.endDate, r => {
                this.entries = r.data;
            });
        },

        watch: {
            searchKey: function () {
                // console.log("WATCH");
                if (this.searchKey == "") {
                    if (this.selectedCheckBox.length == 1) {
                        this.search();
                    } else {
                        //this.entries = this.articles;

                        api.searchPeriod(this.startDate, this.endDate, r => {
                            this.entries = r.data;
                        });
                    }
                }
            },

            selectedCheckBox: function () {
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