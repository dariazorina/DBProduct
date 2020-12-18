import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    search(q, fn) {
        AXIOS
            .get(`/mtype?q=` + q)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    getAll(fn) {
        return AXIOS
            .get(`/mtype`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })

    },

    getLeaf(id, fn) {
        return AXIOS
            .get(`/mtype/leafs?parentid=` + id)
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
            .post('/mtype', hashtag)
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
            .get(`/mtype/` + id)
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
            .put('/mtype/' + id, hashtag)
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
            .delete('/mtype/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    searchHash(searchKey, fn) {
        AXIOS.get(`/mtype/search?mtype=` + encodeURIComponent(searchKey))
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },
}


