<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.cronogramaCiecyt.home.createOrEditLabel" v-text="$t('ciecytApp.cronogramaCiecyt.home.createOrEditLabel')">Create or edit a CronogramaCiecyt</h2>
                <div>
                    <div class="form-group" v-if="cronogramaCiecyt.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cronogramaCiecyt.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecyt.tituloCronograma')" for="cronograma-ciecyt-tituloCronograma">Titulo Cronograma</label>
                        <input type="text" class="form-control" name="tituloCronograma" id="cronograma-ciecyt-tituloCronograma"
                            :class="{'valid': !$v.cronogramaCiecyt.tituloCronograma.$invalid, 'invalid': $v.cronogramaCiecyt.tituloCronograma.$invalid }" v-model="$v.cronogramaCiecyt.tituloCronograma.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecyt.fechaInicio')" for="cronograma-ciecyt-fechaInicio">Fecha Inicio</label>
                        <div class="input-group">
                            <input id="cronograma-ciecyt-fechaInicio" type="date" class="form-control" name="fechaInicio"  :class="{'valid': !$v.cronogramaCiecyt.fechaInicio.$invalid, 'invalid': $v.cronogramaCiecyt.fechaInicio.$invalid }"
                            v-model="$v.cronogramaCiecyt.fechaInicio.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecyt.fechaFin')" for="cronograma-ciecyt-fechaFin">Fecha Fin</label>
                        <div class="input-group">
                            <input id="cronograma-ciecyt-fechaFin" type="date" class="form-control" name="fechaFin"  :class="{'valid': !$v.cronogramaCiecyt.fechaFin.$invalid, 'invalid': $v.cronogramaCiecyt.fechaFin.$invalid }"
                            v-model="$v.cronogramaCiecyt.fechaFin.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecyt.observaciones')" for="cronograma-ciecyt-observaciones">Observaciones</label>
                        <input type="text" class="form-control" name="observaciones" id="cronograma-ciecyt-observaciones"
                            :class="{'valid': !$v.cronogramaCiecyt.observaciones.$invalid, 'invalid': $v.cronogramaCiecyt.observaciones.$invalid }" v-model="$v.cronogramaCiecyt.observaciones.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.cronogramaCiecyt.cronogramaCiecytModalidad')" for="cronograma-ciecyt-cronogramaCiecytModalidad">Cronograma Ciecyt Modalidad</label>
                        <select class="form-control" id="cronograma-ciecyt-cronogramaCiecytModalidad" name="cronogramaCiecytModalidad" v-model="cronogramaCiecyt.cronogramaCiecytModalidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="modalidadOption.id" v-for="modalidadOption in modalidads" :key="modalidadOption.id">{{modalidadOption.modalidad}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cronogramaCiecyt.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cronograma-ciecyt-update.component.ts">
</script>
