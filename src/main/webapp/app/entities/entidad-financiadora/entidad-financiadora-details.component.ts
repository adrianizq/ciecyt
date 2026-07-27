import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEntidadFinanciadora } from '@/shared/model/entidad-financiadora.model';
import EntidadFinanciadoraService from './entidad-financiadora.service';

@Component
export default class EntidadFinanciadoraDetails extends Vue {
  @Inject('entidadFinanciadoraService') private entidadFinanciadoraService: () => EntidadFinanciadoraService;
  public entidadFinanciadora: IEntidadFinanciadora = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entidadFinanciadoraId) {
        vm.retrieveEntidadFinanciadora(to.params.entidadFinanciadoraId);
      }
    });
  }

  public retrieveEntidadFinanciadora(entidadFinanciadoraId) {
    this.entidadFinanciadoraService()
      .find(entidadFinanciadoraId)
      .then(res => {
        this.entidadFinanciadora = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
