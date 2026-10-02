<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.elementoProyecto.home.createOrEditLabel" v-text="$t('ciecytApp.elementoProyecto.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="elementoProyecto.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="elementoProyecto.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.elementoProyecto.dato')" for="elemento-proyecto-dato"></label>
                        <input type="text" class="form-control" name="dato" id="elemento-proyecto-dato"
                            :class="{'valid': !v$.elementoProyecto.dato.$invalid, 'invalid': v$.elementoProyecto.dato.$invalid }" v-model="v$.elementoProyecto.dato.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyectoDescripcion')" for="elemento-proyecto-elementoProyectoProyectoDescripcion"></label>
                        <input type="text" class="form-control" name="elementoProyectoProyectoDescripcion" id="elemento-proyecto-elementoProyectoProyectoDescripcion"
                            :class="{'valid': !v$.elementoProyecto.elementoProyectoProyectoDescripcion.$invalid, 'invalid': v$.elementoProyecto.elementoProyectoProyectoDescripcion.$invalid }" v-model="v$.elementoProyecto.elementoProyectoProyectoDescripcion.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.elementoProyecto.elementoProyectoProyecto')" for="elemento-proyecto-elementoProyectoProyecto">Elemento Proyecto Proyecto</label>
                        <select class="form-control" id="elemento-proyecto-elementoProyectoProyecto" name="elementoProyectoProyecto" v-model="elementoProyecto.elementoProyectoProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.elementoProyecto.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./elemento-proyecto-update.component.ts">
</script>
