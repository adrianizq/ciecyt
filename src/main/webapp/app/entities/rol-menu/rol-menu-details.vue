<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rolMenu">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.rolMenu.detail.title')"></span> {{rolMenu.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.rolMenu.permitirAcceso')"></span>
                    </dt>
                    <dd>
                        <span>{{rolMenu.permitirAcceso}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolMenu.permitirCrear')"></span>
                    </dt>
                    <dd>
                        <span>{{rolMenu.permitirCrear}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolMenu.permitirEditar')"></span>
                    </dt>
                    <dd>
                        <span>{{rolMenu.permitirEditar}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolMenu.permitirEliminar')"></span>
                    </dt>
                    <dd>
                        <span>{{rolMenu.permitirEliminar}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolMenu.authName')"></span>
                    </dt>
                    <dd>
                        <span>{{rolMenu.authName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.rolMenu.rolMenuMenu')"></span>
                    </dt>
                    <dd>
                        <div v-if="rolMenu.rolMenuMenuId">
                            <router-link :to="{name: 'MenuView', params: {rolMenuMenuId: rolMenu.rolMenuMenuId}}">{{rolMenu.rolMenuMenuNombre}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="rolMenu.id" :to="{name: 'RolMenuEdit', params: {rolMenuId: rolMenu.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rol-menu-details.component.ts">
</script>
