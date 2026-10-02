<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.entidad.home.createOrEditLabel" v-text="$t('ciecytApp.entidad.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="entidad.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="entidad.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.entidad.entidad')" for="entidad-entidad"></label>
                        <input type="text" class="form-control" name="entidad" id="entidad-entidad"
                            :class="{'valid': !v$.entidad.entidad.$invalid, 'invalid': v$.entidad.entidad.$invalid }" v-model="v$.entidad.entidad.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.entidad.nit')" for="entidad-nit"></label>
                        <input type="text" class="form-control" name="nit" id="entidad-nit"
                            :class="{'valid': !v$.entidad.nit.$invalid, 'invalid': v$.entidad.nit.$invalid }" v-model="v$.entidad.nit.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.entidad.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./entidad-update.component.ts">
</script>
