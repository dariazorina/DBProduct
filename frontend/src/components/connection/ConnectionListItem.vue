<template>
    <div style="padding-left: 0px; padding-top: 10px; background-color: gold">
        <b>{{item.name}}</b>

        <span class="close" style="background-color: transparent; padding-right: 10px"
              @click="$emit('remove', item.id)">&times;</span>

        <!--            <div class="form-row col-6"-->
        <div class="form-row col-12" style="background-color: transparent; padding:0px">
            <div v-if="isSelectionMode" class="form-row col-6"
                 style="background-color: transparent;
                 margin-right: 0px; padding-right: 0px;
                 padding-left:20px; padding-top: 0px; padding-bottom: 0px">

                <ConnectionTypeSelection :allTypes="allTypes"
                                         v-model="item.connection"
                                         :existedComment="item.connection"
                                         @update-selectedConnection="updateConnection"/>
            </div>
            <div v-else class="form-row col-6" style="background-color: transparent;
                                               margin-right: 0px;
                                               padding-right: 0px; padding-left:20px; padding-top: 0px; padding-bottom: 0px">

                <InputConnectionText v-model="item.connection"
                                     :existedConnection="item.connection"
                                     @has-clicked="hasClickedChanged(item)"/>
            </div>

            <div class="form-row col-5"
                 style="background-color: transparent; padding-right: 0px; padding-left:20px; padding-top: 0px; padding-bottom: 0px">
                <InputCommentText v-model="item.comment" :existedComment="item.comment"/>
            </div>
            <div class="form-row col-1"
                 style="background-color: transparent; padding-right: 0px; padding-left:25px; padding-top: 5px; padding-bottom: 0px">
<!--                :hasClicked="item.hasClicked"-->
                <AddButton :item="item"
                           :hasClicked="hasClicked"
                           :isEditMode="isEditMode"
                           :isSelectionMode="isSelectionMode"
                           @add="addItem"
                           @save="saveItem"/>
            </div>
        </div>
    </div>
</template>

<script>
    import InputConnectionText from "./InputConnectionText";
    import InputCommentText from "./InputCommentText";
    import AddButton from "./AddButton";
    import ConnectionTypeSelection from "./ConnectionTypeSelection";

    export default {
        components: {ConnectionTypeSelection, AddButton, InputCommentText, InputConnectionText},
        props: {
            item: {
                type: Object,
                required: true
            },
            allTypes: {
                type: Array,
                required: true
            },
            hasClicked: {
                type: Boolean,
                required: true
            },
            isEditMode: {
                type: Boolean,
                required: true
            },
            isSelectionMode: {
                type: Boolean,
                required: true
            },

        },

        methods: {
            hasClickedChanged(item){
                item.hasClicked = false;
                // console.log("hasClickedChanged", item);
            },

            updateConnection(selected) {
                this.item.connection = selected;
                this.item.hasClicked = false;
                // console.log("--LIST ITEM---updateOrg--SELECTION-----", this.item);
            },

            addItem(item) {
                 // console.log("--------addItem--CONN-LIST--ITEM-----", item);
                this.$emit("get-input-text", item); //newText
            },
            saveItem(item) {
                // console.log("--------addItem--CONN-LISTITEM-----", item);
                this.$emit("save-input-text", item); //newText
            }
        }
    }
</script>