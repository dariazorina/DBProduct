import axios from 'axios'
import router from "../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 1000
});


export default {
    upload(entity, id, file, fn) {
        AXIOS
            .post(entity + '/attachment/' + id, file)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    uploadFile(entity, id, file, fn) {
        let formData = new FormData();
        formData.append('file', file);
        AXIOS
            .post('/' + entity + '/attachment/' + id,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    getAttachments(entity, id, fn) {
        AXIOS
            .get('/' + entity + '/attachments/' + id)
            .then((response) => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    }
}


