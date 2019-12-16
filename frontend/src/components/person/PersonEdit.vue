<!--<template id="person-edit">-->
<!--    <div>-->
<!--        <h4>Edit Person</h4>-->
<!--        <div class="unprotected" v-if="errorFlag">-->
<!--            <h5>Error: {{errors}}</h5>-->
<!--        </div>-->
<!--        <form>-->


<!--            &lt;!&ndash;            <div class="col-md-2">&ndash;&gt;-->
<!--            &lt;!&ndash;                <h5>Хэштэги</h5>&ndash;&gt;-->
<!--            &lt;!&ndash;                <input type="text" v-model="hashTag"  autocomplete="off" name="hashTag" value=""  @input="loadData()">&ndash;&gt;-->

<!--            &lt;!&ndash;                <ul class="list-group">&ndash;&gt;-->
<!--            &lt;!&ndash;                    <li v-for="hashtag in hashTags"&ndash;&gt;-->
<!--            &lt;!&ndash;                        class="list-group-item d-flex justify-content-between align-items-center">&ndash;&gt;-->
<!--            &lt;!&ndash;                        {{ hashtag.content }}&ndash;&gt;-->
<!--            &lt;!&ndash;                        <span class="close" @click="deleteHashTag">&times;</span>&ndash;&gt;-->
<!--            &lt;!&ndash;                    </li>&ndash;&gt;-->
<!--            &lt;!&ndash;                </ul>&ndash;&gt;-->
<!--            &lt;!&ndash;            </div>&ndash;&gt;-->
<!--            <div class="col-md-2">-->
<!--                <div>-->
<!--                    <h5>Хэштэги</h5>-->
<!--                    <input-tag v-model="tags"></input-tag>-->

<!--                </div>-->

<!--                <div>-->
<!--                    <h5>Авторы</h5>-->
<!--                    <autocomplete :items="customers"-->
<!--                                  :data-producer="searchAuthor"-->
<!--                                  filterby="surname"-->
<!--                                  @change="onChange"-->
<!--                                  title="Look for a customer"-->
<!--                                  @selected="customerSelected"/>-->
<!--                </div>-->


<!--            </div>-->


<!--            &lt;!&ndash;            <div class="row col-md-6">&ndash;&gt;-->
<!--            &lt;!&ndash;                <div class="col-md-6 mb-3">&ndash;&gt;-->
<!--            &lt;!&ndash;                    <label for="firstName">First name</label>&ndash;&gt;-->
<!--            &lt;!&ndash;                    <input type="text" class="form-control" name="firstName" placeholder="" value="" required>&ndash;&gt;-->
<!--            &lt;!&ndash;                    <span class="text-danger" v-if="validationErrors.firstName"&ndash;&gt;-->
<!--            &lt;!&ndash;                          v-text="validationErrors.firstName"></span>&ndash;&gt;-->
<!--            &lt;!&ndash;                </div>&ndash;&gt;-->
<!--            &lt;!&ndash;                <div class="col-md-6 mb-3">&ndash;&gt;-->
<!--            &lt;!&ndash;                    <label for="lastName">Last name</label>&ndash;&gt;-->
<!--            &lt;!&ndash;                    <input type="text" class="form-control" name="lastName" placeholder="" value="" required>&ndash;&gt;-->
<!--            &lt;!&ndash;                    <span class="text-danger" v-if="validationErrors.lastName"&ndash;&gt;-->
<!--            &lt;!&ndash;                          v-text="validationErrors.lastName"></span>&ndash;&gt;-->
<!--            &lt;!&ndash;                </div>&ndash;&gt;-->
<!--            &lt;!&ndash;            </div>&ndash;&gt;-->


<!--            &lt;!&ndash;        <form @submit="checkForm" action="/country" method="put">&ndash;&gt;-->
<!--            <div class="form-group">-->
<!--                <label for="edit-title">Title</label>-->
<!--                <input class="form-control" id="edit-title" v-model="article.title" required/>-->
<!--            </div>-->

<!--            &lt;!&ndash;            <ul id="example-1">&ndash;&gt;-->
<!--            &lt;!&ndash;                <li v-for="hashtag in article.hashtagList">&ndash;&gt;-->
<!--            &lt;!&ndash;                    {{ hashtag.content }}&ndash;&gt;-->
<!--            &lt;!&ndash;                </li>&ndash;&gt;-->
<!--            &lt;!&ndash;            </ul>&ndash;&gt;-->


