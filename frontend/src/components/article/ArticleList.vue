<template id="article">
    <div>
<!--        <link href="../dbnm.css" rel="stylesheet"/>-->
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
            <div class="form-group row col-9"
                 style="margin: 0; background-color: transparent; padding-right: 0px; padding: 0;">

                <div class="col-8"
                     style="margin-top: 0px; background-color: transparent; padding:0">
                    <a class="btn btn-default">
                        <router-link :to="{name: 'article-add'}">Add article</router-link>
                    </a>
                    <v-icon style="color: #0074D9">mdi-shape-rectangle-plus</v-icon>
                </div>

                <div class="form-group row col-4" style="margin: 0; margin-left: 0; padding:0; background-color: transparent">

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
                      <div class="col-2"
                         style="background-color: transparent; padding-left: 0px; padding-bottom: 0px; padding-top: 0px; margin-left: 0px">
                        <button id="refreshButton" type="button" class="btn" @click="refreshPeriod">
                             <v-icon style="color: #0074D9">mdi-database-refresh</v-icon>
                        </button>
                    </div>

                </div>
            </div>
        </div>
        <!--        ////////////////////////////////////////search//////////////////////////////////-->
        <div class="row" style="background-color: transparent; padding: 0px;  margin:0;  ">
            <label class="col-sm-2 col-form-label" align="right" style="line-height: 45px; padding-right: 0;background-color: transparent">Выберете поле для поиска:</label>
            <div class="col-sm-auto" style="padding-right: 0;background-color: transparent">
                <b-form-select v-model="selected" id="search-selection">
                    <option v-for="item in searchItems" v-bind:value="item">{{item}}</option>
                </b-form-select>
            </div>

            <div class="col-sm-2" style="padding-right: 0;background-color: transparent">
                <input :placeholder="placeholderCreation()" v-model="searchKey" class="form-control"
                       id="search-element" style="padding-right: 20px" v-on:keyup.enter="search" requred/>
                <span class="close" @click="deleteSearch()" style="margin-top: -31px; margin-right: 5px">&times;</span>
                <!--                todo-->
            </div>

            <div class="form-group row col-sm-3"  align="left" style="margin: 0; padding: 10px 15px; background-color: transparent">
<!--                <button style="margin-left: 5px; margin-right: 5px; margin-top: 0px; margin-bottom: 10px; padding-top: 0px; padding-bottom: 0px"-->
                <button type="button" style="margin-right: 10px" @click="search" class="btn btn-outline-primary" disabled>Поиск
                </button>
                <button type="button" @click="resetAllFilters" class="btn btn-outline-dark">Сброс фильтров
                </button>
            </div>

            <!--//////////////////////////////////STATUS////////////////////////////////////////////////////////////////-->

            <div class="form-group row col-2"
                 style="margin-left:auto; margin-right:0; background-color: transparent; padding: 0;  margin-top: 0; margin-bottom: 0">
