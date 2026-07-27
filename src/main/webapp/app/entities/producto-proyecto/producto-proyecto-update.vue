<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.productoProyecto.home.createOrEditLabel" v-text="$t('ciecytApp.productoProyecto.home.createOrEditLabel')">Create or edit a ProductoProyecto</h2>
                <div>
                    <div class="form-group" v-if="productoProyecto.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="productoProyecto.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.productoProyecto.aplica')" for="producto-proyecto-aplica">Aplica</label>
                        <input type="checkbox" class="form-check" name="aplica" id="producto-proyecto-aplica"
                            :class="{'valid': !$v.productoProyecto.aplica.$invalid, 'invalid': $v.productoProyecto.aplica.$invalid }" v-model="$v.productoProyecto.aplica.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.productoProyecto.descripcion')" for="producto-proyecto-descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="producto-proyecto-descripcion"
                            :class="{'valid': !$v.productoProyecto.descripcion.$invalid, 'invalid': $v.productoProyecto.descripcion.$invalid }" v-model="$v.productoProyecto.descripcion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.productoProyecto.productoProyectoProducto')" for="producto-proyecto-productoProyectoProducto">Producto Proyecto Producto</label>
                        <select class="form-control" id="producto-proyecto-productoProyectoProducto" name="productoProyectoProducto" v-model="productoProyecto.productoProyectoProductoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="productoOption.id" v-for="productoOption in productos" :key="productoOption.id">{{productoOption.producto}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.productoProyecto.productoProyectoProyecto')" for="producto-proyecto-productoProyectoProyecto">Producto Proyecto Proyecto</label>
                        <select class="form-control" id="producto-proyecto-productoProyectoProyecto" name="productoProyectoProyecto" v-model="productoProyecto.productoProyectoProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.productoProyecto.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./producto-proyecto-update.component.ts">
</script>
