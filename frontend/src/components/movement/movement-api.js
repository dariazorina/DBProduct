import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    getAllMovements(fn) {
        return AXIOS
            .get(`/movement`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    create(movement, fn, fnError) {
        AXIOS
            .post('/movement', movement)
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
            .get(`/movement/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    update(id, article, fn, fnError) {
        AXIOS
            .put('/movement/' + id, article)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                } else {
                    fnError(error.response.data);
                }
            })
    },

    delete(id, fn) {
        AXIOS
            .delete('/movement/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    }
}


