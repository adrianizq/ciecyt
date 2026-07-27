<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="integranteProyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.integranteProyecto.detail.title')">IntegranteProyecto</span> {{integranteProyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integrante')">Integrante</span>
                    </dt>
                    <dd>
                        <span>{{integranteProyecto.integrante}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.descripcion')">Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{integranteProyecto.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoUser')">Integrante Proyecto User</span>
                    </dt>
                    <dd>
                        {{integranteProyecto.integranteProyectoUserLogin}}
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoProyecto')">Integrante Proyecto Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="integranteProyecto.integranteProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {integranteProyectoProyectoId: integranteProyecto.integranteProyectoProyectoId}}">{{integranteProyecto.integranteProyectoProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoRolesModalidad')">Integrante Proyecto Roles Modalidad</span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="integranteProyecto.id" :to="{name: 'IntegranteProyectoEdit', params: {integranteProyectoId: integranteProyecto.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./integrante-proyecto-details.component.ts">
</script>
