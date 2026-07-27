<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.entidad.home.createOrEditLabel" v-text="$t('ciecytApp.entidad.home.createOrEditLabel')">Create or edit a Entidad</h2>
                <div>
                    <div class="form-group" v-if="entidad.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="entidad.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.entidad.entidad')" for="entidad-entidad">Entidad</label>
                        <input type="text" class="form-control" name="entidad" id="entidad-entidad"
                            :class="{'valid': !$v.entidad.entidad.$invalid, 'invalid': $v.entidad.entidad.$invalid }" v-model="$v.entidad.entidad.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.entidad.nit')" for="entidad-nit">Nit</label>
                        <input type="text" class="form-control" name="nit" id="entidad-nit"
                            :class="{'valid': !$v.entidad.nit.$invalid, 'invalid': $v.entidad.nit.$invalid }" v-model="$v.entidad.nit.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.entidad.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./entidad-update.component.ts">
</script>
