<template id="article-add">
    <v-app id="inspire">
        <div>
            <iframe id="iframeToDownload" style="display:none;"></iframe>

            <div class="form-group row" style="margin-bottom: 0">
                <div class="unprotected" v-if="errorFlag">
                    <h5>Error: {{errors}}</h5>
                </div>
            </div>

            <div class="col-lg-12" style="background-color: transparent; padding-top: 0">
                <!--                <form class="formCreation">-->
                <!--                    <div class="form-row align-items-center" style="background-color: transparent">-->
                <!--                        <div class="col-12" style="background-color: transparent">-->
                <!--                            <label>Текущее движение: {{currentUserMovement.name}} </label><br>-->
                <!--                        </div>-->
                <!--                        <div class="col-12" style="background-color: transparent">-->
                <!--                            <div v-if="isAdmin==='true'||addAdditionalMovementFlag">-->
                <!--                                <label>Добавить дополнительное движение:</label>-->
                <!--                                <div v-for="(movement, index) in allMovements">-->
                <!--                                    <input v-bind:value="movement.id" name="movement.name" type="checkbox"-->
                <!--                                           v-model="checkedMovements"/>-->
                <!--                                    <label :for="movement.id"><span>{{" . " + movement.name}}</span></label>-->
                <!--                                </div>-->
                <!--                            </div>-->
                <!--                            <div v-else>-->
                <!--                                <b-button size="sm" variant="info" @click="addAdditionalMovement">-->
                <!--                                    Добавить дополнительное движение-->
                <!--                                </b-button>-->
                <!--                            </div>-->
                <!--                        </div>-->
                <!--                    </div>-->

                <form class="formCreation"
                      style="background-color: transparent; margin-left: 30px; margin-bottom: 0; padding-bottom: 0">
                    <div class="col-md-4" style="margin-left: -15px; background-color: transparent">
                        <label><b>Тема*</b></label>
                        <div class="col-md-4" style="background-color: transparent">
                            <div v-for="(movement, index) in allMovements">
                                <input style="margin-right: 5px;" v-bind:value="movement.id" name="movement.name"
                                       type="checkbox"
                                       v-model="checkedMovements"/>
                                <label style="margin: 5px; padding-top: 0px; padding-bottom: 3px; background-color: transparent"
                                       :for="movement.id"><span>{{movement.name}}</span></label>
                            </div>
                        </div>
                    </div>

                    <div class="form-row align-items-center">
                        <label><b>Тип материала</b></label>
                        <div class="col-12" style="background-color: transparent">
                            <b-card style="background-color: transparent">
                                <v-row style="background-color: transparent; margin-top: -10px; margin-bottom: -10px;">
                                    <v-col style="background-color: transparent; margin-top: -10px; margin-left: -5px; margin-bottom: -10px">
                                        <v-sheet
                                                style="padding-left: 0; padding-top: 0; padding-right: 18px; background-color: transparent">
                                            <v-text-field label="поиск типа материала"
                                                          v-model="searchMType"
                                                          filled>
                                            </v-text-field>
                                        </v-sheet>

                                        <v-container
                                                id="scroll-target"
                                                style="max-height: 300px; background-color: transparent; margin-top: -10px; margin-left: -15px; padding-top: 0; padding-left: 0;"
                                                class="overflow-y-auto"
                                        >

                                            <v-treeview
                                                    :items="filteredMTypes"
                                                    :open="filteredOpenMTypes"
                                                    item-key="name"

                                                    activatable
                                                    color="warning"
                                                    dense
                                                    :open-all="true"

                                                    return-object
                                                    hoverable
                                                    ref="mtypetreeviewref">

                                                <template slot="label" slot-scope="{ item }">
                                                    <a @click="onMTypeSelect(item)">{{ item.name }}</a>
                                                </template>

                                            </v-treeview>
                                        </v-container>
                                    </v-col>

                                    <v-divider vertical
                                               style="background-color: transparent; margin-top: -10px; margin-left: -10px; margin-bottom: -10px;"></v-divider>

                                    <v-col
                                            style="background-color: transparent; margin-top: -10px; margin-left: -10px; margin-bottom: -10px;">

                                        <v-container
                                                id="scroll-target"
                                                style="max-height: 300px; background-color: transparent; margin-top: -10px;"
                                                class="overflow-y-auto">

                                            <template v-if="!isObjectValidAndNotEmpty(selectedMType.name)">
                                                Ничего не выбрано
                                            </template>

                                            <template v-else>
                                                <label style="background-color: transparent">Выбранный тип
                                                    материала</label>
                                                <div>
                                                    <v-btn text icon x-small @click="removeSelectedMType">
                                                        <v-icon style="color: red">mdi-delete-forever</v-icon>
                                                    </v-btn>
                                                    {{selectedMType.name}}
                                                </div>
                                            </template>
                                        </v-container>
                                    </v-col>
                                </v-row>
                            </b-card>
                        </div>
                    </div>
<!--                </form>-->

<!--                <form class="formCreation" style="background-color: transparent; margin-left: 15px; margin-bottom: 0; padding-bottom: 0">-->
                    <div class="form-row">
                        <div class="col-md-6">
                            <label for="add-linkS"><b>URL’s*</b></label>
                            <div>
                                <!--                                <textarea class="form-control" id="add-linkS"-->
                                <!--                                          placeholder="enter links with 'return'"-->
                                <!--                                          v-model="links"/>-->

                                <CreatedList :items="links"
                                             :isWithIdContentAdded="true"
                                             @update-item="updateLink"/>

                                <!--                                костылище редкой величины-->
                                <CreatedList :items="links"
                                             :isWithIdContentAdded="true"
                                             hidden="true"
                                             @update-item="updateLink"/>

                            </div>
                        </div>

                        <div class="col-md-3">
                            <label><b>Язык*</b></label>
                            <b-form-select v-model="selectedL" class="mb-3" :disabled="uploadMode"
                                           id="language-selection">-->
                                <option v-for="lang in allLanguages" v-bind:value="lang.id">{{lang.name}}</option>
                            </b-form-select>
                        </div>

                        <div class="col-md-3">
                            <label for="date-input"><b>Дата*</b></label>
                            <input class="form-control" type="date" id="date-input" :disabled="uploadMode"
                                   v-model="article.date">
                        </div>
                    </div>
