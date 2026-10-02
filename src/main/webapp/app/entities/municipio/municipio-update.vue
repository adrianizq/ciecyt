<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.municipio.home.createOrEditLabel" v-text="$t('ciecytApp.municipio.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="municipio.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="municipio.id" readonly />
                    </div> 
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.municipio.region')" for="municipio-region"></label>
                        <input type="text" class="form-control" name="region" id="municipio-region"
                            :class="{'valid': !v$.municipio.region.$invalid, 'invalid': v$.municipio.region.$invalid }" v-model="v$.municipio.region.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.municipio.codigo-dane-departamento')" for="municipio-codigo-dane-departamento"></label>
                        <input type="text" class="form-control" name="version" id="codigo-dane-departamento"
                            :class="{'valid': !v$.municipio.codigoDaneDepartamento.$invalid, 'invalid': v$.municipio.codigoDaneDepartamento.$invalid }" v-model="v$.municipio.codigoDaneDepartamento.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.municipio.departamento')" for="municipio-departamento"></label>
                        <input type="text" class="form-control" name="departamento" id="municipio-departamento"
                            :class="{'valid': !v$.municipio.departamento.$invalid, 'invalid': v$.municipio.departamento.$invalid }" v-model="v$.municipio.departamento.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.municipio.codigo-dane-municipio')" for="codigo-dane-municipio"></label>
                        <input type="text" class="form-control" name="codigo-dane-municipio" id="codigo-dane-municipio"
                            :class="{'valid': !v$.municipio.codigoDaneMunicipio.$invalid, 'invalid': v$.municipio.codigoDaneMunicipio.$invalid }" v-model="v$.municipio.codigoDaneMunicipio.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.municipio.municipio')" for="municipio"></label>
                        <input type="text" class="form-control" name="municipio" id="municipio"
                            :class="{'valid': !v$.municipio.municipio.$invalid, 'invalid': v$.municipio.municipio.$invalid }" v-model="v$.municipio.municipio.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.municipio.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./municipio-update.component.ts">
</script>
