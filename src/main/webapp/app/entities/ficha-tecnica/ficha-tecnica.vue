<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.fichaTecnica.home.title')" id="ficha-tecnica-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'FichaTecnicaCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ficha-tecnica" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.fichaTecnica.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && fichaTecnicas && fichaTecnicas.length === 0" :titulo="$t('ciecytApp.fichaTecnica.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="fichaTecnicas && fichaTecnicas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'tituloProfesional' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('tituloProfesional')" v-on:keydown.enter="changeOrder('tituloProfesional')" v-on:keydown.space.prevent="changeOrder('tituloProfesional')"><span v-text="$t('ciecytApp.fichaTecnica.tituloProfesional')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'tituloPostgrado' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('tituloPostgrado')" v-on:keydown.enter="changeOrder('tituloPostgrado')" v-on:keydown.space.prevent="changeOrder('tituloPostgrado')"><span v-text="$t('ciecytApp.fichaTecnica.tituloPostgrado')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'experiencia' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('experiencia')" v-on:keydown.enter="changeOrder('experiencia')" v-on:keydown.space.prevent="changeOrder('experiencia')"><span v-text="$t('ciecytApp.fichaTecnica.experiencia')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fichaTecnicaUserLogin' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fichaTecnicaUserLogin')" v-on:keydown.enter="changeOrder('fichaTecnicaUserLogin')" v-on:keydown.space.prevent="changeOrder('fichaTecnicaUserLogin')"><span v-text="$t('ciecytApp.fichaTecnica.fichaTecnicaUser')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="fichaTecnica in fichaTecnicas"
                    :key="fichaTecnica.id">
                    <td>
                        <router-link :to="{name: 'FichaTecnicaView', params: {fichaTecnicaId: fichaTecnica.id}}">{{fichaTecnica.id}}</router-link>
                    </td>
                    <td>{{fichaTecnica.tituloProfesional}}</td>
                    <td>{{fichaTecnica.tituloPostgrado}}</td>
                    <td>{{fichaTecnica.experiencia}}</td>
                    <td>
                        {{fichaTecnica.fichaTecnicaUserLogin}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'FichaTecnicaView', params: {fichaTecnicaId: fichaTecnica.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'FichaTecnicaEdit', params: {fichaTecnicaId: fichaTecnica.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(fichaTecnica)"
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
<span ><span id="ciecytApp.fichaTecnica.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-fichaTecnica-heading" v-bind:title="$t('ciecytApp.fichaTecnica.delete.question')">Are you sure you want to delete this Ficha Tecnica?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-fichaTecnica" v-text="$t('entity.action.delete')" v-on:click="removeFichaTecnica()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="fichaTecnicas && fichaTecnicas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ficha-tecnica.component.ts">
</script>
