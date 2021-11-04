<template id="person-add">
    <v-app id="inspire">
        <div>
            <iframe id="iframeToDownload" style="display:none;"></iframe>
            <div class="form-group row">
                <div class="unprotected" v-if="errorFlag">
                    <h5>Error: {{errors}}</h5>
                </div>
            </div>
        </div>

        <!--        <form class="formCreation">-->
        <!--            <div class="form-row align-items-center" style="background-color: transparent">-->
        <!--                <div class="col-12" style="background-color: transparent">-->
        <!--                    <label>Текущее движение: {{currentUserMovement.name}} </label><br>-->
        <!--                </div>-->
        <!--                <div class="col-12" style="background-color: transparent">-->
        <!--                    <div v-if="isAdmin==='true'||addAdditionalMovementFlag">-->
        <!--                        <label>Добавить дополнительное движение:</label>-->
        <!--                        <div v-for="(movement, index) in allMovements">-->
        <!--                            <input style="margin-right: 5px;" v-bind:value="movement.id" name="movement.name"-->
        <!--                                   type="checkbox"-->
        <!--                                   v-model="checkedMovements"/>-->
        <!--                            <label :for="movement.id"><span>{{movement.name}}</span></label>-->
        <!--                        </div>-->
        <!--                    </div>-->
        <!--                    <div v-else>-->
        <!--                        <b-button size="sm" variant="info" @click="addAdditionalMovement">-->
        <!--                            Добавить дополнительное движение-->
        <!--                        </b-button>-->
        <!--                    </div>-->
        <!--                </div>-->
        <!--            </div>-->
        <!--        </form>-->

        <form class="formCreation"
              style="background-color: transparent; margin-left: 15px; margin-bottom: 0; padding-bottom: 0">
            <div class="col-md-4">
                <label for="add-name"><b>Тема*</b></label>
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

        <form class="authorsFormCreation">
            <div class="row" style="background-color: transparent">
                <div class="col-md-8">

                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-surname">Фамилия на русском*</label>
                            <input class="form-control" id="add-surname" v-model="personAddSurnameTFValues[0]"
                                   placeholder="Поле должно быть заполнено"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-name">Имя на русском*</label>
                            <input class="form-control" id="add-name" v-model="personAddNameTFValues[0]"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-patronymic">Отчество</label>
                            <input class="form-control" id="add-patronymic" v-model="personAddPatrTFValues[0]">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-3">
                            <label>Год рождения</label>
                            <b-form-select v-model="selectedBYear" class="mb-3" id="birth-year-selection">
                                <option v-for="year in years">{{year}}</option>
                            </b-form-select>
                        </div>

                        <div class="col-md-3">
                            <label>Год смерти</label>
                            <b-form-select v-model="selectedDYear" class="mb-3" id="death-year-selection">
                                <option v-for="year in years">{{year}}</option>
                            </b-form-select>
                        </div>
                    </div>
                    <b-button size="sm" variant="info" v-b-modal="'modalNames'">
                        Добавить фамилии на других языках
                    </b-button>

                    <div v-if="personAddSurnameTFValues[1]!=null" style="margin-top: 20px">
                        <label for="add-name">Дополнительные фио:
                            <span class='notbold'><br>
                                {{personAddSurnameTFValues[1]}} {{personAddNameTFValues[1]}} {{personAddPatrTFValues[1]}} </span>
                            <span v-if="personAddSurnameTFValues[2]!=null" class='notbold'><br>
                                {{personAddSurnameTFValues[2]}} {{personAddNameTFValues[2]}} {{personAddPatrTFValues[2]}} </span>
                            <span v-if="personAddSurnameTFValues[3]!=null" class='notbold'><br>
                                {{personAddSurnameTFValues[3]}} {{personAddNameTFValues[3]}} {{personAddPatrTFValues[3]}} </span>
                            <span v-if="personAddSurnameTFValues[4]!=null" class='notbold'><br>
                                {{personAddSurnameTFValues[4]}} {{personAddNameTFValues[4]}} {{personAddPatrTFValues[4]}} </span>
                            <span v-if="personAddSurnameTFValues[5]!=null" class='notbold'><br>
                                {{personAddSurnameTFValues[5]}} {{personAddNameTFValues[5]}} {{personAddPatrTFValues[5]}} </span>

                        </label>
                        <!--                            <input class="form-control" v-model="orgNameTFValues[1]" disabled/>-->
                    </div>

                </div>


                <div class="col-md-2" style="margin-top: 10px; background-color: transparent">
                    <div id="preview">
                        <div v-if="avatar.imageBase64">
                            <img :src="avatar.imageBase64" @load="setHeight"
                                 :style="{ height: imageHeight + 'px' }"/>
                        </div>
                        <!--                        <div v-else>-->
                        <!--                            <img v-if="person.photo" v-bind:src="'data:image/jpeg;base64,'+person.photo"-->
                        <!--                                 :style="{ width: 250+'px' }"/>-->
                        <!--                        </div>-->
                    </div>
                    <!--                    <div v-if="avatar.imageBase64" style="margin-top: 5px">-->
                    <!--                        <input type="file" accept="image/*" @change="onChange"/>-->
                    <!--                    </div>-->
                    <div style="margin-top: 30px">
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
                                item-text="content"
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


        <form class="authorsFormCreation"
              style="background-color: transparent; padding: 25px 0 5px">
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
        </form>

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
                        <option v-for="status in statusList">
                            {{status.name}}
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
                                 @removeAttachment="removeAttachment"
                                 :userName="loggedName"
                                 :already-uploaded-files="uploadedFiles"
                                 :is-details-mode="false"/>
            </div>
            <div v-if="editMode" class="form-group row align-items-center">
                <div class="offset-sm-4 col-sm-3">

                    <button type="button" @click="preliminaryDataCheck(0)" class="btn btn-primary">Обновить</button>
                    <a class="btn btn-default">
                        <router-link to="/person">Отмена</router-link>
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
                        <router-link to="/article" style="color: white">Отмена</router-link>
                    </button>
                </div>

                <div v-else class="offset-sm-4 col-sm-3">
                    <button type="button" style="margin-right: 20px" @click="uploadFiles"
                            class="btn btn-info">Upload Files
                    </button>
                    <button type="button" class="btn btn-info">
                        <router-link to="/person" style="color: white">Cancel</router-link>

                    </button>
                </div>
            </div>
        </form>

        <b-modal ref="modalNames" id="modalNames" size="lg" :hide-footer="true" :hide-header="true">
            <template>
                <b><p class="myHeader">Добавить имя, фамилию на других языках</p><br></b>
                <div align="center">
                    <div class="form-row">
                        <div class="col-md-4">
                            <label for="add-surname"><b>Фамилия*</b></label>
                            <input class="form-control" id="surnameTF1" v-model="personAddSurnameTFValues[1]"
                                   placeholder="Обязательное поле"/>
                        </div>

                        <div class="col-md-4">
                            <label for="add-name">Имя</label>
                            <input class="form-control" id="nameTF1" v-model="personAddNameTFValues[1]"/>
                        </div>

                        <div class="form-row col-md-4">
                            <div class="col-md-11" style="background-color: transparent; padding: 0px 15px 0px 5px">
                                <label for="add-patronymic">Отчество</label>
                                <input class="form-control" id="patrTF1" v-model="personAddPatrTFValues[1]">
                            </div>
                            <div class="col-md-1" style="background-color: transparent; padding-top: 35px">
                                <span class="close"
                                      @click="clearFields(1)">&times;</span>
                            </div>
                        </div>

                    </div>


                    <div class="form-row">
                        <div class="col-md-4">
                            <input class="form-control" id="surnameTF2" v-model="personAddSurnameTFValues[2]"
                                   placeholder="Обязательное поле"/>
                        </div>
                        <div class="col-md-4">
                            <input class="form-control" id="nameTF2" v-model="personAddNameTFValues[2]"/>
                        </div>
                        <div class="form-row col-md-4">
                            <div class="col-md-11" style="background-color: transparent; padding: 0px 15px 0px 5px">
                                <input class="form-control" id="patrTF2" v-model="personAddPatrTFValues[2]">
                            </div>
                            <div class="col-md-1" style="background-color: transparent; padding-top: 5px">
                                <span class="close"
                                      @click="clearFields(2)">&times;</span>
                            </div>
                        </div>
                    </div>


                    <div class="form-row">
                        <div class="col-md-4">
                            <input class="form-control" id="surnameTF3" v-model="personAddSurnameTFValues[3]"
                                   placeholder="Обязательное поле"/>
                        </div>
                        <div class="col-md-4">
                            <input class="form-control" id="nameTF3" v-model="personAddNameTFValues[3]"/>
                        </div>
                        <div class="form-row col-md-4">
                            <div class="col-md-11" style="background-color: transparent; padding: 0px 15px 0px 5px">
                                <input class="form-control" id="patrTF3" v-model="personAddPatrTFValues[3]">
                            </div>
                            <div class="col-md-1" style="background-color: transparent; padding-top: 5px">
                                <span class="close"
                                      @click="clearFields(3)">&times;</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-4">
                            <input class="form-control" id="surnameTF4" v-model="personAddSurnameTFValues[4]"
                                   placeholder="Обязательное поле"/>
                        </div>
                        <div class="col-md-4">
                            <input class="form-control" id="nameTF4" v-model="personAddNameTFValues[4]"/>
                        </div>
                        <div class="form-row col-md-4">
                            <div class="col-md-11" style="background-color: transparent; padding: 0px 15px 0px 5px">
                                <input class="form-control" id="patrTF4" v-model="personAddPatrTFValues[4]">
                            </div>
                            <div class="col-md-1" style="background-color: transparent; padding-top: 5px">
                                <span class="close"
                                      @click="clearFields(4)">&times;</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-4">
                            <input class="form-control" id="surnameTF5" v-model="personAddSurnameTFValues[5]"
                                   placeholder="Обязательное поле"/>
                        </div>
                        <div class="col-md-4">
                            <input class="form-control" id="nameTF5" v-model="personAddNameTFValues[5]"/>
                        </div>
                        <div class="form-row col-md-4">
                            <div class="col-md-11" style="background-color: transparent; padding: 0px 15px 0px 5px">
                                <input class="form-control" id="patrTF5" v-model="personAddPatrTFValues[5]">
                            </div>
                            <div class="col-md-1" style="background-color: transparent; padding-top: 5px">
                                <span class="close"
                                      @click="clearFields(5)">&times;</span>
                            </div>
                        </div>
                    </div>

                    <b-button style="margin-right: 4px" size="sm" variant="success" @click="hideModalView()">
                        OK
                    </b-button>
                    <!--                    <b-button size="sm" variant="info" @click="cancelModalNamesView()">-->
                    <!--                        Отмена-->
                    <!--                    </b-button>-->

                    <div style="color: gray; font-size: 12px; font-weight: normal; margin-top: 20px">* - Обязательное
                        поле. Если хотя бы одно из обязательных полей не будет заполнено,
                        то весь набор <br><b> Фамилия-Имя-Отчество </b> сохранен не будет
                    </div>
                </div>
            </template>
        </b-modal>

    </v-app>
