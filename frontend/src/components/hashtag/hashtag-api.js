import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {


    search(q, fn) {
        AXIOS
            .get(`/hashtag?q=` + q)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    getAllHashtags() {
        return AXIOS.get(`/hashtag`);
    },

    create(hashtag, fn, fnError) {
        AXIOS
            .post('/hashtag', hashtag)
            .then(response => fn(response))
            .catch(error => fnError(error.response.data))
    },

    findById(id, fn) {
        AXIOS
            .get(`/hashtag/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, hashtag, fn, fnError) {
        AXIOS
            .put('/hashtag/' + id, hashtag)
            .then(response => fn(response))
            .catch(error => fnError(error.response.data))
    },

    delete(id, fn) {
        AXIOS
            .delete('/hashtag/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


