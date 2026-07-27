import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFichaTecnica } from '@/shared/model/ficha-tecnica.model';
import FichaTecnicaService from './ficha-tecnica.service';

@Component
export default class FichaTecnicaDetails extends Vue {
  @Inject('fichaTecnicaService') private fichaTecnicaService: () => FichaTecnicaService;
  public fichaTecnica: IFichaTecnica = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fichaTecnicaId) {
        vm.retrieveFichaTecnica(to.params.fichaTecnicaId);
      }
    });
  }

  public retrieveFichaTecnica(fichaTecnicaId) {
    this.fichaTecnicaService()
      .find(fichaTecnicaId)
      .then(res => {
        this.fichaTecnica = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
