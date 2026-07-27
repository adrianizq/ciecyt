import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { ICategorizacion, Categorizacion } from '@/shared/model/categorizacion.model';
import CategorizacionService from './categorizacion.service';

const validations: any = {
  categorizacion: {
    categoria: {},
    descripcion: {},
  },
};

@Component({
  validations,
})
export default class CategorizacionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('categorizacionService') private categorizacionService: () => CategorizacionService;
  public categorizacion: ICategorizacion = new Categorizacion();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categorizacionId) {
        vm.retrieveCategorizacion(to.params.categorizacionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.categorizacion.id) {
      this.categorizacionService()
        .update(this.categorizacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.categorizacion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.categorizacionService()
        .create(this.categorizacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.categorizacion.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCategorizacion(categorizacionId): void {
    this.categorizacionService()
      .find(categorizacionId)
      .then(res => {
        this.categorizacion = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
