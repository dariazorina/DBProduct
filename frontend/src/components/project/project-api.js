import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    getAllProjects(mov, page, size, fn) {
        return AXIOS
            .get(`/project?mov=` + mov + `&page=` + page + `&size=` + size)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    getQuantAllEntities(mov, fn) {
        return AXIOS
            .get(`/project/allQuant?mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    getAllTags(fn) {
        return AXIOS
            .get(`/project/allTags`)
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
            .get(`/project/allTags?q=` + q)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },


    update(id, project, fn) {
        AXIOS
            .put('/project/' + id, project)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    updateColor(id, project, fn) {
        AXIOS
            .put('/project/color/' + id, project)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    create(project, fn, fnError) {
        AXIOS
            .post('/project', project)
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
            .get(`/project/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error.response.data);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },
    getProjectsByIds(idList, fn) {
        AXIOS
            .post(`/project/ids`, idList)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    getProjectsByIdsAndSymmetrically(id, fn) {
        AXIOS
            .post(`/project/symmids/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    searchProject(searchKey, fn) {
        AXIOS.get(`/project/search?q=` + encodeURIComponent(searchKey))
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

    filterAll(body, mov, fn) {
        AXIOS.get(`project/filter?` + body + `&mov=` + mov)
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


