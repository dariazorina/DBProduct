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
        },

        mounted() {
            // console.log("mounted SELCTION this.existedComment", this.existedComment);
            // console.log("mounted SELCTION this.currentItem", this.currentItem);
            this.itemMap = this.existedComment;
        },
        computed: {
            currentItem: {
                get() {
                    return this.itemMap
                },
                set(v) {
                    // console.log("-------------------+++++++-----computed: currentItem: { currentItem, watch", this.itemMap, v);
                    if (v !== this.itemMap) {
                        this.itemMap = v;
                        // console.log("*********************************SELECTION EMIT", this.currentItem, v);
                        this.$emit("update-selectedConnection", v);
                    }
                }
            }
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
