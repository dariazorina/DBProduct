<template id="person">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions" style="background-color: transparent; margin: 0">
            <a class="btn btn-default">
                <router-link :to="{name: 'person-add'}"><span class="glyphicon glyphicon-plus"></span>Add person
                </router-link>
            </a>
        </div>

<!--        <div class="filters row">-->
<!--            <div class="form-group col-sm-3">-->
<!--                <input placeholder="Поиск по фамилии" v-model="searchKey" class="form-control" id="search-element"-->
<!--                       requred/>-->
<!--            </div>-->
<!--        </div>-->

        <div class="form-group col-sm-12" align="right" style="margin: 0; padding: 5px; background-color: transparent">
            <button type="button" @click="resetAllFilters" class="btn btn-outline-dark">Сброс фильтров</button>
        </div>

        <table class="redTable" id="mainPersonListTable">
            <!--        <table class="table">-->
            <thead>
            <tr>
                <th class='tdTitle headerLink' style="color:lightgray; width: 3%" @click="sort('status')">Статус</th>
                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(3)">
                <div class="row" style="background-color: transparent">
                    <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                    <div class='headerLink col-sm-6'
                         style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                        ФИО
                    </div>
                    <div class='col-sm-3'
                         style="padding-left: 0px; background-color: transparent; visibility: hidden"
                         id="surnameFilterId">
                        <v-btn text icon x-small @click="resetFilter(3)">
                            <v-icon style="color: white">mdi-close-circle</v-icon>
                        </v-btn>
                    </div>
                </div>
                <th class='tdAlignLeft headerLink' @click="sort('birthYear')">Год рождения</th>
                <th class='tdAlignLeft'>Год смерти</th>
                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(2)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Локация
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="locationFilterId">
                            <v-btn text icon x-small @click="resetFilter(2)">
                                <v-icon style="color: white">mdi-close-circle</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </th>
                <th class='tdAlignLeft'>Должность</th>
                <th class='tdAlignLeft' @contextmenu.prevent="searchByField(1)">
                    <div class="row" style="background-color: transparent">
                        <div class='col-sm-3' style="background-color: transparent; padding: 0"></div>
                        <div class='headerLink col-sm-6'
                             style="text-align: center; background-color: transparent; padding-right: 0; padding-left: 0">
                            Организация
                        </div>
                        <div class='col-sm-3'
                             style="padding-left: 0px; background-color: transparent; visibility: hidden"
                             id="orgFilterId">
                            <v-btn text icon x-small @click="resetFilter(1)">
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
            <tr v-for="person in sortedPersons"
                @contextmenu.prevent="handler($event, person.id)"
                :class="{'active': ((person.id === selectedPerson)&&(previousSelectedPerson!==selectedPerson))}">


                <td>
                    <div v-if="statusList.length > 0">
                        <div v-if="statusList[0].name === person.status">
                            <v-icon style="color: orange">mdi-pencil-plus</v-icon>
                        </div>
                    </div>

                    <div v-if="statusList.length > 1">
                        <div v-if="statusList[1].name === person.status">
                            <v-icon style="color: orange">mdi-check</v-icon>
                        </div>
                    </div>

                    <div v-if="statusList.length > 2">
                        <div v-if="statusList[2].name === person.status">
                            <v-icon style="color: red">mdi-clipboard-arrow-left</v-icon>
                        </div>
                    </div>

                    <div v-if="statusList.length > 3">
                        <div v-if="statusList[3].name === person.status">
                            <v-icon style="color: green">mdi-check</v-icon>
                        </div>
                    </div>
                </td>

                <td><a>
                    <router-link :to="{name: 'person-details', params: {person_id: person.id}}">
                        {{ getPrioritySNP(person) }}
                    </router-link>
                </a></td>
                <td class='tdAlignLeft'>{{person.birthYear}}</td>
                <td class='tdAlignLeft'>{{person.deathYear}}</td>
                <td class='tdAlignLeft'>
                    <div v-for="location in person.locationList">
                        <div v-if="person.locationList.length > 0">
                            {{getLocationCellById(location.itemId)}}
                        </div>
                    </div>
                </td>
                <td class='tdAlignLeft'>
                    <!--                    <div v-for="occ in person.testList">{{occ.position}}</div>-->
                </td>

                <td class='tdAlignLeft'>
                    <!--                    <div v-for="occ in person.testList">{{getOrgNameById(occ.orgId)}}</div>-->
                    <!--                    <div v-for="org in person.orgList">{{org.name}}</div>-->
                </td>
                <td class='tdAlignLeft'>
                    <div v-for="ph in person.hashtagList">
                        {{ph}}
                    </div>
                </td>
                <td>
                    <v-btn text icon x-small>
                        <a>
                            <router-link :to="{name: 'person-add', params: {person_id: person.id}}">
                                <v-icon style="color: green">mdi-pencil</v-icon>
                            </router-link>
                        </a>
                    </v-btn>
                    <!--                    <a class="btn btn-danger btn-sm">-->
                    <!--                        <router-link :to="{name: 'person-delete', params: {person_id: person.id}}">Delete</router-link>-->
                    <!--                    </a>-->
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
            </template>
        </b-modal>
    </div>
