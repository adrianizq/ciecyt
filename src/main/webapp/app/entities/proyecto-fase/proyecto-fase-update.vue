<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.proyectoFase.home.createOrEditLabel" v-text="$t('ciecytApp.proyectoFase.home.createOrEditLabel')">Create or edit a ProyectoFase</h2>
                <div>
                    <div class="form-group" v-if="proyectoFase.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="proyectoFase.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoFase.titulo')" for="proyecto-fase-titulo">Titulo</label>
                        <input type="text" class="form-control" name="titulo" id="proyecto-fase-titulo"
                            :class="{'valid': !$v.proyectoFase.titulo.$invalid, 'invalid': $v.proyectoFase.titulo.$invalid }" v-model="$v.proyectoFase.titulo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoFase.cumplida')" for="proyecto-fase-cumplida">Cumplida</label>
                        <input type="checkbox" class="form-check" name="cumplida" id="proyecto-fase-cumplida"
                            :class="{'valid': !$v.proyectoFase.cumplida.$invalid, 'invalid': $v.proyectoFase.cumplida.$invalid }" v-model="$v.proyectoFase.cumplida.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoFase.fechaCumplimiento')" for="proyecto-fase-fechaCumplimiento">Fecha Cumplimiento</label>
                        <div class="input-group">
                            <input id="proyecto-fase-fechaCumplimiento" type="date" class="form-control" name="fechaCumplimiento"  :class="{'valid': !$v.proyectoFase.fechaCumplimiento.$invalid, 'invalid': $v.proyectoFase.fechaCumplimiento.$invalid }"
                            v-model="$v.proyectoFase.fechaCumplimiento.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoFase.observaciones')" for="proyecto-fase-observaciones">Observaciones</label>
                        <input type="text" class="form-control" name="observaciones" id="proyecto-fase-observaciones"
                            :class="{'valid': !$v.proyectoFase.observaciones.$invalid, 'invalid': $v.proyectoFase.observaciones.$invalid }" v-model="$v.proyectoFase.observaciones.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyectoFase.proyectoFaseFases')" for="proyecto-fase-proyectoFaseFases">Proyecto Fase Fases</label>
                        <select class="form-control" id="proyecto-fase-proyectoFaseFases" name="proyectoFaseFases" v-model="proyectoFase.proyectoFaseFasesId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="fasesOption.id" v-for="fasesOption in fases" :key="fasesOption.id">{{fasesOption.fase}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyectoFase.proyectoFaseProyecto')" for="proyecto-fase-proyectoFaseProyecto">Proyecto Fase Proyecto</label>
                        <select class="form-control" id="proyecto-fase-proyectoFaseProyecto" name="proyectoFaseProyecto" v-model="proyectoFase.proyectoFaseProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.proyectoFase.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./proyecto-fase-update.component.ts">
</script>
