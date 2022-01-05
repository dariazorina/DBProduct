<template id="project-details">
    <div class="noselect" style="background-color: transparent">
        <link href="../dbnm.css" rel="stylesheet"/>
        <iframe id="iframeToDownload" style="display:none;"></iframe>

        <div class="row">
            <!--            ////////////////photo exists!/////////////////////////-->
            <div v-if="avatar.imageBase64" class="col-sm-2" style="background-color: transparent">
                <div id="preview" style="background-color: transparent">
                    <img v-bind:src="avatar.imageBase64" :style="{ width: 250+'px' }"/>
                </div>
            </div>

            <div v-bind:class="[avatar.imageBase64 ? 'col-sm-10' : 'col-sm-12']">
                <div class="details" style="background-color: transparent">
                    <!--        ///////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                                <span class="float-left">Название проекта</span></div>
                        </div>
                        <div class="col-sm-10">
                            <span class="float-left"> {{project.mainTitle}}</span></div>
                    </div>

                    <!--//////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Прочие написания названия</span>
                            </div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">{{project.otherTitle}}</span>
                        </div>
                    </div>

                    <!--        //////////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Годы существования</span></div>
                        </div>
                        <div class="col-sm-10"><span
                                class="float-left"> {{project.foundationYear}} - {{project.closureYear}}</span>
                        </div>
                    </div>


                    <!--/////////////////////////////////////////////////////////////////////////////////////////////-->

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Местонахождение</span></div>
                        </div>
                        <div class="col-sm-10 back1">
                            <div v-for="location in projectLocationEntities">
                                {{createComplexLocationById(location.id)}}<br>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back">
                            <div class="cellTitle"><span class="float-left">Связанные проекты</span></div>
                        </div>
                        <div class="col-sm-10 back"><span class="float-left">
                            <div style="text-align: left" v-for="project in projectProjectEntities"> <a><router-link
                                    :to="{name: 'project-details', params: {project_id: project.itemId}}" target="_blank">
                                {{createComplexProById(project)}}</router-link></a>
                            </div>
                        </span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Теги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                            <div v-for="tag in project.tagList" style="background-color: transparent;">
                                        {{tag.content}}
                                </div></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Связанные организация</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
                            <div v-for="org in projectOrgEntities">
                                <a>
                                    <router-link
                                            :to="{name: 'org-details', params: {org_id: org.id}}"
                                            target="_blank">
                                        {{createComplexOrgById(org.id)}}
                                    </router-link>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные персоны</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
                            <div v-for="prsn in projectPersonEntities">
                                <a>
                                    <router-link
                                            :to="{name: 'person-details', params: {person_id: prsn.id}}"
                                            target="_blank">
                                        {{createComplexPersonById(prsn.id)}}
                                    </router-link>
                                </a>
                            </div>

                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Связанные материалы</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
                            <!--                            <div v-for="art in org.articleList">-->
                            <div v-for="art in projectArticleEntities">
                                <a>
                                    <router-link :to="{name: 'article-details', params: {article_id: art.id}}"
                                                 target="_blank">
                                        {{createComplexArticleById(art.id)}}<br></router-link>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные события</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
                            <div>
                                <!--                                <a v-for="prsn in orgPersonEntities">{{createComplexPersonById(prsn.id)}}<br></a>-->
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10"
                             style="text-align: left; background-color: transparent; padding-bottom: 0">
                            <span v-html="project.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                            <div style="text-align: left" v-for="ht in project.hashtagList">{{ht}}
                            </div></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Links</span></div>
                        </div>
                        <div class="col-sm-10" style="background-color:transparent;">
                            <div class="linkButton" style="background-color: transparent">
                                <div v-for="link in project.linkList" style="background-color: transparent;">
                                    <a class="btn btn-link"
                                       style="font-size: small; background-color: transparent; text-align: left; padding-top: 2px; padding-bottom: 2px"
                                       :href="link.content" target="_blank">
                                        {{link.content}}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div v-if="uploadedFiles.length > 0"
                         style="background-color: transparent; margin: 10px 10px 10px 0px ">
                        <file-attachment @getAttachment="getAttachment"
                                         @downloadAttachment="downloadAttachment"
                                         :userName="loggedName"
                                         :already-uploaded-files="uploadedFiles"
                                         :is-details-mode="true"/>
                    </div>

                </div>
            </div>
        </div>
        <div class="col-md-12 col-md-offset-4 column" style="background-color: transparent">
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link to="/project">Обратно к списку проектов</router-link>
            </a>
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link :to="{name: 'project-add', params: {project_id: project.id}}">Редактировать
                </router-link>
            </a>
        </div>

    </div>
</template>

<style lang="scss">
    @import '../dbnm.css';
</style>
<style>
    .back1 {
        background-color: lavender;
    }

    .back2 {
        background-color: pink;
    }
</style>

