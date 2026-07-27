<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.solicitud.home.createOrEditLabel" v-text="$t('ciecytApp.solicitud.home.createOrEditLabel')">Create or edit a Solicitud</h2>
                <div>
                    <div class="form-group" v-if="solicitud.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="solicitud.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.solicitud.estado')" for="solicitud-estado">Estado</label>
                        <input type="checkbox" class="form-check" name="estado" id="solicitud-estado"
                            :class="{'valid': !$v.solicitud.estado.$invalid, 'invalid': $v.solicitud.estado.$invalid }" v-model="$v.solicitud.estado.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.solicitud.asunto')" for="solicitud-asunto">Asunto</label>
                        <input type="text" class="form-control" name="asunto" id="solicitud-asunto"
                            :class="{'valid': !$v.solicitud.asunto.$invalid, 'invalid': $v.solicitud.asunto.$invalid }" v-model="$v.solicitud.asunto.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.solicitud.textoSolicitud')" for="solicitud-textoSolicitud">Texto Solicitud</label>
                        <input type="text" class="form-control" name="textoSolicitud" id="solicitud-textoSolicitud"
                            :class="{'valid': !$v.solicitud.textoSolicitud.$invalid, 'invalid': $v.solicitud.textoSolicitud.$invalid }" v-model="$v.solicitud.textoSolicitud.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.solicitud.fechaSolicitud')" for="solicitud-fechaSolicitud">Fecha Solicitud</label>
                        <div class="input-group">
                            <input id="solicitud-fechaSolicitud" type="date" class="form-control" name="fechaSolicitud"  :class="{'valid': !$v.solicitud.fechaSolicitud.$invalid, 'invalid': $v.solicitud.fechaSolicitud.$invalid }"
                            v-model="$v.solicitud.fechaSolicitud.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.solicitud.solicitudIntegranteProyecto')" for="solicitud-solicitudIntegranteProyecto">Solicitud Integrante Proyecto</label>
                        <select class="form-control" id="solicitud-solicitudIntegranteProyecto" name="solicitudIntegranteProyecto" v-model="solicitud.solicitudIntegranteProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="integranteProyectoOption.id" v-for="integranteProyectoOption in integranteProyectos" :key="integranteProyectoOption.id">{{integranteProyectoOption.integrante}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.solicitud.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./solicitud-update.component.ts">
</script>
