import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/movement`);
    },

    create(movement, fn) {
        AXIOS
            .post('/movement', movement)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/movement/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, article, fn) {
        AXIOS
            .put('/movement/' + id, article)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/movement/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


