<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="adjuntoProyectoFase">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.adjuntoProyectoFase.detail.title')">AdjuntoProyectoFase</span> {{adjuntoProyectoFase.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreAdjunto')">Nombre Adjunto</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.nombreAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaCreacion')">Fecha Creacion</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaCreacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaModificacion')">Fecha Modificacion</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaModificacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.estadoAdjunto')">Estado Adjunto</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.estadoAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFase')">Adjunto Proyecto Fase</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.adjuntoProyectoFase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreArchivoOriginal')">Nombre Archivo Original</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.nombreArchivoOriginal}}</span>
                    </dd>
                     <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')">Archivo</span>
                    </dt>
                    <dd>
                        <div v-if="adjuntoProyectoFase.archivo">
                            <a v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.archivo)" v-text="$t('entity.action.open')">open</a>
                            {{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.archivo)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaInicio')">Fecha Inicio</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaFin')">Fecha Fin</span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFaseFase')">Adjunto Proyecto Fase Proyecto Fase</span>
                    </dt>
                    <dd>
                        <div v-if="adjuntoProyectoFase.adjuntoProyectoFaseFaseId">
                            <router-link :to="{name: 'ProyectoFaseView', params: {adjuntoProyectoFaseFaseId: adjuntoProyectoFase.adjuntoProyectoFaseFaseId}}">{{adjuntoProyectoFase.adjuntoProyectoFaseFaseFase}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="adjuntoProyectoFase.id" :to="{name: 'AdjuntoProyectoFaseEdit', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-proyecto-fase-details.component.ts">
</script>
