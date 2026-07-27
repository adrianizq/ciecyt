<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="menu">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.menu.detail.title')">Menu</span> {{menu.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.menu.nombre')">Nombre</span>
                    </dt>
                    <dd>
                        <span>{{menu.nombre}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.menu.url')">Url</span>
                    </dt>
                    <dd>
                        <span>{{menu.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.menu.icono')">Icono</span>
                    </dt>
                    <dd>
                        <span>{{menu.icono}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.menu.activo')">Activo</span>
                    </dt>
                    <dd>
                        <span>{{menu.activo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.menu.menuPadre')">Menu Padre</span>
                    </dt>
                    <dd>
                        <div v-if="menu.menuPadreId">
                            <router-link :to="{name: 'MenuView', params: {menuPadreId: menu.menuPadreId}}">{{menu.menuPadreNombre}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="menu.id" :to="{name: 'MenuEdit', params: {menuId: menu.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./menu-details.component.ts">
</script>
