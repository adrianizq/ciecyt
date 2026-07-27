<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.resultadosEsperados.home.createOrEditLabel" v-text="$t('ciecytApp.resultadosEsperados.home.createOrEditLabel')">Create or edit a ResultadosEsperados</h2>
                <div>
                    <div class="form-group" v-if="resultadosEsperados.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="resultadosEsperados.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.resultadosEsperados.resultado')" for="resultados-esperados-resultado">Resultado</label>
                        <input type="text" class="form-control" name="resultado" id="resultados-esperados-resultado"
                            :class="{'valid': !$v.resultadosEsperados.resultado.$invalid, 'invalid': $v.resultadosEsperados.resultado.$invalid }" v-model="$v.resultadosEsperados.resultado.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.resultadosEsperados.indicador')" for="resultados-esperados-indicador">Indicador</label>
                        <input type="text" class="form-control" name="indicador" id="resultados-esperados-indicador"
                            :class="{'valid': !$v.resultadosEsperados.indicador.$invalid, 'invalid': $v.resultadosEsperados.indicador.$invalid }" v-model="$v.resultadosEsperados.indicador.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.resultadosEsperados.beneficiario')" for="resultados-esperados-beneficiario">Beneficiario</label>
                        <input type="text" class="form-control" name="beneficiario" id="resultados-esperados-beneficiario"
                            :class="{'valid': !$v.resultadosEsperados.beneficiario.$invalid, 'invalid': $v.resultadosEsperados.beneficiario.$invalid }" v-model="$v.resultadosEsperados.beneficiario.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.resultadosEsperados.ordenVista')" for="resultados-esperados-ordenVista">Orden Vista</label>
                        <input type="number" class="form-control" name="ordenVista" id="resultados-esperados-ordenVista"
                            :class="{'valid': !$v.resultadosEsperados.ordenVista.$invalid, 'invalid': $v.resultadosEsperados.ordenVista.$invalid }" v-model.number="$v.resultadosEsperados.ordenVista.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.resultadosEsperados.resultadosEsperadosProyecto')" for="resultados-esperados-resultadosEsperadosProyecto">Resultados Esperados Proyecto</label>
                        <select class="form-control" id="resultados-esperados-resultadosEsperadosProyecto" name="resultadosEsperadosProyecto" v-model="resultadosEsperados.resultadosEsperadosProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.resultadosEsperados.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./resultados-esperados-update.component.ts">
</script>
