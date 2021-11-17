import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    getAllLanguages(fn) {
        return AXIOS.
        get(`/language`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    create(language, fn, fnError) {
        AXIOS
            .post('/language', language)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                } else {
                    fnError(error.response.data);
                }
            })
            // .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/language/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    update(id, language, fn, fnError) {
        AXIOS
            .put('/language/' + id, language)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }else {
                    fnError(error.response.data);
                }
            })
            // .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/language/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    }
}


