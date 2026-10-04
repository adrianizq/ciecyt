import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import LineaInvestigacionService from '../linea-investigacion/linea-investigacion.service';
import { ILineaInvestigacion } from '@/shared/model/linea-investigacion.model';

import GrupoSemilleroService from '../grupo-semillero/grupo-semillero.service';
import { IGrupoSemillero } from '@/shared/model/grupo-semillero.model';

import ModalidadService from '../modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';

import FacultadService from '../facultad/facultad.service';
import { IFacultad } from '@/shared/model/facultad.model';

import AlertService from '@/shared/alert/alert.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from './proyecto.service';

const validations: any = {
  proyecto: {
    titulo: {},
    url: {},
    lugarEjecucion: {},
    duracion: {},
    fechaIni: {},
    fechaFin: {},
    contrapartidaPesos: {},
    contrapartidaEspecie: {},
    palabrasClave: {},
    convocatoria: {},
  },
};

@Component({
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class ProyectoUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private proyectoService: () => ProyectoService;
  public proyecto: IProyecto = new Proyecto();

  @Inject private lineaInvestigacionService: () => LineaInvestigacionService;

  public lineaInvestigacions: ILineaInvestigacion[] = [];

  @Inject private grupoSemilleroService: () => GrupoSemilleroService;

  public grupoSemilleros: IGrupoSemillero[] = [];

  @Inject private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];

  @Inject private facultadService: () => FacultadService;

  public facultads: IFacultad[] = [];
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.proyecto.id) {
      this.proyectoService()
        .update(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyecto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.proyectoService()
        .create(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyecto.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    }
  }

  public retrieveProyecto(proyectoId): void {
    this.proyectoService()
      .find(proyectoId)
      .then(res => {
        this.proyecto = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.lineaInvestigacionService()
      .retrieve()
      .then(res => {
        this.lineaInvestigacions = res.data;
      });
    this.grupoSemilleroService()
      .retrieve()
      .then(res => {
        this.grupoSemilleros = res.data;
      });
    this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });
    this.facultadService()
      .retrieve()
      .then(res => {
        this.facultads = res.data;
      });
    this.lineaInvestigacionService()
      .retrieve()
      .then(res => {
        this.lineaInvestigacions = res.data;
      });
  }
}
