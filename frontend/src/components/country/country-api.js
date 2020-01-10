import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/country`);//  /api/hello
    },

    findById(id, fn) {
        AXIOS
            .get(`/country/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    create(country, fn, fnError) {
        AXIOS
            .post('/country', country)
            .then(response => fn(response))
            .catch( error =>fnError(error.response.data))
            // .catch(response => console.log('hhhh' + response))
    },

    update(id, country, fn, fnError) {
        AXIOS
            .put('/country/' + id, country)
            .then(response => fn(response))
            .catch(error => fnError(error.response.data))
            // .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/country/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }


}


