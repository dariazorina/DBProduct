<template id="person-details">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>

        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDetailsTitle">Person Details</p>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" >
                <div class="cellTitle">  <!--                <div class="ml-md-4"> instead-->
                    <span class="float-left">Surname, original</span></div>
            </div>
            <div class="col-sm-10" >
                <span class="float-left"> {{person.surname}}</span></div>
        </div>

        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle">
                    <span class="float-left">Name, original</span>
                </div>
            </div>
            <div class="col-sm-10" style="background-color:lavender;"><span class="float-left">
                {{person.name}}
                </span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2">
                <div class="cellTitle">
                    <span class="float-left">Patronymic</span></div>
            </div>
            <div class="col-sm-10"><span class="float-left"> {{person.patronymic}}</span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Фамилия</span></div>
            </div>
            <div class="col-sm-10" style="background-color:lavender;"><span
                    class="float-left"> {{ person.surnameRus }}</span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2">
                <div class="cellTitle"><span class="float-left">Имя</span></div>
            </div>
            <div class="col-sm-10"><span
                    class="float-left"> {{person.nameRus}}</span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Surname, eng</span></div>
            </div>
            <div class="col-sm-10" style="background-color:lavender;"><span
                    class="float-left"> {{person.surnameEng}}</span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" >
                <div class="cellTitle"><span class="float-left">Name, eng</span></div>
            </div>
            <div class="col-sm-10" ><span
                    class="float-left"> {{person.nameEng}}</span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Occupation</span></div>
            </div>

            <div class="col-sm-10" style="background-color:lavender;"> <span class="float-left">
                {{person.occupation}} </span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2">
                <div class="cellTitle"><span class="float-left">Hashtags</span></div>
            </div>
            <div class="col-sm-10"><span class="float-left">
                <div class="leftAlignForDetails" v-for="ht in person.hashtagList">{{ht.content}}</div></span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Movement</span></div>
            </div>

            <div class="col-sm-10" style="background-color:lavender;"><span
                    class="float-left">{{person.movement.name}} </span></div>
        </div>

        <div class="row">
            <div class="col-sm-2" >
                <div class="cellTitle"><span class="float-left">Country</span></div>
            </div>
            <div class="col-sm-10"><span class="float-left"> {{person.country.name}} </span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2" style="background-color:lavender;">
                <div class="cellTitle"><span class="float-left">Links</span></div>
            </div>
            <div class="col-sm-10" style="background-color:lavender;"><span class="float-left">
                <div class="linkButton" v-for="link in person.linkList"> <span class="float-left">
                    <button class="btn btn-link" @click="goURL(link.content)">{{link.content}}</button></span>
                </div>
            </span>
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

<script>
    import api from "./person-api";
    import "vue-scroll-table";

    export default {
        name: 'person-details',
        data() {
            return {
                persons: [],
                person: {orgList: [], country: {}, movement: {}, hashtagList: [], linkList: []},
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
