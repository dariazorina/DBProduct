<template id="project-add">
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
                <div class="row" style="background-color: transparent">
                    <div class="col-md-12">

                        <div class="form-row">
                            <div class="col-md-6">
                                <label for="add-name">Основное название*</label>
                                <input class="form-control" id="add-name" v-model="project.mainTitle"
                                       v-on:input="onTitleInput"
                                       placeholder="Поле должно быть заполнено"/>
                            </div>

                            <div class="col-md-2" style="background-color: transparent">
                                <label for="add-abbr">Другие названия</label>
                                <input class="form-control" id="add-abbr" v-model="project.otherTitle"/>
                            </div>
                        </div>

                        <div> <span class="notbold">
                            <ul id="alreadyExistedProhect">
                                <li v-for="item in alreadyExistedProject" :key="item.content">
                                    {{ item.content }}
                                </li>
                            </ul>
                            </span>
                        </div>
                    </div>
                </div>


                <div style="background-color: transparent">
                    <label>Теги проекта:</label>
                    <multiselect v-model="project.tagList" :options="allTags" label="content" track-by="content"
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

                                    @change="addSearchedEntity(selectedLocation, locationList, false)"
                                    item-text="content"
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

                                    @change="addSearchedEntity(selectedPerson, personList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти персону"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="personList.length>0" class="col-9"
                         style="background-color: transparent; padding:0">
                        <ConnectionComponent :itemsList="personList"
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

                                    @change="addSearchedEntity(selectedOrg, orgList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти организацию"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="orgList.length>0" class="col-9"
                         style="background-color: transparent; padding:0; margin: 0px">
                        <ConnectionComponent :itemsList="orgList"
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
                            <label style="font-size: medium; font-weight: bold">Связанные проекты (указание связи
                                обязательно)</label>
                            <v-autocomplete
                                    id="project-autocomplete"
                                    :items="projectItems"
                                    :loading="isLoadingProject"
                                    :search-input.sync="projectSearch"
                                    color="brown"
                                    hide-no-data
                                    hide-selected

                                    v-model="selectedProject"

                                    @change="addSearchedEntity(selectedProject, projectList, true)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы найти проект"
                                    prepend-icon="mdi-database-search"
                                    return-object
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="projectList.length > 0" class="col-9"
                         style="background-color: transparent; padding:0; margin: 0px">
                        <OneTypeConnComp :itemsList="projectList"
                                         :isLinkMode="false"
                                         :startValue="startValueOneTypeConnection"
                                         :isSelectionMode="false"
                                         :allTypes="connectionTypes"
                                         @get-is-parent="typeConnectionFunction"
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

                                    @change="addSearchedEntity(selectedArticle, articleList, false)"
                                    item-text="content"
                                    item-value="id"
                                    placeholder="Начните печатать, чтобы материал"
                                    prepend-icon="mdi-database-search"
                                    return-object
                                    :disabled="uploadMode"
                            ></v-autocomplete>
                        </v-card-text>
                    </div>

                    <div v-if="articleList.length>0" class="col-9"
                         style="background-color: transparent; padding:0">
                        <ConnectionComponent :itemsList="articleList"
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
                            <label style="font-size: medium; font-weight: bold">Связанные события - <i>в
                                процессе</i></label>
                            <!--                        <v-autocomplete-->
                            <!--                                style="background-color: transparent"-->
                            <!--                                id="author-autocomplete"-->
                            <!--                                :items="isourceItems"-->
                            <!--                                :loading="isLoadingIsource"-->
                            <!--                                :search-input.sync="isourceSearch"-->
                            <!--                                color="black"-->
                            <!--                                hide-no-data-->
                            <!--                                hide-selected-->

                            <!--                                v-model="selectedIsource"-->

                            <!--                                @change="addIsource(selectedIsource)"-->
                            <!--                                item-text="title"-->
                            <!--                                item-value="id"-->
                            <!--                                placeholder="Начните печатать, чтобы найти ресурс"-->
                            <!--                                prepend-icon="mdi-database-search"-->
                            <!--                                return-object-->
                            <!--                                :disabled="uploadMode"-->
                            <!--                        ></v-autocomplete>-->
                        </v-card-text>
                    </div>

                    <!--                    <div v-if="eventList.length>0" class="col-9"-->
                    <!--                         style="background-color: transparent; padding:0; margin: 0px">-->
                    <!--                        <ConnectionComponent :itemsList="eventList"-->
                    <!--                                             :isLinkMode="false"-->
                    <!--                                             :isSelectionMode="false"-->
                    <!--                                             :allTypes="connectionTypes"-->
                    <!--                                             style="background-color: transparent; padding:0px" class="col-12"/>-->
                    <!--                    </div>-->
                </div>

                <div class="row">
                    <div class="col-md-6" style="background-color: transparent">
                        <div class="form-row">
                            <div class="col-md-6" style="background-color: transparent">
                                <label>Год создания</label>
                                <b-form-select v-model="selectedFYear" class="mb-3" id="birth-year-selection">
                                    <option v-for="year in years">{{year}}</option>
                                </b-form-select>
                            </div>

                            <div class="col-md-6" style="background-color: transparent">
                                <label>Год закрытия</label>
                                <b-form-select v-model="selectedCYear" class="mb-3" id="death-year-selection">
                                    <option v-for="year in years">{{year}}</option>
                                </b-form-select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6" style="background-color: transparent">

                        <label>Описание</label>
                        <!--                    <label for="add-description">Описание</label>-->
                        <!--                    <textarea class="form-control" id="add-description" rows="7" v-model="person.description"/>-->

                        <ckeditor :editor="editor" v-model="project.description" :config="editorConfig"></ckeditor>
                    </div>
                    <div class="col-md-3" style="margin-top: 0px; background-color: transparent">
                        <div id="preview" style="margin-top: 31px" class="form-row">
                            <div v-if="avatar.imageBase64">
                                <img :src="avatar.imageBase64" @load="setHeight"
                                     :style="{ height: imageHeight + 'px' }"/>
                            </div>

                            <div v-if="avatar.imageBase64" class="col-md-1"
                                 style="background-color: transparent; padding-top: 0px; padding-left: 0px">
                                <span class="close" @click="deletePhoto">&times;</span>
                            </div>

                            <!--                            <div v-else>-->
                            <!--                                <img v-if="person.photo" v-bind:src="'data:image/jpeg;base64,'+person.photo"-->
                            <!--                                     :style="{ width: 250+'px' }"/>-->
                            <!--                            </div>-->
                        </div>
                        <!--                        <div v-if="avatar.imageBase64" style="margin-top: 5px">-->
                        <!--                            <input type="file" accept="image/*" @change="onChange"/>-->
                        <!--                        </div>-->
                        <div style="margin-top: 30px">
                            <input type="file" accept="image/*" @change="onChange"/>
                        </div>
                    </div>
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

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="project.miscellany"
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
                        <v-swatches style="margin-top: 5px" v-model="project.rowColor"
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
                            <router-link to="/project">Отмена</router-link>
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
                            <router-link to="/project" style="color: white">Отмена</router-link>
                        </button>
                    </div>

                    <div v-else class="offset-sm-4 col-sm-3">
                        <button type="button" style="margin-right: 20px" @click="uploadFiles"
                                class="btn btn-info">Upload Files
                        </button>
                        <button type="button" class="btn btn-info">
                            <router-link to="/project" style="color: white">Cancel</router-link>

                        </button>
                    </div>
                </div>
            </form>
        </div>
    </v-app>
