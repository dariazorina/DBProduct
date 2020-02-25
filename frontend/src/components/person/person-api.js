import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/person`);
    },

    getAllCountries() {
        return AXIOS.get(`/country`);
    },

    getAllMovements() {
        return AXIOS.get(`/movement`);
    },

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

    // searchPerson(searchKey, loading, fn, fn2) {
    //     AXIOS.get(`/person/search?q=` + encodeURIComponent(searchKey))
    //         .then(response => fn(response))
    //         .then(response => fn2(response))
    //         .catch(error => console.log(error))
    //         .finally(() => (loading = false))
    // },
    //
    //
    // searchPerson1(searchKey ) {
    //     fetch(`/person/search?q=` + encodeURIComponent(searchKey))
    //         .then(function (response) {
    //             return response.json();
    //         })
    //
    //         .catch(function (error) {
    //             console.log(error)
    //         });
    // }
}


// fetch('../api/v1/person/search?q=' + encodeURIComponent(val))
//     .then(res => res.json())
//     .then(res => {
//         this.entries = res;
//         // const {count, entries} = res;
//         // this.count = count;
//         // this.entries = entries;
//     })
//     .catch(err => {
//         console.log(err)
//     })
//     .finally(() => (this.isLoading = false))
// }

