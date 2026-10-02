<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.cronogramaCiecyt.home.title')" id="cronograma-ciecyt-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCiecytCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cronograma-ciecyt" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.cronogramaCiecyt.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && cronogramaCiecyts && cronogramaCiecyts.length === 0">
            <span v-text="$t('ciecytApp.cronogramaCiecyt.home.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="cronogramaCiecyts && cronogramaCiecyts.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('tituloCronograma')"><span v-text="$t('ciecytApp.cronogramaCiecyt.tituloCronograma')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.cronogramaCiecyt.fechaInicio')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.cronogramaCiecyt.fechaFin')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('observaciones')"><span v-text="$t('ciecytApp.cronogramaCiecyt.observaciones')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaCiecytModalidadModalidad')"><span v-text="$t('ciecytApp.cronogramaCiecyt.cronogramaCiecytModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cronogramaCiecyt in cronogramaCiecyts"
                    :key="cronogramaCiecyt.id">
                    <td>
                        <router-link :to="{name: 'CronogramaCiecytView', params: {cronogramaCiecytId: cronogramaCiecyt.id}}">{{cronogramaCiecyt.id}}</router-link>
                    </td>
                    <td>{{cronogramaCiecyt.tituloCronograma}}</td>
                    <td>{{cronogramaCiecyt.fechaInicio}}</td>
                    <td>{{cronogramaCiecyt.fechaFin}}</td>
                    <td>{{cronogramaCiecyt.observaciones}}</td>
                    <td>
                        <div v-if="cronogramaCiecyt.cronogramaCiecytModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {cronogramaCiecytModalidadId: cronogramaCiecyt.cronogramaCiecytModalidadId}}">{{cronogramaCiecyt.cronogramaCiecytModalidadModalidad}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCiecytView', params: {cronogramaCiecytId: cronogramaCiecyt.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCiecytEdit', params: {cronogramaCiecytId: cronogramaCiecyt.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(cronogramaCiecyt)"
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
<span ><span id="ciecytApp.cronogramaCiecyt.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-cronogramaCiecyt-heading" v-bind:title="$t('ciecytApp.cronogramaCiecyt.delete.question')">Are you sure you want to delete this Cronograma Ciecyt?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cronogramaCiecyt" v-text="$t('entity.action.delete')" v-on:click="removeCronogramaCiecyt()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="cronogramaCiecyts && cronogramaCiecyts.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma-ciecyt.component.ts">
</script>
