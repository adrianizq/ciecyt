<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="adjuntoProyectoFase">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.adjuntoProyectoFase.detail.title')"></span> {{adjuntoProyectoFase.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreAdjunto')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.nombreAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaCreacion')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaCreacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaModificacion')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaModificacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.estadoAdjunto')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.estadoAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFase')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.adjuntoProyectoFase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreArchivoOriginal')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.nombreArchivoOriginal}}</span>
                    </dd>
                     <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')"></span>
                    </dt>
                    <dd>
                        <div v-if="adjuntoProyectoFase.archivo">
                            <a v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.archivo)" v-text="$t('entity.action.open')"></a>
                            {{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.archivo)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaInicio')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaFin')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoProyectoFase.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFaseFase')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="adjuntoProyectoFase.id" :to="{name: 'AdjuntoProyectoFaseEdit', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-proyecto-fase-details.component.ts">
</script>
