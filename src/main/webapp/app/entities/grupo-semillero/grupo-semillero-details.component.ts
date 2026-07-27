import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGrupoSemillero } from '@/shared/model/grupo-semillero.model';
import GrupoSemilleroService from './grupo-semillero.service';

@Component
export default class GrupoSemilleroDetails extends Vue {
  @Inject('grupoSemilleroService') private grupoSemilleroService: () => GrupoSemilleroService;
  public grupoSemillero: IGrupoSemillero = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.grupoSemilleroId) {
        vm.retrieveGrupoSemillero(to.params.grupoSemilleroId);
      }
    });
  }

  public retrieveGrupoSemillero(grupoSemilleroId) {
    this.grupoSemilleroService()
      .find(grupoSemilleroId)
      .then(res => {
        this.grupoSemillero = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
