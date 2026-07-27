<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.presupuestoValor.home.title')" id="presupuesto-valor-heading">Presupuesto Valors</span>
            <router-link :to="{name: 'PresupuestoValorCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-presupuesto-valor">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.presupuestoValor.home.createLabel')">
                    Create a new Presupuesto Valor
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
        <div class="alert alert-warning" v-if="!isFetching && presupuestoValors && presupuestoValors.length === 0">
            <span v-text="$t('ciecytApp.presupuestoValor.home.notFound')">No presupuestoValors found</span>
        </div>
        <div class="table-responsive" v-if="presupuestoValors && presupuestoValors.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.presupuestoValor.descripcion')">Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('justificacion')"><span v-text="$t('ciecytApp.presupuestoValor.justificacion')">Justificacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cantidad')"><span v-text="$t('ciecytApp.presupuestoValor.cantidad')">Cantidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valorUnitario')"><span v-text="$t('ciecytApp.presupuestoValor.valorUnitario')">Valor Unitario</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('especie')"><span v-text="$t('ciecytApp.presupuestoValor.especie')">Especie</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('dinero')"><span v-text="$t('ciecytApp.presupuestoValor.dinero')">Dinero</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ordenVista')"><span v-text="$t('ciecytApp.presupuestoValor.ordenVista')">Orden Vista</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('presupuestoValorRubroRubro')"><span v-text="$t('ciecytApp.presupuestoValor.presupuestoValorRubro')">Presupuesto Valor Rubro</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('presupuestoValorProyectoTitulo')"><span v-text="$t('ciecytApp.presupuestoValor.presupuestoValorProyecto')">Presupuesto Valor Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('presupuestoValorEntidadEntidad')"><span v-text="$t('ciecytApp.presupuestoValor.presupuestoValorEntidad')">Presupuesto Valor Entidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="presupuestoValor in presupuestoValors"
                    :key="presupuestoValor.id">
                    <td>
                        <router-link :to="{name: 'PresupuestoValorView', params: {presupuestoValorId: presupuestoValor.id}}">{{presupuestoValor.id}}</router-link>
                    </td>
                    <td>{{presupuestoValor.descripcion}}</td>
                    <td>{{presupuestoValor.justificacion}}</td>
                    <td>{{presupuestoValor.cantidad}}</td>
                    <td>{{presupuestoValor.valorUnitario}}</td>
                    <td>{{presupuestoValor.especie}}</td>
                    <td>{{presupuestoValor.dinero}}</td>
                    <td>{{presupuestoValor.ordenVista}}</td>
                    <td>
                        <div v-if="presupuestoValor.presupuestoValorRubroId">
                            <router-link :to="{name: 'RubroView', params: {presupuestoValorRubroId: presupuestoValor.presupuestoValorRubroId}}">{{presupuestoValor.presupuestoValorRubroRubro}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="presupuestoValor.presupuestoValorProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {presupuestoValorProyectoId: presupuestoValor.presupuestoValorProyectoId}}">{{presupuestoValor.presupuestoValorProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="presupuestoValor.presupuestoValorEntidadId">
                            <router-link :to="{name: 'EntidadView', params: {presupuestoValorEntidadId: presupuestoValor.presupuestoValorEntidadId}}">{{presupuestoValor.presupuestoValorEntidadEntidad}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PresupuestoValorView', params: {presupuestoValorId: presupuestoValor.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PresupuestoValorEdit', params: {presupuestoValorId: presupuestoValor.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(presupuestoValor)"
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
            <span slot="modal-title"><span id="ciecytApp.presupuestoValor.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-presupuestoValor-heading" v-bind:title="$t('ciecytApp.presupuestoValor.delete.question')">Are you sure you want to delete this Presupuesto Valor?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-presupuestoValor" v-text="$t('entity.action.delete')" v-on:click="removePresupuestoValor()">Delete</button>
            </div>
        </b-modal>
        <div v-show="presupuestoValors && presupuestoValors.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./presupuesto-valor.component.ts">
</script>
