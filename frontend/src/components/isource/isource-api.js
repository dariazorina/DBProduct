import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAllIsource(fn) {
        return AXIOS
            .get(`/isource`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })

    },

    create(isource, fn, fnError) {
        AXIOS
            .post('/isource', isource)
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
            .get(`/isource/` + id)
            .then(response => fn(response))
            .catch(error =>  {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    update(id, isource, fn, fnError) {
        AXIOS
            .put('/isource/' + id, isource)
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
            .delete('/isource/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    searchIsource(searchKey, fn) {
        AXIOS.get(`/isource/search?isource=` + encodeURIComponent(searchKey))
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },
}


