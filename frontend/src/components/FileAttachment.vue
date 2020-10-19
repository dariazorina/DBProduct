<template id="file-attachment">

    <!--    <form class="formCreation">-->
    <b-card style="background-color: transparent">
        <!--        <div class="col-md-12" style="margin-top: 0; background-color: transparent">-->
        <label>Attach Files</label>
        <div style="margin-top: 0px; background-color: transparent">
            <input type="file" accept="*" multiple @change="onChange"/>
        </div>


        <template v-if="!attachedFiles.length">
            <!--               Здесь могла бы быть Ваша реклама ,)-->
        </template>

        <template v-else>
            <div style="margin-top: 30px; background-color: transparent">
                <!--                    <div v-for="node in attachedFiles">-->
                <!--                        <v-btn text icon x-small @click="removeSelectedHashtag(node.name)">-->
                <!--                            <v-icon style="color: red">mdi-delete-forever</v-icon>-->
                <!--                        </v-btn>-->
                <!--                        {{ node.name }}-->
                <!--                    </div>-->


                <table class="greyGridTable" style="background-color: transparent">
                    <thead>
                    <tr>
                        <th class='tdTitle'>Имя</th>
                        <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat">Дата создания</th>
                        <th class='tdTitle'>Автор</th>
                        <th class='tdTitle'>Размер</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="file in attachedFiles">
                        <td>
                            {{file.name}}
                        </td>
                        <td>
                            {{ formatDate(file.date) }}
                        </td>
                        <td>
                            {{userName}}
                        </td>
                        <td>
                            {{formatSize(file.size)}}
                        </td>
                    </tr>
                    </tbody>
                </table>


                <!--                    <div class="form-group row" style="padding-top: 30px">-->

                <!--                        <button type="button" style="margin-right: 20px; margin-left: 15px"-->
                <!--                                @click="addHashtagToArticleList()"-->
                <!--                                class="btn btn-success">Add-->
                <!--                        </button>-->

                <!--                        <button type="button" class="btn btn-info"-->
                <!--                                @click="clearAllSelectedTags()">Clear All-->
                <!--                        </button>-->
                <!--                    </div>-->
            </div>
        </template>
        <!--        </div>-->
    </b-card>
    <!--    </form>-->

</template>

<script>

    import moment from "moment";

    export default {
        name: "FileAttachment",
        // vuetify: new Vuetify(),

        // props: ['fileAttachProp'],

        props: {
            userName: {
                type: String
            },
        },

        data: () => ({
            attachedFiles: [],
        }),

        methods: {
            onChange(e) {
                // console.log("files", e.target.files)

                for (let i = 0; i < e.target.files.length; i++) {
                    this.attachedFiles.push(e.target.files[i]);
                }

                console.log("ON CHANGE", this.attachedFiles);
                this.$emit('onChange', this.attachedFiles);
            },

            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
            },

            formatSize(size) {
                var i = Math.floor( Math.log(size) / Math.log(1024) );
                return ( size / Math.pow(1024, i) ).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
            },

            // removeSelectedHashtag(hash) {
            //     const index = this.selectedHashtag.indexOf(hash);
            //     if (index > -1) {
            //         this.selectedHashtag.splice(index, 1);
            //     }
            // },
            // clearAllSelectedTags() {
            //     this.selectedHashtag = [];
            // },
            // addHashtagToArticleList(hashtag) {//from HashtagList
            //     this.tags = hashtag;
            // },
        }

    }
</script>

<style scoped>

</style>