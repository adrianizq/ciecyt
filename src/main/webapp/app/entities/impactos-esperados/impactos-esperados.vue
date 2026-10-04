<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.impactosEsperados.home.title')" id="impactos-esperados-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ImpactosEsperadosCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-impactos-esperados" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.impactosEsperados.home.createLabel')"></span>
            </button></router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <jhi-loading v-if="isFetching"></jhi-loading>
        <jhi-empty v-if="!isFetching && impactosEsperados && impactosEsperados.length === 0" :titulo="$t('ciecytApp.impactosEsperados.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="impactosEsperados && impactosEsperados.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'impacto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('impacto')" v-on:keydown.enter="changeOrder('impacto')" v-on:keydown.space.prevent="changeOrder('impacto')"><span v-text="$t('ciecytApp.impactosEsperados.impacto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'plazo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('plazo')" v-on:keydown.enter="changeOrder('plazo')" v-on:keydown.space.prevent="changeOrder('plazo')"><span v-text="$t('ciecytApp.impactosEsperados.plazo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'indicador' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('indicador')" v-on:keydown.enter="changeOrder('indicador')" v-on:keydown.space.prevent="changeOrder('indicador')"><span v-text="$t('ciecytApp.impactosEsperados.indicador')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'supuestos' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('supuestos')" v-on:keydown.enter="changeOrder('supuestos')" v-on:keydown.space.prevent="changeOrder('supuestos')"><span v-text="$t('ciecytApp.impactosEsperados.supuestos')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'ordenVista' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('ordenVista')" v-on:keydown.enter="changeOrder('ordenVista')" v-on:keydown.space.prevent="changeOrder('ordenVista')"><span v-text="$t('ciecytApp.impactosEsperados.ordenVista')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'impactosEsperadoProyectoTitulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('impactosEsperadoProyectoTitulo')" v-on:keydown.enter="changeOrder('impactosEsperadoProyectoTitulo')" v-on:keydown.space.prevent="changeOrder('impactosEsperadoProyectoTitulo')"><span v-text="$t('ciecytApp.impactosEsperados.impactosEsperadoProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="impactosEsperados in impactosEsperados"
                    :key="impactosEsperados.id">
                    <td>
                        <router-link :to="{name: 'ImpactosEsperadosView', params: {impactosEsperadosId: impactosEsperados.id}}">{{impactosEsperados.id}}</router-link>
                    </td>
                    <td>{{impactosEsperados.impacto}}</td>
                    <td>{{impactosEsperados.plazo}}</td>
                    <td>{{impactosEsperados.indicador}}</td>
                    <td>{{impactosEsperados.supuestos}}</td>
                    <td>{{impactosEsperados.ordenVista}}</td>
                    <td>
                        <div v-if="impactosEsperados.impactosEsperadoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {impactosEsperadoProyectoId: impactosEsperados.impactosEsperadoProyectoId}}">{{impactosEsperados.impactosEsperadoProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ImpactosEsperadosView', params: {impactosEsperadosId: impactosEsperados.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ImpactosEsperadosEdit', params: {impactosEsperadosId: impactosEsperados.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(impactosEsperados)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')"></span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <template #modal-title>
<span ><span id="ciecytApp.impactosEsperados.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-impactosEsperados-heading" v-bind:title="$t('ciecytApp.impactosEsperados.delete.question')">Are you sure you want to delete this Impactos Esperados?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-impactosEsperados" v-text="$t('entity.action.delete')" v-on:click="removeImpactosEsperados()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="impactosEsperados && impactosEsperados.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./impactos-esperados.component.ts">
</script>
