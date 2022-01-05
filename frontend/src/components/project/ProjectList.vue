<template>
    <div class="noselect" style="background-color: transparent">
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions" style="background-color: transparent; margin: 0">
            <a class="btn btn-default">
                <router-link :to="{name: 'project-add'}"><span class="glyphicon glyphicon-plus"></span>Добавить проект
                </router-link>
            </a>
        </div>
        <div class="actions" style="background-color: transparent; margin: 0">
            <button type="button" @click="previousPageGo" class="btn btn-outline-light">
                <v-icon style="color: #0074D9">mdi-arrow-left-circle</v-icon>
            </button>
            {{currentPageNumber+1}}
            <button type="button" @click="nextPageGo" class="btn btn-outline-light">
                <v-icon style="color: #0074D9">mdi-arrow-right-circle</v-icon>
            </button>
        </div>

        <div v-if="isAnyFilterActive() === true" class="form-group col-sm-12" align="right"
             style="margin: 0; padding: 5px; background-color: transparent">
            <button type="button" @click="resetAllFilters" class="btn btn-outline-dark">Сброс фильтров</button>
        </div>

        <table class="redTable" id="mainProjectListTable">
            <!--        <table class="table">-->
            <thead>
            <tr>
                <th class='tdTitle headerLink' style="color:lightgray; width: 3%" @click="sort('status')">Статус</th>
                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(1)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Локация
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="locationFilterId">
                            <v-btn text icon x-small @click="resetFilter(1)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(2)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0"
                             @click="sort('name')">
                            Название
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="nameFilterId">
                            <v-btn text icon x-small @click="resetFilter(2)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdAlignLeft'>Теги</th>
                <th class='tdAlignLeft headerLink' @click="sort('foundationYear')">Год основания</th>
                <th class='tdAlignLeft'>Год закрытия</th>


                <th class='tdAlignLeft'>Проекты</th>
                <th class='tdAlignLeft'>Персоны</th>

                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(3)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Организация
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="orgFilterId">
                            <v-btn text icon x-small @click="resetFilter(3)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>

                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(0)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Хештеги
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="hashFilterId">
                            <v-btn text icon x-small @click="resetFilter(0)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                <th style="width:10%" class="col-sm-2">Действия</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="pro in sortedProjects"
                @contextmenu.prevent="handler($event, pro.id)"
                :class="{'active': ((pro.id === selectedProject)&&(previousSelectedProject!==selectedProject))}">


                <td style="text-align: center; padding-left: 0">
                    <div v-if="statusList.length > 0">
                        <div v-if="statusList[0].name === pro.status">
                            <v-icon style="color: orange">mdi-pencil-plus</v-icon>
                        </div>
                    </div>

                    <div v-if="statusList.length > 1">
                        <div v-if="statusList[1].name === pro.status">
                            <v-icon style="color: orange">mdi-check</v-icon>
                        </div>
                    </div>

                    <div v-if="statusList.length > 2">
                        <div v-if="statusList[2].name === pro.status">
                            <v-icon style="color: red">mdi-clipboard-arrow-left</v-icon>
                        </div>
                    </div>

                    <div v-if="statusList.length > 3">
                        <div v-if="statusList[3].name === pro.status">
                            <v-icon style="color: green">mdi-check</v-icon>
                        </div>
                    </div>
                </td>
                <td class='tdAlignLeft'>
                    <div v-for="location in pro.locationList">
                        <div v-if="pro.locationList.length > 0">
                            {{location}}
                        </div>
                    </div>
                </td>
                <td><a>
                    <router-link :to="{name: 'project-details', params: {project_id: pro.id}}" target="_blank">
                        {{ pro.name }}
                    </router-link>
                </a></td>
                <td class='tdAlignLeft'>
                    <div v-for="tag in pro.tagList">{{tag}}</div>
                </td>
                <td style="text-align: center; padding-left: 0">{{pro.foundationYear}}</td>
                <td style="text-align: center; padding-left: 0">{{pro.closureYear}}</td>

                <td class='tdAlignLeft'>
                    <div v-for="pro in pro.projectList">{{pro}}</div>
                </td>

                <td class='tdAlignLeft'>
                    <div v-for="person in pro.personList">{{person}}</div>
                </td>

                <td class='tdAlignLeft'>
                    <div v-for="org in pro.orgList">{{org}}</div>
                </td>

                <td class='tdAlignLeft'>
                    <div v-for="ph in pro.hashtagList">
                        {{ph}}
                    </div>
                </td>
                <td style="text-align: center; padding-left: 0">
                    <v-btn text icon x-small>
                        <a>
                            <router-link :to="{name: 'project-add', params: {project_id: pro.id}}">
                                <v-icon style="color: green">mdi-pencil</v-icon>
                            </router-link>
                        </a>
                    </v-btn>
                </td>
            </tr>
            </tbody>
        </table>

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
        <b-modal ref="modalSearch" :hide-footer=true :hide-header=true>
            <template>
                <b><p class="myHeader">Фильтр по полю '{{currentFilterField.text}}'</p><br></b>
                <div align="center">
                    <CreatedList :items="currentFilterItems"
                                 @update-item="updateItem"/>

                    <div v-if="currentFilterItems.length > 0">
                        <b-button size="sm" variant="success"
                                  @click="startModalSearch()">
                            Применить
                        </b-button>
                    </div>

                </div>
                <div style="color: gray; font-size: 12px; font-weight: normal; margin-top: 20px">Множественный фильтр по
                    текущему полю будет отрабатывать
                    только в том случае, если он является <i>единственным</i> фильтром (фильтры других полей не
                    задействованы).
                    Для отработки множественных фильтров многих полей будет использоваться только <i>первое</i>
                    введенное значение фильтра для текущего поля.
                </div>
            </template>
        </b-modal>
    </div>
