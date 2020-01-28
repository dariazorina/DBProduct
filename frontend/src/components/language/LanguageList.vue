<template id="language">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions">
            <a class="btn btn-default">
                <router-link :to="{name: 'language-add'}"><span class="glyphicon glyphicon-plus"></span>Add language
                </router-link>
            </a>
        </div>

        <div class="filters row">
            <div class="form-group col-sm-3">
                <input placeholder="Search by Name" v-model="searchKey" class="form-control" id="search-element" requred/>
            </div>
        </div>

        <table class="redTable">
            <thead>
            <!--                <template slot="thead">-->
            <tr>
                <th class='tdAlignLeft'>Id</th>
                <th class='tdAlignLeft'>Code</th>
                <th class='tdAlignLeft'>Name</th>

                <th style="width:10%" class="col-sm-2">Actions</th>
            </tr>
            </thead>
            <tbody>

<!--            <tr v-for="language in movements">-->
            <tr v-for="language in filteredLanguages">

                <td class='tdAlignLeft'>{{language.id }}</td>
                <td class='tdAlignLeft'>{{language.code}}</td>
                <td class='tdAlignLeft'>{{language.name}}</td>

                <td>
                    <a class="btn btn-warning btn-sm mr-2">
                        <router-link :to="{name: 'language-edit', params: {language_id: language.id}}">Edit
                        </router-link>
                    </a>
<!--                    <a class="btn btn-danger btn-sm">-->
<!--                        <router-link :to="{name: 'article-delete', params: {article_id: article.id}}">Delete-->
<!--                        </router-link>-->
<!--                    </a>-->


                </td>


            </tr>
            </tbody>
        </table>
    </div>
</template>

<style lang="scss">
    @import '../dbnm.css';
</style>

<script>
    import api from "./language-api";

    import "vue-scroll-table";

    export default {
        name: 'language',
        data() {
            return {
                languages: [],
                searchKey: '',
                language: {},
                response: [],
                errors: [],
                showResponse: false,
            }
        },
        computed: {
            filteredLanguages() {
                return this.languages.filter((language) => {
                    return language.name.indexOf(this.searchKey) > -1
                     || language.code.indexOf(this.searchKey) > -1
                    // || article.description.indexOf(this.searchKey) > -1
                })
            }
        },

        methods: {
        },
        mounted() {
            api.getAll().then(response => {
                this.languages = response.data;
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                })
        },
    }
</script>

<!--<style scoped>-->
<!--</style>-->