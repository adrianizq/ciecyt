<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.elemento.home.title')" id="elemento-heading">Elementos</span>
            <router-link :to="{name: 'ElementoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-elemento">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.elemento.home.createLabel')">
                    Create a new Elemento
                </span>
            </router-link>
        </h2>

    
          <div class="col-md-3">
            <label  >Buscar por Fase: </label>
              <font-awesome-icon icon="search" :spin="isFetching"></font-awesome-icon>
               <select class="form-control" id="elemento-elementoFase" 
                  name="buscarFaseId" v-model="searchFaseId"
                  @change="retrieveSearchFaseId">
                 <option v-bind:value="null"></option>
                 <option v-bind:value="faseOption.id" v-for="faseOption in fases" :key="faseOption.id">{{faseOption.fase}}</option>
               </select>            
           </div>
    
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && elementos && elementos.length === 0">
            <span v-text="$t('ciecytApp.elemento.home.notFound')">No elementos found</span>
        </div>
        <div class="table-responsive" v-if="elementos && elementos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('elemento')"><span v-text="$t('ciecytApp.elemento.elemento')">Elemento</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.elemento.descripcion')">Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                   <!-- <th v-on:click="changeOrder('elementoFormatoFormato')"><span v-text="$t('ciecytApp.elemento.elementoFormato')">Elemento Formato</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>-->
                    <th v-on:click="changeOrder('elementoFasesFase')"><span v-text="$t('ciecytApp.elemento.elementoFase')">Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <!--<th v-on:click="changeOrder('modalidad')"><span v-text="$t('ciecytApp.elemento.modalidad')">Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>-->
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="elemento in elementos"
                    :key="elemento.id">
                    <td>
                        <router-link :to="{name: 'ElementoView', params: {elementoId: elemento.id}}">{{elemento.id}}</router-link>
                    </td>
                    <td>{{elemento.elemento}}</td>
                    <td>{{elemento.descripcion}}</td>
                    <!--<td>
                        <div v-if="elemento.elementoFormatoId">
                            <router-link :to="{name: 'FormatoView', params: {elementoFormatoId: elemento.elementoFormatoId}}">{{elemento.elementoFormatoFormato}}</router-link>
                        </div>
                    </td> -->
                    <td>{{elemento.elementoFasesFase}}</td>
                    <!--<td>
                        <div v-if="elemento.modalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {modalidadId: elemento.modalidadId}}">{{elemento.modalidadId}}</router-link>
                        </div>
                    </td>-->
                     
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ElementoView', params: {elementoId: elemento.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ElementoEdit', params: {elementoId: elemento.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            
                            <b-button v-on:click="prepareRemove(elemento)"
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
            <span slot="modal-title"><span id="ciecytApp.elemento.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-elemento-heading" v-bind:title="$t('ciecytApp.elemento.delete.question')">Are you sure you want to delete this Elemento?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-elemento" v-text="$t('entity.action.delete')" v-on:click="removeElemento()">Delete</button>
            </div>
        </b-modal>
        <div v-show="elementos && elementos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./elemento.component.ts">
</script>
