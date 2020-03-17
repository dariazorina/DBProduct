import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAllPersons() {
        return AXIOS.get(`/person`);
    },

    // getAllCountries() {
    //     return AXIOS.get(`/country`);
    // },
    //
    // getAllMovements() {
    //     return AXIOS.get(`/movement`);
    // },

    create(person, fn) {
        AXIOS
            .post('/person', person)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/person/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, person, fn) {
        AXIOS
            .put('/person/' + id, person)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/person/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },


    searchPerson(searchKey, fn) {
        AXIOS.get(
            `/person/search?q=` + encodeURIComponent(searchKey)

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

        })
    },
}
