<template id="event-details">
    <div class="noselect" style="background-color: transparent">
        <link href="../dbnm.css" rel="stylesheet"/>
        <iframe id="iframeToDownload" style="display:none;"></iframe>

        <div class="row">
            <div class="col-sm-12">
                <div class="details" style="background-color: transparent">
                    <!--        ///////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                                <span class="float-left">Заголовок события</span></div>
                        </div>
                        <div class="col-sm-10">
                            <span class="float-left"> {{event.title}}</span></div>
                    </div>

                    <!--//////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Прочие написания названия</span>
                            </div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">{{event.type}}</span>
                        </div>
                    </div>

                    <!--        //////////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Даты события</span></div>
                        </div>
                        <div class="col-sm-10"><span
                                class="float-left"> {{formatDate(event.startDate)}} - {{formatDate(event.endDate)}}</span>
                        </div>
                    </div>


                    <!--/////////////////////////////////////////////////////////////////////////////////////////////-->

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Местонахождение</span></div>
                        </div>
                        <div class="col-sm-10 back1">
                            <div v-for="location in event.locationList">
                                {{ createComplexNameByEntity(location)}}<br>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back">
                            <div class="cellTitle"><span class="float-left">Связанные проекты</span></div>
                        </div>
                        <div class="col-sm-10 back"><span class="float-left">
                            <div style="text-align: left" v-for="project in event.projectList"> <a><router-link
                                    :to="{name: 'project-details', params: {project_id: project.itemId}}"
                                    target="_blank">
                                {{ createComplexNameByEntity(project)}}</router-link></a>
                            </div>
                        </span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Теги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                            <div v-for="tag in event.tagList" style="background-color: transparent;">
                                        {{tag.content}}
                                </div></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Связанные организация</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
                            <div v-for="org in event.orgList">
                                <a>
                                    <router-link
                                            :to="{name: 'org-details', params: {org_id: org.itemId}}"
                                            target="_blank">
                                        {{ createComplexNameByEntity(org)}}
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
                            <div v-for="prsn in event.personList">
                                <a>
                                    <router-link
                                            :to="{name: 'person-details', params: {person_id: prsn.itemId}}"
                                            target="_blank">
                                        {{createComplexNameByEntity(prsn)}}
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
                            <div v-for="art in event.articleList">
                                <a>
                                    <router-link :to="{name: 'article-details', params: {article_id: art.itemId}}"
                                                 target="_blank">
                                        {{createComplexNameByEntity(art)}}<br></router-link>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные события</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
                            <div style="text-align: left" v-for="ev in event.eventList"> <a><router-link
                                    :to="{name: 'event-details', params: {event_id: ev.itemId}}"
                                    target="_blank">
                                {{ createComplexNameByEntity(ev)}}</router-link></a>
                            </div>                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10"
                             style="text-align: left; background-color: transparent; padding-bottom: 0">
                            <span v-html="event.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                            <div style="text-align: left" v-for="ht in event.hashtagList">{{ht}}
                            </div></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Links</span></div>
                        </div>
                        <div class="col-sm-10" style="background-color:transparent;">
                            <div class="linkButton" style="background-color: transparent">
                                <div v-for="link in event.linkList" style="background-color: transparent;">
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
                <router-link to="/event">Обратно к списку проектов</router-link>
            </a>
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link :to="{name: 'event-add', params: {event_id: event.id}}">Редактировать
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
    import api from "./../event/event-api";
    import apiOrg from "./../org/org-api";
    import apiPerson from "./../person/person-api";
    import apiLocation from "./../country/country-api";
    import apiArticle from "./../article/article-api";
    import apiAttachment from "./../attachment-api";
    import FileAttachment from "../components/FileAttachment";

    import "vue-scroll-table";
    import moment from "moment";

    export default {
        name: 'event-details',
        components: {
            FileAttachment,
        },
        data() {
            return {
                event: {
                    hashtagList: [], linkList: [], locationList: [], orgList: [], projectList: [], personList: [],
                    articleList: [], tagList: [], movementList: [], eventList: []
                },
                uploadedFiles: [],
                loggedInFlag: false,  //todo to remove
                loggedName: null,    //should be file's author, not logged user

                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
            }
        },

        methods: {
            formatDate(date) {
                if (date !== null)
                    return moment(date).format('YYYY-MM-DD');
            },

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/event/downloadAttachment?entityId=' + this.event.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                console.log("get attachm");
                apiAttachment.previewAttachment('event', this.event.id, file.id);
            },


            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },

            goURL(url) {
                location.href = url;
            },

            createComplexNameByEntity(currentEntity) {
                let result = currentEntity.name;

                if (this.isObjectValidAndNotEmpty(currentEntity.connection))
                    result += "/ " + currentEntity.connection;

                if (this.isObjectValidAndNotEmpty(currentEntity.comment))
                    result += "/ " + currentEntity.comment;

                if (typeof currentEntity.isParent !== 'undefined') {
                    result += " - ";
                    result += (currentEntity.isParent) ? "родитель" : "потомок";
                }
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

            api.findById(this.$route.params.event_id, r => {
                this.event = r.data;
                console.log("_____event_________________", r.data);

                api.getEventsByIdsAndSymmetrically(this.event.id, response => {
                    this.event.eventList = response.data;
                    // console.log("```````````````````````````````````````````apiOrga", this.projectProjectIds, this.projectProjcetEntities);
                });

                apiAttachment.getAttachments('event', this.$route.params.event_id, r => {
                    console.log("event attachments", r.data);
                    for (let i = 0; i < r.data.length; i++) {
                        this.uploadedFiles.push(r.data[i]);
                    }
                });
            });
        },
    }
</script>
