<template id="mtype">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>

        <!--//////////////////////////////////////////add/edit hashtag/////////////////////////////////////////////////////////////////-->
        <form class="hashtagAddVue">
            <div class="row align-items-center col-12" style="background-color: transparent; padding: 0;">
                <div class="form-row col-12" style="background-color: transparent; padding-bottom: 0; padding-top: 0">
                    <div class="row col-6" style="background-color: transparent; padding-bottom: 0; padding-top: 0">


                        <div class="col-11" style="background-color: transparent; padding-bottom: 0; padding-top: 0">
                            <div style="margin-top:     0px">
                                <label for="add-level">{{labelAddOrEditMType}}</label>
                                <input class="form-control" id="add-level" v-model="mtype.content" required/>
                                <!--                            todo <span class="add" @click="deleteSearch()" style="margin-top: -31px; margin-right: 5px">+</span>-->
                            </div>
                        </div>
                        <div class="col-1" style="background-color: transparent; padding: 36px 0px 0px 0px;">
                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="addOrUpdateMType">
                                <v-icon style="color: green">mdi-message-plus</v-icon>
                            </v-btn>
                        </div>
                    </div>

                    <div class="row col-6" style="background-color: transparent; padding-bottom: 0; padding-top: 0">
                        <div class="col-11" style="background-color: transparent; padding-bottom: 0; padding-top: 0;">
                            <label>1 уровень</label>
                            <b-form-select v-model="selected1LevelId" id="level1-selection">
                                <option v-for="mt in levelMTypes" :value="mt.id" v-if="mt.parentId===0">
                                    {{mt.content}}
                                </option>
                            </b-form-select>
                        </div>
                        <div class="col-1" style="background-color: transparent; padding: 36px 0px 0px 0px;">
                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="clearSelection(1)">
                                <v-icon style="color: green">mdi-close</v-icon>
                            </v-btn>
                        </div>

                        <div class="col-11" style="background-color: transparent; padding-bottom: 0; padding-top: 0">

                            <label>2 уровень</label>
                            <b-form-select v-model="selected2LevelId" id="level2-selection">
                                <option v-for="mt in levelMTypes" :value="mt.id"
                                        v-if="mt.parentId===selected1LevelId">
                                    {{mt.content}}
                                </option>
                            </b-form-select>
                        </div>

                        <div class="col-1" style="background-color: transparent; padding: 36px 0px 0px 0px;">
                            <v-btn text icon x-small style="margin-bottom: 10px; margin-right: 0px"
                                   @click="clearSelection(2)">
                                <v-icon style="color: green">mdi-close</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </div>
            </div>

            <!--////////////////////////////////////////search//////////////////////////////////-/////////////////////////////////////////////////////////////-->
            <div class="row col-12" style="background-color: transparent; padding-bottom: 0; padding-top: 25px">
                <div style="padding-top: 11px;">
                    <button type="button" style="font-size: small" @click="showAllMType" class="btn btn-primary">Show
                        all
                        types
                    </button>
                </div>

                <div class="col-sm-2">
                    <input v-model="searchKey" class="form-control"
                           id="search-element" style="padding-right: 20px; font-size: small" v-on:keyup.enter="search"
                           requred/>

                    <span class="close" @click="deleteSearch()"
                          style="margin-top: -29px; margin-right: 5px">&times;</span>
                    <!--                todo-->

                </div>

                <div style="padding-top: 11px">
                    <button type="button" style="font-size: small" @click="search" class="btn btn-primary">Search
                    </button>
                </div>
            </div>
        </form>

        <!--            /////////////////////////////////table///////////////////////////////////////////////////////-->
        <table class="redTable" style="margin-left: 15px; padding-right: 35px; width: 99%">
            <!--        <table class="table">-->
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <!--                <th class='tdTitle'>Id</th>-->

                <!--                <th class='tdTitle'>Родительский хештег</th>-->
                <th class='tdTitle'>Id</th>
                <th class='tdTitle'>Тип материала</th>
                <th class='tdTitle'>Комментарий</th>


                <th class="tdTitle" style="width:4%"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="mt in filteredMTypes">
                <td>
                    {{mt.id}}
                </td>
                <td>
                    {{mt.content}}
                </td>
                <td>
                    {{mt.miscellany}}
                </td>
                <td>
