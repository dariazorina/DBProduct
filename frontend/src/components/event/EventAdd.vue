<template id="event-add">
    <v-app id="inspire">
        <div>
            <div class="unprotected" v-if="errorFlag">

                <div class="col-sm-3">
                    <div class="cellTitle"><span class="float-center"><div v-for="err in errors"><p style="color:red">DB message: {{err}}</p></div></span>
                    </div>
                </div>

            </div>

            <form class="formCreation"
                  style="background-color: transparent; margin-left: 15px; margin-bottom: 0; padding-bottom: 0">
                <div class="col-md-4">
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
            </form>

            <form class="authorsFormCreation"
                  style="background-color: transparent; padding: 5px 0 5px">

                <div class="form-row col-12" style="background-color: transparent">
                    <div class="col-md-6" style="background-color: transparent">
                        <div class="form-row">
                            <label for="add-name">Заголовок*</label>
                            <input class="form-control" id="add-name" v-model="event.title"
                                   v-on:input="onTitleInput"
                                   placeholder="Поле должно быть заполнено"/>
                        </div>

                        <div style="background-color: transparent"> <span class="notbold">
                            <ul id="alreadyExistedProhect">
                                <li v-for="item in alreadyExistedEvent" :key="item.content">
                                    {{ item.content }}
                                </li>
                            </ul>
                            </span>
                        </div>
                    </div>

                    <div class="col-md-4" style="background-color: transparent; margin: 0px 10px 0px 10px">
                        <div class="form-row">
                            <label for="add-name">Тип события*</label>
                            <input class="form-control" id="add-type" v-model="event.type"
                                   placeholder="Поле должно быть заполнено"/>
                        </div>
                    </div>
                </div>

                <!--                <div class="row">-->
                <div class="form-row col-12">
                    <div class="col-md-3" style="background-color: transparent; margin: 0px 10px 0px 0px">
                        <div class="form-row">
                            <!--                            <div class="col-md-6" style="background-color: transparent">-->
                            <label for="date-input">Дата начала*</label>
                            <input class="form-control" type="date" id="date-input" :disabled="uploadMode"
                                   v-model="event.startDate">
                            <!--                            </div>-->
                        </div>
                    </div>

                    <div class="col-md-3" style="background-color: transparent; margin: 0px 10px 0px 10px">
                        <div class="form-row">
                            <!--                            <div class="col-md-6" style="background-color: transparent">-->
                            <label for="date-input">Дата завершения</label>
                            <input class="form-control" type="date" :disabled="uploadMode"
                                   v-model="event.endDate">
                            <!--                            </div>-->
                        </div>
                    </div>
                </div>

                <div class="form-row col-12" style="background-color: transparent">
                    <label>Теги события :</label>
                    <multiselect v-model="event.tagList" :options="allTags" label="content" track-by="content"
                                 :multiple="true" :searchable="false" :close-on-select="false"
                                 :show-labels="true" placeholder="Pick a value"></multiselect>
                </div>


                <!--                <div>-->
                <!--                    <label class="typo__label">Simple select / dropdown</label>-->
                <!--                    <multiselect v-model="value" :options="all" :multiple="true" :close-on-select="false" :clear-on-select="false" :preserve-search="true" placeholder="Pick some" label="name" track-by="name" :preselect-first="true">-->
                <!--                        <template slot="selection" slot-scope="{ values, search, isOpen }"><span class="multiselect__single" v-if="values.length &amp;&amp; !isOpen">{{ values.length }} options selected</span></template>-->
                <!--                    </multiselect>-->
                <!--                    <pre class="language-json"><code>{{ value  }}</code></pre>-->
                <!--                </div>-->

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

                                    @change="addSearchedEntity(selectedLocation, event.locationList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти локацию"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="event.locationList.length > 0" class="col-9"
                         style="background-color: transparent; padding:0">
                        <connection-component :itemsList="event.locationList"
                                              :isLinkMode="false"
                                              :isSelectionMode="false"
                                              :allTypes="connectionTypes"
                                              style="background-color: transparent; padding:0px" class="col-12"/>
                    </div>
                </div>

                <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                    <div class="col-3"
                         style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                        <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                            <label style="font-size: medium; font-weight: bold">Связанные персоны</label>
                            <v-autocomplete
                                    id="person-autocomplete"
                                    :items="personItems"
                                    :loading="isLoadingPerson"
                                    :search-input.sync="personSearch"
                                    color="pink"
                                    hide-no-data
                                    hide-selected

                                    v-model="selectedPerson"

                                    @change="addSearchedEntity(selectedPerson, event.personList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти персону"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="event.personList.length > 0" class="col-9"
                         style="background-color: transparent; padding:0">
                        <ConnectionComponent :itemsList="event.personList"
                                             :isLinkMode="false"
                                             :isSelectionMode="false"
                                             :allTypes="connectionTypes"
                                             style="background-color: transparent; padding:0px" class="col-12"/>
                    </div>
                </div>

                <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                    <div class="col-3"
                         style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                        <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                            <label style="font-size: medium; font-weight: bold">Связанные организации</label>
                            <v-autocomplete
                                    id="org-autocomplete"
                                    :items="orgItems"
                                    :loading="isLoadingOrg"
                                    :search-input.sync="orgSearch"
                                    color="green"
                                    hide-no-data
                                    hide-selected

                                    v-model="selectedOrg"

                                    @change="addSearchedEntity(selectedOrg, event.orgList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти организацию"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="event.orgList.length > 0" class="col-9"
                         style="background-color: transparent; padding:0; margin: 0px">
                        <ConnectionComponent :itemsList="event.orgList"
                                             :isLinkMode="false"
                                             :isSelectionMode="false"
                                             :allTypes="connectionTypes"
                                             style="background-color: transparent; padding:0px" class="col-12"/>
                    </div>
                </div>

                <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                    <div class="col-3"
                         style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                        <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                            <label style="font-size: medium; font-weight: bold">Связанные проекты</label>
                            <v-autocomplete
                                    id="project-autocomplete"
                                    :items="projectItems"
                                    :loading="isLoadingProject"
                                    :search-input.sync="projectSearch"
                                    color="brown"
                                    hide-no-data
                                    hide-selected

                                    v-model="selectedProject"

                                    @change="addSearchedEntity(selectedProject, event.projectList, true)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти проект"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="event.projectList.length > 0" class="col-9"
                         style="background-color: transparent; padding:0; margin: 0px">

                        <ConnectionComponent :itemsList="event.projectList"
                                             :isLinkMode="false"
                                             :isSelectionMode="false"
                                             :allTypes="connectionTypes"
                                             style="background-color: transparent; padding:0px" class="col-12"/>

                    </div>
                </div>

                <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                    <div class="col-3"
                         style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                        <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                            <label style="font-size: medium; font-weight: bold">Связанные материалы</label>
                            <v-autocomplete
                                    id="article-autocomplete"
                                    :items="articleItems"
                                    :loading="isLoadingArticle"
                                    :search-input.sync="articleSearch"
                                    color="purple"
                                    hide-no-data
                                    hide-selected

                                    v-model="selectedArticle"

                                    @change="addSearchedEntity(selectedArticle, event.articleList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы материал"
                                    prepend-icon="mdi-database-search"
                                    return-object
                                    :disabled="uploadMode"
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="event.articleList.length > 0" class="col-9"
                         style="background-color: transparent; padding:0">
                        <ConnectionComponent :itemsList="event.articleList"
                                             :isLinkMode="true"
                                             :isSelectionMode="false"
                                             :allTypes="connectionTypes"
                                             style="background-color: transparent; padding:0" class="col-12"/>
                    </div>
                </div>

                <div class="form-row col-12" style="padding: 0; margin: 0; background-color: transparent">
                    <div class="col-3"
                         style="background-color: transparent; padding-right: 0; padding-left: 0; margin: 0">
                        <v-card-text style="background-color: transparent; padding: 10px 10px 10px 0">
                            <label style="font-size: medium; font-weight: bold">Связанные события</label>
                            <v-autocomplete
                                    style="background-color: transparent"
                                    id="event-autocomplete"
                                    :items="eventItems"
                                    :loading="isLoadingEvent"
                                    :search-input.sync="eventSearch"
                                    color="blue"
                                    hide-no-data
                                    hide-selected

                                    v-model="selectedEvent"

                                    @change="addSearchedEntity(selectedEvent, event.eventList, true)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти событие"
                                    prepend-icon="mdi-database-search"
                                    return-object
                                    :disabled="uploadMode"
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="event.eventList.length > 0" class="col-9" style="background-color: transparent; padding:0">
                        <OneTypeConnComp :itemsList="event.eventList"
                                         :isLinkMode="false"
                                         :isInternalMode="false"
                                         :isSelectionMode="false"
                                         :allTypes="connectionTypes"
                                         style="background-color: transparent; padding:0px" class="col-12"/>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6" style="background-color: transparent">
                        <label>Описание</label>
                        <ckeditor :editor="editor" v-model="event.description" :config="editorConfig"></ckeditor>
                    </div>
                    <!--                    <div class="col-md-3" style="margin-top: 0px; background-color: transparent">-->
                    <!--                        <div id="preview" style="margin-top: 31px" class="form-row">-->
                    <!--                            <div v-if="avatar.imageBase64">-->
                    <!--                                <img :src="avatar.imageBase64" @load="setHeight"-->
                    <!--                                     :style="{ height: imageHeight + 'px' }"/>-->
                    <!--                            </div>-->

                    <!--                            <div v-if="avatar.imageBase64" class="col-md-1"-->
                    <!--                                 style="background-color: transparent; padding-top: 0px; padding-left: 0px">-->
                    <!--                                <span class="close" @click="deletePhoto">&times;</span>-->
                    <!--                            </div>-->

                    <!--                            &lt;!&ndash;                            <div v-else>&ndash;&gt;-->
                    <!--                            &lt;!&ndash;                                <img v-if="person.photo" v-bind:src="'data:image/jpeg;base64,'+person.photo"&ndash;&gt;-->
                    <!--                            &lt;!&ndash;                                     :style="{ width: 250+'px' }"/>&ndash;&gt;-->
                    <!--                            &lt;!&ndash;                            </div>&ndash;&gt;-->
                    <!--                        </div>-->
                    <!--                        &lt;!&ndash;                        <div v-if="avatar.imageBase64" style="margin-top: 5px">&ndash;&gt;-->
                    <!--                        &lt;!&ndash;                            <input type="file" accept="image/*" @change="onChange"/>&ndash;&gt;-->
                    <!--                        &lt;!&ndash;                        </div>&ndash;&gt;-->
                    <!--                        <div style="margin-top: 30px">-->
                    <!--                            <input type="file" accept="image/*" @change="onChange"/>-->
                    <!--                        </div>-->
                    <!--                    </div>-->
                </div>
            </form>
            <form class="formCreation">
                <div class="form-row">
                    <div class="col-md-7">
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
                                                    :items="filteredHashtags"
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
                                </v-row>

                            </b-card>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6">
                        <label for="add-link">Хештеги</label>
                        <div>
                            <input-tag id="add-hashtag" :add-tag-on-keys="hashAddTagOnKeys"
                                       v-model="hashtags"></input-tag>

                        </div>
                    </div>
                </div>


                <div class="form-row">
                    <div class="col-md-6">
                        <label for="add-link">Link</label>
                        <div>
                            <input-tag id="add-link" :add-tag-on-keys="linkAddTagOnKeys"
                                       placeholder="enter links with 'return' or 'tab'" v-model="links"></input-tag>
                        </div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-6">
                        <label for="add-misc">Комментарии</label>
                        <div class="green-border-focus">

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="event.miscellany"
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
                        <v-swatches style="margin-top: 5px" v-model="event.rowColor"
                                    :disabled="disableColorCheckBoxFlag"
                                    popover-x="left"></v-swatches>
                    </div>
                </div>

                <div v-if="uploadMode||editMode" style="background-color: transparent">
                    <file-attachment @attachFiles="createAttachment"
                                     @getAttachment="getAttachment"
                                     @downloadAttachment="downloadAttachment"
                                     @removeAttachment="removeAttachment"
                                     :userName="loggedName"
                                     :already-uploaded-files="uploadedFiles"
                                     :is-details-mode="false"/>
                </div>
                <div v-if="editMode" class="form-row align-items-center">
                    <div class="offset-sm-4 col-sm-3">

                        <button type="button" @click="preliminaryDataCheck(0)" class="btn btn-primary">Обновить</button>
                        <a class="btn btn-default">
                            <router-link to="/event">Отмена</router-link>
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

                        <button type="button" style="margin-right: 20px" @click="preliminaryDataCheck(statusList[0])"
                                class="btn btn-warning">В работе
                        </button>
                        <button type="button" style="margin-right: 20px" @click="preliminaryDataCheck(statusList[1])"
                                class="btn btn-success">Внесены
                        </button>

                        <button type="button" class="btn btn-info">
                            <router-link to="/event" style="color: white">Отмена</router-link>
                        </button>
                    </div>

                    <div v-else class="offset-sm-4 col-sm-3">
                        <button type="button" style="margin-right: 20px" @click="uploadFiles"
                                class="btn btn-info">Upload Files
                        </button>
                        <button type="button" class="btn btn-info">
                            <router-link to="/event" style="color: white">Cancel</router-link>

                        </button>
                    </div>
                </div>
            </form>
        </div>
    </v-app>
