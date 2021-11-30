<template id="org-add">
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
                    <!--                        v-if="isAdmin==='true'||addAdditionalMovementFlag">-->

                    <!--                        <div class="col-md-2">-->
                    <!--                            <label><b>Текущее движение:</b> </label>-->
                    <!--                            <div>-->
                    <!--                                <input style="margin-right: 5px;" type="checkbox" checked disabled/>-->
                    <!--                                <label>{{currentUserMovement.name}} </label>-->
                    <!--                            </div>-->
                    <!--                        </div>-->

                    <label for="add-name"><b>Тема*</b></label>
                    <div class="col-md-4" style="background-color: transparent">
                        <!--                            <label><b>Добавить дополнительное движение:</b></label>-->

                        <div v-for="(movement, index) in allMovements">
                            <input style="margin-right: 5px;" v-bind:value="movement.id" name="movement.name"
                                   type="checkbox"
                                   v-model="checkedMovements"/>
                            <!--                                    :hidden="movement.id === checkedMovements[0]"                                            :key="toKey"/>-->
                            <label style="margin: 5px; padding-top: 0px; padding-bottom: 3px; background-color: transparent"
                                   :for="movement.id"><span>{{movement.name}}</span></label>
                        </div>
                    </div>
                </div>
                <!--                    <div v-else class="col-12">-->
                <!--                        <button type="button" style="margin-right: 20px" @click="addAdditionalMovement"-->
                <!--                                class="btn btn-info">Добавить дополнительное движение-->
                <!--                        </button>-->
                <!--                    </div>-->
            </form>

            <form class="authorsFormCreation"
                  style="background-color: transparent; padding: 5px 0 5px">
                <div class="row" style="background-color: transparent">
                    <div class="col-md-12">

                        <div class="form-row">
                            <div class="col-md-6">
                                <label for="add-name">Название на русском*</label>
                                <input class="form-control" id="add-name" v-model="orgNameTFValues[0]"
                                       placeholder="Поле должно быть заполнено"/>
                            </div>

                            <div class="col-md-2" style="background-color: transparent">
                                <label for="add-abbr">Аббревиатура</label>
                                <input class="form-control" id="add-abbr" v-model="orgAbbrTFValues[0]"/>
                            </div>

                            <div class="col-md-2" style="background-color: transparent; vertical-align: bottom">
                                <b-button variant="info" v-b-modal="'modalOrgNames'" style="margin-top: 32px">
                                    Добавить названия на других языках
                                </b-button>
                            </div>
                        </div>

                        <div v-if="orgNameTFValues[1]!=null">
                            <label for="add-name">Дополнительные названия:
                                <span class='notbold'><br>{{orgNameTFValues[1]}} {{orgAbbrTFValues[1]}} </span>
                                <span v-if="orgNameTFValues[2]!=null" class='notbold'><br>{{orgNameTFValues[2]}} {{orgAbbrTFValues[2]}} </span>
                                <span v-if="orgNameTFValues[3]!=null" class='notbold'><br>{{orgNameTFValues[3]}} {{orgAbbrTFValues[3]}} </span>
                                <span v-if="orgNameTFValues[4]!=null" class='notbold'><br>{{orgNameTFValues[4]}} {{orgAbbrTFValues[4]}} </span>

                            </label>
                            <!--                            <input class="form-control" v-model="orgNameTFValues[1]" disabled/>-->
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

                                    @change="addPerson(selectedPerson)"
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

                                    @change="addOrg(selectedOrg)"
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

                                    @change="addArticle(selectedArticle)"
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
                            <label style="font-size: medium; font-weight: bold">Связанные ресурсы - <i>в
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

                    <div v-if="isourceList.length>0" class="col-9"
                         style="background-color: transparent; padding:0; margin: 0px">
                        <ConnectionComponent :itemsList="isourceList"
                                             :isLinkMode="false"
                                             :isSelectionMode="false"
                                             :allTypes="connectionTypes"
                                             style="background-color: transparent; padding:0px" class="col-12"/>
                    </div>
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

                        <ckeditor :editor="editor" v-model="org.description" :config="editorConfig"></ckeditor>
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
                                       v-model="tags"></input-tag>

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

                                    <textarea class="form-control" id="add-misc" rows="5" v-model="org.miscellany"
                                              background-color="palegreen" required></textarea>
                        </div>
                    </div>
                </div>


                <div class="form-row align-items-center">
                    <label><b>Тип организации</b></label>
                    <div class="col-12" style="background-color: transparent">
                        <b-card style="background-color: transparent">
                            <v-row style="background-color: transparent; margin-top: -10px; margin-bottom: -10px;">
                                <v-col style="background-color: transparent; margin-top: -10px; margin-left: -5px; margin-bottom: -10px">
                                    <v-sheet
                                            style="padding-left: 0; padding-top: 0; padding-right: 18px; background-color: transparent">
                                        <v-text-field label="поиск типа организации"
                                                      v-model="searchOrgType"
                                                      filled>
                                        </v-text-field>
                                    </v-sheet>

                                    <v-container
                                            id="scroll-target"
                                            style="max-height: 300px; background-color: transparent; margin-top: -10px; margin-left: -15px; padding-top: 0; padding-left: 0;"
                                            class="overflow-y-auto"
                                    >

                                        <v-treeview
                                                :items="filteredOrgTypes"
                                                :open="filteredOpenOrgTypes"
                                                item-key="name"

                                                activatable
                                                color="warning"
                                                dense
                                                :open-all="true"

                                                return-object
                                                hoverable
                                                ref="orgtypetreeviewref">

                                            <template slot="label" slot-scope="{ item }">
                                                <a @click="onOrgTypeSelect(item)">{{ item.name }}</a>
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

                                        <template v-if="!isObjectValidAndNotEmpty(selectedOrgType.name)">
                                            Ничего не выбрано
                                        </template>

                                        <template v-else>
                                            <label style="background-color: transparent">Выбранный тип
                                                организации</label>
                                            <div>
                                                <v-btn text icon x-small @click="removeSelectedOrgType">
                                                    <v-icon style="color: red">mdi-delete-forever</v-icon>
                                                </v-btn>
                                                {{selectedOrgType.name}}
                                            </div>
                                        </template>
                                    </v-container>
                                </v-col>
                            </v-row>
                        </b-card>
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
                        <v-swatches style="margin-top: 5px" v-model="org.rowColor"
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
                            <router-link to="/org">Отмена</router-link>
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
                            <router-link to="/org" style="color: white">Отмена</router-link>
                        </button>
                    </div>

                    <div v-else class="offset-sm-4 col-sm-3">
                        <button type="button" style="margin-right: 20px" @click="uploadFiles"
                                class="btn btn-info">Upload Files
                        </button>
                        <button type="button" class="btn btn-info">
                            <router-link to="/org" style="color: white">Cancel</router-link>

                        </button>
                    </div>
                </div>
            </form>


            <b-modal ref="modalOrgNames" id="modalOrgNames" size="lg" :hide-footer="true" :hide-header="true">
                <template>
                    <b><p class="myHeader">Добавить название, аббревиатуру на других языках</p><br></b>
                    <div align="center">
                        <div class="form-row">
                            <div class="col-md-8" style="background-color: transparent">
                                <label><b>Название*</b></label>
                                <input class="form-control" id="nameTF1" v-model="orgNameTFValues[1]"
                                       placeholder="Обязательное поле"/>
                            </div>

                            <div class="form-row col-md-4">
                                <div class="col-md-11" style="background-color: transparent; padding: 0 5px 0">
                                    <label for="add-abbr">Аббревиатура</label>
                                    <input id="abbrTF1" class="form-control" v-model="orgAbbrTFValues[1]"/>
                                </div>
                                <div class="col-md-1" style="background-color: transparent; padding-top: 30px">
                                <span class="close"
                                      @click="clearFields(1)">&times;</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-md-8" style="background-color: transparent; padding-top: 25px">
                                <input class="form-control" id="nameTF2" v-model="orgNameTFValues[2]"
                                       placeholder="Обязательное поле"/>
                            </div>
                            <div class="form-row col-md-4" style="background-color: transparent; padding-top: 10px">
                                <div class="col-md-11" style="background-color: transparent; padding: 15px 5px 0">
                                    <input class="form-control" id="abbrTF2" v-model="orgAbbrTFValues[2]"/>
                                </div>
                                <div class="col-md-1" style="background-color: transparent">
                                <span class="close"
                                      @click="clearFields(2)">&times;</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-md-8" style="background-color: transparent; padding-top: 25px">
                                <input class="form-control" id="nameTF3" v-model="orgNameTFValues[3]"
                                       placeholder="Обязательное поле"/>
                            </div>
                            <div class="form-row col-md-4" style="background-color: transparent; padding-top: 10px">
                                <div class="col-md-11" style="background-color: transparent; padding: 15px 5px 0">
                                    <input class="form-control" id="abbrTF3" v-model="orgAbbrTFValues[3]"/>
                                </div>
                                <div class="col-md-1" style="background-color: transparent">
                                <span class="close"
                                      @click="clearFields(3)">&times;</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-md-8" style="background-color: transparent; padding-top: 25px">
                                <input class="form-control" id="nameTF4" v-model="orgNameTFValues[4]"
                                       placeholder="Обязательное поле"/>
                            </div>
                            <div class="form-row col-md-4" style="background-color: transparent; padding-top: 10px">
                                <div class="col-md-11" style="background-color: transparent; padding: 15px 5px 0">
                                    <input class="form-control" id="abbrTF4" v-model="orgAbbrTFValues[4]"/>
                                </div>
                                <div class="col-md-1" style="background-color: transparent">
                                <span class="close"
                                      @click="clearFields(4)">&times;</span>
                                </div>
                            </div>
                        </div>

                        <b-button style="margin-right: 4px" size="sm" variant="success"
                                  @click="saveAdditionalOrgNames()">
                            OK
                        </b-button>
                        <div style="color: gray; font-size: 12px; font-weight: normal; margin-top: 20px">* -
                            Обязательное
                            поле. Если <b>Название</b> не будет заполнено,
                            то <br><b> Аббревиатура </b> сохранена не будет
                        </div>
                    </div>
                </template>
            </b-modal>

        </div>
    </v-app>
