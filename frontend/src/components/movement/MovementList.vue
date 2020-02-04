<template id="movement">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions">
            <a class="btn btn-default">
                <router-link :to="{name: 'movement-add'}"><span class="glyphicon glyphicon-plus"></span>Add movement
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
                <th class='tdAlignLeft'>Код</th>
                <th class='tdAlignLeft'>Название</th>

                <th style="width:10%" class="col-sm-2">Actions</th>
            </tr>
            </thead>
            <tbody>

<!--            <tr v-for="movement in movements">-->
            <tr v-for="movement in filteredMovements">

                <td class='tdAlignLeft'>{{movement.id }}</td>
                <td class='tdAlignLeft'>{{movement.code}}</td>
                <td class='tdAlignLeft'>{{movement.name}}</td>

                <td>
                    <a class="btn btn-warning btn-sm mr-2">
                        <router-link :to="{name: 'movement-edit', params: {movement_id: movement.id}}">Edit
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
    import api from "./movement-api";

    import "vue-scroll-table";

    export default {
        name: 'movement',
        data() {
            return {
                movements: [],
                searchKey: '',
                movement: {},
                response: [],
                errors: [],
                showResponse: false,
            }
        },
        computed: {
            filteredMovements() {
                return this.movements.filter((movement) => {
                    return movement.name.indexOf(this.searchKey) > -1
                     || movement.code.indexOf(this.searchKey) > -1
                    // || article.description.indexOf(this.searchKey) > -1
                })
            }
        },

        methods: {
        },
        mounted() {
            api.getAll().then(response => {
                this.movements = response.data;
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