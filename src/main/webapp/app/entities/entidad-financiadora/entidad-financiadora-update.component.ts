import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import EntidadService from '../entidad/entidad.service';
import { IEntidad } from '@/shared/model/entidad.model';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IEntidadFinanciadora, EntidadFinanciadora } from '@/shared/model/entidad-financiadora.model';
import EntidadFinanciadoraService from './entidad-financiadora.service';

const validations: any = {
  entidadFinanciadora: {
    valor: {},
    aprobada: {},
  },
};

@Component({
  validations,
})
export default class EntidadFinanciadoraUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('entidadFinanciadoraService') private entidadFinanciadoraService: () => EntidadFinanciadoraService;
  public entidadFinanciadora: IEntidadFinanciadora = new EntidadFinanciadora();

  @Inject('entidadService') private entidadService: () => EntidadService;

  public entidads: IEntidad[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entidadFinanciadoraId) {
        vm.retrieveEntidadFinanciadora(to.params.entidadFinanciadoraId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.entidadFinanciadora.id) {
      this.entidadFinanciadoraService()
        .update(this.entidadFinanciadora)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.entidadFinanciadora.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.entidadFinanciadoraService()
        .create(this.entidadFinanciadora)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.entidadFinanciadora.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEntidadFinanciadora(entidadFinanciadoraId): void {
    this.entidadFinanciadoraService()
      .find(entidadFinanciadoraId)
      .then(res => {
        this.entidadFinanciadora = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.entidadService()
      .retrieve()
      .then(res => {
        this.entidads = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
