import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    // getAllArticles() {   //todo maybe in other views
    //     return AXIOS.get(`/article`);
    // },

    // getAllLanguages() {
    //     return AXIOS.get(`/language`);
    // },

    // getAllMovements() {
    //     return AXIOS.get(`/movement`);
    // },

    // getAllAuthors() {
    //     return AXIOS.get(`/person`);
    // },

    // getAuthorById(id, fn) {
    //     AXIOS
    //         .get(`/person/` + id)
    //         .then(response => fn(response))
    //         .catch(error => console.log(error))
    //     // return AXIOS.get(`/person/`+ id);
    // },

    create(article, fn) {
        AXIOS
            .post('/article', article)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.code === 401) {
                    this.error401Handling();
                }

            })
    },

    findById(id, fn) {
        AXIOS
            .get(`/article/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    getMaterialsByIds(idList, fn) {
        AXIOS
            .post(`/article/ids`, idList)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    getMaterialsByIdsAndSymmetrically(id,fn) {
        AXIOS
            .post(`/article/symmids/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    getConnectionTypes(fn) {
        AXIOS
            .post(`/article/connectionTypes`)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },


    update(id, article, fn) {
        AXIOS
            .put('/article/' + id, article)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    updateColor(id, article, fn) {
        AXIOS
            .put('/article/color/' + id, article)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    delete(id, fn) {
        AXIOS
            .delete('/article/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },


    searchMaterial(searchKey, fn) {
        AXIOS.get(
            `/article/searchMaterial?q=` + encodeURIComponent(searchKey))
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

    searchMaterialById(id, fn) {  //for the case with article creation with parent/child/equal
        AXIOS.get(
            `/article/searchMaterialById?id=` + encodeURIComponent(id))
            .then((response) => {
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
                this.error401Handling();
            }
        })
    },

    // filter(searchItem, searchKey, status, start, end, fn) {
    //     AXIOS.get(`article/search?` + searchItem + `=` + searchKey + `status=` + status + `&startDate=` + start + `&endDate=` + end)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             console.log(error);
    //             if (error.response.status === 401) {
    //                 router.push('/login');
    //             }
    //         });
    // },

    filterAll(body, status, start, end, mov, fn) {
        AXIOS.get(`article/filter?` + body + `status=` + status + `&startDate=` + start + `&endDate=` + end + `&mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    // searchHash(searchKey, status, start, end, fn) {
    //     AXIOS.get(`article/search?hash=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             console.log(error);
    //             if (error.response.status === 401) {
    //                 router.push('/login');
    //             }
    //         });
    // },
    //
    // searchAuthor(searchKey, status, start, end, fn) {
    //     AXIOS.get(`article/search?author=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             console.log(error);
    //             if (error.response.status === 401) {
    //                 router.push('/login');
    //             }
    //         });
    // },
    //
    // searchLanguage(searchKey, status, start, end, fn) {
    //     AXIOS.get(`article/search?language=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             console.log(error);
    //             if (error.response.status === 401) {
    //                 router.push('/login');
    //             }
    //         });
    // },
    //
    searchText(searchKey, status, start, end, mov, fn) {
        AXIOS.get(`article/search?text=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end + `&mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    searchDescription(searchKey, status, start, end, mov, fn) {
        AXIOS.get(`article/search?description=` + encodeURIComponent(searchKey) + `&status=` + status + `&startDate=` + start + `&endDate=` + end + `&mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    searchPeriodAndStatus(status, start, end, mov, fn) {
        AXIOS.get(`article/filter?status=` + status + `&startDate=` + start + `&endDate=` + end + `&mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    error401Handling() {
        localStorage.clear();
        router.push('/login');
    },
}


