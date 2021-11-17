import axios from 'axios'
import router from "../../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
});


export default {
    getAllOrgs(mov, fn) {
        return AXIOS
            .get(`/org?mov=` + mov)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    update(id, org, fn) {
        AXIOS
            .put('/org/' + id, org)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    updateColor(id, org, fn) {
        AXIOS
            .put('/org/color/' + id, org)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },

    create(org, fn, fnError) {
        AXIOS
            .post('/org', org)
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
            .get(`/org/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            })
    },
    getOrgsByIds(idList, fn) {
        AXIOS
            .post(`/org/ids`, idList)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    getOrgsByIdsAndSymmetrically(id, fn) {
        AXIOS
            .post(`/org/symmids/` + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    this.error401Handling();
                }
            });
    },

    searchOrg(searchKey, fn) {
        AXIOS.get(`/org/search?q=` + encodeURIComponent(searchKey))
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

    error401Handling() {
        localStorage.clear();
        router.push('/login');
    },
}