<script>
    import api from "./../project/project-api";
    import apiOrg from "./../org/org-api";
    import apiPerson from "./../person/person-api";
    import apiLocation from "./../country/country-api";
    import apiArticle from "./../article/article-api";
    // import apiIsource from "./../country/country-api";
    import apiAttachment from "./../attachment-api";
    import FileAttachment from "../components/FileAttachment";

    import "vue-scroll-table";

    export default {
        name: 'project-details',
        components: {
            FileAttachment,
        },
        data() {
            return {
                project: {
                    hashtagList: [], linkList: [], locationList: [], orgList: [], personList: [],
                    articleList: [], tagList: [], movementList: [], eventList: []
                },

                projectLocationIds: [], //before request
                projectLocationEntities: [], //after request

                projectPersonIds: [], //before request
                projectPersonEntities: [], //after request

                projectOrgIds: [], //before request
                projectOrgEntities: [], //after request

                projectProjectIds: [],
                projectProjectEntities: [],

                projectArticleIds: [], //before request
                projectArticleEntities: [], //after request

                projectEventIds: [], //before request
                projectEventEntities: [], //after request

                uploadedFiles: [],
                loggedInFlag: false,  //todo to remove
                loggedName: null,    //should be file's author, not logged user

                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,

                avatar: {
                    image: null,
                    imageUrl: null,
                    imageBase64: null
                },
            }
        },

        methods: {
            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/project/downloadAttachment?entityId=' + this.project.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                console.log("get attachm");
                apiAttachment.previewAttachment('project', this.project.id, file.id);
            },


            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },


            createComplexOrgById(id) {
                let currentOrg = this.projectOrgEntities.find(x => x.id === id);
                let connection = this.project.orgList.find(x => x.itemId === id);
                let result;

                console.log("createComplexOrgById createComplexOrgById createComplexOrgById", currentOrg);

                if (currentOrg != null) {
                    result = currentOrg.content;

                    if (this.isObjectValidAndNotEmpty(currentOrg.connection))
                        result += "/ " + currentOrg.connection;

                    if (this.isObjectValidAndNotEmpty(currentOrg.comment))
                        result += "/ " + currentOrg.comment;
                }

                return result;
            },

            createComplexProById(pro) {
                let result = '';

                if (pro != null) {
                    result = pro.name;
                    if (this.isObjectValidAndNotEmpty(pro.connection))
                        result += "/ " + pro.connection;

                    if (this.isObjectValidAndNotEmpty(pro.comment))
                        result += "/ " + pro.comment;

                    result += " - ";
                    result += (pro.isParent) ? "родитель" : "потомок";
                }
                return result;
            },

            createComplexArticleById(id) {
                let currentArt = this.projectArticleEntities.find(x => x.id === id);
                let connection = this.project.articleList.find(x => x.itemId === id);
                let result;

                if (currentArt != null) {
                    result = currentArt.content;
                }

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },

            goURL(url) {
                location.href = url;
            },

            createComplexLocationById(id) {
                let currentLocation = this.projectLocationEntities.find(x => x.id === id);
                let result = currentLocation.content;

                // console.log("00000000000000000000", currentPerson);

                let connection = this.project.locationList.find(x => x.itemId === id);
                // console.log("1111111111", connection);

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },

            createComplexPersonById(id) {
                let currentPerson = this.projectPersonEntities.find(x => x.id === id);
                console.log("ID", id, this.projectPersonEntities, currentPerson);

                let result = currentPerson.content;

                //let priorSNP = currentPerson.snpList.find(x => x.priority === 1);
                // result = priorSNP.surname;
                let connection = this.project.personList.find(x => x.itemId === id);
                console.log("CONNECTION", connection);

                if (this.isObjectValidAndNotEmpty(connection.connection)) {
                    result += " / " + connection.connection;
                }

                if (this.isObjectValidAndNotEmpty(connection.comment)) {
                    result += "/ " + connection.comment;
                }
                result += "\n";
                return result;
            },

            isObjectValidAndNotEmpty(obj) {
                // console.log("VALID", obj);
                if (typeof obj === 'undefined' || obj === null || obj.length === 0) {
                    return false;
                }
                return true;
            },
        },
        mounted() {

            this.getLoggedIn();

            // apiAttachment.getAttachmentPhoto('project', this.$route.params.project_id, r => {
            //     console.log("R DATA", r);
            //     this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;
            // });
            //
            // apiAttachment.getAttachments('project', this.$route.params.project_id, r => {
            //     console.log("project attachments", r.data);
            //     for (let i = 0; i < r.data.length; i++) {
            //         this.uploadedFiles.push(r.data[i]);
            //     }
            // });

            api.findById(this.$route.params.project_id, r => {
                this.project = r.data;
                console.log("_____project_________________", r.data);

                for (let j = 0; j < this.project.locationList.length; j++) {
                    this.projectLocationIds.push(this.project.locationList[j].itemId);
                    //console.log("+", j);
                }

                apiLocation.getLocationsByIds(this.projectLocationIds, response => {
                    this.projectLocationEntities = response.data;
                    //console.log("getLocationsByIds getLocationsByIds getLocationsByIds", this.personLocationEntities);
                });

                for (let j = 0; j < this.project.personList.length; j++) {
                    this.projectPersonIds.push(this.project.personList[j].itemId);
                    console.log("PRSN project", this.project.personList, this.projectPersonIds);
                }

                apiPerson.getPersonsByIds(this.projectPersonIds, response => {
                    this.projectPersonEntities = response.data;
                    console.log("persperspers", this.projectPersonEntities);

                });

                for (let j = 0; j < this.project.orgList.length; j++) {
                    this.projectOrgIds.push(this.project.orgList[j].itemId);
                    console.log("*************************+", j, this.project.orgList[j].itemId);
                }

                apiOrg.getOrgsByIds(this.projectOrgIds, response => {
                    this.projectOrgEntities = response.data;
                    console.log("perspeORGrspers", this.projectOrgEntities);

                });

                api.getProjectsByIdsAndSymmetrically(this.project.id, response => {
                    this.projectProjectEntities = response.data;
                    // console.log("```````````````````````````````````````````apiOrga", this.projectProjectIds, this.projectProjcetEntities);
                });

                for (let j = 0; j < this.project.articleList.length; j++) {
                    this.projectArticleIds.push(this.project.articleList[j].itemId);
                    //console.log("+", j);
                }

                apiArticle.getMaterialsByIds(this.projectArticleIds, response => {
                    this.projectArticleEntities = response.data;
                    console.log("apiArt", this.projectArticleEntities);
                });

            });
        },
    }
</script>
