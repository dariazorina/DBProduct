import axios from 'axios'
import router from "../router";

const AXIOS = axios.create({
    baseURL: `/api/v1/`,
    timeout: 10000
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

    uploadPhoto(entity, id, file, fn) {
        let formData = new FormData();
        formData.append('file', file);
        AXIOS
            .post('/' + entity + '/attachmentPhoto/' + id,
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


    deletePhoto(entity, id, fn) {
        AXIOS
            .delete('/' + entity + '/attachmentPhoto/' + id)
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    removeAttachment(entity, entityId, fileId, fileName, fn) {
        AXIOS
            // .delete('/' + entity + '/attachment/' + id)
            .delete('/' + entity + '/attachment?entityId=' + entityId + `&fileId=`+ fileId + `&fileName=` + encodeURIComponent(fileName))
            .then(response => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
            });
    },

    getAttachmentPhoto(entity, id, fn) {
        AXIOS
            .get('/' + entity + '/attachmentPhoto/' + id)
            .then((response) => fn(response))
            .catch(error => {
                console.log(error);
                if (error.response.status === 401) {
                    router.push('/login');
                }
                if (error.response.status === 400 || error.response.status === 500) {
                    console.log("No such file");
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
    },

      previewAttachment(entity, entityd, id) {
        let url = '/api/v1/' + entity + '/attachment?entityId=' + entityd + '&id=' + id;
        console.log("PREVIEW ATTACHMENT", url);
        window.open('/api/v1/' + entity + '/attachment?entityId=' + entityd + '&id=' + id, '_blank', `scrollbars=no,resizable=no,status=no,location=no,toolbar=no,menubar=no`);
    }
}

