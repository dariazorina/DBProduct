<template id="person-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Person Details</p>
            </div>
        </div>

        <div class="row">
            <div v-if="person.photo" class="col-sm-2" style="background-color: transparent">

                <div id="preview" style="background-color: transparent">
                    <img v-bind:src="'data:image/jpeg;base64,'+person.photo" :style="{ width: 250+'px' }"/>
                </div>
            </div>

            <div v-if="person.photo" class="col-sm-10">
                <div class="details" style="background-color: transparent">
                    <!--        ///////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                                <span class="float-left">Фамилия</span></div>
                        </div>
                        <div class="col-sm-10">
                            <span class="float-left"> {{showSurname()}}</span></div>
                    </div>

                    <!--//////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Имя</span>
                            </div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">{{showName()}}</span>
                        </div>
                    </div>

                    <!--        //////////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Отчество</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left"> {{person.patronymic}}</span>
                        </div>
                    </div>

                    <!--            ////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Год рождения</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left"> {{person.birthYear}}</span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Год смерти</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left"> {{person.deathYear}}</span>
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
                                {{createComplexLocationById(location.id)}}
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
                                <a v-for="test in person.testList">{{showOrgAndPosition(test)}}</a>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10 back1" style="text-align: left">
                            <span v-html="person.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left">
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
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Links</span></div>
                        </div>

                        <div class="col-sm-10 back1">
                            <span class="float-left">
                                <div class="linkButton" v-for="link in person.linkList">
                                    <span class="float-left">
                                        <button class="btn btn-link" style="font-size: small" @click="goURL(link.content)">{{link.content}}</button>
                                    </span>
                                </div>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div v-else class="col-sm-12">
                <div class="details" style="background-color: transparent">
                    <!--        ///////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                                <span class="float-left">Фамилия</span></div>
                        </div>
                        <div class="col-sm-10">
                            <span class="float-left"> {{showSurname()}}</span></div>
                    </div>

                    <!--//////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Имя</span>
                            </div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left">{{showName()}}</span>
                        </div>
                    </div>

                    <!--        //////////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Отчество</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left"> {{person.patronymic}}</span>
                        </div>
                    </div>

                    <!--            ////////////////////////////////////////////////////////////////-->
                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle">
                                <span class="float-left">Год рождения</span></div>
                        </div>
                        <div class="col-sm-10 back1"><span class="float-left"> {{person.birthYear}}</span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle">
                                <span class="float-left">Год смерти</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left"> {{person.deathYear}}</span>
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
                                {{createComplexLocationById(location.id)}}
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
                                <a v-for="test in person.testList">{{showOrgAndPosition(test)}}</a>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Описание</span></div>
                        </div>

                        <div class="col-sm-10 back1" style="text-align: left">
                            <span v-html="person.description"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                        </div>
                        <div class="col-sm-10"><span class="float-left">
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
                        <div class="col-sm-2 back1">
                            <div class="cellTitle"><span class="float-left">Links</span></div>
                        </div>

                        <div class="col-sm-10 back1">
                    <span class="float-left">
                        <div class="linkButton" v-for="link in person.linkList">
                            <span class="float-left">
                                <button class="btn btn-link" style="font-size: small" @click="goURL(link.content)">{{link.content}}</button>
                            </span>
                        </div>
                    </span>
                        </div>
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
    import "vue-scroll-table";

    export default {
        name: 'person-details',
        data() {
            return {
                persons: [],
                person: {country: {}, hashtagList: [], linkList: [], locationList: []},

                personLocationIds: [], //before request
                personLocationEntities: [], //after request

                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
            }
        },

        methods: {
            getOrgNameById(orgId) {
                if (this.allOrgs !== null) {
                    for (let i = 0; i < this.allOrgs.length; i++) {
                        if (this.allOrgs[i].id === orgId) {
                            return this.allOrgs[i].name;
                        }
                    }
                }
            },

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

            showSurname() {
                let surname = this.person.surnameRus; //surnameRus - obligatory field

                if (this.person.surname)
                    surname = surname + " / " + this.person.surname;

                if (this.person.surnameEng)
                    surname = surname + " / " + this.person.surnameEng;

                return surname;
            },

            showName() {
                let name = this.person.nameRus;

                if (this.person.name)
                    name = name + " / " + this.person.name;

                if (this.person.nameEng)
                    name = name + " / " + this.person.nameEng;

                return name;
            },

            showCountry(country) {
                if (country) {
                    return country.name;
                }
                return "";
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

            isObjectValidAndNotEmpty(obj) {
                // console.log("VALID", obj);
                if (typeof obj === 'undefined' || obj === null || obj.length === 0) {
                    return false;
                }
                return true;
            },
        },
        mounted() {
            api.findById(this.$route.params.person_id, r => {
                console.log(r.data);

                apiOrg.getAllOrgs(response => {  //todo - get only actual orgs
                    this.person = r.data;
                    this.allOrgs = response.data;
                    // console.log(" O R G A ", response.data)

                    for (let j = 0; j < this.person.locationList.length; j++) {
                        this.personLocationIds.push(this.person.locationList[j].itemId);
                        console.log("+", j);
                    }
                    console.log("mountedmountedmountedmountedmounted locations", this.personLocationIds);

                    apiLocation.getLocationsByIds(this.personLocationIds, response => {
                        this.personLocationEntities = response.data;
                        console.log("getLocationsByIds getLocationsByIds getLocationsByIds", this.personLocationEntities);
                    });
                });


            });
        },
    }
</script>
