<!--<template id="country-add">-->
<!--    <div>-->
<!--        <h2>Add new country</h2>-->
<!--        <form @submit="createCountry">-->
<!--            <div class="form-group">-->
<!--                <label for="add-name">Code</label>-->
<!--                <input class="form-control" id="add-name" v-model="country.code" required/>-->
<!--            </div>-->
<!--            <div class="form-group">-->
<!--                <label for="add-description">Name</label>-->
<!--                <textarea class="form-control" id="add-description" rows="10" v-model="country.name"></textarea>-->
<!--            </div>-->
<!--            &lt;!&ndash;<div class="form-group">&ndash;&gt;-->
<!--                &lt;!&ndash;<label for="add-price">Price, <span class="glyphicon glyphicon-euro"></span></label>&ndash;&gt;-->
<!--                &lt;!&ndash;<input type="number" class="form-control" id="add-price" v-model="product.price"/>&ndash;&gt;-->
<!--            &lt;!&ndash;</div>&ndash;&gt;-->
<!--            <button type="submit" class="btn btn-primary">Create</button>-->
<!--            <a class="btn btn-default">-->
<!--                <router-link to="/">Cancel</router-link>-->
<!--            </a>-->
<!--        </form>-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--    var CountryAdd = Vue.extend({-->
<!--        template: '#country-add',-->
<!--        data() {-->
<!--            return {-->
<!--                country: {code: 0, name: ''}-->
<!--            }-->
<!--        },-->
<!--        methods: {-->
<!--            createCountry() {-->
<!--                countryService.create(this.country, r => router.push('/'))-->
<!--            }-->
<!--        }-->
<!--    });-->
<!--</script>-->