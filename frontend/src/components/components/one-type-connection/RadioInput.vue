<template>
    <div class="col-6" style="background-color: transparent; margin-top: -12px; margin-bottom: -12px">
        <!--        <label>Тип связи</label>-->
        <!--        @change.native="tt"-->

        <div v-for="item in items">
            <input style="margin-right: 5px;" v-bind:value="item.id" :name="currentItem.id"
                   type="radio"
                   v-model="selectedItem"/>
            <label style="margin: 5px; padding-top: 0px; padding-bottom: 3px; background-color: transparent"
                   :for="item.id"><span>{{item.name}}</span></label>
        </div>

<!--        <div class="radio-button-group">-->
<!--            <label :for="item.name" v-for="item in items">-->
<!--                <input type="radio" name="radio-input" :value="item.id"-->
<!--                       :id="item"/>-->
<!--&lt;!&ndash;                       @change="$emit('input', item)" />&ndash;&gt;-->
<!--                {{ item.name}}-->
<!--            </label>-->
<!--        </div>-->

<!--        <div class="radio-button-group">-->
<!--            <label :for="item.name" v-for="item in items">-->
<!--                <input type="radio" name="radio-input111" :value="item.id"-->
<!--                       :id="item"/>-->
<!--                &lt;!&ndash;                       @change="$emit('input', item)" />&ndash;&gt;-->
<!--                {{ item.name}}-->
<!--            </label>-->
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

            currentItem: {
                type: Object,
                required: true
            },


            // items: {
            //     type: Array,
            //     required: true
            // },


            // allTypes: {
            //     required: true
            // },
        },
        data: () => ({
        // data: function () {
        //     return {item: 0}

            item:0,

            items: [{
                id: 1,
                name: "родитель"
            },
                {  id: 0,
                    name: "потомок"
                }],

            }),
        //},

        methods:
            {},

        mounted() {
            // console.log("mounted SELCTION this.existedComment", this.existedComment);
            // console.log("mounted SELCTION this.currentItem", this.currentItem);
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
                    console.log("-------------------+++++++-----computed: currentItem: { currentItem, watch", this.item, newValue);

                   // if (newValue !== this.item) {
                        this.item = newValue;
                        console.log("*********************************SELECTION EMIT", newValue);
                        //this.startValue = newValue;
                        this.$emit("update-selectedType", newValue);

                   // }
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
