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

                            <label v-if="isAllHashtagMode" style="color: darkgray"> 1 уровень</label>
                            <label v-else>1 уровень</label>
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
                            <div style="margin-top: 10px">

                                <label for="add-level" v-if="isAllHashtagMode" style="color: darkgray">{{labelAddOrEditHashtag}}</label>
                                <label for="add-level" v-else>{{labelAddOrEditHashtag}}</label>

                                <input class="form-control" id="add-level" v-model="hashtag.content" required/>
                                <!--                            todo <span class="add" @click="deleteSearch()" style="margin-top: -31px; margin-right: 5px">+</span>-->
                            </div>
                        </div>
                        <div class="col-1" style="background-color: lightgray; padding-top: 55px; padding-right: 0px">
                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="addOrUpdateHashtag">
                                <v-icon style="color: green">mdi-message-plus</v-icon>
                            </v-btn>
                        </div>


                    </div>

                    <div class="row col-6" style="background-color: peachpuff">
                        <div class="col-11" style="background-color: gold">

                            <label v-if="isAllHashtagMode" style="color: darkgray"> 2 уровень</label>
                            <label v-else>2 уровень</label>
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
        <div class="row" style="margin-left: 20px">
            <!--            <label class="col-sm-2 col-form-label" style="line-height: 45px;">Выберете поле для поиска:</label>-->
            <!--            <div class="col-sm-auto">-->
            <!--                <b-form-select v-model="selected" id="search-selection">-->
            <!--                    <option v-for="item in searchItems" v-bind:value="item">{{item}}</option>-->
            <!--                </b-form-select>-->
            <!--            </div>-->

            <div style="padding-top: 11px">
                <button type="button" @click="showAllHash" class="btn btn-primary">Show all hashtags</button>
            </div>

            <div class="col-sm-2">
                <input v-model="searchKey" class="form-control"
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

                <!--                <th class='tdTitle'>Родительский хештег</th>-->
                <th class='tdTitle'>Хештег</th>
                <th class='tdTitle'>Комментарий</th>


                <th class="tdTitle" style="width:4%"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="hashtag in filteredHashtags">
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


                    <!--                    <v-btn text icon x-small v-b-modal.modal2>-->
                    <v-btn text icon x-small @click="deleteHashtag(hashtag.id)">
                        <!--                        <a>-->
                        <!--                            <router-link :to="{name: 'hashtag-delete', params: {hashtag_id: hashtag.id}}">-->
                        <v-icon style="color: red">mdi-delete-forever</v-icon>
                        <!--                            </router-link>-->
                        <!--                        </a>-->
                    </v-btn>

                    <!--                    <button type="button" v-if="loggedInFlag" class="btnXSmall btn-link" v-b-modal.modal2>Delete-->
                    <!--                        </button>-->
                    <!--                    <b-modal id="modal2" title="Are you sure you want to delete hashtag?"-->
                    <!--                             @ok="deleteHashtag(hashtag.id)"></b-modal>-->
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>


