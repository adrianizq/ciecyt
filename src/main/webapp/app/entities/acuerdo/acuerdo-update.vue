<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.acuerdo.home.createOrEditLabel" v-text="$t('ciecytApp.acuerdo.home.createOrEditLabel')">Create or edit a Acuerdo</h2>
                <div>
                    <div class="form-group" v-if="acuerdo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="acuerdo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.acuerdo.acuerdo')" for="acuerdo-acuerdo">Acuerdo</label>
                        <input type="text" class="form-control" name="acuerdo" id="acuerdo-acuerdo"
                            :class="{'valid': !$v.acuerdo.acuerdo.$invalid, 'invalid': $v.acuerdo.acuerdo.$invalid }" v-model="$v.acuerdo.acuerdo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.acuerdo.version')" for="acuerdo-version">Version</label>
                        <input type="text" class="form-control" name="version" id="acuerdo-version"
                            :class="{'valid': !$v.acuerdo.version.$invalid, 'invalid': $v.acuerdo.version.$invalid }" v-model="$v.acuerdo.version.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.acuerdo.codigo')" for="acuerdo-codigo">Codigo</label>
                        <input type="text" class="form-control" name="codigo" id="acuerdo-codigo"
                            :class="{'valid': !$v.acuerdo.codigo.$invalid, 'invalid': $v.acuerdo.codigo.$invalid }" v-model="$v.acuerdo.codigo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.acuerdo.fecha')" for="acuerdo-fecha">Fecha</label>
                        <div class="input-group">
                            <input id="acuerdo-fecha" type="date" class="form-control" name="fecha"  :class="{'valid': !$v.acuerdo.fecha.$invalid, 'invalid': $v.acuerdo.fecha.$invalid }"
                            v-model="$v.acuerdo.fecha.$model"  />
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.acuerdo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./acuerdo-update.component.ts">
</script>
