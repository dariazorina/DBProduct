<template id="person-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Person Details</p>
            </div>
        </div>

        <div class="details">
            <!--        ///////////////////////////////////////////////////////////-->
            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                        <span class="float-left">Фамилия</span></div>
                </div>
                <div class="col-sm-10">
                    <span class="float-left"> {{showSurname()}}</span></div>
            </div>
            <!--        <div class="row">-->
            <!--            <div class="col-sm-2 back1">-->
            <!--                <div class="cellTitle"><span class="float-left">Фамилия на русском</span></div>-->
            <!--            </div>-->
            <!--            <div class="col-sm-10 back1"><span-->
            <!--                    class="float-left"> {{ person.surnameRus }}</span>-->
            <!--            </div>-->
            <!--        </div>-->
            <!--        <div class="row">-->
            <!--            <div class="col-sm-2">-->
            <!--                <div class="cellTitle"><span class="float-left">Фамилия на английском</span></div>-->
            <!--            </div>-->
            <!--            <div class="col-sm-10"><span-->
            <!--                    class="float-left"> {{person.surnameEng}}</span>-->
            <!--            </div>-->
            <!--        </div>-->


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

            <!--        <div class="row">-->
            <!--            <div class="col-sm-2">-->
            <!--                <div class="cellTitle"><span class="float-left">Имя на русском</span></div>-->
            <!--            </div>-->
            <!--            <div class="col-sm-10"><span-->
            <!--                    class="float-left"> {{person.nameRus}}</span>-->
            <!--            </div>-->
            <!--        </div>-->

            <!--        <div class="row">-->
            <!--            <div class="col-sm-2 back1">-->
            <!--                <div class="cellTitle"><span class="float-left">Имя на английском</span></div>-->
            <!--            </div>-->
            <!--            <div class="col-sm-10 back1"><span-->
            <!--                    class="float-left"> {{person.nameEng}}</span>-->
            <!--            </div>-->
            <!--        </div>-->

            <!--        //////////////////////////////////////////////////////////////////////-->
            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle">
                        <span class="float-left">Отчество</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> {{person.patronymic}}</span>
                </div>
            </div>


            <!--/////////////////////////////////////////////////////////////////////////////////////////////-->

            <div class="row">
                <div class="col-sm-2 back1">
                    <div class="cellTitle"><span class="float-left">Страна</span></div>
                </div>
                <div class="col-sm-10 back1"><span class="float-left"> {{showCountry(person.country)}} </span>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Город</span></div>
                </div>
                <div class="col-sm-10"><span class="float-left"> {{person.settlement}} </span>
                </div>
            </div>

            <!--        ///////////////////////////////////////////////////////////////////-->


            <div class="row">
                <div class="col-sm-2 back1">
                    <div class="cellTitle"><span class="float-left">Должность</span></div>
                </div>

                <div class="col-sm-10 back1"> <span class="float-left">
                {{person.occupation}} </span>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-2">
                    <div class="cellTitle"><span class="float-left">Организации</span></div>
                </div>
                <div class="col-sm-10" style="text-align: left">{{showCountry(person.country)}}
                </div>
            </div>


            <div class="row">
                <div class="col-sm-2 back1">
                    <div class="cellTitle"><span class="float-left">Описание</span></div>
                </div>

                <div class="col-sm-10 back1" style="text-align: left">
                    {{person.description}}
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
                <div class="col-sm-2" style="background-color:lavender;">
                    <div class="cellTitle"><span class="float-left">Links</span></div>
                </div>

                <div class="col-sm-10" style="background-color:lavender;">
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
    import "vue-scroll-table";

    export default {
        name: 'person-details',
        data() {
            return {
                persons: [],
                person: {orgList: [], country: {}, hashtagList: [], linkList: []},
                orgList: [],
                hashtagList: [],
                linkList: [],
                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
            }
        },

        methods: {
            goURL(url) {
                location.href = url;
                // console.log("123");
            },

            showSurname() {
                let surname; // = this.person.surname; //surname - obligatory field

                if (this.person.surname)
                    surname = this.person.surname;

                if (this.person.surnameRus)
                    surname = surname + " / " + this.person.surnameRus;

                if (this.person.surnameEng)
                    surname = surname + " / " + this.person.surnameEng;

                return surname;
            },

            showName() {
                let name;

                if (this.person.name)
                    name = this.person.name;

                if (this.person.nameRus)
                    name = name + " / " + this.person.nameRus;

                if (this.person.nameEng)
                    name = name + " / " + this.person.nameEng;

                return name;
            },

            showCountry(country) {
                if (country) {
                    return country.name;
                }
                return "";
            }

        },
        mounted() {
            api.findById(this.$route.params.person_id, r => {
                this.person = r.data;
                // console.log(r.data);
                // this.article.date = this.formatDate(this.article.date);
            });
        },
    }
</script>
