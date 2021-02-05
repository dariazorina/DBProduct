<template id="article">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="form-group row" style="margin-bottom: -10px; margin-top: -20px; background-color: transparent">
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

            <!--            <div class="col-4"-->
            <!--                 style="margin-left:auto; background-color: yellow">-->


            <!--                <div class="align-items-lg-baseline"-->
            <!--                     style="margin-top: -20px;  margin-bottom: -5px; background-color: #b5d592">-->

            <!--&lt;!&ndash;                    <label for="startdate-input" class="col-form-label">Начало</label>&ndash;&gt;-->

            <!--                    <div class="col-4"-->
            <!--                         style="margin-bottom: -15px; background-color: khaki">-->
            <!--                        <input class="form-control" id="startdate-input" type="date" v-model="startDate"-->
            <!--                               style="background-color: lightcyan"/>-->
            <!--&lt;!&ndash;                    </div>&ndash;&gt;-->

            <!--                    &lt;!&ndash;                                        <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px" @click="momentFormat()">&ndash;&gt;-->
            <!--                    &lt;!&ndash;                                            <v-icon style="color: green">mdi-pencil</v-icon>&ndash;&gt;-->
            <!--                    &lt;!&ndash;                                        </v-btn>&ndash;&gt;-->
            <!--&lt;!&ndash;                </div>&ndash;&gt;-->

            <!--&lt;!&ndash;                <div class="row align-items-lg-baseline" style="background-color: #ba68c8">&ndash;&gt;-->
            <!--&lt;!&ndash;                    <label for="enddate-input" class="col-form-label">Конец</label>&ndash;&gt;-->

            <!--&lt;!&ndash;                    <div class="col-7" style="margin-left: 10px; margin-bottom: -15px; background-color: #4183c4">&ndash;&gt;-->
            <!--                        <input class="form-control" type="date" id="enddate-input" v-model="endDate"/>-->
            <!--                    </div>-->
            <!--                </div>-->

            <div class="form-group row col-9"
                 style="margin: auto; background-color: transparent; padding-right: 0px; padding: 0;">

                <div class="col-5"
                     style="margin-top: 0px; background-color: transparent; padding-top: 0px; padding-bottom:0px; padding-left: 280px">
                    <a class="btn btn-default">
                        <router-link :to="{name: 'article-add'}">Add article</router-link>
                    </a>
                    <v-icon style="color: #0074D9">mdi-shape-rectangle-plus</v-icon>
                </div>

                <div class="form-group row col-3" style="margin-left: 32%; padding:0; background-color: transparent">

                    <div class="col-5"
                         style="background-color: transparent; padding-left: 2px; padding-right: 2px; margin-left: 0px; padding-bottom: 0px; padding-top: 0px">
                        <input class="form-control" id="startdate-input" type="date" v-model="startDate"
                               style="background-color: transparent;padding-left: 5px; padding-right: 2px;"/>
                    </div>

                    <div class="col-5"
                         style="background-color: transparent; padding-left: 2px; padding-right: 2px; padding-bottom: 0px; padding-top: 0px">
                        <input class="form-control" type="date" id="enddate-input" v-model="endDate"
                               style="padding-left: 5px; padding-right: 2px; "/>
                    </div>
                    <!--            </div>-->

                    <!--            <div>-->
                    <!--                    <a class="btn btn-default" onclick="refreshPeriod">-->
                    <div class="col-2"
                         style="background-color: transparent; padding-left: 0px; padding-bottom: 0px; padding-top: 0px; margin-left: 0px">
                        <button id="refreshButton" type="button" class="btn" @click="refreshPeriod">
                            <!--                    Refresh period-->
                            <!--                    </a>-->
                            <v-icon style="color: #0074D9">mdi-database-refresh</v-icon>
                        </button>
                    </div>

                </div>
            </div>
        </div>


        <!--        <div class="logoutNameAndButton">welcome, {{loggedName}}!-->
        <!--            <b-btn v-if="loggedInFlag" size="sm" variant="outline-secondary" v-b-modal.modal1>Logout</b-btn>-->
        <!--            &lt;!&ndash; Modal Component &ndash;&gt;-->
        <!--            <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>-->
        <!--        </div>-->


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

            <!--//////////////////////////////////STATUS////////////////////////////////////////////////////////////////-->

            <div class="form-group row col-2"
                 style="margin-left:auto; margin-right:0; background-color: transparent; padding: 0;  margin-top: 0; margin-bottom: 0">
                <div class="col-6">
                    <b-form-group label="" style="text-align: left; margin-left: -15px; background-color: transparent">
                        <b-form-checkbox
                                v-for="option in options1"
                                v-model="statusCheckBox"
                                :key="option.key"
                                :value="option.value"
                                name="statusSelection">
                            {{ option.text }}
                        </b-form-checkbox>
                    </b-form-group>
                </div>
                <div class="col-6">
                    <b-form-group label="" style="text-align: left; margin-left: -15px; background-color: transparent">
                        <b-form-checkbox
                                v-for="option in options2"
                                v-model="statusCheckBox"
                                :key="option.key"
                                :value="option.value"
                                name="statusSelection">
                            {{ option.text }}
                        </b-form-checkbox>
                    </b-form-group>
                </div>
            </div>
        </div>

        <!--/////////////////////////////////////  T  A  B  L  E  //////////////////////////////////////////////////////////-->
        <!--        <div style="height:50%; background-color: #b5d592">-->
        <table class="redTable" :key="authorComponentKey" id="mainListTable">
            <thead>
            <tr>
                <th class='tdTitle headerLink' style="color:lightgray; width: 3%" @click="sort('status')">Статус</th>
                <th class='tdTitle headerLink' data-field="createdAt" data-formatter="dateFormat" @click="sort('date')">
                    Дата
                </th>
                <th class='tdTitle'>Язык</th>
                <th class='tdTitle'>Хештеги</th>
                <th class='tdTitle'>Автор</th>
                <th class='tdTitle headerLink' @click="sort('title')">Заголовок</th>
                <th class='tdTitle' style="width:25%">Описание</th>
                <th class='tdTitle' style="width:15%; color:lightgray">Комментарии</th>
                <th class="tdTitle" style="width:4%">Действия</th>
                <th class="tdTitle" style="width:4%">Смена статуса</th>
                <th class="tdTitle" style="width:4%">Добавить материал..</th>
                <!--                    <th class="tdTitle" style="width:4%">Цвет</th>-->
            </tr>
            </thead>
            <tbody>
            <!--            <tr v-for="article in filteredArticles"-->
            <tr v-for="article in sortedArticles"
                @contextmenu.prevent="handler($event, article.id)"
                :key="article.id"
                :id="article.id"
                :class="{'active': ((article.id === selectedArticle)&&(previousSelectedArticle!==selectedArticle))}">

                <td :key="article.status">
                    <div v-if="article.status==0">
                        <v-icon style="color: orange">mdi-pencil-plus</v-icon>
                    </div>

                    <div v-if="article.status==1">
                        <v-icon style="color: orange">mdi-check</v-icon>
                    </div>

                    <div v-if="article.status==2">
                        <v-icon style="color: red">mdi-clipboard-arrow-left</v-icon>
                    </div>

                    <div v-if="article.status==3">
                        <v-icon style="color: green">mdi-check</v-icon>
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
                <td>
                    <v-btn text icon x-small v-b-modal="'modal-scoped'" @click="setCurrentAricle(article.id)">
                        <v-icon style="color: orange">mdi-account-multiple-plus</v-icon>
                    </v-btn>
                </td>
                <!--                    <td>-->
                <!--                        <b-button size="sm" variant="danger" @click="setColor(article, colors[0])" style="padding: 0">-->
                <!--                            красный-->
                <!--                        </b-button>-->
                <!--                        <b-button size="sm" variant="warning" @click="setColor(article, colors[1])" style="padding: 0">-->
                <!--                            желтый-->
                <!--                        </b-button>-->
                <!--                        <b-button size="sm" variant="success" @click="setColor(article, colors[2])" style="padding: 0">-->
                <!--                            зеленый-->
                <!--                        </b-button>-->
                <!--                        <b-button size="sm" variant="info" @click="setColor(article, colors[3])" style="padding: 0">-->
                <!--                            голубой-->
                <!--                        </b-button>-->
                <!--                    </td>-->
            </tr>
            </tbody>
        </table>
        <!--        </div>-->
        <b-modal id="modal-scoped" :hide-footer="true" :hide-header="true">
            <template>
                <b><p class="myHeader">Добавить материал к статье "{{complexArticleTitle()}}" как</p><br></b>
                <div align="center">
                    <b-button size="sm" variant="success"
                              @click="createArticleWithMaterial(currentArticle.id, connectionType[2])">
                        родителя
                    </b-button>
                    <b-button size="sm" variant="danger"
                              @click="createArticleWithMaterial(currentArticle.id, connectionType[0])">
                        потомка
                    </b-button>
                    <b-button size="sm" variant="info"
                              @click="createArticleWithMaterial(currentArticle.id, connectionType[1])">
                        равноправный
                    </b-button>
                </div>
            </template>
        </b-modal>

        <context-menu id="context-menu" ref="ctxMenu">
            <li class="ctx-item" v-b-modal="'modal-color'">выделить...</li>
            <li class="ctx-item" @click="cancelRowSelection">отменить выделение</li>
        </context-menu>

        <b-modal id="modal-color" :hide-footer=true :hide-header=true>
            <template>
                <b><p class="myHeader">Выберите цвет для выделения текущей записи</p><br></b>
                <div align="center">
                    <v-swatches v-model="color" inline></v-swatches>
                    <!--                    <v-swatches v-model="color" inline :swatches="swatches"></v-swatches>-->
                </div>
            </template>
        </b-modal>
    </div>
