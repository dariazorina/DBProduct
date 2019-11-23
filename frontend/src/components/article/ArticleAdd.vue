<template id="article-add">
    <div>
        <h4>Add New Article</h4>
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


                <div>
                    <h5>Линки))</h5>
                    <input-tag v-model="tags"></input-tag>

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

            <div class="form-group row">
                <label for="add-title" class="col-2 col-form-label">Title</label>
                <div class="col-10">
                    <input class="form-control" id="add-title" v-model="article.title" required/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-title-rus" class="col-2 col-form-label">Title Russian</label>
                <div class="col-10">
                    <input class="form-control" id="add-title-rus" v-model="article.titleRus" required/>
                </div>
            </div>

            <div class="form-group row">
                <label for="example-date-input" class="col-2 col-form-label">Date</label>
                <div class="col-10">
                    <input class="form-control" type="date" id="example-date-input" v-model="article.date">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-2 col-form-label">Language</label>
                <div class="col-10">
                    <b-form-select v-model="selectedL" class="mb-3">
                        <option v-for="lang in allLanguages" v-bind:value="lang.id">{{lang.name}}</option>
                    </b-form-select>
<!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                </div>
            </div>

            <!--            <div class="form-group row">-->
            <!--                <label for="add-mov" class="col-2 col-form-label">Select Movement</label>-->
            <!--                <div class="col-10">-->
            <!--                    <select class="form-control" id="add-mov" @click="movementSelected" name="article.movement.id">-->
            <!--                        &lt;!&ndash;                    <select v-model="selected">&ndash;&gt;-->
            <!--                        &lt;!&ndash;                        <option disabled value="">Выберите один из вариантов</option>&ndash;&gt;-->
            <!--                        <option v-for="mov in allMovements">{{mov.id}}:{{mov.name}}</option>-->
            <!--                    </select>-->
            <!--                    &lt;!&ndash;                    <span>Выбрано: {{ selected }}</span>&ndash;&gt;-->
            <!--                </div>-->
            <!--            </div>-->


            <div class="form-group row">
                <label class="col-2 col-form-label">Movement</label>
                <div class="col-10">
                    <b-form-select v-model="selectedM" class="mb-3">
                        <option v-for="mov in allMovements" v-bind:value="mov.id">{{ mov.name }}</option>
                    </b-form-select>
<!--                    <div class="mb-3">SelEcted: <strong>{{ selectedM }}</strong></div>-->
                </div>
            </div>

            <div class="form-group row">
                <label for="add-descr" class="col-2 col-form-label">Description</label>
                <div class="col-10">
                    <input class="form-control" id="add-descr" v-model="article.description" required/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-url" class="col-2 col-form-label">URL</label>
                <div class="col-10">
                    <input class="form-control" id="add-url" type="url" v-model="article.url" required/>
                </div>
            </div>

            <!--            <ul id="example-1">-->
            <!--                <li v-for="hashtag in article.hashtagList">-->
            <!--                    {{ hashtag.content }}-->
            <!--                </li>-->
            <!--            </ul>-->

            <button type="button" @click="createArticle" class="btn btn-primary">Save</button>
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
        name: 'article-add',
        data() {
            return {
                selectedM: null,
                selectedL: null,
                errorFlag: false,
                errors: [],
                selectedTags: "",
                hashTag: "",
                validationErrors: {},
                article: {},
                hashTags: [],
                tags: [],
                customers: [],
                allLanguages: [],
                allMovements: [],
                linkList: [],
                hashtagList: [],
                authorList: {}
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
            // checkForm: function (e) {
            //     console.log('checkForm');
            //     this.errors = [];
            //     this.errorFlag = false;
            //
            //     if (!this.country.name) {
            //         this.errorFlag = true;
            //         this.errors.push('Укажите имя'); //todo ? оставлять ли (есть проверка на уровне поля)
            //     }
            //     if (!this.country.code) {
            //         this.errorFlag = true;
            //         this.errors.push('Укажите код страны'); //todo //? оставлять ли (есть проверка на уровне поля)
            //     } else if (!this.validCode(this.country.code)) {
            //         this.errorFlag = true;
            //         this.errors.push('Укажите код страны из трех цифр');
            //     }
            //
            //     if (!this.errors.length) {
            //         this.errorFlag = false;
            //         this.updateCountry();
            //         return true;
            //     }
            //     e.preventDefault();
            // },
            // validCode: function (code) {
            //     var re = /^[0-9]*$/;
            //     var r1 = re.test(code);
            //     var r2 = code.length;
            //
            //     if (r1)
            //         if (r2 == 3) {
            //             return true;
            //         }
            //     return false;
            //     // return (re.test(code)&&this.code.length()==3);
            // },

            createArticle() {
                this.article.movement = {
                     "id": this.selectedM
                };
                this.article.language = {
                    "id": this.selectedL
                };
                // this.article.linkList = "[]",
                // this.article.authorList = "[]",
                // this.article.hashtagList = "[]",

                api.create(this.article, r => {
                    router.push('/article');
                });
            },
        },
        mounted() {
            this.customers = customers;
            console.log('mounted');

            api.findById(this.$route.params.article_id, r => {
                this.article = r.data
            });

            api.getAllLanguages().then(response => {
                this.allLanguages = response.data;
                console.log(response.data)
            }),
                // .catch(error => {
                //     this.errors.push(error)
                // }),  //todo? is it nesessary?

                api.getAllMovements().then(response => {
                    this.allMovements = response.data;
                    console.log(response.data)
                })
            // .catch(error => {
            //     this.errors.push(error)
            // })
        },
    }
</script>
