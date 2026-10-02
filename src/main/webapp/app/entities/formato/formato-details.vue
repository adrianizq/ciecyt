<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="formato">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.formato.detail.title')"></span> {{formato.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.formato.formato')"></span>
                    </dt>
                    <dd>
                        <span>{{formato.formato}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.formato.version')"></span>
                    </dt>
                    <dd>
                        <span>{{formato.version}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.formato.codigo')"></span>
                    </dt>
                    <dd>
                        <span>{{formato.codigo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.formato.fecha')"></span>
                    </dt>
                    <dd>
                        <span>{{formato.fecha}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="formato.id" :to="{name: 'FormatoEdit', params: {formatoId: formato.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./formato-details.component.ts">
</script>
