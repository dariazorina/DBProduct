<template id="article">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>



        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="greetingsTitle">Wellcome, {{loggedName}}!
                <b-btn v-if="loggedInFlag" size="sm" variant="btn btn-link"  v-b-modal.modal1>Logout</b-btn>
                </p>
                <!-- Modal Component -->
                <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>
            </div>
        </div>




<!--        <div class="logoutNameAndButton">wellcome, {{loggedName}}!-->
<!--            <b-btn v-if="loggedInFlag" size="sm" variant="outline-secondary" v-b-modal.modal1>Logout</b-btn>-->
<!--            &lt;!&ndash; Modal Component &ndash;&gt;-->
<!--            <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>-->
<!--        </div>-->

<!--        <div class="actions">-->
            <a class="btn btn-default">
                <router-link :to="{name: 'article-add'}">Add article</router-link>
            </a>
<!--        </div>-->

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
                <th class='tdAlignLeft'>Id</th>
                <th class='tdAlignLeft'>Authors</th>
                <th class='tdAlignLeft'>Language</th>
                <th class='tdAlignLeft'>Movement</th>
                <th class='tdAlignLeft' style="width:15%">Title</th>
                <th class='tdAlignLeft' style="width:15%">Заголовок</th>
                <th class='tdAlignLeft' data-field="createdAt" data-formatter="dateFormat">Created At</th>
                <th class='tdAlignLeft'>Description</th>
                <th class='tdAlignLeft'>URL</th>
                <!--                <th style="width:10%">Links</th>-->
                <th class='tdAlignLeft'>Hashtags</th>

                <th style="width:10%" class="col-sm-2">Actions</th>
            </tr>
            </thead>
            <tbody>


            <tr v-for="article in filteredArticles">

                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td class='tdAlignLeft'>{{article.id }}</td>
                <td class='tdAlignLeft'>
                    <div v-for="author in article.authorList">{{author.surname}}</div>
                </td>
                <td class='tdAlignLeft'>{{article.language.name}}</td>
                <td class='tdAlignLeft'>{{article.movement.name}}</td>
                <td class='tdAlignLeft'><a>
                    <router-link :to="{name: 'article-details', params: {article_id: article.id}}">{{ article.title }}
                    </router-link>
                </a></td>
                <td class='tdAlignLeft'>{{article.titleRus }}</td>
                <td class='tdAlignLeft'>{{ formatDate(article.date) }}</td>
                <td class='tdAlignLeft'>{{article.description }}</td>
                <td class='tdAlignLeft'>{{article.url }}</td>
                <!--                <td>-->
                <!--                    <div v-for="link in article.linkList">{{link.content}}</div>-->
                <!--                </td>-->
                <td class='tdAlignLeft'>
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
    import router from "./../../router";
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
                loggedInFlag: false,
                loggedName: null,
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
// loadArticles() {
//     api.getAll().then(response => {
//         this.articles = response.data;
//         console.log(response.data)
//     })
//         .catch(error => {
//             this.errors.push(error)
//         })
// },

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
        },
        mounted() {
            this.getLoggedIn();

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