<!--                <div class="col-3" style="padding: 0; margin: 0; background-color: #ba68c8  "></div>-->
                <div class="col-5" style="padding: 0;  margin:0; margin-left:auto; background-color: transparent">
                        <b-form-group label="" style="text-align: left; padding: 0;  margin:0; background-color: transparent">
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
                <div class="col-6" style="padding: 0;  margin:0; margin-left:auto; background-color: transparent">
                    <b-form-group label="" style="text-align: left;  padding: 0;  margin:0;  background-color: transparent">
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
        <!--        <table class="redTable" :key="authorComponentKey" id="mainListTable">-->
        <table class="redTable" id="mainListTable">
            <thead>
            <tr>
                <!--                <th class='tdTitle headerLink' @contextmenu.prevent="'modalStatus'" style="color:lightgray; width: 3%" @click="sort('status')">Статус</th>-->
                <th class='tdTitle headerLink' style="color:lightgray; width: 3%" @click="sort('status')">Статус</th>
                <th class='tdTitle headerLink' data-field="createdAt" data-formatter="dateFormat" @click="sort('date')">
                    Дата
                </th>
                <th class='tdTitle' @contextmenu.prevent="searchByField(0)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Язык
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="languageFilterId">
                            <v-btn text icon x-small @click="resetFilter(0)">
                                <!--                            <v-btn text icon x-small @click="resetFilter(5)" id="titleFilterId" hidden>-->
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>

                <th class='tdTitle' @contextmenu.prevent="searchByField(1)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-2' style="background-color: transparent;  padding: 0"></div>
                        <div class='headerLink col-sm-8' style="text-align: center; background-color: transparent; padding-left: 0px; padding-right: 0px">
                            Хештеги
                        </div>
                        <div class='col-sm-2' style="padding-left: 0px; background-color: transparent; visibility: hidden" id="hashFilterId">
                            <v-btn text icon x-small @click="resetFilter(1)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdTitle' @contextmenu.prevent="searchByField(2)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Лица
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="authorFilterId">
                            <v-btn text icon x-small @click="resetFilter(2)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdTitle' @contextmenu.prevent="searchByField(3)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-2' style="background-color: transparent;  padding: 0"></div>
                        <div class='headerLink col-sm-8' style="text-align: center; background-color: transparent; padding-left: 0px; padding-right: 0px">
                            Организации
                        </div>
                        <div class='col-sm-2' style="padding-left: 0px; background-color: transparent; visibility: hidden" id="orgFilterId">
                            <v-btn text icon x-small @click="resetFilter(3)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdTitle' @contextmenu.prevent="searchByField(4)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-2' style="background-color: transparent;  padding: 0"></div>
                        <div class='headerLink col-sm-8' style="text-align: center; background-color: transparent; padding-left: 0px; padding-right: 0px">
                            Локации
                        </div>
                        <div class='col-sm-2' style="padding-left: 0px; background-color: transparent; visibility: hidden" id="locationFilterId">
                            <v-btn text icon x-small @click="resetFilter(4)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th @contextmenu.prevent="searchByField(5)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-2' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-8'
                             style="text-align: center; background-color: transparent">
                            Заголовок
                        </div>
                        <div class='col-sm-2'
                             style="background-color: transparent; visibility: hidden; padding-left: 0" id="titleFilterId">
                            <v-btn text icon x-small @click="resetFilter(5)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdTitle headerLink' @contextmenu.prevent="searchByField(6)"
                    style="width:15%; color:lightgray"><div class="row" style="background-color: transparent">
                    <div class='col-sm-2' style="background-color: transparent;  padding: 0"></div>
                    <div class='headerLink col-sm-8' style="text-align: center; background-color: transparent; padding-left: 0px; padding-right: 0px">
                        Комментарии
                    </div>
                    <div class='col-sm-2' style="padding-left: 0px; background-color: transparent; visibility: hidden" id="miscellanyFilterId">
                        <v-btn text icon x-small @click="resetFilter(6)">
                            <v-icon style="color: white">mdi-close-circle</v-icon>
                        </v-btn>
                    </div>
                </div>
                </th>
                <th class="tdTitle" style="width:4%">Добавить материал..</th>
                <th class="tdTitle" style="width:4%">Действия</th>
                <!--                <th class="tdTitle" style="width:4%">Смена статуса</th>-->
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

                <!--                <td :key="article.status">-->
                <td>
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
                        <div v-if="article.personList.length > 0">
                            {{getPersonCellById(author.itemId)}}
                            <!--                            {{ createComplexCellValueById(author.itemId)}}-->
                        </div>
                    </div>
                </td>
                <td>
                    <div v-for="org in article.orgList">
                        <div v-if="article.orgList.length > 0">
                            {{getOrgCellById(org.itemId)}}
                        </div>
                    </div>
                </td>
                <td>
                    <div v-for="location in article.locationList">
                        <div v-if="article.locationList.length > 0">
                            {{getLocationCellById(location.itemId)}}
                        </div>
                    </div>
                </td>
                <td>
                    <div>                        <!--                    <div style="white-space:pre-line">-->
                        <a>
                            <router-link :to="{name: 'article-details', params: {article_id: article.id}}">
                                {{ article.titleRus}}
                            </router-link>
                        </a>
                    </div>
                </td>
                <td style="height: 50px">
                    <div style="height:60px; overflow:hidden">
                        {{article.miscellany }}
                    </div>
                </td>
                <td>
                    <v-btn text icon x-small v-b-modal="'modal-scoped'" @click="setCurrentAricle(article.id)">
                        <v-icon style="color: orange">mdi-account-multiple-plus</v-icon>
                    </v-btn>
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
            <li class="ctx-item" @click="changeStatusReturned">изменить "На доработке"</li>
            <li class="ctx-item" @click="changeStatusDone">изменить "Отработаны"</li>
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

        <!--        <b-modal ref="modalStatus" :hide-footer=true :hide-header=true>-->
        <!--            <template>-->
        <!--                <b><p class="myHeader">Сменить статус текущей записи</p><br></b>-->

        <!--                <div align="center" style="background-color: transparent">-->
        <!--                    <v-btn text icon x-large @click="updateArticleStatus(article.id, 2)">-->
        <!--                        <v-icon style="color: red">mdi-clipboard-arrow-left</v-icon>-->
        <!--                    </v-btn>-->

        <!--                    <v-btn text icon x-large @click="updateArticleStatus(article.id, 3)">-->
        <!--                        <v-icon style="color: green">mdi-check</v-icon>-->
        <!--                    </v-btn>-->
        <!--                </div>-->
        <!--            </template>-->
        <!--        </b-modal>-->

        <b-modal ref="modalSearch" :hide-footer=true :hide-header=true>
            <template>
                <b><p class="myHeader">Фильтр по полю '{{currentFilterField.text}}'</p><br></b>
                <div align="center">
                    <!--                    <input :placeholder="placeholderCreation()" v-model="searchKey" class="form-control"-->
                    <!--                           id="search-element1" style="padding-right: 20px" v-on:keyup.enter="search" requred/>-->


                    <CreatedList :items="currentFilterItems"
                                 @update-item="updateItem"/>


                    <div v-if="currentFilterItems.length > 0">
                        <b-button size="sm" variant="success"
                                  @click="startModalSearch()">
                            Применить
                        </b-button>
                    </div>
                </div>
            </template>
        </b-modal>
    </div>
