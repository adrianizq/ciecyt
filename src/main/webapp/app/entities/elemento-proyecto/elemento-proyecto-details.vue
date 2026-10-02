<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="elementoProyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.elementoProyecto.detail.title')"></span> {{elementoProyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.dato')"></span>
                    </dt>
                    <dd>
                        <span>{{elementoProyecto.dato}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyectoDescripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{elementoProyecto.elementoProyectoProyectoDescripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyecto')"></span>
                    </dt>
                    <dd>
                        <div v-if="elementoProyecto.elementoProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {elementoProyectoProyectoId: elementoProyecto.elementoProyectoProyectoId}}">{{elementoProyecto.elementoProyectoProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="elementoProyecto.id" :to="{name: 'ElementoProyectoEdit', params: {elementoProyectoId: elementoProyecto.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./elemento-proyecto-details.component.ts">
</script>