<!--                    <v-btn v-if="clicked.includes(mt.id)" text icon x-small-->
<!--                           @click="deleteAddedMType(mt.id)">-->
<!--                        <v-icon style="color: red">mdi-delete-forever</v-icon>-->
<!--                    </v-btn>-->

                    <v-btn text icon x-small @click="findEditedMType(mt.id)">
                        <v-icon style="color: green">mdi-pencil</v-icon>
                    </v-btn>
                    <v-btn text icon x-small @click="deleteMType(mt.id)">
                        <v-icon style="color: red">mdi-delete-forever</v-icon>
                    </v-btn>
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

<script>

    import api from "./mtype-api";
    import router from "./../../router";
    import "vue-scroll-table";
    import Vuetify from 'vuetify';
    import '@mdi/font/css/materialdesignicons.css'
    import Language from "../language/LanguageList"; //why does icon appear in other file)) add article?

    export default {
        name: 'mtype',
        components: {Language},
        vuetify: new Vuetify(),
        // props: ['commonProp'],
        data() {
            return {
                mtypes: [],
                mtype: {},
                addedMTypesContent: [],
                addedHashtagsId: [], //todo: to delete

                clicked: [], //array for button condition keeping
                response: [],
                errors: [],

                entries: [],

                showResponse: false,
                loggedInFlag: false,
                loggedName: null,
                selected1LevelId: null,
                selected2LevelId: null,

                isAllMTypeMode: false,
                isAddMTypeMode: false,

                selected: null,

                labelAddOrEditMType: "",
                searchKey: '',
            }
        },
        computed: {
            filteredMTypes() {
                // console.log("FILTERED ARTICLES");
                return this.entries;
            },
            levelMTypes() {
                return this.mtypes;
            },
        },
        methods: {

            addMType(id) {
                this.clicked.push(id);
                this.addedMTypesContent.push(this.getMTypeContentById(id));
                // this.addedHashtagsId.push(id);
            },

            // deleteAddedMType(id) {
            //     let clickedIndex = null;
            //     let mtypesContentIndex = null;
            //
            //     this.clicked.forEach((el, index) => {
            //         if (el === id) clickedIndex = index;
            //     });
            //
            //     this.addedMTypesContent.forEach((el, index) => {
            //         if (el === this.getMTypeContentById(id)) mtypesContentIndex = index;
            //     });
            //
            //     this.clicked.splice(clickedIndex, 1);
            //     this.addedMTypesContent.splice(mtypesContentIndex, 1);
            //     // this.addedHashtagsId.splice(hashtagsIdIndex, 1);
            // },

            addChosenMType() { //add to Person
                if (this.addedMTypesContent.length) {
                    // this.$emit('addHashtagToList', this.addedMTypesContent);
                    this.addedMTypesContent = [];
                    // this.addedMTypesId = [];
                    this.clicked = [];
                    console.log("ADD MTYPE");
                }
            },

            showAllMType() {
                this.isAllMTypeMode = true;
                api.getAll(response => {
                    this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                    this.mtypes = response.data;  //to renew edited 1 level dropdown list
                    this.entries = response.data;
                    this.mtype.content = null;
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

                this.mtype.content = null;
            },

            addOrUpdateMType() {
                if (this.mtype.content != null) {
                    if (this.mtype.id != null) { //update

                        api.update(this.mtype.id, this.mtype, r => {

                            if (this.selected2LevelId != null) {                 ///upd 3 level
                                api.getLeaf(this.selected2LevelId, r => {
                                    this.entries = r.data;
                                    this.mtype.content = null;
                                    this.labelAddOrEditMType = "Добавить тип материала 3 уровня, используйте Enter/кнопка <Добавить>";
                                    console.log(r.data)
                                })
                                    .catch(error => {
                                        this.errors.push(error)
                                    });

                            } else {
                                if (this.selected1LevelId != null) {             ///upd 2 level
                                    api.getLeaf(this.selected1LevelId, r => {
                                        this.entries = r.data;
                                        this.mtype.content = null;
                                        this.labelAddOrEditMType = "Добавить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";

                                        api.getAll(response => {
                                            this.mtypes = response.data;  //to renew edited 2 level dropdown list
                                        });
                                    })
                                        .catch(error => {
                                            this.errors.push(error)
                                        });

                                } else {                                         ///upd 1 level
                                    api.getAll(response => {
                                        this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                                        this.mtypes = response.data;  //to renew edited 1 level dropdown list
                                        this.entries = response.data;
                                        this.mtype.content = null;
                                    });
                                }
                            }
                        });
                    } else { //create

                        if (this.selected1LevelId == null) {
                            this.mtype.parentId = 0;

                        } else if (this.selected2LevelId == null) {
                            this.mtype.parentId = this.selected1LevelId;
                            console.log("CREATE 2 level", this.selected1LevelId);

                        } else {
                            this.mtype.parentId = this.selected2LevelId;
                        }

                        api.create(this.mtype, r => {
                            if (this.selected2LevelId != null) {  //crt 3 level
                                api.getLeaf(this.selected2LevelId, r => {
                                    this.entries = r.data;
                                    this.mtype.content = null;
                                    console.log(r.data)
                                })
                                    .catch(error => {
                                        this.errors.push(error)
                                    });
                            } else {
                                if (this.selected1LevelId != null) {   ///crt 2 level

                                    api.getLeaf(this.selected1LevelId, r => {
                                        this.entries = r.data;
                                        this.mtype.content = null;

                                        api.getAll(response => {
                                            this.mtypes = response.data;  //to renew edited 2 level dropdown list
                                        });
                                        // console.log(r.data)
                                    })
                                        .catch(error => {
                                            this.errors.push(error)
                                        });

                                } else {                                  ///crt 1 level
                                    api.getAll(response => {
                                        this.entries = response.data;
                                        this.mtypes = response.data;  //to add new hash in dropdown lists
                                        this.mtype.content = null;

                                        console.log(response.data)
                                    })
                                        .catch(error => {
                                            this.errors.push(error)
                                        });
                                }
                            }
                        }, error => {
                            console.log(error);
                            this.labelAddOrEditMType = "This type already exists";
                        });
                    }
                }
            },

            deleteMType(id) {
                if (confirm("Do you really want to delete '" + this.getMTypeContentById(id) + "'?")) {
                    api.delete(id, r => {
                        api.getAll(response => {
                            this.mtypes = response.data;                          //to renew dropdown lists

                            if (this.isAllMTypeMode) {
                                this.showAllMTypes();
                            } else {
                                if (this.selected1LevelId == null) {
                                    this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                                    api.getLeaf(0, r => {
                                        this.entries = r.data;
                                    });
                                } else {
                                    if (this.selected2LevelId == null) {
                                        this.labelAddOrEditMType = "Добавить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";
                                        api.getLeaf(this.selected1LevelId, r => {
                                            this.entries = r.data;
                                        });
                                    } else {
                                        this.labelAddOrEditMType = "Добавить тип материала 3 уровня, используйте Enter/кнопка <Добавить>";

                                        api.getLeaf(this.selected2LevelId, r => {
                                            this.entries = r.data;
                                        });
                                    }
                                }
                            }
                        });
                    }, error => {
                        console.log(error);
                        this.labelAddOrEditMType = "This type uses in materials, can't delete";
                    });
                }
            },

            getMTypeContentById(id) {
                let content;
                for (let i = 0; i < this.mtypes.length; i++) {
                    if (this.mtypes[i].id === id) {
                        return this.mtypes[i].content;
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

            findEditedMType(id) {
                api.findById(id, resp => {
                    this.mtype = resp.data;
                    if (this.mtype.parentId !== 0) {

                        api.findById(this.mtype.parentId, r => {
                            if (r.data.parentId === 0) {                                                    //second level
                                this.selected1LevelId = r.data.id;
                                this.labelAddOrEditMType = "Изменить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";

                            } else {                                                                         ///third level
                                api.findById(r.data.parentId, r1 => {
                                    this.selected1LevelId = r1.data.id;
                                    this.selected2LevelId = r.data.id;

                                    api.getLeaf(this.selected2LevelId, r => {
                                        this.entries = r.data;
                                    });
                                    this.labelAddOrEditMType = "Изменить тип материала 3 уровня, используйте Enter/кнопка <Добавить>";
                                });
                            }
                        });
                    } else {
                        this.labelAddOrEditMType = "Изменить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                    }
                });
            },

            deleteSearch() {
                this.searchKey = "";
            },

            isArrayValidAndNotEmpty(array) {
                if (typeof array === 'undefined' || array === null || array.length === 0) {
                    return false;
                }
                return true;
            },

            search() {
                if (this.searchKey === "") {
                    api.getAll(response => {
                        this.mtypes = response.data;                          //to renew dropdown lists

                        if (this.isAllMTypeMode) {
                            this.showAllMType();
                        } else {
                            if (this.selected1LevelId == null) {
                                this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                                api.getLeaf(0, r => {
                                    this.entries = r.data;
                                });
                            } else {
                                if (this.selected2LevelId == null) {
                                    this.labelAddOrEditMType = "Добавить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";
                                    api.getLeaf(this.selected1LevelId, r => {
                                        this.entries = r.data;
                                    });
                                } else {
                                    this.labelAddOrEdit = "Добавить тип материала 3 уровня, используйте Enter/кнопка <Добавить>";

                                    api.getLeaf(this.selected2LevelId, r => {
                                        this.entries = r.data;
                                    });
                                }
                            }
                        }
                        console.log("delete", r.data);
                    });
                } else {
                    api.searchHash(this.searchKey, r => {
                        this.entries = r.data;
                    });
                }
            },

            level1UpdateTable() {
                if (this.selected1LevelId == null) {
                    if (!this.isAllMTypeMode) {
                        this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                        api.getLeafHashtags(0, r => {
                            this.entries = r.data;
                        });
                    }
                } else {
                    this.isAllMTypeMode = false;
                    this.labelAddOrEditMType = "Добавить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";

                    api.getLeaf(this.selected1LevelId, r => {
                        this.entries = r.data;
                    });
                }
            },

            mtypeContentTitle() {
                console.log(" mtypeContentTitle(){");

                if (this.mtype.content.length === 0) {
                    console.log(" mtype: function(){ NULL");

                    if (this.selected2LevelId == null) {
                        if (this.selected1LevelId == null) {
                            this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                        } else {
                            this.labelAddOrEditMType = "Добавить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";
                        }
                    } else {
                        this.labelAddOrEditMType = "Добавить тип материала 3 уровня, используйте Enter/кнопка <Добавить>";
                    }
                }
            },
        },

        mounted() {
            // console.log("MOUNT hash", this.commonProp);
            // console.log("MOUNT ARTICLE hash", this.articleProp);

            this.getLoggedIn();

            api.getLeaf(0, r => {
                this.entries = r.data;
            });

            api.getAll(response => {
                this.mtypes = response.data;
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                });

            this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";

            let input = document.getElementById("add-level");
            if (input)
                input.addEventListener("keyup", (event) => {
                    if (event.key === "Enter") {
                        console.log("CATCH ENTER");
                        event.preventDefault();
                        this.addOrUpdateMType();
                    }

                    if (event.key === "Backspace") {
                        event.preventDefault();
                        this.mtypeContentTitle();
                    }

                    if (event.key === "Delete") {
                        event.preventDefault();
                        this.mtypeContentTitle();
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
                        this.labelAddOrEditMType = "Добавить тип материала 1 уровня, используйте Enter/кнопка <Добавить>";
                    } else {
                        this.labelAddOrEditMType = "Добавить тип материала 2 уровня, используйте Enter/кнопка <Добавить>";
                    }

                    api.getLeaf(this.selected1LevelId, r => {
                        this.entries = r.data;
                    });

                } else {
                    this.labelAddOrEditMType = "Добавить тип материала 3 уровня, используйте Enter/кнопка <Добавить>";

                    api.getLeaf(this.selected2LevelId, r => {
                        this.entries = r.data;
                    });
                }
            },

            searchKey: function () {
                //If empty search to renew the table
                console.log("WATCH");
                if (this.searchKey === "") {
                    this.search();
                }
            },
        },
    }
</script>