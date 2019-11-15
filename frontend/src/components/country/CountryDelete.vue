<template id="country-delete">
    <div>
        <h4>Delete country <i> {{ country.name }} </i> </h4>
        <form @submit="deleteCountry" action="/country">
            <p>The action cannot be undone</p>
            <button type="submit" class="btn btn-danger">Delete</button>
            <a class="btn btn-default">
                <router-link to="/country">Cancel</router-link>
            </a>
        </form>
    </div>
</template>
<script>

    import api from "./country-api";

    export default {
        name: 'country-delete',
        data() {
            return {
                errorFlag: false,
                errors: [],
                country: []//{}
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
    // var CountryDelete = Vue.extend({
    //     template: '#country-delete',
    //     data: function () {
    //         return {product: findCountry(this.$route.params.country_id)};
    //     },
    //     methods: {
    //         deleteCountry: function () {
    //             api.delete(this.country.id, r => router.push('/country'))
    //         }
    //     }
    // });
</script>

