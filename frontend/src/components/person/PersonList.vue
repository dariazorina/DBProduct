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
                <th class='tdAlignLeft'>Id</th>
                <th class='tdAlignLeft'>Фамилия</th>
                <th class='tdAlignLeft'>Имя</th>
                <th class='tdAlignLeft'>Фамилия на русском</th>
                <th class='tdAlignLeft'>Имя на русском</th>
                <!--                <th class='tdAlignLeft'>Patronymic</th>-->
                <!--                <th class='tdAlignLeft'>Surname</th>-->
                <!--                <th class='tdAlignLeft'>Name</th>-->
                <!--                <th class='tdAlignLeft'>Movement</th>-->
                <th class='tdAlignLeft'>Год рождения</th>
                <th class='tdAlignLeft'>Год смерти</th>
                <th class='tdAlignLeft'>Страна</th>
                <th class='tdAlignLeft'>Город</th>
                <th class='tdAlignLeft'>Должность</th>
                <th class='tdAlignLeft'>Организация</th>
                <th class='tdAlignLeft'>Хештеги</th>
                <!--                <th class='tdAlignLeft' style="width:35%">Description</th>-->

                <th style="width:10%" class="col-sm-2"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="person in filteredPersons">
                <td>{{person.id }}</td>
                <td><a>
                    <router-link :to="{name: 'person-details', params: {person_id: person.id}}">{{ person.surname }}
                    </router-link>
                </a></td>

                <td class='tdAlignLeft'>{{person.name}}</td>
                <td class='tdAlignLeft'>{{person.surnameRus }}</td>
                <td class='tdAlignLeft'>{{person.nameRus}}</td>
                <td class='tdAlignLeft'>{{person.birthYear}}</td>
                <td class='tdAlignLeft'>{{person.deathYear}}</td>
                <td class='tdAlignLeft'>{{showCountry(person.country)}}</td>
                <!--                <td class='tdAlignLeft'>{{person.surnameEng }}</td>-->
                <!--                <td class='tdAlignLeft'>{{person.nameEng }}</td>-->
                <!--                <td class='tdAlignLeft'>{{person.movement.name}}</td>-->
                <!--                <td class='tdAlignLeft'>{{person.country.name}}</td>-->
                <td class='tdAlignLeft'>{{person.settlement }}</td>
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

                <!--                <td class='tdAlignLeft'>{{person.description }}</td>-->

                <!--                <td>-->
                <!--                    <a>-->
                <!--                        <router-link :to="{name: 'article', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                        </router-link>-->
                <!--                    </a>-->
                <!--                </td>-->


                <td>
<!--                    <a class="btn btn-warning btn-sm mr-2">-->
<!--                        <router-link :to="{name: 'person-add', params: {person_id: person.id}}">Edit</router-link>-->
<!--                    </a>-->


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
    import OccupationList from "./OccupationList";

    export default {
        components: {
            OccupationList,
        },
        name: 'person',
        data() {
            return {
                persons: [],
                allOrgs: [],

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

            getOrgNameById(orgId){
                for (let i = 0; i < this.allOrgs.length; i++) {
                    if (this.allOrgs[i].id === orgId){
                        return this.allOrgs[i].name;
                    }
                }
            },

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
                // console.log(response.data)
            });

            apiOrg.getAllOrgs(response => {
                this.allOrgs = response.data;
                // console.log(" O R G A ", response.data)
            });
        }
    }
</script>