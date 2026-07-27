<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.lineaInvestigacion.home.createOrEditLabel" v-text="$t('ciecytApp.lineaInvestigacion.home.createOrEditLabel')">Create or edit a LineaInvestigacion</h2>
                <div>
                    <div class="form-group" v-if="lineaInvestigacion.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="lineaInvestigacion.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.lineaInvestigacion.linea')" for="linea-investigacion-linea">Linea</label>
                        <input type="text" class="form-control" name="linea" id="linea-investigacion-linea"
                            :class="{'valid': !$v.lineaInvestigacion.linea.$invalid, 'invalid': $v.lineaInvestigacion.linea.$invalid }" v-model="$v.lineaInvestigacion.linea.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.lineaInvestigacion.codigoLinea')" for="linea-investigacion-codigoLinea">Codigo Linea</label>
                        <input type="text" class="form-control" name="codigoLinea" id="linea-investigacion-codigoLinea"
                            :class="{'valid': !$v.lineaInvestigacion.codigoLinea.$invalid, 'invalid': $v.lineaInvestigacion.codigoLinea.$invalid }" v-model="$v.lineaInvestigacion.codigoLinea.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.lineaInvestigacion.lineaPadre')" for="linea-investigacion-lineaPadre">Linea Padre</label>
                        <select class="form-control" id="linea-investigacion-lineaPadre" name="lineaPadre" v-model="lineaInvestigacion.lineaPadreId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="lineaInvestigacionOption.id" v-for="lineaInvestigacionOption in lineaInvestigacions" :key="lineaInvestigacionOption.id">{{lineaInvestigacionOption.linea}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.lineaInvestigacion.lineaInvestigacionPrograma')" for="linea-investigacion-lineaInvestigacionPrograma">Linea Investigacion Programa</label>
                        <select class="form-control" id="linea-investigacion-lineaInvestigacionPrograma" name="lineaInvestigacionPrograma" v-model="lineaInvestigacion.lineaInvestigacionProgramaId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="programaOption.id" v-for="programaOption in programas" :key="programaOption.id">{{programaOption.programa}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.lineaInvestigacion.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./linea-investigacion-update.component.ts">
</script>
