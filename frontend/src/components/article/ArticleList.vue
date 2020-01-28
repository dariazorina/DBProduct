<template id="article">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="greetingsTitle">Welcome, {{loggedName}}!
                    <button type="button" v-if="loggedInFlag" class="btnXSmall btn-link" v-b-modal.modal1>Logout
                    </button>
                    <!--                <b-btn v-if="loggedInFlag" size="xs" variant="btn btn-link  btn-xs"  v-b-modal.modal1>Logout</b-btn>-->
                </p>
                <!-- Modal Component -->
                <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>
            </div>
        </div>
        <!--        <div class="logoutNameAndButton">welcome, {{loggedName}}!-->
        <!--            <b-btn v-if="loggedInFlag" size="sm" variant="outline-secondary" v-b-modal.modal1>Logout</b-btn>-->
        <!--            &lt;!&ndash; Modal Component &ndash;&gt;-->
        <!--            <b-modal id="modal1" title="Are you sure you want to log-off?" @ok="logout"></b-modal>-->
        <!--        </div>-->

        <a class="btn btn-default">
            <router-link :to="{name: 'article-add'}">Add article</router-link>
        </a>

        <div class="filters row">
            <div class="form-group col-sm-3">
                <input placeholder="Search disable temporarily" v-model="searchKey" class="form-control"
                       id="search-element" requred/>
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


        <table class="redTable">
            <!--        <table class="table">-->
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <th class='tdTitle'>Id</th>
                <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat">Date</th>
                <th class='tdTitle'>Language</th>
                <th class='tdTitle'>Hashtags</th>
                <th class='tdTitle'>Authors</th>

                <!--                <th class='tdAlignCell'>Movement</th>-->
                <th class='tdTitle' style="width:15%">Заголовок</th>
                <th class='tdAlignCell' style="width:15%">Title</th>
                <!--                <th class='tdTitle'>URL</th>-->

                <th class='tdTitle'>Description</th>
                <th class='tdTitle'>Miscellany</th>
                <!--                <th style="width:10%">Links</th>-->

                <th style="width:10%" class="tdTitle">Actions</th>
            </tr>
            </thead>
            <tbody>


            <tr v-for="article in articles">
                <!--            <tr v-for="article in filteredArticles">-->

                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>
                    <div class="ListCellStyleHot"><span id=t>{{article.id }}</span></div>
                </td>
                <td>
                    <div class="ListCellStyle">{{ formatDate(article.date) }}</div>
                </td>
                <td>
                    <div class="ListCellStyleHot">{{article.language.name}}</div>
                </td>
                <td>
                    <div class="ListCellStyle"> <!--bgcolor="#7fffd4"-->
                        <div v-for="hashtag in article.hashtagList">{{hashtag.content}}</div>
                    </div>
                </td>
                <td>
                    <div class="ListCellStyleHot">
                        <div v-for="author in article.authorList">{{author.surname}}</div>
                    </div>
                </td>

                <!--                <td class='tdAlignLeft'>{{ article.movement.name}}</td>-->
                <!--                <td class='tdAlignLeft'><a>-->
                <!--                    <router-link :to="{name: 'article-details', params: {article_id: article.id}}">{{ article.title }}-->
                <!--                    </router-link>-->
                <!--                </a></td>-->
                <td>
                    <div class="ListCellStyle">{{article.titleRus }}</div>
                </td>
                <td>
                    <div class="ListCellStyleHot">{{article.title }}</div>
                </td>
                <td>
                    <div class="ListCellStyle"> {{article.description }}</div>
                </td> <!--todo dots? if cut-->
                <td>
                    <div class="ListCellStyleHot">{{article.miscellany }}</div>
                </td>

                <!--                <td>-->
                <!--                    <div v-for="link in article.linkList">{{link.content}}</div>-->
                <!--                </td>-->


                <td>
                    <!--                    <div class="ListCellStyleForButton">-->
                    <a class="btn btn-warning btn-sm mr-2">
                        <router-link :to="{name: 'article-edit', params: {article_id: article.id}}">Edit
                        </router-link>
                    </a>
                    <a class="btn btn-danger btn-sm">
                        <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">Delete
                        </router-link>
                    </a>
                    <!--                    </div>-->

                    <!--                    <v-btn :to="{name: 'article-edit', params: {article_id: article.id}}"-->
                    <!--                           light small right bottom fab-->
                    <!--                           class="pink" slot="action">-->

                    <!--                        <v-icon right dark >cloud_upload</v-icon>-->
                    <!--&lt;!&ndash;                        <v-icon small class="mr-2">smiley-neutral-outline</v-icon>&ndash;&gt;-->
                    <!--&lt;!&ndash;                        <v-icon>mdi-watch</v-icon>&ndash;&gt;-->
                    <!--                    </v-btn>-->


                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet"/>

<style lang="scss">
    @import '../dbnm.css';
</style>

<style>

    .ListCellStyle {
        /*padding: 5px; !* Поля вокруг текста *!*/
        text-align: left;
        font-size: small;
        /*height: 25px;*/
        max-height: 85px;
        overflow: hidden;
        /*border: solid 1px;*/
        /*border-color: #eceaea;*/
        /*padding: 5px 15px 5px 10px; !*top right bottom left*!*/

    }

    .ListCellStyleHot {
        /*padding: 5px; !* Поля вокруг текста *!*/
        text-align: left;
        font-size: small;
        max-height: 85px;
        /*height: 25px;*/
        overflow: hidden;
        /*border: solid 1px;*/
        /*border-color: #eceaea;*/
        /*padding: 5px 15px 5px 10px; !*top right bottom left*!*/
        /*background-color: #eceaea;*/

    }

    .ListCellStyleForButton {
        border: solid 1px;
        border-color: #eceaea;
        /*height: 85px;*/
        overflow: hidden;

    }

    /*.layer12 {*/
    /*    background: #fc3; !* Цвет фона *!*/
    /*    border: 2px solid black; !* Параметры рамки *!*/
    /*    padding: 5px; !* Поля вокруг текста *!*/
    /*}*/

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
            // filteredArticles() {
            //return this.articles.filter((article) => {
            // return article.title.indexOf(this.searchKey) > -1

// || article.date.indexOf(this.searchKey) > -1
// || article.description.indexOf(this.searchKey) > -1
            // })
            // }
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

            // formatAuthor(article) {   //to delete?
            //     let surname = '';
            //
            //     for (let i = 0; i < article.authorList.length; i++) {
            //         surname = surname + article.authorList[i].surname;
            //         surname = surname + '\\10';
            //     }
            //     return surname;
            // },

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