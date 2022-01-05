<template id="article-details">
    <div class="noselect" style="background-color: transparent">
        <link href="../dbnm.css" rel="stylesheet"/>
        <iframe id="iframeToDownload" style="display:none;"></iframe>

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
                    <!--                    <span class="float-left" style="background-color: #b5d592">-->
                    <div class="linkButton" style="background-color: transparent">
                        <!--                            <span class="float-left" style="background-color: darkcyan">-->
                        <div v-for="link in article.linkList" style="background-color: transparent;">
                            <a class="btn btn-link"
                               style="font-size: small; background-color: transparent; text-align: left; padding-top: 2px; padding-bottom: 2px"
                               :href="link.content" target="_blank">
                                {{link.content}}
                            </a>

<!--                            <button class="btn btn-link" style="font-size: small" @click="goURL(link.content)">-->
<!--                                {{link.content}}-->
<!--                            </button>-->
                        </div>
                        <!--                            </span>-->
                    </div>
                    <!--                    </span>-->
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
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Язык</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left"> {{article.language.name}} </span>
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
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle">
                        <span class="float-left">Лица</span>
                    </div>
                </div>

                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left">
                     <div v-for="author in article.personList"><a><router-link :to="{name: 'person-details', params: {person_id:author.itemId}}" target="_blank">
                         {{createComplexNameByEntity(author)}}</router-link></a>
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
                    <div v-for="location in article.locationList">
                            {{createComplexNameByEntity(location)}}
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
                        <div v-for="org in article.orgList"><a><router-link
                              :to="{name: 'org-details', params: {org_id: org.itemId}}" target="_blank">
                                 {{ createComplexNameByEntity(org)}}</router-link>
                            </a>
                        </div>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">
                        <span class="float-left">Проекты</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left">
                    <div v-for="pro in article.projectList"><a><router-link
                            :to="{name: 'project-details', params: {project_id: pro.itemId}}" target="_blank">
                                 {{ createComplexNameByEntity(pro)}}</router-link>
                            </a>
                        </div>
                </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">События(?)</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;">
                    <span class="float-left">
<!--                        <div v-for="org in articleOrgEntities"><a><router-link-->
<!--                                :to="{name: 'org-details', params: {org_id: org.id}}" target="_blank">-->
<!--                                 {{ createComplexOrgById(org.id)}}</router-link>-->
<!--                            </a>-->
<!--                        </div>-->
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
                               :to="{name: 'article-details', params: {article_id: material.itemId}}" target="_blank">
                              {{createComplexNameByEntity (material)}} </router-link></a>
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
                <div class="col-sm-10"><span class="float-left"> {{this.article.status}}</span>
                </div>
            </div>

            <div class="row" v-if="article.miscellany!=null">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Комментарии</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left"> {{article.miscellany}}</span>
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

        <!--        <div class="offset-sm-1 col-sm-3">-->
        <!--            <button type="button" class="btn btn-info">-->
        <!--                <router-link to="/article" style="color: white; font-size: medium">Обратно к списку материалов-->
        <!--                </router-link>-->
        <!--            </button>-->
        <!--        </div>-->

        <div class="col-md-12 col-md-offset-4 column" style="background-color: transparent">
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link to="/article">Обратно к списку материалов</router-link>
            </a>
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link :to="{name: 'article-add', params: {article_id: article.id}}">Редактировать
                </router-link>
            </a>
        </div>
    </div>

</template>

<style lang="scss">
    @import '../dbnm.css';
</style>

<script>
    import api from "./article-api";
    import apiAttachment from "./../attachment-api";
    import moment from "moment";
    import apiStatus from "./../status-api";

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
                    projectList: [],
                    language: {},
                    movement: {},
                    hashtagList: [],
                    linkList: [],
                    locationList: [],
                    orgList: []
                },

                articleMaterialEntities: [], //after request

                uploadedFiles: [],
                loggedInFlag: false,  //todo to remove
                loggedName: null,    //should be file's author, not logged user

                statusList: [],
            }
        },
        methods: {
            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/article/downloadAttachment?entityId=' + this.article.id + '&id=' + file.id;
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

            // goURL(url) {
            //     location.href = url;
            //     // console.log("123");
            // },

            isObjectValidAndNotEmpty(obj) {
                // console.log("VALID", obj);
                return !(typeof obj === 'undefined' || obj === null || obj.length === 0);
            },

            createComplexNameByEntity(currentEntity) {
                let result = currentEntity.name;

                if (this.isObjectValidAndNotEmpty(currentEntity.connection))
                    result += "/ " + currentEntity.connection;

                if (this.isObjectValidAndNotEmpty(currentEntity.comment))
                    result += "/ " + currentEntity.comment;

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
                            result += "/ " + valueOrig;
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

                apiStatus.getAllStatuses(response => {
                    this.statusList = response.data;
                    console.log("STATUS LIST", this.statusList);
                });

                api.getMaterialsByIdsAndSymmetrically(this.article.id, response => {
                    this.articleMaterialEntities = response.data;
                    // console.log("```````````````````````````````````````````apiMater", this.articleMaterialEntities);
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
