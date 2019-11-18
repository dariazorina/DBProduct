<template id="article-delete">
    <div>
        <h4>Delete country <i> {{ country.name }} </i></h4>
        <p>The action cannot be undone</p>
        <button type="button" @click="deleteCountry" class="btn btn-danger">Delete</button>
        <a class="btn btn-default">
            <router-link to="/country">Cancel</router-link>
        </a>
    </div>
</template>

<script>
    import api from "./article-api";
    import router from "./../../router";

    export default {
        name: 'article-delete',
        data() {
            return {
                country: {}
            }
        },

        methods: {
            deleteCountry: function () {
                api.delete(this.country.id, r => router.push('/country'))
            }
        },

        mounted() {
            api.findById(this.$route.params.country_id, r => {
                this.country = r.data
            });
        }
    }
</script>

