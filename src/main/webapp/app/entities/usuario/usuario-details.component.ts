import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUsuario } from '@/shared/model/usuario.model';
import UsuarioService from './usuario.service';

@Component
export default class UsuarioDetails extends Vue {
  @Inject('usuarioService') private usuarioService: () => UsuarioService;
  public usuario: IUsuario = {};

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
