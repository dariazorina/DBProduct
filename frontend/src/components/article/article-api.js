import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/article`);//  /api/hello
    },

    findById(id, fn) {
        AXIOS
            .get(`/article/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, article, fn) {
        AXIOS
            .put('/article/' + id, article)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/article/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


