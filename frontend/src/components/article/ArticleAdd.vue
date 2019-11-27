<template id="article-add">
    <div>
        <h3 class="mb-5">Add New Article</h3>
        <div class="unprotected" v-if="errorFlag">
            <h5>Error: {{errors}}</h5>
        </div>

        <!--       url="http://localhost:8080/api/v1/person"-->
        <!--            http://localhost:8081/article/add-->

        <form>
            <div class="form-group row">
                <label for="add-title" class="col-2 col-form-label">Author</label>
                <div class="col-10">
                    <ul class="list-group" order="1">
                        <li v-for="author in this.article.authorList"
                            class="list-group-item d-flex justify-content-between align-items-center">
                            {{ author.surname }}
                            <span class="close" @click="deleteAuthor(author)">&times;</span>
                        </li>
                    </ul>
                </div>
            </div>

            <div>
                <autocomplete class="mb-3" order="0"
                              url="../api/v1/person"
                              placeholder="Type author name.."
                              anchor="surname"
                              label="writer"
                              :min="2"
                              :on-select="addAuthor">
                </autocomplete>
            </div>
        </form>

        <!--        <form>-->
        <!--        <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="validationServer01">First name</label>
                        <input type="text" class="form-control  is-invalid" id="validationServer01" placeholder="First name"
                               value="Mark" required>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                        <div class="invalid-feedback">
                            Looks not good!
                        </div>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationServer02">Last name</label>
                        <input type="text" class="form-control is-valid" id="validationServer02" placeholder="Last name"
                               value="Otto" required>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationServerUsername">Username</label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend3">@</span>
                            </div>
                            <input type="text" class="form-control is-invalid" id="validationServerUsername"
                                   placeholder="Username" aria-describedby="inputGroupPrepend3" required>
                            <div class="invalid-feedback">
                                Please choose a username.
                            </div>
                        </div>
                    </div>
                </div>-->

        <!--            <div class="form-group">-->
        <!--                <div class="form-check">-->
        <!--                    <input class="form-check-input is-invalid" type="checkbox" value="" id="invalidCheck3" required>-->
        <!--                    <label class="form-check-label" for="invalidCheck3">-->
        <!--                        Agree to terms and conditions-->
        <!--                    </label>-->
        <!--                    <div class="invalid-feedback">-->
        <!--                        You must agree before submitting.-->
        <!--                    </div>-->
        <!--                </div>-->
        <!--            </div>-->
        <!--            <button class="btn btn-primary" type="submit">Submit form</button>-->
        <!--        </form>-->


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

                <!--                <div>-->
                <!--                    <h5>Авторы</h5>-->
                <!--                    &lt;!&ndash;                    <autocomplete :items="customers"&ndash;&gt;-->
                <!--                    &lt;!&ndash;                                  :data-producer="searchAuthor"&ndash;&gt;-->
                <!--                    &lt;!&ndash;                                  filterby="surname"&ndash;&gt;-->
                <!--                    &lt;!&ndash;                                  @change="onChange"&ndash;&gt;-->
                <!--                    &lt;!&ndash;                                  title="Look for a customer"&ndash;&gt;-->
                <!--                    &lt;!&ndash;                                  @selected="customerSelected"/>&ndash;&gt;-->
                <!--                </div>-->


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
                    <input class="form-control" id="add-title" v-model="article.title"
                           required="true"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="add-title-rus" class="col-2 col-form-label">Title Russian</label>
                <div class="col-10">
                    <input class="form-control" id="add-title-rus" v-model="article.titleRus" required/>
                </div>
            </div>

            <div class="form-group row">
                <label for="date-input" class="col-2 col-form-label">Date</label>
                <div class="col-10">
                    <input class="form-control" type="date" id="date-input" v-model="article.date">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-2 col-form-label">Language</label>
                <div class="col-10">
                    <b-form-select v-model="selectedL" class="mb-3" id="language-selection">
                        <option v-for="lang in allLanguages" v-bind:value="lang.id">{{lang.name}}</option>
                    </b-form-select>
                    <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                </div>
            </div>

            <div class="form-group row">
                <label class="col-2 col-form-label">Movement</label>
                <div class="col-10">
                    <b-form-select v-model="selectedM" class="mb-3" id="movement-selection">
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
    require('vue2-autocomplete-js/dist/style/vue2-autocomplete.css')
    import api from "./article-api";
    import hashTagApi from "./hash-tag-api";
    import InputTag from 'vue-input-tag';
    import router from "./../../router";
    import customers from './../../assets/customers';
    import Autocomplete from 'vue2-autocomplete-js'
    import Vue from 'vue';
    // import VoerroTagsInput from '@voerro/vue-tagsinput';
    Vue.component('autocomplete', Autocomplete);
    Vue.component('input-tag', InputTag);

    export default {
        name: 'article-add',
        data() {
            return {
                selectedM: null,
                selectedL: null,

                errorFlag: false,
                errors: [],
                validationErrors: {},

                selectedTags: "",
                hashTag: "",

                hashTags: [],
                tags: [],

                customers: [],

                allLanguages: [],
                allMovements: [],

                linkList: [],
                hashtagList: [],
                article: {authorList: []},

                // theme: 'is-valid',
                // theme: 'is-invalid',
                hasError: false,
            }
        },
        components: {
            Autocomplete
        },
        methods: {
            addAuthor(obj) {
                this.article.authorList.push(obj);
            },
            deleteAuthor(author) {
                for (let i = 0; i < this.article.authorList.length; i++) {
                    if (this.article.authorList[i].id === author.id) {
                        this.article.authorList.splice(i, 1);
                    }
                }
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

            addStatus(id, hasError) {
                document.getElementById(id).classList.remove('is-valid');
                document.getElementById(id).classList.remove('is-invalid');

                if (hasError) {
                    document.getElementById(id).classList.add('is-invalid');
                } else {
                    document.getElementById(id).classList.add('is-valid');
                }
                this.hasError = this.hasError || hasError;
            },

            validDate: function (code) {
                var re = /([12][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))$/; ///digit format "inside", see it while debugging
                // var re = /((0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[0-2])[.][12][0-9]{3})$/;
                var r1 = re.test(code);

                // if (r1)
                //     return true;
                // else
                //     return false;


                 return re.test(code);
            },

            formValidate() {
                this.addStatus('add-title', (!this.article.title));
                if (this.hasError) {
                } else {
                    this.addStatus('add-title-rus', (!this.article.titleRus));
                    if (this.hasError) {
                    } else {
                        this.addStatus('date-input', (!(this.validDate(this.article.date))));
                        if (this.hasError) {
                        } else {
                            this.addStatus('language-selection', (!this.selectedL));
                            if (this.hasError) {
                            } else {
                                this.addStatus('movement-selection', (!this.selectedM));
                            }
                        }
                    }
                }
                if (this.hasError) console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            createArticle() {
                this.article.movement = {
                    "id": this.selectedM
                };
                this.article.language = {
                    "id": this.selectedL
                };

                this.hasError = false;

                if (this.formValidate()) {
                    api.create(this.article, r => {
                        router.push('/article');
                    });
                }
            }
            ,
        },
        mounted() {
            this.customers = customers;
            console.log('mounted');

            // api.findById(this.$route.params.article_id, r => {
            //     this.article = r.data
            // });

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
                }).catch(error => {
                    //this.errors.push(error)
                    console.log(error);
                })
        },
    }
</script>
