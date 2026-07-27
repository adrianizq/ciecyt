<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="informacionPasantia">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytVueApp.informacionPasantia.detail.title')">InformacionPasantia</span> {{informacionPasantia.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.duracionHoras')">Duracion Horas</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.duracionHoras}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.direccion')">Direccion</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.direccion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.email')">Email</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.lunes')">Lunes</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.lunes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.martes')">Martes</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.martes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.miercoles')">Miercoles</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.miercoles}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.jueves')">Jueves</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.jueves}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.viernes')">Viernes</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.viernes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.sabado')">Sabado</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.sabado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.domingo')">Domingo</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.domingo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.horasMes')">Horas Mes</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.horasMes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.bonoAlimenticio')">Bono Alimenticio</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.bonoAlimenticio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.apoyoEconomico')">Apoyo Economico</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.apoyoEconomico}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.auxilioTransporte')">Auxilio Transporte</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.auxilioTransporte}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.capacitacion')">Capacitacion</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.capacitacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.otroApoyo')">Otro Apoyo</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.otroApoyo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.nombreEmpresa')">Nombre Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.nombreEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.nitEmpresa')">Nit Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.nitEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.direccionEmpresa')">Direccion Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.direccionEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.sectorEconomicoEmpresa')">Sector Economico Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.sectorEconomicoEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.representanteLegalEmpresa')">Representante Legal Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.representanteLegalEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.asesorEmpresa')">Asesor Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.asesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.cargoAsesorEmpresa')">Cargo Asesor Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.cargoAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.emailAsesorEmpresa')">Email Asesor Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.emailAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.municipioEmpresa')">Municipio Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.municipioEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.telefonoContactoEmpresa')">Telefono Contacto Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.telefonoContactoEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.emailEmpresa')">Email Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.emailEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.departamentoEmpresa')">Departamento Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.departamentoEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.identificacionRepresentanteLegal')">Identificacion Representante Legal</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.identificacionRepresentanteLegal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.profesionAsesorEmpresa')">Profesion Asesor Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.profesionAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.celularAsesorEmpresa')">Celular Asesor Empresa</span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.celularAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.informacionPasantiaProyecto')">Informacion Pasantia Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="informacionPasantia.informacionPasantiaProyecto">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoId: informacionPasantia.informacionPasantiaProyecto.id}}">{{informacionPasantia.informacionPasantiaProyecto.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="informacionPasantia.id" :to="{name: 'InformacionPasantiaEdit', params: {informacionPasantiaId: informacionPasantia.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./informacion-pasantia-details.component.ts">
</script>
