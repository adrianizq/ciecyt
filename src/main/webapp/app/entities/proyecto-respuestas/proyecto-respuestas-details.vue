<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="proyectoRespuestas">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.proyectoRespuestas.detail.title')"></span> {{proyectoRespuestas.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.respuesta')"></span>
                    </dt>
                    <dd>
                        <span v-text="$t('ciecytApp.EnumRespuestas.' + proyectoRespuestas.respuesta)"></span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.observaciones')"></span>
                    </dt>
                    <dd>
                        <span>{{proyectoRespuestas.observaciones}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.viable')"></span>
                    </dt>
                    <dd>
                        <span>{{proyectoRespuestas.viable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasPregunta')"></span>
                    </dt>
                    <dd>
                        <div v-if="proyectoRespuestas.proyectoRespuestasPreguntaId">
                            <router-link :to="{name: 'PreguntaView', params: {proyectoRespuestasPreguntaId: proyectoRespuestas.proyectoRespuestasPreguntaId}}">{{proyectoRespuestas.proyectoRespuestasPreguntaPregunta}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasProyecto')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="proyectoRespuestas.id" :to="{name: 'ProyectoRespuestasEdit', params: {proyectoRespuestasId: proyectoRespuestas.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-respuestas-details.component.ts">
</script>
