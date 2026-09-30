<template>
    <div class="row">
        <div class="col-12">
            <h3 id="titulo-padron">Docentes habilitados</h3>
            <p class="text-muted">
                Esta es la lista de la que el CIECYT designa asesores y jurados. El CIECYT consulta,
                pero de habilitarla se ocupa la decanatura. Quien sale de la lista conserva las
                designaciones que ya tiene.
            </p>
        </div>

        <div class="col-12" v-if="noFacultades">
            <b-alert show variant="warning">
                No tiene ninguna facultad asignada como decano. Pida que le asignen una para poder
                mantener la lista de habilitados.
            </b-alert>
        </div>

        <div class="col-12" v-else>
            <div class="col-12 col-md-4" v-if="facultades.length > 1">
                <b-form-group label="Facultad" label-for="facultad">
                    <b-form-select id="facultad" v-model="facultadId" :options="facultades" @change="cargar()"></b-form-select>
                </b-form-group>
            </div>

            <div class="col-12 col-md-4">
                <b-form-group label="Docentes con rol de" label-for="filtro-rol">
                    <b-form-select id="filtro-rol" v-model="filtroRol" :options="rolesDisponibles" @change="cargar()"></b-form-select>
                </b-form-group>
            </div>

            <div class="col-12">
                <b-table :items="habilitados" :fields="campos" striped responsive small>
                    <template #empty>
                        <b class="text-muted">No hay docentes habilitados en la facultad.</b>
                    </template>
                    <template #cell(acciones)="{ item }">
                        <b-button size="sm" variant="outline-secondary" @click="verHistorial(item)">
                            Historial
                        </b-button>
                        <b-button size="sm" variant="outline-danger" @click="cerrar(item)">
                            Dar de baja
                        </b-button>
                    </template>
                </b-table>
            </div>

            <div class="col-12">
                <h5>Dar de alta a un docente</h5>
                <b-form @submit.prevent="habilitar()">
                    <b-form-group label="Cédula o login" label-for="alta-login">
                        <b-form-input id="alta-login" v-model="nuevo.login" placeholder="cédula o login del docente"></b-form-input>
                    </b-form-group>
                    <b-form-group label="Rol" label-for="alta-rol">
                        <b-form-select id="alta-rol" v-model="nuevo.rol" :options="rolesDisponibles"></b-form-select>
                    </b-form-group>
                    <b-form-group label="Acto de resolución" label-for="alta-acto">
                        <b-form-input id="alta-acto" v-model="nuevo.actoResolucion" placeholder="número de resolución, si existe"></b-form-input>
                    </b-form-group>
                    <b-form-group label="Observaciones" label-for="alta-obs">
                        <b-form-input id="alta-obs" v-model="nuevo.observaciones"></b-form-input>
                    </b-form-group>
                    <b-button type="submit" variant="primary" :disabled="isSaving || !nuevo.login">
                        <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;Habilitar
                    </b-button>
                </b-form>
            </div>
        </div>

        <b-modal id="modal-historial" :title="tituloHistorial" ok-only size="lg">
            <b-table :items="historial" :fields="camposHistorial" striped responsive small>
                <template #empty>
                    <b class="text-muted">Sin habilitaciones registradas en esta facultad.</b>
                </template>
            </b-table>
        </b-modal>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue } from 'vue-property-decorator';

    import AlertService from '@/shared/alert/alert.service';
    import DocenteHabilitadoService from '@/entities/docente-habilitado/docente-habilitado.service';
    import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
    import FacultadService from '@/entities/facultad/facultad.service';
    import { IDocenteHabilitado, SolicitudHabilitacion } from '@/shared/model/docente-habilitado.model';

    @Component
    export default class PadronDocentesHabilitados extends Vue {
        @Inject('alertService') private alertService: () => AlertService;
        @Inject('docenteHabilitadoService') private docenteHabilitadoService: () => DocenteHabilitadoService;
        @Inject('decanoFacultadService') private decanoFacultadService: () => DecanoFacultadService;
        @Inject('facultadService') private facultadService: () => FacultadService;

        public facultades: any[] = [];
        public facultadId: number = null;
        public noFacultades = false;
        public habilitados: IDocenteHabilitado[] = [];
        public filtroRol: string = 'ASESOR';
        public isSaving = false;
        public nuevo: any = { login: '', rol: 'ASESOR', actoResolucion: '', observaciones: '' };

        public historial: any[] = [];
        public tituloHistorial: string = '';
        public camposHistorial: any[] = [
            { key: 'rol', label: 'Rol' },
            { key: 'fechaDesde', label: 'Desde' },
            { key: 'fechaHasta', label: 'Hasta' },
            { key: 'actoResolucion', label: 'Acto de resolución' },
            { key: 'observaciones', label: 'Observaciones' }
        ];

        public rolesDisponibles: any[] = [
            { value: 'ASESOR', text: 'Asesor' },
            { value: 'JURADO', text: 'Jurado' }
        ];

        public campos: any[] = [
            { key: 'docente', label: 'Docente' },
            { key: 'rol', label: 'Rol' },
            { key: 'fechaDesde', label: 'Desde' },
            { key: 'fechaHasta', label: 'Hasta' },
            { key: 'actoResolucion', label: 'Acto de resolución' },
            { key: 'acciones', label: '' }
        ];

        created() {
            this.init();
        }

        /**
         * La facultad no se recibe por parametro: la resuelve el backend con la sesion del decano,
         * para que cambiar el id en la barra de direcciones no abra el padron de otra facultad.
         */
        async init() {
            try {
                const res = await this.decanoFacultadService().misFacultades();
                const ids: number[] = res.data || [];
                if (ids.length === 0) {
                    this.noFacultades = true;
                    return;
                }
                for (const id of ids) {
                    try {
                        const f: any = await this.facultadService().find(id);
                        this.facultades.push({ value: f.id, text: f.facultad });
                    } catch (e) {
                        this.facultades.push({ value: id, text: 'Facultad ' + id });
                    }
                }
                this.facultadId = ids[0];
                await this.cargar();
            } catch (e) {
                this.noFacultades = true;
            }
        }

        async cargar() {
            if (!this.facultadId) {
                this.habilitados = [];
                return;
            }
            try {
                const res = await this.docenteHabilitadoService().retrieveDeFacultad(this.facultadId, this.filtroRol);
                this.habilitados = (res.data || []).map((h: IDocenteHabilitado) => {
                    const u: any = h.user || {};
                    return {
                        id: h.id,
                        userId: u.id,
                        docente: ((u.firstName || '') + ' ' + (u.lastName || '')).trim() || u.login,
                        login: u.login,
                        rol: h.rol,
                        fechaDesde: h.fechaDesde,
                        fechaHasta: h.fechaHasta,
                        actoResolucion: h.actoResolucion
                    };
                });
            } catch (e) {
                this.habilitados = [];
            }
        }

        async verHistorial(docente: any) {
            this.tituloHistorial = 'Historial de ' + docente.docente;
            this.historial = [];
            try {
                const res = await this.docenteHabilitadoService().retrieveHistorial(this.facultadId, docente.userId);
                this.historial = (res.data || []).map((h: IDocenteHabilitado) => ({
                    rol: h.rol,
                    fechaDesde: h.fechaDesde,
                    fechaHasta: h.fechaHasta,
                    actoResolucion: h.actoResolucion,
                    observaciones: h.observaciones
                }));
            } catch (e) {
                const msg = e && e.response && e.response.data && e.response.data.title
                    ? e.response.data.title
                    : 'No se pudo cargar el historial';
                this.alertService().error(msg);
                return;
            }
            (this.$bvModal as any).show('modal-historial');
        }

        /**
         * Se manda la cedula y la resuelve el backend. Si el docente no existe, el error lo dice
         * y no se crea nada, sin que el navegador tenga que consultar el directorio de usuarios.
         */
        async habilitar() {
            this.isSaving = true;
            try {
                const solicitud = new SolicitudHabilitacion();
                solicitud.login = this.nuevo.login;
                solicitud.facultadId = this.facultadId;
                solicitud.rol = this.nuevo.rol;
                solicitud.actoResolucion = this.nuevo.actoResolucion || null;
                solicitud.observaciones = this.nuevo.observaciones || null;

                await this.docenteHabilitadoService().habilitar(solicitud);
                this.alertService().success('Docente habilitado');
                this.nuevo = { login: '', rol: this.filtroRol, actoResolucion: '', observaciones: '' };
                await this.cargar();
            } catch (e) {
                const msg = e && e.response && e.response.data && e.response.data.title
                    ? e.response.data.title
                    : 'No se pudo habilitar al docente';
                this.alertService().error(msg);
            } finally {
                this.isSaving = false;
            }
        }

        /**
         * Cerrar la habilitacion no borra el registro: deja hasta cuando estuvo vigente. Quien ya
         * estaba designado conserva su designacion.
         */
        async cerrar(docente: any) {
            try {
                const solicitud = new SolicitudHabilitacion();
                solicitud.userId = docente.userId;
                solicitud.facultadId = this.facultadId;
                solicitud.rol = docente.rol;
                await this.docenteHabilitadoService().cerrar(solicitud);
                this.alertService().success('Habilitación cerrada');
                await this.cargar();
            } catch (e) {
                const msg = e && e.response && e.response.data && e.response.data.title
                    ? e.response.data.title
                    : 'No se pudo cerrar la habilitación';
                this.alertService().error(msg);
            }
        }
    }
</script>