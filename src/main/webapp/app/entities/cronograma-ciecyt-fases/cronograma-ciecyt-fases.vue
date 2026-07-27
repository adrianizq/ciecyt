<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.cronogramaCiecytFases.home.title')" id="cronograma-ciecyt-fases-heading">Cronograma Ciecyt Fases</span>
            <router-link :to="{name: 'CronogramaCiecytFasesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cronograma-ciecyt-fases">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.cronogramaCiecytFases.home.createLabel')">
                    Create a new Cronograma Ciecyt Fases
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && cronogramaCiecytFases && cronogramaCiecytFases.length === 0">
            <span v-text="$t('ciecytApp.cronogramaCiecytFases.home.notFound')">No cronogramaCiecytFases found</span>
        </div>
        <div class="table-responsive" v-if="cronogramaCiecytFases && cronogramaCiecytFases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('inicioFase')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.inicioFase')">Inicio Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('finFase')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.finFase')">Fin Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('textoExplicativo')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.textoExplicativo')">Texto Explicativo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaCiecytFasesCronogramaCiecytTituloCronograma')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecyt')">Cronograma Ciecyt Fases Cronograma Ciecyt</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaCiecytFasesFasesFase')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesFases')">Cronograma Ciecyt Fases Fases</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cronogramaCiecytFases in cronogramaCiecytFases"
                    :key="cronogramaCiecytFases.id">
                    <td>
                        <router-link :to="{name: 'CronogramaCiecytFasesView', params: {cronogramaCiecytFasesId: cronogramaCiecytFases.id}}">{{cronogramaCiecytFases.id}}</router-link>
                    </td>
                    <td>{{cronogramaCiecytFases.inicioFase}}</td>
                    <td>{{cronogramaCiecytFases.finFase}}</td>
                    <td>{{cronogramaCiecytFases.textoExplicativo}}</td>
                    <td>
                        <div v-if="cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytId">
                            <router-link :to="{name: 'CronogramaCiecytView', params: {cronogramaCiecytFasesCronogramaCiecytId: cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytId}}">{{cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytTituloCronograma}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cronogramaCiecytFases.cronogramaCiecytFasesFasesId">
                            <router-link :to="{name: 'FasesView', params: {cronogramaCiecytFasesFasesId: cronogramaCiecytFases.cronogramaCiecytFasesFasesId}}">{{cronogramaCiecytFases.cronogramaCiecytFasesFasesFase}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CronogramaCiecytFasesView', params: {cronogramaCiecytFasesId: cronogramaCiecytFases.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CronogramaCiecytFasesEdit', params: {cronogramaCiecytFasesId: cronogramaCiecytFases.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cronogramaCiecytFases)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="ciecytApp.cronogramaCiecytFases.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cronogramaCiecytFases-heading" v-bind:title="$t('ciecytApp.cronogramaCiecytFases.delete.question')">Are you sure you want to delete this Cronograma Ciecyt Fases?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cronogramaCiecytFases" v-text="$t('entity.action.delete')" v-on:click="removeCronogramaCiecytFases()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cronogramaCiecytFases && cronogramaCiecytFases.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma-ciecyt-fases.component.ts">
</script>
