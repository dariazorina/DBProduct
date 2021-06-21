import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});

export default {
    getAllPersons(mov, fn) {
        return AXIOS
            .get(`/person?mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                // if (error.response !== undefined)
                if (error.response.status === 401) {
                    this.error401Handling();
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
                    this.error401Handling();
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
                    this.error401Handling();
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
                    this.error401Handling();
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
                    this.error401Handling();
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
                    this.error401Handling();
                }
            })
    },


    searchPerson(searchKey, mov, fn) {
        AXIOS.get(`/person/searchBySurname?q=` + encodeURIComponent(searchKey) + `&mov=` + mov)
            .then((response) => {
                if (response.status >= 200 && response.status < 300) {
                    return response;

                } else {
                    let error = new Error(response.statusText);
                    error.response = response;
                    throw error
                }
            })
            .then((response) => {
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
                this.error401Handling();
            }
        })
    },

    filterAll(body, fn) {
        AXIOS.get(`person/search?` + body)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    error401Handling() {
        localStorage.clear();
        router.push('/login');
    },
}
