<template>
  <div class="row">
    <div class="col-12">
      <h2>Solicitudes y novedades</h2>
      <p class="text-muted">
        Registre aquí las novedades sustanciales, impedimentos o solicitudes de reemplazo. El CIECYT revisará la
        solicitud y dejará constancia de la decisión.
      </p>

      <jhi-loading v-if="isFetching"></jhi-loading>

      <div v-else>
        <div class="card mb-3">
          <div class="card-body">
            <div class="small text-muted">Proyecto</div>
            <h5 class="mb-1">{{ proyecto.titulo }}</h5>
            <div class="small text-muted">
              #{{ proyecto.id }}<span v-if="proyecto.programa"> · {{ proyecto.programa }}</span>
              <span v-if="proyecto.ciclo"> · {{ proyecto.ciclo }}</span>
            </div>
          </div>
        </div>

        <div v-if="!integranteActual" class="alert alert-warning">
          No se encontró su participación como integrante de este proyecto. Solo un integrante puede radicar solicitudes.
        </div>

        <div v-else class="card mb-3">
          <div class="card-body">
            <h5 class="mb-3">Nueva solicitud</h5>
            <div class="mb-3">
              <label class="form-label" for="asunto-solicitud">Tipo de solicitud</label>
              <select id="asunto-solicitud" v-model="asunto" class="form-select">
                <option v-for="opcion in asuntos" :key="opcion" :value="opcion">{{ opcion }}</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label" for="texto-solicitud">Descripción</label>
              <textarea
                id="texto-solicitud"
                v-model="textoSolicitud"
                class="form-control"
                rows="4"
                placeholder="Explique la novedad, el impedimento o el reemplazo solicitado."
              ></textarea>
            </div>
            <button type="button" class="btn btn-primary" :disabled="guardando" @click="crearSolicitud">
              <font-awesome-icon icon="paper-plane" /> Enviar solicitud
            </button>
          </div>
        </div>

        <div class="card">
          <div class="card-body">
            <h5 class="mb-3">Solicitudes radicadas</h5>
            <div v-if="cargandoSolicitudes" class="text-muted">Cargando solicitudes...</div>
            <div v-else-if="solicitudes.length === 0" class="text-muted">No hay solicitudes registradas.</div>
            <ul v-else class="list-unstyled mb-0">
              <li v-for="solicitud in solicitudes" :key="solicitud.id" class="border-start ps-3 pb-3">
                <div class="d-flex flex-wrap justify-content-between gap-2">
                  <div class="fw-semibold">{{ solicitud.asunto }}</div>
                  <span class="badge" :class="solicitud.estado ? 'bg-success' : 'bg-warning text-dark'">
                    {{ solicitud.estado ? 'Resuelta' : 'Pendiente' }}
                  </span>
                </div>
                <div class="small text-muted">{{ fechaSolicitud(solicitud.fechaSolicitud) }}</div>
                <div class="small">{{ solicitud.textoSolicitud }}</div>
              </li>
            </ul>
          </div>
        </div>

        <button type="button" class="btn btn-secondary mt-3" @click="volver">
          <font-awesome-icon icon="arrow-left" /> Volver
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import { ISolicitud } from '@/shared/model/solicitud.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import SolicitudService from '@/entities/solicitud/solicitud.service';

@Component
export default class SolicitarNovedad extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private solicitudService: () => SolicitudService;

  @Inject
  private alertService: () => AlertService;

  public proyecto: IProyecto = new Proyecto();
  public solicitudes: ISolicitud[] = [];
  public isFetching = true;
  public cargandoSolicitudes = true;
  public guardando = false;
  public asunto = 'Novedad sustancial de la propuesta';
  public textoSolicitud = '';
  public asuntos = [
    'Novedad sustancial de la propuesta',
    'Impedimento de jurado',
    'Solicitud de reemplazo de asesor',
    'Solicitud de reemplazo de jurado',
    'Retiro de estudiante',
    'Otra solicitud',
  ];

  public get proyId(): number {
    return parseInt(this.$route.params.proyectoId, 10);
  }

  public get integranteActual(): any {
    const account = this.$store.getters.account;
    if (!account || !this.proyecto.listaIntegrantesProyecto) {
      return null;
    }
    return (
      this.proyecto.listaIntegrantesProyecto.find(
        integrante => integrante.integranteProyectoUserId === account.id || integrante.integranteProyectoUserLogin === account.login
      ) || null
    );
  }

  public mounted(): void {
    this.cargarProyecto();
    this.cargarSolicitudes();
  }

  public cargarProyecto(): void {
    this.proyectoService()
      .find(this.proyId)
      .then(proyecto => {
        this.proyecto = proyecto;
        this.isFetching = false;
      })
      .catch(err => {
        this.isFetching = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public cargarSolicitudes(): void {
    this.cargandoSolicitudes = true;
    this.solicitudService()
      .findByProyecto(this.proyId)
      .then(solicitudes => {
        this.solicitudes = solicitudes || [];
        this.cargandoSolicitudes = false;
      })
      .catch(() => {
        this.solicitudes = [];
        this.cargandoSolicitudes = false;
      });
  }

  public crearSolicitud(): void {
    if (!this.integranteActual) {
      return;
    }
    if (!this.textoSolicitud || this.textoSolicitud.trim().length < 10) {
      this.alertService().showAlert('Describa la solicitud con al menos 10 caracteres.', 'warning');
      return;
    }
    this.guardando = true;
    this.solicitudService()
      .create({
        asunto: this.asunto,
        textoSolicitud: this.textoSolicitud.trim(),
        fechaSolicitud: new Date(),
        estado: false,
        solicitudIntegranteProyectoId: this.integranteActual.id,
      })
      .then(() => {
        this.guardando = false;
        this.textoSolicitud = '';
        this.alertService().showAlert('Solicitud radicada. El CIECYT la revisará.', 'success');
        this.cargarSolicitudes();
      })
      .catch(err => {
        this.guardando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public fechaSolicitud(fecha: any): string {
    if (!fecha) {
      return '';
    }
    const valor = new Date(fecha);
    if (isNaN(valor.getTime())) {
      return '';
    }
    return valor.toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit', year: 'numeric' });
  }

  public volver(): void {
    this.$router.back();
  }
}
</script>
