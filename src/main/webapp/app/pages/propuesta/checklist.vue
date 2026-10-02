<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    <div class="col-sm-8">
      <form name="checklistRequisitos" role="form" novalidate v-on:submit.prevent>
        <div class="mb-3 form-group">
          <h2>Requisitos de Inscripción</h2>
          <span>
            Complete todos los requisitos obligatorios. El CIECYT revisa cada uno y lo aprueba o lo
            rechaza con una observación (Acuerdo 025, art. 5, parágrafo 1).
          </span>
        </div>

        <div v-if="cargando" class="mb-3 form-group">Cargando requisitos...</div>

        <div v-else-if="requisitos.length === 0" class="alert alert-info">
          Este proyecto todavía no tiene requisitos de inscripción.
        </div>

        <div v-for="requisito in requisitos" :key="requisito.id" class="card mb-3">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-start">
              <div>
                <h5 class="mb-0">{{ requisito.requisitoProyectoRequisitoNombre }}</h5>
                <small class="text-muted">{{ requisito.requisitoProyectoRequisitoCodigo }}</small>
                <span
                  v-if="requisito.requisitoProyectoRequisitoObligatorio"
                  class="badge badge-danger ml-2"
                  >Obligatorio</span
                >
              </div>
              <span class="badge" :class="claseEstado(requisito)">{{ etiquetaEstado(requisito) }}</span>
            </div>

            <div v-if="esCampo(requisito)" class="mt-3">
              <label :for="'dato-' + requisito.id">Avance del trabajo de grado (%)</label>
              <input
                :id="'dato-' + requisito.id"
                type="number"
                min="0"
                max="100"
                step="0.01"
                class="form-control"
                style="max-width: 180px"
                v-model="datos[requisito.id]"
                :disabled="!editable(requisito) || guardando"
              />
            </div>

            <div v-else class="mt-3">
              <label :for="'archivo-' + requisito.id">Archivo</label>
              <div>
                <a
                  v-if="requisito.archivo"
                  href="#"
                  class="mr-3"
                  v-on:click.prevent="descargar(requisito)"
                  >Ver archivo entregado</a
                >
                <input
                  v-if="editable(requisito)"
                  :id="'archivo-' + requisito.id"
                  type="file"
                  v-on:change="asignarArchivo($event, requisito)"
                />
              </div>
            </div>

            <div v-if="requisito.observacion" class="mt-3">
              <strong>Observación:</strong> {{ requisito.observacion }}
            </div>

            <button
              v-if="editable(requisito)"
              type="button"
              class="btn btn-primary mt-3"
              :disabled="guardando"
              v-on:click="entregar(requisito)"
            >
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Entregar</span>
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject } from 'vue-facing-decorator';
import { mixins } from 'vue-facing-decorator';
import JhiDataUtils from '@/shared/data/data-utils.service';

import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
import FasesService from '@/entities/fases/fases.service';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';
import RequisitoProyectoService from '@/entities/requisito-proyecto/requisito-proyecto.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import { IFases, Fases } from '@/shared/model/fases.model';
import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import { IRequisitoProyecto } from '@/shared/model/requisito-proyecto.model';
import { EnumEstadoRequisito } from '@/shared/model/enumerations/enum-estado-requisito.model';
import { TipoRequisito } from '@/shared/model/enumerations/tipo-requisito.model';

const FASE_DOCUMENTOS = 'Documentos';

@Component({
  components: { MenuLateral },
})
export default class ChecklistPropuesta extends mixins(JhiDataUtils) {
  @Inject  private proyectoService: () => ProyectoService;
  @Inject  private alertService: () => AlertService;
  @Inject  private fasesService: () => FasesService;
  @Inject  private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject  private requisitoProyectoService: () => RequisitoProyectoService;

  public requisitos: IRequisitoProyecto[] = [];
  public adjuntos: IAdjuntoProyectoFase[] = [];
  public pendientes: { [id: number]: any } = {};
  public datos: { [id: number]: string } = {};
  public proyecto: IProyecto = new Proyecto();
  public fase: IFases = new Fases();
  public cargando = true;
  public guardando = false;

  get proyId(): number {
    return parseInt(this.$route.params.proyectoId, 10);
  }

  esCampo(requisito: IRequisitoProyecto): boolean {
    return requisito.requisitoProyectoRequisitoTipo === TipoRequisito.CAMPO;
  }

  editable(requisito: IRequisitoProyecto): boolean {
    return (
      requisito.estado === EnumEstadoRequisito.PENDIENTE ||
      requisito.estado === EnumEstadoRequisito.RECHAZADO
    );
  }

  etiquetaEstado(requisito: IRequisitoProyecto): string {
    switch (requisito.estado) {
      case EnumEstadoRequisito.APROBADO:
        return 'Aprobado';
      case EnumEstadoRequisito.ENTREGADO:
        return 'Entregado';
      case EnumEstadoRequisito.RECHAZADO:
        return 'Rechazado';
      default:
        return 'Pendiente';
    }
  }

  claseEstado(requisito: IRequisitoProyecto): string {
    switch (requisito.estado) {
      case EnumEstadoRequisito.APROBADO:
        return 'badge-success';
      case EnumEstadoRequisito.ENTREGADO:
        return 'badge-info';
      case EnumEstadoRequisito.RECHAZADO:
        return 'badge-danger';
      default:
        return 'badge-secondary';
    }
  }

