<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rolesModalidad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.rolesModalidad.detail.title')"></span> {{rolesModalidad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.rolesModalidad.rol')"></span>
                    </dt>
                    <dd>
                        <span>{{rolesModalidad.rol}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolesModalidad.cantidad')"></span>
                    </dt>
                    <dd>
                        <span>{{rolesModalidad.cantidad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolesModalidad.calificador')"></span>
                    </dt>
                    <dd>
                        <span>{{rolesModalidad.calificador}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolesModalidad.rolesModalidadModalidad')"></span>
                    </dt>
                    <dd>
                        <div v-if="rolesModalidad.rolesModalidadModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {rolesModalidadModalidadId: rolesModalidad.rolesModalidadModalidadId}}">{{rolesModalidad.rolesModalidadModalidadModalidad}}</router-link>
                        </div>
                    </dd>

                    <!------------------------------------>
                     <dt>
                        <span v-text="$t('ciecytApp.rolesModalidad.rolesModalidadAuthority')"></span>
                    </dt>
                    <dd>
                         <span>{{rolesModalidad.rolesModalidadAuthority}}</span>
                    </dd>
                    <!------------------------------------->
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="rolesModalidad.id" :to="{name: 'RolesModalidadEdit', params: {rolesModalidadId: rolesModalidad.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./roles-modalidad-details.component.ts">
</script>
