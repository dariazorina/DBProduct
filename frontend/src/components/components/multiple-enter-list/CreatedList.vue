<template>
    <div style="background-color: transparent; margin-left: 0px; margin-top: -20px; margin-bottom: 0px; margin-right: 0px">

        <div class="form-row"
             style="background-color: transparent">
            <!--             style="background-color: transparent; margin-left: -25px; margin-top: -15px; margin-right: -5px; margin-bottom: -15px">-->

            <InputTextField
                    style="background-color: transparent"
                    v-model="newInputText"
                    @keydown.enter="addItem"/>

            <ul v-if="items.length">
                <CreatedListItem
                        v-for="item in items"
                        :item="item"
                        :key="item.id"
                        @remove="removeItem"/>

            </ul>
        </div>
    </div>
</template>

<script>
    import CreatedListItem from './CreatedListItem.vue'
    import InputTextField from "./InputTextField";

    let nextItemId = 1;

    export default {
        components: {
            InputTextField, CreatedListItem
        },
        props: {
            items: {
                type: Array,
                required: true
            },
        },
        data() {
            return {
                componentKey: 0,
                renderComponent: true,
                newInputText: '',
            }
        },
        methods: {

            addItem() {
                console.log("newTodoText = = = = =", this.newInputText);
                const trimmedInputText = this.newInputText.trim();

                if (trimmedInputText) {
                    let currentItem = {
                        "id": nextItemId++,
                        "content": trimmedInputText
                    };

                    // this.$emit("update-item", trimmedInputText);
                    this.$emit("update-item", currentItem);
                    this.newInputText = '';
                    this.componentKey += 1;  //to rerender org selection element
                }
            },

            removeItem(idToRemove) {
                console.log("item to remove", idToRemove);
                for (let i = 0; i < this.items.length; i++) {
                    if (this.items[i].id === idToRemove) {
                        this.items.splice(i, 1);
                    }
                }
            }
        },
        computed: {
            inputItems() {
                console.log("computed inputItems", this.items);
                return this.items;
            }
        },
    }
</script>