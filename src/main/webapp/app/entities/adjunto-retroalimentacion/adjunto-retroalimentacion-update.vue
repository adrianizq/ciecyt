<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.adjuntoRetroalimentacion.home.createOrEditLabel" v-text="$t('ciecytApp.adjuntoRetroalimentacion.home.createOrEditLabel')">Create or edit a AdjuntoRetroalimentacion</h2>
                <div>
                    <div class="form-group" v-if="adjuntoRetroalimentacion.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="adjuntoRetroalimentacion.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreAdjunto')" for="adjunto-retroalimentacion-nombreAdjunto">Nombre Adjunto</label>
                        <input type="text" class="form-control" name="nombreAdjunto" id="adjunto-retroalimentacion-nombreAdjunto"
                            :class="{'valid': !$v.adjuntoRetroalimentacion.nombreAdjunto.$invalid, 'invalid': $v.adjuntoRetroalimentacion.nombreAdjunto.$invalid }" v-model="$v.adjuntoRetroalimentacion.nombreAdjunto.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaCreacion')" for="adjunto-retroalimentacion-fechaCreacion">Fecha Creacion</label>
                        <div class="input-group">
                            <input id="adjunto-retroalimentacion-fechaCreacion" type="date" class="form-control" name="fechaCreacion"  :class="{'valid': !$v.adjuntoRetroalimentacion.fechaCreacion.$invalid, 'invalid': $v.adjuntoRetroalimentacion.fechaCreacion.$invalid }"
                            v-model="$v.adjuntoRetroalimentacion.fechaCreacion.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaModificacion')" for="adjunto-retroalimentacion-fechaModificacion">Fecha Modificacion</label>
                        <div class="input-group">
                            <input id="adjunto-retroalimentacion-fechaModificacion" type="date" class="form-control" name="fechaModificacion"  :class="{'valid': !$v.adjuntoRetroalimentacion.fechaModificacion.$invalid, 'invalid': $v.adjuntoRetroalimentacion.fechaModificacion.$invalid }"
                            v-model="$v.adjuntoRetroalimentacion.fechaModificacion.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.estadoAdjunto')" for="adjunto-retroalimentacion-estadoAdjunto">Estado Adjunto</label>
                        <input type="number" class="form-control" name="estadoAdjunto" id="adjunto-retroalimentacion-estadoAdjunto"
                            :class="{'valid': !$v.adjuntoRetroalimentacion.estadoAdjunto.$invalid, 'invalid': $v.adjuntoRetroalimentacion.estadoAdjunto.$invalid }" v-model.number="$v.adjuntoRetroalimentacion.estadoAdjunto.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacion')" for="adjunto-retroalimentacion-adjuntoRetroalimentacion">Adjunto Retroalimentacion</label>
                        <input type="text" class="form-control" name="adjuntoRetroalimentacion" id="adjunto-retroalimentacion-adjuntoRetroalimentacion"
                            :class="{'valid': !$v.adjuntoRetroalimentacion.adjuntoRetroalimentacion.$invalid, 'invalid': $v.adjuntoRetroalimentacion.adjuntoRetroalimentacion.$invalid }" v-model="$v.adjuntoRetroalimentacion.adjuntoRetroalimentacion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreArchivoOriginal')" for="adjunto-retroalimentacion-nombreArchivoOriginal">Nombre Archivo Original</label>
                        <input type="text" class="form-control" name="nombreArchivoOriginal" id="adjunto-retroalimentacion-nombreArchivoOriginal"
                            :class="{'valid': !$v.adjuntoRetroalimentacion.nombreArchivoOriginal.$invalid, 'invalid': $v.adjuntoRetroalimentacion.nombreArchivoOriginal.$invalid }" v-model="$v.adjuntoRetroalimentacion.nombreArchivoOriginal.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaInicio')" for="adjunto-retroalimentacion-fechaInicio">Fecha Inicio</label>
                        <div class="input-group">
                            <input id="adjunto-retroalimentacion-fechaInicio" type="date" class="form-control" name="fechaInicio"  :class="{'valid': !$v.adjuntoRetroalimentacion.fechaInicio.$invalid, 'invalid': $v.adjuntoRetroalimentacion.fechaInicio.$invalid }"
                            v-model="$v.adjuntoRetroalimentacion.fechaInicio.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaFin')" for="adjunto-retroalimentacion-fechaFin">Fecha Fin</label>
                        <div class="input-group">
                            <input id="adjunto-retroalimentacion-fechaFin" type="date" class="form-control" name="fechaFin"  :class="{'valid': !$v.adjuntoRetroalimentacion.fechaFin.$invalid, 'invalid': $v.adjuntoRetroalimentacion.fechaFin.$invalid }"
                            v-model="$v.adjuntoRetroalimentacion.fechaFin.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacion')" for="adjunto-retroalimentacion-adjuntoRetroalimentacionRetroalimentacion">Adjunto Retroalimentacion Retroalimentacion</label>
                        <select class="form-control" id="adjunto-retroalimentacion-adjuntoRetroalimentacionRetroalimentacion" name="adjuntoRetroalimentacionRetroalimentacion" v-model="adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="retroalimentacionOption.id" v-for="retroalimentacionOption in retroalimentacions" :key="retroalimentacionOption.id">{{retroalimentacionOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.adjuntoRetroalimentacion.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./adjunto-retroalimentacion-update.component.ts">
</script>