</template>

<style lang="scss">
    @import '../dbnm.css';
</style>

<script>
    import api from "./person-api";
    import apiOrg from "./../org/org-api";
    import apiIsource from "./../isource/isource-api";
    import apiCountry from "./../country/country-api";

    import router from "./../../router";
    import Vuetify from 'vuetify';
    // import OccupationList from "../components/person-occupation/OccupationList";
    import apiHashtag from "./../hashtag/hashtag-api";
    import apiMovement from "./../movement/movement-api";
    import apiLanguage from "./../language/language-api";
    import apiStatus from "./../status-api";


    import CKEditor from 'ckeditor4-vue';
    import ConnectionComponent from "../components/connection/ConnectionComponent";
    import FileAttachment from "../components/FileAttachment";
    import apiAttachment from "./../attachment-api";
    import VSwatches from 'vue-swatches'        // https://saintplay.github.io/vue-swatches/examples/#simple
    import apiLogin from "../login-api";

    export default {
        components: {
            // OccupationList,
            ConnectionComponent,
            ckeditor: CKEditor.component, // to use the component locally
            FileAttachment,
            VSwatches
        },
        name: 'person-add',
        vuetify: new Vuetify(),

        data: () => ({
            test: "personProp",
            linkAddTagOnKeys: [13, 9],
            hashAddTagOnKeys: [],
            descriptionLimit: 60,
            entries: [],

            isLoadingLocation: false,
            isLoadingOrg: false,
            isLoadingPerson: false,
            isLoadingIsource: false,
            // isLoadingEvent: false,

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
            selectedL: null,
            selectedLocation: [],
            selectedOrg: [],
            selectedPerson: [],
            selectedIsource: [],
            // selectedEvent: [],
            selectedM: null,
            selectedBYear: null,
            selectedDYear: null,

            tags: [],
            links: [],

            errorFlag: false,
            errors: [],
            validationErrors: {},
            hasError: false,

            locationEntries: [],
            orgEntries: [],
            personEntries: [],
            isourceEntries: [],
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
            orgList: [],

            statusList: [],
            personAddSurnameTFValues: [],
            personAddNameTFValues: [],
            personAddPatrTFValues: [],

            personList: [],
            personSearch: null,

            isourceSearch: null,
            isourceList: [],

            // eventSearch: null,
            // eventList: [],

            personLocationIds: [], //before request
            personLocationEntities: [], //after request
            personOrgIds: [], //before request
            personOrgEntities: [], //after request
            personPersonIds: [], //before request
            personPersonEntities: [], //after request
            personIsourceIds: [], //before request
            personIsourceEntities: [], //after request
            // personEventIds: [], //before request
            // personEventEntities: [], //after request

            orgList: [],
            person: {
                hashtagList: [],
                linkList: [],
                orgList: [],
                locationList: [],
                personList: [],
                isourceList: [],
                // eventList: [],
                movementList: [],
                snpList: []
            },
            years: [],
            photoWasUploaded: false,
            originalPhoto: null,
            editMode: false,
            uploadMode: false,
            uploadFilesCheckBoxValue: false,
            loggedName: '',
            uploadedFiles: [],
            attachedFiles: [],
            isAdmin: 'false',
            addAdditionalMovementFlag: false,
            allMovements: [],
            allLanguages: [],
            //currentUserMovement: '',
            checkedMovements: [],

            editor: CKEditor, // to use the component locally
            editorConfig: {
                // The configuration of the editor.
            }
        }),

        methods: {

            clearFields(index) {

                console.log("index", index);
                console.log("document.getElementById(\"surnameTF\" + index)", document.getElementById("surnameTF" + index));
                console.log("document.getElementById(\"nameTF\" + index)", document.getElementById("nameTF" + index));
                console.log("document.getElementById(\"patrTF\" + index)", document.getElementById("abbsurnameTF" + index));


                document.getElementById("surnameTF" + index).value = '';
                document.getElementById("nameTF" + index).value = '';
                document.getElementById("patrTF" + index).value = '';

                // console.log("this.personAddPatrTFValues", this.personAddPatrTFValues, this.personAddPatrTFValues[index]);

                this.personAddSurnameTFValues.splice(index, 1);// 5-index);
                // if (this.personAddNameTFValues[index] !== null)
                this.personAddNameTFValues.splice(index, 1);//5-index);
                // if (this.personAddPatrTFValues[index] !== null)
                this.personAddPatrTFValues.splice(index, 1);//5-index);

                // this.personAddSurnameTFValues[index] = '';
                // this.personAddNameTFValues[index] = '';
                // this.personAddPatrTFValues[index] = '';

                console.log(this.personAddSurnameTFValues, this.personAddNameTFValues, this.personAddPatrTFValues);
            },

            hideModalView() {
                console.log("SAVE ADD NAMES", this.personAddSurnameTFValues);
                console.log(this.personAddNameTFValues);
                console.log(this.personAddPatrTFValues);
                // this.addAdditionalNameToList();
                this.$refs.modalNames.hide();
            },

            // cancelModalNamesView() {
            //    // console.log("22222222222", this.personAddSurnameTFValues);
            //     if (!this.isAdditionalNamesAlreadyExist()) {
            //         // if (this.personAddSurnameTFValues.length > 0){
            //         //
            //         // } else {
            //         for (let i = 0; i < 5; i++) {
            //             document.getElementById("idsrnm" + i).value = '';
            //             document.getElementById("idnm" + i).value = '';
            //             document.getElementById("idptrnmc" + i).value = '';
            //         }
            //
            //         this.preSurname.splice(0);
            //         this.preName.splice(0);
            //         this.prePatr.splice(0);
            //     }
            //
            //     this.$refs.modalNames.hide();
            // },

            addAdditionalMovement() {
                this.addAdditionalMovementFlag = true;
            },

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

            removeAttachment(file) {
                console.log("removeAtt PERSON", file);
                apiAttachment.removeAttachment('person', this.person.id, file.id, file.name, r => {
                    console.log("result", r.data);
                    if (r.data === true) {

                        // this.uploadedFiles.find(x => x.priority === 0);

                        const index = this.uploadedFiles.indexOf(file);
                        if (index > -1) {
                            this.uploadedFiles.splice(index, 1);
                        }
                    } else {
                        alert("file deletion error");
                    }
                });
            },

            getAttachment(file) {     //emit from FilesAttachment Component 'getAttachment'
                apiAttachment.previewAttachment('person', this.person.id, file.id);
            },


            getLoggedIn() {
                this.loggedName = this.$store.getters.getUserName;  //todo change to author, not current user
                this.isAdmin = localStorage.getItem('isAdmin');
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

            // uploadImage(e) {
            //     console.log(e);
            //
            //     const image = e.target.files[0];
            //     const reader = new FileReader();
            //     reader.readAsDataURL(image);
            //     reader.onload = e => {
            //         this.previewImage = e.target.result;
            //         // console.log(this.previewImage);
            //     };
            // },

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
                        "name": obj.content,
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
                        "name": obj.content,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.personList.push(connection);
                    console.log("ADDED PERSON");
                }
            },

            addIsource(obj) {
                console.log("GET CHANGED ISOURCE", obj);
                for (let i = 0; i < this.isourceList.length; i++) { //to exclude double values
                    if (this.isourceList[i].id === obj.id) {
                        break;
                    }
                }

                if (i === this.isourceList.length) {
                    let connection = {
                        "id": obj.id,
                        "name": obj.title,
                        "comment": '',
                        "connection": '',
                        "hasClicked": false
                    };
                    this.isourceList.push(connection);
                    console.log("ADDED ISOURCE");
                }
            },

            // addEvent(obj) {
            //     console.log("GET CHANGED EVENT", obj);
            //     for (let i = 0; i < this.eventList.length; i++) { //to exclude double values
            //         if (this.eventList[i].id === obj.id) {
            //             break;
            //         }
            //     }
            //
            //     if (i === this.eventList.length) {
            //         let connection = {
            //             "id": obj.id,
            //             "name": obj.title,
            //             "comment": '',
            //             "connection": '',
            //             "hasClicked": false
            //         };
            //         this.eventList.push(connection);
            //         console.log("ADDED EVENT");
            //     }
            // },

            // addAdditionalNameToList() {
            //     let connection = '';
            //     this.nameList.splice(0);
            //
            //     for (let i = 1; i < this.personAddSurnameTFValues.length; i++) {
            //         if (this.personAddSurnameTFValues[i] != null) {
            //             console.log("personAddSurnameTFValues, add to nameList", i);
            //
            //             // if (i > 0) {  //russian priority obligatory value
            //             //     connection = {
            //             //         "surname": this.personAddSurnameTFValues[i],
            //             //         "name": this.personAddNameTFValues[i],
            //             //         "patronymic": this.personAddPatrTFValues[i] != null ? this.personAddPatrTFValues[i] : '',
            //             //         "priority": 1
            //             //     };
            //             //
            //             //     this.nameList.push(connection);
            //             //     console.log("ADDED NAME PERSON", connection);
            //             //     connection = '';
            //             //
            //             // } else {
            //             //
            //             //     if (this.personAddSurnameTFValues[i] != null) {
            //
            //             //item with index = 0 pushed in createPerson()
            //
            //             if (this.personAddSurnameTFValues[i].length > 0) {  //to exclude empty strings
            //
            //                 connection = {
            //                     "surname": this.personAddSurnameTFValues[i],
            //                     "name": this.personAddNameTFValues[i] != null ? this.personAddNameTFValues[i] : '',
            //                     "patronymic": this.personAddPatrTFValues[i] != null ? this.personAddPatrTFValues[i] : '',
            //                     "priority": 0
            //                 };
            //
            //                 this.nameList.push(connection);
            //                 console.log("ADDED NAME PERSON", connection);
            //                 connection = '';
            //             }
            //         }
            //         //  }
            //         //        }
            //     }
            // },

            // isAdditionalNamesAlreadyExist() {
            //     //????cannot use nameList before person saving, then needs to add/delete nameList elements every time
            //     //preSurname -> personAddSurnameTFValues -> nameList
            //
            //     let res = this.nameList.find(x => x.priority === 0);
            //
            //     let count = 0;
            //     for (let i = 0; i < this.nameList.length; i++) {
            //         if (this.nameList[i].priority === 0)
            //             count++;
            //     }
            //     console.log("IS EXIST", res, count, this.nameList);
            //
            //     if (res)
            //         return true;
            //
            //     return false;
            // },

            // finalPositionListCreation(list, finalList) {
            //     // console.log("^^^^^^^^^^^^^^^finalConnectionListCreation^^^^^^^^^ ", list, finalList);
            //     for (let i = 0; i < list.length; i++) {
            //         let a = {
            //             "orgId": list[i].id,
            //             "position": list[i].connection,
            //             "comment": list[i].comment
            //         };
            //         // console.log("CREATE PERS ON A: ", a);
            //
            //         if (a.position.length > 0) { //to avoid add empty connections (wasn't entered)
            //             // console.log("PUSH PERS ON A: ", a);
            //             finalList.push(a);
            //         }
            //         // console.log("CREATE PERS ON A: ", this.article.personList);
            //     }
            // },

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

            finalNameListCreation() {
                // console.log("^^^^^^^^^^^^^^^finalNameListCreation^^^^^^^^^ ", list, finalList);
                // for (let i = 0; i < list.length; i++) {
                //     let a = {
                //         "surname": list[i].surname,
                //         "name": list[i].name,
                //         "patronymic": list[i].patronymic,
                //         "priority": list[i].priority
                //     };
                //     finalList.push(a);
                // }


                let connection = '';
                this.person.snpList.splice(0);
                for (let i = 0; i < this.personAddSurnameTFValues.length; i++) {
                    if (this.personAddSurnameTFValues[i] != null) {
                        console.log("personAddSurnameTFValues, add to person.snpList", i);

                        if (this.personAddSurnameTFValues[i].length > 0) {  //to exclude empty strings
                            connection = {
                                "surname": this.personAddSurnameTFValues[i],
                                "name": this.personAddNameTFValues[i] != null ? this.personAddNameTFValues[i] : '',
                                "patronymic": this.personAddPatrTFValues[i] != null ? this.personAddPatrTFValues[i] : '',
                                "priority": (i === 0) ? 1 : 0
                            };

                            this.person.snpList.push(connection);
                            console.log("ADDED NAME PERSON", connection);
                            connection = '';
                        }
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
                this.addStatus('add-surname', (!this.personAddSurnameTFValues[0]));
                if (this.hasError) {
                } else {
                    this.addStatus('add-name', (!this.personAddNameTFValues[0]));

                    if (this.checkedMovements.length === 0) {
                        this.hasError = true;
                    }
                }

                if (this.hasError) {
                    alert("Пожалуйста, заполните все обязательные поля");
                    console.log('ERROROROR----------------------------');
                }
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
                    if (this.isObjectValidAndNotEmpty(this.orgList)) {
                        g = this.checkConnection(this.orgList);

                        if (!g) {
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
                // this.person.language = {
                //     "id": this.selectedL
                // };

                if (this.selectedBYear) {
                    this.person.birthYear = this.selectedBYear;
                }

                if (this.selectedDYear) {
                    this.person.deathYear = this.selectedDYear;
                }

                if (currentStatus === this.statusList[0]) {
                    this.person.status = this.statusList[0].name;
                } else {
                    this.person.status = this.statusList[1].name;
                }

                // if (this.selectedCountry) {  //otherwise without this check Country entity is created with null fields values and Person can't be saved
                //     this.person.location_id = this.selectedCountry;
                // }
                this.person.linkList.splice(0);
                this.person.hashtagList.splice(0);
                this.person.movementList.splice(0);

                for (let i = 0; i < this.links.length; i++) {
                    this.person.linkList[i] = {
                        "content": this.links[i]
                    };
                    console.log("CREATE PERS link: ", this.links[i]);
                }

                for (let i = 0; i < this.tags.length; i++) {
                    this.person.hashtagList[i] = this.tags[i];
                }


                let i = 0;
                for (; i < this.checkedMovements.length; i++) {
                    this.person.movementList[i] = {
                        "id": this.checkedMovements[i]
                    };
                }

                // if (!this.editMode) {
                //     this.person.movementList[i] = {
                //         "id": this.currentUserMovement.id
                //     };
                // }

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

                // let connection = {
                //     "surname": this.personAddSurnameTFValues[0],
                //     "name": this.personAddNameTFValues[0],
                //     "patronymic": this.personAddPatrTFValues[0] != null ? this.personAddPatrTFValues[0] : '',
                //     "priority": 1
                // };
                //
                // this.nameList.push(connection);
                // console.log("ADDED NAME PERSON", connection);

                this.person.locationList.splice(0);
                this.person.personList.splice(0);
                this.person.orgList.splice(0);
                this.person.isourceList.splice(0);
                // this.person.eventList.splice(0);
                this.person.snpList.splice(0);
                this.finalConnectionListCreation(this.locationList, this.person.locationList);
                this.finalConnectionListCreation(this.orgList, this.person.orgList);
                this.finalConnectionListCreation(this.personList, this.person.personList);
                this.finalConnectionListCreation(this.isourceList, this.person.isourceList);
                // this.finalConnectionListCreation(this.eventList, this.person.eventList);
                this.finalNameListCreation();

                // this.person.photo = this.avatar.imageBase64;


                if (this.editMode) {
                    this.person.status = this.selectedS;
                    console.log("PERSON BEFORE UPDATING", this.person);
                    if (this.formValidate()) {
                        api.update(this.person.id, this.person, r => {
                            console.log(r);
                            if (this.photoWasUploaded) {
                                apiAttachment.deletePhoto('person', r.data.id, r => {
                                });
                                apiAttachment.uploadPhoto('person', r.data.id, this.avatar.image, r => {
                                    console.log("ph was uplded");
                                });
                            }
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

                            apiAttachment.uploadPhoto('person', r.data.id, this.avatar.image, r => {
                            });

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

            //todo
            orgEditConnectionTitleCreation(org) {
                let returnedTitle = org.content;

                // if (this.isArrayValidAndNotEmpty(org.abbrRus)) {
                //     returnedTitle += ", " + org.abbrRus;
                // }
                // if (this.isArrayValidAndNotEmpty(org.name)) {
                //     returnedTitle += ", " + org.name;
                // }
                // //todo add fields

                return returnedTitle;
            },

            countryTitleCreation(country) {
                let returnedTitle = country.content;

                // if (this.isArrayValidAndNotEmpty(country.region)) {
                //     returnedTitle += ", " + country.region;
                // }
                // if (this.isArrayValidAndNotEmpty(country.city)) {
                //     returnedTitle += ", " + country.city;
                // }
                // if (this.isArrayValidAndNotEmpty(country.address)) {
                //     returnedTitle += ", " + country.address;
                // }
                //placement?

                return returnedTitle;
            },

            personNameCreation(person) {
                let personTitle = person.snpList[0].surname;
                personTitle += " " + person.snpList[0].name;

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

            // getPrioritySNP(currentPerson) {
            //     let sss = 0;// = this.person.snpList.find(x => x.priority === 1).surname;
            //
            //     for (let i = 0; i < currentPerson.snpList.length; i++) {
            //         console.log("SNPLIST i", currentPerson.snpList[i], i, currentPerson.snpList[i].priority);
            //
            //         if (currentPerson.snpList[i].priority === 1) {
            //             sss = currentPerson.snpList[i].surname + " " + currentPerson.snpList[i].name + " ";
            //
            //             if (currentPerson.snpList[i].patronymic != null)
            //                 sss += currentPerson.snpList[i].patronymic;
            //             console.log("******^^^^^^^^^^^^^****", sss);
            //         }
            //     }
            //     // console.log("*************************", sss);
            //     return sss;
            // },

            getSNPWithPriority(priority) {
                // let sss = '';// = this.person.snpList.find(x => x.priority === 1).surname;
                let currEl, j = 1;

                for (let i = 0; i < this.person.snpList.length; i++) {
                    //console.log("SNPLIST i", this.person.snpList[i], i, this.person.snpList[i].priority);
                    currEl = this.person.snpList[i];
                    if (currEl.priority === priority) {
                        console.log("SNP priority", priority, currEl.surname);

                        if (priority === 1) {
                            this.personAddSurnameTFValues[0] = currEl.surname;
                            this.personAddNameTFValues[0] = currEl.name;
                            if (this.person.snpList[i].patronymic != null)
                                this.personAddPatrTFValues[0] = currEl.patronymic;
                        } else {
                            this.personAddSurnameTFValues[j] = currEl.surname;
                            this.personAddNameTFValues[j] = currEl.name;
                            if (this.person.snpList[i].patronymic != null)
                                this.personAddPatrTFValues[j] = currEl.patronymic;
                            j++;
                        }
                    }
                }
                // return sss;
            },
        },

        mounted() {
            // console.log("MOUNTED");
            this.getLoggedIn();

            // document.addEventListener('keydown', function(event){
            //     if(event.key === "Escape"){
            //         //do something
            //
            //         console.log("E*S*C*A*P*E", this.preSurname);
            //         this.cancelModalNamesView();
            //
            //         // for (let i = 0; i < 5; i++){
            //         //     // this.preSurname[i] = '';
            //         //     // this.preName[i] = '';
            //         //     // this.prePatr[i] = '';
            //         //
            //         //     document.getElementById("idsrnm" + i).value = '';
            //         //     document.getElementById("idnm" + i).value = '';
            //         //     document.getElementById("idptrnmc" + i).value = '';
            //         //}
            //
            //         //this.$refs.modalNames.hide();
            //     }
            // });


            apiStatus.getAllStatuses(response => {
                this.statusList = response.data;
                console.log("STATUS LIST", this.statusList);
            });

            // apiMovement.getAllMovements(response => {
            //     // this.getLoggedIn();
            //     this.allMovements = response.data;
            //     // console.log("MOVEMENTS", response.data);
            //     this.currentUserMovement = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));
            //
            //     let currentIndex = this.allMovements.find(x => x.id === Number.parseInt(localStorage.getItem('movement')));
            //     let ddd = this.allMovements.indexOf(currentIndex);
            //     this.allMovements.splice(ddd, 1);
            //     // console.log("MOVEMENTS index", response.data, currentIndex, ddd);
            //
            //     apiLanguage.getAllLanguages(response => {
            //         this.allLanguages = response.data;
            //         console.log(response.data)
            //     });
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

            if (this.$route.params.person_id != null) {
                // console.log("-------------------EDIT MODE");
                this.editMode = true;
                this.uploadFilesCheckBoxValue = true;
            }

            this.initYears();

            if (this.editMode) {////////////////////////////////EDIT MODE//////////////////////////////////////
                api.findById(this.$route.params.person_id, r => {
                    this.person = r.data;
                    console.log("CURRENT PERSON FOUND BY ID", this.person);

                    this.getSNPWithPriority(1);
                    this.getSNPWithPriority(0);

                    this.selectedBYear = this.person.birthYear;
                    this.selectedDYear = this.person.deathYear;
                    this.selectedS = this.person.status;

                    for (let i = 0; i < this.person.hashtagList.length; i++) {
                        this.tags.push(this.person.hashtagList[i]);
                    }

                    for (let i = 0; i < this.person.linkList.length; i++) {
                        this.links.push(this.person.linkList[i].content);
                    }

                    for (let i = 0; i < this.person.movementList.length; i++) {
                        this.checkedMovements.push(this.person.movementList[i].id);
                    }

                    for (let j = 0; j < this.person.locationList.length; j++) {
                        this.personLocationIds.push(this.person.locationList[j].itemId);
                    }

                    for (let j = 0; j < this.person.personList.length; j++) {
                        this.personPersonIds.push(this.person.personList[j].itemId);
                    }

                    for (let j = 0; j < this.person.orgList.length; j++) {
                        this.personOrgIds.push(this.person.orgList[j].itemId);
                    }

                    // for (let j = 0; j < this.person.isourceList.length; j++) {  //todo
                    //     this.personIsourceIds.push(this.person.isourceList[j].itemId);
                    // }

                    apiAttachment.getAttachments('person', this.person.id, r => {
                        for (let i = 0; i < r.data.length; i++) {
                            this.uploadedFiles.push(r.data[i]);
                        }
                    });

                    apiCountry.getLocationsByIds(this.personLocationIds, response => {  ///returns List<Location>
                        this.personLocationEntities = response.data;
                        console.log("getLocationsBy#Entities locaList", this.personLocationEntities, this.person.locationList);

                        for (let i = 0; i < this.person.locationList.length; i++) {
                            let element = this.person.locationList[i];
                            let currentLocationEntity = this.personLocationEntities.find(l => l.id === element.itemId);
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

                    api.getPersonsByIdsAndSymmetrically(this.person.id, this.personPersonIds, response => {
                        this.personPersonEntities = response.data;

                        for (let i = 0; i < this.personPersonEntities.length; i++) {
                            let connection = {
                                "id": this.personPersonEntities[i].itemId,
                                "name": this.personPersonEntities[i].name, //this.personNameCreation(currentPersonEntity),
                                "connection": this.personPersonEntities[i].connection,
                                "comment": this.personPersonEntities[i].comment,
                                "hasClicked": true
                            };

                            // console.log("CREATE PERS ON A: ", a);
                            this.personList.push(connection);
                        }
                    });

                    apiOrg.getOrgsByIds(this.personOrgIds, response => {
                        this.personOrgEntities = response.data;   //returns List<Org>
                        console.log("apiOrg", this.personOrgEntities);

                        for (let i = 0; i < this.person.orgList.length; i++) {
                            let element = this.person.orgList[i];
                            let currentOrgEntity = this.personOrgEntities.find(org => org.id === element.itemId);
                            console.log("currentOrgEntity", currentOrgEntity);
                            let connection = {
                                "id": element.itemId,
                                "name": currentOrgEntity.content, //this.orgEditConnectionTitleCreation(currentOrgEntity),
                                "connection": element.connection,
                                "comment": element.comment,
                                "hasClicked": true
                            };
                            this.orgList.push(connection);
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

                    apiAttachment.getAttachmentPhoto('person', this.person.id, r => {
                        console.log("R DATA", r);
                        this.avatar.imageBase64 = "data:image/jpeg;base64," + r.data;//.content;

                        // let buffer = (new Buffer(r.data.toString()));


                        //console.log(buffer.toString("base64"));
                        // console.log("avatar.imageBase64", this.avatar.imageBase64, buffer);

                        // this.originalPhoto = this.avatar.imageBase64;


                        // console.log("BYTE []", r.data.blobContent.binaryStream);


                        // var buffer = new ArrayBuffer(r.data.content);
                        // console.log("buffer", buffer);

                        // let arrayBufferView = new Uint8Array( r.data.blobContent );
                        // console.log("arrBuffer", arrayBufferView);
                        //


                        let blob = new Blob([r.data], {type: 'image/jpeg'});
                        console.log("BLOB", blob);


                        // var array = new Uint8Array([0x04, 0x06, 0x07, 0x08]);
                        // var blob = new Blob([array]);


                        ////// let bytes = new Uint8Array(r.data.content.length);
                        //let bytes = new Uint8Array(r.data.content, r.data.content.length);

//                        // for (let i = 0; i < bytes.length; i++) {
//                        //     bytes[i] = r.data.content[i];
//                        // }


                        // console.log("Uint8Array", bytes);
                        // let blob = new Blob([r.data.content], { type: 'image/jpeg' });
                        //
                        // console.log("BLOB", blob);
                        //
                        //  const reader = new FileReader();
                        //  reader.readAsDataURL(blob);
                        //  reader.onload = e => {
                        //      this.avatar.imageBase64 = e.target.result;
                        //     // console.log("image55555555555555555Base64", this.avatar.imageBase64);
                        //  };
                    });

                    //this.avatar.imageBase64 = this.person.photo;
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
                    const surname = entry.content;
                    return Object.assign({}, entry, {surname})
                })
            },

            isourceItems() {
                return this.isourceEntries.map(entry => {
                    const surname = entry.content;
                    return Object.assign({}, entry, {title})
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

                    return this.orgEntries.map(entry => {
                        return Object.assign({}, entry)
                        //     .map(entry => {
                        //     const id = entry.id;
                        //     const org = entry.name;
                        //     return Object.assign({}, {id}, {org})
                        // })
                    })
                }
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
                    if (val.length > 1) {
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
                            if (this.person.orgList.length > 1)   //todo костылик) иначе удаляет впервые набранную строку поиска
                                this.selectedOrg = "";
                        }

                        // Items have already been requested
                        if (this.isLoadingOrg) return;
                        this.isLoadingOrg = true;

                        //console.log("seracg org", val);

                        apiOrg.searchOrg(val, r => {
                            this.orgEntries = r;  //returns IdContentDto (id, name(connected from different Org fields in OrgServImpl))
                            //   console.log("****", this.orgEntries);
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

            isourceSearch(val) {
                if (val !== null)
                    if (val.length > 2) {
                        if (typeof this.selectedIsource !== 'undefined') {
                            if (this.person.isourceList.length > 1)
                                this.selectedIsource = "";
                        }
                        // Items have already been requested
                        if (this.isLoadingIsource) return;
                        this.isLoadingIsource = true;

                        //todo
                        // apiIsource.searchIsource(val, localStorage.getItem('movement'), r => {
                        //     this.isourceEntries = r;
                        //     console.log("***ПОИСК ******", this.isourceEntries);
                        //     this.isLoadingIsource = false;
                        // });
                    }
            },
        },
    }
</script>
