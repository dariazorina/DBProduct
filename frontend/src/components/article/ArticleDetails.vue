<template id="article-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <iframe id="iframeToDownload" style="display:none;"></iframe>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Article Details</p>
            </div>
        </div>

        <div class="details">

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">Заголовок</span></div>
                </div>
                <div class="col-sm-10">
                    <span class="float-left"> <div><b>{{createComplexTitle()}}</b></div></span></div>
            </div>

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Links</span></div>
                </div>


                <!--            <input type="button" onclick="location.href='http://google.com'" value="${article.url}" />-->
                <!--            <input type="button" onclick="location.href='http://google.com'" value="Go to Google" />-->

                <div class="col-sm-10" style="background-color:lavender;">
                    <span class="float-left">
                        <div class="linkButton">
                            <span class="float-left">
                                <div v-for="link in article.linkList">
                                <button class="btn btn-link" style="font-size: small" @click="goURL(link.content)">
                                    {{link.content}}
                                </button>
                                </div>
                            </span>
                        </div>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Тип материала</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> <div v-if="article.mtype!=null">{{article.mtype.content}}</div> </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2"  style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Язык</span></div>
                </div>
                <div class="col-sm-10"  style="background-color:lavender;"><span class="float-left"> {{article.language.name}} </span>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Дата</span></div>
                </div>
                <div class="col-sm-10">
                    <span class="float-left"> {{ article.date }}</span>
                </div>
            </div>




            <div class="row">
                <div class="col-sm-2"  style="background-color:lavender;">
                    <div class="cellTitle">
                        <span class="float-left">Авторы</span>
                    </div>
                </div>

                <div class="col-sm-10"  style="background-color:lavender;"><span class="float-left">
                     <div v-for="author in articlePersonEntities">
                            {{createComplexPersonById(author.id)}}
                    </div>
                </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">
                        <span class="float-left">Локации</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left">
                    <div v-for="location in articleLocationEntities">
                            {{createComplexLocationById(location.id)}}
                    </div></span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">Организации</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;">
                                       <span class="float-left">
                                           <div v-for="org in articleOrgEntities">
                                                {{createComplexOrgById(org.id)}}
                                           </div>
                                       </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left">
                <div style="text-align: left" v-for="ht in article.hashtagList">{{ht}}</div></span>
                    <!--                <div class="leftAlignForDetails" v-for="ht in article.hashtagList"><span class="linkButton">{{ht.content}}</span></div></span>-->
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Материалы</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left">
                    <div v-for="material in articleMaterialEntities">
                       <a><router-link
                               :to="{name: 'article-details', params: {article_id: material.id}}" target="_blank">
                              {{createComplexMaterialById(material.id)}} </router-link></a>

                        <!--                        <a v-on:click.prevent="updateNav(material.id)">-->
                        <!--                            {{createComplexMaterialById(material.id)}}-->
                        <!--                        </a>-->
                        <!--                        <a :href="$router.resolve({name: 'article-details', params: {article_id: material.id}}).href">link</a>-->
                    </div>
                </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Описание</span></div>
                </div>
                <div class="col-sm-10"><span v-html="article.description"></span>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Текст</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span v-html="article.text"></span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Статус</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> {{getStatusName()}}</span>
                </div>
            </div>

            <div class="row" v-if="article.miscellany!=null">
                <div class="col-sm-2"  style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Комментарии</span></div>
                </div>
                <div class="col-sm-10"  style="background-color:lavender;"><span class="float-left"> {{article.miscellany}}</span>
                </div>
            </div>

            <div v-if="uploadedFiles.length > 0">
                <file-attachment @getAttachment="getAttachment"
                                 @downloadAttachment="downloadAttachment"
                                 :userName="loggedName"
                                 :already-uploaded-files="uploadedFiles"
                                 :is-details-mode="true"/>
            </div>
        </div>

        <div class="offset-sm-1 col-sm-3">
            <button type="button" class="btn btn-info">
                <router-link to="/article" style="color: white; font-size: medium">Back To Article List</router-link>
            </button>
        </div>
    </div>

</template>

<style lang="scss">
    @import '../dbnm.css';
</style>

