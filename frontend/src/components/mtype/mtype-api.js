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

    delete(id, fn, fnError) {
        AXIOS
            .delete('/mtype/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
                else {
                    fnError(error.response.data);
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

    // searchMaterial(searchKey, fn) {
    //     AXIOS.get(
    //         `/mtype/search?q=` + encodeURIComponent(searchKey)
    //     ).then((response) => {
    //         if (response.status >= 200 && response.status < 300) {
    //             return response;
    //
    //         } else {
    //             let error = new Error(response.statusText);
    //             error.response = response;
    //             throw error
    //         }
    //     }).then((response) => {
    //         // if (response.headers['content-type'] !== 'application/json') {
    //         //     let error = new Error('Некорректный ответ от сервера');
    //         //     error.response = response;
    //         //     throw error
    //         // }
    //         return response.data;
    //
    //     }).then((json) => {
    //         fn(json);
    //
    //     }).catch((error) => {
    //         console.log(error);
    //         if (error.response.status === 401) {
    //             router.push('/login');
    //         }
    //     })
    // },
}


