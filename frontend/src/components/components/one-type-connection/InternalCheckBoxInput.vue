<template>
    <div class="col-6" style="background-color: transparent; margin-top: -12px; margin-bottom: -12px">

        <input style="margin-right: 5px; margin-top: 15px;"
               type="checkbox"
               v-model="checkedItem"/>
        <label style="margin: 5px; padding-top: 0px; padding-bottom: 3px; background-color: transparent"><span>{{"внутренний"}}</span></label>


<!--        <div v-for="item in items">-->
<!--            <input style="margin-right: 5px;" v-bind:value="item.id"-->
<!--                   type="radio"-->
<!--                   v-model="selectedItem"/>-->
<!--            <label style="margin: 5px; padding-top: 0px; padding-bottom: 3px; background-color: transparent"-->
<!--                   :for="item.id"><span>{{item.name}}</span></label>-->
<!--        </div>-->
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
            item: false,
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
                this.checkedItem = true;
            } else {
                this.checkedItem = false;
            }
        },

        computed: {
            checkedItem: {
                get() {
                    return this.item;
                },

                set(newValue) {
                    console.log("-------------------+++++++-----computed: ", this.item, newValue);
                    this.item = newValue;
                    this.$emit("update-checkedItem", newValue);
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
