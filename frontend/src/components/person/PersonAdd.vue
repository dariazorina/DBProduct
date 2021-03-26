<template id="person-add">
    <v-app id="inspire">
        <div>
            <iframe id="iframeToDownload" style="display:none;"></iframe>
            <div class="form-group row">
                <!--                <div class="col-2 col-form-label">-->
                <!--                    <p class="pageCreateTitle">Add New Person</p>-->
                <!--                </div>-->

                <!--                <div class="unprotected" v-if="errorFlag">-->
                <!--                    <h5>Error: {{errors}}</h5>-->
                <!--                </div>-->


                <div v-if="editMode" class="col-5 col-form-label">
                    <p class="pageEditTitle">Edit Person <i>{{person.surname}}</i></p>
                </div>

                <div v-else="editMode" class="col-2 col-form-label">
                    <p class="pageCreateTitle">Add New Person</p>
                </div>

                <div class="unprotected" v-if="errorFlag">
                    <h5>Error: {{errors}}</h5>
                </div>


            </div>
        </div>

        <form class="authorsFormCreation">
            <div class="row" style="background-color: transparent">
                <div class="col-md-9">

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-surname-rus">Фамилия на русском*</label>
                            <input class="form-control" id="add-surname-rus" v-model="person.surnameRus"
                                   placeholder="Поле должно быть заполнено"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-surname">Фамилия</label>
                            <input class="form-control" id="add-surname" v-model="person.surname"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-surname-eng">Фамилия на английском</label>
                            <input class="form-control" id="add-surname-eng" v-model="person.surnameEng"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-name-rus">Имя на русском*</label>
                            <input class="form-control" id="add-name-rus" v-model="person.nameRus"
                                   placeholder="Поле должно быть заполнено"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-name">Имя</label>
                            <input class="form-control" id="add-name" v-model="person.name"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-name-eng">Имя на английском</label>
                            <input class="form-control" id="add-name-eng" v-model="person.nameEng"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-patronymic">Отчество</label>
                            <input class="form-control" id="add-patronymic" v-model="person.patronymic">
                        </div>

                        <div class="col-md-4">
                            <label>Год рождения</label>
                            <b-form-select v-model="selectedBYear" class="mb-3" id="birth-year-selection">
                                <option v-for="year in years">{{year}}</option>
                            </b-form-select>
                        </div>

                        <div class="col-md-4">
                            <label>Год смерти</label>
                            <b-form-select v-model="selectedDYear" class="mb-3" id="death-year-selection">
                                <option v-for="year in years">{{year}}</option>
                            </b-form-select>
                        </div>
                    </div>

                    <!--                    <div class="form-row" style="background-color: transparent">-->
                    <!--                        <div class="col-md-8">-->
                    <!--                            <label>Местонахождение</label>-->
                    <!--                            <b-form-select v-model="selectedCountry" class="mb-3" id="country-selection">-->
                    <!--                                <option v-for="country in allCountries" v-bind:value="country.id">{{countryTitleCreation(country)}}-->
                    <!--                                </option>-->
                    <!--                            </b-form-select>-->
                    <!--                            &lt;!&ndash;                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>&ndash;&gt;-->
                    <!--                        </div>-->
                    <!--                    </div>-->

                </div>
                <div class="col-md-2" style="margin-top: 10px; background-color: transparent">
                    <div id="preview">
                        <div v-if="avatar.imageUrl">
                            <img :src="avatar.imageUrl" @load="setHeight"
                                 :style="{ height: imageHeight + 'px' }"/>
                        </div>
                        <div v-else>
                            <img v-if="person.photo" v-bind:src="'data:image/jpeg;base64,'+person.photo"
                                 :style="{ width: 250+'px' }"/>
                        </div>
                    </div>
                    <div v-if="avatar.imageUrl" style="margin-top: 5px">
                        <input type="file" accept="image/*" @change="onChange"/>
                    </div>
                    <div v-else style="margin-top: 30px">
                        <input type="file" accept="image/*" @change="onChange"/>
                    </div>
                </div>
            </div>
        </form>
        <form class="authorsFormCreation"
              style="background-color: transparent; padding: 25px 0 5px">
            <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                <div class="col-3"
                     style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                    <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                        <label style="font-size: medium; font-weight: bold">Связанные страны/н.п./места</label>
                        <v-autocomplete
                                id="location-autocomplete"
                                :items="locationItems"
                                :loading="isLoadingLocation"
                                :search-input.sync="locationSearch"
                                color="orange"
                                hide-no-data
                                hide-selected

                                v-model="selectedLocation"

                                @change="addLocation(selectedLocation)"
                                item-text="country"
                                item-value="id"
                                placeholder="Начните печатать, чтобы найти локацию"
                                prepend-icon="mdi-database-search"
                                return-object
                        ></v-autocomplete>
                    </v-card-text>
                </div>

                <div v-if="locationList.length>0" class="col-9"
                     style="background-color: transparent; padding:0">
                    <ConnectionComponent :itemsList="locationList"
                                         :isLinkMode="false"
                                         :isSelectionMode="false"
                                         :allTypes="connectionTypes"
                                         style="background-color: transparent; padding:0px" class="col-12"/>
                </div>
            </div>
        </form>

        <form class="authorsFormCreation"
              style="background-color: transparent; padding: 25px 0 5px">
            <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                <div class="col-3"
                     style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                    <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                        <label style="font-size: medium; font-weight: bold">Связанные организации</label>
                        <v-autocomplete
                                id="author-autocomplete"
                                :items="orgItems"
                                :loading="isLoadingOrg"
                                :search-input.sync="orgSearch"
                                color="green"
                                hide-no-data
                                hide-selected

                                v-model="selectedOrg"

                                @change="addOrg(selectedOrg)"
                                item-text="name"
                                item-value="id"
                                placeholder="Начните печатать, чтобы найти организацию"
                                prepend-icon="mdi-database-search"
                                return-object
                        ></v-autocomplete>
                    </v-card-text>
                </div>

                <div v-if="occupationList.length>0" class="col-9"
                     style="background-color: transparent; padding:0; margin: 0px">
                    <ConnectionComponent :itemsList="occupationList"
                                         :isLinkMode="false"
                                         :isSelectionMode="false"
                                         :allTypes="connectionTypes"
                                         style="background-color: transparent; padding:0px" class="col-12"/>
                </div>
            </div>
        </form>


        <form class="authorsFormCreation"
              style="background-color: transparent; padding: 25px 0 5px">
            <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                <div class="col-3"
                     style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                    <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                        <label style="font-size: medium; font-weight: bold">Связанные персоны</label>
                        <v-autocomplete
                                style="background-color: transparent"
                                id="author-autocomplete"
                                :items="itemsPerson"
                                :loading="isLoadingPerson"
                                :search-input.sync="personSearch"
                                color="pink"
                                hide-no-data
                                hide-selected

                                v-model="selectedPerson"

                                @change="addPerson(selectedPerson)"
                                item-text="surname"
                                item-value="id"
                                placeholder="Начните печатать, чтобы найти автора"
                                prepend-icon="mdi-database-search"
                                return-object
                                :disabled="uploadMode"
                        ></v-autocomplete>

                    </v-card-text>
                </div>

                <div v-if="personList.length>0" class="col-9"
                     style="background-color: transparent; padding:0; margin: 0px">
                    <ConnectionComponent :itemsList="personList"
                                         :isLinkMode="false"
                                         :isSelectionMode="false"
                                         :allTypes="connectionTypes"
                                         style="background-color: transparent; padding:0px" class="col-12"/>
                </div>
            </div>
        </form>


        <!--        <div style="background-color: transparent; margin-left: 30px">-->
        <!--            <b-card class="col-md-9" style="background-color: #f8f8f8; font-size: small; text-align: left">-->
        <!--                &lt;!&ndash;                <OccupationList v-if="!editMode" :allOrgs="allOrgs" :selected="selected" @update-occupation="updateOccupation"/>&ndash;&gt;-->
        <!--                <OccupationList :allOrgs="allOrgs"-->
        <!--                                :selected="selected"-->
        <!--                                :todos="occupationWithIndexList"-->
        <!--                                @update-occupation="updateOccupation"/>-->
        <!--            </b-card>-->
        <!--        </div>-->
        <!--        component doesn't work correctly in form 'formCreation;-->
        <form class="formCreation">
            <div class="form-row">
                <div class="col-md-6">

                    <label><b>Описание</b></label>
                    <!--                    <label for="add-description">Описание</label>-->
                    <!--                    <textarea class="form-control" id="add-description" rows="7" v-model="person.description"/>-->

                    <ckeditor :editor="editor" v-model="person.description" :config="editorConfig"></ckeditor>
                </div>
            </div>

            <div class="form-row">
                <div class="col-md-7">
                    <!--                <div class="col-md-10">-->
                    <!--                    <label for="add-description">Форма добавления хештегов</label>-->
                    <!--                    <b-card style="width: 80%">-->
                    <!--                        <hashtag-list :commonProp="test" @addHashtagToList="addHashtagToList($event)"/>-->
                    <!--                    </b-card>-->

                    <label><b>Форма добавления хештегов</b></label>
                    <div class="col-12" style="background-color: transparent; margin-left: -15px">
                        <b-card style="background-color: transparent; width: 88%">
                            <!--                            todo? 88%-->
                            <v-row style="background-color: transparent; margin-top: -10px; margin-bottom: -10px;">
                                <v-col style="background-color: transparent; margin-top: -10px; margin-left: -5px; margin-bottom: -10px">
                                    <v-sheet
                                            style="padding-left: 0px; padding-top: 0px; padding-right: 18px; background-color: transparent">
                                        <v-text-field label="search"
                                                      v-model="searchHashtag"
                                                      filled>
                                        </v-text-field>
                                    </v-sheet>

                                    <v-container
                                            id="scroll-target"
                                            style="max-height: 300px; background-color: transparent; margin-top: -10px; margin-left: -15px; padding-top: 0px; padding-left: 0;"
                                            class="overflow-y-auto">
                                        <v-treeview
                                                :items="filteredElements"
                                                :open="filteredKeys"
                                                item-key="name"

                                                activatable
                                                color="warning"
                                                dense
                                                :open-all="true"

                                                return-object
                                                hoverable
                                                ref="treeviewref">

                                            <template slot="label" slot-scope="{ item }">
                                                <a @click="onHashtagSelect(item)">{{ item.name }}</a>
                                            </template>

                                        </v-treeview>
                                    </v-container>
                                </v-col>

                                <!--                                <v-divider vertical-->
                                <!--                                           style="background-color: transparent; margin-top: -10px; margin-left: -10px; margin-bottom: -10px;"></v-divider>-->
                                <!--                                <v-col-->
                                <!--                                        style="background-color: transparent; margin-top: -10px; margin-left: -10px; margin-bottom: -10px;">-->

                                <!--                                    <v-container-->
                                <!--                                            id="scroll-target"-->
                                <!--                                            style="max-height: 300px; background-color: transparent; margin-top: -10px;"-->
                                <!--                                            class="overflow-y-auto">-->

                                <!--                                        <template v-if="!selectedHashtag.length">-->
                                <!--                                            No nodes selected.-->
                                <!--                                        </template>-->

                                <!--                                        <template v-else>-->
                                <!--                                            <div v-for="node in selectedHashtag">-->
                                <!--                                                <v-btn text icon x-small @click="removeSelectedHashtag(node)">-->
                                <!--                                                    <v-icon style="color: red">mdi-delete-forever</v-icon>-->
                                <!--                                                </v-btn>-->
                                <!--                                                {{ node }}-->
                                <!--                                            </div>-->

                                <!--                                            <div class="form-group row" style="padding-top: 30px">-->

                                <!--                                                <button type="button"-->
                                <!--                                                        style="margin-right: 20px; margin-left: 15px"-->
                                <!--                                                        @click="addHashtagToArticleList()"-->
                                <!--                                                        class="btn btn-success">Add-->
                                <!--                                                </button>-->

                                <!--                                                <button type="button" class="btn btn-info"-->
                                <!--                                                        @click="clearAllSelectedTags()">Clear All-->
                                <!--                                                </button>-->
                                <!--                                            </div>-->
                                <!--                                        </template>-->
                                <!--                                    </v-container>-->
                                <!--                                </v-col>-->
                            </v-row>

                        </b-card>
                    </div>
                </div>
            </div>

        </form>
        <form class="authorsFormCreation">
            <div class="form-row">
                <div class="col-md-6">
                    <label for="add-link">Хештеги</label>
                    <div>
                        <input-tag id="add-hashtag" :add-tag-on-keys="addTagOnKeys"
                                   v-model="tags"></input-tag>

                    </div>
                </div>
            </div>


            <div class="form-row">
                <div class="col-md-6">
                    <label for="add-link">Link</label>
                    <div>
                        <input-tag id="add-link" :add-tag-on-keys="addTagOnKeys"
                                   placeholder="enter links with 'return' or 'tab'" v-model="links"></input-tag>
                    </div>
                </div>
            </div>

            <div class="form-row">
                <div class="col-md-6">
                    <label for="add-misc">Комментарии</label>
                    <div class="green-border-focus">

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="person.miscellany"
                                              background-color="palegreen" required></textarea>
                    </div>
                </div>
            </div>

            <div v-if="editMode" class="form-row align-items-center" style="background-color: transparent">
                <label class="col-1 col-form-label labelInCreation"
                       style="vertical-align: center; background-color: transparent">Текущий статус</label>
                <div class="col-3" style="background-color: transparent">
                    <b-form-select v-model="selectedS" class="mb-0" id="status-selection"
                                   style="background-color: transparent;">
                        <option v-for="status in statusOptions" v-bind:value="status.value">
                            {{status.text}}
                        </option>
                    </b-form-select>
                    <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                </div>
                <label class="col-2 col-form-label labelInCreation"
                       style="vertical-align: center; background-color: transparent; margin-left: 40px; margin-right: -50px">Текущий
                    цвет выделения</label>
                <div class="col-1" style="background-color: transparent; padding: 0; vertical-align: center;">
                    <v-swatches v-model="person.rowColor" popover-x="left"></v-swatches>
                </div>
            </div>

            <div v-if="uploadMode||editMode" style="background-color: transparent">
                <file-attachment @attachFiles="createAttachment"
                                 @getAttachment="getAttachment"
                                 @downloadAttachment="downloadAttachment"
                                 :userName="loggedName"
                                 :already-uploaded-files="uploadedFiles"
                                 :is-details-mode="false"/>
            </div>
            <div v-if="editMode" class="form-group row align-items-center">
                <div class="offset-sm-4 col-sm-3">

                    <button type="button" @click="preliminaryDataCheck(0)" class="btn btn-primary">Обновить</button>
                    <a class="btn btn-default">
                        <router-link to="/article">Отмена</router-link>
                    </a>
                </div>
            </div>
            <div v-else class="form-group row"
                 style="background-color: transparent; margin: 0; padding: 0; padding-top: 15px">
                <div v-if="!uploadMode" class="form-group align-items-center col-sm-12"
                     style="background-color: transparent; padding: 0;">
                    <div class="form-group align-items-center col-sm-12"
                         style="background-color: transparent; margin: 0; padding:0; padding-bottom: 15px;">
                        <input type="checkbox" id="checkbox" v-model="uploadFilesCheckBoxValue" class="yyy">
                        <label for="checkbox" style="background-color: transparent">Нажмите "галочку", если хотите
                            добавить файлы </label>
                    </div>

                    <button type="button" style="margin-right: 20px" @click="preliminaryDataCheck(status[0])"
                            class="btn btn-warning">В работе
                    </button>
                    <button type="button" style="margin-right: 20px" @click="preliminaryDataCheck(status[1])"
                            class="btn btn-success">Внесены
                    </button>

                    <button type="button" class="btn btn-info">
                        <router-link to="/article" style="color: white">Отмена</router-link>
                    </button>
                </div>

                <div v-else class="offset-sm-4 col-sm-3">
                    <button type="button" style="margin-right: 20px" @click="uploadFiles"
                            class="btn btn-info">Upload Files
                    </button>
                    <button type="button" class="btn btn-info">
                        <router-link to="/article" style="color: white">Cancel</router-link>

                    </button>
                </div>
            </div>


            <!--            <div v-if="editMode" class="form-group row align-items-center align-items-center">-->
            <!--                <div class="offset-sm-4 col-sm-3">-->

            <!--                    <button type="button" @click="preliminaryDataCheck(0)" class="btn btn-primary">Update</button>-->
            <!--                    <a class="btn btn-default">-->
            <!--                        <router-link to="/article">Cancel</router-link>-->
            <!--                    </a>-->
            <!--                </div>-->
            <!--            </div>-->
            <!--            <div v-else class="form-group row">-->
            <!--                <div class="col-sm-4">-->

            <!--                    <button type="button" @click="preliminaryDataCheck(0)" class="btn btn-primary">Save</button>-->
            <!--                    <a class="btn btn-default">-->
            <!--                        <router-link to="/person">Cancel</router-link>-->
            <!--                    </a>-->
            <!--                </div>-->
            <!--            </div>-->
        </form>
    </v-app>
