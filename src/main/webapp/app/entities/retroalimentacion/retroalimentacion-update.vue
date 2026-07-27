<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.retroalimentacion.home.createOrEditLabel" v-text="$t('ciecytApp.retroalimentacion.home.createOrEditLabel')">Create or edit a Retroalimentacion</h2>
                <div>
                    <div class="form-group" v-if="retroalimentacion.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="retroalimentacion.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.retroalimentacion.titulo')" for="retroalimentacion-titulo">Titulo</label>
                        <input type="text" class="form-control" name="titulo" id="retroalimentacion-titulo"
                            :class="{'valid': !$v.retroalimentacion.titulo.$invalid, 'invalid': $v.retroalimentacion.titulo.$invalid }" v-model="$v.retroalimentacion.titulo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.retroalimentacion.retroalimentacion')" for="retroalimentacion-retroalimentacion">Retroalimentacion</label>
                        <input type="text" class="form-control" name="retroalimentacion" id="retroalimentacion-retroalimentacion"
                            :class="{'valid': !$v.retroalimentacion.retroalimentacion.$invalid, 'invalid': $v.retroalimentacion.retroalimentacion.$invalid }" v-model="$v.retroalimentacion.retroalimentacion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.retroalimentacion.fechaRetroalimentacion')" for="retroalimentacion-fechaRetroalimentacion">Fecha Retroalimentacion</label>
                        <div class="input-group">
                            <input id="retroalimentacion-fechaRetroalimentacion" type="date" class="form-control" name="fechaRetroalimentacion"  :class="{'valid': !$v.retroalimentacion.fechaRetroalimentacion.$invalid, 'invalid': $v.retroalimentacion.fechaRetroalimentacion.$invalid }"
                            v-model="$v.retroalimentacion.fechaRetroalimentacion.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.retroalimentacion.estadoRetroalimentacion')" for="retroalimentacion-estadoRetroalimentacion">Estado Retroalimentacion</label>
                        <div class="input-group">
                            <input id="retroalimentacion-estadoRetroalimentacion" type="date" class="form-control" name="estadoRetroalimentacion"  :class="{'valid': !$v.retroalimentacion.estadoRetroalimentacion.$invalid, 'invalid': $v.retroalimentacion.estadoRetroalimentacion.$invalid }"
                            v-model="$v.retroalimentacion.estadoRetroalimentacion.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.retroalimentacion.estadoProyectoFase')" for="retroalimentacion-estadoProyectoFase">Estado Proyecto Fase</label>
                        <input type="number" class="form-control" name="estadoProyectoFase" id="retroalimentacion-estadoProyectoFase"
                            :class="{'valid': !$v.retroalimentacion.estadoProyectoFase.$invalid, 'invalid': $v.retroalimentacion.estadoProyectoFase.$invalid }" v-model.number="$v.retroalimentacion.estadoProyectoFase.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.retroalimentacion.retroalimentacionProyectoFase')" for="retroalimentacion-retroalimentacionProyectoFase">Retroalimentacion Proyecto Fase</label>
                        <select class="form-control" id="retroalimentacion-retroalimentacionProyectoFase" name="retroalimentacionProyectoFase" v-model="retroalimentacion.retroalimentacionProyectoFaseId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoFaseOption.id" v-for="proyectoFaseOption in proyectoFases" :key="proyectoFaseOption.id">{{proyectoFaseOption.titulo}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.retroalimentacion.retroalimentacionUser')" for="retroalimentacion-retroalimentacionUser">Retroalimentacion User</label>
                        <select class="form-control" id="retroalimentacion-retroalimentacionUser" name="retroalimentacionUser" v-model="retroalimentacion.retroalimentacionUserId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userOption.id" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.retroalimentacion.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./retroalimentacion-update.component.ts">
</script>
