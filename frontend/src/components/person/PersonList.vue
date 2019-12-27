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
                <input placeholder="Search" v-model="searchKey" class="form-control" id="search-element" requred/>
            </div>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th class='tdAlignLeft'>Id</th>
                <th class='tdAlignLeft'>Surname</th>
                <th class='tdAlignLeft'>Name</th>
                <th class='tdAlignLeft'>Patronymic</th>
                <th class='tdAlignLeft'>Фамилия</th>
                <th class='tdAlignLeft'>Имя</th>
                <th class='tdAlignLeft'>Surname</th>
                <th class='tdAlignLeft'>Name</th>
                <th class='tdAlignLeft'>Movement</th>
                <th class='tdAlignLeft'>Country</th>
                <th class='tdAlignLeft'>Settlement</th>
                <th class='tdAlignLeft'>Occupation</th>
                <th class='tdAlignLeft' style="width:35%">Description</th>

                <th style="width:10%" class="col-sm-2">Actions</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="person in filteredPersons">

                <td>{{person.id }}</td>

                <td><a>
                    <router-link :to="{name: 'person-details', params: {person_id: person.id}}">{{ person.surname }}
                    </router-link>
                </a></td>


<!--                <td>{{person.surname }}</td>-->
                <td class='tdAlignLeft'>{{person.name}}</td>
                <td class='tdAlignLeft'>{{person.patronymic }}</td>
                <td class='tdAlignLeft'>{{person.surnameRus }}</td>
                <td class='tdAlignLeft'>{{person.nameRus }}</td>
                <td class='tdAlignLeft'>{{person.surnameEng }}</td>
                <td class='tdAlignLeft'>{{person.nameEng }}</td>
                <td class='tdAlignLeft'>{{person.movement.name}}</td>
                <td class='tdAlignLeft'>{{person.country.name}}</td>
                <td class='tdAlignLeft'>{{person.settlement }}</td>
                <td class='tdAlignLeft'>{{person.occupation }}</td>
                <td class='tdAlignLeft'>{{person.description }}</td>

                <!--                <td>-->
                <!--                    <a>-->
                <!--                        <router-link :to="{name: 'article', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                        </router-link>-->
                <!--                    </a>-->
                <!--                </td>-->


                <td>
                    <a class="btn btn-warning btn-sm mr-2">
                        <router-link :to="{name: 'person-edit', params: {person_id: person.id}}">Edit</router-link>
                    </a>
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

    export default {
        name: 'person',
        data() {
            return {
                persons: [],
                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
            }
        },
        computed: {
            filteredPersons() {
                return this.persons.filter((person) => {
                    return person.surname.indexOf(this.searchKey) > -1
                })
            }
        },
        methods: {
            loadPersons() {
                api.getAll().then(response => {
                    this.persons = response.data;
                    console.log(response.data)
                })
                    .catch(error => {
                        this.errors.push(error)
                    })
            },


        },
        mounted() {
            api.getAll().then(response => {
                this.persons = response.data;
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                })
        }
    }
</script>

<!--<style scoped>-->
<!--</style>-->