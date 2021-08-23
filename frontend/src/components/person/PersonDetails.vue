<template id="person-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <iframe id="iframeToDownload" style="display:none;"></iframe>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Person Details</p>
            </div>
        </div>

        <div class="row">
            <!--            ////////////////photo exists!/////////////////////////-->
            <div v-if="avatar.imageBase64" class="col-sm-2" style="background-color: transparent">

                <div id="preview" style="background-color: transparent">
                    <img v-bind:src="avatar.imageBase64" :style="{ width: 250+'px' }"/>
                </div>
            </div>

            <div v-if="avatar.imageBase64" class="col-sm-10">
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
                            <div>
                                <a v-for="test in person.testList">{{showOrgAndPosition(test)}}<br></a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные персоны</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
                            <div>
                                <a v-for="prsn in personPersonEntities">{{createComplexPersonById(prsn.id)}}<br></a>
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

                        <div class="col-sm-10">
                            <span class="float-left">
                                <div class="linkButton" v-for="link in person.linkList">
                                    <span class="float-left">
                                        <button class="btn btn-link" style="font-size: small"
                                                @click="goURL(link.content)">{{link.content}}</button>
                                    </span>
                                </div>
                            </span>
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
            <!--            /////////////////photo is absent////////////////-->
            <div v-else class="col-sm-12">
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

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Организация / Должность</span></div>
                        </div>
                        <div class="col-sm-10" style="text-align: left;">
                            <div>
                                <a v-for="test in person.testList">{{showOrgAndPosition(test)}}<br></a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Связанные персоны</span></div>
                        </div>
                        <div class="col-sm-10 back1" style="text-align: left;">
                            <div>
                                <a v-for="prsn in personPersonEntities">{{createComplexPersonById(prsn.id)}}<br></a>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10" style="text-align: left; padding-bottom: 0">
                            <span v-html="person.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">
                <div style="text-align: left" v-for="ht in person.hashtagList">{{ht}}</div></span>
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

                        <div class="col-sm-10">
                            <span class="float-left">
                                <div class="linkButton" v-for="link in person.linkList">
                                    <span class="float-left">
                                        <button class="btn btn-link" style="font-size: small"
                                                @click="goURL(link.content)">{{link.content}}</button>
                                     </span>
                                 </div>
                             </span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Статус</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left"> {{person.status}}</span>
                        </div>
                    </div>

                    <div v-if="uploadedFiles.length > 0" style="background-color: transparent">
                        <file-attachment @getAttachment="getAttachment"
                                         @downloadAttachment="downloadAttachment"
                                         :userName="loggedName"
                                         :already-uploaded-files="uploadedFiles"
                                         :is-details-mode="true"/>
                    </div>

                </div>
            </div>
        </div>

        <div class="my-md-4">
            <a class="btn btn-outline-info btn-sm mr-2">
                <router-link to="/person">Back To Person List</router-link>
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
                persons: [],
                person: {country: {}, hashtagList: [], linkList: [], locationList: []},

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

                // statusList: [
                //     {id: 1, name: ''},  //to prevent access to undefined list
                //     {id: 2, name: ''},
                //     {id: 3, name: ''},
                //     {id: 4, name: ''}
                // ],
                // statusOptions: [
                //     {text: 'В работе', value: 0},
                //     {text: 'Внесены', value: 1},
                //     {text: 'На доработке', value: 2},
                //     {text: 'Отработаны', value: 3},
                // ],

                avatar: {
                    image: null,
                    imageUrl: null,
                    imageBase64: null
                },
            }
        },

        methods: {
            getSNPWithPriority(priority) {

                //console.log("*************************", this.entries);
                let sss = '';// = this.person.snpList.find(x => x.priority === 1).surname;

                for (let i = 0; i < this.person.snpList.length; i++) {
                    //console.log("SNPLIST i", this.person.snpList[i], i, this.person.snpList[i].priority);

                    if (this.person.snpList[i].priority === priority) {
                        console.log("SNP priority", priority, this.person.snpList[i].surname);

                        if (priority === 1) {
                            this.personAddSurnameTFValues[0] = this.person.snpList[i].surname;
                            this.personAddNameTFValues[0] = this.person.snpList[i].name;
                            if (this.person.snpList[i].patronymic != null)
                                this.personAddPatrTFValues[0] = this.person.snpList[i].patronymic;
                        } else {
                            this.personAddSurnameTFValues[i + 1] = this.person.snpList[i].surname;
                            this.personAddNameTFValues[i + 1] = this.person.snpList[i].name;
                            if (this.person.snpList[i].patronymic != null)
                                this.personAddPatrTFValues[i + 1] = this.person.snpList[i].patronymic;
                        }
                        //console.log("******^^^^^^^^^^^^^****", sss);
                    }
                }
                // console.log("*************************", sss);
                return sss;
            },

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

            // getOrgNameById(orgId) {
            //     if (this.allOrgs !== null) {
            //         for (let i = 0; i < this.allOrgs.length; i++) {
            //             if (this.allOrgs[i].id === orgId) {
            //                 return this.allOrgs[i].name;
            //             }
            //         }
            //     }
            // },

            getOrgNameById(id) {
                let result = '';
                let currentOrg = this.personOrgEntities.find(x => x.id === id);

                // console.log("ORG", currentOrg);
                if (this.isObjectValidAndNotEmpty(currentOrg)) {//to prevent errors in console when search result isn't ready yet
                    result = currentOrg.nameRus;
                    return result;
                }
            },

            // createComplexOrgById(id) {
            //     let currentOrg = this.articleOrgEntities.find(x => x.id === id);
            //     let connection = this.article.orgList.find(x => x.itemId === id);
            //     let result;
            //
            //     if (this.isObjectValidAndNotEmpty(currentOrg.name)) {
            //         result = currentOrg.name + "/ " + currentOrg.nameRus;
            //     } else {
            //         result = currentOrg.nameRus;
            //     }
            //
            //     if (this.isObjectValidAndNotEmpty(currentOrg.abbr)) {
            //         result += "/ " + currentOrg.abbr;
            //     }
            //
            //     if (this.isObjectValidAndNotEmpty(currentOrg.abbrRus)) {
            //         result += "/ " + currentOrg.abbrRus;
            //     }
            //
            //     if (this.isObjectValidAndNotEmpty(connection.connection))
            //         result += "/ " + connection.connection;
            //
            //     if (this.isObjectValidAndNotEmpty(connection.comment))
            //         result += "/ " + connection.comment;
            //
            //     return result;
            // },

            showOrgAndPosition(occ) {
                if (occ.comment !== null) {
                    if (occ.comment.length === 0) {
                        return this.getOrgNameById(occ.orgId) + " / " + occ.position + "; ";
                    } else {
                        return this.getOrgNameById(occ.orgId) + " / " + occ.position + " / " + occ.comment + "; ";
                    }
                } else {
                    return this.getOrgNameById(occ.orgId) + " / " + occ.position + "; ";
                }
            },

            goURL(url) {
                location.href = url;
            },

            getSNPWithPriority(priority) {

                //console.log("*************************", this.entries);
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

                // console.log("*************************", sss);
                return sss;
            },

            createComplexLocationById(id) {
                let currentLocation = this.personLocationEntities.find(x => x.id === id);
                let result = currentLocation.country;

                // console.log("00000000000000000000", currentPerson);

                let connection = this.person.locationList.find(x => x.itemId === id);
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

            createComplexPersonById(id) {
                let currentPerson = this.personPersonEntities.find(x => x.id === id);
                let result = '';

                let priorSNP = currentPerson.snpList.find(x => x.priority === 1);
                result = priorSNP.surname;
                let connection = this.person.personList.find(x => x.itemId === id);


                if (this.isObjectValidAndNotEmpty(priorSNP.name)) {
                    result += " " + priorSNP.name;
                }
                if (this.isObjectValidAndNotEmpty(priorSNP.patronymic)) {
                    result += " " + priorSNP.patronymic;
                }

                if (this.isObjectValidAndNotEmpty(currentPerson.birthYear)) {
                    result += ", " + currentPerson.birthYear;
                }

                if (this.isObjectValidAndNotEmpty(connection.connection)) {
                    result += " / " + connection.connection;
                }

                if (this.isObjectValidAndNotEmpty(connection.comment)) {
                    result += "/ " + connection.comment;
                }

                result += "\n";

                console.log("res", result);

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
                console.log(r.data);

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
                }

                api.getPersonsByIds(this.personPersonIds, response => {
                    this.personPersonEntities = response.data;
                });

                for (let j = 0; j < this.person.testList.length; j++) {
                    this.personOrgIds.push(this.person.testList[j].orgId);
                    //console.log("+", j);
                }

                apiOrg.getOrgsByIds(this.personOrgIds, response => {
                    this.personOrgEntities = response.data;
                    //console.log("apiOrga", this.personOrgEntities);
                });




            });
        },
    }
</script>
