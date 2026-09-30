<template>
    <div class="row">
        <div class="col-12">
            <h3>Remisión del padrón a CIECYT</h3>
            <p class="text-muted">
                El parágrafo 2 del artículo 8 y el del 9 obligan a remitir la relación al inicio de
                cada periodo académico. La remisión queda como constancia de qué se envió y cuándo.
                Una vez enviada no se edita: si después cambia el padrón, corresponde a la siguiente
                remisión.
            </p>
        </div>

        <div class="col-12" v-if="!facultadId">
            <b-alert show variant="warning">
                No tiene ninguna facultad asignada como decano.
            </b-alert>
        </div>

        <div class="col-12" v-else>
            <div class="col-12 col-md-6">
                <b-form-group label="Periodo académico" label-for="periodo">
                    <b-form-input id="periodo" v-model="nuevoPeriodo" placeholder="por ejemplo 2026-1"></b-form-input>
                </b-form-group>
                <b-form-group label="Observaciones" label-for="obs-remision">
                    <b-form-input id="obs-remision" v-model="nuevasObservaciones"></b-form-input>
                </b-form-group>
                <b-button variant="primary" :disabled="isSaving || !nuevoPeriodo" @click="crearBorrador()">
                    <font-awesome-icon icon="file"></font-awesome-icon>&nbsp;Armar borrador
                </b-button>
            </div>

            <div class="col-12">
                <h5>Remisiones</h5>
                <b-table :items="remisiones" :fields="campos" striped responsive small>
                    <template #empty>
                        <b class="text-muted">Todavía no se ha remitido el padrón de esta facultad.</b>
                    </template>
                    <template #cell(estado)="{ item }">
                        <b-badge :variant="item.estado === 'ENVIADO' ? 'success' : 'warning'">{{ item.estado }}</b-badge>
                    </template>
                    <template #cell(docentes)="{ item }">
                        <span v-if="item.cantidadAsesores !== undefined">
                            {{ item.cantidadAsesores }} asesor(es), {{ item.cantidadJurados }} jurado(s)
                        </span>
                    </template>
                    <template #cell(acciones)="{ item }">
                        <b-button v-if="item.estado !== 'ENVIADO'" size="sm" variant="outline-primary" @click="enviar(item)">
                            Enviar a CIECYT
                        </b-button>
                    </template>
                </b-table>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue } from 'vue-property-decorator';

    import AlertService from '@/shared/alert/alert.service';
    import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
    import RemisionPadronService from '@/entities/remision-padron/remision-padron.service';
    import { IRemisionPadron, SolicitudRemision } from '@/shared/model/remision-padron.model';

    @Component
    export default class RemisionPadron extends Vue {
        @Inject('alertService') private alertService: () => AlertService;
        @Inject('decanoFacultadService') private decanoFacultadService: () => DecanoFacultadService;
        @Inject('remisionPadronService') private remisionPadronService: () => RemisionPadronService;

        public facultadId: number = null;
        public remisiones: any[] = [];
        public nuevoPeriodo: string = '';
        public nuevasObservaciones: string = '';
        public isSaving = false;

        public campos: any[] = [
            { key: 'periodo', label: 'Periodo' },
            { key: 'estado', label: 'Estado' },
            { key: 'docentes', label: 'Docentes' },
            { key: 'remitidoPorNombre', label: 'Remitido por' },
            { key: 'fechaEnvio', label: 'Enviado' },
            { key: 'acciones', label: '' }
        ];

        created() {
            this.init();
        }

        async init() {
            try {
                const res = await this.decanoFacultadService().misFacultades();
                const ids: number[] = res.data || [];
                if (ids.length === 0) {
                    return;
                }
                this.facultadId = ids[0];
                await this.cargar();
            } catch (e) {
                this.facultadId = null;
            }
        }

        async cargar() {
            if (!this.facultadId) {
                return;
            }
            try {
                const res = await this.remisionPadronService().retrieveDeFacultad(this.facultadId);
                this.remisiones = (res.data || []).map((r: IRemisionPadron) => {
                    const remitente: any = (r as any).remitidoPor || {};
                    const facultad: any = (r as any).facultad || {};
                    const docentes: any[] = (r as any).docentes || [];
                    return {
                        id: r.id,
                        periodo: r.periodo,
                        estado: r.estado,
                        fechaEnvio: r.fechaEnvio,
                        remitidoPorNombre: ((remitente.firstName || '') + ' ' + (remitente.lastName || '')).trim() || remitente.login,
                        facultadNombre: facultad.facultad,
                        cantidadAsesores: docentes.filter(d => d.rol === 'ASESOR').length,
                        cantidadJurados: docentes.filter(d => d.rol === 'JURADO').length
                    };
                });
            } catch (e) {
                this.remisiones = [];
            }
        }

        async crearBorrador() {
            this.isSaving = true;
            try {
                const solicitud = new SolicitudRemision();
                solicitud.facultadId = this.facultadId;
                solicitud.periodo = this.nuevoPeriodo;
                solicitud.observaciones = this.nuevasObservaciones || null;
                await this.remisionPadronService().crearBorrador(solicitud);
                this.alertService().success('Borrador armado con la lista vigente');
                this.nuevoPeriodo = '';
                this.nuevasObservaciones = '';
                await this.cargar();
            } catch (e) {
                const msg = e && e.response && e.response.data && e.response.data.title
                    ? e.response.data.title
                    : 'No se pudo armar el borrador';
                this.alertService().error(msg);
            } finally {
                this.isSaving = false;
            }
        }

        async enviar(remision: any) {
            try {
                await this.remisionPadronService().enviar(remision.id, this.facultadId);
                this.alertService().success('Padrón remitido a CIECYT');
                await this.cargar();
            } catch (e) {
                const msg = e && e.response && e.response.data && e.response.data.title
                    ? e.response.data.title
                    : 'No se pudo enviar la remisión';
                this.alertService().error(msg);
            }
        }
    }
</script>
