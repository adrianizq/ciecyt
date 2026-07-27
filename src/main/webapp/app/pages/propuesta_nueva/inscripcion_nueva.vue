<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-nueva :proyectoId='$route.params.proyectoId'></menu-lateral-nueva>
        </div>
        <div class="col-sm-8">
            <div class="page-header mb-4">
                <h4 style="color:#003366; font-weight:600;">
                    <font-awesome-icon icon="file-alt" />&nbsp; Resumen Inscripción
                </h4>
                <p class="text-muted mb-0" style="font-size:0.85rem;">
                    Su propuesta ha sido registrada exitosamente. A continuación se presenta un resumen de la información.
                </p>
            </div>

            <div v-if="proyecto" class="resumen-card">
                <div class="resumen-section">
                    <h5 class="section-title">
                        <font-awesome-icon icon="info-circle" />&nbsp; Información del Proyecto
                    </h5>
                    <div class="info-grid">
                        <div class="info-item">
                            <span class="info-label">Título</span>
                            <span class="info-value">{{ proyecto.titulo }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Modalidad</span>
                            <span class="info-value">{{ proyecto.proyectoModalidadModalidad }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Línea de Investigación</span>
                            <span class="info-value">{{ proyecto.proyectoLineaInvestigacionLinea }}</span>
                        </div>
                        <div class="info-item" v-if="proyecto.subLineaLineaInvestigacionLinea">
                            <span class="info-label">Sublínea</span>
                            <span class="info-value">{{ proyecto.subLineaLineaInvestigacionLinea }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Programa</span>
                            <span class="info-value">{{ proyecto.programa }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Duración</span>
                            <span class="info-value">{{ proyecto.duracion }}</span>
                        </div>
                        <div class="info-item" v-if="proyecto.lugarEjecucion">
                            <span class="info-label">Lugar de Ejecución</span>
                            <span class="info-value">{{ proyecto.lugarEjecucion }}</span>
                        </div>
                        <div class="info-item" v-if="proyecto.fechaIni">
                            <span class="info-label">Fecha de Inicio</span>
                            <span class="info-value">{{ formatDate(proyecto.fechaIni) }}</span>
                        </div>
                        <div class="info-item" v-if="proyecto.fechaFin">
                            <span class="info-label">Fecha de Fin</span>
                            <span class="info-value">{{ formatDate(proyecto.fechaFin) }}</span>
                        </div>
                        <div class="info-item" v-if="proyecto.palabrasClave">
                            <span class="info-label">Palabras Clave</span>
                            <span class="info-value">{{ proyecto.palabrasClave }}</span>
                        </div>
                    </div>
                </div>

                <div class="resumen-section">
                    <h5 class="section-title">
                        <font-awesome-icon icon="users" />&nbsp; Integrantes
                    </h5>
                    <table class="table table-borderless table-sm resumen-table" v-if="estudiantes.length > 0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nombres y Apellidos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(e, idx) in estudiantes" :key="e.id">
                                <td class="text-muted">{{ idx + 1 }}</td>
                                <td>{{ nombreCompleto(e) }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <p v-else class="text-muted small mb-0">No hay integrantes registrados</p>
                </div>

                <div class="resumen-section">
                    <h5 class="section-title">
                        <font-awesome-icon icon="user-tie" />&nbsp; Asesor
                    </h5>
                    <table class="table table-borderless table-sm resumen-table" v-if="asesores.length > 0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nombres y Apellidos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(a, idx) in asesores" :key="a.id">
                                <td class="text-muted">{{ idx + 1 }}</td>
                                <td>{{ nombreCompleto(a) }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <p v-else class="text-muted small mb-0">No hay asesor registrado</p>
                </div>

                <div class="resumen-section">
                    <h5 class="section-title">
                        <font-awesome-icon icon="gavel" />&nbsp; Jurado(s)
                    </h5>
                    <table class="table table-borderless table-sm resumen-table" v-if="jurados.length > 0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nombres y Apellidos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(j, idx) in jurados" :key="j.id">
                                <td class="text-muted">{{ idx + 1 }}</td>
                                <td>{{ nombreCompleto(j) }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <p v-else class="text-muted small mb-0">No hay jurados registrados</p>
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-12 d-flex justify-content-between">
                    <button type="button" class="btn btn-outline-secondary" v-on:click="back">
                        <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;Volver a Jurado
                    </button>
                    <router-link :to="{name: 'PropuestasInvestigadorEditView'}" tag="button" class="btn btn-primary">
                        <font-awesome-icon icon="list"></font-awesome-icon>&nbsp;Ver mis propuestas
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue } from 'vue-property-decorator';
    import AlertService from '@/shared/alert/alert.service';
    import MenuLateralNueva from '@/components/propuesta_nueva/menu_lateral_nueva.vue';
    import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
    import ProyectoService from '@/entities/proyecto/proyecto.service';
    import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
    import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

    const validations: any = {};

    @Component({
        components: { MenuLateralNueva },
        validations
    })

    export default class PropuestaInscripcionNueva extends Vue {
        @Inject('proyectoService') private proyectoService: () => ProyectoService;
        @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;
        @Inject('alertService') private alertService: () => AlertService;

        public proyecto: IProyecto = new Proyecto();
        public estudiantes: IIntegranteProyecto[] = [];
        public asesores: IIntegranteProyecto[] = [];
        public jurados: IIntegranteProyecto[] = [];

        public proyId?: any;

        beforeRouteEnter(to, from, next) {
            next(async vm => {
                vm.initRelationships();
            });
        }

        mounted() {
            this.proyId = this.$route.params.proyectoId;
        }

        nombreCompleto(item: IIntegranteProyecto): string {
            if (item.integranteProyectoUserFirstName || item.integranteProyectoUserLastName) {
                return (item.integranteProyectoUserFirstName || '') + ' ' + (item.integranteProyectoUserLastName || '');
            }
            return item.integranteProyectoUserLogin || '—';
        }

        formatDate(date: Date): string {
            if (!date) return '—';
            const d = new Date(date);
            return d.toLocaleDateString('es-CO', { year: 'numeric', month: 'long', day: 'numeric' });
        }

        public back() {
            this.$router.push({ name: 'PropuestaJuradoNuevaEditView', params: { proyectoId: String(this.proyId) } });
        }

        async initRelationships() {
            try {
                this.proyId = parseInt(this.$route.params.proyectoId);
                this.proyecto = await this.proyectoService().find(this.proyId);

                const [estudRes, asesorRes, juradosRes] = await Promise.all([
                    this.integranteProyectoService().retrieveEstudiantesProyecto(this.proyId),
                    this.integranteProyectoService().retrieveAsesoresProyecto(this.proyId),
                    this.integranteProyectoService().retrieveJuradosProyecto(this.proyId, "Jurado"),
                ]);

                this.estudiantes = estudRes.data || [];
                this.asesores = asesorRes.data || [];
                this.jurados = juradosRes.data || [];
            } catch (e) {
                console.error('Error cargando datos de inscripcion:', e);
                this.alertService().showAlert('Error al cargar los datos del proyecto', 'danger');
            }
        }
    }
</script>

<style scoped>
.page-header {
    border-bottom: 2px solid #003366;
    padding-bottom: 0.75rem;
}

.resumen-card {
    background: #f8f9fa;
    border-radius: 10px;
    padding: 1.5rem;
    box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.resumen-section {
    background: #ffffff;
    border: 1px solid #e9ecef;
    border-radius: 8px;
    padding: 1.25rem;
    margin-bottom: 1rem;
    transition: box-shadow 0.2s ease;
}

.resumen-section:hover {
    box-shadow: 0 4px 12px rgba(0,51,102,0.1);
}

.section-title {
    color: #003366;
    font-weight: 600;
    font-size: 1rem;
    padding-bottom: 0.5rem;
    border-bottom: 1px solid #e9ecef;
    margin-bottom: 0.75rem;
}

.info-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 0.75rem;
}

@media (max-width: 576px) {
    .info-grid {
        grid-template-columns: 1fr;
    }
}

.info-item {
    display: flex;
    flex-direction: column;
}

.info-label {
    font-size: 0.75rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    color: #6c757d;
    font-weight: 600;
    margin-bottom: 0.15rem;
}

.info-value {
    color: #212529;
    font-weight: 500;
}

.resumen-table {
    margin-bottom: 0;
}

.resumen-table th {
    color: #6c757d;
    font-weight: 500;
    font-size: 0.8rem;
    text-transform: uppercase;
    letter-spacing: 0.3px;
    border-top: none;
    padding-left: 0;
}

.resumen-table td {
    border-top: 1px solid #f0f0f0;
    color: #212529;
    padding-left: 0;
}

.resumen-table tbody tr:hover {
    background-color: #f8f9ff;
}

.btn-outline-secondary {
    color: #003366;
    border-color: #003366;
}

.btn-outline-secondary:hover {
    background-color: #003366;
    color: #fff;
}
</style>
