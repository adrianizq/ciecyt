import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IFormato } from '@/shared/model/formato.model';
import FormatoService from './formato.service';

@Component
export default class FormatoDetails extends Vue {
  @Inject private formatoService: () => FormatoService;
  public formato: IFormato = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.formatoId) {
        vm.retrieveFormato(to.params.formatoId);
      }
    });
  }

  public retrieveFormato(formatoId) {
    this.formatoService()
      .find(formatoId)
      .then(res => {
        this.formato = res;
      });
  }

  public previousState() {
    this.$router.push({ name: 'Formato' });
  }
}