</template>

<script>

    import api from "./project-api";
    import apiMovement from "./../movement/movement-api";
    import router from "./../../router";
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';
    import apiCountry from "./../country/country-api";
    import apiOrg from "./../org/org-api";
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

    export default {
        name: 'project-add',
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


            startValueOneTypeConnection: false,

            tagsValue: [],
            allTags: [],
            project: {
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

            alreadyExistedProject: [],

            allMovements: [],
            isAdmin: null,
            checkedMovements: [],

            years: [],
            selectedFYear: null,
            selectedCYear: null,
            statusList: [],

            connectionTypes: [],

            previewImage: null,
            avatar: {
                image: null,
                imageUrl: null,
                imageBase64: null
            },
            photoWasUploaded: false,
            originalPhoto: null,

            isLoadingLocation: false,
            selectedLocation: [],
            locationEntries: [],
            locationSearch: null,
            locationList: [],
            projectLocationIds: [], //before request
            projectLocationEntities: [], //after request


            linkAddTagOnKeys: [13, 9],
            hashAddTagOnKeys: [],
            descriptionLimit: 60,
            // entries: [],

            isLoadingProject: false,
            selectedProject: [],
            projectEntries: [],
            projectSearch: null,
            projectList: [],
            // projectProjectIds: [], //before request
            projectProjectEntities: [], //after request

            isLoadingOrg: false,
            selectedOrg: [],
            orgEntries: [],
            orgSearch: null,
            orgList: [],
            // projectOrgIds: [], //before request
            // projectOrgEntities: [], //after request


            isLoadingPerson: false,
            selectedPerson: [],
            personEntries: [],
            personSearch: null,
            personList: [],
            // projectPersonIds: [], //before request
            // projectPersonEntities: [], //after request


            isLoadingEvent: false,
            selectedEvent: [],
            eventEntries: [],
            eventSearch: null,
            eventList: [],
            // projectEventIds: [], //before request
            // projectEventEntities: [], //after request


            isLoadingArticle: false,
            selectedArticle: [],
            articleEntries: [],
            articleSearch: null,
            articleList: [],
            // projectArticleIds: [], //before request
            // projectArticleEntities: [], //after request


            // selectedProjectType: {},
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

            if (this.$route.params.project_id != null) {
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

            // apiMovement.getAllMovements(response => {
            //     // this.getLoggedIn();
            //     this.allMovements = response.data;
            //     this.currentUserMovement = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));//this.checkedMovements[0]);
            //
            //     let currentIndex = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));//this.checkedMovements[0]);
            //     let ddd = this.allMovements.indexOf(currentIndex);
            //     this.allMovements.splice(ddd, 1);
            //     //console.log("MOVEMENTS index", response.data, currentIndex, ddd);
            // });

            api.getAllTags(response => {
                this.allTags = response.data;
            });

            apiHashtag.getAllHashtags(response => {
                this.hashtagFlatTree = this.createFlatTree(response.data);
                // console.log(response.data)
            });

            // apiType.getAll(response => {
            //     //   this.allTags = response.data;
            //     this.orgTypeFlatTree = this.createFlatTree(response.data);
            //     // console.log("**********************", response.data);
            //     // console.log("**********************", this.orgTypeFlatTree);
            // });

            apiStatus.getAllStatuses(response => {
                this.statusList = response.data;
                // console.log("STATUS LIST", this.statusList);
            });

            apiArticle.getConnectionTypes(response => {
                this.connectionTypes = response.data;
                console.log("connectionTypes---------------", response.data)
            });

            this.initYears();

            if (this.editMode) {////////////////////////////////EDIT MODE//////////////////////////////////////
                api.findById(this.$route.params.project_id, r => {
                    this.project = r.data;
                    console.log("current project finded by id", this.project);

                    this.selectedFYear = this.project.foundationYear;
                    this.selectedCYear = this.project.closureYear;
                    this.selectedS = this.project.status;

                    for (let i = 0; i < this.project.hashtagList.length; i++) {
                        this.hashtags.push(this.project.hashtagList[i]);
                    }

                    for (let i = 0; i < this.project.linkList.length; i++) {
                        this.links.push(this.project.linkList[i].content);
                    }

                    for (let i = 0; i < this.project.movementList.length; i++) {
                        this.checkedMovements.push(this.project.movementList[i].id);
                    }

                    // for (let j = 0; j < this.project.locationList.length; j++) {
                    //     this.projectLocationIds.push(this.project.locationList[j].itemId);
                    // }
                    //
                    // for (let j = 0; j < this.project.articleList.length; j++) {
                    //     this.projectArticleIds.push(this.project.articleList[j].itemId);
                    // }
                    //
                    // for (let j = 0; j < this.project.personList.length; j++) {
                    //     this.projectPersonIds.push(this.project.personList[j].itemId);
                    // }
                    //
                    // for (let j = 0; j < this.project.orgList.length; j++) {
                    //     this.projectOrgIds.push(this.project.orgList[j].itemId);
                    // }

                    // for (let j = 0; j < this.project.eventList.length; j++) {  //todo
                    //     this.projectEventIds.push(this.project.eventList[j].itemId);
                    // }


                    apiAttachment.getAttachments('project', this.project.id, r => {
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });

                    // apiCountry.getLocationsByIds(this.projectLocationIds, response => {  ///returns List<Location>
                    //    this.projectLocationEntities = response.data;
                    //   console.log("getLocationsBy#Entities locaList", this.projectLocationEntities, this.project.locationList);

                    for (let i = 0; i < this.project.locationList.length; i++) {
                        let element = this.project.locationList[i];
                        //     let currentLocationEntity = this.projectLocationEntities.find(l => l.id === element.itemId);
                        //    console.log("--------------------------> currentLocationEntity", currentLocationEntity);
                        let connection = {
                            "id": element.itemId,
                            "name": element.name, //()currentLocationEntity.content, //this.countryTitleCreation(currentLocationEntity),
                            "connection": element.connection,
                            "comment": element.comment,
                        };
                        // console.log("CREATE PERS ON A: ", a);
                        this.locationList.push(connection);
                    }
                    // });

                    // apiPerson.getPersonsByIds(this.projectPersonIds, response => {
                    //     this.projectPersonEntities = response.data;

                    for (let i = 0; i < this.project.personList.length; i++) {
                        let element = this.project.personList[i];
                        // let currentPersonEntity = this.projectPersonEntities.find(l => l.id === element.itemId);
                        let connection = {
                            "id": element.itemId,
                            "name": element.name, //currentPersonEntity.content, //this.personNameCreation(currentPersonEntity),
                            "connection": element.connection,
                            "comment": element.comment,
                        };
                        // console.log("CREATE PERS ON A: ", a);
                        this.personList.push(connection);
                    }
                    // });


                    // apiOrg.getOrgsByIds(this.projectOrgIds, response => {
                    //     this.projectOrgEntities = response.data;

                    for (let i = 0; i < this.project.orgList.length; i++) {
                        let element = this.project.orgList[i];
                        // let currentOrgEntity = this.projectOrgEntities.find(l => l.id === element.itemId);
                        let connection = {
                            "id": element.itemId,
                            "name": element.name, //()currentOrgEntity.content,
                            "connection": element.connection,
                            "comment": element.comment,
                        };
                        // console.log("CREATE PERS ON A: ", a);
                        this.orgList.push(connection);
                    }
                    // });

                    api.getProjectsByIdsAndSymmetrically(this.project.id, response => {
                        this.projectProjectEntities = response.data;
                        console.log("api", this.projectProjectEntities);

                        for (let i = 0; i < this.projectProjectEntities.length; i++) {   ///todo is it necessary? to rewrite from one array to another
                            let connection = {
                                "id": this.projectProjectEntities[i].itemId, //element.itemId,
                                "name": this.projectProjectEntities[i].name, //this.orgEditConnectionTitleCreation(currentOrgEntity),
                                "connection": this.projectProjectEntities[i].connection,
                                "comment": this.projectProjectEntities[i].comment,
                                "isParent": this.projectProjectEntities[i].isParent,
                            };
                            this.projectList.push(connection);
                        }
                    });

                    // apiArticle.getMaterialsByIds(this.projectArticleIds, response => {
                    //     this.projectArticleEntities = response.data;   //returns List<project>
                    //     console.log("apiArt", this.projectArticleEntities);

                    for (let i = 0; i < this.project.articleList.length; i++) {
                        let element = this.project.articleList[i];
                        // let currentArticleEntity = this.projectArticleEntities.find(project => project.id === element.itemId);
                        // console.log("currentprojectEntity", currentArticleEntity);
                        let connection = {
                            "id": element.itemId,
                            "name": element.name, //currentArticleEntity.content,
                            "connection": element.connection,
                            "comment": element.comment,
                        };
                        this.articleList.push(connection);
                    }
                    // });

                    // api.getIsourceByIds(this.personPersonIds, response => {  //todo
                    //     this.personPersonEntities = response.data;
                    //
                    //     for (let i = 0; i < this.person.personList.length; i++) {
                    //         let element = this.person.personList[i];
                    //         let currentPersonEntity = this.personPersonEntities.find(l => l.id === element.itemId);
                    //         let connection = {
                    //             "id": element.itemId,
                    //             "name": this.personNameCreation(currentPersonEntity),
                    //             "connection": element.connection,
                    //             "comment": element.comment,
                    //             "hasClicked": true
                    //         };
                    //         // console.log("CREATE PERS ON A: ", a);
                    //         this.personList.push(connection);
                    //     }
                    // });

                    apiAttachment.getAttachmentPhoto('project', this.project.id, r => {
                        console.log("R DATA", r);
                        this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;//.content;

                        let blob = new Blob([r.data], {type: 'image/jpeg'});
                        console.log("BLOB", blob);
                    });
                });
            }
        },

        methods: {
            typeConnectionFunction(isParent) {
                console.log("typeConnectionFunction", isParent);
            },

            onTitleInput() {
                if (this.project.mainTitle.length > 3) {
                    console.log("IN ONPUT");

                    api.searchProject(this.project.mainTitle, r => {
                        this.alreadyExistedProject = r;
                        console.log("*№*№*№*", this.alreadyExistedProject);
                    });
                } else {
                    this.alreadyExistedProject.splice(0);
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
                // console.log("removeAtt project", file);
                apiAttachment.removeAttachment('project', this.project.id, file.id, file.name, r => {
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

            // onOrgTypeSelect(item) {
            //     console.log("<onOrgTypeSelect>", this.selectedOrgType);
            //     this.selectedOrgType = item;//.name;
            //     console.log("<onOrgTypeSelect>", this.selectedOrgType, item);
            // },

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


            setHeight(event) {
                let image = event.target;
                if (image.clientWidth > image.clientHeight) {
                    let pr = image.clientWidth / image.clientHeight;
                    this.imageHeight = 350 / pr;
                } else {
                    this.imageHeight = 350;
                }
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
                // let projectConnectionExistance = false;
                //
                // console.log("preliminaryDataCheck", currentStatus);
                // if (this.isObjectValidAndNotEmpty(this.projectList)) {
                //     projectConnectionExistance = this.checkConnection(this.projectList);
                // }
                //
                // if (projectConnectionExistance) {
                //     alert("Укажите связь для сущностей, которые вы добавили");
                // } else {
                this.createProject(currentStatus);
                // }
            },

            initYears() {
                this.years.push("null");
                for (let y = 1800; y < 2051; y++) {
                    this.years.push(y);
                }
            },

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

            formValidate() {

                console.log("chM", this.checkedMovements, this.project.mainTitle);

                if (this.project.mainTitle != null) {
                    if (this.project.mainTitle.length !== 0) {
                        this.addStatus('add-name', (!this.project.mainTitle));
                        if (this.checkedMovements.length === 0) {
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

            onChange(e) {
                //todo need to set size limit

                const file = e.target.files[0];
                this.avatar.image = file;
                this.avatar.imageUrl = URL.createObjectURL(file);

                const reader = new FileReader();
                reader.readAsDataURL(file);  // reader.readAsBinaryString(file);
                reader.onload = e => {
                    this.avatar.imageBase64 = e.target.result;
                    console.log(this.avatar);

                    console.log("photoWasUploaded", this.photoWasUploaded);
                    console.log("originalPhoto", this.originalPhoto);
                    console.log("this.avatar.imageBase64", this.avatar.imageBase64);

                    if (this.originalPhoto !== this.avatar.imageBase64) {
                        this.photoWasUploaded = true;
                        console.log("-photoWasUploaded", this.photoWasUploaded);
                    }
                };
            },

            createProject(currentStatus) {
                this.hasError = false;

                if (this.formValidate()) {

                    if (currentStatus.name === this.statusList[0].name) {
                        this.project.status = this.statusList[0].name;
                    } else {
                        this.project.status = this.statusList[1].name;
                    }

                    if (this.selectedFYear) {
                        this.project.foundationYear = this.selectedFYear;
                    }
                    if (this.selectedCYear) {
                        this.project.closureYear = this.selectedCYear;
                    }

                    this.project.linkList.splice(0);
                    this.project.hashtagList.splice(0);
                    this.project.movementList.splice(0);

                    let i = 0;
                    // console.log("+++++++++++++++++++++++++SAVE", this.checkedMovements, this.checkedMovements.length, this.project.movementList);
                    for (; i < this.checkedMovements.length; i++) {
                        this.project.movementList[i] = {
                            "id": this.checkedMovements[i]
                        };
                    }
                    // console.log("+++++++++++++++++++++++++SAVE this.project.movementList", this.project.movementList);

                    // if (!this.editMode) {
                    //     this.project.movementList[i] = {
                    //         "id": this.currentUserMovement.id
                    //     };
                    // }

                    for (let i = 0; i < this.links.length; i++) {
                        this.project.linkList[i] = {
                            "content": this.links[i]
                        };
                        // console.log("CREATE project link: ", this.links[i]);
                    }

                    for (let i = 0; i < this.hashtags.length; i++) {
                        this.project.hashtagList[i] = this.hashtags[i];
                    }

                    this.project.locationList.splice(0);
                    this.project.personList.splice(0);
                    this.project.orgList.splice(0);
                    // this.project.eventList.splice(0);
                    this.project.projectList.splice(0);
                    this.project.articleList.splice(0);
                    this.finalConnectionListCreation(this.locationList, this.project.locationList, true);
                    this.finalConnectionListCreation(this.orgList, this.project.orgList, true);
                    this.finalConnectionListCreation(this.personList, this.project.personList, true);
                    this.finalOneTypeConnectionListCreation(this.projectList, this.project.projectList);
                    // this.finalConnectionListCreation(this.eventList, this.project.eventList, true);
                    this.finalConnectionListCreation(this.articleList, this.project.articleList, true);

                    console.log("ORG BEFORE  CREATION", this.project, this.projectList);


                    if (this.editMode) {
                        this.project.status = this.selectedS;
                        console.log("project BEFORE UPDATING edit mode", this.project);
                        if (this.formValidate()) {
                            api.update(this.project.id, this.project, r => {
                                console.log(r);
                                if (this.photoWasUploaded) {
                                    apiAttachment.deletePhoto('project', r.data.id, r => {
                                    });
                                    if (this.avatar.image !== null) {
                                        apiAttachment.uploadPhoto('project', r.data.id, this.avatar.image, r => {
                                            console.log("ph was uplded");
                                        });
                                    }
                                }
                                for (let i = 0; i < this.attachedFiles.length; i++) {
                                    apiAttachment.uploadFile('project', this.project.id, this.attachedFiles[i], r => {
                                    });
                                    //todo progress bar?
                                }
                                router.push('/project');
                            });
                        }
                    } else {
                        console.log("project BEFORE SAVING", this.project);
                        if (this.formValidate()) {
                            api.create(this.project, r => {
                                console.log(r);

                                if (this.avatar.image !== null) {
                                    apiAttachment.uploadPhoto('project', r.data.id, this.avatar.image, r => {
                                    });
                                }

                                if (!this.uploadFilesCheckBoxValue) {
                                    router.push('/project');
                                } else {
                                    this.uploadMode = true;
                                    this.uploadFilesCheckBoxValue = false;
                                    let ID = r.data.id;
                                    this.project.id = ID;
                                    console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>stay here, project ID", r.data, ID);
                                }
                            });
                        }
                    }
                }
            },

            uploadFiles() {             //on button press
                for (let i = 0; i < this.attachedFiles.length; i++) {
                    apiAttachment.uploadFile('project', this.project.id, this.attachedFiles[i], r => {
                    });
                    // this.submitFile(this.attachedFiles[i]);
                    //todo progress bar?
                }
                router.push('/project');
            },

            createAttachment(files) {     //emit from FilesAttachment Component 'attachFiles'
                console.log("files from COMPONENT", files);
                this.attachedFiles = [];
                for (let i = 0; i < files.length; i++) {
                    this.attachedFiles.push(files[i]);
                }
            },

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/project/downloadAttachment?entityId=' + this.project.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                apiAttachment.previewAttachment('project', this.project.id, file.id);
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
                    if (isEmptyConnectionPossible) {
                        finalList.push(a);

                    } else if (a.connection.length > 0) { //to avoid add empty connections (wasn't entered)
                        finalList.push(a);
                    }
                }
            },

            finalOneTypeConnectionListCreation(list, finalList) {
                console.log("^^^^^^^^^^^^^^^finalONETYPEConnectionListCreation^^^^^^^^^ ", list, finalList);
                for (let i = 0; i < list.length; i++) {
                    let a = {
                        "itemId": list[i].id,
                        "name": list[i].name,
                        "connection": list[i].connection,
                        "comment": list[i].comment,
                        "isParent": list[i].isParent,
                    };
                    // if (a.connection.length > 0) { //to avoid add empty connections (wasn't entered)
                    finalList.push(a);
                    //   }
                }
            },

            addSearchedEntity(obj, list, isOneType) {
                console.log("GET CHANGED ORG", obj, list);
                let i = 0;
                for (i = 0; i < list.length; i++) { //to exclude double values
                    if (list[i].id === obj.id) {
                        break;
                    }
                }

                let connection;
                if (i === list.length) {
                    if (isOneType === true) {
                        connection = {
                            "id": obj.id,
                            "name": obj.content,
                            "comment": '',
                            "connection": '',
                            "isParent": false,
                        };
                    } else {
                        connection = {
                            "id": obj.id,
                            "name": obj.content,
                            "comment": '',
                            "connection": '',
                        };
                    }
                    list.push(connection);
                    console.log("ADDED");
                }
            },


            // addLocation(obj) {  //todo to unite all "addxxx"
            //     console.log("GET CHANGED LOCATION", obj);
            //     let i = 0;
            //     for (i = 0; i < this.locationList.length; i++) { //to exclude double values
            //         if (this.locationList[i].id === obj.id) {
            //             break;
            //         }
            //     }
            //
            //     if (i === this.locationList.length) {
            //         let connection = {
            //             "id": obj.id,
            //             "name": obj.content,
            //             "comment": '',
            //             "connection": '',
            //         };
            //         this.locationList.push(connection);
            //         console.log("ADDED", this.locationList);
            //     }
            // },
            //
            // addPerson(obj) {
            //     console.log("GET CHANGED PERSON", obj);
            //     let i = 0;
            //     for (i = 0; i < this.personList.length; i++) { //to exclude double values
            //         if (this.personList[i].id === obj.id) {
            //             break;
            //         }
            //     }
            //
            //     if (i === this.personList.length) {
            //         let connection = {
            //             "id": obj.id,
            //             "name": obj.content,
            //             "comment": '',
            //             "connection": '',
            //         };
            //         this.personList.push(connection);
            //         console.log("ADDED", this.personList);
            //     }
            // },
            //
            // addOrg(obj) {
            //     let i = 0;
            //     for (i = 0; i < this.orgList.length; i++) { //to exclude double values
            //         if (this.orgList[i].id === obj.id) {
            //             break;
            //         }
            //     }
            //
            //     if (i === this.orgList.length) {
            //         let connection = {
            //             "id": obj.id,
            //             "name": obj.content,
            //             "comment": '',
            //             "connection": '',
            //         };
            //         this.orgList.push(connection);
            //         console.log("ADDED", this.orgList);
            //     }
            // },
            //
            // addProject(obj) {
            //     let i = 0;
            //     for (i = 0; i < this.projectList.length; i++) { //to exclude double values
            //         if (this.projectList[i].id === obj.id) {
            //             break;
            //         }
            //     }
            //
            //     if (i === this.projectList.length) {
            //         let connection = {
            //             "id": obj.id,
            //             "name": obj.content,
            //             "comment": '',
            //             "connection": '',
            //         };
            //         this.projectList.push(connection);
            //         console.log("ADDED", this.projectList);
            //     }
            // },
            // addArticle(obj) {
            //     let i = 0;
            //     for (i = 0; i < this.articleList.length; i++) { //to exclude double values
            //         if (this.articleList[i].id === obj.id) {
            //             break;
            //         }
            //     }
            //
            //     if (i === this.articleList.length) {
            //         let connection = {
            //             "id": obj.id,
            //             "name": obj.content,
            //             "comment": '',
            //             "connection": '',
            //         };
            //         this.articleList.push(connection);
            //         console.log("ADDED ARTICLE", this.articleList);
            //     }
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
                    console.log("project items in", this.projectEntries);

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

            // searchOrgTypeLength() {
            //     return this.searchOrgType.length
            // },

            // filteredOrgTypes() {
            //     let resultSearchTree = this.searchTree(this.orgTypeFlatTree, this.searchOrgType);
            //     return this.flatTreeToTree(resultSearchTree);
            //     // console.log("COMPUTED", resultSearchTree);
            // },

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

            // filteredOpenOrgTypes() {
            //     // console.log("filteredOpenMTypes", this.filteredMTypes);
            //
            //     if (this.searchOrgType != null)   //for start view without search
            //         if (this.searchOrgTypeLength === 0) {
            //             return this.filteredOrgTypes.map((top) => {
            //                 return top.name
            //             })
            //         } else {
            //             return this.filteredOrgTypes;
            //         }
            // },
        },

        watch: {
            // checkedMovements() {
            //     console.log("watch this.checkedMovements.length", this.checkedMovements);
            // },

            disableColorCheckBoxFlag() {
                if (this.disableColorCheckBoxFlag === true) {
                    this.project.rowColor = null;
                }
            },

            // searchOrgType() {
            //     this.$nextTick(() => {
            //         if (this.searchOrgTypeLength === 0) {
            //             this.$refs.orgtypetreeviewref.updateAll(false);
            //         } else {
            //             this.$refs.orgtypetreeviewref.updateAll(true);
            //         }
            //     });
            // },

            locationSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 1) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedLocation !== 'undefined') {
                            // console.log("SELECTED IN WATCH");
                            // console.log(this.selectedLocation);
                            if (this.project.locationList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
                            if (this.project.personList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
                            if (this.project.orgList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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

            projectSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedProject !== 'undefined') {
                            if (this.project.projectList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedProject = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingProject) return;
                        this.isLoadingProject = true;

                        console.log("search pro", val);

                        api.searchProject(val, r => {
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
                            if (this.project.articleList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
