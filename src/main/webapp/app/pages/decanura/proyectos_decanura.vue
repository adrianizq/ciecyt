<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">{{ esAdmin ? 'Proyectos por facultad' : 'Proyectos de mi facultad' }}</h2>
        <p class="text-muted mb-0">
          <template v-if="esAdmin">
            Como administrador puede consultar y designar en todas las facultades; use el filtro para acotar.
          </template>
          <template v-else>
            Desde aquí la decanatura designa el asesor de pasantías y diplomado, y el jurado de la Pasantía Investigativa.
          </template>
        </p>
      </div>
      <div class="d-flex flex-wrap gap-2 align-items-end">
        <div v-if="esAdmin">
          <label class="form-label small mb-1" for="facultad-proyectos">Facultad</label>
          <select id="facultad-proyectos" v-model.number="facultadFiltro" class="form-select form-select-sm">
            <option :value="null">Todas las facultades</option>
            <option v-for="facultad in facultades" :key="facultad.id" :value="facultad.id">
              {{ facultad.facultad }}
            </option>
          </select>
        </div>
        <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="isFetching" @click="retrieve">
          <font-awesome-icon icon="sync" :spin="isFetching" /> Actualizar
        </button>
      </div>
    </div>

    <p v-if="esAdmin" class="small text-muted mb-2">
      Mostrando {{ proyectosFiltrados.length }} de {{ proyectos.length }} proyectos.
    </p>

    <jhi-loading v-if="isFetching"></jhi-loading>
    <div v-else-if="proyectosFiltrados.length === 0" class="alert alert-light border text-center text-muted">
      No hay proyectos para la facultad seleccionada.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Estado</th>
            <th>Propuesta</th>
            <th>Estudiante(s)</th>
            <th>Modalidad</th>
            <th>Asesor</th>
            <th>Jurado</th>
            <th class="text-end">Acción</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="proyecto in proyectosFiltrados" :key="proyecto.id">
            <td>
              <span class="badge" :class="claseEstado(proyecto.estado)">{{ etiquetaEstado(proyecto.estado) }}</span>
            </td>
            <td>
              <div class="fw-semibold">{{ proyecto.titulo }}</div>
              <div class="small text-muted">
                #{{ proyecto.id }}<span v-if="proyecto.programa"> · {{ proyecto.programa }}</span>
                <span v-if="proyecto.ciclo"> · {{ proyecto.ciclo }}</span>
              </div>
            </td>
            <td>
              <div v-for="estudiante in estudiantesDe(proyecto)" :key="estudiante.id">
                {{ nombreIntegrante(estudiante) }}
              </div>
              <span v-if="estudiantesDe(proyecto).length === 0" class="text-muted">—</span>
            </td>
            <td>{{ proyecto.proyectoModalidadModalidad || '—' }}</td>
            <td>
              <div v-for="asesor in asesoresDe(proyecto)" :key="asesor.id">
                {{ nombreIntegrante(asesor) }}
                <span class="small text-muted d-block">{{ asesor.integranteProyectoRolesModalidadRol }}</span>
              </div>
              <span v-if="asesoresDe(proyecto).length === 0" class="text-muted">Sin asignar</span>
            </td>
            <td>
              <template v-if="tieneJurado(proyecto.proyectoModalidadId)">
                <div v-for="jurado in juradosDe(proyecto)" :key="jurado.id">{{ nombreIntegrante(jurado) }}</div>
                <span v-if="juradosDe(proyecto).length === 0" class="text-muted">Sin asignar</span>
              </template>
              <span v-else class="text-muted">No aplica</span>
            </td>
            <td class="text-end">
              <div class="d-flex flex-wrap justify-content-end gap-2">
                <button
                  v-if="puedeDesignarAsesor(proyecto)"
                  type="button"
                  class="btn btn-outline-primary btn-sm"
                  @click="irAAsignarAsesor(proyecto)"
                >
                  {{ asesoresDe(proyecto).length ? 'Cambiar asesor' : 'Asignar asesor' }}
                </button>
                <button
                  v-if="puedeDesignarJurado(proyecto)"
                  type="button"
                  class="btn btn-outline-primary btn-sm"
                  @click="irAAsignarJurado(proyecto)"
                >
                  {{ juradosDe(proyecto).length ? 'Cambiar jurado' : 'Asignar jurado' }}
                </button>
                <span v-if="!puedeDesignarAsesor(proyecto) && !puedeDesignarJurado(proyecto)" class="text-muted small">
                  La designación la hace el CIECYT
                </span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import { IProyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import FacultadService from '@/entities/facultad/facultad.service';
import { tieneJurado } from '@/shared/config/opcion_grado';

@Component
export default class ProyectosDecanura extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private alertService: () => AlertService;

  @Inject
  private facultadService: () => FacultadService;

  public proyectos: IProyecto[] = [];
  public facultades: any[] = [];
  public facultadFiltro: number | null = null;
  public isFetching = false;

  public get esAdmin(): boolean {
    const account: any = this.$store?.getters?.account;
    const auths: string[] = account?.authorities || [];
    return auths.includes('ROLE_ADMIN');
  }

  public get proyectosFiltrados(): IProyecto[] {
    if (!this.facultadFiltro) {
      return this.proyectos;
    }
    return this.proyectos.filter(proyecto => Number(proyecto.facultadId) === Number(this.facultadFiltro));
  }

  public mounted(): void {
    this.cargarFacultades();
    this.retrieve();
  }

  public cargarFacultades(): void {
    const servicio = this.facultadService ? this.facultadService() : new FacultadService();
    servicio
      .retrieve({ sort: ['facultad,asc'], size: 200 })
      .then(res => {
        const data = Array.isArray(res) ? res : res && res.data;
        const lista = Array.isArray(data) ? data : data && Array.isArray(data.content) ? data.content : [];
        this.facultades = lista.filter((facultad: any) => facultad && facultad.id != null);
      })
      .catch(() => {
        this.facultades = [];
      });
  }

  public retrieve(): void {
    this.isFetching = true;
    this.proyectoService()
      .retrieveAllProyectosIntegrantes()
      .then(res => {
        this.proyectos = res.data || [];
        this.isFetching = false;
      })
      .catch(err => {
        this.isFetching = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public puedeDesignarAsesor(proyecto: IProyecto): boolean {
    return [9001, 9002, 9003, 9005].indexOf(Number(proyecto.proyectoModalidadId)) >= 0;
  }

  public puedeDesignarJurado(proyecto: IProyecto): boolean {
    return Number(proyecto.proyectoModalidadId) === 9002;
  }

  public estudiantesDe(proyecto: IProyecto): any[] {
    return ((proyecto && proyecto.listaIntegrantesProyecto) || []).filter(
      integrante => integrante.integranteProyectoRolesModalidadRol === 'Estudiante'
    );
  }

  public asesoresDe(proyecto: IProyecto): any[] {
    return ((proyecto && proyecto.listaIntegrantesProyecto) || []).filter(
      integrante => (integrante.integranteProyectoRolesModalidadRol || '').indexOf('Asesor') >= 0
    );
  }

  public juradosDe(proyecto: IProyecto): any[] {
    return ((proyecto && proyecto.listaIntegrantesProyecto) || []).filter(
      integrante => integrante.integranteProyectoRolesModalidadRol === 'Jurado'
    );
  }

  public nombreIntegrante(integrante: any): string {
    if (!integrante) {
      return '';
    }
    const nombre = `${integrante.integranteProyectoUserFirstName || ''} ${integrante.integranteProyectoUserLastName || ''}`.trim();
    return nombre || integrante.integranteProyectoUserLogin || 'Sin nombre';
  }

  public etiquetaEstado(estado: string | undefined): string {
    switch (estado) {
      case 'PREINSCRITA':
        return 'Preinscrita';
      case 'EN_VALIDACION_DOCUMENTAL':
        return 'En validación documental';
      case 'OBSERVACIONES_DOCUMENTACION':
        return 'Documentación observada';
      case 'HABILITADO':
        return 'Habilitada';
      case 'EN_REVISION_ASESOR':
        return 'En revisión del asesor';
      case 'APROBADA_POR_ASESOR':
        return 'Aprobada por el asesor';
      case 'EN_REVISION_JURADO_PROPUESTA':
        return 'En revisión del jurado';
      case 'LISTO_PARA_SUSTENTAR':
        return 'Lista para sustentar';
      case 'SUSTENTACION_PROGRAMADA':
        return 'Sustentación programada';
      case 'LISTO_PARA_SOCIALIZAR':
        return 'Lista para socializar';
      case 'SOCIALIZACION_PROGRAMADA':
        return 'Socialización programada';
      case 'NOTA_DEFINITIVA':
        return 'Nota definitiva';
      case 'FINALIZADO':
        return 'Finalizado';
      default:
        return estado ? estado.replace(/_/g, ' ') : 'Sin estado';
    }
  }

  public claseEstado(estado: string | undefined): string {
    switch (estado) {
      case 'EN_VALIDACION_DOCUMENTAL':
      case 'CORRECCIONES_ASESOR':
      case 'CORRECCIONES_JURADO_PROPUESTA':
        return 'bg-warning text-dark';
      case 'OBSERVACIONES_DOCUMENTACION':
      case 'NO_VIABLE':
        return 'bg-danger';
      case 'HABILITADO':
      case 'APROBADA_POR_ASESOR':
        return 'bg-success';
      case 'EN_REVISION_ASESOR':
      case 'EN_REVISION_JURADO_PROPUESTA':
        return 'bg-info text-dark';
      case 'LISTO_PARA_SUSTENTAR':
      case 'SUSTENTACION_PROGRAMADA':
      case 'LISTO_PARA_SOCIALIZAR':
      case 'SOCIALIZACION_PROGRAMADA':
        return 'bg-primary';
      default:
        return 'bg-secondary';
    }
  }

  public tieneJurado(modalidadId: number | undefined | null): boolean {
    return tieneJurado(modalidadId);
  }

  public irAAsignarAsesor(proyecto: IProyecto): void {
    this.$router.push({ name: 'AsignarAsesorView', params: { proyectoId: proyecto.id } });
  }

  public irAAsignarJurado(proyecto: IProyecto): void {
    this.$router.push({ name: 'AsignarJuradoView', params: { proyectoId: proyecto.id } });
  }
}
</script>
