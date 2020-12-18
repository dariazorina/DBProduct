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


            <!--            ////////////////////////////////date period/////////////////////////////////////-->

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

                    <!--                                        <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px" @click="momentFormat()">-->
                    <!--                                            <v-icon style="color: green">mdi-pencil</v-icon>-->
                    <!--                                        </v-btn>-->
                </div>

                <div class="row align-items-lg-baseline" style="background-color: white">
                    <label for="enddate-input" class="col-form-label">Конец</label>

                    <div class="col-9" style="margin-left: 10px; margin-bottom: -15px">
                        <input class="form-control" type="date" id="enddate-input" v-model="endDate"/>
                    </div>
                </div>

                <div>
                    <!--                    <a class="btn btn-default" onclick="refreshPeriod">-->
                    <button id="refreshButton" type="button" class="btn btn-link" @click="refreshPeriod">
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
                            v-model="statusCheckBox"
                            :key="option.key"
                            :value="option.value"
                            name="statusSelection"
                    >
                        {{ option.text }}
                    </b-form-checkbox>
                </b-form-group>

            </div>
        </div>
        <!--/////////////////////////////////////  T  A  B  L  E  //////////////////////////////////////////////////////////-->

        <table class="redTable" :key="authorComponentKey">
            <!--        <table class="table">-->
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <!--                <th class='tdTitle'>Id</th>-->
                <th class='tdTitle' style="color:lightgray; width: 3%">Статус</th>
                <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat">Дата</th>
                <th class='tdTitle'>Язык</th>
                <th class='tdTitle'>Хештеги</th>
                <th class='tdTitle'>Автор</th>
                <th class='tdTitle'>Заголовок</th>
                <th class='tdTitle' style="width:25%">Описание</th>
                <th class='tdTitle' style="width:15%; color:lightgray">Комментарии</th>

                <!--                <template v-if="statusCheckBox.length!=1">-->
                <!--            <span v-show="statusCheckBox.length!=1">-->

                <!--                    </span>-->
                <!--                </template>-->
                <!--                <th style="width:10%">Links</th>-->

                <th class="tdTitle" style="width:4%"></th>
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
                <td :key="article.status">
                    <div v-if="article.status==0">
                        <v-icon style="color: orange">mdi-pencil-plus</v-icon>
                    </div>

                    <div v-if="article.status==1">
                        <v-icon style="color: orange">mdi-check</v-icon>
                        <!--                                                <v-icon style="color: green">mdi-pencil-lock</v-icon>-->
                    </div>

                    <div v-if="article.status==2">
                        <v-icon style="color: red">mdi-clipboard-arrow-left</v-icon>
                    </div>

                    <div v-if="article.status==3">
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
                    <div v-for="hashtag in article.hashtagList">
                        {{hashtag}}
                    </div>
                </td>
                <td>
                    <div v-for="author in article.personList">
                        <div v-if="article.personList.length>0">
                            {{createComplexCellValueById(author.itemId)}}
                        </div>
                    </div>
                </td>

                <!--                <td class='tdAlignLeft'>{{ article.movement.name}}</td>-->
                <!--                <td class='tdAlignLeft'><a>-->
                <!--                    <router-link :to="{name: 'article-details', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                    </router-link>-->
                <!--                </a></td>-->

                <!--            <td>-->
                <!--                {{article.titleRus }}-->
                <!--            </td>-->


                <!--                <td>-->
                <!--                    <div v-if="article.titleRus">-->
                <!--                        <a>-->
                <!--                            <router-link :to="{name: 'article-details', params: {article_id: article.id}}">-->
                <!--                                {{article.titleRus }}-->
                <!--                            </router-link>-->
                <!--                        </a>-->
                <!--                    </div>-->
                <!--                </td>-->

                <!--                <td>-->
                <!--                    <div v-if="article.titleRus">-->
                <!--                        {{article.title}}-->
                <!--                    </div>-->
                <!--                    <div v-else>-->
                <!--                        <a>-->
                <!--                            <router-link :to="{name: 'article-details', params: {article_id: article.id}}">-->
                <!--                                {{article.title }}-->
                <!--                            </router-link>-->
                <!--                        </a>-->
                <!--                    </div>-->
                <!--                </td>-->

                <td>
                    <div>
                        <!--                    <div style="white-space:pre-line">-->
                        <a>
                            <router-link :to="{name: 'article-details', params: {article_id: article.id}}">
                                {{createComplexCellValue(article.titleRus, article.title)}}
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


                <!--                <template v-if="statusCheckBox.length!=1">-->
                <!--            <span v-show="statusCheckBox.length!=1">-->

                <!--                    </span>-->
                <!--                </template>-->
                <!--                </div>-->

                <!--                <td>-->
                <!--                    <div v-for="link in article.linkList">{{link.content}}</div>-->
                <!--                </td>-->


                <td>
                    <v-btn text icon x-small>
                        <a>
                            <router-link :to="{name: 'article-add', params: {article_id: article.id}}">
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
                </td>
                <td>
                    <v-btn text icon x-small @click="updateArticleStatus(article.id, 2)">
                        <v-icon style="color: red">mdi-clipboard-arrow-left</v-icon>
                    </v-btn>

                    <v-btn text icon x-small @click="updateArticleStatus(article.id, 3)">
                        <v-icon style="color: green">mdi-check</v-icon>

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
    import api from "./article-api";
    import apiPerson from "./../person/person-api";

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
                article: {status: 0, personList: [], hashtagList: []},
                authors: [],
                articlePersonIds: [], //before request
                articlePersonEntities: [], //after request
                authorComponentKey: 0,

                response: [],
                errors: [],

                entries: [],

                showResponse: false,
                loggedInFlag: false,
                loggedName: null,

                selected: "заголовок",
                searchItems: ["хештег", "заголовок", "автор", "язык", "описание"],
                searchKey: '',

                statusCheckBox: [],
                options: [
                    {text: 'In Progress', value: 0},
                    {text: 'Done', value: 1},
                    {text: 'Returned', value: 2},
                    {text: 'Completed', value: 3},

                ],
                message: 'One Line,\nTwo Lines.',
            }
        },
        computed: {
            filteredArticles() {
                // console.log("FILTERED ARTICLES");
                return this.entries;
            },
        },

        methods: {

            createComplexCellValueById(id) {
                let result = '';
                let currentPerson = this.articlePersonEntities.find(x => x.id === id);

                let valueOrig = '', valueRus = '';

                if (this.isArrayValidAndNotEmpty(currentPerson)) {//to prevent errors in console when search result isn't ready yet
                    // if (this.isArrayValidAndNotEmpty(currentPerson.surname)) { //mandatory field in Person
                        valueOrig = currentPerson.surname;
                    // }

                    if (this.isArrayValidAndNotEmpty(currentPerson.surname)) {
                        valueRus = currentPerson.surnameRus;
                    }

                    // if (this.isArrayValidAndNotEmpty(currentPerson.name)) { //mandatory field in Person
                        valueOrig += " " + currentPerson.name;
                    // }

                    if (this.isArrayValidAndNotEmpty(currentPerson.nameRus)) {
                        valueRus += " " + currentPerson.nameRus;
                    }

                    if (this.isArrayValidAndNotEmpty(valueRus)) {
                        result = valueRus;
                        if (this.isArrayValidAndNotEmpty(valueOrig)) {
                            if (valueRus.localeCompare(valueOrig) !== 0)
                                result += " / " + valueOrig;
                        }
                    } else if (this.isArrayValidAndNotEmpty(valueOrig))
                        result += valueOrig;
                    return result;
                }
            },

            createComplexCellValue(valueRus, valueOrig) {
                let result = '';
                // console.log("RUS - ", valueRus, "ORIG - ", valueOrig);

                if (this.isArrayValidAndNotEmpty(valueRus)) {
                    result = valueRus;
                    if (this.isArrayValidAndNotEmpty(valueOrig))
                        result += " / " + valueOrig;
                } else if (this.isArrayValidAndNotEmpty(valueOrig))
                    result += valueOrig;
                return result;
            },

            updateArticleStatus(id, status) {

                api.findById(id, r => {
                    this.article = r.data;

                    this.article.status = status;
                    api.update(this.article.id, this.article, r => {
                        //router.push('/article');
                        // this.entries = r.data;
                        // console.log("UPDATE= = = = = = =");
                        // this.$forceUpdate();
                        // this.filteredArticles();
                        // document.location.reload(true);

                        this.search();
                    });


                    // this.article.date = this.formatDate(this.article.date);

                    // this.tags = this.article.hashtagList;
                    // for (let i = 0; i < this.article.hashtagList.length; i++) {
                    //     this.tags.push(this.article.hashtagList[i].content);
                    // }

                    // for (let i = 0; i < this.article.linkList.length; i++) {
                    //     this.links.push(this.article.linkList[i].content);
                    // }
                });


            },

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
                    Vue.prototype.startDate = this.startDate;
                    Vue.prototype.endDate = this.endDate;

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

            complexStatusCreation() {

                let result = '';
                let complexStatus = '&status=';
                let i = 0;

                // result = complexStatus;
                // if (this.statusCheckBox.length>0)

                for (; i < this.statusCheckBox.length - 1; i++) {
                    result += this.statusCheckBox[i] + complexStatus;
                }

                result += this.statusCheckBox[i];

                console.log(result);
                return result;
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
                                api.searchHash(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "автор") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {  //ch+
                                api.searchAuthor(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchAuthor(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "язык") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {    //ch+
                                api.searchLanguage(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchLanguage(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "описание") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {   //ch+
                                api.searchDescription(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                api.searchDescription(this.searchKey, -1, this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            }

                        } else if (this.selected === "заголовок") {
                            if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {
                                api.searchTitle(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                console.log("NIT");
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
            this.getLoggedIn();

            // api.getAll().then(response => {
            //     this.articles = response.data;
            //     this.entries = this.articles;
            //     // console.log(response.data)
            // })
            //     .catch(error => {
            //         this.errors.push(error)
            //     })

            api.searchPeriodAndStatus(-1, this.startDate, this.endDate, r => {
                this.entries = r.data;
                console.log(this.entries);
                for (let i = 0; i < this.entries.length; i++) {
                    for (let j = 0; j < this.entries[i].personList.length; j++) {
                        this.articlePersonIds.push(this.entries[i].personList[j].itemId);
                    }
                }
                console.log(this.articlePersonIds);

                apiPerson.getPersonsByIds(this.articlePersonIds, response => {
                    this.articlePersonEntities = response.data;
                    console.log("apiPerson", this.articlePersonEntities);
                });
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

                if (this.statusCheckBox[this.statusCheckBox.length - 1] == 3) {
                    console.log("this.statusCheckBox!!! == 3");

                    document.getElementById("startdate-input").style.backgroundColor = "lightgrey";
                    document.getElementById("enddate-input").style.backgroundColor = "lightgrey";

                    document.getElementById("startdate-input").disabled = true;
                    document.getElementById("enddate-input").disabled = true;
                    document.getElementById("refreshButton").disabled = true;

                    for (let i = 0; i < this.statusCheckBox.length; i++) {
                        if (this.statusCheckBox[i] != 3) {
                            this.statusCheckBox.shift();
                            console.log("SHIFT");
                            // document.getElementById("startdate-input").style.backgroundColor = "green";
                        }
                    }

                    console.log("AFTER", this.statusCheckBox);
                } else {
                    document.getElementById("startdate-input").style.backgroundColor = "white";
                    document.getElementById("enddate-input").style.backgroundColor = "white";

                    document.getElementById("startdate-input").disabled = false;
                    document.getElementById("enddate-input").disabled = false;
                    document.getElementById("refreshButton").disabled = false;

                    for (let i = 0; i < this.statusCheckBox.length; i++) {
                        if (this.statusCheckBox[i] == 3) {
                            this.statusCheckBox.splice(i, 1);
                            console.log("SPLICE");

                            // document.getElementById("startdate-input").style.backgroundColor = "white";
                        }
                    }
                }

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