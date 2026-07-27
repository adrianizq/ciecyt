<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.fichaTecnica.home.createOrEditLabel" v-text="$t('ciecytApp.fichaTecnica.home.createOrEditLabel')">Create or edit a FichaTecnica</h2>
                <div>
                    <div class="form-group" v-if="fichaTecnica.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="fichaTecnica.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.fichaTecnica.tituloProfesional')" for="ficha-tecnica-tituloProfesional">Titulo Profesional</label>
                        <input type="text" class="form-control" name="tituloProfesional" id="ficha-tecnica-tituloProfesional"
                            :class="{'valid': !$v.fichaTecnica.tituloProfesional.$invalid, 'invalid': $v.fichaTecnica.tituloProfesional.$invalid }" v-model="$v.fichaTecnica.tituloProfesional.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.fichaTecnica.tituloPostgrado')" for="ficha-tecnica-tituloPostgrado">Titulo Postgrado</label>
                        <input type="text" class="form-control" name="tituloPostgrado" id="ficha-tecnica-tituloPostgrado"
                            :class="{'valid': !$v.fichaTecnica.tituloPostgrado.$invalid, 'invalid': $v.fichaTecnica.tituloPostgrado.$invalid }" v-model="$v.fichaTecnica.tituloPostgrado.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.fichaTecnica.experiencia')" for="ficha-tecnica-experiencia">Experiencia</label>
                        <input type="text" class="form-control" name="experiencia" id="ficha-tecnica-experiencia"
                            :class="{'valid': !$v.fichaTecnica.experiencia.$invalid, 'invalid': $v.fichaTecnica.experiencia.$invalid }" v-model="$v.fichaTecnica.experiencia.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.fichaTecnica.fichaTecnicaUser')" for="ficha-tecnica-fichaTecnicaUser">Ficha Tecnica User</label>
                        <select class="form-control" id="ficha-tecnica-fichaTecnicaUser" name="fichaTecnicaUser" v-model="fichaTecnica.fichaTecnicaUserId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userOption.id" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.fichaTecnica.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ficha-tecnica-update.component.ts">
</script>