</template>

<script>

    import apiOrg from "./org-api";
    import apiMovement from "./../movement/movement-api";
    import router from "./../../router";
    import Vuetify from 'vuetify';
    import 'vuetify/dist/vuetify.min.css';
    // import LanguageAdd from "../language/LanguageAdd";
    import apiCountry from "./../country/country-api";
    import apiStatus from "./../status-api";
    import apiHashtag from "./../hashtag/hashtag-api";
    import apiPerson from "./../person/person-api";
    import apiArticle from "./../article/article-api";

    import ConnectionComponent from "../components/connection/ConnectionComponent";
    import CKEditor from 'ckeditor4-vue';
    import FileAttachment from "../components/FileAttachment";
    import apiAttachment from "./../attachment-api";
    import apiOrgType from "./../orgtype-api";
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple

    import apiLogin from "../login-api";

    export default {
        name: 'org-add',
        components: {
            ConnectionComponent,
            ckeditor: CKEditor.component,
            FileAttachment,
            // LanguageAdd
            // OccupationList,
            VSwatches,
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

            org: {
                movementList: [],
                locationList: [],
                articleList: [],
                isourceList: [],
                orgList: [],
                personList: [],
                nameList: [],
                linkList: [],
                hashtagList: []
            },

            allMovements: [],
            isAdmin: null,
            // addAdditionalMovementFlag: false,
            checkedMovements: [],

            orgNameTFValues: [],
            orgAbbrTFValues: [],
            // keyOrgNameTFValues: [],
            // keyOrgAbbrTFValues: [],

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
            orgLocationIds: [], //before request
            orgLocationEntities: [], //after request


            linkAddTagOnKeys: [13, 9],
            hashAddTagOnKeys: [],
            descriptionLimit: 60,
            // entries: [],

            isLoadingOrg: false,
            selectedOrg: [],
            orgEntries: [],
            orgSearch: null,
            orgList: [],
            orgOrgIds: [], //before request
            orgOrgEntities: [], //after request


            isLoadingPerson: false,
            selectedPerson: [],
            personEntries: [],
            personSearch: null,
            personList: [],
            orgPersonIds: [], //before request
            orgPersonEntities: [], //after request


            isLoadingIsource: false,
            selectedIsource: [],
            isourceEntries: [],
            isourceSearch: null,
            isourceList: [],
            orgIsourceIds: [], //before request
            orgIsourceEntities: [], //after request


            isLoadingArticle: false,
            selectedArticle: [],
            articleEntries: [],
            articleSearch: null,
            articleList: [],
            orgArticleIds: [], //before request
            orgArticleEntities: [], //after request


            selectedOrgType: {},
            searchHashtag: '',
            tagsTree: [],
            selectedS: null,
            selectedHashtag: [],
            hashtagTree: [],
            hashtagFlatTree: [],
            orgTypeFlatTree: [],
            tags: [],
            links: [],
            editMode: false,
            uploadMode: false,
            uploadFilesCheckBoxValue: false,
            imageHeight: null,
            attachedFiles: [],
            uploadedFiles: [],

            searchOrgType: '',
            nameList: [],
            disableColorCheckBoxFlag: false
        }),

        mounted() {
            this.getLoggedIn();

            if (this.$route.params.org_id != null) {
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

            apiHashtag.getAllHashtags(response => {
                //   this.allTags = response.data;
                this.hashtagFlatTree = this.createFlatTree(response.data);
                // console.log(response.data)
            });

            apiOrgType.getAll(response => {
                //   this.allTags = response.data;
                this.orgTypeFlatTree = this.createFlatTree(response.data);
                // console.log("**********************", response.data);
                // console.log("**********************", this.orgTypeFlatTree);
            });

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
                apiOrg.findById(this.$route.params.org_id, r => {
                    this.org = r.data;
                    console.log("current org finded by id", this.org);

                    this.getNameWithPriority(1);
                    this.getNameWithPriority(0);

                    this.selectedFYear = this.org.foundationYear;
                    this.selectedCYear = this.org.closureYear;
                    this.selectedS = this.org.status;

                    for (let i = 0; i < this.org.hashtagList.length; i++) {
                        this.tags.push(this.org.hashtagList[i]);
                    }

                    for (let i = 0; i < this.org.linkList.length; i++) {
                        this.links.push(this.org.linkList[i].content);
                    }

                    for (let i = 0; i < this.org.movementList.length; i++) {
                        this.checkedMovements.push(this.org.movementList[i].id);
                    }

                    for (let j = 0; j < this.org.locationList.length; j++) {
                        this.orgLocationIds.push(this.org.locationList[j].itemId);
                    }

                    for (let j = 0; j < this.org.articleList.length; j++) {
                        this.orgArticleIds.push(this.org.articleList[j].itemId);
                    }

                    for (let j = 0; j < this.org.personList.length; j++) {
                        this.orgPersonIds.push(this.org.personList[j].itemId);
                    }

                    // for (let j = 0; j < this.org.orgList.length; j++) {
                    //     this.orgOrgIds.push(this.org.orgList[j].itemId);
                    // }

                    // for (let j = 0; j < this.person.isourceList.length; j++) {  //todo
                    //     this.personIsourceIds.push(this.person.isourceList[j].itemId);
                    // }


                    // for (let j = 0; j < this.org.nameList.length; j++) {
                    //     this.nameList.push(this.org.nameList[j].id);
                    // }

                    apiAttachment.getAttachments('org', this.org.id, r => {
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });

                    apiCountry.getLocationsByIds(this.orgLocationIds, response => {  ///returns List<Location>
                        this.orgLocationEntities = response.data;
                        console.log("getLocationsBy#Entities locaList", this.orgLocationEntities, this.org.locationList);

                        for (let i = 0; i < this.org.locationList.length; i++) {
                            let element = this.org.locationList[i];
                            let currentLocationEntity = this.orgLocationEntities.find(l => l.id === element.itemId);
                            console.log("--------------------------> currentLocationEntity", currentLocationEntity);
                            let connection = {
                                "id": element.itemId,
                                "name": currentLocationEntity.content, //this.countryTitleCreation(currentLocationEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.locationList.push(connection);
                        }
                    });

                    apiPerson.getPersonsByIds(this.orgPersonIds, response => {
                        this.orgPersonEntities = response.data;

                        for (let i = 0; i < this.org.personList.length; i++) {
                            let element = this.org.personList[i];
                            let currentPersonEntity = this.orgPersonEntities.find(l => l.id === element.itemId);
                            let connection = {
                                "id": element.itemId,
                                "name": currentPersonEntity.content, //this.personNameCreation(currentPersonEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            // console.log("CREATE PERS ON A: ", a);
                            this.personList.push(connection);
                        }
                    });

                    apiOrg.getOrgsByIdsAndSymmetrically(this.org.id, response => {
                        this.orgOrgEntities = response.data;   //returns List<Org>
                        console.log("apiOrg", this.orgOrgEntities);


                        // for (let i = 0; i < this.org.orgList.length; i++) {
                        //     let element = this.org.orgList[i];
                        //     let currentOrgEntity = this.orgOrgEntities.find(org => org.id === element.itemId);
                        //     console.log("currentOrgEntity", currentOrgEntity);

                        for (let i = 0; i < this.orgOrgEntities.length; i++) {   ///todo is it necessary? to rewrite from one array to another
                            let connection = {
                                "id": this.orgOrgEntities[i].itemId, //element.itemId,
                                "name": this.orgOrgEntities[i].name, //this.orgEditConnectionTitleCreation(currentOrgEntity),
                                "connection": this.orgOrgEntities[i].connection,
                                "comment": this.orgOrgEntities[i].comment,
                                "hasClicked": true
                            };
                            this.orgList.push(connection);
                        }
                    });

                    apiArticle.getMaterialsByIds(this.orgArticleIds, response => {
                        this.orgArticleEntities = response.data;   //returns List<Org>
                        console.log("apiArt", this.orgArticleEntities);

                        for (let i = 0; i < this.org.articleList.length; i++) {
                            let element = this.org.articleList[i];
                            let currentArticleEntity = this.orgArticleEntities.find(org => org.id === element.itemId);
                            console.log("currentOrgEntity", currentArticleEntity);
                            let connection = {
                                "id": element.itemId,
                                "name": currentArticleEntity.content, //this.orgEditConnectionTitleCreation(currentOrgEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            this.articleList.push(connection);
                        }
                    });

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

                    // console.log("---ORG---TYPE", this.org.type);
                    if (this.isObjectValidAndNotEmpty(this.org.type)) {
                        this.selectedOrgType = {
                            "id": this.org.type.id,
                            "name": this.org.type.content,
                        };
                        // console.log("selected---ORG---TYPE", this.selectedOrgType, this.org.type);
                    }

                    apiAttachment.getAttachmentPhoto('org', this.org.id, r => {
                        console.log("R DATA", r);
                        this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;//.content;

                        let blob = new Blob([r.data], {type: 'image/jpeg'});
                        console.log("BLOB", blob);
                    });
                });
            }
        },

        methods: {
            deletePhoto() {
                this.avatar.image = null;
                this.avatar.imageBase64 = null;
                this.avatar.imageUrl = null;

                this.photoWasUploaded = true;
                this.originalPhoto = null;
            },

            removeAttachment(file) {
                // console.log("removeAtt ORG", file);
                apiAttachment.removeAttachment('org', this.org.id, file.id, file.name, r => {
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

            getNameWithPriority(priority) {
                let currEl, j = 1;
                for (let i = 0; i < this.org.nameList.length; i++) {
                    currEl = this.org.nameList[i];
                    if (this.org.nameList[i].priority === priority) {
                        //console.log("NAME priority", priority, currEl.name);

                        // this.orgNameTFValues[(priority === 1 ? 0 : j)] = currEl.name;
                        // if (this.org.nameList[i].abbr != null)
                        //     this.orgAbbrTFValues[(priority === 1 ? 0 : j)] = currEl.abbr;
                        //
                        if (priority === 1) {
                            this.orgNameTFValues[0] = currEl.name;
                            if (this.org.nameList[i].abbr != null)
                                this.orgAbbrTFValues[0] = currEl.abbr;
                        } else {
                            this.orgNameTFValues[j] = currEl.name;
                            if (this.org.nameList[i].abbr != null)
                                this.orgAbbrTFValues[j] = currEl.abbr;
                            j++;
                        }
                    }
                }
            },

            getLoggedIn() {
                this.loggedName = this.$store.getters.getUserName;  //todo change to author, not current user
                this.isAdmin = localStorage.getItem('isAdmin');
               // console.log("this.checkedMovements", this.checkedMovements);
            },

            removeSelectedOrgType() {
                this.selectedOrgType = '';
            },

            clearFields(index) {
                document.getElementById("nameTF" + index).value = '';
                document.getElementById("abbrTF" + index).value = '';

                this.orgNameTFValues.splice(index, 1);
                this.orgAbbrTFValues.splice(index, 1);

                //this.orgAbbrTFValues[index] = '';
            },

            saveAdditionalOrgNames() {
                //this.addAdditionalNameToList();
                this.$refs.modalOrgNames.hide();
            },

            // addAdditionalNameToList() {
            //     let name = '';
            //     this.nameList.splice(0);
            //     for (let i = 1; i < this.orgNameTFValues.length; i++) {
            //         console.log("iiiiii", i);
            //         if (this.orgNameTFValues[i] != null) {
            //             //item with index = 0 pushed in createOrg()
            //
            //             if (this.orgNameTFValues[i].length > 0) {
            //                 name = {
            //                     "name": this.orgNameTFValues[i],
            //                     "abbr": this.orgAbbrTFValues[i] != null ? this.orgAbbrTFValues[i] : '',
            //                     "priority": 0
            //                 };
            //                 this.nameList.push(name);
            //                 console.log("ADDED NAME PERSON", name, this.orgNameTFValues);
            //                 name = '';
            //             }
            //         }
            //     }
            // },

            onHashtagSelect(item) {
                const index = this.tags.indexOf(item.name);
                if (index === -1) {
                    this.tags.push(item.name);
                }
            },

            onOrgTypeSelect(item) {
                console.log("<onOrgTypeSelect>", this.selectedOrgType);
                this.selectedOrgType = item;//.name;
                console.log("<onOrgTypeSelect>", this.selectedOrgType, item);
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
                let t = false, g = false, s = false;

                console.log("preliminaryDataCheck", currentStatus);

                // if (this.isObjectValidAndNotEmpty(this.locationList)) {
                //     t = this.checkConnection(this.locationList);
                // }
                // if (!t) {
                if (this.isObjectValidAndNotEmpty(this.orgList)) {
                    g = this.checkConnection(this.orgList);

                    if (!g) {
                        if (this.isObjectValidAndNotEmpty(this.personList)) {
                            s = this.checkConnection(this.personList);
                        }
                    }
                }
                // }

                if (g || s) {
                    alert("Укажите связь для сущностей, которые вы добавили");
                    // console.log("ALERT");
                } else {
                    //alert("else");
                    // if (this.editMode) {
                    //     this.updateArticle();
                    // } else {
                    this.createOrg(currentStatus);
                    // }
                }
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
                if (this.orgNameTFValues[0] != null) {
                    if (this.orgNameTFValues[0].length !== 0) {
                        this.addStatus('add-name', (!this.orgNameTFValues[0]));
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

            createOrg(currentStatus) {
                this.hasError = false;

                if (this.formValidate()) {

                    if (currentStatus.name === this.statusList[0].name) {
                        this.org.status = this.statusList[0].name;
                    } else {
                        this.org.status = this.statusList[1].name;
                    }

                    if (this.selectedFYear) {
                        this.org.foundationYear = this.selectedFYear;
                    }
                    if (this.selectedCYear) {
                        this.org.closureYear = this.selectedCYear;
                    }

                    // let name = {
                    //     "name": this.orgNameTFValues[0],
                    //     "abbr": this.orgAbbrTFValues[0] != null ? this.orgAbbrTFValues[0] : '',
                    //     "priority": 1
                    // };
                    // this.nameList.push(name);

                    this.org.linkList.splice(0);
                    this.org.hashtagList.splice(0);
                    this.org.movementList.splice(0);

                    let i = 0;
                   // console.log("+++++++++++++++++++++++++SAVE", this.checkedMovements, this.checkedMovements.length, this.org.movementList);
                    for (; i < this.checkedMovements.length; i++) {
                        this.org.movementList[i] = {
                            "id": this.checkedMovements[i]
                        };
                    }
                   // console.log("+++++++++++++++++++++++++SAVE this.org.movementList", this.org.movementList);

                    // if (!this.editMode) {
                    //     this.org.movementList[i] = {
                    //         "id": this.currentUserMovement.id
                    //     };
                    // }

                    for (let i = 0; i < this.links.length; i++) {
                        this.org.linkList[i] = {
                            "content": this.links[i]
                        };
                        // console.log("CREATE ORG link: ", this.links[i]);
                    }

                    for (let i = 0; i < this.tags.length; i++) {
                        this.org.hashtagList[i] = this.tags[i];
                    }

                    this.org.locationList.splice(0);
                    this.org.personList.splice(0);
                    this.org.orgList.splice(0);
                    this.org.isourceList.splice(0);
                    this.org.articleList.splice(0);
                    this.org.nameList.splice(0);
                    this.finalConnectionListCreation(this.locationList, this.org.locationList, true);
                    this.finalConnectionListCreation(this.orgList, this.org.orgList, false);
                    this.finalConnectionListCreation(this.personList, this.org.personList, false);
                    this.finalConnectionListCreation(this.isourceList, this.org.isourceList, false);
                    this.finalConnectionListCreation(this.articleList, this.org.articleList, true);
                    this.finalNameListCreation();

                    this.org.type = {
                        "id": this.selectedOrgType.id,
                        // "content": this.selectedMType.name,
                        // "miscellany":"123",
                        // "parentId": 1
                    };
                    console.log("ORG BEFORE  CREATION", this.org);

                    // this.org.status = {
                    //     "id": this.selectedS.id,
                    // };

                    if (this.editMode) {
                        this.org.status = this.selectedS;
                        console.log("ORG BEFORE UPDATING edit mode", this.org);
                        if (this.formValidate()) {
                            apiOrg.update(this.org.id, this.org, r => {
                                console.log(r);
                                if (this.photoWasUploaded) {
                                    apiAttachment.deletePhoto('org', r.data.id, r => {
                                    });
                                    if (this.avatar.image !== null) {
                                        apiAttachment.uploadPhoto('org', r.data.id, this.avatar.image, r => {
                                            console.log("ph was uplded");
                                        });
                                    }
                                }
                                for (let i = 0; i < this.attachedFiles.length; i++) {
                                    apiAttachment.uploadFile('org', this.org.id, this.attachedFiles[i], r => {
                                    });
                                    //todo progress bar?
                                }
                                router.push('/org');
                            });
                        }
                    } else {
                        console.log("ORG BEFORE SAVING", this.org);
                        if (this.formValidate()) {
                            apiOrg.create(this.org, r => {
                                console.log(r);

                                if (this.avatar.image !== null) {
                                    apiAttachment.uploadPhoto('org', r.data.id, this.avatar.image, r => {
                                    });
                                }

                                if (!this.uploadFilesCheckBoxValue) {
                                    router.push('/org');
                                } else {
                                    this.uploadMode = true;
                                    this.uploadFilesCheckBoxValue = false;
                                    let ID = r.data.id;
                                    this.org.id = ID;
                                    console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>stay here, ORG ID", r.data, ID);
                                }
                            });
                        }
                    }
                }
            },

            uploadFiles() {             //on button press
                for (let i = 0; i < this.attachedFiles.length; i++) {
                    apiAttachment.uploadFile('org', this.org.id, this.attachedFiles[i], r => {
                    });
                    // this.submitFile(this.attachedFiles[i]);
                    //todo progress bar?
                }
                router.push('/org');
            },

            createAttachment(files) {     //emit from FilesAttachment Component 'attachFiles'
                console.log("files from COMPONENT", files);
                this.attachedFiles = [];
                for (let i = 0; i < files.length; i++) {
                    this.attachedFiles.push(files[i]);
                }
            },

            downloadAttachment(file) {
                document.getElementById('iframeToDownload').src = '/api/v1/org/downloadAttachment?entityId=' + this.org.id + '&id=' + file.id;
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                apiAttachment.previewAttachment('org', this.org.id, file.id);
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

            finalNameListCreation() {
                // console.log("^^^^^^^^^^^^^^^finalNameListCreation^^^^^^^^^ ", list, finalList);
                // for (let i = 0; i < list.length; i++) {
                //     let a = {
                //         "name": list[i].name,
                //         "abbr": list[i].abbr,
                //         "priority": list[i].priority
                //     };
                //     finalList.push(a);
                // }

                let name = '';
                this.org.nameList.splice(0);
                for (let i = 0; i < this.orgNameTFValues.length; i++) {
                    // console.log("iiiiii", i);
                    if (this.orgNameTFValues[i] != null) {
                        //item with index = 0 pushed in createOrg()

                        if (this.orgNameTFValues[i].length > 0) {
                            name = {
                                "name": this.orgNameTFValues[i],
                                "abbr": this.orgAbbrTFValues[i] != null ? this.orgAbbrTFValues[i] : '',
                                "priority": (i === 0) ? 1 : 0
                            };
                            this.org.nameList.push(name);
                            console.log("ADDED NAME PERSON", name, this.orgNameTFValues);
                            name = '';
                        }
                    }
                }
            },

            addLocation(obj) {  //todo to unite all "addxxx"
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
                        "name": obj.content,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.locationList.push(connection);
                    console.log("ADDED", this.locationList);
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
                        "name": obj.content,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.personList.push(connection);
                    console.log("ADDED", this.personList);
                }
            },

            addOrg(obj) {
                let i = 0;
                for (i = 0; i < this.orgList.length; i++) { //to exclude double values
                    if (this.orgList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.orgList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.content,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.orgList.push(connection);
                    console.log("ADDED", this.orgList);
                }
            },
            addArticle(obj) {
                let i = 0;
                for (i = 0; i < this.articleList.length; i++) { //to exclude double values
                    if (this.articleList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.articleList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.content,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.articleList.push(connection);
                    console.log("ADDED ARTICLE", this.articleList);
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
                        // const org = entry.name;
                        //  return Object.assign({}, entry, {org})
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

            searchOrgTypeLength() {
                return this.searchOrgType.length
            },

            filteredOrgTypes() {
                let resultSearchTree = this.searchTree(this.orgTypeFlatTree, this.searchOrgType);
                return this.flatTreeToTree(resultSearchTree);
                // console.log("COMPUTED", resultSearchTree);
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

            filteredOpenOrgTypes() {
                // console.log("filteredOpenMTypes", this.filteredMTypes);

                if (this.searchOrgType != null)   //for start view without search
                    if (this.searchOrgTypeLength === 0) {
                        return this.filteredOrgTypes.map((top) => {
                            return top.name
                        })
                    } else {
                        return this.filteredOrgTypes;
                    }
            },
        },

        watch: {
            // checkedMovements() {
            //     console.log("watch this.checkedMovements.length", this.checkedMovements);
            // },

            disableColorCheckBoxFlag() {
                if (this.disableColorCheckBoxFlag === true) {
                    this.org.rowColor = null;
                }
            },

            searchOrgType() {
                this.$nextTick(() => {
                    if (this.searchOrgTypeLength === 0) {
                        this.$refs.orgtypetreeviewref.updateAll(false);
                    } else {
                        this.$refs.orgtypetreeviewref.updateAll(true);
                    }
                });
            },

            locationSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 1) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedLocation !== 'undefined') {
                            // console.log("SELECTED IN WATCH");
                            // console.log(this.selectedLocation);
                            if (this.org.locationList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
                            if (this.org.personList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
                            if (this.org.orgList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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

            articleSearch(val) {
                // console.log("SEARCH ACTIVATED");
                if (val !== null)
                    if (val.length > 2) {
                        // console.log("SEARCH STARTED");

                        if (typeof this.selectedArticle !== 'undefined') {
                            if (this.org.articleList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
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