</template>

<script>

    import apiProject from "./../project/project-api";
    // import apiCountry from "./../country/country-api";
    // import apiPerson from "./../person/person-api";


    import contextMenu from 'vue-context-menu'
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import 'vue-swatches/dist/vue-swatches.css'
    import CreatedList from "../components/multiple-enter-list/CreatedList";
    import apiStatus from "./../status-api";
    import Vuetify from 'vuetify';

    export default {
        components: {
            contextMenu,
            VSwatches,
            CreatedList,
        },
        vuetify: new Vuetify(),

        name: "project-list",
        data() {
            return {
                color: '#1CA085',
                entries: [],

                project: {
                    status: 0,
                    locationList: [],
                    personList: [],
                    orgList: [],
                    projectList: {}, //[]
                    articleList: [],
                    eventList: [],
                    hashtagList: [],
                    tagList: [],
                    linkList: []
                },
                // orgLocationIds: [], //before request
                // orgLocationEntities: [], //after request
                // orgOrgIds: [], //before request
                // orgOrgEntities: [], //after request
                // orgPersonIds: [], //before request
                // orgPersonEntities: [], //after request

                // searchKey: '',
                response: [],
                // errors: [],
                // showResponse: false,

                currentProject: [],
                selectedProject: null,
                previousSelectedProject: null,

                currentSort: 'name',
                // currentSort: 'foundationYear',
                currentSortDir: 'asc',

                filterItems: [{key: 0, value: []}, {key: 1, value: []}, {key: 2, value: []}, {key: 3, value: []},],

                filterTableFields: [
                    {key: 0, text: 'Хештеги'},
                    {key: 1, text: 'Локации'},
                    {key: 2, text: 'Названия'},
                    {key: 3, text: 'Организации'},
                ],

                // todo - maybe remove key? it dublicates index
                filterTableFieldsForRequest: [
                    {key: 0, text: 'hash'},
                    {key: 1, text: 'location'},
                    {key: 2, text: 'name'},
                    {key: 3, text: 'org'},
                ],

                statusList: [
                    {id: 1, name: ''},  //to prevent access to undefined list
                    {id: 2, name: ''},
                    {id: 3, name: ''},
                    {id: 4, name: ''}
                ],

                currentFilterField: '',
                currentFilterItems: [],
                currentPageNumber: 0,
                entriesQuantity: 0,
                entriesQuantPerPage: 15,
            }
        },

        computed: {

            filteredProjects() {  //todo?
                return this.entries;
            },

            sortedProjects() {
                return this.entries.sort((a, b) => {
                    let modifier = 1;
                    if (this.currentSortDir === 'desc') modifier = -1;
                    if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                    if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                    return 0;
                });
            },
        },

        updated() {
            //console.log(`updated At this point, Virtual DOM has re-rendered and patched.`)
            this.alternate('mainProjectListTable');
        },

        methods: {
            nextPageGo() {
                console.log("* number quantPerPage", (this.currentPageNumber + 1) * this.entriesQuantPerPage, this.currentPageNumber, this.entriesQuantPerPage);
                if (((this.currentPageNumber + 1) * this.entriesQuantPerPage) < this.entriesQuantity) {
                    this.currentPageNumber++;
                    this.getAllProjectsWithMov(this.currentPageNumber, this.entriesQuantPerPage);
                }
            },

            previousPageGo() {
                if (this.currentPageNumber > 0) {
                    this.currentPageNumber--;
                    this.getAllProjectsWithMov(this.currentPageNumber, this.entriesQuantPerPage);
                }
            },

            complexMovementCreation(movArray) {

                let result = '';
                let complexMov = '&mov=';
                let i = 0;

                for (; i < movArray.length - 1; i++) {
                    result += movArray[i] + complexMov;
                }

                result += movArray[i];

                // console.log("complexMOVVVCreation", result);
                return result;
            },

            isAnyFilterActive() {
                for (let j = 0; j < this.filterItems.length; j++) {
                    if (this.filterItems[j].value.length) return true;
                }
                return false;
            },

            filterClearButtonActivity(hide, filterClearButtonId) {
                if (hide)
                    document.getElementById(filterClearButtonId).style.visibility = "hidden";
                else
                    document.getElementById(filterClearButtonId).style.visibility = "visible";
            },

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
                this.filterAll();
            },

            updateItem(item) {  // (2) calls when search item adds to search list
                console.log("ADDED LINK", item, this.currentFilterItems);
                /////////////// this.currentFilterItems.push(item);  //push item(item: {id, content}

                let key = this.filterTableFields.find(x => x === this.currentFilterField).key;
                //  console.log("*_*_*_*_*_*_*_*_*_*__*", key);
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
                if (this.previousSelectedProject !== this.selectedProject) {
                    document.getElementById('context-menu').setAttribute('style', 'display');
                    this.$refs.ctxMenu.open(event);
                } else {
                    // console.log("CNTXT MENU", document.getElementById('context-menu'));
                    document.getElementById('context-menu').setAttribute('style', 'display:none');
                    //classList.add('hidden');
                }
            },

            cancelRowSelection() {
                let currentProject = this.filteredProjects.find(x => x.id === this.selectedProject);
                this.setColor(currentProject, null);
            },

            selectRow(proId) {
                if (this.previousSelectedProject === this.selectedProject) { //to set ability to check again after unchecking the same row
                    this.previousSelectedProject = -1;
                } else {
                    this.previousSelectedProject = this.selectedProject;
                }
                this.selectedProject = proId;
            },

            setColor(currentProject, color) {
                currentProject.rowColor = color;
                apiProject.updateColor(currentProject.id, currentProject, r => {
                    console.log("DONEE SAVE PRO");
                });
                this.alternate('mainProjectListTable');
            },

            alternate(id) {
                if (document.getElementsByTagName) {
                    let table = document.getElementById(id);
                    if (table !== null) {

                        let rows = table.getElementsByTagName("tr");
                        for (let i = 0; i < rows.length - 1; i++) {

                            if (this.entries[i] !== null && typeof this.entries[i] !== 'undefined') {
                                if (this.entries[i].rowColor !== null) {
                                    rows[i + 1].style.backgroundColor = this.entries[i].rowColor;//'#888888';//+1 because of title

                                } else { //todo - get colors from css, not consts
                                    rows[i + 1].style.backgroundColor = (i % 2) ? "#EEEEEE" : '#D0E4F5';//'#888888';//+1 because of title
                                }
                            }
                        }
                    }
                }
            },

            isArrayValidAndNotEmpty(array) {
                if (typeof array === 'undefined' || array === null || array.length == 0) {
                    return false;
                }
                return true;
            },

            filterAll() {
                apiProject.filterAll(this.filterAllBodyCreation(), this.complexMovementCreation(JSON.parse(localStorage.getItem('movement'))), r => {
                    this.entries = r.data;
                    console.log("filter all =============", this.entries);
                });
            },

            filterAllBodyCreation() {
                let result = '';
                let i = 0, j = 0;
                //encodeURIComponent  -- to avoid error with '&' in parameters (ex. P&P - cannot find)
                //This function encodes special characters. In addition, it encodes the following characters: , / ? : @ & = + $ #

                for (; j < this.filterItems.length; j++) {
                    for (i = 0; i < this.filterItems[j].value.length; i++) {
                        result += this.filterTableFieldsForRequest[j].text + '=' + encodeURIComponent(this.filterItems[j].value[i]) + '&';
                    }
                }
                return result;
            },

            getAllProjectsWithMov(page, size) {
                apiProject.getAllProjects(this.complexMovementCreation(JSON.parse(localStorage.getItem('movement'))), page, size, response => {
                    this.entries = response.data;
                    console.log("PROJECTSSS", response.data, this.entries.length);
                });
            }
        },
        mounted() {
            apiStatus.getAllStatuses(response => {
                this.statusList = response.data;
//                console.log("STATUS LIST", this.statusList);
            });
            apiProject.getQuantAllEntities(this.complexMovementCreation(JSON.parse(localStorage.getItem('movement'))), response => {
                this.entriesQuantity = response.data;
            });
            this.getAllProjectsWithMov(this.currentPageNumber, this.entriesQuantPerPage);
        },
        watch: {
            color: function () {   //calls when color picking is done
                console.log("----------------------COLOR");
                let currentProject = this.filteredProjects.find(x => x.id === this.selectedProject);
                this.setColor(currentProject, this.color);
                this.$root.$emit('bv::hide::modal', 'modal-color');
            },
        }
    }
</script>