import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

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
  validations,
})
export default class ProyectoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  public proyecto: IProyecto = new Proyecto();

  @Inject('lineaInvestigacionService') private lineaInvestigacionService: () => LineaInvestigacionService;

  public lineaInvestigacions: ILineaInvestigacion[] = [];

  @Inject('grupoSemilleroService') private grupoSemilleroService: () => GrupoSemilleroService;

  public grupoSemilleros: IGrupoSemillero[] = [];

  @Inject('modalidadService') private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];

  @Inject('facultadService') private facultadService: () => FacultadService;

  public facultads: IFacultad[] = [];
  public isSaving = false;

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
        });
    } else {
      this.proyectoService()
        .create(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyecto.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
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
