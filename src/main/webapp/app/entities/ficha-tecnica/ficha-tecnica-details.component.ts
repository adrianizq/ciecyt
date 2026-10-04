import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IFichaTecnica } from '@/shared/model/ficha-tecnica.model';
import FichaTecnicaService from './ficha-tecnica.service';

@Component
export default class FichaTecnicaDetails extends Vue {
  @Inject private fichaTecnicaService: () => FichaTecnicaService;
  public fichaTecnica: IFichaTecnica = {};

  @Hook
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
    this.$router.push({ name: 'FichaTecnica' });
  }
}
