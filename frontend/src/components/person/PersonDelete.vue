<template id="person-delete">
    <div>
        <h4>Delete person <i> "{{ person.surname}}"</i>?</h4>
        <p>The action cannot be undone</p>
        <button type="button" @click="deletePerson" class="btn btn-danger">Delete</button>
        <a class="btn btn-default">
            <router-link to="/article">Cancel</router-link>
        </a>
    </div>
</template>

<script>
    import api from "./person-api";
    import router from "./../../router";

    export default {
        name: 'person-delete',
        data() {
            return {
                person: []
            }
        },
        methods: {
            deletePerson: function () {
                api.delete(this.person.id, r => router.push('/person'))
            }
        },
        mounted() {
            api.findById(this.$route.params.person_id, r => {
                console.log("MESSAGE");
                console.log(this.person);
                this.person = r.data
            });
        }
    }
</script>

