<template id="org-details">
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
                                <span class="float-left">Название организации</span></div>
                        </div>
                        <div class="col-sm-10">
                            <span class="float-left"> {{getNameWithPriority(1)}}</span></div>
                    </div>

                    <!--//////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Прочие написания названия</span>
                            </div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">{{getNameWithPriority(0)}}</span>
                        </div>
                    </div>

                    <!--        //////////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Годы существования</span></div>
                        </div>
                        <div class="col-sm-10"><span
                                class="float-left"> {{org.foundationYear}} - {{org.closureYear}}</span>
                        </div>
                    </div>


                    <!--/////////////////////////////////////////////////////////////////////////////////////////////-->

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Местонахождение</span></div>
                        </div>
                        <div class="col-sm-10 back1">
                            <div v-for="location in org.locationList">
                                {{createComplexNameByEntity(location)}}<br>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Связанные организация</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
<!--                            <div>-->
<!--                                <a v-for="org in this.orgOrgEntities">{{createComplexOrgById(org)}}<br></a>-->
                                <!--                                <a v-for="org in org.orgList">{{createComplexOrgById(org.itemId)}}<br></a>-->

                                <div v-for="org in this.org.orgList">
                                    <div v-if="org.isInternal===true">
                                    <a><router-link
                                            :to="{name: 'org-details', params: {org_id: org.itemId}}" target="_blank">
                                        {{createComplexNameByEntity(org)}} </router-link></a> {{"(внутренняя)"}}
                                    </div>

                                    <div v-else>
                                        <a><router-link
                                                :to="{name: 'org-details', params: {org_id: org.itemId}}" target="_blank">
                                            {{createComplexNameByEntity(org)}} </router-link></a>
                                    </div>

                                    <!--                        <a v-on:click.prevent="updateNav(material.id)">-->
                                    <!--                            {{createComplexMaterialById(material.id)}}-->
                                    <!--                        </a>-->
                                    <!--                        <a :href="$router.resolve({name: 'article-details', params: {article_id: material.id}}).href">link</a>-->
                                </div>

<!--                            </div>-->
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные персоны</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
<!--                            <div>-->
<!--                                <a v-for="prsn in orgPersonEntities">{{createComplexPersonById(prsn.id)}}<br></a>-->
<!--                            </div>-->

                            <div v-for="prsn in org.personList">
                                <a><router-link
                                        :to="{name: 'person-details', params: {person_id: prsn.itemId}}" target="_blank">
                                    {{createComplexNameByEntity(prsn)}} </router-link></a>
                            </div>

                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Связанные материалы</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
<!--                            <div v-for="art in org.articleList">-->
                            <div v-for="art in org.articleList">
                                <a><router-link :to="{name: 'article-details', params: {article_id: art.itemId}}" target="_blank">
                                    {{createComplexNameByEntity(art)}}<br></router-link></a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные электронные ресурсы</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
                            <div>
                                <!--                                <a v-for="prsn in orgPersonEntities">{{createComplexPersonById(prsn.id)}}<br></a>-->
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Проекты</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left">
                    <div v-for="pro in org.projectList"><a><router-link
                            :to="{name: 'project-details', params: {project_id: pro.itemId}}" target="_blank">
                                 {{ createComplexNameByEntity(pro)}}</router-link>
                            </a>
                        </div>
                </span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                                <span class="float-left">События(?)</span></div>
                        </div>
                        <div class="col-sm-10 back1">
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
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10"
                             style="text-align: left; background-color: transparent; padding-bottom: 0">
                            <span v-html="org.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                            <div style="text-align: left" v-for="ht in org.hashtagList">{{ht}}
                            </div></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Links</span></div>
                        </div>

