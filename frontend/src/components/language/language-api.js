import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/language`);
    },

    create(language, fn) {
        AXIOS
            .post('/language', language)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/language/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, article, fn) {
        AXIOS
            .put('/language/' + id, article)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/language/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


