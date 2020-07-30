<template>
    <div style="background-color: transparent; margin-left: 0px; margin-top: -20px; margin-bottom: -30px; margin-right: -20px">

        <div class="form-row"
             style="background-color: transparent; margin-left: -25px; margin-top: -15px; margin-right: -5px; margin-bottom: -15px">

            <BaseSelectOrg :allOrgs="allOrgs" :selected="selected" :key="componentKey" @update-selectedOrg="updateOrg"/>
            <BaseInputOccupationText
                    v-model="newTodoText"
                    @keydown.enter="addTodo"
            />

            <BaseInputCommentText
                    v-model="newCommentText"
                    @keydown.enter="addTodo"
            />

            <ul v-if="todos.length">
                <OccupationListItem
                        v-for="todo in todos"
                        :key="todo.id"
                        :todo="todo"
                        @remove="removeTodo"
                />
            </ul>
            <p v-else style="background-color: transparent; margin-left: 7px; margin-top: -5px; margin-right: -5px">
                Nothing left in the list. Add new information in the input above.
            </p>
        </div>
    </div>
</template>

<script>
    import BaseInputOccupationText from './BaseInputOccupationText.vue'
    import OccupationListItem from './OccupationListItem.vue'
    import BaseSelectOrg from "./BaseSelectOrg";
    import BaseInputCommentText from "./BaseInputCommentText";

    let nextTodoId = 1;

    export default {
        components: {
            BaseInputCommentText,
            BaseInputOccupationText, OccupationListItem, BaseSelectOrg
        },
        props: {
            allOrgs: {
                required: true
            },
        },
        data() {
            return {
                componentKey: 0,
                renderComponent: true,
                selected: null,
                selectedOrg: '',
                newTodoText: '',
                newCommentText: '',
                todos: [
                    // {
                    //     id: nextTodoId++,
                    //     text: 'Learn Vue',
                    //     org: 'Org1'
                    // },
                    // {
                    //     id: nextTodoId++,
                    //     text: 'Learn about single-file components',
                    //     org: 'Org2'
                    // },
                    // {
                    //     id: nextTodoId++,
                    //     text: 'Fall in love',
                    //     org: 'Org3'
                    // }
                ]
            }
        },
        methods: {
            updateOrg(selectedOrg) {
                this.selectedOrg = selectedOrg;
            },
            addTodo() {
                // console.log("newTodoText = = = = =", this.newTodoText, this.selectedOrg);
                const trimmedPositionText = this.newTodoText.trim();
                const trimmedCommentText = this.newCommentText.trim();

                if (trimmedPositionText && this.selectedOrg) {
                    let currentOccupation = {
                        "id": nextTodoId++,
                        "org": this.allOrgs.find(org => org.id === this.selectedOrg).name,
                        "orgId": this.selectedOrg,
                        "position": trimmedPositionText,
                        "comment": trimmedCommentText
                    };

                    this.$emit("update-occupation", currentOccupation);
                    this.todos.push(currentOccupation);

                    // this.todos.push({
                    //     id: nextTodoId++,
                    //     text: trimmedText
                    // });

                    this.newTodoText = '';
                    this.newCommentText = '';
                    this.selected = null;
                    this.selectedOrg = null;

                    this.componentKey += 1;  //to rerender org selection element
                }
            },

            removeTodo(idToRemove) {
                this.todos = this.todos.filter(todo => {
                    return todo.id !== idToRemove
                })
            }
        }
    }
</script>