<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="formato">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.formato.detail.title')">Formato</span> {{formato.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.formato.formato')">Formato</span>
                    </dt>
                    <dd>
                        <span>{{formato.formato}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.formato.version')">Version</span>
                    </dt>
                    <dd>
                        <span>{{formato.version}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.formato.codigo')">Codigo</span>
                    </dt>
                    <dd>
                        <span>{{formato.codigo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.formato.fecha')">Fecha</span>
                    </dt>
                    <dd>
                        <span>{{formato.fecha}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="formato.id" :to="{name: 'FormatoEdit', params: {formatoId: formato.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./formato-details.component.ts">
</script>
