import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {




    search(q, fn) {
        AXIOS.get(`/person?q=` + q)
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}


