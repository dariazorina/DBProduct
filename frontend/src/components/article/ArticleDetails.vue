<template id="article-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Article Details</p>
            </div>
        </div>

        <div class="details">

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
                    <div class="cellTitle"><span class="float-left">Язык</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> {{article.language.name}} </span>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Дата</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;">
                    <span class="float-left"> {{ article.date }}</span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">
                        <span class="float-left">Авторы</span>
                    </div>
                </div>

                <div class="col-sm-10"><span class="float-left">
                     <div v-for="author in articlePersonEntities">
                            {{createComplexPersonById(author.id)}}
                    </div>
                </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">Заголовок</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;">
                    <span class="float-left"> <div>{{createComplexTitle()}}</div></span></div>
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
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">Организации</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;">
                                       <span class="float-left"> <div>
<!--                                           {{createComplexTitle()}}-->
                                       </div></span>
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
                    <div class="cellTitle"><span class="float-left">Описание</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left"> {{article.description}}</span>
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
<!--                    {{article.miscellany}}-->
                </span>
                </div>
            </div>

            <div class="row" v-if="article.miscellany!=null">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Комментарии</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> {{article.miscellany}}</span>
                </div>
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
    import moment from "moment";

    import "vue-scroll-table";

    export default {
        name: 'article-details',
        data() {
            return {
                article: {personList: [], language: {}, movement: {}, hashtagList: [], linkList: []},

                articlePersonIds: [], //before request
                articlePersonEntities: [], //after request

                articleLocationIds: [], //before request
                articleLocationEntities: [], //after request
            }
        },
        methods: {
            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
            },

            goURL(url) {
                location.href = url;
                // console.log("123");
            },

            isObjectValidAndNotEmpty(obj) {
                // console.log("VALID", obj);
                if (typeof obj === 'undefined' || obj === null || obj.length === 0) {
                    return false;
                }
                return true;
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
                            result += " / " + valueOrig;
                    }
                } else if (this.isObjectValidAndNotEmpty(valueOrig))
                    result += valueOrig;

                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += " / " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += " / " + connection.comment;

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
                    result += " / " + currentLocation.miscellany;
                }


                if (this.isObjectValidAndNotEmpty(connection.connection))
                    result += " / " + connection.connection;

                if (this.isObjectValidAndNotEmpty(connection.comment))
                    result += " / " + connection.comment;

                return result;
            },

            createComplexTitle() {
                let result = '';
                let valueOrig = this.article.title;
                let valueRus = this.article.titleRus;

                console.log("COMPLEX TITLE", this.article.title, this.article.titleRus);

                if (this.isObjectValidAndNotEmpty(valueRus)) {
                    result = valueRus;
                    if (this.isObjectValidAndNotEmpty(valueOrig)) {
                        if (valueRus.localeCompare(valueOrig) !== 0)
                            result += " / " + valueOrig;
                    }
                } else if (this.isObjectValidAndNotEmpty(valueOrig))
                    result += valueOrig;

                console.log("COMPLEX TITLE", result);
                return result;
            },
        },

        mounted() {
            api.findById(this.$route.params.article_id, r => {
                this.article = r.data;
                console.log("-------------------", this.article);
                this.article.date = this.formatDate(this.article.date);

                for (let j = 0; j < this.article.personList.length; j++) {
                    this.articlePersonIds.push(this.article.personList[j].itemId);
                }
                // console.log(this.articlePersonIds);


                for (let j = 0; j < this.article.locationList.length; j++) {
                    this.articleLocationIds.push(this.article.locationList[j].itemId);
                }
                console.log("mounted locations", this.articleLocationIds);

                apiPerson.getPersonsByIds(this.articlePersonIds, response => {
                    this.articlePersonEntities = response.data;
                    console.log("apiPerson", this.articlePersonEntities);
                });

                apiLocation.getLocationsByIds(this.articleLocationIds, response => {
                    this.articleLocationEntities = response.data;
                    console.log("apiLoca", this.articleLocationEntities);
                });
            });
        },

        // dateFormat(value, row, index) {  //todo
        //     moment(this.article.date).format('DD/MM/YYYY').then(response => {
        //         this.article.date = response.data;
        //     })
        // }
    }
</script>