<!--                </form>-->
<!--                <form class="formCreation">-->
                    <div class="form-row">
                        <div class="col-md-6">
                            <label for="add-title-rus"> <b>Заголовок на русском*</b></label>
                            <input class="form-control" id="add-title-rus"
                                   placeholder="Поле должно быть заполнено"
                                   :disabled="uploadMode" v-on:input="onTitleInput"
                                   v-model="article.titleRus"/>
                        </div>

                        <div class="col-md-6">
                            <label for="add-title"><b>Заголовок в оригинале</b></label>
                            <input class="form-control" id="add-title"
                                   :disabled="uploadMode"
                                   v-model="article.title"/>
                        </div>
                    </div>
                    <div>
                        <ul id="alreadyExistedArt">
                            <li v-for="item in alreadyExistedMaterial" :key="item.content">
                                {{ item.content }}
                            </li>
                        </ul>
                    </div>
                </form>

                <form class="authorsFormCreation"
                      style="background-color: transparent; padding: 25px 0 5px">
                    <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                        <div class="col-3"
                             style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                            <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                                <label style="font-size: medium; font-weight: bold; background-color: transparent">Связанные
                                    лица</label>
                                <v-autocomplete
                                        style="background-color: transparent"
                                        id="author-autocomplete"
                                        :items="items"
                                        :loading="isLoading"
                                        :search-input.sync="authorSearch"
                                        color="green"
                                        hide-no-data
                                        hide-selected

                                        v-model="selected"

                                        @change="addAuthor(selected)"
                                        item-text="content"
                                        item-value="id"
                                        placeholder="Начните печатать, чтобы найти автора"
                                        prepend-icon="mdi-database-search"
                                        return-object
                                        :disabled="uploadMode"
                                ></v-autocomplete>
                            </v-card-text>
                        </div>

                        <div v-if="personConnectionList.length>0" class="col-9"
                             style="background-color: transparent; padding:0; margin: 0">
                            <ConnectionComponent :itemsList="personConnectionList"
                                                 :isLinkMode="false"
                                                 :isSelectionMode="false"
                                                 :allTypes="connectionTypes"
                                                 style="background-color: transparent; padding:0" class="col-12"/>
                            <!--                                                 @update-item="updateItem"/>-->
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
                                        :items="itemsLocation"
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
                                        :disabled="uploadMode"
                                ></v-autocomplete>
                            </v-card-text>
                        </div>

                        <div v-if="locationConnectionList.length>0" class="col-9"
                             style="background-color: transparent; padding:0">
                            <ConnectionComponent :itemsList="locationConnectionList"
                                                 :isLinkMode="false"
                                                 :isSelectionMode="false"
                                                 :allTypes="connectionTypes"
                                                 style="background-color: transparent; padding:0" class="col-12"/>
                            <!--                                                 @update-item="updateItem"/>-->
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
                                        id="org-autocomplete"
                                        :items="itemsOrg"
                                        :loading="isLoadingOrg"
                                        :search-input.sync="orgSearch"
                                        color="blue"
                                        hide-no-data
                                        hide-selected

                                        v-model="selectedOrg"

                                        @change="addOrg(selectedOrg)"
                                        item-text="content"
                                        item-value="id"
                                        placeholder="Начните печатать, чтобы найти организацию"
                                        prepend-icon="mdi-database-search"
                                        return-object
                                        :disabled="uploadMode"
                                ></v-autocomplete>
                            </v-card-text>
                        </div>

                        <div v-if="orgConnectionList.length>0" class="col-9"
                             style="background-color: transparent; padding:0">
                            <ConnectionComponent :itemsList="orgConnectionList"
                                                 :isLinkMode="false"
                                                 :isSelectionMode="false"
                                                 :allTypes="connectionTypes"
                                                 style="background-color: transparent; padding:0" class="col-12"/>
                            <!--                                                 @update-item="updateItem"/>-->
                        </div>
                    </div>
                </form>
                <form class="formCreation">
                    <div class="form-row align-items-md-start" style="background-color: transparent">
                        <div class="col-5" style="background-color: transparent">
                            <label style="background-color: transparent"><b>Хештеги</b></label>
                            <input-tag id="add-hashtag1" :add-tag-on-keys="addTagOnKeys"
                                       :read-only="uploadMode"
                                       v-model="tags"></input-tag>
                        </div>
                        <div class="col-7" style="background-color: transparent">
                            <label><b>Форма добавления хештегов</b></label>
                            <b-card style="background-color: transparent">
                                <v-row style="background-color: transparent; margin-top: -10px; margin-bottom: -10px;">
                                    <v-col style="background-color: transparent; margin-top: -10px; margin-left: -5px; margin-bottom: -10px">
                                        <v-sheet
                                                style="padding-left: 0px; padding-top: 0px; padding-right: 18px; background-color: transparent">
                                            <v-text-field label="search hashtag"
                                                          v-model="searchHashtag"
                                                          filled>
                                            </v-text-field>
                                        </v-sheet>

                                        <v-container
                                                id="scroll-target"
                                                style="max-height: 300px; background-color: transparent; margin-top: -10px; margin-left: -15px; padding-top: 0px; padding-left: 0;"
                                                class="overflow-y-auto"
                                        >

                                            <v-treeview
                                                    :items="filteredHashtags"
                                                    :open="filteredOpenHashtags"
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
                                </v-row>
                            </b-card>
                        </div>
                    </div>
                </form>
                <form class="authorsFormCreation"
                      style="background-color: transparent; padding: 25px 0 5px">
                    <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                        <div class="col-3"
                             style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                            <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                                <label style="font-size: medium; font-weight: bold">Связанные материалы</label>
                                <v-autocomplete
                                        id="material-autocomplete"
                                        :items="itemsMaterial"
                                        :loading="isLoadingMaterial"
                                        :search-input.sync="materialSearch"
                                        color="purple"
                                        hide-no-data
                                        hide-selected

                                        v-model="selectedMaterial"

                                        @change="addMaterial(selectedMaterial)"
                                        @focus="testFocus(selectedMaterial)"
                                        item-text="material"
                                        item-value="id"
                                        placeholder="Начните печатать, чтобы материал"
                                        prepend-icon="mdi-database-search"
                                        return-object
                                        :disabled="uploadMode"
                                ></v-autocomplete>
                            </v-card-text>
                        </div>

                        <div v-if="materialConnectionList.length>0" class="col-9"
                             style="background-color: transparent; padding:0">
                            <ConnectionComponent :itemsList="materialConnectionList"
                                                 :isLinkMode="true"
                                                 :isSelectionMode="true"
                                                 :allTypes="connectionTypes"
                                                 style="background-color: transparent; padding:0" class="col-12"
                                                 @update-selection="updateSelection"/>
                        </div>
                    </div>
                </form>
                <form class="formCreation">
                    <div class="form-row align-items-center">
                        <label for="add-descr"><b>Описание</b></label>
                        <div class="col-12" :disabled="uploadMode">
                            <ckeditor :editor="editor" id="add-descr" v-model="article.description"
                                      :config="editorConfig"></ckeditor>

                        </div>
                    </div>

                    <div class="form-row align-items-center">
                        <label for="add-text"><b>Текст</b></label>
                        <div class="col-12">
                            <ckeditor :editor="editor" id="add-text" v-model="article.text"
                                      :config="editorConfig"></ckeditor>
                        </div>
                    </div>

                    <div class="form-row align-items-center">
                        <!--                        <div class="form-group green-border-focus">-->
                        <label for="add-misc"><b>Комментарии</b></label>
                        <div class="col-12 green-border-focus">

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="article.miscellany"
                                              :disabled="uploadMode"/>
                        </div>
                    </div>

                    <div v-if="editMode" class="form-row align-items-center" style="background-color: transparent">
                        <label class="col-1 col-form-label labelInCreation"
                               style="vertical-align: center; background-color: transparent">Текущий статус</label>
                        <div class="col-3" style="background-color: transparent">
                            <b-form-select v-model="selectedS" class="mb-0" id="status-selection"
                                           style="background-color: transparent;">
                                <option v-for="status in statusList">
                                    {{status.name}}
                                </option>
                            </b-form-select>
                            <!--                    <div class="mb-3">SELECted: <strong>{{ selectedL }}</strong></div>-->
                        </div>

                    </div>
                    <div class="form-row align-items-center" style="background-color: transparent">
                        <div class="form-row align-items-center col-sm-12"
                             style="background-color: transparent; padding-left: 10px">
                            <div class="form-group" style="padding-top: 25px">
                                <input style="margin-right: 5px" type="checkbox" id="checkbox_сolor"
                                       v-model="disableColorCheckBoxFlag">
                                <label for="checkbox">Снять выделение</label>
                            </div>
                            <!--                        </div>-->

                            <label class="col-2 col-form-label labelInCreation"
                                   style="vertical-align: center; background-color: transparent; margin-left: 40px; margin-right: -50px">Текущий
                                цвет выделения</label>
                            <!--                        <div class="col-1" style="background-color: transparent; padding: 0; vertical-align: center;">-->
                            <v-swatches style="margin-top: 5px" v-model="article.rowColor"
                                        :disabled="disableColorCheckBoxFlag"
                                        popover-x="left"></v-swatches>
                        </div>
                    </div>

                    <div v-if="uploadMode||editMode">
                        <file-attachment @attachFiles="createAttachment"
                                         @getAttachment="getAttachment"
                                         @downloadAttachment="downloadAttachment"
                                         @removeAttachment="removeAttachment"
                                         :userName="loggedName"
                                         :already-uploaded-files="uploadedFiles"
                                         :is-details-mode="false"/>
                    </div>
                </form>

