<template>
    <div style="padding-left: -50px; background-color: transparent">
        <li>
            {{ todo.org}},  {{ todo.connection }},  {{todo.comment}}
            <button class="btn btn-link" style="background-color: #EAF2F5" @click="$emit('remove', todo.id)">
                delete
            </button>
        </li>
    </div>
</template>

<script>
    export default {
        props: {
            todo: {
                type: Object,
                required: true
            }
        }
    }
</script>