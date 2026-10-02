<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="informacionPasantia">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytVueApp.informacionPasantia.detail.title')"></span> {{informacionPasantia.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.duracionHoras')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.duracionHoras}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.direccion')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.direccion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.email')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.lunes')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.lunes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.martes')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.martes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.miercoles')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.miercoles}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.jueves')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.jueves}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.viernes')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.viernes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.sabado')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.sabado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.domingo')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.domingo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.horasMes')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.horasMes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.bonoAlimenticio')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.bonoAlimenticio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.apoyoEconomico')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.apoyoEconomico}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.auxilioTransporte')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.auxilioTransporte}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.capacitacion')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.capacitacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.otroApoyo')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.otroApoyo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.nombreEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.nombreEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.nitEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.nitEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.direccionEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.direccionEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.sectorEconomicoEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.sectorEconomicoEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.representanteLegalEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.representanteLegalEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.asesorEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.asesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.cargoAsesorEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.cargoAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.emailAsesorEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.emailAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.municipioEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.municipioEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.telefonoContactoEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.telefonoContactoEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.emailEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.emailEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.departamentoEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.departamentoEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.identificacionRepresentanteLegal')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.identificacionRepresentanteLegal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.profesionAsesorEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.profesionAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.celularAsesorEmpresa')"></span>
                    </dt>
                    <dd>
                        <span>{{informacionPasantia.celularAsesorEmpresa}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytVueApp.informacionPasantia.informacionPasantiaProyecto')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="informacionPasantia.id" :to="{name: 'InformacionPasantiaEdit', params: {informacionPasantiaId: informacionPasantia.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./informacion-pasantia-details.component.ts">
</script>
