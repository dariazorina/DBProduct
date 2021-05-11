import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/`,
    timeout: 10000
});

export default {
    /*    login(credentials, fn) {
            let promise = AXIOS.post(`authentication`, credentials);

            promise.then(response => fn(true))
                .catch(error => fn(false))
        },*/

    getSecured(credentials) {
       // console.log("LOGIN API AUTH", credentials);
        return AXIOS.post(`authentication`, credentials);
    },

    getAccount() {
        return AXIOS.get(`account`);
    },

    logout() {
        return AXIOS.post(`logout`);
    }


}