<!--                <div v-if="editMode" class="form-group row align-items-center">-->
                <div v-if="editMode" class="form-row align-items-center" style="background-color: transparent">
                    <div class="offset-sm-4 col-sm-3">

                        <button type="button" @click="preliminaryDataCheck" class="btn btn-primary">Обновить</button>
                        <a class="btn btn-default">
                            <router-link to="/article">Отмена</router-link>
                        </a>
                    </div>
                </div>

                <div v-else class="form-group row" style="padding-top: 30px">

                    <div v-if="!uploadMode" class="form-group align-items-center col-sm-12"
                         style="background-color: transparent">
                        <div class="form-group align-items-center col-sm-12" style="background-color: transparent">
                            <input type="checkbox" id="checkbox" v-model="uploadFilesCheckBoxValue">
                            <label for="checkbox">Нажмите "галочку", если хотите добавить файлы</label>
                        </div>

                        <button type="button" style="margin-right: 20px" @click="preliminaryDataCheck(statusList[0])"
                                class="btn btn-warning">В работе
                        </button>
                        <button type="button" style="margin-right: 20px" @click="preliminaryDataCheck(statusList[1])"
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
            </div>
        </div>
    </v-app>
</template>

<script>
    import api from "./article-api";
    import apiPerson from "./../person/person-api";
    import apiCountry from "./../country/country-api";
    import apiOrg from "./../org/org-api";
    import apiLanguage from "./../language/language-api";
    import apiMovement from "./../movement/movement-api";
    import apiHashtag from "./../hashtag/hashtag-api";
    import apiMType from "./../mtype/mtype-api";
    import apiAttachment from "./../attachment-api";
    import apiStatus from "./../status-api";

    import FileAttachment from "../components/FileAttachment";
    import moment from "moment";
    import InputTag from 'vue-input-tag';
    import router from "./../../router";
    import Vue from 'vue';
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';
    import ConnectionComponent from "../components/connection/ConnectionComponent";
    import CreatedList from "../components/multiple-enter-list/CreatedList";
    import CKEditor from 'ckeditor4-vue';
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import apiLogin from "../login-api";

    Vue.component('input-tag', InputTag);

    export default {
        components: {
            CreatedList,
            ConnectionComponent,
            FileAttachment,
            ckeditor: CKEditor.component,
            VSwatches
        },
        name: 'article-add',
        vuetify: new Vuetify(),

        data: () => ({
            test: "articleProp",
            //addTagOnKeys: [13, 9], //enter and tab
            addTagOnKeys: [],  //setting it empty is to prevent manual enter in input-tag))
            descriptionLimit: 60,

            alreadyExistedMaterial: [],

            personEntries: [],
            locationEntries: [],
            orgEntries: [],
            materialEntries: [],

            isLoading: false,
            isLoadingLocation: false,
            isLoadingOrg: false,
            isLoadingMaterial: false,

            authorSearch: null,
            locationSearch: null,
            orgSearch: null,
            materialSearch: null,

            searchHashtag: '',
            searchMType: '',

            selectedM: null,
            selectedL: null,  //language
            selectedS: null,  //status

            errorFlag: false,
            errors: [],
            hasError: false,

            personConnectionList: [],
            locationConnectionList: [],
            orgConnectionList: [],
            materialConnectionList: [],

            links: [],
            tags: [],    //connected with hashtag input
            mtype: '',
            connectionTypes: [],
            selectionType: 'independent',

            // selectedHashtag: [],
            selectedMType: {},

            hashtagFlatTree: [],
            mtypeFlatTree: [],

            allLanguages: [],
            allMovements: [],
            checkedMovements: [],
            // currentUserMovement: '',
            addAdditionalMovementFlag: false,

            article: {
                personList: [],
                locationList: [],
                hashtagList: [],
                orgList: [],
                materialList: [],
                movementList: []
            },

            selected: [],
            selectedLocation: [],
            selectedOrg: [],
            selectedMaterial: [],

            articlePersonIds: [], //before request
            articlePersonEntities: [], //after request
            articleLocationIds: [], //before request
            articleLocationEntities: [], //after request
            articleOrgIds: [], //before request
            articleOrgEntities: [], //after request
           // articleMaterialIds: [], //before request
            articleMaterialEntities: [], //after request

            statusList: [],
            editMode: false,

            avatar: {
                image: null,
                imageUrl: null,
                imageBase64: null
            },

            attachedFiles: [],
            loggedName: null,
            isAdmin: null,
            disableColorCheckBoxFlag: false,
            uploadFilesCheckBoxValue: false,
            uploadMode: false,
            uploadedFiles: [],
            editor: CKEditor, // to use the component locally
            editorConfig: {
                // The configuration of the editor.
            }
        }),

        methods: {
            onTitleInput(){
                if (this.article.titleRus.length > 3) {
                    // console.log("IN ONPUT");

                    api.searchMaterial(this.article.titleRus, r => {
                        this.alreadyExistedMaterial = r;
                       // console.log("*№*№*№*", this.alreadyExistedMaterial);
                    });
                } else {
                    this.alreadyExistedMaterial.splice(0);
                }
            },

            removeAttachment(file) {
                // console.log("removeAtt ARTICLE", file);
                apiAttachment.removeAttachment('article', this.article.id, file.id, file.name, r => {
                    //console.log("result", r.data);
                    if (r.data === true) {
                        const index = this.uploadedFiles.indexOf(file);
                        if (index > -1) {
                            this.uploadedFiles.splice(index, 1);
                        }
                    } else {
                        alert("file deletion error");
                    }
                });
            },

            addAdditionalMovement() {
                this.addAdditionalMovementFlag = true;
            },

            uploadFiles() {             //on button press
                for (let i = 0; i < this.attachedFiles.length; i++) {
                    apiAttachment.uploadFile('article', this.article.id, this.attachedFiles[i], r => {
                    });
                    // this.submitFile(this.attachedFiles[i]);
                    //todo progress bar?
                }
                router.push('/article');
            },

            createAttachment(files) {     //emit from FilesAttachment Component 'attachFiles'
                console.log("files from COMPONENT", files);
                this.attachedFiles = [];
                for (let i = 0; i < files.length; i++) {
                    this.attachedFiles.push(files[i]);
                }
            },

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/article/downloadAttachment?entityId=' + this.article.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                apiAttachment.previewAttachment('article', this.article.id, file.id);
            },

            // removeSelectedHashtag(hash) {
            //     const index = this.selectedHashtag.indexOf(hash);
            //     if (index > -1) {
            //         this.selectedHashtag.splice(index, 1);
            //     }
            // },

            removeSelectedMType() {
                this.selectedMType = '';
            },

            onHashtagSelect(item) {
                const index = this.tags.indexOf(item.name);
                if (index === -1) {
                    this.tags.push(item.name);
                }
            },

            onMTypeSelect(item) {  //todo?
                this.selectedMType = item;//.name;
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

            // addHashtagToArticleList(hashtag) {//from HashtagList
            //     this.tags = hashtag;
            // },

            // addHashtagToArticleList() {
            //     let tagAlreadyAdded = 0;
            //     this.selectedHashtag.forEach((item, i) => {
            //         this.tags.forEach((tag, j) => {
            //             if (tag === item)
            //                 tagAlreadyAdded = 1;
            //         });
            //         if (tagAlreadyAdded == 0)
            //             this.tags.push(item);
            //     });
            //     this.selectedHashtag = [];
            // },

            testFocus(obj) {
                console.log("GET FOCUS");
                if (typeof selected !== 'undefined') console.log(selected); // Any scope console.log(selected);
            },

            addAuthor(obj) {
                let i = 0;
                for (i = 0; i < this.personConnectionList.length; i++) { //to exclude double values
                    if (this.personConnectionList[i].id === obj.id) {
                        break;
                    }
                }
                if (i === this.personConnectionList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.content,// + " " + obj.name,
                        "connection": '',
                        "comment": '',
                        "hasClicked": false
                    };
                    this.personConnectionList.push(connection);
                    console.log("ADDED");
                }
            },

            // deleteAuthor(author) {
            //     if (!this.uploadMode) {
            //         for (let i = 0; i < this.article.authorList.length; i++) {
            //             if (this.article.authorList[i].id === author.id) {
            //                 this.article.authorList.splice(i, 1);
            //             }
            //         }
            //     }
            // },

            addLocation(obj) {
                console.log("GET CHANGED LOCATION", obj);
                if (this.isObjectValidAndNotEmpty(obj)) {
                    let i = 0;
                    for (i = 0; i < this.locationConnectionList.length; i++) { //to exclude double values
                        if (this.locationConnectionList[i].id === obj.id) {
                            break;
                        }
                    }

                    if (i === this.locationConnectionList.length) {
                        let connection = {
                            "id": obj.id,
                            "name": obj.content,
                            "comment": '',
                            "connection": '',
                            "hasClicked": false
                        };
                        this.locationConnectionList.push(connection);
                        //console.log("ADDED");
                    }
                }
            },

            addOrg(obj) {
                // console.log("GET CHANGED LOCATION");
                let i = 0;
                for (i = 0; i < this.orgConnectionList.length; i++) { //to exclude double values
                    if (this.orgConnectionList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.orgConnectionList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.content,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.orgConnectionList.push(connection);
                    console.log("ADDED");
                }
            },

            addMaterial(obj) {
                //console.log("addMaterial addMaterial addMaterial", obj);
                let i = 0;
                for (i = 0; i < this.materialConnectionList.length; i++) { //to exclude double values
                    if (this.materialConnectionList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.materialConnectionList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.content,  //title was created in ArticleServiceImpl
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.materialConnectionList.push(connection);
                    // console.log("ADDED");
                }
            },

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

            validDate: function (code) {
                let re = /([12][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))$/; ///digit format "inside", see it while debugging
                // var re = /((0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[0-2])[.][12][0-9]{3})$/;
                return re.test(code);
            },

            formatDate(date) {
                return moment(date).format('YYYY-MM-DD');
                // return moment(date).format('DD/MM/YYYY');
            },

            formValidate() {
                this.addStatus('add-linkS', (!this.links.length));
                if (this.hasError) {
                } else {
                    this.addStatus('language-selection', (!this.selectedL));
                    if (this.hasError) {
                    } else {
                        this.addStatus('date-input', (!(this.validDate(this.article.date))));
                        if (this.hasError) {
                        } else {
                            this.addStatus('add-title-rus', (!(this.article.titleRus)));

                            if (this.checkedMovements.length === 0) {
                                this.hasError = true;
                            }
                            // if (this.hasError) {
                            //     this.addStatus('add-title-rus', true);
                            // } else {
                            //     this.addStatus('add-title-rus', false);
                            // }
                        }
                    }
                }
                if (this.hasError) {
                    alert("Пожалуйста, заполните все обязательные поля");
                    console.log('ERROROROR----------------------------');
                }
                return !this.hasError;
            },

            //////todo delete/////
            // updateItem(item) {
            //     console.log("<3 ITEM AFTER COMPONENT", item);
            //     console.log("LIST AFTER COMPONENT", this.personConnectionList);
            // },

            updateSelection(item) {
                let index = this.connectionTypes.findIndex(x => x.id === item);
                console.log("<3 <3 ITEM AFTER COMPONENT", item, this.connectionTypes.id === item);
            },
            ///////////////////

            updateLink(link) {
                //   this.links.push(link.content);
                // this.links.push(link);
                console.log("ADDED LINK", link, this.links);
            },

            // actOnEachLine(textarea, func) {
            //     let lines = textarea.value.replace(/\r\n/g, "\n").split("\n");
            //     let newLines, i;
            //
            //     // Use the map() method of Array where available
            //     if (typeof lines.map != "undefined") {
            //         newLines = lines.map(func);
            //     } else {
            //         newLines = [];
            //         i = lines.length;
            //         while (i--) {
            //             newLines[i] = func(lines[i]);
            //         }
            //     }
            //     textarea.value = newLines.join("\r\n");
            // },

            actOnEachLine(textarea, func) {
                let lines = textarea.value.replace(/\r\n/g, "\n").split("\n");
                console.log("777777777777777777", lines);

                let newLines, i;

                // Use the map() method of Array where available
                if (typeof lines.map != "undefined") {
                    newLines = lines.map(func);
                    console.log("IF 7878787878787", newLines);
                } else {

                    newLines = [];
                    i = lines.length;
                    while (i--) {
                        newLines[i] = func(lines[i]);
                    }
                    console.log("ELSE 7878787878787", newLines);
                }
                // textarea.value = newLines.join("\r\n");
                return newLines;
            },

            checkConnection(list) {
                for (let i = 0; i < list.length; i++) {
                    if (list[i].connection.length <= 0) {
                        // console.log("AXTUNG");
                        return true;
                    }
                }
                return false;
            },

            preliminaryDataCheck(currentStatus) {
                let y = false, t = false, g = false, o = false;

                if (this.isObjectValidAndNotEmpty(this.personConnectionList)) {
                    y = this.checkConnection(this.personConnectionList)
                }
                if (!y) {
                    // if (this.isObjectValidAndNotEmpty(this.locationConnectionList)) {
                    //     t = this.checkConnection(this.locationConnectionList);
                    // }
                    // if (!t) {
                    // if (this.isObjectValidAndNotEmpty(this.orgConnectionList)) {
                    //     g = this.checkConnection(this.orgConnectionList);
                    // }
                    // if (!g) {
                    if (this.isObjectValidAndNotEmpty(this.materialConnectionList)) {
                        o = this.checkConnection(this.materialConnectionList);
                    }
                }
                //}
                // }

                if (y || o) {  // if (y || t || g || o) {
                    alert("Укажите связь для сущностей, которые вы добавили");
                    // console.log("ALERT");
                } else {
                    // alert("else");
                    if (this.editMode) {
                        this.updateArticle();
                    } else {
                        this.createArticle(currentStatus);
                    }
                }
            },

            createArticle(currentStatus) {

                this.article.language = {
                    "id": this.selectedL
                };

                this.article.status = currentStatus.name;
                this.hasError = false;

                for (let i = 0; i < this.tags.length; i++) {
                    this.article.hashtagList[i] = this.tags[i];
                }

                this.article.linkList = [];

                for (let i = 0; i < this.links.length; i++) {
                    this.article.linkList[i] = {
                        "content": this.links[i].content
                    };
                }

                let i = 0;
                for (; i < this.checkedMovements.length; i++) {
                    this.article.movementList[i] = {
                        "id": this.checkedMovements[i]
                    };
                }

                // this.article.movementList[i] = {
                //     "id": this.currentUserMovement.id
                // };


                // let textarea = document.getElementById("add-linkS");
                // let lines = textarea.value.replace(/\r\n/g, "\n").split("\n");
                // for (let i = 0; i < lines.length; i++) {
                //     this.article.linkList[i] = {
                //         "content": lines[i]
                //     };
                // }


                // this.actOnEachLine(textarea, function (line) {
                //     console.log(line);
                //    // return "[START]" + line + "[END]";
                //    //  this.article.linkList.push({
                //    //      "content": line});
                //
                //     ttt = {
                //         "content": line,
                //     };
                //
                //     this.article.linkList.push(ttt);
                //     console.log(this.article.linkList);
                //
                // });


                //mtype has structure (from t_material_type)
                // id, content, misc, parentId
                //but here mtype is a leaf of tree, that has structure
                //id, name, children

                this.article.mtype = {
                    "id": this.selectedMType.id,
                    // "content": this.selectedMType.name,
                    // "miscellany":"123",
                    // "parentId": 1
                };

                this.article.personList.splice(0);
                this.article.locationList.splice(0);
                this.article.orgList.splice(0);
                this.article.materialList.splice(0);

                this.finalConnectionListCreation(this.personConnectionList, this.article.personList, false);
                this.finalConnectionListCreation(this.locationConnectionList, this.article.locationList, true);
                this.finalConnectionListCreation(this.orgConnectionList, this.article.orgList, false);
                this.finalConnectionListCreation(this.materialConnectionList, this.article.materialList, false);

                if (this.formValidate()) {
                    console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>to save article with", this.article);
                    api.create(this.article, r => {
                        if (!this.uploadFilesCheckBoxValue) {
                            router.push('/article');
                        } else {
                            this.uploadMode = true;
                            this.uploadFilesCheckBoxValue = false;
                            let ID = r.data.id;
                            this.article.id = ID;
                            console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>stay here, ARTICLE ID", r.data, ID);
                        }
                    });
                }
                // console.log("BEFORE SAVING", this.article);
            },

            updateArticle() {
                this.article.language = {
                    "id": this.selectedL
                };

                this.article.status = this.selectedS;
                this.hasError = false;

                this.article.linkList.splice(0);
                this.article.hashtagList.splice(0);
                this.article.movementList.splice(0);

                //console.log("#*#*#*#*#*#*#*#*#*#*#*#*#    LINKS", this.links, this.links.length);
                for (let i = 0; i < this.links.length; i++) {
                    this.article.linkList[i] = {
                        "content": this.links[i].content
                    };
                }

                let i = 0;
                for (; i < this.checkedMovements.length; i++) {
                    this.article.movementList[i] = {
                        "id": this.checkedMovements[i]
                    };
                }

                this.article.hashtagList = [];
                for (let i = 0; i < this.tags.length; i++) {
                    this.article.hashtagList[i] = this.tags[i];
                }

                this.article.mtype = {
                    "id": this.selectedMType.id,
                    // "content": this.selectedMType.name,
                    // "miscellany":"123",
                    // "parentId": 1
                };

                //console.log(" BEFORE [] this.article.MTYPE: ", this.article.mtype);
                // console.log(" BEFORE [] this.article.person: ", this.article.locationList);

                this.article.personList.splice(0);
                this.article.locationList.splice(0);
                this.article.orgList.splice(0);
                this.article.materialList.splice(0);

                this.finalConnectionListCreation(this.personConnectionList, this.article.personList, false);
                this.finalConnectionListCreation(this.locationConnectionList, this.article.locationList, true);
                this.finalConnectionListCreation(this.orgConnectionList, this.article.orgList, true);
                this.finalConnectionListCreation(this.materialConnectionList, this.article.materialList, false);

                //console.log("SAVE SAVE SAVE SAVE SAVE SAVE SAVE this.article", this.article);

                if (this.formValidate()) {
                    api.update(this.article.id, this.article, r => {
                        for (let i = 0; i < this.attachedFiles.length; i++) {
                            apiAttachment.uploadFile('article', this.article.id, this.attachedFiles[i], r => {
                            });
                            //todo progress bar?
                        }
                        router.push('/article');
                    });
                }
            },

            getLoggedIn() {
                //this.checkedMovements.push(localStorage.getItem('movement'));  //need if we want to show current movement in checkbox list
                //console.log("PUSHED CHECKED MOV");
                //this.toKey++;

                this.loggedName = localStorage.getItem('userName');
                this.isAdmin = localStorage.getItem('isAdmin');
            },

            finalConnectionListCreation(list, finalList, isEmptyConnectionPossible) {
                console.log("^^^^^^^^^^^^^^^finalConnectionListCreation^^^^^^^^^ ", list, finalList);
                for (let i = 0; i < list.length; i++) {
                    let a = {
                        "itemId": list[i].id,
                        "name": list[i].name,
                        "connection": list[i].connection,
                        "comment": list[i].comment
                    };
                    // console.log("CREATE PERS ON A: ", a);

                    if (isEmptyConnectionPossible) {
                        finalList.push(a);

                    } else if (a.connection.length > 0) { //to avoid add empty connections (wasn't entered)
                        finalList.push(a);
                    }
                    // console.log("CREATE PERS ON A: ", this.article.personList);
                }
            },

            isArrayValidAndNotEmpty(array) {

                if (typeof array === 'undefined' || array === null || array.length === 0) {
                    return false;
                }
                return true;
            },

            isObjectValidAndNotEmpty(obj) {
                return !(typeof obj === 'undefined' || obj === null);
            },

            locationEditConnectionTitleCreation(location) {

                let returnedTitle = location.country;

                if (this.isArrayValidAndNotEmpty(location.region)) {
                    returnedTitle += ", " + location.region;
                }
                if (this.isArrayValidAndNotEmpty(location.city)) {
                    returnedTitle += ", " + location.city;
                }
                if (this.isArrayValidAndNotEmpty(location.address)) {
                    returnedTitle += ", " + location.address;
                }
                if (this.isArrayValidAndNotEmpty(location.placement)) {
                    returnedTitle += ", " + location.placement;
                }
                return returnedTitle;
            },

            // materialEditConnectionTitleCreation(material) {
            //     let returnedTitle = '';
            //
            //     if (this.isArrayValidAndNotEmpty(material.title)) {
            //         returnedTitle += material.title;
            //
            //         if (this.isArrayValidAndNotEmpty(material.titleRus)) {
            //             returnedTitle += "/ " + material.titleRus;
            //         }
            //     } else {
            //         returnedTitle = material.titleRus;
            //     }
            //     return returnedTitle;
            // },

            // orgEditConnectionTitleCreation(org) {
            //     let returnedTitle = org.nameRus;
            //
            //     if (this.isArrayValidAndNotEmpty(org.abbrRus)) {
            //         returnedTitle += ", " + org.abbrRus;
            //     }
            //     if (this.isArrayValidAndNotEmpty(org.name)) {
            //         returnedTitle += ", " + org.name;
            //     }
            //     //todo add fields
            //
            //     return returnedTitle;
            // },

            //////////////////tree/////////////////////////////////////
            searchTree(flatTree, searchEntity) {
                let touched = false;
                let resultSearchTree = [];

                resultSearchTree = JSON.parse(JSON.stringify(flatTree)); //the way for deep copy
                // resultSearchTree = JSON.parse(JSON.stringify(this.hashtagFlatTree)); //the way for deep copy
                do {
                    // remove leaves which do not match search string
                    let toRemove = [];
                    touched = false;

                    resultSearchTree.forEach((node, id) => {
                        const index = node.name.toLowerCase().indexOf(searchEntity.toLowerCase()) >= 0;
                        if ((index === false) && (node.children.length === 0)) {
                            console.log("node.children.length (to remove)", node.children.length, node.name);
                            toRemove.push(node.id);
                            touched = true;
                        }
                    });

                    for (let i = 0; i < toRemove.length; i++) {
                        let index = resultSearchTree.findIndex(x => x.id === toRemove[i]);
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

                return resultSearchTree;
            },

            flatTreeToTree(resultSearchTree) {
                let tree = [];
                let resultIds = [];
                let touched = false;

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
        },

        mounted() {
            this.getLoggedIn();
            //   console.log("MOUNTED_ART_ADD");
            // api.getAllAuthors().then(response => {
            //     this.authorListForAutocomplete = response.data;
            //     console.log(response.data);
            // });

            // apiMovement.getAllMovements(response => {
            //     // this.getLoggedIn();
            //     this.allMovements = response.data;
            //     console.log("MOVEMENTS", response.data);
            //     this.currentUserMovement = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));//this.checkedMovements[0]);
            //
            //     let currentIndex = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));//this.checkedMovements[0]);
            //     let ddd = this.allMovements.indexOf(currentIndex);
            //     this.allMovements.splice(ddd, 1);
            //     //console.log("MOVEMENTS index", response.data, currentIndex, ddd);
            // });


            apiLogin.getAccount().then(
                response => {
                    console.log("Account retrieved :" + response.data);

                    this.allMovements = response.data.movementList;
                    console.log("USER MOVEMENTS", this.allMovements);

                    if (this.allMovements.length === 1) {
                        this.checkedMovements.push(this.allMovements[0].id);
                    }
                }
            ).catch(
                error => {
                    console.log("Error: " + error);
                }
            );

            api.getConnectionTypes(response => {
                this.connectionTypes = response.data;
                console.log("connectionTypes---------------", response.data)
            });

            apiLanguage.getAllLanguages(response => {
                this.allLanguages = response.data;
                console.log(response.data)
            });

            apiStatus.getAllStatuses(response => {
                this.statusList = response.data;
                console.log("STATUS LIST", this.statusList);
            });

            apiHashtag.getAllHashtags(response => {
                // this.allTags = response.data;
                this.hashtagFlatTree = this.createFlatTree(response.data);
            }).catch(error => {
                this.errors.push(error)
            });

            apiMType.getAll(response => {
                // this.allMTypes = response.data;
                this.mtypeFlatTree = this.createFlatTree(response.data);

            }).catch(error => {
                this.errors.push(error)
            });

            //console.log("==================PARAMS", this.$route.params);

            if (this.$route.params.id != null) {
                //console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@MATERIAL MODE");

                api.searchMaterialById(this.$route.params.id, r => {  //for the case with article creation with parent/child/equal
                    this.materialEntries = r;
                    //console.log("search by Id*№*№*№*", this.materialEntries);

                    if (this.materialEntries.length > 0) {
                        let connection = {
                            "id": this.materialEntries[0].id,
                            "name": this.materialEntries[0].content,
                            "comment": '',
                            "connection": this.$route.params.connectionType.text,
                            "hasClicked": false
                        };
                        this.materialConnectionList.push(connection);
                    }
                });
            }

            if (this.$route.params.article_id != null) {
                console.log("EDIT MODE");
                this.editMode = true;
                this.uploadFilesCheckBoxValue = true;
            }

            if (this.editMode) {
                api.findById(this.$route.params.article_id, r => {
                    this.article = r.data;
                    console.log("article EDIT!", this.article);

                    this.selectedL = this.article.language.id;

                    // let currentPersonEntity = this.articlePersonEntities.find(person => person.id === element.itemId);
                    // this.selectedS = this.statusList.find(status => status.name === this.article.status).id;

                    this.selectedS = this.article.status;
                    // console.log("STATUS", this.article.status);
                    this.article.date = this.formatDate(this.article.date);

                    //mtype has structure (from t_material_type)
                    // id, content, misc, parentId
                    //but here mtype is a leaf of tree, that has structure
                    //id, name, children

                    //console.log("selectedMTYPE", this.article.mtype);
                    if (this.isObjectValidAndNotEmpty(this.article.mtype)) {
                        this.selectedMType = {
                            "id": this.article.mtype.id,
                            "name": this.article.mtype.content,
                        };
                        // console.log("selectedMTYPE", this.selectedMType, this.article);
                    }

                    for (let i = 0; i < this.article.hashtagList.length; i++) {
                        this.tags.push(this.article.hashtagList[i]);
                    }

                    for (let i = 0; i < this.article.linkList.length; i++) {
                        this.links.push(this.article.linkList[i]);
                    }

                    for (let i = 0; i < this.article.movementList.length; i++) {
                        this.checkedMovements.push(this.article.movementList[i].id);
                    }

//                    console.log("::::::::::::::::LINK LIST", this.article.linkList);
//                    console.log("::::::::::::::::LINKS", this.links);


                    apiAttachment.getAttachments('article', this.article.id, r => {
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });

                    for (let j = 0; j < this.article.personList.length; j++) {
                        this.articlePersonIds.push(this.article.personList[j].itemId);
                    }
                    // console.log(this.articlePersonIds);

                    for (let j = 0; j < this.article.locationList.length; j++) {
                        this.articleLocationIds.push(this.article.locationList[j].itemId);
                    }

                    for (let j = 0; j < this.article.orgList.length; j++) {
                        this.articleOrgIds.push(this.article.orgList[j].itemId);
                    }

                    // for (let j = 0; j < this.article.materialList.length; j++) {
                    //     this.articleMaterialIds.push(this.article.materialList[j].itemId);
                    // }

                    apiPerson.getPersonsByIds(this.articlePersonIds, response => {  ///returns List<NewPersonDto>
                        this.articlePersonEntities = response.data;
                        console.log("apiPerson", this.articlePersonEntities);

                        for (let i = 0; i < this.article.personList.length; i++) {
                            let element = this.article.personList[i];
                            let currentPersonEntity = this.articlePersonEntities.find(person => person.id === element.itemId);
                            let connection = {
                                "id": element.itemId,
                                "name": currentPersonEntity.content, //currentPersonEntity.surname + " " + currentPersonEntity.name,//todo?
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.personConnectionList.push(connection);
                        }
                    });

                    apiCountry.getLocationsByIds(this.articleLocationIds, response => {  ///returns List<Location>
                        this.articleLocationEntities = response.data;
                        console.log("apiLocation", this.articleLocationEntities);

                        for (let i = 0; i < this.article.locationList.length; i++) {
                            let element = this.article.locationList[i];
                            let currentLocationEntity = this.articleLocationEntities.find(location => location.id === element.itemId);
                            let connection = {
                                "id": element.itemId,
                                "name": currentLocationEntity.content, //this.locationEditConnectionTitleCreation(currentLocationEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.locationConnectionList.push(connection);
                        }
                        // console.log("locationConnectionList: ", this.locationConnectionList);
                    });

                    apiOrg.getOrgsByIds(this.articleOrgIds, response => {  ///returns List<Location>
                        this.articleOrgEntities = response.data;   //returns List<Org>
                        console.log("apiOrg", this.articleOrgEntities);

                        for (let i = 0; i < this.article.orgList.length; i++) {
                            let element = this.article.orgList[i];
                            let currentOrgEntity = this.articleOrgEntities.find(org => org.id === element.itemId);
                            console.log("currentOrgEntity", currentOrgEntity);
                            let connection = {
                                "id": element.itemId,
                                "name": currentOrgEntity.content, //this.orgEditConnectionTitleCreation(currentOrgEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.orgConnectionList.push(connection);
                        }
                        //console.log("orgConnectionList: ", this.orgConnectionList);
                    });


                    api.getMaterialsByIdsAndSymmetrically(this.article.id, response => {
                        this.articleMaterialEntities = response.data;  //returns list<NameConnectionDto>
                        console.log("articleMaterialEntities", this.articleMaterialEntities);

                        for (let i = 0; i < this.articleMaterialEntities.length; i++) {
                            let connection = {
                                "id": this.articleMaterialEntities[i].itemId,
                                "name": this.articleMaterialEntities[i].name, //this.materialEditConnectionTitleCreation(currentMaterialEntity),
                                "connection": this.articleMaterialEntities[i].connection, //connectionType.type,
                                "comment": this.articleMaterialEntities[i].comment,
                                "hasClicked": true
                            };
                            //    console.log("CREATE MATERIALS: ", connection);
                            this.materialConnectionList.push(connection);
                        }
                        //console.log("materialConnectionList: ", this.materialConnectionList);
                    });
                });
            }
        },

        ///////////////////////////COMPUTED/////////////////////////////////
        computed: {
            items() {
                return this.personEntries.map(entry => {
                    const surname = entry.content;
                    return Object.assign({}, entry, {surname})
                })
            },

            itemsLocation() {
                //console.log("itemsLocation", this.locationEntries.map);
                console.log("itemsLocation", this.locationEntries);

                return this.locationEntries.map(entry => {
                    const country = entry.content;
                    return Object.assign({}, entry, {country})
                })
            },

            itemsOrg() {
                //console.log("itemsOrg", this.orgEntries.map);
                //console.log("itemsOrg", this.orgEntries);

                if (this.orgEntries) {      ///todo analyze why undefined (after selection in the search list)
                    //console.log("~@~@~@~@~", this.orgEntries);
                    return this.orgEntries.map(entry => {
                        const org = entry.content;
                        return Object.assign({}, entry)
                    })
                }
                // return this.orgEntries;
            },

            itemsMaterial() {
                // console.log("itemsMaterial", this.materialEntries.map);
                console.log("itemsMaterial", this.materialEntries);

                if (this.materialEntries) {
                    // return this.materialEntries;

                    return this.materialEntries.map(entry => {
                        let t = '';
                        //     if (entry.title != null) {
                        //         if (entry.title.length != 0) {
                        //             t = entry.title;
                        //             if (entry.titleRus != null) {
                        //                 if (entry.titleRus.length != 0)
                        //                     t += " / " + entry.titleRus;
                        //             }
                        //         }
                        //     } else {
                        t = entry.content;//titleRus;  ///one of the two titles is mandatory
                        //     }
                        //
                        const material = t;
                        return Object.assign({}, entry, {material})
                    })
                }
            },

            searchHashtagLength() {
                return this.searchHashtag.length
            },

            searchMTypeLength() {
                return this.searchMType.length
            },

            filteredHashtags() {
                // console.log("COMPUTED FLAT", this.hashtagFlatTree);
                let resultSearchTree = this.searchTree(this.hashtagFlatTree, this.searchHashtag);
                // console.log("COMPUTED AFTER SEARCH", resultSearchTree);
                return this.flatTreeToTree(resultSearchTree);
            },

            filteredMTypes() {
                let resultSearchTree = this.searchTree(this.mtypeFlatTree, this.searchMType);
                return this.flatTreeToTree(resultSearchTree);
                // console.log("COMPUTED", resultSearchTree);
            },

            // filteredHashtags1() {
            //     let tree = [];
            //     let resultIds = [];
            //     let touched = false;
            //     let resultSearchTree = [];
            //
            //     resultSearchTree = JSON.parse(JSON.stringify(this.hashtagFlatTree)); //the way for deep copy
            //     do {
            //         // remove leaves which do not match search string
            //         let toRemove = [];
            //         touched = false;
            //
            //         resultSearchTree.forEach((node, id) => {
            //             const index = node.name.toLowerCase().indexOf(this.searchHashtag) >= 0;
            //             if ((index === false) && (node.children.length === 0)) {
            //                 console.log("node.children.length (to remove)", node.children.length, node.name);
            //                 toRemove.push(node.id);
            //                 touched = true;
            //             }
            //         });
            //
            //         for (let i = 0; i < toRemove.length; i++) {
            //             let index = resultSearchTree.findIndex(x => x.id === toRemove[i]);
            //             if (index >= 0) {
            //                 resultSearchTree.splice(index, 1);
            //             }
            //         }
            //
            //         // remove references to deleted leaves
            //         let treeSize = resultSearchTree.length;
            //         for (let i = 0; i < treeSize; i++) {
            //             let newChildren = [];
            //             let currentItem = resultSearchTree[i];
            //
            //             for (let j = 0; j < currentItem.children.length; j++) {
            //                 let childWasFound = false;
            //
            //                 for (let k = 0; k < treeSize; k++) {
            //                     if ((resultSearchTree[k].id === currentItem.children[j].id)) {
            //                         newChildren.push(currentItem.children[j]);
            //                         childWasFound = true;
            //                     }
            //                 }//for k
            //
            //                 if (!childWasFound) {
            //                     touched = true;
            //                     //   console.log("deleted ref", currentItem.children[j].id);
            //                 }
            //             }//for j
            //             resultSearchTree[i].children = newChildren;
            //         }
            //     } while (touched);
            //
            //     // resultSearchTree.forEach((item, id) => {
            //     //     console.log("FLAT SEARCH TREE", item.name, item.id);
            //     // });
            //
            //     //create normal tree from flat tree
            //     let movedChildren = [];
            //     do {
            //         movedChildren = [];
            //
            //         let treeSize = resultSearchTree.length;
            //         for (let i = 0; i < treeSize; i++) {
            //
            //             let currentItem = resultSearchTree[i];
            //             let removedChildrenAlreadyContains = false;
            //             let childrenQ = currentItem.children.length;
            //
            //             for (let j = 0; j < movedChildren.length; j++) {
            //                 if (movedChildren[j] === currentItem.id)
            //                     removedChildrenAlreadyContains = true;
            //             }
            //
            //             if (!removedChildrenAlreadyContains) {
            //                 for (let j = 0; j < childrenQ; j++) {
            //                     let child = currentItem.children[j];
            //
            //                     if ((child.name == null)) {
            //                         let index = resultSearchTree.findIndex(x => x.id === child.id);
            //                         if (index >= 0) {
            //                             //if (resultSearchTree[index].children.length === 0) {
            //                             let isChildrenNull = false;
            //
            //                             if (resultSearchTree[index].children !== null) {
            //                                 for (let k = 0; k < resultSearchTree[index].children.length; k++) {
            //                                     if (resultSearchTree[index].children[k].name === null) {
            //                                         isChildrenNull = true;
            //                                     }
            //                                 }//for k
            //
            //                                 if (!isChildrenNull) {
            //                                     child.name = resultSearchTree[index].name;
            //                                     child.children = resultSearchTree[index].children;
            //                                     movedChildren.push(child.id);
            //                                 }
            //                             }//if child.children
            //                         }
            //                     }
            //                 }//for j
            //             }//if contains
            //         }
            //
            //         for (let j = 0; j < movedChildren.length; j++) {
            //             let index = resultSearchTree.findIndex(x => x.id === movedChildren[j]);
            //             // console.log("I-N-D-E-X-======", index, movedChildren[j]);
            //             if (index >= 0) {
            //                 resultSearchTree.splice(index, 1);
            //             }
            //         }
            //         //console.log("-----------------------------------------------");
            //     } while (movedChildren.length > 0);
            //     return resultSearchTree;
            // },

            filteredOpenHashtags() {
                if (this.searchHashtag != null)   //for start view without search
                    if (this.searchHashtagLength === 0) {
                        return this.filteredHashtags.map((top) => {
                            return top.name
                        })
                    } else {
                        return this.filteredHashtags;
                    }
            },

            filteredOpenMTypes() {
                // console.log("filteredOpenMTypes", this.filteredMTypes);

                if (this.searchMType != null)   //for start view without search
                    if (this.searchMTypeLength === 0) {
                        return this.filteredMTypes.map((top) => {
                            return top.name
                        })
                    } else {
                        return this.filteredMTypes;
                    }
            }
        },


        ///////////////////////////////////////////WATCH////////////////////////////////////////////////////
        watch: {
            // checkedMovements() {
            //     console.log("CHECKED", this.checkedMovements);
            // },

            disableColorCheckBoxFlag() {
                //  console.log("enter the watcher dis flag");
                if (this.disableColorCheckBoxFlag === true) {
                    //  console.log("enter ENTER the watcher dis flag");
                    this.article.rowColor = null;
                }
            },

            searchHashtag() {
                this.$nextTick(() => {
                    if (this.searchHashtagLength === 0) {
                        this.$refs.treeviewref.updateAll(false);
                    } else {
                        this.$refs.treeviewref.updateAll(true);
                    }
                });
            },

            searchMType() {
                this.$nextTick(() => {
                    if (this.searchMTypeLength === 0) {
                        this.$refs.mtypetreeviewref.updateAll(false);
                    } else {
                        this.$refs.mtypetreeviewref.updateAll(true);
                    }
                });
            },

            authorSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 2) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selected !== 'undefined') {
                            // console.log("SELECTED IN WATCH", this.selected);
                            // console.log(this.selected);
                            if (this.article.personList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selected = "";
                        }

                        // Items have already been loaded
                        //  if (this.items.length > 0) return;

                        // Items have already been requested
                        if (this.isLoading) return;
                        this.isLoading = true;

                        apiPerson.searchPerson(val, r => {
                            this.personEntries = r;
                            //  console.log("****", this.entries);
                            this.isLoading = false;
                        });
                    }
            },

            locationSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 1) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedLocation !== 'undefined') {
                            // console.log("SELECTED IN WATCH");
                            // console.log(this.selectedLocation);
                            if (this.article.locationList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
                            if (this.article.orgList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedOrg = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingOrg) return;
                        this.isLoadingOrg = true;

                        //console.log("seracg org", val);

                        apiOrg.searchOrg(val, r => {
                            this.orgEntries = r;  //returns OrgDto (id, name(connected from different Org fields in OrgServImpl))
                            //console.log("**орг**", this.orgEntries);
                            this.isLoadingOrg = false;
                        });
                    }
            },

            materialSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 2) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedMaterial !== 'undefined') {
                            if (this.article.materialList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedMaterial = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingMaterial) return;
                        this.isLoadingMaterial = true;

                        api.searchMaterial(val, r => {
                            this.materialEntries = r;
                            //  console.log("*№*№*№*", this.materialEntries);
                            this.isLoadingMaterial = false;
                        });
                    }
            },
        },
    }
</script>
