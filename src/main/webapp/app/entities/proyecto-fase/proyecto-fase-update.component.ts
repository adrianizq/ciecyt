import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IProyectoFase, ProyectoFase } from '@/shared/model/proyecto-fase.model';
import ProyectoFaseService from './proyecto-fase.service';

const validations: any = {
  proyectoFase: {
    titulo: {},
    cumplida: {},
    fechaCumplimiento: {},
    observaciones: {},
  },
};

@Component({
  validations,
})
export default class ProyectoFaseUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('proyectoFaseService') private proyectoFaseService: () => ProyectoFaseService;
  public proyectoFase: IProyectoFase = new ProyectoFase();

  @Inject('fasesService') private fasesService: () => FasesService;

  public fases: IFases[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoFaseId) {
        vm.retrieveProyectoFase(to.params.proyectoFaseId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.proyectoFase.id) {
      this.proyectoFaseService()
        .update(this.proyectoFase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyectoFase.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.proyectoFaseService()
        .create(this.proyectoFase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyectoFase.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProyectoFase(proyectoFaseId): void {
    this.proyectoFaseService()
      .find(proyectoFaseId)
      .then(res => {
        this.proyectoFase = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.fasesService()
      .retrieve()
      .then(res => {
        this.fases = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
