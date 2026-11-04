<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">Métricas de negocio</h2>
        <p class="text-muted mb-0">Resumen de usuarios, proyectos, catálogos y cargas de trabajo.</p>
      </div>
      <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="cargando" @click="cargar">
        <font-awesome-icon icon="sync" :spin="cargando" /> Actualizar
      </button>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else>
      <div class="row g-3 mb-3">
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Usuarios</div>
            <div class="fs-3 fw-semibold">{{ usuarios.length }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Proyectos</div>
            <div class="fs-3 fw-semibold">{{ proyectos.length }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Facultades</div>
            <div class="fs-3 fw-semibold">{{ facultades.length }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Ciclos / periodos</div>
            <div class="fs-3 fw-semibold">{{ ciclos.length }} / {{ periodos.length }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Estudiantes</div>
            <div class="fs-3 fw-semibold">{{ estudiantes }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Asesores designados</div>
            <div class="fs-3 fw-semibold">{{ asesores }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">Jurados designados</div>
            <div class="fs-3 fw-semibold">{{ jurados }}</div>
          </div></div>
        </div>
        <div class="col-6 col-lg-3">
          <div class="card"><div class="card-body py-3">
            <div class="small text-muted">En validación</div>
            <div class="fs-3 fw-semibold">{{ porEstado['EN_VALIDACION_DOCUMENTAL'] || 0 }}</div>
          </div></div>
        </div>
      </div>

      <div class="row g-3">
        <div class="col-lg-6">
          <div class="card h-100">
            <div class="card-header">Proyectos por estado</div>
            <div class="card-body">
              <div v-if="estados.length === 0" class="text-muted small">Sin datos.</div>
              <table v-else class="table table-sm mb-0">
                <tbody>
                  <tr v-for="item in estados" :key="item.estado">
                    <td>{{ etiquetaEstado(item.estado) }}</td>
                    <td class="text-end fw-semibold">{{ item.total }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="card h-100">
            <div class="card-header">Proyectos por modalidad</div>
            <div class="card-body">
              <div v-if="modalidades.length === 0" class="text-muted small">Sin datos.</div>
              <table v-else class="table table-sm mb-0">
                <tbody>
                  <tr v-for="item in modalidades" :key="item.modalidad">
                    <td>{{ item.modalidad }}</td>
                    <td class="text-end fw-semibold">{{ item.total }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import FacultadService from '@/entities/facultad/facultad.service';
import CicloService from '@/entities/ciclo/ciclo.service';
import PeriodoService from '@/entities/periodo/periodo.service';
import UserManagementService from '@/admin/user-management/user-management.service';
import { IProyecto } from '@/shared/model/proyecto.model';

@Component
export default class MetricasNegocio extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private facultadService: () => FacultadService;

  @Inject
  private cicloService: () => CicloService;

  @Inject
  private periodoService: () => PeriodoService;

  @Inject({ from: 'userService' })
  private userService: () => UserManagementService;

  @Inject
  private alertService: () => AlertService;

  public proyectos: IProyecto[] = [];
  public facultades: any[] = [];
  public ciclos: any[] = [];
  public periodos: any[] = [];
  public usuarios: any[] = [];
  public cargando = false;

  public mounted(): void {
    this.cargar();
  }

  public get porEstado(): { [estado: string]: number } {
    return this.proyectos.reduce((acc, proyecto) => {
      const estado = String(proyecto.estado || 'SIN_ESTADO');
      acc[estado] = (acc[estado] || 0) + 1;
      return acc;
    }, {} as { [estado: string]: number });
  }

  public get estados(): Array<{ estado: string; total: number }> {
    return Object.keys(this.porEstado)
      .map(estado => ({ estado, total: this.porEstado[estado] }))
      .sort((a, b) => b.total - a.total);
  }

  public get modalidades(): Array<{ modalidad: string; total: number }> {
    const conteo: { [modalidad: string]: number } = {};
    for (const proyecto of this.proyectos) {
      const modalidad = proyecto.proyectoModalidadModalidad || 'Sin modalidad';
      conteo[modalidad] = (conteo[modalidad] || 0) + 1;
    }
    return Object.keys(conteo)
      .map(modalidad => ({ modalidad, total: conteo[modalidad] }))
      .sort((a, b) => b.total - a.total);
  }

  public get estudiantes(): number {
    return this.contarIntegrantes('Estudiante');
  }

  public get asesores(): number {
    return this.contarIntegrantes('Asesor');
  }

  public get jurados(): number {
    return this.contarIntegrantes('Jurado');
  }

  public cargar(): void {
    this.cargando = true;
    Promise.all([
      this.proyectoService().retrieveAllProyectosIntegrantes(),
      this.facultadService().retrieve({ page: 0, size: 200, sort: ['facultad,asc'] }),
      this.cicloService().retrieveAll(),
      this.periodoService().retrieveAll(),
      this.userService().retrieveAll(),
    ])
      .then(([proyectos, facultades, ciclos, periodos, usuarios]) => {
        this.proyectos = proyectos.data || [];
        this.facultades = facultades.data || [];
        this.ciclos = ciclos || [];
        this.periodos = periodos || [];
        this.usuarios = usuarios.data || [];
        this.cargando = false;
      })
      .catch(err => {
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public etiquetaEstado(estado: string): string {
    return String(estado || 'SIN_ESTADO').replace(/_/g, ' ');
  }

  private contarIntegrantes(rol: string): number {
    const ids = new Set<number>();
    for (const proyecto of this.proyectos) {
      for (const integrante of proyecto.listaIntegrantesProyecto || []) {
        const rolIntegrante = integrante.integranteProyectoRolesModalidadRol || '';
        if (rol === 'Asesor' ? rolIntegrante.indexOf('Asesor') >= 0 : rolIntegrante === rol) {
          const id = integrante.integranteProyectoUserId || integrante.integranteProyectoExternoId;
          if (id != null) {
            ids.add(Number(id));
          }
        }
      }
    }
    return ids.size;
  }
}
</script>
