<template>
    <div class="col-6" style="background-color: transparent; margin-top: -12px; margin-bottom: -12px">
        <div v-for="item in items">
            <input style="margin-right: 5px;" v-bind:value="item.id"
                   type="radio"
                   v-model="selectedItem"/>
            <label style="margin: 5px; padding-top: 0; padding-bottom: 3px; background-color: transparent"
                   :for="item.id"><span>{{item.name}}</span></label>
        </div>
    </div>
</template>

<script>
    export default {

        props: {
            startValue: {
                type: Boolean,
                required: true
            },
        },
        data: () => ({
            item: 0,
            items: [
                {
                    id: 1,
                    name: "родитель"
                },
                {
                    id: 0,
                    name: "потомок"
                }],
        }),

        methods:
            {},

        mounted() {
            console.log("mounted START VALUE", this.startValue);
            if (this.startValue === true) {
                this.selectedItem = 1;
            } else {
                this.selectedItem = 0;
            }
        },

        computed: {
            selectedItem: {
                get() {
                    return this.item;
                },

                set(newValue) {
                    console.log("-------------------+++++++-----computed: ", this.item, newValue);
                    this.item = newValue;
                    this.$emit("update-selectedType", newValue);
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
