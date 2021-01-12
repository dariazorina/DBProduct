<template>
    <div class="col-12" style="background-color: transparent; margin-top: -12px; ; margin-bottom: -12px">
        <input class="form-control" type="text" v-model="currentItem" v-on="listeners" placeholder="Комментарий"/>
    </div>
</template>

<script>
    export default {
        props: {
            existedComment: {
                type: String,
                required: true
            },
        },
        data() {
            return {
                itemMap: null
            }
        },
        mounted() {
            this.itemMap = this.existedComment;
        },
        computed: {
            listeners() {
                // console.log("listeners");
                return {
                    // Pass all component listeners directly to input
                    ...this.$listeners,
                    // Override input listener to work with v-model
                    input: event => this.$emit('input', event.target.value)
                }
            },
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
            existedComment(v) {
                this.itemMap = v;
                console.log("WATCH", this.itemMap);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .input {
        width: 100%;
        padding: 8px 10px;
        border: 1px solid #32485F;
    }
</style>
