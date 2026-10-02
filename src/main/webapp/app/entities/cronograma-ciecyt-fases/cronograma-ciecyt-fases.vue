<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.cronogramaCiecytFases.home.title')" id="cronograma-ciecyt-fases-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCiecytFasesCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cronograma-ciecyt-fases" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.cronogramaCiecytFases.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && cronogramaCiecytFases && cronogramaCiecytFases.length === 0">
            <span v-text="$t('ciecytApp.cronogramaCiecytFases.home.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="cronogramaCiecytFases && cronogramaCiecytFases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('inicioFase')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.inicioFase')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('finFase')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.finFase')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('textoExplicativo')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.textoExplicativo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaCiecytFasesCronogramaCiecytTituloCronograma')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecyt')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaCiecytFasesFasesFase')"><span v-text="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesFases')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCiecytFasesView', params: {cronogramaCiecytFasesId: cronogramaCiecytFases.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCiecytFasesEdit', params: {cronogramaCiecytFasesId: cronogramaCiecytFases.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(cronogramaCiecytFases)"
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
<span ><span id="ciecytApp.cronogramaCiecytFases.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-cronogramaCiecytFases-heading" v-bind:title="$t('ciecytApp.cronogramaCiecytFases.delete.question')">Are you sure you want to delete this Cronograma Ciecyt Fases?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cronogramaCiecytFases" v-text="$t('entity.action.delete')" v-on:click="removeCronogramaCiecytFases()"></button>
            </div>
</template>
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
