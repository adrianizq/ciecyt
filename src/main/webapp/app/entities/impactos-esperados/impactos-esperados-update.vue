<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.impactosEsperados.home.createOrEditLabel" v-text="$t('ciecytApp.impactosEsperados.home.createOrEditLabel')">Create or edit a ImpactosEsperados</h2>
                <div>
                    <div class="form-group" v-if="impactosEsperados.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="impactosEsperados.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.impactosEsperados.impacto')" for="impactos-esperados-impacto">Impacto</label>
                        <input type="text" class="form-control" name="impacto" id="impactos-esperados-impacto"
                            :class="{'valid': !$v.impactosEsperados.impacto.$invalid, 'invalid': $v.impactosEsperados.impacto.$invalid }" v-model="$v.impactosEsperados.impacto.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.impactosEsperados.plazo')" for="impactos-esperados-plazo">Plazo</label>
                        <input type="number" class="form-control" name="plazo" id="impactos-esperados-plazo"
                            :class="{'valid': !$v.impactosEsperados.plazo.$invalid, 'invalid': $v.impactosEsperados.plazo.$invalid }" v-model.number="$v.impactosEsperados.plazo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.impactosEsperados.indicador')" for="impactos-esperados-indicador">Indicador</label>
                        <input type="text" class="form-control" name="indicador" id="impactos-esperados-indicador"
                            :class="{'valid': !$v.impactosEsperados.indicador.$invalid, 'invalid': $v.impactosEsperados.indicador.$invalid }" v-model="$v.impactosEsperados.indicador.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.impactosEsperados.supuestos')" for="impactos-esperados-supuestos">Supuestos</label>
                        <input type="text" class="form-control" name="supuestos" id="impactos-esperados-supuestos"
                            :class="{'valid': !$v.impactosEsperados.supuestos.$invalid, 'invalid': $v.impactosEsperados.supuestos.$invalid }" v-model="$v.impactosEsperados.supuestos.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.impactosEsperados.ordenVista')" for="impactos-esperados-ordenVista">Orden Vista</label>
                        <input type="number" class="form-control" name="ordenVista" id="impactos-esperados-ordenVista"
                            :class="{'valid': !$v.impactosEsperados.ordenVista.$invalid, 'invalid': $v.impactosEsperados.ordenVista.$invalid }" v-model.number="$v.impactosEsperados.ordenVista.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.impactosEsperados.impactosEsperadoProyecto')" for="impactos-esperados-impactosEsperadoProyecto">Impactos Esperado Proyecto</label>
                        <select class="form-control" id="impactos-esperados-impactosEsperadoProyecto" name="impactosEsperadoProyecto" v-model="impactosEsperados.impactosEsperadoProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.impactosEsperados.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./impactos-esperados-update.component.ts">
</script>
