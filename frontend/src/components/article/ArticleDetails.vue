<template id="article-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Article Details</p>
            </div>
        </div>


        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">URL</span></div>
            </div>


            <!--            <input type="button" onclick="location.href='http://google.com'" value="${article.url}" />-->
            <!--            <input type="button" onclick="location.href='http://google.com'" value="Go to Google" />-->

            <div class="col-sm-10" style="background-color:lavender;"> <span class="float-left"><div class ="linkButton"><span class="float-left">
                <button class="btn btn-link" @click="goURL(article.url)">{{article.url}}</button> </span></div></span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" >
                <div class="cellTitle"><span class="float-left">Язык</span></div>
            </div>
            <div class="col-sm-10" ><span class="float-left"> {{article.language.name}} </span>
            </div>
        </div>


        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Дата</span></div>
            </div>
            <div class="col-sm-10" style="background-color:lavender;"><span class="float-left"> {{ formatDate(article.date) }}</span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2">
                <div class="cellTitle">
                    <span class="float-left">Авторы</span>
                </div>
            </div>
            <div class="col-sm-10"><span class="float-left">
                <div v-for="author in article.authorList">{{author.surname}}</div>
                </span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2"  style="background-color:lavender;">
                <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                    <span class="float-left">Заголовок</span></div>
            </div>
            <div class="col-sm-10"  style="background-color:lavender;">
                <span class="float-left"> {{article.title}}</span></div>
        </div>

        <div class="row">
            <div class="col-sm-2" >
                <div class="cellTitle">
                    <span class="float-left">Заголовок на русском</span></div>
            </div>
            <div class="col-sm-10" ><span class="float-left"> {{article.titleRus}}</span>
            </div>
        </div>


        <div class="row">
            <div class="col-sm-2"  style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Описание</span></div>
            </div>
            <div class="col-sm-10"  style="background-color:lavender;"><span class="float-left"> {{article.description}}</span>
            </div>
        </div>



        <!--        <div class="form-group row">-->
        <!--            <label for="date-input" class="col-2 col-form-label">Date</label>-->
        <!--            <div class="col-10">-->
        <!--                <input class="form-control" type="date" id="date-input" v-model="article.date">-->
        <!--            </div>-->
        <!--        </div>-->


        <div class="row">
            <div class="col-sm-2" >
                <div class="cellTitle"><span class="float-left">Хештеги</span></div>
            </div>
            <div class="col-sm-10" ><span class="float-left">
                <div class="leftAlignForDetails" v-for="ht in article.hashtagList">{{ht.content}}</div></span>
<!--                <div class="leftAlignForDetails" v-for="ht in article.hashtagList"><span class="linkButton">{{ht.content}}</span></div></span>-->
            </div>
        </div>

<!--        <div class="row">-->
<!--            <div class="col-sm-2" style="background-color:lavender;">-->
<!--                <div class="cellTitle"><span class="float-left">Movement</span></div>-->
<!--            </div>-->

<!--            <div class="col-sm-10" style="background-color:lavender;"><span-->
<!--                    class="float-left">{{article.movement.name}} </span></div>-->
<!--        </div>-->



<!--        <div class="row">-->
<!--            <div class="col-sm-2" style="background-color:lavender;">-->
<!--                <div class="cellTitle"><span class="float-left">Links</span></div>-->
<!--            </div>-->
<!--            <div class="col-sm-10" style="background-color:lavender;"><span class="float-left">-->
<!--                <div class ="linkButton" v-for="link in article.linkList"><span class="float-left"><button class="btn btn-link"-->
<!--                                                                                       @click="goURL(link.content)">{{link.content}}</button></span></div></span>-->
<!--            </div>-->
<!--        </div>-->

        <div class="row">
            <div class="col-sm-2"  style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Комментарии</span></div>
            </div>
            <div class="col-sm-10"  style="background-color:lavender;"><span class="float-left"> {{article.miscellany}}</span>
            </div>
        </div>

        <div class="my-md-4">
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link to="/article">Back To Article List</router-link>
            </a>
        </div>
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
        name: 'article-details',
        data() {
            return {
                articles: [],
                article: {authorList: [], language: {}, movement: {}, hashtagList: [], linkList: []},
                authors: [],
                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
            }
        },
        // computed: {
        //     filteredArticles() {
        //         return this.articles.filter((article) => {
        //             return article.title.indexOf(this.searchKey) > -1
        //                 || article.date.indexOf(this.searchKey) > -1
        //                 || article.description.indexOf(this.searchKey) > -1
        //         })
        //     }
        // },
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

            // formatAuthor(article) {
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

            goURL(url) {
                location.href = url;
                // console.log("123");
            },

        },
        mounted() {
            api.findById(this.$route.params.article_id, r => {
                this.article = r.data;
                // console.log(r.data);
                this.article.date = this.formatDate(this.article.date);
            });

            // document.addEventListener("DOMContentLoaded", function (event) {
            //     // this.addLink("URL", "http://facebook.com")
            //
            //     let element = document.getElementById("URL");
            //     element.href = "http://facebook.com";
            // });

        },

        dateFormat(value, row, index) {  //todo
            moment(this.article.date).format('DD/MM/YYYY').then(response => {
                this.article.date = response.data;
            })
        }
    }
</script>