</template>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet"/>
<style lang="scss">
    @import '../dbnm.css';
</style>
<style>
    .odd {
        background-color: white;
    }

    .even {
        background-color: gray;
    }

    /*.highlight {*/
    /*    background-color: tomato;*/
    /*}*/
    /*tr:hover{*/
    /*    cursor: pointer;*/
    /*}*/

    .active {
        color: blue;
        font-weight: bold;
        border: 3px solid #1C6EA4;
    }
</style>
<script>
    import Vue from 'vue';
    import api from "./article-api";
    import apiPerson from "./../person/person-api";

    import moment from "moment";
    import router from "./../../router";
    import "vue-scroll-table";
    import Vuetify from 'vuetify';
    import '@mdi/font/css/materialdesignicons.css' //why does icon appear in other file)) add article?
    import contextMenu from 'vue-context-menu'

    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import 'vue-swatches/dist/vue-swatches.css'

    export default {
        name: 'article',
        components: {
            contextMenu,
            VSwatches,
        },
        vuetify: new Vuetify(),
        data() {
            return {
                color: '#1CA085',
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
                options1: [
                    {text: 'В работе', value: 0},
                    {text: 'Внесены', value: 1},
                ],
                options2: [
                    {text: 'На доработке', value: 2},
                    {text: 'Отработаны', value: 3},
                ],
                connectionType: [
                    {text: 'потомок', value: 0},
                    {text: 'равноправные', value: 1},
                    {text: 'родитель', value: 2},
                ],
                // currentArticleId: 0,
                currentArticle: [],
                // message: 'One Line,\nTwo Lines.',

                selectedArticle: null,
                previousSelectedArticle: null,

                currentSort: 'date',
                currentSortDir: 'asc'

                // swatches: [
                //     '#FF0000', '#AA0000', '#550000',
                //     '#FFFF00', '#AAAA00', '#555500',
                //     '#00FF00', '#00AA00', '#005500',
                //     '#00FFFF', '#00AAAA', '#005555',
                //     '#0000FF', '#0000AA', '#000055'
                // ],
                //
                // colors: [
                //     '#D90D0D',
                //     '#FBFF1F',
                //     '#01A722',
                //     '#01D3EF'
                // ],
            }
        },
        computed: {
            filteredArticles() {
                // console.log("*************************************FILTERED ARTICLES", this.entries);
                return this.entries;
            },

            sortedArticles() {
                console.log("sorted articles");
                this.authorComponentKey++; //for zebra after sorting

                // if (this.currentSort === 'author'){  //
                //     return this.entries;
                //
                // }
                // else {
                    return this.entries.sort((a, b) => {
                        let modifier = 1;
                        if (this.currentSortDir === 'desc') modifier = -1;
                        if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                        if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                        return 0;
                    });
                // }
             }
        },

        // beforeUpdate() {
        //     console.log(`beforeUpdate At this point, Virtual DOM has not re-rendered or patched yet.`)
        // },

        updated() {
            //console.log(`updated At this point, Virtual DOM has re-rendered and patched.`)
            this.alternate('mainListTable');
        },

        methods: {
            sort(s) {
                console.log("click");
                //if s == current sort, reverse
                if (s === this.currentSort) {
                    this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
                }
                this.currentSort = s;
            },

            handler: function (event, id) {
                // console.log("RIGHT BUTTON", event, event.target, id);

                this.selectRow(id);
                if (this.previousSelectedArticle !== this.selectedArticle) {
                    document.getElementById('context-menu').setAttribute('style', 'display');
                    this.$refs.ctxMenu.open(event);
                } else {
                    // console.log("CNTXT MENU", document.getElementById('context-menu'));
                    document.getElementById('context-menu').setAttribute('style', 'display:none');
                    //classList.add('hidden');
                }
            },

            // optionClicked(event) {
            //     window.alert(JSON.stringify(event))
            // },

            cancelRowSelection() {
                let currentArticle = this.filteredArticles.find(x => x.id === this.selectedArticle);
                this.setColor(currentArticle, null);
            },

            selectRow(articleId) {
                if (this.previousSelectedArticle === this.selectedArticle) { //to set ability to check again after unchecking the same row
                    this.previousSelectedArticle = -1;
                } else {
                    this.previousSelectedArticle = this.selectedArticle;
                }

                this.selectedArticle = articleId;
                // console.log("====================selected row, prev", this.selectedArticle, this.previousSelectedArticle);
            },

            setColor(currentArticle, color) {
                // console.log("set color", currentArticle, color);
                currentArticle.rowColor = color;

                api.update(currentArticle.id, currentArticle, r => {
                    console.log("DONEEEEEE SAVE");
                });
                this.alternate('mainListTable');
            },
            alternate(id) {
                if (document.getElementsByTagName) {
                    let table = document.getElementById(id);
                    if (table !== null) {

                        let rows = table.getElementsByTagName("tr");
                        // console.log("<<<<<<<<<<<<<<<<<<<<<< ROWS", rows.length);
                        for (let i = 0; i < rows.length - 1; i++) {

                            if (this.entries[i] !== null && typeof this.entries[i] !== 'undefined') {
                                if (this.entries[i].rowColor !== null) {
                                    //console.log("this.entries[i].rowColor", this.entries[i], this.entries[i].rowColor);
                                    //let a = document.getElementsByClassName("redTable");
                                    //let a = document.getElementById('mainListTable');//.style;//.backgroundColor;
                                    // let element = document.getElementById('mainListTable');
                                    // console.log("********************CSS COLOR", a);
                                    rows[i + 1].style.backgroundColor = this.entries[i].rowColor;//'#888888';//+1 because of title

                                } else { //todo - get colors from css, not consts
                                    // let id = this.entries[i].id;
                                    // let element = document.getElementById(id.toString());
                                    // let style = window.getComputedStyle(element);
                                    // let top = style.getPropertyValue('background-color');
                                    //console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$css color null");
                                    rows[i + 1].style.backgroundColor = (i % 2) ? "#EEEEEE" : '#D0E4F5';//'#888888';//+1 because of title
                                }
                            }
                        }
                    }
                }
            },
            complexArticleTitle() {
                let title = '';

                if (this.currentArticle.title != null) {
                    if (this.currentArticle.title.length > 0) {
                        title = this.currentArticle.title;

                        if (this.currentArticle.titleRus != null) {
                            if (this.currentArticle.titleRus.length > 0) {
                                title += " / " + this.currentArticle.titleRus;
                            }
                        }
                    }
                } else {
                    title = this.currentArticle.titleRus;
                }
                // console.log("complexArticleTitle", title);
                return title;
            },

            setCurrentAricle(id) {             ////set current article before new article creation with connected material
                // console.log("setCurrentAricleId", id);
                // this.currentArticleId = id;
                this.currentArticle = this.filteredArticles.find(x => x.id === id);
            },

            createArticleWithMaterial(id, connectionType) {
                console.log("createMateril", id, connectionType);
                // <router-link :to="{name: 'article-add'}">Add article</router-link>
                // <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">
                // <router-link :to="{name: 'article-add', params: {article_id: article.id}}">
                //     router.push({ name: 'user', params: { userId } })
                this.$router.push({name: 'article-add', params: {id: id, connectionType: connectionType}});
                // this.$router.push({path: '/add/$id/$connectionType'});
            },

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

                    this.alternate('mainListTable');
                });
            });
        },

        watch: {
            // currentArticle: function () {
            //     console.log("CURRENT ARTICLE WATCH", this.currentArticle);
            // },

            searchKey: function () {
                //If empty search to renew the table
                console.log("WATCH");
                if (this.searchKey === "") {
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

                if (this.statusCheckBox[this.statusCheckBox.length - 1] === 3) {
                    console.log("this.statusCheckBox!!! == 3");

                    document.getElementById("startdate-input").style.backgroundColor = "lightgrey";
                    document.getElementById("enddate-input").style.backgroundColor = "lightgrey";

                    document.getElementById("startdate-input").disabled = true;
                    document.getElementById("enddate-input").disabled = true;
                    document.getElementById("refreshButton").disabled = true;

                    for (let i = 0; i < this.statusCheckBox.length; i++) {
                        if (this.statusCheckBox[i] !== 3) {
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
                        if (this.statusCheckBox[i] === 3) {
                            this.statusCheckBox.splice(i, 1);
                            console.log("SPLICE");

                            // document.getElementById("startdate-input").style.backgroundColor = "white";
                        }
                    }
                }

                this.search();
            },

            selected: function () {
                if (this.searchKey !== "") {
                    this.search();
                }
            },

            color: function () {   //calls when color picking is done
                console.log("----------------------COLOR");
                let currentArticle = this.filteredArticles.find(x => x.id === this.selectedArticle);
                this.setColor(currentArticle, this.color);
                this.$root.$emit('bv::hide::modal', 'modal-color');
            },
        },
    }
</script>