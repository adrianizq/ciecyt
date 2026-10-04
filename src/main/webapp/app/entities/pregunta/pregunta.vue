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
        <jhi-loading v-if="isFetching"></jhi-loading>
        <jhi-empty v-if="!isFetching && preguntas && preguntas.length === 0" :titulo="$t('ciecytApp.general.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="preguntas && preguntas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'encabezado' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('encabezado')" v-on:keydown.enter="changeOrder('encabezado')" v-on:keydown.space.prevent="changeOrder('encabezado')"><span v-text="$t('ciecytApp.pregunta.encabezado')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'descripcion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('descripcion')" v-on:keydown.enter="changeOrder('descripcion')" v-on:keydown.space.prevent="changeOrder('descripcion')"><span v-text="$t('ciecytApp.pregunta.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'pregunta' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('pregunta')" v-on:keydown.enter="changeOrder('pregunta')" v-on:keydown.space.prevent="changeOrder('pregunta')"><span v-text="$t('ciecytApp.pregunta.pregunta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'preguntaTipoPreguntaTipoPregunta' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('preguntaTipoPreguntaTipoPregunta')" v-on:keydown.enter="changeOrder('preguntaTipoPreguntaTipoPregunta')" v-on:keydown.space.prevent="changeOrder('preguntaTipoPreguntaTipoPregunta')"><span v-text="$t('ciecytApp.pregunta.preguntaTipoPregunta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'preguntaModalidadModalidad' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('preguntaModalidadModalidad')" v-on:keydown.enter="changeOrder('preguntaModalidadModalidad')" v-on:keydown.space.prevent="changeOrder('preguntaModalidadModalidad')"><span v-text="$t('ciecytApp.pregunta.preguntaModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'preguntaRolesModalidadRol' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('preguntaRolesModalidadRol')" v-on:keydown.enter="changeOrder('preguntaRolesModalidadRol')" v-on:keydown.space.prevent="changeOrder('preguntaRolesModalidadRol')"><span v-text="$t('ciecytApp.pregunta.preguntaRolesModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'elemento' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('elemento')" v-on:keydown.enter="changeOrder('elemento')" v-on:keydown.space.prevent="changeOrder('elemento')"><span v-text="$t('ciecytApp.pregunta.elemento')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'puntajeMaximo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('puntajeMaximo')" v-on:keydown.enter="changeOrder('puntajeMaximo')" v-on:keydown.space.prevent="changeOrder('puntajeMaximo')"><span v-text="$t('ciecytApp.pregunta.puntajeMaximo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'PreguntaView', params: {preguntaId: pregunta.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'PreguntaEdit', params: {preguntaId: pregunta.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(pregunta)"
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
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./pregunta.component.ts">
</script>
