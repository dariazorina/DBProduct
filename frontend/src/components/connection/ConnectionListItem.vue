<template>
    <div style="padding-left: 0px; padding-top: 10px; background-color: gold">
        <b>{{item.name}}</b>

        <span class="close" style="background-color: transparent; padding-right: 10px" @click="$emit('remove', item.id)">&times;</span>

        <div class="form-row col-12" style="background-color: transparent; padding:0px">
            <div class="form-row col-6"
                 style="background-color: transparent; margin-right: 0px; padding-right: 0px; padding-left:20px; padding-top: 0px; padding-bottom: 0px">
                <InputConnectionText v-model="item.connection" :existedConnection="item.connection"/>
            </div>
            <div class="form-row col-5"
                 style="background-color: transparent; padding-right: 0px; padding-left:20px; padding-top: 0px; padding-bottom: 0px">
                <InputCommentText v-model="item.comment" :existedComment="item.comment"/>
            </div>
            <div class="form-row col-1"
                 style="background-color: transparent; padding-right: 0px; padding-left:25px; padding-top: 5px; padding-bottom: 0px">
                <AddButton :item="item" :hasClicked="hasClicked" :isEditMode="isEditMode" @add="addItem" @save="saveItem" />
            </div>
        </div>
    </div>
</template>

<script>
    import InputConnectionText from "./InputConnectionText";
    import InputCommentText from "./InputCommentText";
    import AddButton from "./AddButton";

    export default {
        components: {AddButton, InputCommentText, InputConnectionText},
        props: {
            item: {
                type: Object,
                required: true
            },
            hasClicked: {
                type: Boolean,
                required: true
            },
            isEditMode: {
                type: Boolean,
                required: true
            }
        },

        methods: {
            addItem(item) {
                // console.log("--------addItem--CONN-LISTITEM-----", item);
                this.$emit("get-input-text", item); //newText
            },
            saveItem(item) {
                // console.log("--------addItem--CONN-LISTITEM-----", item);
                this.$emit("save-input-text", item); //newText
            }
        }
    }
</script>