<!--                        <div class="col-sm-10">-->
<!--                            <span class="float-left">-->
<!--                                <div class="linkButton" v-for="link in org.linkList">-->
<!--                                    <span class="float-left">-->
<!--                                        <button class="btn btn-link" style="font-size: small"-->
<!--                                                @click="goURL(link.content)">{{link.content}}</button>-->
<!--                                    </span>-->
<!--                                </div>-->
<!--                            </span>-->
<!--                        </div>-->

                        <div class="col-sm-10" style="background-color:transparent;">
                            <div class="linkButton" style="background-color: transparent">
                                <div v-for="link in org.linkList" style="background-color: transparent;">
                                    <a class="btn btn-link"
                                       style="font-size: small; background-color: transparent; text-align: left; padding-top: 2px; padding-bottom: 2px"
                                       :href="link.content" target="_blank">
                                        {{link.content}}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Статус</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left"> {{org.status}}</span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back">
                            <div class="cellTitle"><span class="float-left">Тип</span></div>
                        </div>
                        <div class="col-sm-10 back"><span class="float-left"> <div v-if="org.type!=null">{{org.type.content}}</div></span>
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
<!--        <div class="my-md-4">-->
<!--            <a class="btn btn-outline-info btn-sm mr-2">-->
<!--                <router-link to="/org">Обратно к списку организаций</router-link>-->
<!--            </a>-->
<!--        </div>-->

        <div class="col-md-12 col-md-offset-4 column" style="background-color: transparent">
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link to="/org">Обратно к списку организаций</router-link>
            </a>
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link :to="{name: 'org-add', params: {org_id: org.id}}">Редактировать
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
    import apiOrg from "./../org/org-api";
    import apiPerson from "./../person/person-api";
    import apiLocation from "./../country/country-api";
    import apiArticle from "./../article/article-api";
    // import apiIsource from "./../country/country-api";
    import apiAttachment from "./../attachment-api";
    import FileAttachment from "../components/FileAttachment";

    import "vue-scroll-table";

    export default {
        name: 'org-details',
        components: {
            FileAttachment,
        },
        data() {
            return {
                orgs: [],
                org: {
                    hashtagList: [], linkList: [], locationList: [], orgList: [], personList: [], projectList: [],
                    articleList: [], isourceList: [], movementList: [], snpList: []
                },

                // orgOrgEntities: [], //after request

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

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/org/downloadAttachment?entityId=' + this.org.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                console.log("get attachm");
                apiAttachment.previewAttachment('org', this.org.id, file.id);
            },


            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },

            goURL(url) {
                location.href = url;
            },

            getNameWithPriority(priority) {
                // console.log("*****getNameWithPriority***********getNameWithPriority*********", this.org, this.org.nameList);
                let res = '';
                if (typeof this.org.nameList === 'undefined' || this.org.nameList === null || this.org.nameList.length === 0) {
                    return res;
                } else {
                    for (let i = 0; i < this.org.nameList.length; i++) {

                        if (this.org.nameList[i].priority === priority) {
                            // console.log("------------------SSS length sss priority", priority);

                            if (res.length !== 0) {
                                res += this.org.nameList[i].name + ", ";
                            } else {
                                res = this.org.nameList[i].name + ", ";
                            }

                            if (this.org.nameList[i].abbr != null)
                                if (this.org.nameList[i].abbr.length > 0)
                                    res += this.org.nameList[i].abbr + "; ";
                            //console.log("******^^^^^^^^^^^^^****", sss);
                        }
                    }
                    // console.log("*************************", sss);
                    return res;
                }
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

            apiOrg.findById(this.$route.params.org_id, r => {
                this.org = r.data;
                console.log("_____org_________________", r.data);

                apiOrg.getOrgsByIdsAndSymmetrically(this.org.id, response => {
                    this.org.orgList = response.data;
                    console.log("```````````````````````````````````````````apiOrga", this.org.orgList);

                    apiAttachment.getAttachmentPhoto('org', this.$route.params.org_id, r => {
                        console.log("R DATA", r);
                        this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;
                    });

                    apiAttachment.getAttachments('org', this.$route.params.org_id, r => {
                        console.log("org attachments", r.data);
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });
                });
            });
        },
    }
</script>
