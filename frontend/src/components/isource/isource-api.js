import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
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
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },
    getIsourcesByIds(idList, fn) {
        AXIOS
            .post(`/isource/ids`, idList)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    searchIsource(searchKey, mov, fn) {
        AXIOS.get(`/isource/search?q=` + encodeURIComponent(searchKey)  + `&mov=` + mov)
            .then((response) => {
                if (response.status >= 200 && response.status < 300) {
                    return response;

                } else {
                    let error = new Error(response.statusText);
                    error.response = response;
                    throw error
                }
            }).then((response) => {
            return response.data;

        }).then((json) => {
            fn(json);

        }).catch((error) => {
            console.log(error);
            if (error.response.status === 401) {
                router.push('/login');
            }
        })
    }
}


