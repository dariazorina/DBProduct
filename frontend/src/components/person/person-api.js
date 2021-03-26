import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});

export default {
    getAllPersons(fn) {
        return AXIOS
            .get(`/person`)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                // if (error.response !== undefined)
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    getPersonsByIds(idList, fn) {
        AXIOS
            .post(`/person/ids`, idList)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    create(person, fn) {
        AXIOS
            .post('/person', person)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    findById(id, fn) {
        AXIOS
            .get(`/person/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    update(id, person, fn) {
        AXIOS
            .put('/person/' + id, person)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },

    delete(id, fn) {
        AXIOS
            .delete('/person/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            })
    },


    searchPerson(searchKey, fn) {
        AXIOS.get(
            `/person/searchBySurname?q=` + encodeURIComponent(searchKey)
        ).then((response) => {
            if (response.status >= 200 && response.status < 300) {
                return response;

            } else {
                let error = new Error(response.statusText);
                error.response = response;
                throw error
            }

        }).then((response) => {
            // if (response.headers['content-type'] !== 'application/json') {
            //     let error = new Error('Некорректный ответ от сервера');
            //     error.response = response;
            //     throw error
            // }
            return response.data;

        }).then((json) => {
            fn(json);

        }).catch((error) => {
            console.log(error);
            if (error.response.status === 401) {
                router.push('/login');
            }
        })
    },

    filterAll(body, fn) {
        AXIOS.get(`person/search?` + body)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },
}