  asignarArchivo(event, requisito: IRequisitoProyecto) {
    const fileData = event.target.files[0];
    if (!fileData) {
      return;
    }
    const pendiente: any = { nombreArchivoOriginal: fileData.name };
    this.setFileData(event, pendiente, 'archivo', false);
    this.pendientes[requisito.id] = pendiente;
  }

  descargar(requisito: IRequisitoProyecto) {
    const adjunto = this.adjuntoDe(requisito);
    if (adjunto) {
      this.adjuntoProyectoFaseService().downloadFile(adjunto.id, adjunto.nombreArchivoOriginal);
    }
  }

  entregar(requisito: IRequisitoProyecto) {
    if (this.esCampo(requisito)) {
      this.entregarDato(requisito);
      return;
    }
    this.entregarArchivo(requisito);
  }

  private entregarDato(requisito: IRequisitoProyecto) {
    const valor = this.datos[requisito.id];
    if (valor === undefined || valor === null || String(valor).trim() === '') {
      this.alertService().showAlert('Ingrese el avance del trabajo de grado', 'warning');
      return;
    }
    if (isNaN(Number(valor))) {
      this.alertService().showAlert('El avance debe ser un número', 'warning');
      return;
    }

    this.guardando = true;
    this.requisitoProyectoService()
      .entregar(requisito.id, null, String(valor))
      .then(actualizado => {
        this.guardando = false;
        this.reemplazar(actualizado);
        this.alertService().showAlert('Requisito entregado', 'success');
      })
      .catch(() => {
        this.guardando = false;
        this.alertService().showAlert('No se pudo entregar el requisito', 'danger');
      });
  }

  private entregarArchivo(requisito: IRequisitoProyecto) {
    const pendiente = this.pendientes[requisito.id];
    if (!pendiente || !pendiente.archivo) {
      this.alertService().showAlert('Debe seleccionar un archivo', 'warning');
      return;
    }

    this.guardando = true;
    const adjunto = this.adjuntoDe(requisito) || new AdjuntoProyectoFase();
    adjunto.proyectoFaseProyectoId = this.proyecto.id;
    adjunto.proyectoFaseProyectoTitulo = this.proyecto.titulo;
    adjunto.adjuntoProyectoFaseFaseId = this.fase.id;
    adjunto.nombreAdjunto = requisito.requisitoProyectoRequisitoCodigo;
    adjunto.nombreArchivoOriginal = pendiente.nombreArchivoOriginal;
    adjunto.archivo = pendiente.archivo;
    adjunto.archivoContentType = pendiente.archivoContentType;
    adjunto.fechaCreacion = new Date();

    const guardar = adjunto.id
      ? this.adjuntoProyectoFaseService().update(adjunto)
      : this.adjuntoProyectoFaseService().create(adjunto);

    guardar
      .then((guardado: IAdjuntoProyectoFase) => {
        this.refrescarAdjunto(requisito, guardado);
        return this.requisitoProyectoService().entregar(requisito.id, String(guardado.id), null);
      })
      .then(actualizado => {
        this.guardando = false;
        delete this.pendientes[requisito.id];
        this.reemplazar(actualizado);
        this.alertService().showAlert('Requisito entregado', 'success');
      })
      .catch(() => {
        this.guardando = false;
        this.alertService().showAlert('No se pudo entregar el requisito', 'danger');
      });
  }

  private adjuntoDe(requisito: IRequisitoProyecto): IAdjuntoProyectoFase {
    const codigo = requisito.requisitoProyectoRequisitoCodigo;
    return this.adjuntos.find(adjunto => adjunto.nombreAdjunto === codigo);
  }

  private refrescarAdjunto(requisito: IRequisitoProyecto, guardado: IAdjuntoProyectoFase) {
    const indice = this.adjuntos.findIndex(adjunto => adjunto.nombreAdjunto === guardado.nombreAdjunto);
    if (indice >= 0) {
      this.adjuntos.splice(indice, 1, guardado);
    } else {
      this.adjuntos.push(guardado);
    }
  }

  private reemplazar(actualizado: IRequisitoProyecto) {
    const indice = this.requisitos.findIndex(requisito => requisito.id === actualizado.id);
    if (indice >= 0) {
      this.requisitos.splice(indice, 1, actualizado);
    }
  }

  async initRelationships() {
    await this.proyectoService()
      .findProyectoIntegrantes(this.proyId)
      .then(res => {
        this.proyecto = res.data;
      });

    await this.fasesService()
      .findByFase(FASE_DOCUMENTOS)
      .then(res => {
        this.fase = res;
      });

    await this.requisitoProyectoService()
      .generarPorProyecto(this.proyId)
      .then(lista => {
        this.requisitos = lista || [];
        this.requisitos.forEach(requisito => {
          if (this.esCampo(requisito) && requisito.observacion) {
            this.datos[requisito.id] = requisito.observacion;
          }
        });
      });

    if (this.fase && this.fase.id) {
      await this.adjuntoProyectoFaseService()
        .findAdjuntoProyectoFase(this.proyId, this.fase.id)
        .then(res => {
          this.adjuntos = res.data || [];
        });
    }

    this.cargando = false;
  }

  created() {
    this.initRelationships().catch(() => {
      this.cargando = false;
      this.alertService().showAlert('No se pudieron cargar los requisitos de inscripción', 'danger');
    });
  }
}
</script>

<style scoped>
</style>
