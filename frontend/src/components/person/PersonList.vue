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
                <th>Id</th>
                <th>Surname</th>
                <th>Name</th>
                <th>Patronymic</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Surname</th>
                <th>Name</th>
                <th>Movement</th>
                <th>Country</th>
                <th>Settlement</th>
                <th>Occupation</th>
                <th style="width:35%">Description</th>

                <th  style="width:10%" class="col-sm-2">Actions</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="person in persons">
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>{{person.id }}</td>
                <td>{{person.surname }}</td>
                <td>{{person.name}}</td>
                <td>{{person.patronymic }}</td>
                <td>{{person.surnameRus }}</td>
                <td>{{person.nameRus }}</td>
                <td>{{person.surnameEng }}</td>
                <td>{{person.nameEng }}</td>
                <td>{{person.movement.name}}</td>
                <td>{{person.country.name}}</td>
                <td>{{person.settlement }}</td>
                <td>{{person.occupation }}</td>
                <td>{{person.description }}</td>

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
                    <a class="btn btn-danger btn-sm">
                        <router-link :to="{name: 'person-delete', params: {person_id: person.id}}">Delete</router-link>
                    </a>
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
            filteredArticles() {
                return this.articles.filter((article) => {
                    return article.title.indexOf(this.searchKey) > -1
                        || article.date.indexOf(this.searchKey) > -1
                        || article.description.indexOf(this.searchKey) > -1
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