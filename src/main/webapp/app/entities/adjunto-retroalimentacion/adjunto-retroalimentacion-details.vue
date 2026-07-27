<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="adjuntoRetroalimentacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.detail.title')">AdjuntoRetroalimentacion</span> {{adjuntoRetroalimentacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreAdjunto')">Nombre Adjunto</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.nombreAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaCreacion')">Fecha Creacion</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaCreacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaModificacion')">Fecha Modificacion</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaModificacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.estadoAdjunto')">Estado Adjunto</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.estadoAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacion')">Adjunto Retroalimentacion</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.adjuntoRetroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreArchivoOriginal')">Nombre Archivo Original</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.nombreArchivoOriginal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaInicio')">Fecha Inicio</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaFin')">Fecha Fin</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacion')">Adjunto Retroalimentacion Retroalimentacion</span>
                    </dt>
                    <dd>
                        <div v-if="adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionId">
                            <router-link :to="{name: 'RetroalimentacionView', params: {adjuntoRetroalimentacionRetroalimentacionId: adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionId}}">{{adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="adjuntoRetroalimentacion.id" :to="{name: 'AdjuntoRetroalimentacionEdit', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-retroalimentacion-details.component.ts">
</script>
