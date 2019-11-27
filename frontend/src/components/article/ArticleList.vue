<template id="article">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions">
            <a class="btn btn-default">
                <router-link :to="{name: 'article-add'}"><span class="glyphicon glyphicon-plus"></span>Add article
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
                <th>Language</th>
                <th>Movement</th>
                <th style="width:20%">Title</th>
                <th style="width:20%">Title, russian</th>
                <!--                <th style="speak-date: dmy">Date</th>-->
                <th data-field="createdAt" data-formatter="dateFormat">Created At</th>
                <th style="width:25%">Description</th>
                <th>URL</th>

                <th class="col-sm-2">Actions</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="article in articles">
                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td> {{article.id }}</td>
                <td>{{article.language.name}}</td>
                <td>{{article.movement.name}}</td>
                <td>{{article.title }}</td>
                <td>{{article.titleRus }}</td>
                <td>{{  formatDate(article.date) }}  </td>
                <td>{{article.description }}</td>
                <td>{{article.url }}</td>

                <!--                <td>-->
                <!--                    <a>-->
                <!--                        <router-link :to="{name: 'article', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                        </router-link>-->
                <!--                    </a>-->
                <!--                </td>-->


                <td>
                    <a class="btn btn-warning btn-xs mr-2">
                        <router-link :to="{name: 'article-edit', params: {article_id: article.id}}">Edit</router-link>
                    </a>
                    <a class="btn btn-danger btn-xs">
                        <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">Delete
                        </router-link>
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
    import api from "./article-api";
    import moment from "moment";

    export default {
        name: 'article',
        data() {
            return {
                articles: [],
                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
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
            loadArticles() {
                api.getAll().then(response => {
                    this.articles = response.data;
                    console.log(response.data)
                })
                    .catch(error => {
                        this.errors.push(error)
                    })
            },

            formatDate(date){
                return moment(date).format('DD/MM/YYYY');
            }

        },
        mounted() {
            api.getAll().then(response => {
                this.articles = response.data;
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                })
        },
        dateFormat(value, row, index) {  //todo
            moment(this.article.date).format('DD/MM/YYYY').then(response => {
                this.article.date = response.data;
            })
        }
    }
</script>

<!--<style scoped>-->
<!--</style>-->