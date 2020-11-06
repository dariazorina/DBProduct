<template id="file-attachment">

    <b-card style="background-color: transparent">
        <!--        <label>Attach Files</label>-->

        <template v-if="alreadyUploadedFiles.length > 0">
            <div class="col-12" style="margin-bottom: 30px; background-color: transparent">
                <label style="color: orange; font-size: 15px; font-weight: bold;">Already Uploaded Files</label>
                <table class="greyGridTable" style="background-color: transparent" :key="componentKey">
                    <thead>
                    <tr>
                        <th class='tdTitle'></th>
                        <th class='tdTitle'>Имя</th>
                        <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat">Дата создания</th>
                        <th class='tdTitle'>Автор</th>
                        <th class='tdTitle'>Размер</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="file in alreadyUploadedFiles">
                        <td>
                            <v-btn text icon x-small @click="downloadAttachment(file)">
                                <v-tooltip bottom>
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-icon
                                                color="primary"
                                                dark
                                                v-bind="attrs"
                                                v-on="on">
                                            mdi-download
                                        </v-icon>
                                    </template>
                                    <span>download</span>
                                </v-tooltip>
                            </v-btn>
                        </td>
                        <td>
                            <a href="javascript:void(0)" @click="getAttachment(file)">{{file.name}}</a>
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
            </div>
        </template>


        <div class="col-12">
            <label style="color: orange; font-size: 15px; font-weight: bold;">Attach Files</label>
            <div>
                <input type="file" accept="*" multiple @change="attachFiles"/>
            </div>
        </div>


        <template v-if="attachedFiles.length>0">
            <div class="col-12" style="margin-top: 30px;">
                <label style="color: orange; font-size: 15px; font-weight: bold;">Attachments</label>
                <!--                            <div style="margin-top: 30px; background-color: pink">-->
                <!--                                    <div v-for="node in attachedFiles">-->
                <!--                                        <v-btn text icon x-small @click="removeSelectedHashtag(node.name)">-->
                <!--                                            <v-icon style="color: red">mdi-delete-forever</v-icon>-->
                <!--                                        </v-btn>-->
                <!--                                        {{ node.name }}-->
                <!--                                    </div>-->


                <table class="greyGridTable" style="background-color: transparent" :key="componentKey">
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
                        <!--                        <td>-->
                        <!--                            1111-->
                        <!--                            <v-btn text icon x-small v-on:click="kkk">-->
                        <!--&lt;!&ndash;                            <v-btn text icon x-small @click="removeSelectedFile(file.name)">&ndash;&gt;-->
                        <!--                                <v-icon style="color: red">mdi-delete-forever</v-icon>-->
                        <!--                            </v-btn>-->
                        <!--                        </td>-->
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
        <template v-else>
        </template>
    </b-card>

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
            alreadyUploadedFiles: {
                type: Array
            },
        },

        data: () => ({
            attachedFiles: [],
            componentKey: 0,
        }),

        methods: {
            downloadAttachment(file) {
                console.log("DOWNLD Attachment", file);
                this.$emit('downloadAttachment', file);
            },

            getAttachment(file) {
                console.log("get Attachment", file);
                this.$emit('getAttachment', file);
            },

            attachFiles(e) {
                // console.log("files", e.target.files)
                for (let i = 0; i < e.target.files.length; i++) {
                    this.attachedFiles.push(e.target.files[i]);
                }
                console.log("attach Files", this.attachedFiles);
                this.$emit('attachFiles', this.attachedFiles);
            },

            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
            },

            formatSize(size) {
                let i = Math.floor(Math.log(size) / Math.log(1024));
                return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
            },

            // kkk(){
            //   console.log("KKK");
            // },
            //
            // removeSelectedFile(file) {
            //     console.log("IATT FILES", this.attachedFiles);
            //     const index = this.attachedFiles.indexOf(file);
            //     console.log("INDEX", index);
            //
            //     if (index > -1) {
            //         this.attachedFiles.splice(index, 1);
            //     }
            // },

            // clearAllSelectedTags() {
            //     this.selectedHashtag = [];
            // },
            // addHashtagToArticleList(hashtag) {//from HashtagList
            //     this.tags = hashtag;
            // },
        },
        mounted() {
            console.log("@@@@COMPONENT MOUNTED1", this.alreadyUploadedFiles);

            // if (this.alreadyUploadedFiles.length > 0) {
            //     console.log("@@@@COMPONENT MOUNTED2", this.alreadyUploadedFiles);
            //     this.$emit('onChange', this.alreadyUploadedFiles);
            // }
            //if (already)
            // this.componentKey += 1;
        }

    }
</script>

<style scoped>

</style>