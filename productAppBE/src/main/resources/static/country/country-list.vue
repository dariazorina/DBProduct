
Vue.config.devtools = true;


<template id="country-list">
    <div>
        <!--<div class="actions">-->
        <!--<a class="btn btn-default">-->
        <!--<router-link :to="{path: '/add-product'}">-->
        <!--<span class="glyphicon glyphicon-plus"></span>-->
        <!--Add product-->
        <!--</router-link>-->
        <!--</a>-->
        <!--</div>-->
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


            <tr v-for="country in filteredProducts">
                <!-- tr v-for="product in products" -->
                <!-- tr v-for="product in products | filterBy searchKey in 'name'" -->

                <td>
                    {{ country.id }}
                    <!--<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>-->
                </td>

                <td>{{ country.code }}</td>

                <td>
                    <a>
                        <router-link :to="{name: 'country', params: {country_id: country.id}}">{{ country.name }}
                        </router-link>
                    </a>
                </td>

                <td>
                    <a class="btn btn-warning btn-xs">
                        <router-link :to="{name: 'country-edit', params: {country_id: country.id}}">Edit</router-link>
                    </a>
                    <a class="btn btn-danger btn-xs">
                        <router-link :to="{name: 'country-delete', params: {country_id: country.id}}">Delete
                        </router-link>
                    </a>
                </td>
            </tr>

            </tbody>
        </table>
    </div>
</template>

<script>
//    <!--import App from './App.vue'-->
    import countryService from './countryService'

    var List = Vue.extend({
        template: '#country-list',
        data: function () {
            return {countries: [], searchKey: ''};
        },
        computed: {
            filteredProducts() {
                return this.countries.filter((country) => {
                    return country.code.indexOf(this.searchKey) > -1
                        || country.name.indexOf(this.searchKey) > -1
                        //|| product.price.toString().indexOf(this.searchKey) > -1
                })
            }
        },
        mounted() {
            countryService.findAll(r => {
                this.countries = r.data;
                countries = r.data
            })
        }
    });
</script>




