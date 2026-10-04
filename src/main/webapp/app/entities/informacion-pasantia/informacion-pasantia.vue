<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytVueApp.informacionPasantia.home.title')" id="informacion-pasantia-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'InformacionPasantiaCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-informacion-pasantia" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytVueApp.informacionPasantia.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && informacionPasantias && informacionPasantias.length === 0" :titulo="$t('ciecytVueApp.informacionPasantia.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="informacionPasantias && informacionPasantias.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><span v-text="$t('global.field.id')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.duracionHoras')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.direccion')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.email')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.lunes')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.martes')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.miercoles')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.jueves')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.viernes')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.sabado')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.domingo')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.horasMes')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.bonoAlimenticio')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.apoyoEconomico')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.auxilioTransporte')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.capacitacion')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.otroApoyo')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.nombreEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.nitEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.direccionEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.sectorEconomicoEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.representanteLegalEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.asesorEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.cargoAsesorEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.emailAsesorEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.municipioEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.telefonoContactoEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.emailEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.departamentoEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.identificacionRepresentanteLegal')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.profesionAsesorEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.celularAsesorEmpresa')"></span></th>
                    <th scope="col"><span v-text="$t('ciecytVueApp.informacionPasantia.informacionPasantiaProyecto')"></span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="informacionPasantia in informacionPasantias"
                    :key="informacionPasantia.id">
                    <td>
                        <router-link :to="{name: 'InformacionPasantiaView', params: {informacionPasantiaId: informacionPasantia.id}}">{{informacionPasantia.id}}</router-link>
                    </td>
                    <td>{{informacionPasantia.duracionHoras}}</td>
                    <td>{{informacionPasantia.direccion}}</td>
                    <td>{{informacionPasantia.email}}</td>
                    <td>{{informacionPasantia.lunes}}</td>
                    <td>{{informacionPasantia.martes}}</td>
                    <td>{{informacionPasantia.miercoles}}</td>
                    <td>{{informacionPasantia.jueves}}</td>
                    <td>{{informacionPasantia.viernes}}</td>
                    <td>{{informacionPasantia.sabado}}</td>
                    <td>{{informacionPasantia.domingo}}</td>
                    <td>{{informacionPasantia.horasMes}}</td>
                    <td>{{informacionPasantia.bonoAlimenticio}}</td>
                    <td>{{informacionPasantia.apoyoEconomico}}</td>
                    <td>{{informacionPasantia.auxilioTransporte}}</td>
                    <td>{{informacionPasantia.capacitacion}}</td>
                    <td>{{informacionPasantia.otroApoyo}}</td>
                    <td>{{informacionPasantia.nombreEmpresa}}</td>
                    <td>{{informacionPasantia.nitEmpresa}}</td>
                    <td>{{informacionPasantia.direccionEmpresa}}</td>
                    <td>{{informacionPasantia.sectorEconomicoEmpresa}}</td>
                    <td>{{informacionPasantia.representanteLegalEmpresa}}</td>
                    <td>{{informacionPasantia.asesorEmpresa}}</td>
                    <td>{{informacionPasantia.cargoAsesorEmpresa}}</td>
                    <td>{{informacionPasantia.emailAsesorEmpresa}}</td>
                    <td>{{informacionPasantia.municipioEmpresa}}</td>
                    <td>{{informacionPasantia.telefonoContactoEmpresa}}</td>
                    <td>{{informacionPasantia.emailEmpresa}}</td>
                    <td>{{informacionPasantia.departamentoEmpresa}}</td>
                    <td>{{informacionPasantia.identificacionRepresentanteLegal}}</td>
                    <td>{{informacionPasantia.profesionAsesorEmpresa}}</td>
                    <td>{{informacionPasantia.celularAsesorEmpresa}}</td>
                    <td>
                        <div v-if="informacionPasantia.informacionPasantiaProyecto">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoId: informacionPasantia.informacionPasantiaProyecto.id}}">{{informacionPasantia.informacionPasantiaProyecto.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'InformacionPasantiaView', params: {informacionPasantiaId: informacionPasantia.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'InformacionPasantiaEdit', params: {informacionPasantiaId: informacionPasantia.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(informacionPasantia)"
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
<span ><span id="ciecytVueApp.informacionPasantia.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-informacionPasantia-heading" v-text="$t('ciecytVueApp.informacionPasantia.delete.question', {'id': removeId})"></p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-informacionPasantia" v-text="$t('entity.action.delete')" v-on:click="removeInformacionPasantia()"></button>
            </div>
</template>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./informacion-pasantia.component.ts">
</script>
