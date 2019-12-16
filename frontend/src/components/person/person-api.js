import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/person`);
    },

    getAllCountries() {
        return AXIOS.get(`/country`);
    },

    getAllMovements() {
        return AXIOS.get(`/movement`);
    },

    create(person, fn) {
        AXIOS
            .post('/person', person)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/person/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, person, fn) {
        AXIOS
            .put('/person/' + id, person)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/person/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


