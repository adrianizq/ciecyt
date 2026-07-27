<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.usuario.home.createOrEditLabel" v-text="$t('ciecytApp.usuario.home.createOrEditLabel')">Create or edit a Usuario</h2>
                <div>
                    <div class="form-group" v-if="usuario.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="usuario.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.usuario.usuario')" for="usuario-usuario">Usuario</label>
                        <input type="text" class="form-control" name="usuario" id="usuario-usuario"
                            :class="{'valid': !$v.usuario.usuario.$invalid, 'invalid': $v.usuario.usuario.$invalid }" v-model="$v.usuario.usuario.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.usuario.descripcion')" for="usuario-descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="usuario-descripcion"
                            :class="{'valid': !$v.usuario.descripcion.$invalid, 'invalid': $v.usuario.descripcion.$invalid }" v-model="$v.usuario.descripcion.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.usuario.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./usuario-update.component.ts">
</script>
