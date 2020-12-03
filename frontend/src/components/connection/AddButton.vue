<template>
    <div style="padding-left: -50px; background-color: transparent">
        <button type="button" class="btn btn-primary" v-bind:disabled="isDisable" @click="$emit('add', item)">
            Добавить
        </button>
    </div>
</template>

<script>
    export default {
        props: {
            hasClicked: {
                type: Boolean,
                required: true
            },
            item: {
                type: Object,
                required: true
            }
        },

        computed: {
            isDisable() {
                // console.log("this.item.connection.length", this.item.connection.length);
                return (this.item.connection.length <= 0)||(this.hasClicked);
            }
        }
    }
</script>