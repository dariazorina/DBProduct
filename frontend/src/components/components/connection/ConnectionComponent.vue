<template>
    <div class="form-row col-12"
         style="background-color: transparent;
             margin-left: 0px; margin-top: 0; margin-right: 0px; margin-bottom: 0px">

        <ul v-if="itemsList.length" style="background-color: transparent;
            margin: 0; padding:0" class="form-row col-12">
            <li v-for="item in connections"
                class="list-group-item d-flex justify-content-between align-items-center form-row col-12"
                style="background-color: transparent;
                    padding: 0; margin: 0">

                <ConnectionListItem class="col-12"
                                    style="background-color: transparent;
                                    margin-left: 0px; margin-top: 0px; margin-bottom: 0px; margin-right: 0px;
                                    padding-left: 0px; padding-top: 5px; padding-right: 0"

                                    :item="item"
                                    :hasClicked="item.hasClicked"
                                    :allTypes = "allTypes"
                                    :isEditMode="isEditMode"
                                    :isSelectionMode="isSelectionMode"
                                    @remove="removeItem"
                                    @get-input-text="addItem"
                                    @save-input-text="saveItem"
                                    @update-selection="updateSelection"
                                  />


            </li>
        </ul>
    </div>
</template>

<script>
    import ConnectionListItem from "./ConnectionListItem";

    export default {
        components: {
            ConnectionListItem,
        },
        props: {
            itemsList: {
                type: Array,
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
            allTypes: {
                type: Array,
                required: true
            },
        },
        methods: {
            updateSelection(item){
                console.log("upd selection ConnCompomemt", item);
                this.$emit("update-selection", item);
            },

            addItem(item) {
                // console.log("------addItem CONN-COMP---------", item);
                item.connection = item.connection.trim();
                item.comment = item.comment.trim();
                item.hasClicked = true;

                this.$emit("update-item", item);
            },

            saveItem(item) {
                // console.log("------SAVE item CONN-COMP---------", item);
                item.connection = item.connection.trim();
                item.comment = item.comment.trim();
                item.hasClicked = true;

                this.$emit("update-item", item);
            },

            removeItem(idToRemove) {
                // console.log("------full list -- toRemove---------", this.itemsList);
                for (let i = 0; i < this.itemsList.length; i++) {
                    if (this.itemsList[i].id === idToRemove) {
                        // console.log("------idtem To R e m o v e ---------", this.itemsList[i]);
                        this.itemsList.splice(i, 1);
                    }
                }
            }
        },

        computed: {
            connections() {
                console.log("COMPUTED CONN-COMP", this.isEditMode);
                return this.itemsList;
            }
        },
    }
</script>