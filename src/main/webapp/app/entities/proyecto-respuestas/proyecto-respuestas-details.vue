<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="proyectoRespuestas">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.proyectoRespuestas.detail.title')">ProyectoRespuestas</span> {{proyectoRespuestas.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.respuesta')">Respuesta</span>
                    </dt>
                    <dd>
                        <span v-text="$t('ciecytApp.EnumRespuestas.' + proyectoRespuestas.respuesta)">{{proyectoRespuestas.respuesta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.observaciones')">Observaciones</span>
                    </dt>
                    <dd>
                        <span>{{proyectoRespuestas.observaciones}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.viable')">Viable</span>
                    </dt>
                    <dd>
                        <span>{{proyectoRespuestas.viable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasPregunta')">Proyecto Respuestas Pregunta</span>
                    </dt>
                    <dd>
                        <div v-if="proyectoRespuestas.proyectoRespuestasPreguntaId">
                            <router-link :to="{name: 'PreguntaView', params: {proyectoRespuestasPreguntaId: proyectoRespuestas.proyectoRespuestasPreguntaId}}">{{proyectoRespuestas.proyectoRespuestasPreguntaPregunta}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasProyecto')">Proyecto Respuestas Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="proyectoRespuestas.proyectoRespuestasProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoRespuestasProyectoId: proyectoRespuestas.proyectoRespuestasProyectoId}}">{{proyectoRespuestas.proyectoRespuestasProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="proyectoRespuestas.id" :to="{name: 'ProyectoRespuestasEdit', params: {proyectoRespuestasId: proyectoRespuestas.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-respuestas-details.component.ts">
</script>