</template>

<style lang="scss">
    @import '../dbnm.css';
</style>

<script>
    import api from "./person-api";
    import apiOrg from "./../org/org-api";
    import apiCountry from "./../country/country-api";

    import router from "./../../router";
    import Vuetify from 'vuetify';
    import OccupationList from "../components/person-occupation/OccupationList";
    import apiHashtag from "./../hashtag/hashtag-api";

    import CKEditor from 'ckeditor4-vue';
    import ConnectionComponent from "../components/connection/ConnectionComponent";
    import FileAttachment from "../components/FileAttachment";
    import apiAttachment from "./../attachment-api";
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple

    export default {
        components: {
            OccupationList,
            ConnectionComponent,
            ckeditor: CKEditor.component, // to use the component locally
            FileAttachment,
            VSwatches
        },
        name: 'person-add',
        vuetify: new Vuetify(),

        data: () => ({
            test: "personProp",
            addTagOnKeys: [13, 9],
            descriptionLimit: 60,
            entries: [],

            isLoadingLocation: false,
            isLoadingOrg: false,
            isLoadingPerson: false,

            model: null,
            search: null,
            searchHashtag: '',
            allTags: [],
            tagsTree: [],
            selectionType: 'independent',
            selectedHashtag: [],
            hashtagTree: [],
            hashtagFlatTree: [],

            selected: '',
            selectedS: null,
            selectedLocation: [],
            selectedOrg: [],
            selectedPerson: [],
            selectedM: null,
            // selectedCountry: null,
            selectedBYear: null,
            selectedDYear: null,

            tags: [],
            links: [],

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            // allCountries: [],
            // allOrgs: [],
            // allMovements: [],

            locationEntries: [],
            orgEntries: [],
            personEntries: [],
            connectionTypes: [],

            previewImage: null,
            avatar: {
                image: null,
                imageUrl: null,
                imageBase64: null
            },

            imageHeight: null,

            linkList: [],
            hashtagList: [],

            locationSearch: null,
            locationList: [],

            orgSearch: null,
            occupationList: [],

            personSearch: null,
            personList: [],

            personLocationIds: [], //before request
            personLocationEntities: [], //after request
            personOrgIds: [], //before request
            personOrgEntities: [], //after request
            personPersonIds: [], //before request
            personPersonEntities: [], //after request

            testList: [],
            person: {hashtagList: [], linkList: [], testList: [], locationList: [], personList:[]},
            years: [],
            editMode: false,
            uploadMode: false,
            uploadFilesCheckBoxValue: false,
            status: ["statusProgress", "statusDone"],
            statusOptions: [
                {text: 'В работе', value: 0},
                {text: 'Внесены', value: 1},
                {text: 'На доработке', value: 2},
                {text: 'Отработаны', value: 3},
            ],
            loggedInFlag: false,
            loggedName: '',
            uploadedFiles: [],
            attachedFiles: [],

            editor: CKEditor, // to use the component locally
            editorConfig: {
                // The configuration of the editor.
            }
        }),

        methods: {
            uploadFiles() {             //on button press
                for (let i = 0; i < this.attachedFiles.length; i++) {
                    apiAttachment.uploadFile('person', this.person.id, this.attachedFiles[i], r => {
                    });
                    // this.submitFile(this.attachedFiles[i]);
                    //todo progress bar?
                }
                router.push('/person');
            },

            createAttachment(files) {     //emit from FilesAttachment Component 'attachFiles'
                console.log("files from COMPONENT", files);
                this.attachedFiles = [];
                for (let i = 0; i < files.length; i++) {
                    this.attachedFiles.push(files[i]);
                }
            },

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/person/downloadAttachment?entityId=' + this.person.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                apiAttachment.previewAttachment('person', this.person.id, file.id);
            },


            getLoggedIn() {
                this.loggedInFlag = this.$store.getters.isLoggedIn;
                this.loggedName = this.$store.getters.getUserName;
            },

            setHeight(event) {
                let image = event.target;
                if (image.clientWidth > image.clientHeight) {
                    let pr = image.clientWidth / image.clientHeight;
                    this.imageHeight = 350 / pr;
                } else {
                    this.imageHeight = 350;
                }
            },

            onChange(e) {
                //todo need to set size limit

                const file = e.target.files[0];
                this.avatar.image = file;
                this.avatar.imageUrl = URL.createObjectURL(file);


                // img = new Image();
                // img.onload = function() {
                //     alert(this.width + " " + this.height);
                // };
                // img.onerror = function() {
                //     alert( "not a valid file: " + file.type);
                // };
                // avatar.img.src = _URL.createObjectURL(file);


                // console.log("IMAGE FILE", this.avatar);
                // let config = {
                //     header: {
                //         'Content-Type': 'image/png'
                //     }
                // };

                const reader = new FileReader();
                reader.readAsDataURL(file);  // reader.readAsBinaryString(file);
                reader.onload = e => {
                    this.avatar.imageBase64 = e.target.result;
                    console.log(this.avatar.imageBase64);
                    ///////////api.getImage(this.avatar.imageBase64, config);
                };
            },

            uploadImage(e) {
                console.log(e);

                const image = e.target.files[0];
                const reader = new FileReader();
                reader.readAsDataURL(image);
                reader.onload = e => {
                    this.previewImage = e.target.result;
                    // console.log(this.previewImage);
                };
            },

            // uploadImage(event) {
            //
            //     // const URL = 'http://foobar.com/upload';
            //
            //     let data = new FormData();
            //     data.append('name', 'my-picture');
            //     data.append('file', event.target.files[0]);
            //
            //     let config = {
            //         header: {
            //             'Content-Type': 'image/png'
            //         }
            //     };
            //     api.getImage(data, config);
            //
            //
            //     // apiCountry.getAllCountries(response => {
            //     //     this.allCountries = response.data;
            //     //     // console.log(response.data)
            //     // });
            // },


            initYears() {
                for (let y = 1900; y < 2051; y++) {
                    this.years.push(y);
                }
            },

            // updateOccupation(occupation) {   //occupation was sent by Property //todo to delete
            //     //this.occupationList.push(occupation);
            //     console.log("OCCUPATION PUSH", occupation.orgId, occupation.position, occupation.comment);
            // },

            addLocation(obj) {
                console.log("GET CHANGED LOCATION", obj);
                let i = 0;
                for (i = 0; i < this.locationList.length; i++) { //to exclude double values
                    if (this.locationList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.locationList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.country,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.locationList.push(connection);
                    console.log("ADDED", this.locationList);
                }
            },

            addOrg(obj) {
                console.log("GET CHANGED ORG", obj);
                let i = 0;
                for (i = 0; i < this.occupationList.length; i++) { //to exclude double values
                    if (this.occupationList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.occupationList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.name,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.occupationList.push(connection);
                    console.log("ADDED");
                }
            },

            addPerson(obj) {
                console.log("GET CHANGED PERSON", obj);
                let i = 0;
                for (i = 0; i < this.personList.length; i++) { //to exclude double values
                    if (this.personList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.personList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.surname,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.personList.push(connection);
                    console.log("ADDED PERSON");
                }
            },


            finalPositionListCreation(list, finalList) {
                // console.log("^^^^^^^^^^^^^^^finalConnectionListCreation^^^^^^^^^ ", list, finalList);
                for (let i = 0; i < list.length; i++) {
                    let a = {
                        "orgId": list[i].id,
                        "position": list[i].connection,
                        "comment": list[i].comment
                    };
                    // console.log("CREATE PERS ON A: ", a);

                    if (a.position.length > 0) { //to avoid add empty connections (wasn't entered)
                        // console.log("PUSH PERS ON A: ", a);
                        finalList.push(a);
                    }
                    // console.log("CREATE PERS ON A: ", this.article.personList);
                }
            },

            finalConnectionListCreation(list, finalList) {
                console.log("^^^^^^^^^^^^^^^finalConnectionListCreation^^^^^^^^^ ", list, finalList);
                for (let i = 0; i < list.length; i++) {
                    let a = {
                        "itemId": list[i].id,
                        "connection": list[i].connection,
                        "comment": list[i].comment
                    };
                    if (a.connection.length > 0) { //to avoid add empty connections (wasn't entered)
                        finalList.push(a);
                    }
                }
            },

            // removeSelectedHashtag(hash) {
            //     const index = this.selectedHashtag.indexOf(hash);
            //     if (index > -1) {
            //         this.selectedHashtag.splice(index, 1);
            //     }
            // },

            onHashtagSelect(item) {
                const index = this.tags.indexOf(item.name);
                if (index === -1) {
                    this.tags.push(item.name);
                }
            },

            buildTree() {
                //this.hashtagTree = this.createTree(this.allTags, 0);
                this.hashtagFlatTree = this.createFlatTree(this.allTags);
                // console.log("CREATED TREE", this.hashtagTree);
                // console.log("FLAT TREE", this.hashtagFlatTree);
            },

            createFlatTree(treeData) {
                let flatTree = [];
                for (let i = 0; i < treeData.length; i++) {
                    let newItem = {
                        id: treeData[i].id,
                        name: treeData[i].content,
                        children: [],
                    };
                    for (let j = 0; j < treeData.length; j++) {
                        if (treeData[j].parentId === treeData[i].id) {

                            let refNewItem = {
                                id: treeData[j].id,
                                name: null,
                                children: null,
                            };
                            newItem.children.push(refNewItem);
                        }
                    }
                    flatTree.push(newItem);
                }
                return flatTree;
            },

            // clearAllSelectedTags() {
            //     this.selectedHashtag = [];
            // },
            //
            // addHashtagToArticleList() {
            //     let tagAlreadyAdded = 0;
            //     this.selectedHashtag.forEach((item, i) => {
            //         this.tags.forEach((tag, j) => {
            //             if (tag === item)
            //                 tagAlreadyAdded = 1;
            //         });
            //         if (tagAlreadyAdded === 0)
            //             this.tags.push(item);
            //     });
            //     this.selectedHashtag = [];
            // },

            addStatus(id, hasError) {
                document.getElementById(id).classList.remove('is-valid');
                document.getElementById(id).classList.remove('is-invalid');

                if (hasError) {
                    document.getElementById(id).classList.add('is-invalid');
                } else {
                    document.getElementById(id).classList.add('is-valid');
                }
                this.hasError = this.hasError || hasError;
            },

            formValidate() {
                this.addStatus('add-surname-rus', (!this.person.surnameRus));
                if (this.hasError) {
                } else {
                    this.addStatus('add-name-rus', (!this.person.nameRus));
                    // if (this.hasError) {
                    // } else {
                    //     this.addStatus('country-selection', (!this.selectedC));
                    // }
                }

                if (this.hasError)
                    console.log('ERROROROR----------------------------');
                return !this.hasError;
            },

            isObjectValidAndNotEmpty(obj) {
                return !(typeof obj === 'undefined' || obj === null);
            },

            checkConnection(list) {
                for (let i = 0; i < list.length; i++) {
                    if (list[i].connection.length <= 0) {
                        console.log("AXTUNG");
                        return true;
                    }
                }
                return false;
            },

            preliminaryDataCheck(currentStatus) {
                let t = false, g = false, s = false;


                if (this.isObjectValidAndNotEmpty(this.locationList)) {
                    t = this.checkConnection(this.locationList);
                }
                if (!t) {
                    if (this.isObjectValidAndNotEmpty(this.occupationList)) {
                        g = this.checkConnection(this.occupationList);

                        if (!g){
                            if (this.isObjectValidAndNotEmpty(this.personList)) {
                                s = this.checkConnection(this.personList);
                            }
                        }
                    }
                }

                if (t || g || s) {
                    alert("Укажите связь для сущностей, которые вы добавили");
                    // console.log("ALERT");
                } else {
                    // alert("else");
                    // if (this.editMode) {
                    //     this.updateArticle();
                    // } else {
                    this.createPerson(currentStatus);
                    // }
                }
            },

            createPerson(currentStatus) {
                // this.person.movement = {
                //     "id": this.selectedM
                // }; todo

                // this.person.movement = {
                //     "id": 1
                // };

                if (this.selectedBYear) {
                    this.person.birthYear = this.selectedBYear;
                }

                if (this.selectedDYear) {
                    this.person.deathYear = this.selectedDYear;
                }

                if (currentStatus === this.status[0]) {
                    this.person.status = 0;
                } else {
                    this.person.status = 1;
                }

                // if (this.selectedCountry) {  //otherwise without this check Country entity is created with null fields values and Person can't be saved
                //     this.person.location_id = this.selectedCountry;
                // }

                for (let i = 0; i < this.links.length; i++) {
                    this.person.linkList[i] = {
                        "content": this.links[i]
                    };
                    // console.log("CREATE PERS link: ", this.links[i]);
                }

                for (let i = 0; i < this.tags.length; i++) {
                    this.person.hashtagList[i] = this.tags[i];
                }

                this.hasError = false;
                // this.person.testList = [];

                // for (let i = 0; i < this.occupationWithIndexList.length; i++) {
                //     let a = {
                //         "orgId": this.occupationWithIndexList[i].orgId,
                //         "position": this.occupationWithIndexList[i].position,
                //         "comment": this.occupationWithIndexList[i].comment
                //     };
                //     //console.log("CREATE PERS    ON A: ", a);
                //     this.person.testList.push(a);
                // }

                this.person.locationList.splice(0);
                this.person.personList.splice(0);
                this.person.testList.splice(0);
                this.finalConnectionListCreation(this.locationList, this.person.locationList);
                this.finalPositionListCreation(this.occupationList, this.person.testList);
                this.finalConnectionListCreation(this.personList, this.person.personList);

                this.person.photo = this.avatar.imageBase64;

                if (this.editMode) {
                    this.person.status = this.selectedS;
                    console.log("PERSON BEFORE UPDATING", this.person);
                    if (this.formValidate()) {
                        api.update(this.person.id, this.person, r => {
                            console.log(r);
                            for (let i = 0; i < this.attachedFiles.length; i++) {
                                apiAttachment.uploadFile('person', this.person.id, this.attachedFiles[i], r => {
                                });
                                //todo progress bar?
                            }
                            router.push('/person');
                        });
                    }
                } else {
                    console.log("PERSON BEFORE SAVING", this.person);
                    if (this.formValidate()) {
                        api.create(this.person, r => {
                            console.log(r);
                            if (!this.uploadFilesCheckBoxValue) {
                                router.push('/person');
                            } else {
                                this.uploadMode = true;
                                this.uploadFilesCheckBoxValue = false;
                                let ID = r.data.id;
                                this.person.id = ID;
                                console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>stay here, PERSON ID", r.data, ID);
                            }
                        });
                    }
                }
            },

            orgEditConnectionTitleCreation(org) {
                let returnedTitle = org.nameRus;

                if (this.isArrayValidAndNotEmpty(org.abbrRus)) {
                    returnedTitle += ", " + org.abbrRus;
                }
                if (this.isArrayValidAndNotEmpty(org.name)) {
                    returnedTitle += ", " + org.name;
                }
                //todo add fields

                return returnedTitle;
            },

            countryTitleCreation(country) {
                let returnedTitle = country.country;

                if (this.isArrayValidAndNotEmpty(country.region)) {
                    returnedTitle += ", " + country.region;
                }
                if (this.isArrayValidAndNotEmpty(country.city)) {
                    returnedTitle += ", " + country.city;
                }
                if (this.isArrayValidAndNotEmpty(country.address)) {
                    returnedTitle += ", " + country.address;
                }
                //placement?

                return returnedTitle;
            },

            personNameCreation(person) {
                let personTitle = person.surnameRus;

                if (this.isArrayValidAndNotEmpty(person.nameRus)) {
                    personTitle += " " + person.nameRus;
                }
                if (this.isArrayValidAndNotEmpty(person.birthYear)) {
                    personTitle += ", " + person.birthYear;
                }
                return personTitle;
            },

            isArrayValidAndNotEmpty(array) {
                if (typeof array === 'undefined' || array === null || array.length === 0) {
                    return false;
                }
                return true;
            },
        },
        mounted() {
            this.getLoggedIn();

            if (this.$route.params.person_id != null) {
                console.log("EDIT MODE");
                this.editMode = true;
                this.uploadFilesCheckBoxValue = true;
            }

            this.initYears();

            if (this.editMode) {////////////////////////////////EDIT MODE//////////////////////////////////////
                api.findById(this.$route.params.person_id, r => {
                    this.person = r.data;

                    this.selectedBYear = this.person.birthYear;
                    this.selectedDYear = this.person.deathYear;
                    this.selectedS = this.person.status;

                    for (let i = 0; i < this.person.hashtagList.length; i++) {
                        this.tags.push(this.person.hashtagList[i]);
                    }

                    for (let i = 0; i < this.person.linkList.length; i++) {
                        this.links.push(this.person.linkList[i].content);
                    }

                    for (let j = 0; j < this.person.locationList.length; j++) {
                        this.personLocationIds.push(this.person.locationList[j].itemId);
                    }

                    for (let j = 0; j < this.person.personList.length; j++) {
                        this.personPersonIds.push(this.person.personList[j].itemId);
                    }

                    for (let j = 0; j < this.person.testList.length; j++) {
                        this.personOrgIds.push(this.person.testList[j].orgId);
                    }

                    // apiOrg.getAllOrgs(response => {
                    //     this.allOrgs = response.data;
                    //
                    //     for (let i = 0; i < this.person.testList.length; i++) {
                    //         let a = {
                    //             "id": i,
                    //             "org": this.allOrgs.find(org => org.id === this.person.testList[i].orgId).name,
                    //             "orgId": this.person.testList[i].orgId,
                    //             "position": this.person.testList[i].position,
                    //             "comment": this.person.testList[i].comment
                    //         };
                    //         // console.log("GET PERS    ON A: ", a);
                    //         this.occupationWithIndexList.push(a);
                    //     }
                    // });

                    apiAttachment.getAttachments('person', this.person.id, r => {
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });

                    apiCountry.getLocationsByIds(this.personLocationIds, response => {  ///returns List<Location>
                        this.personLocationEntities = response.data;   //returns List<Org>
                        console.log("getLocationsBy#Entities locaList", this.personLocationEntities, this.person.locationList);

                        for (let i = 0; i < this.person.locationList.length; i++) {
                            let element = this.person.locationList[i];
                            let currentLocationEntity = this.personLocationEntities.find(l => l.id === element.itemId);
                            console.log("--------------------------> currentLocationEntity", currentLocationEntity);
                            let connection = {
                                "id": element.itemId,
                                "name": this.countryTitleCreation(currentLocationEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.locationList.push(connection);
                        }
                    });

                    api.getPersonsByIds(this.personPersonIds, response => {  ///returns List<Location>
                        this.personPersonEntities = response.data;   //returns List<Org>

                        for (let i = 0; i < this.person.personList.length; i++) {
                            let element = this.person.personList[i];
                            let currentPersonEntity = this.personPersonEntities.find(l => l.id === element.itemId);
                            let connection = {
                                "id": element.itemId,
                                "name": this.personNameCreation(currentPersonEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.personList.push(connection);
                        }
                    });

                    apiOrg.getOrgsByIds(this.personOrgIds, response => {  ///returns List<Location>
                        this.personOrgEntities = response.data;   //returns List<Org>
                        console.log("apiOrg", this.personOrgEntities);

                        for (let i = 0; i < this.person.testList.length; i++) {
                            let element = this.person.testList[i];
                            let currentOrgEntity = this.personOrgEntities.find(org => org.id === element.orgId);
                            console.log("currentOrgEntity", currentOrgEntity);
                            let connection = {
                                "id": element.orgId,
                                "name": this.orgEditConnectionTitleCreation(currentOrgEntity),
                                "connection": element.position,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.occupationList.push(connection);
                        }
                        //console.log("occupationWithIndexList: ", this.occupationWithIndexList);
                    });

                    this.avatar.imageBase64 = this.person.photo;
                });
            }

            // apiCountry.getAllCountries(response => {
            //     this.allCountries = response.data;
            //     console.log("****************************locations", response.data)
            // });

            apiHashtag.getAllHashtags(response => {
                this.allTags = response.data;
                this.buildTree();
                // console.log(response.data)
            });
        },
        computed: {
            itemsPerson() {
                return this.personEntries.map(entry => {
                    const surname = entry.surname;
                    return Object.assign({}, entry, {surname})
                })
            },


            locationItems() {
                if (this.locationEntries) {      ///todo analyze why undefined (after selection in the search list)
                    return this.locationEntries.map(entry => {
                        // const org = entry.name;
                        //  return Object.assign({}, entry, {org})
                        return Object.assign({}, entry)
                    })
                }
            },

            orgItems() {
                if (this.orgEntries) {      ///todo analyze why undefined (after selection in the search list)
                    // console.log("####################", this.orgEntries);

                    return this.orgEntries;
                    //     .map(entry => {
                    //     const id = entry.id;
                    //     const org = entry.name;
                    //     return Object.assign({}, {id}, {org})
                    // })
                }
                // return this.orgEntries;
            },

            searchLength() {
                return this.searchHashtag.length
            },

            filteredElements() {
                let tree = [];
                let resultIds = [];

                let touched = false;
                let resultSearchTree = []; //this.hashtagFlatTree.slice(); //this.hashtagFlatTree.map((x) => x);

                //the way for deep copy
                resultSearchTree = JSON.parse(JSON.stringify(this.hashtagFlatTree));

                do {

                    // remove leaves which do not match search string
                    let toRemove = [];
                    touched = false;

                    resultSearchTree.forEach((node, id) => {
                        const index = node.name.toLowerCase().indexOf(this.searchHashtag) >= 0;
                        if ((index === false) && (node.children.length === 0)) {
                            // console.log("node.children.length (to remove)", node.children.length, node.name);
                            toRemove.push(node.id);
                            touched = true;
                        } else {
                            // console.log("node.children.length (to stay)", node.children.length, node.name);
                        }
                    });

                    for (let i = 0; i < toRemove.length; i++) {
                        let index = resultSearchTree.findIndex(x => x.id === toRemove[i]);
                        //   console.log("INDEX * * * * *", index, toRemove[i]);
                        if (index >= 0) {
                            resultSearchTree.splice(index, 1);
                        }
                    }

                    // remove references to deleted leaves
                    let treeSize = resultSearchTree.length;
                    for (let i = 0; i < treeSize; i++) {
                        let newChildren = [];
                        let currentItem = resultSearchTree[i];

                        for (let j = 0; j < currentItem.children.length; j++) {

                            let childWasFound = false;
                            for (let k = 0; k < treeSize; k++) {
                                if ((resultSearchTree[k].id === currentItem.children[j].id)) {
                                    newChildren.push(currentItem.children[j]);
                                    childWasFound = true;
                                }
                            }//for k

                            if (!childWasFound) {
                                touched = true;
                                //   console.log("deleted ref", currentItem.children[j].id);
                            }
                        }//for j
                        resultSearchTree[i].children = newChildren;
                    }

                } while (touched);


                //create normal tree from flat tree
                let movedChildren = [];
                do {
                    movedChildren = [];

                    let treeSize = resultSearchTree.length;
                    for (let i = 0; i < treeSize; i++) {

                        let currentItem = resultSearchTree[i];
                        let removedChildrenAlreadyContains = false;
                        let childrenQ = currentItem.children.length;

                        for (let j = 0; j < movedChildren.length; j++) {
                            if (movedChildren[j] === currentItem.id)
                                removedChildrenAlreadyContains = true;
                        }

                        if (!removedChildrenAlreadyContains) {
                            for (let j = 0; j < childrenQ; j++) {
                                let child = currentItem.children[j];

                                if ((child.name == null)) {
                                    let index = resultSearchTree.findIndex(x => x.id === child.id);
                                    if (index >= 0) {
                                        //if (resultSearchTree[index].children.length === 0) {
                                        let isChildrenNull = false;

                                        if (resultSearchTree[index].children !== null) {
                                            for (let k = 0; k < resultSearchTree[index].children.length; k++) {
                                                if (resultSearchTree[index].children[k].name === null) {
                                                    isChildrenNull = true;
                                                }
                                            }//for k

                                            if (!isChildrenNull) {
                                                child.name = resultSearchTree[index].name;
                                                child.children = resultSearchTree[index].children;
                                                movedChildren.push(child.id);
                                            }
                                        }//if child.children
                                    }
                                }
                            }//for j
                        }//if contains
                    }

                    for (let j = 0; j < movedChildren.length; j++) {
                        let index = resultSearchTree.findIndex(x => x.id === movedChildren[j]);
                        // console.log("I-N-D-E-X-======", index, movedChildren[j]);
                        if (index >= 0) {
                            resultSearchTree.splice(index, 1);
                        }
                    }
                    //console.log("-----------------------------------------------");
                } while (movedChildren.length > 0);
                return resultSearchTree;
            },

            filteredKeys() {
                if (this.searchHashtag != null)   //for start view without search
                    if (this.searchLength === 0) {
                        return this.filteredElements.map((top) => {
                            return top.name
                        })
                    } else {
                        return this.filteredElements;
                    }
            }
        },

        ///////////////////////////////////////////WATCH////////////////////////////////////////////////////
        watch: {
            searchHashtag() {
                this.$nextTick(() => {
                    if (this.searchLength === 0) {
                        this.$refs.treeviewref.updateAll(false);
                    } else {
                        this.$refs.treeviewref.updateAll(true);
                    }
                });
            },

            locationSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 2) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedLocation !== 'undefined') {
                            // console.log("SELECTED IN WATCH");
                            // console.log(this.selectedLocation);
                            if (this.person.locationList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedLocation = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingLocation) return;
                        this.isLoadingLocation = true;

                        apiCountry.searchLocation(val, r => {
                            this.locationEntries = r;
                            console.log("****", this.locationEntries);
                            this.isLoadingLocation = false;
                        });
                    }
            },

            orgSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedOrg !== 'undefined') {
                            if (this.person.testList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedOrg = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingOrg) return;
                        this.isLoadingOrg = true;

                        //console.log("seracg org", val);

                        apiOrg.searchOrg(val, r => {
                            this.orgEntries = r;  //returns OrgDto (id, name(connected from different Org fields in OrgServImpl))
                            // console.log("****", this.orgEntries);
                            this.isLoadingOrg = false;
                        });
                    }
            },

            personSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedPerson !== 'undefined') {
                            if (this.person.personList.length > 1)
                                this.selectedPerson = "";
                        }
                        // Items have already been requested
                        if (this.isLoadingPerson) return;
                        this.isLoadingPerson = true;

                        api.searchPerson(val, r => {
                            this.personEntries = r;
                            // console.log("***ПОИСК ******", this.personEntries);
                            this.isLoadingPerson = false;
                        });
                    }
            },
        },
    }
</script>
