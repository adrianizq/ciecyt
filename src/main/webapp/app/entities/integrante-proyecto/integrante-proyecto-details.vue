<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="integranteProyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.integranteProyecto.detail.title')"></span> {{integranteProyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integrante')"></span>
                    </dt>
                    <dd>
                        <span>{{integranteProyecto.integrante}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.descripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{integranteProyecto.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoUser')"></span>
                    </dt>
                    <dd>
                        {{integranteProyecto.integranteProyectoUserLogin}}
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoProyecto')"></span>
                    </dt>
                    <dd>
                        <div v-if="integranteProyecto.integranteProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {integranteProyectoProyectoId: integranteProyecto.integranteProyectoProyectoId}}">{{integranteProyecto.integranteProyectoProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoRolesModalidad')"></span>
                    </dt>
                    <dd>
                        <div v-if="integranteProyecto.integranteProyectoRolesModalidadId">
                            <router-link :to="{name: 'RolesModalidadView', params: {integranteProyectoRolesModalidadId: integranteProyecto.integranteProyectoRolesModalidadId}}">{{integranteProyecto.integranteProyectoRolesModalidadRol}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="integranteProyecto.id" :to="{name: 'IntegranteProyectoEdit', params: {integranteProyectoId: integranteProyecto.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./integrante-proyecto-details.component.ts">
</script>
