import axios from 'axios'
import router from "../../router";

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
            .catch(error => {
                console.log(error);
                if (error.code == 401) {
                    router.push('/login');
                }
            })
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

    searchTitle(searchKey, status,  start, end,  fn) {
        AXIOS.get(`article/search?title=` + encodeURIComponent(searchKey) + `&status=` + status  + `&startDate=` + start + `&endDate=` + end)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchHash(searchKey, status, start, end,  fn) {
        AXIOS.get(`article/search?hash=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchAuthor(searchKey, status, start, end,  fn) {
        AXIOS.get(`article/search?author=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchLanguage(searchKey, status, start, end,  fn) {
        AXIOS.get(`article/search?language=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchDescription(searchKey, status, start, end,  fn) {
        AXIOS.get(`article/search?description=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    searchWithStatus(status, start, end, fn) {
        AXIOS.get(`article/search?status=` + status + `&startDate=` + start + `&endDate=` + end)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status == 401) {
                    router.push('/login');
                }
            })
    },

    searchPeriod(start, end, fn) {
        AXIOS.get(`article/search?startDate=` + start + `&endDate=` + end)
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


