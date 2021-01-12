<template>
    <div class="col-md-4" style="background-color: transparent">
        <label>Организация</label>
        <b-form-select v-model="selectedOrg" class="mb-3">
            <option v-for="org in allOrgs" v-bind:value="org.id">{{org.name}}</option>
        </b-form-select>
    </div>
</template>

<script>
    export default {
        props: {
            allOrgs: {
                required: true
            },
        },
        data: () => ({
            selectedOrg: null,
        }),
        mounted() {
            this.selectedOrg = null;
        },
        watch: {
            selectedOrg: function () {
                this.$emit("update-selectedOrg", this.selectedOrg);
            },
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
