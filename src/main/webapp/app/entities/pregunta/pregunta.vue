<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.pregunta.home.title')" id="pregunta-heading">Preguntas</span>
            <router-link :to="{name: 'PreguntaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-pregunta">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.pregunta.home.createLabel')">
                    Create a new Pregunta
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
        <div class="alert alert-warning" v-if="!isFetching && preguntas && preguntas.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')">No preguntas found</span>
        </div>
        <div class="table-responsive" v-if="preguntas && preguntas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('encabezado')"><span v-text="$t('ciecytApp.pregunta.encabezado')">Encabezado</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.pregunta.descripcion')">Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('pregunta')"><span v-text="$t('ciecytApp.pregunta.pregunta')">Pregunta</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preguntaTipoPreguntaTipoPregunta')"><span v-text="$t('ciecytApp.pregunta.preguntaTipoPregunta')">Pregunta Tipo Pregunta</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preguntaModalidadModalidad')"><span v-text="$t('ciecytApp.pregunta.preguntaModalidad')">Pregunta Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preguntaRolesModalidadRol')"><span v-text="$t('ciecytApp.pregunta.preguntaRolesModalidad')">Pregunta Roles Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('elemento')"><span v-text="$t('ciecytApp.pregunta.elemento')">Elemento</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('puntajeMaximo')"><span v-text="$t('ciecytApp.pregunta.puntajeMaximo')">Puntaje Maximo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link :to="{name: 'PreguntaView', params: {preguntaId: pregunta.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PreguntaEdit', params: {preguntaId: pregunta.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(pregunta)"
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
            <span slot="modal-title"><span id="ciecytApp.pregunta.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-pregunta-heading" v-bind:title="$t('ciecytApp.pregunta.delete.question')">Are you sure you want to delete this Pregunta?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-pregunta" v-text="$t('entity.action.delete')" v-on:click="removePregunta()">Delete</button>
            </div>
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
