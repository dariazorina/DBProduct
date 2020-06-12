<template id="events">
    <div>
        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions">
            <a class="btn btn-default">
                <router-link :to="{path: '/add-event'}">
                    <span class="glyphicon glyphicon-plus"></span>
                    Add an event
                </router-link>
            </a>
        </div>
        <div class="filters row">
            <div class="form-group col-sm-3">
                <input placeholder="Search" v-model="searchKey" class="form-control" id="search-element" requred/>
            </div>
        </div>


        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Date</th>
                <th>Title</th>

                <th class="col-sm-2">Actions</th>
            </tr>
            </thead>
            <tbody>


            <tr v-for="event in filteredCountries">
                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>
                    {{ event.id }}
                    <!--<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>-->
                </td>

                <td>{{ event.date }}</td>

                <td>
                    <a>
                        <router-link :to="{name: 'country', params: {event_id: event.id}}">{{ event.title }}
                        </router-link>
                    </a>
                </td>

                <td>
                    <a class="btn btn-warning btn-xs">
                        <router-link :to="{name: 'event-edit', params: {event_id: event.id}}">Edit</router-link>
                    </a>
                    <a class="btn btn-danger btn-xs">
                        <router-link :to="{name: 'event-delete', params: {event_id: event.id}}">Delete
                        </router-link>
                    </a>
                </td>
            </tr>

            </tbody>
        </table>
    </div>
</template>

<script>
    // export default {
    //     name: "EventList"
    // }

    import api from "./event-api";

    export default {
        name: 'events',
        data() {
            return {
                events: [],
                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        computed: {
            filteredEvents() {
                return this.events.filter((event) => {
                    return events.date.indexOf(this.searchKey) > -1
                        || events.title.indexOf(this.searchKey) > -1
                })
            }
        },
        methods: {
            loadEvents() {
                api.getAll().then(response => {
                    this.events = response.data;
                    console.log(response.data)
                })
                    .catch(error => {
                        this.errors.push(error)
                    })
            }
        },
        mounted() {
            api.getAll().then(response => {
                this.events = response.data;
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                })
        }
    }
</script>

<style scoped>
</style>