import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAllCountries(fn) {
        return AXIOS
            .get(`/country`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    findById(id, fn) {
        AXIOS
            .get(`/country/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    create(country, fn, fnError) {
        AXIOS
            .post('/country', country)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                } else {
                    fnError(error.response.data);
                }
            })
        // .catch(response => console.log('hhhh' + response))
    },

    update(id, country, fn, fnError) {
        AXIOS
            .put('/country/' + id, country)
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

    delete(id, fn) {
        AXIOS
            .delete('/country/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    }
}


