<template id="country-delete">
    <div>
        <h2>Delete country {{ country.name }}</h2>
        <form @submit="deleteCountry">
            <p>The action cannot be undone.</p>
            <button type="submit" class="btn btn-danger">Delete</button>
            <a class="btn btn-default">
                <router-link to="/">Cancel</router-link>
            </a>
        </form>
    </div>
</template>

<script>
    var CountryDelete = Vue.extend({
        template: '#country-delete',
        data: function () {
            return {product: findCountry(this.$route.params.country_id)};
        },
        methods: {
            deleteCountry: function () {
                countryService.deleteCountry(this.country.id, r => router.push('/'))
            }
        }
    });
</script>