<!--            <button type="button" @click="checkForm" class="btn btn-primary">Save</button>-->
<!--            <a class="btn btn-default">-->
<!--                <router-link to="/article">Cancel</router-link>-->
<!--            </a>-->
<!--        </form>-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--    import api from "./article-api";-->
<!--    import hashTagApi from "./hash-tag-api";-->
<!--    import personApi from "./person-api";-->
<!--    import InputTag from 'vue-input-tag';-->
<!--    import router from "./../../router";-->
<!--    import customers from './../../assets/customers';-->
<!--    import Autocomplete from './Autocomplete';-->

<!--    import Vue from 'vue';-->
<!--    // import VoerroTagsInput from '@voerro/vue-tagsinput';-->
<!--    //-->
<!--    // Vue.component('tags-input', VoerroTagsInput);-->

<!--    Vue.component('input-tag', InputTag);-->

<!--    export default {-->
<!--        name: 'article-edit',-->
<!--        data() {-->

<!--            return {-->

<!--                errorFlag: false,-->
<!--                errors: [],-->
<!--                selectedTags: "",-->
<!--                hashTag: "",-->
<!--                lastName: "",-->
<!--                validationErrors: {},-->
<!--                article: {},-->
<!--                hashTags: [],-->
<!--                tags: [],-->
<!--                customers: []-->
<!--            }-->
<!--        },-->
<!--        components: {-->
<!--            Autocomplete-->
<!--        },-->
<!--        methods: {-->
<!--            customerSelected: function () {-->

<!--            },-->

<!--            searchAuthor: function (q, fn) {-->
<!--                  personApi.search(q, fn);-->
<!--            },-->

<!--            onChange: function () {-->

<!--            },-->

<!--            loadData: function () {-->
<!--                if (this.hashTag && this.hashTag.length > 3) {-->
<!--                    hashTagApi.search(this.hashTag, r => this.hashTags = r.data);-->

<!--                    console.log("qqqqqqqqqqqq");-->
<!--                }-->
<!--            },-->

<!--            deleteHashTag: function () {-->
<!--                console.log("ddd");-->
<!--            },-->

<!--            checkForm: function (e) {-->
<!--                console.log('checkForm');-->
<!--                this.errors = [];-->
<!--                this.errorFlag = false;-->

<!--                if (!this.country.name) {-->
<!--                    this.errorFlag = true;-->
<!--                    this.errors.push('Укажите имя'); //todo ? оставлять ли (есть проверка на уровне поля)-->
<!--                }-->
<!--                if (!this.country.code) {-->
<!--                    this.errorFlag = true;-->
<!--                    this.errors.push('Укажите код страны'); //todo //? оставлять ли (есть проверка на уровне поля)-->
<!--                } else if (!this.validCode(this.country.code)) {-->
<!--                    this.errorFlag = true;-->
<!--                    this.errors.push('Укажите код страны из трех цифр');-->
<!--                }-->

<!--                if (!this.errors.length) {-->
<!--                    this.errorFlag = false;-->
<!--                    this.updateCountry();-->
<!--                    return true;-->
<!--                }-->
<!--                e.preventDefault();-->
<!--            },-->

<!--            validCode: function (code) {-->
<!--                var re = /^[0-9]*$/;-->
<!--                var r1 = re.test(code);-->
<!--                var r2 = code.length;-->

<!--                if (r1)-->
<!--                    if (r2 == 3) {-->
<!--                        return true;-->
<!--                    }-->

<!--                return false;-->
<!--                // return (re.test(code)&&this.code.length()==3);-->
<!--            },-->


<!--            updateArticle() {-->
<!--                api.update(this.article.id, this.article, r => {-->
<!--                    // console.log(r);-->
<!--                    router.push('/article');-->
<!--                });-->
<!--            }-->

<!--        },-->

<!--        mounted() {-->
<!--            this.customers = customers;-->
<!--            console.log('mounted');-->
<!--            api.findById(this.$route.params.article_id, r => {-->
<!--                this.article = r.data-->
<!--            });-->
<!--        },-->
<!--    }-->
<!--</script>-->


<!--&lt;!&ndash;<style>&ndash;&gt;-->
<!--&lt;!&ndash;    @import 'https://cdn.jsdelivr.net/npm/@voerro/vue-tagsinput@2.0.2/dist/style.css';&ndash;&gt;-->
<!--&lt;!&ndash;</style>&ndash;&gt;-->