import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    getAllEvent(fn) {
        return AXIOS
            .get(`/event`)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    }
}


