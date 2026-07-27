<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.integranteProyecto.home.createOrEditLabel" v-text="$t('ciecytApp.integranteProyecto.home.createOrEditLabel')">Create or edit a IntegranteProyecto</h2>
                <div>
                    <div class="form-group" v-if="integranteProyecto.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="integranteProyecto.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.integranteProyecto.integrante')" for="integrante-proyecto-integrante">Integrante</label>
                        <input type="text" class="form-control" name="integrante" id="integrante-proyecto-integrante"
                            :class="{'valid': !$v.integranteProyecto.integrante.$invalid, 'invalid': $v.integranteProyecto.integrante.$invalid }" v-model="$v.integranteProyecto.integrante.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.integranteProyecto.descripcion')" for="integrante-proyecto-descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="integrante-proyecto-descripcion"
                            :class="{'valid': !$v.integranteProyecto.descripcion.$invalid, 'invalid': $v.integranteProyecto.descripcion.$invalid }" v-model="$v.integranteProyecto.descripcion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.integranteProyecto.integranteProyectoUser')" for="integrante-proyecto-integranteProyectoUser">Integrante Proyecto User</label>
                        <select class="form-control" id="integrante-proyecto-integranteProyectoUser" name="integranteProyectoUser" v-model="integranteProyecto.integranteProyectoUserId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userOption.id" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.integranteProyecto.integranteProyectoProyecto')" for="integrante-proyecto-integranteProyectoProyecto">Integrante Proyecto Proyecto</label>
                        <select class="form-control" id="integrante-proyecto-integranteProyectoProyecto" name="integranteProyectoProyecto" v-model="integranteProyecto.integranteProyectoProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.integranteProyecto.integranteProyectoRolesModalidad')" for="integrante-proyecto-integranteProyectoRolesModalidad">Integrante Proyecto Roles Modalidad</label>
                        <select class="form-control" id="integrante-proyecto-integranteProyectoRolesModalidad" name="integranteProyectoRolesModalidad" v-model="integranteProyecto.integranteProyectoRolesModalidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rolesModalidadOption.id" v-for="rolesModalidadOption in rolesModalidads" :key="rolesModalidadOption.id">{{rolesModalidadOption.rol}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.integranteProyecto.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./integrante-proyecto-update.component.ts">
</script>
