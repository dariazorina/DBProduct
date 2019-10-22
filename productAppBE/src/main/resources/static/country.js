var countries = [];

function findCountry(countryId) {
    return countries[findProductKey(countryId)];
}

function findCountryKey(countryId) {
    for (var key = 0; key < countries.length; key++) {
        if (countries[key].id == countryId) {
            return key;
        }
    }
}

var countryService = {
    findAll(fn) {
        axios
            .get('/api/v1/countries')
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        axios
            .get('/api/v1/countries/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    create(country, fn) {
        axios
            .post('/api/v1/countries', country)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, country, fn) {
        axios
            .put('/api/v1/countries/' + id, country)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    deleteCountry(id, fn) {
        axios
            .delete('/api/v1/countries/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}

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

var Country = Vue.extend({
    template: '#country',
    data: function () {
        return {country: findCountry(this.$route.params.country_id)};
    }
});

var CountryEdit = Vue.extend({
    template: '#country-edit',
    data: function () {
        return {product: findCountry(this.$route.params.country_id)};
    },
    methods: {
        updateProduct: function () {
            countryService.update(this.country.id, this.country, r => router.push('/'))
        }
    }
});

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

var CountryAdd = Vue.extend({
    template: '#country-add',
    data() {
        return {
            country: {code: 0, name: ''}
        }
    },
    methods: {
        createCountry() {
            countryService.create(this.country, r => router.push('/'))
        }
    }
});

var router = new VueRouter({
    routes: [
        {path: '/', component: List},
        {path: '/country/:country_id', component: Country, name: 'country'},
        {path: '/country-add', component: CountryAdd},
        {path: '/country/:country_id/edit', component: CountryEdit, name: 'country-edit'},
        {path: '/country/:country_id/delete', component: CountryDelete, name: 'country-delete'}
    ]
});

new Vue({
    router
}).$mount('#app')
