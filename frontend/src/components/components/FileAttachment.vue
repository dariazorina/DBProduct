<template id="file-attachment">
    <b-card style="background-color: transparent">
        <template v-if="alreadyUploadedFiles.length > 0">
            <div class="col-12" style="margin-bottom: 30px; background-color: transparent">
                <label style="color: orange; font-size: 15px; font-weight: bold;">Загруженные файлы</label>
                <table class="greyGridTable" style="background-color: transparent" :key="componentKey">
                    <thead>
                    <tr>
                        <th class='tdTitle' style="width:20px"></th>
                        <th class='tdTitle'>Имя</th>
                        <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat" style="width:120px">Дата
                            создания
                        </th>
                        <th class='tdTitle' style="width:120px">Автор</th>
                        <th class='tdTitle' style="width:120px">Размер</th>
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

        <div v-if="!isDetailsMode" class="col-12">
            <label style="color: orange; font-size: 15px; font-weight: bold;">Загрузить файлы</label>
            <div>
                <input type="file" id="inputFile" accept="*" multiple @change="attachFiles"/>
            </div>
        </div>

        <template v-if="attachedFiles.length>0">
            <div class="col-12" style="margin-top: 30px;">
                <label style="color: orange; font-size: 15px; font-weight: bold;">Добавленные файлы</label>
                <table class="greyGridTable" style="background-color: transparent" :key="componentKey">
                    <thead>
                    <tr>
                        <th class='tdTitle' style="width:20px"></th>
                        <th class='tdTitle'>Имя</th>
                        <th class='tdTitle' data-field="createdAt" data-formatter="dateFormat" style="width:120px">Дата
                            создания
                        </th>
                        <th class='tdTitle' style="width:120px">Автор</th>
                        <th class='tdTitle' style="width:120px">Размер</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="file in attachedFiles">
                        <td>
                            <v-btn text icon x-small @click="removeSelectedFile(file)">

                                <v-tooltip bottom>
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-icon
                                                style="color: red"
                                                v-bind="attrs"
                                                v-on="on">
                                            mdi-delete-forever
                                        </v-icon>
                                    </template>
                                    <span>delete</span>
                                </v-tooltip>
                            </v-btn>
                        </td>
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
        props: {
            userName: {
                type: String
            },
            alreadyUploadedFiles: {
                type: Array
            },
            isDetailsMode: {
                type: Boolean
            }
        },
        data: () => ({
            attachedFiles: [],
            componentKey: 0,
        }),

        methods: {
            downloadAttachment(file) {
                // console.log("DOWNLD Attachment", file);
                this.$emit('downloadAttachment', file);
            },

            getAttachment(file) {
                // console.log("get Attachment", file);
                this.$emit('getAttachment', file);
            },

            attachFiles(e) {
                // console.log("files", e.target.files)
                for (let i = 0; i < e.target.files.length; i++) {
                    this.attachedFiles.push(e.target.files[i]);
                }
                // console.log("attach Files", this.attachedFiles);
                this.$emit('attachFiles', this.attachedFiles);
            },

            formatDate(date) {
                return moment(date).format('DD/MM/YYYY');
            },

            formatSize(size) {
                let i = Math.floor(Math.log(size) / Math.log(1024));
                return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
            },

            removeSelectedFile(file) {

                let index = this.attachedFiles.indexOf(file);
                // console.log("attach Files this.attachFiles.length", this.attachedFiles, this.attachFiles.length);

                if (index > -1) {
                    this.attachedFiles.splice(index, 1);
                }

                // console.log("attach Files this.attachFiles.length", this.attachedFiles, this.attachFiles.length);
                //if (this.attachFiles.length === 0) {
                // console.log("RESER INPUT");
                document.getElementById("inputFile").value = "";
                // }
                this.$emit('attachFiles', this.attachedFiles);
            },
        },
        mounted() {
            // console.log("@@@@COMPONENT MOUNTED1", this.alreadyUploadedFiles);
        }
    }
</script>

<style scoped>

</style>