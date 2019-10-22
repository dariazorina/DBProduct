<template id="articles">
    <div>
        <div class="filters row">
            <div class="form-group col-sm-3">
                <input placeholder="Search" v-model="searchKey" class="form-control" id="search-element" requred/>
            </div>
        </div>
        <table class="table" style="width:100%">
            <thead width="400px">
            <tr>
                <th>Id</th>
                <th>Language</th>
                <th>Movement</th>
                <th>Title</th>
                <th>Title_rus</th>
                <th>Author</th>
                <th style="width:80%">Description</th>
                <th>URL</th>

                <th class="col-sm-2">Actions</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="article in filteredArticles">
                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td> {{article.id }}</td>
                <td>{{article.movement_id}}</td>
                <td>{{article.language_id}}</td>
                <td>{{article.title }}</td>
                <td>{{article.title_rus }}</td>
                <td>{{article.authors.length() }}</td>
                <td>{{article.description }}</td>
                <td>{{article.url }}</td>

                <!--                <td>-->
                <!--                    <a>-->
                <!--                        <router-link :to="{name: 'article', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                        </router-link>-->
                <!--                    </a>-->
                <!--                </td>-->

                <td>
                    <a class="btn btn-warning btn-xs">
                        <router-link :to="{name: 'article-edit', params: {article_id: article.id}}">Edit
                        </router-link>
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

<script>
    import api from "./article-api";

    export default {
        name: 'articles',
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
                        || article.id.indexOf(this.searchKey) > -1
                        || article.url.indexOf(this.searchKey) > -1
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
        }
    }
</script>

<style scoped>
</style>