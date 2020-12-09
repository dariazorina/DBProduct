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
                    <div class="cellTitle"><span class="float-left">URL</span></div>
                </div>


                <!--            <input type="button" onclick="location.href='http://google.com'" value="${article.url}" />-->
                <!--            <input type="button" onclick="location.href='http://google.com'" value="Go to Google" />-->

                <div class="col-sm-10" style="background-color:lavender;"> <span class="float-left"><div
                        class="linkButton"><span class="float-left">
                <button class="btn btn-link" style="font-size: small"
                        @click="goURL(article.url)">{{article.url}}</button> </span></div></span>
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
                            {{createComplexCellValueById(author.id)}}
                    </div>
                </span>
                </div>

<!--                <div class="col-sm-10"><span class="float-left">-->
<!--                     <div v-for="author in articlePersonEntities">-->
<!--                            {{author.surname}} {{author.id}}-->
<!--                    </div>-->
<!--                </span>-->
<!--                </div>-->
            </div>

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">Заголовок</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;">
                    <span class="float-left"> {{article.title}}</span></div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">
                        <span class="float-left">Заголовок на русском</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> {{article.titleRus}}</span>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Описание</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left"> {{article.description}}</span>
                </div>
            </div>


            <!--        <div class="form-group row">-->
            <!--            <label for="date-input" class="col-2 col-form-label">Date</label>-->
            <!--            <div class="col-10">-->
            <!--                <input class="form-control" type="date" id="date-input" v-model="article.date">-->
            <!--            </div>-->
            <!--        </div>-->


            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Хештеги</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left">
                <div style="text-align: left" v-for="ht in article.hashtagList">{{ht}}</div></span>
                    <!--                <div class="leftAlignForDetails" v-for="ht in article.hashtagList"><span class="linkButton">{{ht.content}}</span></div></span>-->
                </div>
            </div>

            <!--        <div class="row">-->
            <!--            <div class="col-sm-2" style="background-color:lavender;">-->
            <!--                <div class="cellTitle"><span class="float-left">Movement</span></div>-->
            <!--            </div>-->

            <!--            <div class="col-sm-10" style="background-color:lavender;"><span-->
            <!--                    class="float-left">{{article.movement.name}} </span></div>-->
            <!--        </div>-->


            <!--        <div class="row">-->
            <!--            <div class="col-sm-2" style="background-color:lavender;">-->
            <!--                <div class="cellTitle"><span class="float-left">Links</span></div>-->
            <!--            </div>-->
            <!--            <div class="col-sm-10" style="background-color:lavender;"><span class="float-left">-->
            <!--                <div class ="linkButton" v-for="link in article.linkList"><span class="float-left"><button class="btn btn-link"-->
            <!--                                                                                       @click="goURL(link.content)">{{link.content}}</button></span></div></span>-->
            <!--            </div>-->
            <!--        </div>-->

            <div class="row">
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Комментарии</span></div>
                </div>
                <div class="col-sm-10" style="background-color:lavender;"><span class="float-left"> {{article.miscellany}}</span>
                </div>
            </div>
        </div>

        <!--        <div class="my-md-4">-->
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
    import moment from "moment";

    import "vue-scroll-table";

    export default {
        name: 'article-details',
        data() {
            return {
                articles: [],
                article: {personList: [], language: {}, movement: {}, hashtagList: [], linkList: []},
                authors: [],
                articlePersonIds: [], //before request
                articlePersonEntities: [], //after request
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

            isArrayValidAndNotEmpty(array) {

                if (typeof array === 'undefined' || array === null || array.length == 0) {
                    return false;
                }
                return true;
            },

            createComplexCellValueById(id) {
                let result = '';
                let currentPerson = this.articlePersonEntities.find(x => x.id === id);

                // for (let i = 0; i < this.articlePersonEntities.length; i++ ){
                //     if (this.articlePersonEntities[i].id === id){
                //         currentPerson = this.articlePersonEntities[i];
                //         break;
                //     }
                // }


                console.log("createComplexCellValue(id)", currentPerson);

                // if (typeof selected !== 'undefined') {  //todo

                // this.authorComponentKey += 1;

                let valueOrig = currentPerson.surname;// + " " + currentPerson.name;
                let valueRus = currentPerson.surnameRus;// + " " + currentPerson.nameRus;

                if (this.isArrayValidAndNotEmpty(currentPerson.name)) {
                    valueOrig += " " + currentPerson.name;
                }

                if (this.isArrayValidAndNotEmpty(currentPerson.nameRus)) {
                    valueRus += " " + currentPerson.nameRus;
                }

                if (this.isArrayValidAndNotEmpty(valueRus)) {
                    result = valueRus;
                    if (this.isArrayValidAndNotEmpty(valueOrig)) {
                        if (valueRus.localeCompare(valueOrig) !== 0)
                            result += " / " + valueOrig;
                    }
                } else if (this.isArrayValidAndNotEmpty(valueOrig))
                    result += valueOrig;
                // }
                return result;
            },
        },

        mounted() {
            api.findById(this.$route.params.article_id, r => {
                this.article = r.data;
                this.article.date = this.formatDate(this.article.date);

                for (let j = 0; j < this.article.personList.length; j++) {
                    this.articlePersonIds.push(this.article.personList[j].itemId);
                }
                console.log(this.articlePersonIds);

                apiPerson.getPersonsByIds(this.articlePersonIds, response => {
                    this.articlePersonEntities = response.data;
                    console.log("apiPerson", this.articlePersonEntities);
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
