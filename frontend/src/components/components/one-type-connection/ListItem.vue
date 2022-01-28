<template>
    <div style="padding-left: 0px; padding-top: 10px; background-color: gold">

        <div class="form-row">
            <div class="col-11" style="background-color: transparent;
                                               margin-right: 0; margin-bottom: 0;
                                               padding: 0 0 10px">
                <div v-if="isLinkMode"
                     style="padding-left: 20px; padding-top: 10px; margin-bottom: 0; background-color: transparent">
                    <a>
                        <router-link :to="{name: 'article-details', params: {article_id: item.itemId}}" target="_blank">
                            {{item.name}}
                        </router-link>
                    </a>
                </div>
                <div v-else
                     style="padding-left: 20px; padding-top: 10px; margin-bottom: 0; background-color: transparent">
                    {{item.name}}
                </div>
            </div>

            <div class="col-1" style="background-color: transparent;
                                               margin-right: 0; margin-bottom: 0;
                                               padding: 0px;">
            <span class="close" style="background-color: transparent; padding-right: 10px"
                  @click="$emit('remove', item.itemId)">&times;</span>

                <!--            <div class="form-row col-6"-->
            </div>
        </div>
        <div class="form-row col-12" style="background-color: transparent; padding:0px">
            <div v-if="isSelectionMode" class="form-row col-6"
                 style="background-color: transparent;
                 margin-right: 0; padding: 0 0 0 20px;">

                <ConnectionTypeSelection :allTypes="allTypes"
                                         v-model="item.connection"
                                         :existedComment="item.connection"
                                         @update-selectedConnection="updateConnection"/>
            </div>
            <div v-else class="form-group col-6" style="background-color: transparent;
                                               margin-right: 0; margin-bottom: 0;
                                               padding: 0 0 0 20px;">

                <div v-if="isInternalMode" style="background-color: transparent">
                    <InternalCheckBoxInput :startValue="item.isInternal"
                                           @update-checkedItem="updateInternalType"/>
                </div>

                <div class="notbold" style="margin-left: 5px; margin-bottom: 5px; margin-top: 10px">
                    Выберите роль текущей связанной сущности по отношению к исходной создаваемой/редактируемой:
                </div>

                <RadioInput :startValue="item.isParent"
                            @update-selectedType="updateParentChildType"/>

            </div>

            <div class="form-group col-6"
                 style="background-color: transparent; padding: 0 0 0 20px;">
                <InputConnectionText v-model="item.connection"
                                     :existedConnection="item.connection"/>
                <InputCommentText v-model="item.comment" :existedComment="item.comment"/>
            </div>
        </div>
    </div>
</template>

<script>
    import InputConnectionText from "../connection/InputConnectionText";
    import InputCommentText from "../connection/InputCommentText";
    import ConnectionTypeSelection from "../connection/ConnectionTypeSelection";
    import RadioInput from "./RadioInput";
    import InternalCheckBoxInput from "./InternalCheckBoxInput";

    export default {
        components: {InternalCheckBoxInput, RadioInput, ConnectionTypeSelection, InputCommentText, InputConnectionText},
        props: {
            item: {
                type: Object,
                required: true
            },
            allTypes: {
                type: Array,
                required: true
            },
            isLinkMode: {
                type: Boolean,
                required: true
            },
            isInternalMode: {
                type: Boolean,
                required: true
            },
            isSelectionMode: {
                type: Boolean,
                required: true
            },
        },
        data: () => ({
            selectedItem: false,
            checkedItem: false,
        }),

        methods: {
            updateConnection(selected) {
                this.item.connection = selected;
                console.log("--LIST ITEM---updateOrg--SELECTION-----", this.item);
            },

            updateParentChildType(selected) {
                console.log("updateParentChildType---update---", selected);
                this.item.isParent = (selected === 1);
//                this.$emit("is-parent", selected);
            },

            updateInternalType(checked) {
                this.item.isInternal = checked;
                console.log("updateInternalType---update---", checked, this.item);
            },

            addItem(item) {
                console.log("--------addItem--CONN-LIST--ITEM-----", item);
                this.$emit("get-input-text", item); //newText
            },
            saveItem(item) {
                console.log("--------saveItem--CONN-LISTITEM-----", item);
                this.$emit("save-input-text", item); //newText
            }
        },
    }
</script>