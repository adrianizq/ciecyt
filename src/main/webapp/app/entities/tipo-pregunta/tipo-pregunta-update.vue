<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.tipoPregunta.home.createOrEditLabel" v-text="$t('ciecytApp.tipoPregunta.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="tipoPregunta.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="tipoPregunta.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.tipoPregunta.tipoPregunta')" for="tipo-pregunta-tipoPregunta"></label>
                        <input type="text" class="form-control" name="tipoPregunta" id="tipo-pregunta-tipoPregunta"
                            :class="{'valid': !v$.tipoPregunta.tipoPregunta.$invalid, 'invalid': v$.tipoPregunta.tipoPregunta.$invalid }" v-model="v$.tipoPregunta.tipoPregunta.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.tipoPregunta.tipoDato')" for="tipo-pregunta-tipoDato"></label>
                        <input type="text" class="form-control" name="tipoDato" id="tipo-pregunta-tipoDato"
                            :class="{'valid': !v$.tipoPregunta.tipoDato.$invalid, 'invalid': v$.tipoPregunta.tipoDato.$invalid }" v-model="v$.tipoPregunta.tipoDato.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.tipoPregunta.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tipo-pregunta-update.component.ts">
</script>
