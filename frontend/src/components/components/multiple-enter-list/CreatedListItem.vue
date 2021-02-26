<template>
    <div style="background-color: transparent; margin-left: 20px; text-align: left">
        <li>
            {{ item.content }}
            <!--            <button class="btn btn-link" style="background-color: #EAF2F5" @click="$emit('remove', item.id)">-->
            <!--                delete-->
            <!--            </button>-->

            <v-btn text icon x-small @click="$emit('remove', item.id)">
                <v-icon style="color: red">mdi-delete-forever</v-icon>
            </v-btn>
        </li>
    </div>
</template>

<script>
    export default {
        props: {
            item: {
                type: Object,
                required: true
            }
        }
    }
</script>