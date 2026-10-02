<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytVueApp.informacionPasantia.home.createOrEditLabel" v-text="$t('ciecytVueApp.informacionPasantia.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="informacionPasantia.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="informacionPasantia.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.duracionHoras')" for="informacion-pasantia-duracionHoras"></label>
                        <input type="number" class="form-control" name="duracionHoras" id="informacion-pasantia-duracionHoras"
                            :class="{'valid': !v$.informacionPasantia.duracionHoras.$invalid, 'invalid': v$.informacionPasantia.duracionHoras.$invalid }" v-model.number="v$.informacionPasantia.duracionHoras.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.direccion')" for="informacion-pasantia-direccion"></label>
                        <input type="text" class="form-control" name="direccion" id="informacion-pasantia-direccion"
                            :class="{'valid': !v$.informacionPasantia.direccion.$invalid, 'invalid': v$.informacionPasantia.direccion.$invalid }" v-model="v$.informacionPasantia.direccion.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.email')" for="informacion-pasantia-email"></label>
                        <input type="text" class="form-control" name="email" id="informacion-pasantia-email"
                            :class="{'valid': !v$.informacionPasantia.email.$invalid, 'invalid': v$.informacionPasantia.email.$invalid }" v-model="v$.informacionPasantia.email.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.lunes')" for="informacion-pasantia-lunes"></label>
                        <input type="checkbox" class="form-check" name="lunes" id="informacion-pasantia-lunes"
                            :class="{'valid': !v$.informacionPasantia.lunes.$invalid, 'invalid': v$.informacionPasantia.lunes.$invalid }" v-model="v$.informacionPasantia.lunes.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.martes')" for="informacion-pasantia-martes"></label>
                        <input type="checkbox" class="form-check" name="martes" id="informacion-pasantia-martes"
                            :class="{'valid': !v$.informacionPasantia.martes.$invalid, 'invalid': v$.informacionPasantia.martes.$invalid }" v-model="v$.informacionPasantia.martes.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.miercoles')" for="informacion-pasantia-miercoles"></label>
                        <input type="checkbox" class="form-check" name="miercoles" id="informacion-pasantia-miercoles"
                            :class="{'valid': !v$.informacionPasantia.miercoles.$invalid, 'invalid': v$.informacionPasantia.miercoles.$invalid }" v-model="v$.informacionPasantia.miercoles.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.jueves')" for="informacion-pasantia-jueves"></label>
                        <input type="checkbox" class="form-check" name="jueves" id="informacion-pasantia-jueves"
                            :class="{'valid': !v$.informacionPasantia.jueves.$invalid, 'invalid': v$.informacionPasantia.jueves.$invalid }" v-model="v$.informacionPasantia.jueves.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.viernes')" for="informacion-pasantia-viernes"></label>
                        <input type="checkbox" class="form-check" name="viernes" id="informacion-pasantia-viernes"
                            :class="{'valid': !v$.informacionPasantia.viernes.$invalid, 'invalid': v$.informacionPasantia.viernes.$invalid }" v-model="v$.informacionPasantia.viernes.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.sabado')" for="informacion-pasantia-sabado"></label>
                        <input type="checkbox" class="form-check" name="sabado" id="informacion-pasantia-sabado"
                            :class="{'valid': !v$.informacionPasantia.sabado.$invalid, 'invalid': v$.informacionPasantia.sabado.$invalid }" v-model="v$.informacionPasantia.sabado.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.domingo')" for="informacion-pasantia-domingo"></label>
                        <input type="checkbox" class="form-check" name="domingo" id="informacion-pasantia-domingo"
                            :class="{'valid': !v$.informacionPasantia.domingo.$invalid, 'invalid': v$.informacionPasantia.domingo.$invalid }" v-model="v$.informacionPasantia.domingo.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.horasMes')" for="informacion-pasantia-horasMes"></label>
                        <input type="number" class="form-control" name="horasMes" id="informacion-pasantia-horasMes"
                            :class="{'valid': !v$.informacionPasantia.horasMes.$invalid, 'invalid': v$.informacionPasantia.horasMes.$invalid }" v-model.number="v$.informacionPasantia.horasMes.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.bonoAlimenticio')" for="informacion-pasantia-bonoAlimenticio"></label>
                        <input type="checkbox" class="form-check" name="bonoAlimenticio" id="informacion-pasantia-bonoAlimenticio"
                            :class="{'valid': !v$.informacionPasantia.bonoAlimenticio.$invalid, 'invalid': v$.informacionPasantia.bonoAlimenticio.$invalid }" v-model="v$.informacionPasantia.bonoAlimenticio.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.apoyoEconomico')" for="informacion-pasantia-apoyoEconomico"></label>
                        <input type="checkbox" class="form-check" name="apoyoEconomico" id="informacion-pasantia-apoyoEconomico"
                            :class="{'valid': !v$.informacionPasantia.apoyoEconomico.$invalid, 'invalid': v$.informacionPasantia.apoyoEconomico.$invalid }" v-model="v$.informacionPasantia.apoyoEconomico.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.auxilioTransporte')" for="informacion-pasantia-auxilioTransporte"></label>
                        <input type="checkbox" class="form-check" name="auxilioTransporte" id="informacion-pasantia-auxilioTransporte"
                            :class="{'valid': !v$.informacionPasantia.auxilioTransporte.$invalid, 'invalid': v$.informacionPasantia.auxilioTransporte.$invalid }" v-model="v$.informacionPasantia.auxilioTransporte.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.capacitacion')" for="informacion-pasantia-capacitacion"></label>
                        <input type="checkbox" class="form-check" name="capacitacion" id="informacion-pasantia-capacitacion"
                            :class="{'valid': !v$.informacionPasantia.capacitacion.$invalid, 'invalid': v$.informacionPasantia.capacitacion.$invalid }" v-model="v$.informacionPasantia.capacitacion.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.otroApoyo')" for="informacion-pasantia-otroApoyo"></label>
                        <input type="text" class="form-control" name="otroApoyo" id="informacion-pasantia-otroApoyo"
                            :class="{'valid': !v$.informacionPasantia.otroApoyo.$invalid, 'invalid': v$.informacionPasantia.otroApoyo.$invalid }" v-model="v$.informacionPasantia.otroApoyo.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.nombreEmpresa')" for="informacion-pasantia-nombreEmpresa"></label>
                        <input type="text" class="form-control" name="nombreEmpresa" id="informacion-pasantia-nombreEmpresa"
                            :class="{'valid': !v$.informacionPasantia.nombreEmpresa.$invalid, 'invalid': v$.informacionPasantia.nombreEmpresa.$invalid }" v-model="v$.informacionPasantia.nombreEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.nitEmpresa')" for="informacion-pasantia-nitEmpresa"></label>
                        <input type="text" class="form-control" name="nitEmpresa" id="informacion-pasantia-nitEmpresa"
                            :class="{'valid': !v$.informacionPasantia.nitEmpresa.$invalid, 'invalid': v$.informacionPasantia.nitEmpresa.$invalid }" v-model="v$.informacionPasantia.nitEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.direccionEmpresa')" for="informacion-pasantia-direccionEmpresa"></label>
                        <input type="text" class="form-control" name="direccionEmpresa" id="informacion-pasantia-direccionEmpresa"
                            :class="{'valid': !v$.informacionPasantia.direccionEmpresa.$invalid, 'invalid': v$.informacionPasantia.direccionEmpresa.$invalid }" v-model="v$.informacionPasantia.direccionEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.sectorEconomicoEmpresa')" for="informacion-pasantia-sectorEconomicoEmpresa"></label>
                        <input type="text" class="form-control" name="sectorEconomicoEmpresa" id="informacion-pasantia-sectorEconomicoEmpresa"
                            :class="{'valid': !v$.informacionPasantia.sectorEconomicoEmpresa.$invalid, 'invalid': v$.informacionPasantia.sectorEconomicoEmpresa.$invalid }" v-model="v$.informacionPasantia.sectorEconomicoEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.representanteLegalEmpresa')" for="informacion-pasantia-representanteLegalEmpresa"></label>
                        <input type="text" class="form-control" name="representanteLegalEmpresa" id="informacion-pasantia-representanteLegalEmpresa"
                            :class="{'valid': !v$.informacionPasantia.representanteLegalEmpresa.$invalid, 'invalid': v$.informacionPasantia.representanteLegalEmpresa.$invalid }" v-model="v$.informacionPasantia.representanteLegalEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.asesorEmpresa')" for="informacion-pasantia-asesorEmpresa"></label>
                        <input type="text" class="form-control" name="asesorEmpresa" id="informacion-pasantia-asesorEmpresa"
                            :class="{'valid': !v$.informacionPasantia.asesorEmpresa.$invalid, 'invalid': v$.informacionPasantia.asesorEmpresa.$invalid }" v-model="v$.informacionPasantia.asesorEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.cargoAsesorEmpresa')" for="informacion-pasantia-cargoAsesorEmpresa"></label>
                        <input type="text" class="form-control" name="cargoAsesorEmpresa" id="informacion-pasantia-cargoAsesorEmpresa"
                            :class="{'valid': !v$.informacionPasantia.cargoAsesorEmpresa.$invalid, 'invalid': v$.informacionPasantia.cargoAsesorEmpresa.$invalid }" v-model="v$.informacionPasantia.cargoAsesorEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.emailAsesorEmpresa')" for="informacion-pasantia-emailAsesorEmpresa"></label>
                        <input type="text" class="form-control" name="emailAsesorEmpresa" id="informacion-pasantia-emailAsesorEmpresa"
                            :class="{'valid': !v$.informacionPasantia.emailAsesorEmpresa.$invalid, 'invalid': v$.informacionPasantia.emailAsesorEmpresa.$invalid }" v-model="v$.informacionPasantia.emailAsesorEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.municipioEmpresa')" for="informacion-pasantia-municipioEmpresa"></label>
                        <input type="text" class="form-control" name="municipioEmpresa" id="informacion-pasantia-municipioEmpresa"
                            :class="{'valid': !v$.informacionPasantia.municipioEmpresa.$invalid, 'invalid': v$.informacionPasantia.municipioEmpresa.$invalid }" v-model="v$.informacionPasantia.municipioEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.telefonoContactoEmpresa')" for="informacion-pasantia-telefonoContactoEmpresa"></label>
                        <input type="text" class="form-control" name="telefonoContactoEmpresa" id="informacion-pasantia-telefonoContactoEmpresa"
                            :class="{'valid': !v$.informacionPasantia.telefonoContactoEmpresa.$invalid, 'invalid': v$.informacionPasantia.telefonoContactoEmpresa.$invalid }" v-model="v$.informacionPasantia.telefonoContactoEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.emailEmpresa')" for="informacion-pasantia-emailEmpresa"></label>
                        <input type="text" class="form-control" name="emailEmpresa" id="informacion-pasantia-emailEmpresa"
                            :class="{'valid': !v$.informacionPasantia.emailEmpresa.$invalid, 'invalid': v$.informacionPasantia.emailEmpresa.$invalid }" v-model="v$.informacionPasantia.emailEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.departamentoEmpresa')" for="informacion-pasantia-departamentoEmpresa"></label>
                        <input type="text" class="form-control" name="departamentoEmpresa" id="informacion-pasantia-departamentoEmpresa"
                            :class="{'valid': !v$.informacionPasantia.departamentoEmpresa.$invalid, 'invalid': v$.informacionPasantia.departamentoEmpresa.$invalid }" v-model="v$.informacionPasantia.departamentoEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.identificacionRepresentanteLegal')" for="informacion-pasantia-identificacionRepresentanteLegal"></label>
                        <input type="text" class="form-control" name="identificacionRepresentanteLegal" id="informacion-pasantia-identificacionRepresentanteLegal"
                            :class="{'valid': !v$.informacionPasantia.identificacionRepresentanteLegal.$invalid, 'invalid': v$.informacionPasantia.identificacionRepresentanteLegal.$invalid }" v-model="v$.informacionPasantia.identificacionRepresentanteLegal.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.profesionAsesorEmpresa')" for="informacion-pasantia-profesionAsesorEmpresa"></label>
                        <input type="text" class="form-control" name="profesionAsesorEmpresa" id="informacion-pasantia-profesionAsesorEmpresa"
                            :class="{'valid': !v$.informacionPasantia.profesionAsesorEmpresa.$invalid, 'invalid': v$.informacionPasantia.profesionAsesorEmpresa.$invalid }" v-model="v$.informacionPasantia.profesionAsesorEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.celularAsesorEmpresa')" for="informacion-pasantia-celularAsesorEmpresa"></label>
                        <input type="text" class="form-control" name="celularAsesorEmpresa" id="informacion-pasantia-celularAsesorEmpresa"
                            :class="{'valid': !v$.informacionPasantia.celularAsesorEmpresa.$invalid, 'invalid': v$.informacionPasantia.celularAsesorEmpresa.$invalid }" v-model="v$.informacionPasantia.celularAsesorEmpresa.$model" />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytVueApp.informacionPasantia.informacionPasantiaProyecto')" for="informacion-pasantia-informacionPasantiaProyecto"></label>
                        <select class="form-control" id="informacion-pasantia-informacionPasantiaProyecto" name="informacionPasantiaProyecto" v-model="informacionPasantia.informacionPasantiaProyecto">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="informacionPasantia.informacionPasantiaProyecto && proyectoOption.id === informacionPasantia.informacionPasantiaProyecto.id ? informacionPasantia.informacionPasantiaProyecto : proyectoOption" v-for="proyectoOption in informacionPasantiaProyectos" :key="proyectoOption.id">{{proyectoOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.informacionPasantia.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./informacion-pasantia-update.component.ts">
</script>
