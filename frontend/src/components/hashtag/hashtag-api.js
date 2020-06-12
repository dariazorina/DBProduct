import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    search(q, fn) {
        AXIOS
            .get(`/hashtag?q=` + q)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    getAllHashtags(fn) {
        return AXIOS
            .get(`/hashtag`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })

    },

    getLeafHashtags(id, fn) {
        return AXIOS
            .get(`/hashtag/leafs?parentid=` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    create(hashtag, fn, fnError) {
        AXIOS
            .post('/hashtag', hashtag)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                } else {
                    fnError(error.response.data);
                }

            })
    },

    findById(id, fn) {
        AXIOS
            .get(`/hashtag/` + id)
            .then(response => fn(response))
            .catch(error =>  {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    update(id, hashtag, fn, fnError) {
        AXIOS
            .put('/hashtag/' + id, hashtag)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
                else {
                    fnError(error.response.data);
                }
            })
    },

    delete(id, fn) {
        AXIOS
            .delete('/hashtag/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    searchHash(searchKey, fn) {
        AXIOS.get(`/hashtag/search?hash=` + encodeURIComponent(searchKey))
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },
}


