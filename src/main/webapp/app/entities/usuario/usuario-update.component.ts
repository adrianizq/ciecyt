import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

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
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class UsuarioUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private usuarioService: () => UsuarioService;
  public usuario: IUsuario = new Usuario();
  public isSaving = false;

  @Hook
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
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.usuarioService()
        .create(this.usuario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.usuario.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
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
