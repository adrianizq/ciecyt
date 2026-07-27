import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IUsuario, Usuario } from '@/shared/model/usuario.model';
import UsuarioService from './usuario.service';

const validations: any = {
  usuario: {
    usuario: {},
    descripcion: {},
  },
};

@Component({
  validations,
})
export default class UsuarioUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('usuarioService') private usuarioService: () => UsuarioService;
  public usuario: IUsuario = new Usuario();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usuarioId) {
        vm.retrieveUsuario(to.params.usuarioId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.usuario.id) {
      this.usuarioService()
        .update(this.usuario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.usuario.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.usuarioService()
        .create(this.usuario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.usuario.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUsuario(usuarioId): void {
    this.usuarioService()
      .find(usuarioId)
      .then(res => {
        this.usuario = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
