<template id="person">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions">
            <a class="btn btn-default">
                <router-link :to="{name: 'person-add'}"><span class="glyphicon glyphicon-plus"></span>Add person
                </router-link>
            </a>
        </div>

        <div class="filters row">
            <div class="form-group col-sm-3">
                <input placeholder="Поиск по фамилии" v-model="searchKey" class="form-control" id="search-element"
                       requred/>
            </div>
        </div>
        <table class="redTable">
            <!--        <table class="table">-->
            <thead>
            <tr>
<!--                <th class='tdAlignLeft'>Статус</th>-->
                <th class='tdAlignLeft'>Фамилия, Имя </th>
                <th class='tdAlignLeft'>Год рождения</th>
                <th class='tdAlignLeft'>Год смерти</th>
                <th class='tdAlignLeft'>Локация</th>
                <th class='tdAlignLeft'>Должность</th>
                <th class='tdAlignLeft'>Организация</th>
                <th class='tdAlignLeft'>Хештеги</th>
                <th style="width:10%" class="col-sm-2"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="person in filteredPersons">
<!--                <td>{{ person. }}</td>-->
                <td><a>
                    <router-link :to="{name: 'person-details', params: {person_id: person.id}}">{{ person.surnameRus}} {{ person.nameRus }} <div v-if="person.patronymic != null">
                        {{ person.patronymic}}
                    </div>
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
                    <div v-for="occ in person.testList">{{occ.position}}</div>
                </td>

                <td class='tdAlignLeft'>
                    <div v-for="occ in person.testList">{{getOrgNameById(occ.orgId)}}</div>
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
    </div>
</template>

<style lang="scss">
    @import '../dbnm.css';
</style>

<script>
    import api from "./person-api";
    import apiOrg from "./../org/org-api";
    import apiCountry from "./../country/country-api";

    import OccupationList from "../components/person-occupation/OccupationList";

    export default {
        components: {
            OccupationList,
        },
        name: 'person',
        data() {
            return {
                persons: [],
                allOrgs: [],

                person: {status: 0, locationList: [], testList: [], hashtagList: []},
                personLocationIds: [], //before request
                personLocationEntities: [], //after request
                personOrgIds: [], //before request
                personOrgEntities: [], //after request

                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
            }
        },
        computed: {
            filteredPersons() {
                return this.persons.filter((person) => {
                    if (person.surname && person.surnameRus) {
                        return person.surname.indexOf(this.searchKey) > -1
                            || person.surnameRus.indexOf(this.searchKey) > -1

                    } else if (person.surname) {
                        return person.surname.indexOf(this.searchKey) > -1

                    } else if (person.surnameRus) {
                        return person.surnameRus.indexOf(this.searchKey) > -1
                    }
                })
            }
        },

        methods: {
            loadPersons() {
                api.getAllPersons(response => {
                    this.persons = response.data;
                    console.log(response.data)
                })
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

            // getOrgNameById(orgId){
            //     for (let i = 0; i < this.allOrgs.length; i++) {
            //         if (this.allOrgs[i].id === orgId){
            //             return this.allOrgs[i].name;
            //         }
            //     }
            // },

            showCountry(country) {
                if (country) {
                    // console.log("COUNTRY " + country.name);
                    return country.name;
                }
                // console.log("COUNTRY NULL")
                return "";
            }
        },
        mounted() {
            api.getAllPersons(response => {
                this.persons = response.data;
                // console.log("PERSONS", response.data)

                for (let i = 0; i < this.persons.length; i++) {
                    for (let j = 0; j < this.persons[i].testList.length; j++) {
                        this.personOrgIds.push(this.persons[i].testList[j].orgId);
                    }
                    for (let j = 0; j < this.persons[i].locationList.length; j++) {
                        this.personLocationIds.push(this.persons[i].locationList[j].itemId);
                    }
                }

                // console.log("IDS", this.personOrgIds, this.personLocationIds);

                apiOrg.getOrgsByIds(this.personOrgIds, response => {
                    this.personOrgEntities = response.data;
                    // console.log("IDS--------------", this.personOrgIds);
                });

                apiCountry.getLocationsByIds(this.personLocationIds, response => {
                    this.personLocationEntities = response.data;
                    // console.log("IDS-----****---------", this.personLocationEntities);
                });


            });

            // apiOrg.getAllOrgs(response => {
            //     this.allOrgs = response.data;
            //     // console.log(" O R G A ", response.data)
            // });
        }
    }
</script>