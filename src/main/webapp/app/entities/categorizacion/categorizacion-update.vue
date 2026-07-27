<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.categorizacion.home.createOrEditLabel" v-text="$t('ciecytApp.categorizacion.home.createOrEditLabel')">Create or edit a Categorizacion</h2>
                <div>
                    <div class="form-group" v-if="categorizacion.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="categorizacion.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.categorizacion.categoria')" for="categorizacion-categoria">Categoria</label>
                        <input type="text" class="form-control" name="categoria" id="categorizacion-categoria"
                            :class="{'valid': !$v.categorizacion.categoria.$invalid, 'invalid': $v.categorizacion.categoria.$invalid }" v-model="$v.categorizacion.categoria.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.categorizacion.descripcion')" for="categorizacion-descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="categorizacion-descripcion"
                            :class="{'valid': !$v.categorizacion.descripcion.$invalid, 'invalid': $v.categorizacion.descripcion.$invalid }" v-model="$v.categorizacion.descripcion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.categorizacion.categorizacionProyecto')" for="categorizacion-categorizacionProyecto">Categorizacion Proyecto</label>
                        <select class="form-control" id="categorizacion-categorizacionProyecto" name="categorizacionProyecto" v-model="categorizacion.categorizacionProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.categorizacion.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./categorizacion-update.component.ts">
</script>
