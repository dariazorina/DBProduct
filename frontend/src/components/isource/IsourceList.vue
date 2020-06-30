<template id="isource">
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


        <!--        <div class="logoutNameAndButton">welcome, {{loggedName}}!-->
        <!--            <b-btn v-if="loggedInFlag" size="sm" variant="outline-secondary" v-b-modal.modal1>Logout</b-btn>-->
        <!--            &lt;!&ndash; Modal Component &ndash;&gt;-->
        <!--            <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>-->
        <!--        </div>-->


        <div style="margin-top: -50px">
            <a class="btn btn-default">
                <router-link :to="{name: 'isource-add'}">Add isource</router-link>
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
        </div>
        <!--///////////////////////////////////////////////////////////////////////////////////////////////-->

        <table class="redTable">
            <!--        <table class="table">-->
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <th class='tdTitle'>Id</th>
                <th class='tdTitle'>Язык</th>
                <th class='tdTitle'>Модератор</th>
                <th class='tdTitle'>Хештеги</th>
                <th class='tdTitle'>Страна</th>
                <th class='tdTitle'>Заголовок</th>
                <th class='tdTitle'>Доступ</th>
                <th class='tdTitle'>Обновляемость</th>
                <th class='tdTitle'>Год создания</th>
                <th class='tdTitle' style="width:20%">Описание</th>
                <th class='tdTitle' style="width:14%; color:lightgray">Комментарии</th>

                <!--                <template v-if="statusCheckBox.length!=1">-->
                <!--            <span v-show="statusCheckBox.length!=1">-->

                <!--                    </span>-->
                <!--                </template>-->
                <!--                <th style="width:10%">Links</th>-->

            </tr>
            </thead>
            <tbody>
            <!--            <tr v-for="article in articles" class="ListCellStyleHot">-->
            <!--                        <tr v-for="article in articles">-->
            <tr v-for="isource in filteredIsources">

                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>
                    <span id=t>{{isource.id }}</span>
                </td>

                <td>
                    <div v-for="lang in isource.langList">
                        {{lang.name}}
                    </div>

                </td>
                <td>
                    <div v-for="moder in isource.moderatorList">
                        {{createComplexCellValue(moder.surnameRus, moder.surname)}}
                    </div>
                </td>
                <td>
                    <div v-for="hashtag in isource.hashtagList">
                        {{hashtag.content}}
                    </div>
                </td>
                <td class='tdAlignLeft'>{{showCountry(isource.country)}}</td>

                <td>
                    <div>
                        <!--                    <div style="white-space:pre-line">-->
                        <a>
                            <!--                            <router-link :to="{name: 'isource-details', params: {isource_id: isource.id}}">-->
                            {{createComplexCellValue(isource.titleRus, isource.title)}}
                            <!--                            </router-link>-->
                        </a>
                    </div>
                </td>

                <td>
                    {{isource.access}}
                </td>

                <td>
                    {{isource.update}}
                </td>

                <td>
                    {{isource.yearCreate}}
                </td>

                <td style="height: 50px">
                    <div style="height:60px; overflow:hidden">
                        {{isource.description }}
                    </div>
                </td> <!--todo dots? if cut-->

                <td style="height: 50px">
                    <div style="height:60px; overflow:hidden">
                        {{isource.miscellany }}
                    </div>
                </td>

            </tr>
            </tbody>
        </table>
    </div>
</template>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet"/>

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
</style>

<script>

    import Vue from 'vue';
    import api from "./isource-api";
    import moment from "moment";
    import router from "./../../router";
    import "vue-scroll-table";
    import Vuetify from 'vuetify';
    // import 'vuetify/dist/vuetify.min.css';
    import '@mdi/font/css/materialdesignicons.css' //why does icon appear in other file)) add article?
    // import 'material-design-icons-iconfont/dist/material-design-icons.css'


    export default {
        name: 'isource',
        vuetify: new Vuetify(),
        data() {
            return {
                // articles: [],
                isource: {linkList: [], langList: [], hashtagList: [], moderatorList: []},
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

                message: 'One Line,\nTwo Lines.',
            }
        },
        computed: {
            filteredIsources() {
                return this.entries;
            },
        },

        methods: {

            createComplexCellValue(valueRus, valueOrig) {
                let result = '';
                if (this.isArrayValidAndNotEmpty(valueRus)) {
                    result = valueRus;
                    if (this.isArrayValidAndNotEmpty(valueOrig))
                        result += " / " + valueOrig;
                } else if (this.isArrayValidAndNotEmpty(valueOrig))
                    result += valueOrig;

                return result;
            },

            showCountry(country) {
                if (country) {
                    return country.name;
                }
                return "";
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


                if (this.searchKey === "") {//s- ch-


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

            api.getAllIsource(r => {
                this.entries = r.data;
                console.log(this.entries);
            });
        },

        watch: {
            searchKey: function () {
                //If empty search to renew the table
                console.log("WATCH");
                if (this.searchKey == "") {
                    this.search();
                }
            },

            selected: function () {
                if (this.searchKey != "") {
                    this.search();
                }
            },
        },
    }
</script>