</template>

<style lang="scss">
    @import '../dbnm.css';

    .active {
        color: blue;
        font-weight: bold;
        border: 3px solid #1C6EA4;
    }
</style>

<script>
    import api from "./person-api";
    import apiOrg from "./../org/org-api";
    import apiCountry from "./../country/country-api";

    import OccupationList from "../components/person-occupation/OccupationList";
    import contextMenu from 'vue-context-menu'
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import 'vue-swatches/dist/vue-swatches.css'
    import CreatedList from "../components/multiple-enter-list/CreatedList";
    import apiStatus from "./../status-api";


    export default {
        components: {
            OccupationList,
            contextMenu,
            VSwatches,
            CreatedList,
        },
        name: 'person',
        data() {
            return {
                color: '#1CA085',
                persons: [],
                entries: [],

                person: {status: 0, locationList: [], positionDtoList: [], hashtagList: [], snpList: []},
                personLocationIds: [], //before request
                personLocationEntities: [], //after request
                personOrgIds: [], //before request
                personOrgEntities: [], //after request

                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,

                currentPerson: [],
                selectedPerson: null,
                previousSelectedPerson: null,

                currentSort: 'birthYear',
                currentSortDir: 'asc',

                filterItems: [{key: 0, value: []}, {key: 1, value: []}, {key: 2, value: []},
                    {key: 3, value: []},],

                filterTableFields: [
                    {key: 0, text: 'Хештеги'},
                    {key: 1, text: 'Организации'},
                    {key: 2, text: 'Локации'},
                    {key: 3, text: 'Фамилии'},
                ],

                // todo - maybe remove key? it dublicates index
                filterTableFieldsForRequest: [
                    {key: 0, text: 'hash'},
                    {key: 1, text: 'org'},
                    {key: 2, text: 'location'},
                    {key: 3, text: 'surname'},
                ],

                statusList: [
                    {id:1, name:''},  //to prevent access to undefined list
                    {id:2, name:''},
                    {id:3, name:''},
                    {id:4, name:''}
                ],

                currentFilterField: '',
                currentFilterItems: [],
            }
        },
        computed: {
            filteredPersons() {
                // return this.persons.filter((person) => {
                //     if (person.surname && person.surnameRus) {
                //         return person.surname.indexOf(this.searchKey) > -1
                //             || person.surnameRus.indexOf(this.searchKey) > -1
                //
                //     } else if (person.surname) {
                //         return person.surname.indexOf(this.searchKey) > -1
                //
                //     } else if (person.surnameRus) {
                //         return person.surnameRus.indexOf(this.searchKey) > -1
                //     }
                // })

                return this.entries;
            },

            sortedPersons() {
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
            this.alternate('mainPersonListTable');
        },

        methods: {

            getPrioritySNP(currentPerson) {

                console.log("*************************", this.entries);
                let sss = 0;// = this.person.snpList.find(x => x.priority === 1).surname;


                    for (let i = 0; i < currentPerson.snpList.length; i++) {
                        console.log("SNPLIST i", currentPerson.snpList[i], i, currentPerson.snpList[i].priority);

                        if (currentPerson.snpList[i].priority === 1) {
                            sss = currentPerson.snpList[i].surname + " " + currentPerson.snpList[i].name + " ";

                            if (currentPerson.snpList[i].patronymic != null)
                                sss += currentPerson.snpList[i].patronymic;
                            console.log("******^^^^^^^^^^^^^****", sss);
                        }
                    }

                // console.log("*************************", sss);
                return sss;
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

                // api.getAllPersons(response => {
                //     this.entries = response.data;
                //     // console.log("PERSONS", response.data)
                // });
            },

            updateItem(item) {  // (2) calls when search item adds to search list
                console.log("ADDED LINK", item, this.currentFilterItems);
                //this.currentFilterItems.push(item);  //push item(item: {id, content}

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
                if (this.previousSelectedPerson !== this.selectedPerson) {
                    document.getElementById('context-menu').setAttribute('style', 'display');
                    this.$refs.ctxMenu.open(event);
                } else {
                    // console.log("CNTXT MENU", document.getElementById('context-menu'));
                    document.getElementById('context-menu').setAttribute('style', 'display:none');
                    //classList.add('hidden');
                }
            },

            cancelRowSelection() {
                let currentPerson = this.filteredPersons.find(x => x.id === this.selectedPerson);
                this.setColor(currentPerson, null);
            },

            selectRow(personId) {
                if (this.previousSelectedPerson === this.selectedPerson) { //to set ability to check again after unchecking the same row
                    this.previousSelectedPerson = -1;
                } else {
                    this.previousSelectedPerson = this.selectedPerson;
                }
                this.selectedPerson = personId;
            },

            setColor(currentPerson, color) {
                currentPerson.rowColor = color;
                api.update(currentPerson.id, currentPerson, r => {
                    console.log("DONEEEEEE SAVE");
                });
                this.alternate('mainPersonListTable');
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

            getOrgNameById(id) {
                let result = '';
                let currentOrg = this.personOrgEntities.find(x => x.id === id);

                // console.log("ORG", currentOrg);
                if (this.isArrayValidAndNotEmpty(currentOrg)) {//to prevent errors in console when search result isn't ready yet
                    result = currentOrg.nameRus;
                    return result;
                }
            },

            getLocationCellById(id) {
                let result = '';
                let currentLocation = this.personLocationEntities.find(x => x.id === id);

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

            isArrayValidAndNotEmpty(array) {
                if (typeof array === 'undefined' || array === null || array.length == 0) {
                    return false;
                }
                return true;
            },

            showCountry(country) {
                if (country) {
                    // console.log("COUNTRY " + country.name);
                    return country.name;
                }
                // console.log("COUNTRY NULL")
                return "";
            },

            filterAll() {
                api.filterAll(this.filterAllBodyCreation(), r => {
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
        },
        mounted() {

            apiStatus.getAllStatuses(response => {
                this.statusList = response.data;
                console.log("STATUS LIST", this.statusList);
            });

            api.getAllPersons(localStorage.getItem('movement'), response => {
                // this.persons = response.data;
                this.entries = response.data;
                // console.log("PERSONS", response.data)

                for (let i = 0; i < this.entries.length; i++) {
                    for (let j = 0; j < this.entries[i].positionDtoList.length; j++) {
                        this.personOrgIds.push(this.entries[i].positionDtoList[j].orgId);
                    }
                    for (let j = 0; j < this.entries[i].locationList.length; j++) {
                        this.personLocationIds.push(this.entries[i].locationList[j].itemId);
                    }
                }
                // apiOrg.getOrgsByIds(this.personOrgIds, response => {
                //     this.personOrgEntities = response.data;
                // });

                apiCountry.getLocationsByIds(this.personLocationIds, response => {
                    this.personLocationEntities = response.data;
                });
            });
        },
        watch: {
            color: function () {   //calls when color picking is done
                console.log("----------------------COLOR");
                let currentPerson = this.filteredPersons.find(x => x.id === this.selectedPerson);
                this.setColor(currentPerson, this.color);
                this.$root.$emit('bv::hide::modal', 'modal-color');
            },
        }
    }
</script>