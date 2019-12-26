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

        <!--        <vue-scroll-table>-->
        <!--            <template slot="thead">-->
        <!--                <table class="table">-->

        <!--        <table id="dtHorizontalExample" class="table table-striped table-bordered table-sm" cellspacing="0"-->
        <!--               width="100%">-->

        <!--            <div class="scrollable">-->

        <!--        <v-layout column style="height: 90vh"> <-&#45;&#45; added height-->
        <!--            <v-flex md6 style="overflow: auto"> <-&#45;&#45; added overflow-->
        <!--                <v-data-table-->
        <!--                        :headers="headers"-->
        <!--                        :items="desserts"-->
        <!--                        hide-actions-->
        <!--                        class="elevation-1"-->
        <!--                >-->


        <table class="table">
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <th>Id</th>
                <th>Authors</th>
                <th>Language</th>
                <th>Movement</th>
                <th style="width:15%">Title</th>
                <th style="width:15%">Title, russian</th>
                <th data-field="createdAt" data-formatter="dateFormat">Created At</th>
                <th>Description</th>
                <th>URL</th>
                <!--                <th style="width:10%">Links</th>-->
                <th>Hashtags</th>

                <th style="width:10%" class="col-sm-2">Actions</th>
            </tr>
            </thead>
            <tbody>

            <!--            <tr v-for="country in filteredCountries">-->
            <!--            <tr v-for="article in articles">-->

            <tr v-for="article in filteredArticles">

                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>{{article.id }}</td>
                <td>
                    <div v-for="author in article.authorList">{{author.surname}}</div>
                </td>
                <td>{{article.language.name}}</td>
                <td>{{article.movement.name}}</td>
                <td><a>
                    <router-link :to="{name: 'article-details', params: {article_id: article.id}}">{{ article.title }}
                    </router-link>
                </a></td>
                <td>{{article.titleRus }}</td>
                <td>{{ formatDate(article.date) }}</td>
                <td>{{article.description }}</td>
                <td>{{article.url }}</td>
                <!--                <td>-->
                <!--                    <div v-for="link in article.linkList">{{link.content}}</div>-->
                <!--                </td>-->
                <td>
                    <div v-for="hashtag in article.hashtagList">{{hashtag.content}}</div>
                </td>

                <!--                <td>-->
                <!--                    <a>-->
                <!--                        <router-link :to="{name: 'article', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                        </router-link>-->
                <!--                    </a>-->
                <!--                </td>-->


                <td>
                    <a class="btn btn-warning btn-sm mr-2">
                        <router-link :to="{name: 'article-edit', params: {article_id: article.id}}">Edit
                        </router-link>
                    </a>
                    <a class="btn btn-danger btn-sm">
                        <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">Delete
                        </router-link>
                    </a>

                    <!--                    <v-btn :to="{name: 'article-edit', params: {article_id: article.id}}"-->
                    <!--                           light small right bottom fab-->
                    <!--                           class="pink" slot="action">-->

                    <!--                        <v-icon right dark >cloud_upload</v-icon>-->
                    <!--&lt;!&ndash;                        <v-icon small class="mr-2">smiley-neutral-outline</v-icon>&ndash;&gt;-->
                    <!--&lt;!&ndash;                        <v-icon>mdi-watch</v-icon>&ndash;&gt;-->
                    <!--                    </v-btn>-->


                </td>


                <!--                <v-btn color="error" fab small dark-->
                <!--                       @click="yoursFunc()">-->
                <!--                    <v-icon>list</v-icon>-->
                <!--                </v-btn>-->

                <!--                <v-btn to="/panda">Kung Fu</v-btn>-->


                <!--                <template v-slot:item.action="{ item }">-->
                <!--                    <v-icon-->
                <!--                            small-->
                <!--                            class="mr-2"-->
                <!--                            @click="editItem(item)"-->
                <!--                    >-->
                <!--                        edit-->
                <!--                    </v-icon>-->
                <!--                    <v-icon-->
                <!--                            small-->
                <!--                            @click="deleteItem(item)"-->
                <!--                    >-->
                <!--                        delete-->
                <!--                    </v-icon>-->
                <!--                </template>-->


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

    import "vue-scroll-table";

    export default {
        name: 'article',
        data() {
            return {
                articles: [],
                article: {authorList: []},
                authors: [],
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
                    // || article.date.indexOf(this.searchKey) > -1
                    // || article.description.indexOf(this.searchKey) > -1
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

            formatAuthor(article) {   //to delete?
                let surname = '';

                for (let i = 0; i < article.authorList.length; i++) {
                    surname = surname + article.authorList[i].surname;
                    surname = surname + '\\10';
                }
                return surname;
            },

            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
            }

        },
        mounted() {

            // const routes = [
            //     { path: '/panda', component: Panda }
            // ];

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