<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.grupoSemillero.home.createOrEditLabel" v-text="$t('ciecytApp.grupoSemillero.home.createOrEditLabel')">Create or edit a GrupoSemillero</h2>
                <div>
                    <div class="form-group" v-if="grupoSemillero.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="grupoSemillero.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.grupoSemillero.nombre')" for="grupo-semillero-nombre">Nombre</label>
                        <input type="text" class="form-control" name="nombre" id="grupo-semillero-nombre"
                            :class="{'valid': !$v.grupoSemillero.nombre.$invalid, 'invalid': $v.grupoSemillero.nombre.$invalid }" v-model="$v.grupoSemillero.nombre.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.grupoSemillero.tipo')" for="grupo-semillero-tipo">Tipo</label>
                        <input type="checkbox" class="form-check" name="tipo" id="grupo-semillero-tipo"
                            :class="{'valid': !$v.grupoSemillero.tipo.$invalid, 'invalid': $v.grupoSemillero.tipo.$invalid }" v-model="$v.grupoSemillero.tipo.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.grupoSemillero.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./grupo-semillero-update.component.ts">
</script>
