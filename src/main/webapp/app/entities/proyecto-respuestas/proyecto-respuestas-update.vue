<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.proyectoRespuestas.home.createOrEditLabel" v-text="$t('ciecytApp.proyectoRespuestas.home.createOrEditLabel')">Create or edit a ProyectoRespuestas</h2>
                <div>
                    <div class="form-group" v-if="proyectoRespuestas.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="proyectoRespuestas.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.respuesta')" for="proyecto-respuestas-respuesta">Respuesta</label>
                        <select class="form-control" name="respuesta" :class="{'valid': !$v.proyectoRespuestas.respuesta.$invalid, 'invalid': $v.proyectoRespuestas.respuesta.$invalid }" v-model="$v.proyectoRespuestas.respuesta.$model" id="proyecto-respuestas-respuesta" >
                            <option value="CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.CUMPLE')">CUMPLE</option>
                            <option value="NO_CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_CUMPLE')">NO_CUMPLE</option>
                            <option value="NO_APLICA" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_APLICA')">NO_APLICA</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.observaciones')" for="proyecto-respuestas-observaciones">Observaciones</label>
                        <input type="text" class="form-control" name="observaciones" id="proyecto-respuestas-observaciones"
                            :class="{'valid': !$v.proyectoRespuestas.observaciones.$invalid, 'invalid': $v.proyectoRespuestas.observaciones.$invalid }" v-model="$v.proyectoRespuestas.observaciones.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.viable')" for="proyecto-respuestas-viable">Viable</label>
                        <input type="checkbox" class="form-check" name="viable" id="proyecto-respuestas-viable"
                            :class="{'valid': !$v.proyectoRespuestas.viable.$invalid, 'invalid': $v.proyectoRespuestas.viable.$invalid }" v-model="$v.proyectoRespuestas.viable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasPregunta')" for="proyecto-respuestas-proyectoRespuestasPregunta">Proyecto Respuestas Pregunta</label>
                        <select class="form-control" id="proyecto-respuestas-proyectoRespuestasPregunta" name="proyectoRespuestasPregunta" v-model="proyectoRespuestas.proyectoRespuestasPreguntaId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="preguntaOption.id" v-for="preguntaOption in preguntas" :key="preguntaOption.id">{{preguntaOption.pregunta}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasProyecto')" for="proyecto-respuestas-proyectoRespuestasProyecto">Proyecto Respuestas Proyecto</label>
                        <select class="form-control" id="proyecto-respuestas-proyectoRespuestasProyecto" name="proyectoRespuestasProyecto" v-model="proyectoRespuestas.proyectoRespuestasProyectoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="proyectoOption.id" v-for="proyectoOption in proyectos" :key="proyectoOption.id">{{proyectoOption.titulo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.proyectoRespuestas.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./proyecto-respuestas-update.component.ts">
</script>
