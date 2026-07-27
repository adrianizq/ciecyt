<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.rolMenu.home.createOrEditLabel" v-text="$t('ciecytApp.rolMenu.home.createOrEditLabel')">Create or edit a RolMenu</h2>
                <div>
                    <div class="form-group" v-if="rolMenu.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rolMenu.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolMenu.permitirAcceso')" for="rol-menu-permitirAcceso">Permitir Acceso</label>
                        <input type="checkbox" class="form-check" name="permitirAcceso" id="rol-menu-permitirAcceso"
                            :class="{'valid': !$v.rolMenu.permitirAcceso.$invalid, 'invalid': $v.rolMenu.permitirAcceso.$invalid }" v-model="$v.rolMenu.permitirAcceso.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolMenu.permitirCrear')" for="rol-menu-permitirCrear">Permitir Crear</label>
                        <input type="checkbox" class="form-check" name="permitirCrear" id="rol-menu-permitirCrear"
                            :class="{'valid': !$v.rolMenu.permitirCrear.$invalid, 'invalid': $v.rolMenu.permitirCrear.$invalid }" v-model="$v.rolMenu.permitirCrear.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolMenu.permitirEditar')" for="rol-menu-permitirEditar">Permitir Editar</label>
                        <input type="checkbox" class="form-check" name="permitirEditar" id="rol-menu-permitirEditar"
                            :class="{'valid': !$v.rolMenu.permitirEditar.$invalid, 'invalid': $v.rolMenu.permitirEditar.$invalid }" v-model="$v.rolMenu.permitirEditar.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolMenu.permitirEliminar')" for="rol-menu-permitirEliminar">Permitir Eliminar</label>
                        <input type="checkbox" class="form-check" name="permitirEliminar" id="rol-menu-permitirEliminar"
                            :class="{'valid': !$v.rolMenu.permitirEliminar.$invalid, 'invalid': $v.rolMenu.permitirEliminar.$invalid }" v-model="$v.rolMenu.permitirEliminar.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rolMenu.authName')" for="rol-menu-authName">Auth Name</label>
                        <input type="text" class="form-control" name="authName" id="rol-menu-authName"
                            :class="{'valid': !$v.rolMenu.authName.$invalid, 'invalid': $v.rolMenu.authName.$invalid }" v-model="$v.rolMenu.authName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.rolMenu.rolMenuMenu')" for="rol-menu-rolMenuMenu">Rol Menu Menu</label>
                        <select class="form-control" id="rol-menu-rolMenuMenu" name="rolMenuMenu" v-model="rolMenu.rolMenuMenuId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="menuOption.id" v-for="menuOption in menus" :key="menuOption.id">{{menuOption.nombre}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rolMenu.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rol-menu-update.component.ts">
</script>
