<template id="country">
    <div>
        <b>Code: </b>
        <h2>{{ country.code }}</h2>
        <b>Name: </b>
        <div>{{ country.name }}</div>
        <!--<b>Price:</b>-->
        <!--<div>{{ product.price }}<span class="glyphicon glyphicon-euro"></span></div>-->
        <!--<br/>-->
        <!--<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>-->
        <a>
            <router-link to="/">Back to country list</router-link>
        </a>
    </div>
</template>

<script>
    var Country = Vue.extend({
        template: '#country',
        data: function () {
            return {country: findCountry(this.$route.params.country_id)};
        }
    });
</script>