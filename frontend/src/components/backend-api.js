import axios from 'axios'
import router from "../router";

const AXIOS = axios.create({
    baseURL: `/api`,
    timeout: 1000
});


export default {
    hello() {
        return AXIOS.get(`/hello`);//  /api/hello
    },
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
    },

    getUserByLogin(login){  //not in use right now
        return AXIOS.get(`/role/` + login);
    },

    getAllUsers() {
        return AXIOS.get(`/users`);
    },

    // updateMovement(userId, movements) {
        updateMovement(userId, movements, fn) {
        return AXIOS
            .put('/user/' + userId, movements)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    createUser(firstName, lastName) {
        return AXIOS.post(`/user/` + firstName + '/' + lastName);
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`,{
            auth: {
                username: user,
                password: password
            }});
    }
}


