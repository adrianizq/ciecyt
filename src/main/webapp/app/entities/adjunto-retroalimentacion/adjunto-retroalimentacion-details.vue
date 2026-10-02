<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="adjuntoRetroalimentacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.detail.title')"></span> {{adjuntoRetroalimentacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreAdjunto')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.nombreAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaCreacion')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaCreacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaModificacion')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaModificacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.estadoAdjunto')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.estadoAdjunto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacion')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.adjuntoRetroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreArchivoOriginal')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.nombreArchivoOriginal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaInicio')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaFin')"></span>
                    </dt>
                    <dd>
                        <span>{{adjuntoRetroalimentacion.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacion')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="adjuntoRetroalimentacion.id" :to="{name: 'AdjuntoRetroalimentacionEdit', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-retroalimentacion-details.component.ts">
</script>
