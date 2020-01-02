<template id="country">
    <div>
        <h2>Countries list</h2>

        <link href="../dbnm.css" rel="stylesheet"/>
        <div class="actions">
            <a class="btn btn-default">
                <router-link :to="{name: 'country-add'}">
                    <span class="glyphicon glyphicon-plus"></span>
                    Add country
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
                <th>Code</th>
                <th>Name</th>

                <th class="col-sm-2">Actions</th>
            </tr>
            </thead>
            <tbody>


            <tr v-for="country in filteredCountries">
                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>
                    {{ country.id }}
                    <!--<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>-->
                </td>

                <td>{{ country.code }}</td>

                <td> {{ country.name }}
                    <!--                    <a>-->
                    <!--                        <router-link :to="{name: 'country', params: {country_id: country.id}}">{{ country.name }}-->
                    <!--                        </router-link>-->
                    <!--                    </a>-->
                </td>

                <td>
                    <a class="btn btn-warning btn-xs">
                        <router-link :to="{name: 'country-edit', params: {country_id: country.id}}">Edit</router-link>
                    </a>
<!--                    <a class="btn btn-danger btn-xs">-->
<!--                        <router-link :to="{name: 'country-delete', params: {country_id: country.id}}">Delete</router-link>-->
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
    import api from "./country-api";
    // import api from "../article/article-api";

    export default {
        name: 'country',
        data() {
            return {
                countries: [],
                searchKey: '',
                response: [],
                errors: [],
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        computed: {
            filteredCountries() {
                return this.countries.filter((country) => {
                    return country.code.indexOf(this.searchKey) > -1
                        || country.name.indexOf(this.searchKey) > -1
                })
            }
        },
        methods: {
            // loadCountries() {
            //     api.getAll().then(response => {
            //         this.countries = response.data;
            //         console.log(response.data)
            //     })
            //         .catch(error => {
            //             this.errors.push(error)
            //         })
            // }
        },
        mounted() {
            api.getAll().then(response => {
                this.countries = response.data;
                console.log(response.data)
            })
                .catch(error => {
                    this.errors.push(error)
                })
        },

        // name: 'country-edit',
        // data() {
        //     return {
        //         country: findCountry(this.$route.params.country_id)
        //     };
        // },
        // methods: {
        //     updateProduct: function () {
        //         countryService.update(this.country.id, this.country, r => router.push('/'))
        //     }
        // }
    }

    // var List = Vue.extend({
    //     template: '#product-list',
    //     data: function () {
    //         return {products: [], searchKey: ''};
    //     },
    //     computed: {
    //         filteredProducts() {
    //             return this.products.filter((product) => {
    //                 return product.name.indexOf(this.searchKey) > -1
    //                     || product.description.indexOf(this.searchKey) > -1
    //                     || product.price.toString().indexOf(this.searchKey) > -1
    //             })
    //         }
    //     },
    //     mounted() {
    //         productService.findAll(r => {this.products = r.data; products = r.data})
    //     }
    // });

</script>






