import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    // getAllEvent(fn) {
    //     return AXIOS
    //         .get(`/event`)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             if (error.response.status === 401) {
    //                 router.push('/login');
    //             }
    //         })
    // },


    getAllEvents(mov, page, size, fn) {
        return AXIOS
            .get(`/event?mov=` + mov + `&page=` + page + `&size=` + size)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    // getQuantAllEntities(mov, fn) {
    //     return AXIOS
    //         .get(`/event/allQuant?mov=` + mov)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             console.log(error);
    //             if (error.response.status === 401) {
    //                 this.error401Handling();
    //             }
    //         })
    // },

    getAllTags(fn) {
        return AXIOS
            .get(`/event/allTags`)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    searchTag(q, fn) {   //todo to delete?
        return AXIOS
            .get(`/event/allTags?q=` + q)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    update(id, event, fn) {
        AXIOS
            .put('/event/' + id, event)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    updateColor(id, event, fn) {
        AXIOS
            .put('/event/color/' + id, event)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    create(event, fn, fnError) {
        AXIOS
            .post('/event', event)
            .then(response => fn(response))
            .catch(error => {
                if (error.response.status === 401) {
                    this.error401Handling();
                } else {
                    fnError(error.response.data);
                }
            })
    },

    findById(id, fn) {
        AXIOS
            .get(`/event/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error.response.data);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },
    // getProjectsByIds(idList, fn) {
    //     AXIOS
    //         .post(`/project/ids`, idList)
    //         .then(response => fn(response))
    //         .catch(error => {
    //             console.log(error);
    //             if (error.response.status === 401) {
    //                 this.error401Handling();
    //             }
    //         });
    // },

    getEventsByIdsAndSymmetrically(id, fn) {
        AXIOS
            .post(`/event/symmids/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    searchEvent(searchKey, fn) {
        AXIOS.get(`/event/search?q=` + encodeURIComponent(searchKey))
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
    },

    filterAll(body, mov, page, size, fn) {
        AXIOS.get(`event/filter?` + body + `&mov=` + mov + `&page=` + page + `&size=` + size)
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


