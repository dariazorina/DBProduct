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
                                    @remove="removeItem"
                                    @get-input-text="addItem"/>
                <!--                    :key="componentKey"-->
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
        },
        // data() {
        //     return {
        // componentKey: 0,
        // }
        // },
        methods: {
            addItem(item) {
                console.log("------addItem CONN-COMP---------", item);
                item.connection = item.connection.trim();
                item.comment = item.comment.trim();
                item.hasClicked = true;

                // this.componentKey += 1;  //to rerender
                this.$emit("update-item", item);
            },

            removeItem(idToRemove) {
                console.log("------idToRemove---------", idToRemove);
                for (let i = 0; i < this.itemsList.length; i++) {
                    if (this.itemsList[i].id === idToRemove) {
                        console.log("------idtem To R e m o v e ---------", this.itemsList[i]);
                        this.itemsList.splice(i, 1);
                    }
                }
            }
        },

        computed: {
            connections() {
                // for (let i = 0; i < this.itemsList.length; i++) {
                //     this.itemsList[i].connection = "author";
                //     this.itemsList[i].comment = "comment";
                // }
                console.log("COMPUTED CONN-COMP", this.itemsList);
                return this.itemsList;
            }
        },
    }
</script>