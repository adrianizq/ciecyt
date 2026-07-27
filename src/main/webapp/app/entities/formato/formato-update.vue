<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.formato.home.createOrEditLabel" v-text="$t('ciecytApp.formato.home.createOrEditLabel')">Create or edit a Formato</h2>
                <div>
                    <div class="form-group" v-if="formato.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="formato.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.formato.formato')" for="formato-formato">Formato</label>
                        <input type="text" class="form-control" name="formato" id="formato-formato"
                            :class="{'valid': !$v.formato.formato.$invalid, 'invalid': $v.formato.formato.$invalid }" v-model="$v.formato.formato.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.formato.version')" for="formato-version">Version</label>
                        <input type="text" class="form-control" name="version" id="formato-version"
                            :class="{'valid': !$v.formato.version.$invalid, 'invalid': $v.formato.version.$invalid }" v-model="$v.formato.version.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.formato.codigo')" for="formato-codigo">Codigo</label>
                        <input type="text" class="form-control" name="codigo" id="formato-codigo"
                            :class="{'valid': !$v.formato.codigo.$invalid, 'invalid': $v.formato.codigo.$invalid }" v-model="$v.formato.codigo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.formato.fecha')" for="formato-fecha">Fecha</label>
                        <div class="input-group">
                            <input id="formato-fecha" type="date" class="form-control" name="fecha"  :class="{'valid': !$v.formato.fecha.$invalid, 'invalid': $v.formato.fecha.$invalid }"
                            v-model="$v.formato.fecha.$model"  />
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.formato.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./formato-update.component.ts">
</script>