<script>
    import api from "./article-api";
    import apiPerson from "./../person/person-api";
    import apiLocation from "./../country/country-api";
    import apiOrg from "./../org/org-api";
    import apiAttachment from "./../attachment-api";
    import moment from "moment";

    import "vue-scroll-table";
    import FileAttachment from "../components/FileAttachment";


    export default {
        components: {
            FileAttachment,
            // ckeditor: CKEditor.component
        },
        name: 'article-details',
        data() {
            return {
                article: {
                    personList: [],
                    language: {},
                    movement: {},
                    hashtagList: [],
                    linkList: [],
                    locationList: [],
                    orgList: []
                },

                articlePersonIds: [], //before request
                articlePersonEntities: [], //after request

                articleLocationIds: [], //before request
                articleLocationEntities: [], //after request

                articleOrgIds: [], //before request
                articleOrgEntities: [], //after request

                articleMaterialIds: [], //before request
                articleMaterialEntities: [], //after request

                uploadedFiles: [],
                loggedInFlag: false,  //todo to remove
                loggedName: null,    //should be file's author, not logged user

                statusOptions: [
                    {text: 'В работе', value: 0},
                    {text: 'Внесены', value: 1},
                    {text: 'На доработке', value: 2},
                    {text: 'Отработаны', value: 3},
                ],
            }
        },
        methods: {
            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/article/downloadAttachment?entityId=' + this.article.id + '&id=' + file.id;
            },

            getStatusName() {
                let status = this.statusOptions.find(x => x.value === this.article.status);
                return status.text;
            },

            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },

            updateNav(id) {  //todo  invest. of route opening in the same view
                // https://forum.vuejs.org/t/router-link-to-the-same-page-needs-to-force-refresh/54622/2
                //  https://forum.vuejs.org/t/router-link-to-the-same-page-needs-to-force-refresh/54622

                let data = Object.assign({}, this.$route.query);
                console.log("updNav", data);
                // data['scrollto'] = query;
                // outer-link :to="{name: 'article-details', params: {article_id: material.id}
                this.$router.push({name: 'article-details', params: {article_id: id}});
                // document.location.reload();
            },

            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
            },

            goURL(url) {
                location.href = url;
                // console.log("123");
            },

            isObjectValidAndNotEmpty(obj) {
                // console.log("VALID", obj);
                return !(typeof obj === 'undefined' || obj === null || obj.length === 0);
            },

            createComplexPersonById(id) {
                let result = '';
                let currentPerson = this.articlePersonEntities.find(x => x.id === id);

                // console.log("00000000000000000000", currentPerson);

                let valueOrig = currentPerson.surname;// + " " + currentPerson.name;
                let valueRus = currentPerson.surnameRus;// + " " + currentPerson.nameRus;

                let connection = this.article.personList.find(x => x.itemId === id);
                // console.log("1111111111", connection);

                if (this.isObjectValidAndNotEmpty(currentPerson.name)) {
                    valueOrig += " " + currentPerson.name;
                }

                if (this.isObjectValidAndNotEmpty(currentPerson.nameRus)) {
                    valueRus += " " + currentPerson.nameRus;
                }

                if (this.isObjectValidAndNotEmpty(valueRus)) {
                    result = valueRus;
                    if (this.isObjectValidAndNotEmpty(valueOrig)) {
                        if (valueRus.localeCompare(valueOrig) !== 0)
                            result += "/ " + valueOrig;
                    }
                } else if (this.isObjectValidAndNotEmpty(valueOrig))
                    result += valueOrig;

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },


            createComplexLocationById(id) {
                let currentLocation = this.articleLocationEntities.find(x => x.id === id);
                let result = currentLocation.country;

                // console.log("00000000000000000000", currentPerson);

                let connection = this.article.locationList.find(x => x.itemId === id);
                // console.log("1111111111", connection);


                if (this.isObjectValidAndNotEmpty(currentLocation.region)) {
                    result += ", " + currentLocation.region;
                }
                if (this.isObjectValidAndNotEmpty(currentLocation.city)) {
                    result += ", " + currentLocation.city;
                }

                if (this.isObjectValidAndNotEmpty(currentLocation.address)) {
                    result += ", " + currentLocation.address;
                }

                if (this.isObjectValidAndNotEmpty(currentLocation.placement)) {
                    result += ", " + currentLocation.placement;
                }

                if (this.isObjectValidAndNotEmpty(currentLocation.miscellany)) {
                    result += "/ " + currentLocation.miscellany;
                }

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },

            createComplexMaterialById(id) {
                let currentMaterial = this.articleMaterialEntities.find(x => x.id === id);
                let result;

                let connection = this.article.materialList.find(x => x.itemId === id);

                if (this.isObjectValidAndNotEmpty(currentMaterial.title)) {
                    result = currentMaterial.title;
                } else {
                    result = currentMaterial.titleRus;
                }

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },

            createComplexOrgById(id) {
                let currentOrg = this.articleOrgEntities.find(x => x.id === id);
                let connection = this.article.orgList.find(x => x.itemId === id);
                let result;

                if (this.isObjectValidAndNotEmpty(currentOrg.name)) {
                    result = currentOrg.name + "/ " + currentOrg.nameRus;
                } else {
                    result = currentOrg.nameRus;
                }

                if (this.isObjectValidAndNotEmpty(currentOrg.abbr)) {
                    result += "/ " + currentOrg.abbr;
                }

                if (this.isObjectValidAndNotEmpty(currentOrg.abbrRus)) {
                    result += "/ " + currentOrg.abbrRus;
                }

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },

            createComplexTitle() {
                let result = '';
                let valueOrig = this.article.title;
                let valueRus = this.article.titleRus;

                // console.log("COMPLEX TITLE", this.article.title, this.article.titleRus);

                if (this.isObjectValidAndNotEmpty(valueRus)) {
                    result = valueRus;
                    if (this.isObjectValidAndNotEmpty(valueOrig)) {
                        if (valueRus.localeCompare(valueOrig) !== 0)
                            result += " / " + valueOrig;
                    }
                } else if (this.isObjectValidAndNotEmpty(valueOrig))
                    result += valueOrig;

                // console.log("COMPLEX TITLE", result);
                return result;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                // console.log("get attachm");
                apiAttachment.previewAttachment('article', this.article.id, file.id);
            },
        },

        mounted() {
            this.getLoggedIn();
            api.findById(this.$route.params.article_id, r => {
                this.article = r.data;
                console.log("-------------------", this.article);
                this.article.date = this.formatDate(this.article.date);

                for (let j = 0; j < this.article.personList.length; j++) {
                    this.articlePersonIds.push(this.article.personList[j].itemId);
                }

                for (let j = 0; j < this.article.locationList.length; j++) {
                    this.articleLocationIds.push(this.article.locationList[j].itemId);
                }
                console.log("mounted locations", this.articleLocationIds);

                for (let j = 0; j < this.article.orgList.length; j++) {
                    this.articleOrgIds.push(this.article.orgList[j].itemId);
                }
                console.log("mounted org", this.articleOrgIds);

                for (let j = 0; j < this.article.materialList.length; j++) {
                    this.articleMaterialIds.push(this.article.materialList[j].itemId);
                }
                console.log("mounted material", this.articleMaterialIds);

                apiPerson.getPersonsByIds(this.articlePersonIds, response => {
                    this.articlePersonEntities = response.data;
                    console.log("apiPerson", this.articlePersonEntities);
                });

                apiLocation.getLocationsByIds(this.articleLocationIds, response => {
                    this.articleLocationEntities = response.data;
                    console.log("apiLoca", this.articleLocationEntities);
                });

                apiOrg.getOrgsByIds(this.articleOrgIds, response => {
                    this.articleOrgEntities = response.data;
                    console.log("apiOrga", this.articleOrgEntities);
                });

                api.getMaterialsByIds(this.articleMaterialIds, response => {
                    this.articleMaterialEntities = response.data;
                    console.log("apiMater", this.articleMaterialEntities);
                });

                apiAttachment.getAttachments('article', this.article.id, r => {
                    for (let i = 0; i < r.data.length; i++) {
                        this.uploadedFiles.push(r.data[i]);
                    }
                });
            });
        },

        // watch: {
        //     $route(to, from) {
        //         console.log(to, from);
        //     }
        // }

        // https://github.com/vuejs/vue-router/issues/311
        // watch: {
        //     '$route' (to, from) {
        //         if (to.path === '/article/56/details') {
        //             this.$store.dispatch('getAllPromotions')
        //         } else if (to.path === '/promotions/coupon') {
        //             this.$store.dispatch('getAllPromotions', {
        //                 type: 'coupon'
        //             })
        //         }
        //     }
        // }

        // dateFormat(value, row, index) {  //todo
        //     moment(this.article.date).format('DD/MM/YYYY').then(response => {
        //         this.article.date = response.data;
        //     })
        // }
    }
</script>
