import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IMunicipio } from '@/shared/model/municipio.model';
import MunicipioService from './municipio.service';

@Component
export default class MunicipioDetails extends Vue {
  @Inject private municipioService: () => MunicipioService;
  public municipio: IMunicipio = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.municipioId) {
        vm.retrieveMunicipio(to.params.municipioId);
      }
    });
  }

  public retrieveMunicipio(municipioId) {
    this.municipioService()
      .find(municipioId)
      .then(res => {
        this.municipio = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
