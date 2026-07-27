<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.entidadFinanciadora.home.createOrEditLabel" v-text="$t('ciecytApp.entidadFinanciadora.home.createOrEditLabel')">Create or edit a EntidadFinanciadora</h2>
                <div>
                    <div class="form-group" v-if="entidadFinanciadora.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="entidadFinanciadora.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.entidadFinanciadora.valor')" for="entidad-financiadora-valor">Valor</label>
                        <input type="number" class="form-control" name="valor" id="entidad-financiadora-valor"
                            :class="{'valid': !$v.entidadFinanciadora.valor.$invalid, 'invalid': $v.entidadFinanciadora.valor.$invalid }" v-model.number="$v.entidadFinanciadora.valor.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.entidadFinanciadora.aprobada')" for="entidad-financiadora-aprobada">Aprobada</label>
                        <input type="checkbox" class="form-check" name="aprobada" id="entidad-financiadora-aprobada"
                            :class="{'valid': !$v.entidadFinanciadora.aprobada.$invalid, 'invalid': $v.entidadFinanciadora.aprobada.$invalid }" v-model="$v.entidadFinanciadora.aprobada.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.entidadFinanciadora.entidadFinanciadoraEntidad')" for="entidad-financiadora-entidadFinanciadoraEntidad">Entidad Financiadora Entidad</label>
                        <select class="form-control" id="entidad-financiadora-entidadFinanciadoraEntidad" name="entidadFinanciadoraEntidad" v-model="entidadFinanciadora.entidadFinanciadoraEntidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="entidadOption.id" v-for="entidadOption in entidads" :key="entidadOption.id">{{entidadOption.entidad}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.entidadFinanciadora.entidadFinanciadoraProyecto')" for="entidad-financiadora-entidadFinanciadoraProyecto">Entidad Financiadora Proyecto</label>
                        <select class="form-control" id="entidad-financiadora-entidadFinanciadoraProyecto" name="entidadFinanciadoraProyecto" v-model="entidadFinanciadora.entidadFinanciadoraProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.entidadFinanciadora.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./entidad-financiadora-update.component.ts">
</script>
