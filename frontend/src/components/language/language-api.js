import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAllLanguages() {
        return AXIOS.get(`/language`);
    },

    create(language, fn, fnError) {
        AXIOS
            .post('/language', language)
            .then(response => fn(response))
            .catch(error => fnError(error.response.data))
            // .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/language/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, language, fn, fnError) {
        AXIOS
            .put('/language/' + id, language)
            .then(response => fn(response))
            .catch(error => fnError(error.response.data))
            // .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/language/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


