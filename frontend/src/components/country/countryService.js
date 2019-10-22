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
};