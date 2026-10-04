<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.municipio.home.title')" id="municipio-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'MunicipioCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-municipio" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.municipio.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && municipios && municipios.length === 0" :titulo="$t('ciecytApp.general.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="municipios && municipios.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'region' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('region')" v-on:keydown.enter="changeOrder('region')" v-on:keydown.space.prevent="changeOrder('region')"><span v-text="$t('ciecytApp.municipio.region')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'codigoDaneDepartamento' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('codigoDaneDepartamento')" v-on:keydown.enter="changeOrder('codigoDaneDepartamento')" v-on:keydown.space.prevent="changeOrder('codigoDaneDepartamento')"><span v-text="$t('ciecytApp.municipio.codigoDaneDepartamento')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'departamento' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('departamento')" v-on:keydown.enter="changeOrder('departamento')" v-on:keydown.space.prevent="changeOrder('departamento')"><span v-text="$t('ciecytApp.municipio.departamento')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'codigoDaneMunicipio' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('codigoDaneMunicipio')" v-on:keydown.enter="changeOrder('codigoDaneMunicipio')" v-on:keydown.space.prevent="changeOrder('codigoDaneMunicipio')"><span v-text="$t('ciecytApp.municipio.codigoDaneMunicipio')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'municipio' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('municipio')" v-on:keydown.enter="changeOrder('municipio')" v-on:keydown.space.prevent="changeOrder('municipio')"><span v-text="$t('ciecytApp.municipio.municipio')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="municipio in municipios"
                    :key="municipio.id">
                    <td>
                        <router-link :to="{name: 'MunicipioView', params: {municipioId: municipio.id}}">{{municipio.id}}</router-link>
                    </td>
                    <td>{{municipio.id}}</td>
                    <td>{{municipio.region}}</td>
                    <td>{{municipio.codigoDaneDepartamento}}</td>
                    <td>{{municipio.departamento}}</td>
                    <td>{{municipio.codigoDaneMunicipio}}</td>
                    <td>{{municipio.municipio}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'MunicipioView', params: {municipioId: municipio.codigoDaneMunicipio}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'MunicipioEdit', params: {municipioId: municipio.codigoDaneMunicipio}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(municipio)"
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
<span ><span id="ciecytApp.municipio.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-municipio-heading" v-bind:title="$t('ciecytApp.municipio.delete.question')">Are you sure you want to delete this Municipio?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-municipio" v-text="$t('entity.action.delete')" v-on:click="removeMunicipio()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="municipios && municipios.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./municipio.component.ts">
</script>
