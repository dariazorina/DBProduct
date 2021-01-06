<template>
    <div class="col-12" style="background-color: transparent; margin-top: -12px; margin-bottom: -12px">
        <!--        <label>Тип связи</label>-->
<!--        @change.native="tt"-->

        <b-form-select v-model="currentItem" selected="existedCommit" class="mb-3">
            <option v-for="type in allTypes" v-bind:value="type.type">{{type.type}}</option>
        </b-form-select>
    </div>
</template>

<script>
    export default {
        props: {
            existedComment: {
                type: String,
                required: true
            },

            allTypes: {
                required: true
            },
        },
        data: () => ({
            itemMap: null,
        }),


        methods: {

            // tt() {
            //     console.log("ttttttttttttt exis", this.existedComment);
            //     console.log("ttttttttttttt currIt", this.currentItem);
            // },

        },

        mounted() {
            console.log("mounted SELCTION this.existedComment", this.existedComment);
            console.log("mounted SELCTION this.currentItem", this.currentItem);
            this.itemMap = this.existedComment;
        },
        computed: {
            currentItem: {
                get() {
                    return this.itemMap
                },
                set(v) {
                    this.itemMap = v
                }
            }
        },
        watch: {
            existedComment:function(v) {
                console.log("SELCTION existedComment, watch");
                this.itemMap = v;
                // selectedOrg: function () {
                // this.$emit("update-selectedOrg", this.selectedOrg);
            },

            currentItem:function(v) {
                console.log("SELECTION currentItem, watch", this.currentItem);
                this.itemMap = v;
                // selectedOrg: function () {
                // this.$emit("update-selectedOrg", this.selectedOrg);

                this.$emit("update-selectedOrg", v);
            },
        },
    }
</script>

<style lang="scss" scoped>
    .input {
        width: 100%;
        padding: 8px 10px;
        border: 1px solid #32485F;
    }
</style>
