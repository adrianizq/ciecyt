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
            <b-alert v-if="esCiecyt()" show variant="info" class="mt-2">
                <strong>Modo lectura.</strong> Esta vista le entrega al CIECYT las remisiones
                que la decanatura ya envío, para que sepa qué padrón es el vigente al momento de
                asignar jurado y asesor.
            </b-alert>
        </div>

        <div class="col-12" v-if="!facultadId">
            <b-alert show variant="warning">
                <template v-if="puedeEditarRemision()">
                    No tiene ninguna facultad asignada como decano. Pida al administrador que lo
                    habilite en <em>Decanos por facultad</em>. Mientras tanto el admin puede armar la
                    remisión en esta página.
                </template>
                <template v-else>
                    Aún no hay remisiones remitidas al CIECYT. Vuelvas a entrar cuando la
                    decanatura arme la primera remisión.
                </template>
            </b-alert>
        </div>

        <div class="col-12" v-else>
            <div class="col-12 col-md-6" v-if="puedeEditarRemision()">
                <b-form-group class="mb-3" label="Facultad" label-for="facultad-remision" v-if="facultadesDisponibles.length > 1">
                    <b-form-select id="facultad-remision" v-model="facultadId" :options="facultadesDisponibles" @change="cargar()"></b-form-select>
                </b-form-group>
                <b-form-group class="mb-3" label="Periodo académico" label-for="periodo">
                    <b-form-input id="periodo" v-model="nuevoPeriodo" placeholder="por ejemplo 2026-1"></b-form-input>
                </b-form-group>
                <b-form-group class="mb-3" label="Observaciones" label-for="obs-remision">
                    <b-form-input id="obs-remision" v-model="nuevasObservaciones"></b-form-input>
                </b-form-group>
                <b-button variant="primary" :disabled="isSaving || !nuevoPeriodo" @click="crearBorrador()">
                    <font-awesome-icon icon="file"></font-awesome-icon>&nbsp;Armar borrador
                </b-button>
            </div>
            <div class="col-12" v-else>
                <b-form-group class="mb-3" label="Facultad" label-for="facultad-remision" v-if="facultadesDisponibles.length > 1">
                    <b-form-select id="facultad-remision" v-model="facultadId" :options="facultadesDisponibles" @change="cargar()"></b-form-select>
                </b-form-group>
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
                        <b-button v-if="puedeEditarRemision() && item.estado !== 'ENVIADO'" size="sm" variant="outline-primary" @click="enviar(item)">
                            Enviar a CIECYT
                        </b-button>
                    </template>
                </b-table>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue } from 'vue-facing-decorator';

    import AlertService from '@/shared/alert/alert.service';
    import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
import FacultadService from '@/entities/facultad/facultad.service';
    import RemisionPadronService from '@/entities/remision-padron/remision-padron.service';
    import { IRemisionPadron, SolicitudRemision } from '@/shared/model/remision-padron.model';

    @Component
    export default class RemisionPadron extends Vue {
        @Inject  private alertService: () => AlertService;
        @Inject  private decanoFacultadService: () => DecanoFacultadService;
        @Inject  private remisionPadronService: () => RemisionPadronService;
        @Inject  private facultadService: () => FacultadService;

        public facultadId: number = null;
        public facultadesDisponibles: any[] = [];
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
                if (ids.length > 0) {
                    this.facultadId = ids[0];
                    this.facultadesDisponibles = ids.map(id => ({ value: id, text: 'Facultad ' + id }));
                    await this.cargar();
                    return;
                }
                // admin del sistema: listamos todas las facultades y el operador elige.
                if (this.isAdministrador()) {
                    await this.cargarTodasLasFacultades();
                    return;
                }
            } catch (e) {
                this.facultadId = null;
            }
        }

        public isAdministrador(): boolean {
            const account: any = this.$store?.getters?.account;
            const auths: string[] = account?.authorities || [];
            return auths.includes('ROLE_ADMIN');
        }

        /**
         * Solo decano de la facultad y admin pueden armar/editar remisiones. El CIECYT entra a
         * esta vista como consulta del histórico de lo remitido por la decanatura.
         */
        public puedeEditarRemision(): boolean {
            const account: any = this.$store?.getters?.account;
            const auths: string[] = account?.authorities || [];
            return auths.includes('ROLE_ADMIN') || auths.includes('ROLE_DECANO');
        }

        public esCiecyt(): boolean {
            const account: any = this.$store?.getters?.account;
            const auths: string[] = account?.authorities || [];
            return auths.includes('ROLE_CIECYT') && !this.puedeEditarRemision();
        }

        async cargarTodasLasFacultades(): Promise<void> {
            try {
                const res = await this.facultadService().retrieve({ sort: 'facultad,asc', size: 200 });
                const lista: any[] = (res.data && res.data) || [];
                this.facultadesDisponibles = lista
                    .filter((f: any) => f && f.id != null)
                    .map((f: any) => ({ value: f.id, text: `${f.codigoFacultad || ''} · ${f.facultad || ''}` }));
                if (this.facultadesDisponibles.length > 0) {
                    this.facultadId = this.facultadesDisponibles[0].value;
                    await this.cargar();
                }
            } catch (e) {
                // sin facultades o sin permisos: se queda el mensaje del alert del template
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
