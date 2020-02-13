import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    getAll() {
        return AXIOS.get(`/article`);
    },

    getAllLanguages() {
        return AXIOS.get(`/language`);
    },

    getAllMovements() {
        return AXIOS.get(`/movement`);
    },

    getAllAuthors() {
        return AXIOS.get(`/person`);
    },

    getAuthorById(id, fn) {
        AXIOS
            .get(`/person/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
        // return AXIOS.get(`/person/`+ id);
    },

    create(article, fn) {
        AXIOS
            .post('/article', article)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        AXIOS
            .get(`/article/` + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, article, fn) {
        AXIOS
            .put('/article/' + id, article)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        AXIOS
            .delete('/article/' + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchTitle(searchKey, status, fn) {
        AXIOS.get(`article/search?title=` + encodeURIComponent(searchKey) + `&status=` + status)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchHash(searchKey, status, fn) {
        AXIOS.get(`article/search?hash=` + encodeURIComponent(searchKey) + `&status=` + status)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchAuthor(searchKey, status, fn) {
        AXIOS.get(`article/search?author=` + encodeURIComponent(searchKey) + `&status=` + status)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchWithStatus(status, fn) {
        AXIOS.get(`article/search?status=` + encodeURIComponent(status))
            .then(response => fn(response))
            .catch(error => console.log(error))
    },




// fetch('../api/v1/article/search?title=' + encodeURIComponent(this.searchKey))
//     .then(res => res.json())
//     .then(res => {
//         this.entries = res;
//     })
//     .catch(err => {
//         console.log(err)
//     })
//     .finally(() => (this.isLoading = false))

}