<!--<button type="button" v-if="loggedInFlag" class="btnXSmall btn-link" v-b-modal.modal1>Logout-->
<!--</button>-->
<!--&lt;!&ndash;                <b-btn v-if="loggedInFlag" size="xs" variant="btn btn-link  btn-xs"  v-b-modal.modal1>Logout</b-btn>&ndash;&gt;-->
<!--</p>-->
<!--&lt;!&ndash; Modal Component &ndash;&gt;-->
<!--<b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>-->


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
    import '@mdi/font/css/materialdesignicons.css'
    import Language from "../language/LanguageList"; //why does icon appear in other file)) add article?
    // import 'material-design-icons-iconfont/dist/material-design-icons.css'

    export default {
        name: 'hashtag',
        components: {Language},
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

                isAllHashtagMode: false,

                selected: null,
                editMode: false,

                labelAddOrEditHashtag: "",
                // searchItems: ["хештег", "тип", "комментарий"],
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
            },
        },
        methods: {
            showAllHash() {
                this.isAllHashtagMode = true;
                api.getAllHashtags().then(response => {
                    this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                    this.hashtags = response.data;  //to renew edited 1 level dropdown list
                    this.entries = response.data;
                    this.hashtag.content = null;
                    this.clearSelection(1);
                });
            },

            clearSelection(level) {
                if (level === 1) {
                    this.selected1LevelId = null;
                    this.selected2LevelId = null;
                } else {
                    this.selected2LevelId = null;
                    this.level1UpdateTable();
                }

                this.hashtag.content = null;
            },

            addOrUpdateHashtag() {
                if (this.hashtag.content != null) {
                    if (this.hashtag.id != null) { //update

                        api.update(this.hashtag.id, this.hashtag, r => {

                            if (this.selected2LevelId != null) {                 ///upd 3 level
                                api.getLeafHashtags(this.selected2LevelId, r => {
                                    this.entries = r.data;
                                    this.hashtag.content = null;
                                    this.labelAddOrEditHashtag = "Добавить хештег 3 уровня, используйте Enter/кнопка <Добавить>";
                                    console.log(r.data)
                                })
                                    .catch(error => {
                                        this.errors.push(error)
                                    });

                            } else {
                                if (this.selected1LevelId != null) {             ///upd 2 level
                                    api.getLeafHashtags(this.selected1LevelId, r => {
                                        this.entries = r.data;
                                        this.hashtag.content = null;
                                        this.labelAddOrEditHashtag = "Добавить хештег 2 уровня, используйте Enter/кнопка <Добавить>";

                                        api.getAllHashtags().then(response => {
                                            this.hashtags = response.data;  //to renew edited 2 level dropdown list
                                        });
                                    })
                                        .catch(error => {
                                            this.errors.push(error)
                                        });

                                } else {                                         ///upd 1 level
                                    api.getAllHashtags().then(response => {
                                        this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                                        this.hashtags = response.data;  //to renew edited 1 level dropdown list
                                        this.entries = response.data;
                                        this.hashtag.content = null;
                                    });
                                }
                            }
                        });
                    } else { //create

                        if (this.selected1LevelId == null) {
                            this.hashtag.parentId = 0;

                        } else if (this.selected2LevelId == null) {
                            this.hashtag.parentId = this.selected1LevelId;
                            console.log("CREATE 2 level", this.selected1LevelId);

                        } else {
                            this.hashtag.parentId = this.selected2LevelId;
                        }

                        api.create(this.hashtag, r => {
                            if (this.selected2LevelId != null) {  //crt 3 level
                                api.getLeafHashtags(this.selected2LevelId, r => {
                                    this.entries = r.data;
                                    this.hashtag.content = null;
                                    console.log(r.data)
                                })
                                    .catch(error => {
                                        this.errors.push(error)
                                    });
                            } else {
                                if (this.selected1LevelId != null) {   ///crt 2 level

                                    api.getLeafHashtags(this.selected1LevelId, r => {
                                        this.entries = r.data;
                                        this.hashtag.content = null;

                                        api.getAllHashtags().then(response => {
                                            this.hashtags = response.data;  //to renew edited 2 level dropdown list
                                        });
                                        // console.log(r.data)
                                    })
                                        .catch(error => {
                                            this.errors.push(error)
                                        });

                                } else {                                  ///crt 1 level
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
                            }
                        }, error => {
                            console.log("Hashtag already exists");
                            this.labelAddOrEditHashtag = "Hashtag already exists";
                        });
                    }
                }
            },

            deleteHashtag(id) {
                if (confirm("Do you really want to delete '" + this.getHashtagContentById(id) + "'?")) {
                    api.delete(id, r => {
                        api.getAllHashtags().then(response => {
                            this.hashtags = response.data;                          //to renew dropdown lists

                            if (this.isAllHashtagMode) {
                                this.showAllHash();
                            } else {
                                if (this.selected1LevelId == null) {
                                    this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                                    api.getLeafHashtags(0, r => {
                                        this.entries = r.data;
                                    });
                                } else {
                                    if (this.selected2LevelId == null) {
                                        this.labelAddOrEditHashtag = "Добавить хештег 2 уровня, используйте Enter/кнопка <Добавить>";
                                        api.getLeafHashtags(this.selected1LevelId, r => {
                                            this.entries = r.data;
                                        });
                                    } else {
                                        this.labelAddOrEditHashtag = "Добавить хештег 3 уровня, используйте Enter/кнопка <Добавить>";

                                        api.getLeafHashtags(this.selected2LevelId, r => {
                                            this.entries = r.data;
                                        });
                                    }
                                }
                            }
                        });
                    });
                }
            },

            getHashtagContentById(id) {
                let content;
                for (let i = 0; i < this.hashtags.length; i++) {
                    if (this.hashtags[i].id === id) {
                        return this.hashtags[i].content;
                    }
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
                api.findById(id, resp => {
                    this.hashtag = resp.data;
                    if (this.hashtag.parentId !== 0) {

                        api.findById(this.hashtag.parentId, r => {
                            if (r.data.parentId === 0) {                                                    //second level
                                this.selected1LevelId = r.data.id;
                                this.labelAddOrEditHashtag = "Изменить хештег 2 уровня, используйте Enter/кнопка <Добавить>";

                            } else {                                                                         ///third level
                                api.findById(r.data.parentId, r1 => {
                                    this.selected1LevelId = r1.data.id;
                                    this.selected2LevelId = r.data.id;

                                    api.getLeafHashtags(this.selected2LevelId, r => {
                                        this.entries = r.data;
                                    });
                                    this.labelAddOrEditHashtag = "Изменить хештег 3 уровня, используйте Enter/кнопка <Добавить>";
                                });
                            }
                        });
                    } else {
                        this.labelAddOrEditHashtag = "Изменить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                    }
                });
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
                if (this.searchKey === "") {
                    api.getAllHashtags().then(response => {
                        this.hashtags = response.data;                          //to renew dropdown lists

                        if (this.isAllHashtagMode) {
                            this.showAllHash();
                        } else {
                            if (this.selected1LevelId == null) {
                                this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                                api.getLeafHashtags(0, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                if (this.selected2LevelId == null) {
                                    this.labelAddOrEditHashtag = "Добавить хештег 2 уровня, используйте Enter/кнопка <Добавить>";
                                    api.getLeafHashtags(this.selected1LevelId, r => {
                                        this.entries = r.data;
                                    });
                                } else {
                                    this.labelAddOrEditHashtag = "Добавить хештег 3 уровня, используйте Enter/кнопка <Добавить>";

                                    api.getLeafHashtags(this.selected2LevelId, r => {
                                        this.entries = r.data;
                                    });
                                }
                            }
                        }
                        console.log("delete", r.data);
                    });
                } else {
                    // if (this.selected === "хештег") {
                    api.searchHash(this.searchKey, r => {
                        this.entries = r.data;
                    });
                    // }
                }
            },

            level1UpdateTable() {
                if (this.selected1LevelId == null) {
                    if (!this.isAllHashtagMode) {
                        this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                        api.getLeafHashtags(0, r => {
                            this.entries = r.data;
                        });
                    }
                } else {
                    this.isAllHashtagMode = false;
                    this.labelAddOrEditHashtag = "Добавить хештег 2 уровня, используйте Enter/кнопка <Добавить>";

                    api.getLeafHashtags(this.selected1LevelId, r => {
                        this.entries = r.data;
                    });
                }
            },

            hashtagContentTitle() {
                console.log(" hashtag: function(){");

                if (this.hashtag.content.length == 0) {
                    console.log(" hashtag: function(){ NULL");

                    if (this.selected2LevelId == null) {
                        if (this.selected1LevelId == null) {
                            this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                        } else {
                            this.labelAddOrEditHashtag = "Добавить хештег 2 уровня, используйте Enter/кнопка <Добавить>";
                        }
                    } else {
                        this.labelAddOrEditHashtag = "Добавить хештег 3 уровня, используйте Enter/кнопка <Добавить>";
                    }
                }
            },
        },

        mounted() {
            this.getLoggedIn();

            api.getLeafHashtags(0, r => {
                this.entries = r.data;
                // this.hashtags = r.data;
            });


            api.getAllHashtags().then(response => {
                // this.entries = response.data;
                this.hashtags = response.data;

                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                });

            this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";

            let input = document.getElementById("add-level");
            if (input)
            // input.addEventListener("keyup", function (event) {
                input.addEventListener("keyup", (event) => {
                    if (event.key === "Enter") {
                        console.log("CATCH ENTER");
                        event.preventDefault();
                        this.addOrUpdateHashtag();
                    }

                    if (event.key === "Backspace") {
                        event.preventDefault();
                        this.hashtagContentTitle();
                    }

                    if (event.key === "Delete") {
                        event.preventDefault();
                        this.hashtagContentTitle();
                    }
                });
        },

        watch: {
            selected1LevelId: function () {
                this.level1UpdateTable();
            },
            selected2LevelId: function () {
                if (this.selected2LevelId == null) {

                    if (this.selected1LevelId == null) {
                        this.labelAddOrEditHashtag = "Добавить хештег 1 уровня, используйте Enter/кнопка <Добавить>";
                    } else {
                        this.labelAddOrEditHashtag = "Добавить хештег 2 уровня, используйте Enter/кнопка <Добавить>";
                    }

                    api.getLeafHashtags(this.selected1LevelId, r => {
                        this.entries = r.data;
                    });

                } else {
                    this.labelAddOrEditHashtag = "Добавить хештег 3 уровня, используйте Enter/кнопка <Добавить>";

                    api.getLeafHashtags(this.selected2LevelId, r => {
                        this.entries = r.data;
                    });
                }
            },

            searchKey: function () {
                //If empty search to renew the table
                console.log("WATCH");
                if (this.searchKey == "") {
                    this.search();
                }
            },
        },
    }
</script>