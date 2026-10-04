<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.lineaInvestigacion.home.title')" id="linea-investigacion-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'LineaInvestigacionCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-linea-investigacion" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.lineaInvestigacion.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && lineaInvestigacions && lineaInvestigacions.length === 0" :titulo="$t('ciecytApp.general.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="lineaInvestigacions && lineaInvestigacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'linea' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('linea')" v-on:keydown.enter="changeOrder('linea')" v-on:keydown.space.prevent="changeOrder('linea')"><span v-text="$t('ciecytApp.lineaInvestigacion.linea')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'codigoLinea' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('codigoLinea')" v-on:keydown.enter="changeOrder('codigoLinea')" v-on:keydown.space.prevent="changeOrder('codigoLinea')"><span v-text="$t('ciecytApp.lineaInvestigacion.codigoLinea')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'lineaPadreLinea' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('lineaPadreLinea')" v-on:keydown.enter="changeOrder('lineaPadreLinea')" v-on:keydown.space.prevent="changeOrder('lineaPadreLinea')"><span v-text="$t('ciecytApp.lineaInvestigacion.lineaPadre')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'lineaInvestigacionProgramaPrograma' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('lineaInvestigacionProgramaPrograma')" v-on:keydown.enter="changeOrder('lineaInvestigacionProgramaPrograma')" v-on:keydown.space.prevent="changeOrder('lineaInvestigacionProgramaPrograma')"><span v-text="$t('ciecytApp.lineaInvestigacion.lineaInvestigacionPrograma')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="lineaInvestigacion in lineaInvestigacions"
                    :key="lineaInvestigacion.id">
                    <td>
                        <router-link :to="{name: 'LineaInvestigacionView', params: {lineaInvestigacionId: lineaInvestigacion.id}}">{{lineaInvestigacion.id}}</router-link>
                    </td>
                    <td>{{lineaInvestigacion.linea}}</td>
                    <td>{{lineaInvestigacion.codigoLinea}}</td>
                    <td>
                        <div v-if="lineaInvestigacion.lineaPadreId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {lineaPadreId: lineaInvestigacion.lineaPadreId}}">{{lineaInvestigacion.lineaPadreLinea}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="lineaInvestigacion.lineaInvestigacionProgramaId">
                            <router-link :to="{name: 'ProgramaView', params: { programaId: lineaInvestigacion.lineaInvestigacionProgramaId}}">{{lineaInvestigacion.lineaInvestigacionProgramaPrograma}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'LineaInvestigacionView', params: {lineaInvestigacionId: lineaInvestigacion.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'LineaInvestigacionEdit', params: {lineaInvestigacionId: lineaInvestigacion.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(lineaInvestigacion)"
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
<span ><span id="ciecytApp.lineaInvestigacion.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-lineaInvestigacion-heading" v-bind:title="$t('ciecytApp.lineaInvestigacion.delete.question')">Are you sure you want to delete this Linea Investigacion?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-lineaInvestigacion" v-text="$t('entity.action.delete')" v-on:click="removeLineaInvestigacion()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="lineaInvestigacions && lineaInvestigacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./linea-investigacion.component.ts">
</script>
