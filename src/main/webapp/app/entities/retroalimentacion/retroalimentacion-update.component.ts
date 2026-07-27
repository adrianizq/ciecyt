import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProyectoFaseService from '../proyecto-fase/proyecto-fase.service';
import { IProyectoFase } from '@/shared/model/proyecto-fase.model';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IRetroalimentacion, Retroalimentacion } from '@/shared/model/retroalimentacion.model';
import RetroalimentacionService from './retroalimentacion.service';

const validations: any = {
  retroalimentacion: {
    titulo: {},
    retroalimentacion: {},
    fechaRetroalimentacion: {},
    estadoRetroalimentacion: {},
    estadoProyectoFase: {},
  },
};

@Component({
  validations,
})
export default class RetroalimentacionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('retroalimentacionService') private retroalimentacionService: () => RetroalimentacionService;
  public retroalimentacion: IRetroalimentacion = new Retroalimentacion();

  @Inject('proyectoFaseService') private proyectoFaseService: () => ProyectoFaseService;

  public proyectoFases: IProyectoFase[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.retroalimentacionId) {
        vm.retrieveRetroalimentacion(to.params.retroalimentacionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.retroalimentacion.id) {
      this.retroalimentacionService()
        .update(this.retroalimentacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.retroalimentacion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.retroalimentacionService()
        .create(this.retroalimentacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.retroalimentacion.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRetroalimentacion(retroalimentacionId): void {
    this.retroalimentacionService()
      .find(retroalimentacionId)
      .then(res => {
        this.retroalimentacion = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.proyectoFaseService()
      .retrieve()
      .then(res => {
        this.proyectoFases = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
