import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/org`);//  /api/hello
    },

    findById(id, fn) {
        AXIOS
            .get(`/org/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },
}