</template>

<script>

    import api from "./event-api";
    import apiMovement from "./../movement/movement-api";
    import router from "./../../router";
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';
    import apiCountry from "./../country/country-api";
    import apiOrg from "./../org/org-api";
    import apiProject from "./../project/project-api";
    import apiStatus from "./../status-api";
    import apiHashtag from "./../hashtag/hashtag-api";
    import apiPerson from "./../person/person-api";
    import apiArticle from "./../article/article-api";

    import ConnectionComponent from "../components/connection/ConnectionComponent";
    import CKEditor from 'ckeditor4-vue';
    import FileAttachment from "../components/FileAttachment";
    import apiAttachment from "./../attachment-api";
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import Multiselect from 'vue-multiselect'

    import apiLogin from "../login-api";
    import OneTypeConnComp from "../components/one-type-connection/OneTypeConnComp";
    import moment from "moment";

    export default {
        name: 'event-add',
        components: {
            OneTypeConnComp,
            ConnectionComponent,
            ckeditor: CKEditor.component,
            FileAttachment,
            VSwatches,
            Multiselect,
        },
        vuetify: new Vuetify(),

        data: () => ({
            editor: CKEditor, // to use the component locally
            editorConfig: {
                // The configuration of the editor.
            },
            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            tagsValue: [],
            allTags: [],
            event: {
                movementList: [],
                locationList: [],
                articleList: [],
                eventList: [],
                orgList: [],
                personList: [],
                projectList: [],
                linkList: [],
                hashtagList: [],
                tagList: []
            },

            alreadyExistedEvent: [],

            allMovements: [],
            isAdmin: null,
            checkedMovements: [],

            // startDate: null,
            // endDate: null,
            statusList: [],

            connectionTypes: [],

            isLoadingLocation: false,
            selectedLocation: [],
            locationEntries: [],
            locationSearch: null,

            linkAddTagOnKeys: [13, 9],
            hashAddTagOnKeys: [],
            descriptionLimit: 60,

            isLoadingProject: false,
            selectedProject: [],
            projectEntries: [],
            projectSearch: null,

            isLoadingOrg: false,
            selectedOrg: [],
            orgEntries: [],
            orgSearch: null,

            isLoadingPerson: false,
            selectedPerson: [],
            personEntries: [],
            personSearch: null,

            isLoadingEvent: false,
            selectedEvent: [],
            eventEntries: [],
            eventSearch: null,

            isLoadingArticle: false,
            selectedArticle: [],
            articleEntries: [],
            articleSearch: null,

            searchHashtag: '',
            selectedS: null,
            selectedHashtag: [],
            hashtagTree: [],
            hashtagFlatTree: [],
            projectTypeFlatTree: [],
            tags: [],
            hashtags: [],
            links: [],
            editMode: false,
            uploadMode: false,
            uploadFilesCheckBoxValue: false,
            imageHeight: null,
            attachedFiles: [],
            uploadedFiles: [],

            searchProjectType: '',
            nameList: [],
            disableColorCheckBoxFlag: false
        }),

        mounted() {
            this.getLoggedIn();

            if (this.$route.params.event_id != null) {
                console.log("EDIT MODE");
                this.editMode = true;
                this.uploadFilesCheckBoxValue = true;
            }

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

            api.getAllTags(response => {
                this.allTags = response.data;
            });

            apiHashtag.getAllHashtags(response => {
                this.hashtagFlatTree = this.createFlatTree(response.data);
                // console.log(response.data)
            });

            apiStatus.getAllStatuses(response => {
                this.statusList = response.data;
                // console.log("STATUS LIST", this.statusList);
            });

            // apiArticle.getConnectionTypes(response => {
            //     this.connectionTypes = response.data;
            //     console.log("connectionTypes---------------", response.data)
            // });

            // this.initYears();

            if (this.editMode) {////////////////////////////////EDIT MODE//////////////////////////////////////
                api.findById(this.$route.params.event_id, r => {
                    this.event = r.data;
                    console.log("current event finded by id", this.event);

                    this.event.startDate = this.formatDate(this.event.startDate);
                    this.event.endDate = this.formatDate(this.event.endDate);

                    this.selectedS = this.event.status;

                    for (let i = 0; i < this.event.hashtagList.length; i++) {
                        this.hashtags.push(this.event.hashtagList[i]);
                    }

                    for (let i = 0; i < this.event.linkList.length; i++) {
                        this.links.push(this.event.linkList[i].content);
                    }

                    for (let i = 0; i < this.event.movementList.length; i++) {
                        this.checkedMovements.push(this.event.movementList[i].id);
                    }

                    apiAttachment.getAttachments('event', this.event.id, r => {
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });

                    api.getEventsByIdsAndSymmetrically(this.event.id, response => {
                        this.event.eventList = response.data;
                        console.log("api", this.event.eventList);
                    });

                    // apiAttachment.getAttachmentPhoto('event', this.event.id, r => {
                    //     console.log("R DATA", r);
                    //     this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;//.content;
                    //
                    //     let blob = new Blob([r.data], {type: 'image/jpeg'});
                    //     console.log("BLOB", blob);
                    // });
                });
            }
        },

        methods: {
            formatDate(date) {
                if (date !== null)
                    return moment(date).format('YYYY-MM-DD');
            },

            typeConnectionFunction(isParent) {
                console.log("typeConnectionFunction", isParent);
            },

            onTitleInput() {
                if (this.event.title.length > 3) {
                    console.log("IN ONPUT");

                    api.searchEvent(this.event.title, r => {
                        this.alreadyExistedEvent = r;
                        console.log("*№*№*№*", this.alreadyExistedEvent);
                    });
                } else {
                    if (this.alreadyExistedEvent.length > 0)
                        this.alreadyExistedEvent.splice(0);
                }
            },

            deletePhoto() {
                this.avatar.image = null;
                this.avatar.imageBase64 = null;
                this.avatar.imageUrl = null;

                this.photoWasUploaded = true;
                this.originalPhoto = null;
            },

            removeAttachment(file) {
                apiAttachment.removeAttachment('event', this.event.id, file.id, file.name, r => {
                    console.log("result", r.data);
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

            getLoggedIn() {
                this.loggedName = this.$store.getters.getUserName;  //todo change to author, not current user
                this.isAdmin = localStorage.getItem('isAdmin');
                // console.log("this.checkedMovements", this.checkedMovements);
            },

            // removeSelectedOrgType() {  //todo remove tag?
            //     this.selectedOrgType = '';
            // },

            onHashtagSelect(item) {
                const index = this.hashtags.indexOf(item.name);
                if (index === -1) {
                    this.hashtags.push(item.name);
                }
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


            // setHeight(event) {
            //     let image = event.target;
            //     if (image.clientWidth > image.clientHeight) {
            //         let pr = image.clientWidth / image.clientHeight;
            //         this.imageHeight = 350 / pr;
            //     } else {
            //         this.imageHeight = 350;
            //     }
            // },

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
                this.createEvent(currentStatus);
            },

            // initYears() {
            //     this.years.push("null");
            //     for (let y = 1800; y < 2051; y++) {
            //         this.years.push(y);
            //     }
            // },

            // addAdditionalMovement() {
            //     this.addAdditionalMovementFlag = true;
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

            validDate: function (code) {
                let re = /([12][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))$/; ///digit format "inside", see it while debugging
                // var re = /((0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[0-2])[.][12][0-9]{3})$/;
                return re.test(code);
            },

            formValidate() {
                console.log("chM", this.checkedMovements, this.event.title);

                this.addStatus('add-name', (!this.event.title));

                if (this.event.title !== null) {
                    if (this.event.title.length !== 0) {
                        this.addStatus('add-name', (!this.event.title));

                        if (this.checkedMovements.length === 0) {
                            this.hasError = true;

                        } else {
                            this.addStatus('add-type', (!this.event.type));

                            if (this.event.type !== null) {
                                if (this.event.type.length !== 0) {
                                    this.addStatus('add-type', (!this.event.type));

                                    this.addStatus('date-input', (!(this.validDate(this.event.startDate))));

                                } else {
                                    this.hasError = true;
                                }
                            } else
                                this.hasError = true;
                        }
                    } else this.hasError = true;
                } else this.hasError = true;

                if (this.hasError) {
                    alert("Пожалуйста, заполните все обязательные поля");
                    console.log('ERROROROR----------------------------');
                }
                return !this.hasError;
            },

            // onChange(e) {
            //     //todo need to set size limit
            //
            //     const file = e.target.files[0];
            //     this.avatar.image = file;
            //     this.avatar.imageUrl = URL.createObjectURL(file);
            //
            //     const reader = new FileReader();
            //     reader.readAsDataURL(file);  // reader.readAsBinaryString(file);
            //     reader.onload = e => {
            //         this.avatar.imageBase64 = e.target.result;
            //         console.log(this.avatar);
            //
            //         console.log("photoWasUploaded", this.photoWasUploaded);
            //         console.log("originalPhoto", this.originalPhoto);
            //         console.log("this.avatar.imageBase64", this.avatar.imageBase64);
            //
            //         if (this.originalPhoto !== this.avatar.imageBase64) {
            //             this.photoWasUploaded = true;
            //             console.log("-photoWasUploaded", this.photoWasUploaded);
            //         }
            //     };
            // },

            createEvent(currentStatus) {
                this.hasError = false;

                if (this.formValidate()) {

                    if (currentStatus.name === this.statusList[0].name) {
                        this.event.status = this.statusList[0].name;
                    } else {
                        this.event.status = this.statusList[1].name;
                    }

                    if (this.selectedFYear) {
                        this.event.foundationYear = this.selectedFYear;
                    }
                    if (this.selectedCYear) {
                        this.event.closureYear = this.selectedCYear;
                    }

                    this.event.linkList.splice(0);
                    this.event.hashtagList.splice(0);
                    this.event.movementList.splice(0);

                    let i = 0;
                    for (; i < this.checkedMovements.length; i++) {
                        this.event.movementList[i] = {
                            "id": this.checkedMovements[i]
                        };
                    }

                    for (let i = 0; i < this.links.length; i++) {
                        this.event.linkList[i] = {
                            "content": this.links[i]
                        };
                        // console.log("CREATE event link: ", this.links[i]);
                    }

                    for (let i = 0; i < this.hashtags.length; i++) {
                        this.event.hashtagList[i] = this.hashtags[i];
                    }

                    // console.log("-------------ORG BEFORE  CREATION", this.event, this.projectList);

                    if (this.editMode) {
                        this.event.status = this.selectedS;
                        console.log("event BEFORE UPDATING edit mode", this.event);
                        if (this.formValidate()) {
                            api.update(this.event.id, this.event, r => {
                                console.log(r);
                                // if (this.photoWasUploaded) {
                                //     apiAttachment.deletePhoto('event', r.data.id, r => {
                                //     });
                                //     if (this.avatar.image !== null) {
                                //         apiAttachment.uploadPhoto('project', r.data.id, this.avatar.image, r => {
                                //             console.log("ph was uplded");
                                //         });
                                //     }
                                // }
                                for (let i = 0; i < this.attachedFiles.length; i++) {
                                    apiAttachment.uploadFile('event', this.event.id, this.attachedFiles[i], r => {
                                    });
                                    //todo progress bar?
                                }
                                router.push('/event');
                            });
                        }
                    } else {
                        console.log("event BEFORE SAVING", this.event);
                        if (this.formValidate()) {
                            api.create(this.event, r => {
                                console.log(r);

                                // if (this.avatar.image !== null) {
                                //     apiAttachment.uploadPhoto('project', r.data.id, this.avatar.image, r => {
                                //     });
                                // }

                                if (!this.uploadFilesCheckBoxValue) {
                                    router.push('/event');
                                } else {
                                    this.uploadMode = true;
                                    this.uploadFilesCheckBoxValue = false;
                                    let ID = r.data.id;
                                    this.event.id = ID;
                                    console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>stay here, event ID", r.data, ID);
                                }
                            });
                        }
                    }
                }
            },

            uploadFiles() {             //on button press
                for (let i = 0; i < this.attachedFiles.length; i++) {
                    apiAttachment.uploadFile('event', this.event.id, this.attachedFiles[i], r => {
                    });
                    // this.submitFile(this.attachedFiles[i]);
                    //todo progress bar?
                }
                router.push('/event');
            },

            createAttachment(files) {     //emit from FilesAttachment Component 'attachFiles'
                console.log("files from COMPONENT", files);
                this.attachedFiles = [];
                for (let i = 0; i < files.length; i++) {
                    this.attachedFiles.push(files[i]);
                }
            },

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/event/downloadAttachment?entityId=' + this.event.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                apiAttachment.previewAttachment('event', this.event.id, file.id);
            },

            addSearchedEntity(obj, list, isOneType) {
                console.log("GET CHANGED EVENT", obj, list);
                let i = 0;
                for (i = 0; i < list.length; i++) { //to exclude double values
                    if (list[i].itemId === obj.id) {
                        break;
                    }
                }

                let connection;
                if (i === list.length) {
                    if (isOneType === true) {
                        connection = {
                            "itemId": obj.id,
                            "name": obj.content,
                            "comment": '',
                            "connection": '',
                            "isParent": false,
                        };
                    } else {
                        connection = {
                            "itemId": obj.id,
                            "name": obj.content,
                            "comment": '',
                            "connection": '',
                        };
                    }
                    list.push(connection);
                    console.log("ADDED");
                }
            },

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
                        // console.log("node.children.length (to remove)", node, node.name);
                        const index = node.name.toLowerCase().indexOf(searchEntity.toLowerCase()) >= 0;
                        if ((index === false) && (node.children.length === 0)) {
                            // console.log("node.children.length (to remove)", node.children.length, node.name);
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
        computed: {
            locationItems() {
                if (this.locationEntries) {      ///todo analyze why undefined (after selection in the search list)
                    return this.locationEntries.map(entry => {
                        return Object.assign({}, entry)
                    })
                }
            },

            personItems() {
                if (this.personEntries) {      ///todo analyze why undefined (after selection in the search list)
                    console.log("person items in", this.personEntries);

                    return this.personEntries.map(entry => {
                        // const org = entry.name;
                        //  return Object.assign({}, entry, {org})
                        return Object.assign({}, entry)
                    })
                }
            },

            projectItems() {
                if (this.projectEntries) {      ///todo analyze why undefined (after selection in the search list)
                    console.log("pro items in", this.projectEntries);

                    return this.projectEntries.map(entry => {
                        return Object.assign({}, entry)
                    })
                }
            },

            orgItems() {
                if (this.orgEntries) {
                    console.log("org items in", this.orgEntries);
                    return this.orgEntries.map(entry => {
                        return Object.assign({}, entry)
                    })
                }
            },

            eventItems() {
                if (this.eventEntries) {
                    console.log("event items in", this.eventEntries);
                    return this.eventEntries.map(entry => {
                        return Object.assign({}, entry)
                    })
                }
            },

            articleItems() {
                if (this.articleEntries) {
                    console.log("article items in", this.articleEntries);
                    return this.articleEntries.map(entry => {
                        return Object.assign({}, entry)
                    })
                }
            },

            filteredHashtags() {
                // console.log("COMPUTED FLAT", this.hashtagFlatTree);
                let resultSearchTree = this.searchTree(this.hashtagFlatTree, this.searchHashtag);
                // console.log("COMPUTED AFTER SEARCH", resultSearchTree);
                return this.flatTreeToTree(resultSearchTree);
            },

            searchHashtagLength() {
                return this.searchHashtag.length
            },

            filteredKeys() {
                if (this.searchHashtag != null)   //for start view without search
                    if (this.searchLength === 0) {
                        return this.filteredHashtags.map((top) => {
                            return top.name
                        })
                    } else {
                        return this.filteredHashtags;
                    }
            },
        },

        watch: {
            // checkedMovements() {
            //     console.log("watch this.checkedMovements.length", this.checkedMovements);
            // },

            disableColorCheckBoxFlag() {
                if (this.disableColorCheckBoxFlag === true) {
                    this.event.rowColor = null;
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
                            if (this.event.locationList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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

            personSearch(val) {
                console.log("SEARCH ACTIVATED", val);
                if (val !== null)
                    if (val.length > 2) {
                        console.log("SEARCH STARTED");

                        if (typeof this.selectedPerson !== 'undefined') {
                            if (this.event.personList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedPerson = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingPerson) return;
                        this.isLoadingPerson = true;


                        apiPerson.searchPerson(val, r => {
                            this.personEntries = r;
                            console.log("-------------*-*-*-*----------------", this.personEntries);
                            this.isLoadingPerson = false;
                        });
                    }
            },

            orgSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedOrg !== 'undefined') {
                            if (this.event.orgList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedOrg = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingOrg) return;
                        this.isLoadingOrg = true;

                        //console.log("seracg org", val);

                        apiOrg.searchOrg(val, r => {
                            this.orgEntries = r;  //returns OrgDto (id, name(connected from different Org fields in OrgServImpl))
                            console.log("****", this.orgEntries);
                            this.isLoadingOrg = false;
                        });
                    }
            },

            eventSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedEvent !== 'undefined') {
                            if (this.event.eventList.length > 1)
                                this.selectedEvent = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingEvent) return;
                        this.isLoadingEvent = true;

                        //console.log("seracg org", val);

                        api.searchEvent(val, r => {
                            this.eventEntries = r;  //returns OrgDto (id, name(connected from different Org fields in OrgServImpl))
                            console.log("*#*#*#*", this.eventEntries);
                            this.isLoadingEvent = false;
                        });
                    }
            },

            projectSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedProject !== 'undefined') {
                            if (this.event.projectList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedProject = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingProject) return;
                        this.isLoadingProject = true;

                        console.log("search pro", val);

                        apiProject.searchProject(val, r => {
                            this.projectEntries = r;  //returns projectDto (id, name(connected from different Project fields in projectServImpl))
                            console.log("****", this.projectEntries);
                            this.isLoadingProject = false;
                        });
                    }
            },

            articleSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 2) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedArticle !== 'undefined') {
                            if (this.event.articleList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedArticle = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingArticle) return;
                        this.isLoadingArticle = true;

                        apiArticle.searchMaterial(val, r => {
                            this.articleEntries = r;
                            console.log("*№*№*№*", this.articleEntries);
                            this.isLoadingArticle = false;
                        });
                    }
            },
        }
    }
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