</template>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet"/>
<style lang="scss">
    /*@import '../dbnm.css';*/
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
    import apiCountry from "./../country/country-api";
    import apiOrg from "./../org/org-api";

    import moment from "moment";
    import router from "./../../router";
    import "vue-scroll-table";
    import Vuetify from 'vuetify';
    import '@mdi/font/css/materialdesignicons.css' //why does icon appear in other file)) add article?
    import contextMenu from 'vue-context-menu'
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import 'vue-swatches/dist/vue-swatches.css'
    import CreatedList from "../components/multiple-enter-list/CreatedList";

    export default {
        name: 'article',
        components: {
            contextMenu,
            VSwatches,
            CreatedList,
        },
        vuetify: new Vuetify(),
        data() {
            return {
                color: '#1CA085',
                articles: [],
                article: {status: 0, personList: [], locationList: [], orgList: [], hashtagList: []},
                authors: [],
                articlePersonIds: [], //before request
                articlePersonEntities: [], //after request
                articleLocationIds: [], //before request
                articleLocationEntities: [], //after request
                articleOrgIds: [], //before request
                articleOrgEntities: [], //after request

                authorComponentKey: 0,  //todo delete?

                response: [],
                errors: [],

                entries: [],

                showResponse: false,
                loggedInFlag: false,
                loggedName: null,

                selected: "текст",
                searchItems: ["текст", "описание"],
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
                currentSortDir: 'asc',

                filterItems: [{key: 0, value: []}, {key: 1, value: []}, {key: 2, value: []},
                    {key: 3, value: []}, {key: 4, value: []}, {key: 5, value: []}, {key: 6, value: []}],

                filterTableFields: [
                    {key: 0, text: 'Язык'},
                    {key: 1, text: 'Хештеги'},
                    {key: 2, text: 'Лица'},
                    {key: 3, text: 'Организации'},
                    {key: 4, text: 'Локации'},
                    {key: 5, text: 'Заголовок'},
                    {key: 6, text: 'Комментарий'},
                ],

                // todo - maybe remove key? it dublicates index
                filterTableFieldsForRequest: [
                    {key: 0, text: 'language'},
                    {key: 1, text: 'hash'},
                    {key: 2, text: 'author'},
                    {key: 3, text: 'org'},
                    {key: 4, text: 'location'},
                    {key: 5, text: 'title'},
                    {key: 6, text: 'miscellany'},
                ],

                currentFilterField: '',
                currentFilterItems: [],
                links: [],
            }
        },
        computed: {
            filteredArticles() {
                // console.log("*************************************FILTERED ARTICLES", this.entries);
                return this.entries;
            },

            sortedArticles() {
                console.log("sorted articles");
                // this.authorComponentKey++; //for zebra after sorting

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
            filterClearButtonActivity(hide, filterClearButtonId) {
                if (hide)
                    document.getElementById(filterClearButtonId).style.visibility = "hidden";
                else
                    document.getElementById(filterClearButtonId).style.visibility = "visible";
            },

            // changeStatus() {
            //     this.$refs.modalStatus.show();
            // },

            resetFilter(filter) {
                this.filterItems[filter].value.splice(0);
                this.filterClearButtonActivity(true, this.filterTableFieldsForRequest[filter].text + 'FilterId');
                this.filterAll();
            },

            resetAllFilters() {
                for (let i = 0; i < this.filterItems.length; i++) {
                    this.filterItems[i].value.splice(0);
                    this.filterClearButtonActivity(true, this.filterTableFieldsForRequest[i].text + 'FilterId');
                }
                this.currentFilterItems = [];
                api.searchPeriodAndStatus(-1, this.startDate, this.endDate, r => {
                    this.entries = r.data;
                });
            },

            updateItem(item) {  // (2) calls when search item adds to search list
                console.log("ADDED LINK", item, this.currentFilterItems);
                this.currentFilterItems.push(item);  //push item(item: {id, content}

                let key = this.filterTableFields.find(x => x === this.currentFilterField).key;
                this.filterClearButtonActivity(false, this.filterTableFieldsForRequest[key].text + 'FilterId');
                // console.log("----------ADDED LINK", key, this.filterTableFieldsForRequest[key].text + 'FilterId');
            },

            startModalSearch() {  // (3) search starts
                let array = [];
                for (let i = 0; i < this.currentFilterItems.length; i++) {
                    array.push(this.currentFilterItems[i].content);
                }
                let item = {
                    "key": this.currentFilterField.key,
                    "value": array,
                };
                this.filterItems[this.currentFilterField.key] = item;
                this.currentFilterItems.splice(0);
                console.log(">>>>>>>>>>>>>>>search by modal filterItems currentSI", this.filterItems, this.currentFilterItems);

                this.filterAll();  //filter(title)
                this.$refs.modalSearch.hide();
            },

            searchByField(field) {  // (1) it calls when user chooses item in context menu
                let i = 0, item;

                // console.log("search by field this.filterItems", field, this.filterItems);
                this.currentFilterField = this.filterTableFields.find(x => x.key === field);
                let res = this.filterItems.find(x => x.key === field).value;
                this.currentFilterItems = [];
                if (res.length > 0) {
                    // console.log("1010101010010010101", res);

                    let arrayValue = [];
                    for (; i < res.length; i++) {
                        item = {
                            "id": i,
                            "content": res[i],
                        };
                        arrayValue.push(item);
                    }
                    this.currentFilterItems = arrayValue;
                }
                console.log("search by field this.currentSearchField, this.filterItems, this.currentSearchItems", this.currentFilterField, this.filterItems, this.currentFilterItems);
                this.$refs.modalSearch.show();
            },

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

            changeStatusReturned() {
                let currentArticle = this.filteredArticles.find(x => x.id === this.selectedArticle);
                this.updateArticleStatus(currentArticle.id, 2);
            },

            changeStatusDone() {
                let currentArticle = this.filteredArticles.find(x => x.id === this.selectedArticle);
                this.updateArticleStatus(currentArticle.id, 3);
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

            // createComplexCellValueById(id) {
            //     let result = '';
            //     let currentPerson = this.articlePersonEntities.find(x => x.id === id);
            //     let valueOrig = '', valueRus = '';
            //
            //     if (this.isArrayValidAndNotEmpty(currentPerson)) {//to prevent errors in console when search result isn't ready yet
            //         // if (this.isArrayValidAndNotEmpty(currentPerson.surname)) { //mandatory field in Person
            //         valueOrig = currentPerson.surname;
            //         // }
            //
            //         if (this.isArrayValidAndNotEmpty(currentPerson.surname)) {
            //             valueRus = currentPerson.surnameRus;
            //         }
            //
            //         // if (this.isArrayValidAndNotEmpty(currentPerson.name)) { //mandatory field in Person
            //         valueOrig += " " + currentPerson.name;
            //         // }
            //
            //         if (this.isArrayValidAndNotEmpty(currentPerson.nameRus)) {
            //             valueRus += " " + currentPerson.nameRus;
            //         }
            //
            //         if (this.isArrayValidAndNotEmpty(valueRus)) {
            //             result = valueRus;
            //             if (this.isArrayValidAndNotEmpty(valueOrig)) {
            //                 if (valueRus.localeCompare(valueOrig) !== 0)
            //                     result += " / " + valueOrig;
            //             }
            //         } else if (this.isArrayValidAndNotEmpty(valueOrig))
            //             result += valueOrig;
            //         return result;
            //     }
            // },

            getPersonCellById(id) {
                let result = '';
                let currentPerson = this.articlePersonEntities.find(x => x.id === id);

                if (this.isArrayValidAndNotEmpty(currentPerson)) {//to prevent errors in console when search result isn't ready yet
                    result = currentPerson.surnameRus;

                    if (this.isArrayValidAndNotEmpty(currentPerson.nameRus)) {
                        result += " " + currentPerson.nameRus;
                    }
                    return result;
                }
            },

            getLocationCellById(id) {
                let result = '';
                let currentLocation = this.articleLocationEntities.find(x => x.id === id);

                if (this.isArrayValidAndNotEmpty(currentLocation)) {//to prevent errors in console when search result isn't ready yet
                    result = currentLocation.country;

                    if (this.isArrayValidAndNotEmpty(currentLocation.region)) {
                        result += " / " + currentLocation.region;
                    }
                    if (this.isArrayValidAndNotEmpty(currentLocation.city)) {
                        result += " / " + currentLocation.city;
                    }
                    return result;
                }
            },

            getOrgCellById(id) {
                let result = '';
                let currentOrg = this.articleOrgEntities.find(x => x.id === id);

                if (this.isArrayValidAndNotEmpty(currentOrg)) {//to prevent errors in console when search result isn't ready yet
                    result = currentOrg.nameRus;
                    return result;
                }
            },

            // createComplexCellValue(valueRus, valueOrig) {
            //     let result = '';
            //
            //     if (this.isArrayValidAndNotEmpty(valueRus)) {
            //         result = valueRus;
            //         if (this.isArrayValidAndNotEmpty(valueOrig))
            //             result += " / " + valueOrig;
            //     } else if (this.isArrayValidAndNotEmpty(valueOrig))
            //         result += valueOrig;
            //     return result;
            // },

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

                        //this.search();
                        this.filterAll();
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

                    //this.search();
                    this.filterAll();

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

                console.log("complexStatusCreation", result);
                return result;
            },

            arrayToParamsConversion(field) {
                let result = '';
                let connectionItem = '&' + field + '=';
                let i = 0;

                //encodeURIComponent  -- to avoid error with '&' in parameters (ex. P&P - cannot find)
                //This function encodes special characters. In addition, it encodes the following characters: , / ? : @ & = + $ #

                for (; i < this.filterItems[this.currentFilterField.key].value.length - 1; i++) {
                    result += encodeURIComponent(this.filterItems[this.currentFilterField.key].value[i]) + connectionItem;
                }

                // console.log("iiii=", i, this.filterItems[this.currentFilterField.key].value.length);
                result += encodeURIComponent(this.filterItems[this.currentFilterField.key].value[i]);

                // console.log("complexTitleCreation filterItems[this.currentFilterField.key].value res", this.filterItems[this.currentFilterField.key].value, result);
                return result;
            },

            filterAllBodyCreation() {
                let result = '';
                // let connectionItem = '&' + field + '=';

                let i = 0, j = 0;

                //encodeURIComponent  -- to avoid error with '&' in parameters (ex. P&P - cannot find)
                //This function encodes special characters. In addition, it encodes the following characters: , / ? : @ & = + $ #

                //console.log("rrrrrrrrrrrrrrres before for", this.filterItems);
                for (; j < this.filterItems.length; j++) {
                    //console.log("rrrrrrrrrrrrrrres In for", this.filterItems[j].value.length, this.filterTableFieldsForRequest[j].text);
                    for (i = 0; i < this.filterItems[j].value.length; i++) {
                        // if (result.length === 0){

                        result += this.filterTableFieldsForRequest[j].text + '=' + encodeURIComponent(this.filterItems[j].value[i]) + '&';
                        //console.log("rrrrrrrrrrrrrrres IN I", i, result);
                        // } else {
                        //         result += encodeURIComponent(this.filterItems[j].value[i]) + connectionItem;
                        //     }
                    }

                }
                //console.log("rrrrrrrrrrrrrrres", result);
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
                        if (this.selected === "текст") {
                            //this.filter('text');

                            // if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {//ch+
                            //     api.filter('text', this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                            //         this.entries = r.data;
                            //     });
                            // } else {
                            //     api.filter('text', this.searchKey, -1, this.startDate, this.endDate, r => {
                            //         this.entries = r.data;
                            //     });
                            // }

                        } else if (this.selected === "описание") {
                            // this.filter('description');

                            // if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {   //ch+
                            //     api.filter('description', this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                            //         this.entries = r.data;
                            //     });
                            // } else {
                            //     api.filter('description', this.searchKey, -1, this.startDate, this.endDate, r => {
                            //         this.entries = r.data;
                            //     });
                            // }
                        }
                        // else if (this.selected === "заголовок") {
                        //     if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {
                        //         api.searchTitle(this.searchKey, this.complexStatusCreation(), this.startDate, this.endDate, r => {
                        //             this.entries = r.data;
                        //         });
                        //     } else {
                        //         console.log("NIT");
                        //         api.searchTitle(this.searchKey, -1, this.startDate, this.endDate, r => {
                        //             this.entries = r.data;
                        //         });
                        //      }
                        // }
                    }
                }
            },

            // filter(field) {
            //     console.log("-------filter by field--------", field);
            //     if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {
            //         api.filter(field, this.arrayToParamsConversion(field), this.complexStatusCreation(), this.startDate, this.endDate, r => {
            //             this.entries = r.data;
            //         });
            //     } else {
            //         api.filter(field, this.arrayToParamsConversion(field), -1, this.startDate, this.endDate, r => {
            //             this.entries = r.data;
            //         });
            //     }
            // },

            filterAll() {
                if (this.isArrayValidAndNotEmpty(this.statusCheckBox)) {
                    api.filterAll(this.filterAllBodyCreation(), this.complexStatusCreation(), this.startDate, this.endDate, r => {
                        this.entries = r.data;
                    });
                } else {
                    api.filterAll(this.filterAllBodyCreation(), -1, this.startDate, this.endDate, r => {
                        this.entries = r.data;
                        console.log("filter all =============", this.entries);
                    });
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
                    for (let j = 0; j < this.entries[i].orgList.length; j++) {
                        this.articleOrgIds.push(this.entries[i].orgList[j].itemId);
                    }
                    for (let j = 0; j < this.entries[i].locationList.length; j++) {
                        this.articleLocationIds.push(this.entries[i].locationList[j].itemId);
                    }
                }
                //console.log("IDS", this.articlePersonIds, this.articleOrgIds, this.articleLocationIds);

                apiOrg.getOrgsByIds(this.articleOrgIds, response => {
                    this.articleOrgEntities = response.data;
                });

                apiCountry.getLocationsByIds(this.articleLocationIds, response => {
                    this.articleLocationEntities = response.data;
                });

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
                console.log("WATCH searchKey");
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

                    // document.getElementById("startdate-input").style.backgroundColor = "lightgrey";
                    // document.getElementById("enddate-input").style.backgroundColor = "lightgrey";
                    //
                    // document.getElementById("startdate-input").disabled = true;
                    // document.getElementById("enddate-input").disabled = true;
                    // document.getElementById("refreshButton").disabled = true;

                    for (let i = 0; i < this.statusCheckBox.length; i++) {
                        if (this.statusCheckBox[i] !== 3) {
                            this.statusCheckBox.shift();
                            console.log("SHIFT");
                            // document.getElementById("startdate-input").style.backgroundColor = "green";
                        }
                    }

                    console.log("AFTER", this.statusCheckBox);
                } else {
                    // document.getElementById("startdate-input").style.backgroundColor = "white";
                    // document.getElementById("enddate-input").style.backgroundColor = "white";
                    //
                    // document.getElementById("startdate-input").disabled = false;
                    // document.getElementById("enddate-input").disabled = false;
                    // document.getElementById("refreshButton").disabled = false;

                    for (let i = 0; i < this.statusCheckBox.length; i++) {
                        if (this.statusCheckBox[i] === 3) {
                            this.statusCheckBox.splice(i, 1);
                            console.log("SPLICE");

                            // document.getElementById("startdate-input").style.backgroundColor = "white";
                        }
                    }
                }

                // this.search();
                this.filterAll();
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