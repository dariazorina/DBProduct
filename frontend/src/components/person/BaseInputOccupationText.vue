<template>
    <div class="col-md-6">
        <label>Должность</label>
        <div class="form-row" style="background-color: transparent">
            <div class="col-md-12" style="background-color: transparent; margin-top: -12px">
                <input class="form-control" type="text" :value="value" v-on="listeners" placeholder="Для сохранения введенных данных нажмите 'Enter'"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {

            value: {
                type: String,
                // default: '',
            }
        },
        computed: {
            listeners() {
                return {
                    // Pass all component listeners directly to input
                    ...this.$listeners,
                    // Override input listener to work with v-model
                    input: event => this.$emit('input', event.target.value)
                }
            },
        }
    }
</script>

<style lang="scss" scoped>
    .input {
        width: 100%;
        padding: 8px 10px;
        border: 1px solid #32485F;
    }
</style>
