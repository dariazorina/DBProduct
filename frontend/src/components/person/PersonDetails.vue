<template id="person-details">
    <div>
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
                                <span class="float-left">ФИО</span></div>
                        </div>
                        <div class="col-sm-10">
                            <span class="float-left"> {{getSNPWithPriority(1)}}</span></div>
                    </div>

                    <!--//////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Прочие написания ФИО</span>
                            </div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">{{getSNPWithPriority(0)}}</span>
                        </div>
                    </div>

                    <!--        //////////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Годы жизни</span></div>
                        </div>
                        <div class="col-sm-10"><span
                                class="float-left"> {{person.birthYear}} - {{person.deathYear}}</span>
                        </div>
                    </div>


                    <!--/////////////////////////////////////////////////////////////////////////////////////////////-->

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Местонахождение</span></div>
                        </div>
                        <!--                        <div class="col-sm-10 back1"><span-->
                        <!--                                class="float-left"> {{showCountry(person.country)}} </span>-->
                        <!--                        </div>-->
                        <div class="col-sm-10 back1">
                            <div v-for="location in personLocationEntities">
                                {{createComplexLocationById(location.id)}}<br>
                            </div>
                        </div>
                    </div>

                    <!--                    <div class="row">-->
                    <!--                        <div class="col-sm-2">-->
                    <!--                            <div class="cellTitle"><span class="float-left">Город</span></div>-->
                    <!--                        </div>-->
                    <!--                        <div class="col-sm-10"><span class="float-left"> {{person.settlement}} </span>-->
                    <!--                        </div>-->
                    <!--                    </div>-->

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Организация / Должность</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
                            <!--                            <div>-->
                            <!--                                <a v-for="org in person.orgList">{{showOrgAndPosition(org.itemId)}}<br></a>-->
                            <!--                            </div>-->

                            <div v-for="org in person.orgList">
                                <a>
                                    <router-link
                                            :to="{name: 'org-details', params: {org_id: org.itemId}}" target="_blank">
                                        {{showOrgAndPosition(org.itemId)}}
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
                            <!--                            <div>-->
                            <!--                                <a v-for="prsn in personPersonEntities">{{createComplexPersonById(prsn)}}<br></a>-->
                            <!--                            </div>-->

                            <div v-for="prsn in personPersonEntities">
                                <a>
                                    <router-link
                                            :to="{name: 'person-details', params: {person_id: prsn.itemId}}"
                                            target="_blank">
                                        {{createComplexPersonById(prsn)}}
                                    </router-link>
                                </a>
                            </div>

                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10"
                             style="text-align: left; background-color: transparent; padding-bottom: 0">
                            <span v-html="person.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                            <div style="text-align: left" v-for="ht in person.hashtagList">{{ht}}
                            </div></span>
                        </div>
                    </div>

                    <!--        <div class="row">-->
                    <!--            <div class="col-sm-2" style="background-color:lavender;">-->
                    <!--                <div class="cellTitle"><span class="float-left">Movement</span></div>-->
                    <!--            </div>-->

                    <!--            <div class="col-sm-10" style="background-color:lavender;"><span-->
                    <!--                    class="float-left">{{person.movement.name}} </span></div>-->
                    <!--        </div>-->


                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Links</span></div>
                        </div>

                        <!--                        <div class="col-sm-10">-->
                        <!--                            <span class="float-left">-->
                        <!--                                <div class="linkButton" v-for="link in person.linkList">-->
                        <!--                                    <span class="float-left">-->
                        <!--                                        <button class="btn btn-link" style="font-size: small"-->
                        <!--                                                @click="goURL(link.content)" target="_blank">{{link.content}}</button>-->
                        <!--                                    </span>-->
                        <!--                                </div>-->
                        <!--                            </span>-->
                        <!--                        </div>-->

                        <div class="col-sm-10" style="background-color:transparent;">
                            <div class="linkButton" style="background-color: transparent">
                                <div v-for="link in person.linkList" style="background-color: transparent;">
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
                        <div class="col-sm-10 back1"><span class="float-left"> {{person.status}}</span>
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
                <router-link to="/person">Обратно к списку персон</router-link>
            </a>
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link :to="{name: 'person-add', params: {person_id: person.id}}">Редактировать
                </router-link>
            </a>
        </div>
    </div>

    <!--        <v-btn text icon x-small>-->
    <!--            <a>-->
    <!--                <router-link :to="{name: 'person-add', params: {person_id: person.id}}">-->
    <!--                    <v-icon style="color: green">mdi-pencil</v-icon>-->
    <!--                </router-link>-->
    <!--            </a>-->
    <!--        </v-btn>-->
    <!--    </div>-->
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
    import api from "./person-api";
    import apiOrg from "./../org/org-api";
    import apiLocation from "./../country/country-api";
    import apiAttachment from "./../attachment-api";
    import FileAttachment from "../components/FileAttachment";
    // import apiStatus from "./../status-api";

    import "vue-scroll-table";

    export default {
        name: 'person-details',
        components: {
            FileAttachment,
        },
        data() {
            return {
                className: null,
                persons: [],
                person: {
                    hashtagList: [], linkList: [], locationList: [], orgList: [], personList: [],
                    isourceList: [], movementList: [], snpList: []
                },

                personLocationIds: [], //before request
                personLocationEntities: [], //after request

                personPersonIds: [], //before request
                personPersonEntities: [], //after request

                personOrgIds: [], //before request
                personOrgEntities: [], //after request

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
                document.getElementById('iframeToDownload').src = '/api/v1/person/downloadAttachment?entityId=' + this.person.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                console.log("get attachm");
                apiAttachment.previewAttachment('person', this.person.id, file.id);
            },


            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },

            showOrgAndPosition(id) {

                let result = '';
                let currentOrg = this.personOrgEntities.find(x => x.id === id);
                if (currentOrg != null) {
                    result = currentOrg.content;
                }
//                console.log("00000000000000000000", currentOrg, id, this.personOrgEntities);

                let connection = this.person.orgList.find(x => x.itemId === id);
                if (connection != null) {
                    console.log("1111111111", connection);

                    if (this.isObjectValidAndNotEmpty(connection.connection))
                        result += "/ " + connection.connection;

                    if (this.isObjectValidAndNotEmpty(connection.comment))
                        result += "/ " + connection.comment;
                }
                return result;
            },

            goURL(url) {
                location.href = url;
            },

            getSNPWithPriority(priority) {
                let sss = '';// = this.person.snpList.find(x => x.priority === 1).surname;

                for (let i = 0; i < this.person.snpList.length; i++) {
                    //console.log("SNPLIST i", this.person.snpList[i], i, this.person.snpList[i].priority);

                    if (this.person.snpList[i].priority === priority) {
                        console.log("SSS length sss priority", sss.length, sss, priority);
                        if (sss.length !== 0) {
                            sss += "/ " + this.person.snpList[i].surname + " " + this.person.snpList[i].name + " ";
                        } else {
                            sss = this.person.snpList[i].surname + " " + this.person.snpList[i].name + " ";
                        }

                        if (this.person.snpList[i].patronymic != null)
                            sss += this.person.snpList[i].patronymic + " ";
                        //console.log("******^^^^^^^^^^^^^****", sss);
                    }
                }
                return sss;
            },

            createComplexLocationById(id) {
                let currentLocation = this.personLocationEntities.find(x => x.id === id);
                let result = currentLocation.content;
                let connection = this.person.locationList.find(x => x.itemId === id);

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += "/ " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += "/ " + connection.comment;

                return result;
            },

            createComplexPersonById(prsn) {
                let result = '';
                let currentPerson = prsn;//this.personPersonEntities.find(x => x.itemId === id);
                //console.log("IIIIIIIIIIIIIIIIIID", id, this.personPersonEntities, currentPerson);

                if (currentPerson != null) {
                    result = currentPerson.name;

                    if (this.isObjectValidAndNotEmpty(currentPerson.connection))
                        result += "/ " + currentPerson.connection;

                    if (this.isObjectValidAndNotEmpty(currentPerson.comment))
                        result += "/ " + currentPerson.comment;
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
            this.className = "col-sm-2";
            this.getLoggedIn();

            // apiStatus.getAllStatuses(response => {
            //     this.statusList = response.data;
            //     console.log("STATUS LIST", this.statusList);
            // });

            apiAttachment.getAttachmentPhoto('person', this.$route.params.person_id, r => {
                console.log("R DATA", r);
                this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;
            });

            apiAttachment.getAttachments('person', this.$route.params.person_id, r => {
                console.log("person attachments", r.data);
                for (let i = 0; i < r.data.length; i++) {
                    this.uploadedFiles.push(r.data[i]);
                }
            });


            api.findById(this.$route.params.person_id, r => {
                this.person = r.data;
                //console.log("_____person__________________", r.data);

                for (let j = 0; j < this.person.locationList.length; j++) {
                    this.personLocationIds.push(this.person.locationList[j].itemId);
                    //console.log("+", j);
                }
                //console.log("PRSN locations", this.personLocationIds);

                apiLocation.getLocationsByIds(this.personLocationIds, response => {
                    this.personLocationEntities = response.data;
                    //console.log("getLocationsByIds getLocationsByIds getLocationsByIds", this.personLocationEntities);
                });

                for (let j = 0; j < this.person.personList.length; j++) {
                    this.personPersonIds.push(this.person.personList[j].itemId);
                    // console.log("PRSN person", this.person.personList, this.personPersonIds);
                }

                api.getPersonsByIdsAndSymmetrically(this.person.id, this.personPersonIds, response => {
                    this.personPersonEntities = response.data;
                    //console.log("``````````````````persperspers", this.personPersonEntities);

                });

                for (let j = 0; j < this.person.orgList.length; j++) {
                    this.personOrgIds.push(this.person.orgList[j].itemId);
                    //  console.log("+ * % ^ & U OIUOI OI", this.personOrgIds);
                }

                apiOrg.getOrgsByIds(this.personOrgIds, response => {
                    this.personOrgEntities = response.data;
                    //console.log("apiOrga", this.personOrgEntities);
                });
            });
        },
    }
</script>
