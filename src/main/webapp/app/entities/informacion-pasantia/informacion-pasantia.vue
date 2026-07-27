<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytVueApp.informacionPasantia.home.title')" id="informacion-pasantia-heading">Informacion Pasantias</span>
            <router-link :to="{name: 'InformacionPasantiaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-informacion-pasantia">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytVueApp.informacionPasantia.home.createLabel')">
                    Create a new Informacion Pasantia
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
        <div class="alert alert-warning" v-if="!isFetching && informacionPasantias && informacionPasantias.length === 0">
            <span v-text="$t('ciecytVueApp.informacionPasantia.home.notFound')">No informacionPasantias found</span>
        </div>
        <div class="table-responsive" v-if="informacionPasantias && informacionPasantias.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.duracionHoras')">Duracion Horas</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.direccion')">Direccion</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.email')">Email</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.lunes')">Lunes</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.martes')">Martes</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.miercoles')">Miercoles</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.jueves')">Jueves</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.viernes')">Viernes</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.sabado')">Sabado</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.domingo')">Domingo</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.horasMes')">Horas Mes</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.bonoAlimenticio')">Bono Alimenticio</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.apoyoEconomico')">Apoyo Economico</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.auxilioTransporte')">Auxilio Transporte</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.capacitacion')">Capacitacion</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.otroApoyo')">Otro Apoyo</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.nombreEmpresa')">Nombre Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.nitEmpresa')">Nit Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.direccionEmpresa')">Direccion Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.sectorEconomicoEmpresa')">Sector Economico Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.representanteLegalEmpresa')">Representante Legal Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.asesorEmpresa')">Asesor Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.cargoAsesorEmpresa')">Cargo Asesor Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.emailAsesorEmpresa')">Email Asesor Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.municipioEmpresa')">Municipio Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.telefonoContactoEmpresa')">Telefono Contacto Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.emailEmpresa')">Email Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.departamentoEmpresa')">Departamento Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.identificacionRepresentanteLegal')">Identificacion Representante Legal</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.profesionAsesorEmpresa')">Profesion Asesor Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.celularAsesorEmpresa')">Celular Asesor Empresa</span></th>
                    <th><span v-text="$t('ciecytVueApp.informacionPasantia.informacionPasantiaProyecto')">Informacion Pasantia Proyecto</span></th>
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
                            <router-link :to="{name: 'InformacionPasantiaView', params: {informacionPasantiaId: informacionPasantia.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'InformacionPasantiaEdit', params: {informacionPasantiaId: informacionPasantia.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(informacionPasantia)"
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
            <span slot="modal-title"><span id="ciecytVueApp.informacionPasantia.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-informacionPasantia-heading" v-text="$t('ciecytVueApp.informacionPasantia.delete.question', {'id': removeId})">Are you sure you want to delete this Informacion Pasantia?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-informacionPasantia" v-text="$t('entity.action.delete')" v-on:click="removeInformacionPasantia()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./informacion-pasantia.component.ts">
</script>
