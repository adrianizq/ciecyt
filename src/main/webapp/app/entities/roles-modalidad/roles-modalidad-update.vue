<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.rolesModalidad.home.createOrEditLabel" v-text="$t('ciecytApp.rolesModalidad.home.createOrEditLabel')">Create or edit a RolesModalidad</h2>
                <div>
                    <div class="form-group" v-if="rolesModalidad.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rolesModalidad.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolesModalidad.rol')" for="roles-modalidad-rol">Rol</label>
                        <input type="text" class="form-control" name="rol" id="roles-modalidad-rol"
                            :class="{'valid': !$v.rolesModalidad.rol.$invalid, 'invalid': $v.rolesModalidad.rol.$invalid }" v-model="$v.rolesModalidad.rol.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolesModalidad.cantidad')" for="roles-modalidad-cantidad">Cantidad</label>
                        <input type="number" class="form-control" name="cantidad" id="roles-modalidad-cantidad"
                            :class="{'valid': !$v.rolesModalidad.cantidad.$invalid, 'invalid': $v.rolesModalidad.cantidad.$invalid }" v-model.number="$v.rolesModalidad.cantidad.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolesModalidad.calificador')" for="roles-modalidad-calificador">Calificador</label>
                        <input type="checkbox" class="form-check" name="calificador" id="roles-modalidad-calificador"
                            :class="{'valid': !$v.rolesModalidad.calificador.$invalid, 'invalid': $v.rolesModalidad.calificador.$invalid }" v-model="$v.rolesModalidad.calificador.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.rolesModalidad.rolesModalidadModalidad')" for="roles-modalidad-rolesModalidadModalidad">Modalidad</label>
                        <select class="form-control" id="roles-modalidad-rolesModalidadModalidad" name="rolesModalidadModalidad" v-model="rolesModalidad.rolesModalidadModalidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="modalidadOption.id" v-for="modalidadOption in modalidads" :key="modalidadOption.id">{{modalidadOption.modalidad}}</option>
                        </select>
                    </div>
                     <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.rolesModalidad.rolesModalidadAuthority')" for="roles-modalidad-rolesModalidadAuthority">Autoridad</label>
                        <select class="form-control"  name="authority" v-model="rolesModalidad.rolesModalidadAuthorityName">
                            <option v-for="authority of authorities" :value="authority" :key="authority">{{authority}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rolesModalidad.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./roles-modalidad-update.component.ts">
</script>
