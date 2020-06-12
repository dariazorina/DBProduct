<template id="article-delete">
    <div>


        <div class="form-group row">
            <div class="col-2 col-form-label">
                <p class="pageDeleteTitle">Delete article <i> "{{ article.title}}"</i></p>
            </div>
        </div>

<!--        <h4>Delete article <i> "{{ article.title}}"</i>?</h4>-->
        <p>The action cannot be undone</p>



        <button type="button" @click="deleteArticle" class="btn btn-danger">Delete</button>
        <a class="btn btn-default">
            <router-link to="/article">Cancel</router-link>
        </a>
    </div>
</template>

<script>
    import api from "./article-api";
    import router from "./../../router";

    export default {
        name: 'article-delete',
        data() {
            return {
                article: []
            }
        },
        methods: {
            deleteArticle: function () {
                api.delete(this.article.id, r => router.push('/article'))
            }
        },
        mounted() {
            api.findById(this.$route.params.article_id, r => {
                console.log("MESSAGE");
                console.log(this.article);
                this.article = r.data
            });
        }
    }
</script>

