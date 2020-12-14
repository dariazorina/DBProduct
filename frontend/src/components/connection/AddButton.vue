<template>
    <div v-if="isEditMode"
         style="padding-left: 0px; padding-right: 0px; margin-right: 20px; background-color: transparent">
        <!--        <button type="button" class="btn btn-primary" v-bind:disabled="isDisable" @click="$emit('add', item)">-->
        <!--            Сохранlf-->
        <!--        </button>-->
        <v-btn text icon small v-bind:disabled="isDisable" @click="$emit('save', item)">
            <v-icon style="color: green">mdi-content-save-outline</v-icon>
        </v-btn>

    </div>
    <div v-else style="padding-left: -50px; background-color: transparent">
        <!--        <button type="button" class="btn btn-primary" v-bind:disabled="isDisable" @click="$emit('add', item)">-->
        <!--            Добавить-->
        <!--        </button>-->
        <v-btn text icon small v-bind:disabled="isDisable" @click="$emit('add', item)">
            <v-icon style="color: green">mdi-plus-circle</v-icon>
        </v-btn>
    </div>

</template>

<script>
    export default {
        props: {
            isEditMode: {
                type: Boolean,
                required: true
            },
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
                // console.log("this.item.connection", this.item.connection);
                return (this.item.connection.length <= 0) || (this.hasClicked);
            }
        }
    }
</script>