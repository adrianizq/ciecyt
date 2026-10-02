<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.grupoSemillero.home.createOrEditLabel" v-text="$t('ciecytApp.grupoSemillero.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="grupoSemillero.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="grupoSemillero.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.grupoSemillero.nombre')" for="grupo-semillero-nombre"></label>
                        <input type="text" class="form-control" name="nombre" id="grupo-semillero-nombre"
                            :class="{'valid': !v$.grupoSemillero.nombre.$invalid, 'invalid': v$.grupoSemillero.nombre.$invalid }" v-model="v$.grupoSemillero.nombre.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.grupoSemillero.tipo')" for="grupo-semillero-tipo"></label>
                        <input type="checkbox" class="form-check" name="tipo" id="grupo-semillero-tipo"
                            :class="{'valid': !v$.grupoSemillero.tipo.$invalid, 'invalid': v$.grupoSemillero.tipo.$invalid }" v-model="v$.grupoSemillero.tipo.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.grupoSemillero.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./grupo-semillero-update.component.ts">
</script>
