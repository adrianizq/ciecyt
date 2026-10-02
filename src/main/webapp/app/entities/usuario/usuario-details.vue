<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="usuario">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.usuario.detail.title')"></span> {{usuario.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.usuario.usuario')"></span>
                    </dt>
                    <dd>
                        <span>{{usuario.usuario}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.usuario.descripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{usuario.descripcion}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="usuario.id" :to="{name: 'UsuarioEdit', params: {usuarioId: usuario.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./usuario-details.component.ts">
</script>
