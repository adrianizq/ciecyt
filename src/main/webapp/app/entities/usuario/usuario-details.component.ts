import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IUsuario } from '@/shared/model/usuario.model';
import UsuarioService from './usuario.service';

@Component
export default class UsuarioDetails extends Vue {
  @Inject private usuarioService: () => UsuarioService;
  public usuario: IUsuario = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usuarioId) {
        vm.retrieveUsuario(to.params.usuarioId);
      }
    });
  }

  public retrieveUsuario(usuarioId) {
    this.usuarioService()
      .find(usuarioId)
      .then(res => {
        this.usuario = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
