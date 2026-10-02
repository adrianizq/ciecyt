<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.pregunta.home.title')" id="pregunta-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'PreguntaCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-pregunta" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.pregunta.home.createLabel')"></span>
            </button></router-link>
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
        <div class="alert alert-warning" v-if="!isFetching && preguntas && preguntas.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="preguntas && preguntas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('encabezado')"><span v-text="$t('ciecytApp.pregunta.encabezado')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.pregunta.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('pregunta')"><span v-text="$t('ciecytApp.pregunta.pregunta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preguntaTipoPreguntaTipoPregunta')"><span v-text="$t('ciecytApp.pregunta.preguntaTipoPregunta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preguntaModalidadModalidad')"><span v-text="$t('ciecytApp.pregunta.preguntaModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preguntaRolesModalidadRol')"><span v-text="$t('ciecytApp.pregunta.preguntaRolesModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('elemento')"><span v-text="$t('ciecytApp.pregunta.elemento')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('puntajeMaximo')"><span v-text="$t('ciecytApp.pregunta.puntajeMaximo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="pregunta in preguntas"
                    :key="pregunta.id">
                    <td>
                        <router-link :to="{name: 'PreguntaView', params: {preguntaId: pregunta.id}}">{{pregunta.id}}</router-link>
                    </td>
                    <td>{{pregunta.encabezado}}</td>
                    <td>{{pregunta.descripcion}}</td>
                    <td>{{pregunta.pregunta}}</td>
                    <td>
                        <div v-if="pregunta.preguntaTipoPreguntaId">
                            <router-link :to="{name: 'TipoPreguntaView', params: {tipoPreguntaId: pregunta.preguntaTipoPreguntaId}}">{{pregunta.preguntaTipoPreguntaTipoPregunta}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="pregunta.preguntaModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {modalidadId: pregunta.preguntaModalidadId}}">{{pregunta.preguntaModalidadModalidad}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="pregunta.preguntaRolesModalidadId">
                            <router-link :to="{name: 'RolesModalidadView', params: {rolesModalidadId: pregunta.preguntaRolesModalidadId}}">{{pregunta.preguntaRolesModalidadRol}}</router-link>
                        </div>
                    </td>
                     <td>
                        <div v-if="pregunta.preguntaElementoId">
                            <router-link :to="{name: 'ElementoView', params: {elementoId: pregunta.preguntaElementoId}}">{{pregunta.preguntaElementoId}}</router-link>
                        </div>
                    </td>
                     <td>{{pregunta.puntajeMaximo}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'PreguntaView', params: {preguntaId: pregunta.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'PreguntaEdit', params: {preguntaId: pregunta.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(pregunta)"
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
<span ><span id="ciecytApp.pregunta.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-pregunta-heading" v-bind:title="$t('ciecytApp.pregunta.delete.question')">Are you sure you want to delete this Pregunta?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-pregunta" v-text="$t('entity.action.delete')" v-on:click="removePregunta()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="preguntas && preguntas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./pregunta.component.ts">
</script>
