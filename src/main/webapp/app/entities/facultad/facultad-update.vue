<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.facultad.home.createOrEditLabel" v-text="$t('ciecytApp.facultad.home.createOrEditLabel')">Create or edit a Facultad</h2>
                <div>
                    <div class="form-group" v-if="facultad.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="facultad.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.facultad.codigoFacultad')" for="facultad-codigoFacultad">Codigo Facultad</label>
                        <input type="text" class="form-control" name="codigoFacultad" id="facultad-codigoFacultad"
                            :class="{'valid': !$v.facultad.codigoFacultad.$invalid, 'invalid': $v.facultad.codigoFacultad.$invalid }" v-model="$v.facultad.codigoFacultad.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.facultad.facultad')" for="facultad-facultad">Facultad</label>
                        <input type="text" class="form-control" name="facultad" id="facultad-facultad"
                            :class="{'valid': !$v.facultad.facultad.$invalid, 'invalid': $v.facultad.facultad.$invalid }" v-model="$v.facultad.facultad.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.facultad.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./facultad-update.component.ts">
</script>
