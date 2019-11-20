<template id="article-edit">
    <div>
        <h4>Edit Article</h4>
        <div class="unprotected" v-if="errorFlag">
            <h5>Error: {{errors}}</h5>
        </div>
        <form>


            <!--            <div class="col-md-2">-->
            <!--                <h5>Хэштэги</h5>-->
            <!--                <input type="text" v-model="hashTag"  autocomplete="off" name="hashTag" value=""  @input="loadData()">-->

            <!--                <ul class="list-group">-->
            <!--                    <li v-for="hashtag in hashTags"-->
            <!--                        class="list-group-item d-flex justify-content-between align-items-center">-->
            <!--                        {{ hashtag.content }}-->
            <!--                        <span class="close" @click="deleteHashTag">&times;</span>-->
            <!--                    </li>-->
            <!--                </ul>-->
            <!--            </div>-->
            <div class="col-md-2">
                <div>
                    <h5>Хэштэги</h5>
                    <input-tag v-model="tags"></input-tag>

                </div>

                <div>
                    <h5>Авторы</h5>
                    <autocomplete :items="customers"
                                  :data-producer="searchAuthor"
                                  filterby="surname"
                                  @change="onChange"
                                  title="Look for a customer"
                                  @selected="customerSelected"/>
                </div>


            </div>


            <!--            <div class="row col-md-6">-->
            <!--                <div class="col-md-6 mb-3">-->
            <!--                    <label for="firstName">First name</label>-->
            <!--                    <input type="text" class="form-control" name="firstName" placeholder="" value="" required>-->
            <!--                    <span class="text-danger" v-if="validationErrors.firstName"-->
            <!--                          v-text="validationErrors.firstName"></span>-->
            <!--                </div>-->
            <!--                <div class="col-md-6 mb-3">-->
            <!--                    <label for="lastName">Last name</label>-->
            <!--                    <input type="text" class="form-control" name="lastName" placeholder="" value="" required>-->
            <!--                    <span class="text-danger" v-if="validationErrors.lastName"-->
            <!--                          v-text="validationErrors.lastName"></span>-->
            <!--                </div>-->
            <!--            </div>-->


            <!--        <form @submit="checkForm" action="/country" method="put">-->
            <div class="form-group">
                <label for="edit-title">Title</label>
                <input class="form-control" id="edit-title" v-model="article.title" required/>
            </div>

            <!--            <ul id="example-1">-->
            <!--                <li v-for="hashtag in article.hashtagList">-->
            <!--                    {{ hashtag.content }}-->
            <!--                </li>-->
            <!--            </ul>-->


            <button type="button" @click="checkForm" class="btn btn-primary">Save</button>
            <a class="btn btn-default">
                <router-link to="/article">Cancel</router-link>
            </a>
        </form>
    </div>
</template>

<script>
    import api from "./article-api";
    import hashTagApi from "./hash-tag-api";
    import personApi from "./person-api";
    import InputTag from 'vue-input-tag';
    import router from "./../../router";
    import customers from './../../assets/customers';
    import Autocomplete from './Autocomplete';

    import Vue from 'vue';
    // import VoerroTagsInput from '@voerro/vue-tagsinput';
    //
    // Vue.component('tags-input', VoerroTagsInput);

    Vue.component('input-tag', InputTag);

    export default {
        name: 'article-edit',
        data() {

            return {

                errorFlag: false,
                errors: [],
                selectedTags: "",
                hashTag: "",
                lastName: "",
                validationErrors: {},
                article: {},
                hashTags: [],
                tags: [],
                customers: []
            }
        },
        components: {
            Autocomplete
        },
        methods: {
            customerSelected: function () {

            },

            searchAuthor: function (q, fn) {
                  personApi.search(q, fn);
            },

            onChange: function () {

            },

            loadData: function () {
                if (this.hashTag && this.hashTag.length > 3) {
                    hashTagApi.search(this.hashTag, r => this.hashTags = r.data);

                    console.log("qqqqqqqqqqqq");
                }
            },

            deleteHashTag: function () {
                console.log("ddd");
            },

            checkForm: function (e) {
                console.log('checkForm');
                this.errors = [];
                this.errorFlag = false;

                if (!this.country.name) {
                    this.errorFlag = true;
                    this.errors.push('Укажите имя'); //todo ? оставлять ли (есть проверка на уровне поля)
                }
                if (!this.country.code) {
                    this.errorFlag = true;
                    this.errors.push('Укажите код страны'); //todo //? оставлять ли (есть проверка на уровне поля)
                } else if (!this.validCode(this.country.code)) {
                    this.errorFlag = true;
                    this.errors.push('Укажите код страны из трех цифр');
                }

                if (!this.errors.length) {
                    this.errorFlag = false;
                    this.updateCountry();
                    return true;
                }
                e.preventDefault();
            },

            validCode: function (code) {
                var re = /^[0-9]*$/;
                var r1 = re.test(code);
                var r2 = code.length;

                if (r1)
                    if (r2 == 3) {
                        return true;
                    }

                return false;
                // return (re.test(code)&&this.code.length()==3);
            },


            updateArticle() {
                api.update(this.article.id, this.article, r => {
                    // console.log(r);
                    router.push('/article');
                });
            }

        },

        mounted() {
            this.customers = customers;
            console.log('mounted');
            api.findById(this.$route.params.article_id, r => {
                this.article = r.data
            });
        },
    }
</script>


<!--<style>-->
<!--    @import 'https://cdn.jsdelivr.net/npm/@voerro/vue-tagsinput@2.0.2/dist/style.css';-->
<!--</style>-->