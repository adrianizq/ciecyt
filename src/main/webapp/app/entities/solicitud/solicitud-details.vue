<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="solicitud">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.solicitud.detail.title')">Solicitud</span> {{solicitud.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.solicitud.estado')">Estado</span>
                    </dt>
                    <dd>
                        <span>{{solicitud.estado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.solicitud.asunto')">Asunto</span>
                    </dt>
                    <dd>
                        <span>{{solicitud.asunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.solicitud.textoSolicitud')">Texto Solicitud</span>
                    </dt>
                    <dd>
                        <span>{{solicitud.textoSolicitud}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.solicitud.fechaSolicitud')">Fecha Solicitud</span>
                    </dt>
                    <dd>
                        <span>{{solicitud.fechaSolicitud}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.solicitud.solicitudIntegranteProyecto')">Solicitud Integrante Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="solicitud.solicitudIntegranteProyectoId">
                            <router-link :to="{name: 'IntegranteProyectoView', params: {solicitudIntegranteProyectoId: solicitud.solicitudIntegranteProyectoId}}">{{solicitud.solicitudIntegranteProyectoIntegrante}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="solicitud.id" :to="{name: 'SolicitudEdit', params: {solicitudId: solicitud.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./solicitud-details.component.ts">
</script>
