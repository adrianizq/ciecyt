<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.adjuntoProyectoFase.home.createOrEditLabel" v-text="$t('ciecytApp.adjuntoProyectoFase.home.createOrEditLabel')">Create or edit a AdjuntoProyectoFase</h2>
                <div>
                    <div class="form-group" v-if="adjuntoProyectoFase.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="adjuntoProyectoFase.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.nombreAdjunto')" for="adjunto-proyecto-fase-nombreAdjunto">Nombre Adjunto</label>
                        <input type="text" class="form-control" name="nombreAdjunto" id="adjunto-proyecto-fase-nombreAdjunto"
                            :class="{'valid': !$v.adjuntoProyectoFase.nombreAdjunto.$invalid, 'invalid': $v.adjuntoProyectoFase.nombreAdjunto.$invalid }" v-model="$v.adjuntoProyectoFase.nombreAdjunto.$model" />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaCreacion')" for="adjunto-proyecto-fase-fechaCreacion">Fecha Creacion</label>
                        <div class="input-group">
                            <input id="adjunto-proyecto-fase-fechaCreacion" type="date" class="form-control" name="fechaCreacion"  :class="{'valid': !$v.adjuntoProyectoFase.fechaCreacion.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaCreacion.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaCreacion.$model"  />
                        </div>
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaCreacion')" for="adjunto-proyecto-fase-fechaCreacion">Fecha Creacion</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="adjunto-proyecto-fase-fechaCreacion"
                                    v-model="$v.adjuntoProyectoFase.fechaCreacion.$model"
                                    name="fechaCreacion"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="adjunto-proyecto-fase-fechaCreacion" type="text" class="form-control" name="fechaCreacion"  :class="{'valid': !$v.adjuntoProyectoFase.fechaCreacion.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaCreacion.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaCreacion.$model"  />
                        </b-input-group>
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaModificacion')" for="adjunto-proyecto-fase-fechaModificacion">Fecha Modificacion</label>
                        <div class="input-group">
                            <input id="adjunto-proyecto-fase-fechaModificacion" type="date" class="form-control" name="fechaModificacion"  :class="{'valid': !$v.adjuntoProyectoFase.fechaModificacion.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaModificacion.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaModificacion.$model"  />
                        </div>
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaModificacion')" for="adjunto-proyecto-fase-fechaModificacion">Fecha Modificacion</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="adjunto-proyecto-fase-fechaModificacion"
                                    v-model="$v.adjuntoProyectoFase.fechaModificacion.$model"
                                    name="fechaModificacion"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="adjunto-proyecto-fase-fechaModificacion" type="text" class="form-control" name="fechaModificacion"  :class="{'valid': !$v.adjuntoProyectoFase.fechaModificacion.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaModificacion.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaModificacion.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.estadoAdjunto')" for="adjunto-proyecto-fase-estadoAdjunto">Estado Adjunto</label>
                        <input type="number" class="form-control" name="estadoAdjunto" id="adjunto-proyecto-fase-estadoAdjunto"
                            :class="{'valid': !$v.adjuntoProyectoFase.estadoAdjunto.$invalid, 'invalid': $v.adjuntoProyectoFase.estadoAdjunto.$invalid }" v-model.number="$v.adjuntoProyectoFase.estadoAdjunto.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFase')" for="adjunto-proyecto-fase-adjuntoProyectoFase">Adjunto Proyecto Fase</label>
                        <input type="text" class="form-control" name="adjuntoProyectoFase" id="adjunto-proyecto-fase-adjuntoProyectoFase"
                            :class="{'valid': !$v.adjuntoProyectoFase.adjuntoProyectoFase.$invalid, 'invalid': $v.adjuntoProyectoFase.adjuntoProyectoFase.$invalid }" v-model="$v.adjuntoProyectoFase.adjuntoProyectoFase.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.nombreArchivoOriginal')" for="adjunto-proyecto-fase-nombreArchivoOriginal">Nombre Archivo Original</label>
                        <input type="text" class="form-control" name="nombreArchivoOriginal" id="adjunto-proyecto-fase-nombreArchivoOriginal"
                            :class="{'valid': !$v.adjuntoProyectoFase.nombreArchivoOriginal.$invalid, 'invalid': $v.adjuntoProyectoFase.nombreArchivoOriginal.$invalid }" v-model="$v.adjuntoProyectoFase.nombreArchivoOriginal.$model" />
                    </div>
                    <!-------------------------DESCARGAR ----------------------->
                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')" for="adjunto-proyecto-fase-archivo">Archivo</label>
                        <div>
                            <div v-if="adjuntoProyectoFase.file" class="form-text text-danger clearfix">
                                <!--<a class="pull-left" v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.archivo)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.archivo)}}</span> -->
                                 <a class="pull-left" v-on:click="this.descargar" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{adjuntoProyectoFase.archivoContentType}}, {{adjuntoProyectoFase.file}}, {{byteSize(adjuntoProyectoFase.file)}}</span>
                                <!--de cerrar-->
                                <!--<button type="button" v-on:click="adjuntoProyectoFase.file=null;adjuntoProyectoFase.archivoContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button> -->
                            </div>
                            <!--  boton seleccionar archivo -->
                            <input type="file" ref="file_archivo" id="file_archivo" v-on:change="setFileData($event, adjuntoProyectoFase, 'archivo', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        
                        <input type="hidden" class="form-control" name="archivo" id="adjunto-proyecto-fase-archivo"
                            :class="{'valid': !$v.adjuntoProyectoFase.archivo.$invalid, 'invalid': $v.adjuntoProyectoFase.archivo.$invalid }" v-model="$v.adjuntoProyectoFase.archivo.$model" />
                        <input type="hidden" class="form-control" name="archivoContentType" id="adjunto-proyecto-fase-archivoContentType"
                            v-model="adjuntoProyectoFase.archivoContentType" />
                            
                    </div>

                    <div class="form-group">
                   <!--<button  v-on:click="this.descargar">DownLoad</button>
                   <button v-if="adjuntoProyectoFase.file!=null" @click="this.descargar" 
                   target="_blank" v-text="$t('entity.action.open')" >
                   Abrir
                   </button> -->
                   
                   
                  
                    </div> 
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaInicio')" for="adjunto-proyecto-fase-fechaInicio">Fecha Inicio</label>
                        <div class="input-group">
                            <input id="adjunto-proyecto-fase-fechaInicio" type="date" class="form-control" name="fechaInicio"  :class="{'valid': !$v.adjuntoProyectoFase.fechaInicio.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaInicio.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaInicio.$model"  />
                        </div>
                    </div>
                    -->
                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaInicio')" for="adjunto-proyecto-fase-fechaInicio">Fecha Inicio</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="adjunto-proyecto-fase-fechaInicio"
                                    v-model="$v.adjuntoProyectoFase.fechaInicio.$model"
                                    name="fechaInicio"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="adjunto-proyecto-fase-fechaInicio" type="text" class="form-control" name="fechaInicio"  :class="{'valid': !$v.adjuntoProyectoFase.fechaInicio.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaInicio.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaInicio.$model"  />
                        </b-input-group>
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaFin')" for="adjunto-proyecto-fase-fechaFin">Fecha Fin</label>
                        <div class="input-group">
                            <input id="adjunto-proyecto-fase-fechaFin" type="date" class="form-control" name="fechaFin"  :class="{'valid': !$v.adjuntoProyectoFase.fechaFin.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaFin.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaFin.$model"  />
                        </div>
                    </div>
                    -->
                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.fechaFin')" for="adjunto-proyecto-fase-fechaFin">Fecha Fin</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="adjunto-proyecto-fase-fechaFin"
                                    v-model="$v.adjuntoProyectoFase.fechaFin.$model"
                                    name="fechaFin"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="adjunto-proyecto-fase-fechaFin" type="text" class="form-control" name="fechaFin"  :class="{'valid': !$v.adjuntoProyectoFase.fechaFin.$invalid, 'invalid': $v.adjuntoProyectoFase.fechaFin.$invalid }"
                            v-model="$v.adjuntoProyectoFase.fechaFin.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFaseFase')" for="adjunto-proyecto-fase-adjuntoProyectoFaseFase">Adjunto  Fase</label>
                        <select class="form-control" id="adjunto-proyecto-fase-adjuntoProyectoFaseFase" name="adjuntoProyectoFaseFase" v-model="adjuntoProyectoFase.adjuntoProyectoFaseFaseId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="faseOption.id" v-for="faseOption in fases" :key="faseOption.id">{{faseOption.fase}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.adjuntoProyectoFase.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./adjunto-proyecto-fase-update.component.ts">
</script>
