<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.fases.home.createOrEditLabel" v-text="$t('ciecytApp.fases.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="fases.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="fases.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.fases.fase')" for="fases-fase"></label>
                        <input type="text" class="form-control" name="fase" id="fases-fase"
                            :class="{'valid': !v$.fases.fase.$invalid, 'invalid': v$.fases.fase.$invalid }" v-model="v$.fases.fase.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.fases.notificable')" for="fases-notificable"></label>
                        <input type="checkbox" class="form-check" name="notificable" id="fases-notificable"
                            :class="{'valid': !v$.fases.notificable.$invalid, 'invalid': v$.fases.notificable.$invalid }" v-model="v$.fases.notificable.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.fases.fasesModalidad')" for="fases-fasesModalidad">Fases Modalidad</label>
                        <select class="form-control" id="fases-fasesModalidad" name="fasesModalidad" v-model="fases.fasesModalidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="modalidadOption.id" v-for="modalidadOption in modalidads" :key="modalidadOption.id">{{modalidadOption.modalidad}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.fases.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./fases-update.component.ts">
</script>
