import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFormato } from '@/shared/model/formato.model';
import FormatoService from './formato.service';

@Component
export default class FormatoDetails extends Vue {
  @Inject('formatoService') private formatoService: () => FormatoService;
  public formato: IFormato = {};

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
    this.$router.go(-1);
  }
}
