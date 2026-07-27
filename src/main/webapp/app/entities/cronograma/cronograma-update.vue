<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.cronograma.home.createOrEditLabel" v-text="$t('ciecytApp.cronograma.home.createOrEditLabel')">Create or edit a Cronograma</h2>
                <div>
                    <div class="form-group" v-if="cronograma.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cronograma.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronograma.actividad')" for="cronograma-actividad">Actividad</label>
                        <input type="text" class="form-control" name="actividad" id="cronograma-actividad"
                            :class="{'valid': !$v.cronograma.actividad.$invalid, 'invalid': $v.cronograma.actividad.$invalid }" v-model="$v.cronograma.actividad.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronograma.duracion')" for="cronograma-duracion">Duracion</label>
                        <input type="number" class="form-control" name="duracion" id="cronograma-duracion"
                            :class="{'valid': !$v.cronograma.duracion.$invalid, 'invalid': $v.cronograma.duracion.$invalid }" v-model.number="$v.cronograma.duracion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronograma.fechaInicio')" for="cronograma-fechaInicio">Fecha Inicio</label>
                        <div class="input-group">
                            <input id="cronograma-fechaInicio" type="date" class="form-control" name="fechaInicio"  :class="{'valid': !$v.cronograma.fechaInicio.$invalid, 'invalid': $v.cronograma.fechaInicio.$invalid }"
                            v-model="$v.cronograma.fechaInicio.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronograma.fechaFin')" for="cronograma-fechaFin">Fecha Fin</label>
                        <div class="input-group">
                            <input id="cronograma-fechaFin" type="date" class="form-control" name="fechaFin"  :class="{'valid': !$v.cronograma.fechaFin.$invalid, 'invalid': $v.cronograma.fechaFin.$invalid }"
                            v-model="$v.cronograma.fechaFin.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronograma.ordenVista')" for="cronograma-ordenVista">Orden Vista</label>
                        <input type="number" class="form-control" name="ordenVista" id="cronograma-ordenVista"
                            :class="{'valid': !$v.cronograma.ordenVista.$invalid, 'invalid': $v.cronograma.ordenVista.$invalid }" v-model.number="$v.cronograma.ordenVista.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.cronograma.cronogramaProyecto')" for="cronograma-cronogramaProyecto">Cronograma Proyecto</label>
                        <select class="form-control" id="cronograma-cronogramaProyecto" name="cronogramaProyecto" v-model="cronograma.cronogramaProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cronograma.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cronograma-update.component.ts">
</script>
