import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IFichaTecnica, FichaTecnica } from '@/shared/model/ficha-tecnica.model';
import FichaTecnicaService from './ficha-tecnica.service';

const validations: any = {
  fichaTecnica: {
    tituloProfesional: {},
    tituloPostgrado: {},
    experiencia: {},
  },
};

@Component({
  validations,
})
export default class FichaTecnicaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('fichaTecnicaService') private fichaTecnicaService: () => FichaTecnicaService;
  public fichaTecnica: IFichaTecnica = new FichaTecnica();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fichaTecnicaId) {
        vm.retrieveFichaTecnica(to.params.fichaTecnicaId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.fichaTecnica.id) {
      this.fichaTecnicaService()
        .update(this.fichaTecnica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.fichaTecnica.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.fichaTecnicaService()
        .create(this.fichaTecnica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.fichaTecnica.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFichaTecnica(fichaTecnicaId): void {
    this.fichaTecnicaService()
      .find(fichaTecnicaId)
      .then(res => {
        this.fichaTecnica = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
