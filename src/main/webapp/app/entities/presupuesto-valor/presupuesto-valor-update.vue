<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.presupuestoValor.home.createOrEditLabel" v-text="$t('ciecytApp.presupuestoValor.home.createOrEditLabel')">Create or edit a PresupuestoValor</h2>
                <div>
                    <div class="form-group" v-if="presupuestoValor.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="presupuestoValor.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.descripcion')" for="presupuesto-valor-descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="presupuesto-valor-descripcion"
                            :class="{'valid': !$v.presupuestoValor.descripcion.$invalid, 'invalid': $v.presupuestoValor.descripcion.$invalid }" v-model="$v.presupuestoValor.descripcion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.justificacion')" for="presupuesto-valor-justificacion">Justificacion</label>
                        <input type="text" class="form-control" name="justificacion" id="presupuesto-valor-justificacion"
                            :class="{'valid': !$v.presupuestoValor.justificacion.$invalid, 'invalid': $v.presupuestoValor.justificacion.$invalid }" v-model="$v.presupuestoValor.justificacion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.cantidad')" for="presupuesto-valor-cantidad">Cantidad</label>
                        <input type="number" class="form-control" name="cantidad" id="presupuesto-valor-cantidad"
                            :class="{'valid': !$v.presupuestoValor.cantidad.$invalid, 'invalid': $v.presupuestoValor.cantidad.$invalid }" v-model.number="$v.presupuestoValor.cantidad.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.valorUnitario')" for="presupuesto-valor-valorUnitario">Valor Unitario</label>
                        <input type="number" class="form-control" name="valorUnitario" id="presupuesto-valor-valorUnitario"
                            :class="{'valid': !$v.presupuestoValor.valorUnitario.$invalid, 'invalid': $v.presupuestoValor.valorUnitario.$invalid }" v-model.number="$v.presupuestoValor.valorUnitario.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.especie')" for="presupuesto-valor-especie">Especie</label>
                        <input type="number" class="form-control" name="especie" id="presupuesto-valor-especie"
                            :class="{'valid': !$v.presupuestoValor.especie.$invalid, 'invalid': $v.presupuestoValor.especie.$invalid }" v-model.number="$v.presupuestoValor.especie.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.dinero')" for="presupuesto-valor-dinero">Dinero</label>
                        <input type="number" class="form-control" name="dinero" id="presupuesto-valor-dinero"
                            :class="{'valid': !$v.presupuestoValor.dinero.$invalid, 'invalid': $v.presupuestoValor.dinero.$invalid }" v-model.number="$v.presupuestoValor.dinero.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.presupuestoValor.ordenVista')" for="presupuesto-valor-ordenVista">Orden Vista</label>
                        <input type="number" class="form-control" name="ordenVista" id="presupuesto-valor-ordenVista"
                            :class="{'valid': !$v.presupuestoValor.ordenVista.$invalid, 'invalid': $v.presupuestoValor.ordenVista.$invalid }" v-model.number="$v.presupuestoValor.ordenVista.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.presupuestoValor.presupuestoValorRubro')" for="presupuesto-valor-presupuestoValorRubro">Presupuesto Valor Rubro</label>
                        <select class="form-control" id="presupuesto-valor-presupuestoValorRubro" name="presupuestoValorRubro" v-model="presupuestoValor.presupuestoValorRubroId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rubroOption.id" v-for="rubroOption in rubros" :key="rubroOption.id">{{rubroOption.rubro}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.presupuestoValor.presupuestoValorProyecto')" for="presupuesto-valor-presupuestoValorProyecto">Presupuesto Valor Proyecto</label>
                        <select class="form-control" id="presupuesto-valor-presupuestoValorProyecto" name="presupuestoValorProyecto" v-model="presupuestoValor.presupuestoValorProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.presupuestoValor.presupuestoValorEntidad')" for="presupuesto-valor-presupuestoValorEntidad">Presupuesto Valor Entidad</label>
                        <select class="form-control" id="presupuesto-valor-presupuestoValorEntidad" name="presupuestoValorEntidad" v-model="presupuestoValor.presupuestoValorEntidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="entidadOption.id" v-for="entidadOption in entidads" :key="entidadOption.id">{{entidadOption.entidad}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.presupuestoValor.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./presupuesto-valor-update.component.ts">
</script>
